package com.google.android.gms.location.places.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class PlaceImpl
  implements SafeParcelable, Place
{
  public static final zzj CREATOR = new zzj();
  private final String mName;
  private final String zzGM;
  private final String zzacM;
  private final LatLng zzarw;
  private final List<Integer> zzarx;
  private final String zzary;
  private final Uri zzarz;
  private final LatLngBounds zzasA;
  private final String zzasB;
  private final boolean zzasC;
  private final float zzasD;
  private final int zzasE;
  private final long zzasF;
  private final List<Integer> zzasG;
  private final String zzasH;
  private final List<String> zzasI;
  final boolean zzasJ;
  private final Map<Integer, String> zzasK;
  private final TimeZone zzasL;
  private zzn zzasM;
  private Locale zzasr;
  private final Bundle zzasx;

  @Deprecated
  private final PlaceLocalization zzasy;
  private final float zzasz;
  final int zzzH;

  PlaceImpl(int paramInt1, String paramString1, List<Integer> paramList1, List<Integer> paramList2, Bundle paramBundle, String paramString2, String paramString3, String paramString4, String paramString5, List<String> paramList, LatLng paramLatLng, float paramFloat1, LatLngBounds paramLatLngBounds, String paramString6, Uri paramUri, boolean paramBoolean1, float paramFloat2, int paramInt2, long paramLong, boolean paramBoolean2, PlaceLocalization paramPlaceLocalization)
  {
    this.zzzH = paramInt1;
    this.zzGM = paramString1;
    this.zzarx = Collections.unmodifiableList(paramList1);
    this.zzasG = paramList2;
    if (paramBundle != null)
    {
      this.zzasx = paramBundle;
      this.mName = paramString2;
      this.zzacM = paramString3;
      this.zzary = paramString4;
      this.zzasH = paramString5;
      if (paramList == null)
        break label182;
      label68: this.zzasI = paramList;
      this.zzarw = paramLatLng;
      this.zzasz = paramFloat1;
      this.zzasA = paramLatLngBounds;
      if (paramString6 == null)
        break label190;
    }
    while (true)
    {
      this.zzasB = paramString6;
      this.zzarz = paramUri;
      this.zzasC = paramBoolean1;
      this.zzasD = paramFloat2;
      this.zzasE = paramInt2;
      this.zzasF = paramLong;
      this.zzasK = Collections.unmodifiableMap(new HashMap());
      this.zzasL = null;
      this.zzasr = null;
      this.zzasJ = paramBoolean2;
      this.zzasy = paramPlaceLocalization;
      return;
      paramBundle = new Bundle();
      break;
      label182: paramList = Collections.emptyList();
      break label68;
      label190: paramString6 = "UTC";
    }
  }

  private void zzcJ(String paramString)
  {
    if ((this.zzasJ) && (this.zzasM != null))
      this.zzasM.zzz(this.zzGM, paramString);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlaceImpl localPlaceImpl;
    do
    {
      return true;
      if (!(paramObject instanceof PlaceImpl))
        return false;
      localPlaceImpl = (PlaceImpl)paramObject;
    }
    while ((this.zzGM.equals(localPlaceImpl.zzGM)) && (zzu.equal(this.zzasr, localPlaceImpl.zzasr)) && (this.zzasF == localPlaceImpl.zzasF));
    return false;
  }

  public String getAddress()
  {
    zzcJ("getAddress");
    return this.zzacM;
  }

  public String getId()
  {
    zzcJ("getId");
    return this.zzGM;
  }

  public LatLng getLatLng()
  {
    zzcJ("getLatLng");
    return this.zzarw;
  }

  public Locale getLocale()
  {
    zzcJ("getLocale");
    return this.zzasr;
  }

  public String getName()
  {
    zzcJ("getName");
    return this.mName;
  }

  public String getPhoneNumber()
  {
    zzcJ("getPhoneNumber");
    return this.zzary;
  }

  public List<Integer> getPlaceTypes()
  {
    zzcJ("getPlaceTypes");
    return this.zzarx;
  }

  public int getPriceLevel()
  {
    zzcJ("getPriceLevel");
    return this.zzasE;
  }

  public float getRating()
  {
    zzcJ("getRating");
    return this.zzasD;
  }

  public LatLngBounds getViewport()
  {
    zzcJ("getViewport");
    return this.zzasA;
  }

  public Uri getWebsiteUri()
  {
    zzcJ("getWebsiteUri");
    return this.zzarz;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.zzGM;
    arrayOfObject[1] = this.zzasr;
    arrayOfObject[2] = Long.valueOf(this.zzasF);
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isDataValid()
  {
    return true;
  }

  public void setLocale(Locale paramLocale)
  {
    this.zzasr = paramLocale;
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("id", this.zzGM).zzg("placeTypes", this.zzarx).zzg("locale", this.zzasr).zzg("name", this.mName).zzg("address", this.zzacM).zzg("phoneNumber", this.zzary).zzg("latlng", this.zzarw).zzg("viewport", this.zzasA).zzg("websiteUri", this.zzarz).zzg("isPermanentlyClosed", Boolean.valueOf(this.zzasC)).zzg("priceLevel", Integer.valueOf(this.zzasE)).zzg("timestampSecs", Long.valueOf(this.zzasF)).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }

  public void zza(zzn paramzzn)
  {
    this.zzasM = paramzzn;
  }

  public boolean zzsT()
  {
    zzcJ("isPermanentlyClosed");
    return this.zzasC;
  }

  public List<Integer> zzsY()
  {
    zzcJ("getTypesDeprecated");
    return this.zzasG;
  }

  public float zzsZ()
  {
    zzcJ("getLevelNumber");
    return this.zzasz;
  }

  public String zzta()
  {
    zzcJ("getRegularOpenHours");
    return this.zzasH;
  }

  public List<String> zztb()
  {
    zzcJ("getAttributions");
    return this.zzasI;
  }

  public long zztc()
  {
    return this.zzasF;
  }

  public Bundle zztd()
  {
    return this.zzasx;
  }

  public String zzte()
  {
    return this.zzasB;
  }

  @Deprecated
  public PlaceLocalization zztf()
  {
    zzcJ("getLocalization");
    return this.zzasy;
  }

  public Place zztg()
  {
    return this;
  }

  public static class zza
  {
    private String mName;
    private String zzGM;
    private String zzacM;
    private LatLng zzarw;
    private String zzary;
    private Uri zzarz;
    private LatLngBounds zzasA;
    private String zzasB;
    private boolean zzasC;
    private float zzasD;
    private int zzasE;
    private long zzasF;
    private String zzasH;
    private List<String> zzasI;
    private boolean zzasJ;
    private Bundle zzasN;
    private List<Integer> zzasO;
    private float zzasz;
    private int zzzH = 0;

    public zza zzY(boolean paramBoolean)
    {
      this.zzasC = paramBoolean;
      return this;
    }

    public zza zzZ(boolean paramBoolean)
    {
      this.zzasJ = paramBoolean;
      return this;
    }

    public zza zza(LatLng paramLatLng)
    {
      this.zzarw = paramLatLng;
      return this;
    }

    public zza zza(LatLngBounds paramLatLngBounds)
    {
      this.zzasA = paramLatLngBounds;
      return this;
    }

    public zza zzcK(String paramString)
    {
      this.zzGM = paramString;
      return this;
    }

    public zza zzcL(String paramString)
    {
      this.mName = paramString;
      return this;
    }

    public zza zzcM(String paramString)
    {
      this.zzacM = paramString;
      return this;
    }

    public zza zzcN(String paramString)
    {
      this.zzary = paramString;
      return this;
    }

    public zza zzf(float paramFloat)
    {
      this.zzasz = paramFloat;
      return this;
    }

    public zza zzfV(int paramInt)
    {
      this.zzasE = paramInt;
      return this;
    }

    public zza zzg(float paramFloat)
    {
      this.zzasD = paramFloat;
      return this;
    }

    public zza zzk(Uri paramUri)
    {
      this.zzarz = paramUri;
      return this;
    }

    public zza zzn(List<Integer> paramList)
    {
      this.zzasO = paramList;
      return this;
    }

    public zza zzo(List<String> paramList)
    {
      this.zzasI = paramList;
      return this;
    }

    public PlaceImpl zzth()
    {
      return new PlaceImpl(this.zzzH, this.zzGM, this.zzasO, Collections.emptyList(), this.zzasN, this.mName, this.zzacM, this.zzary, this.zzasH, this.zzasI, this.zzarw, this.zzasz, this.zzasA, this.zzasB, this.zzarz, this.zzasC, this.zzasD, this.zzasE, this.zzasF, this.zzasJ, PlaceLocalization.zza(this.mName, this.zzacM, this.zzary, this.zzasH, this.zzasI));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.PlaceImpl
 * JD-Core Version:    0.6.2
 */