package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_log_data extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOG_DATA = 120;
  public static final int MAVLINK_MSG_LENGTH = 97;
  private static final long serialVersionUID = 120L;
  public byte count;
  public byte[] data = new byte[90];
  public short id;
  public int ofs;

  public msg_log_data()
  {
    this.msgid = 120;
  }

  public msg_log_data(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 120;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 97;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 120;
    localMAVLinkPacket.payload.putInt(this.ofs);
    localMAVLinkPacket.payload.putShort(this.id);
    localMAVLinkPacket.payload.putByte(this.count);
    for (int i = 0; i < this.data.length; i++)
      localMAVLinkPacket.payload.putByte(this.data[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOG_DATA - ofs:" + this.ofs + " id:" + this.id + " count:" + this.count + " data:" + this.data + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.ofs = paramMAVLinkPayload.getInt();
    this.id = paramMAVLinkPayload.getShort();
    this.count = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.data.length; i++)
      this.data[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_log_data
 * JD-Core Version:    0.6.2
 */