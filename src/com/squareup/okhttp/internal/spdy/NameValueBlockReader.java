package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Source;

class NameValueBlockReader
{
  private int compressedLimit;
  private final InflaterSource inflaterSource = new InflaterSource(new ForwardingSource(paramBufferedSource)
  {
    public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
      throws IOException
    {
      if (NameValueBlockReader.this.compressedLimit == 0)
        return -1L;
      long l = super.read(paramAnonymousBuffer, Math.min(paramAnonymousLong, NameValueBlockReader.this.compressedLimit));
      if (l == -1L)
        return -1L;
      NameValueBlockReader.access$002(NameValueBlockReader.this, (int)(NameValueBlockReader.this.compressedLimit - l));
      return l;
    }
  }
  , new Inflater()
  {
    public int inflate(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      throws DataFormatException
    {
      int i = super.inflate(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      if ((i == 0) && (needsDictionary()))
      {
        setDictionary(Spdy3.DICTIONARY);
        i = super.inflate(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      return i;
    }
  });
  private final BufferedSource source = Okio.buffer(this.inflaterSource);

  public NameValueBlockReader(BufferedSource paramBufferedSource)
  {
  }

  private void doneReading()
    throws IOException
  {
    if (this.compressedLimit > 0)
    {
      this.inflaterSource.refill();
      if (this.compressedLimit != 0)
        throw new IOException("compressedLimit > 0: " + this.compressedLimit);
    }
  }

  private ByteString readByteString()
    throws IOException
  {
    int i = this.source.readInt();
    return this.source.readByteString(i);
  }

  public void close()
    throws IOException
  {
    this.source.close();
  }

  public List<Header> readNameValueBlock(int paramInt)
    throws IOException
  {
    this.compressedLimit = (paramInt + this.compressedLimit);
    int i = this.source.readInt();
    if (i < 0)
      throw new IOException("numberOfPairs < 0: " + i);
    if (i > 1024)
      throw new IOException("numberOfPairs > 1024: " + i);
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
    {
      ByteString localByteString1 = readByteString().toAsciiLowercase();
      ByteString localByteString2 = readByteString();
      if (localByteString1.size() == 0)
        throw new IOException("name.size == 0");
      localArrayList.add(new Header(localByteString1, localByteString2));
    }
    doneReading();
    return localArrayList;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.NameValueBlockReader
 * JD-Core Version:    0.6.2
 */