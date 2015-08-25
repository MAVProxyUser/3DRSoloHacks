package com.o3dr.solo.android.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.o3dr.solo.android.service.update.UpdateService;

public class DownloadsReceiver extends BroadcastReceiver
{
  public static void enableDownloadsReceiver(Context paramContext, boolean paramBoolean)
  {
    ComponentName localComponentName = new ComponentName(paramContext, DownloadsReceiver.class);
    if (paramBoolean);
    for (int i = 1; ; i = 2)
    {
      paramContext.getPackageManager().setComponentEnabledSetting(localComponentName, i, 1);
      return;
    }
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    int i = -1;
    switch (str.hashCode())
    {
    default:
      switch (i)
      {
      default:
      case 0:
      case 1:
      }
      break;
    case 1248865515:
    case -1828181659:
    }
    long[] arrayOfLong;
    do
    {
      long l;
      do
      {
        return;
        if (!str.equals("android.intent.action.DOWNLOAD_COMPLETE"))
          break;
        i = 0;
        break;
        if (!str.equals("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED"))
          break;
        i = 1;
        break;
        l = paramIntent.getLongExtra("extra_download_id", -1L);
      }
      while (l == -1L);
      paramContext.startService(new Intent(paramContext, UpdateService.class).setAction(str).putExtra("extra_download_id", l));
      return;
      arrayOfLong = paramIntent.getLongArrayExtra("extra_click_download_ids");
    }
    while (arrayOfLong == null);
    paramContext.startService(new Intent(paramContext, UpdateService.class).setAction(str).putExtra("extra_click_download_ids", arrayOfLong));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.receiver.DownloadsReceiver
 * JD-Core Version:    0.6.2
 */