package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_command_ack extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_COMMAND_ACK = 77;
  public static final int MAVLINK_MSG_LENGTH = 3;
  private static final long serialVersionUID = 77L;
  public short command;
  public byte result;

  public msg_command_ack()
  {
    this.msgid = 77;
  }

  public msg_command_ack(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 77;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 3;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 77;
    localMAVLinkPacket.payload.putShort(this.command);
    localMAVLinkPacket.payload.putByte(this.result);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_COMMAND_ACK - command:" + this.command + " result:" + this.result + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.command = paramMAVLinkPayload.getShort();
    this.result = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_command_ack
 * JD-Core Version:    0.6.2
 */