package com.o3dr.solo.android.fragment.settings.general;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.o3dr.solo.android.activity.WifiSettingsAccess;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.fragment.TitleUpdater;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.service.update.UpdateInfo;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DownloadVehicleUpdateFragment extends BaseFragment
{
  private static final String DISCONNECTED_LABEL = "--";
  private static final String TAG = DownloadVehicleUpdateFragment.class.getSimpleName();
  private static final long UPDATE_PERIOD = 500L;
  private static final IntentFilter intentFilter = new IntentFilter();
  private AppPreferences appPrefs;
  private View availableServerUpdate;
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      SoloApp localSoloApp = DownloadVehicleUpdateFragment.this.getApplication();
      final UpdateState localUpdateState = localSoloApp.getUpdateState();
      AppAnalytics localAppAnalytics = localSoloApp.getAppAnalytics();
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
        switch (i)
        {
        default:
        case 0:
        case 1:
        case 2:
        case 3:
        }
        break;
      case 822378966:
      case -967688227:
      case -1849811651:
      case -1215522473:
      }
      do
      {
        return;
        if (!str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE"))
          break;
        i = 0;
        break;
        if (!str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED"))
          break;
        i = 1;
        break;
        if (!str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_COMPLETE"))
          break;
        i = 2;
        break;
        if (!str.equals("com.o3dr.solo.android.action.UPDATE_DOWNLOAD_CANCELLED"))
          break;
        i = 3;
        break;
        DownloadVehicleUpdateFragment.this.resetSectionViews();
        DownloadVehicleUpdateFragment.this.updateTitle(2131099952);
        if (!localUpdateState.isGettingUpdatesFromServer())
        {
          DownloadVehicleUpdateFragment.this.availableServerUpdate.setVisibility(0);
          return;
        }
      }
      while (DownloadVehicleUpdateFragment.this.isLinkConnected());
      DownloadVehicleUpdateFragment.this.resetSectionViews();
      DownloadVehicleUpdateFragment.this.updateTitle(2131099918);
      DownloadVehicleUpdateFragment.this.downloadingServerUpdate.setVisibility(0);
      DownloadVehicleUpdateFragment.this.requestConnectivityIfNeeded();
      DownloadVehicleUpdateFragment.this.updateDownloadProgress();
      return;
      DownloadVehicleUpdateFragment.this.handler.removeCallbacks(DownloadVehicleUpdateFragment.this.updateDownloadProgress);
      DownloadVehicleUpdateFragment.this.resetSectionViews();
      DownloadVehicleUpdateFragment.this.updateDownloadComplete.setVisibility(0);
      localAppAnalytics.track("Download Update Completed");
      DownloadVehicleUpdateFragment.this.handler.postDelayed(new Runnable()
      {
        public void run()
        {
          localUpdateState.setVehicleUpdateAvailable(true);
        }
      }
      , 4000L);
      return;
      DownloadVehicleUpdateFragment.this.handler.removeCallbacks(DownloadVehicleUpdateFragment.this.updateDownloadProgress);
      DownloadVehicleUpdateFragment.this.resetSectionViews();
      DownloadVehicleUpdateFragment.this.downloadProgress.setIndeterminate(true);
      DownloadVehicleUpdateFragment.this.checkingForServerUpdate.setVisibility(0);
      paramAnonymousContext.startService(new Intent(paramAnonymousContext, UpdateService.class).setAction("com.o3dr.solo.android.action.CHECK_FOR_UPDATE"));
    }
  };
  private View checkingForServerUpdate;
  private DownloadManager downloadMgr;
  private ProgressBar downloadProgress;
  private View downloadingServerUpdate;
  private final Handler handler = new Handler();
  private LocalBroadcastManager lbm;
  private TextView nextVersion;
  private TitleUpdater titleUpdater;
  private View updateDownloadComplete;
  private final Runnable updateDownloadProgress = new Runnable()
  {
    public void run()
    {
      SoloApp localSoloApp = DownloadVehicleUpdateFragment.this.getApplication();
      if ((localSoloApp == null) || (!localSoloApp.getUpdateState().isGettingUpdatesFromServer()))
      {
        DownloadVehicleUpdateFragment.this.downloadProgress.setIndeterminate(true);
        return;
      }
      Set localSet = localSoloApp.getUpdateState().getServerUpdateDownloadInfos().keySet();
      long[] arrayOfLong = new long[localSet.size()];
      int i = 0;
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        long l = ((Long)localIterator.next()).longValue();
        int i2 = i + 1;
        arrayOfLong[i] = l;
        i = i2;
      }
      DownloadManager.Query localQuery = new DownloadManager.Query().setFilterById(arrayOfLong);
      Cursor localCursor = DownloadVehicleUpdateFragment.this.downloadMgr.query(localQuery);
      if ((localCursor == null) || (!localCursor.moveToFirst()))
        DownloadVehicleUpdateFragment.this.downloadProgress.setIndeterminate(true);
      label362: 
      while (true)
      {
        if (localCursor != null)
          localCursor.close();
        DownloadVehicleUpdateFragment.this.handler.postDelayed(this, 500L);
        return;
        int j = localCursor.getColumnIndex("bytes_so_far");
        int k = 0;
        int m = localCursor.getColumnIndex("total_size");
        int n = 0;
        do
        {
          k += localCursor.getInt(j);
          n += localCursor.getInt(m);
        }
        while (localCursor.moveToNext());
        float f1 = k / n;
        int i1 = DownloadVehicleUpdateFragment.this.downloadProgress.getMax();
        if (i1 == 0);
        for (float f2 = 0.0F; ; f2 = DownloadVehicleUpdateFragment.this.downloadProgress.getProgress() / i1)
        {
          if ((!DownloadVehicleUpdateFragment.this.downloadProgress.isIndeterminate()) && (f1 < f2))
            break label362;
          DownloadVehicleUpdateFragment.this.downloadProgress.setIndeterminate(false);
          DownloadVehicleUpdateFragment.this.downloadProgress.setMax(n);
          DownloadVehicleUpdateFragment.this.downloadProgress.setProgress(k);
          break;
        }
      }
    }
  };
  private TextView versionDescription;

  static
  {
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED");
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_COMPLETE");
    intentFilter.addAction("com.o3dr.solo.android.action.UPDATE_DOWNLOAD_CANCELLED");
  }

  private void requestConnectivityIfNeeded()
  {
    Context localContext = getContext();
    if (localContext == null);
    while (!isArtooLinkConnected())
      return;
    Log.d(TAG, "Requesting internet connection for update downloads.");
    startActivity(new Intent(localContext, WifiSettingsAccess.class).putExtra("extra_title", getString(2131100035)).putExtra("extra_message", getString(2131100039)).putExtra("extra_picture", true).putExtra("extra_button_text", getString(2131100041)));
  }

  private void resetSectionViews()
  {
    if (this.updateDownloadComplete != null)
      this.updateDownloadComplete.setVisibility(8);
    if (this.checkingForServerUpdate != null)
      this.checkingForServerUpdate.setVisibility(8);
    if (this.availableServerUpdate != null)
      this.availableServerUpdate.setVisibility(8);
    if (this.downloadingServerUpdate != null)
      this.downloadingServerUpdate.setVisibility(8);
  }

  private void setVersionInfo(UpdateInfo paramUpdateInfo)
  {
    String str1 = paramUpdateInfo.getUpdateVersion();
    String str2 = paramUpdateInfo.getUpdateReleaseNotes();
    if (TextUtils.isEmpty(str1))
      str1 = "--";
    if (TextUtils.isEmpty(str2))
      str2 = "--";
    this.nextVersion.setText(str1);
    this.versionDescription.setText(str2);
  }

  private void updateDownloadProgress()
  {
    this.handler.postDelayed(this.updateDownloadProgress, 500L);
  }

  private void updateTitle(int paramInt)
  {
    if (this.titleUpdater != null)
      this.titleUpdater.updateTitle(paramInt);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof TitleUpdater))
      throw new IllegalStateException("Parent activity must implement " + TitleUpdater.class.getName());
    this.titleUpdater = ((TitleUpdater)paramActivity);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903096, paramViewGroup, false);
  }

  public void onDetach()
  {
    super.onDetach();
    this.titleUpdater = null;
  }

  public void onStart()
  {
    super.onStart();
    resetSectionViews();
    SoloApp localSoloApp = getApplication();
    UpdateInfo localUpdateInfo = new UpdateInfo("Solo");
    localUpdateInfo.restoreInstance(this.appPrefs);
    if (localSoloApp == null)
      this.checkingForServerUpdate.setVisibility(0);
    while (true)
    {
      this.lbm.registerReceiver(this.broadcastReceiver, intentFilter);
      return;
      UpdateState localUpdateState = localSoloApp.getUpdateState();
      if (localUpdateState.isGettingUpdatesFromServer())
      {
        this.downloadingServerUpdate.setVisibility(0);
        requestConnectivityIfNeeded();
        updateDownloadProgress();
      }
      else if (localUpdateState.isServerUpdateAvailable())
      {
        updateTitle(2131099952);
        this.availableServerUpdate.setVisibility(0);
        setVersionInfo(localUpdateInfo);
      }
      else if (localUpdateState.isCheckingForUpdate())
      {
        this.checkingForServerUpdate.setVisibility(0);
      }
    }
  }

  public void onStop()
  {
    super.onStop();
    this.lbm.unregisterReceiver(this.broadcastReceiver);
    this.handler.removeCallbacks(this.updateDownloadProgress);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    final Context localContext = getContext();
    this.downloadMgr = ((DownloadManager)localContext.getSystemService("download"));
    this.appPrefs = new AppPreferences(localContext);
    this.lbm = LocalBroadcastManager.getInstance(localContext);
    this.checkingForServerUpdate = paramView.findViewById(2131493128);
    this.availableServerUpdate = paramView.findViewById(2131493129);
    this.nextVersion = ((TextView)paramView.findViewById(2131493130));
    this.versionDescription = ((TextView)paramView.findViewById(2131493132));
    this.downloadingServerUpdate = paramView.findViewById(2131493134);
    this.updateDownloadComplete = paramView.findViewById(2131493127);
    this.downloadProgress = ((ProgressBar)paramView.findViewById(2131493058));
    this.downloadProgress.setIndeterminate(true);
    paramView.findViewById(2131493133).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DownloadVehicleUpdateFragment.this.requestConnectivityIfNeeded();
        localContext.startService(new Intent(localContext, UpdateService.class).setAction("com.o3dr.solo.android.action.DOWNLOAD_SERVER_UPDATE"));
      }
    });
    paramView.findViewById(2131493138).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localContext.startService(new Intent(localContext, UpdateService.class).setAction("com.o3dr.solo.android.action.CANCEL_UPDATE_DOWNLOAD"));
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.DownloadVehicleUpdateFragment
 * JD-Core Version:    0.6.2
 */