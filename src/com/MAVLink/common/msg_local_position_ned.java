package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_local_position_ned extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOCAL_POSITION_NED = 32;
  public static final int MAVLINK_MSG_LENGTH = 28;
  private static final long serialVersionUID = 32L;
  public int time_boot_ms;
  public float vx;
  public float vy;
  public float vz;
  public float x;
  public float y;
  public float z;

  public msg_local_position_ned()
  {
    this.msgid = 32;
  }

  public msg_local_position_ned(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 32;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 28;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 32;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    localMAVLinkPacket.payload.putFloat(this.vx);
    localMAVLinkPacket.payload.putFloat(this.vy);
    localMAVLinkPacket.payload.putFloat(this.vz);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOCAL_POSITION_NED - time_boot_ms:" + this.time_boot_ms + " x:" + this.x + " y:" + this.y + " z:" + this.z + " vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
    this.vx = paramMAVLinkPayload.getFloat();
    this.vy = paramMAVLinkPayload.getFloat();
    this.vz = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_local_position_ned
 * JD-Core Version:    0.6.2
 */