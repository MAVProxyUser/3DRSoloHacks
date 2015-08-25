package com.o3dr.solo.android.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.apis.drone.camera.CameraApi;
import com.o3dr.services.android.lib.drone.camera.GoPro;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.MapFragment;
import com.o3dr.solo.android.fragment.TelemetryFragment;
import com.o3dr.solo.android.fragment.TutorialWizardFragment;
import com.o3dr.solo.android.fragment.TutorialWizardFragment.Contract;
import com.o3dr.solo.android.fragment.alerts.AlertType;
import com.o3dr.solo.android.fragment.alerts.AlertsListener;
import com.o3dr.solo.android.fragment.alerts.AlertsManagerFragment;
import com.o3dr.solo.android.fragment.shots.ShotControlFragment;
import com.o3dr.solo.android.fragment.shots.ShotControlListener;
import com.o3dr.solo.android.fragment.shots.ShotMenuFragment;
import com.o3dr.solo.android.fragment.wizards.CoachMarkView;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.CoachMarkTarget;
import com.o3dr.solo.android.util.video.DecoderListener;
import com.o3dr.solo.android.widget.ListenerCardView;
import com.sothree.slidinguppanel.HorizontalSlidingPanelLayout;
import com.sothree.slidinguppanel.SlidingPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingPanelLayout.PanelState;
import me.grantland.widget.AutofitTextView;

