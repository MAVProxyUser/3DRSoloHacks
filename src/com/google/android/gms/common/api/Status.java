package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;

public final class Status
  implements Result, SafeParcelable
{
  public static final StatusCreator CREATOR = new StatusCreator();
  public static final Status zzQU = new Status(0);
  public static final Status zzQV = new Status(14);
  public static final Status zzQW = new Status(8);
  public static final Status zzQX = new Status(15);
  public static final Status zzQY = new Status(16);
  private final PendingIntent mPendingIntent;
  private final int zzOJ;
  private final String zzQZ;
  private final int zzzH;

  public Status(int paramInt)
  {
    this(paramInt, null);
  }

  Status(int paramInt1, int paramInt2, String paramString, PendingIntent paramPendingIntent)
  {
    this.zzzH = paramInt1;
    this.zzOJ = paramInt2;
    this.zzQZ = paramString;
    this.mPendingIntent = paramPendingIntent;
  }

  public Status(int paramInt, String paramString)
  {
    this(1, paramInt, paramString, null);
  }

  public Status(int paramInt, String paramString, PendingIntent paramPendingIntent)
  {
    this(1, paramInt, paramString, paramPendingIntent);
  }

  private String zzkv()
  {
    if (this.zzQZ != null)
      return this.zzQZ;
    return CommonStatusCodes.getStatusCodeString(this.zzOJ);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Status));
    Status localStatus;
    do
    {
      return false;
      localStatus = (Status)paramObject;
    }
    while ((this.zzzH != localStatus.zzzH) || (this.zzOJ != localStatus.zzOJ) || (!zzu.equal(this.zzQZ, localStatus.zzQZ)) || (!zzu.equal(this.mPendingIntent, localStatus.mPendingIntent)));
    return true;
  }

  public PendingIntent getResolution()
  {
    return this.mPendingIntent;
  }

  public Status getStatus()
  {
    return this;
  }

  public int getStatusCode()
  {
    return this.zzOJ;
  }

  public String getStatusMessage()
  {
    return this.zzQZ;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public boolean hasResolution()
  {
    return this.mPendingIntent != null;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Integer.valueOf(this.zzzH);
    arrayOfObject[1] = Integer.valueOf(this.zzOJ);
    arrayOfObject[2] = this.zzQZ;
    arrayOfObject[3] = this.mPendingIntent;
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isCanceled()
  {
    return this.zzOJ == 16;
  }

  public boolean isInterrupted()
  {
    return this.zzOJ == 14;
  }

  public boolean isSuccess()
  {
    return this.zzOJ <= 0;
  }

  public void startResolutionForResult(Activity paramActivity, int paramInt)
    throws IntentSender.SendIntentException
  {
    if (!hasResolution())
      return;
    paramActivity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), paramInt, null, 0, 0, 0);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("statusCode", zzkv()).zzg("resolution", this.mPendingIntent).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    StatusCreator.zza(this, paramParcel, paramInt);
  }

  PendingIntent zzlf()
  {
    return this.mPendingIntent;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Status
 * JD-Core Version:    0.6.2
 */