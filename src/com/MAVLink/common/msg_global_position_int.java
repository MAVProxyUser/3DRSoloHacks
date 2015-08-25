package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_global_position_int extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GLOBAL_POSITION_INT = 33;
  public static final int MAVLINK_MSG_LENGTH = 28;
  private static final long serialVersionUID = 33L;
  public int alt;
  public short hdg;
  public int lat;
  public int lon;
  public int relative_alt;
  public int time_boot_ms;
  public short vx;
  public short vy;
  public short vz;

  public msg_global_position_int()
  {
    this.msgid = 33;
  }

  public msg_global_position_int(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 33;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 28;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 33;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putInt(this.alt);
    localMAVLinkPacket.payload.putInt(this.relative_alt);
    localMAVLinkPacket.payload.putShort(this.vx);
    localMAVLinkPacket.payload.putShort(this.vy);
    localMAVLinkPacket.payload.putShort(this.vz);
    localMAVLinkPacket.payload.putShort(this.hdg);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GLOBAL_POSITION_INT - time_boot_ms:" + this.time_boot_ms + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " relative_alt:" + this.relative_alt + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " hdg:" + this.hdg + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getInt();
    this.relative_alt = paramMAVLinkPayload.getInt();
    this.vx = paramMAVLinkPayload.getShort();
    this.vy = paramMAVLinkPayload.getShort();
    this.vz = paramMAVLinkPayload.getShort();
    this.hdg = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_global_position_int
 * JD-Core Version:    0.6.2
 */