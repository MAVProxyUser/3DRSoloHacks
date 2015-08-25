package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gps_rtk extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GPS_RTK = 127;
  public static final int MAVLINK_MSG_LENGTH = 35;
  private static final long serialVersionUID = 127L;
  public int accuracy;
  public int baseline_a_mm;
  public int baseline_b_mm;
  public int baseline_c_mm;
  public byte baseline_coords_type;
  public int iar_num_hypotheses;
  public byte nsats;
  public byte rtk_health;
  public byte rtk_rate;
  public byte rtk_receiver_id;
  public int time_last_baseline_ms;
  public int tow;
  public short wn;

  public msg_gps_rtk()
  {
    this.msgid = 127;
  }

  public msg_gps_rtk(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 127;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 35;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 127;
    localMAVLinkPacket.payload.putInt(this.time_last_baseline_ms);
    localMAVLinkPacket.payload.putInt(this.tow);
    localMAVLinkPacket.payload.putInt(this.baseline_a_mm);
    localMAVLinkPacket.payload.putInt(this.baseline_b_mm);
    localMAVLinkPacket.payload.putInt(this.baseline_c_mm);
    localMAVLinkPacket.payload.putInt(this.accuracy);
    localMAVLinkPacket.payload.putInt(this.iar_num_hypotheses);
    localMAVLinkPacket.payload.putShort(this.wn);
    localMAVLinkPacket.payload.putByte(this.rtk_receiver_id);
    localMAVLinkPacket.payload.putByte(this.rtk_health);
    localMAVLinkPacket.payload.putByte(this.rtk_rate);
    localMAVLinkPacket.payload.putByte(this.nsats);
    localMAVLinkPacket.payload.putByte(this.baseline_coords_type);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GPS_RTK - time_last_baseline_ms:" + this.time_last_baseline_ms + " tow:" + this.tow + " baseline_a_mm:" + this.baseline_a_mm + " baseline_b_mm:" + this.baseline_b_mm + " baseline_c_mm:" + this.baseline_c_mm + " accuracy:" + this.accuracy + " iar_num_hypotheses:" + this.iar_num_hypotheses + " wn:" + this.wn + " rtk_receiver_id:" + this.rtk_receiver_id + " rtk_health:" + this.rtk_health + " rtk_rate:" + this.rtk_rate + " nsats:" + this.nsats + " baseline_coords_type:" + this.baseline_coords_type + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_last_baseline_ms = paramMAVLinkPayload.getInt();
    this.tow = paramMAVLinkPayload.getInt();
    this.baseline_a_mm = paramMAVLinkPayload.getInt();
    this.baseline_b_mm = paramMAVLinkPayload.getInt();
    this.baseline_c_mm = paramMAVLinkPayload.getInt();
    this.accuracy = paramMAVLinkPayload.getInt();
    this.iar_num_hypotheses = paramMAVLinkPayload.getInt();
    this.wn = paramMAVLinkPayload.getShort();
    this.rtk_receiver_id = paramMAVLinkPayload.getByte();
    this.rtk_health = paramMAVLinkPayload.getByte();
    this.rtk_rate = paramMAVLinkPayload.getByte();
    this.nsats = paramMAVLinkPayload.getByte();
    this.baseline_coords_type = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_gps_rtk
 * JD-Core Version:    0.6.2
 */