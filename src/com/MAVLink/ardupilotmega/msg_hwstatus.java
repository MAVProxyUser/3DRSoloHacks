package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hwstatus extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HWSTATUS = 165;
  public static final int MAVLINK_MSG_LENGTH = 3;
  private static final long serialVersionUID = 165L;
  public byte I2Cerr;
  public short Vcc;

  public msg_hwstatus()
  {
    this.msgid = 165;
  }

  public msg_hwstatus(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 165;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 3;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 165;
    localMAVLinkPacket.payload.putShort(this.Vcc);
    localMAVLinkPacket.payload.putByte(this.I2Cerr);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HWSTATUS - Vcc:" + this.Vcc + " I2Cerr:" + this.I2Cerr + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.Vcc = paramMAVLinkPayload.getShort();
    this.I2Cerr = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_hwstatus
 * JD-Core Version:    0.6.2
 */