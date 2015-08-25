package com.o3dr.solo.android.fragment.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.util.AppPreferences;

public abstract class SettingsMasterFragment extends BaseFragment
{
  protected AppPreferences appPrefs;
  private SettingsMasterListener listener;
  protected View selectedPane;
  protected final View.OnClickListener settingsPaneClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (SettingsMasterFragment.this.listener != null)
        SettingsMasterFragment.this.listener.onDetailPaneClicked(paramAnonymousView.getId());
    }
  };

  public void enablePane(int paramInt, boolean paramBoolean)
  {
    View localView = getViewForPaneId(paramInt);
    if (localView != null)
      localView.setEnabled(paramBoolean);
  }

  protected abstract View getViewForPaneId(int paramInt);

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    Fragment localFragment = getParentFragment();
    if (localFragment != null);
    for (Object localObject = localFragment; !(localObject instanceof SettingsMasterListener); localObject = paramActivity)
      throw new IllegalStateException("Parent activity must be an instance of " + SettingsMasterListener.class.getName());
    this.listener = ((SettingsMasterListener)localObject);
  }

  public void onDetach()
  {
    super.onDetach();
    this.listener = null;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appPrefs = new AppPreferences(getContext());
  }

  public void selectPane(int paramInt)
  {
    if (this.selectedPane != null)
    {
      this.selectedPane.setActivated(false);
      this.selectedPane = null;
    }
    this.selectedPane = getViewForPaneId(paramInt);
    if (this.selectedPane != null)
      this.selectedPane.setActivated(true);
  }

  public static abstract interface SettingsMasterListener
  {
    public abstract void onDetailPaneClicked(int paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.SettingsMasterFragment
 * JD-Core Version:    0.6.2
 */