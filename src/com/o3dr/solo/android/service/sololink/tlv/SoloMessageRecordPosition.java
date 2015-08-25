package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;

public class SoloMessageRecordPosition extends TLVPacket
{
  public SoloMessageRecordPosition()
  {
    super(3, 0);
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloMessageRecordPosition
 * JD-Core Version:    0.6.2
 */