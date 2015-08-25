package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_fence_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_FENCE_STATUS = 162;
  public static final int MAVLINK_MSG_LENGTH = 8;
  private static final long serialVersionUID = 162L;
  public short breach_count;
  public byte breach_status;
  public int breach_time;
  public byte breach_type;

  public msg_fence_status()
  {
    this.msgid = 162;
  }

  public msg_fence_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 162;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 8;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 162;
    localMAVLinkPacket.payload.putInt(this.breach_time);
    localMAVLinkPacket.payload.putShort(this.breach_count);
    localMAVLinkPacket.payload.putByte(this.breach_status);
    localMAVLinkPacket.payload.putByte(this.breach_type);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_FENCE_STATUS - breach_time:" + this.breach_time + " breach_count:" + this.breach_count + " breach_status:" + this.breach_status + " breach_type:" + this.breach_type + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.breach_time = paramMAVLinkPayload.getInt();
    this.breach_count = paramMAVLinkPayload.getShort();
    this.breach_status = paramMAVLinkPayload.getByte();
    this.breach_type = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_fence_status
 * JD-Core Version:    0.6.2
 */