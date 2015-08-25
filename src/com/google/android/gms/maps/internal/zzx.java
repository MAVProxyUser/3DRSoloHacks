package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.Parcelable;

public final class zzx
{
  public static void zza(Bundle paramBundle, String paramString, Parcelable paramParcelable)
  {
    paramBundle.setClassLoader(zzx.class.getClassLoader());
    Bundle localBundle = paramBundle.getBundle("map_state");
    if (localBundle == null)
      localBundle = new Bundle();
    localBundle.setClassLoader(zzx.class.getClassLoader());
    localBundle.putParcelable(paramString, paramParcelable);
    paramBundle.putBundle("map_state", localBundle);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzx
 * JD-Core Version:    0.6.2
 */