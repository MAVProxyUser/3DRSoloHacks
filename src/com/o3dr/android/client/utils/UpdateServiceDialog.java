package com.o3dr.android.client.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.o3dr.android.client.R.id;
import com.o3dr.android.client.R.layout;

public class UpdateServiceDialog extends FragmentActivity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_update_service_dialog);
    ((Button)findViewById(R.id.dialog_cancel_button)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UpdateServiceDialog.this.finish();
      }
    });
    ((Button)findViewById(R.id.dialog_update_button)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UpdateServiceDialog.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.droidplanner.services.android")));
        UpdateServiceDialog.this.finish();
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.utils.UpdateServiceDialog
 * JD-Core Version:    0.6.2
 */