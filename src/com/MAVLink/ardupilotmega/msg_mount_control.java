package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mount_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MOUNT_CONTROL = 157;
  public static final int MAVLINK_MSG_LENGTH = 15;
  private static final long serialVersionUID = 157L;
  public int input_a;
  public int input_b;
  public int input_c;
  public byte save_position;
  public byte target_component;
  public byte target_system;

  public msg_mount_control()
  {
    this.msgid = 157;
  }

  public msg_mount_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 157;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 15;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 157;
    localMAVLinkPacket.payload.putInt(this.input_a);
    localMAVLinkPacket.payload.putInt(this.input_b);
    localMAVLinkPacket.payload.putInt(this.input_c);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.save_position);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MOUNT_CONTROL - input_a:" + this.input_a + " input_b:" + this.input_b + " input_c:" + this.input_c + " target_system:" + this.target_system + " target_component:" + this.target_component + " save_position:" + this.save_position + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.input_a = paramMAVLinkPayload.getInt();
    this.input_b = paramMAVLinkPayload.getInt();
    this.input_c = paramMAVLinkPayload.getInt();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.save_position = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_mount_control
 * JD-Core Version:    0.6.2
 */