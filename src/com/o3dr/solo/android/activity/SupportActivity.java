package com.o3dr.solo.android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.VersionsInfo;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.util.ZipUtils;
import com.o3dr.solo.android.util.connection.SshConnection;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class SupportActivity extends BaseActivity
{
  private static final List<String> ARTOO_FILE_LIST = Arrays.asList(new String[] { "/log/solo.tlog.1", "/log/solo.tlog.2", "/log/solo.tlog.3" });
  private static final String ARTOO_LOCAL_LOGS_DIRECTORY = "/logs.zip";
  private static final String ARTOO_LOG_FOLDER = "/support_logs/";
  public static final int EMAIL_SENT = 2;
  private static final int MAX_PROGRESS = 100;
  private static final int NUMBER_OF_RETRIES = 3;
  private static final List<String> SOLO_FILE_LIST = Arrays.asList(new String[] { "/log/shotlog.000.log", "/log/shotlog.001.log", "/log/shotlog.002.log" });
  private static final String TAG = SupportActivity.class.getSimpleName();
  public static final int WIFI_SELECTION_COMPLETED = 1;
  private DownloadLogs aSyncTask;
  private SshConnection artooConnection;
  private FrameLayout completeLayoutFailed;
  private FrameLayout completeLayoutSuccess;
  private Context context;
  private String currentDate;
  private LinearLayout downloadPaneLayout;
  private ProgressBar downloadProgress;
  private TextView downloadingLogs;
  private Button logTicketButton;
  private SshConnection soloConnection;
  private String versionsInfo;
  private TextView warningText;

  private void changeDownloadText(int paramInt1, int paramInt2)
  {
    TextView localTextView = this.downloadingLogs;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = (paramInt2 + "/" + paramInt1);
    localTextView.setText(getString(2131100021, arrayOfObject));
  }

  private void displayErrorPage()
  {
    this.downloadPaneLayout.setVisibility(8);
    this.completeLayoutSuccess.setVisibility(8);
    this.completeLayoutFailed.setVisibility(0);
  }

  private void displaySuccessPage()
  {
    this.downloadPaneLayout.setVisibility(8);
    this.completeLayoutFailed.setVisibility(8);
    this.completeLayoutSuccess.setVisibility(0);
  }

  private String getSimpleDate()
  {
    Calendar localCalendar = Calendar.getInstance();
    return new SimpleDateFormat("MMMM dd, yyyy").format(localCalendar.getTime());
  }

  private void requestConnectivityIfNeeded()
  {
    Log.d(TAG, "Checking WiFi");
    if (isArtooLinkConnected())
    {
      Log.d(TAG, "Requesting internet connection for update downloads.");
      startActivityForResult(new Intent(this.context, WifiSettingsAccess.class).putExtra("extra_title", getString(2131100035)).putExtra("extra_message", getString(2131100040)).putExtra("extra_picture", true).putExtra("extra_button_text", getString(2131100041)), 1);
    }
  }

  private void sendEmail()
  {
    Intent localIntent = new Intent("android.intent.action.SENDTO");
    StringBuilder localStringBuilder = new StringBuilder().append("mailto:").append(Uri.encode(getString(2131100019))).append("?subject=").append(Uri.encode(this.currentDate)).append("&body=");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.versionsInfo;
    localIntent.setData(Uri.parse(Uri.encode(getString(2131100016, arrayOfObject))));
    startActivity(Intent.createChooser(localIntent, getString(2131099783)));
  }

  private void sendEmailWithLogs()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = this.currentDate;
    localIntent.putExtra("android.intent.extra.SUBJECT", getString(2131100027, arrayOfObject1));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = getString(2131100019);
    localIntent.putExtra("android.intent.extra.EMAIL", arrayOfString);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = this.versionsInfo;
    localIntent.putExtra("android.intent.extra.TEXT", getString(2131100016, arrayOfObject2));
    localIntent.setType("application/zip");
    File localFile = getExternalFilesDir(null);
    if (localFile == null)
      return;
    localIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(localFile.getAbsolutePath() + "/logs.zip")));
    startActivityForResult(Intent.createChooser(localIntent, getString(2131100029)), 2);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
      return;
    case 1:
      if (paramInt2 == -1)
      {
        sendEmailWithLogs();
        return;
      }
      displayErrorPage();
      return;
    case 2:
    }
    displaySuccessPage();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903077);
    this.completeLayoutFailed = ((FrameLayout)findViewById(2131493047));
    this.completeLayoutSuccess = ((FrameLayout)findViewById(2131493050));
    this.downloadPaneLayout = ((LinearLayout)findViewById(2131493053));
    this.context = getApplicationContext();
    this.versionsInfo = this.soloApp.getVersionsInfo().getVehicleVersion();
    this.currentDate = getSimpleDate();
    Button localButton = (Button)findViewById(2131493046);
    if (new Intent("android.intent.action.DIAL", Uri.parse("tel:")).resolveActivity(getPackageManager()) != null)
      localButton.setVisibility(0);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SupportActivity.this.startActivity(new Intent(SupportActivity.this.context, CallSupportDialog.class));
      }
    });
    ((Button)findViewById(2131493044)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Log.d(SupportActivity.TAG, "Downloading user guide");
      }
    });
    ((Button)findViewById(2131493045)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Log.d(SupportActivity.TAG, "Sending email");
        SupportActivity.this.sendEmail();
      }
    });
    this.artooConnection = ArtooLinkManager.getSshLink();
    this.soloConnection = SoloLinkManager.getSshLink();
    this.downloadProgress = ((ProgressBar)findViewById(2131493058));
    setSupportActionBar((Toolbar)findViewById(2131492978));
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDisplayShowTitleEnabled(true);
      localActionBar.setDisplayShowHomeEnabled(true);
      localActionBar.setDisplayHomeAsUpEnabled(true);
    }
    this.warningText = ((TextView)findViewById(2131493057));
    this.downloadingLogs = ((TextView)findViewById(2131493059));
    this.logTicketButton = ((Button)findViewById(2131493056));
    this.logTicketButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (SupportActivity.this.aSyncTask != null)
          SupportActivity.this.aSyncTask.cancel(true);
        SupportActivity.access$302(SupportActivity.this, new SupportActivity.DownloadLogs(SupportActivity.this, SupportActivity.this.isLinkConnected(), null));
        SupportActivity.this.aSyncTask.execute(new Void[0]);
      }
    });
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    super.onServiceConnected(paramComponentName, paramIBinder);
    boolean bool = isArtooLinkConnected();
    this.logTicketButton.setEnabled(bool);
    if (bool)
      this.warningText.setVisibility(8);
  }

  protected void onStop()
  {
    super.onStop();
    if (this.aSyncTask != null)
      this.aSyncTask.cancel(true);
  }

  public void taskCompletionResult(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.downloadProgress.setProgress(100);
      requestConnectivityIfNeeded();
      return;
    }
    displayErrorPage();
  }

  private static class DownloadLogs extends AsyncTask<Void, Integer, Boolean>
  {
    private int fileNumber;
    private File folder;
    private boolean isLinkConnected;
    private int totalFilesToDownload;
    private WeakReference<SupportActivity> weakActivity;

    private DownloadLogs(SupportActivity paramSupportActivity, boolean paramBoolean)
    {
      this.weakActivity = new WeakReference(paramSupportActivity);
      this.isLinkConnected = paramBoolean;
    }

    private boolean downloadFileFromDevice(List<String> paramList, File paramFile, SshConnection paramSshConnection, int paramInt)
      throws IOException
    {
      boolean bool1;
      if ((SupportActivity)this.weakActivity.get() != null)
        bool1 = false;
      while (true)
      {
        try
        {
          Iterator localIterator = paramList.iterator();
          if (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            this.fileNumber = (1 + this.fileNumber);
            int i = 0;
            if (i < paramInt)
            {
              boolean bool2 = paramSshConnection.downloadFile(paramFile.getAbsolutePath(), str);
              if (bool1)
                break label218;
              if (!bool2)
                break label224;
              break label218;
              if (bool2)
                continue;
              Log.w(SupportActivity.TAG, "Unable to download file " + str + " to the phone. Trying again");
              new File(paramFile, str).delete();
              i++;
              continue;
            }
            continue;
            Integer[] arrayOfInteger = new Integer[1];
            arrayOfInteger[0] = Integer.valueOf(this.fileNumber * (100 / this.totalFilesToDownload));
            publishProgress(arrayOfInteger);
            continue;
          }
        }
        catch (IOException localIOException)
        {
          Log.w(SupportActivity.TAG, "Unable to download file", localIOException);
          Log.i(SupportActivity.TAG, "Logs were downloaded to the device");
          return bool1;
        }
        Log.i(SupportActivity.TAG, "Logs were downloaded to the device, reference to activity could not be obtained");
        return false;
        label218: bool1 = true;
        continue;
        label224: bool1 = false;
      }
    }

    private void setUpProgressBar()
    {
      SupportActivity localSupportActivity = (SupportActivity)this.weakActivity.get();
      if (localSupportActivity != null)
      {
        localSupportActivity.logTicketButton.setVisibility(8);
        localSupportActivity.downloadProgress.setVisibility(0);
        localSupportActivity.downloadProgress.setIndeterminate(true);
        localSupportActivity.downloadingLogs.setVisibility(0);
        if (!this.isLinkConnected)
          break label84;
      }
      label84: for (int i = SupportActivity.ARTOO_FILE_LIST.size() + SupportActivity.SOLO_FILE_LIST.size(); ; i = SupportActivity.ARTOO_FILE_LIST.size())
      {
        this.totalFilesToDownload = i;
        this.fileNumber = 0;
        return;
      }
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      Log.d(SupportActivity.TAG, "Downloading the logs from Artoo");
      try
      {
        SupportActivity localSupportActivity = (SupportActivity)this.weakActivity.get();
        if (localSupportActivity != null)
        {
          File localFile = localSupportActivity.getExternalFilesDir(null);
          if (localFile == null)
            return Boolean.valueOf(false);
          this.folder = new File(localFile + "/support_logs/");
          boolean bool1 = this.folder.exists();
          if (bool1)
          {
            File[] arrayOfFile = this.folder.listFiles();
            if (arrayOfFile != null)
            {
              int i = arrayOfFile.length;
              for (int j = 0; j < i; j++)
                arrayOfFile[j].delete();
            }
          }
          if ((bool1) || (this.folder.mkdirs()))
          {
            bool2 = downloadFileFromDevice(SupportActivity.ARTOO_FILE_LIST, this.folder, localSupportActivity.artooConnection, 3);
            if ((this.isLinkConnected) && (downloadFileFromDevice(SupportActivity.SOLO_FILE_LIST, this.folder, localSupportActivity.soloConnection, 3)))
            {
              k = 1;
              break label254;
            }
            while (true)
            {
              Boolean localBoolean = Boolean.valueOf(bool3);
              return localBoolean;
              k = 0;
              break;
              bool3 = false;
            }
          }
        }
      }
      catch (IOException localIOException)
      {
        Log.e(SupportActivity.TAG, "Unable to download the file to the phone.", localIOException);
        return Boolean.valueOf(false);
      }
      catch (NullPointerException localNullPointerException)
      {
        while (true)
        {
          boolean bool2;
          int k;
          boolean bool3;
          Log.e(SupportActivity.TAG, "Unable to create file path. ", localNullPointerException);
          continue;
          label254: if ((bool2) || (k != 0))
            bool3 = true;
        }
      }
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      SupportActivity localSupportActivity = (SupportActivity)this.weakActivity.get();
      if ((localSupportActivity == null) || ((paramBoolean.booleanValue()) && (this.folder.exists())));
      try
      {
        ZipUtils.zipDir(new File(this.folder.getParent(), "logs.zip"), this.folder, "");
        localSupportActivity.taskCompletionResult(paramBoolean.booleanValue());
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e(SupportActivity.TAG, "Unable to compress logs. ", localIOException);
      }
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      setUpProgressBar();
    }

    protected void onProgressUpdate(Integer[] paramArrayOfInteger)
    {
      super.onProgressUpdate(paramArrayOfInteger);
      SupportActivity localSupportActivity = (SupportActivity)this.weakActivity.get();
      if (localSupportActivity != null)
      {
        localSupportActivity.changeDownloadText(this.totalFilesToDownload, this.fileNumber);
        localSupportActivity.downloadProgress.setIndeterminate(false);
        localSupportActivity.downloadProgress.setProgress(paramArrayOfInteger[0].intValue());
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.SupportActivity
 * JD-Core Version:    0.6.2
 */