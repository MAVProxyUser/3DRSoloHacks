package com.jcraft.jsch;

import java.net.Socket;
import java.util.Vector;

public class ChannelForwardedTCPIP extends Channel
{
  private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
  private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
  private static final int TIMEOUT = 10000;
  private static Vector pool = new Vector();
  private Config config = null;
  private ForwardedTCPIPDaemon daemon = null;
  private Socket socket = null;

  ChannelForwardedTCPIP()
  {
    setLocalWindowSizeMax(131072);
    setLocalWindowSize(131072);
    setLocalPacketSize(16384);
    this.io = new IO();
    this.connected = true;
  }

  static void addPort(Session paramSession, String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, SocketFactory paramSocketFactory)
    throws JSchException
  {
    String str = normalize(paramString1);
    synchronized (pool)
    {
      if (getPort(paramSession, str, paramInt1) != null)
        throw new JSchException("PortForwardingR: remote port " + paramInt1 + " is already registered.");
    }
    ConfigLHost localConfigLHost = new ConfigLHost();
    localConfigLHost.session = paramSession;
    localConfigLHost.rport = paramInt1;
    localConfigLHost.allocated_rport = paramInt2;
    localConfigLHost.target = paramString2;
    localConfigLHost.lport = paramInt3;
    localConfigLHost.address_to_bind = str;
    localConfigLHost.factory = paramSocketFactory;
    pool.addElement(localConfigLHost);
  }

  static void addPort(Session paramSession, String paramString1, int paramInt1, int paramInt2, String paramString2, Object[] paramArrayOfObject)
    throws JSchException
  {
    String str = normalize(paramString1);
    synchronized (pool)
    {
      if (getPort(paramSession, str, paramInt1) != null)
        throw new JSchException("PortForwardingR: remote port " + paramInt1 + " is already registered.");
    }
    ConfigDaemon localConfigDaemon = new ConfigDaemon();
    localConfigDaemon.session = paramSession;
    localConfigDaemon.rport = paramInt1;
    localConfigDaemon.allocated_rport = paramInt1;
    localConfigDaemon.target = paramString2;
    localConfigDaemon.arg = paramArrayOfObject;
    localConfigDaemon.address_to_bind = str;
    pool.addElement(localConfigDaemon);
  }

  static void delPort(ChannelForwardedTCPIP paramChannelForwardedTCPIP)
  {
    try
    {
      Session localSession2 = paramChannelForwardedTCPIP.getSession();
      localSession1 = localSession2;
      if ((localSession1 != null) && (paramChannelForwardedTCPIP.config != null))
        delPort(localSession1, paramChannelForwardedTCPIP.config.rport);
      return;
    }
    catch (JSchException localJSchException)
    {
      while (true)
        Session localSession1 = null;
    }
  }

  static void delPort(Session paramSession)
  {
    int[] arrayOfInt;
    int i;
    int j;
    synchronized (pool)
    {
      arrayOfInt = new int[pool.size()];
      i = 0;
      j = 0;
    }
    try
    {
      while (i < pool.size())
      {
        Config localConfig = (Config)pool.elementAt(i);
        Session localSession = localConfig.session;
        if (localSession != paramSession)
          break label125;
        m = j + 1;
        arrayOfInt[j] = localConfig.rport;
        i++;
        j = m;
      }
      int k = 0;
      while (k < j)
      {
        delPort(paramSession, arrayOfInt[k]);
        k++;
        continue;
        localObject1 = finally;
        throw localObject1;
      }
      return;
    }
    finally
    {
      while (true)
      {
        continue;
        label125: int m = j;
      }
    }
  }

  static void delPort(Session paramSession, int paramInt)
  {
    delPort(paramSession, null, paramInt);
  }

