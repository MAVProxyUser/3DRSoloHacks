package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<LocationSettingsRequest> CREATOR = new zzf();
  private final List<LocationRequest> zzafq;
  private final boolean zzaql;
  private final boolean zzaqm;
  private final boolean zzaqn;
  private final int zzzH;

  LocationSettingsRequest(int paramInt, List<LocationRequest> paramList, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.zzzH = paramInt;
    this.zzafq = paramList;
    this.zzaql = paramBoolean1;
    this.zzaqm = paramBoolean2;
    this.zzaqn = paramBoolean3;
  }

  private LocationSettingsRequest(List<LocationRequest> paramList, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this(2, paramList, paramBoolean1, paramBoolean2, paramBoolean3);
  }

  public int describeContents()
  {
    return 0;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzf.zza(this, paramParcel, paramInt);
  }

  public List<LocationRequest> zzpu()
  {
    return Collections.unmodifiableList(this.zzafq);
  }

  public boolean zzsB()
  {
    return this.zzaql;
  }

  public boolean zzsC()
  {
    return this.zzaqm;
  }

  public boolean zzsD()
  {
    return this.zzaqn;
  }

  public static final class Builder
  {
    private boolean zzaql = false;
    private boolean zzaqm = false;
    private boolean zzaqn = false;
    private final ArrayList<LocationRequest> zzaqo = new ArrayList();

    public Builder addAllLocationRequests(Collection<LocationRequest> paramCollection)
    {
      this.zzaqo.addAll(paramCollection);
      return this;
    }

    public Builder addLocationRequest(LocationRequest paramLocationRequest)
    {
      this.zzaqo.add(paramLocationRequest);
      return this;
    }

    public LocationSettingsRequest build()
    {
      return new LocationSettingsRequest(this.zzaqo, this.zzaql, this.zzaqm, this.zzaqn, null);
    }

    public Builder setAlwaysShow(boolean paramBoolean)
    {
      this.zzaql = paramBoolean;
      return this;
    }

    public Builder setNeedBle(boolean paramBoolean)
    {
      this.zzaqm = paramBoolean;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationSettingsRequest
 * JD-Core Version:    0.6.2
 */