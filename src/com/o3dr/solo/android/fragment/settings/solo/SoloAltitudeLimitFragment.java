package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.apis.drone.ParameterApi;
import com.o3dr.services.android.lib.drone.property.Parameter;
import com.o3dr.services.android.lib.drone.property.Parameters;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.Utils;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;
import com.o3dr.solo.android.widget.spinnerWheel.EditWheelHorizontalView;
import com.o3dr.solo.android.widget.spinnerWheel.EditWheelHorizontalView.OnCardWheelScrollListener;
import com.o3dr.solo.android.widget.spinnerWheel.adapters.LengthWheelAdapter;
import java.util.ArrayList;
import java.util.List;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.length.LengthUnit;

public class SoloAltitudeLimitFragment extends SettingsDetailFragment
  implements EditWheelHorizontalView.OnCardWheelScrollListener<LengthUnit>
{
  private static final String[] ALTITUDE_LIMIT_PARAMS = { "FENCE_ENABLE", "FENCE_TYPE", "FENCE_ACTION", "FENCE_ALT_MAX" };
  private static final int ALTITUDE_LIMIT_PARAM_TYPE = 9;
  private static final String ALTITUDE_LIMIT_SET = "Altitude Limit Set";
  private static final int ALTITUDE_LIMIT_VALUE_INDEX = 3;
  private static final int MAX_ALTITUDE = 1000;
  public static final int MIN_ALTITUDE;
  private static final IntentFilter filter = new IntentFilter();
  private EditWheelHorizontalView<LengthUnit> altitudeLimitPicker;
  private Button applyButton;
  private float currentMaxAltValue;
  private float newMaxAltAltValue;
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
      do
      {
        return;
        if (!str1.equals("com.o3dr.services.android.lib.attribute.event.PARAMETERS_RECEIVED"))
          break;
        i = 0;
        break;
        str2 = paramAnonymousIntent.getStringExtra("com.o3dr.services.android.lib.attribute.event.extra.PARAMETER_NAME");
      }
      while (!SoloAltitudeLimitFragment.getAltitudeLimitParameterName().equals(str2));
      double d = paramAnonymousIntent.getDoubleExtra("com.o3dr.services.android.lib.attribute.event.extra.PARAMETER_VALUE", 0.0D);
      SoloAltitudeLimitFragment.this.loadAltitudeLimitParameters(d);
    }
  };

  static
  {
    filter.addAction("com.o3dr.services.android.lib.attribute.event.PARAMETERS_RECEIVED");
  }

  private void disableButton()
  {
    if (this.applyButton != null)
    {
      this.applyButton.setEnabled(false);
      this.applyButton.setText(2131099846);
    }
  }

  private void enableButton()
  {
    if (this.applyButton != null)
    {
      this.applyButton.setEnabled(true);
      this.applyButton.setText(2131099847);
    }
  }

  public static String getAltitudeLimitParameterName()
  {
    return ALTITUDE_LIMIT_PARAMS[3];
  }

  private void loadAltitudeLimitParameters(double paramDouble)
  {
    LengthUnitProvider localLengthUnitProvider = getLengthUnitProvider();
    this.altitudeLimitPicker.setCurrentValue(localLengthUnitProvider.boxBaseValueToTarget(paramDouble));
    this.currentMaxAltValue = ((float)paramDouble);
  }

  private static void updateAltitudeLimitParameters(Drone paramDrone, float paramFloat)
  {
    if (paramDrone == null)
      return;
    ArrayList localArrayList = new ArrayList();
    if (paramFloat > 0.0F)
    {
      localArrayList.add(new Parameter(ALTITUDE_LIMIT_PARAMS[0], 1.0D, 9));
      localArrayList.add(new Parameter(ALTITUDE_LIMIT_PARAMS[1], 1.0D, 9));
      localArrayList.add(new Parameter(ALTITUDE_LIMIT_PARAMS[2], 1.0D, 9));
      localArrayList.add(new Parameter(ALTITUDE_LIMIT_PARAMS[3], paramFloat, 9));
    }
    while (true)
    {
      ParameterApi.writeParameters(paramDrone, new Parameters(localArrayList));
      return;
      localArrayList.add(new Parameter(ALTITUDE_LIMIT_PARAMS[0], 0.0D, 9));
      localArrayList.add(new Parameter(ALTITUDE_LIMIT_PARAMS[1], 0.0D, 9));
    }
  }

  public int getSettingDetailTitle()
  {
    return 2131099934;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903111, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    loadAltitudeLimitParameters(Utils.getAltitudeLimit(paramDrone));
  }

  public void onScrollingEnded(EditWheelHorizontalView paramEditWheelHorizontalView, LengthUnit paramLengthUnit1, LengthUnit paramLengthUnit2)
  {
    float f = (float)paramLengthUnit2.toBase().getValue();
    if (this.currentMaxAltValue != f)
    {
      this.newMaxAltAltValue = f;
      enableButton();
      return;
    }
    this.newMaxAltAltValue = this.currentMaxAltValue;
    disableButton();
  }

  public void onScrollingStarted(EditWheelHorizontalView paramEditWheelHorizontalView, LengthUnit paramLengthUnit)
  {
  }

  public void onScrollingUpdate(EditWheelHorizontalView paramEditWheelHorizontalView, LengthUnit paramLengthUnit1, LengthUnit paramLengthUnit2)
  {
  }

  public void onStart()
  {
    super.onStart();
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    LengthUnitProvider localLengthUnitProvider = getLengthUnitProvider();
    final AppAnalytics localAppAnalytics = getApplication().getAppAnalytics();
    LengthWheelAdapter localLengthWheelAdapter = new LengthWheelAdapter(getContext(), 2130903140, 2131493371, localLengthUnitProvider.boxBaseValueToTarget(0.0D), localLengthUnitProvider.boxBaseValueToTarget(1000.0D));
    this.altitudeLimitPicker = ((EditWheelHorizontalView)paramView.findViewById(2131493257));
    this.altitudeLimitPicker.setViewAdapter(localLengthWheelAdapter);
    this.altitudeLimitPicker.addScrollListener(this);
    this.applyButton = ((Button)paramView.findViewById(2131493258));
    this.applyButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localAppAnalytics.track("Settings", "Altitude Limit Set", Float.valueOf(SoloAltitudeLimitFragment.this.newMaxAltAltValue));
        SoloAltitudeLimitFragment.updateAltitudeLimitParameters(SoloAltitudeLimitFragment.this.getDrone(), SoloAltitudeLimitFragment.this.newMaxAltAltValue);
        SoloAltitudeLimitFragment.this.disableButton();
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloAltitudeLimitFragment
 * JD-Core Version:    0.6.2
 */