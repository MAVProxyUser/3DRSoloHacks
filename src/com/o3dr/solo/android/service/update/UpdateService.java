package com.o3dr.solo.android.service.update;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import com.o3dr.solo.android.activity.SettingsActivity;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.appstate.VersionsInfo;
import com.o3dr.solo.android.receiver.DownloadsReceiver;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.NetworkUtils;
import com.o3dr.solo.android.util.Utils;
import com.o3dr.solo.android.util.connection.SshConnection.UploadListener;
import com.o3dr.solo.android.util.file.MD5Checker;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateService extends Service
{
  public static final String ACTION_CANCEL_UPDATE_DOWNLOAD = "com.o3dr.solo.android.action.CANCEL_UPDATE_DOWNLOAD";
  public static final String ACTION_CHECK_FOR_UPDATE = "com.o3dr.solo.android.action.CHECK_FOR_UPDATE";
  public static final String ACTION_DOWNLOAD_SERVER_UPDATE = "com.o3dr.solo.android.action.DOWNLOAD_SERVER_UPDATE";
  public static final String ACTION_PERFORM_VEHICLE_UPDATE = "com.o3dr.solo.android.action.PERFORM_VEHICLE_UPDATE";
  public static final String ACTION_SERVER_UPDATE_DOWNLOAD_COMPLETE = "com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_COMPLETE";
  public static final String ACTION_UPDATE_COMPONENTS_VERSION = "com.o3dr.solo.android.action.UPDATE_COMPONENTS_VERSION";
  public static final String ACTION_UPDATE_DOWNLOAD_CANCELLED = "com.o3dr.solo.android.action.UPDATE_DOWNLOAD_CANCELLED";
  public static final String ACTION_UPDATE_TRANSFER_CANCELLED = "com.o3dr.solo.android.action.ACTION_UPDATE_TRANSFER_CANCELLED";
  public static final String ACTION_VEHICLE_UPDATE_FAILED = "com.o3dr.solo.android.action.VEHICLE_UPDATE_FAILED";
  public static final String ACTION_VEHICLE_UPDATE_SUCCESSFUL = "com.o3dr.solo.android.action.VEHICLE_UPDATE_SUCCESSFUL";
  public static final String ACTION_VEHICLE_UPDATE_TRANSFER_PROGRESS = "com.o3dr.solo.android.action.VEHICLE_UPDATE_TRANSFER_PROGRESS";
  public static final String ARTOO_COMPONENT_NAME = "Solo controller";
  private static final String ARTOO_PRODUCT_TITLE = "Artoo";
  private static final String CONTROLLER_PRODUCT_FILENAME_PREFIX = "3dr-controller-";
  private static final String CONTROLLER_PRODUCT_TITLE = "Controller";
  public static final String EXTRA_UPDATE_PROGRESS = "extra_update_progress";
  private static final int NOTIFICATION_ID = 101;
  public static final String SOLO_COMPONENT_NAME = "Solo";
  private static final String SOLO_PRODUCT_FILENAME_PREFIX = "3dr-solo-";
  private static final String SOLO_PRODUCT_TITLE = "Solo";
  public static final String SSH_PASSWORD = "TjSDBkAu";
  public static final String SSH_USERNAME = "root";
  private static final String TAG = UpdateService.class.getSimpleName();
  public static final String UPDATE_SERVER_API_TOKEN = "bd02efe84c11b41d11c4644e9b04ad4673deb6df";
  private static final Header UPDATE_SERVER_AUTHORIZATION_HEADER = new BasicHeader("Authorization", "Token bd02efe84c11b41d11c4644e9b04ad4673deb6df");
  private static final String VERSION = "Version";
  private AppAnalytics appAnalytics;
  private AppPreferences appPrefs;
  private final UpdateInfo artooLinkUpdateInfo = new UpdateInfo("Solo controller");
  private final ComponentUpdater artooUpdater = new ComponentUpdater("artoo_updater", ArtooLinkManager.getSshLink(), "/VERSION");
  private ExecutorService asyncTasksRunner;
  private DownloadManager downloadMgr;
  private final Handler handler = new Handler();
  private LocalBroadcastManager lbm;
  private final ComponentUpdater pixhawkUpdater = new ComponentUpdater("pixhawk_updater", SoloLinkManager.getSshLink(), "/PIX_VERSION");
  private final ConcurrentHashMap<String, Runnable> runningTasks = new ConcurrentHashMap();
  private SoloApp soloApp;
  private final UpdateInfo soloLinkUpdateInfo = new UpdateInfo("Solo");
  private final ComponentUpdater soloUpdater = new ComponentUpdater("solo_updater", SoloLinkManager.getSshLink(), "/VERSION");
  private final ComponentUpdater stm32Updater = new ComponentUpdater("stm32_updater", ArtooLinkManager.getSshLink(), "/STM_VERSION");
  private final SshConnection.UploadListener uploadFileListener = new SshConnection.UploadListener()
  {
    static final int LISTENER_COUNT = 2;
    static final int MAX_PROGRESS = 100;
    static final int PROGRESS_PER_LISTENER = 50;
    private final Runnable uploadProgressBroadcaster = new Runnable()
    {
      public void run()
      {
        UpdateService.this.handler.removeCallbacks(this);
        int i = 0;
        Iterator localIterator = UpdateService.this.uploadProgressTracker.values().iterator();
        while (localIterator.hasNext())
        {
          Pair localPair = (Pair)localIterator.next();
          if (((Long)localPair.second).longValue() > 0L)
            i = (int)(i + 50L * ((Long)localPair.first).longValue() / ((Long)localPair.second).longValue());
        }
        UpdateService.this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UPDATE_TRANSFER_PROGRESS").putExtra("extra_update_progress", i));
      }
    };

    private void broadcastUploadProgress()
    {
      UpdateService.this.handler.post(this.uploadProgressBroadcaster);
    }

    public void onUploaded(File paramAnonymousFile, long paramAnonymousLong1, long paramAnonymousLong2)
    {
      UpdateService.this.uploadProgressTracker.put(paramAnonymousFile.getName(), Pair.create(Long.valueOf(paramAnonymousLong1), Long.valueOf(paramAnonymousLong2)));
      broadcastUploadProgress();
    }

    public boolean shouldContinueUpload()
    {
      if (UpdateService.this.soloApp == null)
        return false;
      return UpdateService.this.soloApp.getUpdateState().isVehicleUpdating();
    }
  };
  private final ConcurrentHashMap<String, Pair<Long, Long>> uploadProgressTracker = new ConcurrentHashMap();

  private void applyUpdate(ComponentUpdater paramComponentUpdater)
    throws IllegalStateException
  {
    if (paramComponentUpdater.applyUpdate())
    {
      Log.d(TAG, paramComponentUpdater.getTag() + " update completed.");
      return;
    }
    throw new IllegalStateException("Unable to apply " + paramComponentUpdater.getTag() + " update.");
  }

  private void cancelUpdateDownload()
  {
    if (this.artooLinkUpdateInfo.isUpdateDownloading())
    {
      DownloadManager localDownloadManager2 = this.downloadMgr;
      long[] arrayOfLong2 = new long[1];
      arrayOfLong2[0] = this.artooLinkUpdateInfo.getDownloadId();
      localDownloadManager2.remove(arrayOfLong2);
      this.artooLinkUpdateInfo.setDownloadId(-1L);
    }
    if (this.soloLinkUpdateInfo.isUpdateDownloading())
    {
      DownloadManager localDownloadManager1 = this.downloadMgr;
      long[] arrayOfLong1 = new long[1];
      arrayOfLong1[0] = this.soloLinkUpdateInfo.getDownloadId();
      localDownloadManager1.remove(arrayOfLong1);
      this.soloLinkUpdateInfo.setDownloadId(-1L);
    }
    checkIfDownloadsCompleted(true);
  }

  private void checkForUpdate()
  {
    boolean bool1 = true;
    UpdateState localUpdateState = this.soloApp.getUpdateState();
    VersionsInfo localVersionsInfo = this.soloApp.getVersionsInfo();
    localUpdateState.setCheckingForUpdate(bool1);
    Log.d(TAG, "Starting update check.");
    if ((this.artooLinkUpdateInfo.shouldRefreshInfo()) || (this.soloLinkUpdateInfo.shouldRefreshInfo()))
      fetchLatestVersionsInfo();
    if (!localVersionsInfo.areVersionsSet())
      updateComponentsVersion();
    boolean bool2;
    if ((this.soloUpdater.checkForUpdate(this.soloLinkUpdateInfo.getUpdateVersion(), localVersionsInfo.getVehicleVersion())) || (this.artooUpdater.checkForUpdate(this.artooLinkUpdateInfo.getUpdateVersion(), localVersionsInfo.getControllerVersion())))
    {
      bool2 = bool1;
      if ((!bool2) || ((!shouldDownloadUpdate(this.artooLinkUpdateInfo)) && (!shouldDownloadUpdate(this.soloLinkUpdateInfo))))
        break label216;
      label140: localUpdateState.setServerUpdateAvailable(bool1);
      localUpdateState.setVehicleUpdateAvailable(bool2);
      if (bool2)
        break label221;
      Log.d(TAG, "Everything is up to date.");
      DownloadsReceiver.enableDownloadsReceiver(getApplicationContext(), false);
      this.appAnalytics.track("Vehicle up to date", "Version", localVersionsInfo.getControllerVersion());
    }
    while (true)
    {
      saveState();
      localUpdateState.setCheckingForUpdate(false);
      Log.d(TAG, "Completed update check.");
      return;
      bool2 = false;
      break;
      label216: bool1 = false;
      break label140;
      label221: this.appAnalytics.track("Update Available", "Version", localVersionsInfo.getControllerVersion());
    }
  }

  private void checkIfDownloadsCompleted(boolean paramBoolean)
  {
    if ((this.artooLinkUpdateInfo.getDownloadId() == -1L) && (this.soloLinkUpdateInfo.getDownloadId() == -1L))
    {
      DownloadsReceiver.enableDownloadsReceiver(getApplicationContext(), false);
      this.soloApp.getUpdateState().clearServerUpdateDownloadIds();
      if (paramBoolean)
        this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.UPDATE_DOWNLOAD_CANCELLED"));
    }
    else
    {
      return;
    }
    this.soloApp.getUpdateState().setServerUpdateAvailable(false);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_COMPLETE"));
    this.appAnalytics.track("Update Downloaded", "Version", this.artooLinkUpdateInfo.getUpdateVersion());
    Context localContext = getApplicationContext();
    Notification localNotification = Utils.generateAppNotification(localContext, "Solo update download complete.", "Click to continue Solo system update.", PendingIntent.getActivity(localContext, 0, new Intent(localContext, SettingsActivity.class).putExtra("extra_settings_selected_pane_id", 2131493143).addFlags(268435456), 134217728));
    NotificationManagerCompat.from(localContext).notify(101, localNotification);
  }

  private void downloadLatestVersions()
  {
    Log.d(TAG, "Downloading latest versions.");
    File localFile = new File(getExternalFilesDir(null), "releases");
    if ((!localFile.exists()) && (!localFile.mkdirs()))
    {
      Log.d(TAG, "Unable to create releases download directory.");
      return;
    }
    DownloadsReceiver.enableDownloadsReceiver(getApplicationContext(), true);
    int i;
    if ((shouldDownloadUpdate(this.artooLinkUpdateInfo)) && (requestUpdateFileDownload(this.artooLinkUpdateInfo, "3dr-controller-")))
    {
      i = 1;
      if ((!shouldDownloadUpdate(this.soloLinkUpdateInfo)) || (!requestUpdateFileDownload(this.soloLinkUpdateInfo, "3dr-solo-")))
        break label156;
    }
    label156: for (int j = 1; ; j = 0)
    {
      if ((i != 0) || (j != 0))
        break label162;
      DownloadsReceiver.enableDownloadsReceiver(getApplicationContext(), false);
      this.soloApp.getUpdateState().setServerUpdateAvailable(false);
      this.soloApp.getUpdateState().clearServerUpdateDownloadIds();
      return;
      i = 0;
      break;
    }
    label162: this.soloApp.getUpdateState().addServerUpdateDownloadId(this.soloLinkUpdateInfo.getDownloadId(), this.soloLinkUpdateInfo.getUpdateReleaseNotes());
    this.soloApp.getUpdateState().addServerUpdateDownloadId(this.artooLinkUpdateInfo.getDownloadId(), this.artooLinkUpdateInfo.getUpdateReleaseNotes());
  }

  private void fetchLatestVersionsInfo()
  {
    if (NetworkUtils.isOnSololinkNetwork(getApplicationContext()))
      return;
    while (true)
    {
      int j;
      int k;
      try
      {
        DefaultHttpClient localDefaultHttpClient = NetworkUtils.getHttpClient();
        String str1 = this.soloApp.getUpdateState().getUpdateInfoUrl();
        Log.d(TAG, "Checking for updates @ " + str1);
        HttpGet localHttpGet = new HttpGet(str1);
        localHttpGet.setHeader(UPDATE_SERVER_AUTHORIZATION_HEADER);
        HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
        if (localHttpResponse.getStatusLine().getStatusCode() != 200)
          break label376;
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break;
        String str2 = EntityUtils.toString(localHttpEntity, "UTF-8");
        Log.d(TAG, "Server responded with: " + str2);
        JSONArray localJSONArray = new JSONObject(str2).optJSONArray("results");
        if (localJSONArray == null)
          break label365;
        int i = localJSONArray.length();
        j = 0;
        if (j < i)
        {
          localJSONObject = localJSONArray.optJSONObject(j);
          if (localJSONObject == null)
            break label416;
          String str3 = localJSONObject.optString("title");
          k = -1;
          switch (str3.hashCode())
          {
          case 2582783:
            if (!str3.equals("Solo"))
              break;
            k = 0;
            break;
          case -1664073796:
            if (!str3.equals("Controller"))
              break;
            k = 1;
            break;
          case 63540067:
            if (!str3.equals("Artoo"))
              break;
            k = 2;
            break;
            updateProductInfo(localJSONObject, this.soloLinkUpdateInfo);
          }
        }
      }
      catch (IOException localIOException)
      {
        JSONObject localJSONObject;
        Log.e(TAG, "Unable to access firmware server.", localIOException);
        return;
        updateProductInfo(localJSONObject, this.artooLinkUpdateInfo);
      }
      catch (JSONException localJSONException)
      {
        Log.e(TAG, "Unable to parse the server response.", localJSONException);
        return;
      }
      onLatestVersionsRetrieved();
      return;
      label365: Log.d(TAG, "No available products to update.");
      return;
      label376: Log.d(TAG, "No response was obtained from the server.");
      return;
      switch (k)
      {
      case 0:
      case 1:
      case 2:
      }
      label416: j++;
    }
  }

  private void generateTask(String paramString, final Runnable paramRunnable)
  {
    if (!this.runningTasks.containsKey(paramString))
    {
      Task local2 = new Task(this, paramString)
      {
        protected void doInBackground()
        {
          paramRunnable.run();
        }
      };
      this.runningTasks.put(paramString, local2);
      this.asyncTasksRunner.execute(local2);
      Log.i(TAG, "Added task " + paramString);
      return;
    }
    Log.i(TAG, "Task " + paramString + " is already scheduled.");
  }

  private void onLatestVersionsRetrieved()
  {
    Log.d(TAG, "Latest versions retrieved: ");
    Log.d(TAG, this.soloLinkUpdateInfo.toString());
    Log.d(TAG, this.artooLinkUpdateInfo.toString());
  }

  private void performVehicleUpdate()
  {
    final VersionsInfo localVersionsInfo = this.soloApp.getVersionsInfo();
    NotificationManagerCompat.from(getApplicationContext()).cancel(101);
    final CountDownLatch localCountDownLatch1 = new CountDownLatch(2);
    final CountDownLatch localCountDownLatch2 = new CountDownLatch(1);
    final CountDownLatch localCountDownLatch3 = new CountDownLatch(2);
    final AtomicBoolean localAtomicBoolean1 = new AtomicBoolean(false);
    final AtomicBoolean localAtomicBoolean2 = new AtomicBoolean(false);
    this.uploadProgressTracker.clear();
    this.asyncTasksRunner.execute(new Runnable()
    {
      public void run()
      {
        String str = UpdateService.this.soloUpdater.getTag();
        try
        {
          if (!UpdateService.this.soloUpdater.checkForUpdate(UpdateService.this.soloLinkUpdateInfo.getUpdateVersion(), localVersionsInfo.getVehicleVersion()))
            throw new IllegalStateException("Invalid state for solo update.");
        }
        catch (IllegalStateException localIllegalStateException)
        {
          if (UpdateService.this.soloApp.getUpdateState().isVehicleUpdating())
          {
            Log.e(UpdateService.TAG, "Vehicle update failed.", localIllegalStateException);
            UpdateService.this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UPDATE_FAILED"));
          }
          UpdateService.this.soloApp.getUpdateState().setVehicleUpdating(false);
          return;
          if (UpdateService.this.soloApp.getUpdateState().isVehicleUpdating())
            UpdateService.this.transferUpdate(UpdateService.this.soloUpdater, UpdateService.this.soloLinkUpdateInfo);
          localCountDownLatch3.countDown();
          try
          {
            Log.v(str, "Awaiting updates transfer completion.");
            localCountDownLatch3.await();
            UpdateService.this.appAnalytics.track("Update Transferred", "Version", UpdateService.this.artooLinkUpdateInfo.getUpdateVersion());
            Log.v(str, "Update transfer complete.");
            if (UpdateService.this.soloApp.getUpdateState().isVehicleUpdating())
            {
              UpdateService.this.applyUpdate(UpdateService.this.soloUpdater);
              localAtomicBoolean1.set(true);
            }
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            while (true)
              Log.e(UpdateService.TAG, "Wait for transfers completion was interrupted. Should we abort the update?", localInterruptedException);
          }
        }
        finally
        {
          localCountDownLatch3.countDown();
          localCountDownLatch2.countDown();
          localCountDownLatch1.countDown();
          Log.v(str, "Completed solo update process.");
        }
      }
    });
    this.asyncTasksRunner.execute(new Runnable()
    {
      public void run()
      {
        String str = UpdateService.this.artooUpdater.getTag();
        try
        {
          if (!UpdateService.this.artooUpdater.checkForUpdate(UpdateService.this.artooLinkUpdateInfo.getUpdateVersion(), localVersionsInfo.getControllerVersion()))
            throw new IllegalStateException("Invalid state for artoo update");
        }
        catch (IllegalStateException localIllegalStateException)
        {
          if (UpdateService.this.soloApp.getUpdateState().isVehicleUpdating())
          {
            Log.e(UpdateService.TAG, "Vehicle update failed.", localIllegalStateException);
            UpdateService.this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UPDATE_FAILED"));
          }
          UpdateService.this.soloApp.getUpdateState().setVehicleUpdating(false);
          return;
          if (UpdateService.this.soloApp.getUpdateState().isVehicleUpdating())
            UpdateService.this.transferUpdate(UpdateService.this.artooUpdater, UpdateService.this.artooLinkUpdateInfo);
          localCountDownLatch3.countDown();
          try
          {
            Log.v(str, "Waiting for solo system update completion.");
            localCountDownLatch2.await();
            Log.v(UpdateService.TAG, "Solo update transfer complete.");
            if (UpdateService.this.soloApp.getUpdateState().isVehicleUpdating())
            {
              UpdateService.this.applyUpdate(UpdateService.this.artooUpdater);
              localAtomicBoolean2.set(true);
            }
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            while (true)
              Log.e(UpdateService.TAG, "Wait for solo update completion was interrupted. Should we abort the update?", localInterruptedException);
          }
        }
        finally
        {
          localCountDownLatch3.countDown();
          localCountDownLatch1.countDown();
          Log.v(str, "Completed artoo update process.");
        }
      }
    });
    try
    {
      Log.v(TAG, "Waiting for full update completion.");
      localCountDownLatch1.await();
      if ((this.soloApp.getUpdateState().isVehicleUpdating()) && (localAtomicBoolean2.get()) && (localAtomicBoolean1.get()))
      {
        Log.v(TAG, "Vehicle update successful.");
        this.soloApp.getUpdateState().setVehicleUpdateAvailable(false);
        this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UPDATE_SUCCESSFUL"));
        this.appAnalytics.track("Update Applied", "Version", this.artooLinkUpdateInfo.getUpdateVersion());
        localVersionsInfo.reset();
        localVersionsInfo.saveInstance();
      }
      this.soloApp.getUpdateState().setVehicleUpdating(false);
      Log.v(TAG, "Vehicle update completed.");
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        Log.e(TAG, "Wait for updates completion was interrupted. Should we abort the update?", localInterruptedException);
    }
  }

  private boolean requestUpdateFileDownload(UpdateInfo paramUpdateInfo, final String paramString)
  {
    if ((this.downloadMgr == null) || (paramUpdateInfo == null))
      return false;
    long l = paramUpdateInfo.getDownloadId();
    Cursor localCursor;
    if (l != -1L)
    {
      DownloadManager.Query localQuery = new DownloadManager.Query().setFilterById(new long[] { l }).setFilterByStatus(7);
      localCursor = this.downloadMgr.query(localQuery);
      if (localCursor == null);
    }
    Uri localUri1;
    File localFile1;
    Uri localUri2;
    try
    {
      if (localCursor.getCount() > 0)
      {
        Log.d(TAG, "Download is already ongoing!");
        return true;
      }
      localCursor.close();
      paramUpdateInfo.setDownloadId(-1L);
      localUri1 = Uri.parse(paramUpdateInfo.getUpdateUrl());
      localFile1 = new File(getExternalFilesDir(null), "/releases/");
      File localFile2 = new File(localFile1, localUri1.getLastPathSegment());
      localUri2 = Uri.fromFile(localFile2);
      if ((localFile2.isFile()) && (verifyDownloadedUpdate(paramUpdateInfo, localUri2)))
      {
        Log.d(TAG, "Update was already downloaded.");
        return false;
      }
    }
    finally
    {
      localCursor.close();
    }
    FilenameFilter local17 = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return paramAnonymousString.startsWith(paramString);
      }
    };
    File[] arrayOfFile = localFile1.listFiles(local17);
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
      arrayOfFile[j].delete();
    String str = paramUpdateInfo.getComponentName();
    DownloadManager.Request localRequest1 = new DownloadManager.Request(localUri1);
    DownloadManager.Request localRequest2 = localRequest1.addRequestHeader(UPDATE_SERVER_AUTHORIZATION_HEADER.getName(), UPDATE_SERVER_AUTHORIZATION_HEADER.getValue()).setAllowedOverMetered(true).setAllowedOverRoaming(false).setNotificationVisibility(0).setTitle(str + " system update").setDescription("Downloading " + str + " system update...").setVisibleInDownloadsUi(false).setDestinationUri(localUri2);
    paramUpdateInfo.setDownloadId(this.downloadMgr.enqueue(localRequest2));
    return true;
  }

  private void restoreState()
  {
    Log.d(TAG, "Restoring previous state.");
    this.artooLinkUpdateInfo.restoreInstance(this.appPrefs);
    this.soloApp.getUpdateState().addServerUpdateDownloadId(this.artooLinkUpdateInfo.getDownloadId(), this.artooLinkUpdateInfo.getUpdateReleaseNotes());
    this.soloLinkUpdateInfo.restoreInstance(this.appPrefs);
    this.soloApp.getUpdateState().addServerUpdateDownloadId(this.soloLinkUpdateInfo.getDownloadId(), this.soloLinkUpdateInfo.getUpdateReleaseNotes());
  }

  private void saveState()
  {
    this.artooLinkUpdateInfo.saveInstance(this.appPrefs);
    this.soloLinkUpdateInfo.saveInstance(this.appPrefs);
  }

  private boolean shouldDownloadUpdate(UpdateInfo paramUpdateInfo)
  {
    File localFile = paramUpdateInfo.getDownloadedFile();
    if (localFile == null);
    do
    {
      return true;
      if (!localFile.isFile())
      {
        paramUpdateInfo.setDownloadedFile(null);
        return true;
      }
    }
    while (!verifyDownloadedUpdate(paramUpdateInfo, localFile));
    return false;
  }

  private void transferUpdate(ComponentUpdater paramComponentUpdater, UpdateInfo paramUpdateInfo)
    throws IllegalStateException
  {
    if (paramComponentUpdater.transferUpdate(paramUpdateInfo))
    {
      Log.d(TAG, paramComponentUpdater.getTag() + " update transfer complete.");
      return;
    }
    throw new IllegalStateException("Unable to complete " + paramComponentUpdater.getTag() + " update.");
  }

  private void updateComponentsVersion()
  {
    Log.d(TAG, "Updating components version.");
    if (!NetworkUtils.isOnSololinkNetwork(getApplicationContext()));
    while (true)
    {
      return;
      final VersionsInfo localVersionsInfo = this.soloApp.getVersionsInfo();
      final CountDownLatch localCountDownLatch = new CountDownLatch(4);
      localVersionsInfo.setRefreshingVersionsFlag(true);
      this.asyncTasksRunner.execute(new Runnable()
      {
        public void run()
        {
          String str = UpdateService.this.soloUpdater.retrieveCurrentVersion();
          if (str != null)
            localVersionsInfo.setVehicleVersion(str);
          localCountDownLatch.countDown();
        }
      });
      this.asyncTasksRunner.execute(new Runnable()
      {
        public void run()
        {
          String str = UpdateService.this.artooUpdater.retrieveCurrentVersion();
          if (str != null)
            localVersionsInfo.setControllerVersion(str);
          localCountDownLatch.countDown();
        }
      });
      this.asyncTasksRunner.execute(new Runnable()
      {
        public void run()
        {
          String str = UpdateService.this.stm32Updater.retrieveCurrentVersion();
          if (str != null)
            localVersionsInfo.setStm32Version(str);
          localCountDownLatch.countDown();
        }
      });
      this.asyncTasksRunner.execute(new Runnable()
      {
        public void run()
        {
          String str = UpdateService.this.pixhawkUpdater.retrieveCurrentVersion();
          if (str != null)
            localVersionsInfo.setPixhawkVersion(str);
          localCountDownLatch.countDown();
        }
      });
      try
      {
        localCountDownLatch.await();
        localVersionsInfo.setRefreshingVersionsFlag(false);
        this.soloApp.getVersionsInfo().saveInstance();
        if ((1 == 0) || (!localVersionsInfo.areVersionsSet()))
          continue;
        generateTask("com.o3dr.solo.android.action.CHECK_FOR_UPDATE", new Runnable()
        {
          public void run()
          {
            UpdateService.this.checkForUpdate();
          }
        });
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        Log.e(TAG, "Countdown latch interrupted.", localInterruptedException);
        return;
      }
      finally
      {
        localVersionsInfo.setRefreshingVersionsFlag(false);
        this.soloApp.getVersionsInfo().saveInstance();
        if ((0 != 0) && (localVersionsInfo.areVersionsSet()))
          generateTask("com.o3dr.solo.android.action.CHECK_FOR_UPDATE", new Runnable()
          {
            public void run()
            {
              UpdateService.this.checkForUpdate();
            }
          });
      }
    }
  }

  private void updateProductInfo(JSONObject paramJSONObject, UpdateInfo paramUpdateInfo)
  {
    if (paramJSONObject == null);
    Object localObject1;
    Object localObject2;
    String str1;
    do
    {
      do
      {
        return;
        JSONArray localJSONArray = paramJSONObject.optJSONArray("releases");
        if ((localJSONArray == null) || (localJSONArray.length() == 0))
        {
          Log.d(TAG, "No releases available.");
          return;
        }
        int i = localJSONArray.length();
        localObject1 = null;
        localObject2 = null;
        for (int j = 0; j < i; j++)
        {
          JSONObject localJSONObject = localJSONArray.optJSONObject(j);
          String str2 = localJSONObject.optString("major");
          String str3 = localJSONObject.optString("minor");
          String str4 = localJSONObject.optString("patch");
          String str5 = str2 + "." + str3 + "." + str4;
          if ((TextUtils.isEmpty((CharSequence)localObject2)) || (((String)localObject2).compareTo(str5) < 0))
          {
            localObject2 = str5;
            localObject1 = localJSONObject;
          }
        }
      }
      while ((TextUtils.isEmpty((CharSequence)localObject2)) || (localObject1 == null));
      str1 = paramUpdateInfo.getUpdateVersion();
    }
    while ((!TextUtils.isEmpty(str1)) && (str1.compareTo((String)localObject2) >= 0));
    paramUpdateInfo.reset();
    paramUpdateInfo.setUpdateUrl(localObject1.optString("file"));
    paramUpdateInfo.setUpdateMd5(localObject1.optString("md5"));
    paramUpdateInfo.setUpdateVersion((String)localObject2);
    paramUpdateInfo.setUpdateReleaseNotes(localObject1.optString("release_notes"));
    paramUpdateInfo.setUpdateRequired(localObject1.optBoolean("required"));
    paramUpdateInfo.setUpdateTimestamp(System.currentTimeMillis());
  }

  private boolean verifyDownloadedUpdate(UpdateInfo paramUpdateInfo, Uri paramUri)
  {
    Log.d(TAG, "Starting downloaded file verification process.");
    if ((paramUpdateInfo == null) || (paramUri == null))
    {
      Log.d(TAG, "Invalid update info.");
      return false;
    }
    return verifyDownloadedUpdate(paramUpdateInfo, new File(paramUri.getPath()));
  }

  private boolean verifyDownloadedUpdate(UpdateInfo paramUpdateInfo, File paramFile)
  {
    String str = paramUpdateInfo.getUpdateMd5();
    boolean bool1 = TextUtils.isEmpty(str);
    boolean bool2 = false;
    if (bool1);
    do
    {
      return bool2;
      if (!paramFile.isFile())
      {
        Log.d(TAG, "Unable to locate the downloaded file: " + paramFile.getAbsolutePath());
        return false;
      }
      bool2 = MD5Checker.checkMD5(str, paramFile);
    }
    while (!bool2);
    paramUpdateInfo.setDownloadedFile(paramFile);
    saveState();
    return bool2;
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    Log.d(TAG, "Starting update service...");
    super.onCreate();
    this.soloApp = ((SoloApp)getApplication());
    this.appAnalytics = this.soloApp.getAppAnalytics();
    Context localContext = getApplicationContext();
    this.lbm = LocalBroadcastManager.getInstance(localContext);
    this.downloadMgr = ((DownloadManager)localContext.getSystemService("download"));
    this.appPrefs = new AppPreferences(localContext);
    this.artooUpdater.setUpdateListener(this.uploadFileListener);
    this.soloUpdater.setUpdateListener(this.uploadFileListener);
    this.asyncTasksRunner = Executors.newCachedThreadPool();
    restoreState();
  }

  public void onDestroy()
  {
    super.onDestroy();
    this.asyncTasksRunner.shutdownNow();
    this.asyncTasksRunner = null;
    Log.d(TAG, "Stopping update service...");
    saveState();
  }

  public int onStartCommand(final Intent paramIntent, int paramInt1, int paramInt2)
  {
    String str;
    int i;
    if (paramIntent != null)
    {
      str = paramIntent.getAction();
      i = -1;
      switch (str.hashCode())
      {
      default:
      case 446889900:
      case -569416433:
      case -704743609:
      case 1791912178:
      case -2071212141:
      case 1248865515:
      case -1828181659:
      }
    }
    while (true)
      switch (i)
      {
      default:
        return 2;
        if (str.equals("com.o3dr.solo.android.action.UPDATE_COMPONENTS_VERSION"))
        {
          i = 0;
          continue;
          if (str.equals("com.o3dr.solo.android.action.CHECK_FOR_UPDATE"))
          {
            i = 1;
            continue;
            if (str.equals("com.o3dr.solo.android.action.DOWNLOAD_SERVER_UPDATE"))
            {
              i = 2;
              continue;
              if (str.equals("com.o3dr.solo.android.action.CANCEL_UPDATE_DOWNLOAD"))
              {
                i = 3;
                continue;
                if (str.equals("com.o3dr.solo.android.action.PERFORM_VEHICLE_UPDATE"))
                {
                  i = 4;
                  continue;
                  if (str.equals("android.intent.action.DOWNLOAD_COMPLETE"))
                  {
                    i = 5;
                    continue;
                    if (str.equals("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"))
                      i = 6;
                  }
                }
              }
            }
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    generateTask(str, new Runnable()
    {
      public void run()
      {
        UpdateService.this.updateComponentsVersion();
      }
    });
    return 2;
    generateTask(str, new Runnable()
    {
      public void run()
      {
        UpdateService.this.checkForUpdate();
      }
    });
    return 2;
    generateTask(str, new Runnable()
    {
      public void run()
      {
        UpdateService.this.downloadLatestVersions();
      }
    });
    return 2;
    generateTask(str, new Runnable()
    {
      public void run()
      {
        UpdateService.this.cancelUpdateDownload();
      }
    });
    return 2;
    generateTask(str, new Runnable()
    {
      public void run()
      {
        UpdateService.this.performVehicleUpdate();
      }
    });
    return 2;
    this.asyncTasksRunner.execute(new Runnable()
    {
      public void run()
      {
        long l = paramIntent.getLongExtra("extra_download_id", -1L);
        UpdateInfo localUpdateInfo;
        if (l != -1L)
        {
          if (l != UpdateService.this.artooLinkUpdateInfo.getDownloadId())
            break label106;
          localUpdateInfo = UpdateService.this.artooLinkUpdateInfo;
        }
        while (true)
        {
          if (localUpdateInfo != null)
          {
            UpdateService.this.soloApp.getUpdateState().removeServerUpdateDownloadId(l);
            localUpdateInfo.setDownloadId(-1L);
            Uri localUri = UpdateService.this.downloadMgr.getUriForDownloadedFile(l);
            UpdateService.this.verifyDownloadedUpdate(localUpdateInfo, localUri);
          }
          UpdateService.this.checkIfDownloadsCompleted(false);
          return;
          label106: boolean bool = l < UpdateService.this.soloLinkUpdateInfo.getDownloadId();
          localUpdateInfo = null;
          if (!bool)
            localUpdateInfo = UpdateService.this.soloLinkUpdateInfo;
        }
      }
    });
    return 2;
    this.asyncTasksRunner.execute(new Runnable()
    {
      public void run()
      {
        long[] arrayOfLong = paramIntent.getLongArrayExtra("extra_click_download_ids");
        int i;
        if (arrayOfLong != null)
          i = arrayOfLong.length;
        for (int j = 0; ; j++)
          if (j < i)
          {
            long l = arrayOfLong[j];
            if ((l == UpdateService.this.artooLinkUpdateInfo.getDownloadId()) || (l == UpdateService.this.soloLinkUpdateInfo.getDownloadId()))
              UpdateService.this.startActivity(new Intent(UpdateService.this.getApplicationContext(), SettingsActivity.class).putExtra("extra_settings_selected_pane_id", 2131493143).addFlags(268435456));
          }
          else
          {
            return;
          }
      }
    });
    return 2;
  }

  static abstract class Task
    implements Runnable
  {
    private final String action;
    private final WeakReference<UpdateService> serviceRef;

    Task(UpdateService paramUpdateService, String paramString)
    {
      this.serviceRef = new WeakReference(paramUpdateService);
      this.action = paramString;
    }

    protected abstract void doInBackground();

    public final void run()
    {
      doInBackground();
      UpdateService localUpdateService = (UpdateService)this.serviceRef.get();
      if (localUpdateService != null)
        localUpdateService.runningTasks.remove(this.action);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.update.UpdateService
 * JD-Core Version:    0.6.2
 */