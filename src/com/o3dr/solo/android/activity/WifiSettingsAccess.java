package com.o3dr.solo.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WifiSettingsAccess extends ActionBarActivity
{
  public static final String EXTRA_BUTTON_TEXT = "extra_button_text";
  public static final String EXTRA_HOME_IMAGE = "extra_picture";
  public static final String EXTRA_MESSAGE = "extra_message";
  public static final String EXTRA_TITLE = "extra_title";
  private Button accessWifiButton;
  private TextView messageView;
  private TextView titleView;
  private ImageView wifiImage;

  private void handleIntent(Intent paramIntent)
  {
    if (paramIntent == null)
      return;
    String str1 = paramIntent.getStringExtra("extra_title");
    if (str1 == null)
      str1 = getString(2131100042);
    this.titleView.setText(str1);
    String str2 = paramIntent.getStringExtra("extra_message");
    if (str2 == null)
      str2 = getString(2131099773);
    this.messageView.setText(str2);
    String str3 = paramIntent.getStringExtra("extra_button_text");
    if (str3 == null)
      str3 = getString(2131099840);
    this.accessWifiButton.setText(str3);
    if (paramIntent.getBooleanExtra("extra_picture", false) == true)
    {
      this.wifiImage.setImageResource(2130837684);
      return;
    }
    this.wifiImage.setImageResource(2130837685);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903082);
    this.titleView = ((TextView)findViewById(2131493032));
    this.messageView = ((TextView)findViewById(2131493033));
    this.wifiImage = ((ImageView)findViewById(2131493064));
    this.accessWifiButton = ((Button)findViewById(2131492987));
    this.accessWifiButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WifiSettingsAccess.this.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
        WifiSettingsAccess.this.setResult(-1);
        WifiSettingsAccess.this.finish();
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
 * Qualified Name:     com.o3dr.solo.android.activity.WifiSettingsAccess
 * JD-Core Version:    0.6.2
 */