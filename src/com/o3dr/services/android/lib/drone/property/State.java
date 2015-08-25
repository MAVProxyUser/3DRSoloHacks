package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class State
  implements Parcelable
{
  public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator()
  {
    public State createFromParcel(Parcel paramAnonymousParcel)
    {
      return new State(paramAnonymousParcel, null);
    }

    public State[] newArray(int paramAnonymousInt)
    {
      return new State[paramAnonymousInt];
    }
  };
  public static final int INVALID_MAVLINK_VERSION = -1;
  private boolean armed;
  private String autopilotErrorId;
  private String calibrationStatus;
  private EkfStatus ekfStatus = new EkfStatus();
  private long flightStartTime;
  private boolean isConnected;
  private boolean isFlying;
  private boolean isTelemetryLive;
  private int mavlinkVersion = -1;
  private VehicleMode vehicleMode = VehicleMode.UNKNOWN;

  public State()
  {
  }

  private State(Parcel paramParcel)
  {
    boolean bool2;
    boolean bool3;
    label53: boolean bool4;
    if (paramParcel.readByte() != 0)
    {
      bool2 = bool1;
      this.isConnected = bool2;
      if (paramParcel.readByte() == 0)
        break label157;
      bool3 = bool1;
      this.armed = bool3;
      if (paramParcel.readByte() == 0)
        break label163;
      bool4 = bool1;
      label69: this.isFlying = bool4;
      this.calibrationStatus = paramParcel.readString();
      this.vehicleMode = ((VehicleMode)paramParcel.readParcelable(VehicleMode.class.getClassLoader()));
      this.autopilotErrorId = paramParcel.readString();
      this.mavlinkVersion = paramParcel.readInt();
      this.flightStartTime = paramParcel.readLong();
      this.ekfStatus = ((EkfStatus)paramParcel.readParcelable(EkfStatus.class.getClassLoader()));
      if (paramParcel.readByte() == 0)
        break label169;
    }
    while (true)
    {
      this.isTelemetryLive = bool1;
      return;
      bool2 = false;
      break;
      label157: bool3 = false;
      break label53;
      label163: bool4 = false;
      break label69;
      label169: bool1 = false;
    }
  }

  public State(boolean paramBoolean1, VehicleMode paramVehicleMode, boolean paramBoolean2, boolean paramBoolean3, String paramString1, int paramInt, String paramString2, long paramLong, EkfStatus paramEkfStatus, boolean paramBoolean4)
  {
    this.isConnected = paramBoolean1;
    this.armed = paramBoolean2;
    this.isFlying = paramBoolean3;
    this.flightStartTime = paramLong;
    this.autopilotErrorId = paramString1;
    this.mavlinkVersion = paramInt;
    this.calibrationStatus = paramString2;
    if (paramEkfStatus != null)
      this.ekfStatus = paramEkfStatus;
    if (paramVehicleMode != null)
      this.vehicleMode = paramVehicleMode;
    this.isTelemetryLive = paramBoolean4;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getAutopilotErrorId()
  {
    return this.autopilotErrorId;
  }

  public String getCalibrationStatus()
  {
    return this.calibrationStatus;
  }

  public EkfStatus getEkfStatus()
  {
    return this.ekfStatus;
  }

  public long getFlightStartTime()
  {
    return this.flightStartTime;
  }

  public int getMavlinkVersion()
  {
    return this.mavlinkVersion;
  }

  public VehicleMode getVehicleMode()
  {
    return this.vehicleMode;
  }

  public boolean isArmed()
  {
    return this.armed;
  }

  public boolean isCalibrating()
  {
    return this.calibrationStatus != null;
  }

  public boolean isConnected()
  {
    return this.isConnected;
  }

  public boolean isFlying()
  {
    return this.isFlying;
  }

  public boolean isTelemetryLive()
  {
    return this.isTelemetryLive;
  }

  public boolean isWarning()
  {
    return TextUtils.isEmpty(this.autopilotErrorId);
  }

  public void setArmed(boolean paramBoolean)
  {
    this.armed = paramBoolean;
  }

  public void setAutopilotErrorId(String paramString)
  {
    this.autopilotErrorId = paramString;
  }

  public void setCalibration(String paramString)
  {
    this.calibrationStatus = paramString;
  }

  public void setCalibrationStatus(String paramString)
  {
    this.calibrationStatus = paramString;
  }

  public void setConnected(boolean paramBoolean)
  {
    this.isConnected = paramBoolean;
  }

  public void setFlightStartTime(long paramLong)
  {
    this.flightStartTime = paramLong;
  }

  public void setFlying(boolean paramBoolean)
  {
    this.isFlying = paramBoolean;
  }

  public void setIsTelemetryLive(boolean paramBoolean)
  {
    this.isTelemetryLive = paramBoolean;
  }

  public void setMavlinkVersion(int paramInt)
  {
    this.mavlinkVersion = paramInt;
  }

  public void setVehicleMode(VehicleMode paramVehicleMode)
  {
    this.vehicleMode = paramVehicleMode;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    byte b1 = 1;
    byte b2;
    byte b3;
    label28: byte b4;
    if (this.isConnected)
    {
      b2 = b1;
      paramParcel.writeByte(b2);
      if (!this.armed)
        break label119;
      b3 = b1;
      paramParcel.writeByte(b3);
      if (!this.isFlying)
        break label125;
      b4 = b1;
      label44: paramParcel.writeByte(b4);
      paramParcel.writeString(this.calibrationStatus);
      paramParcel.writeParcelable(this.vehicleMode, 0);
      paramParcel.writeString(this.autopilotErrorId);
      paramParcel.writeInt(this.mavlinkVersion);
      paramParcel.writeLong(this.flightStartTime);
      paramParcel.writeParcelable(this.ekfStatus, 0);
      if (!this.isTelemetryLive)
        break label131;
    }
    while (true)
    {
      paramParcel.writeByte(b1);
      return;
      b2 = 0;
      break;
      label119: b3 = 0;
      break label28;
      label125: b4 = 0;
      break label44;
      label131: b1 = 0;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.State
 * JD-Core Version:    0.6.2
 */