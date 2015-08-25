package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_attitude_target extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_ATTITUDE_TARGET = 83;
  public static final int MAVLINK_MSG_LENGTH = 37;
  private static final long serialVersionUID = 83L;
  public float body_pitch_rate;
  public float body_roll_rate;
  public float body_yaw_rate;
  public float[] q = new float[4];
  public float thrust;
  public int time_boot_ms;
  public byte type_mask;

  public msg_attitude_target()
  {
    this.msgid = 83;
  }

  public msg_attitude_target(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 83;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 37;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 83;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    for (int i = 0; i < this.q.length; i++)
      localMAVLinkPacket.payload.putFloat(this.q[i]);
    localMAVLinkPacket.payload.putFloat(this.body_roll_rate);
    localMAVLinkPacket.payload.putFloat(this.body_pitch_rate);
    localMAVLinkPacket.payload.putFloat(this.body_yaw_rate);
    localMAVLinkPacket.payload.putFloat(this.thrust);
    localMAVLinkPacket.payload.putByte(this.type_mask);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_ATTITUDE_TARGET - time_boot_ms:" + this.time_boot_ms + " q:" + this.q + " body_roll_rate:" + this.body_roll_rate + " body_pitch_rate:" + this.body_pitch_rate + " body_yaw_rate:" + this.body_yaw_rate + " thrust:" + this.thrust + " type_mask:" + this.type_mask + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    for (int i = 0; i < this.q.length; i++)
      this.q[i] = paramMAVLinkPayload.getFloat();
    this.body_roll_rate = paramMAVLinkPayload.getFloat();
    this.body_pitch_rate = paramMAVLinkPayload.getFloat();
    this.body_yaw_rate = paramMAVLinkPayload.getFloat();
    this.thrust = paramMAVLinkPayload.getFloat();
    this.type_mask = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_attitude_target
 * JD-Core Version:    0.6.2
 */