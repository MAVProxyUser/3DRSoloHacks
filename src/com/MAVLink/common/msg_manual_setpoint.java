package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_manual_setpoint extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MANUAL_SETPOINT = 81;
  public static final int MAVLINK_MSG_LENGTH = 22;
  private static final long serialVersionUID = 81L;
  public byte manual_override_switch;
  public byte mode_switch;
  public float pitch;
  public float roll;
  public float thrust;
  public int time_boot_ms;
  public float yaw;

  public msg_manual_setpoint()
  {
    this.msgid = 81;
  }

  public msg_manual_setpoint(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 81;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 22;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 81;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.thrust);
    localMAVLinkPacket.payload.putByte(this.mode_switch);
    localMAVLinkPacket.payload.putByte(this.manual_override_switch);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MANUAL_SETPOINT - time_boot_ms:" + this.time_boot_ms + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " thrust:" + this.thrust + " mode_switch:" + this.mode_switch + " manual_override_switch:" + this.manual_override_switch + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.thrust = paramMAVLinkPayload.getFloat();
    this.mode_switch = paramMAVLinkPayload.getByte();
    this.manual_override_switch = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_manual_setpoint
 * JD-Core Version:    0.6.2
 */