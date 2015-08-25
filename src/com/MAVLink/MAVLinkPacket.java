package com.MAVLink;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.ardupilotmega.CRC;
import com.MAVLink.ardupilotmega.msg_ahrs;
import com.MAVLink.ardupilotmega.msg_ahrs2;
import com.MAVLink.ardupilotmega.msg_ahrs3;
import com.MAVLink.ardupilotmega.msg_airspeed_autocal;
import com.MAVLink.ardupilotmega.msg_ap_adc;
import com.MAVLink.ardupilotmega.msg_autopilot_version_request;
import com.MAVLink.ardupilotmega.msg_battery2;
import com.MAVLink.ardupilotmega.msg_camera_feedback;
import com.MAVLink.ardupilotmega.msg_camera_status;
import com.MAVLink.ardupilotmega.msg_compassmot_status;
import com.MAVLink.ardupilotmega.msg_data16;
import com.MAVLink.ardupilotmega.msg_data32;
import com.MAVLink.ardupilotmega.msg_data64;
import com.MAVLink.ardupilotmega.msg_data96;
import com.MAVLink.ardupilotmega.msg_digicam_configure;
import com.MAVLink.ardupilotmega.msg_digicam_control;
import com.MAVLink.ardupilotmega.msg_ekf_status_report;
import com.MAVLink.ardupilotmega.msg_fence_fetch_point;
import com.MAVLink.ardupilotmega.msg_fence_point;
import com.MAVLink.ardupilotmega.msg_fence_status;
import com.MAVLink.ardupilotmega.msg_gimbal_axis_calibration_progress;
import com.MAVLink.ardupilotmega.msg_gimbal_control;
import com.MAVLink.ardupilotmega.msg_gimbal_erase_firmware_and_config;
import com.MAVLink.ardupilotmega.msg_gimbal_factory_parameters_loaded;
import com.MAVLink.ardupilotmega.msg_gimbal_home_offset_calibration_result;
import com.MAVLink.ardupilotmega.msg_gimbal_perform_factory_tests;
import com.MAVLink.ardupilotmega.msg_gimbal_report;
import com.MAVLink.ardupilotmega.msg_gimbal_report_axis_calibration_status;
import com.MAVLink.ardupilotmega.msg_gimbal_report_factory_tests_progress;
import com.MAVLink.ardupilotmega.msg_gimbal_request_axis_calibration;
import com.MAVLink.ardupilotmega.msg_gimbal_request_axis_calibration_status;
import com.MAVLink.ardupilotmega.msg_gimbal_reset;
import com.MAVLink.ardupilotmega.msg_gimbal_set_factory_parameters;
import com.MAVLink.ardupilotmega.msg_gimbal_set_home_offsets;
import com.MAVLink.ardupilotmega.msg_gopro_get_request;
import com.MAVLink.ardupilotmega.msg_gopro_get_response;
import com.MAVLink.ardupilotmega.msg_gopro_heartbeat;
import com.MAVLink.ardupilotmega.msg_gopro_set_request;
import com.MAVLink.ardupilotmega.msg_gopro_set_response;
import com.MAVLink.ardupilotmega.msg_hwstatus;
import com.MAVLink.ardupilotmega.msg_led_control;
import com.MAVLink.ardupilotmega.msg_limits_status;
import com.MAVLink.ardupilotmega.msg_mag_cal_progress;
import com.MAVLink.ardupilotmega.msg_mag_cal_report;
import com.MAVLink.ardupilotmega.msg_meminfo;
import com.MAVLink.ardupilotmega.msg_mount_configure;
import com.MAVLink.ardupilotmega.msg_mount_control;
import com.MAVLink.ardupilotmega.msg_mount_status;
import com.MAVLink.ardupilotmega.msg_radio;
import com.MAVLink.ardupilotmega.msg_rally_fetch_point;
import com.MAVLink.ardupilotmega.msg_rally_point;
import com.MAVLink.ardupilotmega.msg_rangefinder;
import com.MAVLink.ardupilotmega.msg_sensor_offsets;
import com.MAVLink.ardupilotmega.msg_set_mag_offsets;
import com.MAVLink.ardupilotmega.msg_simstate;
import com.MAVLink.ardupilotmega.msg_wind;
import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_attitude_quaternion;
import com.MAVLink.common.msg_attitude_quaternion_cov;
import com.MAVLink.common.msg_attitude_target;
import com.MAVLink.common.msg_auth_key;
import com.MAVLink.common.msg_autopilot_version;
import com.MAVLink.common.msg_battery_status;
import com.MAVLink.common.msg_change_operator_control;
import com.MAVLink.common.msg_change_operator_control_ack;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_command_int;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_data_stream;
import com.MAVLink.common.msg_data_transmission_handshake;
import com.MAVLink.common.msg_debug;
import com.MAVLink.common.msg_debug_vect;
import com.MAVLink.common.msg_distance_sensor;
import com.MAVLink.common.msg_encapsulated_data;
import com.MAVLink.common.msg_file_transfer_protocol;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_global_position_int_cov;
import com.MAVLink.common.msg_global_vision_position_estimate;
import com.MAVLink.common.msg_gps2_raw;
import com.MAVLink.common.msg_gps2_rtk;
import com.MAVLink.common.msg_gps_global_origin;
import com.MAVLink.common.msg_gps_inject_data;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_gps_rtk;
import com.MAVLink.common.msg_gps_status;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_highres_imu;
import com.MAVLink.common.msg_hil_controls;
import com.MAVLink.common.msg_hil_gps;
import com.MAVLink.common.msg_hil_optical_flow;
import com.MAVLink.common.msg_hil_rc_inputs_raw;
import com.MAVLink.common.msg_hil_sensor;
import com.MAVLink.common.msg_hil_state;
import com.MAVLink.common.msg_hil_state_quaternion;
import com.MAVLink.common.msg_local_position_ned;
import com.MAVLink.common.msg_local_position_ned_cov;
import com.MAVLink.common.msg_local_position_ned_system_global_offset;
import com.MAVLink.common.msg_log_data;
import com.MAVLink.common.msg_log_entry;
import com.MAVLink.common.msg_log_erase;
import com.MAVLink.common.msg_log_request_data;
import com.MAVLink.common.msg_log_request_end;
import com.MAVLink.common.msg_log_request_list;
import com.MAVLink.common.msg_manual_control;
import com.MAVLink.common.msg_manual_setpoint;
import com.MAVLink.common.msg_memory_vect;
import com.MAVLink.common.msg_mission_ack;
import com.MAVLink.common.msg_mission_clear_all;
import com.MAVLink.common.msg_mission_count;
import com.MAVLink.common.msg_mission_current;
import com.MAVLink.common.msg_mission_item;
import com.MAVLink.common.msg_mission_item_int;
import com.MAVLink.common.msg_mission_item_reached;
import com.MAVLink.common.msg_mission_request;
import com.MAVLink.common.msg_mission_request_list;
import com.MAVLink.common.msg_mission_request_partial_list;
import com.MAVLink.common.msg_mission_set_current;
import com.MAVLink.common.msg_mission_write_partial_list;
import com.MAVLink.common.msg_named_value_float;
import com.MAVLink.common.msg_named_value_int;
import com.MAVLink.common.msg_nav_controller_output;
import com.MAVLink.common.msg_optical_flow;
import com.MAVLink.common.msg_optical_flow_rad;
import com.MAVLink.common.msg_param_request_list;
import com.MAVLink.common.msg_param_request_read;
import com.MAVLink.common.msg_param_set;
import com.MAVLink.common.msg_param_value;
import com.MAVLink.common.msg_ping;
import com.MAVLink.common.msg_position_target_global_int;
import com.MAVLink.common.msg_position_target_local_ned;
import com.MAVLink.common.msg_power_status;
import com.MAVLink.common.msg_radio_status;
import com.MAVLink.common.msg_raw_imu;
import com.MAVLink.common.msg_raw_pressure;
import com.MAVLink.common.msg_rc_channels;
import com.MAVLink.common.msg_rc_channels_override;
import com.MAVLink.common.msg_rc_channels_raw;
import com.MAVLink.common.msg_rc_channels_scaled;
import com.MAVLink.common.msg_request_data_stream;
import com.MAVLink.common.msg_safety_allowed_area;
import com.MAVLink.common.msg_safety_set_allowed_area;
import com.MAVLink.common.msg_scaled_imu;
import com.MAVLink.common.msg_scaled_imu2;
import com.MAVLink.common.msg_scaled_pressure;
import com.MAVLink.common.msg_serial_control;
import com.MAVLink.common.msg_servo_output_raw;
import com.MAVLink.common.msg_set_attitude_target;
import com.MAVLink.common.msg_set_gps_global_origin;
import com.MAVLink.common.msg_set_mode;
import com.MAVLink.common.msg_set_position_target_global_int;
import com.MAVLink.common.msg_set_position_target_local_ned;
import com.MAVLink.common.msg_sim_state;
import com.MAVLink.common.msg_statustext;
import com.MAVLink.common.msg_sys_status;
import com.MAVLink.common.msg_system_time;
import com.MAVLink.common.msg_terrain_check;
import com.MAVLink.common.msg_terrain_data;
import com.MAVLink.common.msg_terrain_report;
import com.MAVLink.common.msg_terrain_request;
import com.MAVLink.common.msg_timesync;
import com.MAVLink.common.msg_v2_extension;
import com.MAVLink.common.msg_vfr_hud;
import com.MAVLink.common.msg_vicon_position_estimate;
import com.MAVLink.common.msg_vision_position_estimate;
import com.MAVLink.common.msg_vision_speed_estimate;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class MAVLinkPacket
  implements Serializable
{
  public static final int MAVLINK_STX = 254;
  private static final long serialVersionUID = 2095947771227815314L;
  public int compid;
  public CRC crc;
  public int len;
  public int msgid;
  public MAVLinkPayload payload = new MAVLinkPayload();
  public int seq;
  public int sysid;

  public byte[] encodePacket()
  {
    byte[] arrayOfByte = new byte[2 + (6 + this.len)];
    int i = 0 + 1;
    arrayOfByte[0] = -2;
    int j = i + 1;
    arrayOfByte[i] = ((byte)this.len);
    int k = j + 1;
    arrayOfByte[j] = ((byte)this.seq);
    int m = k + 1;
    arrayOfByte[k] = ((byte)this.sysid);
    int n = m + 1;
    arrayOfByte[m] = ((byte)this.compid);
    int i1 = n + 1;
    arrayOfByte[n] = ((byte)this.msgid);
    int i2 = 0;
    while (i2 < this.payload.size())
    {
      int i4 = i1 + 1;
      arrayOfByte[i1] = this.payload.payload.get(i2);
      i2++;
      i1 = i4;
    }
    generateCRC();
    int i3 = i1 + 1;
    arrayOfByte[i1] = ((byte)this.crc.getLSB());
    (i3 + 1);
    arrayOfByte[i3] = ((byte)this.crc.getMSB());
    return arrayOfByte;
  }

  public void generateCRC()
  {
    if (this.crc == null)
      this.crc = new CRC();
    while (true)
    {
      this.crc.update_checksum(this.len);
      this.crc.update_checksum(this.seq);
      this.crc.update_checksum(this.sysid);
      this.crc.update_checksum(this.compid);
      this.crc.update_checksum(this.msgid);
      this.payload.resetIndex();
      for (int i = 0; i < this.payload.size(); i++)
        this.crc.update_checksum(this.payload.getByte());
      this.crc.start_checksum();
    }
    this.crc.finish_checksum(this.msgid);
  }

  public boolean payloadIsFilled()
  {
    if (this.payload.size() >= 511);
    while (this.payload.size() == this.len)
      return true;
    return false;
  }

  public MAVLinkMessage unpack()
  {
    switch (this.msgid)
    {
    case 3:
    case 8:
    case 9:
    case 10:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    case 50:
    case 51:
    case 52:
    case 53:
    case 56:
    case 57:
    case 58:
    case 59:
    case 60:
    case 68:
    case 71:
    case 72:
    case 78:
    case 79:
    case 80:
    case 88:
    case 93:
    case 94:
    case 95:
    case 96:
    case 97:
    case 98:
    case 99:
    case 112:
    case 129:
    case 137:
    case 138:
    case 139:
    case 140:
    case 141:
    case 142:
    case 143:
    case 144:
    case 145:
    case 146:
    case 149:
    case 159:
    case 184:
    case 185:
    case 187:
    case 188:
    case 189:
    case 190:
    case 194:
    case 195:
    case 196:
    case 197:
    case 198:
    case 199:
    case 214:
    case 220:
    case 221:
    case 222:
    case 223:
    case 224:
    case 225:
    case 226:
    case 227:
    case 228:
    case 229:
    case 230:
    case 231:
    case 232:
    case 233:
    case 234:
    case 235:
    case 236:
    case 237:
    case 238:
    case 239:
    case 240:
    case 241:
    case 242:
    case 243:
    case 244:
    case 245:
    case 246:
    case 247:
    default:
      return null;
    case 150:
      return new msg_sensor_offsets(this);
    case 151:
      return new msg_set_mag_offsets(this);
    case 152:
      return new msg_meminfo(this);
    case 153:
      return new msg_ap_adc(this);
    case 154:
      return new msg_digicam_configure(this);
    case 155:
      return new msg_digicam_control(this);
    case 156:
      return new msg_mount_configure(this);
    case 157:
      return new msg_mount_control(this);
    case 158:
      return new msg_mount_status(this);
    case 160:
      return new msg_fence_point(this);
    case 161:
      return new msg_fence_fetch_point(this);
    case 162:
      return new msg_fence_status(this);
    case 163:
      return new msg_ahrs(this);
    case 164:
      return new msg_simstate(this);
    case 165:
      return new msg_hwstatus(this);
    case 166:
      return new msg_radio(this);
    case 167:
      return new msg_limits_status(this);
    case 168:
      return new msg_wind(this);
    case 169:
      return new msg_data16(this);
    case 170:
      return new msg_data32(this);
    case 171:
      return new msg_data64(this);
    case 172:
      return new msg_data96(this);
    case 173:
      return new msg_rangefinder(this);
    case 174:
      return new msg_airspeed_autocal(this);
    case 175:
      return new msg_rally_point(this);
    case 176:
      return new msg_rally_fetch_point(this);
    case 177:
      return new msg_compassmot_status(this);
    case 178:
      return new msg_ahrs2(this);
    case 179:
      return new msg_camera_status(this);
    case 180:
      return new msg_camera_feedback(this);
    case 181:
      return new msg_battery2(this);
    case 182:
      return new msg_ahrs3(this);
    case 183:
      return new msg_autopilot_version_request(this);
    case 186:
      return new msg_led_control(this);
    case 191:
      return new msg_mag_cal_progress(this);
    case 192:
      return new msg_mag_cal_report(this);
    case 193:
      return new msg_ekf_status_report(this);
    case 200:
      return new msg_gimbal_report(this);
    case 201:
      return new msg_gimbal_control(this);
    case 202:
      return new msg_gimbal_reset(this);
    case 203:
      return new msg_gimbal_axis_calibration_progress(this);
    case 204:
      return new msg_gimbal_set_home_offsets(this);
    case 205:
      return new msg_gimbal_home_offset_calibration_result(this);
    case 206:
      return new msg_gimbal_set_factory_parameters(this);
    case 207:
      return new msg_gimbal_factory_parameters_loaded(this);
    case 208:
      return new msg_gimbal_erase_firmware_and_config(this);
    case 209:
      return new msg_gimbal_perform_factory_tests(this);
    case 210:
      return new msg_gimbal_report_factory_tests_progress(this);
    case 211:
      return new msg_gimbal_request_axis_calibration_status(this);
    case 212:
      return new msg_gimbal_report_axis_calibration_status(this);
    case 213:
      return new msg_gimbal_request_axis_calibration(this);
    case 215:
      return new msg_gopro_heartbeat(this);
    case 216:
      return new msg_gopro_get_request(this);
    case 217:
      return new msg_gopro_get_response(this);
    case 218:
      return new msg_gopro_set_request(this);
    case 219:
      return new msg_gopro_set_response(this);
    case 0:
      return new msg_heartbeat(this);
    case 1:
      return new msg_sys_status(this);
    case 2:
      return new msg_system_time(this);
    case 4:
      return new msg_ping(this);
    case 5:
      return new msg_change_operator_control(this);
    case 6:
      return new msg_change_operator_control_ack(this);
    case 7:
      return new msg_auth_key(this);
    case 11:
      return new msg_set_mode(this);
    case 20:
      return new msg_param_request_read(this);
    case 21:
      return new msg_param_request_list(this);
    case 22:
      return new msg_param_value(this);
    case 23:
      return new msg_param_set(this);
    case 24:
      return new msg_gps_raw_int(this);
    case 25:
      return new msg_gps_status(this);
    case 26:
      return new msg_scaled_imu(this);
    case 27:
      return new msg_raw_imu(this);
    case 28:
      return new msg_raw_pressure(this);
    case 29:
      return new msg_scaled_pressure(this);
    case 30:
      return new msg_attitude(this);
    case 31:
      return new msg_attitude_quaternion(this);
    case 32:
      return new msg_local_position_ned(this);
    case 33:
      return new msg_global_position_int(this);
    case 34:
      return new msg_rc_channels_scaled(this);
    case 35:
      return new msg_rc_channels_raw(this);
    case 36:
      return new msg_servo_output_raw(this);
    case 37:
      return new msg_mission_request_partial_list(this);
    case 38:
      return new msg_mission_write_partial_list(this);
    case 39:
      return new msg_mission_item(this);
    case 40:
      return new msg_mission_request(this);
    case 41:
      return new msg_mission_set_current(this);
    case 42:
      return new msg_mission_current(this);
    case 43:
      return new msg_mission_request_list(this);
    case 44:
      return new msg_mission_count(this);
    case 45:
      return new msg_mission_clear_all(this);
    case 46:
      return new msg_mission_item_reached(this);
    case 47:
      return new msg_mission_ack(this);
    case 48:
      return new msg_set_gps_global_origin(this);
    case 49:
      return new msg_gps_global_origin(this);
    case 54:
      return new msg_safety_set_allowed_area(this);
    case 55:
      return new msg_safety_allowed_area(this);
    case 61:
      return new msg_attitude_quaternion_cov(this);
    case 62:
      return new msg_nav_controller_output(this);
    case 63:
      return new msg_global_position_int_cov(this);
    case 64:
      return new msg_local_position_ned_cov(this);
    case 65:
      return new msg_rc_channels(this);
    case 66:
      return new msg_request_data_stream(this);
    case 67:
      return new msg_data_stream(this);
    case 69:
      return new msg_manual_control(this);
    case 70:
      return new msg_rc_channels_override(this);
    case 73:
      return new msg_mission_item_int(this);
    case 74:
      return new msg_vfr_hud(this);
    case 75:
      return new msg_command_int(this);
    case 76:
      return new msg_command_long(this);
    case 77:
      return new msg_command_ack(this);
    case 81:
      return new msg_manual_setpoint(this);
    case 82:
      return new msg_set_attitude_target(this);
    case 83:
      return new msg_attitude_target(this);
    case 84:
      return new msg_set_position_target_local_ned(this);
    case 85:
      return new msg_position_target_local_ned(this);
    case 86:
      return new msg_set_position_target_global_int(this);
    case 87:
      return new msg_position_target_global_int(this);
    case 89:
      return new msg_local_position_ned_system_global_offset(this);
    case 90:
      return new msg_hil_state(this);
    case 91:
      return new msg_hil_controls(this);
    case 92:
      return new msg_hil_rc_inputs_raw(this);
    case 100:
      return new msg_optical_flow(this);
    case 101:
      return new msg_global_vision_position_estimate(this);
    case 102:
      return new msg_vision_position_estimate(this);
    case 103:
      return new msg_vision_speed_estimate(this);
    case 104:
      return new msg_vicon_position_estimate(this);
    case 105:
      return new msg_highres_imu(this);
    case 106:
      return new msg_optical_flow_rad(this);
    case 107:
      return new msg_hil_sensor(this);
    case 108:
      return new msg_sim_state(this);
    case 109:
      return new msg_radio_status(this);
    case 110:
      return new msg_file_transfer_protocol(this);
    case 111:
      return new msg_timesync(this);
    case 113:
      return new msg_hil_gps(this);
    case 114:
      return new msg_hil_optical_flow(this);
    case 115:
      return new msg_hil_state_quaternion(this);
    case 116:
      return new msg_scaled_imu2(this);
    case 117:
      return new msg_log_request_list(this);
    case 118:
      return new msg_log_entry(this);
    case 119:
      return new msg_log_request_data(this);
    case 120:
      return new msg_log_data(this);
    case 121:
      return new msg_log_erase(this);
    case 122:
      return new msg_log_request_end(this);
    case 123:
      return new msg_gps_inject_data(this);
    case 124:
      return new msg_gps2_raw(this);
    case 125:
      return new msg_power_status(this);
    case 126:
      return new msg_serial_control(this);
    case 127:
      return new msg_gps_rtk(this);
    case 128:
      return new msg_gps2_rtk(this);
    case 130:
      return new msg_data_transmission_handshake(this);
    case 131:
      return new msg_encapsulated_data(this);
    case 132:
      return new msg_distance_sensor(this);
    case 133:
      return new msg_terrain_request(this);
    case 134:
      return new msg_terrain_data(this);
    case 135:
      return new msg_terrain_check(this);
    case 136:
      return new msg_terrain_report(this);
    case 147:
      return new msg_battery_status(this);
    case 148:
      return new msg_autopilot_version(this);
    case 248:
      return new msg_v2_extension(this);
    case 249:
      return new msg_memory_vect(this);
    case 250:
      return new msg_debug_vect(this);
    case 251:
      return new msg_named_value_float(this);
    case 252:
      return new msg_named_value_int(this);
    case 253:
      return new msg_statustext(this);
    case 254:
    }
    return new msg_debug(this);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.MAVLinkPacket
 * JD-Core Version:    0.6.2
 */