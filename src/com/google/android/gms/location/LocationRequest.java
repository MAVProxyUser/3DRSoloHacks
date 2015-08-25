package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

public final class LocationRequest
  implements SafeParcelable
{
  public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
  public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
  public static final int PRIORITY_HIGH_ACCURACY = 100;
  public static final int PRIORITY_LOW_POWER = 104;
  public static final int PRIORITY_NO_POWER = 105;
  int mPriority;
  boolean zzafv;
  long zzapJ;
  long zzaqe;
  long zzaqf;
  int zzaqg;
  float zzaqh;
  long zzaqi;
  private final int zzzH;

  public LocationRequest()
  {
    this.zzzH = 1;
    this.mPriority = 102;
    this.zzaqe = 3600000L;
    this.zzaqf = 600000L;
    this.zzafv = false;
    this.zzapJ = 9223372036854775807L;
    this.zzaqg = 2147483647;
    this.zzaqh = 0.0F;
    this.zzaqi = 0L;
  }

  LocationRequest(int paramInt1, int paramInt2, long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3, int paramInt3, float paramFloat, long paramLong4)
  {
    this.zzzH = paramInt1;
    this.mPriority = paramInt2;
    this.zzaqe = paramLong1;
    this.zzaqf = paramLong2;
    this.zzafv = paramBoolean;
    this.zzapJ = paramLong3;
    this.zzaqg = paramInt3;
    this.zzaqh = paramFloat;
    this.zzaqi = paramLong4;
  }

  public static LocationRequest create()
  {
    return new LocationRequest();
  }

  private static void zzJ(long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("invalid interval: " + paramLong);
  }

  private static void zzd(float paramFloat)
  {
    if (paramFloat < 0.0F)
      throw new IllegalArgumentException("invalid displacement: " + paramFloat);
  }

  private static void zzfA(int paramInt)
  {
    switch (paramInt)
    {
    case 101:
    case 103:
    default:
      throw new IllegalArgumentException("invalid quality: " + paramInt);
    case 100:
    case 102:
    case 104:
    case 105:
    }
  }

  public static String zzfB(int paramInt)
  {
    switch (paramInt)
    {
    case 101:
    case 103:
    default:
      return "???";
    case 100:
      return "PRIORITY_HIGH_ACCURACY";
    case 102:
      return "PRIORITY_BALANCED_POWER_ACCURACY";
    case 104:
      return "PRIORITY_LOW_POWER";
    case 105:
    }
    return "PRIORITY_NO_POWER";
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    LocationRequest localLocationRequest;
    do
    {
      return true;
      if (!(paramObject instanceof LocationRequest))
        return false;
      localLocationRequest = (LocationRequest)paramObject;
    }
    while ((this.mPriority == localLocationRequest.mPriority) && (this.zzaqe == localLocationRequest.zzaqe) && (this.zzaqf == localLocationRequest.zzaqf) && (this.zzafv == localLocationRequest.zzafv) && (this.zzapJ == localLocationRequest.zzapJ) && (this.zzaqg == localLocationRequest.zzaqg) && (this.zzaqh == localLocationRequest.zzaqh));
    return false;
  }

  public long getExpirationTime()
  {
    return this.zzapJ;
  }

  public long getFastestInterval()
  {
    return this.zzaqf;
  }

  public long getInterval()
  {
    return this.zzaqe;
  }

  public long getMaxWaitTime()
  {
    long l = this.zzaqi;
    if (l < this.zzaqe)
      l = this.zzaqe;
    return l;
  }

  public int getNumUpdates()
  {
    return this.zzaqg;
  }

  public int getPriority()
  {
    return this.mPriority;
  }

  public float getSmallestDisplacement()
  {
    return this.zzaqh;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = Integer.valueOf(this.mPriority);
    arrayOfObject[1] = Long.valueOf(this.zzaqe);
    arrayOfObject[2] = Long.valueOf(this.zzaqf);
    arrayOfObject[3] = Boolean.valueOf(this.zzafv);
    arrayOfObject[4] = Long.valueOf(this.zzapJ);
    arrayOfObject[5] = Integer.valueOf(this.zzaqg);
    arrayOfObject[6] = Float.valueOf(this.zzaqh);
    return zzu.hashCode(arrayOfObject);
  }

  public LocationRequest setExpirationDuration(long paramLong)
  {
    long l = SystemClock.elapsedRealtime();
    if (paramLong > 9223372036854775807L - l);
    for (this.zzapJ = 9223372036854775807L; ; this.zzapJ = (l + paramLong))
    {
      if (this.zzapJ < 0L)
        this.zzapJ = 0L;
      return this;
    }
  }

  public LocationRequest setExpirationTime(long paramLong)
  {
    this.zzapJ = paramLong;
    if (this.zzapJ < 0L)
      this.zzapJ = 0L;
    return this;
  }

  public LocationRequest setFastestInterval(long paramLong)
  {
    zzJ(paramLong);
    this.zzafv = true;
    this.zzaqf = paramLong;
    return this;
  }

  public LocationRequest setInterval(long paramLong)
  {
    zzJ(paramLong);
    this.zzaqe = paramLong;
    if (!this.zzafv)
      this.zzaqf = (()(this.zzaqe / 6.0D));
    return this;
  }

  public LocationRequest setMaxWaitTime(long paramLong)
  {
    zzJ(paramLong);
    this.zzaqi = paramLong;
    return this;
  }

  public LocationRequest setNumUpdates(int paramInt)
  {
    if (paramInt <= 0)
      throw new IllegalArgumentException("invalid numUpdates: " + paramInt);
    this.zzaqg = paramInt;
    return this;
  }

  public LocationRequest setPriority(int paramInt)
  {
    zzfA(paramInt);
    this.mPriority = paramInt;
    return this;
  }

  public LocationRequest setSmallestDisplacement(float paramFloat)
  {
    zzd(paramFloat);
    this.zzaqh = paramFloat;
    return this;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Request[").append(zzfB(this.mPriority));
    if (this.mPriority != 105)
    {
      localStringBuilder.append(" requested=");
      localStringBuilder.append(this.zzaqe + "ms");
    }
    localStringBuilder.append(" fastest=");
    localStringBuilder.append(this.zzaqf + "ms");
    if (this.zzaqi > this.zzaqe)
    {
      localStringBuilder.append(" maxWait=");
      localStringBuilder.append(this.zzaqi + "ms");
    }
    if (this.zzapJ != 9223372036854775807L)
    {
      long l = this.zzapJ - SystemClock.elapsedRealtime();
      localStringBuilder.append(" expireIn=");
      localStringBuilder.append(l + "ms");
    }
    if (this.zzaqg != 2147483647)
      localStringBuilder.append(" num=").append(this.zzaqg);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationRequestCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationRequest
 * JD-Core Version:    0.6.2
 */