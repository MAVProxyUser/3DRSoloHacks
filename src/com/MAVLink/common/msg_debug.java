package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_debug extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DEBUG = 254;
  public static final int MAVLINK_MSG_LENGTH = 9;
  private static final long serialVersionUID = 254L;
  public byte ind;
  public int time_boot_ms;
  public float value;

  public msg_debug()
  {
    this.msgid = 254;
  }

  public msg_debug(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 254;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 9;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 254;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.value);
    localMAVLinkPacket.payload.putByte(this.ind);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DEBUG - time_boot_ms:" + this.time_boot_ms + " value:" + this.value + " ind:" + this.ind + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.value = paramMAVLinkPayload.getFloat();
    this.ind = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_debug
 * JD-Core Version:    0.6.2
 */