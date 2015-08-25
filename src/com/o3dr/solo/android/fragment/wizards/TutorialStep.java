package com.o3dr.solo.android.fragment.wizards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.o3dr.solo.android.fragment.base.BaseFragment;

public class TutorialStep extends BaseFragment
{
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903121, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    TextView localTextView1 = (TextView)paramView.findViewById(2131493343);
    TextView localTextView2 = (TextView)paramView.findViewById(2131493344);
    if (localBundle != null)
    {
      localTextView1.setText(localBundle.getString("wizard_title_key"));
      localTextView2.setText(localBundle.getString("wizard_subtitle_key"));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.wizards.TutorialStep
 * JD-Core Version:    0.6.2
 */