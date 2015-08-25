package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_distance_sensor extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DISTANCE_SENSOR = 132;
  public static final int MAVLINK_MSG_LENGTH = 14;
  private static final long serialVersionUID = 132L;
  public byte covariance;
  public short current_distance;
  public byte id;
  public short max_distance;
  public short min_distance;
  public byte orientation;
  public int time_boot_ms;
  public byte type;

  public msg_distance_sensor()
  {
    this.msgid = 132;
  }

  public msg_distance_sensor(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 132;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 14;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 132;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putShort(this.min_distance);
    localMAVLinkPacket.payload.putShort(this.max_distance);
    localMAVLinkPacket.payload.putShort(this.current_distance);
    localMAVLinkPacket.payload.putByte(this.type);
    localMAVLinkPacket.payload.putByte(this.id);
    localMAVLinkPacket.payload.putByte(this.orientation);
    localMAVLinkPacket.payload.putByte(this.covariance);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DISTANCE_SENSOR - time_boot_ms:" + this.time_boot_ms + " min_distance:" + this.min_distance + " max_distance:" + this.max_distance + " current_distance:" + this.current_distance + " type:" + this.type + " id:" + this.id + " orientation:" + this.orientation + " covariance:" + this.covariance + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.min_distance = paramMAVLinkPayload.getShort();
    this.max_distance = paramMAVLinkPayload.getShort();
    this.current_distance = paramMAVLinkPayload.getShort();
    this.type = paramMAVLinkPayload.getByte();
    this.id = paramMAVLinkPayload.getByte();
    this.orientation = paramMAVLinkPayload.getByte();
    this.covariance = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_distance_sensor
 * JD-Core Version:    0.6.2
 */