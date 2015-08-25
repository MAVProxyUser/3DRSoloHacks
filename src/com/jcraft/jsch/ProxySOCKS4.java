package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxySOCKS4
  implements Proxy
{
  private static int DEFAULTPORT = 1080;
  private InputStream in;
  private OutputStream out;
  private String passwd;
  private String proxy_host;
  private int proxy_port;
  private Socket socket;
  private String user;

  public ProxySOCKS4(String paramString)
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

  public ProxySOCKS4(String paramString, int paramInt)
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

  // ERROR //
  public void connect(SocketFactory paramSocketFactory, String paramString, int paramInt1, int paramInt2)
    throws JSchException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +180 -> 181
    //   4: aload_0
    //   5: aload_0
    //   6: getfield 50	com/jcraft/jsch/ProxySOCKS4:proxy_host	Ljava/lang/String;
    //   9: aload_0
    //   10: getfield 52	com/jcraft/jsch/ProxySOCKS4:proxy_port	I
    //   13: iload 4
    //   15: invokestatic 86	com/jcraft/jsch/Util:createSocket	(Ljava/lang/String;II)Ljava/net/Socket;
    //   18: putfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   21: aload_0
    //   22: aload_0
    //   23: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   26: invokevirtual 90	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   29: putfield 58	com/jcraft/jsch/ProxySOCKS4:in	Ljava/io/InputStream;
    //   32: aload_0
    //   33: aload_0
    //   34: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   37: invokevirtual 94	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   40: putfield 64	com/jcraft/jsch/ProxySOCKS4:out	Ljava/io/OutputStream;
    //   43: iload 4
    //   45: ifle +12 -> 57
    //   48: aload_0
    //   49: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   52: iload 4
    //   54: invokevirtual 98	java/net/Socket:setSoTimeout	(I)V
    //   57: aload_0
    //   58: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   61: iconst_1
    //   62: invokevirtual 102	java/net/Socket:setTcpNoDelay	(Z)V
    //   65: sipush 1024
    //   68: newarray byte
    //   70: astore 8
    //   72: iconst_0
    //   73: iconst_1
    //   74: iadd
    //   75: istore 9
    //   77: aload 8
    //   79: iconst_0
    //   80: iconst_4
    //   81: bastore
    //   82: iload 9
    //   84: iconst_1
    //   85: iadd
    //   86: istore 10
    //   88: aload 8
    //   90: iload 9
    //   92: iconst_1
    //   93: bastore
    //   94: iload 10
    //   96: iconst_1
    //   97: iadd
    //   98: istore 11
    //   100: aload 8
    //   102: iload 10
    //   104: iload_3
    //   105: bipush 8
    //   107: iushr
    //   108: i2b
    //   109: bastore
    //   110: iload 11
    //   112: iconst_1
    //   113: iadd
    //   114: istore 12
    //   116: aload 8
    //   118: iload 11
    //   120: iload_3
    //   121: sipush 255
    //   124: iand
    //   125: i2b
    //   126: bastore
    //   127: aload_2
    //   128: invokestatic 108	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   131: invokevirtual 112	java/net/InetAddress:getAddress	()[B
    //   134: astore 14
    //   136: iconst_0
    //   137: istore 15
    //   139: iload 12
    //   141: istore 16
    //   143: aload 14
    //   145: arraylength
    //   146: istore 18
    //   148: iload 15
    //   150: iload 18
    //   152: if_icmpge +165 -> 317
    //   155: iload 16
    //   157: iconst_1
    //   158: iadd
    //   159: istore 24
    //   161: aload 8
    //   163: iload 16
    //   165: aload 14
    //   167: iload 15
    //   169: baload
    //   170: bastore
    //   171: iinc 15 1
    //   174: iload 24
    //   176: istore 16
    //   178: goto -35 -> 143
    //   181: aload_0
    //   182: aload_1
    //   183: aload_0
    //   184: getfield 50	com/jcraft/jsch/ProxySOCKS4:proxy_host	Ljava/lang/String;
    //   187: aload_0
    //   188: getfield 52	com/jcraft/jsch/ProxySOCKS4:proxy_port	I
    //   191: invokeinterface 117 3 0
    //   196: putfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   199: aload_0
    //   200: aload_1
    //   201: aload_0
    //   202: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   205: invokeinterface 120 2 0
    //   210: putfield 58	com/jcraft/jsch/ProxySOCKS4:in	Ljava/io/InputStream;
    //   213: aload_0
    //   214: aload_1
    //   215: aload_0
    //   216: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   219: invokeinterface 123 2 0
    //   224: putfield 64	com/jcraft/jsch/ProxySOCKS4:out	Ljava/io/OutputStream;
    //   227: goto -184 -> 43
    //   230: astore 7
    //   232: aload 7
    //   234: athrow
    //   235: astore 13
    //   237: new 76	com/jcraft/jsch/JSchException
    //   240: dup
    //   241: new 125	java/lang/StringBuilder
    //   244: dup
    //   245: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   248: ldc 128
    //   250: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: aload 13
    //   255: invokevirtual 136	java/net/UnknownHostException:toString	()Ljava/lang/String;
    //   258: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   264: aload 13
    //   266: invokespecial 140	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   269: athrow
    //   270: astore 5
    //   272: aload_0
    //   273: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   276: ifnull +10 -> 286
    //   279: aload_0
    //   280: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   283: invokevirtual 72	java/net/Socket:close	()V
    //   286: new 76	com/jcraft/jsch/JSchException
    //   289: dup
    //   290: new 125	java/lang/StringBuilder
    //   293: dup
    //   294: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   297: ldc 128
    //   299: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: aload 5
    //   304: invokevirtual 141	java/lang/Exception:toString	()Ljava/lang/String;
    //   307: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   313: invokespecial 143	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   316: athrow
    //   317: aload_0
    //   318: getfield 145	com/jcraft/jsch/ProxySOCKS4:user	Ljava/lang/String;
    //   321: ifnull +37 -> 358
    //   324: aload_0
    //   325: getfield 145	com/jcraft/jsch/ProxySOCKS4:user	Ljava/lang/String;
    //   328: invokestatic 149	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   331: iconst_0
    //   332: aload 8
    //   334: iload 16
    //   336: aload_0
    //   337: getfield 145	com/jcraft/jsch/ProxySOCKS4:user	Ljava/lang/String;
    //   340: invokevirtual 152	java/lang/String:length	()I
    //   343: invokestatic 158	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   346: iload 16
    //   348: aload_0
    //   349: getfield 145	com/jcraft/jsch/ProxySOCKS4:user	Ljava/lang/String;
    //   352: invokevirtual 152	java/lang/String:length	()I
    //   355: iadd
    //   356: istore 16
    //   358: iload 16
    //   360: iconst_1
    //   361: iadd
    //   362: istore 19
    //   364: aload 8
    //   366: iload 16
    //   368: iconst_0
    //   369: bastore
    //   370: aload_0
    //   371: getfield 64	com/jcraft/jsch/ProxySOCKS4:out	Ljava/io/OutputStream;
    //   374: aload 8
    //   376: iconst_0
    //   377: iload 19
    //   379: invokevirtual 162	java/io/OutputStream:write	([BII)V
    //   382: iconst_0
    //   383: istore 20
    //   385: iload 20
    //   387: bipush 8
    //   389: if_icmpge +36 -> 425
    //   392: aload_0
    //   393: getfield 58	com/jcraft/jsch/ProxySOCKS4:in	Ljava/io/InputStream;
    //   396: aload 8
    //   398: iload 20
    //   400: bipush 8
    //   402: iload 20
    //   404: isub
    //   405: invokevirtual 166	java/io/InputStream:read	([BII)I
    //   408: istore 21
    //   410: iload 21
    //   412: ifgt +119 -> 531
    //   415: new 76	com/jcraft/jsch/JSchException
    //   418: dup
    //   419: ldc 168
    //   421: invokespecial 143	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   424: athrow
    //   425: aload 8
    //   427: iconst_0
    //   428: baload
    //   429: ifeq +33 -> 462
    //   432: new 76	com/jcraft/jsch/JSchException
    //   435: dup
    //   436: new 125	java/lang/StringBuilder
    //   439: dup
    //   440: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   443: ldc 170
    //   445: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: aload 8
    //   450: iconst_0
    //   451: baload
    //   452: invokevirtual 173	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   455: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   458: invokespecial 143	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   461: athrow
    //   462: aload 8
    //   464: iconst_1
    //   465: baload
    //   466: istore 22
    //   468: iload 22
    //   470: bipush 90
    //   472: if_icmpeq +40 -> 512
    //   475: aload_0
    //   476: getfield 69	com/jcraft/jsch/ProxySOCKS4:socket	Ljava/net/Socket;
    //   479: invokevirtual 72	java/net/Socket:close	()V
    //   482: new 76	com/jcraft/jsch/JSchException
    //   485: dup
    //   486: new 125	java/lang/StringBuilder
    //   489: dup
    //   490: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   493: ldc 175
    //   495: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: aload 8
    //   500: iconst_1
    //   501: baload
    //   502: invokevirtual 173	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   505: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   508: invokespecial 143	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   511: athrow
    //   512: return
    //   513: astore 6
    //   515: goto -229 -> 286
    //   518: astore 23
    //   520: goto -38 -> 482
    //   523: astore 13
    //   525: iload 16
    //   527: pop
    //   528: goto -291 -> 237
    //   531: iload 20
    //   533: iload 21
    //   535: iadd
    //   536: istore 20
    //   538: goto -153 -> 385
    //
    // Exception table:
    //   from	to	target	type
    //   4	43	230	java/lang/RuntimeException
    //   48	57	230	java/lang/RuntimeException
    //   57	72	230	java/lang/RuntimeException
    //   77	82	230	java/lang/RuntimeException
    //   88	94	230	java/lang/RuntimeException
    //   100	110	230	java/lang/RuntimeException
    //   116	127	230	java/lang/RuntimeException
    //   127	136	230	java/lang/RuntimeException
    //   143	148	230	java/lang/RuntimeException
    //   161	171	230	java/lang/RuntimeException
    //   181	227	230	java/lang/RuntimeException
    //   237	270	230	java/lang/RuntimeException
    //   317	358	230	java/lang/RuntimeException
    //   364	382	230	java/lang/RuntimeException
    //   392	410	230	java/lang/RuntimeException
    //   415	425	230	java/lang/RuntimeException
    //   425	462	230	java/lang/RuntimeException
    //   462	468	230	java/lang/RuntimeException
    //   475	482	230	java/lang/RuntimeException
    //   482	512	230	java/lang/RuntimeException
    //   127	136	235	java/net/UnknownHostException
    //   161	171	235	java/net/UnknownHostException
    //   4	43	270	java/lang/Exception
    //   48	57	270	java/lang/Exception
    //   57	72	270	java/lang/Exception
    //   77	82	270	java/lang/Exception
    //   88	94	270	java/lang/Exception
    //   100	110	270	java/lang/Exception
    //   116	127	270	java/lang/Exception
    //   127	136	270	java/lang/Exception
    //   143	148	270	java/lang/Exception
    //   161	171	270	java/lang/Exception
    //   181	227	270	java/lang/Exception
    //   237	270	270	java/lang/Exception
    //   317	358	270	java/lang/Exception
    //   364	382	270	java/lang/Exception
    //   392	410	270	java/lang/Exception
    //   415	425	270	java/lang/Exception
    //   425	462	270	java/lang/Exception
    //   462	468	270	java/lang/Exception
    //   482	512	270	java/lang/Exception
    //   272	286	513	java/lang/Exception
    //   475	482	518	java/lang/Exception
    //   143	148	523	java/net/UnknownHostException
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
 * Qualified Name:     com.jcraft.jsch.ProxySOCKS4
 * JD-Core Version:    0.6.2
 */