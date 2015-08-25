package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import java.util.List;

public class HereContent
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private final String zzath;
  private final List<Action> zzati;
  final int zzzH;

  HereContent(int paramInt, String paramString, List<Action> paramList)
  {
    this.zzzH = paramInt;
    this.zzath = paramString;
    this.zzati = paramList;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    HereContent localHereContent;
    do
    {
      return true;
      if (!(paramObject instanceof HereContent))
        return false;
      localHereContent = (HereContent)paramObject;
    }
    while ((zzu.equal(this.zzath, localHereContent.zzath)) && (zzu.equal(this.zzati, localHereContent.zzati)));
    return false;
  }

  public List<Action> getActions()
  {
    return this.zzati;
  }

  public String getData()
  {
    return this.zzath;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.zzath;
    arrayOfObject[1] = this.zzati;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("data", this.zzath).zzg("actions", this.zzati).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }

  public static final class Action
    implements SafeParcelable
  {
    public static final zza CREATOR = new zza();
    private final String zzJf;
    private final String zzWn;
    final int zzzH;

    Action(int paramInt, String paramString1, String paramString2)
    {
      this.zzzH = paramInt;
      this.zzWn = paramString1;
      this.zzJf = paramString2;
    }

    public int describeContents()
    {
      return 0;
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      Action localAction;
      do
      {
        return true;
        if (!(paramObject instanceof Action))
          return false;
        localAction = (Action)paramObject;
      }
      while ((zzu.equal(this.zzWn, localAction.zzWn)) && (zzu.equal(this.zzJf, localAction.zzJf)));
      return false;
    }

    public String getTitle()
    {
      return this.zzWn;
    }

    public String getUri()
    {
      return this.zzJf;
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.zzWn;
      arrayOfObject[1] = this.zzJf;
      return zzu.hashCode(arrayOfObject);
    }

    public String toString()
    {
      return zzu.zzq(this).zzg("title", this.zzWn).zzg("uri", this.zzJf).toString();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zza.zza(this, paramParcel, paramInt);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.HereContent
 * JD-Core Version:    0.6.2
 */