public class FlightActivity extends NavigationDrawerActivity
  implements AlertsListener, ShotControlListener, TextureView.SurfaceTextureListener, DecoderListener, TutorialWizardFragment.Contract
{
  private static final String SCREEN_APPEARS = "Screen Appears";
  private static final String START_RECORDING = "Start Recording";
  private static final String STOP_RECORDING = "Stop Recording";
  public static final String TAG = FlightActivity.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter();
  private static SurfaceTexture savedSurfaceTexture;
  private final Runnable animateConfirmationCheckOut = new Runnable()
  {
    public void run()
    {
      Log.d(FlightActivity.TAG, "Hiding the confirmation check.");
      FlightActivity.this.confirmationCheck.animate().alpha(0.0F).withEndAction(FlightActivity.this.hideConfirmationCheck).start();
    }
  };
  private AppAnalytics appAnalytics;
  private CoachMarkView coachMark;
  private SparseArray<CoachMarkTarget> coachMarkLocations;
  private ImageView confirmationCheck;
  private final View.OnClickListener expandSlidingPanelTask = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (FlightActivity.this.slidingPanelLayout != null)
        FlightActivity.this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.EXPANDED);
    }
  };
  private ImageView goProButton;
  private View goProContainer;
  private final Handler handler = new Handler();
  private final Runnable hideConfirmationCheck = new Runnable()
  {
    public void run()
    {
      FlightActivity.this.confirmationCheck.setVisibility(8);
    }
  };
  private View loadingVideo;
  private FrameLayout mainContainer;
  private float mainContainerWidth;
  private RelativeLayout mainContentView;
  private MapFragment mapFragment;
  private final View.OnClickListener maximizeVideoTask = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      FlightActivity.this.maximizeVideo();
    }
  };
  private ListenerCardView minimizedContainer;
  private int previousWizardPage = 0;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i;
      switch (str.hashCode())
      {
      default:
        i = -1;
        label47: switch (i)
        {
        default:
        case 0:
        case 1:
        case 2:
        }
        break;
      case 1256617868:
      case -666650844:
      case 1343486835:
      }
      Drone localDrone;
      int j;
      do
      {
        do
        {
          return;
          if (!str.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
            break;
          i = 0;
          break label47;
          if (!str.equals("com.o3dr.services.android.lib.attribute.event.GOPRO_STATE_UPDATED"))
            break;
          i = 1;
          break label47;
          if (!str.equals("com.o3dr.services.android.lib.attribute.event.STATE_VEHICLE_MODE"))
            break;
          i = 2;
          break label47;
          FlightActivity.this.updateGoProState();
          return;
          localDrone = FlightActivity.this.getDrone();
        }
        while (localDrone == null);
        j = FlightActivity.this.getSoloLinkManager().getShotManager().getCurrentShot();
      }
      while (j != -1);
      State localState = (State)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
      FlightActivity.this.displayFlightModeText(j, localState.getVehicleMode());
    }
  };
  private FrameLayout shotContainer;
  private TextView shotListIndicator;
  private FrameLayout slidingContentView;
  private HorizontalSlidingPanelLayout slidingPanelLayout;
  private TextureView streamingView;
  private TelemetryFragment telemFragment;
  private FrameLayout videoContainer;
  private AutofitTextView videoPlaybackError;
  private RelativeLayout wizardContainer;

  static
  {
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_VEHICLE_MODE");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.GOPRO_STATE_UPDATED");
  }

  private void cleanWizardScreen(int paramInt1, int paramInt2)
  {
    if (((paramInt2 == 5) && (paramInt1 == 4)) || ((paramInt1 == 5) && (paramInt2 == 4)));
    while (true)
    {
      return;
      SlidingPanelLayout.PanelState localPanelState = this.slidingPanelLayout.getPanelState();
      if (localPanelState == SlidingPanelLayout.PanelState.ANCHORED)
      {
        this.slidingPanelLayout.setEnabled(true);
        this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.COLLAPSED);
      }
      while (this.coachMark != null)
      {
        this.coachMark.setVisibility(8);
        return;
        if (localPanelState == SlidingPanelLayout.PanelState.EXPANDED)
          maximizeVideo();
      }
    }
  }

  private void displayFlightModeText(int paramInt, VehicleMode paramVehicleMode)
  {
    Context localContext = getApplicationContext();
    CharSequence localCharSequence;
    switch (paramInt)
    {
    case 3:
    case 4:
    default:
      localCharSequence = null;
    case 0:
    case 1:
    case 2:
    case 5:
    case -1:
    }
    while (true)
    {
      displayFlightModeText(localCharSequence);
      return;
      localCharSequence = SoloMessageShot.getShotLabel(localContext, paramInt);
      continue;
      localCharSequence = SoloMessageShot.getFlightModeLabel(localContext, paramVehicleMode);
    }
  }

  private void displayFlightModeText(CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramCharSequence))
      return;
    this.shotListIndicator.setText(paramCharSequence);
    this.shotListIndicator.setVisibility(0);
    this.shotListIndicator.setAlpha(0.5F);
    this.shotListIndicator.animate().alpha(1.0F).setDuration(500L).withEndAction(new Runnable()
    {
      public void run()
      {
        FlightActivity.this.shotListIndicator.animate().alpha(0.0F).setDuration(2000L).withEndAction(new Runnable()
        {
          public void run()
          {
            FlightActivity.this.shotListIndicator.setVisibility(8);
            FlightActivity.this.shotListIndicator.setAlpha(1.0F);
          }
        });
      }
    });
  }

  private void maximizeVideo()
  {
    this.minimizedContainer.removeView(this.videoContainer);
    this.mainContainer.addView(this.videoContainer, 0);
    if (savedSurfaceTexture != null)
      this.streamingView.setSurfaceTexture(savedSurfaceTexture);
    this.minimizedContainer.setVisibility(8);
    this.slidingPanelLayout.setEnabled(true);
    this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.COLLAPSED);
  }

  private void minimizeVideo()
  {
    this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.EXPANDED);
    this.slidingPanelLayout.setEnabled(false);
    this.minimizedContainer.setVisibility(0);
    this.mainContainer.removeView(this.videoContainer);
    this.minimizedContainer.addView(this.videoContainer);
    if (savedSurfaceTexture != null)
      this.streamingView.setSurfaceTexture(savedSurfaceTexture);
  }

  private void showErrorScreen()
  {
    this.loadingVideo.setVisibility(8);
    this.streamingView.setVisibility(8);
    this.videoPlaybackError.setVisibility(0);
  }

  private void showLoadingVideo()
  {
    this.loadingVideo.setVisibility(0);
    this.streamingView.setVisibility(0);
    this.videoPlaybackError.setVisibility(8);
  }

  private void showVideoScreen()
  {
    this.loadingVideo.setVisibility(8);
    this.streamingView.setVisibility(0);
    this.videoPlaybackError.setVisibility(8);
  }

  private void startDecoding()
  {
    ArtooLinkManager localArtooLinkManager = getArtooLinkManager();
    if ((localArtooLinkManager != null) && (savedSurfaceTexture != null))
      localArtooLinkManager.startDecoding(new Surface(savedSurfaceTexture), this);
  }

  private void stopDecoding()
  {
    ArtooLinkManager localArtooLinkManager = getArtooLinkManager();
    if (localArtooLinkManager != null)
      localArtooLinkManager.stopDecoding(this);
  }

  private void updateGoProState()
  {
    Drone localDrone = getDrone();
    if (localDrone == null)
      return;
    GoPro localGoPro = (GoPro)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GOPRO");
    if (!localGoPro.isConnected())
    {
      this.goProButton.setEnabled(false);
      return;
    }
    this.goProButton.setActivated(localGoPro.isRecording());
  }

  private void updateSlidingPanel()
  {
    SlidingPanelLayout.PanelState localPanelState = this.slidingPanelLayout.getPanelState();
    int i;
    int j;
    label45: View.OnClickListener localOnClickListener;
    if ((localPanelState == SlidingPanelLayout.PanelState.EXPANDED) || (localPanelState == SlidingPanelLayout.PanelState.ANCHORED) || (localPanelState == SlidingPanelLayout.PanelState.COLLAPSED))
    {
      i = 1;
      HorizontalSlidingPanelLayout localHorizontalSlidingPanelLayout = this.slidingPanelLayout;
      if (i == 0)
        break label166;
      j = 2131492997;
      localHorizontalSlidingPanelLayout.setDragView(j);
      FrameLayout localFrameLayout = this.slidingContentView;
      if (localPanelState != SlidingPanelLayout.PanelState.ANCHORED)
        break label172;
      localOnClickListener = this.expandSlidingPanelTask;
      label70: localFrameLayout.setOnClickListener(localOnClickListener);
      if (this.mapFragment != null)
        if ((localPanelState != SlidingPanelLayout.PanelState.COLLAPSED) && (localPanelState != SlidingPanelLayout.PanelState.HIDDEN))
          break label178;
    }
    label166: label172: label178: for (int k = 1; ; k = 0)
    {
      if (k == 0)
      {
        float f = (1.0F - this.slidingPanelLayout.getSlideOffset()) * this.slidingContentView.getWidth();
        this.mapFragment.setMapPadding(0, 0, (int)f, 0);
        Drone localDrone = getDrone();
        if (localDrone != null)
          this.mapFragment.updateDroneOnMap(localDrone, true);
      }
      return;
      i = 0;
      break;
      j = -1;
      break label45;
      localOnClickListener = null;
      break label70;
    }
  }

  public void closeWizard()
  {
    cleanWizardScreen(this.previousWizardPage, this.previousWizardPage);
    FragmentManager localFragmentManager = getSupportFragmentManager();
    TutorialWizardFragment localTutorialWizardFragment = (TutorialWizardFragment)localFragmentManager.findFragmentById(2131493005);
    if (localTutorialWizardFragment != null)
      localFragmentManager.beginTransaction().remove(localTutorialWizardFragment).commit();
    if (this.wizardContainer != null)
    {
      this.wizardContainer.removeView(this.coachMark);
      this.wizardContainer.setVisibility(8);
    }
  }

  protected void enableNavigationAlertIcon(boolean paramBoolean)
  {
    if (this.telemFragment != null)
      this.telemFragment.enableNavigationAlertIcon(paramBoolean);
  }

  public void firstPageSetup()
  {
    this.mainContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        FlightActivity.this.onPageChanged(0);
        FlightActivity.this.mainContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }
    });
  }

  protected int getNavigationDrawerEntryId()
  {
    return -1;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    case 147:
    }
    switch (paramInt2)
    {
    default:
      return;
    case -1:
      ShotMenuFragment.checkGpsLocationSettings(this);
      return;
    case 0:
    }
    Toast.makeText(getApplicationContext(), 2131100045, 1).show();
  }

  public void onAlertHidden(AlertType paramAlertType)
  {
    if (this.mapFragment != null)
      this.mapFragment.onAlertHidden(paramAlertType);
    if (this.shotContainer != null)
      this.shotContainer.setVisibility(0);
  }

  public void onAlertShown(AlertType paramAlertType)
  {
    if (this.mapFragment != null)
      this.mapFragment.onAlertShown(paramAlertType);
    if (this.shotContainer != null)
      this.shotContainer.setVisibility(8);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903069);
    AppPreferences localAppPreferences = new AppPreferences(getApplicationContext());
    this.appAnalytics = this.soloApp.getAppAnalytics();
    this.goProContainer = findViewById(2131493001);
    this.goProButton = ((ImageView)findViewById(2131493002));
    this.goProButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Drone localDrone = FlightActivity.this.getDrone();
        if (localDrone == null)
          return;
        GoPro localGoPro = (GoPro)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GOPRO");
        long l = System.currentTimeMillis();
        if (localGoPro.isRecording())
        {
          CameraApi.stopVideoRecording(localDrone);
          FlightActivity.this.appAnalytics.track("Flight Screen", "Stop Recording", Long.valueOf(l));
          return;
        }
        CameraApi.startVideoRecording(localDrone);
        FlightActivity.this.appAnalytics.track("Flight Screen", "Start Recording", Long.valueOf(l));
      }
    });
    this.videoContainer = ((FrameLayout)findViewById(2131492992));
    this.confirmationCheck = ((ImageView)findViewById(2131492996));
    this.loadingVideo = findViewById(2131492994);
    this.streamingView = ((TextureView)findViewById(2131492993));
    this.streamingView.setSurfaceTextureListener(this);
    if (savedSurfaceTexture != null)
      this.streamingView.setSurfaceTexture(savedSurfaceTexture);
    this.videoPlaybackError = ((AutofitTextView)findViewById(2131492995));
    this.shotListIndicator = ((TextView)findViewById(2131493008));
    this.mainContainer = ((FrameLayout)findViewById(2131492991));
    this.mainContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        FlightActivity.access$702(FlightActivity.this, FlightActivity.this.mainContainer.getWidth());
        FlightActivity.this.mainContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }
    });
    this.shotContainer = ((FrameLayout)findViewById(2131493003));
    this.minimizedContainer = ((ListenerCardView)findViewById(2131493000));
    this.minimizedContainer.setCardElevation(0.0F);
    this.minimizedContainer.setOnClickListener(this.maximizeVideoTask);
    this.slidingContentView = ((FrameLayout)findViewById(2131492998));
    final float f = getResources().getDimension(2131165311);
    this.slidingPanelLayout = ((HorizontalSlidingPanelLayout)findViewById(2131492990));
    this.slidingPanelLayout.setDragView(-1);
    this.slidingPanelLayout.setPanelSlideListener(new SlidingPanelLayout.PanelSlideListener()
    {
      public void onPanelAnchored(View paramAnonymousView)
      {
        if (FlightActivity.this.mapFragment != null)
          FlightActivity.this.mapFragment.onPanelAnchored(paramAnonymousView);
        FlightActivity.this.updateSlidingPanel();
      }

      public void onPanelCollapsed(View paramAnonymousView)
      {
        if (FlightActivity.this.mapFragment != null)
          FlightActivity.this.mapFragment.onPanelCollapsed(paramAnonymousView);
        FlightActivity.this.updateSlidingPanel();
        FlightActivity.this.goProContainer.setTranslationX(0.0F);
      }

      public void onPanelExpanded(View paramAnonymousView)
      {
        if (FlightActivity.this.mapFragment != null)
          FlightActivity.this.mapFragment.onPanelExpanded(paramAnonymousView);
        FlightActivity.this.updateSlidingPanel();
        FlightActivity.this.minimizeVideo();
      }

      public void onPanelHidden(View paramAnonymousView)
      {
        if (FlightActivity.this.mapFragment != null)
          FlightActivity.this.mapFragment.onPanelHidden(paramAnonymousView);
        FlightActivity.this.updateSlidingPanel();
        FlightActivity.this.goProContainer.setTranslationX(0.0F);
      }

      public void onPanelSlide(View paramAnonymousView, float paramAnonymousFloat)
      {
        if (FlightActivity.this.mapFragment != null)
          FlightActivity.this.mapFragment.onPanelSlide(paramAnonymousView, paramAnonymousFloat);
        FlightActivity.this.slidingContentView.setTranslationX(paramAnonymousFloat * f);
        float f = paramAnonymousFloat * -FlightActivity.this.mainContainerWidth;
        FlightActivity.this.goProContainer.setTranslationX(f);
      }
    });
    FragmentManager localFragmentManager = getSupportFragmentManager();
    this.mapFragment = ((MapFragment)localFragmentManager.findFragmentById(2131492998));
    if (this.mapFragment == null)
    {
      this.mapFragment = new MapFragment();
      localFragmentManager.beginTransaction().add(2131492998, this.mapFragment).commit();
    }
    this.telemFragment = ((TelemetryFragment)localFragmentManager.findFragmentById(2131492999));
    if (this.telemFragment == null)
    {
      this.telemFragment = new TelemetryFragment();
      localFragmentManager.beginTransaction().add(2131492999, this.telemFragment).commit();
    }
    if ((AlertsManagerFragment)localFragmentManager.findFragmentById(2131493004) == null)
    {
      AlertsManagerFragment localAlertsManagerFragment = new AlertsManagerFragment();
      localFragmentManager.beginTransaction().add(2131493004, localAlertsManagerFragment).commit();
    }
    this.mainContentView = ((RelativeLayout)findViewById(2131492989));
    if (localAppPreferences.runWizard())
    {
      this.coachMarkLocations = new SparseArray();
      this.wizardContainer = ((RelativeLayout)findViewById(2131493005));
      if ((TutorialWizardFragment)localFragmentManager.findFragmentById(2131493005) == null)
      {
        TutorialWizardFragment localTutorialWizardFragment = new TutorialWizardFragment();
        localFragmentManager.beginTransaction().add(2131493005, localTutorialWizardFragment).commit();
      }
      this.wizardContainer.setVisibility(0);
    }
    if ((ShotControlFragment)localFragmentManager.findFragmentById(2131493003) == null)
    {
      ShotControlFragment localShotControlFragment = new ShotControlFragment();
      localFragmentManager.beginTransaction().add(2131493003, localShotControlFragment).commit();
    }
  }

  public void onDecodingEnded()
  {
    Log.d(TAG, "Decoding ended.");
  }

  public void onDecodingError()
  {
    showErrorScreen();
    Log.e(TAG, "Decoding error.");
  }

  public void onDecodingStarted()
  {
    Log.d(TAG, "Decoding started.");
    showVideoScreen();
  }

  public void onPageChanged(int paramInt)
  {
    if (this.wizardContainer == null)
      return;
    if (this.coachMark == null)
    {
      this.coachMark = new CoachMarkView(this);
      this.wizardContainer.addView(this.coachMark);
    }
    cleanWizardScreen(this.previousWizardPage, paramInt);
    switch (paramInt)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      this.previousWizardPage = paramInt;
      return;
      CoachMarkTarget localCoachMarkTarget6 = (CoachMarkTarget)this.coachMarkLocations.get(2131493332);
      if (localCoachMarkTarget6 == null)
      {
        localCoachMarkTarget6 = new CoachMarkTarget(2131493332, this);
        this.coachMarkLocations.put(2131493332, localCoachMarkTarget6);
      }
      this.coachMark.setCoachMarkView(getString(2131099924), 2, localCoachMarkTarget6.getPoint());
      this.coachMark.setVisibility(0);
      continue;
      CoachMarkTarget localCoachMarkTarget5 = (CoachMarkTarget)this.coachMarkLocations.get(2131493337);
      if (localCoachMarkTarget5 == null)
      {
        localCoachMarkTarget5 = new CoachMarkTarget(2131493337, this);
        this.coachMarkLocations.put(2131493337, localCoachMarkTarget5);
      }
      this.coachMark.setCoachMarkView(getString(2131099762), 2, localCoachMarkTarget5.getPoint());
      this.coachMark.setVisibility(0);
      continue;
      this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.ANCHORED);
      CoachMarkTarget localCoachMarkTarget4 = (CoachMarkTarget)this.coachMarkLocations.get(2131493007);
      if (localCoachMarkTarget4 == null)
      {
        localCoachMarkTarget4 = new CoachMarkTarget(2131493007, this);
        this.coachMarkLocations.put(2131493007, localCoachMarkTarget4);
      }
      this.coachMark.setCoachMarkView(getString(2131099759), 1, localCoachMarkTarget4.getAbsoluteCenter());
      this.coachMark.setVisibility(0);
      continue;
      this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.EXPANDED);
      CoachMarkTarget localCoachMarkTarget3 = (CoachMarkTarget)this.coachMarkLocations.get(2131493000);
      if (localCoachMarkTarget3 == null)
      {
        localCoachMarkTarget3 = new CoachMarkTarget(2131493000, this);
        this.coachMarkLocations.put(2131493000, localCoachMarkTarget3);
      }
      this.coachMark.setCoachMarkView(getString(2131099754), 0, localCoachMarkTarget3.getAbsoluteCenter());
      this.coachMark.setVisibility(0);
      continue;
      this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.EXPANDED);
      CoachMarkTarget localCoachMarkTarget2 = (CoachMarkTarget)this.coachMarkLocations.get(2131493006);
      if (localCoachMarkTarget2 == null)
      {
        localCoachMarkTarget2 = new CoachMarkTarget(2131493006, this);
        this.coachMarkLocations.put(2131493006, localCoachMarkTarget2);
      }
      this.coachMark.setCoachMarkView(getString(2131099755), 2, localCoachMarkTarget2.getAbsoluteCenter());
      this.coachMark.setVisibility(0);
      continue;
      CoachMarkTarget localCoachMarkTarget1 = (CoachMarkTarget)this.coachMarkLocations.get(2131493326);
      if (localCoachMarkTarget1 == null)
      {
        localCoachMarkTarget1 = new CoachMarkTarget(2131493326, this);
        this.coachMarkLocations.put(2131493326, localCoachMarkTarget1);
      }
      this.coachMark.setCoachMarkView(getString(2131099761), 3, localCoachMarkTarget1.getPoint());
      this.coachMark.setVisibility(0);
    }
  }

  public void onPause()
  {
    super.onPause();
    stopDecoding();
    savedSurfaceTexture = null;
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    super.onServiceConnected(paramComponentName, paramIBinder);
    this.appAnalytics.track("Flight Screen", "Screen Appears", Boolean.valueOf(isLinkConnected()));
    startDecoding();
    updateGoProState();
  }

  public void onShotEnded(int paramInt)
  {
    if (this.mapFragment != null)
      this.mapFragment.onShotEnded(paramInt);
    switch (paramInt)
    {
    case 2:
    case 3:
    case 4:
    default:
    case 0:
    case 1:
    case 5:
    }
    do
      return;
    while (this.slidingPanelLayout == null);
    this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.COLLAPSED);
  }

  public void onShotPaused(int paramInt)
  {
    CharSequence localCharSequence = SoloMessageShot.getShotLabel(getApplicationContext(), paramInt);
    if (TextUtils.isEmpty(localCharSequence))
      return;
    displayFlightModeText(getString(2131099925, new Object[] { localCharSequence }));
  }

  public void onShotResumed(int paramInt)
  {
    CharSequence localCharSequence = SoloMessageShot.getShotLabel(getApplicationContext(), paramInt);
    if (TextUtils.isEmpty(localCharSequence))
      return;
    displayFlightModeText(getString(2131099926, new Object[] { localCharSequence }));
  }

  public void onShotSetupCompleted(int paramInt)
  {
    if (this.mapFragment != null)
      this.mapFragment.onShotSetupCompleted(paramInt);
    switch (paramInt)
    {
    default:
      return;
    case 1:
    case 2:
    }
    Log.d(TAG, "Animating confirmation check.");
    this.confirmationCheck.setVisibility(0);
    this.confirmationCheck.setAlpha(0.0F);
    this.confirmationCheck.animate().alpha(1.0F).start();
    this.handler.postDelayed(this.animateConfirmationCheckOut, 1500L);
  }

  public void onShotStarted(int paramInt)
  {
    if (this.mapFragment != null)
      this.mapFragment.onShotStarted(paramInt);
    displayFlightModeText(paramInt, null);
    switch (paramInt)
    {
    case 2:
    case 3:
    case 4:
    default:
    case 0:
    case 1:
    case 5:
    }
    do
      return;
    while (this.slidingPanelLayout == null);
    this.slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.ANCHORED);
  }

  public void onStart()
  {
    super.onStart();
    showLoadingVideo();
    updateGoProState();
    this.lbm.registerReceiver(this.receiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    this.lbm.unregisterReceiver(this.receiver);
  }

  public void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
  {
    Log.d(TAG, "Surface texture available.");
    if (savedSurfaceTexture == null)
    {
      savedSurfaceTexture = paramSurfaceTexture;
      startDecoding();
    }
  }

  public boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture)
  {
    Log.d(TAG, "Surface texture destroyed.");
    return savedSurfaceTexture == null;
  }

  public void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
  {
    Log.d(TAG, "Surface texture size changed.");
  }

  public void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture)
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.FlightActivity
 * JD-Core Version:    0.6.2
 */