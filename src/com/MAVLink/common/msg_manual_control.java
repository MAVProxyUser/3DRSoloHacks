package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_manual_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MANUAL_CONTROL = 69;
  public static final int MAVLINK_MSG_LENGTH = 11;
  private static final long serialVersionUID = 69L;
  public short buttons;
  public short r;
  public byte target;
  public short x;
  public short y;
  public short z;

  public msg_manual_control()
  {
    this.msgid = 69;
  }

  public msg_manual_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 69;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 11;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 69;
    localMAVLinkPacket.payload.putShort(this.x);
    localMAVLinkPacket.payload.putShort(this.y);
    localMAVLinkPacket.payload.putShort(this.z);
    localMAVLinkPacket.payload.putShort(this.r);
    localMAVLinkPacket.payload.putShort(this.buttons);
    localMAVLinkPacket.payload.putByte(this.target);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MANUAL_CONTROL - x:" + this.x + " y:" + this.y + " z:" + this.z + " r:" + this.r + " buttons:" + this.buttons + " target:" + this.target + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.x = paramMAVLinkPayload.getShort();
    this.y = paramMAVLinkPayload.getShort();
    this.z = paramMAVLinkPayload.getShort();
    this.r = paramMAVLinkPayload.getShort();
    this.buttons = paramMAVLinkPayload.getShort();
    this.target = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_manual_control
 * JD-Core Version:    0.6.2
 */