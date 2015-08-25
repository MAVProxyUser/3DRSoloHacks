package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_scaled_pressure extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SCALED_PRESSURE = 29;
  public static final int MAVLINK_MSG_LENGTH = 14;
  private static final long serialVersionUID = 29L;
  public float press_abs;
  public float press_diff;
  public short temperature;
  public int time_boot_ms;

  public msg_scaled_pressure()
  {
    this.msgid = 29;
  }

  public msg_scaled_pressure(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 29;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 14;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 29;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.press_abs);
    localMAVLinkPacket.payload.putFloat(this.press_diff);
    localMAVLinkPacket.payload.putShort(this.temperature);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SCALED_PRESSURE - time_boot_ms:" + this.time_boot_ms + " press_abs:" + this.press_abs + " press_diff:" + this.press_diff + " temperature:" + this.temperature + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.press_abs = paramMAVLinkPayload.getFloat();
    this.press_diff = paramMAVLinkPayload.getFloat();
    this.temperature = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_scaled_pressure
 * JD-Core Version:    0.6.2
 */