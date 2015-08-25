package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class UserDataType
  implements SafeParcelable
{
  public static final zzg CREATOR = new zzg();
  public static final UserDataType zzase = zzx("test_type", 1);
  public static final UserDataType zzasf = zzx("labeled_place", 6);
  public static final UserDataType zzasg = zzx("here_content", 7);
  public static final Set<UserDataType> zzash;
  final String zzAV;
  final int zzasi;
  final int zzzH;

  static
  {
    UserDataType[] arrayOfUserDataType = new UserDataType[3];
    arrayOfUserDataType[0] = zzase;
    arrayOfUserDataType[1] = zzasf;
    arrayOfUserDataType[2] = zzasg;
    zzash = Collections.unmodifiableSet(new HashSet(Arrays.asList(arrayOfUserDataType)));
  }

  UserDataType(int paramInt1, String paramString, int paramInt2)
  {
    zzv.zzbS(paramString);
    this.zzzH = paramInt1;
    this.zzAV = paramString;
    this.zzasi = paramInt2;
  }

  private static UserDataType zzx(String paramString, int paramInt)
  {
    return new UserDataType(0, paramString, paramInt);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    UserDataType localUserDataType;
    do
    {
      return true;
      if (!(paramObject instanceof UserDataType))
        return false;
      localUserDataType = (UserDataType)paramObject;
    }
    while ((this.zzAV.equals(localUserDataType.zzAV)) && (this.zzasi == localUserDataType.zzasi));
    return false;
  }

  public int hashCode()
  {
    return this.zzAV.hashCode();
  }

  public String toString()
  {
    return this.zzAV;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzg.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.UserDataType
 * JD-Core Version:    0.6.2
 */