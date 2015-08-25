package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_ahrs2 extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AHRS2 = 178;
  public static final int MAVLINK_MSG_LENGTH = 24;
  private static final long serialVersionUID = 178L;
  public float altitude;
  public int lat;
  public int lng;
  public float pitch;
  public float roll;
  public float yaw;

  public msg_ahrs2()
  {
    this.msgid = 178;
  }

  public msg_ahrs2(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 178;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 24;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 178;
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.altitude);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lng);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AHRS2 - roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " altitude:" + this.altitude + " lat:" + this.lat + " lng:" + this.lng + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.altitude = paramMAVLinkPayload.getFloat();
    this.lat = paramMAVLinkPayload.getInt();
    this.lng = paramMAVLinkPayload.getInt();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_ahrs2
 * JD-Core Version:    0.6.2
 */