package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_named_value_int extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_NAMED_VALUE_INT = 252;
  public static final int MAVLINK_MSG_LENGTH = 18;
  private static final long serialVersionUID = 252L;
  public byte[] name = new byte[10];
  public int time_boot_ms;
  public int value;

  public msg_named_value_int()
  {
    this.msgid = 252;
  }

  public msg_named_value_int(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 252;
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
    localMAVLinkPacket.len = 18;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 252;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putInt(this.value);
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
    return "MAVLINK_MSG_ID_NAMED_VALUE_INT - time_boot_ms:" + this.time_boot_ms + " value:" + this.value + " name:" + this.name + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.value = paramMAVLinkPayload.getInt();
    for (int i = 0; i < this.name.length; i++)
      this.name[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_named_value_int
 * JD-Core Version:    0.6.2
 */