package android.support.v7.internal.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v7.appcompat.R.dimen;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.integer;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.string;
import android.widget.RemoteViews;
import java.text.NumberFormat;
import java.util.List;

public class NotificationCompatImplBase
{
  static final int MAX_MEDIA_BUTTONS = 5;
  static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

  private static RemoteViews applyStandardTemplate(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt1, Bitmap paramBitmap, CharSequence paramCharSequence4, boolean paramBoolean1, long paramLong, int paramInt2, boolean paramBoolean2)
  {
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), paramInt2);
    int i;
    label95: int j;
    label153: int k;
    if ((paramBitmap != null) && (Build.VERSION.SDK_INT >= 16))
    {
      localRemoteViews.setImageViewBitmap(R.id.icon, paramBitmap);
      if (paramCharSequence1 != null)
        localRemoteViews.setTextViewText(R.id.title, paramCharSequence1);
      i = 0;
      if (paramCharSequence2 != null)
      {
        localRemoteViews.setTextViewText(R.id.text, paramCharSequence2);
        i = 1;
      }
      if (paramCharSequence3 == null)
        break label297;
      localRemoteViews.setTextViewText(R.id.info, paramCharSequence3);
      localRemoteViews.setViewVisibility(R.id.info, 0);
      i = 1;
      j = 0;
      if (paramCharSequence4 != null)
      {
        int n = Build.VERSION.SDK_INT;
        j = 0;
        if (n >= 16)
        {
          localRemoteViews.setTextViewText(R.id.text, paramCharSequence4);
          if (paramCharSequence2 == null)
            break label387;
          localRemoteViews.setTextViewText(R.id.text2, paramCharSequence2);
          localRemoteViews.setViewVisibility(R.id.text2, 0);
          j = 1;
        }
      }
      if ((j != 0) && (Build.VERSION.SDK_INT >= 16))
      {
        if (paramBoolean2)
        {
          float f = paramContext.getResources().getDimensionPixelSize(R.dimen.notification_subtext_size);
          localRemoteViews.setTextViewTextSize(R.id.text, 0, f);
        }
        localRemoteViews.setViewPadding(R.id.line1, 0, 0, 0, 0);
      }
      if (paramLong != 0L)
      {
        if (!paramBoolean1)
          break label403;
        localRemoteViews.setViewVisibility(R.id.chronometer, 0);
        localRemoteViews.setLong(R.id.chronometer, "setBase", paramLong + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
        localRemoteViews.setBoolean(R.id.chronometer, "setStarted", true);
      }
      label259: k = R.id.line3;
      if (i == 0)
        break label427;
    }
    label297: label427: for (int m = 0; ; m = 8)
    {
      localRemoteViews.setViewVisibility(k, m);
      return localRemoteViews;
      localRemoteViews.setViewVisibility(R.id.icon, 8);
      break;
      if (paramInt1 > 0)
      {
        if (paramInt1 > paramContext.getResources().getInteger(R.integer.status_bar_notification_info_maxnum))
          localRemoteViews.setTextViewText(R.id.info, paramContext.getResources().getString(R.string.status_bar_notification_info_overflow));
        while (true)
        {
          localRemoteViews.setViewVisibility(R.id.info, 0);
          i = 1;
          break;
          NumberFormat localNumberFormat = NumberFormat.getIntegerInstance();
          localRemoteViews.setTextViewText(R.id.info, localNumberFormat.format(paramInt1));
        }
      }
      localRemoteViews.setViewVisibility(R.id.info, 8);
      break label95;
      localRemoteViews.setViewVisibility(R.id.text2, 8);
      j = 0;
      break label153;
      localRemoteViews.setViewVisibility(R.id.time, 0);
      localRemoteViews.setLong(R.id.time, "setTime", paramLong);
      break label259;
    }
  }

  private static <T extends NotificationCompatBase.Action> RemoteViews generateBigContentView(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt, Bitmap paramBitmap, CharSequence paramCharSequence4, boolean paramBoolean1, long paramLong, List<T> paramList, boolean paramBoolean2, PendingIntent paramPendingIntent)
  {
    int i = Math.min(paramList.size(), 5);
    RemoteViews localRemoteViews1 = applyStandardTemplate(paramContext, paramCharSequence1, paramCharSequence2, paramCharSequence3, paramInt, paramBitmap, paramCharSequence4, paramBoolean1, paramLong, getBigLayoutResource(i), false);
    localRemoteViews1.removeAllViews(R.id.media_actions);
    if (i > 0)
      for (int j = 0; j < i; j++)
      {
        RemoteViews localRemoteViews2 = generateMediaActionButton(paramContext, (NotificationCompatBase.Action)paramList.get(j));
        localRemoteViews1.addView(R.id.media_actions, localRemoteViews2);
      }
    if (paramBoolean2)
    {
      localRemoteViews1.setViewVisibility(R.id.cancel_action, 0);
      localRemoteViews1.setInt(R.id.cancel_action, "setAlpha", paramContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
      localRemoteViews1.setOnClickPendingIntent(R.id.cancel_action, paramPendingIntent);
      return localRemoteViews1;
    }
    localRemoteViews1.setViewVisibility(R.id.cancel_action, 8);
    return localRemoteViews1;
  }

  private static <T extends NotificationCompatBase.Action> RemoteViews generateContentView(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt, Bitmap paramBitmap, CharSequence paramCharSequence4, boolean paramBoolean1, long paramLong, List<T> paramList, int[] paramArrayOfInt, boolean paramBoolean2, PendingIntent paramPendingIntent)
  {
    RemoteViews localRemoteViews1 = applyStandardTemplate(paramContext, paramCharSequence1, paramCharSequence2, paramCharSequence3, paramInt, paramBitmap, paramCharSequence4, paramBoolean1, paramLong, R.layout.notification_template_media, true);
    int i = paramList.size();
    int j;
    if (paramArrayOfInt == null)
    {
      j = 0;
      localRemoteViews1.removeAllViews(R.id.media_actions);
      if (j <= 0);
    }
    else
    {
      for (int k = 0; ; k++)
      {
        if (k >= j)
          break label160;
        if (k >= i)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(k);
          arrayOfObject[1] = Integer.valueOf(i - 1);
          throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", arrayOfObject));
          j = Math.min(paramArrayOfInt.length, 3);
          break;
        }
        RemoteViews localRemoteViews2 = generateMediaActionButton(paramContext, (NotificationCompatBase.Action)paramList.get(paramArrayOfInt[k]));
        localRemoteViews1.addView(R.id.media_actions, localRemoteViews2);
      }
    }
    label160: if (paramBoolean2)
    {
      localRemoteViews1.setViewVisibility(R.id.end_padder, 8);
      localRemoteViews1.setViewVisibility(R.id.cancel_action, 0);
      localRemoteViews1.setOnClickPendingIntent(R.id.cancel_action, paramPendingIntent);
      localRemoteViews1.setInt(R.id.cancel_action, "setAlpha", paramContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
      return localRemoteViews1;
    }
    localRemoteViews1.setViewVisibility(R.id.end_padder, 0);
    localRemoteViews1.setViewVisibility(R.id.cancel_action, 8);
    return localRemoteViews1;
  }

  private static RemoteViews generateMediaActionButton(Context paramContext, NotificationCompatBase.Action paramAction)
  {
    if (paramAction.getActionIntent() == null);
    for (int i = 1; ; i = 0)
    {
      RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), R.layout.notification_media_action);
      localRemoteViews.setImageViewResource(R.id.action0, paramAction.getIcon());
      if (i == 0)
        localRemoteViews.setOnClickPendingIntent(R.id.action0, paramAction.getActionIntent());
      localRemoteViews.setContentDescription(R.id.action0, paramAction.getTitle());
      return localRemoteViews;
    }
  }

  private static int getBigLayoutResource(int paramInt)
  {
    if (paramInt <= 3)
      return R.layout.notification_template_big_media_narrow;
    return R.layout.notification_template_big_media;
  }

  public static <T extends NotificationCompatBase.Action> void overrideBigContentView(Notification paramNotification, Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt, Bitmap paramBitmap, CharSequence paramCharSequence4, boolean paramBoolean1, long paramLong, List<T> paramList, boolean paramBoolean2, PendingIntent paramPendingIntent)
  {
    paramNotification.bigContentView = generateBigContentView(paramContext, paramCharSequence1, paramCharSequence2, paramCharSequence3, paramInt, paramBitmap, paramCharSequence4, paramBoolean1, paramLong, paramList, paramBoolean2, paramPendingIntent);
    if (paramBoolean2)
      paramNotification.flags = (0x2 | paramNotification.flags);
  }

  public static <T extends NotificationCompatBase.Action> void overrideContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt, Bitmap paramBitmap, CharSequence paramCharSequence4, boolean paramBoolean1, long paramLong, List<T> paramList, int[] paramArrayOfInt, boolean paramBoolean2, PendingIntent paramPendingIntent)
  {
    RemoteViews localRemoteViews = generateContentView(paramContext, paramCharSequence1, paramCharSequence2, paramCharSequence3, paramInt, paramBitmap, paramCharSequence4, paramBoolean1, paramLong, paramList, paramArrayOfInt, paramBoolean2, paramPendingIntent);
    paramNotificationBuilderWithBuilderAccessor.getBuilder().setContent(localRemoteViews);
    if (paramBoolean2)
      paramNotificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.NotificationCompatImplBase
 * JD-Core Version:    0.6.2
 */