package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mission_item extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MISSION_ITEM = 39;
  public static final int MAVLINK_MSG_LENGTH = 37;
  private static final long serialVersionUID = 39L;
  public byte autocontinue;
  public short command;
  public byte current;
  public byte frame;
  public float param1;
  public float param2;
  public float param3;
  public float param4;
  public short seq;
  public byte target_component;
  public byte target_system;
  public float x;
  public float y;
  public float z;

  public msg_mission_item()
  {
    this.msgid = 39;
  }

  public msg_mission_item(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 39;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 37;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 39;
    localMAVLinkPacket.payload.putFloat(this.param1);
    localMAVLinkPacket.payload.putFloat(this.param2);
    localMAVLinkPacket.payload.putFloat(this.param3);
    localMAVLinkPacket.payload.putFloat(this.param4);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    localMAVLinkPacket.payload.putShort(this.seq);
    localMAVLinkPacket.payload.putShort(this.command);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.frame);
    localMAVLinkPacket.payload.putByte(this.current);
    localMAVLinkPacket.payload.putByte(this.autocontinue);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MISSION_ITEM - param1:" + this.param1 + " param2:" + this.param2 + " param3:" + this.param3 + " param4:" + this.param4 + " x:" + this.x + " y:" + this.y + " z:" + this.z + " seq:" + this.seq + " command:" + this.command + " target_system:" + this.target_system + " target_component:" + this.target_component + " frame:" + this.frame + " current:" + this.current + " autocontinue:" + this.autocontinue + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.param1 = paramMAVLinkPayload.getFloat();
    this.param2 = paramMAVLinkPayload.getFloat();
    this.param3 = paramMAVLinkPayload.getFloat();
    this.param4 = paramMAVLinkPayload.getFloat();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
    this.seq = paramMAVLinkPayload.getShort();
    this.command = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.frame = paramMAVLinkPayload.getByte();
    this.current = paramMAVLinkPayload.getByte();
    this.autocontinue = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_mission_item
 * JD-Core Version:    0.6.2
 */