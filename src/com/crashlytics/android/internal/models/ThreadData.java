package com.crashlytics.android.internal.models;

public class ThreadData
{
  public static final int IMPORTANCE_CRASHED_THREAD = 4;
  public final FrameData[] frames;
  public final int importance;

  public ThreadData(int paramInt, FrameData[] paramArrayOfFrameData)
  {
    this.frames = paramArrayOfFrameData;
    this.importance = paramInt;
  }

  public static final class FrameData
  {
    public final long address;
    public final String file;
    public final int importance;
    public final long offset;
    public final String symbol;

    public FrameData(long paramLong, int paramInt)
    {
      this(paramLong, "", "", 0L, paramInt);
    }

    public FrameData(long paramLong1, String paramString1, String paramString2, long paramLong2, int paramInt)
    {
      this.address = paramLong1;
      this.symbol = paramString1;
      this.file = paramString2;
      this.offset = paramLong2;
      this.importance = paramInt;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.internal.models.ThreadData
 * JD-Core Version:    0.6.2
 */