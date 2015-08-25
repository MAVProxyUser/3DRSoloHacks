package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_camera_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_CAMERA_STATUS = 179;
  public static final int MAVLINK_MSG_LENGTH = 29;
  private static final long serialVersionUID = 179L;
  public byte cam_idx;
  public byte event_id;
  public short img_idx;
  public float p1;
  public float p2;
  public float p3;
  public float p4;
  public byte target_system;
  public long time_usec;

  public msg_camera_status()
  {
    this.msgid = 179;
  }

  public msg_camera_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 179;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 29;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 179;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.p1);
    localMAVLinkPacket.payload.putFloat(this.p2);
    localMAVLinkPacket.payload.putFloat(this.p3);
    localMAVLinkPacket.payload.putFloat(this.p4);
    localMAVLinkPacket.payload.putShort(this.img_idx);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.cam_idx);
    localMAVLinkPacket.payload.putByte(this.event_id);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_CAMERA_STATUS - time_usec:" + this.time_usec + " p1:" + this.p1 + " p2:" + this.p2 + " p3:" + this.p3 + " p4:" + this.p4 + " img_idx:" + this.img_idx + " target_system:" + this.target_system + " cam_idx:" + this.cam_idx + " event_id:" + this.event_id + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.p1 = paramMAVLinkPayload.getFloat();
    this.p2 = paramMAVLinkPayload.getFloat();
    this.p3 = paramMAVLinkPayload.getFloat();
    this.p4 = paramMAVLinkPayload.getFloat();
    this.img_idx = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.cam_idx = paramMAVLinkPayload.getByte();
    this.event_id = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_camera_status
 * JD-Core Version:    0.6.2
 */