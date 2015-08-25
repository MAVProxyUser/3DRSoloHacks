package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zzd.zza;

public final class GroundOverlayOptions
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public static final float NO_DIMENSION = -1.0F;
  private float zzauW;
  private float zzavd;
  private boolean zzave = true;
  private BitmapDescriptor zzavg;
  private LatLng zzavh;
  private float zzavi;
  private float zzavj;
  private LatLngBounds zzavk;
  private float zzavl = 0.0F;
  private float zzavm = 0.5F;
  private float zzavn = 0.5F;
  private final int zzzH;

  public GroundOverlayOptions()
  {
    this.zzzH = 1;
  }

  GroundOverlayOptions(int paramInt, IBinder paramIBinder, LatLng paramLatLng, float paramFloat1, float paramFloat2, LatLngBounds paramLatLngBounds, float paramFloat3, float paramFloat4, boolean paramBoolean, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    this.zzzH = paramInt;
    this.zzavg = new BitmapDescriptor(zzd.zza.zzat(paramIBinder));
    this.zzavh = paramLatLng;
    this.zzavi = paramFloat1;
    this.zzavj = paramFloat2;
    this.zzavk = paramLatLngBounds;
    this.zzauW = paramFloat3;
    this.zzavd = paramFloat4;
    this.zzave = paramBoolean;
    this.zzavl = paramFloat5;
    this.zzavm = paramFloat6;
    this.zzavn = paramFloat7;
  }

  private GroundOverlayOptions zza(LatLng paramLatLng, float paramFloat1, float paramFloat2)
  {
    this.zzavh = paramLatLng;
    this.zzavi = paramFloat1;
    this.zzavj = paramFloat2;
    return this;
  }

  public GroundOverlayOptions anchor(float paramFloat1, float paramFloat2)
  {
    this.zzavm = paramFloat1;
    this.zzavn = paramFloat2;
    return this;
  }

  public GroundOverlayOptions bearing(float paramFloat)
  {
    this.zzauW = ((360.0F + paramFloat % 360.0F) % 360.0F);
    return this;
  }

  public int describeContents()
  {
    return 0;
  }

  public float getAnchorU()
  {
    return this.zzavm;
  }

  public float getAnchorV()
  {
    return this.zzavn;
  }

  public float getBearing()
  {
    return this.zzauW;
  }

  public LatLngBounds getBounds()
  {
    return this.zzavk;
  }

  public float getHeight()
  {
    return this.zzavj;
  }

  public BitmapDescriptor getImage()
  {
    return this.zzavg;
  }

  public LatLng getLocation()
  {
    return this.zzavh;
  }

  public float getTransparency()
  {
    return this.zzavl;
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

  public GroundOverlayOptions image(BitmapDescriptor paramBitmapDescriptor)
  {
    this.zzavg = paramBitmapDescriptor;
    return this;
  }

  public boolean isVisible()
  {
    return this.zzave;
  }

  public GroundOverlayOptions position(LatLng paramLatLng, float paramFloat)
  {
    boolean bool1 = true;
    boolean bool2;
    boolean bool3;
    if (this.zzavk == null)
    {
      bool2 = bool1;
      zzv.zza(bool2, "Position has already been set using positionFromBounds");
      if (paramLatLng == null)
        break label60;
      bool3 = bool1;
      label26: zzv.zzb(bool3, "Location must be specified");
      if (paramFloat < 0.0F)
        break label66;
    }
    while (true)
    {
      zzv.zzb(bool1, "Width must be non-negative");
      return zza(paramLatLng, paramFloat, -1.0F);
      bool2 = false;
      break;
      label60: bool3 = false;
      break label26;
      label66: bool1 = false;
    }
  }

  public GroundOverlayOptions position(LatLng paramLatLng, float paramFloat1, float paramFloat2)
  {
    boolean bool1 = true;
    boolean bool2;
    boolean bool3;
    label29: boolean bool4;
    if (this.zzavk == null)
    {
      bool2 = bool1;
      zzv.zza(bool2, "Position has already been set using positionFromBounds");
      if (paramLatLng == null)
        break label80;
      bool3 = bool1;
      zzv.zzb(bool3, "Location must be specified");
      if (paramFloat1 < 0.0F)
        break label86;
      bool4 = bool1;
      label46: zzv.zzb(bool4, "Width must be non-negative");
      if (paramFloat2 < 0.0F)
        break label92;
    }
    while (true)
    {
      zzv.zzb(bool1, "Height must be non-negative");
      return zza(paramLatLng, paramFloat1, paramFloat2);
      bool2 = false;
      break;
      label80: bool3 = false;
      break label29;
      label86: bool4 = false;
      break label46;
      label92: bool1 = false;
    }
  }

  public GroundOverlayOptions positionFromBounds(LatLngBounds paramLatLngBounds)
  {
    if (this.zzavh == null);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "Position has already been set using position: " + this.zzavh);
      this.zzavk = paramLatLngBounds;
      return this;
    }
  }

  public GroundOverlayOptions transparency(float paramFloat)
  {
    if ((paramFloat >= 0.0F) && (paramFloat <= 1.0F));
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzb(bool, "Transparency must be in the range [0..1]");
      this.zzavl = paramFloat;
      return this;
    }
  }

  public GroundOverlayOptions visible(boolean paramBoolean)
  {
    this.zzave = paramBoolean;
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }

  public GroundOverlayOptions zIndex(float paramFloat)
  {
    this.zzavd = paramFloat;
    return this;
  }

  IBinder zztR()
  {
    return this.zzavg.zztp().asBinder();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.GroundOverlayOptions
 * JD-Core Version:    0.6.2
 */