package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzab
  implements Parcelable.Creator<ValidateAccountRequest>
{
  static void zza(ValidateAccountRequest paramValidateAccountRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramValidateAccountRequest.zzzH);
    zzb.zzc(paramParcel, 2, paramValidateAccountRequest.zzmq());
    zzb.zza(paramParcel, 3, paramValidateAccountRequest.zzSS, false);
    zzb.zza(paramParcel, 4, paramValidateAccountRequest.zzmr(), paramInt, false);
    zzb.zza(paramParcel, 5, paramValidateAccountRequest.zzms(), false);
    zzb.zza(paramParcel, 6, paramValidateAccountRequest.getCallingPackage(), false);
    zzb.zzH(paramParcel, i);
  }

  public ValidateAccountRequest zzJ(Parcel paramParcel)
  {
    int i = 0;
    String str = null;
    int j = zza.zzL(paramParcel);
    Bundle localBundle = null;
    Scope[] arrayOfScope = null;
    IBinder localIBinder = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
    {
      int m = zza.zzK(paramParcel);
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        break;
      case 1:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        i = zza.zzg(paramParcel, m);
        break;
      case 3:
        localIBinder = zza.zzp(paramParcel, m);
        break;
      case 4:
        arrayOfScope = (Scope[])zza.zzb(paramParcel, m, Scope.CREATOR);
        break;
      case 5:
        localBundle = zza.zzq(paramParcel, m);
        break;
      case 6:
        str = zza.zzo(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new ValidateAccountRequest(k, i, localIBinder, arrayOfScope, localBundle, str);
  }

  public ValidateAccountRequest[] zzaU(int paramInt)
  {
    return new ValidateAccountRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzab
 * JD-Core Version:    0.6.2
 */