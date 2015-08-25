package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class MarkerOptionsParcelable
  implements SafeParcelable
{
  public static final zzm CREATOR = new zzm();
  private BitmapDescriptorParcelable zzavT;
  private final int zzzH;

  public MarkerOptionsParcelable()
  {
    this.zzzH = 1;
  }

  MarkerOptionsParcelable(int paramInt, BitmapDescriptorParcelable paramBitmapDescriptorParcelable)
  {
    this.zzzH = paramInt;
    this.zzavT = paramBitmapDescriptorParcelable;
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
    zzm.zza(this, paramParcel, paramInt);
  }

  public BitmapDescriptorParcelable zztY()
  {
    return this.zzavT;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.MarkerOptionsParcelable
 * JD-Core Version:    0.6.2
 */