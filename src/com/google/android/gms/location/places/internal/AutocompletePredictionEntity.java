package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePrediction.Substring;
import java.util.List;

public class AutocompletePredictionEntity
  implements SafeParcelable, AutocompletePrediction
{
  public static final Parcelable.Creator<AutocompletePredictionEntity> CREATOR = new zza();
  final String zzadH;
  final String zzarP;
  final List<Integer> zzarx;
  final List<SubstringEntity> zzasj;
  final int zzask;
  final int zzzH;

  AutocompletePredictionEntity(int paramInt1, String paramString1, String paramString2, List<Integer> paramList, List<SubstringEntity> paramList1, int paramInt2)
  {
    this.zzzH = paramInt1;
    this.zzadH = paramString1;
    this.zzarP = paramString2;
    this.zzarx = paramList;
    this.zzasj = paramList1;
    this.zzask = paramInt2;
  }

  public static AutocompletePredictionEntity zza(String paramString1, String paramString2, List<Integer> paramList, List<SubstringEntity> paramList1, int paramInt)
  {
    return new AutocompletePredictionEntity(0, (String)zzv.zzr(paramString1), paramString2, paramList, paramList1, paramInt);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    AutocompletePredictionEntity localAutocompletePredictionEntity;
    do
    {
      return true;
      if (!(paramObject instanceof AutocompletePredictionEntity))
        return false;
      localAutocompletePredictionEntity = (AutocompletePredictionEntity)paramObject;
    }
    while ((zzu.equal(this.zzadH, localAutocompletePredictionEntity.zzadH)) && (zzu.equal(this.zzarP, localAutocompletePredictionEntity.zzarP)) && (zzu.equal(this.zzarx, localAutocompletePredictionEntity.zzarx)) && (zzu.equal(this.zzasj, localAutocompletePredictionEntity.zzasj)) && (zzu.equal(Integer.valueOf(this.zzask), Integer.valueOf(localAutocompletePredictionEntity.zzask))));
    return false;
  }

  public String getDescription()
  {
    return this.zzadH;
  }

  public List<? extends AutocompletePrediction.Substring> getMatchedSubstrings()
  {
    return this.zzasj;
  }

  public String getPlaceId()
  {
    return this.zzarP;
  }

  public List<Integer> getPlaceTypes()
  {
    return this.zzarx;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = this.zzadH;
    arrayOfObject[1] = this.zzarP;
    arrayOfObject[2] = this.zzarx;
    arrayOfObject[3] = this.zzasj;
    arrayOfObject[4] = Integer.valueOf(this.zzask);
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isDataValid()
  {
    return true;
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("description", this.zzadH).zzg("placeId", this.zzarP).zzg("placeTypes", this.zzarx).zzg("substrings", this.zzasj).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }

  public AutocompletePrediction zzsW()
  {
    return this;
  }

  public static class SubstringEntity
    implements SafeParcelable, AutocompletePrediction.Substring
  {
    public static final Parcelable.Creator<SubstringEntity> CREATOR = new zzr();
    final int mLength;
    final int mOffset;
    final int zzzH;

    public SubstringEntity(int paramInt1, int paramInt2, int paramInt3)
    {
      this.zzzH = paramInt1;
      this.mOffset = paramInt2;
      this.mLength = paramInt3;
    }

    public int describeContents()
    {
      return 0;
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      SubstringEntity localSubstringEntity;
      do
      {
        return true;
        if (!(paramObject instanceof SubstringEntity))
          return false;
        localSubstringEntity = (SubstringEntity)paramObject;
      }
      while ((zzu.equal(Integer.valueOf(this.mOffset), Integer.valueOf(localSubstringEntity.mOffset))) && (zzu.equal(Integer.valueOf(this.mLength), Integer.valueOf(localSubstringEntity.mLength))));
      return false;
    }

    public int getLength()
    {
      return this.mLength;
    }

    public int getOffset()
    {
      return this.mOffset;
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.mOffset);
      arrayOfObject[1] = Integer.valueOf(this.mLength);
      return zzu.hashCode(arrayOfObject);
    }

    public String toString()
    {
      return zzu.zzq(this).zzg("offset", Integer.valueOf(this.mOffset)).zzg("length", Integer.valueOf(this.mLength)).toString();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzr.zza(this, paramParcel, paramInt);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.AutocompletePredictionEntity
 * JD-Core Version:    0.6.2
 */