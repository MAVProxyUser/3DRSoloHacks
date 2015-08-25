package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_set_position_target_global_int extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SET_POSITION_TARGET_GLOBAL_INT = 86;
  public static final int MAVLINK_MSG_LENGTH = 53;
  private static final long serialVersionUID = 86L;
  public float afx;
  public float afy;
  public float afz;
  public float alt;
  public byte coordinate_frame;
  public int lat_int;
  public int lon_int;
  public byte target_component;
  public byte target_system;
  public int time_boot_ms;
  public short type_mask;
  public float vx;
  public float vy;
  public float vz;
  public float yaw;
  public float yaw_rate;

  public msg_set_position_target_global_int()
  {
    this.msgid = 86;
  }

  public msg_set_position_target_global_int(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 86;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 53;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 86;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putInt(this.lat_int);
    localMAVLinkPacket.payload.putInt(this.lon_int);
    localMAVLinkPacket.payload.putFloat(this.alt);
    localMAVLinkPacket.payload.putFloat(this.vx);
    localMAVLinkPacket.payload.putFloat(this.vy);
    localMAVLinkPacket.payload.putFloat(this.vz);
    localMAVLinkPacket.payload.putFloat(this.afx);
    localMAVLinkPacket.payload.putFloat(this.afy);
    localMAVLinkPacket.payload.putFloat(this.afz);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.yaw_rate);
    localMAVLinkPacket.payload.putShort(this.type_mask);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.coordinate_frame);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SET_POSITION_TARGET_GLOBAL_INT - time_boot_ms:" + this.time_boot_ms + " lat_int:" + this.lat_int + " lon_int:" + this.lon_int + " alt:" + this.alt + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " afx:" + this.afx + " afy:" + this.afy + " afz:" + this.afz + " yaw:" + this.yaw + " yaw_rate:" + this.yaw_rate + " type_mask:" + this.type_mask + " target_system:" + this.target_system + " target_component:" + this.target_component + " coordinate_frame:" + this.coordinate_frame + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.lat_int = paramMAVLinkPayload.getInt();
    this.lon_int = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getFloat();
    this.vx = paramMAVLinkPayload.getFloat();
    this.vy = paramMAVLinkPayload.getFloat();
    this.vz = paramMAVLinkPayload.getFloat();
    this.afx = paramMAVLinkPayload.getFloat();
    this.afy = paramMAVLinkPayload.getFloat();
    this.afz = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.yaw_rate = paramMAVLinkPayload.getFloat();
    this.type_mask = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.coordinate_frame = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_set_position_target_global_int
 * JD-Core Version:    0.6.2
 */