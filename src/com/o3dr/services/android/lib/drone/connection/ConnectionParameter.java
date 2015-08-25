package com.o3dr.services.android.lib.drone.connection;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Iterator;
import java.util.Set;

public class ConnectionParameter
  implements Parcelable
{
  public static final Parcelable.Creator<ConnectionParameter> CREATOR = new Parcelable.Creator()
  {
    public ConnectionParameter createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ConnectionParameter(paramAnonymousParcel, null);
    }

    public ConnectionParameter[] newArray(int paramAnonymousInt)
    {
      return new ConnectionParameter[paramAnonymousInt];
    }
  };
  private final int connectionType;
  private final DroneSharePrefs droneSharePrefs;
  private final Bundle paramsBundle;

  public ConnectionParameter(int paramInt, Bundle paramBundle, DroneSharePrefs paramDroneSharePrefs)
  {
    this.connectionType = paramInt;
    this.paramsBundle = paramBundle;
    this.droneSharePrefs = paramDroneSharePrefs;
  }

  private ConnectionParameter(Parcel paramParcel)
  {
    this.connectionType = paramParcel.readInt();
    this.paramsBundle = paramParcel.readBundle();
    this.droneSharePrefs = ((DroneSharePrefs)paramParcel.readParcelable(DroneSharePrefs.class.getClassLoader()));
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof ConnectionParameter))
      return false;
    ConnectionParameter localConnectionParameter = (ConnectionParameter)paramObject;
    return getUniqueId().equals(localConnectionParameter.getUniqueId());
  }

  public int getConnectionType()
  {
    return this.connectionType;
  }

  public DroneSharePrefs getDroneSharePrefs()
  {
    return this.droneSharePrefs;
  }

  public Bundle getParamsBundle()
  {
    return this.paramsBundle;
  }

  public String getUniqueId()
  {
    switch (this.connectionType)
    {
    default:
      return "";
    case 1:
      int j = 14550;
      if (this.paramsBundle != null)
        j = this.paramsBundle.getInt("extra_udp_server_port", j);
      return "udp." + j;
    case 3:
      Bundle localBundle2 = this.paramsBundle;
      String str3 = null;
      if (localBundle2 != null)
        str3 = this.paramsBundle.getString("extra_bluetooth_address");
      if (str3 == null)
        return "bluetooth";
      return "bluetooth." + str3;
    case 2:
      int i = 5763;
      Bundle localBundle1 = this.paramsBundle;
      String str1 = null;
      if (localBundle1 != null)
      {
        str1 = this.paramsBundle.getString("extra_tcp_server_ip");
        i = this.paramsBundle.getInt("extra_tcp_server_port", i);
      }
      StringBuilder localStringBuilder = new StringBuilder().append("tcp.").append(i);
      if (str1 == null);
      for (String str2 = ""; ; str2 = "." + str1)
        return str2;
    case 0:
    }
    return "usb";
  }

  public int hashCode()
  {
    return getUniqueId().hashCode();
  }

  public String toString()
  {
    String str1 = "ConnectionParameter{connectionType=" + this.connectionType + ", paramsBundle=[";
    if ((this.paramsBundle != null) && (!this.paramsBundle.isEmpty()))
    {
      int i = 1;
      Iterator localIterator = this.paramsBundle.keySet().iterator();
      if (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if (i != 0)
          i = 0;
        while (true)
        {
          str1 = str1 + str2 + "=" + this.paramsBundle.get(str2);
          break;
          str1 = str1 + ", ";
        }
      }
    }
    return str1 + "]}";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.connectionType);
    paramParcel.writeBundle(this.paramsBundle);
    paramParcel.writeParcelable(this.droneSharePrefs, 0);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.connection.ConnectionParameter
 * JD-Core Version:    0.6.2
 */