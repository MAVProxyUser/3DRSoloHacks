package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class Tile
  implements SafeParcelable
{
  public static final zzm CREATOR = new zzm();
  public final byte[] data;
  public final int height;
  public final int width;
  private final int zzzH;

  Tile(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    this.zzzH = paramInt1;
    this.width = paramInt2;
    this.height = paramInt3;
    this.data = paramArrayOfByte;
  }

  public Tile(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    this(1, paramInt1, paramInt2, paramArrayOfByte);
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
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.Tile
 * JD-Core Version:    0.6.2
 */