package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ConnectionEvent
  implements SafeParcelable
{
  public static final Parcelable.Creator<ConnectionEvent> CREATOR = new zza();
  private final long zzUR;
  private String zzUS;
  private final String zzUT;
  private final String zzUU;
  private final String zzUV;
  private final String zzUW;
  private final String zzUX;
  private final long zzUY;
  private final long zzUZ;
  private final long zzVa;
  private long zzVb;
  final int zzzH;

  ConnectionEvent(int paramInt, long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2, long paramLong3, long paramLong4)
  {
    this.zzzH = paramInt;
    this.zzUR = paramLong1;
    this.zzUS = paramString1;
    this.zzUT = paramString2;
    this.zzUU = paramString3;
    this.zzUV = paramString4;
    this.zzUW = paramString5;
    this.zzVb = -1L;
    this.zzUX = paramString6;
    this.zzUY = paramLong2;
    this.zzUZ = paramLong3;
    this.zzVa = paramLong4;
  }

  public ConnectionEvent(long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong2, long paramLong3, long paramLong4)
  {
    this(1, paramLong1, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramLong2, paramLong3, paramLong4);
  }

  public int describeContents()
  {
    return 0;
  }

  public long getTimeMillis()
  {
    return this.zzUR;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }

  public String zzmS()
  {
    return this.zzUS;
  }

  public String zzmT()
  {
    return this.zzUT;
  }

  public String zzmU()
  {
    return this.zzUU;
  }

  public String zzmV()
  {
    return this.zzUV;
  }

  public String zzmW()
  {
    return this.zzUW;
  }

  public String zzmX()
  {
    return this.zzUX;
  }

  public long zzmY()
  {
    return this.zzUY;
  }

  public long zzmZ()
  {
    return this.zzVa;
  }

  public long zzna()
  {
    return this.zzUZ;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.ConnectionEvent
 * JD-Core Version:    0.6.2
 */