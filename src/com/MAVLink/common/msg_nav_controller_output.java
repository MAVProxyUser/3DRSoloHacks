package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_nav_controller_output extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_NAV_CONTROLLER_OUTPUT = 62;
  public static final int MAVLINK_MSG_LENGTH = 26;
  private static final long serialVersionUID = 62L;
  public float alt_error;
  public float aspd_error;
  public short nav_bearing;
  public float nav_pitch;
  public float nav_roll;
  public short target_bearing;
  public short wp_dist;
  public float xtrack_error;

  public msg_nav_controller_output()
  {
    this.msgid = 62;
  }

  public msg_nav_controller_output(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 62;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 26;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 62;
    localMAVLinkPacket.payload.putFloat(this.nav_roll);
    localMAVLinkPacket.payload.putFloat(this.nav_pitch);
    localMAVLinkPacket.payload.putFloat(this.alt_error);
    localMAVLinkPacket.payload.putFloat(this.aspd_error);
    localMAVLinkPacket.payload.putFloat(this.xtrack_error);
    localMAVLinkPacket.payload.putShort(this.nav_bearing);
    localMAVLinkPacket.payload.putShort(this.target_bearing);
    localMAVLinkPacket.payload.putShort(this.wp_dist);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_NAV_CONTROLLER_OUTPUT - nav_roll:" + this.nav_roll + " nav_pitch:" + this.nav_pitch + " alt_error:" + this.alt_error + " aspd_error:" + this.aspd_error + " xtrack_error:" + this.xtrack_error + " nav_bearing:" + this.nav_bearing + " target_bearing:" + this.target_bearing + " wp_dist:" + this.wp_dist + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.nav_roll = paramMAVLinkPayload.getFloat();
    this.nav_pitch = paramMAVLinkPayload.getFloat();
    this.alt_error = paramMAVLinkPayload.getFloat();
    this.aspd_error = paramMAVLinkPayload.getFloat();
    this.xtrack_error = paramMAVLinkPayload.getFloat();
    this.nav_bearing = paramMAVLinkPayload.getShort();
    this.target_bearing = paramMAVLinkPayload.getShort();
    this.wp_dist = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_nav_controller_output
 * JD-Core Version:    0.6.2
 */