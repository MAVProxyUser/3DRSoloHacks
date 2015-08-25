package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_set_mode extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SET_MODE = 11;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 11L;
  public byte base_mode;
  public int custom_mode;
  public byte target_system;

  public msg_set_mode()
  {
    this.msgid = 11;
  }

  public msg_set_mode(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 11;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 11;
    localMAVLinkPacket.payload.putInt(this.custom_mode);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.base_mode);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SET_MODE - custom_mode:" + this.custom_mode + " target_system:" + this.target_system + " base_mode:" + this.base_mode + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.custom_mode = paramMAVLinkPayload.getInt();
    this.target_system = paramMAVLinkPayload.getByte();
    this.base_mode = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_set_mode
 * JD-Core Version:    0.6.2
 */