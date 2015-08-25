package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_camera_feedback extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_CAMERA_FEEDBACK = 180;
  public static final int MAVLINK_MSG_LENGTH = 45;
  private static final long serialVersionUID = 180L;
  public float alt_msl;
  public float alt_rel;
  public byte cam_idx;
  public byte flags;
  public float foc_len;
  public short img_idx;
  public int lat;
  public int lng;
  public float pitch;
  public float roll;
  public byte target_system;
  public long time_usec;
  public float yaw;

  public msg_camera_feedback()
  {
    this.msgid = 180;
  }

  public msg_camera_feedback(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 180;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 45;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 180;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lng);
    localMAVLinkPacket.payload.putFloat(this.alt_msl);
    localMAVLinkPacket.payload.putFloat(this.alt_rel);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.foc_len);
    localMAVLinkPacket.payload.putShort(this.img_idx);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.cam_idx);
    localMAVLinkPacket.payload.putByte(this.flags);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_CAMERA_FEEDBACK - time_usec:" + this.time_usec + " lat:" + this.lat + " lng:" + this.lng + " alt_msl:" + this.alt_msl + " alt_rel:" + this.alt_rel + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " foc_len:" + this.foc_len + " img_idx:" + this.img_idx + " target_system:" + this.target_system + " cam_idx:" + this.cam_idx + " flags:" + this.flags + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.lat = paramMAVLinkPayload.getInt();
    this.lng = paramMAVLinkPayload.getInt();
    this.alt_msl = paramMAVLinkPayload.getFloat();
    this.alt_rel = paramMAVLinkPayload.getFloat();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.foc_len = paramMAVLinkPayload.getFloat();
    this.img_idx = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.cam_idx = paramMAVLinkPayload.getByte();
    this.flags = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_camera_feedback
 * JD-Core Version:    0.6.2
 */