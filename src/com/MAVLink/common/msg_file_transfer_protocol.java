package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_file_transfer_protocol extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_FILE_TRANSFER_PROTOCOL = 110;
  public static final int MAVLINK_MSG_LENGTH = 254;
  private static final long serialVersionUID = 110L;
  public byte[] payload = new byte['û'];
  public byte target_component;
  public byte target_network;
  public byte target_system;

  public msg_file_transfer_protocol()
  {
    this.msgid = 110;
  }

  public msg_file_transfer_protocol(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 110;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 254;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 110;
    localMAVLinkPacket.payload.putByte(this.target_network);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    for (int i = 0; i < this.payload.length; i++)
      localMAVLinkPacket.payload.putByte(this.payload[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_FILE_TRANSFER_PROTOCOL - target_network:" + this.target_network + " target_system:" + this.target_system + " target_component:" + this.target_component + " payload:" + this.payload + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_network = paramMAVLinkPayload.getByte();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.payload.length; i++)
      this.payload[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_file_transfer_protocol
 * JD-Core Version:    0.6.2
 */