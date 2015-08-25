package com.o3dr.services.android.lib.drone.connection;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class DroneSharePrefs
  implements Parcelable
{
  public static final Parcelable.Creator<DroneSharePrefs> CREATOR = new Parcelable.Creator()
  {
    public DroneSharePrefs createFromParcel(Parcel paramAnonymousParcel)
    {
      return new DroneSharePrefs(paramAnonymousParcel, null);
    }

    public DroneSharePrefs[] newArray(int paramAnonymousInt)
    {
      return new DroneSharePrefs[paramAnonymousInt];
    }
  };
  private final boolean enableLiveUpload;
  private final boolean isEnabled;
  private final String password;
  private final String username;

  private DroneSharePrefs(Parcel paramParcel)
  {
    this.username = paramParcel.readString();
    this.password = paramParcel.readString();
    boolean bool2;
    if (paramParcel.readByte() != 0)
    {
      bool2 = bool1;
      this.isEnabled = bool2;
      if (paramParcel.readByte() == 0)
        break label54;
    }
    while (true)
    {
      this.enableLiveUpload = bool1;
      return;
      bool2 = false;
      break;
      label54: bool1 = false;
    }
  }

  public DroneSharePrefs(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.username = paramString1;
    this.password = paramString2;
    this.isEnabled = paramBoolean1;
    this.enableLiveUpload = paramBoolean2;
  }

  public boolean areLoginCredentialsSet()
  {
    return (!TextUtils.isEmpty(this.username)) && (!TextUtils.isEmpty(this.password));
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    DroneSharePrefs localDroneSharePrefs;
    do
    {
      return true;
      if (!(paramObject instanceof DroneSharePrefs))
        return false;
      localDroneSharePrefs = (DroneSharePrefs)paramObject;
      if (this.enableLiveUpload != localDroneSharePrefs.enableLiveUpload)
        return false;
      if (this.isEnabled != localDroneSharePrefs.isEnabled)
        return false;
      if (this.password != null)
      {
        if (this.password.equals(localDroneSharePrefs.password));
      }
      else
        while (localDroneSharePrefs.password != null)
          return false;
      if (this.username == null)
        break;
    }
    while (this.username.equals(localDroneSharePrefs.username));
    while (true)
    {
      return false;
      if (localDroneSharePrefs.username == null)
        break;
    }
  }

  public String getPassword()
  {
    return this.password;
  }

  public String getUsername()
  {
    return this.username;
  }

  public int hashCode()
  {
    int i = 1;
    int j;
    int m;
    label38: int i1;
    label57: int i2;
    if (this.username != null)
    {
      j = this.username.hashCode();
      int k = j * 31;
      if (this.password == null)
        break label84;
      m = this.password.hashCode();
      int n = 31 * (k + m);
      if (!this.isEnabled)
        break label90;
      i1 = i;
      i2 = 31 * (n + i1);
      if (!this.enableLiveUpload)
        break label96;
    }
    while (true)
    {
      return i2 + i;
      j = 0;
      break;
      label84: m = 0;
      break label38;
      label90: i1 = 0;
      break label57;
      label96: i = 0;
    }
  }

  public boolean isEnabled()
  {
    return this.isEnabled;
  }

  public boolean isLiveUploadEnabled()
  {
    return (this.isEnabled) && (this.enableLiveUpload);
  }

  public String toString()
  {
    return "DroneSharePrefs{username='" + this.username + '\'' + ", isEnabled=" + this.isEnabled + ", enableLiveUpload=" + this.enableLiveUpload + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    byte b1 = 1;
    paramParcel.writeString(this.username);
    paramParcel.writeString(this.password);
    byte b2;
    if (this.isEnabled)
    {
      b2 = b1;
      paramParcel.writeByte(b2);
      if (!this.enableLiveUpload)
        break label53;
    }
    while (true)
    {
      paramParcel.writeByte(b1);
      return;
      b2 = 0;
      break;
      label53: b1 = 0;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.connection.DroneSharePrefs
 * JD-Core Version:    0.6.2
 */