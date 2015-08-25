package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public final class MarkerOptions
  implements SafeParcelable
{
  public static final zzf CREATOR = new zzf();
  private float mAlpha = 1.0F;
  private String zzWn;
  private LatLng zzauA;
  private float zzavA = 0.5F;
  private float zzavB = 0.0F;
  private boolean zzave = true;
  private float zzavm = 0.5F;
  private float zzavn = 1.0F;
  private String zzavv;
  private BitmapDescriptor zzavw;
  private boolean zzavx;
  private boolean zzavy = false;
  private float zzavz = 0.0F;
  private final int zzzH;

  public MarkerOptions()
  {
    this.zzzH = 1;
  }

  MarkerOptions(int paramInt, LatLng paramLatLng, String paramString1, String paramString2, IBinder paramIBinder, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.zzzH = paramInt;
    this.zzauA = paramLatLng;
    this.zzWn = paramString1;
    this.zzavv = paramString2;
    if (paramIBinder == null);
    for (BitmapDescriptor localBitmapDescriptor = null; ; localBitmapDescriptor = new BitmapDescriptor(zzd.zza.zzat(paramIBinder)))
    {
      this.zzavw = localBitmapDescriptor;
      this.zzavm = paramFloat1;
      this.zzavn = paramFloat2;
      this.zzavx = paramBoolean1;
      this.zzave = paramBoolean2;
      this.zzavy = paramBoolean3;
      this.zzavz = paramFloat3;
      this.zzavA = paramFloat4;
      this.zzavB = paramFloat5;
      this.mAlpha = paramFloat6;
      return;
    }
  }

  public MarkerOptions alpha(float paramFloat)
  {
    this.mAlpha = paramFloat;
    return this;
  }

  public MarkerOptions anchor(float paramFloat1, float paramFloat2)
  {
    this.zzavm = paramFloat1;
    this.zzavn = paramFloat2;
    return this;
  }

  public int describeContents()
  {
    return 0;
  }

  public MarkerOptions draggable(boolean paramBoolean)
  {
    this.zzavx = paramBoolean;
    return this;
  }

  public MarkerOptions flat(boolean paramBoolean)
  {
    this.zzavy = paramBoolean;
    return this;
  }

  public float getAlpha()
  {
    return this.mAlpha;
  }

  public float getAnchorU()
  {
    return this.zzavm;
  }

  public float getAnchorV()
  {
    return this.zzavn;
  }

  public BitmapDescriptor getIcon()
  {
    return this.zzavw;
  }

  public float getInfoWindowAnchorU()
  {
    return this.zzavA;
  }

  public float getInfoWindowAnchorV()
  {
    return this.zzavB;
  }

  public LatLng getPosition()
  {
    return this.zzauA;
  }

  public float getRotation()
  {
    return this.zzavz;
  }

  public String getSnippet()
  {
    return this.zzavv;
  }

  public String getTitle()
  {
    return this.zzWn;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public MarkerOptions icon(BitmapDescriptor paramBitmapDescriptor)
  {
    this.zzavw = paramBitmapDescriptor;
    return this;
  }

  public MarkerOptions infoWindowAnchor(float paramFloat1, float paramFloat2)
  {
    this.zzavA = paramFloat1;
    this.zzavB = paramFloat2;
    return this;
  }

  public boolean isDraggable()
  {
    return this.zzavx;
  }

  public boolean isFlat()
  {
    return this.zzavy;
  }

  public boolean isVisible()
  {
    return this.zzave;
  }

  public MarkerOptions position(LatLng paramLatLng)
  {
    this.zzauA = paramLatLng;
    return this;
  }

  public MarkerOptions rotation(float paramFloat)
  {
    this.zzavz = paramFloat;
    return this;
  }

  public MarkerOptions snippet(String paramString)
  {
    this.zzavv = paramString;
    return this;
  }

  public MarkerOptions title(String paramString)
  {
    this.zzWn = paramString;
    return this;
  }

  public MarkerOptions visible(boolean paramBoolean)
  {
    this.zzave = paramBoolean;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzf.zza(this, paramParcel, paramInt);
  }

  IBinder zztS()
  {
    if (this.zzavw == null)
      return null;
    return this.zzavw.zztp().asBinder();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.MarkerOptions
 * JD-Core Version:    0.6.2
 */