package com.o3dr.solo.android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.o3dr.solo.android.util.NetworkUtils;
import com.o3dr.solo.android.widget.NiceProgressView;

public class WifiConnectionProgress extends ActionBarActivity
{
  private static final int COUNTDOWN_TO_DISMISSAL = 1500;
  private static final IntentFilter intentFilter = new IntentFilter();
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      WifiConnectionProgress.this.handleIntent(paramAnonymousIntent);
    }
  };
  private ImageView connectionFailed;
  private NiceProgressView connectionProgress;
  private ImageView connectionSuccessful;
  private TextView dialogTitle;
  private final Runnable dismissDialog = new Runnable()
  {
    public void run()
    {
      WifiConnectionProgress.this.finish();
    }
  };
  private final Handler handler = new Handler();
  private TextView soloLinkLabel;

  static
  {
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
  }

  private void handleIntent(Intent paramIntent)
  {
    if (paramIntent == null)
      return;
    String str = paramIntent.getAction();
    int i = -1;
    switch (str.hashCode())
    {
    default:
    case 551871472:
    case 662782932:
    }
    while (true)
      switch (i)
      {
      default:
        return;
      case 0:
        this.handler.removeCallbacks(this.dismissDialog);
        this.dialogTitle.setText(2131099867);
        this.connectionSuccessful.setVisibility(0);
        this.connectionProgress.setVisibility(8);
        this.handler.postDelayed(this.dismissDialog, 1500L);
        return;
        if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
        {
          i = 0;
          continue;
          if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
            i = 1;
        }
        break;
      case 1:
      }
    this.dialogTitle.setText(2131099871);
    this.connectionFailed.setVisibility(0);
    this.connectionProgress.setVisibility(8);
    this.handler.postDelayed(this.dismissDialog, 1500L);
  }

  private void reset()
  {
    this.dialogTitle.setText(2131099868);
    this.soloLinkLabel.setText(NetworkUtils.getCurrentWifiLink(getApplicationContext()));
    this.connectionProgress.setVisibility(0);
    this.connectionFailed.setVisibility(8);
    this.connectionSuccessful.setVisibility(8);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903076);
    this.dialogTitle = ((TextView)findViewById(2131493038));
    this.soloLinkLabel = ((TextView)findViewById(2131492986));
    this.connectionProgress = ((NiceProgressView)findViewById(2131493039));
    this.connectionFailed = ((ImageView)findViewById(2131493041));
    this.connectionSuccessful = ((ImageView)findViewById(2131493040));
    reset();
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    reset();
  }

  public void onStart()
  {
    super.onStart();
    LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.broadcastReceiver, intentFilter);
  }

  public void onStop()
  {
    super.onStop();
    LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.broadcastReceiver);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.WifiConnectionProgress
 * JD-Core Version:    0.6.2
 */