package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_ahrs extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AHRS = 163;
  public static final int MAVLINK_MSG_LENGTH = 28;
  private static final long serialVersionUID = 163L;
  public float accel_weight;
  public float error_rp;
  public float error_yaw;
  public float omegaIx;
  public float omegaIy;
  public float omegaIz;
  public float renorm_val;

  public msg_ahrs()
  {
    this.msgid = 163;
  }

  public msg_ahrs(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 163;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 28;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 163;
    localMAVLinkPacket.payload.putFloat(this.omegaIx);
    localMAVLinkPacket.payload.putFloat(this.omegaIy);
    localMAVLinkPacket.payload.putFloat(this.omegaIz);
    localMAVLinkPacket.payload.putFloat(this.accel_weight);
    localMAVLinkPacket.payload.putFloat(this.renorm_val);
    localMAVLinkPacket.payload.putFloat(this.error_rp);
    localMAVLinkPacket.payload.putFloat(this.error_yaw);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AHRS - omegaIx:" + this.omegaIx + " omegaIy:" + this.omegaIy + " omegaIz:" + this.omegaIz + " accel_weight:" + this.accel_weight + " renorm_val:" + this.renorm_val + " error_rp:" + this.error_rp + " error_yaw:" + this.error_yaw + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.omegaIx = paramMAVLinkPayload.getFloat();
    this.omegaIy = paramMAVLinkPayload.getFloat();
    this.omegaIz = paramMAVLinkPayload.getFloat();
    this.accel_weight = paramMAVLinkPayload.getFloat();
    this.renorm_val = paramMAVLinkPayload.getFloat();
    this.error_rp = paramMAVLinkPayload.getFloat();
    this.error_yaw = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_ahrs
 * JD-Core Version:    0.6.2
 */