package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.location.places.internal.zzb;

public class AutocompletePredictionBuffer extends AbstractDataBuffer<AutocompletePrediction>
  implements Result
{
  public AutocompletePredictionBuffer(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }

  public AutocompletePrediction get(int paramInt)
  {
    return new zzb(this.zzPy, paramInt);
  }

  public Status getStatus()
  {
    return new Status(this.zzPy.getStatusCode());
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("status", getStatus()).toString();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.AutocompletePredictionBuffer
 * JD-Core Version:    0.6.2
 */