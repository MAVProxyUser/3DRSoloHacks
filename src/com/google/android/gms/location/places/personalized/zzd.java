package com.google.android.gms.location.places.personalized;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzv;

public final class zzd extends com.google.android.gms.common.data.zzd<PlaceUserData>
  implements Result
{
  private final Status zzKr;

  public zzd(DataHolder paramDataHolder)
  {
    this(paramDataHolder, new Status(paramDataHolder.getStatusCode()));
  }

  private zzd(DataHolder paramDataHolder, Status paramStatus)
  {
    super(paramDataHolder, PlaceUserData.CREATOR);
    if ((paramDataHolder == null) || (paramDataHolder.getStatusCode() == paramStatus.getStatusCode()));
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzQ(bool);
      this.zzKr = paramStatus;
      return;
    }
  }

  public static zzd zzaE(Status paramStatus)
  {
    return new zzd(null, paramStatus);
  }

  public Status getStatus()
  {
    return this.zzKr;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.zzd
 * JD-Core Version:    0.6.2
 */