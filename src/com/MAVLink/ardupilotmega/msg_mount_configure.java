package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mount_configure extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MOUNT_CONFIGURE = 156;
  public static final int MAVLINK_MSG_LENGTH = 6;
  private static final long serialVersionUID = 156L;
  public byte mount_mode;
  public byte stab_pitch;
  public byte stab_roll;
  public byte stab_yaw;
  public byte target_component;
  public byte target_system;

  public msg_mount_configure()
  {
    this.msgid = 156;
  }

  public msg_mount_configure(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 156;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 6;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 156;
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.mount_mode);
    localMAVLinkPacket.payload.putByte(this.stab_roll);
    localMAVLinkPacket.payload.putByte(this.stab_pitch);
    localMAVLinkPacket.payload.putByte(this.stab_yaw);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MOUNT_CONFIGURE - target_system:" + this.target_system + " target_component:" + this.target_component + " mount_mode:" + this.mount_mode + " stab_roll:" + this.stab_roll + " stab_pitch:" + this.stab_pitch + " stab_yaw:" + this.stab_yaw + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.mount_mode = paramMAVLinkPayload.getByte();
    this.stab_roll = paramMAVLinkPayload.getByte();
    this.stab_pitch = paramMAVLinkPayload.getByte();
    this.stab_yaw = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_mount_configure
 * JD-Core Version:    0.6.2
 */