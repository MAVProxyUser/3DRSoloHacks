package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_data32 extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DATA32 = 170;
  public static final int MAVLINK_MSG_LENGTH = 34;
  private static final long serialVersionUID = 170L;
  public byte[] data = new byte[32];
  public byte len;
  public byte type;

  public msg_data32()
  {
    this.msgid = 170;
  }

  public msg_data32(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 170;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 34;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 170;
    localMAVLinkPacket.payload.putByte(this.type);
    localMAVLinkPacket.payload.putByte(this.len);
    for (int i = 0; i < this.data.length; i++)
      localMAVLinkPacket.payload.putByte(this.data[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DATA32 - type:" + this.type + " len:" + this.len + " data:" + this.data + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.type = paramMAVLinkPayload.getByte();
    this.len = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.data.length; i++)
      this.data[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_data32
 * JD-Core Version:    0.6.2
 */