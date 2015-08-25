package com.o3dr.solo.android.fragment.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.o3dr.solo.android.fragment.settings.solo.SoloSettingsMasterFragment;
import com.o3dr.solo.android.widget.SlidePaneLayout;
import com.o3dr.solo.android.widget.SlidePaneLayout.PanelSlideListener;

public abstract class SubSettingsFragment extends SettingsDetailFragment
  implements SlidePaneLayout.PanelSlideListener, SettingsMasterFragment.SettingsMasterListener
{
  private static final boolean DEFAULT_OPEN_DETAIL_PANE = false;
  private static final int DEFAULT_SELECTED_PANE_ID = -1;
  public static final String EXTRA_SUB_SETTINGS_OPEN_DETAIL_PANE = "extra_sub_settings_open_detail_pane";
  public static final String EXTRA_SUB_SETTINGS_SELECTED_PANE_ID = "extra_sub_settings_selected_pane_id";
  private DetailPaneHolder detailPaneHolder;
  private int selectedSubPaneId = -1;
  private SettingsDetailFragment subDetailFrag;
  private SoloSettingsMasterFragment subMasterFrag;
  private SlidePaneLayout subSlidingPane;

  private void selectDetailPane(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    FragmentTransaction localFragmentTransaction = getChildFragmentManager().beginTransaction();
    if ((this.selectedSubPaneId != paramInt) || (this.subDetailFrag == null))
    {
      this.subDetailFrag = getDetailFragmentForPaneId(paramInt);
      if (this.subDetailFrag != null);
    }
    while (true)
    {
      return;
      localFragmentTransaction.replace(getSubDetailFragmentId(), this.subDetailFrag).commit();
      this.selectedSubPaneId = paramInt;
      if (paramBoolean1)
        this.subMasterFrag.selectPane(paramInt);
      while (paramBoolean2)
      {
        this.subSlidingPane.closePane();
        return;
        localFragmentTransaction.show(this.subDetailFrag).commit();
        updateTitle(this.subDetailFrag.getSettingDetailTitle());
      }
    }
  }

  protected void enableSettingsPane(int paramInt, boolean paramBoolean)
  {
    this.subMasterFrag.enablePane(paramInt, paramBoolean);
    if ((!paramBoolean) && (this.selectedSubPaneId == paramInt) && (!this.subSlidingPane.isOpen()))
      this.subSlidingPane.openPane();
  }

  protected SlidePaneLayout getContainerSlidePane()
  {
    if (this.detailPaneHolder == null)
      return null;
    return this.detailPaneHolder.getContainerSlidePane();
  }

  protected abstract SettingsDetailFragment getDetailFragmentForPaneId(int paramInt);

  protected abstract int getSubDetailFragmentId();

  protected abstract int getSubMasterFragmentId();

  protected abstract int getSubSlidePaneId();

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof DetailPaneHolder))
      throw new IllegalStateException("Parent activity must implement " + DetailPaneHolder.class.getName());
    this.detailPaneHolder = ((DetailPaneHolder)paramActivity);
  }

  public boolean onBackPressed()
  {
    if (this.subSlidingPane.isOpen())
      return false;
    this.subSlidingPane.openPane();
    return true;
  }

  public void onDetach()
  {
    super.onDetach();
    this.detailPaneHolder = null;
  }

  public void onDetailPaneClicked(int paramInt)
  {
    selectDetailPane(paramInt, true, true);
  }

  public void onPanelClosed(View paramView)
  {
    SlidePaneLayout localSlidePaneLayout = getContainerSlidePane();
    if (localSlidePaneLayout != null)
      localSlidePaneLayout.disallowInterceptTouchEvent(true);
  }

  public void onPanelOpened(View paramView)
  {
    SlidePaneLayout localSlidePaneLayout = getContainerSlidePane();
    if (localSlidePaneLayout != null)
      localSlidePaneLayout.disallowInterceptTouchEvent(false);
    updateTitle(getSettingDetailTitle());
    if (this.subDetailFrag != null)
      getChildFragmentManager().beginTransaction().hide(this.subDetailFrag).commit();
  }

  public void onPanelSlide(View paramView, float paramFloat)
  {
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("extra_sub_settings_selected_pane_id", this.selectedSubPaneId);
    if (!this.subSlidingPane.isOpen());
    for (boolean bool = true; ; bool = false)
    {
      paramBundle.putBoolean("extra_sub_settings_open_detail_pane", bool);
      return;
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.subSlidingPane = ((SlidePaneLayout)paramView.findViewById(getSubSlidePaneId()));
    this.subSlidingPane.setPanelSlideListener(this);
    boolean bool;
    int i;
    if (paramBundle != null)
    {
      bool = paramBundle.getBoolean("extra_sub_settings_selected_pane_id", false);
      i = paramBundle.getInt("extra_sub_settings_selected_pane_id", -1);
    }
    while (true)
    {
      Bundle localBundle1 = getArguments();
      if (localBundle1 != null)
      {
        i = localBundle1.getInt("extra_sub_settings_selected_pane_id", i);
        bool = localBundle1.getBoolean("extra_sub_settings_open_detail_pane", bool);
      }
      FragmentManager localFragmentManager = getChildFragmentManager();
      this.subMasterFrag = ((SoloSettingsMasterFragment)localFragmentManager.findFragmentById(getSubMasterFragmentId()));
      if (this.subMasterFrag == null)
      {
        Bundle localBundle2 = new Bundle(1);
        localBundle2.putInt("extra_sub_settings_selected_pane_id", i);
        this.subMasterFrag = new SoloSettingsMasterFragment();
        this.subMasterFrag.setArguments(localBundle2);
        localFragmentManager.beginTransaction().add(getSubMasterFragmentId(), this.subMasterFrag).commit();
      }
      selectDetailPane(i, false, false);
      if (!bool)
        break;
      this.subSlidingPane.closePane();
      return;
      i = -1;
      bool = false;
    }
    this.subSlidingPane.openPane();
  }

  public static abstract interface DetailPaneHolder
  {
    public abstract SlidePaneLayout getContainerSlidePane();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.SubSettingsFragment
 * JD-Core Version:    0.6.2
 */