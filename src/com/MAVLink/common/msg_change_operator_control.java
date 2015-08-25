package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_change_operator_control extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL = 5;
  public static final int MAVLINK_MSG_LENGTH = 28;
  private static final long serialVersionUID = 5L;
  public byte control_request;
  public byte[] passkey = new byte[25];
  public byte target_system;
  public byte version;

  public msg_change_operator_control()
  {
    this.msgid = 5;
  }

  public msg_change_operator_control(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 5;
    unpack(paramMAVLinkPacket.payload);
  }

  public String getPasskey()
  {
    String str = "";
    for (int i = 0; (i < 25) && (this.passkey[i] != 0); i++)
      str = str + (char)this.passkey[i];
    return str;
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 28;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 5;
    localMAVLinkPacket.payload.putByte(this.target_system);
    localMAVLinkPacket.payload.putByte(this.control_request);
    localMAVLinkPacket.payload.putByte(this.version);
    for (int i = 0; i < this.passkey.length; i++)
      localMAVLinkPacket.payload.putByte(this.passkey[i]);
    return localMAVLinkPacket;
  }

  public void setPasskey(String paramString)
  {
    int i = Math.min(paramString.length(), 25);
    for (int j = 0; j < i; j++)
      this.passkey[j] = ((byte)paramString.charAt(j));
    for (int k = i; k < 25; k++)
      this.passkey[k] = 0;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL - target_system:" + this.target_system + " control_request:" + this.control_request + " version:" + this.version + " passkey:" + this.passkey + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.target_system = paramMAVLinkPayload.getByte();
    this.control_request = paramMAVLinkPayload.getByte();
    this.version = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.passkey.length; i++)
      this.passkey[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_change_operator_control
 * JD-Core Version:    0.6.2
 */