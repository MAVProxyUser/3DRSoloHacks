package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hil_state extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIL_STATE = 90;
  public static final int MAVLINK_MSG_LENGTH = 56;
  private static final long serialVersionUID = 90L;
  public int alt;
  public int lat;
  public int lon;
  public float pitch;
  public float pitchspeed;
  public float roll;
  public float rollspeed;
  public long time_usec;
  public short vx;
  public short vy;
  public short vz;
  public short xacc;
  public short yacc;
  public float yaw;
  public float yawspeed;
  public short zacc;

  public msg_hil_state()
  {
    this.msgid = 90;
  }

  public msg_hil_state(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 90;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 56;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 90;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.rollspeed);
    localMAVLinkPacket.payload.putFloat(this.pitchspeed);
    localMAVLinkPacket.payload.putFloat(this.yawspeed);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putInt(this.alt);
    localMAVLinkPacket.payload.putShort(this.vx);
    localMAVLinkPacket.payload.putShort(this.vy);
    localMAVLinkPacket.payload.putShort(this.vz);
    localMAVLinkPacket.payload.putShort(this.xacc);
    localMAVLinkPacket.payload.putShort(this.yacc);
    localMAVLinkPacket.payload.putShort(this.zacc);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIL_STATE - time_usec:" + this.time_usec + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " rollspeed:" + this.rollspeed + " pitchspeed:" + this.pitchspeed + " yawspeed:" + this.yawspeed + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " xacc:" + this.xacc + " yacc:" + this.yacc + " zacc:" + this.zacc + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.rollspeed = paramMAVLinkPayload.getFloat();
    this.pitchspeed = paramMAVLinkPayload.getFloat();
    this.yawspeed = paramMAVLinkPayload.getFloat();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.alt = paramMAVLinkPayload.getInt();
    this.vx = paramMAVLinkPayload.getShort();
    this.vy = paramMAVLinkPayload.getShort();
    this.vz = paramMAVLinkPayload.getShort();
    this.xacc = paramMAVLinkPayload.getShort();
    this.yacc = paramMAVLinkPayload.getShort();
    this.zacc = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_hil_state
 * JD-Core Version:    0.6.2
 */