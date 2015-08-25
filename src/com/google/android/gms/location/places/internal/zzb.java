package com.google.android.gms.location.places.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.AutocompletePrediction;
import java.util.Collections;
import java.util.List;

public class zzb extends zzq
  implements AutocompletePrediction
{
  public zzb(DataHolder paramDataHolder, int paramInt)
  {
    super(paramDataHolder, paramInt);
  }

  public String getDescription()
  {
    return zzA("ap_description", "");
  }

  public List<AutocompletePredictionEntity.SubstringEntity> getMatchedSubstrings()
  {
    return zza("ap_matched_subscriptions", AutocompletePredictionEntity.SubstringEntity.CREATOR, Collections.emptyList());
  }

  public String getPlaceId()
  {
    return zzA("ap_place_id", null);
  }

  public List<Integer> getPlaceTypes()
  {
    return zza("ap_place_types", Collections.emptyList());
  }

  public AutocompletePrediction zzsW()
  {
    return AutocompletePredictionEntity.zza(getDescription(), getPlaceId(), getPlaceTypes(), getMatchedSubstrings(), zzsX());
  }

  public int zzsX()
  {
    return zzy("ap_personalization_type", 6);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzb
 * JD-Core Version:    0.6.2
 */