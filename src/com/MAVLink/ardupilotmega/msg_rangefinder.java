package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_rangefinder extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RANGEFINDER = 173;
  public static final int MAVLINK_MSG_LENGTH = 8;
  private static final long serialVersionUID = 173L;
  public float distance;
  public float voltage;

  public msg_rangefinder()
  {
    this.msgid = 173;
  }

  public msg_rangefinder(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 173;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 8;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 173;
    localMAVLinkPacket.payload.putFloat(this.distance);
    localMAVLinkPacket.payload.putFloat(this.voltage);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RANGEFINDER - distance:" + this.distance + " voltage:" + this.voltage + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.distance = paramMAVLinkPayload.getFloat();
    this.voltage = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_rangefinder
 * JD-Core Version:    0.6.2
 */