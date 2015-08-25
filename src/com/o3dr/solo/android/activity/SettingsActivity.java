package com.o3dr.solo.android.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.fragment.TitleUpdater;
import com.o3dr.solo.android.fragment.settings.GlobalSettingsMasterFragment;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.fragment.settings.SettingsMasterFragment.SettingsMasterListener;
import com.o3dr.solo.android.fragment.settings.SubSettingsFragment.DetailPaneHolder;
import com.o3dr.solo.android.fragment.settings.general.AdvancedSettingsFragment;
import com.o3dr.solo.android.fragment.settings.general.SystemInfoFragment;
import com.o3dr.solo.android.fragment.settings.general.SystemUpdateFragment;
import com.o3dr.solo.android.fragment.settings.general.UnitsFragment;
import com.o3dr.solo.android.fragment.settings.general.VoiceAlertsFragment;
import com.o3dr.solo.android.fragment.settings.solo.SoloSettingsFragment;
import com.o3dr.solo.android.widget.SlidePaneLayout;
import com.o3dr.solo.android.widget.SlidePaneLayout.PanelSlideListener;

public class SettingsActivity extends BaseActivity
  implements SettingsMasterFragment.SettingsMasterListener, SlidePaneLayout.PanelSlideListener, SubSettingsFragment.DetailPaneHolder, TitleUpdater
{
  private static final boolean DEFAULT_OPEN_DETAIL_PANE = false;
  private static final int DEFAULT_SELECTED_PANE_ID = -1;
  public static final String EXTRA_SETTINGS_ARGUMENTS = "extra_settings_arguments";
  public static final String EXTRA_SETTINGS_OPEN_DETAIL_PANE = "extra_settings_open_detail_pane";
  public static final String EXTRA_SETTINGS_SELECTED_PANE_ID = "extra_settings_selected_pane_id";
  private static final IntentFilter filter = new IntentFilter();
  private SettingsDetailFragment detailFrag;
  private GlobalSettingsMasterFragment masterFrag;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 551871472:
      case 662782932:
      case 1256617868:
      case 1962523320:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
          {
            i = 0;
            continue;
            if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
            {
              i = 1;
              continue;
              if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
              {
                i = 2;
                continue;
                if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED"))
                  i = 3;
              }
            }
          }
          break;
        case 0:
        case 1:
        case 2:
        case 3:
        }
      SettingsActivity.this.updateSettingsAccess();
    }
  };
  private int selectedPaneId = -1;
  private SlidePaneLayout slidingPane;
  private TextView titleView;

  static
  {
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED");
  }

  private boolean isUpdateAvailable()
  {
    SoloApp localSoloApp = (SoloApp)getApplication();
    if (localSoloApp == null);
    UpdateState localUpdateState;
    do
    {
      return false;
      localUpdateState = localSoloApp.getUpdateState();
    }
    while ((localUpdateState == null) || ((!localUpdateState.isServerUpdateAvailable()) && (!localUpdateState.isVehicleUpdateAvailable()) && (!localUpdateState.isVehicleUpdating()) && (!localUpdateState.isGettingUpdatesFromServer())));
    return true;
  }

  private void selectDetailPane(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    selectDetailPane(paramInt, paramBoolean1, paramBoolean2, null);
  }

  private void selectDetailPane(int paramInt, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle)
  {
    if (this.selectedPaneId != paramInt)
      switch (paramInt)
      {
      case 2131493141:
      case 2131493142:
      case 2131493144:
      case 2131493145:
      case 2131493147:
      case 2131493148:
      case 2131493150:
      case 2131493151:
      default:
        this.detailFrag = null;
        if (this.detailFrag != null)
          break;
      case 2131493140:
      case 2131493143:
      case 2131493146:
      case 2131493149:
      case 2131493152:
      case 2131493153:
      }
    while (!paramBoolean2)
    {
      while (true)
      {
        return;
        this.detailFrag = new SoloSettingsFragment();
        continue;
        this.detailFrag = new SystemUpdateFragment();
        continue;
        this.detailFrag = new UnitsFragment();
        continue;
        this.detailFrag = new VoiceAlertsFragment();
        continue;
        this.detailFrag = new SystemInfoFragment();
        continue;
        this.detailFrag = new AdvancedSettingsFragment();
      }
      if (paramBundle != null)
        this.detailFrag.setArguments(paramBundle);
      getSupportFragmentManager().beginTransaction().replace(2131493037, this.detailFrag).commitAllowingStateLoss();
      this.selectedPaneId = paramInt;
      if (paramBoolean1)
        this.masterFrag.selectPane(this.selectedPaneId);
    }
    this.slidingPane.closePane();
  }

  private void updateSettingsAccess()
  {
    boolean bool = isLinkConnected();
    this.masterFrag.enablePane(2131493140, bool);
    if ((!bool) && (this.selectedPaneId == 2131493140))
    {
      selectDetailPane(2131493152, true, false);
      if (!this.slidingPane.isOpen())
        this.slidingPane.openPane();
    }
  }

  public SlidePaneLayout getContainerSlidePane()
  {
    return this.slidingPane;
  }

  public void onBackPressed()
  {
    if ((this.detailFrag == null) || (!this.detailFrag.onBackPressed()))
    {
      if (this.slidingPane.isOpen())
        super.onBackPressed();
    }
    else
      return;
    this.slidingPane.openPane();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903075);
    this.slidingPane = ((SlidePaneLayout)findViewById(2131493035));
    this.slidingPane.setPanelSlideListener(this);
    setSupportActionBar((Toolbar)findViewById(2131492976));
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDisplayShowTitleEnabled(false);
      localActionBar.setDisplayShowHomeEnabled(true);
      localActionBar.setDisplayHomeAsUpEnabled(true);
    }
    this.titleView = ((TextView)findViewById(2131492977));
    updateTitle(2131099916);
    int i = -1;
    boolean bool1 = isUpdateAvailable();
    boolean bool2 = false;
    if (bool1)
    {
      bool2 = true;
      i = 2131493143;
    }
    if (paramBundle != null)
    {
      bool2 = paramBundle.getBoolean("extra_settings_open_detail_pane", bool2);
      i = paramBundle.getInt("extra_settings_selected_pane_id", i);
    }
    Intent localIntent = getIntent();
    Bundle localBundle1 = null;
    if (localIntent != null)
    {
      i = localIntent.getIntExtra("extra_settings_selected_pane_id", i);
      bool2 = localIntent.getBooleanExtra("extra_settings_open_detail_pane", bool2);
      localBundle1 = localIntent.getBundleExtra("extra_settings_arguments");
    }
    FragmentManager localFragmentManager = getSupportFragmentManager();
    this.masterFrag = ((GlobalSettingsMasterFragment)localFragmentManager.findFragmentById(2131493036));
    if (this.masterFrag == null)
    {
      Bundle localBundle2 = new Bundle(1);
      localBundle2.putInt("extra_settings_selected_pane_id", i);
      this.masterFrag = new GlobalSettingsMasterFragment();
      this.masterFrag.setArguments(localBundle2);
      localFragmentManager.beginTransaction().add(2131493036, this.masterFrag).commit();
    }
    selectDetailPane(i, false, false, localBundle1);
    if (bool2)
    {
      this.slidingPane.closePane();
      return;
    }
    this.slidingPane.openPane();
  }

  public void onDetailPaneClicked(int paramInt)
  {
    selectDetailPane(paramInt, true, true);
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    selectDetailPane(paramIntent.getExtras().getInt("extra_settings_selected_pane_id", this.selectedPaneId), true, true);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 16908332:
    }
    if ((this.detailFrag == null) || (!this.detailFrag.onBackPressed()))
    {
      if (!this.slidingPane.isOpen())
        break label63;
      NavUtils.navigateUpFromSameTask(this);
    }
    while (true)
    {
      return true;
      label63: this.slidingPane.openPane();
    }
  }

  public void onPanelClosed(View paramView)
  {
  }

  public void onPanelOpened(View paramView)
  {
    updateTitle(2131099916);
  }

  public void onPanelSlide(View paramView, float paramFloat)
  {
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("extra_settings_selected_pane_id", this.selectedPaneId);
    if (!this.slidingPane.isOpen());
    for (boolean bool = true; ; bool = false)
    {
      paramBundle.putBoolean("extra_settings_open_detail_pane", bool);
      return;
    }
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    super.onServiceConnected(paramComponentName, paramIBinder);
    updateSettingsAccess();
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    super.onServiceDisconnected(paramComponentName);
    updateSettingsAccess();
  }

  public void onStart()
  {
    super.onStart();
    if (this.appManager != null)
      updateSettingsAccess();
    this.lbm.registerReceiver(this.receiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    this.lbm.unregisterReceiver(this.receiver);
  }

  public void updateTitle(@StringRes int paramInt)
  {
    if (this.titleView != null)
      this.titleView.setText(paramInt);
  }

  public void updateTitle(CharSequence paramCharSequence)
  {
    if (this.titleView != null)
      this.titleView.setText(paramCharSequence);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.SettingsActivity
 * JD-Core Version:    0.6.2
 */