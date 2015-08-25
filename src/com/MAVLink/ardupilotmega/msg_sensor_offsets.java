package com.MAVLink.ardupilotmega;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;

public class msg_sensor_offsets extends MAVLinkMessage
{
  public static final int MAVLINK_MSG_ID_SENSOR_OFFSETS = 150;
  public static final int MAVLINK_MSG_LENGTH = 42;
  private static final long serialVersionUID = 150L;
  public float accel_cal_x;
  public float accel_cal_y;
  public float accel_cal_z;
  public float gyro_cal_x;
  public float gyro_cal_y;
  public float gyro_cal_z;
  public float mag_declination;
  public short mag_ofs_x;
  public short mag_ofs_y;
  public short mag_ofs_z;
  public int raw_press;
  public int raw_temp;

  public msg_sensor_offsets()
  {
    this.msgid = 150;
  }

  public msg_sensor_offsets(MAVLinkPacket paramMAVLinkPacket)
  {
    this.sysid = paramMAVLinkPacket.sysid;
    this.compid = paramMAVLinkPacket.compid;
    this.msgid = 150;
    unpack(paramMAVLinkPacket.payload);
  }

  public MAVLinkPacket pack()
  {
    MAVLinkPacket localMAVLinkPacket = new MAVLinkPacket();
    localMAVLinkPacket.len = 42;
    localMAVLinkPacket.sysid = 255;
    localMAVLinkPacket.compid = 190;
    localMAVLinkPacket.msgid = 150;
    localMAVLinkPacket.payload.putFloat(this.mag_declination);
    localMAVLinkPacket.payload.putInt(this.raw_press);
    localMAVLinkPacket.payload.putInt(this.raw_temp);
    localMAVLinkPacket.payload.putFloat(this.gyro_cal_x);
    localMAVLinkPacket.payload.putFloat(this.gyro_cal_y);
    localMAVLinkPacket.payload.putFloat(this.gyro_cal_z);
    localMAVLinkPacket.payload.putFloat(this.accel_cal_x);
    localMAVLinkPacket.payload.putFloat(this.accel_cal_y);
    localMAVLinkPacket.payload.putFloat(this.accel_cal_z);
    localMAVLinkPacket.payload.putShort(this.mag_ofs_x);
    localMAVLinkPacket.payload.putShort(this.mag_ofs_y);
    localMAVLinkPacket.payload.putShort(this.mag_ofs_z);
    return localMAVLinkPacket;
  }

  public String toString()
  {
    return "MAVLINK_MSG_ID_SENSOR_OFFSETS - mag_declination:" + this.mag_declination + " raw_press:" + this.raw_press + " raw_temp:" + this.raw_temp + " gyro_cal_x:" + this.gyro_cal_x + " gyro_cal_y:" + this.gyro_cal_y + " gyro_cal_z:" + this.gyro_cal_z + " accel_cal_x:" + this.accel_cal_x + " accel_cal_y:" + this.accel_cal_y + " accel_cal_z:" + this.accel_cal_z + " mag_ofs_x:" + this.mag_ofs_x + " mag_ofs_y:" + this.mag_ofs_y + " mag_ofs_z:" + this.mag_ofs_z + "";
  }

  public void unpack(MAVLinkPayload paramMAVLinkPayload)
  {
    paramMAVLinkPayload.resetIndex();
    this.mag_declination = paramMAVLinkPayload.getFloat();
    this.raw_press = paramMAVLinkPayload.getInt();
    this.raw_temp = paramMAVLinkPayload.getInt();
    this.gyro_cal_x = paramMAVLinkPayload.getFloat();
    this.gyro_cal_y = paramMAVLinkPayload.getFloat();
    this.gyro_cal_z = paramMAVLinkPayload.getFloat();
    this.accel_cal_x = paramMAVLinkPayload.getFloat();
    this.accel_cal_y = paramMAVLinkPayload.getFloat();
    this.accel_cal_z = paramMAVLinkPayload.getFloat();
    this.mag_ofs_x = paramMAVLinkPayload.getShort();
    this.mag_ofs_y = paramMAVLinkPayload.getShort();
    this.mag_ofs_z = paramMAVLinkPayload.getShort();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.msg_sensor_offsets
 * JD-Core Version:    0.6.2
 */