package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class AccountChangeEventCreator
  implements Parcelable.Creator<AccountChangeEvent>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(AccountChangeEvent paramAccountChangeEvent, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramAccountChangeEvent.zzKu);
    zzb.zza(paramParcel, 2, paramAccountChangeEvent.zzKv);
    zzb.zza(paramParcel, 3, paramAccountChangeEvent.zzKw, false);
    zzb.zzc(paramParcel, 4, paramAccountChangeEvent.zzKx);
    zzb.zzc(paramParcel, 5, paramAccountChangeEvent.zzKy);
    zzb.zza(paramParcel, 6, paramAccountChangeEvent.zzKz, false);
    zzb.zzH(paramParcel, i);
  }

  public AccountChangeEvent createFromParcel(Parcel paramParcel)
  {
    String str1 = null;
    int i = 0;
    int j = zza.zzL(paramParcel);
    long l = 0L;
    int k = 0;
    String str2 = null;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = zza.zzK(paramParcel);
      switch (zza.zzaV(n))
      {
      default:
        zza.zzb(paramParcel, n);
        break;
      case 1:
        m = zza.zzg(paramParcel, n);
        break;
      case 2:
        l = zza.zzi(paramParcel, n);
        break;
      case 3:
        str2 = zza.zzo(paramParcel, n);
        break;
      case 4:
        k = zza.zzg(paramParcel, n);
        break;
      case 5:
        i = zza.zzg(paramParcel, n);
        break;
      case 6:
        str1 = zza.zzo(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new AccountChangeEvent(m, l, str2, k, i, str1);
  }

  public AccountChangeEvent[] newArray(int paramInt)
  {
    return new AccountChangeEvent[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.AccountChangeEventCreator
 * JD-Core Version:    0.6.2
 */