package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_power_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_POWER_STATUS = 125;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 125L;
  public short Vcc;
  public short Vservo;
  public short flags;

  public msg_power_status()
  {
    this.msgid = 125;
  }

  public msg_power_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 125;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 125;
    localMAVLinkPacket.payload.putShort(this.Vcc);
    localMAVLinkPacket.payload.putShort(this.Vservo);
    localMAVLinkPacket.payload.putShort(this.flags);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_POWER_STATUS - Vcc:" + this.Vcc + " Vservo:" + this.Vservo + " flags:" + this.flags + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.Vcc = paramMAVLinkPayload.getShort();
    this.Vservo = paramMAVLinkPayload.getShort();
    this.flags = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_power_status
 * JD-Core Version:    0.6.2
 */