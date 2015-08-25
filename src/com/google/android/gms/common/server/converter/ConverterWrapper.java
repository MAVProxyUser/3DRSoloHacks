package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;

public class ConverterWrapper
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private final StringToIntConverter zzUs;
  private final int zzzH;

  ConverterWrapper(int paramInt, StringToIntConverter paramStringToIntConverter)
  {
    this.zzzH = paramInt;
    this.zzUs = paramStringToIntConverter;
  }

  private ConverterWrapper(StringToIntConverter paramStringToIntConverter)
  {
    this.zzzH = 1;
    this.zzUs = paramStringToIntConverter;
  }

  public static ConverterWrapper zza(FastJsonResponse.zza<?, ?> paramzza)
  {
    if ((paramzza instanceof StringToIntConverter))
      return new ConverterWrapper((StringToIntConverter)paramzza);
    throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
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
    zza.zza(this, paramParcel, paramInt);
  }

  StringToIntConverter zzmt()
  {
    return this.zzUs;
  }

  public FastJsonResponse.zza<?, ?> zzmu()
  {
    if (this.zzUs != null)
      return this.zzUs;
    throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.converter.ConverterWrapper
 * JD-Core Version:    0.6.2
 */