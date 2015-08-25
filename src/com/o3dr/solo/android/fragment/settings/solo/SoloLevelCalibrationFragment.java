package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.solo.android.activity.FlightActivity;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SoloLevelCalibrationFragment extends SettingsDetailFragment
{
  private static final String ACCEL_CAL_COMPLETED = "Level Calibration Completed";
  private static final String ACCEL_CAL_FAILED = "Level Calibration Failed";
  private static final String ACCEL_CAL_STARTED = "Level Calibration Started";
  private static final int STEP_BEGIN_CALIBRATION = 0;
  private static final int STEP_CALIBRATION_FAILED = 8;
  private static final int STEP_CALIBRATION_SUCCESSFUL = 7;
  private static final int STEP_SOLO_BACK_SIDE = 5;
  private static final int STEP_SOLO_FRONT_SIDE = 4;
  private static final int STEP_SOLO_LEFT_SIDE = 3;
  private static final int STEP_SOLO_LEVEL = 1;
  private static final int STEP_SOLO_RIGHT_SIDE = 2;
  private static final int STEP_SOLO_UPSIDE_DOWN = 6;

  @StringRes
  private static final int[] buttonLabelResId = { 2131099851, 2131099898, 2131099898, 2131099898, 2131099898, 2131099898, 2131099880, 2131099912, 2131099949 };

  @StringRes
  private static final int[] calibrationTitleResIds;
  private static final IntentFilter filter;

  @StringRes
  private static final int[] instructionsResId = { 2131099827, 2131099830, 2131099831, 2131099829, 2131099828, 2131099826, 2131099832, 2131099843, 2131099895 };
  private AppAnalytics appAnalytics;
  private ImageView calibrationImage;
  private TextView calibrationInstructions;
  private int calibrationStep = 0;
  private TextView calibrationStepButton;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str1.hashCode())
      {
      default:
        switch (i)
        {
        default:
        case 0:
        case 1:
        case 2:
        }
        break;
      case -2031203667:
      case 255070678:
      case 474860975:
      }
      String str2;
      do
      {
        return;
        if (!str1.equals("com.o3dr.services.android.lib.attribute.event.CALIBRATION_IMU"))
          break;
        i = 0;
        break;
        if (!str1.equals("com.o3dr.services.android.lib.attribute.event.CALIBRATION_IMU_ERROR"))
          break;
        i = 1;
        break;
        if (!str1.equals("com.o3dr.services.android.lib.attribute.event.CALIBRATION_IMU_TIMEOUT"))
          break;
        i = 2;
        break;
        str2 = paramAnonymousIntent.getStringExtra("com.o3dr.services.android.lib.attribute.event.extra.CALIBRATION_IMU_MESSAGE");
      }
      while (TextUtils.isEmpty(str2));
      SoloLevelCalibrationFragment.this.processCalibrationStatus(str2);
      return;
      SoloLevelCalibrationFragment.this.updateUI(8);
    }
  };

  static
  {
    calibrationTitleResIds = new int[] { 2131099928, 2131099928, 2131099928, 2131099928, 2131099928, 2131099928, 2131099928, 2131099855, 2131099854 };
    filter = new IntentFilter();
    filter.addAction("com.o3dr.services.android.lib.attribute.event.CALIBRATION_IMU");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.CALIBRATION_IMU_ERROR");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.CALIBRATION_IMU_TIMEOUT");
  }

  private void proceedWithCalibration(int paramInt)
  {
    switch (paramInt)
    {
    default:
      sendAck(paramInt);
      return;
    case 0:
      this.appAnalytics.track("Calibration", "Level Calibration Started", "");
      startCalibration();
      return;
    case 8:
      this.appAnalytics.track("Calibration", "Level Calibration Failed", "");
      startCalibration();
      return;
    case 7:
    }
    this.appAnalytics.track("Calibration", "Level Calibration Completed", "");
    startActivity(new Intent(getContext(), FlightActivity.class));
  }

  private void processCalibrationStatus(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
    {
      do
        return;
      while ((!paramString.contains("Place")) && (!paramString.contains("Calibration")));
      if (paramString.contains("level"))
      {
        updateUI(1);
        return;
      }
      if (paramString.contains("LEFT"))
      {
        updateUI(3);
        return;
      }
      if (paramString.contains("RIGHT"))
      {
        updateUI(2);
        return;
      }
      if (paramString.contains("DOWN"))
      {
        updateUI(4);
        return;
      }
      if (paramString.contains("UP"))
      {
        updateUI(5);
        return;
      }
      if (paramString.contains("BACK"))
      {
        updateUI(6);
        return;
      }
      if (paramString.contains("Calibration successful"))
      {
        updateUI(7);
        return;
      }
    }
    while (!paramString.contains("Calibration FAILED"));
    updateUI(8);
  }

  private void resetCalibration()
  {
    updateUI(0);
  }

  private void sendAck(int paramInt)
  {
    getDrone().sendIMUCalibrationAck(paramInt);
  }

  private void startCalibration()
  {
    getDrone().startIMUCalibration();
  }

  private void updateUI(int paramInt)
  {
    this.calibrationStep = paramInt;
    this.calibrationStepButton.setText(buttonLabelResId[paramInt]);
    this.calibrationImage.setImageLevel(paramInt);
    this.calibrationInstructions.setText(instructionsResId[paramInt]);
    updateTitle(calibrationTitleResIds[paramInt]);
    switch (paramInt)
    {
    default:
      return;
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
      this.calibrationImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
      this.calibrationStepButton.setTextColor(-1);
      this.calibrationStepButton.setBackgroundResource(2130837753);
      return;
    case 7:
      playSound(2131034130);
    case 8:
    }
    this.calibrationImage.setScaleType(ImageView.ScaleType.CENTER);
    this.calibrationStepButton.setBackgroundResource(2130837861);
    this.calibrationStepButton.setTextColor(getResources().getColor(2131427386));
  }

  public int getSettingDetailTitle()
  {
    return 2131099928;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903109, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    State localState = (State)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
    if (localState.isFlying())
    {
      resetCalibration();
      this.calibrationStepButton.setEnabled(false);
    }
    while (true)
    {
      getBroadcastManager().registerReceiver(this.receiver, filter);
      return;
      this.calibrationStepButton.setEnabled(true);
      if (localState.isCalibrating())
        processCalibrationStatus(localState.getCalibrationStatus());
      else
        resetCalibration();
    }
  }

  public void onDroneDetached()
  {
    super.onDroneDetached();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appAnalytics = getApplication().getAppAnalytics();
    this.calibrationImage = ((ImageView)paramView.findViewById(2131493248));
    this.calibrationImage.setImageLevel(0);
    this.calibrationInstructions = ((TextView)paramView.findViewById(2131493247));
    this.calibrationStepButton = ((TextView)paramView.findViewById(2131493246));
    this.calibrationStepButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SoloLevelCalibrationFragment.this.proceedWithCalibration(SoloLevelCalibrationFragment.this.calibrationStep);
      }
    });
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface AccelCalibrationStep
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloLevelCalibrationFragment
 * JD-Core Version:    0.6.2
 */