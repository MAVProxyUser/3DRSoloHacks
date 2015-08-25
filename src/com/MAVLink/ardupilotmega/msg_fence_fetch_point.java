package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_fence_fetch_point extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_FENCE_FETCH_POINT = 161;
  public static final int MAVLINK_MSG_LENGTH = 3;
  private static final long serialVersionUID = 161L;
  public byte idx;
  public byte target_component;
  public byte target_system;

  public msg_fence_fetch_point()
  {
    this.msgid = 161;
  }

  public msg_fence_fetch_point(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 161;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 3;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 161;
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.idx);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_FENCE_FETCH_POINT - target_system:" + this.target_system + " target_component:" + this.target_component + " idx:" + this.idx + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.idx = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_fence_fetch_point
 * JD-Core Version:    0.6.2
 */