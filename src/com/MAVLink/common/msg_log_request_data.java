package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_log_request_data extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOG_REQUEST_DATA = 119;
  public static final int MAVLINK_MSG_LENGTH = 12;
  private static final long serialVersionUID = 119L;
  public int count;
  public short id;
  public int ofs;
  public byte target_component;
  public byte target_system;

  public msg_log_request_data()
  {
    this.msgid = 119;
  }

  public msg_log_request_data(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 119;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 12;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 119;
    localMAVLinkPacket.payload.putInt(this.ofs);
    localMAVLinkPacket.payload.putInt(this.count);
    localMAVLinkPacket.payload.putShort(this.id);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOG_REQUEST_DATA - ofs:" + this.ofs + " count:" + this.count + " id:" + this.id + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.ofs = paramMAVLinkPayload.getInt();
    this.count = paramMAVLinkPayload.getInt();
    this.id = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_log_request_data
 * JD-Core Version:    0.6.2
 */