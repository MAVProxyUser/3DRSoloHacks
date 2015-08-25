package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_wind extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_WIND = 168;
  public static final int MAVLINK_MSG_LENGTH = 12;
  private static final long serialVersionUID = 168L;
  public float direction;
  public float speed;
  public float speed_z;

  public msg_wind()
  {
    this.msgid = 168;
  }

  public msg_wind(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 168;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 12;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 168;
    localMAVLinkPacket.payload.putFloat(this.direction);
    localMAVLinkPacket.payload.putFloat(this.speed);
    localMAVLinkPacket.payload.putFloat(this.speed_z);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_WIND - direction:" + this.direction + " speed:" + this.speed + " speed_z:" + this.speed_z + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.direction = paramMAVLinkPayload.getFloat();
    this.speed = paramMAVLinkPayload.getFloat();
    this.speed_z = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_wind
 * JD-Core Version:    0.6.2
 */