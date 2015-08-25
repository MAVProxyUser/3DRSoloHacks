package com.google.android.gms.maps.internal;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.maps.OnMapReadyCallback;

public abstract interface MapLifecycleDelegate extends LifecycleDelegate
{
  public abstract void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.MapLifecycleDelegate
 * JD-Core Version:    0.6.2
 */