package com.o3dr.android.client.data.tlog;

import android.app.Activity;
import android.content.Intent;

public class TLogPicker
{
  public static void startTLogPicker(Activity paramActivity, int paramInt)
  {
    if (paramActivity == null)
      return;
    paramActivity.startActivityForResult(new Intent("com.o3dr.services.android.provider.action.REQUEST_TLOG_FILE").putExtra("com.o3dr.services.android.provider.extra.REQUEST_TLOG_APP_ID", paramActivity.getPackageName()).setType("application/octet-stream"), paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.data.tlog.TLogPicker
 * JD-Core Version:    0.6.2
 */