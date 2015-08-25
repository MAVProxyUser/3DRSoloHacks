package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mission_request_partial_list extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MISSION_REQUEST_PARTIAL_LIST = 37;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 37L;
  public short end_index;
  public short start_index;
  public byte target_component;
  public byte target_system;

  public msg_mission_request_partial_list()
  {
    this.msgid = 37;
  }

  public msg_mission_request_partial_list(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 37;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 37;
    localMAVLinkPacket.payload.putShort(this.start_index);
    localMAVLinkPacket.payload.putShort(this.end_index);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MISSION_REQUEST_PARTIAL_LIST - start_index:" + this.start_index + " end_index:" + this.end_index + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.start_index = paramMAVLinkPayload.getShort();
    this.end_index = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_mission_request_partial_list
 * JD-Core Version:    0.6.2
 */