package com.o3dr.solo.android.fragment.settings.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;

public class UnitsFragment extends SettingsDetailFragment
{
  private static final String IMPERIAL = "Imperial";
  private static final String METRIC = "Metric";
  private static final String SYSTEM = "System";

  public int getSettingDetailTitle()
  {
    return 2131099921;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903102, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    final AppPreferences localAppPreferences = new AppPreferences(getContext());
    final AppAnalytics localAppAnalytics = getApplication().getAppAnalytics();
    RadioGroup localRadioGroup = (RadioGroup)paramView.findViewById(2131493206);
    localRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return;
        case 2131493207:
          localAppAnalytics.track("Settings", "System", "Metric");
          localAppPreferences.setUnitSystemType(1);
          return;
        case 2131493208:
        }
        localAppAnalytics.track("Settings", "System", "Imperial");
        localAppPreferences.setUnitSystemType(2);
      }
    });
    if (localAppPreferences.getUnitSystemType() == 1)
    {
      localRadioGroup.check(2131493207);
      return;
    }
    localRadioGroup.check(2131493208);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.UnitsFragment
 * JD-Core Version:    0.6.2
 */