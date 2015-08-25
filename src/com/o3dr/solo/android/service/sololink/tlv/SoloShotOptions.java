package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class SoloShotOptions extends TLVPacket
{
  public static final int DEFAULT_ABS_CRUISE_SPEED = 4;
  public static final int MAX_ABS_CRUISE_SPEED = 10;
  public static final int MIN_ABS_CRUISE_SPEED = 1;
  public static final int PAUSED_CRUISE_SPEED;
  private float cruiseSpeed;

  public SoloShotOptions()
  {
    this(20, 4, 0.0F);
  }

  public SoloShotOptions(float paramFloat)
  {
    this(20, 4, paramFloat);
  }

  protected SoloShotOptions(int paramInt1, int paramInt2, float paramFloat)
  {
    super(paramInt1, paramInt2);
    this.cruiseSpeed = paramFloat;
  }

  public float getCruiseSpeed()
  {
    return this.cruiseSpeed;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putFloat(this.cruiseSpeed);
  }

  public void setCruiseSpeed(float paramFloat)
  {
    this.cruiseSpeed = paramFloat;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloShotOptions
 * JD-Core Version:    0.6.2
 */