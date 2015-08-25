package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzv;

public class PlayLoggerContext
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  public final String packageName;
  public final int versionCode;
  public final int zzayB;
  public final int zzayC;
  public final String zzayD;
  public final String zzayE;
  public final boolean zzayF;
  public final String zzayG;
  public final boolean zzayH;

  public PlayLoggerContext(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, String paramString3, boolean paramBoolean1, String paramString4, boolean paramBoolean2)
  {
    this.versionCode = paramInt1;
    this.packageName = paramString1;
    this.zzayB = paramInt2;
    this.zzayC = paramInt3;
    this.zzayD = paramString2;
    this.zzayE = paramString3;
    this.zzayF = paramBoolean1;
    this.zzayG = paramString4;
    this.zzayH = paramBoolean2;
  }

  @Deprecated
  public PlayLoggerContext(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, boolean paramBoolean)
  {
    this.versionCode = 1;
    this.packageName = ((String)zzv.zzr(paramString1));
    this.zzayB = paramInt1;
    this.zzayC = paramInt2;
    this.zzayG = null;
    this.zzayD = paramString2;
    this.zzayE = paramString3;
    this.zzayF = paramBoolean;
    this.zzayH = false;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlayLoggerContext localPlayLoggerContext;
    do
    {
      return true;
      if (!(paramObject instanceof PlayLoggerContext))
        break;
      localPlayLoggerContext = (PlayLoggerContext)paramObject;
    }
    while ((this.packageName.equals(localPlayLoggerContext.packageName)) && (this.zzayB == localPlayLoggerContext.zzayB) && (this.zzayC == localPlayLoggerContext.zzayC) && (zzu.equal(this.zzayG, localPlayLoggerContext.zzayG)) && (zzu.equal(this.zzayD, localPlayLoggerContext.zzayD)) && (zzu.equal(this.zzayE, localPlayLoggerContext.zzayE)) && (this.zzayF == localPlayLoggerContext.zzayF) && (this.zzayH == localPlayLoggerContext.zzayH));
    return false;
    return false;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = this.packageName;
    arrayOfObject[1] = Integer.valueOf(this.zzayB);
    arrayOfObject[2] = Integer.valueOf(this.zzayC);
    arrayOfObject[3] = this.zzayG;
    arrayOfObject[4] = this.zzayD;
    arrayOfObject[5] = this.zzayE;
    arrayOfObject[6] = Boolean.valueOf(this.zzayF);
    arrayOfObject[7] = Boolean.valueOf(this.zzayH);
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PlayLoggerContext[");
    localStringBuilder.append("package=").append(this.packageName).append(',');
    localStringBuilder.append("versionCode=").append(this.versionCode).append(',');
    localStringBuilder.append("logSource=").append(this.zzayC).append(',');
    localStringBuilder.append("logSourceName=").append(this.zzayG).append(',');
    localStringBuilder.append("uploadAccount=").append(this.zzayD).append(',');
    localStringBuilder.append("loggingId=").append(this.zzayE).append(',');
    localStringBuilder.append("logAndroidId=").append(this.zzayF).append(',');
    localStringBuilder.append("isAnonymous=").append(this.zzayH);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.PlayLoggerContext
 * JD-Core Version:    0.6.2
 */