package com.jcraft.jsch;

import java.math.BigInteger;

public class KeyPairRSA extends KeyPair
{
  private static final byte[] begin = Util.str2byte("-----BEGIN RSA PRIVATE KEY-----");
  private static final byte[] end = Util.str2byte("-----END RSA PRIVATE KEY-----");
  private static final byte[] sshrsa = Util.str2byte("ssh-rsa");
  private byte[] c_array;
  private byte[] ep_array;
  private byte[] eq_array;
  private int key_size = 1024;
  private byte[] n_array;
  private byte[] p_array;
  private byte[] prv_array;
  private byte[] pub_array;
  private byte[] q_array;

  public KeyPairRSA(JSch paramJSch)
  {
    this(paramJSch, null, null, null);
  }

  public KeyPairRSA(JSch paramJSch, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    super(paramJSch);
    this.n_array = paramArrayOfByte1;
    this.pub_array = paramArrayOfByte2;
    this.prv_array = paramArrayOfByte3;
    if (paramArrayOfByte1 != null)
      this.key_size = new BigInteger(paramArrayOfByte1).bitLength();
  }

  static KeyPair fromSSHAgent(JSch paramJSch, Buffer paramBuffer)
    throws JSchException
  {
    byte[][] arrayOfByte = paramBuffer.getBytes(8, "invalid key format");
    KeyPairRSA localKeyPairRSA = new KeyPairRSA(paramJSch, arrayOfByte[1], arrayOfByte[2], arrayOfByte[3]);
    localKeyPairRSA.c_array = arrayOfByte[4];
    localKeyPairRSA.p_array = arrayOfByte[5];
    localKeyPairRSA.q_array = arrayOfByte[6];
    localKeyPairRSA.publicKeyComment = new String(arrayOfByte[7]);
    localKeyPairRSA.vendor = 0;
    return localKeyPairRSA;
  }

  private byte[] getCArray()
  {
    if (this.c_array == null)
      this.c_array = new BigInteger(this.q_array).modInverse(new BigInteger(this.p_array)).toByteArray();
    return this.c_array;
  }

  private byte[] getEPArray()
  {
    if (this.ep_array == null)
      this.ep_array = new BigInteger(this.prv_array).mod(new BigInteger(this.p_array).subtract(BigInteger.ONE)).toByteArray();
    return this.ep_array;
  }

  private byte[] getEQArray()
  {
    if (this.eq_array == null)
      this.eq_array = new BigInteger(this.prv_array).mod(new BigInteger(this.q_array).subtract(BigInteger.ONE)).toByteArray();
    return this.eq_array;
  }

  public void dispose()
  {
    super.dispose();
    Util.bzero(this.prv_array);
  }

