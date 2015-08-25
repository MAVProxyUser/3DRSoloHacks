package com.o3dr.solo.android.fragment.shots.settings;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.o3dr.solo.android.fragment.ShotTutorialFragment;
import com.o3dr.solo.android.fragment.base.BaseDialogFragment;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;

public class ShotSettingsDialog extends BaseDialogFragment
{
  private static final String DIALOG_TAG = "Shot tutorial";
  public static final String EXTRA_SHOT_TYPE = "extra_shot_type";
  private static final String TAG = ShotSettingsDialog.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE");
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
        switch (i)
        {
        default:
        case 0:
        }
        break;
      case 1138777058:
      }
      do
      {
        return;
        if (!str.equals("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE"))
          break;
        i = 0;
        break;
      }
      while (paramAnonymousIntent.getIntExtra("extra_shot_type", ShotSettingsDialog.this.shotType) == ShotSettingsDialog.this.shotType);
      ShotSettingsDialog.this.dismiss();
    }
  };
  private int shotType;

  private Fragment getSettingsContent()
  {
    switch (this.shotType)
    {
    case 3:
    case 4:
    default:
      dismiss();
      return null;
    case 2:
      return new CableCamSettings();
    case 1:
    case 5:
      OrbitSettings localOrbitSettings = new OrbitSettings();
      localOrbitSettings.setArguments(getArguments());
      return localOrbitSettings;
    case 0:
    }
    return new SelfieSettings();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903105, paramViewGroup, false);
  }

  public void onStart()
  {
    super.onStart();
    getDialog().setCanceledOnTouchOutside(true);
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
    Bundle localBundle = getArguments();
    if ((localBundle == null) || (!localBundle.containsKey("extra_shot_type")))
    {
      Log.w(TAG, "Missing shot type argument. Aborting...");
      dismiss();
    }
    FragmentManager localFragmentManager;
    Fragment localFragment;
    do
    {
      do
      {
        return;
        Context localContext = getContext();
        this.shotType = localBundle.getInt("extra_shot_type");
        TextView localTextView1 = (TextView)paramView.findViewById(2131493230);
        localTextView1.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            Bundle localBundle = new Bundle();
            localBundle.putInt("extra_shot_tutorial_type", ShotSettingsDialog.this.shotType);
            ShotTutorialFragment localShotTutorialFragment = new ShotTutorialFragment();
            localShotTutorialFragment.setArguments(localBundle);
            localShotTutorialFragment.show(ShotSettingsDialog.this.getChildFragmentManager(), "Shot tutorial");
          }
        });
        localTextView1.setText(getString(2131099923, new Object[] { SoloMessageShot.getShotLabel(localContext, this.shotType) }));
        TextView localTextView2 = (TextView)paramView.findViewById(2131493227);
        CharSequence localCharSequence = SoloMessageShot.getShotLabel(localContext, this.shotType);
        if (localCharSequence != null)
          localTextView2.setText(localCharSequence + " Settings");
        localFragmentManager = getChildFragmentManager();
      }
      while (localFragmentManager.findFragmentById(2131493228) != null);
      localFragment = getSettingsContent();
    }
    while (localFragment == null);
    localFragmentManager.beginTransaction().add(2131493228, localFragment).commit();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.settings.ShotSettingsDialog
 * JD-Core Version:    0.6.2
 */