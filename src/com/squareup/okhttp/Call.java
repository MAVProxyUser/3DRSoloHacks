package com.squareup.okhttp;

import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Call
{
  volatile boolean canceled;
  private final OkHttpClient client;
  HttpEngine engine;
  private boolean executed;
  Request originalRequest;

  protected Call(OkHttpClient paramOkHttpClient, Request paramRequest)
  {
    this.client = paramOkHttpClient.copyWithDefaults();
    this.originalRequest = paramRequest;
  }

  private Response getResponseWithInterceptorChain(boolean paramBoolean)
    throws IOException
  {
    return new ApplicationInterceptorChain(0, this.originalRequest, paramBoolean).proceed(this.originalRequest);
  }

  private String toLoggableString()
  {
    String str1;
    if (this.canceled)
      str1 = "canceled call";
    try
    {
      while (true)
      {
        String str2 = new URL(this.originalRequest.url(), "/...").toString();
        String str3 = str1 + " to " + str2;
        return str3;
        str1 = "call";
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return str1;
  }

  public void cancel()
  {
    this.canceled = true;
    if (this.engine != null)
      this.engine.disconnect();
  }

  public void enqueue(Callback paramCallback)
  {
    enqueue(paramCallback, false);
  }

  void enqueue(Callback paramCallback, boolean paramBoolean)
  {
    try
    {
      if (this.executed)
        throw new IllegalStateException("Already Executed");
    }
    finally
    {
    }
    this.executed = true;
    this.client.getDispatcher().enqueue(new AsyncCall(paramCallback, paramBoolean, null));
  }

  public Response execute()
    throws IOException
  {
    try
    {
      if (this.executed)
        throw new IllegalStateException("Already Executed");
    }
    finally
    {
    }
    this.executed = true;
    Response localResponse;
    try
    {
      this.client.getDispatcher().executed(this);
      localResponse = getResponseWithInterceptorChain(false);
      if (localResponse == null)
        throw new IOException("Canceled");
    }
    finally
    {
      this.client.getDispatcher().finished(this);
    }
    this.client.getDispatcher().finished(this);
    return localResponse;
  }

  Response getResponse(Request paramRequest, boolean paramBoolean)
    throws IOException
  {
    RequestBody localRequestBody = paramRequest.body();
    Request.Builder localBuilder;
    int i;
    if (localRequestBody != null)
    {
      localBuilder = paramRequest.newBuilder();
      MediaType localMediaType = localRequestBody.contentType();
      if (localMediaType != null)
        localBuilder.header("Content-Type", localMediaType.toString());
      long l = localRequestBody.contentLength();
      if (l != -1L)
      {
        localBuilder.header("Content-Length", Long.toString(l));
        localBuilder.removeHeader("Transfer-Encoding");
        paramRequest = localBuilder.build();
      }
    }
    else
    {
      this.engine = new HttpEngine(this.client, paramRequest, false, false, paramBoolean, null, null, null, null);
      i = 0;
    }
    while (true)
    {
      Response localResponse;
      if (this.canceled)
      {
        this.engine.releaseConnection();
        localResponse = null;
        label124: return localResponse;
        localBuilder.header("Transfer-Encoding", "chunked");
        localBuilder.removeHeader("Content-Length");
        break;
      }
      Request localRequest;
      try
      {
        this.engine.sendRequest();
        this.engine.readResponse();
        localResponse = this.engine.getResponse();
        localRequest = this.engine.followUpRequest();
        if (localRequest == null)
        {
          if (paramBoolean)
            break label124;
          this.engine.releaseConnection();
          return localResponse;
        }
      }
      catch (IOException localIOException)
      {
        HttpEngine localHttpEngine = this.engine.recover(localIOException, null);
        if (localHttpEngine != null)
        {
          this.engine = localHttpEngine;
          continue;
        }
        throw localIOException;
      }
      i++;
      if (i > 20)
        throw new ProtocolException("Too many follow-up requests: " + i);
      if (!this.engine.sameConnection(localRequest.url()))
        this.engine.releaseConnection();
      Connection localConnection = this.engine.close();
      this.engine = new HttpEngine(this.client, localRequest, false, false, paramBoolean, localConnection, null, null, localResponse);
    }
  }

  public boolean isCanceled()
  {
    return this.canceled;
  }

  Object tag()
  {
    return this.originalRequest.tag();
  }

  class ApplicationInterceptorChain
    implements Interceptor.Chain
  {
    private final boolean forWebSocket;
    private final int index;
    private final Request request;

    ApplicationInterceptorChain(int paramRequest, Request paramBoolean, boolean arg4)
    {
      this.index = paramRequest;
      this.request = paramBoolean;
      boolean bool;
      this.forWebSocket = bool;
    }

    public Connection connection()
    {
      return null;
    }

    public Response proceed(Request paramRequest)
      throws IOException
    {
      if (this.index < Call.this.client.interceptors().size())
      {
        ApplicationInterceptorChain localApplicationInterceptorChain = new ApplicationInterceptorChain(Call.this, 1 + this.index, paramRequest, this.forWebSocket);
        return ((Interceptor)Call.this.client.interceptors().get(this.index)).intercept(localApplicationInterceptorChain);
      }
      return Call.this.getResponse(paramRequest, this.forWebSocket);
    }

    public Request request()
    {
      return this.request;
    }
  }

  final class AsyncCall extends NamedRunnable
  {
    private final boolean forWebSocket;
    private final Callback responseCallback;

    private AsyncCall(Callback paramBoolean, boolean arg3)
    {
      super(arrayOfObject);
      this.responseCallback = paramBoolean;
      boolean bool;
      this.forWebSocket = bool;
    }

    void cancel()
    {
      Call.this.cancel();
    }

    protected void execute()
    {
      int i = 0;
      try
      {
        Response localResponse = Call.this.getResponseWithInterceptorChain(this.forWebSocket);
        if (Call.this.canceled)
        {
          i = 1;
          this.responseCallback.onFailure(Call.this.originalRequest, new IOException("Canceled"));
        }
        while (true)
        {
          return;
          i = 1;
          this.responseCallback.onResponse(localResponse);
        }
      }
      catch (IOException localIOException)
      {
        if (i != 0)
          Internal.logger.log(Level.INFO, "Callback failure for " + Call.this.toLoggableString(), localIOException);
        while (true)
        {
          return;
          this.responseCallback.onFailure(Call.this.engine.getRequest(), localIOException);
        }
      }
      finally
      {
        Call.this.client.getDispatcher().finished(this);
      }
    }

    Call get()
    {
      return Call.this;
    }

    String host()
    {
      return Call.this.originalRequest.url().getHost();
    }

    Request request()
    {
      return Call.this.originalRequest;
    }

    Object tag()
    {
      return Call.this.originalRequest.tag();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Call
 * JD-Core Version:    0.6.2
 */