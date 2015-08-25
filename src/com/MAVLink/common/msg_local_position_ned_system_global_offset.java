package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_local_position_ned_system_global_offset extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOCAL_POSITION_NED_SYSTEM_GLOBAL_OFFSET = 89;
  public static final int MAVLINK_MSG_LENGTH = 28;
  private static final long serialVersionUID = 89L;
  public float pitch;
  public float roll;
  public int time_boot_ms;
  public float x;
  public float y;
  public float yaw;
  public float z;

  public msg_local_position_ned_system_global_offset()
  {
    this.msgid = 89;
  }

  public msg_local_position_ned_system_global_offset(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 89;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 28;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 89;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOCAL_POSITION_NED_SYSTEM_GLOBAL_OFFSET - time_boot_ms:" + this.time_boot_ms + " x:" + this.x + " y:" + this.y + " z:" + this.z + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_local_position_ned_system_global_offset
 * JD-Core Version:    0.6.2
 */