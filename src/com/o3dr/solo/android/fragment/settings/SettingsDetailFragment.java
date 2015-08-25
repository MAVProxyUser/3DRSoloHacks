package com.o3dr.solo.android.fragment.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.o3dr.solo.android.fragment.TitleUpdater;
import com.o3dr.solo.android.fragment.base.BaseFragment;

public abstract class SettingsDetailFragment extends BaseFragment
{
  private TitleUpdater titleUpdater;

  public abstract int getSettingDetailTitle();

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof TitleUpdater))
      throw new IllegalStateException("Parent activity must implement " + TitleUpdater.class.getName());
    this.titleUpdater = ((TitleUpdater)paramActivity);
  }

  public boolean onBackPressed()
  {
    return false;
  }

  public void onDetach()
  {
    super.onDetach();
    this.titleUpdater = null;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    updateTitle(getSettingDetailTitle());
  }

  protected void updateTitle(int paramInt)
  {
    if (this.titleUpdater != null)
      this.titleUpdater.updateTitle(paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.SettingsDetailFragment
 * JD-Core Version:    0.6.2
 */