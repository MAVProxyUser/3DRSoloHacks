package com.google.android.gms.location.places;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.location.places.internal.zzl;

public class PlaceLikelihoodBuffer extends AbstractDataBuffer<PlaceLikelihood>
  implements Result
{
  private final Context mContext;
  private final Status zzKr;
  private final String zzarF;
  private final int zzarO;

  public PlaceLikelihoodBuffer(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder);
    this.mContext = paramContext;
    this.zzKr = new Status(paramDataHolder.getStatusCode());
    this.zzarO = zza.zzfR(paramInt);
    if ((paramDataHolder != null) && (paramDataHolder.zzlm() != null))
    {
      this.zzarF = paramDataHolder.zzlm().getString("com.google.android.gms.location.places.PlaceLikelihoodBuffer.ATTRIBUTIONS_EXTRA_KEY");
      return;
    }
    this.zzarF = null;
  }

  public PlaceLikelihood get(int paramInt)
  {
    return new zzl(this.zzPy, paramInt, this.mContext);
  }

  public CharSequence getAttributions()
  {
    return this.zzarF;
  }

  public Status getStatus()
  {
    return this.zzKr;
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("status", getStatus()).zzg("attributions", this.zzarF).toString();
  }

  public static class zza
  {
    static int zzfR(int paramInt)
    {
      switch (paramInt)
      {
      default:
        throw new IllegalArgumentException("invalid source: " + paramInt);
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
      }
      return paramInt;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceLikelihoodBuffer
 * JD-Core Version:    0.6.2
 */