package com.MAVLink.Messages;

import com.MAVLink.MAVLinkPacket;
import java.io.Serializable;

public abstract class MAVLinkMessage
  implements Serializable
{
  private static final long serialVersionUID = -7754622750478538539L;
  public int compid;
  public int msgid;
  public int sysid;

  public abstract MAVLinkPacket pack();

  public abstract void unpack(MAVLinkPayload paramMAVLinkPayload);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.Messages.MAVLinkMessage
 * JD-Core Version:    0.6.2
 */