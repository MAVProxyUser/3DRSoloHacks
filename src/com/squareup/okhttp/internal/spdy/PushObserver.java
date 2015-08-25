package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.List;
import okio.BufferedSource;

public abstract interface PushObserver
{
  public static final PushObserver CANCEL = new PushObserver()
  {
    public boolean onData(int paramAnonymousInt1, BufferedSource paramAnonymousBufferedSource, int paramAnonymousInt2, boolean paramAnonymousBoolean)
      throws IOException
    {
      paramAnonymousBufferedSource.skip(paramAnonymousInt2);
      return true;
    }

    public boolean onHeaders(int paramAnonymousInt, List<Header> paramAnonymousList, boolean paramAnonymousBoolean)
    {
      return true;
    }

    public boolean onRequest(int paramAnonymousInt, List<Header> paramAnonymousList)
    {
      return true;
    }

    public void onReset(int paramAnonymousInt, ErrorCode paramAnonymousErrorCode)
    {
    }
  };

  public abstract boolean onData(int paramInt1, BufferedSource paramBufferedSource, int paramInt2, boolean paramBoolean)
    throws IOException;

  public abstract boolean onHeaders(int paramInt, List<Header> paramList, boolean paramBoolean);

  public abstract boolean onRequest(int paramInt, List<Header> paramList);

  public abstract void onReset(int paramInt, ErrorCode paramErrorCode);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.PushObserver
 * JD-Core Version:    0.6.2
 */