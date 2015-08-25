package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_airspeed_autocal extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AIRSPEED_AUTOCAL = 174;
  public static final int MAVLINK_MSG_LENGTH = 48;
  private static final long serialVersionUID = 174L;
  public float EAS2TAS;
  public float Pax;
  public float Pby;
  public float Pcz;
  public float diff_pressure;
  public float ratio;
  public float state_x;
  public float state_y;
  public float state_z;
  public float vx;
  public float vy;
  public float vz;

  public msg_airspeed_autocal()
  {
    this.msgid = 174;
  }

  public msg_airspeed_autocal(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 174;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 48;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 174;
    localMAVLinkPacket.payload.putFloat(this.vx);
    localMAVLinkPacket.payload.putFloat(this.vy);
    localMAVLinkPacket.payload.putFloat(this.vz);
    localMAVLinkPacket.payload.putFloat(this.diff_pressure);
    localMAVLinkPacket.payload.putFloat(this.EAS2TAS);
    localMAVLinkPacket.payload.putFloat(this.ratio);
    localMAVLinkPacket.payload.putFloat(this.state_x);
    localMAVLinkPacket.payload.putFloat(this.state_y);
    localMAVLinkPacket.payload.putFloat(this.state_z);
    localMAVLinkPacket.payload.putFloat(this.Pax);
    localMAVLinkPacket.payload.putFloat(this.Pby);
    localMAVLinkPacket.payload.putFloat(this.Pcz);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AIRSPEED_AUTOCAL - vx:" + this.vx + " vy:" + this.vy + " vz:" + this.vz + " diff_pressure:" + this.diff_pressure + " EAS2TAS:" + this.EAS2TAS + " ratio:" + this.ratio + " state_x:" + this.state_x + " state_y:" + this.state_y + " state_z:" + this.state_z + " Pax:" + this.Pax + " Pby:" + this.Pby + " Pcz:" + this.Pcz + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.vx = paramMAVLinkPayload.getFloat();
    this.vy = paramMAVLinkPayload.getFloat();
    this.vz = paramMAVLinkPayload.getFloat();
    this.diff_pressure = paramMAVLinkPayload.getFloat();
    this.EAS2TAS = paramMAVLinkPayload.getFloat();
    this.ratio = paramMAVLinkPayload.getFloat();
    this.state_x = paramMAVLinkPayload.getFloat();
    this.state_y = paramMAVLinkPayload.getFloat();
    this.state_z = paramMAVLinkPayload.getFloat();
    this.Pax = paramMAVLinkPayload.getFloat();
    this.Pby = paramMAVLinkPayload.getFloat();
    this.Pcz = paramMAVLinkPayload.getFloat();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_airspeed_autocal
 * JD-Core Version:    0.6.2
 */