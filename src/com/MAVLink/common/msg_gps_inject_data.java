package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gps_inject_data extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GPS_INJECT_DATA = 123;
  public static final int MAVLINK_MSG_LENGTH = 113;
  private static final long serialVersionUID = 123L;
  public byte[] data = new byte[110];
  public byte len;
  public byte target_component;
  public byte target_system;

  public msg_gps_inject_data()
  {
    this.msgid = 123;
  }

  public msg_gps_inject_data(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 123;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 113;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 123;
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.len);
    for (int i = 0; i < this.data.length; i++)
      localMAVLinkPacket.payload.putByte(this.data[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GPS_INJECT_DATA - target_system:" + this.target_system + " target_component:" + this.target_component + " len:" + this.len + " data:" + this.data + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.len = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.data.length; i++)
      this.data[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_gps_inject_data
 * JD-Core Version:    0.6.2
 */