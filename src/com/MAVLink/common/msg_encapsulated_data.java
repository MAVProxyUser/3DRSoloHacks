package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_encapsulated_data extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_ENCAPSULATED_DATA = 131;
  public static final int MAVLINK_MSG_LENGTH = 255;
  private static final long serialVersionUID = 131L;
  public byte[] data = new byte['ý'];
  public short seqnr;

  public msg_encapsulated_data()
  {
    this.msgid = 131;
  }

  public msg_encapsulated_data(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 131;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 255;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 131;
    localMAVLinkPacket.payload.putShort(this.seqnr);
    for (int i = 0; i < this.data.length; i++)
      localMAVLinkPacket.payload.putByte(this.data[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_ENCAPSULATED_DATA - seqnr:" + this.seqnr + " data:" + this.data + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.seqnr = paramMAVLinkPayload.getShort();
    for (int i = 0; i < this.data.length; i++)
      this.data[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_encapsulated_data
 * JD-Core Version:    0.6.2
 */