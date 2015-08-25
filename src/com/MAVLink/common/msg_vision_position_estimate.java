package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_vision_position_estimate extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_VISION_POSITION_ESTIMATE = 102;
  public static final int MAVLINK_MSG_LENGTH = 32;
  private static final long serialVersionUID = 102L;
  public float pitch;
  public float roll;
  public long usec;
  public float x;
  public float y;
  public float yaw;
  public float z;

  public msg_vision_position_estimate()
  {
    this.msgid = 102;
  }

  public msg_vision_position_estimate(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 102;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 32;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 102;
    localMAVLinkPacket.payload.putLong(this.usec);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_VISION_POSITION_ESTIMATE - usec:" + this.usec + " x:" + this.x + " y:" + this.y + " z:" + this.z + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.usec = paramMAVLinkPayload.getLong();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_vision_position_estimate
 * JD-Core Version:    0.6.2
 */