package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

class PortWatcher
  implements Runnable
{
  private static InetAddress anyLocalAddress;
  private static Vector pool = new Vector();
  InetAddress boundaddress;
  int connectTimeout = 0;
  String host;
  int lport;
  int rport;
  Session session;
  ServerSocket ss;
  Runnable thread;

  static
  {
    anyLocalAddress = null;
    try
    {
      anyLocalAddress = InetAddress.getByName("0.0.0.0");
      return;
    }
    catch (UnknownHostException localUnknownHostException)
    {
    }
  }

  PortWatcher(Session paramSession, String paramString1, int paramInt1, String paramString2, int paramInt2, ServerSocketFactory paramServerSocketFactory)
    throws JSchException
  {
    this.session = paramSession;
    this.lport = paramInt1;
    this.host = paramString2;
    this.rport = paramInt2;
    try
    {
      this.boundaddress = InetAddress.getByName(paramString1);
      if (paramServerSocketFactory == null);
      ServerSocket localServerSocket;
      for (Object localObject = new ServerSocket(paramInt1, 0, this.boundaddress); ; localObject = localServerSocket)
      {
        this.ss = ((ServerSocket)localObject);
        if (paramInt1 == 0)
        {
          int i = this.ss.getLocalPort();
          if (i != -1)
            this.lport = i;
        }
        return;
        localServerSocket = paramServerSocketFactory.createServerSocket(paramInt1, 0, this.boundaddress);
      }
    }
    catch (Exception localException)
    {
      String str = "PortForwardingL: local port " + paramString1 + ":" + paramInt1 + " cannot be bound.";
      if ((localException instanceof Throwable))
        throw new JSchException(str, localException);
      throw new JSchException(str);
    }
  }

  static PortWatcher addPort(Session paramSession, String paramString1, int paramInt1, String paramString2, int paramInt2, ServerSocketFactory paramServerSocketFactory)
    throws JSchException
  {
    String str = normalize(paramString1);
    if (getPort(paramSession, str, paramInt1) != null)
      throw new JSchException("PortForwardingL: local port " + str + ":" + paramInt1 + " is already registered.");
    PortWatcher localPortWatcher = new PortWatcher(paramSession, str, paramInt1, paramString2, paramInt2, paramServerSocketFactory);
    pool.addElement(localPortWatcher);
    return localPortWatcher;
  }

  static void delPort(Session paramSession)
  {
    while (true)
    {
      int i;
      synchronized (pool)
      {
        PortWatcher[] arrayOfPortWatcher = new PortWatcher[pool.size()];
        i = 0;
        j = 0;
        if (i >= pool.size())
          break label128;
        PortWatcher localPortWatcher2 = (PortWatcher)pool.elementAt(i);
        if (localPortWatcher2.session == paramSession)
        {
          localPortWatcher2.delete();
          m = j + 1;
          arrayOfPortWatcher[j] = localPortWatcher2;
          break label118;
          if (k < j)
          {
            PortWatcher localPortWatcher1 = arrayOfPortWatcher[k];
            pool.removeElement(localPortWatcher1);
            k++;
            continue;
          }
          return;
        }
      }
      int m = j;
      label118: i++;
      int j = m;
      continue;
      label128: int k = 0;
    }
  }

  static void delPort(Session paramSession, String paramString, int paramInt)
    throws JSchException
  {
    String str = normalize(paramString);
    PortWatcher localPortWatcher = getPort(paramSession, str, paramInt);
    if (localPortWatcher == null)
      throw new JSchException("PortForwardingL: local port " + str + ":" + paramInt + " is not registered.");
    localPortWatcher.delete();
    pool.removeElement(localPortWatcher);
  }

  // ERROR //
  static PortWatcher getPort(Session paramSession, String paramString, int paramInt)
    throws JSchException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 44	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   4: astore 4
    //   6: getstatic 34	com/jcraft/jsch/PortWatcher:pool	Ljava/util/Vector;
    //   9: astore 5
    //   11: aload 5
    //   13: monitorenter
    //   14: iconst_0
    //   15: istore 6
    //   17: iload 6
    //   19: getstatic 34	com/jcraft/jsch/PortWatcher:pool	Ljava/util/Vector;
    //   22: invokevirtual 130	java/util/Vector:size	()I
    //   25: if_icmpge +116 -> 141
    //   28: getstatic 34	com/jcraft/jsch/PortWatcher:pool	Ljava/util/Vector;
    //   31: iload 6
    //   33: invokevirtual 134	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   36: checkcast 2	com/jcraft/jsch/PortWatcher
    //   39: checkcast 2	com/jcraft/jsch/PortWatcher
    //   42: astore 8
    //   44: aload 8
    //   46: getfield 54	com/jcraft/jsch/PortWatcher:session	Lcom/jcraft/jsch/Session;
    //   49: aload_0
    //   50: if_acmpne +85 -> 135
    //   53: aload 8
    //   55: getfield 56	com/jcraft/jsch/PortWatcher:lport	I
    //   58: iload_2
    //   59: if_icmpne +76 -> 135
    //   62: getstatic 36	com/jcraft/jsch/PortWatcher:anyLocalAddress	Ljava/net/InetAddress;
    //   65: ifnull +17 -> 82
    //   68: aload 8
    //   70: getfield 62	com/jcraft/jsch/PortWatcher:boundaddress	Ljava/net/InetAddress;
    //   73: getstatic 36	com/jcraft/jsch/PortWatcher:anyLocalAddress	Ljava/net/InetAddress;
    //   76: invokevirtual 147	java/net/InetAddress:equals	(Ljava/lang/Object;)Z
    //   79: ifne +16 -> 95
    //   82: aload 8
    //   84: getfield 62	com/jcraft/jsch/PortWatcher:boundaddress	Ljava/net/InetAddress;
    //   87: aload 4
    //   89: invokevirtual 147	java/net/InetAddress:equals	(Ljava/lang/Object;)Z
    //   92: ifeq +43 -> 135
    //   95: aload 5
    //   97: monitorexit
    //   98: aload 8
    //   100: areturn
    //   101: astore_3
    //   102: new 47	com/jcraft/jsch/JSchException
    //   105: dup
    //   106: new 81	java/lang/StringBuilder
    //   109: dup
    //   110: invokespecial 82	java/lang/StringBuilder:<init>	()V
    //   113: ldc 149
    //   115: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: aload_1
    //   119: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: ldc 151
    //   124: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: aload_3
    //   131: invokespecial 104	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   134: athrow
    //   135: iinc 6 1
    //   138: goto -121 -> 17
    //   141: aload 5
    //   143: monitorexit
    //   144: aconst_null
    //   145: areturn
    //   146: astore 7
    //   148: aload 5
    //   150: monitorexit
    //   151: aload 7
    //   153: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	101	java/net/UnknownHostException
    //   17	82	146	finally
    //   82	95	146	finally
    //   95	98	146	finally
    //   141	144	146	finally
    //   148	151	146	finally
  }

  static String[] getPortForwarding(Session paramSession)
  {
    Vector localVector1 = new Vector();
    Vector localVector2 = pool;
    for (int i = 0; ; i++)
    {
      String[] arrayOfString;
      try
      {
        if (i < pool.size())
        {
          PortWatcher localPortWatcher = (PortWatcher)pool.elementAt(i);
          if (localPortWatcher.session != paramSession)
            continue;
          localVector1.addElement(localPortWatcher.lport + ":" + localPortWatcher.host + ":" + localPortWatcher.rport);
          continue;
        }
        arrayOfString = new String[localVector1.size()];
        for (int j = 0; j < localVector1.size(); j++)
          arrayOfString[j] = ((String)(String)localVector1.elementAt(j));
      }
      finally
      {
      }
      return arrayOfString;
    }
  }

  private static String normalize(String paramString)
  {
    if (paramString != null)
    {
      if ((paramString.length() != 0) && (!paramString.equals("*")))
        break label25;
      paramString = "0.0.0.0";
    }
    label25: 
    while (!paramString.equals("localhost"))
      return paramString;
    return "127.0.0.1";
  }

  void delete()
  {
    this.thread = null;
    try
    {
      if (this.ss != null)
        this.ss.close();
      this.ss = null;
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void run()
  {
    this.thread = this;
    try
    {
      while (this.thread != null)
      {
        Socket localSocket = this.ss.accept();
        localSocket.setTcpNoDelay(true);
        InputStream localInputStream = localSocket.getInputStream();
        OutputStream localOutputStream = localSocket.getOutputStream();
        ChannelDirectTCPIP localChannelDirectTCPIP = new ChannelDirectTCPIP();
        localChannelDirectTCPIP.init();
        localChannelDirectTCPIP.setInputStream(localInputStream);
        localChannelDirectTCPIP.setOutputStream(localOutputStream);
        this.session.addChannel(localChannelDirectTCPIP);
        localChannelDirectTCPIP.setHost(this.host);
        localChannelDirectTCPIP.setPort(this.rport);
        localChannelDirectTCPIP.setOrgIPAddress(localSocket.getInetAddress().getHostAddress());
        localChannelDirectTCPIP.setOrgPort(localSocket.getPort());
        localChannelDirectTCPIP.connect(this.connectTimeout);
        int i = localChannelDirectTCPIP.exitstatus;
        if (i == -1);
      }
    }
    catch (Exception localException)
    {
      delete();
    }
  }

  void setConnectTimeout(int paramInt)
  {
    this.connectTimeout = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.PortWatcher
 * JD-Core Version:    0.6.2
 */