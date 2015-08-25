package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_named_value_float extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_NAMED_VALUE_FLOAT = 251;
  public static final int MAVLINK_MSG_LENGTH = 18;
  private static final long serialVersionUID = 251L;
  public byte[] name = new byte[10];
  public int time_boot_ms;
  public float value;

  public msg_named_value_float()
  {
    this.msgid = 251;
  }

  public msg_named_value_float(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 251;
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
    localMAVLinkPacket.msgid = 251;
    localMAVLinkPacket.payload.putInt(this.time_boot_ms);
    localMAVLinkPacket.payload.putFloat(this.value);
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
    return "MAVLINK_MSG_ID_NAMED_VALUE_FLOAT - time_boot_ms:" + this.time_boot_ms + " value:" + this.value + " name:" + this.name + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.time_boot_ms = paramMAVLinkPayload.getInt();
    this.value = paramMAVLinkPayload.getFloat();
    for (int i = 0; i < this.name.length; i++)
      this.name[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_named_value_float
 * JD-Core Version:    0.6.2
 */