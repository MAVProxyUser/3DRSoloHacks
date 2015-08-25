package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_local_position_ned_cov extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOCAL_POSITION_NED_COV = 64;
  public static final int MAVLINK_MSG_LENGTH = 181;
  private static final long serialVersionUID = 64L;
  public float[] covariance = new float[36];
  public byte estimator_type;
  public int time_boot_ms;
  public long time_utc;
  public float vx;
  public float vy;
  public float vz;
  public float x;
  public float y;
  public float z;

  public msg_local_position_ned_cov()
  {
    this.msgid = 64;
  }

  public msg_local_position_ned_cov(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 64;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 181;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 64;
    localMAVLinkPacket.payload.putLong(this.time_utc);
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    localMAVLinkPacket.payload.putFloat(this.vx);
    localMAVLinkPacket.payload.putFloat(this.vy);
    localMAVLinkPacket.payload.putFloat(this.vz);
    for (int i = 0; i < this.covariance.length; i++)
      localMAVLinkPacket.payload.putFloat(this.covariance[i]);
    localMAVLinkPacket.payload.putByte(this.estimator_type);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOCAL_POSITION_NED_COV - time_utc:" + this.time_utc + " time_boot_ms:" + this.time_boot_ms + " x:" + this.x + " y:" + this.y + " z:" + this.z + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " covariance:" + this.covariance + " estimator_type:" + this.estimator_type + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_utc = paramMAVLinkPayload.getLong();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
    this.vx = paramMAVLinkPayload.getFloat();
    this.vy = paramMAVLinkPayload.getFloat();
    this.vz = paramMAVLinkPayload.getFloat();
    for (int i = 0; i < this.covariance.length; i++)
      this.covariance[i] = paramMAVLinkPayload.getFloat();
    this.estimator_type = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_local_position_ned_cov
 * JD-Core Version:    0.6.2
 */