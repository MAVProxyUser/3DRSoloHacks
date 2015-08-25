package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

public class ClientIdentity
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public final String packageName;
  public final int uid;
  private final int zzzH;

  ClientIdentity(int paramInt1, int paramInt2, String paramString)
  {
    this.zzzH = paramInt1;
    this.uid = paramInt2;
    this.packageName = paramString;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ClientIdentity));
    ClientIdentity localClientIdentity;
    do
    {
      return false;
      localClientIdentity = (ClientIdentity)paramObject;
    }
    while ((localClientIdentity.uid != this.uid) || (!zzu.equal(localClientIdentity.packageName, this.packageName)));
    return true;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    return this.uid;
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.uid);
    arrayOfObject[1] = this.packageName;
    return String.format("%d:%s", arrayOfObject);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.ClientIdentity
 * JD-Core Version:    0.6.2
 */