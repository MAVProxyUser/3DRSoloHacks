package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_report_axis_calibration_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_REPORT_AXIS_CALIBRATION_STATUS = 212;
  public static final int MAVLINK_MSG_LENGTH = 3;
  private static final long serialVersionUID = 212L;
  public byte pitch_requires_calibration;
  public byte roll_requires_calibration;
  public byte yaw_requires_calibration;

  public msg_gimbal_report_axis_calibration_status()
  {
    this.msgid = 212;
  }

  public msg_gimbal_report_axis_calibration_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 212;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 3;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 212;
    localMAVLinkPacket.payload.putByte(this.yaw_requires_calibration);
    localMAVLinkPacket.payload.putByte(this.pitch_requires_calibration);
    localMAVLinkPacket.payload.putByte(this.roll_requires_calibration);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_REPORT_AXIS_CALIBRATION_STATUS - yaw_requires_calibration:" + this.yaw_requires_calibration + " pitch_requires_calibration:" + this.pitch_requires_calibration + " roll_requires_calibration:" + this.roll_requires_calibration + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.yaw_requires_calibration = paramMAVLinkPayload.getByte();
    this.pitch_requires_calibration = paramMAVLinkPayload.getByte();
    this.roll_requires_calibration = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_report_axis_calibration_status
 * JD-Core Version:    0.6.2
 */