  static void delPort(Session paramSession, String paramString, int paramInt)
  {
    while (true)
    {
      synchronized (pool)
      {
        Config localConfig = getPort(paramSession, normalize(paramString), paramInt);
        if (localConfig == null)
          localConfig = getPort(paramSession, null, paramInt);
        if (localConfig == null)
          return;
        pool.removeElement(localConfig);
        if (paramString == null)
        {
          paramString = localConfig.address_to_bind;
          break label144;
          Buffer localBuffer = new Buffer(100);
          Packet localPacket = new Packet(localBuffer);
          try
          {
            localPacket.reset();
            localBuffer.putByte((byte)80);
            localBuffer.putString(Util.str2byte("cancel-tcpip-forward"));
            localBuffer.putByte((byte)0);
            localBuffer.putString(Util.str2byte(paramString));
            localBuffer.putInt(paramInt);
            paramSession.write(localPacket);
            return;
          }
          catch (Exception localException)
          {
            return;
          }
        }
      }
      label144: if (paramString == null)
        paramString = "0.0.0.0";
    }
  }

  private static Config getPort(Session paramSession, String paramString, int paramInt)
  {
    Vector localVector = pool;
    for (int i = 0; ; i++)
      try
      {
        if (i < pool.size())
        {
          Config localConfig = (Config)pool.elementAt(i);
          if ((localConfig.session == paramSession) && ((localConfig.rport == paramInt) || ((localConfig.rport == 0) && (localConfig.allocated_rport == paramInt))) && ((paramString == null) || (localConfig.address_to_bind.equals(paramString))))
            return localConfig;
        }
        else
        {
          return null;
        }
      }
      finally
      {
      }
  }

  static String[] getPortForwarding(Session paramSession)
  {
    Vector localVector1 = new Vector();
    Vector localVector2 = pool;
    for (int i = 0; ; i++)
    {
      try
      {
        if (i < pool.size())
        {
          Config localConfig = (Config)pool.elementAt(i);
          if ((localConfig instanceof ConfigDaemon))
            localVector1.addElement(localConfig.allocated_rport + ":" + localConfig.target + ":");
          else
            localVector1.addElement(localConfig.allocated_rport + ":" + localConfig.target + ":" + ((ConfigLHost)localConfig).lport);
        }
      }
      finally
      {
      }
      String[] arrayOfString = new String[localVector1.size()];
      for (int j = 0; j < localVector1.size(); j++)
        arrayOfString[j] = ((String)(String)localVector1.elementAt(j));
      return arrayOfString;
    }
  }

  static String normalize(String paramString)
  {
    if (paramString == null)
      paramString = "localhost";
    while ((paramString.length() != 0) && (!paramString.equals("*")))
      return paramString;
    return "";
  }

  private void setSocketFactory(SocketFactory paramSocketFactory)
  {
    if ((this.config != null) && ((this.config instanceof ConfigLHost)))
      ((ConfigLHost)this.config).factory = paramSocketFactory;
  }

  void getData(Buffer paramBuffer)
  {
    setRecipient(paramBuffer.getInt());
    setRemoteWindowSize(paramBuffer.getUInt());
    setRemotePacketSize(paramBuffer.getInt());
    byte[] arrayOfByte = paramBuffer.getString();
    int i = paramBuffer.getInt();
    paramBuffer.getString();
    paramBuffer.getInt();
    try
    {
      Session localSession2 = getSession();
      localSession1 = localSession2;
      this.config = getPort(localSession1, Util.byte2str(arrayOfByte), i);
      if (this.config == null)
        this.config = getPort(localSession1, null, i);
      if ((this.config == null) && (JSch.getLogger().isEnabled(3)))
        JSch.getLogger().log(3, "ChannelForwardedTCPIP: " + Util.byte2str(arrayOfByte) + ":" + i + " is not registered.");
      return;
    }
    catch (JSchException localJSchException)
    {
      while (true)
        Session localSession1 = null;
    }
  }

