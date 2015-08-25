package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class AccountChangeEventsRequestCreator
  implements Parcelable.Creator<AccountChangeEventsRequest>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(AccountChangeEventsRequest paramAccountChangeEventsRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramAccountChangeEventsRequest.zzKu);
    zzb.zzc(paramParcel, 2, paramAccountChangeEventsRequest.zzKy);
    zzb.zza(paramParcel, 3, paramAccountChangeEventsRequest.zzKw, false);
    zzb.zza(paramParcel, 4, paramAccountChangeEventsRequest.zzJc, paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public AccountChangeEventsRequest createFromParcel(Parcel paramParcel)
  {
    Account localAccount = null;
    int i = 0;
    int j = zza.zzL(paramParcel);
    String str = null;
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
        str = zza.zzo(paramParcel, m);
        break;
      case 4:
        localAccount = (Account)zza.zza(paramParcel, m, Account.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new AccountChangeEventsRequest(k, i, str, localAccount);
  }

  public AccountChangeEventsRequest[] newArray(int paramInt)
  {
    return new AccountChangeEventsRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.AccountChangeEventsRequestCreator
 * JD-Core Version:    0.6.2
 */