package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeofencingRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<GeofencingRequest> CREATOR = new zza();
  public static final int INITIAL_TRIGGER_DWELL = 4;
  public static final int INITIAL_TRIGGER_ENTER = 1;
  public static final int INITIAL_TRIGGER_EXIT = 2;
  private final List<ParcelableGeofence> zzapT;
  private final int zzapU;
  private final int zzzH;

  GeofencingRequest(int paramInt1, List<ParcelableGeofence> paramList, int paramInt2)
  {
    this.zzzH = paramInt1;
    this.zzapT = paramList;
    this.zzapU = paramInt2;
  }

  private GeofencingRequest(List<ParcelableGeofence> paramList, int paramInt)
  {
    this(1, paramList, paramInt);
  }

  public int describeContents()
  {
    return 0;
  }

  public List<Geofence> getGeofences()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.zzapT);
    return localArrayList;
  }

  public int getInitialTrigger()
  {
    return this.zzapU;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }

  public List<ParcelableGeofence> zzsz()
  {
    return this.zzapT;
  }

  public static final class Builder
  {
    private final List<ParcelableGeofence> zzapT = new ArrayList();
    private int zzapU = 5;

    public static int zzfx(int paramInt)
    {
      return paramInt & 0x7;
    }

    public Builder addGeofence(Geofence paramGeofence)
    {
      zzv.zzb(paramGeofence, "geofence can't be null.");
      zzv.zzb(paramGeofence instanceof ParcelableGeofence, "Geofence must be created using Geofence.Builder.");
      this.zzapT.add((ParcelableGeofence)paramGeofence);
      return this;
    }

    public Builder addGeofences(List<Geofence> paramList)
    {
      if ((paramList == null) || (paramList.isEmpty()));
      while (true)
      {
        return this;
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Geofence localGeofence = (Geofence)localIterator.next();
          if (localGeofence != null)
            addGeofence(localGeofence);
        }
      }
    }

    public GeofencingRequest build()
    {
      if (!this.zzapT.isEmpty());
      for (boolean bool = true; ; bool = false)
      {
        zzv.zzb(bool, "No geofence has been added to this request.");
        return new GeofencingRequest(this.zzapT, this.zzapU, null);
      }
    }

    public Builder setInitialTrigger(int paramInt)
    {
      this.zzapU = zzfx(paramInt);
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.GeofencingRequest
 * JD-Core Version:    0.6.2
 */