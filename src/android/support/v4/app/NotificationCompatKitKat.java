package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

class NotificationCompatKitKat
{
  public static NotificationCompatBase.Action getAction(Notification paramNotification, int paramInt, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    Notification.Action localAction = paramNotification.actions[paramInt];
    SparseArray localSparseArray = paramNotification.extras.getSparseParcelableArray("android.support.actionExtras");
    Bundle localBundle = null;
    if (localSparseArray != null)
      localBundle = (Bundle)localSparseArray.get(paramInt);
    return NotificationCompatJellybean.readAction(paramFactory, paramFactory1, localAction.icon, localAction.title, localAction.actionIntent, localBundle);
  }

  public static int getActionCount(Notification paramNotification)
  {
    if (paramNotification.actions != null)
      return paramNotification.actions.length;
    return 0;
  }

  public static Bundle getExtras(Notification paramNotification)
  {
    return paramNotification.extras;
  }

  public static String getGroup(Notification paramNotification)
  {
    return paramNotification.extras.getString("android.support.groupKey");
  }

  public static boolean getLocalOnly(Notification paramNotification)
  {
    return paramNotification.extras.getBoolean("android.support.localOnly");
  }

  public static String getSortKey(Notification paramNotification)
  {
    return paramNotification.extras.getString("android.support.sortKey");
  }

  public static boolean isGroupSummary(Notification paramNotification)
  {
    return paramNotification.extras.getBoolean("android.support.isGroupSummary");
  }

  public static class Builder
    implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions
  {
    private Notification.Builder b;
    private List<Bundle> mActionExtrasList = new ArrayList();
    private Bundle mExtras;

    public Builder(Context paramContext, Notification paramNotification, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean4, ArrayList<String> paramArrayList, Bundle paramBundle, String paramString1, boolean paramBoolean5, String paramString2)
    {
      Notification.Builder localBuilder1 = new Notification.Builder(paramContext).setWhen(paramNotification.when).setShowWhen(paramBoolean2).setSmallIcon(paramNotification.icon, paramNotification.iconLevel).setContent(paramNotification.contentView).setTicker(paramNotification.tickerText, paramRemoteViews).setSound(paramNotification.sound, paramNotification.audioStreamType).setVibrate(paramNotification.vibrate).setLights(paramNotification.ledARGB, paramNotification.ledOnMS, paramNotification.ledOffMS);
      boolean bool1;
      boolean bool2;
      label131: boolean bool3;
      label153: boolean bool4;
      if ((0x2 & paramNotification.flags) != 0)
      {
        bool1 = true;
        Notification.Builder localBuilder2 = localBuilder1.setOngoing(bool1);
        if ((0x8 & paramNotification.flags) == 0)
          break label388;
        bool2 = true;
        Notification.Builder localBuilder3 = localBuilder2.setOnlyAlertOnce(bool2);
        if ((0x10 & paramNotification.flags) == 0)
          break label394;
        bool3 = true;
        Notification.Builder localBuilder4 = localBuilder3.setAutoCancel(bool3).setDefaults(paramNotification.defaults).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setSubText(paramCharSequence4).setContentInfo(paramCharSequence3).setContentIntent(paramPendingIntent1).setDeleteIntent(paramNotification.deleteIntent);
        if ((0x80 & paramNotification.flags) == 0)
          break label400;
        bool4 = true;
        label214: this.b = localBuilder4.setFullScreenIntent(paramPendingIntent2, bool4).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean3).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1);
        this.mExtras = new Bundle();
        if (paramBundle != null)
          this.mExtras.putAll(paramBundle);
        if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
          this.mExtras.putStringArray("android.people", (String[])paramArrayList.toArray(new String[paramArrayList.size()]));
        if (paramBoolean4)
          this.mExtras.putBoolean("android.support.localOnly", true);
        if (paramString1 != null)
        {
          this.mExtras.putString("android.support.groupKey", paramString1);
          if (!paramBoolean5)
            break label406;
          this.mExtras.putBoolean("android.support.isGroupSummary", true);
        }
      }
      while (true)
      {
        if (paramString2 != null)
          this.mExtras.putString("android.support.sortKey", paramString2);
        return;
        bool1 = false;
        break;
        label388: bool2 = false;
        break label131;
        label394: bool3 = false;
        break label153;
        label400: bool4 = false;
        break label214;
        label406: this.mExtras.putBoolean("android.support.useSideChannel", true);
      }
    }

    public void addAction(NotificationCompatBase.Action paramAction)
    {
      this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.b, paramAction));
    }

    public Notification build()
    {
      SparseArray localSparseArray = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
      if (localSparseArray != null)
        this.mExtras.putSparseParcelableArray("android.support.actionExtras", localSparseArray);
      this.b.setExtras(this.mExtras);
      return this.b.build();
    }

    public Notification.Builder getBuilder()
    {
      return this.b;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.NotificationCompatKitKat
 * JD-Core Version:    0.6.2
 */