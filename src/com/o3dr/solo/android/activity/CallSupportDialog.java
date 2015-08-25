package com.o3dr.solo.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CallSupportDialog extends ActionBarActivity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903067);
    Button localButton1 = (Button)findViewById(2131492983);
    Button localButton2 = (Button)findViewById(2131492982);
    localButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent("android.intent.action.DIAL");
        localIntent.setData(Uri.parse(CallSupportDialog.this.getString(2131099944)));
        CallSupportDialog.this.startActivity(localIntent);
      }
    });
    localButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CallSupportDialog.this.finish();
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
 * Qualified Name:     com.o3dr.solo.android.activity.CallSupportDialog
 * JD-Core Version:    0.6.2
 */