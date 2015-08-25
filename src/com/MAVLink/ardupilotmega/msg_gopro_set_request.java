package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gopro_set_request extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GOPRO_SET_REQUEST = 218;
  public static final int MAVLINK_MSG_LENGTH = 4;
  private static final long serialVersionUID = 218L;
  public byte cmd_id;
  public byte target_component;
  public byte target_system;
  public byte value;

  public msg_gopro_set_request()
  {
    this.msgid = 218;
  }

  public msg_gopro_set_request(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 218;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 4;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 218;
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.cmd_id);
    localMAVLinkPacket.payload.putByte(this.value);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GOPRO_SET_REQUEST - target_system:" + this.target_system + " target_component:" + this.target_component + " cmd_id:" + this.cmd_id + " value:" + this.value + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.cmd_id = paramMAVLinkPayload.getByte();
    this.value = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gopro_set_request
 * JD-Core Version:    0.6.2
 */