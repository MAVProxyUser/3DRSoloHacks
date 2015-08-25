package com.MAVLink.ardupilotmega;

public class CRC
{
  private static final int CRC_INIT_VALUE = 65535;
  private static final int[] MAVLINK_MESSAGE_CRCS = { 50, 124, 137, 0, 237, 217, 104, 119, 0, 0, 0, 89, 0, 0, 0, 0, 0, 0, 0, 0, 214, 159, 220, 168, 24, 23, 170, 144, 67, 115, 39, 246, 185, 104, 237, 244, 222, 212, 9, 254, 230, 28, 28, 132, 221, 232, 11, 153, 41, 39, 0, 0, 0, 0, 15, 3, 0, 0, 0, 0, 0, 153, 183, 51, 82, 118, 148, 21, 0, 243, 124, 0, 0, 38, 20, 158, 152, 143, 0, 0, 0, 106, 49, 22, 143, 140, 5, 150, 0, 231, 183, 63, 54, 0, 0, 0, 0, 0, 0, 0, 175, 102, 158, 208, 56, 93, 138, 108, 32, 185, 84, 34, 0, 124, 237, 4, 76, 128, 56, 116, 134, 237, 203, 250, 87, 203, 220, 25, 226, 0, 29, 223, 85, 6, 229, 203, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 49, 0, 134, 219, 208, 188, 84, 22, 19, 21, 134, 0, 78, 68, 189, 127, 154, 21, 21, 144, 1, 234, 73, 181, 22, 83, 167, 138, 234, 240, 47, 189, 52, 174, 229, 85, 0, 0, 72, 0, 0, 0, 0, 92, 36, 71, 0, 0, 0, 0, 0, 0, 134, 205, 94, 128, 54, 63, 112, 201, 221, 226, 238, 103, 235, 14, 0, 77, 50, 163, 115, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 204, 49, 170, 44, 83, 46, 0 };
  private int crcValue;

  public CRC()
  {
    start_checksum();
  }

  public void finish_checksum(int paramInt)
  {
    update_checksum(MAVLINK_MESSAGE_CRCS[paramInt]);
  }

  public int getLSB()
  {
    return 0xFF & this.crcValue;
  }

  public int getMSB()
  {
    return 0xFF & this.crcValue >> 8;
  }

  public void start_checksum()
  {
    this.crcValue = 65535;
  }

  public void update_checksum(int paramInt)
  {
    int i = paramInt & 0xFF ^ 0xFF & this.crcValue;
    int j = i ^ 0xFF & i << 4;
    this.crcValue = (0xFF & this.crcValue >> 8 ^ j << 8 ^ j << 3 ^ 0xF & j >> 4);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.ardupilotmega.CRC
 * JD-Core Version:    0.6.2
 */