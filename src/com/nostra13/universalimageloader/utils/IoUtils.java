package com.nostra13.universalimageloader.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IoUtils
{
  public static final int CONTINUE_LOADING_PERCENTAGE = 75;
  public static final int DEFAULT_BUFFER_SIZE = 32768;
  public static final int DEFAULT_IMAGE_TOTAL_SIZE = 512000;

  public static void closeSilently(Closeable paramCloseable)
  {
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static boolean copyStream(InputStream paramInputStream, OutputStream paramOutputStream, CopyListener paramCopyListener)
    throws IOException
  {
    return copyStream(paramInputStream, paramOutputStream, paramCopyListener, 32768);
  }

  public static boolean copyStream(InputStream paramInputStream, OutputStream paramOutputStream, CopyListener paramCopyListener, int paramInt)
    throws IOException
  {
    int i = paramInputStream.available();
    if (i <= 0)
      i = 512000;
    byte[] arrayOfByte = new byte[paramInt];
    boolean bool = shouldStopLoading(paramCopyListener, 0, i);
    int j = 0;
    if (bool)
      return false;
    do
    {
      int k = paramInputStream.read(arrayOfByte, 0, paramInt);
      if (k == -1)
        break;
      paramOutputStream.write(arrayOfByte, 0, k);
      j += k;
    }
    while (!shouldStopLoading(paramCopyListener, j, i));
    return false;
    paramOutputStream.flush();
    return true;
  }

  // ERROR //
  public static void readAndCloseStream(InputStream paramInputStream)
  {
    // Byte code:
    //   0: ldc 9
    //   2: newarray byte
    //   4: astore_1
    //   5: aload_0
    //   6: aload_1
    //   7: iconst_0
    //   8: ldc 9
    //   10: invokevirtual 45	java/io/InputStream:read	([BII)I
    //   13: istore 4
    //   15: iload 4
    //   17: iconst_m1
    //   18: if_icmpne -13 -> 5
    //   21: aload_0
    //   22: invokestatic 58	com/nostra13/universalimageloader/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   25: return
    //   26: astore_3
    //   27: aload_0
    //   28: invokestatic 58	com/nostra13/universalimageloader/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   31: return
    //   32: astore_2
    //   33: aload_0
    //   34: invokestatic 58	com/nostra13/universalimageloader/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   37: aload_2
    //   38: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   5	15	26	java/io/IOException
    //   5	15	32	finally
  }

  private static boolean shouldStopLoading(CopyListener paramCopyListener, int paramInt1, int paramInt2)
  {
    return (paramCopyListener != null) && (!paramCopyListener.onBytesCopied(paramInt1, paramInt2)) && (paramInt1 * 100 / paramInt2 < 75);
  }

  public static abstract interface CopyListener
  {
    public abstract boolean onBytesCopied(int paramInt1, int paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.utils.IoUtils
 * JD-Core Version:    0.6.2
 */