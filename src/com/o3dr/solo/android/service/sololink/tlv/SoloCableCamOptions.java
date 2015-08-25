package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class SoloCableCamOptions extends SoloShotOptions
{
  private static final int CAM_INTERPOLATION_DISABLED_VALUE = 0;
  private static final int CAM_INTERPOLATION_ENABLED_VALUE = 1;
  private static final int YAW_DIRECTION_CCW_VALUE = 1;
  private static final int YAW_DIRECTION_CW_VALUE;
  private boolean camInterpolation;
  private boolean yawDirectionClockwise;

  SoloCableCamOptions(int paramInt1, int paramInt2, float paramFloat)
  {
  }

  public SoloCableCamOptions(boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
  {
    super(4, 8, paramFloat);
    this.camInterpolation = paramBoolean1;
    this.yawDirectionClockwise = paramBoolean2;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    int i;
    int j;
    if (this.camInterpolation)
    {
      i = 1;
      paramByteBuffer.putShort((short)i);
      boolean bool = this.yawDirectionClockwise;
      j = 0;
      if (!bool)
        break label49;
    }
    while (true)
    {
      paramByteBuffer.putShort((short)j);
      super.getMessageValue(paramByteBuffer);
      return;
      i = 0;
      break;
      label49: j = 1;
    }
  }

  public boolean isCamInterpolationOn()
  {
    return this.camInterpolation;
  }

  public boolean isYawDirectionClockWise()
  {
    return this.yawDirectionClockwise;
  }

  public void setCamInterpolation(boolean paramBoolean)
  {
    this.camInterpolation = paramBoolean;
  }

  public void setYawDirection(boolean paramBoolean)
  {
    this.yawDirectionClockwise = paramBoolean;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloCableCamOptions
 * JD-Core Version:    0.6.2
 */