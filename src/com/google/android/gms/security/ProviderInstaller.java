package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzv;
import java.lang.reflect.Method;

public class ProviderInstaller
{
  public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
  private static Method zzaBA = null;
  private static final Object zznu = new Object();

  public static void installIfNeeded(Context paramContext)
    throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
  {
    zzv.zzb(paramContext, "Context must not be null");
    GooglePlayServicesUtil.zzM(paramContext);
    Context localContext = GooglePlayServicesUtil.getRemoteContext(paramContext);
    if (localContext == null)
    {
      Log.e("ProviderInstaller", "Failed to get remote context");
      throw new GooglePlayServicesNotAvailableException(8);
    }
    synchronized (zznu)
    {
      try
      {
        if (zzaBA == null)
          zzam(localContext);
        zzaBA.invoke(null, new Object[] { localContext });
        return;
      }
      catch (Exception localException)
      {
        Log.e("ProviderInstaller", "Failed to install provider: " + localException.getMessage());
        throw new GooglePlayServicesNotAvailableException(8);
      }
    }
  }

  public static void installIfNeededAsync(Context paramContext, final ProviderInstallListener paramProviderInstallListener)
  {
    zzv.zzb(paramContext, "Context must not be null");
    zzv.zzb(paramProviderInstallListener, "Listener must not be null");
    zzv.zzbI("Must be called on the UI thread");
    new AsyncTask()
    {
      protected Integer zzb(Void[] paramAnonymousArrayOfVoid)
      {
        try
        {
          ProviderInstaller.installIfNeeded(this.zzoH);
          return Integer.valueOf(0);
        }
        catch (GooglePlayServicesRepairableException localGooglePlayServicesRepairableException)
        {
          return Integer.valueOf(localGooglePlayServicesRepairableException.getConnectionStatusCode());
        }
        catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
        {
          return Integer.valueOf(localGooglePlayServicesNotAvailableException.errorCode);
        }
      }

      protected void zze(Integer paramAnonymousInteger)
      {
        if (paramAnonymousInteger.intValue() == 0)
        {
          paramProviderInstallListener.onProviderInstalled();
          return;
        }
        Intent localIntent = GooglePlayServicesUtil.zzar(paramAnonymousInteger.intValue());
        paramProviderInstallListener.onProviderInstallFailed(paramAnonymousInteger.intValue(), localIntent);
      }
    }
    .execute(new Void[0]);
  }

  private static void zzam(Context paramContext)
    throws ClassNotFoundException, NoSuchMethodException
  {
    zzaBA = paramContext.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[] { Context.class });
  }

  public static abstract interface ProviderInstallListener
  {
    public abstract void onProviderInstallFailed(int paramInt, Intent paramIntent);

    public abstract void onProviderInstalled();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.security.ProviderInstaller
 * JD-Core Version:    0.6.2
 */