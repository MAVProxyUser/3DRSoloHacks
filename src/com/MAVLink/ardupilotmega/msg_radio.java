package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_radio extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_RADIO = 166;
  public static final int MAVLINK_MSG_LENGTH = 9;
  private static final long serialVersionUID = 166L;
  public short fixed;
  public byte noise;
  public byte remnoise;
  public byte remrssi;
  public byte rssi;
  public short rxerrors;
  public byte txbuf;

  public msg_radio()
  {
    this.msgid = 166;
  }

  public msg_radio(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 166;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 9;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 166;
    localMAVLinkPacket.payload.putShort(this.rxerrors);
    localMAVLinkPacket.payload.putShort(this.fixed);
    localMAVLinkPacket.payload.putByte(this.rssi);
    localMAVLinkPacket.payload.putByte(this.remrssi);
    localMAVLinkPacket.payload.putByte(this.txbuf);
    localMAVLinkPacket.payload.putByte(this.noise);
    localMAVLinkPacket.payload.putByte(this.remnoise);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_RADIO - rxerrors:" + this.rxerrors + " fixed:" + this.fixed + " rssi:" + this.rssi + " remrssi:" + this.remrssi + " txbuf:" + this.txbuf + " noise:" + this.noise + " remnoise:" + this.remnoise + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.rxerrors = paramMAVLinkPayload.getShort();
    this.fixed = paramMAVLinkPayload.getShort();
    this.rssi = paramMAVLinkPayload.getByte();
    this.remrssi = paramMAVLinkPayload.getByte();
    this.txbuf = paramMAVLinkPayload.getByte();
    this.noise = paramMAVLinkPayload.getByte();
    this.remnoise = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_radio
 * JD-Core Version:    0.6.2
 */