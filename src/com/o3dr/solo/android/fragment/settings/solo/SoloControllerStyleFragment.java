package com.o3dr.solo.android.fragment.settings.solo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppPreferences;

public class SoloControllerStyleFragment extends SettingsDetailFragment
{
  public static final int MODE_1 = 1;
  public static final int MODE_2 = 2;

  public int getSettingDetailTitle()
  {
    return 2131099936;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903113, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    final AppPreferences localAppPreferences = new AppPreferences(getContext());
    RadioGroup localRadioGroup = (RadioGroup)paramView.findViewById(2131493259);
    localRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return;
        case 2131493260:
          localAppPreferences.setControllerStyle(1);
          return;
        case 2131493261:
        }
        localAppPreferences.setControllerStyle(2);
      }
    });
    if (localAppPreferences.getControllerStyle() == 1)
    {
      localRadioGroup.check(2131493260);
      return;
    }
    localRadioGroup.check(2131493261);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloControllerStyleFragment
 * JD-Core Version:    0.6.2
 */