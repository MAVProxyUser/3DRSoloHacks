package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import java.util.Locale;

public class PlacesParams
  implements SafeParcelable
{
  public static final zzp CREATOR = new zzp();
  public static final PlacesParams zzatb = new PlacesParams("com.google.android.gms", Locale.getDefault(), null);
  public final int versionCode;
  public final String zzasc;
  public final String zzatc;
  public final String zzatd;
  public final String zzate;
  public final String zzatf;
  public final int zzatg;

  public PlacesParams(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2)
  {
    this.versionCode = paramInt1;
    this.zzatc = paramString1;
    this.zzatd = paramString2;
    this.zzate = paramString3;
    this.zzasc = paramString4;
    this.zzatf = paramString5;
    this.zzatg = paramInt2;
  }

  public PlacesParams(String paramString1, Locale paramLocale, String paramString2)
  {
    this(1, paramString1, paramLocale.toString(), paramString2, null, null, 7327000);
  }

  public PlacesParams(String paramString1, Locale paramLocale, String paramString2, String paramString3, String paramString4)
  {
    this(1, paramString1, paramLocale.toString(), paramString2, paramString3, paramString4, 7327000);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlacesParams localPlacesParams;
    do
    {
      return true;
      if ((paramObject == null) || (!(paramObject instanceof PlacesParams)))
        return false;
      localPlacesParams = (PlacesParams)paramObject;
    }
    while ((this.zzatd.equals(localPlacesParams.zzatd)) && (this.zzatc.equals(localPlacesParams.zzatc)) && (zzu.equal(this.zzate, localPlacesParams.zzate)) && (zzu.equal(this.zzasc, localPlacesParams.zzasc)) && (zzu.equal(this.zzatf, localPlacesParams.zzatf)));
    return false;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = this.zzatc;
    arrayOfObject[1] = this.zzatd;
    arrayOfObject[2] = this.zzate;
    arrayOfObject[3] = this.zzasc;
    arrayOfObject[4] = this.zzatf;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("clientPackageName", this.zzatc).zzg("locale", this.zzatd).zzg("accountName", this.zzate).zzg("gCoreClientName", this.zzasc).zzg("chargedPackageName", this.zzatf).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzp.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.PlacesParams
 * JD-Core Version:    0.6.2
 */