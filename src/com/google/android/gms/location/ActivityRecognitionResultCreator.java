package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class ActivityRecognitionResultCreator
  implements Parcelable.Creator<ActivityRecognitionResult>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(ActivityRecognitionResult paramActivityRecognitionResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramActivityRecognitionResult.zzapB, false);
    zzb.zzc(paramParcel, 1000, paramActivityRecognitionResult.getVersionCode());
    zzb.zza(paramParcel, 2, paramActivityRecognitionResult.zzapC);
    zzb.zza(paramParcel, 3, paramActivityRecognitionResult.zzapD);
    zzb.zzc(paramParcel, 4, paramActivityRecognitionResult.zzapE);
    zzb.zzH(paramParcel, i);
  }

  public ActivityRecognitionResult createFromParcel(Parcel paramParcel)
  {
    long l1 = 0L;
    int i = 0;
    int j = zza.zzL(paramParcel);
    ArrayList localArrayList = null;
    long l2 = l1;
    int k = 0;
    while (paramParcel.dataPosition() < j)
    {
      int m = zza.zzK(paramParcel);
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        break;
      case 1:
        localArrayList = zza.zzc(paramParcel, m, DetectedActivity.CREATOR);
        break;
      case 1000:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        l2 = zza.zzi(paramParcel, m);
        break;
      case 3:
        l1 = zza.zzi(paramParcel, m);
        break;
      case 4:
        i = zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new ActivityRecognitionResult(k, localArrayList, l2, l1, i);
  }

  public ActivityRecognitionResult[] newArray(int paramInt)
  {
    return new ActivityRecognitionResult[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.ActivityRecognitionResultCreator
 * JD-Core Version:    0.6.2
 */