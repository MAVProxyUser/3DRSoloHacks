package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gps2_raw extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GPS2_RAW = 124;
  public static final int MAVLINK_MSG_LENGTH = 35;
  private static final long serialVersionUID = 124L;
  public int alt;
  public short cog;
  public int dgps_age;
  public byte dgps_numch;
  public short eph;
  public short epv;
  public byte fix_type;
  public int lat;
  public int lon;
  public byte satellites_visible;
  public long time_usec;
  public short vel;

  public msg_gps2_raw()
  {
    this.msgid = 124;
  }

  public msg_gps2_raw(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 124;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 35;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 124;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putInt(this.alt);
    localMAVLinkPacket.payload.putInt(this.dgps_age);
    localMAVLinkPacket.payload.putShort(this.eph);
    localMAVLinkPacket.payload.putShort(this.epv);
    localMAVLinkPacket.payload.putShort(this.vel);
    localMAVLinkPacket.payload.putShort(this.cog);
    localMAVLinkPacket.payload.putByte(this.fix_type);
    localMAVLinkPacket.payload.putByte(this.satellites_visible);
    localMAVLinkPacket.payload.putByte(this.dgps_numch);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GPS2_RAW - time_usec:" + this.time_usec + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " dgps_age:" + this.dgps_age + " eph:" + this.eph + " epv:" + this.epv + " vel:" + this.vel + " cog:" + this.cog + " fix_type:" + this.fix_type + " satellites_visible:" + this.satellites_visible + " dgps_numch:" + this.dgps_numch + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getInt();
    this.dgps_age = paramMAVLinkPayload.getInt();
    this.eph = paramMAVLinkPayload.getShort();
    this.epv = paramMAVLinkPayload.getShort();
    this.vel = paramMAVLinkPayload.getShort();
    this.cog = paramMAVLinkPayload.getShort();
    this.fix_type = paramMAVLinkPayload.getByte();
    this.satellites_visible = paramMAVLinkPayload.getByte();
    this.dgps_numch = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_gps2_raw
 * JD-Core Version:    0.6.2
 */