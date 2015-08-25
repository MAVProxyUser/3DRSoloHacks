package com.google.android.gms.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.util.Set;

public class zzc
{
  private static final zzc zzPa = new zzc();

  private boolean zza(PackageInfo paramPackageInfo, boolean paramBoolean)
  {
    if (paramPackageInfo.signatures.length != 1)
    {
      Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
      return false;
    }
    zzb.zzb localzzb = new zzb.zzb(paramPackageInfo.signatures[0].toByteArray());
    if (paramBoolean);
    for (Set localSet = zzb.zzkw(); localSet.contains(localzzb); localSet = zzb.zzkx())
      return true;
    if (Log.isLoggable("GoogleSignatureVerifier", 2))
      Log.v("GoogleSignatureVerifier", "Signature not valid.  Found: \n" + Base64.encodeToString(localzzb.getBytes(), 0));
    return false;
  }

  public static zzc zzkA()
  {
    return zzPa;
  }

  zzb.zza zza(PackageInfo paramPackageInfo, zzb.zza[] paramArrayOfzza)
  {
    if (paramPackageInfo.signatures.length != 1)
    {
      Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
      return null;
    }
    zzb.zzb localzzb = new zzb.zzb(paramPackageInfo.signatures[0].toByteArray());
    for (int i = 0; i < paramArrayOfzza.length; i++)
      if (paramArrayOfzza[i].equals(localzzb))
        return paramArrayOfzza[i];
    if (Log.isLoggable("GoogleSignatureVerifier", 2))
      Log.v("GoogleSignatureVerifier", "Signature not valid.  Found: \n" + Base64.encodeToString(localzzb.getBytes(), 0));
    return null;
  }

  public boolean zza(PackageManager paramPackageManager, PackageInfo paramPackageInfo)
  {
    boolean bool = false;
    if (paramPackageInfo == null);
    do
    {
      return bool;
      if (GooglePlayServicesUtil.zzc(paramPackageManager))
        return zza(paramPackageInfo, true);
      bool = zza(paramPackageInfo, false);
    }
    while ((bool) || (!zza(paramPackageInfo, true)));
    Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
    return bool;
  }

  public boolean zzb(PackageManager paramPackageManager, String paramString)
  {
    try
    {
      PackageInfo localPackageInfo = paramPackageManager.getPackageInfo(paramString, 64);
      return zza(paramPackageManager, localPackageInfo);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      if (Log.isLoggable("GoogleSignatureVerifier", 3))
        Log.d("GoogleSignatureVerifier", "Package manager can't find package " + paramString + ", defaulting to false");
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.zzc
 * JD-Core Version:    0.6.2
 */