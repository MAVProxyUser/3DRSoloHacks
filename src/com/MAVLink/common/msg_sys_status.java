package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_sys_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SYS_STATUS = 1;
  public static final int MAVLINK_MSG_LENGTH = 31;
  private static final long serialVersionUID = 1L;
  public byte battery_remaining;
  public short current_battery;
  public short drop_rate_comm;
  public short errors_comm;
  public short errors_count1;
  public short errors_count2;
  public short errors_count3;
  public short errors_count4;
  public short load;
  public int onboard_control_sensors_enabled;
  public int onboard_control_sensors_health;
  public int onboard_control_sensors_present;
  public short voltage_battery;

  public msg_sys_status()
  {
    this.msgid = 1;
  }

  public msg_sys_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 1;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 31;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 1;
    localMAVLinkPacket.payload.putInt(this.onboard_control_sensors_present);
    localMAVLinkPacket.payload.putInt(this.onboard_control_sensors_enabled);
    localMAVLinkPacket.payload.putInt(this.onboard_control_sensors_health);
    localMAVLinkPacket.payload.putShort(this.load);
    localMAVLinkPacket.payload.putShort(this.voltage_battery);
    localMAVLinkPacket.payload.putShort(this.current_battery);
    localMAVLinkPacket.payload.putShort(this.drop_rate_comm);
    localMAVLinkPacket.payload.putShort(this.errors_comm);
    localMAVLinkPacket.payload.putShort(this.errors_count1);
    localMAVLinkPacket.payload.putShort(this.errors_count2);
    localMAVLinkPacket.payload.putShort(this.errors_count3);
    localMAVLinkPacket.payload.putShort(this.errors_count4);
    localMAVLinkPacket.payload.putByte(this.battery_remaining);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SYS_STATUS - onboard_control_sensors_present:" + this.onboard_control_sensors_present + " onboard_control_sensors_enabled:" + this.onboard_control_sensors_enabled + " onboard_control_sensors_health:" + this.onboard_control_sensors_health + " load:" + this.load + " voltage_battery:" + this.voltage_battery + " current_battery:" + this.current_battery + " drop_rate_comm:" + this.drop_rate_comm + " errors_comm:" + this.errors_comm + " errors_count1:" + this.errors_count1 + " errors_count2:" + this.errors_count2 + " errors_count3:" + this.errors_count3 + " errors_count4:" + this.errors_count4 + " battery_remaining:" + this.battery_remaining + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.onboard_control_sensors_present = paramMAVLinkPayload.getInt();
    this.onboard_control_sensors_enabled = paramMAVLinkPayload.getInt();
    this.onboard_control_sensors_health = paramMAVLinkPayload.getInt();
    this.load = paramMAVLinkPayload.getShort();
    this.voltage_battery = paramMAVLinkPayload.getShort();
    this.current_battery = paramMAVLinkPayload.getShort();
    this.drop_rate_comm = paramMAVLinkPayload.getShort();
    this.errors_comm = paramMAVLinkPayload.getShort();
    this.errors_count1 = paramMAVLinkPayload.getShort();
    this.errors_count2 = paramMAVLinkPayload.getShort();
    this.errors_count3 = paramMAVLinkPayload.getShort();
    this.errors_count4 = paramMAVLinkPayload.getShort();
    this.battery_remaining = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_sys_status
 * JD-Core Version:    0.6.2
 */