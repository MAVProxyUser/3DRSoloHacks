package android.support.v4.media.session;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract interface IMediaSession extends IInterface
{
  public abstract void adjustVolume(int paramInt1, int paramInt2, String paramString)
    throws RemoteException;

  public abstract void fastForward()
    throws RemoteException;

  public abstract Bundle getExtras()
    throws RemoteException;

  public abstract long getFlags()
    throws RemoteException;

  public abstract PendingIntent getLaunchPendingIntent()
    throws RemoteException;

  public abstract MediaMetadataCompat getMetadata()
    throws RemoteException;

  public abstract String getPackageName()
    throws RemoteException;

  public abstract PlaybackStateCompat getPlaybackState()
    throws RemoteException;

  public abstract List<MediaSessionCompat.QueueItem> getQueue()
    throws RemoteException;

  public abstract CharSequence getQueueTitle()
    throws RemoteException;

  public abstract int getRatingType()
    throws RemoteException;

  public abstract String getTag()
    throws RemoteException;

  public abstract ParcelableVolumeInfo getVolumeAttributes()
    throws RemoteException;

  public abstract boolean isTransportControlEnabled()
    throws RemoteException;

  public abstract void next()
    throws RemoteException;

  public abstract void pause()
    throws RemoteException;

  public abstract void play()
    throws RemoteException;

  public abstract void playFromMediaId(String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract void playFromSearch(String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract void previous()
    throws RemoteException;

  public abstract void rate(RatingCompat paramRatingCompat)
    throws RemoteException;

  public abstract void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
    throws RemoteException;

  public abstract void rewind()
    throws RemoteException;

  public abstract void seekTo(long paramLong)
    throws RemoteException;

  public abstract void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper)
    throws RemoteException;

  public abstract void sendCustomAction(String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract boolean sendMediaButton(KeyEvent paramKeyEvent)
    throws RemoteException;

  public abstract void setVolumeTo(int paramInt1, int paramInt2, String paramString)
    throws RemoteException;

  public abstract void skipToQueueItem(long paramLong)
    throws RemoteException;

  public abstract void stop()
    throws RemoteException;

  public abstract void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IMediaSession
  {
    private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaSession";
    static final int TRANSACTION_adjustVolume = 11;
    static final int TRANSACTION_fastForward = 21;
    static final int TRANSACTION_getExtras = 30;
    static final int TRANSACTION_getFlags = 9;
    static final int TRANSACTION_getLaunchPendingIntent = 8;
    static final int TRANSACTION_getMetadata = 26;
    static final int TRANSACTION_getPackageName = 6;
    static final int TRANSACTION_getPlaybackState = 27;
    static final int TRANSACTION_getQueue = 28;
    static final int TRANSACTION_getQueueTitle = 29;
    static final int TRANSACTION_getRatingType = 31;
    static final int TRANSACTION_getTag = 7;
    static final int TRANSACTION_getVolumeAttributes = 10;
    static final int TRANSACTION_isTransportControlEnabled = 5;
    static final int TRANSACTION_next = 19;
    static final int TRANSACTION_pause = 17;
    static final int TRANSACTION_play = 13;
    static final int TRANSACTION_playFromMediaId = 14;
    static final int TRANSACTION_playFromSearch = 15;
    static final int TRANSACTION_previous = 20;
    static final int TRANSACTION_rate = 24;
    static final int TRANSACTION_registerCallbackListener = 3;
    static final int TRANSACTION_rewind = 22;
    static final int TRANSACTION_seekTo = 23;
    static final int TRANSACTION_sendCommand = 1;
    static final int TRANSACTION_sendCustomAction = 25;
    static final int TRANSACTION_sendMediaButton = 2;
    static final int TRANSACTION_setVolumeTo = 12;
    static final int TRANSACTION_skipToQueueItem = 16;
    static final int TRANSACTION_stop = 18;
    static final int TRANSACTION_unregisterCallbackListener = 4;

    public Stub()
    {
      attachInterface(this, "android.support.v4.media.session.IMediaSession");
    }

    public static IMediaSession asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("android.support.v4.media.session.IMediaSession");
      if ((localIInterface != null) && ((localIInterface instanceof IMediaSession)))
        return (IMediaSession)localIInterface;
      return new Proxy(paramIBinder);
    }

    public IBinder asBinder()
    {
      return this;
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default:
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        paramParcel2.writeString("android.support.v4.media.session.IMediaSession");
        return true;
      case 1:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        String str6 = paramParcel1.readString();
        Bundle localBundle5;
        if (paramParcel1.readInt() != 0)
        {
          localBundle5 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label362;
        }
        for (MediaSessionCompat.ResultReceiverWrapper localResultReceiverWrapper = (MediaSessionCompat.ResultReceiverWrapper)MediaSessionCompat.ResultReceiverWrapper.CREATOR.createFromParcel(paramParcel1); ; localResultReceiverWrapper = null)
        {
          sendCommand(str6, localBundle5, localResultReceiverWrapper);
          paramParcel2.writeNoException();
          return true;
          localBundle5 = null;
          break;
        }
      case 2:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        if (paramParcel1.readInt() != 0);
        for (KeyEvent localKeyEvent = (KeyEvent)KeyEvent.CREATOR.createFromParcel(paramParcel1); ; localKeyEvent = null)
        {
          boolean bool2 = sendMediaButton(localKeyEvent);
          paramParcel2.writeNoException();
          int k = 0;
          if (bool2)
            k = 1;
          paramParcel2.writeInt(k);
          return true;
        }
      case 3:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        registerCallbackListener(IMediaControllerCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 4:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        unregisterCallbackListener(IMediaControllerCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 5:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        boolean bool1 = isTransportControlEnabled();
        paramParcel2.writeNoException();
        int j = 0;
        if (bool1)
          j = 1;
        paramParcel2.writeInt(j);
        return true;
      case 6:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        String str5 = getPackageName();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str5);
        return true;
      case 7:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        String str4 = getTag();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str4);
        return true;
      case 8:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        PendingIntent localPendingIntent = getLaunchPendingIntent();
        paramParcel2.writeNoException();
        if (localPendingIntent != null)
        {
          paramParcel2.writeInt(1);
          localPendingIntent.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 9:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        long l = getFlags();
        paramParcel2.writeNoException();
        paramParcel2.writeLong(l);
        return true;
      case 10:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        ParcelableVolumeInfo localParcelableVolumeInfo = getVolumeAttributes();
        paramParcel2.writeNoException();
        if (localParcelableVolumeInfo != null)
        {
          paramParcel2.writeInt(1);
          localParcelableVolumeInfo.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 11:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        adjustVolume(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 12:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        setVolumeTo(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 13:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        play();
        paramParcel2.writeNoException();
        return true;
      case 14:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        String str3 = paramParcel1.readString();
        if (paramParcel1.readInt() != 0);
        for (Bundle localBundle4 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle4 = null)
        {
          playFromMediaId(str3, localBundle4);
          paramParcel2.writeNoException();
          return true;
        }
      case 15:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        String str2 = paramParcel1.readString();
        if (paramParcel1.readInt() != 0);
        for (Bundle localBundle3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle3 = null)
        {
          playFromSearch(str2, localBundle3);
          paramParcel2.writeNoException();
          return true;
        }
      case 16:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        skipToQueueItem(paramParcel1.readLong());
        paramParcel2.writeNoException();
        return true;
      case 17:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        pause();
        paramParcel2.writeNoException();
        return true;
      case 18:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        stop();
        paramParcel2.writeNoException();
        return true;
      case 19:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        next();
        paramParcel2.writeNoException();
        return true;
      case 20:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        previous();
        paramParcel2.writeNoException();
        return true;
      case 21:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        fastForward();
        paramParcel2.writeNoException();
        return true;
      case 22:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        rewind();
        paramParcel2.writeNoException();
        return true;
      case 23:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        seekTo(paramParcel1.readLong());
        paramParcel2.writeNoException();
        return true;
      case 24:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        if (paramParcel1.readInt() != 0);
        for (RatingCompat localRatingCompat = (RatingCompat)RatingCompat.CREATOR.createFromParcel(paramParcel1); ; localRatingCompat = null)
        {
          rate(localRatingCompat);
          paramParcel2.writeNoException();
          return true;
        }
      case 25:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        String str1 = paramParcel1.readString();
        if (paramParcel1.readInt() != 0);
        for (Bundle localBundle2 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle2 = null)
        {
          sendCustomAction(str1, localBundle2);
          paramParcel2.writeNoException();
          return true;
        }
      case 26:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        MediaMetadataCompat localMediaMetadataCompat = getMetadata();
        paramParcel2.writeNoException();
        if (localMediaMetadataCompat != null)
        {
          paramParcel2.writeInt(1);
          localMediaMetadataCompat.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 27:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        PlaybackStateCompat localPlaybackStateCompat = getPlaybackState();
        paramParcel2.writeNoException();
        if (localPlaybackStateCompat != null)
        {
          paramParcel2.writeInt(1);
          localPlaybackStateCompat.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 28:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        List localList = getQueue();
        paramParcel2.writeNoException();
        paramParcel2.writeTypedList(localList);
        return true;
      case 29:
        paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        CharSequence localCharSequence = getQueueTitle();
        paramParcel2.writeNoException();
        if (localCharSequence != null)
        {
          paramParcel2.writeInt(1);
          TextUtils.writeToParcel(localCharSequence, paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 30:
        label362: paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
        Bundle localBundle1 = getExtras();
        paramParcel2.writeNoException();
        if (localBundle1 != null)
        {
          paramParcel2.writeInt(1);
          localBundle1.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 31:
      }
      paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
      int i = getRatingType();
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class Proxy
      implements IMediaSession
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public void adjustVolume(int paramInt1, int paramInt2, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeString(paramString);
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public void fastForward()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public Bundle getExtras()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(30, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
            return localBundle;
          }
          Bundle localBundle = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public long getFlags()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          long l = localParcel2.readLong();
          return l;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getInterfaceDescriptor()
      {
        return "android.support.v4.media.session.IMediaSession";
      }

      public PendingIntent getLaunchPendingIntent()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localPendingIntent = (PendingIntent)PendingIntent.CREATOR.createFromParcel(localParcel2);
            return localPendingIntent;
          }
          PendingIntent localPendingIntent = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public MediaMetadataCompat getMetadata()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localMediaMetadataCompat = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(localParcel2);
            return localMediaMetadataCompat;
          }
          MediaMetadataCompat localMediaMetadataCompat = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getPackageName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public PlaybackStateCompat getPlaybackState()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localPlaybackStateCompat = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(localParcel2);
            return localPlaybackStateCompat;
          }
          PlaybackStateCompat localPlaybackStateCompat = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public List<MediaSessionCompat.QueueItem> getQueue()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public CharSequence getQueueTitle()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localCharSequence = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(localParcel2);
            return localCharSequence;
          }
          CharSequence localCharSequence = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int getRatingType()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(31, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getTag()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public ParcelableVolumeInfo getVolumeAttributes()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localParcelableVolumeInfo = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(localParcel2);
            return localParcelableVolumeInfo;
          }
          ParcelableVolumeInfo localParcelableVolumeInfo = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean isTransportControlEnabled()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void next()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void pause()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void play()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void playFromMediaId(String paramString, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(14, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void playFromSearch(String paramString, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(15, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void previous()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(20, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void rate(RatingCompat paramRatingCompat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramRatingCompat != null)
          {
            localParcel1.writeInt(1);
            paramRatingCompat.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(24, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramIMediaControllerCallback != null);
          for (IBinder localIBinder = paramIMediaControllerCallback.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(3, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void rewind()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void seekTo(long paramLong)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeLong(paramLong);
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
            localParcel1.writeString(paramString);
            if (paramBundle != null)
            {
              localParcel1.writeInt(1);
              paramBundle.writeToParcel(localParcel1, 0);
              if (paramResultReceiverWrapper != null)
              {
                localParcel1.writeInt(1);
                paramResultReceiverWrapper.writeToParcel(localParcel1, 0);
                this.mRemote.transact(1, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          localParcel1.writeInt(0);
        }
      }

      public void sendCustomAction(String paramString, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(25, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean sendMediaButton(KeyEvent paramKeyEvent)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
            if (paramKeyEvent != null)
            {
              localParcel1.writeInt(1);
              paramKeyEvent.writeToParcel(localParcel1, 0);
              this.mRemote.transact(2, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int i = localParcel2.readInt();
              if (i != 0)
                return bool;
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          bool = false;
        }
      }

      public void setVolumeTo(int paramInt1, int paramInt2, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeString(paramString);
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void skipToQueueItem(long paramLong)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeLong(paramLong);
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void stop()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramIMediaControllerCallback != null);
          for (IBinder localIBinder = paramIMediaControllerCallback.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(4, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.media.session.IMediaSession
 * JD-Core Version:    0.6.2
 */