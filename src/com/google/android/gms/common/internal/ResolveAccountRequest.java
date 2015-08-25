package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<ResolveAccountRequest> CREATOR = new zzw();
  private final Account zzJc;
  private final int zzUa;
  final int zzzH;

  ResolveAccountRequest(int paramInt1, Account paramAccount, int paramInt2)
  {
    this.zzzH = paramInt1;
    this.zzJc = paramAccount;
    this.zzUa = paramInt2;
  }

  public ResolveAccountRequest(Account paramAccount, int paramInt)
  {
    this(1, paramAccount, paramInt);
  }

  public int describeContents()
  {
    return 0;
  }

  public Account getAccount()
  {
    return this.zzJc;
  }

  public int getSessionId()
  {
    return this.zzUa;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzw.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.ResolveAccountRequest
 * JD-Core Version:    0.6.2
 */