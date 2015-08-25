package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R.styleable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  private Boolean zzatL;
  private Boolean zzatM;
  private int zzatN = -1;
  private CameraPosition zzatO;
  private Boolean zzatP;
  private Boolean zzatQ;
  private Boolean zzatR;
  private Boolean zzatS;
  private Boolean zzatT;
  private Boolean zzatU;
  private Boolean zzatV;
  private Boolean zzatW;
  private final int zzzH;

  public GoogleMapOptions()
  {
    this.zzzH = 1;
  }

  GoogleMapOptions(int paramInt1, byte paramByte1, byte paramByte2, int paramInt2, CameraPosition paramCameraPosition, byte paramByte3, byte paramByte4, byte paramByte5, byte paramByte6, byte paramByte7, byte paramByte8, byte paramByte9, byte paramByte10)
  {
    this.zzzH = paramInt1;
    this.zzatL = com.google.android.gms.maps.internal.zza.zza(paramByte1);
    this.zzatM = com.google.android.gms.maps.internal.zza.zza(paramByte2);
    this.zzatN = paramInt2;
    this.zzatO = paramCameraPosition;
    this.zzatP = com.google.android.gms.maps.internal.zza.zza(paramByte3);
    this.zzatQ = com.google.android.gms.maps.internal.zza.zza(paramByte4);
    this.zzatR = com.google.android.gms.maps.internal.zza.zza(paramByte5);
    this.zzatS = com.google.android.gms.maps.internal.zza.zza(paramByte6);
    this.zzatT = com.google.android.gms.maps.internal.zza.zza(paramByte7);
    this.zzatU = com.google.android.gms.maps.internal.zza.zza(paramByte8);
    this.zzatV = com.google.android.gms.maps.internal.zza.zza(paramByte9);
    this.zzatW = com.google.android.gms.maps.internal.zza.zza(paramByte10);
  }

  public static GoogleMapOptions createFromAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet == null)
      return null;
    TypedArray localTypedArray = paramContext.getResources().obtainAttributes(paramAttributeSet, R.styleable.MapAttrs);
    GoogleMapOptions localGoogleMapOptions = new GoogleMapOptions();
    if (localTypedArray.hasValue(R.styleable.MapAttrs_mapType))
      localGoogleMapOptions.mapType(localTypedArray.getInt(R.styleable.MapAttrs_mapType, -1));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_zOrderOnTop))
      localGoogleMapOptions.zOrderOnTop(localTypedArray.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_useViewLifecycle))
      localGoogleMapOptions.useViewLifecycleInFragment(localTypedArray.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiCompass))
      localGoogleMapOptions.compassEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiCompass, true));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiRotateGestures))
      localGoogleMapOptions.rotateGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiScrollGestures))
      localGoogleMapOptions.scrollGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiTiltGestures))
      localGoogleMapOptions.tiltGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiZoomGestures))
      localGoogleMapOptions.zoomGesturesEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiZoomControls))
      localGoogleMapOptions.zoomControlsEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_liteMode))
      localGoogleMapOptions.liteMode(localTypedArray.getBoolean(R.styleable.MapAttrs_liteMode, false));
    if (localTypedArray.hasValue(R.styleable.MapAttrs_uiMapToolbar))
      localGoogleMapOptions.mapToolbarEnabled(localTypedArray.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
    localGoogleMapOptions.camera(CameraPosition.createFromAttributes(paramContext, paramAttributeSet));
    localTypedArray.recycle();
    return localGoogleMapOptions;
  }

  public GoogleMapOptions camera(CameraPosition paramCameraPosition)
  {
    this.zzatO = paramCameraPosition;
    return this;
  }

  public GoogleMapOptions compassEnabled(boolean paramBoolean)
  {
    this.zzatQ = Boolean.valueOf(paramBoolean);
    return this;
  }

  public int describeContents()
  {
    return 0;
  }

  public CameraPosition getCamera()
  {
    return this.zzatO;
  }

  public Boolean getCompassEnabled()
  {
    return this.zzatQ;
  }

  public Boolean getLiteMode()
  {
    return this.zzatV;
  }

  public Boolean getMapToolbarEnabled()
  {
    return this.zzatW;
  }

  public int getMapType()
  {
    return this.zzatN;
  }

  public Boolean getRotateGesturesEnabled()
  {
    return this.zzatU;
  }

  public Boolean getScrollGesturesEnabled()
  {
    return this.zzatR;
  }

  public Boolean getTiltGesturesEnabled()
  {
    return this.zzatT;
  }

  public Boolean getUseViewLifecycleInFragment()
  {
    return this.zzatM;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public Boolean getZOrderOnTop()
  {
    return this.zzatL;
  }

  public Boolean getZoomControlsEnabled()
  {
    return this.zzatP;
  }

  public Boolean getZoomGesturesEnabled()
  {
    return this.zzatS;
  }

  public GoogleMapOptions liteMode(boolean paramBoolean)
  {
    this.zzatV = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions mapToolbarEnabled(boolean paramBoolean)
  {
    this.zzatW = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions mapType(int paramInt)
  {
    this.zzatN = paramInt;
    return this;
  }

  public GoogleMapOptions rotateGesturesEnabled(boolean paramBoolean)
  {
    this.zzatU = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions scrollGesturesEnabled(boolean paramBoolean)
  {
    this.zzatR = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions tiltGesturesEnabled(boolean paramBoolean)
  {
    this.zzatT = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions useViewLifecycleInFragment(boolean paramBoolean)
  {
    this.zzatM = Boolean.valueOf(paramBoolean);
    return this;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }

  public GoogleMapOptions zOrderOnTop(boolean paramBoolean)
  {
    this.zzatL = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions zoomControlsEnabled(boolean paramBoolean)
  {
    this.zzatP = Boolean.valueOf(paramBoolean);
    return this;
  }

  public GoogleMapOptions zoomGesturesEnabled(boolean paramBoolean)
  {
    this.zzatS = Boolean.valueOf(paramBoolean);
    return this;
  }

  byte zztA()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatV);
  }

  byte zztB()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatW);
  }

  byte zzts()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatL);
  }

  byte zztt()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatM);
  }

  byte zztu()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatP);
  }

  byte zztv()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatQ);
  }

  byte zztw()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatR);
  }

  byte zztx()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatS);
  }

  byte zzty()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatT);
  }

  byte zztz()
  {
    return com.google.android.gms.maps.internal.zza.zzd(this.zzatU);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.GoogleMapOptions
 * JD-Core Version:    0.6.2
 */