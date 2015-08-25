package com.o3dr.solo.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmVehicleLink extends ActionBarActivity
{
  private TextView selectedSSID;

  private void handleIntent(Intent paramIntent)
  {
    if (paramIntent == null);
    String str;
    do
    {
      return;
      str = paramIntent.getStringExtra("extra_selected_solo_link");
    }
    while (TextUtils.isEmpty(str));
    this.selectedSSID.setText(str);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903068);
    this.selectedSSID = ((TextView)findViewById(2131492986));
    ((Button)findViewById(2131492988)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ConfirmVehicleLink.this.startActivity(new Intent(ConfirmVehicleLink.this.getApplicationContext(), FlightActivity.class));
        ConfirmVehicleLink.this.finish();
      }
    });
    ((Button)findViewById(2131492987)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ConfirmVehicleLink.this.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
        ConfirmVehicleLink.this.finish();
      }
    });
    handleIntent(getIntent());
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    handleIntent(paramIntent);
  }

  public void onStop()
  {
    super.onStop();
    finish();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.ConfirmVehicleLink
 * JD-Core Version:    0.6.2
 */