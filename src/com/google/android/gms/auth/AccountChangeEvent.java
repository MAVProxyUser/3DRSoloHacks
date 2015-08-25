package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzv;

public class AccountChangeEvent
  implements SafeParcelable
{
  public static final AccountChangeEventCreator CREATOR = new AccountChangeEventCreator();
  final int zzKu;
  final long zzKv;
  final String zzKw;
  final int zzKx;
  final int zzKy;
  final String zzKz;

  AccountChangeEvent(int paramInt1, long paramLong, String paramString1, int paramInt2, int paramInt3, String paramString2)
  {
    this.zzKu = paramInt1;
    this.zzKv = paramLong;
    this.zzKw = ((String)zzv.zzr(paramString1));
    this.zzKx = paramInt2;
    this.zzKy = paramInt3;
    this.zzKz = paramString2;
  }

  public AccountChangeEvent(long paramLong, String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    this.zzKu = 1;
    this.zzKv = paramLong;
    this.zzKw = ((String)zzv.zzr(paramString1));
    this.zzKx = paramInt1;
    this.zzKy = paramInt2;
    this.zzKz = paramString2;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this);
    AccountChangeEvent localAccountChangeEvent;
    do
    {
      return true;
      if (!(paramObject instanceof AccountChangeEvent))
        break;
      localAccountChangeEvent = (AccountChangeEvent)paramObject;
    }
    while ((this.zzKu == localAccountChangeEvent.zzKu) && (this.zzKv == localAccountChangeEvent.zzKv) && (zzu.equal(this.zzKw, localAccountChangeEvent.zzKw)) && (this.zzKx == localAccountChangeEvent.zzKx) && (this.zzKy == localAccountChangeEvent.zzKy) && (zzu.equal(this.zzKz, localAccountChangeEvent.zzKz)));
    return false;
    return false;
  }

  public String getAccountName()
  {
    return this.zzKw;
  }

  public String getChangeData()
  {
    return this.zzKz;
  }

  public int getChangeType()
  {
    return this.zzKx;
  }

  public int getEventIndex()
  {
    return this.zzKy;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = Integer.valueOf(this.zzKu);
    arrayOfObject[1] = Long.valueOf(this.zzKv);
    arrayOfObject[2] = this.zzKw;
    arrayOfObject[3] = Integer.valueOf(this.zzKx);
    arrayOfObject[4] = Integer.valueOf(this.zzKy);
    arrayOfObject[5] = this.zzKz;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    String str = "UNKNOWN";
    switch (this.zzKx)
    {
    default:
    case 1:
    case 2:
    case 4:
    case 3:
    }
    while (true)
    {
      return "AccountChangeEvent {accountName = " + this.zzKw + ", changeType = " + str + ", changeData = " + this.zzKz + ", eventIndex = " + this.zzKy + "}";
      str = "ADDED";
      continue;
      str = "REMOVED";
      continue;
      str = "RENAMED_TO";
      continue;
      str = "RENAMED_FROM";
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    AccountChangeEventCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.AccountChangeEvent
 * JD-Core Version:    0.6.2
 */