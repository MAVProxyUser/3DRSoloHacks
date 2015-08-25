package com.o3dr.solo.android.fragment.shots;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gc.materialdesign.views.Slider;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.fragment.shots.settings.ShotSettingsDialog;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.CableCamState;
import com.o3dr.solo.android.service.sololink.shot.FollowShotState;
import com.o3dr.solo.android.service.sololink.shot.OrbitState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;

public class ShotControlFragment extends BaseFragment
{
  private static final int PROGRESS_MAX_VALUE = 100;
  private static final int PROGRESS_MIN_VALUE = 0;
  private static final String SHOT_TYPE = "Shot type";
  private static final String TAG = ShotControlFragment.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter();
  private AppAnalytics appAnalytics;
  AppPreferences appPrefs;
  private CableCamControlHandler cableCamHandler;
  private FollowControlHandler followHandler;
  final Handler handler = new Handler();
  private ShotControlListener listener;
  private ImageView orbitAutoLeft;
  private ImageView orbitAutoRight;
  private View orbitControlView;
  private OrbitControlHandler orbitHandler;
  private TextView orbitRadius;
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
          label136: if ((ShotControlFragment.this.cableCamHandler != null) && (ShotControlFragment.this.cableCamHandler.onReceive(paramAnonymousContext, paramAnonymousIntent)))
          {
            j = 1;
            label164: if (j == 0)
            {
              if ((ShotControlFragment.this.selfieHandler == null) || (!ShotControlFragment.this.selfieHandler.onReceive(paramAnonymousContext, paramAnonymousIntent)))
                break label406;
              j = 1;
            }
            label197: if (j == 0)
              if ((ShotControlFragment.this.orbitHandler == null) || (!ShotControlFragment.this.orbitHandler.onReceive(paramAnonymousContext, paramAnonymousIntent)))
                break label412;
          }
          break;
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        }
        break;
      case -1578907242:
      case 1383094019:
      case -260677709:
      case -818937780:
      case -42951236:
      case 1841369708:
      case 1781897487:
      case 191628613:
      }
      label406: label412: for (int j = 1; ; j = 0)
      {
        if ((j == 0) && ((ShotControlFragment.this.followHandler == null) || (!ShotControlFragment.this.followHandler.onReceive(paramAnonymousContext, paramAnonymousIntent))))
          return;
        return;
        if (!str.equals("com.o3dr.solo.android.action.CABLE_CAM_STARTED"))
          break;
        i = 0;
        break;
        if (!str.equals("com.o3dr.solo.android.action.SELFIE_STARTED"))
          break;
        i = 1;
        break;
        if (!str.equals("com.o3dr.solo.android.action.ORBIT_STARTED"))
          break;
        i = 2;
        break;
        if (!str.equals("com.o3dr.solo.android.action.FOLLOW_STARTED"))
          break;
        i = 3;
        break;
        if (!str.equals("com.o3dr.solo.android.action.SELFIE_ENDED"))
          break;
        i = 4;
        break;
        if (!str.equals("com.o3dr.solo.android.action.ORBIT_ENDED"))
          break;
        i = 5;
        break;
        if (!str.equals("com.o3dr.solo.android.action.CABLE_CAM_ENDED"))
          break;
        i = 6;
        break;
        if (!str.equals("com.o3dr.solo.android.action.FOLLOW_ENDED"))
          break;
        i = 7;
        break;
        ShotControlFragment.this.resetProgress();
        ShotControlFragment.this.resetOrbitRadius();
        break label136;
        j = 0;
        break label164;
        j = 0;
        break label197;
      }
    }
  };
  private SelfieControlHandler selfieHandler;
  private ImageView shotBackButton;
  private View shotControlContainer;
  private View shotControlView;
  private ImageView shotForwardButton;
  private Slider shotProgress;
  private View shotSettingsButton;
  private TextView shotSetupIndex;
  private TextView shotSetupInstructions;
  private View shotSetupView;

  static
  {
    filter.addAction("com.o3dr.solo.android.action.CABLE_CAM_STARTED");
    filter.addAction("com.o3dr.solo.android.action.CABLE_CAM_ENDED");
    filter.addAction("com.o3dr.solo.android.action.CABLE_CAM_WAYPOINT_SET");
    filter.addAction("com.o3dr.solo.android.action.CABLE_CAM_STATUS");
    filter.addAction("com.o3dr.solo.android.action.SELFIE_STARTED");
    filter.addAction("com.o3dr.solo.android.action.SELFIE_STATUS");
    filter.addAction("com.o3dr.solo.android.action.SELFIE_ENDED");
    filter.addAction("com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED");
    filter.addAction("com.o3dr.solo.android.action.ORBIT_STARTED");
    filter.addAction("com.o3dr.solo.android.action.ORBIT_STATUS");
    filter.addAction("com.o3dr.solo.android.action.ORBIT_ENDED");
    filter.addAction("com.o3dr.solo.android.action_ORBIT_ROI_SET");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_STARTED");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_STATUS");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_ENDED");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_TARGET_SET");
    filter.addAction("com.o3dr.solo.android.action.FOLLOW_LOOK_AT");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.GPS_POSITION");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED");
  }

  private void handleBackControlButton(boolean paramBoolean)
  {
    ShotState localShotState = getSoloLinkManager().getShotManager().getCurrentShotState();
    if (localShotState == null)
      return;
    if (paramBoolean)
    {
      localShotState.setActiveState(2);
      return;
    }
    localShotState.setActiveState(4);
  }

  private void handleForwardControlButton(boolean paramBoolean)
  {
    ShotState localShotState = getSoloLinkManager().getShotManager().getCurrentShotState();
    if (localShotState == null)
      return;
    if (paramBoolean)
    {
      localShotState.setActiveState(2);
      return;
    }
    localShotState.setActiveState(3);
  }

  private void resetOrbitRadius()
  {
    LengthUnitProvider localLengthUnitProvider = getLengthUnitProvider();
    if (localLengthUnitProvider == null);
    while (this.orbitRadius == null)
      return;
    this.orbitRadius.setText(localLengthUnitProvider.boxBaseValueToTarget(0.0D).toString());
  }

  private void resetProgress()
  {
    if (this.shotProgress != null)
      this.shotProgress.setValue(0);
  }

  void displayInstructions(@StringRes int paramInt1, @StringRes int paramInt2, @DrawableRes int paramInt3, int paramInt4)
  {
    String str1 = getString(paramInt1);
    String str2;
    if (paramInt2 == 0)
    {
      str2 = "";
      if (paramInt3 != 0)
        break label45;
    }
    label45: for (Drawable localDrawable = null; ; localDrawable = getResources().getDrawable(paramInt3))
    {
      displayInstructions(str1, str2, localDrawable, paramInt4);
      return;
      str2 = getString(paramInt2);
      break;
    }
  }

  void displayInstructions(CharSequence paramCharSequence1, CharSequence paramCharSequence2, Drawable paramDrawable, int paramInt)
  {
    resetViews();
    this.shotControlContainer.setVisibility(0);
    this.shotSetupView.setVisibility(0);
    if (TextUtils.isEmpty(paramCharSequence2))
      this.shotSetupIndex.setText("");
    while (true)
    {
      this.shotSetupIndex.setBackground(paramDrawable);
      this.shotSetupInstructions.setText(paramCharSequence1);
      playSound(paramInt);
      return;
      this.shotSetupIndex.setText(paramCharSequence2);
    }
  }

  void notifyShotEnded(int paramInt)
  {
    if (this.listener != null)
      this.listener.onShotEnded(paramInt);
    Context localContext = getContext();
    if (localContext != null)
      this.appAnalytics.track("Shot Completed", "Shot type", SoloMessageShot.getShotLabel(localContext, paramInt));
  }

  void notifyShotPaused(int paramInt)
  {
    if (this.listener != null)
      this.listener.onShotPaused(paramInt);
  }

  void notifyShotResumed(int paramInt)
  {
    if (this.listener != null)
      this.listener.onShotResumed(paramInt);
  }

  void notifyShotSetupCompleted(int paramInt)
  {
    if (this.listener != null)
      this.listener.onShotSetupCompleted(paramInt);
  }

  void notifyShotStarted(int paramInt)
  {
    if (this.listener != null)
      this.listener.onShotStarted(paramInt);
    Context localContext = getContext();
    if (localContext != null)
      this.appAnalytics.track("Shot Started", "Shot type", SoloMessageShot.getShotLabel(localContext, paramInt));
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof ShotControlListener))
      throw new IllegalStateException("Parent activity must implement " + ShotControlListener.class.getSimpleName());
    this.listener = ((ShotControlListener)paramActivity);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903104, paramViewGroup, false);
  }

  public void onDetach()
  {
    super.onDetach();
    this.listener = null;
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
    this.appPrefs = new AppPreferences(getContext());
    this.appAnalytics = getApplication().getAppAnalytics();
    this.shotControlContainer = paramView.findViewById(2131493214);
    this.shotSettingsButton = paramView.findViewById(2131493215);
    this.shotSettingsButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SoloLinkManager localSoloLinkManager = ShotControlFragment.this.getSoloLinkManager();
        if (localSoloLinkManager == null)
          return;
        int i = localSoloLinkManager.getShotManager().getCurrentShot();
        Bundle localBundle = new Bundle();
        localBundle.putInt("extra_shot_type", i);
        ShotSettingsDialog localShotSettingsDialog = new ShotSettingsDialog();
        localShotSettingsDialog.setArguments(localBundle);
        localShotSettingsDialog.show(ShotControlFragment.this.getChildFragmentManager(), "Shot settings dialog");
      }
    });
    this.shotSetupView = paramView.findViewById(2131493216);
    this.shotSetupView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ShotState localShotState = ShotControlFragment.this.getSoloLinkManager().getShotManager().getCurrentShotState();
        if ((localShotState instanceof OrbitState))
        {
          OrbitState localOrbitState = (OrbitState)localShotState;
          if (!localOrbitState.isActive())
            localOrbitState.recordLocation();
        }
        CableCamState localCableCamState;
        do
        {
          do
          {
            do
            {
              return;
              if (!(localShotState instanceof FollowShotState))
                break;
            }
            while (((FollowShotState)localShotState).isActive());
            ShotControlFragment.this.followHandler.enableLocationReporting();
            return;
          }
          while (!(localShotState instanceof CableCamState));
          localCableCamState = (CableCamState)localShotState;
        }
        while (localCableCamState.isActive());
        localCableCamState.recordLocation();
      }
    });
    this.shotSetupIndex = ((TextView)paramView.findViewById(2131493217));
    this.shotSetupInstructions = ((TextView)paramView.findViewById(2131493218));
    this.shotControlView = paramView.findViewById(2131493219);
    this.shotBackButton = ((ImageView)paramView.findViewById(2131493220));
    this.shotBackButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ShotControlFragment.this.handleBackControlButton(ShotControlFragment.this.shotBackButton.isActivated());
      }
    });
    this.shotForwardButton = ((ImageView)paramView.findViewById(2131493222));
    this.shotForwardButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ShotControlFragment.this.handleForwardControlButton(ShotControlFragment.this.shotForwardButton.isActivated());
      }
    });
    this.shotProgress = ((Slider)paramView.findViewById(2131493221));
    this.shotProgress.setMax(100);
    this.shotProgress.setMin(-1);
    this.shotProgress.setEnabled(false);
    this.orbitControlView = paramView.findViewById(2131493223);
    this.orbitRadius = ((TextView)paramView.findViewById(2131493225));
    this.orbitAutoLeft = ((ImageView)paramView.findViewById(2131493224));
    this.orbitAutoLeft.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ShotControlFragment.this.handleBackControlButton(ShotControlFragment.this.orbitAutoLeft.isActivated());
      }
    });
    this.orbitAutoRight = ((ImageView)paramView.findViewById(2131493226));
    this.orbitAutoRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ShotControlFragment.this.handleForwardControlButton(ShotControlFragment.this.orbitAutoRight.isActivated());
      }
    });
    this.cableCamHandler = new CableCamControlHandler(this);
    this.selfieHandler = new SelfieControlHandler(this);
    this.orbitHandler = new OrbitControlHandler(this);
    this.followHandler = new FollowControlHandler(this);
  }

  void resetViews()
  {
    this.handler.removeCallbacksAndMessages(null);
    if (this.shotControlContainer != null)
      this.shotControlContainer.setVisibility(4);
    if (this.shotSetupView != null)
      this.shotSetupView.setVisibility(8);
    if (this.shotControlView != null)
      this.shotControlView.setVisibility(8);
    if (this.orbitControlView != null)
      this.orbitControlView.setVisibility(8);
    if (this.shotSettingsButton != null)
      this.shotSettingsButton.setVisibility(4);
  }

  void showControlView(int paramInt)
  {
    resetViews();
    this.shotControlContainer.setVisibility(0);
    this.shotControlView.setVisibility(0);
    this.shotSettingsButton.setVisibility(0);
    switch (paramInt)
    {
    default:
      this.shotBackButton.setActivated(false);
      this.shotForwardButton.setActivated(false);
      return;
    case 3:
      this.shotBackButton.setActivated(false);
      this.shotForwardButton.setActivated(true);
      return;
    case 4:
    }
    this.shotBackButton.setActivated(true);
    this.shotForwardButton.setActivated(false);
  }

  void showOrbitControl(int paramInt)
  {
    resetViews();
    this.shotControlContainer.setVisibility(0);
    this.orbitControlView.setVisibility(0);
    this.shotSettingsButton.setVisibility(0);
    switch (paramInt)
    {
    default:
      this.orbitAutoLeft.setActivated(false);
      this.orbitAutoRight.setActivated(false);
      return;
    case 3:
      this.orbitAutoLeft.setActivated(false);
      this.orbitAutoRight.setActivated(true);
      return;
    case 4:
    }
    this.orbitAutoLeft.setActivated(true);
    this.orbitAutoRight.setActivated(false);
  }

  void updateOrbitRadius(double paramDouble)
  {
    LengthUnitProvider localLengthUnitProvider = getLengthUnitProvider();
    if (localLengthUnitProvider == null)
      return;
    resetViews();
    this.shotControlContainer.setVisibility(0);
    this.shotSettingsButton.setVisibility(0);
    this.orbitControlView.setVisibility(0);
    this.orbitRadius.setText(localLengthUnitProvider.boxBaseValueToTarget(paramDouble).toString());
  }

  void updateVehicleProgress(int paramInt)
  {
    resetViews();
    this.shotControlContainer.setVisibility(0);
    this.shotControlView.setVisibility(0);
    this.shotSettingsButton.setVisibility(0);
    int i = Math.max(0, Math.min(100, paramInt));
    this.shotProgress.setValue(i);
    this.shotForwardButton.setEnabled(true);
    this.shotBackButton.setEnabled(true);
    if (i >= 100)
    {
      this.shotForwardButton.setActivated(false);
      this.shotForwardButton.setEnabled(false);
    }
    while (i > 0)
      return;
    this.shotBackButton.setActivated(false);
    this.shotBackButton.setEnabled(false);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.ShotControlFragment
 * JD-Core Version:    0.6.2
 */