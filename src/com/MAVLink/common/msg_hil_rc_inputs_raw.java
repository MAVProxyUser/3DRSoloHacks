package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hil_rc_inputs_raw extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW = 92;
  public static final int MAVLINK_MSG_LENGTH = 33;
  private static final long serialVersionUID = 92L;
  public short chan10_raw;
  public short chan11_raw;
  public short chan12_raw;
  public short chan1_raw;
  public short chan2_raw;
  public short chan3_raw;
  public short chan4_raw;
  public short chan5_raw;
  public short chan6_raw;
  public short chan7_raw;
  public short chan8_raw;
  public short chan9_raw;
  public byte rssi;
  public long time_usec;

  public msg_hil_rc_inputs_raw()
  {
    this.msgid = 92;
  }

  public msg_hil_rc_inputs_raw(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 92;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 33;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 92;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putShort(this.chan1_raw);
    localMAVLinkPacket.payload.putShort(this.chan2_raw);
    localMAVLinkPacket.payload.putShort(this.chan3_raw);
    localMAVLinkPacket.payload.putShort(this.chan4_raw);
    localMAVLinkPacket.payload.putShort(this.chan5_raw);
    localMAVLinkPacket.payload.putShort(this.chan6_raw);
    localMAVLinkPacket.payload.putShort(this.chan7_raw);
    localMAVLinkPacket.payload.putShort(this.chan8_raw);
    localMAVLinkPacket.payload.putShort(this.chan9_raw);
    localMAVLinkPacket.payload.putShort(this.chan10_raw);
    localMAVLinkPacket.payload.putShort(this.chan11_raw);
    localMAVLinkPacket.payload.putShort(this.chan12_raw);
    localMAVLinkPacket.payload.putByte(this.rssi);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW - time_usec:" + this.time_usec + " chan1_raw:" + this.chan1_raw + " chan2_raw:" + this.chan2_raw + " chan3_raw:" + this.chan3_raw + " chan4_raw:" + this.chan4_raw + " chan5_raw:" + this.chan5_raw + " chan6_raw:" + this.chan6_raw + " chan7_raw:" + this.chan7_raw + " chan8_raw:" + this.chan8_raw + " chan9_raw:" + this.chan9_raw + " chan10_raw:" + this.chan10_raw + " chan11_raw:" + this.chan11_raw + " chan12_raw:" + this.chan12_raw + " rssi:" + this.rssi + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.chan1_raw = paramMAVLinkPayload.getShort();
    this.chan2_raw = paramMAVLinkPayload.getShort();
    this.chan3_raw = paramMAVLinkPayload.getShort();
    this.chan4_raw = paramMAVLinkPayload.getShort();
    this.chan5_raw = paramMAVLinkPayload.getShort();
    this.chan6_raw = paramMAVLinkPayload.getShort();
    this.chan7_raw = paramMAVLinkPayload.getShort();
    this.chan8_raw = paramMAVLinkPayload.getShort();
    this.chan9_raw = paramMAVLinkPayload.getShort();
    this.chan10_raw = paramMAVLinkPayload.getShort();
    this.chan11_raw = paramMAVLinkPayload.getShort();
    this.chan12_raw = paramMAVLinkPayload.getShort();
    this.rssi = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_hil_rc_inputs_raw
 * JD-Core Version:    0.6.2
 */