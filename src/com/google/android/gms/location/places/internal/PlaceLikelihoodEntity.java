package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;

public class PlaceLikelihoodEntity
  implements SafeParcelable, PlaceLikelihood
{
  public static final Parcelable.Creator<PlaceLikelihoodEntity> CREATOR = new zzk();
  final PlaceImpl zzasP;
  final float zzasQ;
  final int zzzH;

  PlaceLikelihoodEntity(int paramInt, PlaceImpl paramPlaceImpl, float paramFloat)
  {
    this.zzzH = paramInt;
    this.zzasP = paramPlaceImpl;
    this.zzasQ = paramFloat;
  }

  public static PlaceLikelihoodEntity zza(PlaceImpl paramPlaceImpl, float paramFloat)
  {
    return new PlaceLikelihoodEntity(0, (PlaceImpl)zzv.zzr(paramPlaceImpl), paramFloat);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlaceLikelihoodEntity localPlaceLikelihoodEntity;
    do
    {
      return true;
      if (!(paramObject instanceof PlaceLikelihoodEntity))
        return false;
      localPlaceLikelihoodEntity = (PlaceLikelihoodEntity)paramObject;
    }
    while ((this.zzasP.equals(localPlaceLikelihoodEntity.zzasP)) && (this.zzasQ == localPlaceLikelihoodEntity.zzasQ));
    return false;
  }

  public float getLikelihood()
  {
    return this.zzasQ;
  }

  public Place getPlace()
  {
    return this.zzasP;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.zzasP;
    arrayOfObject[1] = Float.valueOf(this.zzasQ);
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isDataValid()
  {
    return true;
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("place", this.zzasP).zzg("likelihood", Float.valueOf(this.zzasQ)).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }

  public PlaceLikelihood zzti()
  {
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.PlaceLikelihoodEntity
 * JD-Core Version:    0.6.2
 */