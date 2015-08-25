package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_data_transmission_handshake extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DATA_TRANSMISSION_HANDSHAKE = 130;
  public static final int MAVLINK_MSG_LENGTH = 13;
  private static final long serialVersionUID = 130L;
  public short height;
  public byte jpg_quality;
  public short packets;
  public byte payload;
  public int size;
  public byte type;
  public short width;

  public msg_data_transmission_handshake()
  {
    this.msgid = 130;
  }

  public msg_data_transmission_handshake(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 130;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 13;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 130;
    localMAVLinkPacket.payload.putInt(this.size);
    localMAVLinkPacket.payload.putShort(this.width);
    localMAVLinkPacket.payload.putShort(this.height);
    localMAVLinkPacket.payload.putShort(this.packets);
    localMAVLinkPacket.payload.putByte(this.type);
    localMAVLinkPacket.payload.putByte(this.payload);
    localMAVLinkPacket.payload.putByte(this.jpg_quality);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DATA_TRANSMISSION_HANDSHAKE - size:" + this.size + " width:" + this.width + " height:" + this.height + " packets:" + this.packets + " type:" + this.type + " payload:" + this.payload + " jpg_quality:" + this.jpg_quality + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.size = paramMAVLinkPayload.getInt();
    this.width = paramMAVLinkPayload.getShort();
    this.height = paramMAVLinkPayload.getShort();
    this.packets = paramMAVLinkPayload.getShort();
    this.type = paramMAVLinkPayload.getByte();
    this.payload = paramMAVLinkPayload.getByte();
    this.jpg_quality = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_data_transmission_handshake
 * JD-Core Version:    0.6.2
 */