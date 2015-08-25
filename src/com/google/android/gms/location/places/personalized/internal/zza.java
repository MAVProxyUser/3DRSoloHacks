package com.google.android.gms.location.places.personalized.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<TestDataImpl>
{
  static void zza(TestDataImpl paramTestDataImpl, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramTestDataImpl.zzto(), false);
    zzb.zzc(paramParcel, 1000, paramTestDataImpl.zzzH);
    zzb.zzH(paramParcel, i);
  }

  public TestDataImpl zzea(Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
    String str = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(k))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, k);
        break;
      case 1:
        str = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 1000:
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new TestDataImpl(j, str);
  }

  public TestDataImpl[] zzgf(int paramInt)
  {
    return new TestDataImpl[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.internal.zza
 * JD-Core Version:    0.6.2
 */