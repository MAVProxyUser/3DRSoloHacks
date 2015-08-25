package com.jcraft.jsch;

class ChannelAgentForwarding extends Channel
{
  private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
  private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
  private final byte SSH2_AGENTC_ADD_IDENTITY = 17;
  private final byte SSH2_AGENTC_REMOVE_ALL_IDENTITIES = 19;
  private final byte SSH2_AGENTC_REMOVE_IDENTITY = 18;
  private final byte SSH2_AGENTC_REQUEST_IDENTITIES = 11;
  private final byte SSH2_AGENTC_SIGN_REQUEST = 13;
  private final byte SSH2_AGENT_FAILURE = 30;
  private final byte SSH2_AGENT_IDENTITIES_ANSWER = 12;
  private final byte SSH2_AGENT_SIGN_RESPONSE = 14;
  private final byte SSH_AGENTC_ADD_RSA_IDENTITY = 7;
  private final byte SSH_AGENTC_REMOVE_ALL_RSA_IDENTITIES = 9;
  private final byte SSH_AGENTC_REMOVE_RSA_IDENTITY = 8;
  private final byte SSH_AGENTC_REQUEST_RSA_IDENTITIES = 1;
  private final byte SSH_AGENTC_RSA_CHALLENGE = 3;
  private final byte SSH_AGENT_FAILURE = 5;
  private final byte SSH_AGENT_RSA_IDENTITIES_ANSWER = 2;
  private final byte SSH_AGENT_RSA_RESPONSE = 4;
  private final byte SSH_AGENT_SUCCESS = 6;
  boolean init = true;
  private Buffer mbuf = null;
  private Packet packet = null;
  private Buffer rbuf = null;
  private Buffer wbuf = null;

  ChannelAgentForwarding()
  {
    setLocalWindowSizeMax(131072);
    setLocalWindowSize(131072);
    setLocalPacketSize(16384);
    this.type = Util.str2byte("auth-agent@openssh.com");
    this.rbuf = new Buffer();
    this.rbuf.reset();
    this.mbuf = new Buffer();
    this.connected = true;
  }

