package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

final class SegmentedByteString extends ByteString
{
  final transient int[] directory;
  final transient byte[][] segments;

  SegmentedByteString(Buffer paramBuffer, int paramInt)
  {
    super(null);
    Util.checkOffsetAndCount(paramBuffer.size, 0L, paramInt);
    int i = 0;
    int j = 0;
    for (Segment localSegment1 = paramBuffer.head; i < paramInt; localSegment1 = localSegment1.next)
    {
      if (localSegment1.limit == localSegment1.pos)
        throw new AssertionError("s.limit == s.pos");
      i += localSegment1.limit - localSegment1.pos;
      j++;
    }
    this.segments = new byte[j][];
    this.directory = new int[j * 2];
    int k = 0;
    int m = 0;
    for (Segment localSegment2 = paramBuffer.head; k < paramInt; localSegment2 = localSegment2.next)
    {
      this.segments[m] = localSegment2.data;
      k += localSegment2.limit - localSegment2.pos;
      this.directory[m] = k;
      this.directory[(m + this.segments.length)] = localSegment2.pos;
      localSegment2.shared = true;
      m++;
    }
  }

  private int segment(int paramInt)
  {
    int i = Arrays.binarySearch(this.directory, 0, this.segments.length, paramInt + 1);
    if (i >= 0)
      return i;
    return i ^ 0xFFFFFFFF;
  }

  private ByteString toByteString()
  {
    return new ByteString(toByteArray());
  }

  private Object writeReplace()
  {
    return toByteString();
  }

  public String base64()
  {
    return toByteString().base64();
  }

  public String base64Url()
  {
    return toByteString().base64Url();
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (((paramObject instanceof ByteString)) && (((ByteString)paramObject).size() == size()) && (rangeEquals(0, (ByteString)paramObject, 0, size())));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public byte getByte(int paramInt)
  {
    Util.checkOffsetAndCount(this.directory[(-1 + this.segments.length)], paramInt, 1L);
    int i = segment(paramInt);
    if (i == 0);
    for (int j = 0; ; j = this.directory[(i - 1)])
    {
      int k = this.directory[(i + this.segments.length)];
      return this.segments[i][(k + (paramInt - j))];
    }
  }

  public int hashCode()
  {
    int i = this.hashCode;
    if (i != 0)
      return i;
    int j = 1;
    int k = 0;
    int m = 0;
    int n = this.segments.length;
    while (m < n)
    {
      byte[] arrayOfByte = this.segments[m];
      int i1 = this.directory[(n + m)];
      int i2 = this.directory[m];
      int i3 = i2 - k;
      int i4 = i1;
      int i5 = i1 + i3;
      while (i4 < i5)
      {
        j = j * 31 + arrayOfByte[i4];
        i4++;
      }
      k = i2;
      m++;
    }
    this.hashCode = j;
    return j;
  }

  public String hex()
  {
    return toByteString().hex();
  }

  public ByteString md5()
  {
    return toByteString().md5();
  }

  public boolean rangeEquals(int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3)
  {
    if (paramInt1 > size() - paramInt3)
      return false;
    int i = segment(paramInt1);
    label20: if (paramInt3 > 0)
    {
      if (i == 0);
      for (int j = 0; ; j = this.directory[(i - 1)])
      {
        int k = Math.min(paramInt3, j + (this.directory[i] - j) - paramInt1);
        int m = this.directory[(i + this.segments.length)] + (paramInt1 - j);
        if (!paramByteString.rangeEquals(paramInt2, this.segments[i], m, k))
          break;
        paramInt1 += k;
        paramInt2 += k;
        paramInt3 -= k;
        i++;
        break label20;
      }
    }
    return true;
  }

  public boolean rangeEquals(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if ((paramInt1 > size() - paramInt3) || (paramInt2 > paramArrayOfByte.length - paramInt3))
      return false;
    int i = segment(paramInt1);
    label29: if (paramInt3 > 0)
    {
      if (i == 0);
      for (int j = 0; ; j = this.directory[(i - 1)])
      {
        int k = Math.min(paramInt3, j + (this.directory[i] - j) - paramInt1);
        int m = this.directory[(i + this.segments.length)] + (paramInt1 - j);
        if (!Util.arrayRangeEquals(this.segments[i], m, paramArrayOfByte, paramInt2, k))
          break;
        paramInt1 += k;
        paramInt2 += k;
        paramInt3 -= k;
        i++;
        break label29;
      }
    }
    return true;
  }

  public ByteString sha256()
  {
    return toByteString().sha256();
  }

  public int size()
  {
    return this.directory[(-1 + this.segments.length)];
  }

  public ByteString substring(int paramInt)
  {
    return toByteString().substring(paramInt);
  }

  public ByteString substring(int paramInt1, int paramInt2)
  {
    return toByteString().substring(paramInt1, paramInt2);
  }

  public ByteString toAsciiLowercase()
  {
    return toByteString().toAsciiLowercase();
  }

  public ByteString toAsciiUppercase()
  {
    return toByteString().toAsciiUppercase();
  }

  public byte[] toByteArray()
  {
    byte[] arrayOfByte = new byte[this.directory[(-1 + this.segments.length)]];
    int i = 0;
    int j = 0;
    int k = this.segments.length;
    while (j < k)
    {
      int m = this.directory[(k + j)];
      int n = this.directory[j];
      System.arraycopy(this.segments[j], m, arrayOfByte, i, n - i);
      i = n;
      j++;
    }
    return arrayOfByte;
  }

  public String toString()
  {
    return toByteString().toString();
  }

  public String utf8()
  {
    return toByteString().utf8();
  }

  public void write(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException("out == null");
    int i = 0;
    int j = 0;
    int k = this.segments.length;
    while (j < k)
    {
      int m = this.directory[(k + j)];
      int n = this.directory[j];
      paramOutputStream.write(this.segments[j], m, n - i);
      i = n;
      j++;
    }
  }

  void write(Buffer paramBuffer)
  {
    int i = 0;
    int j = 0;
    int k = this.segments.length;
    if (j < k)
    {
      int m = this.directory[(k + j)];
      int n = this.directory[j];
      Segment localSegment = new Segment(this.segments[j], m, m + n - i);
      if (paramBuffer.head == null)
      {
        localSegment.prev = localSegment;
        localSegment.next = localSegment;
        paramBuffer.head = localSegment;
      }
      while (true)
      {
        i = n;
        j++;
        break;
        paramBuffer.head.prev.push(localSegment);
      }
    }
    paramBuffer.size += i;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.SegmentedByteString
 * JD-Core Version:    0.6.2
 */