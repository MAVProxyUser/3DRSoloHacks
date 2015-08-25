package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutocompleteFilter
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  final boolean zzarA;
  final List<Integer> zzarB;
  private final Set<Integer> zzarC;
  final int zzzH;

  AutocompleteFilter(int paramInt, boolean paramBoolean, Collection<Integer> paramCollection)
  {
    this.zzzH = paramInt;
    this.zzarA = paramBoolean;
    if (paramCollection == null);
    for (Object localObject = Collections.emptyList(); ; localObject = new ArrayList(paramCollection))
    {
      this.zzarB = ((List)localObject);
      if (!this.zzarB.isEmpty())
        break;
      this.zzarC = Collections.emptySet();
      return;
    }
    this.zzarC = Collections.unmodifiableSet(new HashSet(this.zzarB));
  }

  public static AutocompleteFilter create(Collection<Integer> paramCollection)
  {
    return zza(true, paramCollection);
  }

  public static AutocompleteFilter zza(boolean paramBoolean, Collection<Integer> paramCollection)
  {
    return new AutocompleteFilter(0, paramBoolean, paramCollection);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    AutocompleteFilter localAutocompleteFilter;
    do
    {
      return true;
      if (!(paramObject instanceof AutocompleteFilter))
        return false;
      localAutocompleteFilter = (AutocompleteFilter)paramObject;
    }
    while ((this.zzarC.equals(localAutocompleteFilter.zzarC)) && (this.zzarA == localAutocompleteFilter.zzarA));
    return false;
  }

  public Set<Integer> getPlaceTypes()
  {
    return this.zzarC;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Boolean.valueOf(this.zzarA);
    arrayOfObject[1] = this.zzarC;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    zzu.zza localzza = zzu.zzq(this);
    if (!this.zzarA)
      localzza.zzg("restrictedToPlaces", Boolean.valueOf(this.zzarA));
    localzza.zzg("placeTypes", this.zzarC);
    return localzza.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }

  public boolean zzsQ()
  {
    return this.zzarA;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.AutocompleteFilter
 * JD-Core Version:    0.6.2
 */