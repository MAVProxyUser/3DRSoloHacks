package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.common.internal.zzv;

public class PlaceReport
  implements SafeParcelable
{
  public static final PlaceReportCreator CREATOR = new PlaceReportCreator();
  private final String mTag;
  private final String zzarP;
  private final String zzarQ;
  final int zzzH;

  PlaceReport(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.zzzH = paramInt;
    this.zzarP = paramString1;
    this.mTag = paramString2;
    this.zzarQ = paramString3;
  }

  public static PlaceReport create(String paramString1, String paramString2)
  {
    return zzf(paramString1, paramString2, "unknown");
  }

  private static boolean zzcI(String paramString)
  {
    int i = 1;
    int j = -1;
    switch (paramString.hashCode())
    {
    default:
    case -284840886:
    case -1194968642:
    case -1436706272:
    case 1287171955:
    case -262743844:
    }
    while (true)
    {
      switch (j)
      {
      default:
        i = 0;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      }
      return i;
      if (paramString.equals("unknown"))
      {
        j = 0;
        continue;
        if (paramString.equals("userReported"))
        {
          j = i;
          continue;
          if (paramString.equals("inferredGeofencing"))
          {
            j = 2;
            continue;
            if (paramString.equals("inferredRadioSignals"))
            {
              j = 3;
              continue;
              if (paramString.equals("inferredReverseGeocoding"))
                j = 4;
            }
          }
        }
      }
    }
  }

  public static PlaceReport zzf(String paramString1, String paramString2, String paramString3)
  {
    zzv.zzbS(paramString1);
    zzv.zzbS(paramString2);
    zzv.zzbS(paramString3);
    zzv.zzb(zzcI(paramString3), "Invalid source");
    return new PlaceReport(1, paramString1, paramString2, paramString3);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PlaceReport));
    PlaceReport localPlaceReport;
    do
    {
      return false;
      localPlaceReport = (PlaceReport)paramObject;
    }
    while ((!zzu.equal(this.zzarP, localPlaceReport.zzarP)) || (!zzu.equal(this.mTag, localPlaceReport.mTag)) || (!zzu.equal(this.zzarQ, localPlaceReport.zzarQ)));
    return true;
  }

  public String getPlaceId()
  {
    return this.zzarP;
  }

  public String getSource()
  {
    return this.zzarQ;
  }

  public String getTag()
  {
    return this.mTag;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.zzarP;
    arrayOfObject[1] = this.mTag;
    arrayOfObject[2] = this.zzarQ;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    zzu.zza localzza = zzu.zzq(this);
    localzza.zzg("placeId", this.zzarP);
    localzza.zzg("tag", this.mTag);
    if (!"unknown".equals(this.zzarQ))
      localzza.zzg("source", this.zzarQ);
    return localzza.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    PlaceReportCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceReport
 * JD-Core Version:    0.6.2
 */