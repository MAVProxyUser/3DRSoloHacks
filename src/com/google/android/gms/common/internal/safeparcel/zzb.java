package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class zzb
{
  private static int zzF(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(0xFFFF0000 | paramInt);
    paramParcel.writeInt(0);
    return paramParcel.dataPosition();
  }

  private static void zzG(Parcel paramParcel, int paramInt)
  {
    int i = paramParcel.dataPosition();
    int j = i - paramInt;
    paramParcel.setDataPosition(paramInt - 4);
    paramParcel.writeInt(j);
    paramParcel.setDataPosition(i);
  }

  public static void zzH(Parcel paramParcel, int paramInt)
  {
    zzG(paramParcel, paramInt);
  }

  public static int zzM(Parcel paramParcel)
  {
    return zzF(paramParcel, 20293);
  }

  public static void zza(Parcel paramParcel, int paramInt, byte paramByte)
  {
    zzb(paramParcel, paramInt, 4);
    paramParcel.writeInt(paramByte);
  }

  public static void zza(Parcel paramParcel, int paramInt, double paramDouble)
  {
    zzb(paramParcel, paramInt, 8);
    paramParcel.writeDouble(paramDouble);
  }

  public static void zza(Parcel paramParcel, int paramInt, float paramFloat)
  {
    zzb(paramParcel, paramInt, 4);
    paramParcel.writeFloat(paramFloat);
  }

  public static void zza(Parcel paramParcel, int paramInt, long paramLong)
  {
    zzb(paramParcel, paramInt, 8);
    paramParcel.writeLong(paramLong);
  }

  public static void zza(Parcel paramParcel, int paramInt, Bundle paramBundle, boolean paramBoolean)
  {
    if (paramBundle == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeBundle(paramBundle);
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, IBinder paramIBinder, boolean paramBoolean)
  {
    if (paramIBinder == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeStrongBinder(paramIBinder);
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel1, int paramInt, Parcel paramParcel2, boolean paramBoolean)
  {
    if (paramParcel2 == null)
    {
      if (paramBoolean)
        zzb(paramParcel1, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel1, paramInt);
    paramParcel1.appendFrom(paramParcel2, 0, paramParcel2.dataSize());
    zzG(paramParcel1, i);
  }

  public static void zza(Parcel paramParcel, int paramInt1, Parcelable paramParcelable, int paramInt2, boolean paramBoolean)
  {
    if (paramParcelable == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt1, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt1);
    paramParcelable.writeToParcel(paramParcel, paramInt2);
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, Boolean paramBoolean, boolean paramBoolean1)
  {
    if (paramBoolean == null)
    {
      if (paramBoolean1)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    zzb(paramParcel, paramInt, 4);
    boolean bool = paramBoolean.booleanValue();
    int i = 0;
    if (bool)
      i = 1;
    paramParcel.writeInt(i);
  }

  public static void zza(Parcel paramParcel, int paramInt, Integer paramInteger, boolean paramBoolean)
  {
    if (paramInteger == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    zzb(paramParcel, paramInt, 4);
    paramParcel.writeInt(paramInteger.intValue());
  }

  public static void zza(Parcel paramParcel, int paramInt, Long paramLong, boolean paramBoolean)
  {
    if (paramLong == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    zzb(paramParcel, paramInt, 8);
    paramParcel.writeLong(paramLong.longValue());
  }

  public static void zza(Parcel paramParcel, int paramInt, String paramString, boolean paramBoolean)
  {
    if (paramString == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeString(paramString);
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, List<Integer> paramList, boolean paramBoolean)
  {
    if (paramList == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    int j = paramList.size();
    paramParcel.writeInt(j);
    for (int k = 0; k < j; k++)
      paramParcel.writeInt(((Integer)paramList.get(k)).intValue());
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, short paramShort)
  {
    zzb(paramParcel, paramInt, 4);
    paramParcel.writeInt(paramShort);
  }

  public static void zza(Parcel paramParcel, int paramInt, boolean paramBoolean)
  {
    zzb(paramParcel, paramInt, 4);
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      paramParcel.writeInt(i);
      return;
    }
  }

  public static void zza(Parcel paramParcel, int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (paramArrayOfByte == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeByteArray(paramArrayOfByte);
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, int[] paramArrayOfInt, boolean paramBoolean)
  {
    if (paramArrayOfInt == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeIntArray(paramArrayOfInt);
    zzG(paramParcel, i);
  }

  public static <T extends Parcelable> void zza(Parcel paramParcel, int paramInt1, T[] paramArrayOfT, int paramInt2, boolean paramBoolean)
  {
    if (paramArrayOfT == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt1, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt1);
    int j = paramArrayOfT.length;
    paramParcel.writeInt(j);
    int k = 0;
    if (k < j)
    {
      T ? = paramArrayOfT[k];
      if (? == null)
        paramParcel.writeInt(0);
      while (true)
      {
        k++;
        break;
        zza(paramParcel, ?, paramInt2);
      }
    }
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, String[] paramArrayOfString, boolean paramBoolean)
  {
    if (paramArrayOfString == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeStringArray(paramArrayOfString);
    zzG(paramParcel, i);
  }

  public static void zza(Parcel paramParcel, int paramInt, byte[][] paramArrayOfByte, boolean paramBoolean)
  {
    int i = 0;
    if (paramArrayOfByte == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int j = zzF(paramParcel, paramInt);
    int k = paramArrayOfByte.length;
    paramParcel.writeInt(k);
    while (i < k)
    {
      paramParcel.writeByteArray(paramArrayOfByte[i]);
      i++;
    }
    zzG(paramParcel, j);
  }

  private static <T extends Parcelable> void zza(Parcel paramParcel, T paramT, int paramInt)
  {
    int i = paramParcel.dataPosition();
    paramParcel.writeInt(1);
    int j = paramParcel.dataPosition();
    paramT.writeToParcel(paramParcel, paramInt);
    int k = paramParcel.dataPosition();
    paramParcel.setDataPosition(i);
    paramParcel.writeInt(k - j);
    paramParcel.setDataPosition(k);
  }

  private static void zzb(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    if (paramInt2 >= 65535)
    {
      paramParcel.writeInt(0xFFFF0000 | paramInt1);
      paramParcel.writeInt(paramInt2);
      return;
    }
    paramParcel.writeInt(paramInt1 | paramInt2 << 16);
  }

  public static void zzb(Parcel paramParcel, int paramInt, List<String> paramList, boolean paramBoolean)
  {
    if (paramList == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeStringList(paramList);
    zzG(paramParcel, i);
  }

  public static void zzc(Parcel paramParcel, int paramInt1, int paramInt2)
  {
    zzb(paramParcel, paramInt1, 4);
    paramParcel.writeInt(paramInt2);
  }

  public static <T extends Parcelable> void zzc(Parcel paramParcel, int paramInt, List<T> paramList, boolean paramBoolean)
  {
    if (paramList == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    int j = paramList.size();
    paramParcel.writeInt(j);
    int k = 0;
    if (k < j)
    {
      Parcelable localParcelable = (Parcelable)paramList.get(k);
      if (localParcelable == null)
        paramParcel.writeInt(0);
      while (true)
      {
        k++;
        break;
        zza(paramParcel, localParcelable, 0);
      }
    }
    zzG(paramParcel, i);
  }

  public static void zzd(Parcel paramParcel, int paramInt, List paramList, boolean paramBoolean)
  {
    if (paramList == null)
    {
      if (paramBoolean)
        zzb(paramParcel, paramInt, 0);
      return;
    }
    int i = zzF(paramParcel, paramInt);
    paramParcel.writeList(paramList);
    zzG(paramParcel, i);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.safeparcel.zzb
 * JD-Core Version:    0.6.2
 */