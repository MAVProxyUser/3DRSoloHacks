package com.google.android.gms.location;

import com.google.android.gms.common.api.CommonStatusCodes;

public final class GeofenceStatusCodes extends CommonStatusCodes
{
  public static final int GEOFENCE_NOT_AVAILABLE = 1000;
  public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
  public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;

  public static String getStatusCodeString(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return CommonStatusCodes.getStatusCodeString(paramInt);
    case 1000:
      return "GEOFENCE_NOT_AVAILABLE";
    case 1001:
      return "GEOFENCE_TOO_MANY_GEOFENCES";
    case 1002:
    }
    return "GEOFENCE_TOO_MANY_PENDING_INTENTS";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.GeofenceStatusCodes
 * JD-Core Version:    0.6.2
 */