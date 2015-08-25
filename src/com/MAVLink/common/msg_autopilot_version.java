package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_autopilot_version extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AUTOPILOT_VERSION = 148;
  public static final int MAVLINK_MSG_LENGTH = 20;
  private static final long serialVersionUID = 148L;
  public long capabilities;
  public byte[] custom_version = new byte[8];
  public int version;

  public msg_autopilot_version()
  {
    this.msgid = 148;
  }

  public msg_autopilot_version(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 148;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 20;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 148;
    localMAVLinkPacket.payload.putLong(this.capabilities);
    localMAVLinkPacket.payload.putInt(this.version);
    for (int i = 0; i < this.custom_version.length; i++)
      localMAVLinkPacket.payload.putByte(this.custom_version[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AUTOPILOT_VERSION - capabilities:" + this.capabilities + " version:" + this.version + " custom_version:" + this.custom_version + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.capabilities = paramMAVLinkPayload.getLong();
    this.version = paramMAVLinkPayload.getInt();
    for (int i = 0; i < this.custom_version.length; i++)
      this.custom_version[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_autopilot_version
 * JD-Core Version:    0.6.2
 */