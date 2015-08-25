package com.google.android.gms.maps.model.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CameraUpdateParcelable
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private int type;
  private Bundle zzavQ;
  private final int zzzH;

  CameraUpdateParcelable(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    this.zzzH = paramInt1;
    this.type = paramInt2;
    this.zzavQ = paramBundle;
  }

  public int describeContents()
  {
    return 0;
  }

  public int getType()
  {
    return this.type;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }

  public Bundle zztV()
  {
    return this.zzavQ;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.CameraUpdateParcelable
 * JD-Core Version:    0.6.2
 */