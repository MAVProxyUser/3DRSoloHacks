package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_sim_state extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SIM_STATE = 108;
  public static final int MAVLINK_MSG_LENGTH = 84;
  private static final long serialVersionUID = 108L;
  public float alt;
  public float lat;
  public float lon;
  public float pitch;
  public float q1;
  public float q2;
  public float q3;
  public float q4;
  public float roll;
  public float std_dev_horz;
  public float std_dev_vert;
  public float vd;
  public float ve;
  public float vn;
  public float xacc;
  public float xgyro;
  public float yacc;
  public float yaw;
  public float ygyro;
  public float zacc;
  public float zgyro;

  public msg_sim_state()
  {
    this.msgid = 108;
  }

  public msg_sim_state(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 108;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 84;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 108;
    localMAVLinkPacket.payload.putFloat(this.q1);
    localMAVLinkPacket.payload.putFloat(this.q2);
    localMAVLinkPacket.payload.putFloat(this.q3);
    localMAVLinkPacket.payload.putFloat(this.q4);
    localMAVLinkPacket.payload.putFloat(this.roll);
    localMAVLinkPacket.payload.putFloat(this.pitch);
    localMAVLinkPacket.payload.putFloat(this.yaw);
    localMAVLinkPacket.payload.putFloat(this.xacc);
    localMAVLinkPacket.payload.putFloat(this.yacc);
    localMAVLinkPacket.payload.putFloat(this.zacc);
    localMAVLinkPacket.payload.putFloat(this.xgyro);
    localMAVLinkPacket.payload.putFloat(this.ygyro);
    localMAVLinkPacket.payload.putFloat(this.zgyro);
    localMAVLinkPacket.payload.putFloat(this.lat);
    localMAVLinkPacket.payload.putFloat(this.lon);
    localMAVLinkPacket.payload.putFloat(this.alt);
    localMAVLinkPacket.payload.putFloat(this.std_dev_horz);
    localMAVLinkPacket.payload.putFloat(this.std_dev_vert);
    localMAVLinkPacket.payload.putFloat(this.vn);
    localMAVLinkPacket.payload.putFloat(this.ve);
    localMAVLinkPacket.payload.putFloat(this.vd);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SIM_STATE - q1:" + this.q1 + " q2:" + this.q2 + " q3:" + this.q3 + " q4:" + this.q4 + " roll:" + this.roll + " pitch:" + this.pitch + " yaw:" + this.yaw + " xacc:" + this.xacc + " yacc:" + this.yacc + " zacc:" + this.zacc + " xgyro:" + this.xgyro + " ygyro:" + this.ygyro + " zgyro:" + this.zgyro + " lat:" + this.lat + " lon:" + this.lon + " alt:" + this.alt + " std_dev_horz:" + this.std_dev_horz + " std_dev_vert:" + this.std_dev_vert + " vn:" + this.vn + " ve:" + this.ve + " vd:" + this.vd + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.q1 = paramMAVLinkPayload.getFloat();
    this.q2 = paramMAVLinkPayload.getFloat();
    this.q3 = paramMAVLinkPayload.getFloat();
    this.q4 = paramMAVLinkPayload.getFloat();
    this.roll = paramMAVLinkPayload.getFloat();
    this.pitch = paramMAVLinkPayload.getFloat();
    this.yaw = paramMAVLinkPayload.getFloat();
    this.xacc = paramMAVLinkPayload.getFloat();
    this.yacc = paramMAVLinkPayload.getFloat();
    this.zacc = paramMAVLinkPayload.getFloat();
    this.xgyro = paramMAVLinkPayload.getFloat();
    this.ygyro = paramMAVLinkPayload.getFloat();
    this.zgyro = paramMAVLinkPayload.getFloat();
    this.lat = paramMAVLinkPayload.getFloat();
    this.lon = paramMAVLinkPayload.getFloat();
    this.alt = paramMAVLinkPayload.getFloat();
    this.std_dev_horz = paramMAVLinkPayload.getFloat();
    this.std_dev_vert = paramMAVLinkPayload.getFloat();
    this.vn = paramMAVLinkPayload.getFloat();
    this.ve = paramMAVLinkPayload.getFloat();
    this.vd = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_sim_state
 * JD-Core Version:    0.6.2
 */