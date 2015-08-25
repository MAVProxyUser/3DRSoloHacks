package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_rally_point extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RALLY_POINT = 175;
  public static final int MAVLINK_MSG_LENGTH = 19;
  private static final long serialVersionUID = 175L;
  public short alt;
  public short break_alt;
  public byte count;
  public byte flags;
  public byte idx;
  public short land_dir;
  public int lat;
  public int lng;
  public byte target_component;
  public byte target_system;

  public msg_rally_point()
  {
    this.msgid = 175;
  }

  public msg_rally_point(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 175;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 19;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 175;
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lng);
    localMAVLinkPacket.payload.putShort(this.alt);
    localMAVLinkPacket.payload.putShort(this.break_alt);
    localMAVLinkPacket.payload.putShort(this.land_dir);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.idx);
    localMAVLinkPacket.payload.putByte(this.count);
    localMAVLinkPacket.payload.putByte(this.flags);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RALLY_POINT - lat:" + this.lat + " lng:" + this.lng + " alt:" + this.alt + " break_alt:" + this.break_alt + " land_dir:" + this.land_dir + " target_system:" + this.target_system + " target_component:" + this.target_component + " idx:" + this.idx + " count:" + this.count + " flags:" + this.flags + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.lat = paramMAVLinkPayload.getInt();
    this.lng = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getShort();
    this.break_alt = paramMAVLinkPayload.getShort();
    this.land_dir = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.idx = paramMAVLinkPayload.getByte();
    this.count = paramMAVLinkPayload.getByte();
    this.flags = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_rally_point
 * JD-Core Version:    0.6.2
 */