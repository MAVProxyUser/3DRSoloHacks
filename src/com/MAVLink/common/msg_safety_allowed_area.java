package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_safety_allowed_area extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SAFETY_ALLOWED_AREA = 55;
  public static final int MAVLINK_MSG_LENGTH = 25;
  private static final long serialVersionUID = 55L;
  public byte frame;
  public float p1x;
  public float p1y;
  public float p1z;
  public float p2x;
  public float p2y;
  public float p2z;

  public msg_safety_allowed_area()
  {
    this.msgid = 55;
  }

  public msg_safety_allowed_area(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 55;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 25;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 55;
    localMAVLinkPacket.payload.putFloat(this.p1x);
    localMAVLinkPacket.payload.putFloat(this.p1y);
    localMAVLinkPacket.payload.putFloat(this.p1z);
    localMAVLinkPacket.payload.putFloat(this.p2x);
    localMAVLinkPacket.payload.putFloat(this.p2y);
    localMAVLinkPacket.payload.putFloat(this.p2z);
    localMAVLinkPacket.payload.putByte(this.frame);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SAFETY_ALLOWED_AREA - p1x:" + this.p1x + " p1y:" + this.p1y + " p1z:" + this.p1z + " p2x:" + this.p2x + " p2y:" + this.p2y + " p2z:" + this.p2z + " frame:" + this.frame + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.p1x = paramMAVLinkPayload.getFloat();
    this.p1y = paramMAVLinkPayload.getFloat();
    this.p1z = paramMAVLinkPayload.getFloat();
    this.p2x = paramMAVLinkPayload.getFloat();
    this.p2y = paramMAVLinkPayload.getFloat();
    this.p2z = paramMAVLinkPayload.getFloat();
    this.frame = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_safety_allowed_area
 * JD-Core Version:    0.6.2
 */