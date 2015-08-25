package com.o3dr.solo.android.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;

public abstract class NavigationDrawerActivity extends BaseActivity
  implements DrawerLayout.DrawerListener
{
  private static final int GOOGLE_PLAY_SERVICES_REQUEST_CODE = 101;
  private static final IntentFilter intentFilter = new IntentFilter();
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 822378966:
      case -1038879041:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE"))
          {
            i = 0;
            continue;
            if (str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE"))
              i = 1;
          }
          break;
        case 0:
        case 1:
        }
      boolean bool = NavigationDrawerActivity.this.isUpdateAvailable();
      if (NavigationDrawerActivity.this.mNavViewsHolder != null)
        NavigationDrawerActivity.NavDrawerViewHolder.access$200(NavigationDrawerActivity.this.mNavViewsHolder, bool);
      NavigationDrawerActivity.this.enableNavigationAlertIcon(bool);
    }
  };
  private DrawerLayout drawerLayout;
  private NavDrawerViewHolder mNavViewsHolder;
  private Intent mNavigationIntent;
  private ViewGroup navigationContentLayout;

  static
  {
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED");
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED");
  }

  private void initNavigationDrawer()
  {
    View localView = findViewById(2131493028);
    if (localView != null)
      this.mNavViewsHolder = new NavDrawerViewHolder(localView, null);
  }

  private boolean isUpdateAvailable()
  {
    if (this.soloApp == null);
    UpdateState localUpdateState;
    do
    {
      return false;
      localUpdateState = this.soloApp.getUpdateState();
    }
    while ((localUpdateState == null) || ((!localUpdateState.isServerUpdateAvailable()) && (!localUpdateState.isVehicleUpdateAvailable()) && (!localUpdateState.isVehicleUpdating()) && (!localUpdateState.isGettingUpdatesFromServer())));
    return true;
  }

  private void setupNavigationEntry(int paramInt, TextView paramTextView, final Intent paramIntent)
  {
    if (paramTextView == null)
      return;
    if (paramInt == paramTextView.getId())
    {
      paramTextView.setActivated(true);
      paramTextView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          NavigationDrawerActivity.this.drawerLayout.closeDrawer(8388611);
        }
      });
      return;
    }
    paramTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (paramIntent != null)
          NavigationDrawerActivity.access$502(NavigationDrawerActivity.this, paramIntent);
        NavigationDrawerActivity.this.drawerLayout.closeDrawer(8388611);
      }
    });
  }

  private void updateNavigationDrawer()
  {
    if (this.mNavViewsHolder == null)
      return;
    Context localContext = getApplicationContext();
    int i = getNavigationDrawerEntryId();
    setupNavigationEntry(i, this.mNavViewsHolder.homeNav, new Intent(localContext, HomeActivity.class));
    setupNavigationEntry(i, this.mNavViewsHolder.settingsNav, new Intent(localContext, SettingsActivity.class));
    setupNavigationEntry(i, this.mNavViewsHolder.helpNav, new Intent(localContext, SupportActivity.class));
    boolean bool = isUpdateAvailable();
    this.mNavViewsHolder.updateSettingsNavEntry(bool);
    enableNavigationAlertIcon(bool);
  }

  public void closeDrawer()
  {
    if (this.drawerLayout != null)
      this.drawerLayout.closeDrawer(8388611);
  }

  protected abstract void enableNavigationAlertIcon(boolean paramBoolean);

  protected abstract int getNavigationDrawerEntryId();

  protected boolean isGooglePlayServicesValid(boolean paramBoolean)
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
    if (i == 0);
    for (boolean bool = true; ; bool = false)
    {
      if ((!bool) && (paramBoolean))
      {
        Dialog localDialog = GooglePlayServicesUtil.getErrorDialog(i, this, 101, new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            NavigationDrawerActivity.this.finish();
          }
        });
        if (localDialog != null)
          localDialog.show();
      }
      return bool;
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.drawerLayout = ((DrawerLayout)getLayoutInflater().inflate(2130903073, null));
    this.navigationContentLayout = ((ViewGroup)this.drawerLayout.findViewById(2131493027));
    this.drawerLayout.setDrawerListener(this);
  }

  public void onDrawerClosed(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131493028:
    }
    do
      return;
    while (this.mNavigationIntent == null);
    startActivity(this.mNavigationIntent);
    this.mNavigationIntent = null;
  }

  public void onDrawerOpened(View paramView)
  {
  }

  public void onDrawerSlide(View paramView, float paramFloat)
  {
  }

  public void onDrawerStateChanged(int paramInt)
  {
  }

  public void onResume()
  {
    super.onResume();
    updateNavigationDrawer();
  }

  public void onStart()
  {
    super.onStart();
    this.lbm.registerReceiver(this.broadcastReceiver, intentFilter);
  }

  public void onStop()
  {
    super.onStop();
    this.lbm.unregisterReceiver(this.broadcastReceiver);
  }

  public void openDrawer()
  {
    if (this.drawerLayout != null)
      this.drawerLayout.openDrawer(8388611);
  }

  public void setContentView(int paramInt)
  {
    View localView = getLayoutInflater().inflate(paramInt, this.drawerLayout, false);
    this.navigationContentLayout.addView(localView);
    setContentView(this.drawerLayout);
    initNavigationDrawer();
  }

  private static class NavDrawerViewHolder
  {
    final TextView helpNav;
    final TextView homeNav;
    final TextView settingsNav;

    private NavDrawerViewHolder(View paramView)
    {
      this.homeNav = ((TextView)paramView.findViewById(2131493029));
      this.settingsNav = ((TextView)paramView.findViewById(2131493030));
      this.helpNav = ((TextView)paramView.findViewById(2131493031));
    }

    private void updateSettingsNavEntry(boolean paramBoolean)
    {
      TextView localTextView;
      if (this.settingsNav != null)
      {
        localTextView = this.settingsNav;
        if (!paramBoolean)
          break label29;
      }
      label29: for (int i = 2130837860; ; i = 0)
      {
        localTextView.setCompoundDrawablesWithIntrinsicBounds(2130837580, 0, i, 0);
        return;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.NavigationDrawerActivity
 * JD-Core Version:    0.6.2
 */