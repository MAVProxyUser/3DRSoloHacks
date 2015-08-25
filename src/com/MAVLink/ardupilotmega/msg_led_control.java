package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_led_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LED_CONTROL = 186;
  public static final int MAVLINK_MSG_LENGTH = 29;
  private static final long serialVersionUID = 186L;
  public byte[] custom_bytes = new byte[24];
  public byte custom_len;
  public byte instance;
  public byte pattern;
  public byte target_component;
  public byte target_system;

  public msg_led_control()
  {
    this.msgid = 186;
  }

  public msg_led_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 186;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 29;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 186;
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.instance);
    localMAVLinkPacket.payload.putByte(this.pattern);
    localMAVLinkPacket.payload.putByte(this.custom_len);
    for (int i = 0; i < this.custom_bytes.length; i++)
      localMAVLinkPacket.payload.putByte(this.custom_bytes[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LED_CONTROL - target_system:" + this.target_system + " target_component:" + this.target_component + " instance:" + this.instance + " pattern:" + this.pattern + " custom_len:" + this.custom_len + " custom_bytes:" + this.custom_bytes + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.instance = paramMAVLinkPayload.getByte();
    this.pattern = paramMAVLinkPayload.getByte();
    this.custom_len = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.custom_bytes.length; i++)
      this.custom_bytes[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_led_control
 * JD-Core Version:    0.6.2
 */