package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class PlaceFilter
  implements SafeParcelable
{
  public static final zzd CREATOR = new zzd();
  final List<Integer> zzarB;
  private final Set<Integer> zzarC;
  final boolean zzarG;
  final List<UserDataType> zzarH;
  final List<String> zzarI;
  private final Set<UserDataType> zzarJ;
  private final Set<String> zzarK;
  final int zzzH;

  public PlaceFilter()
  {
    this(false, null);
  }

  PlaceFilter(int paramInt, List<Integer> paramList, boolean paramBoolean, List<String> paramList1, List<UserDataType> paramList2)
  {
    this.zzzH = paramInt;
    List localList1;
    List localList2;
    if (paramList == null)
    {
      localList1 = Collections.emptyList();
      this.zzarB = localList1;
      this.zzarG = paramBoolean;
      if (paramList2 != null)
        break label104;
      localList2 = Collections.emptyList();
      label39: this.zzarH = localList2;
      if (paramList1 != null)
        break label114;
    }
    label104: label114: for (List localList3 = Collections.emptyList(); ; localList3 = Collections.unmodifiableList(paramList1))
    {
      this.zzarI = localList3;
      this.zzarC = zzm(this.zzarB);
      this.zzarJ = zzm(this.zzarH);
      this.zzarK = zzm(this.zzarI);
      return;
      localList1 = Collections.unmodifiableList(paramList);
      break;
      localList2 = Collections.unmodifiableList(paramList2);
      break label39;
    }
  }

  public PlaceFilter(Collection<Integer> paramCollection, boolean paramBoolean, Collection<String> paramCollection1, Collection<UserDataType> paramCollection2)
  {
    this(0, zzb(paramCollection), paramBoolean, zzb(paramCollection1), zzb(paramCollection2));
  }

  public PlaceFilter(boolean paramBoolean, Collection<String> paramCollection)
  {
    this(null, paramBoolean, paramCollection, null);
  }

  private static <E> List<E> zzb(Collection<E> paramCollection)
  {
    if ((paramCollection == null) || (paramCollection.isEmpty()))
      return Collections.emptyList();
    return new ArrayList(paramCollection);
  }

  private static <E> Set<E> zzm(List<E> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty()))
      return Collections.emptySet();
    return Collections.unmodifiableSet(new HashSet(paramList));
  }

  @Deprecated
  public static PlaceFilter zzsU()
  {
    return new zza(null).zzsV();
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlaceFilter localPlaceFilter;
    do
    {
      return true;
      if (!(paramObject instanceof PlaceFilter))
        return false;
      localPlaceFilter = (PlaceFilter)paramObject;
    }
    while ((this.zzarC.equals(localPlaceFilter.zzarC)) && (this.zzarG == localPlaceFilter.zzarG) && (this.zzarJ.equals(localPlaceFilter.zzarJ)) && (this.zzarK.equals(localPlaceFilter.zzarK)));
    return false;
  }

  public Set<String> getPlaceIds()
  {
    return this.zzarK;
  }

  public Set<Integer> getPlaceTypes()
  {
    return this.zzarC;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.zzarC;
    arrayOfObject[1] = Boolean.valueOf(this.zzarG);
    arrayOfObject[2] = this.zzarJ;
    arrayOfObject[3] = this.zzarK;
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isRestrictedToPlacesOpenNow()
  {
    return this.zzarG;
  }

  public boolean matches(Place paramPlace)
  {
    if ((isRestrictedToPlacesOpenNow()) && (paramPlace.zzsT()))
      return false;
    Set localSet1 = getPlaceTypes();
    int i;
    if (localSet1.isEmpty())
      i = 1;
    while (true)
      if (i == 0)
      {
        return false;
        Iterator localIterator = paramPlace.getPlaceTypes().iterator();
        do
          if (!localIterator.hasNext())
            break;
        while (!localSet1.contains((Integer)localIterator.next()));
        i = 1;
      }
      else
      {
        Set localSet2 = getPlaceIds();
        if ((localSet2.isEmpty()) || (localSet2.contains(paramPlace.getId())));
        for (int j = 1; j == 0; j = 0)
          return false;
        return true;
        i = 0;
      }
  }

  public String toString()
  {
    zzu.zza localzza = zzu.zzq(this);
    if (!this.zzarC.isEmpty())
      localzza.zzg("types", this.zzarC);
    localzza.zzg("requireOpenNow", Boolean.valueOf(this.zzarG));
    if (!this.zzarK.isEmpty())
      localzza.zzg("placeIds", this.zzarK);
    if (!this.zzarJ.isEmpty())
      localzza.zzg("requestedUserDataTypes", this.zzarJ);
    return localzza.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzd.zza(this, paramParcel, paramInt);
  }

  @Deprecated
  public static final class zza
  {
    private boolean zzarG = false;
    private Collection<Integer> zzarL = null;
    private Collection<UserDataType> zzarM = null;
    private String[] zzarN = null;

    public PlaceFilter zzsV()
    {
      ArrayList localArrayList1;
      if (this.zzarL != null)
      {
        localArrayList1 = new ArrayList(this.zzarL);
        if (this.zzarM == null)
          break label80;
      }
      label80: for (ArrayList localArrayList2 = new ArrayList(this.zzarM); ; localArrayList2 = null)
      {
        String[] arrayOfString = this.zzarN;
        List localList = null;
        if (arrayOfString != null)
          localList = Arrays.asList(this.zzarN);
        return new PlaceFilter(localArrayList1, this.zzarG, localList, localArrayList2);
        localArrayList1 = null;
        break;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceFilter
 * JD-Core Version:    0.6.2
 */