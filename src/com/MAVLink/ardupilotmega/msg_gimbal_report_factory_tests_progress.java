package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_report_factory_tests_progress extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_REPORT_FACTORY_TESTS_PROGRESS = 210;
  public static final int MAVLINK_MSG_LENGTH = 4;
  private static final long serialVersionUID = 210L;
  public byte test;
  public byte test_section;
  public byte test_section_progress;
  public byte test_status;

  public msg_gimbal_report_factory_tests_progress()
  {
    this.msgid = 210;
  }

  public msg_gimbal_report_factory_tests_progress(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 210;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 4;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 210;
    localMAVLinkPacket.payload.putByte(this.test);
    localMAVLinkPacket.payload.putByte(this.test_section);
    localMAVLinkPacket.payload.putByte(this.test_section_progress);
    localMAVLinkPacket.payload.putByte(this.test_status);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_REPORT_FACTORY_TESTS_PROGRESS - test:" + this.test + " test_section:" + this.test_section + " test_section_progress:" + this.test_section_progress + " test_status:" + this.test_status + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.test = paramMAVLinkPayload.getByte();
    this.test_section = paramMAVLinkPayload.getByte();
    this.test_section_progress = paramMAVLinkPayload.getByte();
    this.test_status = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_report_factory_tests_progress
 * JD-Core Version:    0.6.2
 */