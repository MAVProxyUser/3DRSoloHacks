package com.crashlytics.android;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

final class ByteString
{
  public static final ByteString EMPTY = new ByteString(new byte[0]);
  private final byte[] bytes;
  private volatile int hash = 0;

  private ByteString(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }

  public static ByteString copyFrom(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    return new ByteString(paramString1.getBytes(paramString2));
  }

  public static ByteString copyFrom(ByteBuffer paramByteBuffer)
  {
    return copyFrom(paramByteBuffer, paramByteBuffer.remaining());
  }

  public static ByteString copyFrom(ByteBuffer paramByteBuffer, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    paramByteBuffer.get(arrayOfByte);
    return new ByteString(arrayOfByte);
  }

  public static ByteString copyFrom(List<ByteString> paramList)
  {
    if (paramList.size() == 0)
      return EMPTY;
    if (paramList.size() == 1)
      return (ByteString)paramList.get(0);
    int i = 0;
    Iterator localIterator1 = paramList.iterator();
    while (localIterator1.hasNext())
      i += ((ByteString)localIterator1.next()).size();
    byte[] arrayOfByte = new byte[i];
    int j = 0;
    Iterator localIterator2 = paramList.iterator();
    while (localIterator2.hasNext())
    {
      ByteString localByteString = (ByteString)localIterator2.next();
      System.arraycopy(localByteString.bytes, 0, arrayOfByte, j, localByteString.size());
      j += localByteString.size();
    }
    return new ByteString(arrayOfByte);
  }

  public static ByteString copyFrom(byte[] paramArrayOfByte)
  {
    return copyFrom(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static ByteString copyFrom(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return new ByteString(arrayOfByte);
  }

  public static ByteString copyFromUtf8(String paramString)
  {
    try
    {
      ByteString localByteString = new ByteString(paramString.getBytes("UTF-8"));
      return localByteString;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported.", localUnsupportedEncodingException);
    }
  }

  static CodedBuilder newCodedBuilder(int paramInt)
  {
    return new CodedBuilder(paramInt, null);
  }

  public static Output newOutput()
  {
    return newOutput(32);
  }

  public static Output newOutput(int paramInt)
  {
    return new Output(new ByteArrayOutputStream(paramInt), null);
  }

  public ByteBuffer asReadOnlyByteBuffer()
  {
    return ByteBuffer.wrap(this.bytes).asReadOnlyBuffer();
  }

  public byte byteAt(int paramInt)
  {
    return this.bytes[paramInt];
  }

  public void copyTo(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put(this.bytes, 0, this.bytes.length);
  }

  public void copyTo(byte[] paramArrayOfByte, int paramInt)
  {
    System.arraycopy(this.bytes, 0, paramArrayOfByte, paramInt, this.bytes.length);
  }

  public void copyTo(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.bytes, paramInt1, paramArrayOfByte, paramInt2, paramInt3);
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this);
    while (true)
    {
      return true;
      if (!(paramObject instanceof ByteString))
        return false;
      ByteString localByteString = (ByteString)paramObject;
      int i = this.bytes.length;
      if (i != localByteString.bytes.length)
        return false;
      byte[] arrayOfByte1 = this.bytes;
      byte[] arrayOfByte2 = localByteString.bytes;
      for (int j = 0; j < i; j++)
        if (arrayOfByte1[j] != arrayOfByte2[j])
          return false;
    }
  }

  public int hashCode()
  {
    int i = this.hash;
    if (i == 0)
    {
      byte[] arrayOfByte = this.bytes;
      int j = this.bytes.length;
      i = j;
      for (int k = 0; k < j; k++)
        i = i * 31 + arrayOfByte[k];
      if (i == 0)
        i = 1;
      this.hash = i;
    }
    return i;
  }

  public boolean isEmpty()
  {
    return this.bytes.length == 0;
  }

  public InputStream newInput()
  {
    return new ByteArrayInputStream(this.bytes);
  }

  public int size()
  {
    return this.bytes.length;
  }

  public byte[] toByteArray()
  {
    int i = this.bytes.length;
    byte[] arrayOfByte = new byte[i];
    System.arraycopy(this.bytes, 0, arrayOfByte, 0, i);
    return arrayOfByte;
  }

  public String toString(String paramString)
    throws UnsupportedEncodingException
  {
    return new String(this.bytes, paramString);
  }

  public String toStringUtf8()
  {
    try
    {
      String str = new String(this.bytes, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported?", localUnsupportedEncodingException);
    }
  }

  static final class CodedBuilder
  {
    private final byte[] buffer;
    private final CodedOutputStream output;

    private CodedBuilder(int paramInt)
    {
      this.buffer = new byte[paramInt];
      this.output = CodedOutputStream.newInstance(this.buffer);
    }

    public ByteString build()
    {
      this.output.checkNoSpaceLeft();
      return new ByteString(this.buffer, null);
    }

    public CodedOutputStream getCodedOutput()
    {
      return this.output;
    }
  }

  static final class Output extends FilterOutputStream
  {
    private final ByteArrayOutputStream bout;

    private Output(ByteArrayOutputStream paramByteArrayOutputStream)
    {
      super();
      this.bout = paramByteArrayOutputStream;
    }

    public ByteString toByteString()
    {
      return new ByteString(this.bout.toByteArray(), null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.ByteString
 * JD-Core Version:    0.6.2
 */