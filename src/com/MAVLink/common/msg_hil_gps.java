package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hil_gps extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIL_GPS = 113;
  public static final int MAVLINK_MSG_LENGTH = 36;
  private static final long serialVersionUID = 113L;
  public int alt;
  public short cog;
  public short eph;
  public short epv;
  public byte fix_type;
  public int lat;
  public int lon;
  public byte satellites_visible;
  public long time_usec;
  public short vd;
  public short ve;
  public short vel;
  public short vn;

  public msg_hil_gps()
  {
    this.msgid = 113;
  }

  public msg_hil_gps(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 113;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 36;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 113;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putInt(this.alt);
    localMAVLinkPacket.payload.putShort(this.eph);
    localMAVLinkPacket.payload.putShort(this.epv);
    localMAVLinkPacket.payload.putShort(this.vel);
    localMAVLinkPacket.payload.putShort(this.vn);
    localMAVLinkPacket.payload.putShort(this.ve);
    localMAVLinkPacket.payload.putShort(this.vd);
    localMAVLinkPacket.payload.putShort(this.cog);
    localMAVLinkPacket.payload.putByte(this.fix_type);
    localMAVLinkPacket.payload.putByte(this.satellites_visible);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIL_GPS - time_usec:" + this.time_usec + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " eph:" + this.eph + " epv:" + this.epv + " vel:" + this.vel + " vn:" + this.vn + " ve:" + this.ve + " vd:" + this.vd + " cog:" + this.cog + " fix_type:" + this.fix_type + " satellites_visible:" + this.satellites_visible + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getInt();
    this.eph = paramMAVLinkPayload.getShort();
    this.epv = paramMAVLinkPayload.getShort();
    this.vel = paramMAVLinkPayload.getShort();
    this.vn = paramMAVLinkPayload.getShort();
    this.ve = paramMAVLinkPayload.getShort();
    this.vd = paramMAVLinkPayload.getShort();
    this.cog = paramMAVLinkPayload.getShort();
    this.fix_type = paramMAVLinkPayload.getByte();
    this.satellites_visible = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_hil_gps
 * JD-Core Version:    0.6.2
 */