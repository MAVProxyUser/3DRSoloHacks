package com.o3dr.solo.android.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Battery;
import com.o3dr.services.android.lib.drone.property.EkfStatus;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.drone.property.Home;
import com.o3dr.services.android.lib.drone.property.Signal;
import com.o3dr.services.android.lib.drone.property.Speed;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.services.android.lib.util.SpannableUtils;
import com.o3dr.solo.android.activity.FlightActivity;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.fragment.shots.ShotMenuFragment;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;
import com.o3dr.solo.android.util.unit.UnitManager;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.SpeedUnitProvider;
import com.o3dr.solo.android.util.unit.systems.UnitSystem;
import java.util.Locale;
import org.beyene.sius.unit.length.LengthUnit;

public class TelemetryFragment extends BaseFragment
{
  public static final boolean DEFAULT_IS_TELEM_VISIBLE = true;
  private static final String DIALOG_TAG = "Telemetry dialog";
  private static final int DISCONNECTED = 0;
  public static final String EXTRA_IS_TELEM_VISIBLE = "extra_is_telemetry_visible";
  private static final int GPS_CONNECTED = 2;
  private static final int GPS_DISCONNECTED = 1;
  private static final String TAG = TelemetryFragment.class.getSimpleName();
  private static final double WIFI_IS_EXCEPTIONAL = -30.0D;
  private static final double WIFI_IS_GOOD = -50.0D;
  private static final double WIFI_IS_MARGINAL = -60.0D;
  private static final double WIFI_IS_POOR = -70.0D;
  private static final double WIFI_IS_VERY_GOOD = -40.0D;
  private static final IntentFilter eventFilter = new IntentFilter();
  private TextView altitudeTelem;
  private LevelListDrawable batteryStatusIcon;
  private TextView batteryTelem;
  private TextView distanceTelem;
  private final BroadcastReceiver eventReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      Drone localDrone = TelemetryFragment.this.getDrone();
      if (localDrone == null)
        return;
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 1059836852:
      case 566338349:
      case -258149067:
      case -495005525:
      case -966973459:
      case -1096304662:
      case -1921525958:
      case 1256617868:
      case 1962523320:
      case 551871472:
      case 662782932:
      case -1172478733:
      case -1467072149:
      case 1035382905:
      case -302519047:
      case -508972501:
      case 1343486835:
      case 465944229:
      case 1138777058:
      }
      while (true)
        switch (i)
        {
        case 2:
        default:
          return;
        case 0:
          Speed localSpeed = (Speed)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.SPEED");
          TelemetryFragment.this.updateSpeedTelemetry(localSpeed);
          return;
          if (str.equals("com.o3dr.services.android.lib.attribute.event.SPEED_UPDATED"))
          {
            i = 0;
            continue;
            if (str.equals("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED"))
            {
              i = 1;
              continue;
              if (str.equals("com.o3dr.services.android.lib.attribute.event.ATTITUDE_UPDATED"))
              {
                i = 2;
                continue;
                if (str.equals("com.o3dr.services.android.lib.attribute.event.GPS_COUNT"))
                {
                  i = 3;
                  continue;
                  if (str.equals("com.o3dr.services.android.lib.attribute.event.GPS_POSITION"))
                  {
                    i = 4;
                    continue;
                    if (str.equals("com.o3dr.services.android.lib.attribute.event.HOME_UPDATED"))
                    {
                      i = 5;
                      continue;
                      if (str.equals("com.o3dr.services.android.lib.attribute.event.BATTERY_UPDATED"))
                      {
                        i = 6;
                        continue;
                        if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
                        {
                          i = 7;
                          continue;
                          if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED"))
                          {
                            i = 8;
                            continue;
                            if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
                            {
                              i = 9;
                              continue;
                              if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
                              {
                                i = 10;
                                continue;
                                if (str.equals("com.o3dr.services.android.lib.attribute.event.SIGNAL_UPDATED"))
                                {
                                  i = 11;
                                  continue;
                                  if (str.equals("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED"))
                                  {
                                    i = 12;
                                    continue;
                                    if (str.equals("com.o3dr.solo.android.action.SOLO_LINK_DISCONNECTED"))
                                    {
                                      i = 13;
                                      continue;
                                      if (str.equals("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED"))
                                      {
                                        i = 14;
                                        continue;
                                        if (str.equals("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED"))
                                        {
                                          i = 15;
                                          continue;
                                          if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_VEHICLE_MODE"))
                                          {
                                            i = 16;
                                            continue;
                                            if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_EKF_POSITION"))
                                            {
                                              i = 17;
                                              continue;
                                              if (str.equals("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE"))
                                                i = 18;
                                            }
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          break;
        case 1:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        }
      Altitude localAltitude = (Altitude)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE");
      TelemetryFragment.this.updateAltitudeTelemetry(localAltitude);
      return;
      Gps localGps2 = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
      TelemetryFragment.this.updateGpsTelemetry(localGps2);
      return;
      Gps localGps1 = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
      Home localHome = (Home)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.HOME");
      TelemetryFragment.this.updateHomeDistance(localGps1, localHome);
      return;
      Battery localBattery = (Battery)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.BATTERY");
      TelemetryFragment.this.updateBatteryTelemetry(localBattery, TelemetryFragment.access$400(TelemetryFragment.this));
      return;
      TelemetryFragment.this.updateAllTelemetry(localDrone);
      return;
      Signal localSignal = (Signal)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.SIGNAL");
      TelemetryFragment.this.updateSignalTelemetry(localSignal);
      return;
      TelemetryFragment.this.updateShotTelemetry(localDrone);
    }
  };
  private boolean isTelemVisible = true;
  private View leftTelemSet;
  private View navAlertIcon;
  private FlightActivity parent;
  private View rightTelemSet;
  private TextView shotTelem;
  private ImageView signalTelem;
  private TextView subBatteryTelem;
  private TextView subGpsTelem;
  private TextView subSignalTelem;
  private TextView subSpeedTelem;
  private View subTelemBar;
  private View telemBg;
  private final View.OnClickListener telemClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (TelemetryFragment.this.subTelemBar.getVisibility() == 0)
      {
        TelemetryFragment.this.subTelemBar.setVisibility(8);
        return;
      }
      TelemetryFragment.this.subTelemBar.setVisibility(0);
    }
  };
  private ImageView telemShotIcon;
  private ImageView toggleTelemButton;

  static
  {
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.SPEED_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.ATTITUDE_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.GPS_COUNT");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.GPS_POSITION");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_EKF_POSITION");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.HOME_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.BATTERY_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.SIGNAL_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_VEHICLE_MODE");
    eventFilter.addAction("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE");
    eventFilter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    eventFilter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
    eventFilter.addAction("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED");
    eventFilter.addAction("com.o3dr.solo.android.action.SOLO_LINK_DISCONNECTED");
    eventFilter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED");
    eventFilter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED");
  }

  private void updateAllTelemetry(Drone paramDrone)
  {
    Gps localGps = (Gps)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    Home localHome = (Home)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.HOME");
    updateShotTelemetry(paramDrone);
    updateAltitudeTelemetry((Altitude)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE"));
    updateSpeedTelemetry((Speed)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.SPEED"));
    updateBatteryTelemetry((Battery)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.BATTERY"), isSoloLinkConnected());
    updateSignalTelemetry((Signal)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.SIGNAL"));
    updateGpsTelemetry(localGps);
    updateHomeDistance(localGps, localHome);
  }

  private void updateAltitudeTelemetry(Altitude paramAltitude)
  {
    if (!isSoloLinkConnected())
      return;
    LengthUnit localLengthUnit = UnitManager.getUnitSystem(getContext()).getLengthUnitProvider().boxBaseValueToTarget(paramAltitude.getAltitude());
    this.altitudeTelem.setText(localLengthUnit.toString());
  }

  private void updateBatteryTelemetry(Battery paramBattery, boolean paramBoolean)
  {
    TextView localTextView = this.subBatteryTelem;
    CharSequence[] arrayOfCharSequence = new CharSequence[2];
    arrayOfCharSequence[0] = SpannableUtils.bold(new CharSequence[] { "BATT:" });
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Double.valueOf(paramBattery.getBatteryVoltage());
    arrayOfObject[1] = Double.valueOf(paramBattery.getBatteryCurrent());
    arrayOfCharSequence[1] = String.format(localLocale, " %2.1f V | %2.1f A ", arrayOfObject);
    localTextView.setText(SpannableUtils.normal(arrayOfCharSequence));
    if (paramBoolean)
    {
      int i = (int)paramBattery.getBatteryRemain();
      this.batteryTelem.setText(i + "%");
      this.batteryStatusIcon.setLevel(i);
      return;
    }
    this.batteryTelem.setText(2131099900);
    this.batteryStatusIcon.setLevel(0);
  }

  private void updateGpsTelemetry(Gps paramGps)
  {
    if (!isSoloLinkConnected())
      return;
    TextView localTextView = this.subGpsTelem;
    CharSequence[] arrayOfCharSequence = new CharSequence[2];
    arrayOfCharSequence[0] = SpannableUtils.bold(new CharSequence[] { "GPS:" });
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramGps.getSatellitesCount());
    arrayOfObject[1] = Double.valueOf(paramGps.getGpsEph());
    arrayOfCharSequence[1] = String.format(localLocale, " %d SATS | %.1f HDOP ", arrayOfObject);
    localTextView.setText(SpannableUtils.normal(arrayOfCharSequence));
  }

  private void updateHomeDistance(Gps paramGps, Home paramHome)
  {
    if (!isSoloLinkConnected())
      return;
    if ((paramGps.isValid()) && (paramHome.isValid()))
    {
      LengthUnit localLengthUnit = getLengthUnitProvider().boxBaseValueToTarget(MathUtils.getDistance2D(paramHome.getCoordinate(), paramGps.getPosition()));
      this.distanceTelem.setText(localLengthUnit.toString());
      return;
    }
    this.distanceTelem.setText("--");
  }

  private void updateShotTelemetry(Drone paramDrone)
  {
    Object localObject;
    if (!isSoloLinkConnected())
    {
      localObject = getString(2131099871);
      this.telemShotIcon.setImageResource(2130837751);
      this.telemShotIcon.setImageLevel(0);
    }
    while (true)
    {
      if (localObject != null)
        this.shotTelem.setText((CharSequence)localObject);
      return;
      int i = getSoloLinkManager().getShotManager().getCurrentShot();
      switch (i)
      {
      case 3:
      case 4:
      default:
        localObject = null;
        break;
      case -1:
        State localState = (State)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
        EkfStatus localEkfStatus = localState.getEkfStatus();
        this.telemShotIcon.setImageResource(2130837751);
        if (localEkfStatus.isPositionOk(localState.isArmed()))
          this.telemShotIcon.setImageLevel(2);
        while (true)
        {
          VehicleMode localVehicleMode = localState.getVehicleMode();
          localObject = SoloMessageShot.getFlightModeLabel(getContext(), localVehicleMode);
          break;
          this.telemShotIcon.setImageLevel(1);
        }
      case 2:
        localObject = SoloMessageShot.getShotLabel(getContext(), i);
        this.telemShotIcon.setImageResource(2130837795);
        break;
      case 0:
        localObject = SoloMessageShot.getShotLabel(getContext(), i);
        this.telemShotIcon.setImageResource(2130837801);
        break;
      case 1:
        localObject = SoloMessageShot.getShotLabel(getContext(), i);
        this.telemShotIcon.setImageResource(2130837800);
        break;
      case 5:
        localObject = SoloMessageShot.getShotLabel(getContext(), i);
        this.telemShotIcon.setImageResource(2130837797);
      }
    }
  }

  private void updateSignalTelemetry(Signal paramSignal)
  {
    if (!isSoloLinkConnected())
      return;
    double d1 = 1.9D * (127.0D + paramSignal.getRemrssi());
    if (d1 <= 127.0D);
    for (double d2 = d1; ; d2 = d1 - 256.0D)
    {
      TextView localTextView = this.subSignalTelem;
      CharSequence[] arrayOfCharSequence = new CharSequence[2];
      arrayOfCharSequence[0] = SpannableUtils.bold(new CharSequence[] { "RSSI: " });
      arrayOfCharSequence[1] = ((int)d2 + " dB");
      localTextView.setText(SpannableUtils.normal(arrayOfCharSequence));
      if (!paramSignal.isValid())
        break label213;
      if (d2 < -30.0D)
        break;
      this.signalTelem.setImageLevel(5);
      return;
    }
    if (d2 >= -40.0D)
    {
      this.signalTelem.setImageLevel(4);
      return;
    }
    if (d2 >= -50.0D)
    {
      this.signalTelem.setImageLevel(3);
      return;
    }
    if (d2 >= -60.0D)
    {
      this.signalTelem.setImageLevel(2);
      return;
    }
    if (d2 >= -70.0D)
    {
      this.signalTelem.setImageLevel(1);
      return;
    }
    this.signalTelem.setImageLevel(0);
    return;
    label213: this.signalTelem.setImageLevel(0);
  }

  private void updateSpeedTelemetry(Speed paramSpeed)
  {
    if (!isSoloLinkConnected())
      return;
    SpeedUnitProvider localSpeedUnitProvider = UnitManager.getUnitSystem(getContext()).getSpeedUnitProvider();
    TextView localTextView = this.subSpeedTelem;
    CharSequence[] arrayOfCharSequence = new CharSequence[2];
    arrayOfCharSequence[0] = SpannableUtils.bold(new CharSequence[] { "SPEED: " });
    arrayOfCharSequence[1] = localSpeedUnitProvider.boxBaseValueToTarget(paramSpeed.getAirSpeed()).toString().toUpperCase(Locale.US);
    localTextView.setText(SpannableUtils.normal(arrayOfCharSequence));
  }

  public void enableNavigationAlertIcon(boolean paramBoolean)
  {
    View localView;
    if (this.navAlertIcon != null)
    {
      localView = this.navAlertIcon;
      if (!paramBoolean)
        break label24;
    }
    label24: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void hideTelemetry()
  {
    this.telemBg.animate().translationYBy(-this.telemBg.getHeight());
    this.leftTelemSet.animate().translationYBy(-this.leftTelemSet.getHeight());
    this.rightTelemSet.animate().translationYBy(-this.rightTelemSet.getHeight());
    this.toggleTelemButton.animate().rotation(0.0F);
    this.subTelemBar.setVisibility(8);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof FlightActivity))
      throw new IllegalStateException("Parent activity must be an instance of " + FlightActivity.class.getName());
    this.parent = ((FlightActivity)paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903119, paramViewGroup, false);
  }

  public void onDetach()
  {
    super.onDetach();
    this.parent = null;
  }

  protected void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    if (paramDrone != null)
      updateAllTelemetry(paramDrone);
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("extra_is_telemetry_visible", this.isTelemVisible);
  }

  public void onStart()
  {
    super.onStart();
    LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.eventReceiver, eventFilter);
    if (this.isTelemVisible)
    {
      showTelemetry();
      return;
    }
    hideTelemetry();
  }

  public void onStop()
  {
    super.onStop();
    LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.eventReceiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.telemBg = paramView.findViewById(2131493325);
    this.leftTelemSet = paramView.findViewById(2131493327);
    this.rightTelemSet = paramView.findViewById(2131493333);
    this.navAlertIcon = paramView.findViewById(2131493018);
    this.navAlertIcon.setVisibility(8);
    ((ImageView)paramView.findViewById(2131493326)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TelemetryFragment.this.parent != null)
          TelemetryFragment.this.parent.openDrawer();
      }
    });
    this.altitudeTelem = ((TextView)paramView.findViewById(2131493330));
    this.altitudeTelem.setOnClickListener(this.telemClickListener);
    this.distanceTelem = ((TextView)paramView.findViewById(2131493329));
    this.distanceTelem.setOnClickListener(this.telemClickListener);
    this.batteryTelem = ((TextView)paramView.findViewById(2131493335));
    this.batteryTelem.setOnClickListener(this.telemClickListener);
    this.batteryStatusIcon = ((LevelListDrawable)this.batteryTelem.getCompoundDrawablesRelative()[0]);
    this.batteryStatusIcon.setLevel(100);
    this.signalTelem = ((ImageView)paramView.findViewById(2131493336));
    this.signalTelem.setImageLevel(4);
    this.signalTelem.setOnClickListener(this.telemClickListener);
    this.telemShotIcon = ((ImageView)paramView.findViewById(2131493331));
    this.telemShotIcon.setImageLevel(1);
    View localView = paramView.findViewById(2131493328);
    this.shotTelem = ((TextView)paramView.findViewById(2131493332));
    localView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FragmentManager localFragmentManager = TelemetryFragment.this.getChildFragmentManager();
        FragmentTransaction localFragmentTransaction = localFragmentManager.beginTransaction();
        Fragment localFragment = localFragmentManager.findFragmentByTag("Telemetry dialog");
        if (localFragment != null)
          localFragmentTransaction.remove(localFragment);
        localFragmentTransaction.addToBackStack(null);
        new ShotMenuFragment().show(localFragmentTransaction, "Telemetry dialog");
      }
    });
    this.toggleTelemButton = ((ImageView)paramView.findViewById(2131493334));
    this.toggleTelemButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TelemetryFragment localTelemetryFragment = TelemetryFragment.this;
        if (!TelemetryFragment.this.isTelemVisible);
        for (boolean bool = true; ; bool = false)
        {
          TelemetryFragment.access$1102(localTelemetryFragment, bool);
          if (!TelemetryFragment.this.isTelemVisible)
            break;
          TelemetryFragment.this.showTelemetry();
          return;
        }
        TelemetryFragment.this.hideTelemetry();
      }
    });
    this.subTelemBar = paramView.findViewById(2131493320);
    this.subSpeedTelem = ((TextView)paramView.findViewById(2131493321));
    this.subGpsTelem = ((TextView)paramView.findViewById(2131493322));
    this.subBatteryTelem = ((TextView)paramView.findViewById(2131493323));
    this.subSignalTelem = ((TextView)paramView.findViewById(2131493324));
    if (paramBundle != null)
      this.isTelemVisible = paramBundle.getBoolean("extra_is_telemetry_visible", true);
  }

  public void showTelemetry()
  {
    this.telemBg.animate().translationY(0.0F);
    this.leftTelemSet.animate().translationY(0.0F);
    this.rightTelemSet.animate().translationY(0.0F);
    this.toggleTelemButton.animate().rotation(180.0F);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.TelemetryFragment
 * JD-Core Version:    0.6.2
 */