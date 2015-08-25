package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_command_long extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_COMMAND_LONG = 76;
  public static final int MAVLINK_MSG_LENGTH = 33;
  private static final long serialVersionUID = 76L;
  public short command;
  public byte confirmation;
  public float param1;
  public float param2;
  public float param3;
  public float param4;
  public float param5;
  public float param6;
  public float param7;
  public byte target_component;
  public byte target_system;

  public msg_command_long()
  {
    this.msgid = 76;
  }

  public msg_command_long(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 76;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 33;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 76;
    localMAVLinkPacket.payload.putFloat(this.param1);
    localMAVLinkPacket.payload.putFloat(this.param2);
    localMAVLinkPacket.payload.putFloat(this.param3);
    localMAVLinkPacket.payload.putFloat(this.param4);
    localMAVLinkPacket.payload.putFloat(this.param5);
    localMAVLinkPacket.payload.putFloat(this.param6);
    localMAVLinkPacket.payload.putFloat(this.param7);
    localMAVLinkPacket.payload.putShort(this.command);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.confirmation);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_COMMAND_LONG - param1:" + this.param1 + " param2:" + this.param2 + " param3:" + this.param3 + " param4:" + this.param4 + " param5:" + this.param5 + " param6:" + this.param6 + " param7:" + this.param7 + " command:" + this.command + " target_system:" + this.target_system + " target_component:" + this.target_component + " confirmation:" + this.confirmation + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.param1 = paramMAVLinkPayload.getFloat();
    this.param2 = paramMAVLinkPayload.getFloat();
    this.param3 = paramMAVLinkPayload.getFloat();
    this.param4 = paramMAVLinkPayload.getFloat();
    this.param5 = paramMAVLinkPayload.getFloat();
    this.param6 = paramMAVLinkPayload.getFloat();
    this.param7 = paramMAVLinkPayload.getFloat();
    this.command = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.confirmation = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_command_long
 * JD-Core Version:    0.6.2
 */