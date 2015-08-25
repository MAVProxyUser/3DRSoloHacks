package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.apis.drone.ParameterApi;
import com.o3dr.services.android.lib.drone.property.Parameter;
import com.o3dr.services.android.lib.drone.property.Parameters;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import java.util.ArrayList;
import java.util.List;

public class SoloPerformanceFragment extends SettingsDetailFragment
  implements SeekBar.OnSeekBarChangeListener
{
  private static final float AGILITY_INDEX_TO_PROGRESS = 0.0F;
  private static final float GPS_SPEED_INDEX_TO_PROGRESS = 0.0F;
  private static final int MAX_PROGRESS = 100;
  private static final String SPEED = "Speed";
  private static final String TAG = SoloPerformanceFragment.class.getSimpleName();
  private static final String YAW_RATE = "Yaw Rate";
  private static final IntentFilter filter = new IntentFilter();
  private SeekBar agilityBar;
  private TextView agilityView;
  private AppAnalytics appAnalytics;
  private SeekBar gpsSpeedBar;
  private TextView gpsSpeedView;
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
        }
        break;
      case 598821639:
      }
      String str2;
      double d;
      do
      {
        return;
        if (!str1.equals("com.o3dr.services.android.lib.attribute.event.PARAMETERS_RECEIVED"))
          break;
        i = 0;
        break;
        str2 = paramAnonymousIntent.getStringExtra("com.o3dr.services.android.lib.attribute.event.extra.PARAMETER_NAME");
        d = paramAnonymousIntent.getDoubleExtra("com.o3dr.services.android.lib.attribute.event.extra.PARAMETER_VALUE", -1.0D);
        if (SoloPerformanceFragment.FlightParam.getReferenceParam().name().equals(str2))
        {
          SoloPerformanceFragment.this.loadFlightPerformance(d);
          return;
        }
      }
      while (!SoloPerformanceFragment.CameraPanParam.getReferenceParam().name().equals(str2));
      SoloPerformanceFragment.this.loadCameraPanPerformance(d);
    }
  };

  static
  {
    filter.addAction("com.o3dr.services.android.lib.attribute.event.PARAMETERS_RECEIVED");
    GPS_SPEED_INDEX_TO_PROGRESS = 100 / FlightParam.getMaxIndex();
  }

  private int getMatchingIndex(double[] paramArrayOfDouble, double paramDouble)
  {
    int i = paramArrayOfDouble.length;
    double d1 = 1.7976931348623157E+308D;
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      double d2 = Math.abs(paramDouble - paramArrayOfDouble[k]);
      if (d2 < d1)
      {
        d1 = d2;
        j = k;
      }
    }
    return j;
  }

  private void loadCameraPanPerformance(double paramDouble)
  {
    int i = getMatchingIndex(CameraPanParam.getReferenceParam().getValues(), paramDouble);
    this.agilityView.setText(CameraPanParam.getAgilityName(i));
    if (i != (int)(this.agilityBar.getProgress() / AGILITY_INDEX_TO_PROGRESS))
      this.agilityBar.setProgress((int)(i * AGILITY_INDEX_TO_PROGRESS));
  }

  private void loadFlightPerformance(double paramDouble)
  {
    int i = getMatchingIndex(FlightParam.getReferenceParam().getValues(), paramDouble);
    this.gpsSpeedView.setText(FlightParam.getSpeedName(i));
    if (i != (int)(this.gpsSpeedBar.getProgress() / GPS_SPEED_INDEX_TO_PROGRESS))
      this.gpsSpeedBar.setProgress((int)(i * GPS_SPEED_INDEX_TO_PROGRESS));
  }

  private void updateParameters(int paramInt, PerformanceParam[] paramArrayOfPerformanceParam)
  {
    updateParameters(getDrone(), paramArrayOfPerformanceParam, paramInt);
  }

  private static void updateParameters(Drone paramDrone, PerformanceParam[] paramArrayOfPerformanceParam, int paramInt)
  {
    if (paramDrone == null)
      return;
    ArrayList localArrayList = new ArrayList(paramArrayOfPerformanceParam.length);
    int i = paramArrayOfPerformanceParam.length;
    for (int j = 0; j < i; j++)
    {
      PerformanceParam localPerformanceParam = paramArrayOfPerformanceParam[j];
      double d = localPerformanceParam.getValues()[paramInt];
      localArrayList.add(new Parameter(localPerformanceParam.name(), d, 9));
    }
    ParameterApi.writeParameters(paramDrone, new Parameters(localArrayList));
  }

  public int getSettingDetailTitle()
  {
    return 2131099937;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903115, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    Parameters localParameters = (Parameters)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.PARAMETERS");
    if ((localParameters == null) || (localParameters.getParameters().isEmpty()));
    Parameter localParameter2;
    do
    {
      return;
      Parameter localParameter1 = localParameters.getParameter(FlightParam.getReferenceParam().name());
      if (localParameter1 != null)
        loadFlightPerformance(localParameter1.getValue());
      localParameter2 = localParameters.getParameter(CameraPanParam.getReferenceParam().name());
    }
    while (localParameter2 == null);
    loadCameraPanPerformance(localParameter2.getValue());
  }

  public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
  {
  }

  public void onStart()
  {
    super.onStart();
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onStartTrackingTouch(SeekBar paramSeekBar)
  {
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onStopTrackingTouch(SeekBar paramSeekBar)
  {
    Log.d(TAG, "Updating performance values.");
    int i = paramSeekBar.getProgress();
    switch (paramSeekBar.getId())
    {
    default:
      return;
    case 2131493291:
      int k = (int)(i / AGILITY_INDEX_TO_PROGRESS);
      updateParameters(k, CameraPanParam.values());
      String str2 = CameraPanParam.getAgilityName(k);
      this.agilityView.setText(str2);
      this.appAnalytics.track("Settings", "Yaw Rate", str2);
      return;
    case 2131493286:
    }
    int j = (int)(i / GPS_SPEED_INDEX_TO_PROGRESS);
    updateParameters(j, FlightParam.values());
    String str1 = FlightParam.getSpeedName(j);
    this.gpsSpeedView.setText(str1);
    this.appAnalytics.track("Settings", "Speed", str1);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appAnalytics = getApplication().getAppAnalytics();
    this.gpsSpeedView = ((TextView)paramView.findViewById(2131493283));
    this.gpsSpeedBar = ((SeekBar)paramView.findViewById(2131493286));
    this.gpsSpeedBar.setMax(100);
    this.gpsSpeedBar.setOnSeekBarChangeListener(this);
    this.agilityView = ((TextView)paramView.findViewById(2131493288));
    this.agilityBar = ((SeekBar)paramView.findViewById(2131493291));
    this.agilityBar.setMax(100);
    this.agilityBar.setOnSeekBarChangeListener(this);
  }

  private static enum CameraPanParam
    implements SoloPerformanceFragment.PerformanceParam
  {
    private static final String[] PERF_CAMERA_PAN_NAMES = { "Very slow", "Slow", "Medium", "Fast", "Very fast" };
    private final double[] values;

    static
    {
      ACRO_YAW_P = new CameraPanParam("ACRO_YAW_P", 1, new double[] { 1.0D, 1.4D, 1.8D, 2.2D, 3.0D });
      CameraPanParam[] arrayOfCameraPanParam = new CameraPanParam[2];
      arrayOfCameraPanParam[0] = ATC_ACCEL_Y_MAX;
      arrayOfCameraPanParam[1] = ACRO_YAW_P;
    }

    private CameraPanParam(double[] paramArrayOfDouble)
    {
      if (paramArrayOfDouble.length != 5)
        throw new IllegalStateException("Invalid value set.");
      this.values = paramArrayOfDouble;
    }

    public static String getAgilityName(int paramInt)
    {
      return PERF_CAMERA_PAN_NAMES[paramInt];
    }

    public static int getMaxIndex()
    {
      return -1 + PERF_CAMERA_PAN_NAMES.length;
    }

    public static CameraPanParam getReferenceParam()
    {
      return ATC_ACCEL_Y_MAX;
    }

    public double[] getValues()
    {
      return this.values;
    }
  }

  private static enum FlightParam
    implements SoloPerformanceFragment.PerformanceParam
  {
    private static final String[] PERF_FLIGHT_NAMES = { "Very slow", "Slow", "Medium", "Fast", "Very fast" };
    private final double[] values;

    static
    {
      WPNAV_LOIT_JERK = new FlightParam("WPNAV_LOIT_JERK", 1, new double[] { 1000.0D, 1400.0D, 1800.0D, 2200.0D, 3000.0D });
      WPNAV_LOIT_MAXA = new FlightParam("WPNAV_LOIT_MAXA", 2, new double[] { 229.0D, 321.0D, 412.0D, 504.0D, 687.0D });
      WPNAV_LOIT_MINA = new FlightParam("WPNAV_LOIT_MINA", 3, new double[] { 108.0D, 138.0D, 163.0D, 181.0D, 206.0D });
      WPNAV_ACCEL = new FlightParam("WPNAV_ACCEL", 4, new double[] { 100.0D, 180.0D, 260.0D, 340.0D, 500.0D });
      WPNAV_ACCEL_Z = new FlightParam("WPNAV_ACCEL_Z", 5, new double[] { 100.0D, 120.0D, 140.0D, 160.0D, 200.0D });
      WPNAV_SPEED_DN = new FlightParam("WPNAV_SPEED_DN", 6, new double[] { 150.0D, 180.0D, 210.0D, 240.0D, 300.0D });
      WPNAV_SPEED_UP = new FlightParam("WPNAV_SPEED_UP", 7, new double[] { 200.0D, 240.0D, 280.0D, 320.0D, 400.0D });
      WPNAV_SPEED = new FlightParam("WPNAV_SPEED", 8, new double[] { 500.0D, 700.0D, 900.0D, 1100.0D, 1500.0D });
      PILOT_VELZ_MAX = new FlightParam("PILOT_VELZ_MAX", 9, new double[] { 133.0D, 186.0D, 240.0D, 293.0D, 400.0D });
      PILOT_ACCEL_Z = new FlightParam("PILOT_ACCEL_Z", 10, new double[] { 100.0D, 140.0D, 180.0D, 220.0D, 300.0D });
      ATC_ACCEL_R_MAX = new FlightParam("ATC_ACCEL_R_MAX", 11, new double[] { 36000.0D, 43200.0D, 50400.0D, 57600.0D, 72000.0D });
      ATC_ACCEL_P_MAX = new FlightParam("ATC_ACCEL_P_MAX", 12, new double[] { 36000.0D, 43200.0D, 50400.0D, 57600.0D, 72000.0D });
      RC_FEEL_RP = new FlightParam("RC_FEEL_RP", 13, new double[] { 20.0D, 28.0D, 36.0D, 44.0D, 60.0D });
      ANGLE_MAX = new FlightParam("ANGLE_MAX", 14, new double[] { 2000.0D, 2333.0D, 2649.0D, 2950.0D, 3500.0D });
      ACRO_BAL_PITCH = new FlightParam("ACRO_BAL_PITCH", 15, new double[] { 1.0D, 0.5D, 0.25D, 0.1D, 0.0D });
      ACRO_BAL_ROLL = new FlightParam("ACRO_BAL_ROLL", 16, new double[] { 1.0D, 0.5D, 0.25D, 0.1D, 0.0D });
      ACRO_RP_P = new FlightParam("ACRO_RP_P", 17, new double[] { 4.0D, 4.8D, 5.6D, 6.4D, 8.0D });
      ACRO_TRAINER = new FlightParam("ACRO_TRAINER", 18, new double[] { 2.0D, 2.0D, 2.0D, 2.0D, 0.0D });
      FlightParam[] arrayOfFlightParam = new FlightParam[19];
      arrayOfFlightParam[0] = WPNAV_LOIT_SPEED;
      arrayOfFlightParam[1] = WPNAV_LOIT_JERK;
      arrayOfFlightParam[2] = WPNAV_LOIT_MAXA;
      arrayOfFlightParam[3] = WPNAV_LOIT_MINA;
      arrayOfFlightParam[4] = WPNAV_ACCEL;
      arrayOfFlightParam[5] = WPNAV_ACCEL_Z;
      arrayOfFlightParam[6] = WPNAV_SPEED_DN;
      arrayOfFlightParam[7] = WPNAV_SPEED_UP;
      arrayOfFlightParam[8] = WPNAV_SPEED;
      arrayOfFlightParam[9] = PILOT_VELZ_MAX;
      arrayOfFlightParam[10] = PILOT_ACCEL_Z;
      arrayOfFlightParam[11] = ATC_ACCEL_R_MAX;
      arrayOfFlightParam[12] = ATC_ACCEL_P_MAX;
      arrayOfFlightParam[13] = RC_FEEL_RP;
      arrayOfFlightParam[14] = ANGLE_MAX;
      arrayOfFlightParam[15] = ACRO_BAL_PITCH;
      arrayOfFlightParam[16] = ACRO_BAL_ROLL;
      arrayOfFlightParam[17] = ACRO_RP_P;
      arrayOfFlightParam[18] = ACRO_TRAINER;
    }

    private FlightParam(double[] paramArrayOfDouble)
    {
      if (paramArrayOfDouble.length != 5)
        throw new IllegalStateException("Invalid value set.");
      this.values = paramArrayOfDouble;
    }

    public static int getMaxIndex()
    {
      return -1 + PERF_FLIGHT_NAMES.length;
    }

    public static FlightParam getReferenceParam()
    {
      return WPNAV_LOIT_SPEED;
    }

    public static String getSpeedName(int paramInt)
    {
      return PERF_FLIGHT_NAMES[paramInt];
    }

    public double[] getValues()
    {
      return this.values;
    }
  }

  private static abstract interface PerformanceParam
  {
    public abstract double[] getValues();

    public abstract String name();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloPerformanceFragment
 * JD-Core Version:    0.6.2
 */