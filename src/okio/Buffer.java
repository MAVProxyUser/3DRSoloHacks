package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Buffer
  implements BufferedSource, BufferedSink, Cloneable
{
  private static final byte[] DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  Segment head;
  long size;

  private void readFrom(InputStream paramInputStream, long paramLong, boolean paramBoolean)
    throws IOException
  {
    if (paramInputStream == null)
      throw new IllegalArgumentException("in == null");
    int j;
    do
    {
      localSegment.limit = (j + localSegment.limit);
      this.size += j;
      paramLong -= j;
      if ((paramLong <= 0L) && (!paramBoolean))
        break;
      Segment localSegment = writableSegment(1);
      int i = (int)Math.min(paramLong, 2048 - localSegment.limit);
      j = paramInputStream.read(localSegment.data, localSegment.limit, i);
    }
    while (j != -1);
    if (paramBoolean)
      return;
    throw new EOFException();
  }

  public Buffer buffer()
  {
    return this;
  }

  public void clear()
  {
    try
    {
      skip(this.size);
      return;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }

  public Buffer clone()
  {
    Buffer localBuffer = new Buffer();
    if (this.size == 0L)
      return localBuffer;
    localBuffer.head = new Segment(this.head);
    Segment localSegment1 = localBuffer.head;
    Segment localSegment2 = localBuffer.head;
    Segment localSegment3 = localBuffer.head;
    localSegment2.prev = localSegment3;
    localSegment1.next = localSegment3;
    for (Segment localSegment4 = this.head.next; localSegment4 != this.head; localSegment4 = localSegment4.next)
      localBuffer.head.prev.push(new Segment(localSegment4));
    localBuffer.size = this.size;
    return localBuffer;
  }

  public void close()
  {
  }

  public long completeSegmentByteCount()
  {
    long l = this.size;
    if (l == 0L)
      return 0L;
    Segment localSegment = this.head.prev;
    if ((localSegment.limit < 2048) && (localSegment.owner))
      l -= localSegment.limit - localSegment.pos;
    return l;
  }

  public Buffer copyTo(OutputStream paramOutputStream)
    throws IOException
  {
    return copyTo(paramOutputStream, 0L, this.size);
  }

  public Buffer copyTo(OutputStream paramOutputStream, long paramLong1, long paramLong2)
    throws IOException
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException("out == null");
    Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
    if (paramLong2 == 0L);
    while (true)
    {
      return this;
      for (Segment localSegment = this.head; paramLong1 >= localSegment.limit - localSegment.pos; localSegment = localSegment.next)
        paramLong1 -= localSegment.limit - localSegment.pos;
      while (paramLong2 > 0L)
      {
        int i = (int)(paramLong1 + localSegment.pos);
        int j = (int)Math.min(localSegment.limit - i, paramLong2);
        paramOutputStream.write(localSegment.data, i, j);
        paramLong2 -= j;
        paramLong1 = 0L;
        localSegment = localSegment.next;
      }
    }
  }

  public Buffer copyTo(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    if (paramBuffer == null)
      throw new IllegalArgumentException("out == null");
    Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
    if (paramLong2 == 0L)
      return this;
    paramBuffer.size = (paramLong2 + paramBuffer.size);
    for (Segment localSegment1 = this.head; paramLong1 >= localSegment1.limit - localSegment1.pos; localSegment1 = localSegment1.next)
      paramLong1 -= localSegment1.limit - localSegment1.pos;
    label92: Segment localSegment2;
    if (paramLong2 > 0L)
    {
      localSegment2 = new Segment(localSegment1);
      localSegment2.pos = ((int)(paramLong1 + localSegment2.pos));
      localSegment2.limit = Math.min(localSegment2.pos + (int)paramLong2, localSegment2.limit);
      if (paramBuffer.head != null)
        break label202;
      localSegment2.prev = localSegment2;
      localSegment2.next = localSegment2;
      paramBuffer.head = localSegment2;
    }
    while (true)
    {
      paramLong2 -= localSegment2.limit - localSegment2.pos;
      paramLong1 = 0L;
      localSegment1 = localSegment1.next;
      break label92;
      break;
      label202: paramBuffer.head.prev.push(localSegment2);
    }
  }

  public BufferedSink emit()
  {
    return this;
  }

  public Buffer emitCompleteSegments()
  {
    return this;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Buffer))
      return false;
    Buffer localBuffer = (Buffer)paramObject;
    if (this.size != localBuffer.size)
      return false;
    if (this.size == 0L)
      return true;
    Segment localSegment1 = this.head;
    Segment localSegment2 = localBuffer.head;
    int i = localSegment1.pos;
    int j = localSegment2.pos;
    long l1 = 0L;
    long l2;
    int m;
    int n;
    if (l1 < this.size)
    {
      l2 = Math.min(localSegment1.limit - i, localSegment2.limit - j);
      int k = 0;
      m = j;
      int i1;
      for (n = i; k < l2; n = i1)
      {
        byte[] arrayOfByte1 = localSegment1.data;
        i1 = n + 1;
        int i2 = arrayOfByte1[n];
        byte[] arrayOfByte2 = localSegment2.data;
        int i3 = m + 1;
        if (i2 != arrayOfByte2[m])
          return false;
        k++;
        m = i3;
      }
      if (n != localSegment1.limit)
        break label245;
      localSegment1 = localSegment1.next;
    }
    label245: for (i = localSegment1.pos; ; i = n)
    {
      if (m == localSegment2.limit)
        localSegment2 = localSegment2.next;
      for (j = localSegment2.pos; ; j = m)
      {
        l1 += l2;
        break;
        return true;
      }
    }
  }

  public boolean exhausted()
  {
    return this.size == 0L;
  }

  public void flush()
  {
  }

  public byte getByte(long paramLong)
  {
    Util.checkOffsetAndCount(this.size, paramLong, 1L);
    for (Segment localSegment = this.head; ; localSegment = localSegment.next)
    {
      int i = localSegment.limit - localSegment.pos;
      if (paramLong < i)
        return localSegment.data[(localSegment.pos + (int)paramLong)];
      paramLong -= i;
    }
  }

  public int hashCode()
  {
    Segment localSegment = this.head;
    if (localSegment == null)
      return 0;
    int i = 1;
    do
    {
      int j = localSegment.pos;
      int k = localSegment.limit;
      while (j < k)
      {
        i = i * 31 + localSegment.data[j];
        j++;
      }
      localSegment = localSegment.next;
    }
    while (localSegment != this.head);
    return i;
  }

  public long indexOf(byte paramByte)
  {
    return indexOf(paramByte, 0L);
  }

  public long indexOf(byte paramByte, long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("fromIndex < 0");
    Segment localSegment = this.head;
    if (localSegment == null)
      return -1L;
    long l1 = 0L;
    int i = localSegment.limit - localSegment.pos;
    if (paramLong >= i);
    for (paramLong -= i; ; paramLong = 0L)
    {
      l1 += i;
      localSegment = localSegment.next;
      if (localSegment != this.head)
        break;
      return -1L;
      byte[] arrayOfByte = localSegment.data;
      long l2 = paramLong + localSegment.pos;
      long l3 = localSegment.limit;
      while (l2 < l3)
      {
        if (arrayOfByte[((int)l2)] == paramByte)
          return l1 + l2 - localSegment.pos;
        l2 += 1L;
      }
    }
  }

  public long indexOfElement(ByteString paramByteString)
  {
    return indexOfElement(paramByteString, 0L);
  }

  public long indexOfElement(ByteString paramByteString, long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("fromIndex < 0");
    Segment localSegment = this.head;
    if (localSegment == null)
      return -1L;
    long l1 = 0L;
    byte[] arrayOfByte1 = paramByteString.toByteArray();
    int i = localSegment.limit - localSegment.pos;
    if (paramLong >= i);
    for (paramLong -= i; ; paramLong = 0L)
    {
      l1 += i;
      localSegment = localSegment.next;
      if (localSegment != this.head)
        break;
      return -1L;
      byte[] arrayOfByte2 = localSegment.data;
      long l2 = paramLong + localSegment.pos;
      long l3 = localSegment.limit;
      while (l2 < l3)
      {
        int j = arrayOfByte2[((int)l2)];
        int k = arrayOfByte1.length;
        for (int m = 0; m < k; m++)
          if (j == arrayOfByte1[m])
            return l1 + l2 - localSegment.pos;
        l2 += 1L;
      }
    }
  }

  public InputStream inputStream()
  {
    return new InputStream()
    {
      public int available()
      {
        return (int)Math.min(Buffer.this.size, 2147483647L);
      }

      public void close()
      {
      }

      public int read()
      {
        if (Buffer.this.size > 0L)
          return 0xFF & Buffer.this.readByte();
        return -1;
      }

      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return Buffer.this.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }

      public String toString()
      {
        return Buffer.this + ".inputStream()";
      }
    };
  }

  public OutputStream outputStream()
  {
    return new OutputStream()
    {
      public void close()
      {
      }

      public void flush()
      {
      }

      public String toString()
      {
        return this + ".outputStream()";
      }

      public void write(int paramAnonymousInt)
      {
        Buffer.this.writeByte((byte)paramAnonymousInt);
      }

      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Buffer.this.write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
    };
  }

  public int read(byte[] paramArrayOfByte)
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = this.head;
    int i;
    if (localSegment == null)
      i = -1;
    do
    {
      return i;
      i = Math.min(paramInt2, localSegment.limit - localSegment.pos);
      System.arraycopy(localSegment.data, localSegment.pos, paramArrayOfByte, paramInt1, i);
      localSegment.pos = (i + localSegment.pos);
      this.size -= i;
    }
    while (localSegment.pos != localSegment.limit);
    this.head = localSegment.pop();
    SegmentPool.recycle(localSegment);
    return i;
  }

  public long read(Buffer paramBuffer, long paramLong)
  {
    if (paramBuffer == null)
      throw new IllegalArgumentException("sink == null");
    if (paramLong < 0L)
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    if (this.size == 0L)
      return -1L;
    if (paramLong > this.size)
      paramLong = this.size;
    paramBuffer.write(this, paramLong);
    return paramLong;
  }

  public long readAll(Sink paramSink)
    throws IOException
  {
    long l = this.size;
    if (l > 0L)
      paramSink.write(this, l);
    return l;
  }

  public byte readByte()
  {
    if (this.size == 0L)
      throw new IllegalStateException("size == 0");
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    byte b = arrayOfByte[i];
    this.size -= 1L;
    if (k == j)
    {
      this.head = localSegment.pop();
      SegmentPool.recycle(localSegment);
      return b;
    }
    localSegment.pos = k;
    return b;
  }

  public byte[] readByteArray()
  {
    try
    {
      byte[] arrayOfByte = readByteArray(this.size);
      return arrayOfByte;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }

  public byte[] readByteArray(long paramLong)
    throws EOFException
  {
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    if (paramLong > 2147483647L)
      throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + paramLong);
    byte[] arrayOfByte = new byte[(int)paramLong];
    readFully(arrayOfByte);
    return arrayOfByte;
  }

  public ByteString readByteString()
  {
    return new ByteString(readByteArray());
  }

  public ByteString readByteString(long paramLong)
    throws EOFException
  {
    return new ByteString(readByteArray(paramLong));
  }

  public long readDecimalLong()
  {
    if (this.size == 0L)
      throw new IllegalStateException("size == 0");
    long l1 = 0L;
    int i = 0;
    int j = 0;
    int k = 0;
    long l2 = -7L;
    while (true)
    {
      Segment localSegment = this.head;
      byte[] arrayOfByte = localSegment.data;
      int m = localSegment.pos;
      int n = localSegment.limit;
      if (m < n)
      {
        int i1 = arrayOfByte[m];
        if ((i1 >= 48) && (i1 <= 57))
        {
          int i2 = 48 - i1;
          if ((l1 < -922337203685477580L) || ((l1 == -922337203685477580L) && (i2 < l2)))
          {
            Buffer localBuffer = new Buffer().writeDecimalLong(l1).writeByte(i1);
            if (j == 0)
              localBuffer.readByte();
            throw new NumberFormatException("Number too large: " + localBuffer.readUtf8());
          }
          l1 = l1 * 10L + i2;
        }
        while (true)
        {
          m++;
          i++;
          break;
          if ((i1 != 45) || (i != 0))
            break label224;
          j = 1;
          l2 -= 1L;
        }
        label224: if (i == 0)
          throw new NumberFormatException("Expected leading [0-9] or '-' character but was 0x" + Integer.toHexString(i1));
        k = 1;
      }
      if (m == n)
      {
        this.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      while ((k != 0) || (this.head == null))
      {
        this.size -= i;
        if (j == 0)
          break label324;
        return l1;
        localSegment.pos = m;
      }
    }
    label324: return -l1;
  }

  public Buffer readFrom(InputStream paramInputStream)
    throws IOException
  {
    readFrom(paramInputStream, 9223372036854775807L, true);
    return this;
  }

  public Buffer readFrom(InputStream paramInputStream, long paramLong)
    throws IOException
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    readFrom(paramInputStream, paramLong, false);
    return this;
  }

  public void readFully(Buffer paramBuffer, long paramLong)
    throws EOFException
  {
    if (this.size < paramLong)
    {
      paramBuffer.write(this, this.size);
      throw new EOFException();
    }
    paramBuffer.write(this, paramLong);
  }

  public void readFully(byte[] paramArrayOfByte)
    throws EOFException
  {
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j == -1)
        throw new EOFException();
      i += j;
    }
  }

  public long readHexadecimalUnsignedLong()
  {
    if (this.size == 0L)
      throw new IllegalStateException("size == 0");
    long l = 0L;
    int i = 0;
    int j = 0;
    label287: label312: 
    while (true)
    {
      Segment localSegment = this.head;
      byte[] arrayOfByte = localSegment.data;
      int k = localSegment.pos;
      int m = localSegment.limit;
      int i1;
      if (k < m)
      {
        int n = arrayOfByte[k];
        if ((n >= 48) && (n <= 57))
          i1 = n - 48;
        while (true)
        {
          if ((0x0 & l) == 0L)
            break label287;
          Buffer localBuffer = new Buffer().writeHexadecimalUnsignedLong(l).writeByte(n);
          throw new NumberFormatException("Number too large: " + localBuffer.readUtf8());
          if ((n >= 97) && (n <= 102))
          {
            i1 = 10 + (n - 97);
          }
          else
          {
            if ((n < 65) || (n > 70))
              break;
            i1 = 10 + (n - 65);
          }
        }
        if (i == 0)
          throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(n));
        j = 1;
      }
      if (k == m)
      {
        this.head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      while (true)
      {
        if ((j == 0) && (this.head != null))
          break label312;
        this.size -= i;
        return l;
        l = l << 4 | i1;
        k++;
        i++;
        break;
        localSegment.pos = k;
      }
    }
  }

  public int readInt()
  {
    if (this.size < 4L)
      throw new IllegalStateException("size < 4: " + this.size);
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    if (j - i < 4)
      return (0xFF & readByte()) << 24 | (0xFF & readByte()) << 16 | (0xFF & readByte()) << 8 | 0xFF & readByte();
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    int m = (0xFF & arrayOfByte[i]) << 24;
    int n = k + 1;
    int i1 = m | (0xFF & arrayOfByte[k]) << 16;
    int i2 = n + 1;
    int i3 = i1 | (0xFF & arrayOfByte[n]) << 8;
    int i4 = i2 + 1;
    int i5 = i3 | 0xFF & arrayOfByte[i2];
    this.size -= 4L;
    if (i4 == j)
    {
      this.head = localSegment.pop();
      SegmentPool.recycle(localSegment);
      return i5;
    }
    localSegment.pos = i4;
    return i5;
  }

  public int readIntLe()
  {
    return Util.reverseBytesInt(readInt());
  }

  public long readLong()
  {
    if (this.size < 8L)
      throw new IllegalStateException("size < 8: " + this.size);
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    if (j - i < 8)
      return (0xFFFFFFFF & readInt()) << 32 | 0xFFFFFFFF & readInt();
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    long l1 = (0xFF & arrayOfByte[i]) << 56;
    int m = k + 1;
    long l2 = l1 | (0xFF & arrayOfByte[k]) << 48;
    int n = m + 1;
    long l3 = l2 | (0xFF & arrayOfByte[m]) << 40;
    int i1 = n + 1;
    long l4 = l3 | (0xFF & arrayOfByte[n]) << 32;
    int i2 = i1 + 1;
    long l5 = l4 | (0xFF & arrayOfByte[i1]) << 24;
    int i3 = i2 + 1;
    long l6 = l5 | (0xFF & arrayOfByte[i2]) << 16;
    int i4 = i3 + 1;
    long l7 = l6 | (0xFF & arrayOfByte[i3]) << 8;
    int i5 = i4 + 1;
    long l8 = l7 | 0xFF & arrayOfByte[i4];
    this.size -= 8L;
    if (i5 == j)
    {
      this.head = localSegment.pop();
      SegmentPool.recycle(localSegment);
      return l8;
    }
    localSegment.pos = i5;
    return l8;
  }

  public long readLongLe()
  {
    return Util.reverseBytesLong(readLong());
  }

  public short readShort()
  {
    if (this.size < 2L)
      throw new IllegalStateException("size < 2: " + this.size);
    Segment localSegment = this.head;
    int i = localSegment.pos;
    int j = localSegment.limit;
    if (j - i < 2)
      return (short)((0xFF & readByte()) << 8 | 0xFF & readByte());
    byte[] arrayOfByte = localSegment.data;
    int k = i + 1;
    int m = (0xFF & arrayOfByte[i]) << 8;
    int n = k + 1;
    int i1 = m | 0xFF & arrayOfByte[k];
    this.size -= 2L;
    if (n == j)
    {
      this.head = localSegment.pop();
      SegmentPool.recycle(localSegment);
    }
    while (true)
    {
      return (short)i1;
      localSegment.pos = n;
    }
  }

  public short readShortLe()
  {
    return Util.reverseBytesShort(readShort());
  }

  public String readString(long paramLong, Charset paramCharset)
    throws EOFException
  {
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    if (paramCharset == null)
      throw new IllegalArgumentException("charset == null");
    if (paramLong > 2147483647L)
      throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + paramLong);
    String str;
    if (paramLong == 0L)
      str = "";
    Segment localSegment;
    do
    {
      return str;
      localSegment = this.head;
      if (paramLong + localSegment.pos > localSegment.limit)
        return new String(readByteArray(paramLong), paramCharset);
      str = new String(localSegment.data, localSegment.pos, (int)paramLong, paramCharset);
      localSegment.pos = ((int)(paramLong + localSegment.pos));
      this.size -= paramLong;
    }
    while (localSegment.pos != localSegment.limit);
    this.head = localSegment.pop();
    SegmentPool.recycle(localSegment);
    return str;
  }

  public String readString(Charset paramCharset)
  {
    try
    {
      String str = readString(this.size, paramCharset);
      return str;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }

  public String readUtf8()
  {
    try
    {
      String str = readString(this.size, Util.UTF_8);
      return str;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }

  public String readUtf8(long paramLong)
    throws EOFException
  {
    return readString(paramLong, Util.UTF_8);
  }

  public String readUtf8Line()
    throws EOFException
  {
    long l = indexOf((byte)10);
    if (l == -1L)
    {
      if (this.size != 0L)
        return readUtf8(this.size);
      return null;
    }
    return readUtf8Line(l);
  }

  String readUtf8Line(long paramLong)
    throws EOFException
  {
    if ((paramLong > 0L) && (getByte(paramLong - 1L) == 13))
    {
      String str2 = readUtf8(paramLong - 1L);
      skip(2L);
      return str2;
    }
    String str1 = readUtf8(paramLong);
    skip(1L);
    return str1;
  }

  public String readUtf8LineStrict()
    throws EOFException
  {
    long l = indexOf((byte)10);
    if (l == -1L)
    {
      Buffer localBuffer = new Buffer();
      copyTo(localBuffer, 0L, Math.min(32L, this.size));
      throw new EOFException("\\n not found: size=" + size() + " content=" + localBuffer.readByteString().hex() + "...");
    }
    return readUtf8Line(l);
  }

  public boolean request(long paramLong)
  {
    return this.size >= paramLong;
  }

  public void require(long paramLong)
    throws EOFException
  {
    if (this.size < paramLong)
      throw new EOFException();
  }

  List<Integer> segmentSizes()
  {
    Object localObject;
    if (this.head == null)
      localObject = Collections.emptyList();
    while (true)
    {
      return localObject;
      localObject = new ArrayList();
      ((List)localObject).add(Integer.valueOf(this.head.limit - this.head.pos));
      for (Segment localSegment = this.head.next; localSegment != this.head; localSegment = localSegment.next)
        ((List)localObject).add(Integer.valueOf(localSegment.limit - localSegment.pos));
    }
  }

  public long size()
  {
    return this.size;
  }

  public void skip(long paramLong)
    throws EOFException
  {
    while (paramLong > 0L)
    {
      if (this.head == null)
        throw new EOFException();
      int i = (int)Math.min(paramLong, this.head.limit - this.head.pos);
      this.size -= i;
      paramLong -= i;
      Segment localSegment1 = this.head;
      localSegment1.pos = (i + localSegment1.pos);
      if (this.head.pos == this.head.limit)
      {
        Segment localSegment2 = this.head;
        this.head = localSegment2.pop();
        SegmentPool.recycle(localSegment2);
      }
    }
  }

  public ByteString snapshot()
  {
    if (this.size > 2147483647L)
      throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.size);
    return snapshot((int)this.size);
  }

  public ByteString snapshot(int paramInt)
  {
    if (paramInt == 0)
      return ByteString.EMPTY;
    return new SegmentedByteString(this, paramInt);
  }

  public Timeout timeout()
  {
    return Timeout.NONE;
  }

  public String toString()
  {
    if (this.size == 0L)
      return "Buffer[size=0]";
    if (this.size <= 16L)
    {
      ByteString localByteString = clone().readByteString();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Long.valueOf(this.size);
      arrayOfObject2[1] = localByteString.hex();
      return String.format("Buffer[size=%s data=%s]", arrayOfObject2);
    }
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
      for (Segment localSegment = this.head.next; localSegment != this.head; localSegment = localSegment.next)
        localMessageDigest.update(localSegment.data, localSegment.pos, localSegment.limit - localSegment.pos);
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Long.valueOf(this.size);
      arrayOfObject1[1] = ByteString.of(localMessageDigest.digest()).hex();
      String str = String.format("Buffer[size=%s md5=%s]", arrayOfObject1);
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
    }
    throw new AssertionError();
  }

  Segment writableSegment(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 2048))
      throw new IllegalArgumentException();
    Segment localSegment1;
    if (this.head == null)
    {
      this.head = SegmentPool.take();
      Segment localSegment2 = this.head;
      Segment localSegment3 = this.head;
      localSegment1 = this.head;
      localSegment3.prev = localSegment1;
      localSegment2.next = localSegment1;
    }
    do
    {
      return localSegment1;
      localSegment1 = this.head.prev;
    }
    while ((paramInt + localSegment1.limit <= 2048) && (localSegment1.owner));
    return localSegment1.push(SegmentPool.take());
  }

  public Buffer write(ByteString paramByteString)
  {
    if (paramByteString == null)
      throw new IllegalArgumentException("byteString == null");
    paramByteString.write(this);
    return this;
  }

  public Buffer write(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("source == null");
    return write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public Buffer write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("source == null");
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    int i = paramInt1 + paramInt2;
    while (paramInt1 < i)
    {
      Segment localSegment = writableSegment(1);
      int j = Math.min(i - paramInt1, 2048 - localSegment.limit);
      System.arraycopy(paramArrayOfByte, paramInt1, localSegment.data, localSegment.limit, j);
      paramInt1 += j;
      localSegment.limit = (j + localSegment.limit);
    }
    this.size += paramInt2;
    return this;
  }

  public BufferedSink write(Source paramSource, long paramLong)
    throws IOException
  {
    while (paramLong > 0L)
    {
      long l = paramSource.read(this, paramLong);
      if (l == -1L)
        throw new EOFException();
      paramLong -= l;
    }
    return this;
  }

  public void write(Buffer paramBuffer, long paramLong)
  {
    if (paramBuffer == null)
      throw new IllegalArgumentException("source == null");
    if (paramBuffer == this)
      throw new IllegalArgumentException("source == this");
    Util.checkOffsetAndCount(paramBuffer.size, 0L, paramLong);
    Segment localSegment5;
    long l2;
    if (paramLong > 0L)
    {
      if (paramLong >= paramBuffer.head.limit - paramBuffer.head.pos)
        break label191;
      if (this.head == null)
        break label162;
      localSegment5 = this.head.prev;
      if ((localSegment5 == null) || (!localSegment5.owner))
        break label178;
      l2 = paramLong + localSegment5.limit;
      if (!localSegment5.shared)
        break label168;
    }
    label162: label168: for (int i = 0; ; i = localSegment5.pos)
    {
      if (l2 - i > 2048L)
        break label178;
      paramBuffer.head.writeTo(localSegment5, (int)paramLong);
      paramBuffer.size -= paramLong;
      this.size = (paramLong + this.size);
      return;
      localSegment5 = null;
      break;
    }
    label178: paramBuffer.head = paramBuffer.head.split((int)paramLong);
    label191: Segment localSegment1 = paramBuffer.head;
    long l1 = localSegment1.limit - localSegment1.pos;
    paramBuffer.head = localSegment1.pop();
    if (this.head == null)
    {
      this.head = localSegment1;
      Segment localSegment2 = this.head;
      Segment localSegment3 = this.head;
      Segment localSegment4 = this.head;
      localSegment3.prev = localSegment4;
      localSegment2.next = localSegment4;
    }
    while (true)
    {
      paramBuffer.size -= l1;
      this.size = (l1 + this.size);
      paramLong -= l1;
      break;
      this.head.prev.push(localSegment1).compact();
    }
  }

  public long writeAll(Source paramSource)
    throws IOException
  {
    if (paramSource == null)
      throw new IllegalArgumentException("source == null");
    long l2;
    for (long l1 = 0L; ; l1 += l2)
    {
      l2 = paramSource.read(this, 2048L);
      if (l2 == -1L)
        break;
    }
    return l1;
  }

  public Buffer writeByte(int paramInt)
  {
    Segment localSegment = writableSegment(1);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    localSegment.limit = (i + 1);
    arrayOfByte[i] = ((byte)paramInt);
    this.size = (1L + this.size);
    return this;
  }

  public Buffer writeDecimalLong(long paramLong)
  {
    if (paramLong == 0L)
      return writeByte(48);
    boolean bool = paramLong < 0L;
    int i = 0;
    if (bool)
    {
      paramLong = -paramLong;
      if (paramLong < 0L)
        return writeUtf8("-9223372036854775808");
      i = 1;
    }
    int j;
    if (paramLong < 100000000L)
      if (paramLong < 10000L)
        if (paramLong < 100L)
          if (paramLong < 10L)
            j = 1;
    Segment localSegment;
    byte[] arrayOfByte;
    int k;
    while (true)
    {
      if (i != 0)
        j++;
      localSegment = writableSegment(j);
      arrayOfByte = localSegment.data;
      k = j + localSegment.limit;
      while (paramLong != 0L)
      {
        int m = (int)(paramLong % 10L);
        k--;
        arrayOfByte[k] = DIGITS[m];
        paramLong /= 10L;
      }
      j = 2;
      continue;
      if (paramLong < 1000L)
      {
        j = 3;
      }
      else
      {
        j = 4;
        continue;
        if (paramLong < 1000000L)
        {
          if (paramLong < 100000L)
            j = 5;
          else
            j = 6;
        }
        else if (paramLong < 10000000L)
        {
          j = 7;
        }
        else
        {
          j = 8;
          continue;
          if (paramLong < 1000000000000L)
          {
            if (paramLong < 10000000000L)
            {
              if (paramLong < 1000000000L)
                j = 9;
              else
                j = 10;
            }
            else if (paramLong < 100000000000L)
              j = 11;
            else
              j = 12;
          }
          else if (paramLong < 1000000000000000L)
          {
            if (paramLong < 10000000000000L)
              j = 13;
            else if (paramLong < 100000000000000L)
              j = 14;
            else
              j = 15;
          }
          else if (paramLong < 100000000000000000L)
          {
            if (paramLong < 10000000000000000L)
              j = 16;
            else
              j = 17;
          }
          else if (paramLong < 1000000000000000000L)
            j = 18;
          else
            j = 19;
        }
      }
    }
    if (i != 0)
      arrayOfByte[(k - 1)] = 45;
    localSegment.limit = (j + localSegment.limit);
    this.size += j;
    return this;
  }

  public Buffer writeHexadecimalUnsignedLong(long paramLong)
  {
    if (paramLong == 0L)
      return writeByte(48);
    int i = 1 + Long.numberOfTrailingZeros(Long.highestOneBit(paramLong)) / 4;
    Segment localSegment = writableSegment(i);
    byte[] arrayOfByte = localSegment.data;
    int j = -1 + (i + localSegment.limit);
    int k = localSegment.limit;
    while (j >= k)
    {
      arrayOfByte[j] = DIGITS[((int)(0xF & paramLong))];
      paramLong >>>= 4;
      j--;
    }
    localSegment.limit = (i + localSegment.limit);
    this.size += i;
    return this;
  }

  public Buffer writeInt(int paramInt)
  {
    Segment localSegment = writableSegment(4);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(0xFF & paramInt >>> 24));
    int k = j + 1;
    arrayOfByte[j] = ((byte)(0xFF & paramInt >>> 16));
    int m = k + 1;
    arrayOfByte[k] = ((byte)(0xFF & paramInt >>> 8));
    int n = m + 1;
    arrayOfByte[m] = ((byte)(paramInt & 0xFF));
    localSegment.limit = n;
    this.size = (4L + this.size);
    return this;
  }

  public Buffer writeIntLe(int paramInt)
  {
    return writeInt(Util.reverseBytesInt(paramInt));
  }

  public Buffer writeLong(long paramLong)
  {
    Segment localSegment = writableSegment(8);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(int)(0xFF & paramLong >>> 56));
    int k = j + 1;
    arrayOfByte[j] = ((byte)(int)(0xFF & paramLong >>> 48));
    int m = k + 1;
    arrayOfByte[k] = ((byte)(int)(0xFF & paramLong >>> 40));
    int n = m + 1;
    arrayOfByte[m] = ((byte)(int)(0xFF & paramLong >>> 32));
    int i1 = n + 1;
    arrayOfByte[n] = ((byte)(int)(0xFF & paramLong >>> 24));
    int i2 = i1 + 1;
    arrayOfByte[i1] = ((byte)(int)(0xFF & paramLong >>> 16));
    int i3 = i2 + 1;
    arrayOfByte[i2] = ((byte)(int)(0xFF & paramLong >>> 8));
    int i4 = i3 + 1;
    arrayOfByte[i3] = ((byte)(int)(paramLong & 0xFF));
    localSegment.limit = i4;
    this.size = (8L + this.size);
    return this;
  }

  public Buffer writeLongLe(long paramLong)
  {
    return writeLong(Util.reverseBytesLong(paramLong));
  }

  public Buffer writeShort(int paramInt)
  {
    Segment localSegment = writableSegment(2);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(0xFF & paramInt >>> 8));
    int k = j + 1;
    arrayOfByte[j] = ((byte)(paramInt & 0xFF));
    localSegment.limit = k;
    this.size = (2L + this.size);
    return this;
  }

  public Buffer writeShortLe(int paramInt)
  {
    return writeShort(Util.reverseBytesShort((short)paramInt));
  }

  public Buffer writeString(String paramString, Charset paramCharset)
  {
    if (paramString == null)
      throw new IllegalArgumentException("string == null");
    if (paramCharset == null)
      throw new IllegalArgumentException("charset == null");
    if (paramCharset.equals(Util.UTF_8))
      return writeUtf8(paramString);
    byte[] arrayOfByte = paramString.getBytes(paramCharset);
    return write(arrayOfByte, 0, arrayOfByte.length);
  }

  public Buffer writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    return writeTo(paramOutputStream, this.size);
  }

  public Buffer writeTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException("out == null");
    Util.checkOffsetAndCount(this.size, 0L, paramLong);
    Segment localSegment1 = this.head;
    while (paramLong > 0L)
    {
      int i = (int)Math.min(paramLong, localSegment1.limit - localSegment1.pos);
      paramOutputStream.write(localSegment1.data, localSegment1.pos, i);
      localSegment1.pos = (i + localSegment1.pos);
      this.size -= i;
      paramLong -= i;
      if (localSegment1.pos == localSegment1.limit)
      {
        Segment localSegment2 = localSegment1;
        localSegment1 = localSegment2.pop();
        this.head = localSegment1;
        SegmentPool.recycle(localSegment2);
      }
    }
    return this;
  }

  public Buffer writeUtf8(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("string == null");
    int i = paramString.length();
    int j = 0;
    if (j < i)
    {
      int k = paramString.charAt(j);
      byte[] arrayOfByte;
      int i2;
      int i5;
      label96: int i7;
      int m;
      if (k < 128)
      {
        Segment localSegment = writableSegment(1);
        arrayOfByte = localSegment.data;
        i2 = localSegment.limit - j;
        int i3 = Math.min(i, 2048 - i2);
        int i4 = j + 1;
        arrayOfByte[(i2 + j)] = ((byte)k);
        i5 = i4;
        if (i5 < i3)
        {
          i7 = paramString.charAt(i5);
          if (i7 < 128);
        }
        else
        {
          int i6 = i5 + i2 - localSegment.limit;
          localSegment.limit = (i6 + localSegment.limit);
          this.size += i6;
          m = i5;
        }
      }
      while (true)
      {
        j = m;
        break;
        int i8 = i5 + 1;
        arrayOfByte[(i2 + i5)] = ((byte)i7);
        i5 = i8;
        break label96;
        if (k < 2048)
        {
          writeByte(0xC0 | k >> 6);
          writeByte(0x80 | k & 0x3F);
          m = j + 1;
        }
        else if ((k < 55296) || (k > 57343))
        {
          writeByte(0xE0 | k >> 12);
          writeByte(0x80 | 0x3F & k >> 6);
          writeByte(0x80 | k & 0x3F);
          m = j + 1;
        }
        else
        {
          if (j + 1 < i);
          for (int n = paramString.charAt(j + 1); ; n = 0)
          {
            if ((k <= 56319) && (n >= 56320) && (n <= 57343))
              break label363;
            writeByte(63);
            j++;
            break;
          }
          label363: int i1 = 65536 + ((0xFFFF27FF & k) << 10 | 0xFFFF23FF & n);
          writeByte(0xF0 | i1 >> 18);
          writeByte(0x80 | 0x3F & i1 >> 12);
          writeByte(0x80 | 0x3F & i1 >> 6);
          writeByte(0x80 | i1 & 0x3F);
          m = j + 2;
        }
      }
    }
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.Buffer
 * JD-Core Version:    0.6.2
 */