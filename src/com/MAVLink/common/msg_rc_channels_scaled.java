package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_rc_channels_scaled extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RC_CHANNELS_SCALED = 34;
  public static final int MAVLINK_MSG_LENGTH = 22;
  private static final long serialVersionUID = 34L;
  public short chan1_scaled;
  public short chan2_scaled;
  public short chan3_scaled;
  public short chan4_scaled;
  public short chan5_scaled;
  public short chan6_scaled;
  public short chan7_scaled;
  public short chan8_scaled;
  public byte port;
  public byte rssi;
  public int time_boot_ms;

  public msg_rc_channels_scaled()
  {
    this.msgid = 34;
  }

  public msg_rc_channels_scaled(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 34;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 22;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 34;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putShort(this.chan1_scaled);
    localMAVLinkPacket.payload.putShort(this.chan2_scaled);
    localMAVLinkPacket.payload.putShort(this.chan3_scaled);
    localMAVLinkPacket.payload.putShort(this.chan4_scaled);
    localMAVLinkPacket.payload.putShort(this.chan5_scaled);
    localMAVLinkPacket.payload.putShort(this.chan6_scaled);
    localMAVLinkPacket.payload.putShort(this.chan7_scaled);
    localMAVLinkPacket.payload.putShort(this.chan8_scaled);
    localMAVLinkPacket.payload.putByte(this.port);
    localMAVLinkPacket.payload.putByte(this.rssi);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RC_CHANNELS_SCALED - time_boot_ms:" + this.time_boot_ms + " chan1_scaled:" + this.chan1_scaled + " chan2_scaled:" + this.chan2_scaled + " chan3_scaled:" + this.chan3_scaled + " chan4_scaled:" + this.chan4_scaled + " chan5_scaled:" + this.chan5_scaled + " chan6_scaled:" + this.chan6_scaled + " chan7_scaled:" + this.chan7_scaled + " chan8_scaled:" + this.chan8_scaled + " port:" + this.port + " rssi:" + this.rssi + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.chan1_scaled = paramMAVLinkPayload.getShort();
    this.chan2_scaled = paramMAVLinkPayload.getShort();
    this.chan3_scaled = paramMAVLinkPayload.getShort();
    this.chan4_scaled = paramMAVLinkPayload.getShort();
    this.chan5_scaled = paramMAVLinkPayload.getShort();
    this.chan6_scaled = paramMAVLinkPayload.getShort();
    this.chan7_scaled = paramMAVLinkPayload.getShort();
    this.chan8_scaled = paramMAVLinkPayload.getShort();
    this.port = paramMAVLinkPayload.getByte();
    this.rssi = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_rc_channels_scaled
 * JD-Core Version:    0.6.2
 */