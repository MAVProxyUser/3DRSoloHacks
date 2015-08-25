package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NotificationCompat
{
  public static final String CATEGORY_ALARM = "alarm";
  public static final String CATEGORY_CALL = "call";
  public static final String CATEGORY_EMAIL = "email";
  public static final String CATEGORY_ERROR = "err";
  public static final String CATEGORY_EVENT = "event";
  public static final String CATEGORY_MESSAGE = "msg";
  public static final String CATEGORY_PROGRESS = "progress";
  public static final String CATEGORY_PROMO = "promo";
  public static final String CATEGORY_RECOMMENDATION = "recommendation";
  public static final String CATEGORY_SERVICE = "service";
  public static final String CATEGORY_SOCIAL = "social";
  public static final String CATEGORY_STATUS = "status";
  public static final String CATEGORY_SYSTEM = "sys";
  public static final String CATEGORY_TRANSPORT = "transport";
  public static final int COLOR_DEFAULT = 0;
  public static final int DEFAULT_ALL = -1;
  public static final int DEFAULT_LIGHTS = 4;
  public static final int DEFAULT_SOUND = 1;
  public static final int DEFAULT_VIBRATE = 2;
  public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
  public static final String EXTRA_BIG_TEXT = "android.bigText";
  public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
  public static final String EXTRA_INFO_TEXT = "android.infoText";
  public static final String EXTRA_LARGE_ICON = "android.largeIcon";
  public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
  public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
  public static final String EXTRA_PEOPLE = "android.people";
  public static final String EXTRA_PICTURE = "android.picture";
  public static final String EXTRA_PROGRESS = "android.progress";
  public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
  public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
  public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
  public static final String EXTRA_SHOW_WHEN = "android.showWhen";
  public static final String EXTRA_SMALL_ICON = "android.icon";
  public static final String EXTRA_SUB_TEXT = "android.subText";
  public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
  public static final String EXTRA_TEMPLATE = "android.template";
  public static final String EXTRA_TEXT = "android.text";
  public static final String EXTRA_TEXT_LINES = "android.textLines";
  public static final String EXTRA_TITLE = "android.title";
  public static final String EXTRA_TITLE_BIG = "android.title.big";
  public static final int FLAG_AUTO_CANCEL = 16;
  public static final int FLAG_FOREGROUND_SERVICE = 64;
  public static final int FLAG_GROUP_SUMMARY = 512;
  public static final int FLAG_HIGH_PRIORITY = 128;
  public static final int FLAG_INSISTENT = 4;
  public static final int FLAG_LOCAL_ONLY = 256;
  public static final int FLAG_NO_CLEAR = 32;
  public static final int FLAG_ONGOING_EVENT = 2;
  public static final int FLAG_ONLY_ALERT_ONCE = 8;
  public static final int FLAG_SHOW_LIGHTS = 1;
  private static final NotificationCompatImpl IMPL = new NotificationCompatImplBase();
  public static final int PRIORITY_DEFAULT = 0;
  public static final int PRIORITY_HIGH = 1;
  public static final int PRIORITY_LOW = -1;
  public static final int PRIORITY_MAX = 2;
  public static final int PRIORITY_MIN = -2;
  public static final int STREAM_DEFAULT = -1;
  public static final int VISIBILITY_PRIVATE = 0;
  public static final int VISIBILITY_PUBLIC = 1;
  public static final int VISIBILITY_SECRET = -1;

  static
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      IMPL = new NotificationCompatImplApi21();
      return;
    }
    if (Build.VERSION.SDK_INT >= 20)
    {
      IMPL = new NotificationCompatImplApi20();
      return;
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      IMPL = new NotificationCompatImplKitKat();
      return;
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      IMPL = new NotificationCompatImplJellybean();
      return;
    }
    if (Build.VERSION.SDK_INT >= 14)
    {
      IMPL = new NotificationCompatImplIceCreamSandwich();
      return;
    }
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new NotificationCompatImplHoneycomb();
      return;
    }
    if (Build.VERSION.SDK_INT >= 9)
    {
      IMPL = new NotificationCompatImplGingerbread();
      return;
    }
  }

  private static void addActionsToBuilder(NotificationBuilderWithActions paramNotificationBuilderWithActions, ArrayList<Action> paramArrayList)
  {
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
      paramNotificationBuilderWithActions.addAction((Action)localIterator.next());
  }

  private static void addStyleToBuilderJellybean(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, Style paramStyle)
  {
    if (paramStyle != null)
    {
      if (!(paramStyle instanceof BigTextStyle))
        break label42;
      BigTextStyle localBigTextStyle = (BigTextStyle)paramStyle;
      NotificationCompatJellybean.addBigTextStyle(paramNotificationBuilderWithBuilderAccessor, localBigTextStyle.mBigContentTitle, localBigTextStyle.mSummaryTextSet, localBigTextStyle.mSummaryText, localBigTextStyle.mBigText);
    }
    label42: 
    do
    {
      return;
      if ((paramStyle instanceof InboxStyle))
      {
        InboxStyle localInboxStyle = (InboxStyle)paramStyle;
        NotificationCompatJellybean.addInboxStyle(paramNotificationBuilderWithBuilderAccessor, localInboxStyle.mBigContentTitle, localInboxStyle.mSummaryTextSet, localInboxStyle.mSummaryText, localInboxStyle.mTexts);
        return;
      }
    }
    while (!(paramStyle instanceof BigPictureStyle));
    BigPictureStyle localBigPictureStyle = (BigPictureStyle)paramStyle;
    NotificationCompatJellybean.addBigPictureStyle(paramNotificationBuilderWithBuilderAccessor, localBigPictureStyle.mBigContentTitle, localBigPictureStyle.mSummaryTextSet, localBigPictureStyle.mSummaryText, localBigPictureStyle.mPicture, localBigPictureStyle.mBigLargeIcon, localBigPictureStyle.mBigLargeIconSet);
  }

  public static Action getAction(Notification paramNotification, int paramInt)
  {
    return IMPL.getAction(paramNotification, paramInt);
  }

  public static int getActionCount(Notification paramNotification)
  {
    return IMPL.getActionCount(paramNotification);
  }

  public static String getCategory(Notification paramNotification)
  {
    return IMPL.getCategory(paramNotification);
  }

  public static Bundle getExtras(Notification paramNotification)
  {
    return IMPL.getExtras(paramNotification);
  }

  public static String getGroup(Notification paramNotification)
  {
    return IMPL.getGroup(paramNotification);
  }

  public static boolean getLocalOnly(Notification paramNotification)
  {
    return IMPL.getLocalOnly(paramNotification);
  }

  private static Notification[] getNotificationArrayFromBundle(Bundle paramBundle, String paramString)
  {
    Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray(paramString);
    if (((arrayOfParcelable instanceof Notification[])) || (arrayOfParcelable == null))
      return (Notification[])arrayOfParcelable;
    Notification[] arrayOfNotification = new Notification[arrayOfParcelable.length];
    for (int i = 0; i < arrayOfParcelable.length; i++)
      arrayOfNotification[i] = ((Notification)arrayOfParcelable[i]);
    paramBundle.putParcelableArray(paramString, arrayOfNotification);
    return arrayOfNotification;
  }

  public static String getSortKey(Notification paramNotification)
  {
    return IMPL.getSortKey(paramNotification);
  }

  public static boolean isGroupSummary(Notification paramNotification)
  {
    return IMPL.isGroupSummary(paramNotification);
  }

  public static class Action extends NotificationCompatBase.Action
  {
    public static final NotificationCompatBase.Action.Factory FACTORY = new NotificationCompatBase.Action.Factory()
    {
      public NotificationCompat.Action build(int paramAnonymousInt, CharSequence paramAnonymousCharSequence, PendingIntent paramAnonymousPendingIntent, Bundle paramAnonymousBundle, RemoteInputCompatBase.RemoteInput[] paramAnonymousArrayOfRemoteInput)
      {
        return new NotificationCompat.Action(paramAnonymousInt, paramAnonymousCharSequence, paramAnonymousPendingIntent, paramAnonymousBundle, (RemoteInput[])paramAnonymousArrayOfRemoteInput, null);
      }

      public NotificationCompat.Action[] newArray(int paramAnonymousInt)
      {
        return new NotificationCompat.Action[paramAnonymousInt];
      }
    };
    public PendingIntent actionIntent;
    public int icon;
    private final Bundle mExtras;
    private final RemoteInput[] mRemoteInputs;
    public CharSequence title;

    public Action(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
    {
      this(paramInt, paramCharSequence, paramPendingIntent, new Bundle(), null);
    }

    private Action(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle, RemoteInput[] paramArrayOfRemoteInput)
    {
      this.icon = paramInt;
      this.title = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.actionIntent = paramPendingIntent;
      if (paramBundle != null);
      while (true)
      {
        this.mExtras = paramBundle;
        this.mRemoteInputs = paramArrayOfRemoteInput;
        return;
        paramBundle = new Bundle();
      }
    }

    public PendingIntent getActionIntent()
    {
      return this.actionIntent;
    }

    public Bundle getExtras()
    {
      return this.mExtras;
    }

    public int getIcon()
    {
      return this.icon;
    }

    public RemoteInput[] getRemoteInputs()
    {
      return this.mRemoteInputs;
    }

    public CharSequence getTitle()
    {
      return this.title;
    }

    public static final class Builder
    {
      private final Bundle mExtras;
      private final int mIcon;
      private final PendingIntent mIntent;
      private ArrayList<RemoteInput> mRemoteInputs;
      private final CharSequence mTitle;

      public Builder(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
      {
        this(paramInt, paramCharSequence, paramPendingIntent, new Bundle());
      }

      private Builder(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle)
      {
        this.mIcon = paramInt;
        this.mTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
        this.mIntent = paramPendingIntent;
        this.mExtras = paramBundle;
      }

      public Builder(NotificationCompat.Action paramAction)
      {
        this(paramAction.icon, paramAction.title, paramAction.actionIntent, new Bundle(paramAction.mExtras));
      }

      public Builder addExtras(Bundle paramBundle)
      {
        if (paramBundle != null)
          this.mExtras.putAll(paramBundle);
        return this;
      }

      public Builder addRemoteInput(RemoteInput paramRemoteInput)
      {
        if (this.mRemoteInputs == null)
          this.mRemoteInputs = new ArrayList();
        this.mRemoteInputs.add(paramRemoteInput);
        return this;
      }

      public NotificationCompat.Action build()
      {
        if (this.mRemoteInputs != null);
        for (RemoteInput[] arrayOfRemoteInput = (RemoteInput[])this.mRemoteInputs.toArray(new RemoteInput[this.mRemoteInputs.size()]); ; arrayOfRemoteInput = null)
          return new NotificationCompat.Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrayOfRemoteInput, null);
      }

      public Builder extend(NotificationCompat.Action.Extender paramExtender)
      {
        paramExtender.extend(this);
        return this;
      }

      public Bundle getExtras()
      {
        return this.mExtras;
      }
    }

    public static abstract interface Extender
    {
      public abstract NotificationCompat.Action.Builder extend(NotificationCompat.Action.Builder paramBuilder);
    }

    public static final class WearableExtender
      implements NotificationCompat.Action.Extender
    {
      private static final int DEFAULT_FLAGS = 1;
      private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
      private static final int FLAG_AVAILABLE_OFFLINE = 1;
      private static final String KEY_CANCEL_LABEL = "cancelLabel";
      private static final String KEY_CONFIRM_LABEL = "confirmLabel";
      private static final String KEY_FLAGS = "flags";
      private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
      private CharSequence mCancelLabel;
      private CharSequence mConfirmLabel;
      private int mFlags = 1;
      private CharSequence mInProgressLabel;

      public WearableExtender()
      {
      }

      public WearableExtender(NotificationCompat.Action paramAction)
      {
        Bundle localBundle = paramAction.getExtras().getBundle("android.wearable.EXTENSIONS");
        if (localBundle != null)
        {
          this.mFlags = localBundle.getInt("flags", 1);
          this.mInProgressLabel = localBundle.getCharSequence("inProgressLabel");
          this.mConfirmLabel = localBundle.getCharSequence("confirmLabel");
          this.mCancelLabel = localBundle.getCharSequence("cancelLabel");
        }
      }

      private void setFlag(int paramInt, boolean paramBoolean)
      {
        if (paramBoolean)
        {
          this.mFlags = (paramInt | this.mFlags);
          return;
        }
        this.mFlags &= (paramInt ^ 0xFFFFFFFF);
      }

      public WearableExtender clone()
      {
        WearableExtender localWearableExtender = new WearableExtender();
        localWearableExtender.mFlags = this.mFlags;
        localWearableExtender.mInProgressLabel = this.mInProgressLabel;
        localWearableExtender.mConfirmLabel = this.mConfirmLabel;
        localWearableExtender.mCancelLabel = this.mCancelLabel;
        return localWearableExtender;
      }

      public NotificationCompat.Action.Builder extend(NotificationCompat.Action.Builder paramBuilder)
      {
        Bundle localBundle = new Bundle();
        if (this.mFlags != 1)
          localBundle.putInt("flags", this.mFlags);
        if (this.mInProgressLabel != null)
          localBundle.putCharSequence("inProgressLabel", this.mInProgressLabel);
        if (this.mConfirmLabel != null)
          localBundle.putCharSequence("confirmLabel", this.mConfirmLabel);
        if (this.mCancelLabel != null)
          localBundle.putCharSequence("cancelLabel", this.mCancelLabel);
        paramBuilder.getExtras().putBundle("android.wearable.EXTENSIONS", localBundle);
        return paramBuilder;
      }

      public CharSequence getCancelLabel()
      {
        return this.mCancelLabel;
      }

      public CharSequence getConfirmLabel()
      {
        return this.mConfirmLabel;
      }

      public CharSequence getInProgressLabel()
      {
        return this.mInProgressLabel;
      }

      public boolean isAvailableOffline()
      {
        return (0x1 & this.mFlags) != 0;
      }

      public WearableExtender setAvailableOffline(boolean paramBoolean)
      {
        setFlag(1, paramBoolean);
        return this;
      }

      public WearableExtender setCancelLabel(CharSequence paramCharSequence)
      {
        this.mCancelLabel = paramCharSequence;
        return this;
      }

      public WearableExtender setConfirmLabel(CharSequence paramCharSequence)
      {
        this.mConfirmLabel = paramCharSequence;
        return this;
      }

      public WearableExtender setInProgressLabel(CharSequence paramCharSequence)
      {
        this.mInProgressLabel = paramCharSequence;
        return this;
      }
    }
  }

  public static class BigPictureStyle extends NotificationCompat.Style
  {
    Bitmap mBigLargeIcon;
    boolean mBigLargeIconSet;
    Bitmap mPicture;

    public BigPictureStyle()
    {
    }

    public BigPictureStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }

    public BigPictureStyle bigLargeIcon(Bitmap paramBitmap)
    {
      this.mBigLargeIcon = paramBitmap;
      this.mBigLargeIconSet = true;
      return this;
    }

    public BigPictureStyle bigPicture(Bitmap paramBitmap)
    {
      this.mPicture = paramBitmap;
      return this;
    }

    public BigPictureStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public BigPictureStyle setSummaryText(CharSequence paramCharSequence)
    {
      this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.mSummaryTextSet = true;
      return this;
    }
  }

  public static class BigTextStyle extends NotificationCompat.Style
  {
    CharSequence mBigText;

    public BigTextStyle()
    {
    }

    public BigTextStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }

    public BigTextStyle bigText(CharSequence paramCharSequence)
    {
      this.mBigText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public BigTextStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public BigTextStyle setSummaryText(CharSequence paramCharSequence)
    {
      this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.mSummaryTextSet = true;
      return this;
    }
  }

  public static class Builder
  {
    private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
    public ArrayList<NotificationCompat.Action> mActions = new ArrayList();
    String mCategory;
    int mColor = 0;
    public CharSequence mContentInfo;
    PendingIntent mContentIntent;
    public CharSequence mContentText;
    public CharSequence mContentTitle;
    public Context mContext;
    Bundle mExtras;
    PendingIntent mFullScreenIntent;
    String mGroupKey;
    boolean mGroupSummary;
    public Bitmap mLargeIcon;
    boolean mLocalOnly = false;
    public Notification mNotification = new Notification();
    public int mNumber;
    public ArrayList<String> mPeople;
    int mPriority;
    int mProgress;
    boolean mProgressIndeterminate;
    int mProgressMax;
    Notification mPublicVersion;
    boolean mShowWhen = true;
    String mSortKey;
    public NotificationCompat.Style mStyle;
    public CharSequence mSubText;
    RemoteViews mTickerView;
    public boolean mUseChronometer;
    int mVisibility = 0;

    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.mNotification.when = System.currentTimeMillis();
      this.mNotification.audioStreamType = -1;
      this.mPriority = 0;
      this.mPeople = new ArrayList();
    }

    protected static CharSequence limitCharSequenceLength(CharSequence paramCharSequence)
    {
      if (paramCharSequence == null);
      while (paramCharSequence.length() <= 5120)
        return paramCharSequence;
      return paramCharSequence.subSequence(0, 5120);
    }

    private void setFlag(int paramInt, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        Notification localNotification2 = this.mNotification;
        localNotification2.flags = (paramInt | localNotification2.flags);
        return;
      }
      Notification localNotification1 = this.mNotification;
      localNotification1.flags &= (paramInt ^ 0xFFFFFFFF);
    }

    public Builder addAction(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
    {
      this.mActions.add(new NotificationCompat.Action(paramInt, paramCharSequence, paramPendingIntent));
      return this;
    }

    public Builder addAction(NotificationCompat.Action paramAction)
    {
      this.mActions.add(paramAction);
      return this;
    }

    public Builder addExtras(Bundle paramBundle)
    {
      if (paramBundle != null)
      {
        if (this.mExtras == null)
          this.mExtras = new Bundle(paramBundle);
      }
      else
        return this;
      this.mExtras.putAll(paramBundle);
      return this;
    }

    public Builder addPerson(String paramString)
    {
      this.mPeople.add(paramString);
      return this;
    }

    public Notification build()
    {
      return NotificationCompat.IMPL.build(this, getExtender());
    }

    public Builder extend(NotificationCompat.Extender paramExtender)
    {
      paramExtender.extend(this);
      return this;
    }

    protected NotificationCompat.BuilderExtender getExtender()
    {
      return new NotificationCompat.BuilderExtender();
    }

    public Bundle getExtras()
    {
      if (this.mExtras == null)
        this.mExtras = new Bundle();
      return this.mExtras;
    }

    @Deprecated
    public Notification getNotification()
    {
      return build();
    }

    public Builder setAutoCancel(boolean paramBoolean)
    {
      setFlag(16, paramBoolean);
      return this;
    }

    public Builder setCategory(String paramString)
    {
      this.mCategory = paramString;
      return this;
    }

    public Builder setColor(int paramInt)
    {
      this.mColor = paramInt;
      return this;
    }

    public Builder setContent(RemoteViews paramRemoteViews)
    {
      this.mNotification.contentView = paramRemoteViews;
      return this;
    }

    public Builder setContentInfo(CharSequence paramCharSequence)
    {
      this.mContentInfo = limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public Builder setContentIntent(PendingIntent paramPendingIntent)
    {
      this.mContentIntent = paramPendingIntent;
      return this;
    }

    public Builder setContentText(CharSequence paramCharSequence)
    {
      this.mContentText = limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public Builder setContentTitle(CharSequence paramCharSequence)
    {
      this.mContentTitle = limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public Builder setDefaults(int paramInt)
    {
      this.mNotification.defaults = paramInt;
      if ((paramInt & 0x4) != 0)
      {
        Notification localNotification = this.mNotification;
        localNotification.flags = (0x1 | localNotification.flags);
      }
      return this;
    }

    public Builder setDeleteIntent(PendingIntent paramPendingIntent)
    {
      this.mNotification.deleteIntent = paramPendingIntent;
      return this;
    }

    public Builder setExtras(Bundle paramBundle)
    {
      this.mExtras = paramBundle;
      return this;
    }

    public Builder setFullScreenIntent(PendingIntent paramPendingIntent, boolean paramBoolean)
    {
      this.mFullScreenIntent = paramPendingIntent;
      setFlag(128, paramBoolean);
      return this;
    }

    public Builder setGroup(String paramString)
    {
      this.mGroupKey = paramString;
      return this;
    }

    public Builder setGroupSummary(boolean paramBoolean)
    {
      this.mGroupSummary = paramBoolean;
      return this;
    }

    public Builder setLargeIcon(Bitmap paramBitmap)
    {
      this.mLargeIcon = paramBitmap;
      return this;
    }

    public Builder setLights(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = 1;
      this.mNotification.ledARGB = paramInt1;
      this.mNotification.ledOnMS = paramInt2;
      this.mNotification.ledOffMS = paramInt3;
      int j;
      Notification localNotification;
      int k;
      if ((this.mNotification.ledOnMS != 0) && (this.mNotification.ledOffMS != 0))
      {
        j = i;
        localNotification = this.mNotification;
        k = 0xFFFFFFFE & this.mNotification.flags;
        if (j == 0)
          break label92;
      }
      while (true)
      {
        localNotification.flags = (i | k);
        return this;
        j = 0;
        break;
        label92: i = 0;
      }
    }

    public Builder setLocalOnly(boolean paramBoolean)
    {
      this.mLocalOnly = paramBoolean;
      return this;
    }

    public Builder setNumber(int paramInt)
    {
      this.mNumber = paramInt;
      return this;
    }

    public Builder setOngoing(boolean paramBoolean)
    {
      setFlag(2, paramBoolean);
      return this;
    }

    public Builder setOnlyAlertOnce(boolean paramBoolean)
    {
      setFlag(8, paramBoolean);
      return this;
    }

    public Builder setPriority(int paramInt)
    {
      this.mPriority = paramInt;
      return this;
    }

    public Builder setProgress(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.mProgressMax = paramInt1;
      this.mProgress = paramInt2;
      this.mProgressIndeterminate = paramBoolean;
      return this;
    }

    public Builder setPublicVersion(Notification paramNotification)
    {
      this.mPublicVersion = paramNotification;
      return this;
    }

    public Builder setShowWhen(boolean paramBoolean)
    {
      this.mShowWhen = paramBoolean;
      return this;
    }

    public Builder setSmallIcon(int paramInt)
    {
      this.mNotification.icon = paramInt;
      return this;
    }

    public Builder setSmallIcon(int paramInt1, int paramInt2)
    {
      this.mNotification.icon = paramInt1;
      this.mNotification.iconLevel = paramInt2;
      return this;
    }

    public Builder setSortKey(String paramString)
    {
      this.mSortKey = paramString;
      return this;
    }

    public Builder setSound(Uri paramUri)
    {
      this.mNotification.sound = paramUri;
      this.mNotification.audioStreamType = -1;
      return this;
    }

    public Builder setSound(Uri paramUri, int paramInt)
    {
      this.mNotification.sound = paramUri;
      this.mNotification.audioStreamType = paramInt;
      return this;
    }

    public Builder setStyle(NotificationCompat.Style paramStyle)
    {
      if (this.mStyle != paramStyle)
      {
        this.mStyle = paramStyle;
        if (this.mStyle != null)
          this.mStyle.setBuilder(this);
      }
      return this;
    }

    public Builder setSubText(CharSequence paramCharSequence)
    {
      this.mSubText = limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public Builder setTicker(CharSequence paramCharSequence)
    {
      this.mNotification.tickerText = limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public Builder setTicker(CharSequence paramCharSequence, RemoteViews paramRemoteViews)
    {
      this.mNotification.tickerText = limitCharSequenceLength(paramCharSequence);
      this.mTickerView = paramRemoteViews;
      return this;
    }

    public Builder setUsesChronometer(boolean paramBoolean)
    {
      this.mUseChronometer = paramBoolean;
      return this;
    }

    public Builder setVibrate(long[] paramArrayOfLong)
    {
      this.mNotification.vibrate = paramArrayOfLong;
      return this;
    }

    public Builder setVisibility(int paramInt)
    {
      this.mVisibility = paramInt;
      return this;
    }

    public Builder setWhen(long paramLong)
    {
      this.mNotification.when = paramLong;
      return this;
    }
  }

  protected static class BuilderExtender
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      return paramNotificationBuilderWithBuilderAccessor.build();
    }
  }

  public static final class CarExtender
    implements NotificationCompat.Extender
  {
    private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
    private static final String EXTRA_COLOR = "app_color";
    private static final String EXTRA_CONVERSATION = "car_conversation";
    private static final String EXTRA_LARGE_ICON = "large_icon";
    private static final String TAG = "CarExtender";
    private int mColor = 0;
    private Bitmap mLargeIcon;
    private UnreadConversation mUnreadConversation;

    public CarExtender()
    {
    }

    public CarExtender(Notification paramNotification)
    {
      if (Build.VERSION.SDK_INT < 21);
      while (true)
      {
        return;
        if (NotificationCompat.getExtras(paramNotification) == null);
        for (Bundle localBundle1 = null; localBundle1 != null; localBundle1 = NotificationCompat.getExtras(paramNotification).getBundle("android.car.EXTENSIONS"))
        {
          this.mLargeIcon = ((Bitmap)localBundle1.getParcelable("large_icon"));
          this.mColor = localBundle1.getInt("app_color", 0);
          Bundle localBundle2 = localBundle1.getBundle("car_conversation");
          this.mUnreadConversation = ((UnreadConversation)NotificationCompat.IMPL.getUnreadConversationFromBundle(localBundle2, UnreadConversation.FACTORY, RemoteInput.FACTORY));
          return;
        }
      }
    }

    public NotificationCompat.Builder extend(NotificationCompat.Builder paramBuilder)
    {
      if (Build.VERSION.SDK_INT < 21)
        return paramBuilder;
      Bundle localBundle = new Bundle();
      if (this.mLargeIcon != null)
        localBundle.putParcelable("large_icon", this.mLargeIcon);
      if (this.mColor != 0)
        localBundle.putInt("app_color", this.mColor);
      if (this.mUnreadConversation != null)
        localBundle.putBundle("car_conversation", NotificationCompat.IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
      paramBuilder.getExtras().putBundle("android.car.EXTENSIONS", localBundle);
      return paramBuilder;
    }

    public int getColor()
    {
      return this.mColor;
    }

    public Bitmap getLargeIcon()
    {
      return this.mLargeIcon;
    }

    public UnreadConversation getUnreadConversation()
    {
      return this.mUnreadConversation;
    }

    public CarExtender setColor(int paramInt)
    {
      this.mColor = paramInt;
      return this;
    }

    public CarExtender setLargeIcon(Bitmap paramBitmap)
    {
      this.mLargeIcon = paramBitmap;
      return this;
    }

    public CarExtender setUnreadConversation(UnreadConversation paramUnreadConversation)
    {
      this.mUnreadConversation = paramUnreadConversation;
      return this;
    }

    public static class UnreadConversation extends NotificationCompatBase.UnreadConversation
    {
      static final NotificationCompatBase.UnreadConversation.Factory FACTORY = new NotificationCompatBase.UnreadConversation.Factory()
      {
        public NotificationCompat.CarExtender.UnreadConversation build(String[] paramAnonymousArrayOfString1, RemoteInputCompatBase.RemoteInput paramAnonymousRemoteInput, PendingIntent paramAnonymousPendingIntent1, PendingIntent paramAnonymousPendingIntent2, String[] paramAnonymousArrayOfString2, long paramAnonymousLong)
        {
          return new NotificationCompat.CarExtender.UnreadConversation(paramAnonymousArrayOfString1, (RemoteInput)paramAnonymousRemoteInput, paramAnonymousPendingIntent1, paramAnonymousPendingIntent2, paramAnonymousArrayOfString2, paramAnonymousLong);
        }
      };
      private final long mLatestTimestamp;
      private final String[] mMessages;
      private final String[] mParticipants;
      private final PendingIntent mReadPendingIntent;
      private final RemoteInput mRemoteInput;
      private final PendingIntent mReplyPendingIntent;

      UnreadConversation(String[] paramArrayOfString1, RemoteInput paramRemoteInput, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, String[] paramArrayOfString2, long paramLong)
      {
        this.mMessages = paramArrayOfString1;
        this.mRemoteInput = paramRemoteInput;
        this.mReadPendingIntent = paramPendingIntent2;
        this.mReplyPendingIntent = paramPendingIntent1;
        this.mParticipants = paramArrayOfString2;
        this.mLatestTimestamp = paramLong;
      }

      public long getLatestTimestamp()
      {
        return this.mLatestTimestamp;
      }

      public String[] getMessages()
      {
        return this.mMessages;
      }

      public String getParticipant()
      {
        if (this.mParticipants.length > 0)
          return this.mParticipants[0];
        return null;
      }

      public String[] getParticipants()
      {
        return this.mParticipants;
      }

      public PendingIntent getReadPendingIntent()
      {
        return this.mReadPendingIntent;
      }

      public RemoteInput getRemoteInput()
      {
        return this.mRemoteInput;
      }

      public PendingIntent getReplyPendingIntent()
      {
        return this.mReplyPendingIntent;
      }

      public static class Builder
      {
        private long mLatestTimestamp;
        private final List<String> mMessages = new ArrayList();
        private final String mParticipant;
        private PendingIntent mReadPendingIntent;
        private RemoteInput mRemoteInput;
        private PendingIntent mReplyPendingIntent;

        public Builder(String paramString)
        {
          this.mParticipant = paramString;
        }

        public Builder addMessage(String paramString)
        {
          this.mMessages.add(paramString);
          return this;
        }

        public NotificationCompat.CarExtender.UnreadConversation build()
        {
          String[] arrayOfString1 = (String[])this.mMessages.toArray(new String[this.mMessages.size()]);
          String[] arrayOfString2 = new String[1];
          arrayOfString2[0] = this.mParticipant;
          return new NotificationCompat.CarExtender.UnreadConversation(arrayOfString1, this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, arrayOfString2, this.mLatestTimestamp);
        }

        public Builder setLatestTimestamp(long paramLong)
        {
          this.mLatestTimestamp = paramLong;
          return this;
        }

        public Builder setReadPendingIntent(PendingIntent paramPendingIntent)
        {
          this.mReadPendingIntent = paramPendingIntent;
          return this;
        }

        public Builder setReplyAction(PendingIntent paramPendingIntent, RemoteInput paramRemoteInput)
        {
          this.mRemoteInput = paramRemoteInput;
          this.mReplyPendingIntent = paramPendingIntent;
          return this;
        }
      }
    }
  }

  public static abstract interface Extender
  {
    public abstract NotificationCompat.Builder extend(NotificationCompat.Builder paramBuilder);
  }

  public static class InboxStyle extends NotificationCompat.Style
  {
    ArrayList<CharSequence> mTexts = new ArrayList();

    public InboxStyle()
    {
    }

    public InboxStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }

    public InboxStyle addLine(CharSequence paramCharSequence)
    {
      this.mTexts.add(NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence));
      return this;
    }

    public InboxStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }

    public InboxStyle setSummaryText(CharSequence paramCharSequence)
    {
      this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.mSummaryTextSet = true;
      return this;
    }
  }

  static abstract interface NotificationCompatImpl
  {
    public abstract Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender);

    public abstract NotificationCompat.Action getAction(Notification paramNotification, int paramInt);

    public abstract int getActionCount(Notification paramNotification);

    public abstract NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList);

    public abstract Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation);

    public abstract String getCategory(Notification paramNotification);

    public abstract Bundle getExtras(Notification paramNotification);

    public abstract String getGroup(Notification paramNotification);

    public abstract boolean getLocalOnly(Notification paramNotification);

    public abstract ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction);

    public abstract String getSortKey(Notification paramNotification);

    public abstract NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1);

    public abstract boolean isGroupSummary(Notification paramNotification);
  }

  static class NotificationCompatImplApi20 extends NotificationCompat.NotificationCompatImplKitKat
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatApi20.Builder localBuilder = new NotificationCompatApi20.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      NotificationCompat.addStyleToBuilderJellybean(localBuilder, paramBuilder.mStyle);
      return paramBuilderExtender.build(paramBuilder, localBuilder);
    }

    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatApi20.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }

    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return (NotificationCompat.Action[])NotificationCompatApi20.getActionsFromParcelableArrayList(paramArrayList, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }

    public String getGroup(Notification paramNotification)
    {
      return NotificationCompatApi20.getGroup(paramNotification);
    }

    public boolean getLocalOnly(Notification paramNotification)
    {
      return NotificationCompatApi20.getLocalOnly(paramNotification);
    }

    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return NotificationCompatApi20.getParcelableArrayListForActions(paramArrayOfAction);
    }

    public String getSortKey(Notification paramNotification)
    {
      return NotificationCompatApi20.getSortKey(paramNotification);
    }

    public boolean isGroupSummary(Notification paramNotification)
    {
      return NotificationCompatApi20.isGroupSummary(paramNotification);
    }
  }

  static class NotificationCompatImplApi21 extends NotificationCompat.NotificationCompatImplApi20
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatApi21.Builder localBuilder = new NotificationCompatApi21.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mCategory, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mColor, paramBuilder.mVisibility, paramBuilder.mPublicVersion, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      NotificationCompat.addStyleToBuilderJellybean(localBuilder, paramBuilder.mStyle);
      return paramBuilderExtender.build(paramBuilder, localBuilder);
    }

    public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation)
    {
      return NotificationCompatApi21.getBundleForUnreadConversation(paramUnreadConversation);
    }

    public String getCategory(Notification paramNotification)
    {
      return NotificationCompatApi21.getCategory(paramNotification);
    }

    public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
    {
      return NotificationCompatApi21.getUnreadConversationFromBundle(paramBundle, paramFactory, paramFactory1);
    }
  }

  static class NotificationCompatImplBase
    implements NotificationCompat.NotificationCompatImpl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      Notification localNotification = paramBuilder.mNotification;
      localNotification.setLatestEventInfo(paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent);
      if (paramBuilder.mPriority > 0)
        localNotification.flags = (0x80 | localNotification.flags);
      return localNotification;
    }

    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return null;
    }

    public int getActionCount(Notification paramNotification)
    {
      return 0;
    }

    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return null;
    }

    public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation)
    {
      return null;
    }

    public String getCategory(Notification paramNotification)
    {
      return null;
    }

    public Bundle getExtras(Notification paramNotification)
    {
      return null;
    }

    public String getGroup(Notification paramNotification)
    {
      return null;
    }

    public boolean getLocalOnly(Notification paramNotification)
    {
      return false;
    }

    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return null;
    }

    public String getSortKey(Notification paramNotification)
    {
      return null;
    }

    public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
    {
      return null;
    }

    public boolean isGroupSummary(Notification paramNotification)
    {
      return false;
    }
  }

  static class NotificationCompatImplGingerbread extends NotificationCompat.NotificationCompatImplBase
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      Notification localNotification1 = paramBuilder.mNotification;
      localNotification1.setLatestEventInfo(paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent);
      Notification localNotification2 = NotificationCompatGingerbread.add(localNotification1, paramBuilder.mContext, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent);
      if (paramBuilder.mPriority > 0)
        localNotification2.flags = (0x80 | localNotification2.flags);
      return localNotification2;
    }
  }

  static class NotificationCompatImplHoneycomb extends NotificationCompat.NotificationCompatImplBase
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      return NotificationCompatHoneycomb.add(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon);
    }
  }

  static class NotificationCompatImplIceCreamSandwich extends NotificationCompat.NotificationCompatImplBase
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      return paramBuilderExtender.build(paramBuilder, new NotificationCompatIceCreamSandwich.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate));
    }
  }

  static class NotificationCompatImplJellybean extends NotificationCompat.NotificationCompatImplBase
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatJellybean.Builder localBuilder = new NotificationCompatJellybean.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      NotificationCompat.addStyleToBuilderJellybean(localBuilder, paramBuilder.mStyle);
      return paramBuilderExtender.build(paramBuilder, localBuilder);
    }

    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatJellybean.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }

    public int getActionCount(Notification paramNotification)
    {
      return NotificationCompatJellybean.getActionCount(paramNotification);
    }

    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return (NotificationCompat.Action[])NotificationCompatJellybean.getActionsFromParcelableArrayList(paramArrayList, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }

    public Bundle getExtras(Notification paramNotification)
    {
      return NotificationCompatJellybean.getExtras(paramNotification);
    }

    public String getGroup(Notification paramNotification)
    {
      return NotificationCompatJellybean.getGroup(paramNotification);
    }

    public boolean getLocalOnly(Notification paramNotification)
    {
      return NotificationCompatJellybean.getLocalOnly(paramNotification);
    }

    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return NotificationCompatJellybean.getParcelableArrayListForActions(paramArrayOfAction);
    }

    public String getSortKey(Notification paramNotification)
    {
      return NotificationCompatJellybean.getSortKey(paramNotification);
    }

    public boolean isGroupSummary(Notification paramNotification)
    {
      return NotificationCompatJellybean.isGroupSummary(paramNotification);
    }
  }

  static class NotificationCompatImplKitKat extends NotificationCompat.NotificationCompatImplJellybean
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatKitKat.Builder localBuilder = new NotificationCompatKitKat.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      NotificationCompat.addStyleToBuilderJellybean(localBuilder, paramBuilder.mStyle);
      return paramBuilderExtender.build(paramBuilder, localBuilder);
    }

    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatKitKat.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }

    public int getActionCount(Notification paramNotification)
    {
      return NotificationCompatKitKat.getActionCount(paramNotification);
    }

    public Bundle getExtras(Notification paramNotification)
    {
      return NotificationCompatKitKat.getExtras(paramNotification);
    }

    public String getGroup(Notification paramNotification)
    {
      return NotificationCompatKitKat.getGroup(paramNotification);
    }

    public boolean getLocalOnly(Notification paramNotification)
    {
      return NotificationCompatKitKat.getLocalOnly(paramNotification);
    }

    public String getSortKey(Notification paramNotification)
    {
      return NotificationCompatKitKat.getSortKey(paramNotification);
    }

    public boolean isGroupSummary(Notification paramNotification)
    {
      return NotificationCompatKitKat.isGroupSummary(paramNotification);
    }
  }

  public static abstract class Style
  {
    CharSequence mBigContentTitle;
    NotificationCompat.Builder mBuilder;
    CharSequence mSummaryText;
    boolean mSummaryTextSet = false;

    public Notification build()
    {
      NotificationCompat.Builder localBuilder = this.mBuilder;
      Notification localNotification = null;
      if (localBuilder != null)
        localNotification = this.mBuilder.build();
      return localNotification;
    }

    public void setBuilder(NotificationCompat.Builder paramBuilder)
    {
      if (this.mBuilder != paramBuilder)
      {
        this.mBuilder = paramBuilder;
        if (this.mBuilder != null)
          this.mBuilder.setStyle(this);
      }
    }
  }

  public static final class WearableExtender
    implements NotificationCompat.Extender
  {
    private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
    private static final int DEFAULT_FLAGS = 1;
    private static final int DEFAULT_GRAVITY = 80;
    private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
    private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
    private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
    private static final int FLAG_HINT_HIDE_ICON = 2;
    private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
    private static final int FLAG_START_SCROLL_BOTTOM = 8;
    private static final String KEY_ACTIONS = "actions";
    private static final String KEY_BACKGROUND = "background";
    private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
    private static final String KEY_CONTENT_ICON = "contentIcon";
    private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
    private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
    private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
    private static final String KEY_DISPLAY_INTENT = "displayIntent";
    private static final String KEY_FLAGS = "flags";
    private static final String KEY_GRAVITY = "gravity";
    private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
    private static final String KEY_PAGES = "pages";
    public static final int SCREEN_TIMEOUT_LONG = -1;
    public static final int SCREEN_TIMEOUT_SHORT = 0;
    public static final int SIZE_DEFAULT = 0;
    public static final int SIZE_FULL_SCREEN = 5;
    public static final int SIZE_LARGE = 4;
    public static final int SIZE_MEDIUM = 3;
    public static final int SIZE_SMALL = 2;
    public static final int SIZE_XSMALL = 1;
    public static final int UNSET_ACTION_INDEX = -1;
    private ArrayList<NotificationCompat.Action> mActions = new ArrayList();
    private Bitmap mBackground;
    private int mContentActionIndex = -1;
    private int mContentIcon;
    private int mContentIconGravity = 8388613;
    private int mCustomContentHeight;
    private int mCustomSizePreset = 0;
    private PendingIntent mDisplayIntent;
    private int mFlags = 1;
    private int mGravity = 80;
    private int mHintScreenTimeout;
    private ArrayList<Notification> mPages = new ArrayList();

    public WearableExtender()
    {
    }

    public WearableExtender(Notification paramNotification)
    {
      Bundle localBundle1 = NotificationCompat.getExtras(paramNotification);
      if (localBundle1 != null);
      for (Bundle localBundle2 = localBundle1.getBundle("android.wearable.EXTENSIONS"); ; localBundle2 = null)
      {
        if (localBundle2 != null)
        {
          NotificationCompat.Action[] arrayOfAction = NotificationCompat.IMPL.getActionsFromParcelableArrayList(localBundle2.getParcelableArrayList("actions"));
          if (arrayOfAction != null)
            Collections.addAll(this.mActions, arrayOfAction);
          this.mFlags = localBundle2.getInt("flags", 1);
          this.mDisplayIntent = ((PendingIntent)localBundle2.getParcelable("displayIntent"));
          Notification[] arrayOfNotification = NotificationCompat.getNotificationArrayFromBundle(localBundle2, "pages");
          if (arrayOfNotification != null)
            Collections.addAll(this.mPages, arrayOfNotification);
          this.mBackground = ((Bitmap)localBundle2.getParcelable("background"));
          this.mContentIcon = localBundle2.getInt("contentIcon");
          this.mContentIconGravity = localBundle2.getInt("contentIconGravity", 8388613);
          this.mContentActionIndex = localBundle2.getInt("contentActionIndex", -1);
          this.mCustomSizePreset = localBundle2.getInt("customSizePreset", 0);
          this.mCustomContentHeight = localBundle2.getInt("customContentHeight");
          this.mGravity = localBundle2.getInt("gravity", 80);
          this.mHintScreenTimeout = localBundle2.getInt("hintScreenTimeout");
        }
        return;
      }
    }

    private void setFlag(int paramInt, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.mFlags = (paramInt | this.mFlags);
        return;
      }
      this.mFlags &= (paramInt ^ 0xFFFFFFFF);
    }

    public WearableExtender addAction(NotificationCompat.Action paramAction)
    {
      this.mActions.add(paramAction);
      return this;
    }

    public WearableExtender addActions(List<NotificationCompat.Action> paramList)
    {
      this.mActions.addAll(paramList);
      return this;
    }

    public WearableExtender addPage(Notification paramNotification)
    {
      this.mPages.add(paramNotification);
      return this;
    }

    public WearableExtender addPages(List<Notification> paramList)
    {
      this.mPages.addAll(paramList);
      return this;
    }

    public WearableExtender clearActions()
    {
      this.mActions.clear();
      return this;
    }

    public WearableExtender clearPages()
    {
      this.mPages.clear();
      return this;
    }

    public WearableExtender clone()
    {
      WearableExtender localWearableExtender = new WearableExtender();
      localWearableExtender.mActions = new ArrayList(this.mActions);
      localWearableExtender.mFlags = this.mFlags;
      localWearableExtender.mDisplayIntent = this.mDisplayIntent;
      localWearableExtender.mPages = new ArrayList(this.mPages);
      localWearableExtender.mBackground = this.mBackground;
      localWearableExtender.mContentIcon = this.mContentIcon;
      localWearableExtender.mContentIconGravity = this.mContentIconGravity;
      localWearableExtender.mContentActionIndex = this.mContentActionIndex;
      localWearableExtender.mCustomSizePreset = this.mCustomSizePreset;
      localWearableExtender.mCustomContentHeight = this.mCustomContentHeight;
      localWearableExtender.mGravity = this.mGravity;
      localWearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
      return localWearableExtender;
    }

    public NotificationCompat.Builder extend(NotificationCompat.Builder paramBuilder)
    {
      Bundle localBundle = new Bundle();
      if (!this.mActions.isEmpty())
        localBundle.putParcelableArrayList("actions", NotificationCompat.IMPL.getParcelableArrayListForActions((NotificationCompat.Action[])this.mActions.toArray(new NotificationCompat.Action[this.mActions.size()])));
      if (this.mFlags != 1)
        localBundle.putInt("flags", this.mFlags);
      if (this.mDisplayIntent != null)
        localBundle.putParcelable("displayIntent", this.mDisplayIntent);
      if (!this.mPages.isEmpty())
        localBundle.putParcelableArray("pages", (Parcelable[])this.mPages.toArray(new Notification[this.mPages.size()]));
      if (this.mBackground != null)
        localBundle.putParcelable("background", this.mBackground);
      if (this.mContentIcon != 0)
        localBundle.putInt("contentIcon", this.mContentIcon);
      if (this.mContentIconGravity != 8388613)
        localBundle.putInt("contentIconGravity", this.mContentIconGravity);
      if (this.mContentActionIndex != -1)
        localBundle.putInt("contentActionIndex", this.mContentActionIndex);
      if (this.mCustomSizePreset != 0)
        localBundle.putInt("customSizePreset", this.mCustomSizePreset);
      if (this.mCustomContentHeight != 0)
        localBundle.putInt("customContentHeight", this.mCustomContentHeight);
      if (this.mGravity != 80)
        localBundle.putInt("gravity", this.mGravity);
      if (this.mHintScreenTimeout != 0)
        localBundle.putInt("hintScreenTimeout", this.mHintScreenTimeout);
      paramBuilder.getExtras().putBundle("android.wearable.EXTENSIONS", localBundle);
      return paramBuilder;
    }

    public List<NotificationCompat.Action> getActions()
    {
      return this.mActions;
    }

    public Bitmap getBackground()
    {
      return this.mBackground;
    }

    public int getContentAction()
    {
      return this.mContentActionIndex;
    }

    public int getContentIcon()
    {
      return this.mContentIcon;
    }

    public int getContentIconGravity()
    {
      return this.mContentIconGravity;
    }

    public boolean getContentIntentAvailableOffline()
    {
      return (0x1 & this.mFlags) != 0;
    }

    public int getCustomContentHeight()
    {
      return this.mCustomContentHeight;
    }

    public int getCustomSizePreset()
    {
      return this.mCustomSizePreset;
    }

    public PendingIntent getDisplayIntent()
    {
      return this.mDisplayIntent;
    }

    public int getGravity()
    {
      return this.mGravity;
    }

    public boolean getHintAvoidBackgroundClipping()
    {
      return (0x10 & this.mFlags) != 0;
    }

    public boolean getHintHideIcon()
    {
      return (0x2 & this.mFlags) != 0;
    }

    public int getHintScreenTimeout()
    {
      return this.mHintScreenTimeout;
    }

    public boolean getHintShowBackgroundOnly()
    {
      return (0x4 & this.mFlags) != 0;
    }

    public List<Notification> getPages()
    {
      return this.mPages;
    }

    public boolean getStartScrollBottom()
    {
      return (0x8 & this.mFlags) != 0;
    }

    public WearableExtender setBackground(Bitmap paramBitmap)
    {
      this.mBackground = paramBitmap;
      return this;
    }

    public WearableExtender setContentAction(int paramInt)
    {
      this.mContentActionIndex = paramInt;
      return this;
    }

    public WearableExtender setContentIcon(int paramInt)
    {
      this.mContentIcon = paramInt;
      return this;
    }

    public WearableExtender setContentIconGravity(int paramInt)
    {
      this.mContentIconGravity = paramInt;
      return this;
    }

    public WearableExtender setContentIntentAvailableOffline(boolean paramBoolean)
    {
      setFlag(1, paramBoolean);
      return this;
    }

    public WearableExtender setCustomContentHeight(int paramInt)
    {
      this.mCustomContentHeight = paramInt;
      return this;
    }

    public WearableExtender setCustomSizePreset(int paramInt)
    {
      this.mCustomSizePreset = paramInt;
      return this;
    }

    public WearableExtender setDisplayIntent(PendingIntent paramPendingIntent)
    {
      this.mDisplayIntent = paramPendingIntent;
      return this;
    }

    public WearableExtender setGravity(int paramInt)
    {
      this.mGravity = paramInt;
      return this;
    }

    public WearableExtender setHintAvoidBackgroundClipping(boolean paramBoolean)
    {
      setFlag(16, paramBoolean);
      return this;
    }

    public WearableExtender setHintHideIcon(boolean paramBoolean)
    {
      setFlag(2, paramBoolean);
      return this;
    }

    public WearableExtender setHintScreenTimeout(int paramInt)
    {
      this.mHintScreenTimeout = paramInt;
      return this;
    }

    public WearableExtender setHintShowBackgroundOnly(boolean paramBoolean)
    {
      setFlag(4, paramBoolean);
      return this;
    }

    public WearableExtender setStartScrollBottom(boolean paramBoolean)
    {
      setFlag(8, paramBoolean);
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.NotificationCompat
 * JD-Core Version:    0.6.2
 */