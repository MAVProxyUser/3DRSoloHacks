package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_hil_optical_flow extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIL_OPTICAL_FLOW = 114;
  public static final int MAVLINK_MSG_LENGTH = 44;
  private static final long serialVersionUID = 114L;
  public float distance;
  public float integrated_x;
  public float integrated_xgyro;
  public float integrated_y;
  public float integrated_ygyro;
  public float integrated_zgyro;
  public int integration_time_us;
  public byte quality;
  public byte sensor_id;
  public short temperature;
  public int time_delta_distance_us;
  public long time_usec;

  public msg_hil_optical_flow()
  {
    this.msgid = 114;
  }

  public msg_hil_optical_flow(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 114;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 44;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 114;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putInt(this.integration_time_us);
    localMAVLinkPacket.payload.putFloat(this.integrated_x);
    localMAVLinkPacket.payload.putFloat(this.integrated_y);
    localMAVLinkPacket.payload.putFloat(this.integrated_xgyro);
    localMAVLinkPacket.payload.putFloat(this.integrated_ygyro);
    localMAVLinkPacket.payload.putFloat(this.integrated_zgyro);
    localMAVLinkPacket.payload.putInt(this.time_delta_distance_us);
    localMAVLinkPacket.payload.putFloat(this.distance);
    localMAVLinkPacket.payload.putShort(this.temperature);
    localMAVLinkPacket.payload.putByte(this.sensor_id);
    localMAVLinkPacket.payload.putByte(this.quality);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIL_OPTICAL_FLOW - time_usec:" + this.time_usec + " integration_time_us:" + this.integration_time_us + " integrated_x:" + this.integrated_x + " integrated_y:" + this.integrated_y + " integrated_xgyro:" + this.integrated_xgyro + " integrated_ygyro:" + this.integrated_ygyro + " integrated_zgyro:" + this.integrated_zgyro + " time_delta_distance_us:" + this.time_delta_distance_us + " distance:" + this.distance + " temperature:" + this.temperature + " sensor_id:" + this.sensor_id + " quality:" + this.quality + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.integration_time_us = paramMAVLinkPayload.getInt();
    this.integrated_x = paramMAVLinkPayload.getFloat();
    this.integrated_y = paramMAVLinkPayload.getFloat();
    this.integrated_xgyro = paramMAVLinkPayload.getFloat();
    this.integrated_ygyro = paramMAVLinkPayload.getFloat();
    this.integrated_zgyro = paramMAVLinkPayload.getFloat();
    this.time_delta_distance_us = paramMAVLinkPayload.getInt();
    this.distance = paramMAVLinkPayload.getFloat();
    this.temperature = paramMAVLinkPayload.getShort();
    this.sensor_id = paramMAVLinkPayload.getByte();
    this.quality = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_hil_optical_flow
 * JD-Core Version:    0.6.2
 */