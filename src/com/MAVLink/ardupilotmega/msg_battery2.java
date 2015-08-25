package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_battery2 extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_BATTERY2 = 181;
  public static final int MAVLINK_MSG_LENGTH = 4;
  private static final long serialVersionUID = 181L;
  public short current_battery;
  public short voltage;

  public msg_battery2()
  {
    this.msgid = 181;
  }

  public msg_battery2(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 181;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 4;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 181;
    localMAVLinkPacket.payload.putShort(this.voltage);
    localMAVLinkPacket.payload.putShort(this.current_battery);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_BATTERY2 - voltage:" + this.voltage + " current_battery:" + this.current_battery + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.voltage = paramMAVLinkPayload.getShort();
    this.current_battery = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_battery2
 * JD-Core Version:    0.6.2
 */