package com.google.android.gms.location.places;

import com.google.android.gms.common.data.Freezable;
import java.util.List;

public abstract interface AutocompletePrediction extends Freezable<AutocompletePrediction>
{
  public abstract String getDescription();

  public abstract List<? extends Substring> getMatchedSubstrings();

  public abstract String getPlaceId();

  public abstract List<Integer> getPlaceTypes();

  public static abstract interface Substring
  {
    public abstract int getLength();

    public abstract int getOffset();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.AutocompletePrediction
 * JD-Core Version:    0.6.2
 */