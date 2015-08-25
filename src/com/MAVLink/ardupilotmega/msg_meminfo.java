package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_meminfo extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MEMINFO = 152;
  public static final int MAVLINK_MSG_LENGTH = 4;
  private static final long serialVersionUID = 152L;
  public short brkval;
  public short freemem;

  public msg_meminfo()
  {
    this.msgid = 152;
  }

  public msg_meminfo(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 152;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 4;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 152;
    localMAVLinkPacket.payload.putShort(this.brkval);
    localMAVLinkPacket.payload.putShort(this.freemem);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MEMINFO - brkval:" + this.brkval + " freemem:" + this.freemem + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.brkval = paramMAVLinkPayload.getShort();
    this.freemem = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_meminfo
 * JD-Core Version:    0.6.2
 */