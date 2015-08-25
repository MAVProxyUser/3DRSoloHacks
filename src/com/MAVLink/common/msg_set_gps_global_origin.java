package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_set_gps_global_origin extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SET_GPS_GLOBAL_ORIGIN = 48;
  public static final int MAVLINK_MSG_LENGTH = 13;
  private static final long serialVersionUID = 48L;
  public int altitude;
  public int latitude;
  public int longitude;
  public byte target_system;

  public msg_set_gps_global_origin()
  {
    this.msgid = 48;
  }

  public msg_set_gps_global_origin(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 48;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 13;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 48;
    localMAVLinkPacket.payload.putInt(this.latitude);
    localMAVLinkPacket.payload.putInt(this.longitude);
    localMAVLinkPacket.payload.putInt(this.altitude);
    localMAVLinkPacket.payload.putByte(this.target_system);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SET_GPS_GLOBAL_ORIGIN - latitude:" + this.latitude + " longitude:" + this.longitude + " altitude:" + this.altitude + " target_system:" + this.target_system + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.latitude = paramMAVLinkPayload.getInt();
    this.longitude = paramMAVLinkPayload.getInt();
    this.altitude = paramMAVLinkPayload.getInt();
    this.target_system = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_set_gps_global_origin
 * JD-Core Version:    0.6.2
 */