package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class SoloShotError extends TLVPacket
{
  public static final int SHOT_ERROR_BAD_EKF = 0;
  public static final int SHOT_ERROR_UNARMED = 1;
  private int errorType;

  public SoloShotError(int paramInt)
  {
    super(20, 4);
    this.errorType = paramInt;
  }

  public int getErrorType()
  {
    return this.errorType;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.errorType);
  }

  public void setErrorType(int paramInt)
  {
    this.errorType = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloShotError
 * JD-Core Version:    0.6.2
 */