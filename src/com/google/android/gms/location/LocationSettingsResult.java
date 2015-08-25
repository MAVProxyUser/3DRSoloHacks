package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LocationSettingsResult
  implements Result, SafeParcelable
{
  public static final LocationSettingsResultCreator CREATOR = new LocationSettingsResultCreator();
  private final Status zzKr;
  private final LocationSettingsStates zzaqp;
  private final int zzzH;

  LocationSettingsResult(int paramInt, Status paramStatus, LocationSettingsStates paramLocationSettingsStates)
  {
    this.zzzH = paramInt;
    this.zzKr = paramStatus;
    this.zzaqp = paramLocationSettingsStates;
  }

  public LocationSettingsResult(Status paramStatus)
  {
    this(1, paramStatus, null);
  }

  public int describeContents()
  {
    return 0;
  }

  public LocationSettingsStates getLocationSettingsStates()
  {
    return this.zzaqp;
  }

  public Status getStatus()
  {
    return this.zzKr;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationSettingsResultCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationSettingsResult
 * JD-Core Version:    0.6.2
 */