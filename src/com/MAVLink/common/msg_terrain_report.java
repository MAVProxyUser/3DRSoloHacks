package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_terrain_report extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_TERRAIN_REPORT = 136;
  public static final int MAVLINK_MSG_LENGTH = 22;
  private static final long serialVersionUID = 136L;
  public float current_height;
  public int lat;
  public short loaded;
  public int lon;
  public short pending;
  public short spacing;
  public float terrain_height;

  public msg_terrain_report()
  {
    this.msgid = 136;
  }

  public msg_terrain_report(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 136;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 22;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 136;
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putFloat(this.terrain_height);
    localMAVLinkPacket.payload.putFloat(this.current_height);
    localMAVLinkPacket.payload.putShort(this.spacing);
    localMAVLinkPacket.payload.putShort(this.pending);
    localMAVLinkPacket.payload.putShort(this.loaded);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_TERRAIN_REPORT - lat:" + this.lat + " lon:" + this.lon + " terrain_height:" + this.terrain_height + " current_height:" + this.current_height + " spacing:" + this.spacing + " pending:" + this.pending + " loaded:" + this.loaded + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.terrain_height = paramMAVLinkPayload.getFloat();
    this.current_height = paramMAVLinkPayload.getFloat();
    this.spacing = paramMAVLinkPayload.getShort();
    this.pending = paramMAVLinkPayload.getShort();
    this.loaded = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_terrain_report
 * JD-Core Version:    0.6.2
 */