  public byte[] forSSHAgent()
    throws JSchException
  {
    if (isEncrypted())
      throw new JSchException("key is encrypted.");
    Buffer localBuffer = new Buffer();
    localBuffer.putString(sshrsa);
    localBuffer.putString(this.n_array);
    localBuffer.putString(this.pub_array);
    localBuffer.putString(this.prv_array);
    localBuffer.putString(getCArray());
    localBuffer.putString(this.p_array);
    localBuffer.putString(this.q_array);
    localBuffer.putString(Util.str2byte(this.publicKeyComment));
    byte[] arrayOfByte = new byte[localBuffer.getLength()];
    localBuffer.getByte(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  void generate(int paramInt)
    throws JSchException
  {
    this.key_size = paramInt;
    try
    {
      KeyPairGenRSA localKeyPairGenRSA = (KeyPairGenRSA)Class.forName(JSch.getConfig("keypairgen.rsa")).newInstance();
      localKeyPairGenRSA.init(paramInt);
      this.pub_array = localKeyPairGenRSA.getE();
      this.prv_array = localKeyPairGenRSA.getD();
      this.n_array = localKeyPairGenRSA.getN();
      this.p_array = localKeyPairGenRSA.getP();
      this.q_array = localKeyPairGenRSA.getQ();
      this.ep_array = localKeyPairGenRSA.getEP();
      this.eq_array = localKeyPairGenRSA.getEQ();
      this.c_array = localKeyPairGenRSA.getC();
      return;
    }
    catch (Exception localException)
    {
      if ((localException instanceof Throwable))
        throw new JSchException(localException.toString(), localException);
      throw new JSchException(localException.toString());
    }
  }

  byte[] getBegin()
  {
    return begin;
  }

  byte[] getEnd()
  {
    return end;
  }

  public int getKeySize()
  {
    return this.key_size;
  }

  public int getKeyType()
  {
    return 2;
  }

  byte[] getKeyTypeName()
  {
    return sshrsa;
  }

  byte[] getPrivateKey()
  {
    int i = 1 + (1 + (1 + (1 + (1 + (1 + (1 + (1 + (1 + (1 + countLength(1))) + countLength(this.n_array.length) + this.n_array.length) + countLength(this.pub_array.length) + this.pub_array.length) + countLength(this.prv_array.length) + this.prv_array.length) + countLength(this.p_array.length) + this.p_array.length) + countLength(this.q_array.length) + this.q_array.length) + countLength(this.ep_array.length) + this.ep_array.length) + countLength(this.eq_array.length) + this.eq_array.length) + countLength(this.c_array.length) + this.c_array.length;
    byte[] arrayOfByte = new byte[i + (1 + countLength(i))];
    writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeSEQUENCE(arrayOfByte, 0, i), new byte[1]), this.n_array), this.pub_array), this.prv_array), this.p_array), this.q_array), this.ep_array), this.eq_array), this.c_array);
    return arrayOfByte;
  }

  public byte[] getPublicKeyBlob()
  {
    byte[] arrayOfByte = super.getPublicKeyBlob();
    if (arrayOfByte != null)
      return arrayOfByte;
    if (this.pub_array == null)
      return null;
    byte[][] arrayOfByte1 = new byte[3][];
    arrayOfByte1[0] = sshrsa;
    arrayOfByte1[1] = this.pub_array;
    arrayOfByte1[2] = this.n_array;
    return Buffer.fromBytes(arrayOfByte1).buffer;
  }

  public byte[] getSignature(byte[] paramArrayOfByte)
  {
    try
    {
      SignatureRSA localSignatureRSA = (SignatureRSA)Class.forName(JSch.getConfig("signature.rsa")).newInstance();
      localSignatureRSA.init();
      localSignatureRSA.setPrvKey(this.prv_array, this.n_array);
      localSignatureRSA.update(paramArrayOfByte);
      byte[] arrayOfByte1 = localSignatureRSA.sign();
      byte[][] arrayOfByte = new byte[2][];
      arrayOfByte[0] = sshrsa;
      arrayOfByte[1] = arrayOfByte1;
      byte[] arrayOfByte2 = Buffer.fromBytes(arrayOfByte).buffer;
      return arrayOfByte2;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public Signature getVerifier()
  {
    try
    {
      SignatureRSA localSignatureRSA = (SignatureRSA)Class.forName(JSch.getConfig("signature.rsa")).newInstance();
      localSignatureRSA.init();
      if ((this.pub_array == null) && (this.n_array == null) && (getPublicKeyBlob() != null))
      {
        Buffer localBuffer = new Buffer(getPublicKeyBlob());
        localBuffer.getString();
        this.pub_array = localBuffer.getString();
        this.n_array = localBuffer.getString();
      }
      localSignatureRSA.setPubKey(this.pub_array, this.n_array);
      return localSignatureRSA;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  // ERROR //
  boolean parse(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 90	com/jcraft/jsch/KeyPairRSA:vendor	I
    //   4: iconst_2
    //   5: if_icmpne +72 -> 77
    //   8: new 70	com/jcraft/jsch/Buffer
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 260	com/jcraft/jsch/Buffer:<init>	([B)V
    //   16: astore_3
    //   17: aload_3
    //   18: aload_1
    //   19: arraylength
    //   20: invokevirtual 271	com/jcraft/jsch/Buffer:skip	(I)V
    //   23: aload_3
    //   24: iconst_4
    //   25: ldc_w 273
    //   28: invokevirtual 74	com/jcraft/jsch/Buffer:getBytes	(ILjava/lang/String;)[[B
    //   31: astore 5
    //   33: aload_0
    //   34: aload 5
    //   36: iconst_0
    //   37: aaload
    //   38: putfield 53	com/jcraft/jsch/KeyPairRSA:prv_array	[B
    //   41: aload_0
    //   42: aload 5
    //   44: iconst_1
    //   45: aaload
    //   46: putfield 78	com/jcraft/jsch/KeyPairRSA:p_array	[B
    //   49: aload_0
    //   50: aload 5
    //   52: iconst_2
    //   53: aaload
    //   54: putfield 80	com/jcraft/jsch/KeyPairRSA:q_array	[B
    //   57: aload_0
    //   58: aload 5
    //   60: iconst_3
    //   61: aaload
    //   62: putfield 76	com/jcraft/jsch/KeyPairRSA:c_array	[B
    //   65: aload_0
    //   66: invokespecial 275	com/jcraft/jsch/KeyPairRSA:getEPArray	()[B
    //   69: pop
    //   70: aload_0
    //   71: invokespecial 277	com/jcraft/jsch/KeyPairRSA:getEQArray	()[B
    //   74: pop
    //   75: iconst_1
    //   76: ireturn
    //   77: aload_0
    //   78: getfield 90	com/jcraft/jsch/KeyPairRSA:vendor	I
    //   81: iconst_1
    //   82: if_icmpne +119 -> 201
    //   85: aload_1
    //   86: iconst_0
    //   87: baload
    //   88: bipush 48
    //   90: if_icmpeq +109 -> 199
    //   93: new 70	com/jcraft/jsch/Buffer
    //   96: dup
    //   97: aload_1
    //   98: invokespecial 260	com/jcraft/jsch/Buffer:<init>	([B)V
    //   101: astore 104
    //   103: aload_0
    //   104: aload 104
    //   106: invokevirtual 280	com/jcraft/jsch/Buffer:getMPIntBits	()[B
    //   109: putfield 51	com/jcraft/jsch/KeyPairRSA:pub_array	[B
    //   112: aload_0
    //   113: aload 104
    //   115: invokevirtual 280	com/jcraft/jsch/Buffer:getMPIntBits	()[B
    //   118: putfield 53	com/jcraft/jsch/KeyPairRSA:prv_array	[B
    //   121: aload_0
    //   122: aload 104
    //   124: invokevirtual 280	com/jcraft/jsch/Buffer:getMPIntBits	()[B
    //   127: putfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   130: aload 104
    //   132: invokevirtual 280	com/jcraft/jsch/Buffer:getMPIntBits	()[B
    //   135: pop
    //   136: aload_0
    //   137: aload 104
    //   139: invokevirtual 280	com/jcraft/jsch/Buffer:getMPIntBits	()[B
    //   142: putfield 78	com/jcraft/jsch/KeyPairRSA:p_array	[B
    //   145: aload_0
    //   146: aload 104
    //   148: invokevirtual 280	com/jcraft/jsch/Buffer:getMPIntBits	()[B
    //   151: putfield 80	com/jcraft/jsch/KeyPairRSA:q_array	[B
    //   154: aload_0
    //   155: getfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   158: ifnull +21 -> 179
    //   161: aload_0
    //   162: new 55	java/math/BigInteger
    //   165: dup
    //   166: aload_0
    //   167: getfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   170: invokespecial 58	java/math/BigInteger:<init>	([B)V
    //   173: invokevirtual 62	java/math/BigInteger:bitLength	()I
    //   176: putfield 47	com/jcraft/jsch/KeyPairRSA:key_size	I
    //   179: aload_0
    //   180: invokespecial 275	com/jcraft/jsch/KeyPairRSA:getEPArray	()[B
    //   183: pop
    //   184: aload_0
    //   185: invokespecial 277	com/jcraft/jsch/KeyPairRSA:getEQArray	()[B
    //   188: pop
    //   189: aload_0
    //   190: invokespecial 138	com/jcraft/jsch/KeyPairRSA:getCArray	()[B
    //   193: pop
    //   194: iconst_1
    //   195: ireturn
    //   196: astore_2
    //   197: iconst_0
    //   198: ireturn
    //   199: iconst_0
    //   200: ireturn
    //   201: iconst_0
    //   202: iconst_1
    //   203: iadd
    //   204: istore 8
    //   206: iload 8
    //   208: iconst_1
    //   209: iadd
    //   210: istore 9
    //   212: aload_1
    //   213: iload 8
    //   215: baload
    //   216: istore 12
    //   218: iload 12
    //   220: sipush 255
    //   223: iand
    //   224: istore 13
    //   226: iload 13
    //   228: sipush 128
    //   231: iand
    //   232: ifeq +65 -> 297
    //   235: iload 13
    //   237: bipush 127
    //   239: iand
    //   240: istore 98
    //   242: iconst_0
    //   243: istore 99
    //   245: iload 98
    //   247: istore 100
    //   249: iload 100
    //   251: iconst_1
    //   252: isub
    //   253: istore 101
    //   255: iload 100
    //   257: ifle +40 -> 297
    //   260: iload 99
    //   262: bipush 8
    //   264: ishl
    //   265: istore 102
    //   267: iload 9
    //   269: iconst_1
    //   270: iadd
    //   271: istore 103
    //   273: iload 102
    //   275: sipush 255
    //   278: aload_1
    //   279: iload 9
    //   281: baload
    //   282: iand
    //   283: iadd
    //   284: istore 99
    //   286: iload 101
    //   288: istore 100
    //   290: iload 103
    //   292: istore 9
    //   294: goto -45 -> 249
    //   297: iload 9
    //   299: istore 14
    //   301: aload_1
    //   302: iload 14
    //   304: baload
    //   305: istore 15
    //   307: iload 15
    //   309: iconst_2
    //   310: if_icmpeq +5 -> 315
    //   313: iconst_0
    //   314: ireturn
    //   315: iload 14
    //   317: iconst_1
    //   318: iadd
    //   319: istore 16
    //   321: iload 16
    //   323: iconst_1
    //   324: iadd
    //   325: istore 9
    //   327: aload_1
    //   328: iload 16
    //   330: baload
    //   331: istore 17
    //   333: iload 17
    //   335: sipush 255
    //   338: iand
    //   339: istore 18
    //   341: iload 18
    //   343: sipush 128
    //   346: iand
    //   347: ifeq +69 -> 416
    //   350: iload 18
    //   352: bipush 127
    //   354: iand
    //   355: istore 92
    //   357: iconst_0
    //   358: istore 18
    //   360: iload 92
    //   362: istore 93
    //   364: iload 93
    //   366: iconst_1
    //   367: isub
    //   368: istore 94
    //   370: iload 93
    //   372: ifle +44 -> 416
    //   375: iload 18
    //   377: bipush 8
    //   379: ishl
    //   380: istore 95
    //   382: iload 9
    //   384: iconst_1
    //   385: iadd
    //   386: istore 96
    //   388: aload_1
    //   389: iload 9
    //   391: baload
    //   392: istore 97
    //   394: iload 95
    //   396: iload 97
    //   398: sipush 255
    //   401: iand
    //   402: iadd
    //   403: istore 18
    //   405: iload 94
    //   407: istore 93
    //   409: iload 96
    //   411: istore 9
    //   413: goto -49 -> 364
    //   416: iconst_1
    //   417: iload 18
    //   419: iload 9
    //   421: iadd
    //   422: iadd
    //   423: istore 19
    //   425: iload 19
    //   427: iconst_1
    //   428: iadd
    //   429: istore 9
    //   431: aload_1
    //   432: iload 19
    //   434: baload
    //   435: istore 20
    //   437: iload 20
    //   439: sipush 255
    //   442: iand
    //   443: istore 21
    //   445: iload 21
    //   447: sipush 128
    //   450: iand
    //   451: ifeq +65 -> 516
    //   454: iload 21
    //   456: bipush 127
    //   458: iand
    //   459: istore 87
    //   461: iconst_0
    //   462: istore 21
    //   464: iload 87
    //   466: istore 88
    //   468: iload 88
    //   470: iconst_1
    //   471: isub
    //   472: istore 89
    //   474: iload 88
    //   476: ifle +40 -> 516
    //   479: iload 21
    //   481: bipush 8
    //   483: ishl
    //   484: istore 90
    //   486: iload 9
    //   488: iconst_1
    //   489: iadd
    //   490: istore 91
    //   492: iload 90
    //   494: sipush 255
    //   497: aload_1
    //   498: iload 9
    //   500: baload
    //   501: iand
    //   502: iadd
    //   503: istore 21
    //   505: iload 89
    //   507: istore 88
    //   509: iload 91
    //   511: istore 9
    //   513: goto -45 -> 468
    //   516: iload 9
    //   518: istore 22
    //   520: aload_0
    //   521: iload 21
    //   523: newarray byte
    //   525: putfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   528: aload_1
    //   529: iload 22
    //   531: aload_0
    //   532: getfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   535: iconst_0
    //   536: iload 21
    //   538: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   541: iconst_1
    //   542: iload 22
    //   544: iload 21
    //   546: iadd
    //   547: iadd
    //   548: istore 23
    //   550: iload 23
    //   552: iconst_1
    //   553: iadd
    //   554: istore 9
    //   556: aload_1
    //   557: iload 23
    //   559: baload
    //   560: istore 24
    //   562: iload 24
    //   564: sipush 255
    //   567: iand
    //   568: istore 25
    //   570: iload 25
    //   572: sipush 128
    //   575: iand
    //   576: ifeq +65 -> 641
    //   579: iload 25
    //   581: bipush 127
    //   583: iand
    //   584: istore 82
    //   586: iconst_0
    //   587: istore 25
    //   589: iload 82
    //   591: istore 83
    //   593: iload 83
    //   595: iconst_1
    //   596: isub
    //   597: istore 84
    //   599: iload 83
    //   601: ifle +40 -> 641
    //   604: iload 25
    //   606: bipush 8
    //   608: ishl
    //   609: istore 85
    //   611: iload 9
    //   613: iconst_1
    //   614: iadd
    //   615: istore 86
    //   617: iload 85
    //   619: sipush 255
    //   622: aload_1
    //   623: iload 9
    //   625: baload
    //   626: iand
    //   627: iadd
    //   628: istore 25
    //   630: iload 84
    //   632: istore 83
    //   634: iload 86
    //   636: istore 9
    //   638: goto -45 -> 593
    //   641: iload 9
    //   643: istore 26
    //   645: aload_0
    //   646: iload 25
    //   648: newarray byte
    //   650: putfield 51	com/jcraft/jsch/KeyPairRSA:pub_array	[B
    //   653: aload_1
    //   654: iload 26
    //   656: aload_0
    //   657: getfield 51	com/jcraft/jsch/KeyPairRSA:pub_array	[B
    //   660: iconst_0
    //   661: iload 25
    //   663: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   666: iconst_1
    //   667: iload 26
    //   669: iload 25
    //   671: iadd
    //   672: iadd
    //   673: istore 27
    //   675: iload 27
    //   677: iconst_1
    //   678: iadd
    //   679: istore 9
    //   681: aload_1
    //   682: iload 27
    //   684: baload
    //   685: istore 28
    //   687: iload 28
    //   689: sipush 255
    //   692: iand
    //   693: istore 29
    //   695: iload 29
    //   697: sipush 128
    //   700: iand
    //   701: ifeq +65 -> 766
    //   704: iload 29
    //   706: bipush 127
    //   708: iand
    //   709: istore 77
    //   711: iconst_0
    //   712: istore 29
    //   714: iload 77
    //   716: istore 78
    //   718: iload 78
    //   720: iconst_1
    //   721: isub
    //   722: istore 79
    //   724: iload 78
    //   726: ifle +40 -> 766
    //   729: iload 29
    //   731: bipush 8
    //   733: ishl
    //   734: istore 80
    //   736: iload 9
    //   738: iconst_1
    //   739: iadd
    //   740: istore 81
    //   742: iload 80
    //   744: sipush 255
    //   747: aload_1
    //   748: iload 9
    //   750: baload
    //   751: iand
    //   752: iadd
    //   753: istore 29
    //   755: iload 79
    //   757: istore 78
    //   759: iload 81
    //   761: istore 9
    //   763: goto -45 -> 718
    //   766: iload 9
    //   768: istore 30
    //   770: aload_0
    //   771: iload 29
    //   773: newarray byte
    //   775: putfield 53	com/jcraft/jsch/KeyPairRSA:prv_array	[B
    //   778: aload_1
    //   779: iload 30
    //   781: aload_0
    //   782: getfield 53	com/jcraft/jsch/KeyPairRSA:prv_array	[B
    //   785: iconst_0
    //   786: iload 29
    //   788: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   791: iconst_1
    //   792: iload 30
    //   794: iload 29
    //   796: iadd
    //   797: iadd
    //   798: istore 31
    //   800: iload 31
    //   802: iconst_1
    //   803: iadd
    //   804: istore 9
    //   806: aload_1
    //   807: iload 31
    //   809: baload
    //   810: istore 32
    //   812: iload 32
    //   814: sipush 255
    //   817: iand
    //   818: istore 33
    //   820: iload 33
    //   822: sipush 128
    //   825: iand
    //   826: ifeq +65 -> 891
    //   829: iload 33
    //   831: bipush 127
    //   833: iand
    //   834: istore 72
    //   836: iconst_0
    //   837: istore 33
    //   839: iload 72
    //   841: istore 73
    //   843: iload 73
    //   845: iconst_1
    //   846: isub
    //   847: istore 74
    //   849: iload 73
    //   851: ifle +40 -> 891
    //   854: iload 33
    //   856: bipush 8
    //   858: ishl
    //   859: istore 75
    //   861: iload 9
    //   863: iconst_1
    //   864: iadd
    //   865: istore 76
    //   867: iload 75
    //   869: sipush 255
    //   872: aload_1
    //   873: iload 9
    //   875: baload
    //   876: iand
    //   877: iadd
    //   878: istore 33
    //   880: iload 74
    //   882: istore 73
    //   884: iload 76
    //   886: istore 9
    //   888: goto -45 -> 843
    //   891: iload 9
    //   893: istore 34
    //   895: aload_0
    //   896: iload 33
    //   898: newarray byte
    //   900: putfield 78	com/jcraft/jsch/KeyPairRSA:p_array	[B
    //   903: aload_1
    //   904: iload 34
    //   906: aload_0
    //   907: getfield 78	com/jcraft/jsch/KeyPairRSA:p_array	[B
    //   910: iconst_0
    //   911: iload 33
    //   913: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   916: iconst_1
    //   917: iload 34
    //   919: iload 33
    //   921: iadd
    //   922: iadd
    //   923: istore 35
    //   925: iload 35
    //   927: iconst_1
    //   928: iadd
    //   929: istore 9
    //   931: aload_1
    //   932: iload 35
    //   934: baload
    //   935: istore 36
    //   937: iload 36
    //   939: sipush 255
    //   942: iand
    //   943: istore 37
    //   945: iload 37
    //   947: sipush 128
    //   950: iand
    //   951: ifeq +65 -> 1016
    //   954: iload 37
    //   956: bipush 127
    //   958: iand
    //   959: istore 67
    //   961: iconst_0
    //   962: istore 37
    //   964: iload 67
    //   966: istore 68
    //   968: iload 68
    //   970: iconst_1
    //   971: isub
    //   972: istore 69
    //   974: iload 68
    //   976: ifle +40 -> 1016
    //   979: iload 37
    //   981: bipush 8
    //   983: ishl
    //   984: istore 70
    //   986: iload 9
    //   988: iconst_1
    //   989: iadd
    //   990: istore 71
    //   992: iload 70
    //   994: sipush 255
    //   997: aload_1
    //   998: iload 9
    //   1000: baload
    //   1001: iand
    //   1002: iadd
    //   1003: istore 37
    //   1005: iload 69
    //   1007: istore 68
    //   1009: iload 71
    //   1011: istore 9
    //   1013: goto -45 -> 968
    //   1016: iload 9
    //   1018: istore 38
    //   1020: aload_0
    //   1021: iload 37
    //   1023: newarray byte
    //   1025: putfield 80	com/jcraft/jsch/KeyPairRSA:q_array	[B
    //   1028: aload_1
    //   1029: iload 38
    //   1031: aload_0
    //   1032: getfield 80	com/jcraft/jsch/KeyPairRSA:q_array	[B
    //   1035: iconst_0
    //   1036: iload 37
    //   1038: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1041: iconst_1
    //   1042: iload 38
    //   1044: iload 37
    //   1046: iadd
    //   1047: iadd
    //   1048: istore 39
    //   1050: iload 39
    //   1052: iconst_1
    //   1053: iadd
    //   1054: istore 9
    //   1056: aload_1
    //   1057: iload 39
    //   1059: baload
    //   1060: istore 40
    //   1062: iload 40
    //   1064: sipush 255
    //   1067: iand
    //   1068: istore 41
    //   1070: iload 41
    //   1072: sipush 128
    //   1075: iand
    //   1076: ifeq +65 -> 1141
    //   1079: iload 41
    //   1081: bipush 127
    //   1083: iand
    //   1084: istore 62
    //   1086: iconst_0
    //   1087: istore 41
    //   1089: iload 62
    //   1091: istore 63
    //   1093: iload 63
    //   1095: iconst_1
    //   1096: isub
    //   1097: istore 64
    //   1099: iload 63
    //   1101: ifle +40 -> 1141
    //   1104: iload 41
    //   1106: bipush 8
    //   1108: ishl
    //   1109: istore 65
    //   1111: iload 9
    //   1113: iconst_1
    //   1114: iadd
    //   1115: istore 66
    //   1117: iload 65
    //   1119: sipush 255
    //   1122: aload_1
    //   1123: iload 9
    //   1125: baload
    //   1126: iand
    //   1127: iadd
    //   1128: istore 41
    //   1130: iload 64
    //   1132: istore 63
    //   1134: iload 66
    //   1136: istore 9
    //   1138: goto -45 -> 1093
    //   1141: iload 9
    //   1143: istore 42
    //   1145: aload_0
    //   1146: iload 41
    //   1148: newarray byte
    //   1150: putfield 102	com/jcraft/jsch/KeyPairRSA:ep_array	[B
    //   1153: aload_1
    //   1154: iload 42
    //   1156: aload_0
    //   1157: getfield 102	com/jcraft/jsch/KeyPairRSA:ep_array	[B
    //   1160: iconst_0
    //   1161: iload 41
    //   1163: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1166: iconst_1
    //   1167: iload 42
    //   1169: iload 41
    //   1171: iadd
    //   1172: iadd
    //   1173: istore 43
    //   1175: iload 43
    //   1177: iconst_1
    //   1178: iadd
    //   1179: istore 9
    //   1181: aload_1
    //   1182: iload 43
    //   1184: baload
    //   1185: istore 44
    //   1187: iload 44
    //   1189: sipush 255
    //   1192: iand
    //   1193: istore 45
    //   1195: iload 45
    //   1197: sipush 128
    //   1200: iand
    //   1201: ifeq +65 -> 1266
    //   1204: iload 45
    //   1206: bipush 127
    //   1208: iand
    //   1209: istore 57
    //   1211: iconst_0
    //   1212: istore 45
    //   1214: iload 57
    //   1216: istore 58
    //   1218: iload 58
    //   1220: iconst_1
    //   1221: isub
    //   1222: istore 59
    //   1224: iload 58
    //   1226: ifle +40 -> 1266
    //   1229: iload 45
    //   1231: bipush 8
    //   1233: ishl
    //   1234: istore 60
    //   1236: iload 9
    //   1238: iconst_1
    //   1239: iadd
    //   1240: istore 61
    //   1242: iload 60
    //   1244: sipush 255
    //   1247: aload_1
    //   1248: iload 9
    //   1250: baload
    //   1251: iand
    //   1252: iadd
    //   1253: istore 45
    //   1255: iload 59
    //   1257: istore 58
    //   1259: iload 61
    //   1261: istore 9
    //   1263: goto -45 -> 1218
    //   1266: iload 9
    //   1268: istore 46
    //   1270: aload_0
    //   1271: iload 45
    //   1273: newarray byte
    //   1275: putfield 115	com/jcraft/jsch/KeyPairRSA:eq_array	[B
    //   1278: aload_1
    //   1279: iload 46
    //   1281: aload_0
    //   1282: getfield 115	com/jcraft/jsch/KeyPairRSA:eq_array	[B
    //   1285: iconst_0
    //   1286: iload 45
    //   1288: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1291: iconst_1
    //   1292: iload 46
    //   1294: iload 45
    //   1296: iadd
    //   1297: iadd
    //   1298: istore 47
    //   1300: iload 47
    //   1302: iconst_1
    //   1303: iadd
    //   1304: istore 9
    //   1306: aload_1
    //   1307: iload 47
    //   1309: baload
    //   1310: istore 48
    //   1312: iload 48
    //   1314: sipush 255
    //   1317: iand
    //   1318: istore 49
    //   1320: iload 49
    //   1322: sipush 128
    //   1325: iand
    //   1326: ifeq +65 -> 1391
    //   1329: iload 49
    //   1331: bipush 127
    //   1333: iand
    //   1334: istore 52
    //   1336: iconst_0
    //   1337: istore 49
    //   1339: iload 52
    //   1341: istore 53
    //   1343: iload 53
    //   1345: iconst_1
    //   1346: isub
    //   1347: istore 54
    //   1349: iload 53
    //   1351: ifle +40 -> 1391
    //   1354: iload 49
    //   1356: bipush 8
    //   1358: ishl
    //   1359: istore 55
    //   1361: iload 9
    //   1363: iconst_1
    //   1364: iadd
    //   1365: istore 56
    //   1367: iload 55
    //   1369: sipush 255
    //   1372: aload_1
    //   1373: iload 9
    //   1375: baload
    //   1376: iand
    //   1377: iadd
    //   1378: istore 49
    //   1380: iload 54
    //   1382: istore 53
    //   1384: iload 56
    //   1386: istore 9
    //   1388: goto -45 -> 1343
    //   1391: iload 9
    //   1393: istore 50
    //   1395: aload_0
    //   1396: iload 49
    //   1398: newarray byte
    //   1400: putfield 76	com/jcraft/jsch/KeyPairRSA:c_array	[B
    //   1403: aload_1
    //   1404: iload 50
    //   1406: aload_0
    //   1407: getfield 76	com/jcraft/jsch/KeyPairRSA:c_array	[B
    //   1410: iconst_0
    //   1411: iload 49
    //   1413: invokestatic 286	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   1416: iload 50
    //   1418: iload 49
    //   1420: iadd
    //   1421: pop
    //   1422: aload_0
    //   1423: getfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   1426: ifnull +31 -> 1457
    //   1429: aload_0
    //   1430: new 55	java/math/BigInteger
    //   1433: dup
    //   1434: aload_0
    //   1435: getfield 49	com/jcraft/jsch/KeyPairRSA:n_array	[B
    //   1438: invokespecial 58	java/math/BigInteger:<init>	([B)V
    //   1441: invokevirtual 62	java/math/BigInteger:bitLength	()I
    //   1444: putfield 47	com/jcraft/jsch/KeyPairRSA:key_size	I
    //   1447: iconst_1
    //   1448: ireturn
    //   1449: astore 10
    //   1451: iload 9
    //   1453: pop
    //   1454: goto -1257 -> 197
    //   1457: iconst_1
    //   1458: ireturn
    //   1459: astore 4
    //   1461: iconst_0
    //   1462: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	23	196	java/lang/Exception
    //   23	65	196	java/lang/Exception
    //   65	75	196	java/lang/Exception
    //   77	179	196	java/lang/Exception
    //   179	194	196	java/lang/Exception
    //   273	286	196	java/lang/Exception
    //   301	307	196	java/lang/Exception
    //   388	394	196	java/lang/Exception
    //   492	505	196	java/lang/Exception
    //   520	541	196	java/lang/Exception
    //   617	630	196	java/lang/Exception
    //   645	666	196	java/lang/Exception
    //   742	755	196	java/lang/Exception
    //   770	791	196	java/lang/Exception
    //   867	880	196	java/lang/Exception
    //   895	916	196	java/lang/Exception
    //   992	1005	196	java/lang/Exception
    //   1020	1041	196	java/lang/Exception
    //   1117	1130	196	java/lang/Exception
    //   1145	1166	196	java/lang/Exception
    //   1242	1255	196	java/lang/Exception
    //   1270	1291	196	java/lang/Exception
    //   1367	1380	196	java/lang/Exception
    //   1395	1416	196	java/lang/Exception
    //   1422	1447	196	java/lang/Exception
    //   212	218	1449	java/lang/Exception
    //   327	333	1449	java/lang/Exception
    //   431	437	1449	java/lang/Exception
    //   556	562	1449	java/lang/Exception
    //   681	687	1449	java/lang/Exception
    //   806	812	1449	java/lang/Exception
    //   931	937	1449	java/lang/Exception
    //   1056	1062	1449	java/lang/Exception
    //   1181	1187	1449	java/lang/Exception
    //   1306	1312	1449	java/lang/Exception
    //   23	65	1459	com/jcraft/jsch/JSchException
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.KeyPairRSA
 * JD-Core Version:    0.6.2
 */