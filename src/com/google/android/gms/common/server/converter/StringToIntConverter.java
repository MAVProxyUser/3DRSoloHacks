package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class StringToIntConverter
  implements SafeParcelable, FastJsonResponse.zza<String, Integer>
{
  public static final zzb CREATOR = new zzb();
  private final HashMap<String, Integer> zzUt;
  private final HashMap<Integer, String> zzUu;
  private final ArrayList<Entry> zzUv;
  private final int zzzH;

  public StringToIntConverter()
  {
    this.zzzH = 1;
    this.zzUt = new HashMap();
    this.zzUu = new HashMap();
    this.zzUv = null;
  }

  StringToIntConverter(int paramInt, ArrayList<Entry> paramArrayList)
  {
    this.zzzH = paramInt;
    this.zzUt = new HashMap();
    this.zzUu = new HashMap();
    this.zzUv = null;
    zzb(paramArrayList);
  }

  private void zzb(ArrayList<Entry> paramArrayList)
  {
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      zzg(localEntry.zzUw, localEntry.zzUx);
    }
  }

  public int describeContents()
  {
    return 0;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }

  public String zzb(Integer paramInteger)
  {
    String str = (String)this.zzUu.get(paramInteger);
    if ((str == null) && (this.zzUt.containsKey("gms_unknown")))
      str = "gms_unknown";
    return str;
  }

  public StringToIntConverter zzg(String paramString, int paramInt)
  {
    this.zzUt.put(paramString, Integer.valueOf(paramInt));
    this.zzUu.put(Integer.valueOf(paramInt), paramString);
    return this;
  }

  ArrayList<Entry> zzmv()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.zzUt.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new Entry(str, ((Integer)this.zzUt.get(str)).intValue()));
    }
    return localArrayList;
  }

  public int zzmw()
  {
    return 7;
  }

  public int zzmx()
  {
    return 0;
  }

  public static final class Entry
    implements SafeParcelable
  {
    public static final zzc CREATOR = new zzc();
    final int versionCode;
    final String zzUw;
    final int zzUx;

    Entry(int paramInt1, String paramString, int paramInt2)
    {
      this.versionCode = paramInt1;
      this.zzUw = paramString;
      this.zzUx = paramInt2;
    }

    Entry(String paramString, int paramInt)
    {
      this.versionCode = 1;
      this.zzUw = paramString;
      this.zzUx = paramInt;
    }

    public int describeContents()
    {
      return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzc.zza(this, paramParcel, paramInt);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.converter.StringToIntConverter
 * JD-Core Version:    0.6.2
 */