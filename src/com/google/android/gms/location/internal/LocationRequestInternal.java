package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class LocationRequestInternal
  implements SafeParcelable
{
  public static final zzk CREATOR = new zzk();
  static final List<ClientIdentity> zzarj = Collections.emptyList();
  final String mTag;
  LocationRequest zzaft;
  boolean zzark;
  boolean zzarl;
  boolean zzarm;
  List<ClientIdentity> zzarn;
  private final int zzzH;

  LocationRequestInternal(int paramInt, LocationRequest paramLocationRequest, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, List<ClientIdentity> paramList, String paramString)
  {
    this.zzzH = paramInt;
    this.zzaft = paramLocationRequest;
    this.zzark = paramBoolean1;
    this.zzarl = paramBoolean2;
    this.zzarm = paramBoolean3;
    this.zzarn = paramList;
    this.mTag = paramString;
  }

  public static LocationRequestInternal zza(String paramString, LocationRequest paramLocationRequest)
  {
    return new LocationRequestInternal(1, paramLocationRequest, false, true, true, zzarj, paramString);
  }

  public static LocationRequestInternal zzb(LocationRequest paramLocationRequest)
  {
    return zza(null, paramLocationRequest);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationRequestInternal));
    LocationRequestInternal localLocationRequestInternal;
    do
    {
      return false;
      localLocationRequestInternal = (LocationRequestInternal)paramObject;
    }
    while ((!zzu.equal(this.zzaft, localLocationRequestInternal.zzaft)) || (this.zzark != localLocationRequestInternal.zzark) || (this.zzarl != localLocationRequestInternal.zzarl) || (this.zzarm != localLocationRequestInternal.zzarm) || (!zzu.equal(this.zzarn, localLocationRequestInternal.zzarn)));
    return true;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    return this.zzaft.hashCode();
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.zzaft.toString());
    localStringBuilder.append(" requestNlpDebugInfo=");
    localStringBuilder.append(this.zzark);
    localStringBuilder.append(" restorePendingIntentListeners=");
    localStringBuilder.append(this.zzarl);
    localStringBuilder.append(" triggerUpdate=");
    localStringBuilder.append(this.zzarm);
    localStringBuilder.append(" clients=");
    localStringBuilder.append(this.zzarn);
    if (this.mTag != null)
    {
      localStringBuilder.append(" tag=");
      localStringBuilder.append(this.mTag);
    }
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.LocationRequestInternal
 * JD-Core Version:    0.6.2
 */