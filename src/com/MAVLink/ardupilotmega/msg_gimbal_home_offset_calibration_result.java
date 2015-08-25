package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_home_offset_calibration_result extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_HOME_OFFSET_CALIBRATION_RESULT = 205;
  public static final int MAVLINK_MSG_LENGTH = 1;
  private static final long serialVersionUID = 205L;
  public byte calibration_result;

  public msg_gimbal_home_offset_calibration_result()
  {
    this.msgid = 205;
  }

  public msg_gimbal_home_offset_calibration_result(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 205;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 1;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 205;
    localMAVLinkPacket.payload.putByte(this.calibration_result);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_HOME_OFFSET_CALIBRATION_RESULT - calibration_result:" + this.calibration_result + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.calibration_result = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_home_offset_calibration_result
 * JD-Core Version:    0.6.2
 */