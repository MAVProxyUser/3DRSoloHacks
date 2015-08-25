package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_log_request_list extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOG_REQUEST_LIST = 117;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 117L;
  public short end;
  public short start;
  public byte target_component;
  public byte target_system;

  public msg_log_request_list()
  {
    this.msgid = 117;
  }

  public msg_log_request_list(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 117;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 117;
    localMAVLinkPacket.payload.putShort(this.start);
    localMAVLinkPacket.payload.putShort(this.end);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOG_REQUEST_LIST - start:" + this.start + " end:" + this.end + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.start = paramMAVLinkPayload.getShort();
    this.end = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_log_request_list
 * JD-Core Version:    0.6.2
 */