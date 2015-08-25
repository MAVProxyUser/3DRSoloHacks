package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AccountChangeEventsRequest
  implements SafeParcelable
{
  public static final AccountChangeEventsRequestCreator CREATOR = new AccountChangeEventsRequestCreator();
  Account zzJc;
  final int zzKu;

  @Deprecated
  String zzKw;
  int zzKy;

  public AccountChangeEventsRequest()
  {
    this.zzKu = 1;
  }

  AccountChangeEventsRequest(int paramInt1, int paramInt2, String paramString, Account paramAccount)
  {
    this.zzKu = paramInt1;
    this.zzKy = paramInt2;
    this.zzKw = paramString;
    if ((paramAccount == null) && (!TextUtils.isEmpty(paramString)))
    {
      this.zzJc = new Account(paramString, "com.google");
      return;
    }
    this.zzJc = paramAccount;
  }

  public int describeContents()
  {
    return 0;
  }

  public Account getAccount()
  {
    return this.zzJc;
  }

  public String getAccountName()
  {
    return this.zzKw;
  }

  public int getEventIndex()
  {
    return this.zzKy;
  }

  public AccountChangeEventsRequest setAccount(Account paramAccount)
  {
    this.zzJc = paramAccount;
    return this;
  }

  public AccountChangeEventsRequest setAccountName(String paramString)
  {
    this.zzKw = paramString;
    return this;
  }

  public AccountChangeEventsRequest setEventIndex(int paramInt)
  {
    this.zzKy = paramInt;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    AccountChangeEventsRequestCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.AccountChangeEventsRequest
 * JD-Core Version:    0.6.2
 */