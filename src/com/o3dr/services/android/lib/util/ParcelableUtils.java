package com.o3dr.services.android.lib.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelableUtils
{
  public static byte[] marshall(Parcelable paramParcelable)
  {
    Parcel localParcel = Parcel.obtain();
    paramParcelable.writeToParcel(localParcel, 0);
    byte[] arrayOfByte = localParcel.marshall();
    localParcel.recycle();
    return arrayOfByte;
  }

  private static Parcel unmarshall(byte[] paramArrayOfByte)
  {
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(paramArrayOfByte, 0, paramArrayOfByte.length);
    localParcel.setDataPosition(0);
    return localParcel;
  }

  private static Parcel unmarshall(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(paramArrayOfByte, paramInt1, paramInt2);
    localParcel.setDataPosition(0);
    return localParcel;
  }

  public static <T> T unmarshall(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Parcelable.Creator<T> paramCreator)
  {
    Parcel localParcel = unmarshall(paramArrayOfByte, paramInt1, paramInt2);
    Object localObject = paramCreator.createFromParcel(localParcel);
    localParcel.recycle();
    return localObject;
  }

  public static <T> T unmarshall(byte[] paramArrayOfByte, Parcelable.Creator<T> paramCreator)
  {
    Parcel localParcel = unmarshall(paramArrayOfByte);
    Object localObject = paramCreator.createFromParcel(localParcel);
    localParcel.recycle();
    return localObject;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.util.ParcelableUtils
 * JD-Core Version:    0.6.2
 */