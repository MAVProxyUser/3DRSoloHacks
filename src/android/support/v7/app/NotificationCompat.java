package android.support.v7.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat.BuilderExtender;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v7.internal.app.NotificationCompatImpl21;
import android.support.v7.internal.app.NotificationCompatImplBase;

public class NotificationCompat extends android.support.v4.app.NotificationCompat
{
  private static void addBigMediaStyleToBuilderJellybean(Notification paramNotification, android.support.v4.app.NotificationCompat.Builder paramBuilder)
  {
    if ((paramBuilder.mStyle instanceof MediaStyle))
    {
      MediaStyle localMediaStyle = (MediaStyle)paramBuilder.mStyle;
      NotificationCompatImplBase.overrideBigContentView(paramNotification, paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mNumber, paramBuilder.mLargeIcon, paramBuilder.mSubText, paramBuilder.mUseChronometer, paramBuilder.mNotification.when, paramBuilder.mActions, localMediaStyle.mShowCancelButton, localMediaStyle.mCancelButtonIntent);
    }
  }

  private static void addMediaStyleToBuilderIcs(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, android.support.v4.app.NotificationCompat.Builder paramBuilder)
  {
    if ((paramBuilder.mStyle instanceof MediaStyle))
    {
      MediaStyle localMediaStyle = (MediaStyle)paramBuilder.mStyle;
      NotificationCompatImplBase.overrideContentView(paramNotificationBuilderWithBuilderAccessor, paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mNumber, paramBuilder.mLargeIcon, paramBuilder.mSubText, paramBuilder.mUseChronometer, paramBuilder.mNotification.when, paramBuilder.mActions, localMediaStyle.mActionsToShowInCompact, localMediaStyle.mShowCancelButton, localMediaStyle.mCancelButtonIntent);
    }
  }

  private static void addMediaStyleToBuilderLollipop(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, NotificationCompat.Style paramStyle)
  {
    MediaStyle localMediaStyle;
    int[] arrayOfInt;
    if ((paramStyle instanceof MediaStyle))
    {
      localMediaStyle = (MediaStyle)paramStyle;
      arrayOfInt = localMediaStyle.mActionsToShowInCompact;
      if (localMediaStyle.mToken == null)
        break label41;
    }
    label41: for (Object localObject = localMediaStyle.mToken.getToken(); ; localObject = null)
    {
      NotificationCompatImpl21.addMediaStyle(paramNotificationBuilderWithBuilderAccessor, arrayOfInt, localObject);
      return;
    }
  }

  public static class Builder extends android.support.v4.app.NotificationCompat.Builder
  {
    public Builder(Context paramContext)
    {
      super();
    }

    protected NotificationCompat.BuilderExtender getExtender()
    {
      if (Build.VERSION.SDK_INT >= 21)
        return new NotificationCompat.LollipopExtender(null);
      if (Build.VERSION.SDK_INT >= 16)
        return new NotificationCompat.JellybeanExtender(null);
      if (Build.VERSION.SDK_INT >= 14)
        return new NotificationCompat.IceCreamSandwichExtender(null);
      return super.getExtender();
    }
  }

  private static class IceCreamSandwichExtender extends NotificationCompat.BuilderExtender
  {
    public Notification build(android.support.v4.app.NotificationCompat.Builder paramBuilder, NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      NotificationCompat.addMediaStyleToBuilderIcs(paramNotificationBuilderWithBuilderAccessor, paramBuilder);
      return paramNotificationBuilderWithBuilderAccessor.build();
    }
  }

  private static class JellybeanExtender extends NotificationCompat.BuilderExtender
  {
    public Notification build(android.support.v4.app.NotificationCompat.Builder paramBuilder, NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      NotificationCompat.addMediaStyleToBuilderIcs(paramNotificationBuilderWithBuilderAccessor, paramBuilder);
      Notification localNotification = paramNotificationBuilderWithBuilderAccessor.build();
      NotificationCompat.addBigMediaStyleToBuilderJellybean(localNotification, paramBuilder);
      return localNotification;
    }
  }

  private static class LollipopExtender extends NotificationCompat.BuilderExtender
  {
    public Notification build(android.support.v4.app.NotificationCompat.Builder paramBuilder, NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      NotificationCompat.addMediaStyleToBuilderLollipop(paramNotificationBuilderWithBuilderAccessor, paramBuilder.mStyle);
      return paramNotificationBuilderWithBuilderAccessor.build();
    }
  }

  public static class MediaStyle extends NotificationCompat.Style
  {
    int[] mActionsToShowInCompact = null;
    PendingIntent mCancelButtonIntent;
    boolean mShowCancelButton;
    MediaSessionCompat.Token mToken;

    public MediaStyle()
    {
    }

    public MediaStyle(android.support.v4.app.NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }

    public void setCancelButtonIntent(PendingIntent paramPendingIntent)
    {
      this.mCancelButtonIntent = paramPendingIntent;
    }

    public MediaStyle setMediaSession(MediaSessionCompat.Token paramToken)
    {
      this.mToken = paramToken;
      return this;
    }

    public MediaStyle setShowActionsInCompactView(int[] paramArrayOfInt)
    {
      this.mActionsToShowInCompact = paramArrayOfInt;
      return this;
    }

    public void setShowCancelButton(boolean paramBoolean)
    {
      this.mShowCancelButton = paramBoolean;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.app.NotificationCompat
 * JD-Core Version:    0.6.2
 */