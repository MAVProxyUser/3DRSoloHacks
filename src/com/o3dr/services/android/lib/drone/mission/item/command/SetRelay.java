package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class SetRelay extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<SetRelay> CREATOR = new Parcelable.Creator()
  {
    public SetRelay createFromParcel(Parcel paramAnonymousParcel)
    {
      return new SetRelay(paramAnonymousParcel, null);
    }

    public SetRelay[] newArray(int paramAnonymousInt)
    {
      return new SetRelay[paramAnonymousInt];
    }
  };
  private boolean enabled;
  private int relayNumber;

  public SetRelay()
  {
    super(MissionItemType.SET_RELAY);
  }

  private SetRelay(Parcel paramParcel)
  {
    super(paramParcel);
    this.relayNumber = paramParcel.readInt();
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.enabled = bool;
      return;
    }
  }

  public SetRelay(SetRelay paramSetRelay)
  {
    this();
    this.relayNumber = paramSetRelay.relayNumber;
    this.enabled = paramSetRelay.enabled;
  }

  public MissionItem clone()
  {
    return new SetRelay(this);
  }

  public int getRelayNumber()
  {
    return this.relayNumber;
  }

  public boolean isEnabled()
  {
    return this.enabled;
  }

  public void setEnabled(boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }

  public void setRelayNumber(int paramInt)
  {
    this.relayNumber = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeInt(this.relayNumber);
    if (this.enabled);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.SetRelay
 * JD-Core Version:    0.6.2
 */