package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_timesync extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_TIMESYNC = 111;
  public static final int MAVLINK_MSG_LENGTH = 16;
  private static final long serialVersionUID = 111L;
  public long tc1;
  public long ts1;

  public msg_timesync()
  {
    this.msgid = 111;
  }

  public msg_timesync(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 111;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 16;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 111;
    localMAVLinkPacket.payload.putLong(this.tc1);
    localMAVLinkPacket.payload.putLong(this.ts1);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_TIMESYNC - tc1:" + this.tc1 + " ts1:" + this.ts1 + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.tc1 = paramMAVLinkPayload.getLong();
    this.ts1 = paramMAVLinkPayload.getLong();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_timesync
 * JD-Core Version:    0.6.2
 */