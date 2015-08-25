package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_terrain_data extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_TERRAIN_DATA = 134;
  public static final int MAVLINK_MSG_LENGTH = 43;
  private static final long serialVersionUID = 134L;
  public short[] data = new short[16];
  public short grid_spacing;
  public byte gridbit;
  public int lat;
  public int lon;

  public msg_terrain_data()
  {
    this.msgid = 134;
  }

  public msg_terrain_data(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 134;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 43;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 134;
    localMAVLinkPacket.payload.putInt(this.lat);
    localMAVLinkPacket.payload.putInt(this.lon);
    localMAVLinkPacket.payload.putShort(this.grid_spacing);
    for (int i = 0; i < this.data.length; i++)
      localMAVLinkPacket.payload.putShort(this.data[i]);
    localMAVLinkPacket.payload.putByte(this.gridbit);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_TERRAIN_DATA - lat:" + this.lat + " lon:" + this.lon + " grid_spacing:" + this.grid_spacing + " data:" + this.data + " gridbit:" + this.gridbit + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.lat = paramMAVLinkPayload.getInt();
    this.lon = paramMAVLinkPayload.getInt();
    this.grid_spacing = paramMAVLinkPayload.getShort();
    for (int i = 0; i < this.data.length; i++)
      this.data[i] = paramMAVLinkPayload.getShort();
    this.gridbit = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_terrain_data
 * JD-Core Version:    0.6.2
 */