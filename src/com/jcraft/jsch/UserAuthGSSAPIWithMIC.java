package com.jcraft.jsch;

public class UserAuthGSSAPIWithMIC extends UserAuth
{
  private static final int SSH_MSG_USERAUTH_GSSAPI_ERROR = 64;
  private static final int SSH_MSG_USERAUTH_GSSAPI_ERRTOK = 65;
  private static final int SSH_MSG_USERAUTH_GSSAPI_EXCHANGE_COMPLETE = 63;
  private static final int SSH_MSG_USERAUTH_GSSAPI_MIC = 66;
  private static final int SSH_MSG_USERAUTH_GSSAPI_RESPONSE = 60;
  private static final int SSH_MSG_USERAUTH_GSSAPI_TOKEN = 61;
  private static final String[] supported_method = { "gssapi-with-mic.krb5" };
  private static final byte[][] supported_oid = { { 6, 9, 42, -122, 72, -122, -9, 18, 1, 2, 2 } };

  // ERROR //
  public boolean start(Session paramSession)
    throws java.lang.Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 53	com/jcraft/jsch/UserAuth:start	(Lcom/jcraft/jsch/Session;)Z
    //   5: pop
    //   6: aload_0
    //   7: getfield 57	com/jcraft/jsch/UserAuthGSSAPIWithMIC:username	Ljava/lang/String;
    //   10: invokestatic 63	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 67	com/jcraft/jsch/UserAuthGSSAPIWithMIC:packet	Lcom/jcraft/jsch/Packet;
    //   18: invokevirtual 72	com/jcraft/jsch/Packet:reset	()V
    //   21: aload_0
    //   22: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   25: bipush 50
    //   27: invokevirtual 82	com/jcraft/jsch/Buffer:putByte	(B)V
    //   30: aload_0
    //   31: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   34: aload_3
    //   35: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   38: aload_0
    //   39: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   42: ldc 88
    //   44: invokestatic 63	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   47: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   50: aload_0
    //   51: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   54: ldc 90
    //   56: invokestatic 63	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   59: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   62: aload_0
    //   63: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   66: getstatic 36	com/jcraft/jsch/UserAuthGSSAPIWithMIC:supported_oid	[[B
    //   69: arraylength
    //   70: invokevirtual 94	com/jcraft/jsch/Buffer:putInt	(I)V
    //   73: iconst_0
    //   74: istore 4
    //   76: iload 4
    //   78: getstatic 36	com/jcraft/jsch/UserAuthGSSAPIWithMIC:supported_oid	[[B
    //   81: arraylength
    //   82: if_icmpge +22 -> 104
    //   85: aload_0
    //   86: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   89: getstatic 36	com/jcraft/jsch/UserAuthGSSAPIWithMIC:supported_oid	[[B
    //   92: iload 4
    //   94: aaload
    //   95: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   98: iinc 4 1
    //   101: goto -25 -> 76
    //   104: aload_1
    //   105: aload_0
    //   106: getfield 67	com/jcraft/jsch/UserAuthGSSAPIWithMIC:packet	Lcom/jcraft/jsch/Packet;
    //   109: invokevirtual 100	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   112: aload_0
    //   113: aload_1
    //   114: aload_0
    //   115: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   118: invokevirtual 104	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   121: putfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   124: sipush 255
    //   127: aload_0
    //   128: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   131: invokevirtual 108	com/jcraft/jsch/Buffer:getCommand	()B
    //   134: iand
    //   135: istore 5
    //   137: iload 5
    //   139: bipush 51
    //   141: if_icmpne +5 -> 146
    //   144: iconst_0
    //   145: ireturn
    //   146: iload 5
    //   148: bipush 60
    //   150: if_icmpne +90 -> 240
    //   153: aload_0
    //   154: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   157: invokevirtual 112	com/jcraft/jsch/Buffer:getInt	()I
    //   160: pop
    //   161: aload_0
    //   162: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   165: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   168: pop
    //   169: aload_0
    //   170: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   173: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   176: pop
    //   177: aload_0
    //   178: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   181: invokevirtual 119	com/jcraft/jsch/Buffer:getString	()[B
    //   184: astore 15
    //   186: iconst_0
    //   187: istore 16
    //   189: getstatic 36	com/jcraft/jsch/UserAuthGSSAPIWithMIC:supported_oid	[[B
    //   192: arraylength
    //   193: istore 17
    //   195: aconst_null
    //   196: astore 18
    //   198: iload 16
    //   200: iload 17
    //   202: if_icmpge +25 -> 227
    //   205: aload 15
    //   207: getstatic 36	com/jcraft/jsch/UserAuthGSSAPIWithMIC:supported_oid	[[B
    //   210: iload 16
    //   212: aaload
    //   213: invokestatic 123	com/jcraft/jsch/Util:array_equals	([B[B)Z
    //   216: ifeq +18 -> 234
    //   219: getstatic 42	com/jcraft/jsch/UserAuthGSSAPIWithMIC:supported_method	[Ljava/lang/String;
    //   222: iload 16
    //   224: aaload
    //   225: astore 18
    //   227: aload 18
    //   229: ifnonnull +89 -> 318
    //   232: iconst_0
    //   233: ireturn
    //   234: iinc 16 1
    //   237: goto -48 -> 189
    //   240: iload 5
    //   242: bipush 53
    //   244: if_icmpne +72 -> 316
    //   247: aload_0
    //   248: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   251: invokevirtual 112	com/jcraft/jsch/Buffer:getInt	()I
    //   254: pop
    //   255: aload_0
    //   256: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   259: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   262: pop
    //   263: aload_0
    //   264: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   267: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   270: pop
    //   271: aload_0
    //   272: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   275: invokevirtual 119	com/jcraft/jsch/Buffer:getString	()[B
    //   278: astore 9
    //   280: aload_0
    //   281: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   284: invokevirtual 119	com/jcraft/jsch/Buffer:getString	()[B
    //   287: pop
    //   288: aload 9
    //   290: invokestatic 127	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   293: astore 11
    //   295: aload_0
    //   296: getfield 131	com/jcraft/jsch/UserAuthGSSAPIWithMIC:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   299: ifnull -187 -> 112
    //   302: aload_0
    //   303: getfield 131	com/jcraft/jsch/UserAuthGSSAPIWithMIC:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   306: aload 11
    //   308: invokeinterface 137 2 0
    //   313: goto -201 -> 112
    //   316: iconst_0
    //   317: ireturn
    //   318: aload_1
    //   319: aload 18
    //   321: invokevirtual 141	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   324: invokestatic 147	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   327: invokevirtual 151	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   330: checkcast 153	com/jcraft/jsch/GSSContext
    //   333: checkcast 153	com/jcraft/jsch/GSSContext
    //   336: astore 20
    //   338: aload 20
    //   340: aload_0
    //   341: getfield 57	com/jcraft/jsch/UserAuthGSSAPIWithMIC:username	Ljava/lang/String;
    //   344: aload_1
    //   345: getfield 156	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   348: invokeinterface 160 3 0
    //   353: iconst_0
    //   354: newarray byte
    //   356: astore 22
    //   358: aload 20
    //   360: invokeinterface 164 1 0
    //   365: ifne +223 -> 588
    //   368: aload 22
    //   370: arraylength
    //   371: istore 31
    //   373: aload 20
    //   375: aload 22
    //   377: iconst_0
    //   378: iload 31
    //   380: invokeinterface 168 4 0
    //   385: astore 32
    //   387: aload 32
    //   389: astore 22
    //   391: aload 22
    //   393: ifnull +36 -> 429
    //   396: aload_0
    //   397: getfield 67	com/jcraft/jsch/UserAuthGSSAPIWithMIC:packet	Lcom/jcraft/jsch/Packet;
    //   400: invokevirtual 72	com/jcraft/jsch/Packet:reset	()V
    //   403: aload_0
    //   404: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   407: bipush 61
    //   409: invokevirtual 82	com/jcraft/jsch/Buffer:putByte	(B)V
    //   412: aload_0
    //   413: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   416: aload 22
    //   418: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   421: aload_1
    //   422: aload_0
    //   423: getfield 67	com/jcraft/jsch/UserAuthGSSAPIWithMIC:packet	Lcom/jcraft/jsch/Packet;
    //   426: invokevirtual 100	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   429: aload 20
    //   431: invokeinterface 164 1 0
    //   436: ifne -78 -> 358
    //   439: aload_0
    //   440: aload_1
    //   441: aload_0
    //   442: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   445: invokevirtual 104	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   448: putfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   451: sipush 255
    //   454: aload_0
    //   455: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   458: invokevirtual 108	com/jcraft/jsch/Buffer:getCommand	()B
    //   461: iand
    //   462: istore 33
    //   464: iload 33
    //   466: bipush 64
    //   468: if_icmpne +49 -> 517
    //   471: aload_0
    //   472: aload_1
    //   473: aload_0
    //   474: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   477: invokevirtual 104	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   480: putfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   483: sipush 255
    //   486: aload_0
    //   487: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   490: invokevirtual 108	com/jcraft/jsch/Buffer:getCommand	()B
    //   493: iand
    //   494: istore 33
    //   496: iload 33
    //   498: bipush 51
    //   500: if_icmpne +52 -> 552
    //   503: iconst_0
    //   504: ireturn
    //   505: astore 19
    //   507: iconst_0
    //   508: ireturn
    //   509: astore 21
    //   511: iconst_0
    //   512: ireturn
    //   513: astore 30
    //   515: iconst_0
    //   516: ireturn
    //   517: iload 33
    //   519: bipush 65
    //   521: if_icmpne -25 -> 496
    //   524: aload_0
    //   525: aload_1
    //   526: aload_0
    //   527: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   530: invokevirtual 104	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   533: putfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   536: sipush 255
    //   539: aload_0
    //   540: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   543: invokevirtual 108	com/jcraft/jsch/Buffer:getCommand	()B
    //   546: iand
    //   547: istore 33
    //   549: goto -53 -> 496
    //   552: aload_0
    //   553: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   556: invokevirtual 112	com/jcraft/jsch/Buffer:getInt	()I
    //   559: pop
    //   560: aload_0
    //   561: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   564: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   567: pop
    //   568: aload_0
    //   569: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   572: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   575: pop
    //   576: aload_0
    //   577: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   580: invokevirtual 119	com/jcraft/jsch/Buffer:getString	()[B
    //   583: astore 22
    //   585: goto -227 -> 358
    //   588: new 78	com/jcraft/jsch/Buffer
    //   591: dup
    //   592: invokespecial 169	com/jcraft/jsch/Buffer:<init>	()V
    //   595: astore 23
    //   597: aload 23
    //   599: aload_1
    //   600: invokevirtual 172	com/jcraft/jsch/Session:getSessionId	()[B
    //   603: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   606: aload 23
    //   608: bipush 50
    //   610: invokevirtual 82	com/jcraft/jsch/Buffer:putByte	(B)V
    //   613: aload 23
    //   615: aload_3
    //   616: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   619: aload 23
    //   621: ldc 88
    //   623: invokestatic 63	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   626: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   629: aload 23
    //   631: ldc 90
    //   633: invokestatic 63	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   636: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   639: aload 20
    //   641: aload 23
    //   643: getfield 175	com/jcraft/jsch/Buffer:buffer	[B
    //   646: iconst_0
    //   647: aload 23
    //   649: invokevirtual 178	com/jcraft/jsch/Buffer:getLength	()I
    //   652: invokeinterface 181 4 0
    //   657: astore 24
    //   659: aload 24
    //   661: ifnonnull +5 -> 666
    //   664: iconst_0
    //   665: ireturn
    //   666: aload_0
    //   667: getfield 67	com/jcraft/jsch/UserAuthGSSAPIWithMIC:packet	Lcom/jcraft/jsch/Packet;
    //   670: invokevirtual 72	com/jcraft/jsch/Packet:reset	()V
    //   673: aload_0
    //   674: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   677: bipush 66
    //   679: invokevirtual 82	com/jcraft/jsch/Buffer:putByte	(B)V
    //   682: aload_0
    //   683: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   686: aload 24
    //   688: invokevirtual 86	com/jcraft/jsch/Buffer:putString	([B)V
    //   691: aload_1
    //   692: aload_0
    //   693: getfield 67	com/jcraft/jsch/UserAuthGSSAPIWithMIC:packet	Lcom/jcraft/jsch/Packet;
    //   696: invokevirtual 100	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   699: aload 20
    //   701: invokeinterface 184 1 0
    //   706: aload_0
    //   707: aload_1
    //   708: aload_0
    //   709: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   712: invokevirtual 104	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   715: putfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   718: sipush 255
    //   721: aload_0
    //   722: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   725: invokevirtual 108	com/jcraft/jsch/Buffer:getCommand	()B
    //   728: iand
    //   729: istore 25
    //   731: iload 25
    //   733: bipush 52
    //   735: if_icmpne +5 -> 740
    //   738: iconst_1
    //   739: ireturn
    //   740: iload 25
    //   742: bipush 51
    //   744: if_icmpne +59 -> 803
    //   747: aload_0
    //   748: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   751: invokevirtual 112	com/jcraft/jsch/Buffer:getInt	()I
    //   754: pop
    //   755: aload_0
    //   756: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   759: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   762: pop
    //   763: aload_0
    //   764: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   767: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   770: pop
    //   771: aload_0
    //   772: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   775: invokevirtual 119	com/jcraft/jsch/Buffer:getString	()[B
    //   778: astore 29
    //   780: aload_0
    //   781: getfield 76	com/jcraft/jsch/UserAuthGSSAPIWithMIC:buf	Lcom/jcraft/jsch/Buffer;
    //   784: invokevirtual 115	com/jcraft/jsch/Buffer:getByte	()I
    //   787: ifeq +16 -> 803
    //   790: new 186	com/jcraft/jsch/JSchPartialAuthException
    //   793: dup
    //   794: aload 29
    //   796: invokestatic 127	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   799: invokespecial 188	com/jcraft/jsch/JSchPartialAuthException:<init>	(Ljava/lang/String;)V
    //   802: athrow
    //   803: iconst_0
    //   804: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   318	338	505	java/lang/Exception
    //   338	353	509	com/jcraft/jsch/JSchException
    //   368	387	513	com/jcraft/jsch/JSchException
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserAuthGSSAPIWithMIC
 * JD-Core Version:    0.6.2
 */