  public int getRemotePort()
  {
    if (this.config != null)
      return this.config.rport;
    return 0;
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 35	com/jcraft/jsch/ChannelForwardedTCPIP:config	Lcom/jcraft/jsch/ChannelForwardedTCPIP$Config;
    //   4: instanceof 123
    //   7: ifeq +214 -> 221
    //   10: aload_0
    //   11: getfield 35	com/jcraft/jsch/ChannelForwardedTCPIP:config	Lcom/jcraft/jsch/ChannelForwardedTCPIP$Config;
    //   14: checkcast 123	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigDaemon
    //   17: astore 11
    //   19: aload_0
    //   20: aload 11
    //   22: getfield 128	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigDaemon:target	Ljava/lang/String;
    //   25: invokestatic 280	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   28: invokevirtual 284	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   31: checkcast 286	com/jcraft/jsch/ForwardedTCPIPDaemon
    //   34: putfield 33	com/jcraft/jsch/ChannelForwardedTCPIP:daemon	Lcom/jcraft/jsch/ForwardedTCPIPDaemon;
    //   37: new 288	java/io/PipedOutputStream
    //   40: dup
    //   41: invokespecial 289	java/io/PipedOutputStream:<init>	()V
    //   44: astore 12
    //   46: aload_0
    //   47: getfield 52	com/jcraft/jsch/ChannelForwardedTCPIP:io	Lcom/jcraft/jsch/IO;
    //   50: new 291	com/jcraft/jsch/Channel$PassiveInputStream
    //   53: dup
    //   54: aload_0
    //   55: aload 12
    //   57: ldc_w 292
    //   60: invokespecial 295	com/jcraft/jsch/Channel$PassiveInputStream:<init>	(Lcom/jcraft/jsch/Channel;Ljava/io/PipedOutputStream;I)V
    //   63: iconst_0
    //   64: invokevirtual 299	com/jcraft/jsch/IO:setInputStream	(Ljava/io/InputStream;Z)V
    //   67: aload_0
    //   68: getfield 33	com/jcraft/jsch/ChannelForwardedTCPIP:daemon	Lcom/jcraft/jsch/ForwardedTCPIPDaemon;
    //   71: aload_0
    //   72: aload_0
    //   73: invokevirtual 303	com/jcraft/jsch/ChannelForwardedTCPIP:getInputStream	()Ljava/io/InputStream;
    //   76: aload 12
    //   78: invokeinterface 307 4 0
    //   83: aload_0
    //   84: getfield 33	com/jcraft/jsch/ChannelForwardedTCPIP:daemon	Lcom/jcraft/jsch/ForwardedTCPIPDaemon;
    //   87: aload 11
    //   89: getfield 132	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigDaemon:arg	[Ljava/lang/Object;
    //   92: invokeinterface 311 2 0
    //   97: new 313	java/lang/Thread
    //   100: dup
    //   101: aload_0
    //   102: getfield 33	com/jcraft/jsch/ChannelForwardedTCPIP:daemon	Lcom/jcraft/jsch/ForwardedTCPIPDaemon;
    //   105: invokespecial 316	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   108: invokevirtual 319	java/lang/Thread:start	()V
    //   111: aload_0
    //   112: invokevirtual 322	com/jcraft/jsch/ChannelForwardedTCPIP:sendOpenConfirmation	()V
    //   115: aload_0
    //   116: invokestatic 326	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   119: putfield 330	com/jcraft/jsch/ChannelForwardedTCPIP:thread	Ljava/lang/Thread;
    //   122: new 167	com/jcraft/jsch/Buffer
    //   125: dup
    //   126: aload_0
    //   127: getfield 333	com/jcraft/jsch/ChannelForwardedTCPIP:rmpsize	I
    //   130: invokespecial 169	com/jcraft/jsch/Buffer:<init>	(I)V
    //   133: astore 5
    //   135: new 171	com/jcraft/jsch/Packet
    //   138: dup
    //   139: aload 5
    //   141: invokespecial 174	com/jcraft/jsch/Packet:<init>	(Lcom/jcraft/jsch/Buffer;)V
    //   144: astore 6
    //   146: aload_0
    //   147: invokevirtual 139	com/jcraft/jsch/ChannelForwardedTCPIP:getSession	()Lcom/jcraft/jsch/Session;
    //   150: astore 8
    //   152: aload_0
    //   153: getfield 330	com/jcraft/jsch/ChannelForwardedTCPIP:thread	Ljava/lang/Thread;
    //   156: ifnull +60 -> 216
    //   159: aload_0
    //   160: getfield 52	com/jcraft/jsch/ChannelForwardedTCPIP:io	Lcom/jcraft/jsch/IO;
    //   163: ifnull +53 -> 216
    //   166: aload_0
    //   167: getfield 52	com/jcraft/jsch/ChannelForwardedTCPIP:io	Lcom/jcraft/jsch/IO;
    //   170: getfield 337	com/jcraft/jsch/IO:in	Ljava/io/InputStream;
    //   173: ifnull +43 -> 216
    //   176: aload_0
    //   177: getfield 52	com/jcraft/jsch/ChannelForwardedTCPIP:io	Lcom/jcraft/jsch/IO;
    //   180: getfield 337	com/jcraft/jsch/IO:in	Ljava/io/InputStream;
    //   183: aload 5
    //   185: getfield 341	com/jcraft/jsch/Buffer:buffer	[B
    //   188: bipush 14
    //   190: bipush 172
    //   192: bipush 242
    //   194: aload 5
    //   196: getfield 341	com/jcraft/jsch/Buffer:buffer	[B
    //   199: arraylength
    //   200: iadd
    //   201: iadd
    //   202: invokevirtual 347	java/io/InputStream:read	([BII)I
    //   205: istore 9
    //   207: iload 9
    //   209: ifgt +128 -> 337
    //   212: aload_0
    //   213: invokevirtual 350	com/jcraft/jsch/ChannelForwardedTCPIP:eof	()V
    //   216: aload_0
    //   217: invokevirtual 353	com/jcraft/jsch/ChannelForwardedTCPIP:disconnect	()V
    //   220: return
    //   221: aload_0
    //   222: getfield 35	com/jcraft/jsch/ChannelForwardedTCPIP:config	Lcom/jcraft/jsch/ChannelForwardedTCPIP$Config;
    //   225: checkcast 91	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost
    //   228: astore_2
    //   229: aload_2
    //   230: getfield 116	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost:factory	Lcom/jcraft/jsch/SocketFactory;
    //   233: ifnonnull +80 -> 313
    //   236: aload_2
    //   237: getfield 106	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost:target	Ljava/lang/String;
    //   240: aload_2
    //   241: getfield 109	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost:lport	I
    //   244: sipush 10000
    //   247: invokestatic 357	com/jcraft/jsch/Util:createSocket	(Ljava/lang/String;II)Ljava/net/Socket;
    //   250: astore 4
    //   252: aload_0
    //   253: aload 4
    //   255: putfield 31	com/jcraft/jsch/ChannelForwardedTCPIP:socket	Ljava/net/Socket;
    //   258: aload_0
    //   259: getfield 31	com/jcraft/jsch/ChannelForwardedTCPIP:socket	Ljava/net/Socket;
    //   262: iconst_1
    //   263: invokevirtual 363	java/net/Socket:setTcpNoDelay	(Z)V
    //   266: aload_0
    //   267: getfield 52	com/jcraft/jsch/ChannelForwardedTCPIP:io	Lcom/jcraft/jsch/IO;
    //   270: aload_0
    //   271: getfield 31	com/jcraft/jsch/ChannelForwardedTCPIP:socket	Ljava/net/Socket;
    //   274: invokevirtual 364	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   277: invokevirtual 367	com/jcraft/jsch/IO:setInputStream	(Ljava/io/InputStream;)V
    //   280: aload_0
    //   281: getfield 52	com/jcraft/jsch/ChannelForwardedTCPIP:io	Lcom/jcraft/jsch/IO;
    //   284: aload_0
    //   285: getfield 31	com/jcraft/jsch/ChannelForwardedTCPIP:socket	Ljava/net/Socket;
    //   288: invokevirtual 371	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   291: invokevirtual 375	com/jcraft/jsch/IO:setOutputStream	(Ljava/io/OutputStream;)V
    //   294: goto -183 -> 111
    //   297: astore_1
    //   298: aload_0
    //   299: iconst_1
    //   300: invokevirtual 378	com/jcraft/jsch/ChannelForwardedTCPIP:sendOpenFailure	(I)V
    //   303: aload_0
    //   304: iconst_1
    //   305: putfield 381	com/jcraft/jsch/ChannelForwardedTCPIP:close	Z
    //   308: aload_0
    //   309: invokevirtual 353	com/jcraft/jsch/ChannelForwardedTCPIP:disconnect	()V
    //   312: return
    //   313: aload_2
    //   314: getfield 116	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost:factory	Lcom/jcraft/jsch/SocketFactory;
    //   317: aload_2
    //   318: getfield 106	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost:target	Ljava/lang/String;
    //   321: aload_2
    //   322: getfield 109	com/jcraft/jsch/ChannelForwardedTCPIP$ConfigLHost:lport	I
    //   325: invokeinterface 386 3 0
    //   330: astore_3
    //   331: aload_3
    //   332: astore 4
    //   334: goto -82 -> 252
    //   337: aload 6
    //   339: invokevirtual 177	com/jcraft/jsch/Packet:reset	()V
    //   342: aload 5
    //   344: bipush 94
    //   346: invokevirtual 181	com/jcraft/jsch/Buffer:putByte	(B)V
    //   349: aload 5
    //   351: aload_0
    //   352: getfield 389	com/jcraft/jsch/ChannelForwardedTCPIP:recipient	I
    //   355: invokevirtual 196	com/jcraft/jsch/Buffer:putInt	(I)V
    //   358: aload 5
    //   360: iload 9
    //   362: invokevirtual 196	com/jcraft/jsch/Buffer:putInt	(I)V
    //   365: aload 5
    //   367: iload 9
    //   369: invokevirtual 392	com/jcraft/jsch/Buffer:skip	(I)V
    //   372: aload_0
    //   373: monitorenter
    //   374: aload_0
    //   375: getfield 381	com/jcraft/jsch/ChannelForwardedTCPIP:close	Z
    //   378: ifeq +20 -> 398
    //   381: aload_0
    //   382: monitorexit
    //   383: goto -167 -> 216
    //   386: astore 10
    //   388: aload_0
    //   389: monitorexit
    //   390: aload 10
    //   392: athrow
    //   393: astore 7
    //   395: goto -179 -> 216
    //   398: aload 8
    //   400: aload 6
    //   402: aload_0
    //   403: iload 9
    //   405: invokevirtual 395	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;Lcom/jcraft/jsch/Channel;I)V
    //   408: aload_0
    //   409: monitorexit
    //   410: goto -258 -> 152
    //
    // Exception table:
    //   from	to	target	type
    //   0	111	297	java/lang/Exception
    //   111	115	297	java/lang/Exception
    //   221	252	297	java/lang/Exception
    //   252	294	297	java/lang/Exception
    //   313	331	297	java/lang/Exception
    //   374	383	386	finally
    //   388	390	386	finally
    //   398	410	386	finally
    //   146	152	393	java/lang/Exception
    //   152	207	393	java/lang/Exception
    //   212	216	393	java/lang/Exception
    //   337	374	393	java/lang/Exception
    //   390	393	393	java/lang/Exception
  }

  static abstract class Config
  {
    String address_to_bind;
    int allocated_rport;
    int rport;
    Session session;
    String target;
  }

  static class ConfigDaemon extends ChannelForwardedTCPIP.Config
  {
    Object[] arg;
  }

  static class ConfigLHost extends ChannelForwardedTCPIP.Config
  {
    SocketFactory factory;
    int lport;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelForwardedTCPIP
 * JD-Core Version:    0.6.2
 */