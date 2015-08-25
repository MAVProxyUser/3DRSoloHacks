package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class SetServo extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<SetServo> CREATOR = new Parcelable.Creator()
  {
    public SetServo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new SetServo(paramAnonymousParcel, null);
    }

    public SetServo[] newArray(int paramAnonymousInt)
    {
      return new SetServo[paramAnonymousInt];
    }
  };
  private int channel;
  private int pwm;

  public SetServo()
  {
    super(MissionItemType.SET_SERVO);
  }

  private SetServo(Parcel paramParcel)
  {
    super(paramParcel);
    this.pwm = paramParcel.readInt();
    this.channel = paramParcel.readInt();
  }

  public SetServo(SetServo paramSetServo)
  {
    this();
    this.pwm = paramSetServo.pwm;
    this.channel = paramSetServo.channel;
  }

  public MissionItem clone()
  {
    return new SetServo(this);
  }

  public int getChannel()
  {
    return this.channel;
  }

  public int getPwm()
  {
    return this.pwm;
  }

  public void setChannel(int paramInt)
  {
    this.channel = paramInt;
  }

  public void setPwm(int paramInt)
  {
    this.pwm = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeInt(this.pwm);
    paramParcel.writeInt(this.channel);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.SetServo
 * JD-Core Version:    0.6.2
 */