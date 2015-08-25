package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_param_value extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_PARAM_VALUE = 22;
  public static final int MAVLINK_MSG_LENGTH = 25;
  private static final long serialVersionUID = 22L;
  public short param_count;
  public byte[] param_id = new byte[16];
  public short param_index;
  public byte param_type;
  public float param_value;

  public msg_param_value()
  {
    this.msgid = 22;
  }

  public msg_param_value(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 22;
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
    localMAVLinkPacket.len = 25;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 22;
    localMAVLinkPacket.payload.putFloat(this.param_value);
    localMAVLinkPacket.payload.putShort(this.param_count);
    localMAVLinkPacket.payload.putShort(this.param_index);
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
    return "MAVLINK_MSG_ID_PARAM_VALUE - param_value:" + this.param_value + " param_count:" + this.param_count + " param_index:" + this.param_index + " param_id:" + this.param_id + " param_type:" + this.param_type + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.param_value = paramMAVLinkPayload.getFloat();
    this.param_count = paramMAVLinkPayload.getShort();
    this.param_index = paramMAVLinkPayload.getShort();
    for (int i = 0; i < this.param_id.length; i++)
      this.param_id[i] = paramMAVLinkPayload.getByte();
    this.param_type = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_param_value
 * JD-Core Version:    0.6.2
 */