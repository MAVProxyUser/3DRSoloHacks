package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;

public class PlaceAlias
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public static final PlaceAlias zzatj = new PlaceAlias(0, "Home");
  public static final PlaceAlias zzatk = new PlaceAlias(0, "Work");
  private final String zzatl;
  final int zzzH;

  PlaceAlias(int paramInt, String paramString)
  {
    this.zzzH = paramInt;
    this.zzatl = paramString;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof PlaceAlias))
      return false;
    PlaceAlias localPlaceAlias = (PlaceAlias)paramObject;
    return zzu.equal(this.zzatl, localPlaceAlias.zzatl);
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.zzatl;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("alias", this.zzatl).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }

  public String zztj()
  {
    return this.zzatl;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.PlaceAlias
 * JD-Core Version:    0.6.2
 */