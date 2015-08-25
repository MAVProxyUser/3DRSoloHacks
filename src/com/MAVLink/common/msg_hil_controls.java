package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hil_controls extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIL_CONTROLS = 91;
  public static final int MAVLINK_MSG_LENGTH = 42;
  private static final long serialVersionUID = 91L;
  public float aux1;
  public float aux2;
  public float aux3;
  public float aux4;
  public byte mode;
  public byte nav_mode;
  public float pitch_elevator;
  public float roll_ailerons;
  public float throttle;
  public long time_usec;
  public float yaw_rudder;

  public msg_hil_controls()
  {
    this.msgid = 91;
  }

  public msg_hil_controls(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 91;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 42;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 91;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.roll_ailerons);
    localMAVLinkPacket.payload.putFloat(this.pitch_elevator);
    localMAVLinkPacket.payload.putFloat(this.yaw_rudder);
    localMAVLinkPacket.payload.putFloat(this.throttle);
    localMAVLinkPacket.payload.putFloat(this.aux1);
    localMAVLinkPacket.payload.putFloat(this.aux2);
    localMAVLinkPacket.payload.putFloat(this.aux3);
    localMAVLinkPacket.payload.putFloat(this.aux4);
    localMAVLinkPacket.payload.putByte(this.mode);
    localMAVLinkPacket.payload.putByte(this.nav_mode);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIL_CONTROLS - time_usec:" + this.time_usec + " roll_ailerons:" + this.roll_ailerons + " pitch_elevator:" + this.pitch_elevator + " yaw_rudder:" + this.yaw_rudder + " throttle:" + this.throttle + " aux1:" + this.aux1 + " aux2:" + this.aux2 + " aux3:" + this.aux3 + " aux4:" + this.aux4 + " mode:" + this.mode + " nav_mode:" + this.nav_mode + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.roll_ailerons = paramMAVLinkPayload.getFloat();
    this.pitch_elevator = paramMAVLinkPayload.getFloat();
    this.yaw_rudder = paramMAVLinkPayload.getFloat();
    this.throttle = paramMAVLinkPayload.getFloat();
    this.aux1 = paramMAVLinkPayload.getFloat();
    this.aux2 = paramMAVLinkPayload.getFloat();
    this.aux3 = paramMAVLinkPayload.getFloat();
    this.aux4 = paramMAVLinkPayload.getFloat();
    this.mode = paramMAVLinkPayload.getByte();
    this.nav_mode = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_hil_controls
 * JD-Core Version:    0.6.2
 */