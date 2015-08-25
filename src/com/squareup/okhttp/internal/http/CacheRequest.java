package com.squareup.okhttp.internal.http;

import java.io.IOException;
import okio.Sink;

public abstract interface CacheRequest
{
  public abstract void abort();

  public abstract Sink body()
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.CacheRequest
 * JD-Core Version:    0.6.2
 */