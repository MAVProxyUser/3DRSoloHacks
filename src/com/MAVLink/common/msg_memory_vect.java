package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_memory_vect extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MEMORY_VECT = 249;
  public static final int MAVLINK_MSG_LENGTH = 36;
  private static final long serialVersionUID = 249L;
  public short address;
  public byte type;
  public byte[] value = new byte[32];
  public byte ver;

  public msg_memory_vect()
  {
    this.msgid = 249;
  }

  public msg_memory_vect(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 249;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 36;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 249;
    localMAVLinkPacket.payload.putShort(this.address);
    localMAVLinkPacket.payload.putByte(this.ver);
    localMAVLinkPacket.payload.putByte(this.type);
    for (int i = 0; i < this.value.length; i++)
      localMAVLinkPacket.payload.putByte(this.value[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MEMORY_VECT - address:" + this.address + " ver:" + this.ver + " type:" + this.type + " value:" + this.value + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.address = paramMAVLinkPayload.getShort();
    this.ver = paramMAVLinkPayload.getByte();
    this.type = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.value.length; i++)
      this.value[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_memory_vect
 * JD-Core Version:    0.6.2
 */