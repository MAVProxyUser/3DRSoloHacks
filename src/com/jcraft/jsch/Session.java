package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class Session
  implements Runnable
{
  private static final int PACKET_MAX_SIZE = 262144;
  static final int SSH_MSG_CHANNEL_CLOSE = 97;
  static final int SSH_MSG_CHANNEL_DATA = 94;
  static final int SSH_MSG_CHANNEL_EOF = 96;
  static final int SSH_MSG_CHANNEL_EXTENDED_DATA = 95;
  static final int SSH_MSG_CHANNEL_FAILURE = 100;
  static final int SSH_MSG_CHANNEL_OPEN = 90;
  static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
  static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
  static final int SSH_MSG_CHANNEL_REQUEST = 98;
  static final int SSH_MSG_CHANNEL_SUCCESS = 99;
  static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
  static final int SSH_MSG_DEBUG = 4;
  static final int SSH_MSG_DISCONNECT = 1;
  static final int SSH_MSG_GLOBAL_REQUEST = 80;
  static final int SSH_MSG_IGNORE = 2;
  static final int SSH_MSG_KEXDH_INIT = 30;
  static final int SSH_MSG_KEXDH_REPLY = 31;
  static final int SSH_MSG_KEXINIT = 20;
  static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
  static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
  static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
  static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
  static final int SSH_MSG_NEWKEYS = 21;
  static final int SSH_MSG_REQUEST_FAILURE = 82;
  static final int SSH_MSG_REQUEST_SUCCESS = 81;
  static final int SSH_MSG_SERVICE_ACCEPT = 6;
  static final int SSH_MSG_SERVICE_REQUEST = 5;
  static final int SSH_MSG_UNIMPLEMENTED = 3;
  static final int buffer_margin = 84;
  private static final byte[] keepalivemsg = Util.str2byte("keepalive@jcraft.com");
  private static final byte[] nomoresessions = Util.str2byte("no-more-sessions@openssh.com");
  static Random random;
  private byte[] Ec2s;
  private byte[] Es2c;
  private byte[] IVc2s;
  private byte[] IVs2c;
  private byte[] I_C;
  private byte[] I_S;
  private byte[] K_S;
  private byte[] MACc2s;
  private byte[] MACs2c;
  private byte[] V_C = Util.str2byte("SSH-2.0-JSCH-0.1.51");
  private byte[] V_S;
  boolean agent_forwarding = false;
  int auth_failures = 0;
  Buffer buf;
  private Cipher c2scipher;
  private int c2scipher_size = 8;
  private MAC c2smac;
  int[] compress_len = new int[1];
  private Hashtable config = null;
  private Thread connectThread = null;
  protected boolean daemon_thread = false;
  private Compression deflater;
  private GlobalRequestReply grr = new GlobalRequestReply(null);
  String[] guess = null;
  String host = "127.0.0.1";
  private String hostKeyAlias = null;
  private HostKey hostkey = null;
  private HostKeyRepository hostkeyRepository = null;
  private IdentityRepository identityRepository = null;
  InputStream in = null;
  private boolean in_kex = false;
  private Compression inflater;
  private IO io;
  private boolean isAuthed = false;
  private volatile boolean isConnected = false;
  JSch jsch;
  private long kex_start_time = 0L;
  private Object lock = new Object();
  int max_auth_tries = 6;
  String org_host = "127.0.0.1";
  OutputStream out = null;
  Packet packet;
  byte[] password = null;
  int port = 22;
  private Proxy proxy = null;
  private Cipher s2ccipher;
  private int s2ccipher_size = 8;
  private MAC s2cmac;
  private byte[] s2cmac_result1;
  private byte[] s2cmac_result2;
  private int seqi = 0;
  private int seqo = 0;
  private int serverAliveCountMax = 1;
  private int serverAliveInterval = 0;
  private byte[] session_id;
  private Socket socket;
  SocketFactory socket_factory = null;
  Runnable thread;
  private int timeout = 0;
  int[] uncompress_len = new int[1];
  private UserInfo userinfo;
  String username = null;
  boolean x11_forwarding = false;

  Session(JSch paramJSch, String paramString1, String paramString2, int paramInt)
    throws JSchException
  {
    this.jsch = paramJSch;
    this.buf = new Buffer();
    this.packet = new Packet(this.buf);
    this.username = paramString1;
    this.host = paramString2;
    this.org_host = paramString2;
    this.port = paramInt;
    applyConfig();
    if (this.username == null);
    try
    {
      this.username = ((String)System.getProperties().get("user.name"));
      label299: if (this.username == null)
        throw new JSchException("username is not given.");
      return;
    }
    catch (SecurityException localSecurityException)
    {
      break label299;
    }
  }

  // ERROR //
  private int _setPortForwardingR(String paramString, int paramInt)
    throws JSchException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: new 271	com/jcraft/jsch/Buffer
    //   10: dup
    //   11: bipush 100
    //   13: invokespecial 312	com/jcraft/jsch/Buffer:<init>	(I)V
    //   16: astore 4
    //   18: new 276	com/jcraft/jsch/Packet
    //   21: dup
    //   22: aload 4
    //   24: invokespecial 279	com/jcraft/jsch/Packet:<init>	(Lcom/jcraft/jsch/Buffer;)V
    //   27: astore 5
    //   29: aload_1
    //   30: invokestatic 318	com/jcraft/jsch/ChannelForwardedTCPIP:normalize	(Ljava/lang/String;)Ljava/lang/String;
    //   33: astore 7
    //   35: aload_0
    //   36: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   39: invokestatic 324	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   42: invokevirtual 328	com/jcraft/jsch/Session$GlobalRequestReply:setThread	(Ljava/lang/Thread;)V
    //   45: aload_0
    //   46: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   49: iload_2
    //   50: invokevirtual 331	com/jcraft/jsch/Session$GlobalRequestReply:setPort	(I)V
    //   53: aload 5
    //   55: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   58: aload 4
    //   60: bipush 80
    //   62: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   65: aload 4
    //   67: ldc_w 340
    //   70: invokestatic 170	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   73: invokevirtual 344	com/jcraft/jsch/Buffer:putString	([B)V
    //   76: aload 4
    //   78: iconst_1
    //   79: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   82: aload 4
    //   84: aload 7
    //   86: invokestatic 170	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   89: invokevirtual 344	com/jcraft/jsch/Buffer:putString	([B)V
    //   92: aload 4
    //   94: iload_2
    //   95: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   98: aload_0
    //   99: aload 5
    //   101: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   104: iconst_0
    //   105: istore 9
    //   107: aload_0
    //   108: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   111: invokevirtual 355	com/jcraft/jsch/Session$GlobalRequestReply:getReply	()I
    //   114: istore 10
    //   116: iload 10
    //   118: istore 11
    //   120: iload 9
    //   122: bipush 10
    //   124: if_icmpge +83 -> 207
    //   127: iload 11
    //   129: iconst_m1
    //   130: if_icmpne +77 -> 207
    //   133: ldc2_w 356
    //   136: invokestatic 361	java/lang/Thread:sleep	(J)V
    //   139: iinc 9 1
    //   142: aload_0
    //   143: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   146: invokevirtual 355	com/jcraft/jsch/Session$GlobalRequestReply:getReply	()I
    //   149: istore 11
    //   151: goto -31 -> 120
    //   154: astore 8
    //   156: aload_0
    //   157: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   160: aconst_null
    //   161: invokevirtual 328	com/jcraft/jsch/Session$GlobalRequestReply:setThread	(Ljava/lang/Thread;)V
    //   164: aload 8
    //   166: instanceof 363
    //   169: ifeq +25 -> 194
    //   172: new 180	com/jcraft/jsch/JSchException
    //   175: dup
    //   176: aload 8
    //   178: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   181: aload 8
    //   183: invokespecial 370	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   186: athrow
    //   187: astore 6
    //   189: aload_3
    //   190: monitorexit
    //   191: aload 6
    //   193: athrow
    //   194: new 180	com/jcraft/jsch/JSchException
    //   197: dup
    //   198: aload 8
    //   200: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   203: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   206: athrow
    //   207: aload_0
    //   208: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   211: aconst_null
    //   212: invokevirtual 328	com/jcraft/jsch/Session$GlobalRequestReply:setThread	(Ljava/lang/Thread;)V
    //   215: iload 11
    //   217: iconst_1
    //   218: if_icmpeq +31 -> 249
    //   221: new 180	com/jcraft/jsch/JSchException
    //   224: dup
    //   225: new 372	java/lang/StringBuilder
    //   228: dup
    //   229: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   232: ldc_w 375
    //   235: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: iload_2
    //   239: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   242: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   245: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   248: athrow
    //   249: aload_0
    //   250: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   253: invokevirtual 386	com/jcraft/jsch/Session$GlobalRequestReply:getPort	()I
    //   256: istore 12
    //   258: aload_3
    //   259: monitorexit
    //   260: iload 12
    //   262: ireturn
    //   263: astore 13
    //   265: goto -126 -> 139
    //
    // Exception table:
    //   from	to	target	type
    //   53	104	154	java/lang/Exception
    //   7	53	187	finally
    //   53	104	187	finally
    //   107	116	187	finally
    //   133	139	187	finally
    //   142	151	187	finally
    //   156	187	187	finally
    //   189	191	187	finally
    //   194	207	187	finally
    //   207	215	187	finally
    //   221	249	187	finally
    //   249	260	187	finally
    //   133	139	263	java/lang/Exception
  }

  private void _write(Packet paramPacket)
    throws Exception
  {
    synchronized (this.lock)
    {
      encode(paramPacket);
      if (this.io != null)
      {
        this.io.put(paramPacket);
        this.seqo = (1 + this.seqo);
      }
      return;
    }
  }

  private void applyConfig()
    throws JSchException
  {
    ConfigRepository localConfigRepository = this.jsch.getConfigRepository();
    if (localConfigRepository == null);
    while (true)
    {
      return;
      ConfigRepository.Config localConfig = localConfigRepository.getConfig(this.org_host);
      String str1 = localConfig.getUser();
      if (str1 != null)
        this.username = str1;
      String str2 = localConfig.getHostname();
      if (str2 != null)
        this.host = str2;
      int i = localConfig.getPort();
      if (i != -1)
        this.port = i;
      checkConfig(localConfig, "kex");
      checkConfig(localConfig, "server_host_key");
      checkConfig(localConfig, "cipher.c2s");
      checkConfig(localConfig, "cipher.s2c");
      checkConfig(localConfig, "mac.c2s");
      checkConfig(localConfig, "mac.s2c");
      checkConfig(localConfig, "compression.c2s");
      checkConfig(localConfig, "compression.s2c");
      checkConfig(localConfig, "compression_level");
      checkConfig(localConfig, "StrictHostKeyChecking");
      checkConfig(localConfig, "HashKnownHosts");
      checkConfig(localConfig, "PreferredAuthentications");
      checkConfig(localConfig, "MaxAuthTries");
      checkConfig(localConfig, "ClearAllForwardings");
      String str3 = localConfig.getValue("HostKeyAlias");
      if (str3 != null)
        setHostKeyAlias(str3);
      String str4 = localConfig.getValue("UserKnownHostsFile");
      if (str4 != null)
      {
        KnownHosts localKnownHosts = new KnownHosts(this.jsch);
        localKnownHosts.setKnownHosts(str4);
        setHostKeyRepository(localKnownHosts);
      }
      String[] arrayOfString1 = localConfig.getValues("IdentityFile");
      if (arrayOfString1 != null)
      {
        String[] arrayOfString2 = localConfigRepository.getConfig("").getValues("IdentityFile");
        if (arrayOfString2 != null)
          for (int m = 0; m < arrayOfString2.length; m++)
            this.jsch.addIdentity(arrayOfString2[m]);
        arrayOfString2 = new String[0];
        if (arrayOfString1.length - arrayOfString2.length > 0)
        {
          IdentityRepository.Wrapper localWrapper = new IdentityRepository.Wrapper(this.jsch.getIdentityRepository(), true);
          int j = 0;
          if (j < arrayOfString1.length)
          {
            String str9 = arrayOfString1[j];
            int k = 0;
            while (k < arrayOfString2.length)
              if (!str9.equals(arrayOfString2[k]))
                k++;
              else
                str9 = null;
            if (str9 == null);
            while (true)
            {
              j++;
              break;
              localWrapper.add(IdentityFile.newInstance(str9, null, this.jsch));
            }
          }
          setIdentityRepository(localWrapper);
        }
      }
      String str5 = localConfig.getValue("ServerAliveInterval");
      if (str5 != null);
      try
      {
        setServerAliveInterval(Integer.parseInt(str5));
        label468: String str6 = localConfig.getValue("ConnectTimeout");
        if (str6 != null);
        try
        {
          setTimeout(Integer.parseInt(str6));
          label493: String str7 = localConfig.getValue("MaxAuthTries");
          if (str7 != null)
            setConfig("MaxAuthTries", str7);
          String str8 = localConfig.getValue("ClearAllForwardings");
          if (str8 == null)
            continue;
          setConfig("ClearAllForwardings", str8);
          return;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          break label493;
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        break label468;
      }
    }
  }

  private void applyConfigChannel(ChannelSession paramChannelSession)
    throws JSchException
  {
    ConfigRepository localConfigRepository = this.jsch.getConfigRepository();
    if (localConfigRepository == null);
    String str2;
    do
    {
      return;
      ConfigRepository.Config localConfig = localConfigRepository.getConfig(this.org_host);
      String str1 = localConfig.getValue("ForwardAgent");
      if (str1 != null)
        paramChannelSession.setAgentForwarding(str1.equals("yes"));
      str2 = localConfig.getValue("RequestTTY");
    }
    while (str2 == null);
    paramChannelSession.setPty(str2.equals("yes"));
  }

  static boolean checkCipher(String paramString)
  {
    try
    {
      Cipher localCipher = (Cipher)Class.forName(paramString).newInstance();
      localCipher.init(0, new byte[localCipher.getBlockSize()], new byte[localCipher.getIVSize()]);
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private String[] checkCiphers(String paramString)
  {
    String[] arrayOfString1 = null;
    if (paramString != null)
    {
      int i = paramString.length();
      arrayOfString1 = null;
      if (i != 0)
        break label19;
    }
    while (true)
    {
      return arrayOfString1;
      label19: if (JSch.getLogger().isEnabled(1))
        JSch.getLogger().log(1, "CheckCiphers: " + paramString);
      String str1 = getConfig("cipher.c2s");
      String str2 = getConfig("cipher.s2c");
      Vector localVector = new Vector();
      String[] arrayOfString2 = Util.split(paramString, ",");
      int j = 0;
      if (j < arrayOfString2.length)
      {
        String str3 = arrayOfString2[j];
        if ((str2.indexOf(str3) == -1) && (str1.indexOf(str3) == -1));
        while (true)
        {
          j++;
          break;
          if (!checkCipher(getConfig(str3)))
            localVector.addElement(str3);
        }
      }
      int k = localVector.size();
      arrayOfString1 = null;
      if (k != 0)
      {
        arrayOfString1 = new String[localVector.size()];
        System.arraycopy(localVector.toArray(), 0, arrayOfString1, 0, localVector.size());
        if (JSch.getLogger().isEnabled(1))
          for (int m = 0; m < arrayOfString1.length; m++)
            JSch.getLogger().log(1, arrayOfString1[m] + " is not available.");
      }
    }
  }

  private void checkConfig(ConfigRepository.Config paramConfig, String paramString)
  {
    String str = paramConfig.getValue(paramString);
    if (str != null)
      setConfig(paramString, str);
  }

  // ERROR //
  private void checkHost(String paramString, int paramInt, KeyExchange paramKeyExchange)
    throws JSchException
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 444
    //   4: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   7: astore 4
    //   9: aload_0
    //   10: getfield 220	com/jcraft/jsch/Session:hostKeyAlias	Ljava/lang/String;
    //   13: ifnull +8 -> 21
    //   16: aload_0
    //   17: getfield 220	com/jcraft/jsch/Session:hostKeyAlias	Ljava/lang/String;
    //   20: astore_1
    //   21: aload_3
    //   22: invokevirtual 633	com/jcraft/jsch/KeyExchange:getHostKey	()[B
    //   25: astore 5
    //   27: aload_3
    //   28: invokevirtual 636	com/jcraft/jsch/KeyExchange:getKeyType	()Ljava/lang/String;
    //   31: astore 6
    //   33: aload_3
    //   34: invokevirtual 639	com/jcraft/jsch/KeyExchange:getFingerPrint	()Ljava/lang/String;
    //   37: astore 7
    //   39: aload_0
    //   40: getfield 220	com/jcraft/jsch/Session:hostKeyAlias	Ljava/lang/String;
    //   43: ifnonnull +40 -> 83
    //   46: iload_2
    //   47: bipush 22
    //   49: if_icmpeq +34 -> 83
    //   52: new 372	java/lang/StringBuilder
    //   55: dup
    //   56: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   59: ldc_w 641
    //   62: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: aload_1
    //   66: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: ldc_w 643
    //   72: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: iload_2
    //   76: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   79: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: astore_1
    //   83: aload_0
    //   84: invokevirtual 647	com/jcraft/jsch/Session:getHostKeyRepository	()Lcom/jcraft/jsch/HostKeyRepository;
    //   87: astore 8
    //   89: aload_0
    //   90: ldc_w 446
    //   93: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   96: ldc_w 538
    //   99: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   102: ifeq +277 -> 379
    //   105: aload 8
    //   107: instanceof 464
    //   110: ifeq +269 -> 379
    //   113: aload_0
    //   114: aload 8
    //   116: checkcast 464	com/jcraft/jsch/KnownHosts
    //   119: aload_1
    //   120: aload 5
    //   122: invokevirtual 651	com/jcraft/jsch/KnownHosts:createHashedHostKey	(Ljava/lang/String;[B)Lcom/jcraft/jsch/HostKey;
    //   125: putfield 267	com/jcraft/jsch/Session:hostkey	Lcom/jcraft/jsch/HostKey;
    //   128: aload 8
    //   130: monitorenter
    //   131: aload 8
    //   133: aload_1
    //   134: aload 5
    //   136: invokeinterface 657 3 0
    //   141: istore 11
    //   143: aload 8
    //   145: monitorexit
    //   146: aload 4
    //   148: ldc_w 659
    //   151: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   154: ifne +21 -> 175
    //   157: aload 4
    //   159: ldc_w 538
    //   162: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   165: istore 25
    //   167: iconst_0
    //   168: istore 12
    //   170: iload 25
    //   172: ifeq +297 -> 469
    //   175: iconst_0
    //   176: istore 12
    //   178: iload 11
    //   180: iconst_2
    //   181: if_icmpne +288 -> 469
    //   184: aload 8
    //   186: monitorenter
    //   187: aload 8
    //   189: invokeinterface 662 1 0
    //   194: astore 19
    //   196: aload 8
    //   198: monitorexit
    //   199: aload 19
    //   201: ifnonnull +8 -> 209
    //   204: ldc_w 664
    //   207: astore 19
    //   209: aload_0
    //   210: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   213: astore 20
    //   215: iconst_0
    //   216: istore 21
    //   218: aload 20
    //   220: ifnull +126 -> 346
    //   223: new 372	java/lang/StringBuilder
    //   226: dup
    //   227: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   230: ldc_w 668
    //   233: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: aload 6
    //   238: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   241: ldc_w 670
    //   244: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   247: ldc_w 672
    //   250: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: aload 6
    //   255: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: ldc_w 674
    //   261: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: aload 7
    //   266: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: ldc_w 676
    //   272: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: ldc_w 678
    //   278: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: ldc_w 680
    //   284: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: aload 19
    //   289: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: ldc_w 682
    //   295: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   301: astore 24
    //   303: aload 4
    //   305: ldc_w 659
    //   308: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   311: ifeq +105 -> 416
    //   314: aload_0
    //   315: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   318: new 372	java/lang/StringBuilder
    //   321: dup
    //   322: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   325: aload 24
    //   327: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: ldc_w 684
    //   333: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   336: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   339: invokeinterface 689 2 0
    //   344: istore 21
    //   346: iload 21
    //   348: ifne +85 -> 433
    //   351: new 180	com/jcraft/jsch/JSchException
    //   354: dup
    //   355: new 372	java/lang/StringBuilder
    //   358: dup
    //   359: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   362: ldc_w 691
    //   365: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   368: aload_1
    //   369: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   372: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   375: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   378: athrow
    //   379: new 693	com/jcraft/jsch/HostKey
    //   382: dup
    //   383: aload_1
    //   384: aload 5
    //   386: invokespecial 696	com/jcraft/jsch/HostKey:<init>	(Ljava/lang/String;[B)V
    //   389: astore 9
    //   391: aload_0
    //   392: aload 9
    //   394: putfield 267	com/jcraft/jsch/Session:hostkey	Lcom/jcraft/jsch/HostKey;
    //   397: goto -269 -> 128
    //   400: astore 10
    //   402: aload 8
    //   404: monitorexit
    //   405: aload 10
    //   407: athrow
    //   408: astore 18
    //   410: aload 8
    //   412: monitorexit
    //   413: aload 18
    //   415: athrow
    //   416: aload_0
    //   417: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   420: aload 24
    //   422: invokeinterface 699 2 0
    //   427: iconst_0
    //   428: istore 21
    //   430: goto -84 -> 346
    //   433: aload 8
    //   435: monitorenter
    //   436: aload 6
    //   438: ldc_w 701
    //   441: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   444: ifeq +99 -> 543
    //   447: ldc_w 703
    //   450: astore 23
    //   452: aload 8
    //   454: aload_1
    //   455: aload 23
    //   457: aconst_null
    //   458: invokeinterface 707 4 0
    //   463: iconst_1
    //   464: istore 12
    //   466: aload 8
    //   468: monitorexit
    //   469: aload 4
    //   471: ldc_w 659
    //   474: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   477: ifne +14 -> 491
    //   480: aload 4
    //   482: ldc_w 538
    //   485: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   488: ifeq +181 -> 669
    //   491: iload 11
    //   493: ifeq +176 -> 669
    //   496: iload 12
    //   498: ifne +171 -> 669
    //   501: aload 4
    //   503: ldc_w 538
    //   506: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   509: ifeq +50 -> 559
    //   512: new 180	com/jcraft/jsch/JSchException
    //   515: dup
    //   516: new 372	java/lang/StringBuilder
    //   519: dup
    //   520: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   523: ldc_w 709
    //   526: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   529: aload_0
    //   530: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   533: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   536: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   539: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   542: athrow
    //   543: ldc_w 711
    //   546: astore 23
    //   548: goto -96 -> 452
    //   551: astore 22
    //   553: aload 8
    //   555: monitorexit
    //   556: aload 22
    //   558: athrow
    //   559: aload_0
    //   560: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   563: ifnull +366 -> 929
    //   566: aload_0
    //   567: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   570: new 372	java/lang/StringBuilder
    //   573: dup
    //   574: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   577: ldc_w 713
    //   580: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   583: aload_0
    //   584: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   587: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   590: ldc_w 715
    //   593: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: aload 6
    //   598: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   601: ldc_w 717
    //   604: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   607: aload 7
    //   609: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   612: ldc_w 676
    //   615: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   618: ldc_w 719
    //   621: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   624: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   627: invokeinterface 689 2 0
    //   632: ifne +34 -> 666
    //   635: new 180	com/jcraft/jsch/JSchException
    //   638: dup
    //   639: new 372	java/lang/StringBuilder
    //   642: dup
    //   643: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   646: ldc_w 709
    //   649: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   652: aload_0
    //   653: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   656: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   659: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   662: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   665: athrow
    //   666: iconst_1
    //   667: istore 12
    //   669: aload 4
    //   671: ldc_w 721
    //   674: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   677: ifeq +12 -> 689
    //   680: iconst_1
    //   681: iload 11
    //   683: if_icmpne +6 -> 689
    //   686: iconst_1
    //   687: istore 12
    //   689: iload 11
    //   691: ifne +342 -> 1033
    //   694: aload 6
    //   696: ldc_w 701
    //   699: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   702: ifeq +317 -> 1019
    //   705: ldc_w 703
    //   708: astore 14
    //   710: aload 8
    //   712: aload_1
    //   713: aload 14
    //   715: invokeinterface 724 3 0
    //   720: astore 15
    //   722: aload 5
    //   724: iconst_0
    //   725: aload 5
    //   727: arraylength
    //   728: invokestatic 728	com/jcraft/jsch/Util:toBase64	([BII)[B
    //   731: invokestatic 732	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   734: astore 16
    //   736: iconst_0
    //   737: istore 17
    //   739: iload 17
    //   741: aload 15
    //   743: arraylength
    //   744: if_icmpge +289 -> 1033
    //   747: aload 15
    //   749: iload 11
    //   751: aaload
    //   752: invokevirtual 735	com/jcraft/jsch/HostKey:getKey	()Ljava/lang/String;
    //   755: aload 16
    //   757: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   760: ifeq +267 -> 1027
    //   763: aload 15
    //   765: iload 17
    //   767: aaload
    //   768: invokevirtual 738	com/jcraft/jsch/HostKey:getMarker	()Ljava/lang/String;
    //   771: ldc_w 740
    //   774: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   777: ifeq +250 -> 1027
    //   780: aload_0
    //   781: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   784: ifnull +64 -> 848
    //   787: aload_0
    //   788: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   791: new 372	java/lang/StringBuilder
    //   794: dup
    //   795: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   798: ldc_w 742
    //   801: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   804: aload 6
    //   806: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   809: ldc_w 744
    //   812: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   815: aload_0
    //   816: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   819: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   822: ldc_w 746
    //   825: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   828: ldc_w 748
    //   831: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   834: ldc_w 750
    //   837: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   840: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   843: invokeinterface 699 2 0
    //   848: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   851: iconst_1
    //   852: invokeinterface 586 2 0
    //   857: ifeq +41 -> 898
    //   860: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   863: iconst_1
    //   864: new 372	java/lang/StringBuilder
    //   867: dup
    //   868: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   871: ldc_w 752
    //   874: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   877: aload_0
    //   878: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   881: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   884: ldc_w 754
    //   887: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   890: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   893: invokeinterface 592 3 0
    //   898: new 180	com/jcraft/jsch/JSchException
    //   901: dup
    //   902: new 372	java/lang/StringBuilder
    //   905: dup
    //   906: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   909: ldc_w 756
    //   912: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   915: aload_0
    //   916: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   919: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   922: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   925: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   928: athrow
    //   929: iload 11
    //   931: iconst_1
    //   932: if_icmpne +56 -> 988
    //   935: new 180	com/jcraft/jsch/JSchException
    //   938: dup
    //   939: new 372	java/lang/StringBuilder
    //   942: dup
    //   943: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   946: ldc_w 758
    //   949: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   952: aload_0
    //   953: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   956: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   959: ldc_w 760
    //   962: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   965: aload 6
    //   967: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   970: ldc_w 717
    //   973: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   976: aload 7
    //   978: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   981: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   984: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   987: athrow
    //   988: new 180	com/jcraft/jsch/JSchException
    //   991: dup
    //   992: new 372	java/lang/StringBuilder
    //   995: dup
    //   996: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   999: ldc_w 691
    //   1002: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1005: aload_0
    //   1006: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   1009: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1012: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1015: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   1018: athrow
    //   1019: ldc_w 711
    //   1022: astore 14
    //   1024: goto -314 -> 710
    //   1027: iinc 17 1
    //   1030: goto -291 -> 739
    //   1033: iload 11
    //   1035: ifne +64 -> 1099
    //   1038: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1041: iconst_1
    //   1042: invokeinterface 586 2 0
    //   1047: ifeq +52 -> 1099
    //   1050: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1053: iconst_1
    //   1054: new 372	java/lang/StringBuilder
    //   1057: dup
    //   1058: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1061: ldc_w 752
    //   1064: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1067: aload_0
    //   1068: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   1071: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1074: ldc_w 762
    //   1077: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1080: aload 6
    //   1082: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1085: ldc_w 764
    //   1088: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1091: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1094: invokeinterface 592 3 0
    //   1099: iload 12
    //   1101: ifeq +64 -> 1165
    //   1104: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1107: iconst_2
    //   1108: invokeinterface 586 2 0
    //   1113: ifeq +52 -> 1165
    //   1116: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1119: iconst_2
    //   1120: new 372	java/lang/StringBuilder
    //   1123: dup
    //   1124: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1127: ldc_w 766
    //   1130: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1133: aload_0
    //   1134: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   1137: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1140: ldc_w 768
    //   1143: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1146: aload 6
    //   1148: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1151: ldc_w 770
    //   1154: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1157: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1160: invokeinterface 592 3 0
    //   1165: iload 12
    //   1167: ifeq +33 -> 1200
    //   1170: aload 8
    //   1172: monitorenter
    //   1173: aload 8
    //   1175: aload_0
    //   1176: getfield 267	com/jcraft/jsch/Session:hostkey	Lcom/jcraft/jsch/HostKey;
    //   1179: aload_0
    //   1180: getfield 666	com/jcraft/jsch/Session:userinfo	Lcom/jcraft/jsch/UserInfo;
    //   1183: invokeinterface 773 3 0
    //   1188: aload 8
    //   1190: monitorexit
    //   1191: return
    //   1192: astore 13
    //   1194: aload 8
    //   1196: monitorexit
    //   1197: aload 13
    //   1199: athrow
    //   1200: return
    //
    // Exception table:
    //   from	to	target	type
    //   131	146	400	finally
    //   402	405	400	finally
    //   187	199	408	finally
    //   410	413	408	finally
    //   436	447	551	finally
    //   452	463	551	finally
    //   466	469	551	finally
    //   553	556	551	finally
    //   1173	1191	1192	finally
    //   1194	1197	1192	finally
  }

  static boolean checkKex(Session paramSession, String paramString)
  {
    try
    {
      ((KeyExchange)Class.forName(paramString).newInstance()).init(paramSession, null, null, null, null);
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private String[] checkKexes(String paramString)
  {
    String[] arrayOfString1 = null;
    if (paramString != null)
    {
      int i = paramString.length();
      arrayOfString1 = null;
      if (i != 0)
        break label19;
    }
    while (true)
    {
      return arrayOfString1;
      label19: if (JSch.getLogger().isEnabled(1))
        JSch.getLogger().log(1, "CheckKexes: " + paramString);
      Vector localVector = new Vector();
      String[] arrayOfString2 = Util.split(paramString, ",");
      for (int j = 0; j < arrayOfString2.length; j++)
        if (!checkKex(this, getConfig(arrayOfString2[j])))
          localVector.addElement(arrayOfString2[j]);
      int k = localVector.size();
      arrayOfString1 = null;
      if (k != 0)
      {
        arrayOfString1 = new String[localVector.size()];
        System.arraycopy(localVector.toArray(), 0, arrayOfString1, 0, localVector.size());
        if (JSch.getLogger().isEnabled(1))
          for (int m = 0; m < arrayOfString1.length; m++)
            JSch.getLogger().log(1, arrayOfString1[m] + " is not available.");
      }
    }
  }

  private byte[] expandKey(Buffer paramBuffer, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, HASH paramHASH, int paramInt)
    throws Exception
  {
    Object localObject = paramArrayOfByte3;
    int i = paramHASH.getBlockSize();
    while (localObject.length < paramInt)
    {
      paramBuffer.reset();
      paramBuffer.putMPInt(paramArrayOfByte1);
      paramBuffer.putByte(paramArrayOfByte2);
      paramBuffer.putByte((byte[])localObject);
      paramHASH.update(paramBuffer.buffer, 0, paramBuffer.index);
      byte[] arrayOfByte = new byte[i + localObject.length];
      System.arraycopy(localObject, 0, arrayOfByte, 0, localObject.length);
      System.arraycopy(paramHASH.digest(), 0, arrayOfByte, localObject.length, i);
      Util.bzero((byte[])localObject);
      localObject = arrayOfByte;
    }
    return localObject;
  }

  // ERROR //
  private void initDeflater(String paramString)
    throws JSchException
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 815
    //   4: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   7: ifeq +9 -> 16
    //   10: aload_0
    //   11: aconst_null
    //   12: putfield 817	com/jcraft/jsch/Session:deflater	Lcom/jcraft/jsch/Compression;
    //   15: return
    //   16: aload_0
    //   17: aload_1
    //   18: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   21: astore_2
    //   22: aload_2
    //   23: ifnull -8 -> 15
    //   26: aload_1
    //   27: ldc_w 819
    //   30: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   33: ifne +20 -> 53
    //   36: aload_0
    //   37: getfield 200	com/jcraft/jsch/Session:isAuthed	Z
    //   40: ifeq -25 -> 15
    //   43: aload_1
    //   44: ldc_w 821
    //   47: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   50: ifeq -35 -> 15
    //   53: aload_0
    //   54: aload_2
    //   55: invokestatic 557	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   58: invokevirtual 560	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   61: checkcast 823	com/jcraft/jsch/Compression
    //   64: checkcast 823	com/jcraft/jsch/Compression
    //   67: putfield 817	com/jcraft/jsch/Session:deflater	Lcom/jcraft/jsch/Compression;
    //   70: bipush 6
    //   72: istore 5
    //   74: aload_0
    //   75: ldc_w 442
    //   78: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   81: invokestatic 520	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   84: istore 7
    //   86: iload 7
    //   88: istore 5
    //   90: aload_0
    //   91: getfield 817	com/jcraft/jsch/Session:deflater	Lcom/jcraft/jsch/Compression;
    //   94: iconst_1
    //   95: iload 5
    //   97: invokeinterface 826 3 0
    //   102: return
    //   103: astore 4
    //   105: new 180	com/jcraft/jsch/JSchException
    //   108: dup
    //   109: aload 4
    //   111: invokevirtual 827	java/lang/NoClassDefFoundError:toString	()Ljava/lang/String;
    //   114: aload 4
    //   116: invokespecial 370	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   119: athrow
    //   120: astore_3
    //   121: new 180	com/jcraft/jsch/JSchException
    //   124: dup
    //   125: aload_3
    //   126: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   129: aload_3
    //   130: invokespecial 370	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   133: athrow
    //   134: astore 6
    //   136: goto -46 -> 90
    //
    // Exception table:
    //   from	to	target	type
    //   53	70	103	java/lang/NoClassDefFoundError
    //   74	86	103	java/lang/NoClassDefFoundError
    //   90	102	103	java/lang/NoClassDefFoundError
    //   53	70	120	java/lang/Exception
    //   90	102	120	java/lang/Exception
    //   74	86	134	java/lang/Exception
  }

  private void initInflater(String paramString)
    throws JSchException
  {
    if (paramString.equals("none"))
      this.inflater = null;
    String str;
    do
    {
      return;
      str = getConfig(paramString);
    }
    while ((str == null) || ((!paramString.equals("zlib")) && ((!this.isAuthed) || (!paramString.equals("zlib@openssh.com")))));
    try
    {
      this.inflater = ((Compression)Class.forName(str).newInstance());
      this.inflater.init(0, 0);
      return;
    }
    catch (Exception localException)
    {
      throw new JSchException(localException.toString(), localException);
    }
  }

  private Forwarding parseForwarding(String paramString)
    throws JSchException
  {
    String[] arrayOfString = paramString.split(" ");
    if (arrayOfString.length > 1)
    {
      Vector localVector = new Vector();
      int i = 0;
      if (i < arrayOfString.length)
      {
        if (arrayOfString[i].length() == 0);
        while (true)
        {
          i++;
          break;
          localVector.addElement(arrayOfString[i].trim());
        }
      }
      StringBuffer localStringBuffer = new StringBuffer();
      for (int j = 0; j < localVector.size(); j++)
      {
        localStringBuffer.append((String)localVector.elementAt(j));
        if (j + 1 < localVector.size())
          localStringBuffer.append(":");
      }
      paramString = localStringBuffer.toString();
    }
    String str1 = paramString;
    Forwarding localForwarding = new Forwarding(null);
    try
    {
      if (paramString.lastIndexOf(":") == -1)
        throw new JSchException("parseForwarding: " + str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new JSchException("parseForwarding: " + localNumberFormatException.toString());
    }
    localForwarding.hostport = Integer.parseInt(paramString.substring(1 + paramString.lastIndexOf(":")));
    String str2 = paramString.substring(0, paramString.lastIndexOf(":"));
    if (str2.lastIndexOf(":") == -1)
      throw new JSchException("parseForwarding: " + str1);
    localForwarding.host = str2.substring(1 + str2.lastIndexOf(":"));
    String str3 = str2.substring(0, str2.lastIndexOf(":"));
    String str4;
    if (str3.lastIndexOf(":") != -1)
    {
      localForwarding.port = Integer.parseInt(str3.substring(1 + str3.lastIndexOf(":")));
      str4 = str3.substring(0, str3.lastIndexOf(":"));
      if ((str4.length() == 0) || (str4.equals("*")))
        break label451;
    }
    while (true)
    {
      if (str4.equals("localhost"))
        str4 = "127.0.0.1";
      localForwarding.bind_address = str4;
      return localForwarding;
      localForwarding.port = Integer.parseInt(str3);
      localForwarding.bind_address = "127.0.0.1";
      return localForwarding;
      label451: str4 = "0.0.0.0";
    }
  }

  private KeyExchange receive_kexinit(Buffer paramBuffer)
    throws Exception
  {
    int i = paramBuffer.getInt();
    if (i != paramBuffer.getLength())
      paramBuffer.getByte();
    for (this.I_S = new byte[-5 + paramBuffer.index]; ; this.I_S = new byte[i - 1 - paramBuffer.getByte()])
    {
      System.arraycopy(paramBuffer.buffer, paramBuffer.s, this.I_S, 0, this.I_S.length);
      if (!this.in_kex)
        send_kexinit();
      this.guess = KeyExchange.guess(this.I_S, this.I_C);
      if (this.guess != null)
        break;
      throw new JSchException("Algorithm negotiation fail");
    }
    if ((!this.isAuthed) && ((this.guess[2].equals("none")) || (this.guess[3].equals("none"))))
      throw new JSchException("NONE Cipher should not be chosen before authentification is successed.");
    try
    {
      KeyExchange localKeyExchange = (KeyExchange)Class.forName(getConfig(this.guess[0])).newInstance();
      localKeyExchange.init(this, this.V_S, this.V_C, this.I_S, this.I_C);
      return localKeyExchange;
    }
    catch (Exception localException)
    {
      throw new JSchException(localException.toString(), localException);
    }
  }

  private void receive_newkeys(Buffer paramBuffer, KeyExchange paramKeyExchange)
    throws Exception
  {
    updateKeys(paramKeyExchange);
    this.in_kex = false;
  }

  private void requestPortForwarding()
    throws JSchException
  {
    if (getConfig("ClearAllForwardings").equals("yes"));
    while (true)
    {
      return;
      ConfigRepository localConfigRepository = this.jsch.getConfigRepository();
      if (localConfigRepository != null)
      {
        ConfigRepository.Config localConfig = localConfigRepository.getConfig(this.org_host);
        String[] arrayOfString1 = localConfig.getValues("LocalForward");
        if (arrayOfString1 != null)
          for (int j = 0; j < arrayOfString1.length; j++)
            setPortForwardingL(arrayOfString1[j]);
        String[] arrayOfString2 = localConfig.getValues("RemoteForward");
        if (arrayOfString2 != null)
          for (int i = 0; i < arrayOfString2.length; i++)
            setPortForwardingR(arrayOfString2[i]);
      }
    }
  }

  private void send_kexinit()
    throws Exception
  {
    if (this.in_kex);
    while (true)
    {
      return;
      String str1 = getConfig("cipher.c2s");
      String str2 = getConfig("cipher.s2c");
      String[] arrayOfString1 = checkCiphers(getConfig("CheckCiphers"));
      if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
      {
        str1 = Util.diffString(str1, arrayOfString1);
        str2 = Util.diffString(str2, arrayOfString1);
        if ((str1 == null) || (str2 == null))
          throw new JSchException("There are not any available ciphers.");
      }
      String str3 = getConfig("kex");
      String[] arrayOfString2 = checkKexes(getConfig("CheckKexes"));
      if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
      {
        str3 = Util.diffString(str3, arrayOfString2);
        if (str3 == null)
          throw new JSchException("There are not any available kexes.");
      }
      this.in_kex = true;
      this.kex_start_time = System.currentTimeMillis();
      Buffer localBuffer = new Buffer();
      Packet localPacket = new Packet(localBuffer);
      localPacket.reset();
      localBuffer.putByte((byte)20);
      synchronized (random)
      {
        random.fill(localBuffer.buffer, localBuffer.index, 16);
        localBuffer.skip(16);
        localBuffer.putString(Util.str2byte(str3));
        localBuffer.putString(Util.str2byte(getConfig("server_host_key")));
        localBuffer.putString(Util.str2byte(str1));
        localBuffer.putString(Util.str2byte(str2));
        localBuffer.putString(Util.str2byte(getConfig("mac.c2s")));
        localBuffer.putString(Util.str2byte(getConfig("mac.s2c")));
        localBuffer.putString(Util.str2byte(getConfig("compression.c2s")));
        localBuffer.putString(Util.str2byte(getConfig("compression.s2c")));
        localBuffer.putString(Util.str2byte(getConfig("lang.c2s")));
        localBuffer.putString(Util.str2byte(getConfig("lang.s2c")));
        localBuffer.putByte((byte)0);
        localBuffer.putInt(0);
        localBuffer.setOffSet(5);
        this.I_C = new byte[localBuffer.getLength()];
        localBuffer.getByte(this.I_C);
        write(localPacket);
        if (!JSch.getLogger().isEnabled(1))
          continue;
        JSch.getLogger().log(1, "SSH_MSG_KEXINIT sent");
        return;
      }
    }
  }

  private void send_newkeys()
    throws Exception
  {
    this.packet.reset();
    this.buf.putByte((byte)21);
    write(this.packet);
    if (JSch.getLogger().isEnabled(1))
      JSch.getLogger().log(1, "SSH_MSG_NEWKEYS sent");
  }

  private void start_discard(Buffer paramBuffer, Cipher paramCipher, MAC paramMAC, int paramInt1, int paramInt2)
    throws JSchException, IOException
  {
    if (!paramCipher.isCBC())
      throw new JSchException("Packet corrupt");
    MAC localMAC = null;
    if (paramInt1 != 262144)
    {
      localMAC = null;
      if (paramMAC != null)
        localMAC = paramMAC;
    }
    int i = paramInt2 - paramBuffer.index;
    if (i > 0)
    {
      paramBuffer.reset();
      if (i > paramBuffer.buffer.length);
      for (int j = paramBuffer.buffer.length; ; j = i)
      {
        this.io.getByte(paramBuffer.buffer, 0, j);
        if (localMAC != null)
          localMAC.update(paramBuffer.buffer, 0, j);
        i -= j;
        break;
      }
    }
    if (localMAC != null)
      localMAC.doFinal(paramBuffer.buffer, 0);
    throw new JSchException("Packet corrupt");
  }

  private void updateKeys(KeyExchange paramKeyExchange)
    throws Exception
  {
    byte[] arrayOfByte1 = paramKeyExchange.getK();
    byte[] arrayOfByte2 = paramKeyExchange.getH();
    HASH localHASH = paramKeyExchange.getHash();
    if (this.session_id == null)
    {
      this.session_id = new byte[arrayOfByte2.length];
      System.arraycopy(arrayOfByte2, 0, this.session_id, 0, arrayOfByte2.length);
    }
    this.buf.reset();
    this.buf.putMPInt(arrayOfByte1);
    this.buf.putByte(arrayOfByte2);
    this.buf.putByte((byte)65);
    this.buf.putByte(this.session_id);
    localHASH.update(this.buf.buffer, 0, this.buf.index);
    this.IVc2s = localHASH.digest();
    int i = -1 + (this.buf.index - this.session_id.length);
    byte[] arrayOfByte3 = this.buf.buffer;
    arrayOfByte3[i] = ((byte)(1 + arrayOfByte3[i]));
    localHASH.update(this.buf.buffer, 0, this.buf.index);
    this.IVs2c = localHASH.digest();
    byte[] arrayOfByte4 = this.buf.buffer;
    arrayOfByte4[i] = ((byte)(1 + arrayOfByte4[i]));
    localHASH.update(this.buf.buffer, 0, this.buf.index);
    this.Ec2s = localHASH.digest();
    byte[] arrayOfByte5 = this.buf.buffer;
    arrayOfByte5[i] = ((byte)(1 + arrayOfByte5[i]));
    localHASH.update(this.buf.buffer, 0, this.buf.index);
    this.Es2c = localHASH.digest();
    byte[] arrayOfByte6 = this.buf.buffer;
    arrayOfByte6[i] = ((byte)(1 + arrayOfByte6[i]));
    localHASH.update(this.buf.buffer, 0, this.buf.index);
    this.MACc2s = localHASH.digest();
    byte[] arrayOfByte7 = this.buf.buffer;
    arrayOfByte7[i] = ((byte)(1 + arrayOfByte7[i]));
    localHASH.update(this.buf.buffer, 0, this.buf.index);
    this.MACs2c = localHASH.digest();
    try
    {
      this.s2ccipher = ((Cipher)Class.forName(getConfig(this.guess[3])).newInstance());
      while (this.s2ccipher.getBlockSize() > this.Es2c.length)
      {
        this.buf.reset();
        this.buf.putMPInt(arrayOfByte1);
        this.buf.putByte(arrayOfByte2);
        this.buf.putByte(this.Es2c);
        localHASH.update(this.buf.buffer, 0, this.buf.index);
        byte[] arrayOfByte10 = localHASH.digest();
        byte[] arrayOfByte11 = new byte[this.Es2c.length + arrayOfByte10.length];
        System.arraycopy(this.Es2c, 0, arrayOfByte11, 0, this.Es2c.length);
        System.arraycopy(arrayOfByte10, 0, arrayOfByte11, this.Es2c.length, arrayOfByte10.length);
        this.Es2c = arrayOfByte11;
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof JSchException))
      {
        throw localException;
        this.s2ccipher.init(1, this.Es2c, this.IVs2c);
        this.s2ccipher_size = this.s2ccipher.getIVSize();
        this.s2cmac = ((MAC)Class.forName(getConfig(this.guess[5])).newInstance());
        this.MACs2c = expandKey(this.buf, arrayOfByte1, arrayOfByte2, this.MACs2c, localHASH, this.s2cmac.getBlockSize());
        this.s2cmac.init(this.MACs2c);
        this.s2cmac_result1 = new byte[this.s2cmac.getBlockSize()];
        this.s2cmac_result2 = new byte[this.s2cmac.getBlockSize()];
        this.c2scipher = ((Cipher)Class.forName(getConfig(this.guess[2])).newInstance());
        while (this.c2scipher.getBlockSize() > this.Ec2s.length)
        {
          this.buf.reset();
          this.buf.putMPInt(arrayOfByte1);
          this.buf.putByte(arrayOfByte2);
          this.buf.putByte(this.Ec2s);
          localHASH.update(this.buf.buffer, 0, this.buf.index);
          byte[] arrayOfByte8 = localHASH.digest();
          byte[] arrayOfByte9 = new byte[this.Ec2s.length + arrayOfByte8.length];
          System.arraycopy(this.Ec2s, 0, arrayOfByte9, 0, this.Ec2s.length);
          System.arraycopy(arrayOfByte8, 0, arrayOfByte9, this.Ec2s.length, arrayOfByte8.length);
          this.Ec2s = arrayOfByte9;
        }
        this.c2scipher.init(0, this.Ec2s, this.IVc2s);
        this.c2scipher_size = this.c2scipher.getIVSize();
        this.c2smac = ((MAC)Class.forName(getConfig(this.guess[4])).newInstance());
        this.MACc2s = expandKey(this.buf, arrayOfByte1, arrayOfByte2, this.MACc2s, localHASH, this.c2smac.getBlockSize());
        this.c2smac.init(this.MACc2s);
        initDeflater(this.guess[6]);
        initInflater(this.guess[7]);
        return;
      }
      throw new JSchException(localException.toString(), localException);
    }
  }

  void addChannel(Channel paramChannel)
  {
    paramChannel.setSession(this);
  }

  public void connect()
    throws JSchException
  {
    connect(this.timeout);
  }

  // ERROR //
  public void connect(int paramInt)
    throws JSchException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   4: ifeq +14 -> 18
    //   7: new 180	com/jcraft/jsch/JSchException
    //   10: dup
    //   11: ldc_w 1056
    //   14: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   17: athrow
    //   18: aload_0
    //   19: new 394	com/jcraft/jsch/IO
    //   22: dup
    //   23: invokespecial 1057	com/jcraft/jsch/IO:<init>	()V
    //   26: putfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   29: getstatic 951	com/jcraft/jsch/Session:random	Lcom/jcraft/jsch/Random;
    //   32: ifnonnull +25 -> 57
    //   35: aload_0
    //   36: ldc_w 1058
    //   39: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   42: invokestatic 557	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   45: invokevirtual 560	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   48: checkcast 953	com/jcraft/jsch/Random
    //   51: checkcast 953	com/jcraft/jsch/Random
    //   54: putstatic 951	com/jcraft/jsch/Session:random	Lcom/jcraft/jsch/Random;
    //   57: getstatic 951	com/jcraft/jsch/Session:random	Lcom/jcraft/jsch/Random;
    //   60: invokestatic 1062	com/jcraft/jsch/Packet:setRandom	(Lcom/jcraft/jsch/Random;)V
    //   63: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   66: iconst_1
    //   67: invokeinterface 586 2 0
    //   72: ifeq +48 -> 120
    //   75: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   78: iconst_1
    //   79: new 372	java/lang/StringBuilder
    //   82: dup
    //   83: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   86: ldc_w 1064
    //   89: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: aload_0
    //   93: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   96: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: ldc_w 1066
    //   102: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: aload_0
    //   106: getfield 244	com/jcraft/jsch/Session:port	I
    //   109: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   112: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokeinterface 592 3 0
    //   120: aload_0
    //   121: getfield 218	com/jcraft/jsch/Session:proxy	Lcom/jcraft/jsch/Proxy;
    //   124: ifnonnull +428 -> 552
    //   127: aload_0
    //   128: getfield 214	com/jcraft/jsch/Session:socket_factory	Lcom/jcraft/jsch/SocketFactory;
    //   131: ifnonnull +367 -> 498
    //   134: aload_0
    //   135: aload_0
    //   136: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   139: aload_0
    //   140: getfield 244	com/jcraft/jsch/Session:port	I
    //   143: iload_1
    //   144: invokestatic 1070	com/jcraft/jsch/Util:createSocket	(Ljava/lang/String;II)Ljava/net/Socket;
    //   147: putfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   150: aload_0
    //   151: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   154: invokevirtual 1078	java/net/Socket:getInputStream	()Ljava/io/InputStream;
    //   157: astore 56
    //   159: aload_0
    //   160: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   163: invokevirtual 1082	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
    //   166: astore 57
    //   168: aload_0
    //   169: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   172: iconst_1
    //   173: invokevirtual 1085	java/net/Socket:setTcpNoDelay	(Z)V
    //   176: aload_0
    //   177: getfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   180: aload 56
    //   182: invokevirtual 1089	com/jcraft/jsch/IO:setInputStream	(Ljava/io/InputStream;)V
    //   185: aload_0
    //   186: getfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   189: aload 57
    //   191: invokevirtual 1093	com/jcraft/jsch/IO:setOutputStream	(Ljava/io/OutputStream;)V
    //   194: iload_1
    //   195: ifle +18 -> 213
    //   198: aload_0
    //   199: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   202: ifnull +11 -> 213
    //   205: aload_0
    //   206: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   209: iload_1
    //   210: invokevirtual 1096	java/net/Socket:setSoTimeout	(I)V
    //   213: aload_0
    //   214: iconst_1
    //   215: putfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   218: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   221: iconst_1
    //   222: invokeinterface 586 2 0
    //   227: ifeq +15 -> 242
    //   230: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   233: iconst_1
    //   234: ldc_w 1098
    //   237: invokeinterface 592 3 0
    //   242: aload_0
    //   243: getfield 269	com/jcraft/jsch/Session:jsch	Lcom/jcraft/jsch/JSch;
    //   246: aload_0
    //   247: invokevirtual 1101	com/jcraft/jsch/JSch:addSession	(Lcom/jcraft/jsch/Session;)V
    //   250: iconst_1
    //   251: aload_0
    //   252: getfield 188	com/jcraft/jsch/Session:V_C	[B
    //   255: arraylength
    //   256: iadd
    //   257: newarray byte
    //   259: astore 9
    //   261: aload_0
    //   262: getfield 188	com/jcraft/jsch/Session:V_C	[B
    //   265: iconst_0
    //   266: aload 9
    //   268: iconst_0
    //   269: aload_0
    //   270: getfield 188	com/jcraft/jsch/Session:V_C	[B
    //   273: arraylength
    //   274: invokestatic 623	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   277: aload 9
    //   279: iconst_m1
    //   280: aload 9
    //   282: arraylength
    //   283: iadd
    //   284: bipush 10
    //   286: bastore
    //   287: aload_0
    //   288: getfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   291: aload 9
    //   293: iconst_0
    //   294: aload 9
    //   296: arraylength
    //   297: invokevirtual 1103	com/jcraft/jsch/IO:put	([BII)V
    //   300: goto +2031 -> 2331
    //   303: iload 10
    //   305: aload_0
    //   306: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   309: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   312: arraylength
    //   313: if_icmpge +17 -> 330
    //   316: aload_0
    //   317: getfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   320: invokevirtual 1104	com/jcraft/jsch/IO:getByte	()I
    //   323: istore 11
    //   325: iload 11
    //   327: ifge +315 -> 642
    //   330: iload 11
    //   332: ifge +336 -> 668
    //   335: new 180	com/jcraft/jsch/JSchException
    //   338: dup
    //   339: ldc_w 1106
    //   342: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   345: athrow
    //   346: astore_3
    //   347: aload_0
    //   348: iconst_0
    //   349: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   352: aload_0
    //   353: getfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   356: ifeq +85 -> 441
    //   359: aload_3
    //   360: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   363: astore 6
    //   365: aload_0
    //   366: getfield 281	com/jcraft/jsch/Session:packet	Lcom/jcraft/jsch/Packet;
    //   369: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   372: aload_0
    //   373: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   376: bipush 84
    //   378: iconst_2
    //   379: bipush 13
    //   381: aload 6
    //   383: invokevirtual 576	java/lang/String:length	()I
    //   386: iadd
    //   387: iadd
    //   388: iadd
    //   389: invokevirtual 1109	com/jcraft/jsch/Buffer:checkFreeSize	(I)V
    //   392: aload_0
    //   393: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   396: iconst_1
    //   397: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   400: aload_0
    //   401: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   404: iconst_3
    //   405: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   408: aload_0
    //   409: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   412: aload 6
    //   414: invokestatic 170	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   417: invokevirtual 344	com/jcraft/jsch/Buffer:putString	([B)V
    //   420: aload_0
    //   421: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   424: ldc_w 1111
    //   427: invokestatic 170	com/jcraft/jsch/Util:str2byte	(Ljava/lang/String;)[B
    //   430: invokevirtual 344	com/jcraft/jsch/Buffer:putString	([B)V
    //   433: aload_0
    //   434: aload_0
    //   435: getfield 281	com/jcraft/jsch/Session:packet	Lcom/jcraft/jsch/Packet;
    //   438: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   441: aload_0
    //   442: invokevirtual 1114	com/jcraft/jsch/Session:disconnect	()V
    //   445: aload_0
    //   446: iconst_0
    //   447: putfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   450: aload_3
    //   451: instanceof 1054
    //   454: ifeq +1821 -> 2275
    //   457: aload_3
    //   458: checkcast 1054	java/lang/RuntimeException
    //   461: athrow
    //   462: astore_2
    //   463: aload_0
    //   464: getfield 248	com/jcraft/jsch/Session:password	[B
    //   467: invokestatic 810	com/jcraft/jsch/Util:bzero	([B)V
    //   470: aload_0
    //   471: aconst_null
    //   472: putfield 248	com/jcraft/jsch/Session:password	[B
    //   475: aload_2
    //   476: athrow
    //   477: astore 58
    //   479: new 180	com/jcraft/jsch/JSchException
    //   482: dup
    //   483: aload 58
    //   485: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   488: aload 58
    //   490: invokespecial 370	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   493: astore 59
    //   495: aload 59
    //   497: athrow
    //   498: aload_0
    //   499: aload_0
    //   500: getfield 214	com/jcraft/jsch/Session:socket_factory	Lcom/jcraft/jsch/SocketFactory;
    //   503: aload_0
    //   504: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   507: aload_0
    //   508: getfield 244	com/jcraft/jsch/Session:port	I
    //   511: invokeinterface 1119 3 0
    //   516: putfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   519: aload_0
    //   520: getfield 214	com/jcraft/jsch/Session:socket_factory	Lcom/jcraft/jsch/SocketFactory;
    //   523: aload_0
    //   524: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   527: invokeinterface 1122 2 0
    //   532: astore 56
    //   534: aload_0
    //   535: getfield 214	com/jcraft/jsch/Session:socket_factory	Lcom/jcraft/jsch/SocketFactory;
    //   538: aload_0
    //   539: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   542: invokeinterface 1125 2 0
    //   547: astore 57
    //   549: goto -381 -> 168
    //   552: aload_0
    //   553: getfield 218	com/jcraft/jsch/Session:proxy	Lcom/jcraft/jsch/Proxy;
    //   556: astore 7
    //   558: aload 7
    //   560: monitorenter
    //   561: aload_0
    //   562: getfield 218	com/jcraft/jsch/Session:proxy	Lcom/jcraft/jsch/Proxy;
    //   565: aload_0
    //   566: getfield 214	com/jcraft/jsch/Session:socket_factory	Lcom/jcraft/jsch/SocketFactory;
    //   569: aload_0
    //   570: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   573: aload_0
    //   574: getfield 244	com/jcraft/jsch/Session:port	I
    //   577: iload_1
    //   578: invokeinterface 1130 5 0
    //   583: aload_0
    //   584: getfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   587: aload_0
    //   588: getfield 218	com/jcraft/jsch/Session:proxy	Lcom/jcraft/jsch/Proxy;
    //   591: invokeinterface 1131 1 0
    //   596: invokevirtual 1089	com/jcraft/jsch/IO:setInputStream	(Ljava/io/InputStream;)V
    //   599: aload_0
    //   600: getfield 392	com/jcraft/jsch/Session:io	Lcom/jcraft/jsch/IO;
    //   603: aload_0
    //   604: getfield 218	com/jcraft/jsch/Session:proxy	Lcom/jcraft/jsch/Proxy;
    //   607: invokeinterface 1132 1 0
    //   612: invokevirtual 1093	com/jcraft/jsch/IO:setOutputStream	(Ljava/io/OutputStream;)V
    //   615: aload_0
    //   616: aload_0
    //   617: getfield 218	com/jcraft/jsch/Session:proxy	Lcom/jcraft/jsch/Proxy;
    //   620: invokeinterface 1136 1 0
    //   625: putfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   628: aload 7
    //   630: monitorexit
    //   631: goto -437 -> 194
    //   634: astore 8
    //   636: aload 7
    //   638: monitorexit
    //   639: aload 8
    //   641: athrow
    //   642: aload_0
    //   643: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   646: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   649: iload 10
    //   651: iload 11
    //   653: i2b
    //   654: bastore
    //   655: iinc 10 1
    //   658: iload 11
    //   660: bipush 10
    //   662: if_icmpne -359 -> 303
    //   665: goto -335 -> 330
    //   668: aload_0
    //   669: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   672: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   675: iload 10
    //   677: iconst_1
    //   678: isub
    //   679: baload
    //   680: bipush 10
    //   682: if_icmpne +31 -> 713
    //   685: iinc 10 255
    //   688: iload 10
    //   690: ifle +23 -> 713
    //   693: aload_0
    //   694: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   697: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   700: iload 10
    //   702: iconst_1
    //   703: isub
    //   704: baload
    //   705: bipush 13
    //   707: if_icmpne +6 -> 713
    //   710: iinc 10 255
    //   713: iload 10
    //   715: iconst_3
    //   716: if_icmple +1615 -> 2331
    //   719: iload 10
    //   721: aload_0
    //   722: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   725: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   728: arraylength
    //   729: if_icmpeq +59 -> 788
    //   732: aload_0
    //   733: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   736: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   739: iconst_0
    //   740: baload
    //   741: bipush 83
    //   743: if_icmpne +1588 -> 2331
    //   746: aload_0
    //   747: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   750: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   753: iconst_1
    //   754: baload
    //   755: bipush 83
    //   757: if_icmpne +1574 -> 2331
    //   760: aload_0
    //   761: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   764: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   767: iconst_2
    //   768: baload
    //   769: bipush 72
    //   771: if_icmpne +1560 -> 2331
    //   774: aload_0
    //   775: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   778: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   781: iconst_3
    //   782: baload
    //   783: bipush 45
    //   785: if_icmpne +1546 -> 2331
    //   788: iload 10
    //   790: aload_0
    //   791: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   794: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   797: arraylength
    //   798: if_icmpeq +39 -> 837
    //   801: iload 10
    //   803: bipush 7
    //   805: if_icmplt +32 -> 837
    //   808: aload_0
    //   809: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   812: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   815: iconst_4
    //   816: baload
    //   817: bipush 49
    //   819: if_icmpne +29 -> 848
    //   822: aload_0
    //   823: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   826: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   829: bipush 6
    //   831: baload
    //   832: bipush 57
    //   834: if_icmpeq +14 -> 848
    //   837: new 180	com/jcraft/jsch/JSchException
    //   840: dup
    //   841: ldc_w 1138
    //   844: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   847: athrow
    //   848: aload_0
    //   849: iload 10
    //   851: newarray byte
    //   853: putfield 912	com/jcraft/jsch/Session:V_S	[B
    //   856: aload_0
    //   857: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   860: getfield 797	com/jcraft/jsch/Buffer:buffer	[B
    //   863: iconst_0
    //   864: aload_0
    //   865: getfield 912	com/jcraft/jsch/Session:V_S	[B
    //   868: iconst_0
    //   869: iload 10
    //   871: invokestatic 623	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   874: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   877: iconst_1
    //   878: invokeinterface 586 2 0
    //   883: ifeq +73 -> 956
    //   886: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   889: iconst_1
    //   890: new 372	java/lang/StringBuilder
    //   893: dup
    //   894: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   897: ldc_w 1140
    //   900: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   903: aload_0
    //   904: getfield 912	com/jcraft/jsch/Session:V_S	[B
    //   907: invokestatic 732	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   910: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   913: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   916: invokeinterface 592 3 0
    //   921: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   924: iconst_1
    //   925: new 372	java/lang/StringBuilder
    //   928: dup
    //   929: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   932: ldc_w 1142
    //   935: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   938: aload_0
    //   939: getfield 188	com/jcraft/jsch/Session:V_C	[B
    //   942: invokestatic 732	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   945: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   948: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   951: invokeinterface 592 3 0
    //   956: aload_0
    //   957: invokespecial 901	com/jcraft/jsch/Session:send_kexinit	()V
    //   960: aload_0
    //   961: aload_0
    //   962: aload_0
    //   963: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   966: invokevirtual 1146	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   969: putfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   972: aload_0
    //   973: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   976: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   979: bipush 20
    //   981: if_icmpeq +42 -> 1023
    //   984: aload_0
    //   985: iconst_0
    //   986: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   989: new 180	com/jcraft/jsch/JSchException
    //   992: dup
    //   993: new 372	java/lang/StringBuilder
    //   996: dup
    //   997: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1000: ldc_w 1152
    //   1003: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1006: aload_0
    //   1007: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1010: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   1013: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1016: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1019: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   1022: athrow
    //   1023: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1026: iconst_1
    //   1027: invokeinterface 586 2 0
    //   1032: ifeq +15 -> 1047
    //   1035: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1038: iconst_1
    //   1039: ldc_w 1154
    //   1042: invokeinterface 592 3 0
    //   1047: aload_0
    //   1048: aload_0
    //   1049: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1052: invokespecial 1156	com/jcraft/jsch/Session:receive_kexinit	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/KeyExchange;
    //   1055: astore 12
    //   1057: aload_0
    //   1058: aload_0
    //   1059: aload_0
    //   1060: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1063: invokevirtual 1146	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   1066: putfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1069: aload 12
    //   1071: invokevirtual 1159	com/jcraft/jsch/KeyExchange:getState	()I
    //   1074: aload_0
    //   1075: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1078: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   1081: if_icmpne +60 -> 1141
    //   1084: aload_0
    //   1085: invokestatic 949	java/lang/System:currentTimeMillis	()J
    //   1088: putfield 232	com/jcraft/jsch/Session:kex_start_time	J
    //   1091: aload 12
    //   1093: aload_0
    //   1094: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1097: invokevirtual 1163	com/jcraft/jsch/KeyExchange:next	(Lcom/jcraft/jsch/Buffer;)Z
    //   1100: istore 13
    //   1102: iload 13
    //   1104: ifne +76 -> 1180
    //   1107: aload_0
    //   1108: iconst_0
    //   1109: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   1112: new 180	com/jcraft/jsch/JSchException
    //   1115: dup
    //   1116: new 372	java/lang/StringBuilder
    //   1119: dup
    //   1120: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1123: ldc_w 1165
    //   1126: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1129: iload 13
    //   1131: invokevirtual 1168	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   1134: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1137: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   1140: athrow
    //   1141: aload_0
    //   1142: iconst_0
    //   1143: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   1146: new 180	com/jcraft/jsch/JSchException
    //   1149: dup
    //   1150: new 372	java/lang/StringBuilder
    //   1153: dup
    //   1154: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1157: ldc_w 1170
    //   1160: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1163: aload_0
    //   1164: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1167: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   1170: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1173: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1176: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   1179: athrow
    //   1180: aload 12
    //   1182: invokevirtual 1159	com/jcraft/jsch/KeyExchange:getState	()I
    //   1185: istore 14
    //   1187: iload 14
    //   1189: ifne -132 -> 1057
    //   1192: aload_0
    //   1193: aload_0
    //   1194: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   1197: aload_0
    //   1198: getfield 244	com/jcraft/jsch/Session:port	I
    //   1201: aload 12
    //   1203: invokespecial 1172	com/jcraft/jsch/Session:checkHost	(Ljava/lang/String;ILcom/jcraft/jsch/KeyExchange;)V
    //   1206: aload_0
    //   1207: invokespecial 1174	com/jcraft/jsch/Session:send_newkeys	()V
    //   1210: aload_0
    //   1211: aload_0
    //   1212: aload_0
    //   1213: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1216: invokevirtual 1146	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   1219: putfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1222: aload_0
    //   1223: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1226: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   1229: bipush 21
    //   1231: if_icmpne +240 -> 1471
    //   1234: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1237: iconst_1
    //   1238: invokeinterface 586 2 0
    //   1243: ifeq +15 -> 1258
    //   1246: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1249: iconst_1
    //   1250: ldc_w 1176
    //   1253: invokeinterface 592 3 0
    //   1258: aload_0
    //   1259: aload_0
    //   1260: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1263: aload 12
    //   1265: invokespecial 1178	com/jcraft/jsch/Session:receive_newkeys	(Lcom/jcraft/jsch/Buffer;Lcom/jcraft/jsch/KeyExchange;)V
    //   1268: aload_0
    //   1269: ldc_w 450
    //   1272: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   1275: astore 18
    //   1277: aload 18
    //   1279: ifnull +12 -> 1291
    //   1282: aload_0
    //   1283: aload 18
    //   1285: invokestatic 520	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1288: putfield 234	com/jcraft/jsch/Session:max_auth_tries	I
    //   1291: iconst_0
    //   1292: istore 19
    //   1294: aload_0
    //   1295: ldc_w 1180
    //   1298: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   1301: invokestatic 557	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   1304: invokevirtual 560	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   1307: checkcast 1182	com/jcraft/jsch/UserAuth
    //   1310: checkcast 1182	com/jcraft/jsch/UserAuth
    //   1313: astore 22
    //   1315: aload 22
    //   1317: aload_0
    //   1318: invokevirtual 1186	com/jcraft/jsch/UserAuth:start	(Lcom/jcraft/jsch/Session;)Z
    //   1321: istore 23
    //   1323: aload_0
    //   1324: ldc_w 448
    //   1327: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   1330: astore 24
    //   1332: aload 24
    //   1334: ldc_w 599
    //   1337: invokestatic 603	com/jcraft/jsch/Util:split	(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
    //   1340: astore 25
    //   1342: aconst_null
    //   1343: astore 26
    //   1345: iload 23
    //   1347: ifne +25 -> 1372
    //   1350: aload 22
    //   1352: checkcast 1188	com/jcraft/jsch/UserAuthNone
    //   1355: invokevirtual 1191	com/jcraft/jsch/UserAuthNone:getMethods	()Ljava/lang/String;
    //   1358: astore 27
    //   1360: aload 27
    //   1362: ifnull +990 -> 2352
    //   1365: aload 27
    //   1367: invokevirtual 1194	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   1370: astore 26
    //   1372: aload 26
    //   1374: ldc_w 599
    //   1377: invokestatic 603	com/jcraft/jsch/Util:split	(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
    //   1380: astore 28
    //   1382: iconst_0
    //   1383: istore 29
    //   1385: iload 23
    //   1387: ifne +938 -> 2325
    //   1390: aload 25
    //   1392: ifnull +933 -> 2325
    //   1395: aload 25
    //   1397: arraylength
    //   1398: istore 34
    //   1400: iload 29
    //   1402: iload 34
    //   1404: if_icmpge +921 -> 2325
    //   1407: iload 29
    //   1409: iconst_1
    //   1410: iadd
    //   1411: istore 35
    //   1413: aload 25
    //   1415: iload 29
    //   1417: aaload
    //   1418: astore 36
    //   1420: iconst_0
    //   1421: istore 37
    //   1423: aload 28
    //   1425: arraylength
    //   1426: istore 38
    //   1428: iload 37
    //   1430: istore 39
    //   1432: iconst_0
    //   1433: istore 40
    //   1435: iload 39
    //   1437: iload 38
    //   1439: if_icmpge +901 -> 2340
    //   1442: aload 28
    //   1444: iload 37
    //   1446: aaload
    //   1447: aload 36
    //   1449: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1452: ifeq +907 -> 2359
    //   1455: iconst_1
    //   1456: istore 40
    //   1458: goto +882 -> 2340
    //   1461: astore 15
    //   1463: aload_0
    //   1464: iconst_0
    //   1465: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   1468: aload 15
    //   1470: athrow
    //   1471: aload_0
    //   1472: iconst_0
    //   1473: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   1476: new 180	com/jcraft/jsch/JSchException
    //   1479: dup
    //   1480: new 372	java/lang/StringBuilder
    //   1483: dup
    //   1484: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1487: ldc_w 1196
    //   1490: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1493: aload_0
    //   1494: getfield 274	com/jcraft/jsch/Session:buf	Lcom/jcraft/jsch/Buffer;
    //   1497: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   1500: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1503: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1506: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   1509: athrow
    //   1510: astore 16
    //   1512: new 180	com/jcraft/jsch/JSchException
    //   1515: dup
    //   1516: new 372	java/lang/StringBuilder
    //   1519: dup
    //   1520: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1523: ldc_w 1198
    //   1526: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1529: aload_0
    //   1530: ldc_w 450
    //   1533: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   1536: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1539: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1542: aload 16
    //   1544: invokespecial 370	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   1547: astore 17
    //   1549: aload 17
    //   1551: athrow
    //   1552: astore 20
    //   1554: new 180	com/jcraft/jsch/JSchException
    //   1557: dup
    //   1558: aload 20
    //   1560: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   1563: aload 20
    //   1565: invokespecial 370	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   1568: astore 21
    //   1570: aload 21
    //   1572: athrow
    //   1573: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1576: iconst_1
    //   1577: invokeinterface 586 2 0
    //   1582: ifeq +128 -> 1710
    //   1585: ldc_w 1200
    //   1588: astore 41
    //   1590: iload 35
    //   1592: iconst_1
    //   1593: isub
    //   1594: istore 42
    //   1596: aload 25
    //   1598: arraylength
    //   1599: istore 43
    //   1601: iload 42
    //   1603: iload 43
    //   1605: if_icmpge +64 -> 1669
    //   1608: new 372	java/lang/StringBuilder
    //   1611: dup
    //   1612: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1615: aload 41
    //   1617: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1620: aload 25
    //   1622: iload 42
    //   1624: aaload
    //   1625: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1628: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1631: astore 41
    //   1633: iload 42
    //   1635: iconst_1
    //   1636: iadd
    //   1637: aload 25
    //   1639: arraylength
    //   1640: if_icmpge +725 -> 2365
    //   1643: new 372	java/lang/StringBuilder
    //   1646: dup
    //   1647: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1650: aload 41
    //   1652: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1655: ldc_w 599
    //   1658: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1661: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1664: astore 41
    //   1666: goto +699 -> 2365
    //   1669: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1672: iconst_1
    //   1673: aload 41
    //   1675: invokeinterface 592 3 0
    //   1680: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1683: iconst_1
    //   1684: new 372	java/lang/StringBuilder
    //   1687: dup
    //   1688: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1691: ldc_w 1202
    //   1694: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1697: aload 36
    //   1699: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1702: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1705: invokeinterface 592 3 0
    //   1710: aload_0
    //   1711: new 372	java/lang/StringBuilder
    //   1714: dup
    //   1715: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1718: ldc_w 1204
    //   1721: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1724: aload 36
    //   1726: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1729: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1732: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   1735: astore 55
    //   1737: aconst_null
    //   1738: astore 46
    //   1740: aload 55
    //   1742: ifnull +42 -> 1784
    //   1745: aload_0
    //   1746: new 372	java/lang/StringBuilder
    //   1749: dup
    //   1750: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1753: ldc_w 1204
    //   1756: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1759: aload 36
    //   1761: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1764: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1767: invokevirtual 594	com/jcraft/jsch/Session:getConfig	(Ljava/lang/String;)Ljava/lang/String;
    //   1770: invokestatic 557	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   1773: invokevirtual 560	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   1776: checkcast 1182	com/jcraft/jsch/UserAuth
    //   1779: checkcast 1182	com/jcraft/jsch/UserAuth
    //   1782: astore 46
    //   1784: aload 46
    //   1786: ifnull +74 -> 1860
    //   1789: aload 46
    //   1791: aload_0
    //   1792: invokevirtual 1186	com/jcraft/jsch/UserAuth:start	(Lcom/jcraft/jsch/Session;)Z
    //   1795: istore 23
    //   1797: iconst_0
    //   1798: istore 19
    //   1800: iload 23
    //   1802: ifeq +58 -> 1860
    //   1805: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1808: iconst_1
    //   1809: invokeinterface 586 2 0
    //   1814: istore 54
    //   1816: iconst_0
    //   1817: istore 19
    //   1819: iload 54
    //   1821: ifeq +39 -> 1860
    //   1824: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1827: iconst_1
    //   1828: new 372	java/lang/StringBuilder
    //   1831: dup
    //   1832: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1835: ldc_w 1206
    //   1838: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1841: aload 36
    //   1843: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1846: ldc_w 1208
    //   1849: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1852: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1855: invokeinterface 592 3 0
    //   1860: iload 35
    //   1862: istore 29
    //   1864: goto -479 -> 1385
    //   1867: astore 44
    //   1869: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1872: iconst_2
    //   1873: invokeinterface 586 2 0
    //   1878: istore 45
    //   1880: aconst_null
    //   1881: astore 46
    //   1883: iload 45
    //   1885: ifeq -101 -> 1784
    //   1888: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1891: iconst_2
    //   1892: new 372	java/lang/StringBuilder
    //   1895: dup
    //   1896: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1899: ldc_w 1210
    //   1902: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1905: aload 36
    //   1907: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1910: ldc_w 1212
    //   1913: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1916: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1919: invokeinterface 592 3 0
    //   1924: aconst_null
    //   1925: astore 46
    //   1927: goto -143 -> 1784
    //   1930: astore 51
    //   1932: aload 26
    //   1934: astore 52
    //   1936: aload 51
    //   1938: invokevirtual 1213	com/jcraft/jsch/JSchPartialAuthException:getMethods	()Ljava/lang/String;
    //   1941: astore 26
    //   1943: aload 26
    //   1945: ldc_w 599
    //   1948: invokestatic 603	com/jcraft/jsch/Util:split	(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
    //   1951: astore 28
    //   1953: aload 52
    //   1955: aload 26
    //   1957: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1960: ifne +419 -> 2379
    //   1963: iconst_0
    //   1964: istore 35
    //   1966: goto +413 -> 2379
    //   1969: astore 50
    //   1971: aload 50
    //   1973: athrow
    //   1974: astore 49
    //   1976: aload 49
    //   1978: athrow
    //   1979: astore 47
    //   1981: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   1984: iconst_2
    //   1985: invokeinterface 586 2 0
    //   1990: istore 48
    //   1992: iconst_0
    //   1993: istore 19
    //   1995: iload 48
    //   1997: ifeq +36 -> 2033
    //   2000: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   2003: iconst_2
    //   2004: new 372	java/lang/StringBuilder
    //   2007: dup
    //   2008: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   2011: ldc_w 1215
    //   2014: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2017: aload 47
    //   2019: invokevirtual 367	java/lang/Exception:toString	()Ljava/lang/String;
    //   2022: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2025: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2028: invokeinterface 592 3 0
    //   2033: iload 23
    //   2035: ifne +85 -> 2120
    //   2038: aload_0
    //   2039: getfield 236	com/jcraft/jsch/Session:auth_failures	I
    //   2042: aload_0
    //   2043: getfield 234	com/jcraft/jsch/Session:max_auth_tries	I
    //   2046: if_icmplt +47 -> 2093
    //   2049: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   2052: iconst_1
    //   2053: invokeinterface 586 2 0
    //   2058: ifeq +35 -> 2093
    //   2061: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   2064: iconst_1
    //   2065: new 372	java/lang/StringBuilder
    //   2068: dup
    //   2069: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   2072: ldc_w 1217
    //   2075: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2078: aload_0
    //   2079: getfield 234	com/jcraft/jsch/Session:max_auth_tries	I
    //   2082: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   2085: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2088: invokeinterface 592 3 0
    //   2093: iload 19
    //   2095: ifeq +14 -> 2109
    //   2098: new 180	com/jcraft/jsch/JSchException
    //   2101: dup
    //   2102: ldc_w 1219
    //   2105: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   2108: athrow
    //   2109: new 180	com/jcraft/jsch/JSchException
    //   2112: dup
    //   2113: ldc_w 1221
    //   2116: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   2119: athrow
    //   2120: aload_0
    //   2121: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   2124: ifnull +25 -> 2149
    //   2127: iload_1
    //   2128: ifgt +10 -> 2138
    //   2131: aload_0
    //   2132: getfield 196	com/jcraft/jsch/Session:timeout	I
    //   2135: ifle +14 -> 2149
    //   2138: aload_0
    //   2139: getfield 1072	com/jcraft/jsch/Session:socket	Ljava/net/Socket;
    //   2142: aload_0
    //   2143: getfield 196	com/jcraft/jsch/Session:timeout	I
    //   2146: invokevirtual 1096	java/net/Socket:setSoTimeout	(I)V
    //   2149: aload_0
    //   2150: iconst_1
    //   2151: putfield 200	com/jcraft/jsch/Session:isAuthed	Z
    //   2154: aload_0
    //   2155: getfield 204	com/jcraft/jsch/Session:lock	Ljava/lang/Object;
    //   2158: astore 31
    //   2160: aload 31
    //   2162: monitorenter
    //   2163: aload_0
    //   2164: getfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   2167: ifeq +84 -> 2251
    //   2170: new 320	java/lang/Thread
    //   2173: dup
    //   2174: aload_0
    //   2175: invokespecial 1224	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   2178: astore 33
    //   2180: aload_0
    //   2181: aload 33
    //   2183: putfield 202	com/jcraft/jsch/Session:connectThread	Ljava/lang/Thread;
    //   2186: aload_0
    //   2187: getfield 202	com/jcraft/jsch/Session:connectThread	Ljava/lang/Thread;
    //   2190: new 372	java/lang/StringBuilder
    //   2193: dup
    //   2194: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   2197: ldc_w 1226
    //   2200: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2203: aload_0
    //   2204: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   2207: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2210: ldc_w 1228
    //   2213: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2216: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2219: invokevirtual 1231	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   2222: aload_0
    //   2223: getfield 230	com/jcraft/jsch/Session:daemon_thread	Z
    //   2226: ifeq +14 -> 2240
    //   2229: aload_0
    //   2230: getfield 202	com/jcraft/jsch/Session:connectThread	Ljava/lang/Thread;
    //   2233: aload_0
    //   2234: getfield 230	com/jcraft/jsch/Session:daemon_thread	Z
    //   2237: invokevirtual 1234	java/lang/Thread:setDaemon	(Z)V
    //   2240: aload_0
    //   2241: getfield 202	com/jcraft/jsch/Session:connectThread	Ljava/lang/Thread;
    //   2244: invokevirtual 1236	java/lang/Thread:start	()V
    //   2247: aload_0
    //   2248: invokespecial 1238	com/jcraft/jsch/Session:requestPortForwarding	()V
    //   2251: aload 31
    //   2253: monitorexit
    //   2254: aload_0
    //   2255: getfield 248	com/jcraft/jsch/Session:password	[B
    //   2258: invokestatic 810	com/jcraft/jsch/Util:bzero	([B)V
    //   2261: aload_0
    //   2262: aconst_null
    //   2263: putfield 248	com/jcraft/jsch/Session:password	[B
    //   2266: return
    //   2267: astore 32
    //   2269: aload 31
    //   2271: monitorexit
    //   2272: aload 32
    //   2274: athrow
    //   2275: aload_3
    //   2276: instanceof 180
    //   2279: ifeq +8 -> 2287
    //   2282: aload_3
    //   2283: checkcast 180	com/jcraft/jsch/JSchException
    //   2286: athrow
    //   2287: new 180	com/jcraft/jsch/JSchException
    //   2290: dup
    //   2291: new 372	java/lang/StringBuilder
    //   2294: dup
    //   2295: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   2298: ldc_w 1240
    //   2301: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2304: aload_3
    //   2305: invokevirtual 1243	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2308: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2311: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   2314: athrow
    //   2315: astore 5
    //   2317: goto -1872 -> 445
    //   2320: astore 4
    //   2322: goto -1881 -> 441
    //   2325: iload 29
    //   2327: pop
    //   2328: goto -295 -> 2033
    //   2331: iconst_0
    //   2332: istore 10
    //   2334: iconst_0
    //   2335: istore 11
    //   2337: goto -2034 -> 303
    //   2340: iload 40
    //   2342: ifne -769 -> 1573
    //   2345: iload 35
    //   2347: istore 29
    //   2349: goto -964 -> 1385
    //   2352: aload 24
    //   2354: astore 26
    //   2356: goto -984 -> 1372
    //   2359: iinc 37 1
    //   2362: goto -939 -> 1423
    //   2365: iinc 42 1
    //   2368: goto -772 -> 1596
    //   2371: astore 53
    //   2373: iconst_1
    //   2374: istore 19
    //   2376: goto -516 -> 1860
    //   2379: iload 35
    //   2381: istore 29
    //   2383: iconst_0
    //   2384: istore 19
    //   2386: goto -1001 -> 1385
    //
    // Exception table:
    //   from	to	target	type
    //   120	168	346	java/lang/Exception
    //   168	194	346	java/lang/Exception
    //   198	213	346	java/lang/Exception
    //   213	242	346	java/lang/Exception
    //   242	300	346	java/lang/Exception
    //   303	325	346	java/lang/Exception
    //   335	346	346	java/lang/Exception
    //   498	549	346	java/lang/Exception
    //   552	561	346	java/lang/Exception
    //   639	642	346	java/lang/Exception
    //   642	655	346	java/lang/Exception
    //   668	685	346	java/lang/Exception
    //   693	710	346	java/lang/Exception
    //   719	788	346	java/lang/Exception
    //   788	801	346	java/lang/Exception
    //   808	837	346	java/lang/Exception
    //   837	848	346	java/lang/Exception
    //   848	956	346	java/lang/Exception
    //   956	1023	346	java/lang/Exception
    //   1023	1047	346	java/lang/Exception
    //   1047	1057	346	java/lang/Exception
    //   1057	1102	346	java/lang/Exception
    //   1107	1141	346	java/lang/Exception
    //   1141	1180	346	java/lang/Exception
    //   1180	1187	346	java/lang/Exception
    //   1192	1206	346	java/lang/Exception
    //   1206	1258	346	java/lang/Exception
    //   1258	1268	346	java/lang/Exception
    //   1268	1277	346	java/lang/Exception
    //   1282	1291	346	java/lang/Exception
    //   1315	1342	346	java/lang/Exception
    //   1350	1360	346	java/lang/Exception
    //   1365	1372	346	java/lang/Exception
    //   1372	1382	346	java/lang/Exception
    //   1395	1400	346	java/lang/Exception
    //   1413	1420	346	java/lang/Exception
    //   1423	1428	346	java/lang/Exception
    //   1442	1455	346	java/lang/Exception
    //   1463	1471	346	java/lang/Exception
    //   1471	1510	346	java/lang/Exception
    //   1512	1552	346	java/lang/Exception
    //   1554	1573	346	java/lang/Exception
    //   1573	1585	346	java/lang/Exception
    //   1596	1601	346	java/lang/Exception
    //   1608	1666	346	java/lang/Exception
    //   1669	1710	346	java/lang/Exception
    //   1869	1880	346	java/lang/Exception
    //   1888	1924	346	java/lang/Exception
    //   1936	1963	346	java/lang/Exception
    //   1971	1974	346	java/lang/Exception
    //   1976	1979	346	java/lang/Exception
    //   1981	1992	346	java/lang/Exception
    //   2000	2033	346	java/lang/Exception
    //   2038	2093	346	java/lang/Exception
    //   2098	2109	346	java/lang/Exception
    //   2109	2120	346	java/lang/Exception
    //   2120	2127	346	java/lang/Exception
    //   2131	2138	346	java/lang/Exception
    //   2138	2149	346	java/lang/Exception
    //   2149	2163	346	java/lang/Exception
    //   2272	2275	346	java/lang/Exception
    //   120	168	462	finally
    //   168	194	462	finally
    //   198	213	462	finally
    //   213	242	462	finally
    //   242	300	462	finally
    //   303	325	462	finally
    //   335	346	462	finally
    //   347	352	462	finally
    //   352	441	462	finally
    //   441	445	462	finally
    //   445	462	462	finally
    //   498	549	462	finally
    //   552	561	462	finally
    //   639	642	462	finally
    //   642	655	462	finally
    //   668	685	462	finally
    //   693	710	462	finally
    //   719	788	462	finally
    //   788	801	462	finally
    //   808	837	462	finally
    //   837	848	462	finally
    //   848	956	462	finally
    //   956	1023	462	finally
    //   1023	1047	462	finally
    //   1047	1057	462	finally
    //   1057	1102	462	finally
    //   1107	1141	462	finally
    //   1141	1180	462	finally
    //   1180	1187	462	finally
    //   1192	1206	462	finally
    //   1206	1258	462	finally
    //   1258	1268	462	finally
    //   1268	1277	462	finally
    //   1282	1291	462	finally
    //   1294	1315	462	finally
    //   1315	1342	462	finally
    //   1350	1360	462	finally
    //   1365	1372	462	finally
    //   1372	1382	462	finally
    //   1395	1400	462	finally
    //   1413	1420	462	finally
    //   1423	1428	462	finally
    //   1442	1455	462	finally
    //   1463	1471	462	finally
    //   1471	1510	462	finally
    //   1512	1552	462	finally
    //   1554	1573	462	finally
    //   1573	1585	462	finally
    //   1596	1601	462	finally
    //   1608	1666	462	finally
    //   1669	1710	462	finally
    //   1710	1737	462	finally
    //   1745	1784	462	finally
    //   1789	1797	462	finally
    //   1805	1816	462	finally
    //   1824	1860	462	finally
    //   1869	1880	462	finally
    //   1888	1924	462	finally
    //   1936	1963	462	finally
    //   1971	1974	462	finally
    //   1976	1979	462	finally
    //   1981	1992	462	finally
    //   2000	2033	462	finally
    //   2038	2093	462	finally
    //   2098	2109	462	finally
    //   2109	2120	462	finally
    //   2120	2127	462	finally
    //   2131	2138	462	finally
    //   2138	2149	462	finally
    //   2149	2163	462	finally
    //   2272	2275	462	finally
    //   2275	2287	462	finally
    //   2287	2315	462	finally
    //   35	57	477	java/lang/Exception
    //   561	631	634	finally
    //   636	639	634	finally
    //   1192	1206	1461	com/jcraft/jsch/JSchException
    //   1268	1277	1510	java/lang/NumberFormatException
    //   1282	1291	1510	java/lang/NumberFormatException
    //   1294	1315	1552	java/lang/Exception
    //   1710	1737	1867	java/lang/Exception
    //   1745	1784	1867	java/lang/Exception
    //   1789	1797	1930	com/jcraft/jsch/JSchPartialAuthException
    //   1805	1816	1930	com/jcraft/jsch/JSchPartialAuthException
    //   1824	1860	1930	com/jcraft/jsch/JSchPartialAuthException
    //   1789	1797	1969	java/lang/RuntimeException
    //   1805	1816	1969	java/lang/RuntimeException
    //   1824	1860	1969	java/lang/RuntimeException
    //   1789	1797	1974	com/jcraft/jsch/JSchException
    //   1805	1816	1974	com/jcraft/jsch/JSchException
    //   1824	1860	1974	com/jcraft/jsch/JSchException
    //   1789	1797	1979	java/lang/Exception
    //   1805	1816	1979	java/lang/Exception
    //   1824	1860	1979	java/lang/Exception
    //   2163	2240	2267	finally
    //   2240	2251	2267	finally
    //   2251	2254	2267	finally
    //   2269	2272	2267	finally
    //   441	445	2315	java/lang/Exception
    //   352	441	2320	java/lang/Exception
    //   1789	1797	2371	com/jcraft/jsch/JSchAuthCancelException
    //   1805	1816	2371	com/jcraft/jsch/JSchAuthCancelException
    //   1824	1860	2371	com/jcraft/jsch/JSchAuthCancelException
  }

  public void delPortForwardingL(int paramInt)
    throws JSchException
  {
    delPortForwardingL("127.0.0.1", paramInt);
  }

  public void delPortForwardingL(String paramString, int paramInt)
    throws JSchException
  {
    PortWatcher.delPort(this, paramString, paramInt);
  }

  public void delPortForwardingR(int paramInt)
    throws JSchException
  {
    delPortForwardingR(null, paramInt);
  }

  public void delPortForwardingR(String paramString, int paramInt)
    throws JSchException
  {
    ChannelForwardedTCPIP.delPort(this, paramString, paramInt);
  }

  public void disconnect()
  {
    if (!this.isConnected)
      return;
    if (JSch.getLogger().isEnabled(1))
      JSch.getLogger().log(1, "Disconnecting from " + this.host + " port " + this.port);
    Channel.disconnect(this);
    this.isConnected = false;
    PortWatcher.delPort(this);
    ChannelForwardedTCPIP.delPort(this);
    ChannelX11.removeFakedCookie(this);
    synchronized (this.lock)
    {
      if (this.connectThread != null)
      {
        Thread.yield();
        this.connectThread.interrupt();
        this.connectThread = null;
      }
      this.thread = null;
    }
    try
    {
      if (this.io != null)
      {
        if (this.io.in != null)
          this.io.in.close();
        if (this.io.out != null)
          this.io.out.close();
        if (this.io.out_ext != null)
          this.io.out_ext.close();
      }
      if (this.proxy == null)
      {
        if (this.socket != null)
          this.socket.close();
        this.io = null;
        this.socket = null;
        this.jsch.removeSession(this);
        return;
        localObject2 = finally;
        throw localObject2;
      }
    }
    catch (Exception localException)
    {
      synchronized (this.proxy)
      {
        while (true)
        {
          this.proxy.close();
          this.proxy = null;
        }
        localException = localException;
      }
    }
  }

  public void encode(Packet paramPacket)
    throws Exception
  {
    if (this.deflater != null)
    {
      this.compress_len[0] = paramPacket.buffer.index;
      paramPacket.buffer.buffer = this.deflater.compress(paramPacket.buffer.buffer, 5, this.compress_len);
      paramPacket.buffer.index = this.compress_len[0];
    }
    int i;
    if (this.c2scipher != null)
    {
      paramPacket.padding(this.c2scipher_size);
      i = paramPacket.buffer.buffer[4];
    }
    while (true)
    {
      synchronized (random)
      {
        random.fill(paramPacket.buffer.buffer, paramPacket.buffer.index - i, i);
        if (this.c2smac != null)
        {
          this.c2smac.update(this.seqo);
          this.c2smac.update(paramPacket.buffer.buffer, 0, paramPacket.buffer.index);
          this.c2smac.doFinal(paramPacket.buffer.buffer, paramPacket.buffer.index);
        }
        if (this.c2scipher != null)
        {
          byte[] arrayOfByte = paramPacket.buffer.buffer;
          this.c2scipher.update(arrayOfByte, 0, paramPacket.buffer.index, arrayOfByte, 0);
        }
        if (this.c2smac != null)
          paramPacket.buffer.skip(this.c2smac.getBlockSize());
        return;
      }
      paramPacket.padding(8);
    }
  }

  public String getClientVersion()
  {
    return Util.byte2str(this.V_C);
  }

  public String getConfig(String paramString)
  {
    if (this.config != null)
    {
      Object localObject = this.config.get(paramString);
      if ((localObject instanceof String))
        return (String)localObject;
    }
    String str = JSch.getConfig(paramString);
    if ((str instanceof String))
      return (String)str;
    return null;
  }

  public String getHost()
  {
    return this.host;
  }

  public HostKey getHostKey()
  {
    return this.hostkey;
  }

  public String getHostKeyAlias()
  {
    return this.hostKeyAlias;
  }

  public HostKeyRepository getHostKeyRepository()
  {
    if (this.hostkeyRepository == null)
      return this.jsch.getHostKeyRepository();
    return this.hostkeyRepository;
  }

  IdentityRepository getIdentityRepository()
  {
    if (this.identityRepository == null)
      return this.jsch.getIdentityRepository();
    return this.identityRepository;
  }

  public int getPort()
  {
    return this.port;
  }

  public String[] getPortForwardingL()
    throws JSchException
  {
    return PortWatcher.getPortForwarding(this);
  }

  public String[] getPortForwardingR()
    throws JSchException
  {
    return ChannelForwardedTCPIP.getPortForwarding(this);
  }

  public int getServerAliveCountMax()
  {
    return this.serverAliveCountMax;
  }

  public int getServerAliveInterval()
  {
    return this.serverAliveInterval;
  }

  public String getServerVersion()
  {
    return Util.byte2str(this.V_S);
  }

  byte[] getSessionId()
  {
    return this.session_id;
  }

  public Channel getStreamForwarder(String paramString, int paramInt)
    throws JSchException
  {
    ChannelDirectTCPIP localChannelDirectTCPIP = new ChannelDirectTCPIP();
    localChannelDirectTCPIP.init();
    addChannel(localChannelDirectTCPIP);
    localChannelDirectTCPIP.setHost(paramString);
    localChannelDirectTCPIP.setPort(paramInt);
    return localChannelDirectTCPIP;
  }

  public int getTimeout()
  {
    return this.timeout;
  }

  public UserInfo getUserInfo()
  {
    return this.userinfo;
  }

  public String getUserName()
  {
    return this.username;
  }

  public boolean isConnected()
  {
    return this.isConnected;
  }

  public void noMoreSessionChannels()
    throws Exception
  {
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)80);
    localBuffer.putString(nomoresessions);
    localBuffer.putByte((byte)0);
    write(localPacket);
  }

  public Channel openChannel(String paramString)
    throws JSchException
  {
    if (!this.isConnected)
      throw new JSchException("session is down");
    try
    {
      Channel localChannel = Channel.getChannel(paramString);
      addChannel(localChannel);
      localChannel.init();
      if ((localChannel instanceof ChannelSession))
        applyConfigChannel((ChannelSession)localChannel);
      return localChannel;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public Buffer read(Buffer paramBuffer)
    throws Exception
  {
    while (true)
    {
      paramBuffer.reset();
      this.io.getByte(paramBuffer.buffer, paramBuffer.index, this.s2ccipher_size);
      paramBuffer.index += this.s2ccipher_size;
      if (this.s2ccipher != null)
        this.s2ccipher.update(paramBuffer.buffer, 0, this.s2ccipher_size, paramBuffer.buffer, 0);
      int i = 0xFF000000 & paramBuffer.buffer[0] << 24 | 0xFF0000 & paramBuffer.buffer[1] << 16 | 0xFF00 & paramBuffer.buffer[2] << 8 | 0xFF & paramBuffer.buffer[3];
      if ((i < 5) || (i > 262144))
        start_discard(paramBuffer, this.s2ccipher, this.s2cmac, i, 262144);
      int j = i + 4 - this.s2ccipher_size;
      if (j + paramBuffer.index > paramBuffer.buffer.length)
      {
        byte[] arrayOfByte4 = new byte[j + paramBuffer.index];
        System.arraycopy(paramBuffer.buffer, 0, arrayOfByte4, 0, paramBuffer.index);
        paramBuffer.buffer = arrayOfByte4;
      }
      if (j % this.s2ccipher_size != 0)
      {
        String str = "Bad packet length " + j;
        if (JSch.getLogger().isEnabled(4))
          JSch.getLogger().log(4, str);
        start_discard(paramBuffer, this.s2ccipher, this.s2cmac, i, 262144 - this.s2ccipher_size);
      }
      if (j > 0)
      {
        this.io.getByte(paramBuffer.buffer, paramBuffer.index, j);
        paramBuffer.index = (j + paramBuffer.index);
        if (this.s2ccipher != null)
          this.s2ccipher.update(paramBuffer.buffer, this.s2ccipher_size, j, paramBuffer.buffer, this.s2ccipher_size);
      }
      if (this.s2cmac == null)
        break;
      this.s2cmac.update(this.seqi);
      this.s2cmac.update(paramBuffer.buffer, 0, paramBuffer.index);
      this.s2cmac.doFinal(this.s2cmac_result1, 0);
      this.io.getByte(this.s2cmac_result2, 0, this.s2cmac_result2.length);
      if (Arrays.equals(this.s2cmac_result1, this.s2cmac_result2))
        break;
      if (j > 262144)
        throw new IOException("MAC Error");
      start_discard(paramBuffer, this.s2ccipher, this.s2cmac, i, 262144 - j);
    }
    this.seqi = (1 + this.seqi);
    int k;
    if (this.inflater != null)
    {
      int i1 = paramBuffer.buffer[4];
      this.uncompress_len[0] = (-5 + paramBuffer.index - i1);
      byte[] arrayOfByte3 = this.inflater.uncompress(paramBuffer.buffer, 5, this.uncompress_len);
      if (arrayOfByte3 != null)
      {
        paramBuffer.buffer = arrayOfByte3;
        paramBuffer.index = (5 + this.uncompress_len[0]);
      }
    }
    else
    {
      k = 0xFF & paramBuffer.getCommand();
      if (k != 1)
        break label663;
      paramBuffer.rewind();
      paramBuffer.getInt();
      paramBuffer.getShort();
      int n = paramBuffer.getInt();
      byte[] arrayOfByte1 = paramBuffer.getString();
      byte[] arrayOfByte2 = paramBuffer.getString();
      throw new JSchException("SSH_MSG_DISCONNECT: " + n + " " + Util.byte2str(arrayOfByte1) + " " + Util.byte2str(arrayOfByte2));
    }
    System.err.println("fail in inflater");
    while (true)
    {
      paramBuffer.rewind();
      return paramBuffer;
      label663: if (k == 2)
        break;
      if (k == 3)
      {
        paramBuffer.rewind();
        paramBuffer.getInt();
        paramBuffer.getShort();
        int m = paramBuffer.getInt();
        if (!JSch.getLogger().isEnabled(1))
          break;
        JSch.getLogger().log(1, "Received SSH_MSG_UNIMPLEMENTED for " + m);
        break;
      }
      if (k == 4)
      {
        paramBuffer.rewind();
        paramBuffer.getInt();
        paramBuffer.getShort();
        break;
      }
      if (k == 93)
      {
        paramBuffer.rewind();
        paramBuffer.getInt();
        paramBuffer.getShort();
        Channel localChannel = Channel.getChannel(paramBuffer.getInt(), this);
        if (localChannel == null)
          break;
        localChannel.addRemoteWindowSize(paramBuffer.getUInt());
        break;
      }
      if (k == 52)
      {
        this.isAuthed = true;
        if ((this.inflater == null) && (this.deflater == null))
        {
          initDeflater(this.guess[6]);
          initInflater(this.guess[7]);
        }
      }
    }
  }

  public void rekey()
    throws Exception
  {
    send_kexinit();
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: putfield 1277	com/jcraft/jsch/Session:thread	Ljava/lang/Runnable;
    //   5: new 271	com/jcraft/jsch/Buffer
    //   8: dup
    //   9: invokespecial 272	com/jcraft/jsch/Buffer:<init>	()V
    //   12: astore_1
    //   13: new 276	com/jcraft/jsch/Packet
    //   16: dup
    //   17: aload_1
    //   18: invokespecial 279	com/jcraft/jsch/Packet:<init>	(Lcom/jcraft/jsch/Buffer;)V
    //   21: astore_2
    //   22: iconst_1
    //   23: newarray int
    //   25: astore_3
    //   26: iconst_1
    //   27: newarray int
    //   29: astore 4
    //   31: aconst_null
    //   32: astore 5
    //   34: iconst_0
    //   35: istore 6
    //   37: aload_0
    //   38: getfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   41: ifeq +153 -> 194
    //   44: aload_0
    //   45: getfield 1277	com/jcraft/jsch/Session:thread	Ljava/lang/Runnable;
    //   48: astore 10
    //   50: aload 10
    //   52: ifnull +142 -> 194
    //   55: aload_0
    //   56: aload_1
    //   57: invokevirtual 1146	com/jcraft/jsch/Session:read	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/Buffer;
    //   60: astore 14
    //   62: aload 14
    //   64: astore_1
    //   65: sipush 255
    //   68: aload_1
    //   69: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   72: iand
    //   73: istore 15
    //   75: aload 5
    //   77: ifnull +1487 -> 1564
    //   80: aload 5
    //   82: invokevirtual 1159	com/jcraft/jsch/KeyExchange:getState	()I
    //   85: iload 15
    //   87: if_icmpne +1477 -> 1564
    //   90: aload_0
    //   91: invokestatic 949	java/lang/System:currentTimeMillis	()J
    //   94: putfield 232	com/jcraft/jsch/Session:kex_start_time	J
    //   97: aload 5
    //   99: aload_1
    //   100: invokevirtual 1163	com/jcraft/jsch/KeyExchange:next	(Lcom/jcraft/jsch/Buffer;)Z
    //   103: istore 84
    //   105: iconst_0
    //   106: istore 6
    //   108: iload 84
    //   110: ifne -73 -> 37
    //   113: new 180	com/jcraft/jsch/JSchException
    //   116: dup
    //   117: new 372	java/lang/StringBuilder
    //   120: dup
    //   121: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   124: ldc_w 1165
    //   127: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: iload 84
    //   132: invokevirtual 1168	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   135: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   138: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   141: athrow
    //   142: astore 7
    //   144: aload_0
    //   145: iconst_0
    //   146: putfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   149: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   152: iconst_1
    //   153: invokeinterface 586 2 0
    //   158: ifeq +36 -> 194
    //   161: invokestatic 580	com/jcraft/jsch/JSch:getLogger	()Lcom/jcraft/jsch/Logger;
    //   164: iconst_1
    //   165: new 372	java/lang/StringBuilder
    //   168: dup
    //   169: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   172: ldc_w 1417
    //   175: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: aload 7
    //   180: invokevirtual 1420	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   183: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   189: invokeinterface 592 3 0
    //   194: aload_0
    //   195: invokevirtual 1114	com/jcraft/jsch/Session:disconnect	()V
    //   198: aload_0
    //   199: iconst_0
    //   200: putfield 198	com/jcraft/jsch/Session:isConnected	Z
    //   203: return
    //   204: astore 11
    //   206: aload_0
    //   207: getfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   210: ifne +26 -> 236
    //   213: aload_0
    //   214: getfield 224	com/jcraft/jsch/Session:serverAliveCountMax	I
    //   217: istore 13
    //   219: iload 6
    //   221: iload 13
    //   223: if_icmpge +13 -> 236
    //   226: aload_0
    //   227: invokevirtual 1423	com/jcraft/jsch/Session:sendKeepAliveMsg	()V
    //   230: iinc 6 1
    //   233: goto -196 -> 37
    //   236: aload_0
    //   237: getfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   240: ifeq +22 -> 262
    //   243: aload_0
    //   244: getfield 224	com/jcraft/jsch/Session:serverAliveCountMax	I
    //   247: istore 12
    //   249: iload 6
    //   251: iload 12
    //   253: if_icmpge +9 -> 262
    //   256: iinc 6 1
    //   259: goto -222 -> 37
    //   262: aload 11
    //   264: athrow
    //   265: new 977	java/io/IOException
    //   268: dup
    //   269: new 372	java/lang/StringBuilder
    //   272: dup
    //   273: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   276: ldc_w 1425
    //   279: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: iload 15
    //   284: invokevirtual 382	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   287: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   290: invokespecial 1373	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   293: athrow
    //   294: aload_0
    //   295: aload_1
    //   296: invokespecial 1156	com/jcraft/jsch/Session:receive_kexinit	(Lcom/jcraft/jsch/Buffer;)Lcom/jcraft/jsch/KeyExchange;
    //   299: astore 5
    //   301: iconst_0
    //   302: istore 6
    //   304: goto -267 -> 37
    //   307: aload_0
    //   308: invokespecial 1174	com/jcraft/jsch/Session:send_newkeys	()V
    //   311: aload_0
    //   312: aload_1
    //   313: aload 5
    //   315: invokespecial 1178	com/jcraft/jsch/Session:receive_newkeys	(Lcom/jcraft/jsch/Buffer;Lcom/jcraft/jsch/KeyExchange;)V
    //   318: aconst_null
    //   319: astore 5
    //   321: iconst_0
    //   322: istore 6
    //   324: goto -287 -> 37
    //   327: aload_1
    //   328: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   331: pop
    //   332: aload_1
    //   333: invokevirtual 893	com/jcraft/jsch/Buffer:getByte	()I
    //   336: pop
    //   337: aload_1
    //   338: invokevirtual 893	com/jcraft/jsch/Buffer:getByte	()I
    //   341: pop
    //   342: aload_1
    //   343: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   346: aload_0
    //   347: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   350: astore 75
    //   352: aload_1
    //   353: aload_3
    //   354: aload 4
    //   356: invokevirtual 1428	com/jcraft/jsch/Buffer:getString	([I[I)[B
    //   359: astore 76
    //   361: iconst_0
    //   362: istore 6
    //   364: aload 75
    //   366: ifnull -329 -> 37
    //   369: aload 4
    //   371: iconst_0
    //   372: iaload
    //   373: istore 77
    //   375: iconst_0
    //   376: istore 6
    //   378: iload 77
    //   380: ifeq -343 -> 37
    //   383: aload 75
    //   385: aload 76
    //   387: aload_3
    //   388: iconst_0
    //   389: iaload
    //   390: aload 4
    //   392: iconst_0
    //   393: iaload
    //   394: invokevirtual 1430	com/jcraft/jsch/Channel:write	([BII)V
    //   397: aload 4
    //   399: iconst_0
    //   400: iaload
    //   401: istore 80
    //   403: aload 75
    //   405: aload 75
    //   407: getfield 1433	com/jcraft/jsch/Channel:lwsize	I
    //   410: iload 80
    //   412: isub
    //   413: invokevirtual 1436	com/jcraft/jsch/Channel:setLocalWindowSize	(I)V
    //   416: aload 75
    //   418: getfield 1433	com/jcraft/jsch/Channel:lwsize	I
    //   421: istore 81
    //   423: aload 75
    //   425: getfield 1439	com/jcraft/jsch/Channel:lwsize_max	I
    //   428: iconst_2
    //   429: idiv
    //   430: istore 82
    //   432: iconst_0
    //   433: istore 6
    //   435: iload 81
    //   437: iload 82
    //   439: if_icmpge -402 -> 37
    //   442: aload_2
    //   443: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   446: aload_1
    //   447: bipush 93
    //   449: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   452: aload_1
    //   453: aload 75
    //   455: invokevirtual 1442	com/jcraft/jsch/Channel:getRecipient	()I
    //   458: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   461: aload_1
    //   462: aload 75
    //   464: getfield 1439	com/jcraft/jsch/Channel:lwsize_max	I
    //   467: aload 75
    //   469: getfield 1433	com/jcraft/jsch/Channel:lwsize	I
    //   472: isub
    //   473: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   476: aload 75
    //   478: monitorenter
    //   479: aload 75
    //   481: getfield 1444	com/jcraft/jsch/Channel:close	Z
    //   484: ifne +8 -> 492
    //   487: aload_0
    //   488: aload_2
    //   489: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   492: aload 75
    //   494: monitorexit
    //   495: aload 75
    //   497: aload 75
    //   499: getfield 1439	com/jcraft/jsch/Channel:lwsize_max	I
    //   502: invokevirtual 1436	com/jcraft/jsch/Channel:setLocalWindowSize	(I)V
    //   505: iconst_0
    //   506: istore 6
    //   508: goto -471 -> 37
    //   511: astore 78
    //   513: aload 75
    //   515: invokevirtual 1445	com/jcraft/jsch/Channel:disconnect	()V
    //   518: iconst_0
    //   519: istore 6
    //   521: goto -484 -> 37
    //   524: astore 79
    //   526: iconst_0
    //   527: istore 6
    //   529: goto -492 -> 37
    //   532: astore 83
    //   534: aload 75
    //   536: monitorexit
    //   537: aload 83
    //   539: athrow
    //   540: aload_1
    //   541: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   544: pop
    //   545: aload_1
    //   546: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   549: pop
    //   550: aload_1
    //   551: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   554: aload_0
    //   555: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   558: astore 64
    //   560: aload_1
    //   561: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   564: pop
    //   565: aload_1
    //   566: aload_3
    //   567: aload 4
    //   569: invokevirtual 1428	com/jcraft/jsch/Buffer:getString	([I[I)[B
    //   572: astore 66
    //   574: iconst_0
    //   575: istore 6
    //   577: aload 64
    //   579: ifnull -542 -> 37
    //   582: aload 4
    //   584: iconst_0
    //   585: iaload
    //   586: istore 67
    //   588: iconst_0
    //   589: istore 6
    //   591: iload 67
    //   593: ifeq -556 -> 37
    //   596: aload 64
    //   598: aload 66
    //   600: aload_3
    //   601: iconst_0
    //   602: iaload
    //   603: aload 4
    //   605: iconst_0
    //   606: iaload
    //   607: invokevirtual 1448	com/jcraft/jsch/Channel:write_ext	([BII)V
    //   610: aload 4
    //   612: iconst_0
    //   613: iaload
    //   614: istore 68
    //   616: aload 64
    //   618: aload 64
    //   620: getfield 1433	com/jcraft/jsch/Channel:lwsize	I
    //   623: iload 68
    //   625: isub
    //   626: invokevirtual 1436	com/jcraft/jsch/Channel:setLocalWindowSize	(I)V
    //   629: aload 64
    //   631: getfield 1433	com/jcraft/jsch/Channel:lwsize	I
    //   634: istore 69
    //   636: aload 64
    //   638: getfield 1439	com/jcraft/jsch/Channel:lwsize_max	I
    //   641: iconst_2
    //   642: idiv
    //   643: istore 70
    //   645: iconst_0
    //   646: istore 6
    //   648: iload 69
    //   650: iload 70
    //   652: if_icmpge -615 -> 37
    //   655: aload_2
    //   656: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   659: aload_1
    //   660: bipush 93
    //   662: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   665: aload_1
    //   666: aload 64
    //   668: invokevirtual 1442	com/jcraft/jsch/Channel:getRecipient	()I
    //   671: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   674: aload_1
    //   675: aload 64
    //   677: getfield 1439	com/jcraft/jsch/Channel:lwsize_max	I
    //   680: aload 64
    //   682: getfield 1433	com/jcraft/jsch/Channel:lwsize	I
    //   685: isub
    //   686: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   689: aload 64
    //   691: monitorenter
    //   692: aload 64
    //   694: getfield 1444	com/jcraft/jsch/Channel:close	Z
    //   697: ifne +8 -> 705
    //   700: aload_0
    //   701: aload_2
    //   702: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   705: aload 64
    //   707: monitorexit
    //   708: aload 64
    //   710: aload 64
    //   712: getfield 1439	com/jcraft/jsch/Channel:lwsize_max	I
    //   715: invokevirtual 1436	com/jcraft/jsch/Channel:setLocalWindowSize	(I)V
    //   718: iconst_0
    //   719: istore 6
    //   721: goto -684 -> 37
    //   724: astore 71
    //   726: aload 64
    //   728: monitorexit
    //   729: aload 71
    //   731: athrow
    //   732: aload_1
    //   733: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   736: pop
    //   737: aload_1
    //   738: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   741: pop
    //   742: aload_1
    //   743: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   746: aload_0
    //   747: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   750: astore 61
    //   752: iconst_0
    //   753: istore 6
    //   755: aload 61
    //   757: ifnull -720 -> 37
    //   760: aload 61
    //   762: aload_1
    //   763: invokevirtual 1406	com/jcraft/jsch/Buffer:getUInt	()J
    //   766: invokevirtual 1409	com/jcraft/jsch/Channel:addRemoteWindowSize	(J)V
    //   769: iconst_0
    //   770: istore 6
    //   772: goto -735 -> 37
    //   775: aload_1
    //   776: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   779: pop
    //   780: aload_1
    //   781: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   784: pop
    //   785: aload_1
    //   786: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   789: aload_0
    //   790: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   793: astore 58
    //   795: iconst_0
    //   796: istore 6
    //   798: aload 58
    //   800: ifnull -763 -> 37
    //   803: aload 58
    //   805: invokevirtual 1451	com/jcraft/jsch/Channel:eof_remote	()V
    //   808: iconst_0
    //   809: istore 6
    //   811: goto -774 -> 37
    //   814: aload_1
    //   815: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   818: pop
    //   819: aload_1
    //   820: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   823: pop
    //   824: aload_1
    //   825: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   828: aload_0
    //   829: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   832: astore 55
    //   834: iconst_0
    //   835: istore 6
    //   837: aload 55
    //   839: ifnull -802 -> 37
    //   842: aload 55
    //   844: invokevirtual 1445	com/jcraft/jsch/Channel:disconnect	()V
    //   847: iconst_0
    //   848: istore 6
    //   850: goto -813 -> 37
    //   853: aload_1
    //   854: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   857: pop
    //   858: aload_1
    //   859: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   862: pop
    //   863: aload_1
    //   864: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   867: aload_0
    //   868: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   871: astore 48
    //   873: aload_1
    //   874: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   877: istore 49
    //   879: aload_1
    //   880: invokevirtual 1406	com/jcraft/jsch/Buffer:getUInt	()J
    //   883: lstore 50
    //   885: aload_1
    //   886: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   889: istore 52
    //   891: iconst_0
    //   892: istore 6
    //   894: aload 48
    //   896: ifnull -859 -> 37
    //   899: aload 48
    //   901: lload 50
    //   903: invokevirtual 1454	com/jcraft/jsch/Channel:setRemoteWindowSize	(J)V
    //   906: aload 48
    //   908: iload 52
    //   910: invokevirtual 1457	com/jcraft/jsch/Channel:setRemotePacketSize	(I)V
    //   913: aload 48
    //   915: iconst_1
    //   916: putfield 1460	com/jcraft/jsch/Channel:open_confirmation	Z
    //   919: aload 48
    //   921: iload 49
    //   923: invokevirtual 1463	com/jcraft/jsch/Channel:setRecipient	(I)V
    //   926: iconst_0
    //   927: istore 6
    //   929: goto -892 -> 37
    //   932: aload_1
    //   933: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   936: pop
    //   937: aload_1
    //   938: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   941: pop
    //   942: aload_1
    //   943: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   946: aload_0
    //   947: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   950: astore 45
    //   952: iconst_0
    //   953: istore 6
    //   955: aload 45
    //   957: ifnull -920 -> 37
    //   960: aload 45
    //   962: aload_1
    //   963: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   966: invokevirtual 1466	com/jcraft/jsch/Channel:setExitStatus	(I)V
    //   969: aload 45
    //   971: iconst_1
    //   972: putfield 1444	com/jcraft/jsch/Channel:close	Z
    //   975: aload 45
    //   977: iconst_1
    //   978: putfield 1468	com/jcraft/jsch/Channel:eof_remote	Z
    //   981: aload 45
    //   983: iconst_0
    //   984: invokevirtual 1463	com/jcraft/jsch/Channel:setRecipient	(I)V
    //   987: iconst_0
    //   988: istore 6
    //   990: goto -953 -> 37
    //   993: aload_1
    //   994: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   997: pop
    //   998: aload_1
    //   999: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   1002: pop
    //   1003: aload_1
    //   1004: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1007: istore 38
    //   1009: aload_1
    //   1010: invokevirtual 1385	com/jcraft/jsch/Buffer:getString	()[B
    //   1013: astore 39
    //   1015: aload_1
    //   1016: invokevirtual 893	com/jcraft/jsch/Buffer:getByte	()I
    //   1019: ifeq +685 -> 1704
    //   1022: iconst_1
    //   1023: istore 40
    //   1025: iload 38
    //   1027: aload_0
    //   1028: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   1031: astore 41
    //   1033: iconst_0
    //   1034: istore 6
    //   1036: aload 41
    //   1038: ifnull -1001 -> 37
    //   1041: bipush 100
    //   1043: istore 42
    //   1045: aload 39
    //   1047: invokestatic 732	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   1050: ldc_w 1470
    //   1053: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1056: ifeq +16 -> 1072
    //   1059: aload 41
    //   1061: aload_1
    //   1062: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1065: invokevirtual 1466	com/jcraft/jsch/Channel:setExitStatus	(I)V
    //   1068: bipush 99
    //   1070: istore 42
    //   1072: iconst_0
    //   1073: istore 6
    //   1075: iload 40
    //   1077: ifeq -1040 -> 37
    //   1080: aload_2
    //   1081: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   1084: aload_1
    //   1085: iload 42
    //   1087: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   1090: aload_1
    //   1091: aload 41
    //   1093: invokevirtual 1442	com/jcraft/jsch/Channel:getRecipient	()I
    //   1096: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   1099: aload_0
    //   1100: aload_2
    //   1101: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   1104: iconst_0
    //   1105: istore 6
    //   1107: goto -1070 -> 37
    //   1110: aload_1
    //   1111: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1114: pop
    //   1115: aload_1
    //   1116: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   1119: pop
    //   1120: aload_1
    //   1121: invokevirtual 1385	com/jcraft/jsch/Buffer:getString	()[B
    //   1124: invokestatic 732	com/jcraft/jsch/Util:byte2str	([B)Ljava/lang/String;
    //   1127: astore 33
    //   1129: ldc_w 1472
    //   1132: aload 33
    //   1134: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1137: ifne +87 -> 1224
    //   1140: ldc_w 1474
    //   1143: aload 33
    //   1145: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1148: ifeq +10 -> 1158
    //   1151: aload_0
    //   1152: getfield 206	com/jcraft/jsch/Session:x11_forwarding	Z
    //   1155: ifne +69 -> 1224
    //   1158: ldc_w 1476
    //   1161: aload 33
    //   1163: invokevirtual 498	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1166: ifeq +10 -> 1176
    //   1169: aload_0
    //   1170: getfield 208	com/jcraft/jsch/Session:agent_forwarding	Z
    //   1173: ifne +51 -> 1224
    //   1176: aload_2
    //   1177: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   1180: aload_1
    //   1181: bipush 92
    //   1183: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   1186: aload_1
    //   1187: aload_1
    //   1188: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1191: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   1194: aload_1
    //   1195: iconst_1
    //   1196: invokevirtual 347	com/jcraft/jsch/Buffer:putInt	(I)V
    //   1199: aload_1
    //   1200: getstatic 1479	com/jcraft/jsch/Util:empty	[B
    //   1203: invokevirtual 344	com/jcraft/jsch/Buffer:putString	([B)V
    //   1206: aload_1
    //   1207: getstatic 1479	com/jcraft/jsch/Util:empty	[B
    //   1210: invokevirtual 344	com/jcraft/jsch/Buffer:putString	([B)V
    //   1213: aload_0
    //   1214: aload_2
    //   1215: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   1218: iconst_0
    //   1219: istore 6
    //   1221: goto -1184 -> 37
    //   1224: aload 33
    //   1226: invokestatic 1355	com/jcraft/jsch/Channel:getChannel	(Ljava/lang/String;)Lcom/jcraft/jsch/Channel;
    //   1229: astore 34
    //   1231: aload_0
    //   1232: aload 34
    //   1234: invokevirtual 1339	com/jcraft/jsch/Session:addChannel	(Lcom/jcraft/jsch/Channel;)V
    //   1237: aload 34
    //   1239: aload_1
    //   1240: invokevirtual 1482	com/jcraft/jsch/Channel:getData	(Lcom/jcraft/jsch/Buffer;)V
    //   1243: aload 34
    //   1245: invokevirtual 1356	com/jcraft/jsch/Channel:init	()V
    //   1248: new 320	java/lang/Thread
    //   1251: dup
    //   1252: aload 34
    //   1254: invokespecial 1224	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   1257: astore 35
    //   1259: aload 35
    //   1261: new 372	java/lang/StringBuilder
    //   1264: dup
    //   1265: invokespecial 373	java/lang/StringBuilder:<init>	()V
    //   1268: ldc_w 1484
    //   1271: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1274: aload 33
    //   1276: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1279: ldc_w 834
    //   1282: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1285: aload_0
    //   1286: getfield 240	com/jcraft/jsch/Session:host	Ljava/lang/String;
    //   1289: invokevirtual 379	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1292: invokevirtual 383	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1295: invokevirtual 1231	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   1298: aload_0
    //   1299: getfield 230	com/jcraft/jsch/Session:daemon_thread	Z
    //   1302: ifeq +12 -> 1314
    //   1305: aload 35
    //   1307: aload_0
    //   1308: getfield 230	com/jcraft/jsch/Session:daemon_thread	Z
    //   1311: invokevirtual 1234	java/lang/Thread:setDaemon	(Z)V
    //   1314: aload 35
    //   1316: invokevirtual 1236	java/lang/Thread:start	()V
    //   1319: iconst_0
    //   1320: istore 6
    //   1322: goto -1285 -> 37
    //   1325: aload_1
    //   1326: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1329: pop
    //   1330: aload_1
    //   1331: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   1334: pop
    //   1335: aload_1
    //   1336: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1339: aload_0
    //   1340: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   1343: astore 30
    //   1345: iconst_0
    //   1346: istore 6
    //   1348: aload 30
    //   1350: ifnull -1313 -> 37
    //   1353: aload 30
    //   1355: iconst_1
    //   1356: putfield 1487	com/jcraft/jsch/Channel:reply	I
    //   1359: iconst_0
    //   1360: istore 6
    //   1362: goto -1325 -> 37
    //   1365: aload_1
    //   1366: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1369: pop
    //   1370: aload_1
    //   1371: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   1374: pop
    //   1375: aload_1
    //   1376: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1379: aload_0
    //   1380: invokestatic 1403	com/jcraft/jsch/Channel:getChannel	(ILcom/jcraft/jsch/Session;)Lcom/jcraft/jsch/Channel;
    //   1383: astore 27
    //   1385: iconst_0
    //   1386: istore 6
    //   1388: aload 27
    //   1390: ifnull -1353 -> 37
    //   1393: aload 27
    //   1395: iconst_0
    //   1396: putfield 1487	com/jcraft/jsch/Channel:reply	I
    //   1399: iconst_0
    //   1400: istore 6
    //   1402: goto -1365 -> 37
    //   1405: aload_1
    //   1406: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1409: pop
    //   1410: aload_1
    //   1411: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   1414: pop
    //   1415: aload_1
    //   1416: invokevirtual 1385	com/jcraft/jsch/Buffer:getString	()[B
    //   1419: pop
    //   1420: aload_1
    //   1421: invokevirtual 893	com/jcraft/jsch/Buffer:getByte	()I
    //   1424: ifeq +286 -> 1710
    //   1427: iconst_1
    //   1428: istore 24
    //   1430: iconst_0
    //   1431: istore 6
    //   1433: iload 24
    //   1435: ifeq -1398 -> 37
    //   1438: aload_2
    //   1439: invokevirtual 334	com/jcraft/jsch/Packet:reset	()V
    //   1442: aload_1
    //   1443: bipush 82
    //   1445: invokevirtual 338	com/jcraft/jsch/Buffer:putByte	(B)V
    //   1448: aload_0
    //   1449: aload_2
    //   1450: invokevirtual 351	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   1453: iconst_0
    //   1454: istore 6
    //   1456: goto -1419 -> 37
    //   1459: aload_0
    //   1460: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   1463: invokevirtual 1490	com/jcraft/jsch/Session$GlobalRequestReply:getThread	()Ljava/lang/Thread;
    //   1466: astore 16
    //   1468: iconst_0
    //   1469: istore 6
    //   1471: aload 16
    //   1473: ifnull -1436 -> 37
    //   1476: aload_0
    //   1477: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   1480: astore 17
    //   1482: iload 15
    //   1484: bipush 81
    //   1486: if_icmpne +62 -> 1548
    //   1489: iconst_1
    //   1490: istore 18
    //   1492: aload 17
    //   1494: iload 18
    //   1496: invokevirtual 1493	com/jcraft/jsch/Session$GlobalRequestReply:setReply	(I)V
    //   1499: iload 15
    //   1501: bipush 81
    //   1503: if_icmpne +34 -> 1537
    //   1506: aload_0
    //   1507: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   1510: invokevirtual 386	com/jcraft/jsch/Session$GlobalRequestReply:getPort	()I
    //   1513: ifne +24 -> 1537
    //   1516: aload_1
    //   1517: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1520: pop
    //   1521: aload_1
    //   1522: invokevirtual 1382	com/jcraft/jsch/Buffer:getShort	()I
    //   1525: pop
    //   1526: aload_0
    //   1527: getfield 265	com/jcraft/jsch/Session:grr	Lcom/jcraft/jsch/Session$GlobalRequestReply;
    //   1530: aload_1
    //   1531: invokevirtual 887	com/jcraft/jsch/Buffer:getInt	()I
    //   1534: invokevirtual 331	com/jcraft/jsch/Session$GlobalRequestReply:setPort	(I)V
    //   1537: aload 16
    //   1539: invokevirtual 1275	java/lang/Thread:interrupt	()V
    //   1542: iconst_0
    //   1543: istore 6
    //   1545: goto -1508 -> 37
    //   1548: iconst_0
    //   1549: istore 18
    //   1551: goto -59 -> 1492
    //   1554: astore 9
    //   1556: goto -1358 -> 198
    //   1559: astore 8
    //   1561: goto -1363 -> 198
    //   1564: iload 15
    //   1566: lookupswitch	default:+-1301->265, 20:+-1272->294, 21:+-1259->307, 80:+-161->1405, 81:+-107->1459, 82:+-107->1459, 90:+-456->1110, 91:+-713->853, 92:+-634->932, 93:+-834->732, 94:+-1239->327, 95:+-1026->540, 96:+-791->775, 97:+-752->814, 98:+-573->993, 99:+-241->1325, 100:+-201->1365
    //   1705: istore 40
    //   1707: goto -682 -> 1025
    //   1710: iconst_0
    //   1711: istore 24
    //   1713: goto -283 -> 1430
    //
    // Exception table:
    //   from	to	target	type
    //   37	50	142	java/lang/Exception
    //   55	62	142	java/lang/Exception
    //   65	75	142	java/lang/Exception
    //   80	105	142	java/lang/Exception
    //   113	142	142	java/lang/Exception
    //   206	219	142	java/lang/Exception
    //   226	230	142	java/lang/Exception
    //   236	249	142	java/lang/Exception
    //   262	265	142	java/lang/Exception
    //   265	294	142	java/lang/Exception
    //   294	301	142	java/lang/Exception
    //   307	318	142	java/lang/Exception
    //   327	361	142	java/lang/Exception
    //   369	375	142	java/lang/Exception
    //   397	432	142	java/lang/Exception
    //   442	479	142	java/lang/Exception
    //   495	505	142	java/lang/Exception
    //   537	540	142	java/lang/Exception
    //   540	574	142	java/lang/Exception
    //   582	588	142	java/lang/Exception
    //   596	645	142	java/lang/Exception
    //   655	692	142	java/lang/Exception
    //   708	718	142	java/lang/Exception
    //   729	732	142	java/lang/Exception
    //   732	752	142	java/lang/Exception
    //   760	769	142	java/lang/Exception
    //   775	795	142	java/lang/Exception
    //   803	808	142	java/lang/Exception
    //   814	834	142	java/lang/Exception
    //   842	847	142	java/lang/Exception
    //   853	891	142	java/lang/Exception
    //   899	926	142	java/lang/Exception
    //   932	952	142	java/lang/Exception
    //   960	987	142	java/lang/Exception
    //   993	1022	142	java/lang/Exception
    //   1025	1033	142	java/lang/Exception
    //   1045	1068	142	java/lang/Exception
    //   1080	1104	142	java/lang/Exception
    //   1110	1158	142	java/lang/Exception
    //   1158	1176	142	java/lang/Exception
    //   1176	1218	142	java/lang/Exception
    //   1224	1314	142	java/lang/Exception
    //   1314	1319	142	java/lang/Exception
    //   1325	1345	142	java/lang/Exception
    //   1353	1359	142	java/lang/Exception
    //   1365	1385	142	java/lang/Exception
    //   1393	1399	142	java/lang/Exception
    //   1405	1427	142	java/lang/Exception
    //   1438	1453	142	java/lang/Exception
    //   1459	1468	142	java/lang/Exception
    //   1476	1482	142	java/lang/Exception
    //   1492	1499	142	java/lang/Exception
    //   1506	1537	142	java/lang/Exception
    //   1537	1542	142	java/lang/Exception
    //   55	62	204	java/io/InterruptedIOException
    //   383	397	511	java/lang/Exception
    //   513	518	524	java/lang/Exception
    //   479	492	532	finally
    //   492	495	532	finally
    //   534	537	532	finally
    //   692	705	724	finally
    //   705	708	724	finally
    //   726	729	724	finally
    //   194	198	1554	java/lang/NullPointerException
    //   194	198	1559	java/lang/Exception
  }

  public void sendIgnore()
    throws Exception
  {
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)2);
    write(localPacket);
  }

  public void sendKeepAliveMsg()
    throws Exception
  {
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)80);
    localBuffer.putString(keepalivemsg);
    localBuffer.putByte((byte)1);
    write(localPacket);
  }

  public void setClientVersion(String paramString)
  {
    this.V_C = Util.str2byte(paramString);
  }

  public void setConfig(String paramString1, String paramString2)
  {
    synchronized (this.lock)
    {
      if (this.config == null)
        this.config = new Hashtable();
      this.config.put(paramString1, paramString2);
      return;
    }
  }

  public void setConfig(Hashtable paramHashtable)
  {
    synchronized (this.lock)
    {
      if (this.config == null)
        this.config = new Hashtable();
      Enumeration localEnumeration = paramHashtable.keys();
      if (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        this.config.put(str, (String)paramHashtable.get(str));
      }
    }
  }

  public void setConfig(Properties paramProperties)
  {
    setConfig(paramProperties);
  }

  public void setDaemonThread(boolean paramBoolean)
  {
    this.daemon_thread = paramBoolean;
  }

  public void setHost(String paramString)
  {
    this.host = paramString;
  }

  public void setHostKeyAlias(String paramString)
  {
    this.hostKeyAlias = paramString;
  }

  public void setHostKeyRepository(HostKeyRepository paramHostKeyRepository)
  {
    this.hostkeyRepository = paramHostKeyRepository;
  }

  public void setIdentityRepository(IdentityRepository paramIdentityRepository)
  {
    this.identityRepository = paramIdentityRepository;
  }

  public void setInputStream(InputStream paramInputStream)
  {
    this.in = paramInputStream;
  }

  public void setOutputStream(OutputStream paramOutputStream)
  {
    this.out = paramOutputStream;
  }

  public void setPassword(String paramString)
  {
    if (paramString != null)
      this.password = Util.str2byte(paramString);
  }

  public void setPassword(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      this.password = new byte[paramArrayOfByte.length];
      System.arraycopy(paramArrayOfByte, 0, this.password, 0, paramArrayOfByte.length);
    }
  }

  public void setPort(int paramInt)
  {
    this.port = paramInt;
  }

  public int setPortForwardingL(int paramInt1, String paramString, int paramInt2)
    throws JSchException
  {
    return setPortForwardingL("127.0.0.1", paramInt1, paramString, paramInt2);
  }

  public int setPortForwardingL(String paramString)
    throws JSchException
  {
    Forwarding localForwarding = parseForwarding(paramString);
    return setPortForwardingL(localForwarding.bind_address, localForwarding.port, localForwarding.host, localForwarding.hostport);
  }

  public int setPortForwardingL(String paramString1, int paramInt1, String paramString2, int paramInt2)
    throws JSchException
  {
    return setPortForwardingL(paramString1, paramInt1, paramString2, paramInt2, null);
  }

  public int setPortForwardingL(String paramString1, int paramInt1, String paramString2, int paramInt2, ServerSocketFactory paramServerSocketFactory)
    throws JSchException
  {
    return setPortForwardingL(paramString1, paramInt1, paramString2, paramInt2, paramServerSocketFactory, 0);
  }

  public int setPortForwardingL(String paramString1, int paramInt1, String paramString2, int paramInt2, ServerSocketFactory paramServerSocketFactory, int paramInt3)
    throws JSchException
  {
    PortWatcher localPortWatcher = PortWatcher.addPort(this, paramString1, paramInt1, paramString2, paramInt2, paramServerSocketFactory);
    localPortWatcher.setConnectTimeout(paramInt3);
    Thread localThread = new Thread(localPortWatcher);
    localThread.setName("PortWatcher Thread for " + paramString2);
    if (this.daemon_thread)
      localThread.setDaemon(this.daemon_thread);
    localThread.start();
    return localPortWatcher.lport;
  }

  public int setPortForwardingR(String paramString)
    throws JSchException
  {
    Forwarding localForwarding = parseForwarding(paramString);
    int i = _setPortForwardingR(localForwarding.bind_address, localForwarding.port);
    ChannelForwardedTCPIP.addPort(this, localForwarding.bind_address, localForwarding.port, i, localForwarding.host, localForwarding.hostport, null);
    return i;
  }

  public void setPortForwardingR(int paramInt, String paramString)
    throws JSchException
  {
    setPortForwardingR(null, paramInt, paramString, null);
  }

  public void setPortForwardingR(int paramInt1, String paramString, int paramInt2)
    throws JSchException
  {
    setPortForwardingR(null, paramInt1, paramString, paramInt2, (SocketFactory)null);
  }

  public void setPortForwardingR(int paramInt1, String paramString, int paramInt2, SocketFactory paramSocketFactory)
    throws JSchException
  {
    setPortForwardingR(null, paramInt1, paramString, paramInt2, paramSocketFactory);
  }

  public void setPortForwardingR(int paramInt, String paramString, Object[] paramArrayOfObject)
    throws JSchException
  {
    setPortForwardingR(null, paramInt, paramString, paramArrayOfObject);
  }

  public void setPortForwardingR(String paramString1, int paramInt1, String paramString2, int paramInt2)
    throws JSchException
  {
    setPortForwardingR(paramString1, paramInt1, paramString2, paramInt2, (SocketFactory)null);
  }

  public void setPortForwardingR(String paramString1, int paramInt1, String paramString2, int paramInt2, SocketFactory paramSocketFactory)
    throws JSchException
  {
    ChannelForwardedTCPIP.addPort(this, paramString1, paramInt1, _setPortForwardingR(paramString1, paramInt1), paramString2, paramInt2, paramSocketFactory);
  }

  public void setPortForwardingR(String paramString1, int paramInt, String paramString2, Object[] paramArrayOfObject)
    throws JSchException
  {
    ChannelForwardedTCPIP.addPort(this, paramString1, paramInt, _setPortForwardingR(paramString1, paramInt), paramString2, paramArrayOfObject);
  }

  public void setProxy(Proxy paramProxy)
  {
    this.proxy = paramProxy;
  }

  public void setServerAliveCountMax(int paramInt)
  {
    this.serverAliveCountMax = paramInt;
  }

  public void setServerAliveInterval(int paramInt)
    throws JSchException
  {
    setTimeout(paramInt);
    this.serverAliveInterval = paramInt;
  }

  public void setSocketFactory(SocketFactory paramSocketFactory)
  {
    this.socket_factory = paramSocketFactory;
  }

  public void setTimeout(int paramInt)
    throws JSchException
  {
    if (this.socket == null)
    {
      if (paramInt < 0)
        throw new JSchException("invalid timeout value");
      this.timeout = paramInt;
      return;
    }
    try
    {
      this.socket.setSoTimeout(paramInt);
      this.timeout = paramInt;
      return;
    }
    catch (Exception localException)
    {
      if ((localException instanceof Throwable))
        throw new JSchException(localException.toString(), localException);
      throw new JSchException(localException.toString());
    }
  }

  public void setUserInfo(UserInfo paramUserInfo)
  {
    this.userinfo = paramUserInfo;
  }

  void setUserName(String paramString)
  {
    this.username = paramString;
  }

  public void setX11Cookie(String paramString)
  {
    ChannelX11.setCookie(paramString);
  }

  public void setX11Host(String paramString)
  {
    ChannelX11.setHost(paramString);
  }

  public void setX11Port(int paramInt)
  {
    ChannelX11.setPort(paramInt);
  }

  public void write(Packet paramPacket)
    throws Exception
  {
    long l = getTimeout();
    while (true)
    {
      if (this.in_kex)
      {
        if ((l > 0L) && (System.currentTimeMillis() - this.kex_start_time > l))
          throw new JSchException("timeout in wating for rekeying process.");
        int i = paramPacket.buffer.getCommand();
        if ((i != 20) && (i != 21) && (i != 30) && (i != 31) && (i != 31) && (i != 32) && (i != 33) && (i != 34) && (i != 1));
      }
      else
      {
        _write(paramPacket);
        return;
      }
      try
      {
        Thread.sleep(10L);
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }
  }

  // ERROR //
  void write(Packet paramPacket, Channel paramChannel, int paramInt)
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 1581	com/jcraft/jsch/Session:getTimeout	()I
    //   4: i2l
    //   5: lstore 4
    //   7: aload_0
    //   8: getfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   11: ifeq +49 -> 60
    //   14: lload 4
    //   16: lconst_0
    //   17: lcmp
    //   18: ifle +28 -> 46
    //   21: invokestatic 949	java/lang/System:currentTimeMillis	()J
    //   24: aload_0
    //   25: getfield 232	com/jcraft/jsch/Session:kex_start_time	J
    //   28: lsub
    //   29: lload 4
    //   31: lcmp
    //   32: ifle +14 -> 46
    //   35: new 180	com/jcraft/jsch/JSchException
    //   38: dup
    //   39: ldc_w 1583
    //   42: invokespecial 305	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   45: athrow
    //   46: ldc2_w 1586
    //   49: invokestatic 361	java/lang/Thread:sleep	(J)V
    //   52: goto -45 -> 7
    //   55: astore 24
    //   57: goto -50 -> 7
    //   60: aload_2
    //   61: monitorenter
    //   62: aload_2
    //   63: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   66: lstore 7
    //   68: lload 7
    //   70: iload_3
    //   71: i2l
    //   72: lcmp
    //   73: ifge +30 -> 103
    //   76: aload_2
    //   77: iconst_1
    //   78: aload_2
    //   79: getfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   82: iadd
    //   83: putfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   86: aload_2
    //   87: ldc2_w 1595
    //   90: invokevirtual 1599	java/lang/Object:wait	(J)V
    //   93: aload_2
    //   94: iconst_m1
    //   95: aload_2
    //   96: getfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   99: iadd
    //   100: putfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   103: aload_2
    //   104: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   107: iload_3
    //   108: i2l
    //   109: lcmp
    //   110: iflt +59 -> 169
    //   113: aload_2
    //   114: aload_2
    //   115: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   118: iload_3
    //   119: i2l
    //   120: lsub
    //   121: putfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   124: aload_2
    //   125: monitorexit
    //   126: aload_0
    //   127: aload_1
    //   128: invokespecial 1585	com/jcraft/jsch/Session:_write	(Lcom/jcraft/jsch/Packet;)V
    //   131: return
    //   132: astore 23
    //   134: aload_2
    //   135: iconst_m1
    //   136: aload_2
    //   137: getfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   140: iadd
    //   141: putfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   144: goto -41 -> 103
    //   147: astore 6
    //   149: aload_2
    //   150: monitorexit
    //   151: aload 6
    //   153: athrow
    //   154: astore 22
    //   156: aload_2
    //   157: iconst_m1
    //   158: aload_2
    //   159: getfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   162: iadd
    //   163: putfield 1594	com/jcraft/jsch/Channel:notifyme	I
    //   166: aload 22
    //   168: athrow
    //   169: aload_2
    //   170: monitorexit
    //   171: aload_2
    //   172: getfield 1444	com/jcraft/jsch/Channel:close	Z
    //   175: ifne +10 -> 185
    //   178: aload_2
    //   179: invokevirtual 1601	com/jcraft/jsch/Channel:isConnected	()Z
    //   182: ifne +14 -> 196
    //   185: new 977	java/io/IOException
    //   188: dup
    //   189: ldc_w 1603
    //   192: invokespecial 1373	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   195: athrow
    //   196: iconst_m1
    //   197: istore 9
    //   199: aload_2
    //   200: monitorenter
    //   201: aload_2
    //   202: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   205: lconst_0
    //   206: lcmp
    //   207: istore 11
    //   209: iconst_0
    //   210: istore 12
    //   212: iconst_0
    //   213: istore 13
    //   215: iconst_0
    //   216: istore 14
    //   218: iload 11
    //   220: ifle +120 -> 340
    //   223: aload_2
    //   224: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   227: lstore 15
    //   229: lload 15
    //   231: iload_3
    //   232: i2l
    //   233: lcmp
    //   234: ifle +7 -> 241
    //   237: iload_3
    //   238: i2l
    //   239: lstore 15
    //   241: lload 15
    //   243: iload_3
    //   244: i2l
    //   245: lcmp
    //   246: istore 17
    //   248: iconst_0
    //   249: istore 13
    //   251: iload 17
    //   253: ifeq +51 -> 304
    //   256: lload 15
    //   258: l2i
    //   259: istore 19
    //   261: aload_0
    //   262: getfield 1031	com/jcraft/jsch/Session:c2scipher	Lcom/jcraft/jsch/Cipher;
    //   265: ifnull +123 -> 388
    //   268: aload_0
    //   269: getfield 258	com/jcraft/jsch/Session:c2scipher_size	I
    //   272: istore 20
    //   274: aload_0
    //   275: getfield 1033	com/jcraft/jsch/Session:c2smac	Lcom/jcraft/jsch/MAC;
    //   278: ifnull +117 -> 395
    //   281: aload_0
    //   282: getfield 1033	com/jcraft/jsch/Session:c2smac	Lcom/jcraft/jsch/MAC;
    //   285: invokeinterface 1021 1 0
    //   290: istore 21
    //   292: aload_1
    //   293: iload 19
    //   295: iload 20
    //   297: iload 21
    //   299: invokevirtual 1607	com/jcraft/jsch/Packet:shift	(III)I
    //   302: istore 13
    //   304: aload_1
    //   305: getfield 1297	com/jcraft/jsch/Packet:buffer	Lcom/jcraft/jsch/Buffer;
    //   308: invokevirtual 1150	com/jcraft/jsch/Buffer:getCommand	()B
    //   311: istore 12
    //   313: aload_2
    //   314: invokevirtual 1442	com/jcraft/jsch/Channel:getRecipient	()I
    //   317: istore 9
    //   319: iload_3
    //   320: i2l
    //   321: lload 15
    //   323: lsub
    //   324: l2i
    //   325: istore_3
    //   326: aload_2
    //   327: aload_2
    //   328: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   331: lload 15
    //   333: lsub
    //   334: putfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   337: iconst_1
    //   338: istore 14
    //   340: aload_2
    //   341: monitorexit
    //   342: iload 14
    //   344: ifeq +23 -> 367
    //   347: aload_0
    //   348: aload_1
    //   349: invokespecial 1585	com/jcraft/jsch/Session:_write	(Lcom/jcraft/jsch/Packet;)V
    //   352: iload_3
    //   353: ifeq -222 -> 131
    //   356: aload_1
    //   357: iload 12
    //   359: iload 9
    //   361: iload 13
    //   363: iload_3
    //   364: invokevirtual 1611	com/jcraft/jsch/Packet:unshift	(BIII)V
    //   367: aload_2
    //   368: monitorenter
    //   369: aload_0
    //   370: getfield 250	com/jcraft/jsch/Session:in_kex	Z
    //   373: ifeq +35 -> 408
    //   376: aload_2
    //   377: monitorexit
    //   378: goto -371 -> 7
    //   381: astore 18
    //   383: aload_2
    //   384: monitorexit
    //   385: aload 18
    //   387: athrow
    //   388: bipush 8
    //   390: istore 20
    //   392: goto -118 -> 274
    //   395: iconst_0
    //   396: istore 21
    //   398: goto -106 -> 292
    //   401: astore 10
    //   403: aload_2
    //   404: monitorexit
    //   405: aload 10
    //   407: athrow
    //   408: aload_2
    //   409: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   412: iload_3
    //   413: i2l
    //   414: lcmp
    //   415: iflt +19 -> 434
    //   418: aload_2
    //   419: aload_2
    //   420: getfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   423: iload_3
    //   424: i2l
    //   425: lsub
    //   426: putfield 1591	com/jcraft/jsch/Channel:rwsize	J
    //   429: aload_2
    //   430: monitorexit
    //   431: goto -305 -> 126
    //   434: aload_2
    //   435: monitorexit
    //   436: goto -429 -> 7
    //
    // Exception table:
    //   from	to	target	type
    //   46	52	55	java/lang/InterruptedException
    //   76	93	132	java/lang/InterruptedException
    //   62	68	147	finally
    //   93	103	147	finally
    //   103	126	147	finally
    //   134	144	147	finally
    //   149	151	147	finally
    //   156	169	147	finally
    //   169	171	147	finally
    //   76	93	154	finally
    //   369	378	381	finally
    //   383	385	381	finally
    //   408	431	381	finally
    //   434	436	381	finally
    //   201	209	401	finally
    //   223	229	401	finally
    //   261	274	401	finally
    //   274	292	401	finally
    //   292	304	401	finally
    //   304	319	401	finally
    //   326	337	401	finally
    //   340	342	401	finally
    //   403	405	401	finally
  }

  private class Forwarding
  {
    String bind_address = null;
    String host = null;
    int hostport = -1;
    int port = -1;

    private Forwarding()
    {
    }
  }

  private class GlobalRequestReply
  {
    private int port = 0;
    private int reply = -1;
    private Thread thread = null;

    private GlobalRequestReply()
    {
    }

    int getPort()
    {
      return this.port;
    }

    int getReply()
    {
      return this.reply;
    }

    Thread getThread()
    {
      return this.thread;
    }

    void setPort(int paramInt)
    {
      this.port = paramInt;
    }

    void setReply(int paramInt)
    {
      this.reply = paramInt;
    }

    void setThread(Thread paramThread)
    {
      this.thread = paramThread;
      this.reply = -1;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Session
 * JD-Core Version:    0.6.2
 */