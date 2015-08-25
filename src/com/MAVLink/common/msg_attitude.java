package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_attitude extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_ATTITUDE = 30;
  public static final int MAVLINK_MSG_LENGTH = 28;
  private static final long serialVersionUID = 30L;
  public float pitch;
  public float pitchspeed;
  public float roll;
  public float rollspeed;
  public int time_boot_ms;
  public float yaw;
  public float yawspeed;

  public msg_attitude()
  {
    this.msgid = 30;
  }

  public msg_attitude(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 30;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 28;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 30;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.rollspeed);
    localMAVLinkPacket.payload.putFloat(this.pitchspeed);
    localMAVLinkPacket.payload.putFloat(this.yawspeed);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_ATTITUDE - time_boot_ms:" + this.time_boot_ms + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " rollspeed:" + this.rollspeed + " pitchspeed:" + this.pitchspeed + " yawspeed:" + this.yawspeed + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.rollspeed = paramMAVLinkPayload.getFloat();
    this.pitchspeed = paramMAVLinkPayload.getFloat();
    this.yawspeed = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_attitude
 * JD-Core Version:    0.6.2
 */