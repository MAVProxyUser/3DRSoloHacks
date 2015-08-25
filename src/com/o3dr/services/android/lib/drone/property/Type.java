package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Type
  implements Parcelable
{
  public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator()
  {
    public Type createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Type(paramAnonymousParcel, null);
    }

    public Type[] newArray(int paramAnonymousInt)
    {
      return new Type[paramAnonymousInt];
    }
  };
  public static final int TYPE_COPTER = 2;
  public static final int TYPE_PLANE = 1;
  public static final int TYPE_ROVER = 10;
  public static final int TYPE_UNKNOWN = -1;
  private int droneType = -1;
  private Firmware firmware;
  private String firmwareVersion;

  public Type()
  {
  }

  public Type(int paramInt, String paramString)
  {
    this.droneType = paramInt;
    this.firmwareVersion = paramString;
    switch (paramInt)
    {
    default:
      this.firmware = null;
      return;
    case 2:
      this.firmware = Firmware.ARDU_COPTER;
      return;
    case 1:
      this.firmware = Firmware.ARDU_PLANE;
      return;
    case 10:
    }
    this.firmware = Firmware.APM_ROVER;
  }

  private Type(Parcel paramParcel)
  {
    this.droneType = paramParcel.readInt();
    this.firmwareVersion = paramParcel.readString();
    int i = paramParcel.readInt();
    if (i == -1);
    for (Firmware localFirmware = null; ; localFirmware = Firmware.values()[i])
    {
      this.firmware = localFirmware;
      return;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public int getDroneType()
  {
    return this.droneType;
  }

  public Firmware getFirmware()
  {
    return this.firmware;
  }

  public String getFirmwareVersion()
  {
    return this.firmwareVersion;
  }

  public void setDroneType(int paramInt)
  {
    this.droneType = paramInt;
  }

  public void setFirmware(Firmware paramFirmware)
  {
    this.firmware = paramFirmware;
  }

  public void setFirmwareVersion(String paramString)
  {
    this.firmwareVersion = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.droneType);
    paramParcel.writeString(this.firmwareVersion);
    if (this.firmware == null);
    for (int i = -1; ; i = this.firmware.ordinal())
    {
      paramParcel.writeInt(i);
      return;
    }
  }

  public static enum Firmware
  {
    private final String label;

    static
    {
      ARDU_COPTER = new Firmware("ARDU_COPTER", 1, "ArduCopter");
      APM_ROVER = new Firmware("APM_ROVER", 2, "APMRover");
      Firmware[] arrayOfFirmware = new Firmware[3];
      arrayOfFirmware[0] = ARDU_PLANE;
      arrayOfFirmware[1] = ARDU_COPTER;
      arrayOfFirmware[2] = APM_ROVER;
    }

    private Firmware(String paramString)
    {
      this.label = paramString;
    }

    public String getLabel()
    {
      return this.label;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Type
 * JD-Core Version:    0.6.2
 */