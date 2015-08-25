package com.o3dr.solo.android.fragment.alerts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.annotation.DrawableRes;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.attribute.error.ErrorType;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Battery;
import com.o3dr.services.android.lib.drone.property.EkfStatus;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.service.artoo.button.ButtonPacket;
import com.o3dr.solo.android.service.sololink.tlv.ArtooMessageInputReport;
import com.o3dr.solo.android.service.sololink.tlv.TLVMessageParser;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.Utils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AlertsManagerFragment extends BaseFragment
  implements TextToSpeech.OnInitListener
{
  private static final String ALERT_STRING = "Alert String";
  private static final String ARM_READY_MESSAGE = "READY TO ARM";
  private static final String ARM_UNREADY_MESSAGE = "UNREADY FOR ARMING";
  private static final int AUTO_DISMISSAL_TIMEOUT = 5000;
  private static final String CALIBRATING_SENSORS_MESSAGE = "Waiting for Nav Checks";
  private static final int CONTROLLER_BATTERY_CRITICAL_LEVEL = 5;
  private static final int CONTROLLER_BATTERY_SHUTDOWN_LEVEL = 0;
  private static final int SOLO_BATTERY_AT_10_PERCENT = 10;
  private static final int SOLO_BATTERY_AT_25_PERCENT = 25;
  private static final int SOLO_BATTERY_AT_30_PERCENT = 30;
  private static final int SOLO_BATTERY_AT_3_PERCENT = 3;
  private static final String TAG = AlertsManagerFragment.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter();
  private ImageView alertIcon;
  private final View.OnClickListener alertMessageClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (AlertsManagerFragment.this.currentAlert == null);
      OnAlertClickListener localOnAlertClickListener;
      do
      {
        return;
        localOnAlertClickListener = AlertsManagerFragment.this.currentAlert.getOnClickListener();
      }
      while (localOnAlertClickListener == null);
      localOnAlertClickListener.onAlertClick(AlertsManagerFragment.this);
    }
  };
  private View alertMessageContainer;
  private final SparseBooleanArray alertsShown = new SparseBooleanArray();
  private AppAnalytics appAnalytics;
  private AppPreferences appPrefs;
  private final Runnable autoDismissAlert = new Runnable()
  {
    public void run()
    {
      AlertsManagerFragment.this.removeAlert();
    }
  };
  private ImageView closeButton;
  private AlertType currentAlert;
  private ViewPropertyAnimator currentAnim;
  private int errorBgColor;
  private final BroadcastReceiver eventReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Drone localDrone = AlertsManagerFragment.this.getDrone();
      if (localDrone == null);
      label766: 
      do
      {
        State localState;
        ArtooMessageInputReport localArtooMessageInputReport;
        do
        {
          byte[] arrayOfByte;
          do
          {
            do
            {
              ButtonPacket localButtonPacket;
              do
              {
                String str2;
                do
                {
                  do
                  {
                    return;
                    localState = (State)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
                    String str1 = paramAnonymousIntent.getAction();
                    int i = -1;
                    switch (str1.hashCode())
                    {
                    default:
                    case -1921525958:
                    case 465944229:
                    case 566338349:
                    case -286151170:
                    case -1549522365:
                    case 2120888156:
                    case 551871472:
                    case 662782932:
                    case 1256617868:
                    case -1467072149:
                    case 1962523320:
                    case 1035382905:
                    case -302519047:
                    case -508972501:
                    case 600585103:
                    case -1116774648:
                    case 1626503011:
                    case 2135209634:
                    case -821198533:
                    case 1165657982:
                    case -2037796754:
                    case 1010447515:
                    case 1138777058:
                    }
                    int n;
                    while (true)
                      switch (i)
                      {
                      default:
                        return;
                      case 0:
                        n = (int)((Battery)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.BATTERY")).getBatteryRemain();
                        if (!localState.isFlying())
                          break label833;
                        if (n > 3)
                          break label766;
                        AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_FLIGHT_BATTERY_3P);
                        return;
                        if (str1.equals("com.o3dr.services.android.lib.attribute.event.BATTERY_UPDATED"))
                        {
                          i = 0;
                          continue;
                          if (str1.equals("com.o3dr.services.android.lib.attribute.event.STATE_EKF_POSITION"))
                          {
                            i = 1;
                            continue;
                            if (str1.equals("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED"))
                            {
                              i = 2;
                              continue;
                              if (str1.equals("com.o3dr.services.android.lib.attribute.event.STATE_UPDATED"))
                              {
                                i = 3;
                                continue;
                                if (str1.equals("com.o3dr.services.android.lib.attribute.event.STATE_ARMING"))
                                {
                                  i = 4;
                                  continue;
                                  if (str1.equals("com.o3dr.solo.android.action.SHOT_ERROR"))
                                  {
                                    i = 5;
                                    continue;
                                    if (str1.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
                                    {
                                      i = 6;
                                      continue;
                                      if (str1.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
                                      {
                                        i = 7;
                                        continue;
                                        if (str1.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
                                        {
                                          i = 8;
                                          continue;
                                          if (str1.equals("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED"))
                                          {
                                            i = 9;
                                            continue;
                                            if (str1.equals("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED"))
                                            {
                                              i = 10;
                                              continue;
                                              if (str1.equals("com.o3dr.solo.android.action.SOLO_LINK_DISCONNECTED"))
                                              {
                                                i = 11;
                                                continue;
                                                if (str1.equals("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED"))
                                                {
                                                  i = 12;
                                                  continue;
                                                  if (str1.equals("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED"))
                                                  {
                                                    i = 13;
                                                    continue;
                                                    if (str1.equals("com.o3dr.services.android.lib.attribute.event.HEARTBEAT_TIMEOUT"))
                                                    {
                                                      i = 14;
                                                      continue;
                                                      if (str1.equals("com.o3dr.services.android.lib.attribute.event.HEARTBEAT_RESTORED"))
                                                      {
                                                        i = 15;
                                                        continue;
                                                        if (str1.equals("com.o3dr.services.android.lib.attribute.event.AUTOPILOT_ERROR"))
                                                        {
                                                          i = 16;
                                                          continue;
                                                          if (str1.equals("com.o3dr.services.android.lib.attribute.event.AUTOPILOT_MESSAGE"))
                                                          {
                                                            i = 17;
                                                            continue;
                                                            if (str1.equals("com.o3dr.solo.android.action.ACTION_BUTTON_PACKET_RECEIVED"))
                                                            {
                                                              i = 18;
                                                              continue;
                                                              if (str1.equals("com.o3dr.solo.android.action.TLV_PACKET_RECEIVED"))
                                                              {
                                                                i = 19;
                                                                continue;
                                                                if (str1.equals("com.o3dr.solo.android.action.FOLLOW_LOCATION_ACCURACY"))
                                                                {
                                                                  i = 20;
                                                                  continue;
                                                                  if (str1.equals("com.o3dr.solo.android.action.FOLLOW_LOCATION_UNAVAILABLE"))
                                                                  {
                                                                    i = 21;
                                                                    continue;
                                                                    if (str1.equals("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE"))
                                                                      i = 22;
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
                              }
                            }
                          }
                        }
                        break;
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
                      case 12:
                      case 13:
                      case 14:
                      case 15:
                      case 16:
                      case 17:
                      case 18:
                      case 19:
                      case 20:
                      case 21:
                      case 22:
                      }
                    if (n <= 10)
                    {
                      AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_FLIGHT_BATTERY_10P);
                      return;
                    }
                    if (n <= 25)
                    {
                      AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_FLIGHT_BATTERY_25P);
                      return;
                    }
                    AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_FLIGHT_BATTERY_3P);
                    AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_FLIGHT_BATTERY_10P);
                    AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_FLIGHT_BATTERY_25P);
                    return;
                    if (n <= 30)
                    {
                      AlertsManagerFragment.this.prepareAlert(AlertType.PREFLIGHT_BATTERY_TOO_LOW);
                      return;
                    }
                    AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_BATTERY_TOO_LOW);
                    return;
                    AlertsManagerFragment.this.checkEkfStatus(localDrone);
                    return;
                    double d = Utils.getAltitudeLimit(localDrone);
                    if (d >= 1.0D)
                    {
                      if (((Altitude)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE")).getAltitude() >= d)
                      {
                        AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_MAX_ALTITUDE);
                        return;
                      }
                      AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_MAX_ALTITUDE);
                      return;
                    }
                    AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_MAX_ALTITUDE);
                    return;
                  }
                  while (!localState.isFlying());
                  AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_READY_TO_TAKEOFF);
                  AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE);
                  return;
                  if (localState.isArmed())
                  {
                    if (localState.getEkfStatus().isPositionOk(true))
                    {
                      AlertsManagerFragment.this.prepareAlert(AlertType.PREFLIGHT_READY_TO_TAKEOFF);
                      return;
                    }
                    AlertsManagerFragment.this.prepareAlert(AlertType.PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE);
                    return;
                  }
                  AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_READY_TO_TAKEOFF);
                  AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE);
                  return;
                  switch (paramAnonymousIntent.getIntExtra("extra_shot_error_type", -1))
                  {
                  default:
                    return;
                  case 0:
                    AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_GPS_LOST);
                    return;
                  case 1:
                  }
                  AlertsManagerFragment.this.prepareAlert(AlertType.TAKEOFF_FOR_SHOT);
                  return;
                  AlertsManagerFragment.this.checkLinkConnection();
                  return;
                  AlertsManagerFragment.this.removeAlert(AlertType.POOR_CONNECTION_TO_SOLO, true);
                  return;
                  AlertsManagerFragment.this.prepareAlert(AlertType.POOR_CONNECTION_TO_SOLO);
                  return;
                  AlertsManagerFragment.this.removeAlert(AlertType.POOR_CONTROLLER_CONNECTION);
                  return;
                  AlertsManagerFragment.this.prepareAlert(AlertType.POOR_CONTROLLER_CONNECTION);
                  return;
                  AlertsManagerFragment.this.prepareAlert(AlertType.POOR_CONNECTION_TO_SOLO);
                  return;
                  AlertsManagerFragment.this.removeAlert(AlertType.POOR_CONNECTION_TO_SOLO, true);
                  return;
                  ErrorType localErrorType = ErrorType.getErrorById(paramAnonymousIntent.getStringExtra("com.o3dr.services.android.lib.attribute.event.extra.AUTOPILOT_ERROR_ID"));
                  AlertsManagerFragment.this.handleAutopilotError(localDrone, localErrorType);
                  return;
                  str2 = paramAnonymousIntent.getStringExtra("com.o3dr.services.android.lib.attribute.event.extra.AUTOPILOT_MESSAGE");
                }
                while (TextUtils.isEmpty(str2));
                if ("Waiting for Nav Checks".equalsIgnoreCase(str2))
                  AlertsManagerFragment.this.prepareAlert(AlertType.PREFLIGHT_CALIBRATING_SENSORS);
                while (true)
                {
                  Log.println(paramAnonymousIntent.getIntExtra("com.o3dr.services.android.lib.attribute.event.extra.AUTOPILOT_MESSAGE_LEVEL", 2), AlertsManagerFragment.TAG, str2);
                  return;
                  if (!localState.isArmed())
                    if ("READY TO ARM".equals(str2))
                    {
                      if (AlertsManagerFragment.this.currentAlert != AlertType.PREFLIGHT_READY_TO_ARM)
                      {
                        AlertsManagerFragment.this.removeAlert(AlertsManagerFragment.this.currentAlert);
                        AlertsManagerFragment.this.prepareAlert(AlertType.PREFLIGHT_READY_TO_ARM);
                      }
                    }
                    else if ("UNREADY FOR ARMING".equals(str2))
                      AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_READY_TO_ARM);
                }
                localButtonPacket = (ButtonPacket)paramAnonymousIntent.getParcelableExtra("extra_button_packet");
              }
              while (localButtonPacket == null);
              int k = localButtonPacket.getButtonId();
              int m = localButtonPacket.getEventType();
              switch (k)
              {
              default:
                return;
              case 1:
              }
              switch (m)
              {
              default:
                return;
              case 1:
              case 2:
              }
              AlertsManagerFragment.this.removeAlert(AlertType.PREFLIGHT_READY_TO_TAKEOFF);
              return;
            }
            while (paramAnonymousIntent.getIntExtra("extra_tlv_packet_type", -1) != 2003);
            arrayOfByte = paramAnonymousIntent.getByteArrayExtra("extra_tlv_packet_bytes");
          }
          while (arrayOfByte == null);
          localArtooMessageInputReport = (ArtooMessageInputReport)TLVMessageParser.parseTLVPacket(ByteBuffer.wrap(arrayOfByte));
        }
        while ((localArtooMessageInputReport == null) || (!localState.isFlying()));
        int j = localArtooMessageInputReport.getBattery();
        if (j <= 0)
        {
          if (localState.getEkfStatus().isPositionOk(true))
          {
            AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_CONTROL_BATTERY_0P_RETURNING);
            return;
          }
          AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_CONTROL_BATTERY_0P_LANDING);
          return;
        }
        if (j <= 5)
        {
          AlertsManagerFragment.this.prepareAlert(AlertType.INFLIGHT_CONTROL_BATTERY_5P);
          return;
        }
        AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_CONTROL_BATTERY_0P_LANDING);
        AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_CONTROL_BATTERY_0P_RETURNING);
        AlertsManagerFragment.this.removeAlert(AlertType.INFLIGHT_CONTROL_BATTERY_5P);
        return;
        if (paramAnonymousIntent.getBooleanExtra("extra_is_location_accurate", true))
        {
          AlertsManagerFragment.this.removeAlert(AlertType.FOLLOW_INACCURATE_LOCATION);
          return;
        }
        AlertsManagerFragment.this.prepareAlert(AlertType.FOLLOW_INACCURATE_LOCATION);
        return;
        AlertsManagerFragment.this.prepareAlert(AlertType.FOLLOW_UNAVAILABLE_LOCATION);
        return;
      }
      while (paramAnonymousIntent.getIntExtra("extra_shot_type", -1) == 5);
      label833: AlertsManagerFragment.this.removeAlert(AlertType.FOLLOW_INACCURATE_LOCATION);
    }
  };
  private final Handler handler = new Handler();
  private final Runnable hideAlertFinish = new Runnable()
  {
    public void run()
    {
      View localView = AlertsManagerFragment.this.getView();
      if (localView != null)
        localView.setVisibility(8);
    }
  };
  private int infoBgColor;
  private AlertsListener listener;
  private TextView message;
  private final Runnable showAlertStart = new Runnable()
  {
    public void run()
    {
      View localView = AlertsManagerFragment.this.getView();
      if (localView != null)
        localView.setVisibility(0);
    }
  };
  private TextView subMessage;
  private TextToSpeech tts;
  private final HashMap<String, String> ttsParams = new HashMap();
  private int warningBgColor;

  static
  {
    filter.addAction("com.o3dr.services.android.lib.attribute.event.BATTERY_UPDATED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_ARMING");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_UPDATED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.AUTOPILOT_ERROR");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.AUTOPILOT_MESSAGE");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_EKF_POSITION");
    filter.addAction("com.o3dr.solo.android.action.SHOT_ERROR");
    filter.addAction("com.o3dr.solo.android.action.TLV_PACKET_RECEIVED");
    filter.addAction("com.o3dr.solo.android.action.ACTION_BUTTON_PACKET_RECEIVED");
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.HEARTBEAT_RESTORED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.HEARTBEAT_TIMEOUT");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED");
    filter.addAction("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED");
    filter.addAction("com.o3dr.solo.android.action.SOLO_LINK_DISCONNECTED");
    filter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED");
    filter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_LOCATION_ACCURACY");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_LOCATION_UNAVAILABLE");
    filter.addAction("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE");
  }

  private void checkEkfStatus(Drone paramDrone)
  {
    State localState = (State)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
    if (localState == null)
      return;
    boolean bool = localState.isArmed();
    if (!localState.getEkfStatus().isPositionOk(bool))
    {
      if (localState.isFlying())
      {
        prepareAlert(AlertType.INFLIGHT_GPS_LOST);
        return;
      }
      if (bool)
      {
        prepareAlert(AlertType.PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE);
        return;
      }
      prepareAlert(AlertType.PREFLIGHT_WAITING_FOR_GPS);
      return;
    }
    if (localState.isFlying())
    {
      removeAlert(AlertType.INFLIGHT_GPS_LOST);
      prepareAlert(AlertType.INFLIGHT_GPS_LOCK);
      return;
    }
    if (bool)
    {
      removeAlert(AlertType.PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE);
      prepareAlert(AlertType.PREFLIGHT_READY_TO_TAKEOFF);
      return;
    }
    removeAlert(AlertType.PREFLIGHT_WAITING_FOR_GPS);
    removeAlert(AlertType.PREFLIGHT_CALIBRATING_SENSORS);
  }

  private void checkLinkConnection()
  {
    if (isLinkConnected())
    {
      removeAlert(AlertType.POOR_CONNECTION_TO_SOLO, true);
      removeAlert(AlertType.POOR_CONTROLLER_CONNECTION);
    }
    do
    {
      return;
      if (!isArtooLinkConnected())
      {
        prepareAlert(AlertType.POOR_CONTROLLER_CONNECTION);
        return;
      }
    }
    while ((isDroneConnected()) && (isSoloLinkConnected()));
    prepareAlert(AlertType.POOR_CONNECTION_TO_SOLO);
  }

  private void handleAutopilotError(Drone paramDrone, ErrorType paramErrorType)
  {
    if (paramErrorType == null);
    do
    {
      do
      {
        State localState;
        do
        {
          return;
          localState = (State)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
          Log.i(TAG, "Autopilot error: " + paramErrorType);
          switch (7.$SwitchMap$com$o3dr$services$android$lib$drone$attribute$error$ErrorType[paramErrorType.ordinal()])
          {
          default:
            return;
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
            prepareAlert(AlertType.PREFLIGHT_SOLO_NEEDS_SERVICE);
            return;
          case 7:
          case 8:
            prepareAlert(AlertType.PREFLIGHT_SOLO_COMPASS_CALIBRATION);
            return;
          case 9:
          case 10:
            prepareAlert(AlertType.PREFLIGHT_MAG_INTERFERENCE);
            return;
          case 11:
            prepareAlert(AlertType.PREFLIGHT_CALIBRATION_FAILED);
            return;
          case 12:
          case 13:
            prepareAlert(AlertType.PREFLIGHT_SOLO_LEVEL_CALIBRATION);
            return;
          case 14:
            prepareAlert(AlertType.PREFLIGHT_CALIBRATING_COMPASS);
            return;
          case 15:
            prepareAlert(AlertType.PREFLIGHT_THROTTLE_TOO_HIGH);
            return;
          case 16:
            if (localState.getVehicleMode() != VehicleMode.COPTER_LOITER)
            {
              prepareAlert(AlertType.PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE);
              return;
            }
            break;
          case 17:
          case 18:
          case 19:
          case 20:
          case 21:
          case 22:
          case 23:
          case 24:
          }
        }
        while (localState.isArmed());
        prepareAlert(AlertType.PREFLIGHT_WAITING_FOR_GPS);
        return;
        prepareAlert(AlertType.PREFLIGHT_SOLO_MUST_BE_LEVEL);
        return;
        prepareAlert(AlertType.PREFLIGHT_CRASH_DETECTED);
        return;
        if (localState.getEkfStatus().isPositionOk(localState.isArmed()))
        {
          prepareAlert(AlertType.INFLIGHT_CONTROLLER_SIGNAL_LOST);
          return;
        }
        prepareAlert(AlertType.INFLIGHT_CONTROLLER_SIGNAL_LOST_NO_GPS);
        return;
      }
      while (this.currentAlert == null);
      if ((this.currentAlert == AlertType.INFLIGHT_CONTROLLER_SIGNAL_LOST) || (this.currentAlert == AlertType.INFLIGHT_CONTROLLER_SIGNAL_LOST_NO_GPS))
      {
        removeAlert(this.currentAlert);
        prepareAlert(AlertType.INFLIGHT_CONTROLLER_SIGNAL_RECOVERY);
        return;
      }
    }
    while (this.currentAlert.getOrigin() != 0);
    removeAlert(this.currentAlert);
  }

  private void hideAlert()
  {
    View localView = getView();
    if (localView != null)
    {
      if (this.currentAnim != null)
        this.currentAnim.cancel();
      this.currentAnim = localView.animate().translationYBy(localView.getHeight()).withEndAction(this.hideAlertFinish);
      this.currentAnim.start();
    }
  }

  private void prepareAlert(int paramInt1, String paramString1, String paramString2, @DrawableRes int paramInt2, boolean paramBoolean1, boolean paramBoolean2, OnAlertClickListener paramOnAlertClickListener, int paramInt3, boolean paramBoolean3)
  {
    resetAlert();
    if (!TextUtils.isEmpty(paramString1))
      this.message.setText(paramString1);
    if (!TextUtils.isEmpty(paramString2))
    {
      this.subMessage.setText(paramString2);
      this.subMessage.setVisibility(0);
    }
    this.alertIcon.setImageResource(paramInt2);
    switch (paramInt1)
    {
    default:
      this.alertIcon.setBackgroundResource(0);
      if (paramBoolean1)
      {
        this.closeButton.setVisibility(0);
        label101: if (paramBoolean2)
          this.handler.postDelayed(this.autoDismissAlert, 5000L);
        if (paramOnAlertClickListener != null)
          break label239;
        this.alertMessageContainer.setOnClickListener(null);
      }
      break;
    case 3:
    case 2:
    case 1:
    }
    while (true)
    {
      if ((paramBoolean3) && (this.listener != null))
        this.listener.onAlertShown(null);
      speak(paramString1);
      playSound(paramInt3);
      showAlert();
      this.appAnalytics.track("Alert Shown", "Alert String", paramString1);
      return;
      this.alertIcon.setBackgroundColor(this.errorBgColor);
      break;
      this.alertIcon.setBackgroundColor(this.warningBgColor);
      break;
      this.alertIcon.setBackgroundColor(this.infoBgColor);
      break;
      this.closeButton.setVisibility(4);
      break label101;
      label239: this.alertMessageContainer.setOnClickListener(this.alertMessageClickListener);
    }
  }

  private void prepareAlert(AlertType paramAlertType)
  {
    if (paramAlertType == null)
      break label4;
    label4: 
    while (((paramAlertType.isShownOnce()) && (this.alertsShown.get(paramAlertType.ordinal()))) || ((this.currentAlert != null) && ((this.currentAlert == paramAlertType) || (this.currentAlert.getPriority() > paramAlertType.getPriority()))))
      return;
    int i = paramAlertType.getMessageResId();
    String str1;
    label68: int j;
    if (i == 0)
    {
      str1 = "";
      j = paramAlertType.getSubMessageResId();
      if (j != 0)
        break label163;
    }
    label163: for (String str2 = ""; ; str2 = getString(j))
    {
      prepareAlert(paramAlertType.getPriority(), str1, str2, paramAlertType.getIconResId(), paramAlertType.canDismiss(), paramAlertType.isAutoDismiss(), paramAlertType.getOnClickListener(), paramAlertType.getSoundType(), false);
      this.currentAlert = paramAlertType;
      this.alertsShown.put(paramAlertType.ordinal(), true);
      if (this.listener == null)
        break;
      this.listener.onAlertShown(this.currentAlert);
      return;
      str1 = getString(i);
      break label68;
    }
  }

  private void removeAlert()
  {
    if (this.listener != null)
      this.listener.onAlertHidden(this.currentAlert);
    this.appAnalytics.track("Alert Dismissed", "Alert String", this.message.getText());
    resetAlert();
    hideAlert();
  }

  private void removeAlert(AlertType paramAlertType)
  {
    if (paramAlertType == null);
    while (this.currentAlert != paramAlertType)
      return;
    removeAlert();
  }

  private void removeAlert(AlertType paramAlertType, boolean paramBoolean)
  {
    removeAlert(paramAlertType);
    if (paramBoolean)
      this.alertsShown.put(paramAlertType.ordinal(), false);
  }

  private void resetAlert()
  {
    this.handler.removeCallbacks(this.autoDismissAlert);
    this.message.setText("");
    this.subMessage.setText("");
    this.subMessage.setVisibility(8);
    this.alertIcon.setImageResource(0);
    this.alertIcon.setBackgroundResource(0);
    this.currentAlert = null;
  }

  private void showAlert()
  {
    View localView = getView();
    if (localView == null)
      return;
    if (this.currentAnim != null)
      this.currentAnim.cancel();
    this.currentAnim = localView.animate().translationY(0.0F).withStartAction(this.showAlertStart);
    this.currentAnim.start();
  }

  private void speak(String paramString)
  {
    speak(paramString, false, null);
  }

  private void speak(String paramString1, boolean paramBoolean, String paramString2)
  {
    if ((this.tts != null) && (this.appPrefs.isVoiceAlertsEnabled()))
      if (!paramBoolean)
        break label93;
    label93: for (int i = 1; ; i = 0)
    {
      this.ttsParams.clear();
      if (paramString2 != null)
        this.ttsParams.put("utteranceId", paramString2);
      float f = this.appPrefs.getVoiceAlertsVolume() / 100.0F;
      this.ttsParams.put("volume", Float.toString(f));
      this.tts.speak(paramString1, i, this.ttsParams);
      return;
    }
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof AlertsListener))
      throw new IllegalStateException("Parent activity must implement " + AlertsListener.class.getSimpleName());
    this.listener = ((AlertsListener)paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
    Context localContext = getContext();
    this.tts = new TextToSpeech(localContext, this);
    this.appPrefs = new AppPreferences(localContext);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903088, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    this.tts.shutdown();
    this.tts = null;
  }

  public void onDetach()
  {
    super.onDetach();
    this.listener = null;
  }

  protected void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    if (paramDrone == null)
      return;
    checkEkfStatus(paramDrone);
    checkLinkConnection();
    handleAutopilotError(paramDrone, ErrorType.getErrorById(((State)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE")).getAutopilotErrorId()));
  }

  @SuppressLint({"NewApi"})
  public void onInit(int paramInt)
  {
    Locale localLocale;
    if (paramInt == 0)
    {
      Log.v(TAG, "Text to speech initialized.");
      localLocale = this.tts.getDefaultLanguage();
      if ((localLocale == null) || (this.tts.isLanguageAvailable(localLocale) == -2))
      {
        ArrayList localArrayList = new ArrayList(this.tts.getAvailableLanguages());
        if (!localArrayList.isEmpty())
          localLocale = (Locale)localArrayList.get(0);
      }
      else
      {
        if (this.tts.isLanguageAvailable(localLocale) == -1)
          startActivity(new Intent("android.speech.tts.engine.INSTALL_TTS_DATA"));
        switch (this.tts.setLanguage(localLocale))
        {
        default:
        case -2:
        case -1:
        }
      }
    }
    Context localContext1;
    do
    {
      Context localContext2;
      do
      {
        return;
        localLocale = Locale.US;
        break;
        this.tts.shutdown();
        this.tts = null;
        Log.w(TAG, "TTS Language data is not available.");
        localContext2 = getContext();
      }
      while (localContext2 == null);
      Toast.makeText(localContext2, 2131100047, 1).show();
      return;
      Log.w(TAG, "Unable to initialize text to speech.");
      localContext1 = getContext();
    }
    while (localContext1 == null);
    Toast.makeText(localContext1, 2131100047, 1).show();
  }

  public void onStart()
  {
    super.onStart();
    getBroadcastManager().registerReceiver(this.eventReceiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.eventReceiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appAnalytics = getApplication().getAppAnalytics();
    Resources localResources = getResources();
    this.errorBgColor = localResources.getColor(2131427380);
    this.warningBgColor = localResources.getColor(2131427397);
    this.infoBgColor = localResources.getColor(2131427385);
    this.alertMessageContainer = paramView.findViewById(2131493088);
    this.message = ((TextView)paramView.findViewById(2131493089));
    this.subMessage = ((TextView)paramView.findViewById(2131493090));
    this.alertIcon = ((ImageView)paramView.findViewById(2131493086));
    this.closeButton = ((ImageView)paramView.findViewById(2131493087));
    this.closeButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AlertsManagerFragment.this.removeAlert();
      }
    });
    paramView.setVisibility(8);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.alerts.AlertsManagerFragment
 * JD-Core Version:    0.6.2
 */