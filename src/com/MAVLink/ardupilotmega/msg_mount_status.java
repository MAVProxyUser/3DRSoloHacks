package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mount_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MOUNT_STATUS = 158;
  public static final int MAVLINK_MSG_LENGTH = 14;
  private static final long serialVersionUID = 158L;
  public int pointing_a;
  public int pointing_b;
  public int pointing_c;
  public byte target_component;
  public byte target_system;

  public msg_mount_status()
  {
    this.msgid = 158;
  }

  public msg_mount_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 158;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 14;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 158;
    localMAVLinkPacket.payload.putInt(this.pointing_a);
    localMAVLinkPacket.payload.putInt(this.pointing_b);
    localMAVLinkPacket.payload.putInt(this.pointing_c);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MOUNT_STATUS - pointing_a:" + this.pointing_a + " pointing_b:" + this.pointing_b + " pointing_c:" + this.pointing_c + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.pointing_a = paramMAVLinkPayload.getInt();
    this.pointing_b = paramMAVLinkPayload.getInt();
    this.pointing_c = paramMAVLinkPayload.getInt();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_mount_status
 * JD-Core Version:    0.6.2
 */