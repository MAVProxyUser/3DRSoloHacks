package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_data_stream extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DATA_STREAM = 67;
  public static final int MAVLINK_MSG_LENGTH = 4;
  private static final long serialVersionUID = 67L;
  public short message_rate;
  public byte on_off;
  public byte stream_id;

  public msg_data_stream()
  {
    this.msgid = 67;
  }

  public msg_data_stream(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 67;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 4;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 67;
    localMAVLinkPacket.payload.putShort(this.message_rate);
    localMAVLinkPacket.payload.putByte(this.stream_id);
    localMAVLinkPacket.payload.putByte(this.on_off);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DATA_STREAM - message_rate:" + this.message_rate + " stream_id:" + this.stream_id + " on_off:" + this.on_off + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.message_rate = paramMAVLinkPayload.getShort();
    this.stream_id = paramMAVLinkPayload.getByte();
    this.on_off = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_data_stream
 * JD-Core Version:    0.6.2
 */