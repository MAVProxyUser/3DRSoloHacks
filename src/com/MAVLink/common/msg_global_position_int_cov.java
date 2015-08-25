package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_global_position_int_cov extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GLOBAL_POSITION_INT_COV = 63;
  public static final int MAVLINK_MSG_LENGTH = 185;
  private static final long serialVersionUID = 63L;
  public int alt;
  public float[] covariance = new float[36];
  public byte estimator_type;
  public int lat;
  public int lon;
  public int relative_alt;
  public int time_boot_ms;
  public long time_utc;
  public float vx;
  public float vy;
  public float vz;

  public msg_global_position_int_cov()
  {
    this.msgid = 63;
  }

  public msg_global_position_int_cov(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 63;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 185;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 63;
    localMAVLinkPacket.payload.putLong(this.time_utc);
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putInt(this.alt);
    localMAVLinkPacket.payload.putInt(this.relative_alt);
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
    return "MAVLINK_MSG_ID_GLOBAL_POSITION_INT_COV - time_utc:" + this.time_utc + " time_boot_ms:" + this.time_boot_ms + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " relative_alt:" + this.relative_alt + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " covariance:" + this.covariance + " estimator_type:" + this.estimator_type + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_utc = paramMAVLinkPayload.getLong();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getInt();
    this.relative_alt = paramMAVLinkPayload.getInt();
    this.vx = paramMAVLinkPayload.getFloat();
    this.vy = paramMAVLinkPayload.getFloat();
    this.vz = paramMAVLinkPayload.getFloat();
    for (int i = 0; i < this.covariance.length; i++)
      this.covariance[i] = paramMAVLinkPayload.getFloat();
    this.estimator_type = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_global_position_int_cov
 * JD-Core Version:    0.6.2
 */