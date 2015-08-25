package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_mag_cal_report extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_MAG_CAL_REPORT = 192;
  public static final int MAVLINK_MSG_LENGTH = 44;
  private static final long serialVersionUID = 192L;
  public byte autosaved;
  public byte cal_mask;
  public byte cal_status;
  public byte compass_id;
  public float diag_x;
  public float diag_y;
  public float diag_z;
  public float fitness;
  public float offdiag_x;
  public float offdiag_y;
  public float offdiag_z;
  public float ofs_x;
  public float ofs_y;
  public float ofs_z;

  public msg_mag_cal_report()
  {
    this.msgid = 192;
  }

  public msg_mag_cal_report(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 192;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 44;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 192;
    localMAVLinkPacket.payload.putFloat(this.fitness);
    localMAVLinkPacket.payload.putFloat(this.ofs_x);
    localMAVLinkPacket.payload.putFloat(this.ofs_y);
    localMAVLinkPacket.payload.putFloat(this.ofs_z);
    localMAVLinkPacket.payload.putFloat(this.diag_x);
    localMAVLinkPacket.payload.putFloat(this.diag_y);
    localMAVLinkPacket.payload.putFloat(this.diag_z);
    localMAVLinkPacket.payload.putFloat(this.offdiag_x);
    localMAVLinkPacket.payload.putFloat(this.offdiag_y);
    localMAVLinkPacket.payload.putFloat(this.offdiag_z);
    localMAVLinkPacket.payload.putByte(this.compass_id);
    localMAVLinkPacket.payload.putByte(this.cal_mask);
    localMAVLinkPacket.payload.putByte(this.cal_status);
    localMAVLinkPacket.payload.putByte(this.autosaved);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_MAG_CAL_REPORT - fitness:" + this.fitness + " ofs_x:" + this.ofs_x + " ofs_y:" + this.ofs_y + " ofs_z:" + this.ofs_z + " diag_x:" + this.diag_x + " diag_y:" + this.diag_y + " diag_z:" + this.diag_z + " offdiag_x:" + this.offdiag_x + " offdiag_y:" + this.offdiag_y + " offdiag_z:" + this.offdiag_z + " compass_id:" + this.compass_id + " cal_mask:" + this.cal_mask + " cal_status:" + this.cal_status + " autosaved:" + this.autosaved + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.fitness = paramMAVLinkPayload.getFloat();
    this.ofs_x = paramMAVLinkPayload.getFloat();
    this.ofs_y = paramMAVLinkPayload.getFloat();
    this.ofs_z = paramMAVLinkPayload.getFloat();
    this.diag_x = paramMAVLinkPayload.getFloat();
    this.diag_y = paramMAVLinkPayload.getFloat();
    this.diag_z = paramMAVLinkPayload.getFloat();
    this.offdiag_x = paramMAVLinkPayload.getFloat();
    this.offdiag_y = paramMAVLinkPayload.getFloat();
    this.offdiag_z = paramMAVLinkPayload.getFloat();
    this.compass_id = paramMAVLinkPayload.getByte();
    this.cal_mask = paramMAVLinkPayload.getByte();
    this.cal_status = paramMAVLinkPayload.getByte();
    this.autosaved = paramMAVLinkPayload.getByte();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_mag_cal_report
 * JD-Core Version:    0.6.2
 */