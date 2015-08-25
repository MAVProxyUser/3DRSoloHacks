package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.common.internal.zzv;

public final class LatLngBounds
  implements SafeParcelable
{
  public static final zzd CREATOR = new zzd();
  public final LatLng northeast;
  public final LatLng southwest;
  private final int zzzH;

  LatLngBounds(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2)
  {
    zzv.zzb(paramLatLng1, "null southwest");
    zzv.zzb(paramLatLng2, "null northeast");
    if (paramLatLng2.latitude >= paramLatLng1.latitude);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Double.valueOf(paramLatLng1.latitude);
      arrayOfObject[1] = Double.valueOf(paramLatLng2.latitude);
      zzv.zzb(bool, "southern latitude exceeds northern latitude (%s > %s)", arrayOfObject);
      this.zzzH = paramInt;
      this.southwest = paramLatLng1;
      this.northeast = paramLatLng2;
      return;
    }
  }

  public LatLngBounds(LatLng paramLatLng1, LatLng paramLatLng2)
  {
    this(1, paramLatLng1, paramLatLng2);
  }

  public static Builder builder()
  {
    return new Builder();
  }

  private static double zzb(double paramDouble1, double paramDouble2)
  {
    return (360.0D + (paramDouble1 - paramDouble2)) % 360.0D;
  }

  private static double zzc(double paramDouble1, double paramDouble2)
  {
    return (360.0D + (paramDouble2 - paramDouble1)) % 360.0D;
  }

  private boolean zzd(double paramDouble)
  {
    return (this.southwest.latitude <= paramDouble) && (paramDouble <= this.northeast.latitude);
  }

  private boolean zze(double paramDouble)
  {
    if (this.southwest.longitude <= this.northeast.longitude)
      return (this.southwest.longitude <= paramDouble) && (paramDouble <= this.northeast.longitude);
    boolean bool1;
    if (this.southwest.longitude > paramDouble)
    {
      boolean bool2 = paramDouble < this.northeast.longitude;
      bool1 = false;
      if (bool2);
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  public boolean contains(LatLng paramLatLng)
  {
    return (zzd(paramLatLng.latitude)) && (zze(paramLatLng.longitude));
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    LatLngBounds localLatLngBounds;
    do
    {
      return true;
      if (!(paramObject instanceof LatLngBounds))
        return false;
      localLatLngBounds = (LatLngBounds)paramObject;
    }
    while ((this.southwest.equals(localLatLngBounds.southwest)) && (this.northeast.equals(localLatLngBounds.northeast)));
    return false;
  }

  public LatLng getCenter()
  {
    double d1 = (this.southwest.latitude + this.northeast.latitude) / 2.0D;
    double d2 = this.northeast.longitude;
    double d3 = this.southwest.longitude;
    if (d3 <= d2);
    for (double d4 = (d2 + d3) / 2.0D; ; d4 = (d3 + (d2 + 360.0D)) / 2.0D)
      return new LatLng(d1, d4);
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.southwest;
    arrayOfObject[1] = this.northeast;
    return zzu.hashCode(arrayOfObject);
  }

  public LatLngBounds including(LatLng paramLatLng)
  {
    double d1 = Math.min(this.southwest.latitude, paramLatLng.latitude);
    double d2 = Math.max(this.northeast.latitude, paramLatLng.latitude);
    double d3 = this.northeast.longitude;
    double d4 = this.southwest.longitude;
    double d5 = paramLatLng.longitude;
    double d6;
    if (!zze(d5))
      if (zzb(d4, d5) < zzc(d3, d5))
        d6 = d3;
    while (true)
    {
      return new LatLngBounds(new LatLng(d1, d5), new LatLng(d2, d6));
      d6 = d5;
      d5 = d4;
      continue;
      d5 = d4;
      d6 = d3;
    }
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("southwest", this.southwest).zzg("northeast", this.northeast).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzd.zza(this, paramParcel, paramInt);
  }

  public static final class Builder
  {
    private double zzavq = (1.0D / 0.0D);
    private double zzavr = (-1.0D / 0.0D);
    private double zzavs = (0.0D / 0.0D);
    private double zzavt = (0.0D / 0.0D);

    private boolean zze(double paramDouble)
    {
      if (this.zzavs <= this.zzavt)
        return (this.zzavs <= paramDouble) && (paramDouble <= this.zzavt);
      boolean bool1;
      if (this.zzavs > paramDouble)
      {
        boolean bool2 = paramDouble < this.zzavt;
        bool1 = false;
        if (bool2);
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }

    public LatLngBounds build()
    {
      if (!Double.isNaN(this.zzavs));
      for (boolean bool = true; ; bool = false)
      {
        zzv.zza(bool, "no included points");
        return new LatLngBounds(new LatLng(this.zzavq, this.zzavs), new LatLng(this.zzavr, this.zzavt));
      }
    }

    public Builder include(LatLng paramLatLng)
    {
      this.zzavq = Math.min(this.zzavq, paramLatLng.latitude);
      this.zzavr = Math.max(this.zzavr, paramLatLng.latitude);
      double d = paramLatLng.longitude;
      if (Double.isNaN(this.zzavs))
      {
        this.zzavs = d;
        this.zzavt = d;
      }
      while (zze(d))
        return this;
      if (LatLngBounds.zzd(this.zzavs, d) < LatLngBounds.zze(this.zzavt, d))
      {
        this.zzavs = d;
        return this;
      }
      this.zzavt = d;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.LatLngBounds
 * JD-Core Version:    0.6.2
 */