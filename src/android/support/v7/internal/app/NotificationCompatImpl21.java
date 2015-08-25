package android.support.v7.internal.app;

import android.app.Notification.MediaStyle;
import android.media.session.MediaSession.Token;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;

public class NotificationCompatImpl21
{
  public static void addMediaStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, int[] paramArrayOfInt, Object paramObject)
  {
    Notification.MediaStyle localMediaStyle = new Notification.MediaStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder());
    if (paramArrayOfInt != null)
      localMediaStyle.setShowActionsInCompactView(paramArrayOfInt);
    if (paramObject != null)
      localMediaStyle.setMediaSession((MediaSession.Token)paramObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.NotificationCompatImpl21
 * JD-Core Version:    0.6.2
 */