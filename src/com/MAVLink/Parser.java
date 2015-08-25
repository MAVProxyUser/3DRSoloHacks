package com.MAVLink;

import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.Messages.MAVLinkStats;
import com.MAVLink.ardupilotmega.CRC;

public class Parser
{
  static boolean msg_received;
  private MAVLinkPacket m;
  MAV_states state = MAV_states.MAVLINK_PARSE_STATE_UNINIT;
  public MAVLinkStats stats = new MAVLinkStats();

  public MAVLinkPacket mavlink_parse_char(int paramInt)
  {
    msg_received = false;
    switch (1.$SwitchMap$com$MAVLink$Parser$MAV_states[this.state.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    }
    while (msg_received)
    {
      return this.m;
      if (paramInt == 254)
      {
        this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_STX;
        this.m = new MAVLinkPacket();
        continue;
        if (msg_received)
        {
          msg_received = false;
          this.state = MAV_states.MAVLINK_PARSE_STATE_IDLE;
        }
        else
        {
          this.m.len = paramInt;
          this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_LENGTH;
          continue;
          this.m.seq = paramInt;
          this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_SEQ;
          continue;
          this.m.sysid = paramInt;
          this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_SYSID;
          continue;
          this.m.compid = paramInt;
          this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_COMPID;
          continue;
          this.m.msgid = paramInt;
          if (this.m.len == 0)
          {
            this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_PAYLOAD;
          }
          else
          {
            this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_MSGID;
            continue;
            this.m.payload.add((byte)paramInt);
            if (this.m.payloadIsFilled())
            {
              this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_PAYLOAD;
              continue;
              this.m.generateCRC();
              if (paramInt != this.m.crc.getLSB())
              {
                msg_received = false;
                this.state = MAV_states.MAVLINK_PARSE_STATE_IDLE;
                if (paramInt == 254)
                {
                  this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_STX;
                  this.m.crc.start_checksum();
                }
                this.stats.crcError();
              }
              else
              {
                this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_CRC1;
                continue;
                if (paramInt != this.m.crc.getMSB())
                {
                  msg_received = false;
                  this.state = MAV_states.MAVLINK_PARSE_STATE_IDLE;
                  if (paramInt == 254)
                  {
                    this.state = MAV_states.MAVLINK_PARSE_STATE_GOT_STX;
                    this.m.crc.start_checksum();
                  }
                  this.stats.crcError();
                }
                else
                {
                  this.stats.newPacket(this.m);
                  msg_received = true;
                  this.state = MAV_states.MAVLINK_PARSE_STATE_IDLE;
                }
              }
            }
          }
        }
      }
    }
    return null;
  }

  static enum MAV_states
  {
    static
    {
      MAVLINK_PARSE_STATE_IDLE = new MAV_states("MAVLINK_PARSE_STATE_IDLE", 1);
      MAVLINK_PARSE_STATE_GOT_STX = new MAV_states("MAVLINK_PARSE_STATE_GOT_STX", 2);
      MAVLINK_PARSE_STATE_GOT_LENGTH = new MAV_states("MAVLINK_PARSE_STATE_GOT_LENGTH", 3);
      MAVLINK_PARSE_STATE_GOT_SEQ = new MAV_states("MAVLINK_PARSE_STATE_GOT_SEQ", 4);
      MAVLINK_PARSE_STATE_GOT_SYSID = new MAV_states("MAVLINK_PARSE_STATE_GOT_SYSID", 5);
      MAVLINK_PARSE_STATE_GOT_COMPID = new MAV_states("MAVLINK_PARSE_STATE_GOT_COMPID", 6);
      MAVLINK_PARSE_STATE_GOT_MSGID = new MAV_states("MAVLINK_PARSE_STATE_GOT_MSGID", 7);
      MAVLINK_PARSE_STATE_GOT_CRC1 = new MAV_states("MAVLINK_PARSE_STATE_GOT_CRC1", 8);
      MAVLINK_PARSE_STATE_GOT_PAYLOAD = new MAV_states("MAVLINK_PARSE_STATE_GOT_PAYLOAD", 9);
      MAV_states[] arrayOfMAV_states = new MAV_states[10];
      arrayOfMAV_states[0] = MAVLINK_PARSE_STATE_UNINIT;
      arrayOfMAV_states[1] = MAVLINK_PARSE_STATE_IDLE;
      arrayOfMAV_states[2] = MAVLINK_PARSE_STATE_GOT_STX;
      arrayOfMAV_states[3] = MAVLINK_PARSE_STATE_GOT_LENGTH;
      arrayOfMAV_states[4] = MAVLINK_PARSE_STATE_GOT_SEQ;
      arrayOfMAV_states[5] = MAVLINK_PARSE_STATE_GOT_SYSID;
      arrayOfMAV_states[6] = MAVLINK_PARSE_STATE_GOT_COMPID;
      arrayOfMAV_states[7] = MAVLINK_PARSE_STATE_GOT_MSGID;
      arrayOfMAV_states[8] = MAVLINK_PARSE_STATE_GOT_CRC1;
      arrayOfMAV_states[9] = MAVLINK_PARSE_STATE_GOT_PAYLOAD;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.Parser
 * JD-Core Version:    0.6.2
 */