package com.o3dr.solo.android.util.video;

import java.nio.ByteBuffer;

public class NALUChunk
{
  public static final int PPS_NAL_TYPE = 8;
  public static final int SPS_NAL_TYPE = 7;
  public static final byte[] START_CODE = { 0, 0, 0, 1 };
  public int flags;
  public final ByteBuffer[] payloads;
  public long presentationTime;
  public int sequenceNumber;
  public int type;

  public NALUChunk(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    this.payloads = new ByteBuffer[paramInt1];
    for (int i = 0; i < paramInt1; i++)
    {
      this.payloads[i] = ByteBuffer.allocate(paramInt2);
      if (paramArrayOfByte != null)
      {
        this.payloads[i].put(paramArrayOfByte);
        this.payloads[i].mark();
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.video.NALUChunk
 * JD-Core Version:    0.6.2
 */