package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_digicam_configure extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DIGICAM_CONFIGURE = 154;
  public static final int MAVLINK_MSG_LENGTH = 15;
  private static final long serialVersionUID = 154L;
  public byte aperture;
  public byte command_id;
  public byte engine_cut_off;
  public byte exposure_type;
  public byte extra_param;
  public float extra_value;
  public byte iso;
  public byte mode;
  public short shutter_speed;
  public byte target_component;
  public byte target_system;

  public msg_digicam_configure()
  {
    this.msgid = 154;
  }

  public msg_digicam_configure(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 154;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 15;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 154;
    localMAVLinkPacket.payload.putFloat(this.extra_value);
    localMAVLinkPacket.payload.putShort(this.shutter_speed);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    localMAVLinkPacket.payload.putByte(this.mode);
    localMAVLinkPacket.payload.putByte(this.aperture);
    localMAVLinkPacket.payload.putByte(this.iso);
    localMAVLinkPacket.payload.putByte(this.exposure_type);
    localMAVLinkPacket.payload.putByte(this.command_id);
    localMAVLinkPacket.payload.putByte(this.engine_cut_off);
    localMAVLinkPacket.payload.putByte(this.extra_param);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DIGICAM_CONFIGURE - extra_value:" + this.extra_value + " shutter_speed:" + this.shutter_speed + " target_system:" + this.target_system + " target_component:" + this.target_component + " mode:" + this.mode + " aperture:" + this.aperture + " iso:" + this.iso + " exposure_type:" + this.exposure_type + " command_id:" + this.command_id + " engine_cut_off:" + this.engine_cut_off + " extra_param:" + this.extra_param + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.extra_value = paramMAVLinkPayload.getFloat();
    this.shutter_speed = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    this.mode = paramMAVLinkPayload.getByte();
    this.aperture = paramMAVLinkPayload.getByte();
    this.iso = paramMAVLinkPayload.getByte();
    this.exposure_type = paramMAVLinkPayload.getByte();
    this.command_id = paramMAVLinkPayload.getByte();
    this.engine_cut_off = paramMAVLinkPayload.getByte();
    this.extra_param = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_digicam_configure
 * JD-Core Version:    0.6.2
 */