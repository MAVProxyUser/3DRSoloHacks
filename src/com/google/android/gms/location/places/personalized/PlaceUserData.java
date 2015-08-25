package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.List;

public class PlaceUserData
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  private final String zzKw;
  private final String zzarP;
  private final List<TestDataImpl> zzatm;
  private final List<PlaceAlias> zzatn;
  private final List<HereContent> zzato;
  final int zzzH;

  PlaceUserData(int paramInt, String paramString1, String paramString2, List<TestDataImpl> paramList, List<PlaceAlias> paramList1, List<HereContent> paramList2)
  {
    this.zzzH = paramInt;
    this.zzKw = paramString1;
    this.zzarP = paramString2;
    this.zzatm = paramList;
    this.zzatn = paramList1;
    this.zzato = paramList2;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlaceUserData localPlaceUserData;
    do
    {
      return true;
      if (!(paramObject instanceof PlaceUserData))
        return false;
      localPlaceUserData = (PlaceUserData)paramObject;
    }
    while ((this.zzKw.equals(localPlaceUserData.zzKw)) && (this.zzarP.equals(localPlaceUserData.zzarP)) && (this.zzatm.equals(localPlaceUserData.zzatm)) && (this.zzatn.equals(localPlaceUserData.zzatn)) && (this.zzato.equals(localPlaceUserData.zzato)));
    return false;
  }

  public String getPlaceId()
  {
    return this.zzarP;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = this.zzKw;
    arrayOfObject[1] = this.zzarP;
    arrayOfObject[2] = this.zzatm;
    arrayOfObject[3] = this.zzatn;
    arrayOfObject[4] = this.zzato;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("accountName", this.zzKw).zzg("placeId", this.zzarP).zzg("testDataImpls", this.zzatm).zzg("placeAliases", this.zzatn).zzg("hereContents", this.zzato).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze.zza(this, paramParcel, paramInt);
  }

  public String zztk()
  {
    return this.zzKw;
  }

  public List<PlaceAlias> zztl()
  {
    return this.zzatn;
  }

  public List<HereContent> zztm()
  {
    return this.zzato;
  }

  public List<TestDataImpl> zztn()
  {
    return this.zzatm;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.PlaceUserData
 * JD-Core Version:    0.6.2
 */