package com.o3dr.solo.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReturnToSystemUpdate extends ActionBarActivity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903074);
    ((Button)findViewById(2131493034)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ReturnToSystemUpdate.this.startActivity(new Intent(ReturnToSystemUpdate.this.getApplicationContext(), SettingsActivity.class).putExtra("extra_settings_selected_pane_id", 2131493143));
        ReturnToSystemUpdate.this.finish();
      }
    });
    ((Button)findViewById(2131492987)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ReturnToSystemUpdate.this.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
        ReturnToSystemUpdate.this.finish();
      }
    });
  }

  public void onStop()
  {
    super.onStop();
    finish();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.ReturnToSystemUpdate
 * JD-Core Version:    0.6.2
 */