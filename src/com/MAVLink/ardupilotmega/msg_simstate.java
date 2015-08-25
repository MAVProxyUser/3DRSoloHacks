package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_simstate extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SIMSTATE = 164;
  public static final int MAVLINK_MSG_LENGTH = 44;
  private static final long serialVersionUID = 164L;
  public int lat;
  public int lng;
  public float pitch;
  public float roll;
  public float xacc;
  public float xgyro;
  public float yacc;
  public float yaw;
  public float ygyro;
  public float zacc;
  public float zgyro;

  public msg_simstate()
  {
    this.msgid = 164;
  }

  public msg_simstate(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 164;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 44;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 164;
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.xacc);
    localMAVLinkPacket.payload.putFloat(this.yacc);
    localMAVLinkPacket.payload.putFloat(this.zacc);
    localMAVLinkPacket.payload.putFloat(this.xgyro);
    localMAVLinkPacket.payload.putFloat(this.ygyro);
    localMAVLinkPacket.payload.putFloat(this.zgyro);
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lng);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SIMSTATE - roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " xacc:" + this.xacc + " yacc:" + this.yacc + " zacc:" + this.zacc + " xgyro:" + this.xgyro + " ygyro:" + this.ygyro + " zgyro:" + this.zgyro + " lat:" + this.lat + " lng:" + this.lng + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.xacc = paramMAVLinkPayload.getFloat();
    this.yacc = paramMAVLinkPayload.getFloat();
    this.zacc = paramMAVLinkPayload.getFloat();
    this.xgyro = paramMAVLinkPayload.getFloat();
    this.ygyro = paramMAVLinkPayload.getFloat();
    this.zgyro = paramMAVLinkPayload.getFloat();
    this.lat = paramMAVLinkPayload.getInt();
    this.lng = paramMAVLinkPayload.getInt();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_simstate
 * JD-Core Version:    0.6.2
 */