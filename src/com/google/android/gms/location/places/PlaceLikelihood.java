package com.google.android.gms.location.places;

import com.google.android.gms.common.data.Freezable;

public abstract interface PlaceLikelihood extends Freezable<PlaceLikelihood>
{
  public abstract float getLikelihood();

  public abstract Place getPlace();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceLikelihood
 * JD-Core Version:    0.6.2
 */