package com.o3dr.solo.android.util.video;

import android.util.Log;
import java.nio.ByteBuffer;

public class NALUChunkAssembler
{
  private static final int PPS_BUFFER_INDEX = 1;
  private static final int SPS_BUFFER_INDEX;
  private static final String TAG = NALUChunkAssembler.class.getSimpleName();
  private final NALUChunk assembledNaluChunk = new NALUChunk(1, 1048576, NALUChunk.START_CODE);
  private final NALUChunk eosNaluChunk;
  private boolean isPpsSet = false;
  private boolean isSpsSet = false;
  private final NALUChunk paramsNaluChunk = new NALUChunk(2, 256, NALUChunk.START_CODE);

  NALUChunkAssembler()
  {
    this.paramsNaluChunk.type = 78;
    this.paramsNaluChunk.flags = 2;
    this.eosNaluChunk = new NALUChunk(1, 0, null);
    this.eosNaluChunk.flags = 4;
  }

  private boolean areParametersSet()
  {
    return (this.isSpsSet) && (this.isPpsSet);
  }

  NALUChunk assembleNALUChunk(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[12];
    if ((i & 0x80) >> 7 != 0)
    {
      Log.w(TAG, "Forbidden bit is set, indicating possible errors.");
      return null;
    }
    long l = 0L | (0xFF & paramArrayOfByte[4]) << 24 | (0xFF & paramArrayOfByte[5]) << 16 | (0xFF & paramArrayOfByte[6]) << 8 | 0xFF & paramArrayOfByte[7];
    int j = (0xFF & paramArrayOfByte[2]) << 8 | 0xFF & paramArrayOfByte[3];
    int k = i & 0x1F;
    if (k <= 0)
    {
      Log.d(TAG, "Undefined nal type: " + k);
      return null;
    }
    if (k <= 23)
    {
      int i6 = paramInt - 12;
      switch (k)
      {
      default:
        if (!areParametersSet())
          return null;
        break;
      case 7:
      case 8:
        ByteBuffer localByteBuffer2;
        if (k == 7)
        {
          localByteBuffer2 = this.paramsNaluChunk.payloads[0];
          this.isSpsSet = true;
        }
        while (true)
        {
          localByteBuffer2.reset();
          localByteBuffer2.put(paramArrayOfByte, 12, i6);
          if (!areParametersSet())
            break;
          this.paramsNaluChunk.sequenceNumber = j;
          this.paramsNaluChunk.presentationTime = l;
          return this.paramsNaluChunk;
          localByteBuffer2 = this.paramsNaluChunk.payloads[1];
          this.isPpsSet = true;
        }
        return null;
      }
      ByteBuffer localByteBuffer3 = this.assembledNaluChunk.payloads[0];
      localByteBuffer3.reset();
      localByteBuffer3.put(paramArrayOfByte, 12, i6);
      this.assembledNaluChunk.type = k;
      this.assembledNaluChunk.sequenceNumber = j;
      this.assembledNaluChunk.flags = 0;
      this.assembledNaluChunk.presentationTime = l;
      return this.assembledNaluChunk;
    }
    if (k == 28)
    {
      if (!areParametersSet())
        return null;
      int m = paramInt - 14;
      int n = paramArrayOfByte[13];
      int i1 = n & 0x1F;
      int i2 = (n & 0x80) >> 7;
      int i3 = (n & 0x40) >> 6;
      if (i2 == 1)
      {
        ByteBuffer localByteBuffer1 = this.assembledNaluChunk.payloads[0];
        localByteBuffer1.reset();
        localByteBuffer1.put((byte)(i1 | i & 0xE0));
        localByteBuffer1.put(paramArrayOfByte, 14, m);
        int i4;
        NALUChunk localNALUChunk;
        if ((i1 == 7) || (i1 == 8))
        {
          i4 = 1;
          this.assembledNaluChunk.sequenceNumber = j;
          this.assembledNaluChunk.type = i1;
          localNALUChunk = this.assembledNaluChunk;
          if (i4 == 0)
            break label522;
        }
        label522: for (int i5 = 2; ; i5 = 0)
        {
          localNALUChunk.flags = i5;
          this.assembledNaluChunk.presentationTime = l;
          return null;
          i4 = 0;
          break;
        }
      }
      if (j - 1 != this.assembledNaluChunk.sequenceNumber)
        return null;
      this.assembledNaluChunk.payloads[0].put(paramArrayOfByte, 14, m);
      this.assembledNaluChunk.sequenceNumber = j;
      if (i3 == 1)
        return this.assembledNaluChunk;
      return null;
    }
    return null;
  }

  NALUChunk getEndOfStream()
  {
    return this.eosNaluChunk;
  }

  void reset()
  {
    this.isSpsSet = false;
    this.isPpsSet = false;
    this.assembledNaluChunk.payloads[0].reset();
    this.paramsNaluChunk.payloads[0].reset();
    this.paramsNaluChunk.payloads[1].reset();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.video.NALUChunkAssembler
 * JD-Core Version:    0.6.2
 */