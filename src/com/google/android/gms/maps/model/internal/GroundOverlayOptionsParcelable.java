package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class GroundOverlayOptionsParcelable
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  private BitmapDescriptorParcelable zzavS;
  private final int zzzH;

  public GroundOverlayOptionsParcelable()
  {
    this.zzzH = 1;
  }

  GroundOverlayOptionsParcelable(int paramInt, BitmapDescriptorParcelable paramBitmapDescriptorParcelable)
  {
    this.zzzH = paramInt;
    this.zzavS = paramBitmapDescriptorParcelable;
  }

  public int describeContents()
  {
    return 0;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }

  public BitmapDescriptorParcelable zztW()
  {
    return this.zzavS;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.GroundOverlayOptionsParcelable
 * JD-Core Version:    0.6.2
 */