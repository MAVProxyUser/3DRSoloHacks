package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxySOCKS5
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

  public ProxySOCKS5(String paramString)
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

  public ProxySOCKS5(String paramString, int paramInt)
  {
    this.proxy_host = paramString;
    this.proxy_port = paramInt;
  }

  private void fill(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt)
    throws JSchException, IOException
  {
    int i = 0;
    while (i < paramInt)
    {
      int j = paramInputStream.read(paramArrayOfByte, i, paramInt - i);
      if (j <= 0)
        throw new JSchException("ProxySOCKS5: stream is closed");
      i += j;
    }
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
    //   1: ifnonnull +214 -> 215
    //   4: aload_0
    //   5: aload_0
    //   6: getfield 50	com/jcraft/jsch/ProxySOCKS5:proxy_host	Ljava/lang/String;
    //   9: aload_0
    //   10: getfield 52	com/jcraft/jsch/ProxySOCKS5:proxy_port	I
    //   13: iload 4
    //   15: invokestatic 96	com/jcraft/jsch/Util:createSocket	(Ljava/lang/String;II)Ljava/net/Socket;
    //   18: putfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   21: aload_0
    //   22: aload_0
    //   23: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   26: invokevirtual 100	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   29: putfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   32: aload_0
    //   33: aload_0
    //   34: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   37: invokevirtual 104	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   40: putfield 78	com/jcraft/jsch/ProxySOCKS5:out	Ljava/io/OutputStream;
    //   43: iload 4
    //   45: ifle +12 -> 57
    //   48: aload_0
    //   49: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   52: iload 4
    //   54: invokevirtual 108	java/net/Socket:setSoTimeout	(I)V
    //   57: aload_0
    //   58: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   61: iconst_1
    //   62: invokevirtual 112	java/net/Socket:setTcpNoDelay	(Z)V
    //   65: sipush 1024
    //   68: newarray byte
    //   70: astore 9
    //   72: iconst_0
    //   73: iconst_1
    //   74: iadd
    //   75: istore 10
    //   77: aload 9
    //   79: iconst_0
    //   80: iconst_5
    //   81: bastore
    //   82: iload 10
    //   84: iconst_1
    //   85: iadd
    //   86: istore 11
    //   88: aload 9
    //   90: iload 10
    //   92: iconst_2
    //   93: bastore
    //   94: iload 11
    //   96: iconst_1
    //   97: iadd
    //   98: istore 12
    //   100: aload 9
    //   102: iload 11
    //   104: iconst_0
    //   105: bastore
    //   106: iload 12
    //   108: iconst_1
    //   109: iadd
    //   110: istore 13
    //   112: aload 9
    //   114: iload 12
    //   116: iconst_2
    //   117: bastore
    //   118: aload_0
    //   119: getfield 78	com/jcraft/jsch/ProxySOCKS5:out	Ljava/io/OutputStream;
    //   122: aload 9
    //   124: iconst_0
    //   125: iload 13
    //   127: invokevirtual 116	java/io/OutputStream:write	([BII)V
    //   130: aload_0
    //   131: aload_0
    //   132: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   135: aload 9
    //   137: iconst_2
    //   138: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   141: aload 9
    //   143: iconst_1
    //   144: baload
    //   145: istore 14
    //   147: iload 14
    //   149: sipush 255
    //   152: iand
    //   153: istore 15
    //   155: iconst_0
    //   156: istore 16
    //   158: iload 15
    //   160: tableswitch	default:+28 -> 188, 0:+165->325, 1:+28->188, 2:+171->331
    //   189: bipush 154
    //   191: aconst_null
    //   192: dstore_0
    //   193: aload_0
    //   194: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   197: invokevirtual 86	java/net/Socket:close	()V
    //   200: new 57	com/jcraft/jsch/JSchException
    //   203: dup
    //   204: ldc 120
    //   206: invokespecial 69	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   209: athrow
    //   210: astore 8
    //   212: aload 8
    //   214: athrow
    //   215: aload_0
    //   216: aload_1
    //   217: aload_0
    //   218: getfield 50	com/jcraft/jsch/ProxySOCKS5:proxy_host	Ljava/lang/String;
    //   221: aload_0
    //   222: getfield 52	com/jcraft/jsch/ProxySOCKS5:proxy_port	I
    //   225: invokeinterface 125 3 0
    //   230: putfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   233: aload_0
    //   234: aload_1
    //   235: aload_0
    //   236: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   239: invokeinterface 128 2 0
    //   244: putfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   247: aload_0
    //   248: aload_1
    //   249: aload_0
    //   250: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   253: invokeinterface 131 2 0
    //   258: putfield 78	com/jcraft/jsch/ProxySOCKS5:out	Ljava/io/OutputStream;
    //   261: goto -218 -> 43
    //   264: astore 5
    //   266: aload_0
    //   267: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   270: ifnull +10 -> 280
    //   273: aload_0
    //   274: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   277: invokevirtual 86	java/net/Socket:close	()V
    //   280: new 133	java/lang/StringBuilder
    //   283: dup
    //   284: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   287: ldc 136
    //   289: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: aload 5
    //   294: invokevirtual 144	java/lang/Exception:toString	()Ljava/lang/String;
    //   297: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   300: invokevirtual 145	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   303: astore 7
    //   305: aload 5
    //   307: instanceof 147
    //   310: ifeq +496 -> 806
    //   313: new 57	com/jcraft/jsch/JSchException
    //   316: dup
    //   317: aload 7
    //   319: aload 5
    //   321: invokespecial 150	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   324: athrow
    //   325: iconst_1
    //   326: istore 16
    //   328: goto -140 -> 188
    //   331: aload_0
    //   332: getfield 152	com/jcraft/jsch/ProxySOCKS5:user	Ljava/lang/String;
    //   335: astore 17
    //   337: iconst_0
    //   338: istore 16
    //   340: aload 17
    //   342: ifnull -154 -> 188
    //   345: aload_0
    //   346: getfield 154	com/jcraft/jsch/ProxySOCKS5:passwd	Ljava/lang/String;
    //   349: astore 18
    //   351: iconst_0
    //   352: istore 16
    //   354: aload 18
    //   356: ifnull -168 -> 188
    //   359: iconst_0
    //   360: iconst_1
    //   361: iadd
    //   362: istore 19
    //   364: aload 9
    //   366: iconst_0
    //   367: iconst_1
    //   368: bastore
    //   369: iload 19
    //   371: iconst_1
    //   372: iadd
    //   373: istore 20
    //   375: aload 9
    //   377: iload 19
    //   379: aload_0
    //   380: getfield 152	com/jcraft/jsch/ProxySOCKS5:user	Ljava/lang/String;
    //   383: invokevirtual 157	java/lang/String:length	()I
    //   386: i2b
    //   387: bastore
    //   388: aload_0
    //   389: getfield 152	com/jcraft/jsch/ProxySOCKS5:user	Ljava/lang/String;
    //   392: invokestatic 161	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   395: iconst_0
    //   396: aload 9
    //   398: iload 20
    //   400: aload_0
    //   401: getfield 152	com/jcraft/jsch/ProxySOCKS5:user	Ljava/lang/String;
    //   404: invokevirtual 157	java/lang/String:length	()I
    //   407: invokestatic 167	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   410: iconst_2
    //   411: aload_0
    //   412: getfield 152	com/jcraft/jsch/ProxySOCKS5:user	Ljava/lang/String;
    //   415: invokevirtual 157	java/lang/String:length	()I
    //   418: iadd
    //   419: istore 21
    //   421: iload 21
    //   423: iconst_1
    //   424: iadd
    //   425: istore 22
    //   427: aload 9
    //   429: iload 21
    //   431: aload_0
    //   432: getfield 154	com/jcraft/jsch/ProxySOCKS5:passwd	Ljava/lang/String;
    //   435: invokevirtual 157	java/lang/String:length	()I
    //   438: i2b
    //   439: bastore
    //   440: aload_0
    //   441: getfield 154	com/jcraft/jsch/ProxySOCKS5:passwd	Ljava/lang/String;
    //   444: invokestatic 161	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   447: iconst_0
    //   448: aload 9
    //   450: iload 22
    //   452: aload_0
    //   453: getfield 154	com/jcraft/jsch/ProxySOCKS5:passwd	Ljava/lang/String;
    //   456: invokevirtual 157	java/lang/String:length	()I
    //   459: invokestatic 167	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   462: iload 22
    //   464: aload_0
    //   465: getfield 154	com/jcraft/jsch/ProxySOCKS5:passwd	Ljava/lang/String;
    //   468: invokevirtual 157	java/lang/String:length	()I
    //   471: iadd
    //   472: istore 23
    //   474: aload_0
    //   475: getfield 78	com/jcraft/jsch/ProxySOCKS5:out	Ljava/io/OutputStream;
    //   478: aload 9
    //   480: iconst_0
    //   481: iload 23
    //   483: invokevirtual 116	java/io/OutputStream:write	([BII)V
    //   486: aload_0
    //   487: aload_0
    //   488: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   491: aload 9
    //   493: iconst_2
    //   494: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   497: aload 9
    //   499: iconst_1
    //   500: baload
    //   501: istore 24
    //   503: iconst_0
    //   504: istore 16
    //   506: iload 24
    //   508: ifne -320 -> 188
    //   511: iconst_1
    //   512: istore 16
    //   514: goto -326 -> 188
    //   517: iconst_0
    //   518: iconst_1
    //   519: iadd
    //   520: istore 25
    //   522: aload 9
    //   524: iconst_0
    //   525: iconst_5
    //   526: bastore
    //   527: iload 25
    //   529: iconst_1
    //   530: iadd
    //   531: istore 26
    //   533: aload 9
    //   535: iload 25
    //   537: iconst_1
    //   538: bastore
    //   539: iload 26
    //   541: iconst_1
    //   542: iadd
    //   543: istore 27
    //   545: aload 9
    //   547: iload 26
    //   549: iconst_0
    //   550: bastore
    //   551: aload_2
    //   552: invokestatic 161	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   555: astore 28
    //   557: aload 28
    //   559: arraylength
    //   560: istore 29
    //   562: iload 27
    //   564: iconst_1
    //   565: iadd
    //   566: istore 30
    //   568: aload 9
    //   570: iload 27
    //   572: iconst_3
    //   573: bastore
    //   574: iload 30
    //   576: iconst_1
    //   577: iadd
    //   578: istore 31
    //   580: aload 9
    //   582: iload 30
    //   584: iload 29
    //   586: i2b
    //   587: bastore
    //   588: aload 28
    //   590: iconst_0
    //   591: aload 9
    //   593: iload 31
    //   595: iload 29
    //   597: invokestatic 167	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   600: iload 29
    //   602: iconst_5
    //   603: iadd
    //   604: istore 32
    //   606: iload 32
    //   608: iconst_1
    //   609: iadd
    //   610: istore 33
    //   612: aload 9
    //   614: iload 32
    //   616: iload_3
    //   617: bipush 8
    //   619: iushr
    //   620: i2b
    //   621: bastore
    //   622: iload 33
    //   624: iconst_1
    //   625: iadd
    //   626: istore 34
    //   628: aload 9
    //   630: iload 33
    //   632: iload_3
    //   633: sipush 255
    //   636: iand
    //   637: i2b
    //   638: bastore
    //   639: aload_0
    //   640: getfield 78	com/jcraft/jsch/ProxySOCKS5:out	Ljava/io/OutputStream;
    //   643: aload 9
    //   645: iconst_0
    //   646: iload 34
    //   648: invokevirtual 116	java/io/OutputStream:write	([BII)V
    //   651: aload_0
    //   652: aload_0
    //   653: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   656: aload 9
    //   658: iconst_4
    //   659: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   662: aload 9
    //   664: iconst_1
    //   665: baload
    //   666: istore 35
    //   668: iload 35
    //   670: ifeq +40 -> 710
    //   673: aload_0
    //   674: getfield 83	com/jcraft/jsch/ProxySOCKS5:socket	Ljava/net/Socket;
    //   677: invokevirtual 86	java/net/Socket:close	()V
    //   680: new 57	com/jcraft/jsch/JSchException
    //   683: dup
    //   684: new 133	java/lang/StringBuilder
    //   687: dup
    //   688: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   691: ldc 169
    //   693: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   696: aload 9
    //   698: iconst_1
    //   699: baload
    //   700: invokevirtual 172	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   703: invokevirtual 145	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   706: invokespecial 69	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   709: athrow
    //   710: sipush 255
    //   713: aload 9
    //   715: iconst_3
    //   716: baload
    //   717: iand
    //   718: tableswitch	default:+113 -> 831, 1:+30->748, 2:+113->831, 3:+43->761, 4:+75->793
    //   749: aload_0
    //   750: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   753: aload 9
    //   755: bipush 6
    //   757: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   760: return
    //   761: aload_0
    //   762: aload_0
    //   763: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   766: aload 9
    //   768: iconst_1
    //   769: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   772: aload_0
    //   773: aload_0
    //   774: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   777: aload 9
    //   779: iconst_2
    //   780: sipush 255
    //   783: aload 9
    //   785: iconst_0
    //   786: baload
    //   787: iand
    //   788: iadd
    //   789: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   792: return
    //   793: aload_0
    //   794: aload_0
    //   795: getfield 74	com/jcraft/jsch/ProxySOCKS5:in	Ljava/io/InputStream;
    //   798: aload 9
    //   800: bipush 18
    //   802: invokespecial 118	com/jcraft/jsch/ProxySOCKS5:fill	(Ljava/io/InputStream;[BI)V
    //   805: return
    //   806: new 57	com/jcraft/jsch/JSchException
    //   809: dup
    //   810: aload 7
    //   812: invokespecial 69	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   815: athrow
    //   816: astore 6
    //   818: goto -538 -> 280
    //   821: astore 36
    //   823: goto -143 -> 680
    //   826: astore 37
    //   828: goto -628 -> 200
    //   831: return
    //
    // Exception table:
    //   from	to	target	type
    //   4	43	210	java/lang/RuntimeException
    //   48	57	210	java/lang/RuntimeException
    //   57	72	210	java/lang/RuntimeException
    //   77	82	210	java/lang/RuntimeException
    //   88	94	210	java/lang/RuntimeException
    //   100	106	210	java/lang/RuntimeException
    //   112	147	210	java/lang/RuntimeException
    //   193	200	210	java/lang/RuntimeException
    //   200	210	210	java/lang/RuntimeException
    //   215	261	210	java/lang/RuntimeException
    //   331	337	210	java/lang/RuntimeException
    //   345	351	210	java/lang/RuntimeException
    //   364	369	210	java/lang/RuntimeException
    //   375	421	210	java/lang/RuntimeException
    //   427	503	210	java/lang/RuntimeException
    //   522	527	210	java/lang/RuntimeException
    //   533	539	210	java/lang/RuntimeException
    //   545	562	210	java/lang/RuntimeException
    //   568	574	210	java/lang/RuntimeException
    //   580	600	210	java/lang/RuntimeException
    //   612	622	210	java/lang/RuntimeException
    //   628	668	210	java/lang/RuntimeException
    //   673	680	210	java/lang/RuntimeException
    //   680	710	210	java/lang/RuntimeException
    //   710	748	210	java/lang/RuntimeException
    //   748	760	210	java/lang/RuntimeException
    //   761	792	210	java/lang/RuntimeException
    //   793	805	210	java/lang/RuntimeException
    //   4	43	264	java/lang/Exception
    //   48	57	264	java/lang/Exception
    //   57	72	264	java/lang/Exception
    //   77	82	264	java/lang/Exception
    //   88	94	264	java/lang/Exception
    //   100	106	264	java/lang/Exception
    //   112	147	264	java/lang/Exception
    //   200	210	264	java/lang/Exception
    //   215	261	264	java/lang/Exception
    //   331	337	264	java/lang/Exception
    //   345	351	264	java/lang/Exception
    //   364	369	264	java/lang/Exception
    //   375	421	264	java/lang/Exception
    //   427	503	264	java/lang/Exception
    //   522	527	264	java/lang/Exception
    //   533	539	264	java/lang/Exception
    //   545	562	264	java/lang/Exception
    //   568	574	264	java/lang/Exception
    //   580	600	264	java/lang/Exception
    //   612	622	264	java/lang/Exception
    //   628	668	264	java/lang/Exception
    //   680	710	264	java/lang/Exception
    //   710	748	264	java/lang/Exception
    //   748	760	264	java/lang/Exception
    //   761	792	264	java/lang/Exception
    //   793	805	264	java/lang/Exception
    //   266	280	816	java/lang/Exception
    //   673	680	821	java/lang/Exception
    //   193	200	826	java/lang/Exception
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
 * Qualified Name:     com.jcraft.jsch.ProxySOCKS5
 * JD-Core Version:    0.6.2
 */