  private void send(byte[] paramArrayOfByte)
  {
    this.packet.reset();
    this.wbuf.putByte((byte)94);
    this.wbuf.putInt(this.recipient);
    this.wbuf.putInt(4 + paramArrayOfByte.length);
    this.wbuf.putString(paramArrayOfByte);
    try
    {
      getSession().write(this.packet, this, 4 + paramArrayOfByte.length);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  void eof_remote()
  {
    super.eof_remote();
    eof();
  }

  public void run()
  {
    try
    {
      sendOpenConfirmation();
      return;
    }
    catch (Exception localException)
    {
      this.close = true;
      disconnect();
    }
  }

  // ERROR //
  void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 81	com/jcraft/jsch/ChannelAgentForwarding:packet	Lcom/jcraft/jsch/Packet;
    //   4: ifnonnull +33 -> 37
    //   7: aload_0
    //   8: new 107	com/jcraft/jsch/Buffer
    //   11: dup
    //   12: aload_0
    //   13: getfield 168	com/jcraft/jsch/ChannelAgentForwarding:rmpsize	I
    //   16: invokespecial 170	com/jcraft/jsch/Buffer:<init>	(I)V
    //   19: putfield 79	com/jcraft/jsch/ChannelAgentForwarding:wbuf	Lcom/jcraft/jsch/Buffer;
    //   22: aload_0
    //   23: new 120	com/jcraft/jsch/Packet
    //   26: dup
    //   27: aload_0
    //   28: getfield 79	com/jcraft/jsch/ChannelAgentForwarding:wbuf	Lcom/jcraft/jsch/Buffer;
    //   31: invokespecial 173	com/jcraft/jsch/Packet:<init>	(Lcom/jcraft/jsch/Buffer;)V
    //   34: putfield 81	com/jcraft/jsch/ChannelAgentForwarding:packet	Lcom/jcraft/jsch/Packet;
    //   37: aload_0
    //   38: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   41: invokevirtual 176	com/jcraft/jsch/Buffer:shift	()V
    //   44: aload_0
    //   45: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   48: getfield 179	com/jcraft/jsch/Buffer:buffer	[B
    //   51: arraylength
    //   52: iload_3
    //   53: aload_0
    //   54: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   57: getfield 182	com/jcraft/jsch/Buffer:index	I
    //   60: iadd
    //   61: if_icmpge +47 -> 108
    //   64: iload_3
    //   65: aload_0
    //   66: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   69: getfield 185	com/jcraft/jsch/Buffer:s	I
    //   72: iadd
    //   73: newarray byte
    //   75: astore 37
    //   77: aload_0
    //   78: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   81: getfield 179	com/jcraft/jsch/Buffer:buffer	[B
    //   84: iconst_0
    //   85: aload 37
    //   87: iconst_0
    //   88: aload_0
    //   89: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   92: getfield 179	com/jcraft/jsch/Buffer:buffer	[B
    //   95: arraylength
    //   96: invokestatic 191	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   99: aload_0
    //   100: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   103: aload 37
    //   105: putfield 179	com/jcraft/jsch/Buffer:buffer	[B
    //   108: aload_0
    //   109: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   112: aload_1
    //   113: iload_2
    //   114: iload_3
    //   115: invokevirtual 193	com/jcraft/jsch/Buffer:putByte	([BII)V
    //   118: aload_0
    //   119: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   122: invokevirtual 197	com/jcraft/jsch/Buffer:getInt	()I
    //   125: aload_0
    //   126: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   129: invokevirtual 200	com/jcraft/jsch/Buffer:getLength	()I
    //   132: if_icmple +23 -> 155
    //   135: aload_0
    //   136: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   139: astore 36
    //   141: aload 36
    //   143: bipush 252
    //   145: aload 36
    //   147: getfield 185	com/jcraft/jsch/Buffer:s	I
    //   150: iadd
    //   151: putfield 185	com/jcraft/jsch/Buffer:s	I
    //   154: return
    //   155: aload_0
    //   156: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   159: invokevirtual 203	com/jcraft/jsch/Buffer:getByte	()I
    //   162: istore 4
    //   164: aload_0
    //   165: invokevirtual 138	com/jcraft/jsch/ChannelAgentForwarding:getSession	()Lcom/jcraft/jsch/Session;
    //   168: astore 6
    //   170: aload 6
    //   172: invokevirtual 207	com/jcraft/jsch/Session:getIdentityRepository	()Lcom/jcraft/jsch/IdentityRepository;
    //   175: astore 7
    //   177: aload 6
    //   179: invokevirtual 211	com/jcraft/jsch/Session:getUserInfo	()Lcom/jcraft/jsch/UserInfo;
    //   182: astore 8
    //   184: aload_0
    //   185: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   188: invokevirtual 111	com/jcraft/jsch/Buffer:reset	()V
    //   191: iload 4
    //   193: bipush 11
    //   195: if_icmpne +199 -> 394
    //   198: aload_0
    //   199: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   202: bipush 12
    //   204: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   207: aload 7
    //   209: invokeinterface 217 1 0
    //   214: astore 29
    //   216: aload 29
    //   218: monitorenter
    //   219: iconst_0
    //   220: istore 30
    //   222: iconst_0
    //   223: istore 31
    //   225: iload 31
    //   227: aload 29
    //   229: invokevirtual 222	java/util/Vector:size	()I
    //   232: if_icmpge +52 -> 284
    //   235: aload 29
    //   237: iload 31
    //   239: invokevirtual 226	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   242: checkcast 228	com/jcraft/jsch/Identity
    //   245: checkcast 228	com/jcraft/jsch/Identity
    //   248: invokeinterface 232 1 0
    //   253: astore 35
    //   255: aload 35
    //   257: ifnull +6 -> 263
    //   260: iinc 30 1
    //   263: iinc 31 1
    //   266: goto -41 -> 225
    //   269: astore 5
    //   271: new 163	java/io/IOException
    //   274: dup
    //   275: aload 5
    //   277: invokevirtual 236	com/jcraft/jsch/JSchException:toString	()Ljava/lang/String;
    //   280: invokespecial 239	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   283: athrow
    //   284: aload_0
    //   285: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   288: iload 30
    //   290: invokevirtual 131	com/jcraft/jsch/Buffer:putInt	(I)V
    //   293: iconst_0
    //   294: istore 33
    //   296: iload 33
    //   298: aload 29
    //   300: invokevirtual 222	java/util/Vector:size	()I
    //   303: if_icmpge +61 -> 364
    //   306: aload 29
    //   308: iload 33
    //   310: invokevirtual 226	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   313: checkcast 228	com/jcraft/jsch/Identity
    //   316: checkcast 228	com/jcraft/jsch/Identity
    //   319: invokeinterface 232 1 0
    //   324: astore 34
    //   326: aload 34
    //   328: ifnonnull +6 -> 334
    //   331: goto +559 -> 890
    //   334: aload_0
    //   335: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   338: aload 34
    //   340: invokevirtual 134	com/jcraft/jsch/Buffer:putString	([B)V
    //   343: aload_0
    //   344: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   347: getstatic 242	com/jcraft/jsch/Util:empty	[B
    //   350: invokevirtual 134	com/jcraft/jsch/Buffer:putString	([B)V
    //   353: goto +537 -> 890
    //   356: astore 32
    //   358: aload 29
    //   360: monitorexit
    //   361: aload 32
    //   363: athrow
    //   364: aload 29
    //   366: monitorexit
    //   367: aload_0
    //   368: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   371: invokevirtual 200	com/jcraft/jsch/Buffer:getLength	()I
    //   374: newarray byte
    //   376: astore 9
    //   378: aload_0
    //   379: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   382: aload 9
    //   384: invokevirtual 244	com/jcraft/jsch/Buffer:getByte	([B)V
    //   387: aload_0
    //   388: aload 9
    //   390: invokespecial 246	com/jcraft/jsch/ChannelAgentForwarding:send	([B)V
    //   393: return
    //   394: iload 4
    //   396: iconst_1
    //   397: if_icmpne +22 -> 419
    //   400: aload_0
    //   401: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   404: iconst_2
    //   405: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   408: aload_0
    //   409: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   412: iconst_0
    //   413: invokevirtual 131	com/jcraft/jsch/Buffer:putInt	(I)V
    //   416: goto -49 -> 367
    //   419: iload 4
    //   421: bipush 13
    //   423: if_icmpne +292 -> 715
    //   426: aload_0
    //   427: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   430: invokevirtual 249	com/jcraft/jsch/Buffer:getString	()[B
    //   433: astore 15
    //   435: aload_0
    //   436: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   439: invokevirtual 249	com/jcraft/jsch/Buffer:getString	()[B
    //   442: astore 16
    //   444: aload_0
    //   445: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   448: invokevirtual 197	com/jcraft/jsch/Buffer:getInt	()I
    //   451: pop
    //   452: aload 7
    //   454: invokeinterface 217 1 0
    //   459: astore 18
    //   461: aload 18
    //   463: monitorenter
    //   464: iconst_0
    //   465: istore 19
    //   467: aload 18
    //   469: invokevirtual 222	java/util/Vector:size	()I
    //   472: istore 21
    //   474: aconst_null
    //   475: astore 22
    //   477: iload 19
    //   479: iload 21
    //   481: if_icmpge +121 -> 602
    //   484: aload 18
    //   486: iload 19
    //   488: invokevirtual 226	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   491: checkcast 228	com/jcraft/jsch/Identity
    //   494: checkcast 228	com/jcraft/jsch/Identity
    //   497: astore 23
    //   499: aload 23
    //   501: invokeinterface 232 1 0
    //   506: ifnonnull +6 -> 512
    //   509: goto +387 -> 896
    //   512: aload 15
    //   514: aload 23
    //   516: invokeinterface 232 1 0
    //   521: invokestatic 253	com/jcraft/jsch/Util:array_equals	([B[B)Z
    //   524: ifeq +372 -> 896
    //   527: aload 23
    //   529: invokeinterface 257 1 0
    //   534: ifeq +54 -> 588
    //   537: aload 8
    //   539: ifnull +357 -> 896
    //   542: aload 23
    //   544: invokeinterface 257 1 0
    //   549: ifeq +39 -> 588
    //   552: aload 8
    //   554: new 259	java/lang/StringBuilder
    //   557: dup
    //   558: invokespecial 260	java/lang/StringBuilder:<init>	()V
    //   561: ldc_w 262
    //   564: invokevirtual 266	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   567: aload 23
    //   569: invokeinterface 269 1 0
    //   574: invokevirtual 266	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   577: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   580: invokeinterface 276 2 0
    //   585: ifne +56 -> 641
    //   588: aload 23
    //   590: invokeinterface 257 1 0
    //   595: ifne +301 -> 896
    //   598: aload 23
    //   600: astore 22
    //   602: aload 18
    //   604: monitorexit
    //   605: aconst_null
    //   606: astore 24
    //   608: aload 22
    //   610: ifnull +14 -> 624
    //   613: aload 22
    //   615: aload 16
    //   617: invokeinterface 280 2 0
    //   622: astore 24
    //   624: aload 24
    //   626: ifnonnull +68 -> 694
    //   629: aload_0
    //   630: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   633: bipush 30
    //   635: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   638: goto -271 -> 367
    //   641: aload 8
    //   643: invokeinterface 283 1 0
    //   648: astore 25
    //   650: aload 25
    //   652: ifnull -64 -> 588
    //   655: aload 25
    //   657: invokestatic 101	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   660: astore 26
    //   662: aload 23
    //   664: aload 26
    //   666: invokeinterface 287 2 0
    //   671: istore 28
    //   673: iload 28
    //   675: ifeq -133 -> 542
    //   678: goto -90 -> 588
    //   681: astore 27
    //   683: goto -95 -> 588
    //   686: astore 20
    //   688: aload 18
    //   690: monitorexit
    //   691: aload 20
    //   693: athrow
    //   694: aload_0
    //   695: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   698: bipush 14
    //   700: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   703: aload_0
    //   704: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   707: aload 24
    //   709: invokevirtual 134	com/jcraft/jsch/Buffer:putString	([B)V
    //   712: goto -345 -> 367
    //   715: iload 4
    //   717: bipush 18
    //   719: if_icmpne +30 -> 749
    //   722: aload 7
    //   724: aload_0
    //   725: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   728: invokevirtual 249	com/jcraft/jsch/Buffer:getString	()[B
    //   731: invokeinterface 290 2 0
    //   736: pop
    //   737: aload_0
    //   738: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   741: bipush 6
    //   743: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   746: goto -379 -> 367
    //   749: iload 4
    //   751: bipush 9
    //   753: if_icmpne +15 -> 768
    //   756: aload_0
    //   757: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   760: bipush 6
    //   762: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   765: goto -398 -> 367
    //   768: iload 4
    //   770: bipush 19
    //   772: if_icmpne +22 -> 794
    //   775: aload 7
    //   777: invokeinterface 293 1 0
    //   782: aload_0
    //   783: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   786: bipush 6
    //   788: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   791: goto -424 -> 367
    //   794: iload 4
    //   796: bipush 17
    //   798: if_icmpne +65 -> 863
    //   801: aload_0
    //   802: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   805: invokevirtual 200	com/jcraft/jsch/Buffer:getLength	()I
    //   808: newarray byte
    //   810: astore 10
    //   812: aload_0
    //   813: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   816: aload 10
    //   818: invokevirtual 244	com/jcraft/jsch/Buffer:getByte	([B)V
    //   821: aload 7
    //   823: aload 10
    //   825: invokeinterface 296 2 0
    //   830: istore 11
    //   832: aload_0
    //   833: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   836: astore 12
    //   838: iload 11
    //   840: ifeq +17 -> 857
    //   843: bipush 6
    //   845: istore 13
    //   847: aload 12
    //   849: iload 13
    //   851: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   854: goto -487 -> 367
    //   857: iconst_5
    //   858: istore 13
    //   860: goto -13 -> 847
    //   863: aload_0
    //   864: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   867: iconst_m1
    //   868: aload_0
    //   869: getfield 77	com/jcraft/jsch/ChannelAgentForwarding:rbuf	Lcom/jcraft/jsch/Buffer;
    //   872: invokevirtual 200	com/jcraft/jsch/Buffer:getLength	()I
    //   875: iadd
    //   876: invokevirtual 299	com/jcraft/jsch/Buffer:skip	(I)V
    //   879: aload_0
    //   880: getfield 83	com/jcraft/jsch/ChannelAgentForwarding:mbuf	Lcom/jcraft/jsch/Buffer;
    //   883: iconst_5
    //   884: invokevirtual 125	com/jcraft/jsch/Buffer:putByte	(B)V
    //   887: goto -520 -> 367
    //   890: iinc 33 1
    //   893: goto -597 -> 296
    //   896: iinc 19 1
    //   899: goto -432 -> 467
    //
    // Exception table:
    //   from	to	target	type
    //   164	170	269	com/jcraft/jsch/JSchException
    //   225	255	356	finally
    //   284	293	356	finally
    //   296	326	356	finally
    //   334	353	356	finally
    //   358	361	356	finally
    //   364	367	356	finally
    //   662	673	681	com/jcraft/jsch/JSchException
    //   467	474	686	finally
    //   484	509	686	finally
    //   512	537	686	finally
    //   542	588	686	finally
    //   588	598	686	finally
    //   602	605	686	finally
    //   641	650	686	finally
    //   655	662	686	finally
    //   662	673	686	finally
    //   688	691	686	finally
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelAgentForwarding
 * JD-Core Version:    0.6.2
 */