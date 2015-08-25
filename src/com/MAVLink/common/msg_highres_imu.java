package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_highres_imu extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_HIGHRES_IMU = 105;
  public static final int MAVLINK_MSG_LENGTH = 62;
  private static final long serialVersionUID = 105L;
  public float abs_pressure;
  public float diff_pressure;
  public short fields_updated;
  public float pressure_alt;
  public float temperature;
  public long time_usec;
  public float xacc;
  public float xgyro;
  public float xmag;
  public float yacc;
  public float ygyro;
  public float ymag;
  public float zacc;
  public float zgyro;
  public float zmag;

  public msg_highres_imu()
  {
    this.msgid = 105;
  }

  public msg_highres_imu(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 105;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 62;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 105;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.xacc);
    localMAVLinkPacket.payload.putFloat(this.yacc);
    localMAVLinkPacket.payload.putFloat(this.zacc);
    localMAVLinkPacket.payload.putFloat(this.xgyro);
    localMAVLinkPacket.payload.putFloat(this.ygyro);
    localMAVLinkPacket.payload.putFloat(this.zgyro);
    localMAVLinkPacket.payload.putFloat(this.xmag);
    localMAVLinkPacket.payload.putFloat(this.ymag);
    localMAVLinkPacket.payload.putFloat(this.zmag);
    localMAVLinkPacket.payload.putFloat(this.abs_pressure);
    localMAVLinkPacket.payload.putFloat(this.diff_pressure);
    localMAVLinkPacket.payload.putFloat(this.pressure_alt);
    localMAVLinkPacket.payload.putFloat(this.temperature);
    localMAVLinkPacket.payload.putShort(this.fields_updated);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_HIGHRES_IMU - time_usec:" + this.time_usec + " xacc:" + this.xacc + " yacc:" + this.yacc + " zacc:" + this.zacc + " xgyro:" + this.xgyro + " ygyro:" + this.ygyro + " zgyro:" + this.zgyro + " xmag:" + this.xmag + " ymag:" + this.ymag + " zmag:" + this.zmag + " abs_pressure:" + this.abs_pressure + " diff_pressure:" + this.diff_pressure + " pressure_alt:" + this.pressure_alt + " temperature:" + this.temperature + " fields_updated:" + this.fields_updated + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.xacc = paramMAVLinkPayload.getFloat();
    this.yacc = paramMAVLinkPayload.getFloat();
    this.zacc = paramMAVLinkPayload.getFloat();
    this.xgyro = paramMAVLinkPayload.getFloat();
    this.ygyro = paramMAVLinkPayload.getFloat();
    this.zgyro = paramMAVLinkPayload.getFloat();
    this.xmag = paramMAVLinkPayload.getFloat();
    this.ymag = paramMAVLinkPayload.getFloat();
    this.zmag = paramMAVLinkPayload.getFloat();
    this.abs_pressure = paramMAVLinkPayload.getFloat();
    this.diff_pressure = paramMAVLinkPayload.getFloat();
    this.pressure_alt = paramMAVLinkPayload.getFloat();
    this.temperature = paramMAVLinkPayload.getFloat();
    this.fields_updated = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_highres_imu
 * JD-Core Version:    0.6.2
 */