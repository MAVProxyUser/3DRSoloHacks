package okio;

import java.nio.charset.Charset;

final class Util
{
  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static boolean arrayRangeEquals(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < paramInt3; i++)
      if (paramArrayOfByte1[(i + paramInt1)] != paramArrayOfByte2[(i + paramInt2)])
        return false;
    return true;
  }

  public static void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3)
  {
    if (((paramLong2 | paramLong3) < 0L) || (paramLong2 > paramLong1) || (paramLong1 - paramLong2 < paramLong3))
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Long.valueOf(paramLong1);
      arrayOfObject[1] = Long.valueOf(paramLong2);
      arrayOfObject[2] = Long.valueOf(paramLong3);
      throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", arrayOfObject));
    }
  }

  public static int reverseBytesInt(int paramInt)
  {
    return (0xFF000000 & paramInt) >>> 24 | (0xFF0000 & paramInt) >>> 8 | (0xFF00 & paramInt) << 8 | (paramInt & 0xFF) << 24;
  }

  public static long reverseBytesLong(long paramLong)
  {
    return (0x0 & paramLong) >>> 56 | (0x0 & paramLong) >>> 40 | (0x0 & paramLong) >>> 24 | (0x0 & paramLong) >>> 8 | (0xFF000000 & paramLong) << 8 | (0xFF0000 & paramLong) << 24 | (0xFF00 & paramLong) << 40 | (0xFF & paramLong) << 56;
  }

  public static short reverseBytesShort(short paramShort)
  {
    int i = paramShort & 0xFFFF;
    return (short)((0xFF00 & i) >>> 8 | (i & 0xFF) << 8);
  }

  public static void sneakyRethrow(Throwable paramThrowable)
  {
    sneakyThrow2(paramThrowable);
  }

  private static <T extends Throwable> void sneakyThrow2(Throwable paramThrowable)
    throws Throwable
  {
    throw paramThrowable;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.Util
 * JD-Core Version:    0.6.2
 */