package com.squareup.okhttp.internal;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.CacheRequest;
import com.squareup.okhttp.internal.http.CacheStrategy;
import java.io.IOException;

public abstract interface InternalCache
{
  public abstract Response get(Request paramRequest)
    throws IOException;

  public abstract CacheRequest put(Response paramResponse)
    throws IOException;

  public abstract void remove(Request paramRequest)
    throws IOException;

  public abstract void trackConditionalCacheHit();

  public abstract void trackResponse(CacheStrategy paramCacheStrategy);

  public abstract void update(Response paramResponse1, Response paramResponse2)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.InternalCache
 * JD-Core Version:    0.6.2
 */