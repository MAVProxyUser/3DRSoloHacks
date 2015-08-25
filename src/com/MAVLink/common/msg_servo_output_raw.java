package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_servo_output_raw extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SERVO_OUTPUT_RAW = 36;
  public static final int MAVLINK_MSG_LENGTH = 21;
  private static final long serialVersionUID = 36L;
  public byte port;
  public short servo1_raw;
  public short servo2_raw;
  public short servo3_raw;
  public short servo4_raw;
  public short servo5_raw;
  public short servo6_raw;
  public short servo7_raw;
  public short servo8_raw;
  public int time_usec;

  public msg_servo_output_raw()
  {
    this.msgid = 36;
  }

  public msg_servo_output_raw(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 36;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 21;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 36;
    localMAVLinkPacket.payload.putInt(this.time_usec);
    localMAVLinkPacket.payload.putShort(this.servo1_raw);
    localMAVLinkPacket.payload.putShort(this.servo2_raw);
    localMAVLinkPacket.payload.putShort(this.servo3_raw);
    localMAVLinkPacket.payload.putShort(this.servo4_raw);
    localMAVLinkPacket.payload.putShort(this.servo5_raw);
    localMAVLinkPacket.payload.putShort(this.servo6_raw);
    localMAVLinkPacket.payload.putShort(this.servo7_raw);
    localMAVLinkPacket.payload.putShort(this.servo8_raw);
    localMAVLinkPacket.payload.putByte(this.port);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SERVO_OUTPUT_RAW - time_usec:" + this.time_usec + " servo1_raw:" + this.servo1_raw + " servo2_raw:" + this.servo2_raw + " servo3_raw:" + this.servo3_raw + " servo4_raw:" + this.servo4_raw + " servo5_raw:" + this.servo5_raw + " servo6_raw:" + this.servo6_raw + " servo7_raw:" + this.servo7_raw + " servo8_raw:" + this.servo8_raw + " port:" + this.port + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getInt();
    this.servo1_raw = paramMAVLinkPayload.getShort();
    this.servo2_raw = paramMAVLinkPayload.getShort();
    this.servo3_raw = paramMAVLinkPayload.getShort();
    this.servo4_raw = paramMAVLinkPayload.getShort();
    this.servo5_raw = paramMAVLinkPayload.getShort();
    this.servo6_raw = paramMAVLinkPayload.getShort();
    this.servo7_raw = paramMAVLinkPayload.getShort();
    this.servo8_raw = paramMAVLinkPayload.getShort();
    this.port = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_servo_output_raw
 * JD-Core Version:    0.6.2
 */