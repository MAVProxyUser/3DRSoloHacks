package com.o3dr.solo.android.fragment.shotTutorial;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.o3dr.solo.android.fragment.base.BaseFragment;

public class ShotTutorialPageFragment extends BaseFragment
{
  public static final String EXTRA_IMAGE_RESOURCE_ID = "extra_image_resource_id";
  public static final String EXTRA_TITLE_RESOURCE_ID = "extra_title_resource_id";

  public static ShotTutorialPageFragment newInstance(@DrawableRes int paramInt1, @StringRes int paramInt2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("extra_image_resource_id", paramInt1);
    localBundle.putInt("extra_title_resource_id", paramInt2);
    ShotTutorialPageFragment localShotTutorialPageFragment = new ShotTutorialPageFragment();
    localShotTutorialPageFragment.setArguments(localBundle);
    return localShotTutorialPageFragment;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903106, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    ImageView localImageView = (ImageView)paramView.findViewById(2131493231);
    TextView localTextView = (TextView)paramView.findViewById(2131493232);
    if (localBundle != null)
    {
      localImageView.setImageResource(localBundle.getInt("extra_image_resource_id"));
      localTextView.setText(localBundle.getInt("extra_title_resource_id"));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shotTutorial.ShotTutorialPageFragment
 * JD-Core Version:    0.6.2
 */