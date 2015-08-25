package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;

public final class PlacesOptions
  implements Api.ApiOptions.Optional
{
  public final String zzasc;

  private PlacesOptions(Builder paramBuilder)
  {
    this.zzasc = Builder.zza(paramBuilder);
  }

  public static class Builder
  {
    private String zzasd;

    public PlacesOptions build()
    {
      return new PlacesOptions(this, null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlacesOptions
 * JD-Core Version:    0.6.2
 */