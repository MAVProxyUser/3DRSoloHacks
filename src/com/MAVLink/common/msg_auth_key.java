package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_auth_key extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_AUTH_KEY = 7;
  public static final int MAVLINK_MSG_LENGTH = 32;
  private static final long serialVersionUID = 7L;
  public byte[] key = new byte[32];

  public msg_auth_key()
  {
    this.msgid = 7;
  }

  public msg_auth_key(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 7;
    unpack(paramMAVLinkPacket.payload);
  }

  public String getKey()
  {
    String str = "";
    for (int i = 0; (i < 32) && (this.key[i] != 0); i++)
      str = str + (char)this.key[i];
    return str;
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 32;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 7;
    for (int i = 0; i < this.key.length; i++)
      localMAVLinkPacket.payload.putByte(this.key[i]);
    return localMAVLinkPacket;
  }

  public void setKey(String paramString)
  {
    int i = Math.min(paramString.length(), 32);
    for (int j = 0; j < i; j++)
      this.key[j] = ((byte)paramString.charAt(j));
    for (int k = i; k < 32; k++)
      this.key[k] = 0;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_AUTH_KEY - key:" + this.key + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    for (int i = 0; i < this.key.length; i++)
      this.key[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_auth_key
 * JD-Core Version:    0.6.2
 */