package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Set;

public class AuthAccountRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<AuthAccountRequest> CREATOR = new zzc();
  final IBinder zzSS;
  final Scope[] zzST;
  final int zzzH;

  AuthAccountRequest(int paramInt, IBinder paramIBinder, Scope[] paramArrayOfScope)
  {
    this.zzzH = paramInt;
    this.zzSS = paramIBinder;
    this.zzST = paramArrayOfScope;
  }

  public AuthAccountRequest(zzo paramzzo, Set<Scope> paramSet)
  {
    this(1, paramzzo.asBinder(), (Scope[])paramSet.toArray(new Scope[paramSet.size()]));
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.AuthAccountRequest
 * JD-Core Version:    0.6.2
 */