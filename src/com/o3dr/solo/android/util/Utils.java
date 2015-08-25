package com.o3dr.solo.android.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import android.support.v4.app.NotificationCompat.Builder;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.Parameter;
import com.o3dr.services.android.lib.drone.property.Parameters;
import com.o3dr.solo.android.fragment.settings.solo.SoloAltitudeLimitFragment;
import java.io.IOException;
import java.io.Reader;

public class Utils
{
  public static Notification generateAppNotification(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, PendingIntent paramPendingIntent)
  {
    return new NotificationCompat.Builder(paramContext).setSmallIcon(2130837579).setAutoCancel(true).setOnlyAlertOnce(true).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setContentIntent(paramPendingIntent).build();
  }

  public static double getAltitudeLimit(Drone paramDrone)
  {
    if (paramDrone == null);
    Parameter localParameter;
    do
    {
      Parameters localParameters;
      do
      {
        return 0.0D;
        localParameters = (Parameters)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.PARAMETERS");
      }
      while (localParameters == null);
      localParameter = localParameters.getParameter(SoloAltitudeLimitFragment.getAltitudeLimitParameterName());
    }
    while (localParameter == null);
    return localParameter.getValue();
  }

  public static String readAll(Reader paramReader)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    while (true)
    {
      int i = paramReader.read();
      if (i == -1)
        break;
      localStringBuilder.append((char)i);
    }
    return localStringBuilder.toString();
  }

  public static boolean runningOnMainThread()
  {
    return Looper.myLooper() == Looper.getMainLooper();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.Utils
 * JD-Core Version:    0.6.2
 */