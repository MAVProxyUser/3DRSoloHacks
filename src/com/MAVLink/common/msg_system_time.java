package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_system_time extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SYSTEM_TIME = 2;
  public static final int MAVLINK_MSG_LENGTH = 12;
  private static final long serialVersionUID = 2L;
  public int time_boot_ms;
  public long time_unix_usec;

  public msg_system_time()
  {
    this.msgid = 2;
  }

  public msg_system_time(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 2;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 12;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 2;
    localMAVLinkPacket.payload.putLong(this.time_unix_usec);
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SYSTEM_TIME - time_unix_usec:" + this.time_unix_usec + " time_boot_ms:" + this.time_boot_ms + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_unix_usec = paramMAVLinkPayload.getLong();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_system_time
 * JD-Core Version:    0.6.2
 */