package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_terrain_request extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_TERRAIN_REQUEST = 133;
  public static final int MAVLINK_MSG_LENGTH = 18;
  private static final long serialVersionUID = 133L;
  public short grid_spacing;
  public int lat;
  public int lon;
  public long mask;

  public msg_terrain_request()
  {
    this.msgid = 133;
  }

  public msg_terrain_request(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 133;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 18;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 133;
    localMAVLinkPacket.payload.putLong(this.mask);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putShort(this.grid_spacing);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_TERRAIN_REQUEST - mask:" + this.mask + " lat:" + this.lat + " lon:" + this.lon + " grid_spacing:" + this.grid_spacing + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.mask = paramMAVLinkPayload.getLong();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.grid_spacing = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_terrain_request
 * JD-Core Version:    0.6.2
 */