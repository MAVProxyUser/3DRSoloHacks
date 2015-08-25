package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_battery_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_BATTERY_STATUS = 147;
  public static final int MAVLINK_MSG_LENGTH = 36;
  private static final long serialVersionUID = 147L;
  public byte battery_function;
  public byte battery_remaining;
  public short current_battery;
  public int current_consumed;
  public int energy_consumed;
  public byte id;
  public short temperature;
  public byte type;
  public short[] voltages = new short[10];

  public msg_battery_status()
  {
    this.msgid = 147;
  }

  public msg_battery_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 147;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 36;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 147;
    localMAVLinkPacket.payload.putInt(this.current_consumed);
    localMAVLinkPacket.payload.putInt(this.energy_consumed);
    localMAVLinkPacket.payload.putShort(this.temperature);
    for (int i = 0; i < this.voltages.length; i++)
      localMAVLinkPacket.payload.putShort(this.voltages[i]);
    localMAVLinkPacket.payload.putShort(this.current_battery);
    localMAVLinkPacket.payload.putByte(this.id);
    localMAVLinkPacket.payload.putByte(this.battery_function);
    localMAVLinkPacket.payload.putByte(this.type);
    localMAVLinkPacket.payload.putByte(this.battery_remaining);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_BATTERY_STATUS - current_consumed:" + this.current_consumed + " energy_consumed:" + this.energy_consumed + " temperature:" + this.temperature + " voltages:" + this.voltages + " current_battery:" + this.current_battery + " id:" + this.id + " battery_function:" + this.battery_function + " type:" + this.type + " battery_remaining:" + this.battery_remaining + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.current_consumed = paramMAVLinkPayload.getInt();
    this.energy_consumed = paramMAVLinkPayload.getInt();
    this.temperature = paramMAVLinkPayload.getShort();
    for (int i = 0; i < this.voltages.length; i++)
      this.voltages[i] = paramMAVLinkPayload.getShort();
    this.current_battery = paramMAVLinkPayload.getShort();
    this.id = paramMAVLinkPayload.getByte();
    this.battery_function = paramMAVLinkPayload.getByte();
    this.type = paramMAVLinkPayload.getByte();
    this.battery_remaining = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_battery_status
 * JD-Core Version:    0.6.2
 */