package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_log_entry extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LOG_ENTRY = 118;
  public static final int MAVLINK_MSG_LENGTH = 14;
  private static final long serialVersionUID = 118L;
  public short id;
  public short last_log_num;
  public short num_logs;
  public int size;
  public int time_utc;

  public msg_log_entry()
  {
    this.msgid = 118;
  }

  public msg_log_entry(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 118;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 14;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 118;
    localMAVLinkPacket.payload.putInt(this.time_utc);
    localMAVLinkPacket.payload.putInt(this.size);
    localMAVLinkPacket.payload.putShort(this.id);
    localMAVLinkPacket.payload.putShort(this.num_logs);
    localMAVLinkPacket.payload.putShort(this.last_log_num);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LOG_ENTRY - time_utc:" + this.time_utc + " size:" + this.size + " id:" + this.id + " num_logs:" + this.num_logs + " last_log_num:" + this.last_log_num + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_utc = paramMAVLinkPayload.getInt();
    this.size = paramMAVLinkPayload.getInt();
    this.id = paramMAVLinkPayload.getShort();
    this.num_logs = paramMAVLinkPayload.getShort();
    this.last_log_num = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_log_entry
 * JD-Core Version:    0.6.2
 */