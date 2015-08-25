package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mag_cal_progress extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MAG_CAL_PROGRESS = 191;
  public static final int MAVLINK_MSG_LENGTH = 27;
  private static final long serialVersionUID = 191L;
  public byte attempt;
  public byte cal_mask;
  public byte cal_status;
  public byte compass_id;
  public byte[] completion_mask = new byte[10];
  public byte completion_pct;
  public float direction_x;
  public float direction_y;
  public float direction_z;

  public msg_mag_cal_progress()
  {
    this.msgid = 191;
  }

  public msg_mag_cal_progress(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 191;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 27;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 191;
    localMAVLinkPacket.payload.putFloat(this.direction_x);
    localMAVLinkPacket.payload.putFloat(this.direction_y);
    localMAVLinkPacket.payload.putFloat(this.direction_z);
    localMAVLinkPacket.payload.putByte(this.compass_id);
    localMAVLinkPacket.payload.putByte(this.cal_mask);
    localMAVLinkPacket.payload.putByte(this.cal_status);
    localMAVLinkPacket.payload.putByte(this.attempt);
    localMAVLinkPacket.payload.putByte(this.completion_pct);
    for (int i = 0; i < this.completion_mask.length; i++)
      localMAVLinkPacket.payload.putByte(this.completion_mask[i]);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MAG_CAL_PROGRESS - direction_x:" + this.direction_x + " direction_y:" + this.direction_y + " direction_z:" + this.direction_z + " compass_id:" + this.compass_id + " cal_mask:" + this.cal_mask + " cal_status:" + this.cal_status + " attempt:" + this.attempt + " completion_pct:" + this.completion_pct + " completion_mask:" + this.completion_mask + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.direction_x = paramMAVLinkPayload.getFloat();
    this.direction_y = paramMAVLinkPayload.getFloat();
    this.direction_z = paramMAVLinkPayload.getFloat();
    this.compass_id = paramMAVLinkPayload.getByte();
    this.cal_mask = paramMAVLinkPayload.getByte();
    this.cal_status = paramMAVLinkPayload.getByte();
    this.attempt = paramMAVLinkPayload.getByte();
    this.completion_pct = paramMAVLinkPayload.getByte();
    for (int i = 0; i < this.completion_mask.length; i++)
      this.completion_mask[i] = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_mag_cal_progress
 * JD-Core Version:    0.6.2
 */