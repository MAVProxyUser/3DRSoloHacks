package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gopro_set_response extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GOPRO_SET_RESPONSE = 219;
  public static final int MAVLINK_MSG_LENGTH = 2;
  private static final long serialVersionUID = 219L;
  public byte cmd_id;
  public byte result;

  public msg_gopro_set_response()
  {
    this.msgid = 219;
  }

  public msg_gopro_set_response(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 219;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 2;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 219;
    localMAVLinkPacket.payload.putByte(this.cmd_id);
    localMAVLinkPacket.payload.putByte(this.result);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GOPRO_SET_RESPONSE - cmd_id:" + this.cmd_id + " result:" + this.result + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.cmd_id = paramMAVLinkPayload.getByte();
    this.result = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gopro_set_response
 * JD-Core Version:    0.6.2
 */