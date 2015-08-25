package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Comparator;

public class DetectedActivity
  implements SafeParcelable
{
  public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
  public static final int IN_VEHICLE = 0;
  public static final int ON_BICYCLE = 1;
  public static final int ON_FOOT = 2;
  public static final int RUNNING = 8;
  public static final int STILL = 3;
  public static final int TILTING = 5;
  public static final int UNKNOWN = 4;
  public static final int WALKING = 7;
  public static final Comparator<DetectedActivity> zzapF = new Comparator()
  {
    public int zza(DetectedActivity paramAnonymousDetectedActivity1, DetectedActivity paramAnonymousDetectedActivity2)
    {
      int i = Integer.valueOf(paramAnonymousDetectedActivity2.getConfidence()).compareTo(Integer.valueOf(paramAnonymousDetectedActivity1.getConfidence()));
      if (i == 0)
        i = Integer.valueOf(paramAnonymousDetectedActivity1.getType()).compareTo(Integer.valueOf(paramAnonymousDetectedActivity2.getType()));
      return i;
    }
  };
  int zzapG;
  int zzapH;
  private final int zzzH;

  public DetectedActivity(int paramInt1, int paramInt2)
  {
    this.zzzH = 1;
    this.zzapG = paramInt1;
    this.zzapH = paramInt2;
  }

  public DetectedActivity(int paramInt1, int paramInt2, int paramInt3)
  {
    this.zzzH = paramInt1;
    this.zzapG = paramInt2;
    this.zzapH = paramInt3;
  }

  private int zzfv(int paramInt)
  {
    if (paramInt > 14)
      paramInt = 4;
    return paramInt;
  }

  public static String zzfw(int paramInt)
  {
    switch (paramInt)
    {
    case 6:
    default:
      return Integer.toString(paramInt);
    case 0:
      return "IN_VEHICLE";
    case 1:
      return "ON_BICYCLE";
    case 2:
      return "ON_FOOT";
    case 3:
      return "STILL";
    case 4:
      return "UNKNOWN";
    case 5:
      return "TILTING";
    case 7:
      return "WALKING";
    case 8:
    }
    return "RUNNING";
  }

  public int describeContents()
  {
    return 0;
  }

  public int getConfidence()
  {
    return this.zzapH;
  }

  public int getType()
  {
    return zzfv(this.zzapG);
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public String toString()
  {
    return "DetectedActivity [type=" + zzfw(getType()) + ", confidence=" + this.zzapH + "]";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    DetectedActivityCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.DetectedActivity
 * JD-Core Version:    0.6.2
 */