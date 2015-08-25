package com.o3dr.solo.android.util.geo;

import com.google.android.gms.maps.model.LatLng;
import com.o3dr.services.android.lib.coordinate.LatLong;

public class GeoTools
{
  public static LatLong LatLngToLatLong(LatLng paramLatLng)
  {
    return new LatLong(paramLatLng.latitude, paramLatLng.longitude);
  }

  public static LatLng LatLongToLatLng(LatLong paramLatLong)
  {
    return new LatLng(paramLatLong.getLatitude(), paramLatLong.getLongitude());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.geo.GeoTools
 * JD-Core Version:    0.6.2
 */