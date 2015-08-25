package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_fence_point extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_FENCE_POINT = 160;
  public static final int MAVLINK_MSG_LENGTH = 12;
  private static final long serialVersionUID = 160L;
  public byte count;
  public byte idx;
  public float lat;
  public float lng;
  public byte target_component;
  public byte target_system;

  public msg_fence_point()
  {
    this.msgid = 160;
  }

  public msg_fence_point(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 160;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 12;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 160;
    localMAVLinkPacket.payload.putFloat(this.lat);
    localMAVLinkPacket.payload.putFloat(this.lng);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.idx);
    localMAVLinkPacket.payload.putByte(this.count);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_FENCE_POINT - lat:" + this.lat + " lng:" + this.lng + " target_system:" + this.target_system + " target_component:" + this.target_component + " idx:" + this.idx + " count:" + this.count + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.lat = paramMAVLinkPayload.getFloat();
    this.lng = paramMAVLinkPayload.getFloat();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.idx = paramMAVLinkPayload.getByte();
    this.count = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_fence_point
 * JD-Core Version:    0.6.2
 */