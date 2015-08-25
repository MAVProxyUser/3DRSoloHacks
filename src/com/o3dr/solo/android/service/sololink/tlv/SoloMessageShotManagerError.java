package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class SoloMessageShotManagerError extends TLVPacket
{
  private final String exceptionInfo;

  public SoloMessageShotManagerError(String paramString)
  {
    super(1000, paramString.length());
    this.exceptionInfo = paramString;
  }

  public String getExceptionInfo()
  {
    return this.exceptionInfo;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(this.exceptionInfo.getBytes());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloMessageShotManagerError
 * JD-Core Version:    0.6.2
 */