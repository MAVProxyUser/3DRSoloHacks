package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class ArtooMessageInputReport extends TLVPacket
{
  private short battery;
  private short gimbalRate;
  private short gimbalY;
  private double timestamp;

  public ArtooMessageInputReport(double paramDouble, short paramShort1, short paramShort2, short paramShort3)
  {
    super(2003, 14);
    this.timestamp = paramDouble;
    this.gimbalY = paramShort1;
    this.gimbalRate = paramShort2;
    this.battery = paramShort3;
  }

  public short getBattery()
  {
    return this.battery;
  }

  public short getGimbalRate()
  {
    return this.gimbalRate;
  }

  public short getGimbalY()
  {
    return this.gimbalY;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putDouble(this.timestamp);
    paramByteBuffer.putShort(this.gimbalY);
    paramByteBuffer.putShort(this.gimbalRate);
    paramByteBuffer.putShort(this.battery);
  }

  public double getTimestamp()
  {
    return this.timestamp;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.ArtooMessageInputReport
 * JD-Core Version:    0.6.2
 */