package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_compassmot_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_COMPASSMOT_STATUS = 177;
  public static final int MAVLINK_MSG_LENGTH = 20;
  private static final long serialVersionUID = 177L;
  public float CompensationX;
  public float CompensationY;
  public float CompensationZ;
  public float current;
  public short interference;
  public short throttle;

  public msg_compassmot_status()
  {
    this.msgid = 177;
  }

  public msg_compassmot_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 177;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 20;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 177;
    localMAVLinkPacket.payload.putFloat(this.current);
    localMAVLinkPacket.payload.putFloat(this.CompensationX);
    localMAVLinkPacket.payload.putFloat(this.CompensationY);
    localMAVLinkPacket.payload.putFloat(this.CompensationZ);
    localMAVLinkPacket.payload.putShort(this.throttle);
    localMAVLinkPacket.payload.putShort(this.interference);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_COMPASSMOT_STATUS - current:" + this.current + " CompensationX:" + this.CompensationX + " CompensationY:" + this.CompensationY + " CompensationZ:" + this.CompensationZ + " throttle:" + this.throttle + " interference:" + this.interference + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.current = paramMAVLinkPayload.getFloat();
    this.CompensationX = paramMAVLinkPayload.getFloat();
    this.CompensationY = paramMAVLinkPayload.getFloat();
    this.CompensationZ = paramMAVLinkPayload.getFloat();
    this.throttle = paramMAVLinkPayload.getShort();
    this.interference = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_compassmot_status
 * JD-Core Version:    0.6.2
 */