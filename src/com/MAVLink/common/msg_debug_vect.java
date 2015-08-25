package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_debug_vect extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_DEBUG_VECT = 250;
  public static final int MAVLINK_MSG_LENGTH = 30;
  private static final long serialVersionUID = 250L;
  public byte[] name = new byte[10];
  public long time_usec;
  public float x;
  public float y;
  public float z;

  public msg_debug_vect()
  {
    this.msgid = 250;
  }

  public msg_debug_vect(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 250;
    unpack(paramMAVLinkPacket.payload);
  }

  public String getName()
  {
    String str = "";
    for (int i = 0; (i < 10) && (this.name[i] != 0); i++)
      str = str + (char)this.name[i];
    return str;
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 30;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 250;
    localMAVLinkPacket.payload.putLong(this.time_usec);
    localMAVLinkPacket.payload.putFloat(this.x);
    localMAVLinkPacket.payload.putFloat(this.y);
    localMAVLinkPacket.payload.putFloat(this.z);
    for (int i = 0; i < this.name.length; i++)
      localMAVLinkPacket.payload.putByte(this.name[i]);
    return localMAVLinkPacket;
  }

  public void setName(String paramString)
  {
    int i = Math.min(paramString.length(), 10);
    for (int j = 0; j < i; j++)
      this.name[j] = ((byte)paramString.charAt(j));
    for (int k = i; k < 10; k++)
      this.name[k] = 0;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_DEBUG_VECT - time_usec:" + this.time_usec + " x:" + this.x + " y:" + this.y + " z:" + this.z + " name:" + this.name + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_usec = paramMAVLinkPayload.getLong();
    this.x = paramMAVLinkPayload.getFloat();
    this.y = paramMAVLinkPayload.getFloat();
    this.z = paramMAVLinkPayload.getFloat();
    for (int i = 0; i < this.name.length; i++)
      this.name[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_debug_vect
 * JD-Core Version:    0.6.2
 */