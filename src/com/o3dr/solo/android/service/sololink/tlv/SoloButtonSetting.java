package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public abstract class SoloButtonSetting extends TLVPacket
{
  private int button;
  private int event;
  private int flightMode;
  private int shotType;

  public SoloButtonSetting(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    super(paramInt1, 16);
    this.button = paramInt2;
    this.event = paramInt3;
    this.shotType = paramInt4;
    this.flightMode = paramInt5;
  }

  public int getButton()
  {
    return this.button;
  }

  public int getEvent()
  {
    return this.event;
  }

  public int getFlightMode()
  {
    return this.flightMode;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.button);
    paramByteBuffer.putInt(this.event);
    paramByteBuffer.putInt(this.shotType);
    paramByteBuffer.putInt(this.flightMode);
  }

  public int getShotType()
  {
    return this.shotType;
  }

  public void setButton(int paramInt)
  {
    this.button = paramInt;
  }

  public void setEvent(int paramInt)
  {
    this.event = paramInt;
  }

  public void setShotTypeFlightMode(int paramInt1, int paramInt2)
  {
    this.shotType = paramInt1;
    this.flightMode = paramInt2;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloButtonSetting
 * JD-Core Version:    0.6.2
 */