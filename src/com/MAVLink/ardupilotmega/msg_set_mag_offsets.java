package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_set_mag_offsets extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SET_MAG_OFFSETS = 151;
  public static final int MAVLINK_MSG_LENGTH = 8;
  private static final long serialVersionUID = 151L;
  public short mag_ofs_x;
  public short mag_ofs_y;
  public short mag_ofs_z;
  public byte target_component;
  public byte target_system;

  public msg_set_mag_offsets()
  {
    this.msgid = 151;
  }

  public msg_set_mag_offsets(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 151;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 8;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 151;
    localMAVLinkPacket.payload.putShort(this.mag_ofs_x);
    localMAVLinkPacket.payload.putShort(this.mag_ofs_y);
    localMAVLinkPacket.payload.putShort(this.mag_ofs_z);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SET_MAG_OFFSETS - mag_ofs_x:" + this.mag_ofs_x + " mag_ofs_y:" + this.mag_ofs_y + " mag_ofs_z:" + this.mag_ofs_z + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.mag_ofs_x = paramMAVLinkPayload.getShort();
    this.mag_ofs_y = paramMAVLinkPayload.getShort();
    this.mag_ofs_z = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_set_mag_offsets
 * JD-Core Version:    0.6.2
 */