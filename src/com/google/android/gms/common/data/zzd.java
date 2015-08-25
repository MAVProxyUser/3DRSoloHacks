package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class zzd<T extends SafeParcelable> extends AbstractDataBuffer<T>
{
  private static final String[] zzRy = { "data" };
  private final Parcelable.Creator<T> zzRz;

  public zzd(DataHolder paramDataHolder, Parcelable.Creator<T> paramCreator)
  {
    super(paramDataHolder);
    this.zzRz = paramCreator;
  }

  public T zzaC(int paramInt)
  {
    byte[] arrayOfByte = this.zzPy.zzg("data", paramInt, this.zzPy.zzaD(paramInt));
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(arrayOfByte, 0, arrayOfByte.length);
    localParcel.setDataPosition(0);
    SafeParcelable localSafeParcelable = (SafeParcelable)this.zzRz.createFromParcel(localParcel);
    localParcel.recycle();
    return localSafeParcelable;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zzd
 * JD-Core Version:    0.6.2
 */