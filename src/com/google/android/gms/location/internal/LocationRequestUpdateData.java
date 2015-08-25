package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.zzc;
import com.google.android.gms.location.zzc.zza;
import com.google.android.gms.location.zzd;
import com.google.android.gms.location.zzd.zza;

public class LocationRequestUpdateData
  implements SafeParcelable
{
  public static final zzl CREATOR = new zzl();
  PendingIntent mPendingIntent;
  int zzaro;
  LocationRequestInternal zzarp;
  zzd zzarq;
  zzc zzarr;
  private final int zzzH;

  LocationRequestUpdateData(int paramInt1, int paramInt2, LocationRequestInternal paramLocationRequestInternal, IBinder paramIBinder1, PendingIntent paramPendingIntent, IBinder paramIBinder2)
  {
    this.zzzH = paramInt1;
    this.zzaro = paramInt2;
    this.zzarp = paramLocationRequestInternal;
    zzd localzzd;
    zzc localzzc;
    if (paramIBinder1 == null)
    {
      localzzd = null;
      this.zzarq = localzzd;
      this.mPendingIntent = paramPendingIntent;
      localzzc = null;
      if (paramIBinder2 != null)
        break label64;
    }
    while (true)
    {
      this.zzarr = localzzc;
      return;
      localzzd = zzd.zza.zzbe(paramIBinder1);
      break;
      label64: localzzc = zzc.zza.zzbd(paramIBinder2);
    }
  }

  public static LocationRequestUpdateData zza(LocationRequestInternal paramLocationRequestInternal, zzc paramzzc)
  {
    return new LocationRequestUpdateData(1, 1, paramLocationRequestInternal, null, null, paramzzc.asBinder());
  }

  public static LocationRequestUpdateData zza(zzc paramzzc)
  {
    return new LocationRequestUpdateData(1, 2, null, null, null, paramzzc.asBinder());
  }

  public static LocationRequestUpdateData zzb(LocationRequestInternal paramLocationRequestInternal, PendingIntent paramPendingIntent)
  {
    return new LocationRequestUpdateData(1, 1, paramLocationRequestInternal, null, paramPendingIntent, null);
  }

  public static LocationRequestUpdateData zzb(LocationRequestInternal paramLocationRequestInternal, zzd paramzzd)
  {
    return new LocationRequestUpdateData(1, 1, paramLocationRequestInternal, paramzzd.asBinder(), null, null);
  }

  public static LocationRequestUpdateData zzb(zzd paramzzd)
  {
    return new LocationRequestUpdateData(1, 2, null, paramzzd.asBinder(), null, null);
  }

  public static LocationRequestUpdateData zze(PendingIntent paramPendingIntent)
  {
    return new LocationRequestUpdateData(1, 2, null, null, paramPendingIntent, null);
  }

  public int describeContents()
  {
    return 0;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzl.zza(this, paramParcel, paramInt);
  }

  IBinder zzsK()
  {
    if (this.zzarq == null)
      return null;
    return this.zzarq.asBinder();
  }

  IBinder zzsL()
  {
    if (this.zzarr == null)
      return null;
    return this.zzarr.asBinder();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.LocationRequestUpdateData
 * JD-Core Version:    0.6.2
 */