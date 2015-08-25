package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_attitude_quaternion_cov extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_ATTITUDE_QUATERNION_COV = 61;
  public static final int MAVLINK_MSG_LENGTH = 68;
  private static final long serialVersionUID = 61L;
  public float[] covariance = new float[9];
  public float pitchspeed;
  public float[] q = new float[4];
  public float rollspeed;
  public int time_boot_ms;
  public float yawspeed;

  public msg_attitude_quaternion_cov()
  {
    this.msgid = 61;
  }

  public msg_attitude_quaternion_cov(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 61;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 68;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 61;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    for (int i = 0; i < this.q.length; i++)
      localMAVLinkPacket.payload.putFloat(this.q[i]);
    localMAVLinkPacket.payload.putFloat(this.rollspeed);
    localMAVLinkPacket.payload.putFloat(this.pitchspeed);
    localMAVLinkPacket.payload.putFloat(this.yawspeed);
    for (int j = 0; j < this.covariance.length; j++)
      localMAVLinkPacket.payload.putFloat(this.covariance[j]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_ATTITUDE_QUATERNION_COV - time_boot_ms:" + this.time_boot_ms + " q:" + this.q + " rollspeed:" + this.rollspeed + " pitchspeed:" + this.pitchspeed + " yawspeed:" + this.yawspeed + " covariance:" + this.covariance + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    for (int i = 0; i < this.q.length; i++)
      this.q[i] = paramMAVLinkPayload.getFloat();
    this.rollspeed = paramMAVLinkPayload.getFloat();
    this.pitchspeed = paramMAVLinkPayload.getFloat();
    this.yawspeed = paramMAVLinkPayload.getFloat();
    for (int j = 0; j < this.covariance.length; j++)
      this.covariance[j] = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_attitude_quaternion_cov
 * JD-Core Version:    0.6.2
 */