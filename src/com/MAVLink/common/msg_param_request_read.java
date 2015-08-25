package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_param_request_read extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_PARAM_REQUEST_READ = 20;
  public static final int MAVLINK_MSG_LENGTH = 20;
  private static final long serialVersionUID = 20L;
  public byte[] param_id = new byte[16];
  public short param_index;
  public byte target_component;
  public byte target_system;

  public msg_param_request_read()
  {
    this.msgid = 20;
  }

  public msg_param_request_read(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 20;
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
    localMAVLinkPacket.len = 20;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 20;
    localMAVLinkPacket.payload.putShort(this.param_index);
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.target_component);
    for (int i = 0; i < this.param_id.length; i++)
      localMAVLinkPacket.payload.putByte(this.param_id[i]);
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
    return "MAVLINK_MSG_ID_PARAM_REQUEST_READ - param_index:" + this.param_index + " target_system:" + this.target_system + " target_component:" + this.target_component + " param_id:" + this.param_id + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.param_index = paramMAVLinkPayload.getShort();
    this.target_system = paramMAVLinkPayload.getByte();
    this.target_component = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.param_id.length; i++)
      this.param_id[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_param_request_read
 * JD-Core Version:    0.6.2
 */