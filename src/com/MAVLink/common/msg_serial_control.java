package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_serial_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SERIAL_CONTROL = 126;
  public static final int MAVLINK_MSG_LENGTH = 79;
  private static final long serialVersionUID = 126L;
  public int baudrate;
  public byte count;
  public byte[] data = new byte[70];
  public byte device;
  public byte flags;
  public short timeout;

  public msg_serial_control()
  {
    this.msgid = 126;
  }

  public msg_serial_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 126;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 79;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 126;
    localMAVLinkPacket.payload.putInt(this.baudrate);
    localMAVLinkPacket.payload.putShort(this.timeout);
    localMAVLinkPacket.payload.putByte(this.device);
    localMAVLinkPacket.payload.putByte(this.flags);
    localMAVLinkPacket.payload.putByte(this.count);
    for (int i = 0; i < this.data.length; i++)
      localMAVLinkPacket.payload.putByte(this.data[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SERIAL_CONTROL - baudrate:" + this.baudrate + " timeout:" + this.timeout + " device:" + this.device + " flags:" + this.flags + " count:" + this.count + " data:" + this.data + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.baudrate = paramMAVLinkPayload.getInt();
    this.timeout = paramMAVLinkPayload.getShort();
    this.device = paramMAVLinkPayload.getByte();
    this.flags = paramMAVLinkPayload.getByte();
    this.count = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.data.length; i++)
      this.data[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_serial_control
 * JD-Core Version:    0.6.2
 */