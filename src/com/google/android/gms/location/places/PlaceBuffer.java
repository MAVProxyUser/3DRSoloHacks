package com.google.android.gms.location.places;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.internal.zzo;

public class PlaceBuffer extends AbstractDataBuffer<Place>
  implements Result
{
  private final Context mContext;
  private final Status zzKr;
  private final String zzarF;

  public PlaceBuffer(DataHolder paramDataHolder, Context paramContext)
  {
    super(paramDataHolder);
    this.mContext = paramContext;
    this.zzKr = new Status(paramDataHolder.getStatusCode());
    if ((paramDataHolder != null) && (paramDataHolder.zzlm() != null))
    {
      this.zzarF = paramDataHolder.zzlm().getString("com.google.android.gms.location.places.PlaceBuffer.ATTRIBUTIONS_EXTRA_KEY");
      return;
    }
    this.zzarF = null;
  }

  public Place get(int paramInt)
  {
    return new zzo(this.zzPy, paramInt, this.mContext);
  }

  public CharSequence getAttributions()
  {
    return this.zzarF;
  }

  public Status getStatus()
  {
    return this.zzKr;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceBuffer
 * JD-Core Version:    0.6.2
 */