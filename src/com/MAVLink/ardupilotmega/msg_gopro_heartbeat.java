package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gopro_heartbeat extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GOPRO_HEARTBEAT = 215;
  public static final int MAVLINK_MSG_LENGTH = 1;
  private static final long serialVersionUID = 215L;
  public byte status;

  public msg_gopro_heartbeat()
  {
    this.msgid = 215;
  }

  public msg_gopro_heartbeat(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 215;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 1;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 215;
    localMAVLinkPacket.payload.putByte(this.status);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GOPRO_HEARTBEAT - status:" + this.status + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.status = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gopro_heartbeat
 * JD-Core Version:    0.6.2
 */