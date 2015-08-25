package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mission_request extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MISSION_REQUEST = 40;
  public static final int MAVLINK_MSG_LENGTH = 4;
  private static final long serialVersionUID = 40L;
  public short seq;
  public byte target_component;
  public byte target_system;

  public msg_mission_request()
  {
    this.msgid = 40;
  }

  public msg_mission_request(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 40;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 4;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 40;
    localMAVLinkPacket.payload.putShort(this.seq);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MISSION_REQUEST - seq:" + this.seq + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.seq = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_mission_request
 * JD-Core Version:    0.6.2
 */