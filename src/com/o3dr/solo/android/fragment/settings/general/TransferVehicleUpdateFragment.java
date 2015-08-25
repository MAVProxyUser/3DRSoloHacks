package com.o3dr.solo.android.fragment.settings.general;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.o3dr.solo.android.activity.FlightActivity;
import com.o3dr.solo.android.activity.WifiSettingsAccess;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.appstate.VersionsInfo;
import com.o3dr.solo.android.fragment.TitleUpdater;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.fragment.dialogs.OkDialog;
import com.o3dr.solo.android.fragment.dialogs.ProgressDialogFragment;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.NetworkUtils;
import com.o3dr.solo.android.util.connection.SshConnection;
import com.o3dr.solo.android.widget.DotsProgressView;

public class TransferVehicleUpdateFragment extends BaseFragment
{
  private static final String DISCONNECTED_LABEL = "--";
  private static final long DISCONNECTION_CHECKER_PERIOD = 2000L;
  private static final int MAX_PROGRESS = 100;
  private static final int NINETY_NINE_PERCENT_STOP = 99;
  private static final long POWER_CYCLE_DISPLAY_PERIOD = 120000L;
  private static final String TAG = TransferVehicleUpdateFragment.class.getSimpleName();
  private static final IntentFilter intentFilter = new IntentFilter();
  private AsyncStartInstall asyncStartInstall;
  private View availableSystemUpdate;
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      SoloApp localSoloApp = TransferVehicleUpdateFragment.this.getApplication();
      String str = paramAnonymousIntent.getAction();
      int i;
      switch (str.hashCode())
      {
      default:
        i = -1;
        label127: switch (i)
        {
        default:
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        }
        break;
      case 662782932:
      case 551871472:
      case -1038879041:
      case -1285212681:
      case -1437089149:
      case 400331751:
      case 1725422103:
      case -1597981372:
      case -2035223969:
      case -1542478246:
      case -1467072149:
      case -302519047:
      }
      do
      {
        do
        {
          UpdateState localUpdateState;
          do
          {
            int j;
            do
            {
              do
              {
                return;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
                  break;
                i = 0;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
                  break;
                i = 1;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE"))
                  break;
                i = 2;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED"))
                  break;
                i = 3;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.ACTION_UPDATE_TRANSFER_CANCELLED"))
                  break;
                i = 4;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_FAILED"))
                  break;
                i = 5;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_TRANSFER_PROGRESS"))
                  break;
                i = 6;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_SUCCESSFUL"))
                  break;
                i = 7;
                break label127;
                if (!str.equals("com.o3dr.solo.androidaction.ACTION_VEHICLE_WAIT_FOR_DISCONNECTION"))
                  break;
                i = 8;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.VEHICLE_UP_TO_DATE"))
                  break;
                i = 9;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED"))
                  break;
                i = 10;
                break label127;
                if (!str.equals("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED"))
                  break;
                i = 11;
                break label127;
                if (TransferVehicleUpdateFragment.this.systemVersionInfo.getVisibility() == 0)
                {
                  VersionsInfo localVersionsInfo2 = localSoloApp.getVersionsInfo();
                  TransferVehicleUpdateFragment.this.showSystemVersionInfo(localVersionsInfo2.getVehicleVersion(), localVersionsInfo2.getControllerVersion());
                  return;
                }
              }
              while (!localSoloApp.getUpdateState().isWaitingForDisconnection());
              TransferVehicleUpdateFragment.this.displayWaitScreen();
              return;
              TransferVehicleUpdateFragment.this.handler.removeCallbacks(TransferVehicleUpdateFragment.this.promptSoloReboot);
              if (localSoloApp.getUpdateState().isCheckingForUpdate())
              {
                TransferVehicleUpdateFragment.this.resetSectionViews();
                TransferVehicleUpdateFragment.this.checkingForVehicleUpdate.setVisibility(0);
              }
              while (true)
              {
                TransferVehicleUpdateFragment.this.readyToFly.setEnabled(TransferVehicleUpdateFragment.this.isLinkConnected());
                return;
                if (TransferVehicleUpdateFragment.this.systemVersionInfo.getVisibility() == 0)
                {
                  TransferVehicleUpdateFragment.this.resetSectionViews();
                  TransferVehicleUpdateFragment.this.showSystemVersionInfo();
                }
              }
              TransferVehicleUpdateFragment.this.resetSectionViews();
              if (localSoloApp.getUpdateState().isVehicleUpdating())
                TransferVehicleUpdateFragment.this.resetScreenUpdateComponents();
              while (true)
              {
                TransferVehicleUpdateFragment.this.availableSystemUpdate.setVisibility(0);
                return;
                TransferVehicleUpdateFragment.this.installUpdateButton.setVisibility(0);
                TransferVehicleUpdateFragment.this.cancelInstallButton.setVisibility(8);
                TransferVehicleUpdateFragment.this.nextButton.setVisibility(8);
                TransferVehicleUpdateFragment.this.buttonContainer.setVisibility(0);
                TransferVehicleUpdateFragment.this.stopDots();
                TransferVehicleUpdateFragment.this.title.setText(2131099978);
                TransferVehicleUpdateFragment.this.installProgress.setProgress(0);
                TransferVehicleUpdateFragment.this.installProgress.setIndeterminate(false);
                TransferVehicleUpdateFragment.this.installProgress.setVisibility(4);
              }
              TransferVehicleUpdateFragment.this.resetSectionViews();
              TransferVehicleUpdateFragment.this.title.setText(2131100043);
              TransferVehicleUpdateFragment.this.availableSystemUpdate.setVisibility(0);
              TransferVehicleUpdateFragment.this.installProgress.setIndeterminate(true);
              TransferVehicleUpdateFragment.this.installProgress.setVisibility(0);
              return;
              TransferVehicleUpdateFragment.this.installUpdateButton.setVisibility(0);
              TransferVehicleUpdateFragment.this.cancelInstallButton.setVisibility(8);
              TransferVehicleUpdateFragment.this.stopDots();
              TransferVehicleUpdateFragment.this.installProgress.setProgress(0);
              TransferVehicleUpdateFragment.this.installProgress.setIndeterminate(false);
              TransferVehicleUpdateFragment.this.installProgress.setVisibility(4);
              return;
              TransferVehicleUpdateFragment.this.resetSectionViews();
              TransferVehicleUpdateFragment.this.systemUpdateFailed.setVisibility(0);
              return;
              TransferVehicleUpdateFragment.this.resetSectionViews();
              TransferVehicleUpdateFragment.this.availableSystemUpdate.setVisibility(0);
              j = paramAnonymousIntent.getIntExtra("extra_update_progress", -1);
              if (j == -1)
              {
                TransferVehicleUpdateFragment.this.installProgress.setIndeterminate(true);
                return;
              }
              TransferVehicleUpdateFragment.this.installProgress.setIndeterminate(false);
              TransferVehicleUpdateFragment.this.installProgress.setMax(100);
            }
            while (TransferVehicleUpdateFragment.this.installProgress.getProgress() >= 99);
            TransferVehicleUpdateFragment.this.installProgress.setProgress(j);
            return;
            TransferVehicleUpdateFragment.this.resetSectionViews();
            TransferVehicleUpdateFragment.this.installProgress.setMax(100);
            localSoloApp.getUpdateState().setWaitForDisconnection(true);
            return;
            TransferVehicleUpdateFragment.this.resetSectionViews();
            TransferVehicleUpdateFragment.this.displayWaitScreenAnimation();
            return;
            localUpdateState = localSoloApp.getUpdateState();
          }
          while ((localUpdateState.isVehicleUpdating()) || (localUpdateState.isWaitingForFinalConnection()) || (localUpdateState.isWaitingForDisconnection()));
          TransferVehicleUpdateFragment.this.resetSectionViews();
          TransferVehicleUpdateFragment.this.handler.removeCallbacks(TransferVehicleUpdateFragment.this.promptSoloReboot);
        }
        while (TransferVehicleUpdateFragment.this.finishingUpdate.getVisibility() == 0);
        TransferVehicleUpdateFragment.this.resetSectionViews();
        TransferVehicleUpdateFragment.this.readyToFly.setEnabled(TransferVehicleUpdateFragment.this.isLinkConnected());
        if (localSoloApp != null)
        {
          VersionsInfo localVersionsInfo1 = localSoloApp.getVersionsInfo();
          localSoloApp.getUpdateState().setWaitForFinalConnection(false);
          TransferVehicleUpdateFragment.this.showSystemVersionInfo(localVersionsInfo1.getVehicleVersion(), localVersionsInfo1.getControllerVersion());
          return;
        }
        TransferVehicleUpdateFragment.this.showSystemVersionInfo(null, null);
        return;
      }
      while ((!TransferVehicleUpdateFragment.this.getApplication().getUpdateState().isWaitingForFinalConnection()) || (!TransferVehicleUpdateFragment.this.isArtooLinkConnected()));
      TransferVehicleUpdateFragment.this.resetSectionViews();
      TransferVehicleUpdateFragment.this.connectToSolo.setVisibility(0);
    }
  };
  private FrameLayout buttonContainer;
  private View cancelInstallButton;
  private ImageView checkMarkView;
  private View checkingForVehicleUpdate;
  private View connectToSolo;
  private TextView controllerVersion;
  private View finishingUpdate;
  private DotsProgressView firstDots;
  private final Handler handler = new Handler();
  private ProgressBar installProgress;
  private View installUpdateButton;
  private View nextButton;
  private ImageView phoneOutline;
  private final Runnable promptSoloReboot = new Runnable()
  {
    public void run()
    {
      OkDialog.newInstance(TransferVehicleUpdateFragment.this.getContext(), TransferVehicleUpdateFragment.this.getString(2131099962), TransferVehicleUpdateFragment.this.getString(2131099906)).show(TransferVehicleUpdateFragment.this.getChildFragmentManager(), "Wait for two minutes and power cycle alert");
    }
  };
  private View readyToFly;
  private DotsProgressView secondDots;
  private View systemUpdateFailed;
  private View systemVersionInfo;
  private TextView title;
  private TitleUpdater titleUpdater;
  private TextView vehicleVersion;
  private final Runnable waitForDisconnectionChecker = new Runnable()
  {
    public void run()
    {
      TransferVehicleUpdateFragment.this.handler.removeCallbacks(this);
      SoloApp localSoloApp = TransferVehicleUpdateFragment.this.getApplication();
      if (localSoloApp == null);
      while (!localSoloApp.getUpdateState().isWaitingForDisconnection())
        return;
      if (!TransferVehicleUpdateFragment.this.isArtooLinkConnected())
      {
        TransferVehicleUpdateFragment.this.displayWaitScreen();
        return;
      }
      TransferVehicleUpdateFragment.this.handler.postDelayed(TransferVehicleUpdateFragment.this.waitForDisconnectionChecker, 2000L);
    }
  };

  static
  {
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_FAILED");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_SUCCESSFUL");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_TRANSFER_PROGRESS");
    intentFilter.addAction("com.o3dr.solo.android.action.ACTION_UPDATE_TRANSFER_CANCELLED");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UP_TO_DATE");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED");
    intentFilter.addAction("com.o3dr.solo.androidaction.ACTION_VEHICLE_WAIT_FOR_DISCONNECTION");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    intentFilter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED");
    intentFilter.addAction("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED");
  }

  private void clearButtonsViews()
  {
    if (this.installUpdateButton != null)
      this.installUpdateButton.setVisibility(8);
    if (this.cancelInstallButton != null)
      this.cancelInstallButton.setVisibility(8);
    if (this.nextButton != null)
      this.nextButton.setVisibility(8);
  }

  private void displayWaitScreen()
  {
    showWaitScreen();
    this.installProgress.setIndeterminate(false);
    this.installProgress.setProgress(100);
    clearButtonsViews();
    this.nextButton.setVisibility(0);
    this.buttonContainer.setVisibility(0);
  }

  private void displayWaitScreenAnimation()
  {
    this.handler.removeCallbacks(this.waitForDisconnectionChecker);
    this.handler.postDelayed(this.waitForDisconnectionChecker, 2000L);
    showWaitScreen();
    this.buttonContainer.setVisibility(4);
    this.installProgress.setIndeterminate(true);
  }

  private void resetScreenUpdateComponents()
  {
    if (this.installProgress != null)
      this.installUpdateButton.setVisibility(8);
    if (this.cancelInstallButton != null)
      this.cancelInstallButton.setVisibility(0);
    startDots();
  }

  private void resetSectionViews()
  {
    if (this.connectToSolo != null)
      this.connectToSolo.setVisibility(8);
    if (this.checkingForVehicleUpdate != null)
      this.checkingForVehicleUpdate.setVisibility(8);
    if (this.systemUpdateFailed != null)
      this.systemUpdateFailed.setVisibility(8);
    if (this.finishingUpdate != null)
      this.finishingUpdate.setVisibility(8);
    if (this.systemVersionInfo != null)
      this.systemVersionInfo.setVisibility(8);
    if (this.availableSystemUpdate != null)
      this.availableSystemUpdate.setVisibility(8);
  }

  private void setTitleBar(int paramInt)
  {
    if (this.titleUpdater != null)
      this.titleUpdater.updateTitle(paramInt);
  }

  private void showSystemVersionInfo()
  {
    SoloApp localSoloApp = getApplication();
    if (localSoloApp == null)
      return;
    VersionsInfo localVersionsInfo = localSoloApp.getVersionsInfo();
    showSystemVersionInfo(localVersionsInfo.getVehicleVersion(), localVersionsInfo.getControllerVersion());
  }

  private void showSystemVersionInfo(String paramString1, String paramString2)
  {
    this.systemVersionInfo.setVisibility(0);
    if (TextUtils.isEmpty(paramString1))
      paramString1 = "--";
    if (TextUtils.isEmpty(paramString2))
      paramString2 = "--";
    if ((!isArtooLinkConnected()) || (!isSoloLinkConnected()))
    {
      this.readyToFly.setVisibility(8);
      this.checkMarkView.setVisibility(4);
    }
    while (true)
    {
      this.vehicleVersion.setText(paramString1);
      this.controllerVersion.setText(paramString2);
      return;
      this.readyToFly.setVisibility(0);
      this.readyToFly.setEnabled(isLinkConnected());
      this.checkMarkView.setVisibility(0);
    }
  }

  private void showWaitScreen()
  {
    this.title.setText(2131099977);
    this.availableSystemUpdate.setVisibility(0);
    this.phoneOutline.setColorFilter(getResources().getColor(2131427396));
    this.installProgress.setVisibility(0);
    this.firstDots.stop();
    this.secondDots.start();
  }

  private void startDots()
  {
    this.firstDots.start();
    this.secondDots.start();
  }

  private void stopDots()
  {
    this.firstDots.stop();
    this.secondDots.stop();
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
    return paramLayoutInflater.inflate(2130903101, paramViewGroup, false);
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
    this.handler.removeCallbacks(this.promptSoloReboot);
    SoloApp localSoloApp = getApplication();
    VersionsInfo localVersionsInfo = localSoloApp.getVersionsInfo();
    UpdateState localUpdateState = localSoloApp.getUpdateState();
    boolean bool = localUpdateState.isWaitingForDisconnection();
    int i;
    if ((isArtooLinkConnected()) && (isSoloLinkConnected()))
    {
      i = 1;
      if (i != 0)
        break label203;
      Log.d(TAG, "Disconnected from the system.");
      if (!bool)
        break label102;
      displayWaitScreen();
    }
    label203: 
    while (true)
    {
      getBroadcastManager().registerReceiver(this.broadcastReceiver, intentFilter);
      return;
      i = 0;
      break;
      label102: if (localUpdateState.isWaitingForFinalConnection())
      {
        if (isArtooLinkConnected())
        {
          Log.w(TAG, "Connection to Solo could not be established.");
          this.handler.postDelayed(this.promptSoloReboot, 120000L);
          this.connectToSolo.setVisibility(0);
        }
        else
        {
          this.finishingUpdate.setVisibility(0);
        }
      }
      else if (localUpdateState.isVehicleUpdateAvailable())
      {
        this.availableSystemUpdate.setVisibility(0);
      }
      else
      {
        setTitleBar(2131099946);
        showSystemVersionInfo(localVersionsInfo.getVehicleVersion(), localVersionsInfo.getControllerVersion());
        continue;
        Log.d(TAG, "System is connected.");
        if (localUpdateState.isVehicleUpdating())
        {
          resetScreenUpdateComponents();
          this.availableSystemUpdate.setVisibility(0);
          this.installProgress.setVisibility(0);
        }
        else if (bool)
        {
          displayWaitScreenAnimation();
        }
        else if (localUpdateState.isCheckingForUpdate())
        {
          this.checkingForVehicleUpdate.setVisibility(0);
        }
        else if (localUpdateState.isVehicleUpdateAvailable())
        {
          this.availableSystemUpdate.setVisibility(0);
        }
        else
        {
          setTitleBar(2131099946);
          showSystemVersionInfo(localVersionsInfo.getVehicleVersion(), localVersionsInfo.getControllerVersion());
          if (localUpdateState.isWaitingForFinalConnection())
            localUpdateState.setWaitForFinalConnection(false);
        }
      }
    }
  }

  public void onStop()
  {
    super.onStop();
    this.handler.removeCallbacks(this.promptSoloReboot);
    this.handler.removeCallbacks(this.waitForDisconnectionChecker);
    getApplication().getUpdateState().cancelVehicleUpdate();
    getBroadcastManager().unregisterReceiver(this.broadcastReceiver);
    if (this.asyncStartInstall != null)
      this.asyncStartInstall.cancel(true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    final Context localContext = getContext();
    this.firstDots = ((DotsProgressView)paramView.findViewById(2131493194));
    this.secondDots = ((DotsProgressView)paramView.findViewById(2131493195));
    this.checkMarkView = ((ImageView)paramView.findViewById(2131493183));
    this.vehicleVersion = ((TextView)paramView.findViewById(2131493184));
    this.controllerVersion = ((TextView)paramView.findViewById(2131493186));
    this.connectToSolo = paramView.findViewById(2131493204);
    this.checkingForVehicleUpdate = paramView.findViewById(2131493179);
    this.systemUpdateFailed = paramView.findViewById(2131493188);
    this.availableSystemUpdate = paramView.findViewById(2131493189);
    this.finishingUpdate = paramView.findViewById(2131493199);
    this.systemVersionInfo = paramView.findViewById(2131493180);
    this.buttonContainer = ((FrameLayout)paramView.findViewById(2131493192));
    this.title = ((TextView)paramView.findViewById(2131493191));
    this.phoneOutline = ((ImageView)paramView.findViewById(2131493193));
    this.installUpdateButton = paramView.findViewById(2131493198);
    this.installUpdateButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TransferVehicleUpdateFragment.this.getContext() != null)
        {
          if (TransferVehicleUpdateFragment.this.asyncStartInstall != null)
            TransferVehicleUpdateFragment.this.asyncStartInstall.cancel(true);
          TransferVehicleUpdateFragment.access$3002(TransferVehicleUpdateFragment.this, new TransferVehicleUpdateFragment.AsyncStartInstall(TransferVehicleUpdateFragment.this));
          TransferVehicleUpdateFragment.this.asyncStartInstall.execute(new Void[0]);
        }
      }
    });
    this.cancelInstallButton = paramView.findViewById(2131493197);
    this.cancelInstallButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SoloApp localSoloApp = TransferVehicleUpdateFragment.this.getApplication();
        if (localSoloApp != null)
        {
          localSoloApp.getUpdateState().cancelVehicleUpdate();
          Context localContext = TransferVehicleUpdateFragment.this.getContext();
          if (localContext != null)
            localContext.startService(new Intent(localContext, UpdateService.class).setAction("com.o3dr.solo.android.action.CHECK_FOR_UPDATE"));
        }
      }
    });
    this.nextButton = paramView.findViewById(2131493196);
    this.nextButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TransferVehicleUpdateFragment.this.resetSectionViews();
        if (TransferVehicleUpdateFragment.this.availableSystemUpdate != null)
          TransferVehicleUpdateFragment.this.availableSystemUpdate.setVisibility(8);
        if ((TransferVehicleUpdateFragment.this.isSoloLinkConnected()) && (TransferVehicleUpdateFragment.this.isArtooLinkConnected()))
        {
          TransferVehicleUpdateFragment.this.showSystemVersionInfo();
          return;
        }
        TransferVehicleUpdateFragment.this.getApplication().getUpdateState().setWaitForFinalConnection(true);
        TransferVehicleUpdateFragment.this.handler.removeCallbacks(TransferVehicleUpdateFragment.this.promptSoloReboot);
        if (TransferVehicleUpdateFragment.this.isArtooLinkConnected())
        {
          TransferVehicleUpdateFragment.this.handler.postDelayed(TransferVehicleUpdateFragment.this.promptSoloReboot, 120000L);
          TransferVehicleUpdateFragment.this.connectToSolo.setVisibility(0);
          return;
        }
        TransferVehicleUpdateFragment.this.finishingUpdate.setVisibility(0);
      }
    });
    paramView.findViewById(2131493203).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!TransferVehicleUpdateFragment.this.isArtooLinkConnected())
        {
          Log.d(TransferVehicleUpdateFragment.TAG, "Requesting vehicle connection.");
          TransferVehicleUpdateFragment.this.startActivity(new Intent(TransferVehicleUpdateFragment.this.getContext(), WifiSettingsAccess.class).putExtra("extra_title", "Connect to Solo Controller WIFI"));
        }
      }
    });
    this.readyToFly = paramView.findViewById(2131493187);
    this.readyToFly.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TransferVehicleUpdateFragment.this.startActivity(new Intent(localContext, FlightActivity.class));
      }
    });
    this.readyToFly.setEnabled(isLinkConnected());
    this.installProgress = ((ProgressBar)paramView.findViewById(2131493190));
  }

  private class AsyncStartInstall extends AsyncTask<Void, Void, Boolean>
  {
    private final ProgressDialogFragment progressDialog = ProgressDialogFragment.newInstance("Establishing connection...");

    AsyncStartInstall()
    {
      this.progressDialog.setIndeterminate(true);
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      Context localContext = TransferVehicleUpdateFragment.this.getContext();
      if (localContext != null)
      {
        boolean bool = NetworkUtils.isOnSololinkNetwork(localContext);
        int i;
        if ((TransferVehicleUpdateFragment.this.isArtooLinkConnected()) || ((bool) && (ArtooLinkManager.getSshLink().ping())))
        {
          i = 1;
          if ((!TransferVehicleUpdateFragment.this.isSoloLinkConnected()) && ((i == 0) || (!SoloLinkManager.getSshLink().ping())))
            break label125;
        }
        label125: for (int j = 1; ; j = 0)
        {
          if ((i == 0) || (j == 0))
            break label131;
          TransferVehicleUpdateFragment.this.getApplication().getUpdateState().setVehicleUpdating(true);
          localContext.startService(new Intent(localContext, UpdateService.class).setAction("com.o3dr.solo.android.action.PERFORM_VEHICLE_UPDATE"));
          return Boolean.valueOf(true);
          i = 0;
          break;
        }
        label131: if (i == 0)
          break label181;
        OkDialog.newInstance(TransferVehicleUpdateFragment.this.getContext(), TransferVehicleUpdateFragment.this.getString(2131100034), TransferVehicleUpdateFragment.this.getString(2131099858)).show(TransferVehicleUpdateFragment.this.getChildFragmentManager(), "Solo not connected");
      }
      while (true)
      {
        return Boolean.valueOf(false);
        label181: Log.d(TransferVehicleUpdateFragment.TAG, "Requesting vehicle connection.");
        TransferVehicleUpdateFragment.this.startActivity(new Intent(localContext, WifiSettingsAccess.class).putExtra("extra_title", TransferVehicleUpdateFragment.this.getString(2131100038)));
      }
    }

    protected void onCancelled()
    {
      if (this.progressDialog.isShowing())
        this.progressDialog.dismiss();
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      if (this.progressDialog.isShowing())
        this.progressDialog.dismiss();
      if (paramBoolean.booleanValue())
      {
        TransferVehicleUpdateFragment.this.installUpdateButton.setVisibility(8);
        TransferVehicleUpdateFragment.this.cancelInstallButton.setVisibility(0);
        TransferVehicleUpdateFragment.this.installProgress.setProgress(0);
        TransferVehicleUpdateFragment.this.installProgress.setIndeterminate(true);
        TransferVehicleUpdateFragment.this.startDots();
      }
    }

    protected void onPreExecute()
    {
      this.progressDialog.show(TransferVehicleUpdateFragment.this.getChildFragmentManager(), "Async start install.");
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.TransferVehicleUpdateFragment
 * JD-Core Version:    0.6.2
 */