package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_set_factory_parameters extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_SET_FACTORY_PARAMETERS = 206;
  public static final int MAVLINK_MSG_LENGTH = 33;
  private static final long serialVersionUID = 206L;
  public byte assembly_day;
  public byte assembly_hour;
  public byte assembly_minute;
  public byte assembly_month;
  public byte assembly_second;
  public short assembly_year;
  public int magic_1;
  public int magic_2;
  public int magic_3;
  public int serial_number_pt_1;
  public int serial_number_pt_2;
  public int serial_number_pt_3;
  public byte target_component;
  public byte target_system;

  public msg_gimbal_set_factory_parameters()
  {
    this.msgid = 206;
  }

  public msg_gimbal_set_factory_parameters(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 206;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 33;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 206;
    localMAVLinkPacket.payload.putInt(this.magic_1);
    localMAVLinkPacket.payload.putInt(this.magic_2);
    localMAVLinkPacket.payload.putInt(this.magic_3);
    localMAVLinkPacket.payload.putInt(this.serial_number_pt_1);
    localMAVLinkPacket.payload.putInt(this.serial_number_pt_2);
    localMAVLinkPacket.payload.putInt(this.serial_number_pt_3);
    localMAVLinkPacket.payload.putShort(this.assembly_year);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.assembly_month);
    localMAVLinkPacket.payload.putByte(this.assembly_day);
    localMAVLinkPacket.payload.putByte(this.assembly_hour);
    localMAVLinkPacket.payload.putByte(this.assembly_minute);
    localMAVLinkPacket.payload.putByte(this.assembly_second);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_SET_FACTORY_PARAMETERS - magic_1:" + this.magic_1 + " magic_2:" + this.magic_2 + " magic_3:" + this.magic_3 + " serial_number_pt_1:" + this.serial_number_pt_1 + " serial_number_pt_2:" + this.serial_number_pt_2 + " serial_number_pt_3:" + this.serial_number_pt_3 + " assembly_year:" + this.assembly_year + " target_system:" + this.target_system + " target_component:" + this.target_component + " assembly_month:" + this.assembly_month + " assembly_day:" + this.assembly_day + " assembly_hour:" + this.assembly_hour + " assembly_minute:" + this.assembly_minute + " assembly_second:" + this.assembly_second + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.magic_1 = paramMAVLinkPayload.getInt();
    this.magic_2 = paramMAVLinkPayload.getInt();
    this.magic_3 = paramMAVLinkPayload.getInt();
    this.serial_number_pt_1 = paramMAVLinkPayload.getInt();
    this.serial_number_pt_2 = paramMAVLinkPayload.getInt();
    this.serial_number_pt_3 = paramMAVLinkPayload.getInt();
    this.assembly_year = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.assembly_month = paramMAVLinkPayload.getByte();
    this.assembly_day = paramMAVLinkPayload.getByte();
    this.assembly_hour = paramMAVLinkPayload.getByte();
    this.assembly_minute = paramMAVLinkPayload.getByte();
    this.assembly_second = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_set_factory_parameters
 * JD-Core Version:    0.6.2
 */