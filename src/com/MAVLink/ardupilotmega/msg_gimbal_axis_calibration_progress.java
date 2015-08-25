package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_axis_calibration_progress extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_AXIS_CALIBRATION_PROGRESS = 203;
  public static final int MAVLINK_MSG_LENGTH = 3;
  private static final long serialVersionUID = 203L;
  public byte calibration_axis;
  public byte calibration_progress;
  public byte calibration_status;

  public msg_gimbal_axis_calibration_progress()
  {
    this.msgid = 203;
  }

  public msg_gimbal_axis_calibration_progress(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 203;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 3;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 203;
    localMAVLinkPacket.payload.putByte(this.calibration_axis);
    localMAVLinkPacket.payload.putByte(this.calibration_progress);
    localMAVLinkPacket.payload.putByte(this.calibration_status);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_AXIS_CALIBRATION_PROGRESS - calibration_axis:" + this.calibration_axis + " calibration_progress:" + this.calibration_progress + " calibration_status:" + this.calibration_status + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.calibration_axis = paramMAVLinkPayload.getByte();
    this.calibration_progress = paramMAVLinkPayload.getByte();
    this.calibration_status = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_axis_calibration_progress
 * JD-Core Version:    0.6.2
 */