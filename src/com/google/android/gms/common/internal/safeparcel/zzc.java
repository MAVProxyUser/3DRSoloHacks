package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzv;

public final class zzc
{
  public static <T extends SafeParcelable> T zza(Intent paramIntent, String paramString, Parcelable.Creator<T> paramCreator)
  {
    byte[] arrayOfByte = paramIntent.getByteArrayExtra(paramString);
    if (arrayOfByte == null)
      return null;
    return zza(arrayOfByte, paramCreator);
  }

  public static <T extends SafeParcelable> T zza(byte[] paramArrayOfByte, Parcelable.Creator<T> paramCreator)
  {
    zzv.zzr(paramCreator);
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(paramArrayOfByte, 0, paramArrayOfByte.length);
    localParcel.setDataPosition(0);
    SafeParcelable localSafeParcelable = (SafeParcelable)paramCreator.createFromParcel(localParcel);
    localParcel.recycle();
    return localSafeParcelable;
  }

  public static <T extends SafeParcelable> void zza(T paramT, Intent paramIntent, String paramString)
  {
    paramIntent.putExtra(paramString, zza(paramT));
  }

  public static <T extends SafeParcelable> byte[] zza(T paramT)
  {
    Parcel localParcel = Parcel.obtain();
    paramT.writeToParcel(localParcel, 0);
    byte[] arrayOfByte = localParcel.marshall();
    localParcel.recycle();
    return arrayOfByte;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.safeparcel.zzc
 * JD-Core Version:    0.6.2
 */