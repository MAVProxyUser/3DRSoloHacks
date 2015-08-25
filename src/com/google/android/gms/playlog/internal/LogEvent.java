package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Iterator;
import java.util.Set;

public class LogEvent
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  public final String tag;
  public final int versionCode;
  public final long zzayr;
  public final byte[] zzays;
  public final Bundle zzayt;

  LogEvent(int paramInt, long paramLong, String paramString, byte[] paramArrayOfByte, Bundle paramBundle)
  {
    this.versionCode = paramInt;
    this.zzayr = paramLong;
    this.tag = paramString;
    this.zzays = paramArrayOfByte;
    this.zzayt = paramBundle;
  }

  public LogEvent(long paramLong, String paramString, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    this.versionCode = 1;
    this.zzayr = paramLong;
    this.tag = paramString;
    this.zzays = paramArrayOfByte;
    this.zzayt = zzd(paramArrayOfString);
  }

  private static Bundle zzd(String[] paramArrayOfString)
  {
    Bundle localBundle = null;
    if (paramArrayOfString == null);
    while (true)
    {
      return localBundle;
      if (paramArrayOfString.length % 2 != 0)
        throw new IllegalArgumentException("extras must have an even number of elements");
      int i = paramArrayOfString.length / 2;
      localBundle = null;
      if (i != 0)
      {
        localBundle = new Bundle(i);
        for (int j = 0; j < i; j++)
          localBundle.putString(paramArrayOfString[(j * 2)], paramArrayOfString[(1 + j * 2)]);
      }
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("tag=").append(this.tag).append(",");
    localStringBuilder.append("eventTime=").append(this.zzayr).append(",");
    if ((this.zzayt != null) && (!this.zzayt.isEmpty()))
    {
      localStringBuilder.append("keyValues=");
      Iterator localIterator = this.zzayt.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localStringBuilder.append("(").append(str).append(",");
        localStringBuilder.append(this.zzayt.getString(str)).append(")");
        localStringBuilder.append(" ");
      }
    }
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.LogEvent
 * JD-Core Version:    0.6.2
 */