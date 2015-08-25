package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_digicam_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DIGICAM_CONTROL = 155;
  public static final int MAVLINK_MSG_LENGTH = 13;
  private static final long serialVersionUID = 155L;
  public byte command_id;
  public byte extra_param;
  public float extra_value;
  public byte focus_lock;
  public byte session;
  public byte shot;
  public byte target_component;
  public byte target_system;
  public byte zoom_pos;
  public byte zoom_step;

  public msg_digicam_control()
  {
    this.msgid = 155;
  }

  public msg_digicam_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 155;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 13;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 155;
    localMAVLinkPacket.payload.putFloat(this.extra_value);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.session);
    localMAVLinkPacket.payload.putByte(this.zoom_pos);
    localMAVLinkPacket.payload.putByte(this.zoom_step);
    localMAVLinkPacket.payload.putByte(this.focus_lock);
    localMAVLinkPacket.payload.putByte(this.shot);
    localMAVLinkPacket.payload.putByte(this.command_id);
    localMAVLinkPacket.payload.putByte(this.extra_param);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DIGICAM_CONTROL - extra_value:" + this.extra_value + " target_system:" + this.target_system + " target_component:" + this.target_component + " session:" + this.session + " zoom_pos:" + this.zoom_pos + " zoom_step:" + this.zoom_step + " focus_lock:" + this.focus_lock + " shot:" + this.shot + " command_id:" + this.command_id + " extra_param:" + this.extra_param + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.extra_value = paramMAVLinkPayload.getFloat();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.session = paramMAVLinkPayload.getByte();
    this.zoom_pos = paramMAVLinkPayload.getByte();
    this.zoom_step = paramMAVLinkPayload.getByte();
    this.focus_lock = paramMAVLinkPayload.getByte();
    this.shot = paramMAVLinkPayload.getByte();
    this.command_id = paramMAVLinkPayload.getByte();
    this.extra_param = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_digicam_control
 * JD-Core Version:    0.6.2
 */