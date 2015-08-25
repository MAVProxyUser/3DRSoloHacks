package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class SoloFollowOptions extends SoloShotOptions
{
  private static final int LOOK_AT_DISABLED_VALUE = 0;
  private static final int LOOK_AT_ENABLED_VALUE = 1;
  private static final String TAG = SoloFollowOptions.class.getSimpleName();
  private boolean lookAt;

  public SoloFollowOptions()
  {
    this(0.0F, false);
  }

  SoloFollowOptions(float paramFloat, int paramInt)
  {
  }

  public SoloFollowOptions(float paramFloat, boolean paramBoolean)
  {
    super(19, 8, paramFloat);
    this.lookAt = paramBoolean;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    super.getMessageValue(paramByteBuffer);
    if (this.lookAt);
    for (int i = 1; ; i = 0)
    {
      paramByteBuffer.putInt(i);
      return;
    }
  }

  public boolean isLookAt()
  {
    return this.lookAt;
  }

  public void setLookAt(boolean paramBoolean)
  {
    this.lookAt = paramBoolean;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloFollowOptions
 * JD-Core Version:    0.6.2
 */