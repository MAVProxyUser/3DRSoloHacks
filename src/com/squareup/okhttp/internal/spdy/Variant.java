package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import okio.BufferedSink;
import okio.BufferedSource;

public abstract interface Variant
{
  public abstract Protocol getProtocol();

  public abstract FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean);

  public abstract FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Variant
 * JD-Core Version:    0.6.2
 */