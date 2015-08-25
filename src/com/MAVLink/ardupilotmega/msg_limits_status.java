package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_limits_status extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_LIMITS_STATUS = 167;
  public static final int MAVLINK_MSG_LENGTH = 22;
  private static final long serialVersionUID = 167L;
  public short breach_count;
  public int last_action;
  public int last_clear;
  public int last_recovery;
  public int last_trigger;
  public byte limits_state;
  public byte mods_enabled;
  public byte mods_required;
  public byte mods_triggered;

  public msg_limits_status()
  {
    this.msgid = 167;
  }

  public msg_limits_status(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 167;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 22;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 167;
    localMAVLinkPacket.payload.putInt(this.last_trigger);
    localMAVLinkPacket.payload.putInt(this.last_action);
    localMAVLinkPacket.payload.putInt(this.last_recovery);
    localMAVLinkPacket.payload.putInt(this.last_clear);
    localMAVLinkPacket.payload.putShort(this.breach_count);
    localMAVLinkPacket.payload.putByte(this.limits_state);
    localMAVLinkPacket.payload.putByte(this.mods_enabled);
    localMAVLinkPacket.payload.putByte(this.mods_required);
    localMAVLinkPacket.payload.putByte(this.mods_triggered);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_LIMITS_STATUS - last_trigger:" + this.last_trigger + " last_action:" + this.last_action + " last_recovery:" + this.last_recovery + " last_clear:" + this.last_clear + " breach_count:" + this.breach_count + " limits_state:" + this.limits_state + " mods_enabled:" + this.mods_enabled + " mods_required:" + this.mods_required + " mods_triggered:" + this.mods_triggered + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.last_trigger = paramMAVLinkPayload.getInt();
    this.last_action = paramMAVLinkPayload.getInt();
    this.last_recovery = paramMAVLinkPayload.getInt();
    this.last_clear = paramMAVLinkPayload.getInt();
    this.breach_count = paramMAVLinkPayload.getShort();
    this.limits_state = paramMAVLinkPayload.getByte();
    this.mods_enabled = paramMAVLinkPayload.getByte();
    this.mods_required = paramMAVLinkPayload.getByte();
    this.mods_triggered = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_limits_status
 * JD-Core Version:    0.6.2
 */