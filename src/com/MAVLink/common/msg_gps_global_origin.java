package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gps_global_origin extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GPS_GLOBAL_ORIGIN = 49;
  public static final int MAVLINK_MSG_LENGTH = 12;
  private static final long serialVersionUID = 49L;
  public int altitude;
  public int latitude;
  public int longitude;

  public msg_gps_global_origin()
  {
    this.msgid = 49;
  }

  public msg_gps_global_origin(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 49;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 12;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 49;
    localMAVLinkPacket.payload.putInt(this.latitude);
    localMAVLinkPacket.payload.putInt(this.longitude);
    localMAVLinkPacket.payload.putInt(this.altitude);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GPS_GLOBAL_ORIGIN - latitude:" + this.latitude + " longitude:" + this.longitude + " altitude:" + this.altitude + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.latitude = paramMAVLinkPayload.getInt();
    this.longitude = paramMAVLinkPayload.getInt();
    this.altitude = paramMAVLinkPayload.getInt();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_gps_global_origin
 * JD-Core Version:    0.6.2
 */