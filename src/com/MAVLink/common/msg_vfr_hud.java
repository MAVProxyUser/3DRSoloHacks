package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_vfr_hud extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_VFR_HUD = 74;
  public static final int MAVLINK_MSG_LENGTH = 20;
  private static final long serialVersionUID = 74L;
  public float airspeed;
  public float alt;
  public float climb;
  public float groundspeed;
  public short heading;
  public short throttle;

  public msg_vfr_hud()
  {
    this.msgid = 74;
  }

  public msg_vfr_hud(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 74;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 20;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 74;
    localMAVLinkPacket.payload.putFloat(this.airspeed);
    localMAVLinkPacket.payload.putFloat(this.groundspeed);
    localMAVLinkPacket.payload.putFloat(this.alt);
    localMAVLinkPacket.payload.putFloat(this.climb);
    localMAVLinkPacket.payload.putShort(this.heading);
    localMAVLinkPacket.payload.putShort(this.throttle);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_VFR_HUD - airspeed:" + this.airspeed + " groundspeed:" + this.groundspeed + " alt:" + this.alt + " climb:" + this.climb + " heading:" + this.heading + " throttle:" + this.throttle + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.airspeed = paramMAVLinkPayload.getFloat();
    this.groundspeed = paramMAVLinkPayload.getFloat();
    this.alt = paramMAVLinkPayload.getFloat();
    this.climb = paramMAVLinkPayload.getFloat();
    this.heading = paramMAVLinkPayload.getShort();
    this.throttle = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_vfr_hud
 * JD-Core Version:    0.6.2
 */