package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountResponse
  implements SafeParcelable
{
  public static final Parcelable.Creator<ResolveAccountResponse> CREATOR = new zzx();
  private boolean zzQe;
  private ConnectionResult zzRm;
  IBinder zzSS;
  private boolean zzUb;
  final int zzzH;

  public ResolveAccountResponse(int paramInt)
  {
    this(new ConnectionResult(paramInt, null));
  }

  ResolveAccountResponse(int paramInt, IBinder paramIBinder, ConnectionResult paramConnectionResult, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.zzzH = paramInt;
    this.zzSS = paramIBinder;
    this.zzRm = paramConnectionResult;
    this.zzQe = paramBoolean1;
    this.zzUb = paramBoolean2;
  }

  public ResolveAccountResponse(ConnectionResult paramConnectionResult)
  {
    this(1, null, paramConnectionResult, false, false);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    ResolveAccountResponse localResolveAccountResponse;
    do
    {
      return true;
      if (!(paramObject instanceof ResolveAccountResponse))
        return false;
      localResolveAccountResponse = (ResolveAccountResponse)paramObject;
    }
    while ((this.zzRm.equals(localResolveAccountResponse.zzRm)) && (zzmm().equals(localResolveAccountResponse.zzmm())));
    return false;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzx.zza(this, paramParcel, paramInt);
  }

  public zzo zzmm()
  {
    return zzo.zza.zzQ(this.zzSS);
  }

  public ConnectionResult zzmn()
  {
    return this.zzRm;
  }

  public boolean zzmo()
  {
    return this.zzQe;
  }

  public boolean zzmp()
  {
    return this.zzUb;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.ResolveAccountResponse
 * JD-Core Version:    0.6.2
 */