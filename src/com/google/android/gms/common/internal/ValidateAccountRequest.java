package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ValidateAccountRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<ValidateAccountRequest> CREATOR = new zzab();
  final IBinder zzSS;
  private final Scope[] zzST;
  private final int zzUe;
  private final Bundle zzUf;
  private final String zzUg;
  final int zzzH;

  ValidateAccountRequest(int paramInt1, int paramInt2, IBinder paramIBinder, Scope[] paramArrayOfScope, Bundle paramBundle, String paramString)
  {
    this.zzzH = paramInt1;
    this.zzUe = paramInt2;
    this.zzSS = paramIBinder;
    this.zzST = paramArrayOfScope;
    this.zzUf = paramBundle;
    this.zzUg = paramString;
  }

  public ValidateAccountRequest(zzo paramzzo, Scope[] paramArrayOfScope, String paramString, Bundle paramBundle)
  {
  }

  public int describeContents()
  {
    return 0;
  }

  public String getCallingPackage()
  {
    return this.zzUg;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzab.zza(this, paramParcel, paramInt);
  }

  public int zzmq()
  {
    return this.zzUe;
  }

  public Scope[] zzmr()
  {
    return this.zzST;
  }

  public Bundle zzms()
  {
    return this.zzUf;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.ValidateAccountRequest
 * JD-Core Version:    0.6.2
 */