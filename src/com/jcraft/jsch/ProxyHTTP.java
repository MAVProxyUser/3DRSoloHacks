package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxyHTTP
  implements Proxy
{
  private static int DEFAULTPORT = 80;
  private InputStream in;
  private OutputStream out;
  private String passwd;
  private String proxy_host;
  private int proxy_port;
  private Socket socket;
  private String user;

  public ProxyHTTP(String paramString)
  {
    int i = DEFAULTPORT;
    String str = paramString;
    if (paramString.indexOf(':') != -1);
    try
    {
      str = paramString.substring(0, paramString.indexOf(':'));
      int j = Integer.parseInt(paramString.substring(1 + paramString.indexOf(':')));
      i = j;
      label52: this.proxy_host = str;
      this.proxy_port = i;
      return;
    }
    catch (Exception localException)
    {
      break label52;
    }
  }

  public ProxyHTTP(String paramString, int paramInt)
  {
    this.proxy_host = paramString;
    this.proxy_port = paramInt;
  }

  public static int getDefaultPort()
  {
    return DEFAULTPORT;
  }

  public void close()
  {
    try
    {
      if (this.in != null)
        this.in.close();
      if (this.out != null)
        this.out.close();
      if (this.socket != null)
        this.socket.close();
      label42: this.in = null;
      this.out = null;
      this.socket = null;
      return;
    }
    catch (Exception localException)
    {
      break label42;
    }
  }

  public void connect(SocketFactory paramSocketFactory, String paramString, int paramInt1, int paramInt2)
    throws JSchException
  {
    if (paramSocketFactory == null);
    while (true)
    {
      int i;
      StringBuffer localStringBuffer;
      try
      {
        this.socket = Util.createSocket(this.proxy_host, this.proxy_port, paramInt2);
        this.in = this.socket.getInputStream();
        this.out = this.socket.getOutputStream();
        if (paramInt2 > 0)
          this.socket.setSoTimeout(paramInt2);
        this.socket.setTcpNoDelay(true);
        this.out.write(Util.str2byte("CONNECT " + paramString + ":" + paramInt1 + " HTTP/1.0\r\n"));
        if ((this.user != null) && (this.passwd != null))
        {
          byte[] arrayOfByte1 = Util.str2byte(this.user + ":" + this.passwd);
          byte[] arrayOfByte2 = Util.toBase64(arrayOfByte1, 0, arrayOfByte1.length);
          this.out.write(Util.str2byte("Proxy-Authorization: Basic "));
          this.out.write(arrayOfByte2);
          this.out.write(Util.str2byte("\r\n"));
        }
        this.out.write(Util.str2byte("\r\n"));
        this.out.flush();
        i = 0;
        localStringBuffer = new StringBuffer();
        if (i >= 0)
        {
          i = this.in.read();
          if (i != 13)
          {
            localStringBuffer.append((char)i);
            continue;
          }
        }
      }
      catch (RuntimeException localRuntimeException)
      {
        throw localRuntimeException;
        this.socket = paramSocketFactory.createSocket(this.proxy_host, this.proxy_port);
        this.in = paramSocketFactory.getInputStream(this.socket);
        this.out = paramSocketFactory.getOutputStream(this.socket);
        continue;
      }
      catch (Exception localException1)
      {
      }
      try
      {
        if (this.socket != null)
          this.socket.close();
        str1 = "ProxyHTTP: " + localException1.toString();
        if ((localException1 instanceof Throwable))
        {
          throw new JSchException(str1, localException1);
          i = this.in.read();
          if (i != 10)
            continue;
          if (i < 0)
            throw new IOException();
          str2 = localStringBuffer.toString();
          localObject = "Unknow reason";
          j = -1;
        }
      }
      catch (Exception localException2)
      {
        try
        {
          String str1;
          String str2;
          i = str2.indexOf(' ');
          int m = str2.indexOf(' ', i + 1);
          int j = Integer.parseInt(str2.substring(i + 1, m));
          String str3 = str2.substring(m + 1);
          Object localObject = str3;
          if (j != 200)
          {
            throw new IOException("proxy error: " + (String)localObject);
            do
            {
              while (true)
              {
                if (i < 0)
                  break label554;
                i = this.in.read();
                if (i == 13)
                  break;
                k++;
              }
              i = this.in.read();
            }
            while (i != 10);
            label554: if (i < 0)
              throw new IOException();
            if (k == 0)
            {
              return;
              throw new JSchException(str1);
              localException2 = localException2;
            }
          }
        }
        catch (Exception localException3)
        {
          while (true)
          {
            continue;
            int k = 0;
          }
        }
      }
    }
  }

  public InputStream getInputStream()
  {
    return this.in;
  }

  public OutputStream getOutputStream()
  {
    return this.out;
  }

  public Socket getSocket()
  {
    return this.socket;
  }

  public void setUserPasswd(String paramString1, String paramString2)
  {
    this.user = paramString1;
    this.passwd = paramString2;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ProxyHTTP
 * JD-Core Version:    0.6.2
 */