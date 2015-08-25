package com.google.android.gms.location.places.internal;

import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzno;
import com.google.android.gms.internal.zznx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class zzq extends com.google.android.gms.common.data.zzc
{
  private final String TAG = "SafeDataBufferRef";

  public zzq(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }

  protected String zzA(String paramString1, String paramString2)
  {
    if ((zzbF(paramString1)) && (!zzbH(paramString1)))
      paramString2 = getString(paramString1);
    return paramString2;
  }

  protected <E extends SafeParcelable> E zza(String paramString, Parcelable.Creator<E> paramCreator)
  {
    byte[] arrayOfByte = zzc(paramString, null);
    if (arrayOfByte == null)
      return null;
    return com.google.android.gms.common.internal.safeparcel.zzc.zza(arrayOfByte, paramCreator);
  }

  protected <E extends SafeParcelable> List<E> zza(String paramString, Parcelable.Creator<E> paramCreator, List<E> paramList)
  {
    byte[] arrayOfByte = zzc(paramString, null);
    if (arrayOfByte == null);
    do
      while (true)
      {
        return paramList;
        try
        {
          zzno localzzno = zzno.zzt(arrayOfByte);
          if (localzzno.zzaNu != null)
          {
            ArrayList localArrayList = new ArrayList(localzzno.zzaNu.length);
            byte[][] arrayOfByte1 = localzzno.zzaNu;
            int i = arrayOfByte1.length;
            for (int j = 0; j < i; j++)
              localArrayList.add(com.google.android.gms.common.internal.safeparcel.zzc.zza(arrayOfByte1[j], paramCreator));
            return localArrayList;
          }
        }
        catch (zznx localzznx)
        {
        }
      }
    while (!Log.isLoggable("SafeDataBufferRef", 6));
    Log.e("SafeDataBufferRef", "Cannot parse byte[]", localzznx);
    return paramList;
  }

  protected List<Integer> zza(String paramString, List<Integer> paramList)
  {
    byte[] arrayOfByte = zzc(paramString, null);
    if (arrayOfByte == null);
    do
      while (true)
      {
        return paramList;
        try
        {
          zzno localzzno = zzno.zzt(arrayOfByte);
          if (localzzno.zzaNt != null)
          {
            ArrayList localArrayList = new ArrayList(localzzno.zzaNt.length);
            for (int i = 0; i < localzzno.zzaNt.length; i++)
              localArrayList.add(Integer.valueOf(localzzno.zzaNt[i]));
            return localArrayList;
          }
        }
        catch (zznx localzznx)
        {
        }
      }
    while (!Log.isLoggable("SafeDataBufferRef", 6));
    Log.e("SafeDataBufferRef", "Cannot parse byte[]", localzznx);
    return paramList;
  }

  protected float zzb(String paramString, float paramFloat)
  {
    if ((zzbF(paramString)) && (!zzbH(paramString)))
      paramFloat = getFloat(paramString);
    return paramFloat;
  }

  protected List<String> zzb(String paramString, List<String> paramList)
  {
    byte[] arrayOfByte = zzc(paramString, null);
    if (arrayOfByte == null);
    do
      while (true)
      {
        return paramList;
        try
        {
          zzno localzzno = zzno.zzt(arrayOfByte);
          if (localzzno.zzaNs != null)
          {
            List localList = Arrays.asList(localzzno.zzaNs);
            return localList;
          }
        }
        catch (zznx localzznx)
        {
        }
      }
    while (!Log.isLoggable("SafeDataBufferRef", 6));
    Log.e("SafeDataBufferRef", "Cannot parse byte[]", localzznx);
    return paramList;
  }

  protected byte[] zzc(String paramString, byte[] paramArrayOfByte)
  {
    if ((zzbF(paramString)) && (!zzbH(paramString)))
      paramArrayOfByte = getByteArray(paramString);
    return paramArrayOfByte;
  }

  protected boolean zzh(String paramString, boolean paramBoolean)
  {
    if ((zzbF(paramString)) && (!zzbH(paramString)))
      paramBoolean = getBoolean(paramString);
    return paramBoolean;
  }

  protected int zzy(String paramString, int paramInt)
  {
    if ((zzbF(paramString)) && (!zzbH(paramString)))
      paramInt = getInteger(paramString);
    return paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzq
 * JD-Core Version:    0.6.2
 */