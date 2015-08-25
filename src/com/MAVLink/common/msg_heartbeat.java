package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_heartbeat extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HEARTBEAT = 0;
  public static final int MAVLINK_MSG_LENGTH = 9;
  private static final long serialVersionUID;
  public byte autopilot;
  public byte base_mode;
  public int custom_mode;
  public byte mavlink_version;
  public byte system_status;
  public byte type;

  public msg_heartbeat()
  {
    this.msgid = 0;
  }

  public msg_heartbeat(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 0;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 9;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 0;
    localMAVLinkPacket.payload.putInt(this.custom_mode);
    localMAVLinkPacket.payload.putByte(this.type);
    localMAVLinkPacket.payload.putByte(this.autopilot);
    localMAVLinkPacket.payload.putByte(this.base_mode);
    localMAVLinkPacket.payload.putByte(this.system_status);
    localMAVLinkPacket.payload.putByte(this.mavlink_version);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HEARTBEAT - custom_mode:" + this.custom_mode + " type:" + this.type + " autopilot:" + this.autopilot + " base_mode:" + this.base_mode + " system_status:" + this.system_status + " mavlink_version:" + this.mavlink_version + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.custom_mode = paramMAVLinkPayload.getInt();
    this.type = paramMAVLinkPayload.getByte();
    this.autopilot = paramMAVLinkPayload.getByte();
    this.base_mode = paramMAVLinkPayload.getByte();
    this.system_status = paramMAVLinkPayload.getByte();
    this.mavlink_version = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_heartbeat
 * JD-Core Version:    0.6.2
 */