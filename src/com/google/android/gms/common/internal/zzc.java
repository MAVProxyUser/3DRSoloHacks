package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<AuthAccountRequest>
{
  static void zza(AuthAccountRequest paramAuthAccountRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramAuthAccountRequest.zzzH);
    zzb.zza(paramParcel, 2, paramAuthAccountRequest.zzSS, false);
    zzb.zza(paramParcel, 3, paramAuthAccountRequest.zzST, paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public AuthAccountRequest zzE(Parcel paramParcel)
  {
    Scope[] arrayOfScope = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    IBinder localIBinder = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        localIBinder = zza.zzp(paramParcel, k);
        break;
      case 3:
        arrayOfScope = (Scope[])zza.zzb(paramParcel, k, Scope.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new AuthAccountRequest(j, localIBinder, arrayOfScope);
  }

  public AuthAccountRequest[] zzaL(int paramInt)
  {
    return new AuthAccountRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzc
 * JD-Core Version:    0.6.2
 */