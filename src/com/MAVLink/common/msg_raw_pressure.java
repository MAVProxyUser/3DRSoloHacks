package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_raw_pressure extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RAW_PRESSURE = 28;
  public static final int MAVLINK_MSG_LENGTH = 16;
  private static final long serialVersionUID = 28L;
  public short press_abs;
  public short press_diff1;
  public short press_diff2;
  public short temperature;
  public long time_usec;

  public msg_raw_pressure()
  {
    this.msgid = 28;
  }

  public msg_raw_pressure(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 28;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 16;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 28;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putShort(this.press_abs);
    localMAVLinkPacket.payload.putShort(this.press_diff1);
    localMAVLinkPacket.payload.putShort(this.press_diff2);
    localMAVLinkPacket.payload.putShort(this.temperature);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RAW_PRESSURE - time_usec:" + this.time_usec + " press_abs:" + this.press_abs + " press_diff1:" + this.press_diff1 + " press_diff2:" + this.press_diff2 + " temperature:" + this.temperature + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.press_abs = paramMAVLinkPayload.getShort();
    this.press_diff1 = paramMAVLinkPayload.getShort();
    this.press_diff2 = paramMAVLinkPayload.getShort();
    this.temperature = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_raw_pressure
 * JD-Core Version:    0.6.2
 */