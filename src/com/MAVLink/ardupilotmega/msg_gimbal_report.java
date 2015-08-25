package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gimbal_report extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GIMBAL_REPORT = 200;
  public static final int MAVLINK_MSG_LENGTH = 42;
  private static final long serialVersionUID = 200L;
  public float delta_angle_x;
  public float delta_angle_y;
  public float delta_angle_z;
  public float delta_time;
  public float delta_velocity_x;
  public float delta_velocity_y;
  public float delta_velocity_z;
  public float joint_az;
  public float joint_el;
  public float joint_roll;
  public byte target_component;
  public byte target_system;

  public msg_gimbal_report()
  {
    this.msgid = 200;
  }

  public msg_gimbal_report(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 200;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 42;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 200;
    localMAVLinkPacket.payload.putFloat(this.delta_time);
    localMAVLinkPacket.payload.putFloat(this.delta_angle_x);
    localMAVLinkPacket.payload.putFloat(this.delta_angle_y);
    localMAVLinkPacket.payload.putFloat(this.delta_angle_z);
    localMAVLinkPacket.payload.putFloat(this.delta_velocity_x);
    localMAVLinkPacket.payload.putFloat(this.delta_velocity_y);
    localMAVLinkPacket.payload.putFloat(this.delta_velocity_z);
    localMAVLinkPacket.payload.putFloat(this.joint_roll);
    localMAVLinkPacket.payload.putFloat(this.joint_el);
    localMAVLinkPacket.payload.putFloat(this.joint_az);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GIMBAL_REPORT - delta_time:" + this.delta_time + " delta_angle_x:" + this.delta_angle_x + " delta_angle_y:" + this.delta_angle_y + " delta_angle_z:" + this.delta_angle_z + " delta_velocity_x:" + this.delta_velocity_x + " delta_velocity_y:" + this.delta_velocity_y + " delta_velocity_z:" + this.delta_velocity_z + " joint_roll:" + this.joint_roll + " joint_el:" + this.joint_el + " joint_az:" + this.joint_az + " target_system:" + this.target_system + " target_component:" + this.target_component + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.delta_time = paramMAVLinkPayload.getFloat();
    this.delta_angle_x = paramMAVLinkPayload.getFloat();
    this.delta_angle_y = paramMAVLinkPayload.getFloat();
    this.delta_angle_z = paramMAVLinkPayload.getFloat();
    this.delta_velocity_x = paramMAVLinkPayload.getFloat();
    this.delta_velocity_y = paramMAVLinkPayload.getFloat();
    this.delta_velocity_z = paramMAVLinkPayload.getFloat();
    this.joint_roll = paramMAVLinkPayload.getFloat();
    this.joint_el = paramMAVLinkPayload.getFloat();
    this.joint_az = paramMAVLinkPayload.getFloat();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_gimbal_report
 * JD-Core Version:    0.6.2
 */