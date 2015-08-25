package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_change_operator_control_ack extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK = 6;
  public static final int MAVLINK_MSG_LENGTH = 3;
  private static final long serialVersionUID = 6L;
  public byte ack;
  public byte control_request;
  public byte gcs_system_id;

  public msg_change_operator_control_ack()
  {
    this.msgid = 6;
  }

  public msg_change_operator_control_ack(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 6;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 3;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 6;
    localMAVLinkPacket.payload.putByte(this.gcs_system_id);
    localMAVLinkPacket.payload.putByte(this.control_request);
    localMAVLinkPacket.payload.putByte(this.ack);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK - gcs_system_id:" + this.gcs_system_id + " control_request:" + this.control_request + " ack:" + this.ack + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.gcs_system_id = paramMAVLinkPayload.getByte();
    this.control_request = paramMAVLinkPayload.getByte();
    this.ack = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_change_operator_control_ack
 * JD-Core Version:    0.6.2
 */