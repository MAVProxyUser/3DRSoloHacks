package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_ekf_status_report extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_EKF_STATUS_REPORT = 193;
  public static final int MAVLINK_MSG_LENGTH = 22;
  private static final long serialVersionUID = 193L;
  public float compass_variance;
  public short flags;
  public float pos_horiz_variance;
  public float pos_vert_variance;
  public float terrain_alt_variance;
  public float velocity_variance;

  public msg_ekf_status_report()
  {
    this.msgid = 193;
  }

  public msg_ekf_status_report(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 193;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 22;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 193;
    localMAVLinkPacket.payload.putFloat(this.velocity_variance);
    localMAVLinkPacket.payload.putFloat(this.pos_horiz_variance);
    localMAVLinkPacket.payload.putFloat(this.pos_vert_variance);
    localMAVLinkPacket.payload.putFloat(this.compass_variance);
    localMAVLinkPacket.payload.putFloat(this.terrain_alt_variance);
    localMAVLinkPacket.payload.putShort(this.flags);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_EKF_STATUS_REPORT - velocity_variance:" + this.velocity_variance + " pos_horiz_variance:" + this.pos_horiz_variance + " pos_vert_variance:" + this.pos_vert_variance + " compass_variance:" + this.compass_variance + " terrain_alt_variance:" + this.terrain_alt_variance + " flags:" + this.flags + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.velocity_variance = paramMAVLinkPayload.getFloat();
    this.pos_horiz_variance = paramMAVLinkPayload.getFloat();
    this.pos_vert_variance = paramMAVLinkPayload.getFloat();
    this.compass_variance = paramMAVLinkPayload.getFloat();
    this.terrain_alt_variance = paramMAVLinkPayload.getFloat();
    this.flags = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_ekf_status_report
 * JD-Core Version:    0.6.2
 */