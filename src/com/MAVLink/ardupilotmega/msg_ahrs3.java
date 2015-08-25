package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_ahrs3 extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AHRS3 = 182;
  public static final int MAVLINK_MSG_LENGTH = 40;
  private static final long serialVersionUID = 182L;
  public float altitude;
  public int lat;
  public int lng;
  public float pitch;
  public float roll;
  public float v1;
  public float v2;
  public float v3;
  public float v4;
  public float yaw;

  public msg_ahrs3()
  {
    this.msgid = 182;
  }

  public msg_ahrs3(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 182;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 40;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 182;
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.altitude);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lng);
    localMAVLinkPacket.payload.putFloat(this.v1);
    localMAVLinkPacket.payload.putFloat(this.v2);
    localMAVLinkPacket.payload.putFloat(this.v3);
    localMAVLinkPacket.payload.putFloat(this.v4);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AHRS3 - roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " altitude:" + this.altitude + " lat:" + this.lat + " lng:" + this.lng + " v1:" + this.v1 + " v2:" + this.v2 + " v3:" + this.v3 + " v4:" + this.v4 + "";
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
    this.v1 = paramMAVLinkPayload.getFloat();
    this.v2 = paramMAVLinkPayload.getFloat();
    this.v3 = paramMAVLinkPayload.getFloat();
    this.v4 = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_ahrs3
 * JD-Core Version:    0.6.2
 */