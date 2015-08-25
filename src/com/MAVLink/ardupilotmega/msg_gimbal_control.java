package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_CONTROL = 201;
  public static final int MAVLINK_MSG_LENGTH = 14;
  private static final long serialVersionUID = 201L;
  public float demanded_rate_x;
  public float demanded_rate_y;
  public float demanded_rate_z;
  public byte target_component;
  public byte target_system;

  public msg_gimbal_control()
  {
    this.msgid = 201;
  }

  public msg_gimbal_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 201;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 14;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 201;
    localMAVLinkPacket.payload.putFloat(this.demanded_rate_x);
    localMAVLinkPacket.payload.putFloat(this.demanded_rate_y);
    localMAVLinkPacket.payload.putFloat(this.demanded_rate_z);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_CONTROL - demanded_rate_x:" + this.demanded_rate_x + " demanded_rate_y:" + this.demanded_rate_y + " demanded_rate_z:" + this.demanded_rate_z + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.demanded_rate_x = paramMAVLinkPayload.getFloat();
    this.demanded_rate_y = paramMAVLinkPayload.getFloat();
    this.demanded_rate_z = paramMAVLinkPayload.getFloat();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_control
 * JD-Core Version:    0.6.2
 */