package com.o3dr.solo.android.fragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.base.BaseDialogFragment;
import com.o3dr.solo.android.fragment.shotTutorial.ShotTutorialPageFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.List;

public class ShotTutorialFragment extends BaseDialogFragment
{
  public static final String EXTRA_SHOT_TUTORIAL_TYPE = "extra_shot_tutorial_type";
  private static final String TAG = ShotTutorialFragment.class.getSimpleName();
  private AppAnalytics appAnalytics;
  private final List<ShotTutorialPageFragment> pages = new ArrayList();
  private TextView title;

  private void setupPageContents(int paramInt)
  {
    if (this.title != null);
    switch (paramInt)
    {
    case 3:
    case 4:
    default:
      this.title.setText(2131100000);
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837716, 2131099996));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837717, 2131099999));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837718, 2131099999));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837719, 2131099998));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837720, 2131099997));
      return;
    case 2:
      this.title.setText(2131099995);
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837626, 2131099992));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837627, 2131099993));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837628, 2131099994));
      return;
    case 0:
      this.title.setText(2131100013);
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837852, 2131100010));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837853, 2131100011));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837854, 2131100012));
      return;
    case 1:
      this.title.setText(2131100009);
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837808, 2131100005));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837809, 2131100007));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837810, 2131100008));
      this.pages.add(ShotTutorialPageFragment.newInstance(2130837811, 2131100006));
      return;
    case 5:
    }
    this.title.setText(2131100004);
    this.pages.add(ShotTutorialPageFragment.newInstance(2130837727, 2131100001));
    this.pages.add(ShotTutorialPageFragment.newInstance(2130837728, 2131100007));
    this.pages.add(ShotTutorialPageFragment.newInstance(2130837729, 2131100003));
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903107, paramViewGroup, false);
  }

  public void onStart()
  {
    super.onStart();
    this.appAnalytics.track("Shot Tutorial Started");
    Dialog localDialog = getDialog();
    if (localDialog != null)
    {
      localDialog.setCanceledOnTouchOutside(true);
      localDialog.getWindow().setLayout((int)getResources().getDimension(2131165200), -2);
    }
  }

  public void onStop()
  {
    super.onStop();
    this.appAnalytics.track("Shot Tutorial Completed");
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    this.title = ((TextView)paramView.findViewById(2131493233));
    this.appAnalytics = getApplication().getAppAnalytics();
    setupPageContents(localBundle.getInt("extra_shot_tutorial_type"));
    FragmentManager localFragmentManager = getChildFragmentManager();
    CirclePageIndicator localCirclePageIndicator = (CirclePageIndicator)paramView.findViewById(2131493235);
    ViewPager localViewPager = (ViewPager)paramView.findViewById(2131493234);
    localViewPager.setAdapter(new ShotTutorialAdapter(localFragmentManager));
    localCirclePageIndicator.setViewPager(localViewPager);
  }

  public class ShotTutorialAdapter extends FragmentStatePagerAdapter
  {
    public ShotTutorialAdapter(FragmentManager arg2)
    {
      super();
    }

    public int getCount()
    {
      return ShotTutorialFragment.this.pages.size();
    }

    public Fragment getItem(int paramInt)
    {
      return (Fragment)ShotTutorialFragment.this.pages.get(paramInt);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.ShotTutorialFragment
 * JD-Core Version:    0.6.2
 */