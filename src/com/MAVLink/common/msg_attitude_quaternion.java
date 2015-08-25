package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_attitude_quaternion extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_ATTITUDE_QUATERNION = 31;
  public static final int MAVLINK_MSG_LENGTH = 32;
  private static final long serialVersionUID = 31L;
  public float pitchspeed;
  public float q1;
  public float q2;
  public float q3;
  public float q4;
  public float rollspeed;
  public int time_boot_ms;
  public float yawspeed;

  public msg_attitude_quaternion()
  {
    this.msgid = 31;
  }

  public msg_attitude_quaternion(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 31;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 32;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 31;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.q1);
    localMAVLinkPacket.payload.putFloat(this.q2);
    localMAVLinkPacket.payload.putFloat(this.q3);
    localMAVLinkPacket.payload.putFloat(this.q4);
    localMAVLinkPacket.payload.putFloat(this.rollspeed);
    localMAVLinkPacket.payload.putFloat(this.pitchspeed);
    localMAVLinkPacket.payload.putFloat(this.yawspeed);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_ATTITUDE_QUATERNION - time_boot_ms:" + this.time_boot_ms + " q1:" + this.q1 + " q2:" + this.q2 + " q3:" + this.q3 + " q4:" + this.q4 + " rollspeed:" + this.rollspeed + " pitchspeed:" + this.pitchspeed + " yawspeed:" + this.yawspeed + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.q1 = paramMAVLinkPayload.getFloat();
    this.q2 = paramMAVLinkPayload.getFloat();
    this.q3 = paramMAVLinkPayload.getFloat();
    this.q4 = paramMAVLinkPayload.getFloat();
    this.rollspeed = paramMAVLinkPayload.getFloat();
    this.pitchspeed = paramMAVLinkPayload.getFloat();
    this.yawspeed = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_attitude_quaternion
 * JD-Core Version:    0.6.2
 */