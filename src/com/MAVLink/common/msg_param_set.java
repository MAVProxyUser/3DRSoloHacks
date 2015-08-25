package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_param_set extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_PARAM_SET = 23;
  public static final int MAVLINK_MSG_LENGTH = 23;
  private static final long serialVersionUID = 23L;
  public byte[] param_id = new byte[16];
  public byte param_type;
  public float param_value;
  public byte target_component;
  public byte target_system;

  public msg_param_set()
  {
    this.msgid = 23;
  }

  public msg_param_set(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 23;
    unpack(paramMAVLinkPacket.payload);
  }

  public String getParam_Id()
  {
    String str = "";
    for (int i = 0; (i < 16) && (this.param_id[i] != 0); i++)
      str = str + (char)this.param_id[i];
    return str;
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 23;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 23;
    localMAVLinkPacket.payload.putFloat(this.param_value);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    for (int i = 0; i < this.param_id.length; i++)
      localMAVLinkPacket.payload.putByte(this.param_id[i]);
    localMAVLinkPacket.payload.putByte(this.param_type);
    return localMAVLinkPacket;
  }

  public void setParam_Id(String paramString)
  {
    int i = Math.min(paramString.length(), 16);
    for (int j = 0; j < i; j++)
      this.param_id[j] = ((byte)paramString.charAt(j));
    for (int k = i; k < 16; k++)
      this.param_id[k] = 0;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_PARAM_SET - param_value:" + this.param_value + " target_system:" + this.target_system + " target_component:" + this.target_component + " param_id:" + this.param_id + " param_type:" + this.param_type + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.param_value = paramMAVLinkPayload.getFloat();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.param_id.length; i++)
      this.param_id[i] = paramMAVLinkPayload.getByte();
    this.param_type = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_param_set
 * JD-Core Version:    0.6.2
 */