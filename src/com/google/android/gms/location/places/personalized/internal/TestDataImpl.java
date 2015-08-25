package com.google.android.gms.location.places.personalized.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.location.places.personalized.zzf;

public class TestDataImpl extends zzf
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private final String zzatp;
  final int zzzH;

  TestDataImpl(int paramInt, String paramString)
  {
    this.zzzH = paramInt;
    this.zzatp = paramString;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof TestDataImpl))
      return false;
    TestDataImpl localTestDataImpl = (TestDataImpl)paramObject;
    return this.zzatp.equals(localTestDataImpl.zzatp);
  }

  public int hashCode()
  {
    return this.zzatp.hashCode();
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("testName", this.zzatp).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }

  public String zzto()
  {
    return this.zzatp;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.internal.TestDataImpl
 * JD-Core Version:    0.6.2
 */