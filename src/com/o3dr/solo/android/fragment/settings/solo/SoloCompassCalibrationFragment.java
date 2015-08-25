package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.apis.gcs.CalibrationApi;
import com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationProgress;
import com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationResult;
import com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationStatus;
import com.o3dr.solo.android.activity.FlightActivity;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;

public class SoloCompassCalibrationFragment extends SettingsDetailFragment
{
  private static final String COMPASS_CAL_COMPLETED = "Compass Calibration Completed";
  private static final String COMPASS_CAL_FAILED = "Compass Calibration Failed";
  private static final String COMPASS_CAL_STARTED = "Compass Calibration Started";
  private static final int MAX_PROGRESS = 100;
  private static final int STEP_BEGIN_CALIBRATION = 0;
  private static final int STEP_CALIBRATION_CANCELLED = 5;
  private static final int STEP_CALIBRATION_FAILED = 4;
  private static final int STEP_CALIBRATION_STARTED = 2;
  private static final int STEP_CALIBRATION_SUCCESSFUL = 3;
  private static final int STEP_CALIBRATION_WAITING_TO_START = 1;
  private static final IntentFilter filter = new IntentFilter();
  private View advicesContainer;
  private AppAnalytics appAnalytics;
  private TextView calibrationButton;
  private ImageView calibrationImage;
  private TextView calibrationInstructions;
  private ProgressBar calibrationProgress;
  private int calibrationStep;
  private final SparseArray<MagCalibrationStatus> calibrationTracker = new SparseArray();
  private VideoView calibrationVideo;
  private MenuItem cancelMenuItem;
  private View instructionsContainer;
  private boolean isCancelMenuEnabled = false;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case -1835115231:
      case 580332059:
      case 1972391997:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.services.android.lib.attribute.event.CALIBRATION_MAG_CANCELLED"))
          {
            i = 0;
            continue;
            if (str.equals("com.o3dr.services.android.lib.attribute.event.CALIBRATION_MAG_COMPLETED"))
            {
              i = 1;
              continue;
              if (str.equals("com.o3dr.services.android.lib.attribute.event.CALIBRATION_MAG_PROGRESS"))
                i = 2;
            }
          }
          break;
        case 0:
        case 1:
        case 2:
        }
      SoloCompassCalibrationFragment.this.updateUI(5);
      return;
      MagnetometerCalibrationResult localMagnetometerCalibrationResult = (MagnetometerCalibrationResult)paramAnonymousIntent.getParcelableExtra("com.o3dr.services.android.lib.attribute.event.extra.CALIBRATION_MAG_RESULT");
      SoloCompassCalibrationFragment.this.handleMagResult(localMagnetometerCalibrationResult);
      return;
      MagnetometerCalibrationProgress localMagnetometerCalibrationProgress = (MagnetometerCalibrationProgress)paramAnonymousIntent.getParcelableExtra("com.o3dr.services.android.lib.attribute.event.extra.CALIBRATION_MAG_PROGRESS");
      SoloCompassCalibrationFragment.this.handleMagProgress(localMagnetometerCalibrationProgress);
    }
  };

  static
  {
    filter.addAction("com.o3dr.services.android.lib.attribute.event.CALIBRATION_MAG_CANCELLED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.CALIBRATION_MAG_COMPLETED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.CALIBRATION_MAG_PROGRESS");
  }

  private void cancelCalibration()
  {
    CalibrationApi.cancelMagnetometerCalibration(getDrone());
  }

  private void enableCancelMenu(boolean paramBoolean)
  {
    this.isCancelMenuEnabled = paramBoolean;
    if (this.cancelMenuItem != null)
      this.cancelMenuItem.setEnabled(paramBoolean);
  }

  private void handleMagProgress(MagnetometerCalibrationProgress paramMagnetometerCalibrationProgress)
  {
    if (paramMagnetometerCalibrationProgress == null)
      return;
    updateUI(2);
    MagCalibrationStatus localMagCalibrationStatus = (MagCalibrationStatus)this.calibrationTracker.get(paramMagnetometerCalibrationProgress.getCompassId());
    if (localMagCalibrationStatus == null)
    {
      localMagCalibrationStatus = new MagCalibrationStatus(null);
      this.calibrationTracker.append(paramMagnetometerCalibrationProgress.getCompassId(), localMagCalibrationStatus);
    }
    localMagCalibrationStatus.percentage = paramMagnetometerCalibrationProgress.getCompletionPercentage();
    int i = 0;
    int j = this.calibrationTracker.size();
    for (int k = 0; k < j; k++)
      i += ((MagCalibrationStatus)this.calibrationTracker.valueAt(k)).percentage;
    if (j > 0);
    for (int m = i / j; ; m = 0)
    {
      if (this.calibrationProgress.isIndeterminate())
      {
        this.calibrationProgress.setIndeterminate(false);
        this.calibrationProgress.setMax(100);
        this.calibrationProgress.setProgress(0);
      }
      if (this.calibrationProgress.getProgress() >= m)
        break;
      this.calibrationProgress.setProgress(m);
      return;
    }
  }

  private void handleMagResult(MagnetometerCalibrationResult paramMagnetometerCalibrationResult)
  {
    if (paramMagnetometerCalibrationResult == null);
    int i;
    int j;
    label91: label118: 
    do
    {
      MagCalibrationStatus localMagCalibrationStatus1;
      do
      {
        return;
        localMagCalibrationStatus1 = (MagCalibrationStatus)this.calibrationTracker.get(paramMagnetometerCalibrationResult.getCompassId());
      }
      while (localMagCalibrationStatus1 == null);
      localMagCalibrationStatus1.percentage = 100;
      localMagCalibrationStatus1.isComplete = true;
      localMagCalibrationStatus1.isSuccessful = paramMagnetometerCalibrationResult.isCalibrationSuccessful();
      i = 1;
      j = 1;
      int k = 0;
      if (k < this.calibrationTracker.size())
      {
        MagCalibrationStatus localMagCalibrationStatus2 = (MagCalibrationStatus)this.calibrationTracker.valueAt(k);
        if ((i != 0) && (localMagCalibrationStatus2.isComplete))
        {
          i = 1;
          if ((j == 0) || (!localMagCalibrationStatus2.isSuccessful))
            break label118;
        }
        for (j = 1; ; j = 0)
        {
          k++;
          break;
          i = 0;
          break label91;
        }
      }
    }
    while (i == 0);
    if (j != 0)
      updateUI(3);
    while (true)
    {
      CalibrationApi.acceptMagnetometerCalibration(getDrone());
      return;
      updateUI(4);
    }
  }

  private void proceedWithCalibration(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
    case 2:
    default:
      return;
    case 0:
    case 4:
    case 5:
      startCalibration();
      return;
    case 3:
    }
    startActivity(new Intent(getContext(), FlightActivity.class));
  }

  private void startCalibration()
  {
    CalibrationApi.startMagnetometerCalibration(getDrone(), false, false, 5);
    updateUI(1, true);
  }

  private void updateUI(int paramInt)
  {
    updateUI(paramInt, false);
  }

  private void updateUI(int paramInt, boolean paramBoolean)
  {
    if (this.calibrationStep == paramInt);
    while ((!paramBoolean) && (paramInt < this.calibrationStep))
      return;
    this.calibrationStep = paramInt;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      enableCancelMenu(false);
      this.calibrationProgress.setVisibility(4);
      this.instructionsContainer.setVisibility(0);
      this.calibrationInstructions.setText(2131099834);
      this.calibrationImage.setImageLevel(0);
      this.calibrationImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
      this.calibrationVideo.setVisibility(8);
      this.calibrationButton.setVisibility(0);
      this.calibrationButton.setTextColor(-1);
      this.calibrationButton.setBackgroundResource(2130837753);
      this.advicesContainer.setVisibility(8);
      return;
    case 2:
      this.appAnalytics.track("Calibration", "Compass Calibration Started", "");
      enableCancelMenu(true);
      if (!this.calibrationVideo.isPlaying())
        this.calibrationVideo.start();
    case 1:
      this.calibrationTracker.clear();
      this.calibrationVideo.setVisibility(0);
      this.calibrationProgress.setVisibility(0);
      this.calibrationProgress.setProgress(0);
      this.calibrationProgress.setIndeterminate(true);
      this.instructionsContainer.setVisibility(8);
      this.calibrationButton.setVisibility(8);
      this.advicesContainer.setVisibility(0);
      return;
    case 3:
      this.appAnalytics.track("Calibration", "Compass Calibration Completed", "");
      playSound(2131034130);
      enableCancelMenu(false);
      this.calibrationProgress.setVisibility(0);
      this.calibrationProgress.setIndeterminate(false);
      this.calibrationProgress.setMax(100);
      this.calibrationProgress.setProgress(100);
      this.instructionsContainer.setVisibility(0);
      this.calibrationInstructions.setText(2131099843);
      this.calibrationImage.setImageLevel(1);
      this.calibrationImage.setScaleType(ImageView.ScaleType.CENTER);
      this.calibrationVideo.stopPlayback();
      this.calibrationVideo.setVisibility(8);
      this.calibrationButton.setVisibility(0);
      this.calibrationButton.setBackgroundResource(2130837861);
      this.calibrationButton.setTextColor(getResources().getColor(2131427386));
      this.calibrationButton.setText(2131099912);
      this.advicesContainer.setVisibility(8);
      return;
    case 4:
    case 5:
    }
    this.appAnalytics.track("Calibration", "Compass Calibration Failed", "");
    enableCancelMenu(false);
    this.calibrationProgress.setVisibility(0);
    this.calibrationProgress.setIndeterminate(false);
    this.calibrationProgress.setMax(100);
    this.calibrationProgress.setProgress(100);
    this.instructionsContainer.setVisibility(0);
    this.calibrationInstructions.setText(2131099865);
    this.calibrationImage.setImageLevel(2);
    this.calibrationImage.setScaleType(ImageView.ScaleType.CENTER);
    this.calibrationVideo.stopPlayback();
    this.calibrationVideo.setVisibility(8);
    this.calibrationButton.setVisibility(0);
    this.calibrationButton.setBackgroundResource(2130837861);
    this.calibrationButton.setTextColor(getResources().getColor(2131427386));
    this.calibrationButton.setText(2131099949);
    this.advicesContainer.setVisibility(8);
  }

  public int getSettingDetailTitle()
  {
    return 2131099930;
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131558401, paramMenu);
    this.cancelMenuItem = paramMenu.findItem(2131493374);
    this.cancelMenuItem.setEnabled(this.isCancelMenuEnabled);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    setHasOptionsMenu(true);
    return paramLayoutInflater.inflate(2130903110, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    MagnetometerCalibrationStatus localMagnetometerCalibrationStatus = (MagnetometerCalibrationStatus)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.MAGNETOMETER_CALIBRATION_STATUS");
    if ((localMagnetometerCalibrationStatus != null) && (!localMagnetometerCalibrationStatus.isCalibrationCancelled()) && (!localMagnetometerCalibrationStatus.isCalibrationComplete()))
    {
      List localList = localMagnetometerCalibrationStatus.getCompassIds();
      Iterator localIterator1 = localList.iterator();
      while (localIterator1.hasNext())
        handleMagProgress(localMagnetometerCalibrationStatus.getCalibrationProgress(((Byte)localIterator1.next()).byteValue()));
      Iterator localIterator2 = localList.iterator();
      while (localIterator2.hasNext())
        handleMagResult(localMagnetometerCalibrationStatus.getCalibrationResult(((Byte)localIterator2.next()).byteValue()));
    }
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onDroneDetached()
  {
    super.onDroneDetached();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131493374:
    }
    cancelCalibration();
    return true;
  }

  public void onStop()
  {
    cancelCalibration();
    super.onStop();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appAnalytics = getApplication().getAppAnalytics();
    this.calibrationProgress = ((ProgressBar)paramView.findViewById(2131493249));
    this.instructionsContainer = paramView.findViewById(2131493250);
    this.calibrationInstructions = ((TextView)paramView.findViewById(2131493252));
    this.calibrationImage = ((ImageView)paramView.findViewById(2131493253));
    this.calibrationVideo = ((VideoView)paramView.findViewById(2131493254));
    this.calibrationVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
    {
      public void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
      {
        paramAnonymousMediaPlayer.setLooping(true);
      }
    });
    this.calibrationVideo.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + 2131034117));
    this.calibrationButton = ((TextView)paramView.findViewById(2131493255));
    this.calibrationButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SoloCompassCalibrationFragment.this.proceedWithCalibration(SoloCompassCalibrationFragment.this.calibrationStep);
      }
    });
    this.advicesContainer = paramView.findViewById(2131493256);
    updateUI(0);
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface CompassCalibrationStep
  {
  }

  private static class MagCalibrationStatus
  {
    boolean isComplete;
    boolean isSuccessful;
    int percentage;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloCompassCalibrationFragment
 * JD-Core Version:    0.6.2
 */