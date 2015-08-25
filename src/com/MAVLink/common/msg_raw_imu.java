package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_raw_imu extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RAW_IMU = 27;
  public static final int MAVLINK_MSG_LENGTH = 26;
  private static final long serialVersionUID = 27L;
  public long time_usec;
  public short xacc;
  public short xgyro;
  public short xmag;
  public short yacc;
  public short ygyro;
  public short ymag;
  public short zacc;
  public short zgyro;
  public short zmag;

  public msg_raw_imu()
  {
    this.msgid = 27;
  }

  public msg_raw_imu(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 27;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 26;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 27;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putShort(this.xacc);
    localMAVLinkPacket.payload.putShort(this.yacc);
    localMAVLinkPacket.payload.putShort(this.zacc);
    localMAVLinkPacket.payload.putShort(this.xgyro);
    localMAVLinkPacket.payload.putShort(this.ygyro);
    localMAVLinkPacket.payload.putShort(this.zgyro);
    localMAVLinkPacket.payload.putShort(this.xmag);
    localMAVLinkPacket.payload.putShort(this.ymag);
    localMAVLinkPacket.payload.putShort(this.zmag);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RAW_IMU - time_usec:" + this.time_usec + " xacc:" + this.xacc + " yacc:" + this.yacc + " zacc:" + this.zacc + " xgyro:" + this.xgyro + " ygyro:" + this.ygyro + " zgyro:" + this.zgyro + " xmag:" + this.xmag + " ymag:" + this.ymag + " zmag:" + this.zmag + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.xacc = paramMAVLinkPayload.getShort();
    this.yacc = paramMAVLinkPayload.getShort();
    this.zacc = paramMAVLinkPayload.getShort();
    this.xgyro = paramMAVLinkPayload.getShort();
    this.ygyro = paramMAVLinkPayload.getShort();
    this.zgyro = paramMAVLinkPayload.getShort();
    this.xmag = paramMAVLinkPayload.getShort();
    this.ymag = paramMAVLinkPayload.getShort();
    this.zmag = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_raw_imu
 * JD-Core Version:    0.6.2
 */