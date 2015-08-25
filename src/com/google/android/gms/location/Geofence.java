package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.location.internal.ParcelableGeofence;

public abstract interface Geofence
{
  public static final int GEOFENCE_TRANSITION_DWELL = 4;
  public static final int GEOFENCE_TRANSITION_ENTER = 1;
  public static final int GEOFENCE_TRANSITION_EXIT = 2;
  public static final long NEVER_EXPIRE = -1L;

  public abstract String getRequestId();

  public static final class Builder
  {
    private String zzAu = null;
    private int zzapI = 0;
    private long zzapJ = -9223372036854775808L;
    private short zzapK = -1;
    private double zzapL;
    private double zzapM;
    private float zzapN;
    private int zzapO = 0;
    private int zzapP = -1;

    public Geofence build()
    {
      if (this.zzAu == null)
        throw new IllegalArgumentException("Request ID not set.");
      if (this.zzapI == 0)
        throw new IllegalArgumentException("Transitions types not set.");
      if (((0x4 & this.zzapI) != 0) && (this.zzapP < 0))
        throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
      if (this.zzapJ == -9223372036854775808L)
        throw new IllegalArgumentException("Expiration not set.");
      if (this.zzapK == -1)
        throw new IllegalArgumentException("Geofence region not set.");
      if (this.zzapO < 0)
        throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
      return new ParcelableGeofence(this.zzAu, this.zzapI, (short)1, this.zzapL, this.zzapM, this.zzapN, this.zzapJ, this.zzapO, this.zzapP);
    }

    public Builder setCircularRegion(double paramDouble1, double paramDouble2, float paramFloat)
    {
      this.zzapK = 1;
      this.zzapL = paramDouble1;
      this.zzapM = paramDouble2;
      this.zzapN = paramFloat;
      return this;
    }

    public Builder setExpirationDuration(long paramLong)
    {
      if (paramLong < 0L)
      {
        this.zzapJ = -1L;
        return this;
      }
      this.zzapJ = (paramLong + SystemClock.elapsedRealtime());
      return this;
    }

    public Builder setLoiteringDelay(int paramInt)
    {
      this.zzapP = paramInt;
      return this;
    }

    public Builder setNotificationResponsiveness(int paramInt)
    {
      this.zzapO = paramInt;
      return this;
    }

    public Builder setRequestId(String paramString)
    {
      this.zzAu = paramString;
      return this;
    }

    public Builder setTransitionTypes(int paramInt)
    {
      this.zzapI = paramInt;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.Geofence
 * JD-Core Version:    0.6.2
 */