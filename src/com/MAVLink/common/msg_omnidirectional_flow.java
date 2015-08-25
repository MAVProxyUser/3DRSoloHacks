package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_omnidirectional_flow extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_OMNIDIRECTIONAL_FLOW = 106;
  public static final int MAVLINK_MSG_LENGTH = 54;
  private static final long serialVersionUID = 106L;
  public float front_distance_m;
  public short[] left = new short[10];
  public byte quality;
  public short[] right = new short[10];
  public byte sensor_id;
  public long time_usec;

  public msg_omnidirectional_flow()
  {
    this.msgid = 106;
  }

  public msg_omnidirectional_flow(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 106;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 54;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 106;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.front_distance_m);
    for (int i = 0; i < this.left.length; i++)
      localMAVLinkPacket.payload.putShort(this.left[i]);
    for (int j = 0; j < this.right.length; j++)
      localMAVLinkPacket.payload.putShort(this.right[j]);
    localMAVLinkPacket.payload.putByte(this.sensor_id);
    localMAVLinkPacket.payload.putByte(this.quality);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_OMNIDIRECTIONAL_FLOW - time_usec:" + this.time_usec + " front_distance_m:" + this.front_distance_m + " left:" + this.left + " right:" + this.right + " sensor_id:" + this.sensor_id + " quality:" + this.quality + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.front_distance_m = paramMAVLinkPayload.getFloat();
    for (int i = 0; i < this.left.length; i++)
      this.left[i] = paramMAVLinkPayload.getShort();
    for (int j = 0; j < this.right.length; j++)
      this.right[j] = paramMAVLinkPayload.getShort();
    this.sensor_id = paramMAVLinkPayload.getByte();
    this.quality = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_omnidirectional_flow
 * JD-Core Version:    0.6.2
 */