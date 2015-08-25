package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_rc_channels_raw extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RC_CHANNELS_RAW = 35;
  public static final int MAVLINK_MSG_LENGTH = 22;
  private static final long serialVersionUID = 35L;
  public short chan1_raw;
  public short chan2_raw;
  public short chan3_raw;
  public short chan4_raw;
  public short chan5_raw;
  public short chan6_raw;
  public short chan7_raw;
  public short chan8_raw;
  public byte port;
  public byte rssi;
  public int time_boot_ms;

  public msg_rc_channels_raw()
  {
    this.msgid = 35;
  }

  public msg_rc_channels_raw(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 35;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 22;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 35;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putShort(this.chan1_raw);
    localMAVLinkPacket.payload.putShort(this.chan2_raw);
    localMAVLinkPacket.payload.putShort(this.chan3_raw);
    localMAVLinkPacket.payload.putShort(this.chan4_raw);
    localMAVLinkPacket.payload.putShort(this.chan5_raw);
    localMAVLinkPacket.payload.putShort(this.chan6_raw);
    localMAVLinkPacket.payload.putShort(this.chan7_raw);
    localMAVLinkPacket.payload.putShort(this.chan8_raw);
    localMAVLinkPacket.payload.putByte(this.port);
    localMAVLinkPacket.payload.putByte(this.rssi);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RC_CHANNELS_RAW - time_boot_ms:" + this.time_boot_ms + " chan1_raw:" + this.chan1_raw + " chan2_raw:" + this.chan2_raw + " chan3_raw:" + this.chan3_raw + " chan4_raw:" + this.chan4_raw + " chan5_raw:" + this.chan5_raw + " chan6_raw:" + this.chan6_raw + " chan7_raw:" + this.chan7_raw + " chan8_raw:" + this.chan8_raw + " port:" + this.port + " rssi:" + this.rssi + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.chan1_raw = paramMAVLinkPayload.getShort();
    this.chan2_raw = paramMAVLinkPayload.getShort();
    this.chan3_raw = paramMAVLinkPayload.getShort();
    this.chan4_raw = paramMAVLinkPayload.getShort();
    this.chan5_raw = paramMAVLinkPayload.getShort();
    this.chan6_raw = paramMAVLinkPayload.getShort();
    this.chan7_raw = paramMAVLinkPayload.getShort();
    this.chan8_raw = paramMAVLinkPayload.getShort();
    this.port = paramMAVLinkPayload.getByte();
    this.rssi = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_rc_channels_raw
 * JD-Core Version:    0.6.2
 */