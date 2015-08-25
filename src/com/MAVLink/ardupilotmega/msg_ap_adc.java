package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_ap_adc extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AP_ADC = 153;
  public static final int MAVLINK_MSG_LENGTH = 12;
  private static final long serialVersionUID = 153L;
  public short adc1;
  public short adc2;
  public short adc3;
  public short adc4;
  public short adc5;
  public short adc6;

  public msg_ap_adc()
  {
    this.msgid = 153;
  }

  public msg_ap_adc(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 153;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 12;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 153;
    localMAVLinkPacket.payload.putShort(this.adc1);
    localMAVLinkPacket.payload.putShort(this.adc2);
    localMAVLinkPacket.payload.putShort(this.adc3);
    localMAVLinkPacket.payload.putShort(this.adc4);
    localMAVLinkPacket.payload.putShort(this.adc5);
    localMAVLinkPacket.payload.putShort(this.adc6);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AP_ADC - adc1:" + this.adc1 + " adc2:" + this.adc2 + " adc3:" + this.adc3 + " adc4:" + this.adc4 + " adc5:" + this.adc5 + " adc6:" + this.adc6 + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.adc1 = paramMAVLinkPayload.getShort();
    this.adc2 = paramMAVLinkPayload.getShort();
    this.adc3 = paramMAVLinkPayload.getShort();
    this.adc4 = paramMAVLinkPayload.getShort();
    this.adc5 = paramMAVLinkPayload.getShort();
    this.adc6 = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_ap_adc
 * JD-Core Version:    0.6.2
 */