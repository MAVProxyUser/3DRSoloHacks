package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CheckServerAuthResult
  implements SafeParcelable
{
  public static final Parcelable.Creator<CheckServerAuthResult> CREATOR = new zzc();
  final boolean zzaBL;
  final List<Scope> zzaBM;
  final int zzzH;

  CheckServerAuthResult(int paramInt, boolean paramBoolean, List<Scope> paramList)
  {
    this.zzzH = paramInt;
    this.zzaBL = paramBoolean;
    this.zzaBM = paramList;
  }

  public CheckServerAuthResult(boolean paramBoolean, Set<Scope> paramSet)
  {
    this(1, paramBoolean, zzf(paramSet));
  }

  private static List<Scope> zzf(Set<Scope> paramSet)
  {
    if (paramSet == null)
      return Collections.emptyList();
    return Collections.unmodifiableList(new ArrayList(paramSet));
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.CheckServerAuthResult
 * JD-Core Version:    0.6.2
 */