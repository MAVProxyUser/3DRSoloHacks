package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_gps_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_GPS_STATUS = 25;
  public static final int MAVLINK_MSG_LENGTH = 101;
  private static final long serialVersionUID = 25L;
  public byte[] satellite_azimuth = new byte[20];
  public byte[] satellite_elevation = new byte[20];
  public byte[] satellite_prn = new byte[20];
  public byte[] satellite_snr = new byte[20];
  public byte[] satellite_used = new byte[20];
  public byte satellites_visible;

  public msg_gps_status()
  {
    this.msgid = 25;
  }

  public msg_gps_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 25;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 101;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 25;
    localMAVLinkPacket.payload.putByte(this.satellites_visible);
    for (int i = 0; i < this.satellite_prn.length; i++)
      localMAVLinkPacket.payload.putByte(this.satellite_prn[i]);
    for (int j = 0; j < this.satellite_used.length; j++)
      localMAVLinkPacket.payload.putByte(this.satellite_used[j]);
    for (int k = 0; k < this.satellite_elevation.length; k++)
      localMAVLinkPacket.payload.putByte(this.satellite_elevation[k]);
    for (int m = 0; m < this.satellite_azimuth.length; m++)
      localMAVLinkPacket.payload.putByte(this.satellite_azimuth[m]);
    for (int n = 0; n < this.satellite_snr.length; n++)
      localMAVLinkPacket.payload.putByte(this.satellite_snr[n]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_GPS_STATUS - satellites_visible:" + this.satellites_visible + " satellite_prn:" + this.satellite_prn + " satellite_used:" + this.satellite_used + " satellite_elevation:" + this.satellite_elevation + " satellite_azimuth:" + this.satellite_azimuth + " satellite_snr:" + this.satellite_snr + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.satellites_visible = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.satellite_prn.length; i++)
      this.satellite_prn[i] = paramMAVLinkPayload.getByte();
    for (int j = 0; j < this.satellite_used.length; j++)
      this.satellite_used[j] = paramMAVLinkPayload.getByte();
    for (int k = 0; k < this.satellite_elevation.length; k++)
      this.satellite_elevation[k] = paramMAVLinkPayload.getByte();
    for (int m = 0; m < this.satellite_azimuth.length; m++)
      this.satellite_azimuth[m] = paramMAVLinkPayload.getByte();
    for (int n = 0; n < this.satellite_snr.length; n++)
      this.satellite_snr[n] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_gps_status
 * JD-Core Version:    0.6.2
 */