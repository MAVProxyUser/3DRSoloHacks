package com.MAVLink.common;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_statustext extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_STATUSTEXT = 253;
  public static final int MAVLINK_MSG_LENGTH = 51;
  private static final long serialVersionUID = 253L;
  public byte severity;
  public byte[] text = new byte[50];

  public msg_statustext()
  {
    this.msgid = 253;
  }

  public msg_statustext(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 253;
    unpack(paramMAVLinkPacket.payload);
  }

  public String getText()
  {
    String str = "";
    for (int i = 0; (i < 50) && (this.text[i] != 0); i++)
      str = str + (char)this.text[i];
    return str;
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 51;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 253;
    localMAVLinkPacket.payload.putByte(this.severity);
    for (int i = 0; i < this.text.length; i++)
      localMAVLinkPacket.payload.putByte(this.text[i]);
    return localMAVLinkPacket;
  }

  public void setText(String paramString)
  {
    int i = Math.min(paramString.length(), 50);
    for (int j = 0; j < i; j++)
      this.text[j] = ((byte)paramString.charAt(j));
    for (int k = i; k < 50; k++)
      this.text[k] = 0;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_STATUSTEXT - severity:" + this.severity + " text:" + this.text + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.severity = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.text.length; i++)
      this.text[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.common.msg_statustext
 * JD-Core Version:    0.6.2
 */