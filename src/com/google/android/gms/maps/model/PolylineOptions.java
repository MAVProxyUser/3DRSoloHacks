package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class PolylineOptions
  implements SafeParcelable
{
  public static final zzh CREATOR = new zzh();
  private int mColor = -16777216;
  private final List<LatLng> zzavD;
  private boolean zzavF = false;
  private float zzavd = 0.0F;
  private boolean zzave = true;
  private float zzavi = 10.0F;
  private final int zzzH;

  public PolylineOptions()
  {
    this.zzzH = 1;
    this.zzavD = new ArrayList();
  }

  PolylineOptions(int paramInt1, List paramList, float paramFloat1, int paramInt2, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.zzzH = paramInt1;
    this.zzavD = paramList;
    this.zzavi = paramFloat1;
    this.mColor = paramInt2;
    this.zzavd = paramFloat2;
    this.zzave = paramBoolean1;
    this.zzavF = paramBoolean2;
  }

  public PolylineOptions add(LatLng paramLatLng)
  {
    this.zzavD.add(paramLatLng);
    return this;
  }

  public PolylineOptions add(LatLng[] paramArrayOfLatLng)
  {
    this.zzavD.addAll(Arrays.asList(paramArrayOfLatLng));
    return this;
  }

  public PolylineOptions addAll(Iterable<LatLng> paramIterable)
  {
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      LatLng localLatLng = (LatLng)localIterator.next();
      this.zzavD.add(localLatLng);
    }
    return this;
  }

  public PolylineOptions color(int paramInt)
  {
    this.mColor = paramInt;
    return this;
  }

  public int describeContents()
  {
    return 0;
  }

  public PolylineOptions geodesic(boolean paramBoolean)
  {
    this.zzavF = paramBoolean;
    return this;
  }

  public int getColor()
  {
    return this.mColor;
  }

  public List<LatLng> getPoints()
  {
    return this.zzavD;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public float getWidth()
  {
    return this.zzavi;
  }

  public float getZIndex()
  {
    return this.zzavd;
  }

  public boolean isGeodesic()
  {
    return this.zzavF;
  }

  public boolean isVisible()
  {
    return this.zzave;
  }

  public PolylineOptions visible(boolean paramBoolean)
  {
    this.zzave = paramBoolean;
    return this;
  }

  public PolylineOptions width(float paramFloat)
  {
    this.zzavi = paramFloat;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza(this, paramParcel, paramInt);
  }

  public PolylineOptions zIndex(float paramFloat)
  {
    this.zzavd = paramFloat;
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolylineOptions
 * JD-Core Version:    0.6.2
 */