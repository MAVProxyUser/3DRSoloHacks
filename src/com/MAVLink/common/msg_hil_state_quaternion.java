package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hil_state_quaternion extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIL_STATE_QUATERNION = 115;
  public static final int MAVLINK_MSG_LENGTH = 64;
  private static final long serialVersionUID = 115L;
  public int alt;
  public float[] attitude_quaternion = new float[4];
  public short ind_airspeed;
  public int lat;
  public int lon;
  public float pitchspeed;
  public float rollspeed;
  public long time_usec;
  public short true_airspeed;
  public short vx;
  public short vy;
  public short vz;
  public short xacc;
  public short yacc;
  public float yawspeed;
  public short zacc;

  public msg_hil_state_quaternion()
  {
    this.msgid = 115;
  }

  public msg_hil_state_quaternion(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 115;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 64;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 115;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    for (int i = 0; i < this.attitude_quaternion.length; i++)
      localMAVLinkPacket.payload.putFloat(this.attitude_quaternion[i]);
    localMAVLinkPacket.payload.putFloat(this.rollspeed);
    localMAVLinkPacket.payload.putFloat(this.pitchspeed);
    localMAVLinkPacket.payload.putFloat(this.yawspeed);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putInt(this.alt);
    localMAVLinkPacket.payload.putShort(this.vx);
    localMAVLinkPacket.payload.putShort(this.vy);
    localMAVLinkPacket.payload.putShort(this.vz);
    localMAVLinkPacket.payload.putShort(this.ind_airspeed);
    localMAVLinkPacket.payload.putShort(this.true_airspeed);
    localMAVLinkPacket.payload.putShort(this.xacc);
    localMAVLinkPacket.payload.putShort(this.yacc);
    localMAVLinkPacket.payload.putShort(this.zacc);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIL_STATE_QUATERNION - time_usec:" + this.time_usec + " attitude_quaternion:" + this.attitude_quaternion + " rollspeed:" + this.rollspeed + " pitchspeed:" + this.pitchspeed + " yawspeed:" + this.yawspeed + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " ind_airspeed:" + this.ind_airspeed + " true_airspeed:" + this.true_airspeed + " xacc:" + this.xacc + " yacc:" + this.yacc + " zacc:" + this.zacc + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    for (int i = 0; i < this.attitude_quaternion.length; i++)
      this.attitude_quaternion[i] = paramMAVLinkPayload.getFloat();
    this.rollspeed = paramMAVLinkPayload.getFloat();
    this.pitchspeed = paramMAVLinkPayload.getFloat();
    this.yawspeed = paramMAVLinkPayload.getFloat();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getInt();
    this.vx = paramMAVLinkPayload.getShort();
    this.vy = paramMAVLinkPayload.getShort();
    this.vz = paramMAVLinkPayload.getShort();
    this.ind_airspeed = paramMAVLinkPayload.getShort();
    this.true_airspeed = paramMAVLinkPayload.getShort();
    this.xacc = paramMAVLinkPayload.getShort();
    this.yacc = paramMAVLinkPayload.getShort();
    this.zacc = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_hil_state_quaternion
 * JD-Core Version:    0.6.2
 */