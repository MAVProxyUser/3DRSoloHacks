package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy
  implements AdvertisingInfoStrategy
{
  public static final String GOOGLE_PLAY_SERVICES_INTENT = "com.google.android.gms.ads.identifier.service.START";
  public static final String GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME = "com.google.android.gms";
  private static final String GOOGLE_PLAY_SERVICE_PACKAGE_NAME = "com.android.vending";
  private final Context context;

  public AdvertisingInfoServiceStrategy(Context paramContext)
  {
    this.context = paramContext.getApplicationContext();
  }

  // ERROR //
  public AdvertisingInfo getAdvertisingInfo()
  {
    // Byte code:
    //   0: invokestatic 43	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   3: invokestatic 46	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   6: if_acmpne +17 -> 23
    //   9: invokestatic 52	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   12: ldc 54
    //   14: ldc 56
    //   16: invokeinterface 62 3 0
    //   21: aconst_null
    //   22: areturn
    //   23: aload_0
    //   24: getfield 31	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   27: invokevirtual 66	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   30: ldc 16
    //   32: iconst_0
    //   33: invokevirtual 72	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   36: pop
    //   37: new 74	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection
    //   40: dup
    //   41: aconst_null
    //   42: invokespecial 77	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection:<init>	(Lio/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$1;)V
    //   45: astore_3
    //   46: new 79	android/content/Intent
    //   49: dup
    //   50: ldc 10
    //   52: invokespecial 82	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   55: astore 4
    //   57: aload 4
    //   59: ldc 13
    //   61: invokevirtual 86	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   64: pop
    //   65: aload_0
    //   66: getfield 31	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   69: aload 4
    //   71: aload_3
    //   72: iconst_1
    //   73: invokevirtual 90	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    //   76: istore 7
    //   78: iload 7
    //   80: ifeq +119 -> 199
    //   83: new 92	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface
    //   86: dup
    //   87: aload_3
    //   88: invokevirtual 96	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection:getBinder	()Landroid/os/IBinder;
    //   91: invokespecial 99	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:<init>	(Landroid/os/IBinder;)V
    //   94: astore 8
    //   96: new 101	io/fabric/sdk/android/services/common/AdvertisingInfo
    //   99: dup
    //   100: aload 8
    //   102: invokevirtual 105	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:getId	()Ljava/lang/String;
    //   105: aload 8
    //   107: invokevirtual 109	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:isLimitAdTrackingEnabled	()Z
    //   110: invokespecial 112	io/fabric/sdk/android/services/common/AdvertisingInfo:<init>	(Ljava/lang/String;Z)V
    //   113: astore 9
    //   115: aload_0
    //   116: getfield 31	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   119: aload_3
    //   120: invokevirtual 116	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   123: aload 9
    //   125: areturn
    //   126: astore_1
    //   127: invokestatic 52	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   130: ldc 54
    //   132: ldc 118
    //   134: aload_1
    //   135: invokeinterface 121 4 0
    //   140: aconst_null
    //   141: areturn
    //   142: astore 11
    //   144: invokestatic 52	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   147: ldc 54
    //   149: ldc 123
    //   151: aload 11
    //   153: invokeinterface 126 4 0
    //   158: aload_0
    //   159: getfield 31	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   162: aload_3
    //   163: invokevirtual 116	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   166: aconst_null
    //   167: areturn
    //   168: astore 6
    //   170: invokestatic 52	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   173: ldc 54
    //   175: ldc 128
    //   177: aload 6
    //   179: invokeinterface 121 4 0
    //   184: aconst_null
    //   185: areturn
    //   186: astore 10
    //   188: aload_0
    //   189: getfield 31	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   192: aload_3
    //   193: invokevirtual 116	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   196: aload 10
    //   198: athrow
    //   199: invokestatic 52	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   202: ldc 54
    //   204: ldc 128
    //   206: invokeinterface 62 3 0
    //   211: aconst_null
    //   212: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   23	37	126	java/lang/Exception
    //   83	115	142	java/lang/Exception
    //   65	78	168	java/lang/Throwable
    //   115	123	168	java/lang/Throwable
    //   158	166	168	java/lang/Throwable
    //   188	199	168	java/lang/Throwable
    //   199	211	168	java/lang/Throwable
    //   83	115	186	finally
    //   144	158	186	finally
  }

  private static final class AdvertisingConnection
    implements ServiceConnection
  {
    private static final int QUEUE_TIMEOUT_IN_MS = 200;
    private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue(1);
    private boolean retrieved = false;

    public IBinder getBinder()
    {
      if (this.retrieved)
        Fabric.getLogger().e("Fabric", "getBinder already called");
      this.retrieved = true;
      try
      {
        IBinder localIBinder = (IBinder)this.queue.poll(200L, TimeUnit.MILLISECONDS);
        return localIBinder;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
      return null;
    }

    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      try
      {
        this.queue.put(paramIBinder);
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }

    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.queue.clear();
    }
  }

  private static final class AdvertisingInterface
    implements IInterface
  {
    public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
    private static final int AD_TRANSACTION_CODE_ID = 1;
    private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
    private static final int FLAGS_NONE;
    private final IBinder binder;

    public AdvertisingInterface(IBinder paramIBinder)
    {
      this.binder = paramIBinder;
    }

    public IBinder asBinder()
    {
      return this.binder;
    }

    public String getId()
      throws RemoteException
    {
      Parcel localParcel1 = Parcel.obtain();
      Parcel localParcel2 = Parcel.obtain();
      try
      {
        localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        this.binder.transact(1, localParcel1, localParcel2, 0);
        localParcel2.readException();
        String str = localParcel2.readString();
        return str;
      }
      catch (Exception localException)
      {
        Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
        return null;
      }
      finally
      {
        localParcel2.recycle();
        localParcel1.recycle();
      }
    }

    public boolean isLimitAdTrackingEnabled()
      throws RemoteException
    {
      Parcel localParcel1 = Parcel.obtain();
      Parcel localParcel2 = Parcel.obtain();
      try
      {
        localParcel1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
        localParcel1.writeInt(1);
        this.binder.transact(2, localParcel1, localParcel2, 0);
        localParcel2.readException();
        int i = localParcel2.readInt();
        if (i != 0);
        for (boolean bool = true; ; bool = false)
          return bool;
      }
      catch (Exception localException)
      {
        Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture Advertising limitAdTracking");
        return false;
      }
      finally
      {
        localParcel2.recycle();
        localParcel1.recycle();
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy
 * JD-Core Version:    0.6.2
 */