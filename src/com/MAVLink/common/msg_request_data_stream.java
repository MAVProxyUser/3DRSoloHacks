package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_request_data_stream extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_REQUEST_DATA_STREAM = 66;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 66L;
  public short req_message_rate;
  public byte req_stream_id;
  public byte start_stop;
  public byte target_component;
  public byte target_system;

  public msg_request_data_stream()
  {
    this.msgid = 66;
  }

  public msg_request_data_stream(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 66;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 66;
    localMAVLinkPacket.payload.putShort(this.req_message_rate);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.req_stream_id);
    localMAVLinkPacket.payload.putByte(this.start_stop);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_REQUEST_DATA_STREAM - req_message_rate:" + this.req_message_rate + " target_system:" + this.target_system + " target_component:" + this.target_component + " req_stream_id:" + this.req_stream_id + " start_stop:" + this.start_stop + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.req_message_rate = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.req_stream_id = paramMAVLinkPayload.getByte();
    this.start_stop = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_request_data_stream
 * JD-Core Version:    0.6.2
 */