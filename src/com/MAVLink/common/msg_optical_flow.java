package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_optical_flow extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_OPTICAL_FLOW = 100;
  public static final int MAVLINK_MSG_LENGTH = 26;
  private static final long serialVersionUID = 100L;
  public float flow_comp_m_x;
  public float flow_comp_m_y;
  public short flow_x;
  public short flow_y;
  public float ground_distance;
  public byte quality;
  public byte sensor_id;
  public long time_usec;

  public msg_optical_flow()
  {
    this.msgid = 100;
  }

  public msg_optical_flow(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 100;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 26;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 100;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.flow_comp_m_x);
    localMAVLinkPacket.payload.putFloat(this.flow_comp_m_y);
    localMAVLinkPacket.payload.putFloat(this.ground_distance);
    localMAVLinkPacket.payload.putShort(this.flow_x);
    localMAVLinkPacket.payload.putShort(this.flow_y);
    localMAVLinkPacket.payload.putByte(this.sensor_id);
    localMAVLinkPacket.payload.putByte(this.quality);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_OPTICAL_FLOW - time_usec:" + this.time_usec + " flow_comp_m_x:" + this.flow_comp_m_x + " flow_comp_m_y:" + this.flow_comp_m_y + " ground_distance:" + this.ground_distance + " flow_x:" + this.flow_x + " flow_y:" + this.flow_y + " sensor_id:" + this.sensor_id + " quality:" + this.quality + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.flow_comp_m_x = paramMAVLinkPayload.getFloat();
    this.flow_comp_m_y = paramMAVLinkPayload.getFloat();
    this.ground_distance = paramMAVLinkPayload.getFloat();
    this.flow_x = paramMAVLinkPayload.getShort();
    this.flow_y = paramMAVLinkPayload.getShort();
    this.sensor_id = paramMAVLinkPayload.getByte();
    this.quality = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_optical_flow
 * JD-Core Version:    0.6.2
 */