package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;

public final class Scope
  implements SafeParcelable
{
  public static final Parcelable.Creator<Scope> CREATOR = new zzj();
  private final String zzQT;
  final int zzzH;

  Scope(int paramInt, String paramString)
  {
    zzv.zzh(paramString, "scopeUri must not be null or empty");
    this.zzzH = paramInt;
    this.zzQT = paramString;
  }

  public Scope(String paramString)
  {
    this(1, paramString);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Scope))
      return false;
    return this.zzQT.equals(((Scope)paramObject).zzQT);
  }

  public int hashCode()
  {
    return this.zzQT.hashCode();
  }

  public String toString()
  {
    return this.zzQT;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }

  public String zzle()
  {
    return this.zzQT;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Scope
 * JD-Core Version:    0.6.2
 */