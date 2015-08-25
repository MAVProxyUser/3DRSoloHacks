package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_factory_parameters_loaded extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_FACTORY_PARAMETERS_LOADED = 207;
  public static final int MAVLINK_MSG_LENGTH = 1;
  private static final long serialVersionUID = 207L;
  public byte dummy;

  public msg_gimbal_factory_parameters_loaded()
  {
    this.msgid = 207;
  }

  public msg_gimbal_factory_parameters_loaded(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 207;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 1;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 207;
    localMAVLinkPacket.payload.putByte(this.dummy);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_FACTORY_PARAMETERS_LOADED - dummy:" + this.dummy + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.dummy = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_factory_parameters_loaded
 * JD-Core Version:    0.6.2
 */