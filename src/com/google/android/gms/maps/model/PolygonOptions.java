package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class PolygonOptions
  implements SafeParcelable
{
  public static final zzg CREATOR = new zzg();
  private final List<LatLng> zzavD;
  private final List<List<LatLng>> zzavE;
  private boolean zzavF = false;
  private float zzava = 10.0F;
  private int zzavb = -16777216;
  private int zzavc = 0;
  private float zzavd = 0.0F;
  private boolean zzave = true;
  private final int zzzH;

  public PolygonOptions()
  {
    this.zzzH = 1;
    this.zzavD = new ArrayList();
    this.zzavE = new ArrayList();
  }

  PolygonOptions(int paramInt1, List<LatLng> paramList, List paramList1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.zzzH = paramInt1;
    this.zzavD = paramList;
    this.zzavE = paramList1;
    this.zzava = paramFloat1;
    this.zzavb = paramInt2;
    this.zzavc = paramInt3;
    this.zzavd = paramFloat2;
    this.zzave = paramBoolean1;
    this.zzavF = paramBoolean2;
  }

  public PolygonOptions add(LatLng paramLatLng)
  {
    this.zzavD.add(paramLatLng);
    return this;
  }

  public PolygonOptions add(LatLng[] paramArrayOfLatLng)
  {
    this.zzavD.addAll(Arrays.asList(paramArrayOfLatLng));
    return this;
  }

  public PolygonOptions addAll(Iterable<LatLng> paramIterable)
  {
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      LatLng localLatLng = (LatLng)localIterator.next();
      this.zzavD.add(localLatLng);
    }
    return this;
  }

  public PolygonOptions addHole(Iterable<LatLng> paramIterable)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
      localArrayList.add((LatLng)localIterator.next());
    this.zzavE.add(localArrayList);
    return this;
  }

  public int describeContents()
  {
    return 0;
  }

  public PolygonOptions fillColor(int paramInt)
  {
    this.zzavc = paramInt;
    return this;
  }

  public PolygonOptions geodesic(boolean paramBoolean)
  {
    this.zzavF = paramBoolean;
    return this;
  }

  public int getFillColor()
  {
    return this.zzavc;
  }

  public List<List<LatLng>> getHoles()
  {
    return this.zzavE;
  }

  public List<LatLng> getPoints()
  {
    return this.zzavD;
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

  public boolean isGeodesic()
  {
    return this.zzavF;
  }

  public boolean isVisible()
  {
    return this.zzave;
  }

  public PolygonOptions strokeColor(int paramInt)
  {
    this.zzavb = paramInt;
    return this;
  }

  public PolygonOptions strokeWidth(float paramFloat)
  {
    this.zzava = paramFloat;
    return this;
  }

  public PolygonOptions visible(boolean paramBoolean)
  {
    this.zzave = paramBoolean;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza(this, paramParcel, paramInt);
  }

  public PolygonOptions zIndex(float paramFloat)
  {
    this.zzavd = paramFloat;
    return this;
  }

  List zztT()
  {
    return this.zzavE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolygonOptions
 * JD-Core Version:    0.6.2
 */