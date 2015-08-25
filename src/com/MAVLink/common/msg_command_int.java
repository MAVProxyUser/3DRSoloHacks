package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_command_int extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_COMMAND_INT = 75;
  public static final int MAVLINK_MSG_LENGTH = 35;
  private static final long serialVersionUID = 75L;
  public byte autocontinue;
  public short command;
  public byte current;
  public byte frame;
  public float param1;
  public float param2;
  public float param3;
  public float param4;
  public byte target_component;
  public byte target_system;
  public int x;
  public int y;
  public float z;

  public msg_command_int()
  {
    this.msgid = 75;
  }

  public msg_command_int(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 75;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 35;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 75;
    localMAVLinkPacket.payload.putFloat(this.param1);
    localMAVLinkPacket.payload.putFloat(this.param2);
    localMAVLinkPacket.payload.putFloat(this.param3);
    localMAVLinkPacket.payload.putFloat(this.param4);
    localMAVLinkPacket.payload.putInt(this.x);
    localMAVLinkPacket.payload.putInt(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
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
    return "MAVLINK_MSG_ID_COMMAND_INT - param1:" + this.param1 + " param2:" + this.param2 + " param3:" + this.param3 + " param4:" + this.param4 + " x:" + this.x + " y:" + this.y + " z:" + this.z + " command:" + this.command + " target_system:" + this.target_system + " target_component:" + this.target_component + " frame:" + this.frame + " current:" + this.current + " autocontinue:" + this.autocontinue + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.param1 = paramMAVLinkPayload.getFloat();
    this.param2 = paramMAVLinkPayload.getFloat();
    this.param3 = paramMAVLinkPayload.getFloat();
    this.param4 = paramMAVLinkPayload.getFloat();
    this.x = paramMAVLinkPayload.getInt();
    this.y = paramMAVLinkPayload.getInt();
    this.z = paramMAVLinkPayload.getFloat();
    this.command = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.frame = paramMAVLinkPayload.getByte();
    this.current = paramMAVLinkPayload.getByte();
    this.autocontinue = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_command_int
 * JD-Core Version:    0.6.2
 */