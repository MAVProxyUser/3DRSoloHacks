package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import java.util.List;

public class AccountChangeEventsResponse
  implements SafeParcelable
{
  public static final AccountChangeEventsResponseCreator CREATOR = new AccountChangeEventsResponseCreator();
  final int zzKu;
  final List<AccountChangeEvent> zznq;

  AccountChangeEventsResponse(int paramInt, List<AccountChangeEvent> paramList)
  {
    this.zzKu = paramInt;
    this.zznq = ((List)zzv.zzr(paramList));
  }

  public AccountChangeEventsResponse(List<AccountChangeEvent> paramList)
  {
    this.zzKu = 1;
    this.zznq = ((List)zzv.zzr(paramList));
  }

  public int describeContents()
  {
    return 0;
  }

  public List<AccountChangeEvent> getEvents()
  {
    return this.zznq;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    AccountChangeEventsResponseCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.AccountChangeEventsResponse
 * JD-Core Version:    0.6.2
 */