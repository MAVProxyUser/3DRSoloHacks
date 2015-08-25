package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CircleOptions
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private LatLng zzauY = null;
  private double zzauZ = 0.0D;
  private float zzava = 10.0F;
  private int zzavb = -16777216;
  private int zzavc = 0;
  private float zzavd = 0.0F;
  private boolean zzave = true;
  private final int zzzH;

  public CircleOptions()
  {
    this.zzzH = 1;
  }

  CircleOptions(int paramInt1, LatLng paramLatLng, double paramDouble, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean)
  {
    this.zzzH = paramInt1;
    this.zzauY = paramLatLng;
    this.zzauZ = paramDouble;
    this.zzava = paramFloat1;
    this.zzavb = paramInt2;
    this.zzavc = paramInt3;
    this.zzavd = paramFloat2;
    this.zzave = paramBoolean;
  }

  public CircleOptions center(LatLng paramLatLng)
  {
    this.zzauY = paramLatLng;
    return this;
  }

  public int describeContents()
  {
    return 0;
  }

  public CircleOptions fillColor(int paramInt)
  {
    this.zzavc = paramInt;
    return this;
  }

  public LatLng getCenter()
  {
    return this.zzauY;
  }

  public int getFillColor()
  {
    return this.zzavc;
  }

  public double getRadius()
  {
    return this.zzauZ;
  }

  public int getStrokeColor()
  {
    return this.zzavb;
  }

  public float getStrokeWidth()
  {
    return this.zzava;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public float getZIndex()
  {
    return this.zzavd;
  }

  public boolean isVisible()
  {
    return this.zzave;
  }

  public CircleOptions radius(double paramDouble)
  {
    this.zzauZ = paramDouble;
    return this;
  }

  public CircleOptions strokeColor(int paramInt)
  {
    this.zzavb = paramInt;
    return this;
  }

  public CircleOptions strokeWidth(float paramFloat)
  {
    this.zzava = paramFloat;
    return this;
  }

  public CircleOptions visible(boolean paramBoolean)
  {
    this.zzave = paramBoolean;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }

  public CircleOptions zIndex(float paramFloat)
  {
    this.zzavd = paramFloat;
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.CircleOptions
 * JD-Core Version:    0.6.2
 */