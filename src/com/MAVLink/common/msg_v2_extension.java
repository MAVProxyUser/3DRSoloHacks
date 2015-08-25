package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_v2_extension extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_V2_EXTENSION = 248;
  public static final int MAVLINK_MSG_LENGTH = 254;
  private static final long serialVersionUID = 248L;
  public short message_type;
  public byte[] payload = new byte['ù'];
  public byte target_component;
  public byte target_network;
  public byte target_system;

  public msg_v2_extension()
  {
    this.msgid = 248;
  }

  public msg_v2_extension(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 248;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 254;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 248;
    localMAVLinkPacket.payload.putShort(this.message_type);
    localMAVLinkPacket.payload.putByte(this.target_network);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    for (int i = 0; i < this.payload.length; i++)
      localMAVLinkPacket.payload.putByte(this.payload[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_V2_EXTENSION - message_type:" + this.message_type + " target_network:" + this.target_network + " target_system:" + this.target_system + " target_component:" + this.target_component + " payload:" + this.payload + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.message_type = paramMAVLinkPayload.getShort();
    this.target_network = paramMAVLinkPayload.getByte();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.payload.length; i++)
      this.payload[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_v2_extension
 * JD-Core Version:    0.6.2
 */