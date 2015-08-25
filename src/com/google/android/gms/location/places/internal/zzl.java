package com.google.android.gms.location.places.internal;

import android.content.Context;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;

public class zzl extends zzq
  implements PlaceLikelihood
{
  private final Context mContext;

  public zzl(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder, paramInt);
    this.mContext = paramContext;
  }

  public float getLikelihood()
  {
    return zzb("place_likelihood", -1.0F);
  }

  public Place getPlace()
  {
    return new zzo(this.zzPy, this.zzRw, this.mContext);
  }

  public PlaceLikelihood zzti()
  {
    return PlaceLikelihoodEntity.zza((PlaceImpl)getPlace().freeze(), getLikelihood());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzl
 * JD-Core Version:    0.6.2
 */