package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_vision_speed_estimate extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_VISION_SPEED_ESTIMATE = 103;
  public static final int MAVLINK_MSG_LENGTH = 20;
  private static final long serialVersionUID = 103L;
  public long usec;
  public float x;
  public float y;
  public float z;

  public msg_vision_speed_estimate()
  {
    this.msgid = 103;
  }

  public msg_vision_speed_estimate(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 103;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 20;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 103;
    localMAVLinkPacket.payload.putLong(this.usec);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_VISION_SPEED_ESTIMATE - usec:" + this.usec + " x:" + this.x + " y:" + this.y + " z:" + this.z + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.usec = paramMAVLinkPayload.getLong();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_vision_speed_estimate
 * JD-Core Version:    0.6.2
 */