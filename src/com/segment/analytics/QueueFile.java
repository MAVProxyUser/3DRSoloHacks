package com.segment.analytics;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

class QueueFile
  implements Closeable
{
  static final int HEADER_LENGTH = 16;
  private static final int INITIAL_LENGTH = 4096;
  private static final Logger LOGGER = Logger.getLogger(QueueFile.class.getName());
  private static final byte[] ZEROES = new byte[4096];
  private final byte[] buffer = new byte[16];
  private int elementCount;
  int fileLength;
  private Element first;
  private Element last;
  final RandomAccessFile raf;

  QueueFile(File paramFile)
    throws IOException
  {
    if (!paramFile.exists())
      initialize(paramFile);
    this.raf = open(paramFile);
    readHeader();
  }

  QueueFile(RandomAccessFile paramRandomAccessFile)
    throws IOException
  {
    this.raf = paramRandomAccessFile;
    readHeader();
  }

  private void expandIfNecessary(int paramInt)
    throws IOException
  {
    int i = paramInt + 4;
    int j = remainingBytes();
    if (j >= i)
      return;
    int k = this.fileLength;
    int m;
    do
    {
      j += k;
      m = k << 1;
      k = m;
    }
    while (j < i);
    setLength(m);
    int n = wrapPosition(4 + this.last.position + this.last.length);
    if (n <= this.first.position)
    {
      FileChannel localFileChannel = this.raf.getChannel();
      localFileChannel.position(this.fileLength);
      int i2 = n - 16;
      if (localFileChannel.transferTo(16L, i2, localFileChannel) != i2)
        throw new AssertionError("Copied insufficient number of bytes!");
      ringErase(16, i2);
    }
    if (this.last.position < this.first.position)
    {
      int i1 = -16 + (this.fileLength + this.last.position);
      writeHeader(m, this.elementCount, this.first.position, i1);
      this.last = new Element(i1, this.last.length);
    }
    while (true)
    {
      this.fileLength = m;
      return;
      writeHeader(m, this.elementCount, this.first.position, this.last.position);
    }
  }

  private static void initialize(File paramFile)
    throws IOException
  {
    File localFile = new File(paramFile.getPath() + ".tmp");
    RandomAccessFile localRandomAccessFile = open(localFile);
    try
    {
      localRandomAccessFile.setLength(4096L);
      localRandomAccessFile.seek(0L);
      byte[] arrayOfByte = new byte[16];
      writeInt(arrayOfByte, 0, 4096);
      localRandomAccessFile.write(arrayOfByte);
      localRandomAccessFile.close();
      if (!localFile.renameTo(paramFile))
        throw new IOException("Rename failed!");
    }
    finally
    {
      localRandomAccessFile.close();
    }
  }

  private static RandomAccessFile open(File paramFile)
    throws FileNotFoundException
  {
    return new RandomAccessFile(paramFile, "rwd");
  }

  private Element readElement(int paramInt)
    throws IOException
  {
    if (paramInt == 0)
      return Element.NULL;
    ringRead(paramInt, this.buffer, 0, 4);
    return new Element(paramInt, readInt(this.buffer, 0));
  }

  private void readHeader()
    throws IOException
  {
    this.raf.seek(0L);
    this.raf.readFully(this.buffer);
    this.fileLength = readInt(this.buffer, 0);
    if (this.fileLength > this.raf.length())
      throw new IOException("File is truncated. Expected length: " + this.fileLength + ", Actual length: " + this.raf.length());
    if (this.fileLength <= 0)
      throw new IOException("File is corrupt; length stored in header (" + this.fileLength + ") is invalid.");
    this.elementCount = readInt(this.buffer, 4);
    int i = readInt(this.buffer, 8);
    int j = readInt(this.buffer, 12);
    this.first = readElement(i);
    this.last = readElement(j);
  }

  private static int readInt(byte[] paramArrayOfByte, int paramInt)
  {
    return ((0xFF & paramArrayOfByte[paramInt]) << 24) + ((0xFF & paramArrayOfByte[(paramInt + 1)]) << 16) + ((0xFF & paramArrayOfByte[(paramInt + 2)]) << 8) + (0xFF & paramArrayOfByte[(paramInt + 3)]);
  }

  private int remainingBytes()
  {
    return this.fileLength - usedBytes();
  }

  private void ringErase(int paramInt1, int paramInt2)
    throws IOException
  {
    while (paramInt2 > 0)
    {
      int i = Math.min(paramInt2, ZEROES.length);
      ringWrite(paramInt1, ZEROES, 0, i);
      paramInt2 -= i;
      paramInt1 += i;
    }
  }

  private void ringRead(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException
  {
    int i = wrapPosition(paramInt1);
    if (i + paramInt3 <= this.fileLength)
    {
      this.raf.seek(i);
      this.raf.readFully(paramArrayOfByte, paramInt2, paramInt3);
      return;
    }
    int j = this.fileLength - i;
    this.raf.seek(i);
    this.raf.readFully(paramArrayOfByte, paramInt2, j);
    this.raf.seek(16L);
    this.raf.readFully(paramArrayOfByte, paramInt2 + j, paramInt3 - j);
  }

  private void ringWrite(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
    throws IOException
  {
    int i = wrapPosition(paramInt1);
    if (i + paramInt3 <= this.fileLength)
    {
      this.raf.seek(i);
      this.raf.write(paramArrayOfByte, paramInt2, paramInt3);
      return;
    }
    int j = this.fileLength - i;
    this.raf.seek(i);
    this.raf.write(paramArrayOfByte, paramInt2, j);
    this.raf.seek(16L);
    this.raf.write(paramArrayOfByte, paramInt2 + j, paramInt3 - j);
  }

  private void setLength(int paramInt)
    throws IOException
  {
    this.raf.setLength(paramInt);
    this.raf.getChannel().force(true);
  }

  private int usedBytes()
  {
    if (this.elementCount == 0)
      return 16;
    if (this.last.position >= this.first.position)
      return 16 + (4 + (this.last.position - this.first.position) + this.last.length);
    return 4 + this.last.position + this.last.length + this.fileLength - this.first.position;
  }

  private int wrapPosition(int paramInt)
  {
    if (paramInt < this.fileLength)
      return paramInt;
    return paramInt + 16 - this.fileLength;
  }

  private void writeHeader(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IOException
  {
    writeInt(this.buffer, 0, paramInt1);
    writeInt(this.buffer, 4, paramInt2);
    writeInt(this.buffer, 8, paramInt3);
    writeInt(this.buffer, 12, paramInt4);
    this.raf.seek(0L);
    this.raf.write(this.buffer);
  }

  private static void writeInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte[paramInt1] = ((byte)(paramInt2 >> 24));
    paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 16));
    paramArrayOfByte[(paramInt1 + 2)] = ((byte)(paramInt2 >> 8));
    paramArrayOfByte[(paramInt1 + 3)] = ((byte)paramInt2);
  }

  void add(byte[] paramArrayOfByte)
    throws IOException
  {
    add(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  void add(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramArrayOfByte == null)
      try
      {
        throw new NullPointerException("data == null");
      }
      finally
      {
      }
    if (((paramInt1 | paramInt2) < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1))
      throw new IndexOutOfBoundsException();
    expandIfNecessary(paramInt2);
    boolean bool = isEmpty();
    int i;
    Element localElement;
    if (bool)
    {
      i = 16;
      localElement = new Element(i, paramInt2);
      writeInt(this.buffer, 0, paramInt2);
      ringWrite(localElement.position, this.buffer, 0, 4);
      ringWrite(4 + localElement.position, paramArrayOfByte, paramInt1, paramInt2);
      if (!bool)
        break label206;
    }
    label206: for (int j = localElement.position; ; j = this.first.position)
    {
      writeHeader(this.fileLength, 1 + this.elementCount, j, localElement.position);
      this.last = localElement;
      this.elementCount = (1 + this.elementCount);
      if (bool)
        this.first = this.last;
      return;
      i = wrapPosition(4 + this.last.position + this.last.length);
      break;
    }
  }

  void clear()
    throws IOException
  {
    try
    {
      writeHeader(4096, 0, 0, 0);
      this.raf.seek(16L);
      this.raf.write(ZEROES, 0, 4080);
      this.elementCount = 0;
      this.first = Element.NULL;
      this.last = Element.NULL;
      if (this.fileLength > 4096)
        setLength(4096);
      this.fileLength = 4096;
      return;
    }
    finally
    {
    }
  }

  public void close()
    throws IOException
  {
    try
    {
      this.raf.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  int forEach(ElementVisitor paramElementVisitor)
    throws IOException
  {
    try
    {
      int i = this.first.position;
      int j = 0;
      Element localElement;
      if (j < this.elementCount)
      {
        localElement = readElement(i);
        boolean bool = paramElementVisitor.read(new ElementInputStream(localElement, null), localElement.length);
        if (bool);
      }
      for (int k = j + 1; ; k = this.elementCount)
      {
        return k;
        i = wrapPosition(4 + localElement.position + localElement.length);
        j++;
        break;
      }
    }
    finally
    {
    }
  }

  boolean isEmpty()
  {
    try
    {
      int i = this.elementCount;
      if (i == 0)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  void peek(ElementVisitor paramElementVisitor)
    throws IOException
  {
    try
    {
      if (this.elementCount > 0)
        paramElementVisitor.read(new ElementInputStream(this.first, null), this.first.length);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  byte[] peek()
    throws IOException
  {
    try
    {
      boolean bool = isEmpty();
      byte[] arrayOfByte;
      if (bool)
        arrayOfByte = null;
      while (true)
      {
        return arrayOfByte;
        int i = this.first.length;
        arrayOfByte = new byte[i];
        ringRead(4 + this.first.position, arrayOfByte, 0, i);
      }
    }
    finally
    {
    }
  }

  void remove()
    throws IOException
  {
    try
    {
      remove(1);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void remove(int paramInt)
    throws IOException
  {
    try
    {
      if (isEmpty())
        throw new NoSuchElementException();
    }
    finally
    {
    }
    if (paramInt > this.elementCount)
      throw new IllegalArgumentException("Cannot remove more elements (" + paramInt + ") than present in queue (" + this.elementCount + ").");
    if (paramInt == this.elementCount)
      clear();
    while (true)
    {
      return;
      int i = this.first.position;
      int j = 0;
      int k = this.first.position;
      int m = this.first.length;
      for (int n = 0; n < paramInt; n++)
      {
        j += m + 4;
        k = wrapPosition(m + (k + 4));
        ringRead(k, this.buffer, 0, 4);
        m = readInt(this.buffer, 0);
      }
      writeHeader(this.fileLength, this.elementCount - paramInt, k, this.last.position);
      this.elementCount -= paramInt;
      this.first = new Element(k, m);
      ringErase(i, j);
    }
  }

  int size()
  {
    try
    {
      int i = this.elementCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String toString()
  {
    final StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName()).append('[');
    localStringBuilder.append("fileLength=").append(this.fileLength);
    localStringBuilder.append(", size=").append(this.elementCount);
    localStringBuilder.append(", first=").append(this.first);
    localStringBuilder.append(", last=").append(this.last);
    localStringBuilder.append(", element lengths=[");
    try
    {
      forEach(new ElementVisitor()
      {
        boolean first = true;

        public boolean read(InputStream paramAnonymousInputStream, int paramAnonymousInt)
          throws IOException
        {
          if (this.first)
            this.first = false;
          while (true)
          {
            localStringBuilder.append(paramAnonymousInt);
            return true;
            localStringBuilder.append(", ");
          }
        }
      });
      localStringBuilder.append("]]");
      return localStringBuilder.toString();
    }
    catch (IOException localIOException)
    {
      while (true)
        LOGGER.log(Level.WARNING, "read error", localIOException);
    }
  }

  static class Element
  {
    static final int HEADER_LENGTH = 4;
    static final Element NULL = new Element(0, 0);
    final int length;
    final int position;

    Element(int paramInt1, int paramInt2)
    {
      this.position = paramInt1;
      this.length = paramInt2;
    }

    public String toString()
    {
      return getClass().getSimpleName() + "[" + "position = " + this.position + ", length = " + this.length + "]";
    }
  }

  private final class ElementInputStream extends InputStream
  {
    private int position;
    private int remaining;

    private ElementInputStream(QueueFile.Element arg2)
    {
      Object localObject;
      this.position = QueueFile.this.wrapPosition(4 + localObject.position);
      this.remaining = localObject.length;
    }

    public int read()
      throws IOException
    {
      if (this.remaining == 0)
        return -1;
      QueueFile.this.raf.seek(this.position);
      int i = QueueFile.this.raf.read();
      this.position = QueueFile.this.wrapPosition(1 + this.position);
      this.remaining = (-1 + this.remaining);
      return i;
    }

    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (((paramInt1 | paramInt2) < 0) || (paramInt2 > paramArrayOfByte.length - paramInt1))
        throw new ArrayIndexOutOfBoundsException();
      if (this.remaining == 0)
        return -1;
      if (paramInt2 > this.remaining)
        paramInt2 = this.remaining;
      QueueFile.this.ringRead(this.position, paramArrayOfByte, paramInt1, paramInt2);
      this.position = QueueFile.this.wrapPosition(paramInt2 + this.position);
      this.remaining -= paramInt2;
      return paramInt2;
    }
  }

  static abstract interface ElementVisitor
  {
    public abstract boolean read(InputStream paramInputStream, int paramInt)
      throws IOException;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.QueueFile
 * JD-Core Version:    0.6.2
 */