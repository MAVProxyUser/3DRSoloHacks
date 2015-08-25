package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class BitmapDescriptorParcelable
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private byte zzavP;
  private Bundle zzavQ;
  private Bitmap zzavR;
  private final int zzzH;

  BitmapDescriptorParcelable(int paramInt, byte paramByte, Bundle paramBundle, Bitmap paramBitmap)
  {
    this.zzzH = paramInt;
    this.zzavP = paramByte;
    this.zzavQ = paramBundle;
    this.zzavR = paramBitmap;
  }

  public int describeContents()
  {
    return 0;
  }

  public Bitmap getBitmap()
  {
    return this.zzavR;
  }

  public byte getType()
  {
    return this.zzavP;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }

  public Bundle zztV()
  {
    return this.zzavQ;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.BitmapDescriptorParcelable
 * JD-Core Version:    0.6.2
 */