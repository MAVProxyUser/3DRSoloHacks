package com.google.android.gms.location.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public class ParcelableGeofence
  implements SafeParcelable, Geofence
{
  public static final zzm CREATOR = new zzm();
  private final String zzAu;
  private final int zzapI;
  private final short zzapK;
  private final double zzapL;
  private final double zzapM;
  private final float zzapN;
  private final int zzapO;
  private final int zzapP;
  private final long zzars;
  private final int zzzH;

  public ParcelableGeofence(int paramInt1, String paramString, int paramInt2, short paramShort, double paramDouble1, double paramDouble2, float paramFloat, long paramLong, int paramInt3, int paramInt4)
  {
    zzcH(paramString);
    zze(paramFloat);
    zza(paramDouble1, paramDouble2);
    int i = zzfK(paramInt2);
    this.zzzH = paramInt1;
    this.zzapK = paramShort;
    this.zzAu = paramString;
    this.zzapL = paramDouble1;
    this.zzapM = paramDouble2;
    this.zzapN = paramFloat;
    this.zzars = paramLong;
    this.zzapI = i;
    this.zzapO = paramInt3;
    this.zzapP = paramInt4;
  }

  public ParcelableGeofence(String paramString, int paramInt1, short paramShort, double paramDouble1, double paramDouble2, float paramFloat, long paramLong, int paramInt2, int paramInt3)
  {
    this(1, paramString, paramInt1, paramShort, paramDouble1, paramDouble2, paramFloat, paramLong, paramInt2, paramInt3);
  }

  private static void zza(double paramDouble1, double paramDouble2)
  {
    if ((paramDouble1 > 90.0D) || (paramDouble1 < -90.0D))
      throw new IllegalArgumentException("invalid latitude: " + paramDouble1);
    if ((paramDouble2 > 180.0D) || (paramDouble2 < -180.0D))
      throw new IllegalArgumentException("invalid longitude: " + paramDouble2);
  }

  private static void zzcH(String paramString)
  {
    if ((paramString == null) || (paramString.length() > 100))
      throw new IllegalArgumentException("requestId is null or too long: " + paramString);
  }

  private static void zze(float paramFloat)
  {
    if (paramFloat <= 0.0F)
      throw new IllegalArgumentException("invalid radius: " + paramFloat);
  }

  private static int zzfK(int paramInt)
  {
    int i = paramInt & 0x7;
    if (i == 0)
      throw new IllegalArgumentException("No supported transition specified: " + paramInt);
    return i;
  }

  private static String zzfL(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 1:
    }
    return "CIRCLE";
  }

  public static ParcelableGeofence zzk(byte[] paramArrayOfByte)
  {
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(paramArrayOfByte, 0, paramArrayOfByte.length);
    localParcel.setDataPosition(0);
    ParcelableGeofence localParcelableGeofence = CREATOR.zzdJ(localParcel);
    localParcel.recycle();
    return localParcelableGeofence;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    ParcelableGeofence localParcelableGeofence;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (!(paramObject instanceof ParcelableGeofence))
        return false;
      localParcelableGeofence = (ParcelableGeofence)paramObject;
      if (this.zzapN != localParcelableGeofence.zzapN)
        return false;
      if (this.zzapL != localParcelableGeofence.zzapL)
        return false;
      if (this.zzapM != localParcelableGeofence.zzapM)
        return false;
    }
    while (this.zzapK == localParcelableGeofence.zzapK);
    return false;
  }

  public long getExpirationTime()
  {
    return this.zzars;
  }

  public double getLatitude()
  {
    return this.zzapL;
  }

  public double getLongitude()
  {
    return this.zzapM;
  }

  public int getNotificationResponsiveness()
  {
    return this.zzapO;
  }

  public String getRequestId()
  {
    return this.zzAu;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.zzapL);
    int i = 31 + (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.zzapM);
    return 31 * (31 * (31 * (i * 31 + (int)(l2 ^ l2 >>> 32)) + Float.floatToIntBits(this.zzapN)) + this.zzapK) + this.zzapI;
  }

  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[9];
    arrayOfObject[0] = zzfL(this.zzapK);
    arrayOfObject[1] = this.zzAu;
    arrayOfObject[2] = Integer.valueOf(this.zzapI);
    arrayOfObject[3] = Double.valueOf(this.zzapL);
    arrayOfObject[4] = Double.valueOf(this.zzapM);
    arrayOfObject[5] = Float.valueOf(this.zzapN);
    arrayOfObject[6] = Integer.valueOf(this.zzapO / 1000);
    arrayOfObject[7] = Integer.valueOf(this.zzapP);
    arrayOfObject[8] = Long.valueOf(this.zzars);
    return String.format(localLocale, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", arrayOfObject);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzm.zza(this, paramParcel, paramInt);
  }

  public short zzsM()
  {
    return this.zzapK;
  }

  public float zzsN()
  {
    return this.zzapN;
  }

  public int zzsO()
  {
    return this.zzapI;
  }

  public int zzsP()
  {
    return this.zzapP;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.ParcelableGeofence
 * JD-Core Version:    0.6.2
 */