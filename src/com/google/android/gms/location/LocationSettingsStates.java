package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;

public final class LocationSettingsStates
  implements SafeParcelable
{
  public static final Parcelable.Creator<LocationSettingsStates> CREATOR = new zzg();
  private final boolean zzaqq;
  private final boolean zzaqr;
  private final boolean zzaqs;
  private final boolean zzaqt;
  private final boolean zzaqu;
  private final boolean zzaqv;
  private final boolean zzaqw;
  private final int zzzH;

  LocationSettingsStates(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7)
  {
    this.zzzH = paramInt;
    this.zzaqq = paramBoolean1;
    this.zzaqr = paramBoolean2;
    this.zzaqs = paramBoolean3;
    this.zzaqt = paramBoolean4;
    this.zzaqu = paramBoolean5;
    this.zzaqv = paramBoolean6;
    this.zzaqw = paramBoolean7;
  }

  public static LocationSettingsStates fromIntent(Intent paramIntent)
  {
    return (LocationSettingsStates)zzc.zza(paramIntent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
  }

  public int describeContents()
  {
    return 0;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public boolean isBlePresent()
  {
    return this.zzaqv;
  }

  public boolean isBleUsable()
  {
    return this.zzaqs;
  }

  public boolean isGpsPresent()
  {
    return this.zzaqt;
  }

  public boolean isGpsUsable()
  {
    return this.zzaqq;
  }

  public boolean isLocationPresent()
  {
    return (this.zzaqt) || (this.zzaqu);
  }

  public boolean isLocationUsable()
  {
    return (this.zzaqq) || (this.zzaqr);
  }

  public boolean isNetworkLocationPresent()
  {
    return this.zzaqu;
  }

  public boolean isNetworkLocationUsable()
  {
    return this.zzaqr;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza(this, paramParcel, paramInt);
  }

  public boolean zzsE()
  {
    return this.zzaqw;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationSettingsStates
 * JD-Core Version:    0.6.2
 */