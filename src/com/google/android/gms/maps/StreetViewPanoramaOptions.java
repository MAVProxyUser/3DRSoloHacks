package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class StreetViewPanoramaOptions
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private Boolean zzatM;
  private Boolean zzatS = Boolean.valueOf(true);
  private LatLng zzauA;
  private Integer zzauB;
  private Boolean zzauC = Boolean.valueOf(true);
  private Boolean zzauD = Boolean.valueOf(true);
  private Boolean zzauE = Boolean.valueOf(true);
  private StreetViewPanoramaCamera zzauy;
  private String zzauz;
  private final int zzzH;

  public StreetViewPanoramaOptions()
  {
    this.zzzH = 1;
  }

  StreetViewPanoramaOptions(int paramInt, StreetViewPanoramaCamera paramStreetViewPanoramaCamera, String paramString, LatLng paramLatLng, Integer paramInteger, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5)
  {
    this.zzzH = paramInt;
    this.zzauy = paramStreetViewPanoramaCamera;
    this.zzauA = paramLatLng;
    this.zzauB = paramInteger;
    this.zzauz = paramString;
    this.zzauC = zza.zza(paramByte1);
    this.zzatS = zza.zza(paramByte2);
    this.zzauD = zza.zza(paramByte3);
    this.zzauE = zza.zza(paramByte4);
    this.zzatM = zza.zza(paramByte5);
  }

  public int describeContents()
  {
    return 0;
  }

  public Boolean getPanningGesturesEnabled()
  {
    return this.zzauD;
  }

  public String getPanoramaId()
  {
    return this.zzauz;
  }

  public LatLng getPosition()
  {
    return this.zzauA;
  }

  public Integer getRadius()
  {
    return this.zzauB;
  }

  public Boolean getStreetNamesEnabled()
  {
    return this.zzauE;
  }

  public StreetViewPanoramaCamera getStreetViewPanoramaCamera()
  {
    return this.zzauy;
  }

  public Boolean getUseViewLifecycleInFragment()
  {
    return this.zzatM;
  }

  public Boolean getUserNavigationEnabled()
  {
    return this.zzauC;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public Boolean getZoomGesturesEnabled()
  {
    return this.zzatS;
  }

  public StreetViewPanoramaOptions panningGesturesEnabled(boolean paramBoolean)
  {
    this.zzauD = Boolean.valueOf(paramBoolean);
    return this;
  }

  public StreetViewPanoramaOptions panoramaCamera(StreetViewPanoramaCamera paramStreetViewPanoramaCamera)
  {
    this.zzauy = paramStreetViewPanoramaCamera;
    return this;
  }

  public StreetViewPanoramaOptions panoramaId(String paramString)
  {
    this.zzauz = paramString;
    return this;
  }

  public StreetViewPanoramaOptions position(LatLng paramLatLng)
  {
    this.zzauA = paramLatLng;
    return this;
  }

  public StreetViewPanoramaOptions position(LatLng paramLatLng, Integer paramInteger)
  {
    this.zzauA = paramLatLng;
    this.zzauB = paramInteger;
    return this;
  }

  public StreetViewPanoramaOptions streetNamesEnabled(boolean paramBoolean)
  {
    this.zzauE = Boolean.valueOf(paramBoolean);
    return this;
  }

  public StreetViewPanoramaOptions useViewLifecycleInFragment(boolean paramBoolean)
  {
    this.zzatM = Boolean.valueOf(paramBoolean);
    return this;
  }

  public StreetViewPanoramaOptions userNavigationEnabled(boolean paramBoolean)
  {
    this.zzauC = Boolean.valueOf(paramBoolean);
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }

  public StreetViewPanoramaOptions zoomGesturesEnabled(boolean paramBoolean)
  {
    this.zzatS = Boolean.valueOf(paramBoolean);
    return this;
  }

  byte zztH()
  {
    return zza.zzd(this.zzauC);
  }

  byte zztI()
  {
    return zza.zzd(this.zzauD);
  }

  byte zztJ()
  {
    return zza.zzd(this.zzauE);
  }

  byte zztt()
  {
    return zza.zzd(this.zzatM);
  }

  byte zztx()
  {
    return zza.zzd(this.zzatS);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.StreetViewPanoramaOptions
 * JD-Core Version:    0.6.2
 */