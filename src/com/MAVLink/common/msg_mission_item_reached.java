package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mission_item_reached extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MISSION_ITEM_REACHED = 46;
  public static final int MAVLINK_MSG_LENGTH = 2;
  private static final long serialVersionUID = 46L;
  public short seq;

  public msg_mission_item_reached()
  {
    this.msgid = 46;
  }

  public msg_mission_item_reached(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 46;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 2;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 46;
    localMAVLinkPacket.payload.putShort(this.seq);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MISSION_ITEM_REACHED - seq:" + this.seq + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.seq = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_mission_item_reached
 * JD-Core Version:    0.6.2
 */