package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_erase_firmware_and_config extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_ERASE_FIRMWARE_AND_CONFIG = 208;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 208L;
  public int knock;
  public byte target_component;
  public byte target_system;

  public msg_gimbal_erase_firmware_and_config()
  {
    this.msgid = 208;
  }

  public msg_gimbal_erase_firmware_and_config(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 208;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 208;
    localMAVLinkPacket.payload.putInt(this.knock);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_ERASE_FIRMWARE_AND_CONFIG - knock:" + this.knock + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.knock = paramMAVLinkPayload.getInt();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_erase_firmware_and_config
 * JD-Core Version:    0.6.2
 */