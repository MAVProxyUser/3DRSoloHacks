package com.google.android.gms.location.copresence.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CopresenceApiOptions
  implements SafeParcelable
{
  public static final Parcelable.Creator<CopresenceApiOptions> CREATOR = new zza();
  public static final CopresenceApiOptions zzaqx = new CopresenceApiOptions(true);
  public final boolean zzaqy;
  final int zzzH;

  CopresenceApiOptions(int paramInt, boolean paramBoolean)
  {
    this.zzzH = paramInt;
    this.zzaqy = paramBoolean;
  }

  public CopresenceApiOptions(boolean paramBoolean)
  {
    this(1, paramBoolean);
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.copresence.internal.CopresenceApiOptions
 * JD-Core Version:    0.6.2
 */