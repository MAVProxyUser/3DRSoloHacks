package com.google.android.gms.common.server;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class FavaDiagnosticsEntity
  implements SafeParcelable
{
  public static final zza CREATOR = new zza();
  public final String zzUq;
  public final int zzUr;
  final int zzzH;

  public FavaDiagnosticsEntity(int paramInt1, String paramString, int paramInt2)
  {
    this.zzzH = paramInt1;
    this.zzUq = paramString;
    this.zzUr = paramInt2;
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
 * Qualified Name:     com.google.android.gms.common.server.FavaDiagnosticsEntity
 * JD-Core Version:    0.6.2
 */