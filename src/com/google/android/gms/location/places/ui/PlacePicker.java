package com.google.android.gms.location.places.ui;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceImpl;
import com.google.android.gms.location.places.internal.zzn;
import com.google.android.gms.maps.model.LatLngBounds;

public class PlacePicker
{
  public static final int RESULT_ERROR = 2;

  public static String getAttributions(Intent paramIntent)
  {
    return paramIntent.getStringExtra("third_party_attributions");
  }

  public static LatLngBounds getLatLngBounds(Intent paramIntent)
  {
    return (LatLngBounds)zzc.zza(paramIntent, "final_latlng_bounds", LatLngBounds.CREATOR);
  }

  public static Place getPlace(Intent paramIntent, Context paramContext)
  {
    zzv.zzb(paramContext, "context must not be null");
    PlaceImpl localPlaceImpl = (PlaceImpl)zzc.zza(paramIntent, "selected_place", PlaceImpl.CREATOR);
    localPlaceImpl.zza(zzn.zzag(paramContext));
    return localPlaceImpl;
  }

  public static class IntentBuilder
  {
    private final Intent mIntent = new Intent("com.google.android.gms.location.places.ui.PICK_PLACE");

    public IntentBuilder()
    {
      this.mIntent.setPackage("com.google.android.gms");
      this.mIntent.putExtra("gmscore_client_jar_version", 7327000);
    }

    public Intent build(Context paramContext)
      throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
    {
      GoogleApiAvailability.getInstance().zzN(paramContext);
      return this.mIntent;
    }

    public IntentBuilder setLatLngBounds(LatLngBounds paramLatLngBounds)
    {
      zzv.zzr(paramLatLngBounds);
      zzc.zza(paramLatLngBounds, this.mIntent, "latlng_bounds");
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.ui.PlacePicker
 * JD-Core Version:    0.6.2
 */