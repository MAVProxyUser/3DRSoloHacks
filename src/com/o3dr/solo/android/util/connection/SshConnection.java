package com.o3dr.solo.android.util.connection;

import android.text.TextUtils;
import android.util.Log;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SshConnection
{
  private static final int CONNECTION_TIMEOUT = 15000;
  private static final String EXEC_CHANNEL_TYPE = "exec";
  private static final String TAG = SshConnection.class.getSimpleName();
  private final String host;
  private final JSch jsch = new JSch();
  private final String password;
  private final String username;

  public SshConnection(String paramString1, String paramString2, String paramString3)
  {
    this.host = paramString1;
    this.username = paramString2;
    this.password = paramString3;
  }

  private static int checkAck(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if ((i == 1) || (i == 2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int j;
      do
      {
        j = paramInputStream.read();
        localStringBuilder.append((char)j);
      }
      while (j != 10);
      if (localStringBuilder.length() > 0)
        Log.e(TAG, localStringBuilder.toString());
    }
    return i;
  }

  private Session getSession()
    throws JSchException
  {
    Session localSession = this.jsch.getSession(this.username, this.host);
    localSession.setConfig("StrictHostKeyChecking", "no");
    localSession.setPassword(this.password);
    localSession.connect(15000);
    return localSession;
  }

  public boolean downloadFile(String paramString1, String paramString2)
    throws IOException
  {
    return downloadFile(paramString1, paramString2, null);
  }

  // ERROR //
  public boolean downloadFile(String paramString1, String paramString2, DownloadListener paramDownloadListener)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +7 -> 8
    //   4: aload_2
    //   5: ifnonnull +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: aconst_null
    //   11: astore 4
    //   13: aconst_null
    //   14: astore 5
    //   16: aconst_null
    //   17: astore 6
    //   19: aconst_null
    //   20: astore 7
    //   22: new 103	java/io/File
    //   25: dup
    //   26: aload_1
    //   27: invokespecial 105	java/io/File:<init>	(Ljava/lang/String;)V
    //   30: astore 8
    //   32: aload 8
    //   34: invokevirtual 109	java/io/File:isDirectory	()Z
    //   37: istore 12
    //   39: aconst_null
    //   40: astore 13
    //   42: aconst_null
    //   43: astore 5
    //   45: aconst_null
    //   46: astore 7
    //   48: aconst_null
    //   49: astore 6
    //   51: aconst_null
    //   52: astore 4
    //   54: iload 12
    //   56: ifeq +25 -> 81
    //   59: new 54	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 55	java/lang/StringBuilder:<init>	()V
    //   66: aload_1
    //   67: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: getstatic 115	java/io/File:separator	Ljava/lang/String;
    //   73: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   79: astore 13
    //   81: aload_0
    //   82: invokespecial 117	com/o3dr/solo/android/util/connection/SshConnection:getSession	()Lcom/jcraft/jsch/Session;
    //   85: astore 4
    //   87: new 54	java/lang/StringBuilder
    //   90: dup
    //   91: invokespecial 55	java/lang/StringBuilder:<init>	()V
    //   94: ldc 119
    //   96: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: aload_2
    //   100: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   103: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   106: astore 14
    //   108: aload 4
    //   110: ldc 11
    //   112: invokevirtual 123	com/jcraft/jsch/Session:openChannel	(Ljava/lang/String;)Lcom/jcraft/jsch/Channel;
    //   115: astore 15
    //   117: aload 15
    //   119: checkcast 125	com/jcraft/jsch/ChannelExec
    //   122: aload 14
    //   124: invokevirtual 128	com/jcraft/jsch/ChannelExec:setCommand	(Ljava/lang/String;)V
    //   127: aload 15
    //   129: invokevirtual 134	com/jcraft/jsch/Channel:getOutputStream	()Ljava/io/OutputStream;
    //   132: astore 6
    //   134: aload 15
    //   136: invokevirtual 138	com/jcraft/jsch/Channel:getInputStream	()Ljava/io/InputStream;
    //   139: astore 7
    //   141: aload 15
    //   143: invokevirtual 140	com/jcraft/jsch/Channel:connect	()V
    //   146: sipush 1024
    //   149: newarray byte
    //   151: astore 16
    //   153: aload 16
    //   155: iconst_0
    //   156: iconst_0
    //   157: bastore
    //   158: aload 6
    //   160: aload 16
    //   162: iconst_0
    //   163: iconst_1
    //   164: invokevirtual 146	java/io/OutputStream:write	([BII)V
    //   167: aload 6
    //   169: invokevirtual 149	java/io/OutputStream:flush	()V
    //   172: aload 7
    //   174: invokestatic 151	com/o3dr/solo/android/util/connection/SshConnection:checkAck	(Ljava/io/InputStream;)I
    //   177: istore 17
    //   179: iload 17
    //   181: bipush 67
    //   183: if_icmpeq +66 -> 249
    //   186: iconst_0
    //   187: ifeq +7 -> 194
    //   190: aconst_null
    //   191: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   194: aload 6
    //   196: ifnull +8 -> 204
    //   199: aload 6
    //   201: invokevirtual 157	java/io/OutputStream:close	()V
    //   204: aload 7
    //   206: ifnull +8 -> 214
    //   209: aload 7
    //   211: invokevirtual 158	java/io/InputStream:close	()V
    //   214: iconst_0
    //   215: ifeq +14 -> 229
    //   218: aconst_null
    //   219: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   222: ifeq +7 -> 229
    //   225: aconst_null
    //   226: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   229: aload 4
    //   231: ifnull -223 -> 8
    //   234: aload 4
    //   236: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   239: ifeq -231 -> 8
    //   242: aload 4
    //   244: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   247: iconst_0
    //   248: ireturn
    //   249: aload 7
    //   251: aload 16
    //   253: iconst_0
    //   254: iconst_5
    //   255: invokevirtual 169	java/io/InputStream:read	([BII)I
    //   258: pop
    //   259: lconst_0
    //   260: lstore 19
    //   262: aload 7
    //   264: aload 16
    //   266: iconst_0
    //   267: iconst_1
    //   268: invokevirtual 169	java/io/InputStream:read	([BII)I
    //   271: istore 21
    //   273: iload 21
    //   275: ifge +66 -> 341
    //   278: iconst_0
    //   279: ifeq +7 -> 286
    //   282: aconst_null
    //   283: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   286: aload 6
    //   288: ifnull +8 -> 296
    //   291: aload 6
    //   293: invokevirtual 157	java/io/OutputStream:close	()V
    //   296: aload 7
    //   298: ifnull +8 -> 306
    //   301: aload 7
    //   303: invokevirtual 158	java/io/InputStream:close	()V
    //   306: iconst_0
    //   307: ifeq +14 -> 321
    //   310: aconst_null
    //   311: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   314: ifeq +7 -> 321
    //   317: aconst_null
    //   318: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   321: aload 4
    //   323: ifnull -315 -> 8
    //   326: aload 4
    //   328: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   331: ifeq -323 -> 8
    //   334: aload 4
    //   336: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   339: iconst_0
    //   340: ireturn
    //   341: aload 16
    //   343: iconst_0
    //   344: baload
    //   345: bipush 32
    //   347: if_icmpne +198 -> 545
    //   350: iconst_0
    //   351: istore 22
    //   353: aload 7
    //   355: aload 16
    //   357: iload 22
    //   359: iconst_1
    //   360: invokevirtual 169	java/io/InputStream:read	([BII)I
    //   363: pop
    //   364: aload 16
    //   366: iload 22
    //   368: baload
    //   369: istore 24
    //   371: aconst_null
    //   372: astore 5
    //   374: iload 24
    //   376: bipush 10
    //   378: if_icmpne +552 -> 930
    //   381: new 171	java/lang/String
    //   384: dup
    //   385: aload 16
    //   387: iconst_0
    //   388: iload 22
    //   390: invokespecial 173	java/lang/String:<init>	([BII)V
    //   393: astore 25
    //   395: aload_3
    //   396: ifnull +11 -> 407
    //   399: aload_3
    //   400: lload 19
    //   402: invokeinterface 179 3 0
    //   407: aload 16
    //   409: iconst_0
    //   410: iconst_0
    //   411: bastore
    //   412: aload 6
    //   414: aload 16
    //   416: iconst_0
    //   417: iconst_1
    //   418: invokevirtual 146	java/io/OutputStream:write	([BII)V
    //   421: aload 6
    //   423: invokevirtual 149	java/io/OutputStream:flush	()V
    //   426: aload 13
    //   428: ifnonnull +141 -> 569
    //   431: aload_1
    //   432: astore 27
    //   434: new 153	java/io/FileOutputStream
    //   437: dup
    //   438: aload 27
    //   440: invokespecial 180	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   443: astore 28
    //   445: lconst_0
    //   446: lstore 29
    //   448: aload 16
    //   450: arraylength
    //   451: i2l
    //   452: lload 19
    //   454: lcmp
    //   455: ifge +143 -> 598
    //   458: aload 16
    //   460: arraylength
    //   461: istore 31
    //   463: aload 7
    //   465: aload 16
    //   467: iconst_0
    //   468: iload 31
    //   470: invokevirtual 169	java/io/InputStream:read	([BII)I
    //   473: istore 32
    //   475: iload 32
    //   477: ifge +129 -> 606
    //   480: aload 28
    //   482: ifnull +8 -> 490
    //   485: aload 28
    //   487: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   490: aload 6
    //   492: ifnull +8 -> 500
    //   495: aload 6
    //   497: invokevirtual 157	java/io/OutputStream:close	()V
    //   500: aload 7
    //   502: ifnull +8 -> 510
    //   505: aload 7
    //   507: invokevirtual 158	java/io/InputStream:close	()V
    //   510: iconst_0
    //   511: ifeq +14 -> 525
    //   514: aconst_null
    //   515: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   518: ifeq +7 -> 525
    //   521: aconst_null
    //   522: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   525: aload 4
    //   527: ifnull -519 -> 8
    //   530: aload 4
    //   532: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   535: ifeq -527 -> 8
    //   538: aload 4
    //   540: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   543: iconst_0
    //   544: ireturn
    //   545: ldc2_w 181
    //   548: lload 19
    //   550: lmul
    //   551: lstore 34
    //   553: lload 34
    //   555: bipush 208
    //   557: aload 16
    //   559: iconst_0
    //   560: baload
    //   561: iadd
    //   562: i2l
    //   563: ladd
    //   564: lstore 19
    //   566: goto -304 -> 262
    //   569: new 54	java/lang/StringBuilder
    //   572: dup
    //   573: invokespecial 55	java/lang/StringBuilder:<init>	()V
    //   576: aload 13
    //   578: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   581: aload 25
    //   583: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   586: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   589: astore 26
    //   591: aload 26
    //   593: astore 27
    //   595: goto -161 -> 434
    //   598: lload 19
    //   600: l2i
    //   601: istore 31
    //   603: goto -140 -> 463
    //   606: lload 29
    //   608: iload 32
    //   610: i2l
    //   611: ladd
    //   612: lstore 29
    //   614: aload 28
    //   616: aload 16
    //   618: iconst_0
    //   619: iload 32
    //   621: invokevirtual 183	java/io/FileOutputStream:write	([BII)V
    //   624: lload 19
    //   626: iload 32
    //   628: i2l
    //   629: lsub
    //   630: lstore 19
    //   632: lload 19
    //   634: lconst_0
    //   635: lcmp
    //   636: ifne +86 -> 722
    //   639: aload 28
    //   641: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   644: aconst_null
    //   645: astore 5
    //   647: aload 7
    //   649: invokestatic 151	com/o3dr/solo/android/util/connection/SshConnection:checkAck	(Ljava/io/InputStream;)I
    //   652: istore 33
    //   654: iload 33
    //   656: ifeq +170 -> 826
    //   659: iconst_0
    //   660: ifeq +7 -> 667
    //   663: aconst_null
    //   664: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   667: aload 6
    //   669: ifnull +8 -> 677
    //   672: aload 6
    //   674: invokevirtual 157	java/io/OutputStream:close	()V
    //   677: aload 7
    //   679: ifnull +8 -> 687
    //   682: aload 7
    //   684: invokevirtual 158	java/io/InputStream:close	()V
    //   687: iconst_0
    //   688: ifeq +14 -> 702
    //   691: aconst_null
    //   692: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   695: ifeq +7 -> 702
    //   698: aconst_null
    //   699: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   702: aload 4
    //   704: ifnull -696 -> 8
    //   707: aload 4
    //   709: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   712: ifeq -704 -> 8
    //   715: aload 4
    //   717: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   720: iconst_0
    //   721: ireturn
    //   722: aload_3
    //   723: ifnull -275 -> 448
    //   726: aload_3
    //   727: aload_1
    //   728: lload 29
    //   730: invokeinterface 187 4 0
    //   735: goto -287 -> 448
    //   738: astore 9
    //   740: aload 28
    //   742: astore 5
    //   744: new 46	java/io/IOException
    //   747: dup
    //   748: aload 9
    //   750: invokespecial 190	java/io/IOException:<init>	(Ljava/lang/Throwable;)V
    //   753: astore 10
    //   755: aload 10
    //   757: athrow
    //   758: astore 11
    //   760: aload 5
    //   762: ifnull +8 -> 770
    //   765: aload 5
    //   767: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   770: aload 6
    //   772: ifnull +8 -> 780
    //   775: aload 6
    //   777: invokevirtual 157	java/io/OutputStream:close	()V
    //   780: aload 7
    //   782: ifnull +8 -> 790
    //   785: aload 7
    //   787: invokevirtual 158	java/io/InputStream:close	()V
    //   790: iconst_0
    //   791: ifeq +14 -> 805
    //   794: aconst_null
    //   795: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   798: ifeq +7 -> 805
    //   801: aconst_null
    //   802: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   805: aload 4
    //   807: ifnull +16 -> 823
    //   810: aload 4
    //   812: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   815: ifeq +8 -> 823
    //   818: aload 4
    //   820: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   823: aload 11
    //   825: athrow
    //   826: aload 16
    //   828: iconst_0
    //   829: iconst_0
    //   830: bastore
    //   831: aload 6
    //   833: aload 16
    //   835: iconst_0
    //   836: iconst_1
    //   837: invokevirtual 146	java/io/OutputStream:write	([BII)V
    //   840: aload 6
    //   842: invokevirtual 149	java/io/OutputStream:flush	()V
    //   845: aload 4
    //   847: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   850: iconst_0
    //   851: ifeq +7 -> 858
    //   854: aconst_null
    //   855: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   858: aload 6
    //   860: ifnull +8 -> 868
    //   863: aload 6
    //   865: invokevirtual 157	java/io/OutputStream:close	()V
    //   868: aload 7
    //   870: ifnull +8 -> 878
    //   873: aload 7
    //   875: invokevirtual 158	java/io/InputStream:close	()V
    //   878: iconst_0
    //   879: ifeq +14 -> 893
    //   882: aconst_null
    //   883: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   886: ifeq +7 -> 893
    //   889: aconst_null
    //   890: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   893: aload 4
    //   895: ifnull +16 -> 911
    //   898: aload 4
    //   900: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   903: ifeq +8 -> 911
    //   906: aload 4
    //   908: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   911: iconst_1
    //   912: ireturn
    //   913: astore 11
    //   915: aload 28
    //   917: astore 5
    //   919: goto -159 -> 760
    //   922: astore 9
    //   924: aconst_null
    //   925: astore 5
    //   927: goto -183 -> 744
    //   930: iinc 22 1
    //   933: goto -580 -> 353
    //
    // Exception table:
    //   from	to	target	type
    //   448	463	738	com/jcraft/jsch/JSchException
    //   463	475	738	com/jcraft/jsch/JSchException
    //   614	624	738	com/jcraft/jsch/JSchException
    //   639	644	738	com/jcraft/jsch/JSchException
    //   726	735	738	com/jcraft/jsch/JSchException
    //   22	39	758	finally
    //   59	81	758	finally
    //   81	179	758	finally
    //   249	259	758	finally
    //   262	273	758	finally
    //   341	350	758	finally
    //   353	371	758	finally
    //   381	395	758	finally
    //   399	407	758	finally
    //   407	426	758	finally
    //   434	445	758	finally
    //   553	566	758	finally
    //   569	591	758	finally
    //   647	654	758	finally
    //   744	758	758	finally
    //   826	850	758	finally
    //   448	463	913	finally
    //   463	475	913	finally
    //   614	624	913	finally
    //   639	644	913	finally
    //   726	735	913	finally
    //   22	39	922	com/jcraft/jsch/JSchException
    //   59	81	922	com/jcraft/jsch/JSchException
    //   81	179	922	com/jcraft/jsch/JSchException
    //   249	259	922	com/jcraft/jsch/JSchException
    //   262	273	922	com/jcraft/jsch/JSchException
    //   341	350	922	com/jcraft/jsch/JSchException
    //   353	371	922	com/jcraft/jsch/JSchException
    //   381	395	922	com/jcraft/jsch/JSchException
    //   399	407	922	com/jcraft/jsch/JSchException
    //   407	426	922	com/jcraft/jsch/JSchException
    //   434	445	922	com/jcraft/jsch/JSchException
    //   553	566	922	com/jcraft/jsch/JSchException
    //   569	591	922	com/jcraft/jsch/JSchException
    //   647	654	922	com/jcraft/jsch/JSchException
    //   826	850	922	com/jcraft/jsch/JSchException
  }

  public String execute(String paramString)
    throws IOException
  {
    boolean bool = TextUtils.isEmpty(paramString);
    Object localObject1 = null;
    if (bool);
    while (true)
    {
      return localObject1;
      Session localSession = null;
      Channel localChannel = null;
      try
      {
        localSession = getSession();
        localChannel = localSession.openChannel("exec");
        ((ChannelExec)localChannel).setCommand(paramString);
        localChannel.setInputStream(null);
        InputStream localInputStream = localChannel.getInputStream();
        localChannel.connect(15000);
        StringBuilder localStringBuilder = new StringBuilder();
        byte[] arrayOfByte = new byte[1024];
        while (true)
        {
          int i;
          if (localInputStream.available() > 0)
          {
            i = localInputStream.read(arrayOfByte, 0, 1024);
            if (i >= 0);
          }
          else
          {
            if ((!localChannel.isClosed()) || (localInputStream.available() > 0))
              continue;
            Log.d(TAG, "SSH command exit status: " + localChannel.getExitStatus());
            String str = localStringBuilder.toString();
            localObject1 = str;
            return localObject1;
          }
          localStringBuilder.append(new String(arrayOfByte, 0, i));
        }
      }
      catch (JSchException localJSchException)
      {
        throw new IOException(localJSchException);
      }
      finally
      {
        if ((localChannel != null) && (localChannel.isConnected()))
          localChannel.disconnect();
        if ((localSession != null) && (localSession.isConnected()))
          localSession.disconnect();
      }
    }
  }

  // ERROR //
  public boolean ping()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 117	com/o3dr/solo/android/util/connection/SshConnection:getSession	()Lcom/jcraft/jsch/Session;
    //   4: astore 5
    //   6: iconst_1
    //   7: istore_3
    //   8: aload 5
    //   10: ifnull +16 -> 26
    //   13: aload 5
    //   15: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   18: ifeq +8 -> 26
    //   21: aload 5
    //   23: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   26: iload_3
    //   27: ireturn
    //   28: astore_2
    //   29: iconst_0
    //   30: istore_3
    //   31: iconst_0
    //   32: ifeq -6 -> 26
    //   35: aconst_null
    //   36: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   39: istore 4
    //   41: iconst_0
    //   42: istore_3
    //   43: iload 4
    //   45: ifeq -19 -> 26
    //   48: aconst_null
    //   49: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   52: iconst_0
    //   53: ireturn
    //   54: astore_1
    //   55: iconst_0
    //   56: ifeq +14 -> 70
    //   59: aconst_null
    //   60: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   63: ifeq +7 -> 70
    //   66: aconst_null
    //   67: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   70: aload_1
    //   71: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	28	com/jcraft/jsch/JSchException
    //   0	6	54	finally
  }

  // ERROR //
  public boolean uploadFile(File paramFile, String paramString, UploadListener paramUploadListener)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +23 -> 24
    //   4: aload_1
    //   5: invokevirtual 226	java/io/File:isFile	()Z
    //   8: ifeq +16 -> 24
    //   11: aload_3
    //   12: ifnull +18 -> 30
    //   15: aload_3
    //   16: invokeinterface 231 1 0
    //   21: ifne +9 -> 30
    //   24: iconst_0
    //   25: istore 4
    //   27: iload 4
    //   29: ireturn
    //   30: aconst_null
    //   31: astore 5
    //   33: aconst_null
    //   34: astore 6
    //   36: aconst_null
    //   37: astore 7
    //   39: aconst_null
    //   40: astore 8
    //   42: aconst_null
    //   43: astore 9
    //   45: aload_0
    //   46: invokespecial 117	com/o3dr/solo/android/util/connection/SshConnection:getSession	()Lcom/jcraft/jsch/Session;
    //   49: astore 5
    //   51: new 54	java/lang/StringBuilder
    //   54: dup
    //   55: invokespecial 55	java/lang/StringBuilder:<init>	()V
    //   58: ldc 233
    //   60: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: aload_2
    //   64: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: astore 12
    //   72: aload 5
    //   74: ldc 11
    //   76: invokevirtual 123	com/jcraft/jsch/Session:openChannel	(Ljava/lang/String;)Lcom/jcraft/jsch/Channel;
    //   79: astore 6
    //   81: aload 6
    //   83: checkcast 125	com/jcraft/jsch/ChannelExec
    //   86: aload 12
    //   88: invokevirtual 128	com/jcraft/jsch/ChannelExec:setCommand	(Ljava/lang/String;)V
    //   91: aload 6
    //   93: invokevirtual 134	com/jcraft/jsch/Channel:getOutputStream	()Ljava/io/OutputStream;
    //   96: astore 8
    //   98: aload 6
    //   100: invokevirtual 138	com/jcraft/jsch/Channel:getInputStream	()Ljava/io/InputStream;
    //   103: astore 9
    //   105: aload 6
    //   107: sipush 15000
    //   110: invokevirtual 203	com/jcraft/jsch/Channel:connect	(I)V
    //   113: aload 9
    //   115: invokestatic 151	com/o3dr/solo/android/util/connection/SshConnection:checkAck	(Ljava/io/InputStream;)I
    //   118: istore 13
    //   120: iload 13
    //   122: ifeq +79 -> 201
    //   125: iconst_0
    //   126: ifeq +7 -> 133
    //   129: aconst_null
    //   130: invokevirtual 236	java/io/FileInputStream:close	()V
    //   133: aload 8
    //   135: ifnull +8 -> 143
    //   138: aload 8
    //   140: invokevirtual 157	java/io/OutputStream:close	()V
    //   143: aload 9
    //   145: ifnull +8 -> 153
    //   148: aload 9
    //   150: invokevirtual 158	java/io/InputStream:close	()V
    //   153: aload 6
    //   155: ifnull +16 -> 171
    //   158: aload 6
    //   160: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   163: ifeq +8 -> 171
    //   166: aload 6
    //   168: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   171: iconst_0
    //   172: istore 4
    //   174: aload 5
    //   176: ifnull -149 -> 27
    //   179: aload 5
    //   181: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   184: istore 30
    //   186: iconst_0
    //   187: istore 4
    //   189: iload 30
    //   191: ifeq -164 -> 27
    //   194: aload 5
    //   196: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   199: iconst_0
    //   200: ireturn
    //   201: aload_3
    //   202: ifnull +92 -> 294
    //   205: aload_3
    //   206: invokeinterface 231 1 0
    //   211: istore 28
    //   213: iload 28
    //   215: ifne +79 -> 294
    //   218: iconst_0
    //   219: ifeq +7 -> 226
    //   222: aconst_null
    //   223: invokevirtual 236	java/io/FileInputStream:close	()V
    //   226: aload 8
    //   228: ifnull +8 -> 236
    //   231: aload 8
    //   233: invokevirtual 157	java/io/OutputStream:close	()V
    //   236: aload 9
    //   238: ifnull +8 -> 246
    //   241: aload 9
    //   243: invokevirtual 158	java/io/InputStream:close	()V
    //   246: aload 6
    //   248: ifnull +16 -> 264
    //   251: aload 6
    //   253: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   256: ifeq +8 -> 264
    //   259: aload 6
    //   261: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   264: iconst_0
    //   265: istore 4
    //   267: aload 5
    //   269: ifnull -242 -> 27
    //   272: aload 5
    //   274: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   277: istore 29
    //   279: iconst_0
    //   280: istore 4
    //   282: iload 29
    //   284: ifeq -257 -> 27
    //   287: aload 5
    //   289: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   292: iconst_0
    //   293: ireturn
    //   294: aload_1
    //   295: invokevirtual 239	java/io/File:length	()J
    //   298: lstore 14
    //   300: new 54	java/lang/StringBuilder
    //   303: dup
    //   304: invokespecial 55	java/lang/StringBuilder:<init>	()V
    //   307: ldc 241
    //   309: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: lload 14
    //   314: invokevirtual 244	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   317: ldc 246
    //   319: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: aload_1
    //   323: invokevirtual 249	java/io/File:getName	()Ljava/lang/String;
    //   326: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   329: ldc 251
    //   331: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   334: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   337: invokevirtual 255	java/lang/String:getBytes	()[B
    //   340: astore 16
    //   342: aload 8
    //   344: aload 16
    //   346: invokevirtual 258	java/io/OutputStream:write	([B)V
    //   349: aload 8
    //   351: invokevirtual 149	java/io/OutputStream:flush	()V
    //   354: aload 9
    //   356: invokestatic 151	com/o3dr/solo/android/util/connection/SshConnection:checkAck	(Ljava/io/InputStream;)I
    //   359: istore 17
    //   361: iload 17
    //   363: ifeq +79 -> 442
    //   366: iconst_0
    //   367: ifeq +7 -> 374
    //   370: aconst_null
    //   371: invokevirtual 236	java/io/FileInputStream:close	()V
    //   374: aload 8
    //   376: ifnull +8 -> 384
    //   379: aload 8
    //   381: invokevirtual 157	java/io/OutputStream:close	()V
    //   384: aload 9
    //   386: ifnull +8 -> 394
    //   389: aload 9
    //   391: invokevirtual 158	java/io/InputStream:close	()V
    //   394: aload 6
    //   396: ifnull +16 -> 412
    //   399: aload 6
    //   401: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   404: ifeq +8 -> 412
    //   407: aload 6
    //   409: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   412: iconst_0
    //   413: istore 4
    //   415: aload 5
    //   417: ifnull -390 -> 27
    //   420: aload 5
    //   422: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   425: istore 27
    //   427: iconst_0
    //   428: istore 4
    //   430: iload 27
    //   432: ifeq -405 -> 27
    //   435: aload 5
    //   437: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   440: iconst_0
    //   441: ireturn
    //   442: new 235	java/io/FileInputStream
    //   445: dup
    //   446: aload_1
    //   447: invokespecial 261	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   450: astore 18
    //   452: sipush 8192
    //   455: newarray byte
    //   457: astore 19
    //   459: lconst_0
    //   460: lstore 20
    //   462: aload 18
    //   464: aload 19
    //   466: iconst_0
    //   467: sipush 8192
    //   470: invokevirtual 262	java/io/FileInputStream:read	([BII)I
    //   473: istore 22
    //   475: iload 22
    //   477: ifgt +104 -> 581
    //   480: aload 8
    //   482: iconst_0
    //   483: invokevirtual 264	java/io/OutputStream:write	(I)V
    //   486: aload 8
    //   488: invokevirtual 149	java/io/OutputStream:flush	()V
    //   491: aload 9
    //   493: invokestatic 151	com/o3dr/solo/android/util/connection/SshConnection:checkAck	(Ljava/io/InputStream;)I
    //   496: istore 23
    //   498: iload 23
    //   500: ifeq +205 -> 705
    //   503: aload 18
    //   505: ifnull +8 -> 513
    //   508: aload 18
    //   510: invokevirtual 236	java/io/FileInputStream:close	()V
    //   513: aload 8
    //   515: ifnull +8 -> 523
    //   518: aload 8
    //   520: invokevirtual 157	java/io/OutputStream:close	()V
    //   523: aload 9
    //   525: ifnull +8 -> 533
    //   528: aload 9
    //   530: invokevirtual 158	java/io/InputStream:close	()V
    //   533: aload 6
    //   535: ifnull +16 -> 551
    //   538: aload 6
    //   540: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   543: ifeq +8 -> 551
    //   546: aload 6
    //   548: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   551: iconst_0
    //   552: istore 4
    //   554: aload 5
    //   556: ifnull -529 -> 27
    //   559: aload 5
    //   561: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   564: istore 24
    //   566: iconst_0
    //   567: istore 4
    //   569: iload 24
    //   571: ifeq -544 -> 27
    //   574: aload 5
    //   576: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   579: iconst_0
    //   580: ireturn
    //   581: aload 8
    //   583: aload 19
    //   585: iconst_0
    //   586: iload 22
    //   588: invokevirtual 146	java/io/OutputStream:write	([BII)V
    //   591: lload 20
    //   593: iload 22
    //   595: i2l
    //   596: ladd
    //   597: lstore 20
    //   599: aload_3
    //   600: ifnull -138 -> 462
    //   603: aload_3
    //   604: aload_1
    //   605: lload 20
    //   607: lload 14
    //   609: invokeinterface 268 6 0
    //   614: aload_3
    //   615: invokeinterface 231 1 0
    //   620: istore 25
    //   622: iload 25
    //   624: ifne -162 -> 462
    //   627: aload 18
    //   629: ifnull +8 -> 637
    //   632: aload 18
    //   634: invokevirtual 236	java/io/FileInputStream:close	()V
    //   637: aload 8
    //   639: ifnull +8 -> 647
    //   642: aload 8
    //   644: invokevirtual 157	java/io/OutputStream:close	()V
    //   647: aload 9
    //   649: ifnull +8 -> 657
    //   652: aload 9
    //   654: invokevirtual 158	java/io/InputStream:close	()V
    //   657: aload 6
    //   659: ifnull +16 -> 675
    //   662: aload 6
    //   664: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   667: ifeq +8 -> 675
    //   670: aload 6
    //   672: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   675: iconst_0
    //   676: istore 4
    //   678: aload 5
    //   680: ifnull -653 -> 27
    //   683: aload 5
    //   685: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   688: istore 26
    //   690: iconst_0
    //   691: istore 4
    //   693: iload 26
    //   695: ifeq -668 -> 27
    //   698: aload 5
    //   700: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   703: iconst_0
    //   704: ireturn
    //   705: iconst_1
    //   706: istore 4
    //   708: aload 18
    //   710: ifnull +8 -> 718
    //   713: aload 18
    //   715: invokevirtual 236	java/io/FileInputStream:close	()V
    //   718: aload 8
    //   720: ifnull +8 -> 728
    //   723: aload 8
    //   725: invokevirtual 157	java/io/OutputStream:close	()V
    //   728: aload 9
    //   730: ifnull +8 -> 738
    //   733: aload 9
    //   735: invokevirtual 158	java/io/InputStream:close	()V
    //   738: aload 6
    //   740: ifnull +16 -> 756
    //   743: aload 6
    //   745: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   748: ifeq +8 -> 756
    //   751: aload 6
    //   753: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   756: aload 5
    //   758: ifnull -731 -> 27
    //   761: aload 5
    //   763: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   766: ifeq -739 -> 27
    //   769: aload 5
    //   771: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   774: iload 4
    //   776: ireturn
    //   777: astore 11
    //   779: new 46	java/io/IOException
    //   782: dup
    //   783: aload 11
    //   785: invokespecial 190	java/io/IOException:<init>	(Ljava/lang/Throwable;)V
    //   788: athrow
    //   789: astore 10
    //   791: aload 7
    //   793: ifnull +8 -> 801
    //   796: aload 7
    //   798: invokevirtual 236	java/io/FileInputStream:close	()V
    //   801: aload 8
    //   803: ifnull +8 -> 811
    //   806: aload 8
    //   808: invokevirtual 157	java/io/OutputStream:close	()V
    //   811: aload 9
    //   813: ifnull +8 -> 821
    //   816: aload 9
    //   818: invokevirtual 158	java/io/InputStream:close	()V
    //   821: aload 6
    //   823: ifnull +16 -> 839
    //   826: aload 6
    //   828: invokevirtual 161	com/jcraft/jsch/Channel:isConnected	()Z
    //   831: ifeq +8 -> 839
    //   834: aload 6
    //   836: invokevirtual 164	com/jcraft/jsch/Channel:disconnect	()V
    //   839: aload 5
    //   841: ifnull +16 -> 857
    //   844: aload 5
    //   846: invokevirtual 165	com/jcraft/jsch/Session:isConnected	()Z
    //   849: ifeq +8 -> 857
    //   852: aload 5
    //   854: invokevirtual 166	com/jcraft/jsch/Session:disconnect	()V
    //   857: aload 10
    //   859: athrow
    //   860: astore 10
    //   862: aload 18
    //   864: astore 7
    //   866: goto -75 -> 791
    //   869: astore 11
    //   871: aload 18
    //   873: astore 7
    //   875: goto -96 -> 779
    //
    // Exception table:
    //   from	to	target	type
    //   45	120	777	com/jcraft/jsch/JSchException
    //   205	213	777	com/jcraft/jsch/JSchException
    //   294	361	777	com/jcraft/jsch/JSchException
    //   442	452	777	com/jcraft/jsch/JSchException
    //   45	120	789	finally
    //   205	213	789	finally
    //   294	361	789	finally
    //   442	452	789	finally
    //   779	789	789	finally
    //   452	459	860	finally
    //   462	475	860	finally
    //   480	498	860	finally
    //   581	591	860	finally
    //   603	622	860	finally
    //   452	459	869	com/jcraft/jsch/JSchException
    //   462	475	869	com/jcraft/jsch/JSchException
    //   480	498	869	com/jcraft/jsch/JSchException
    //   581	591	869	com/jcraft/jsch/JSchException
    //   603	622	869	com/jcraft/jsch/JSchException
  }

  public static abstract interface DownloadListener
  {
    public abstract void onDownloaded(String paramString, long paramLong);

    public abstract void onFileSizeCalculated(long paramLong);
  }

  public static abstract interface UploadListener
  {
    public abstract void onUploaded(File paramFile, long paramLong1, long paramLong2);

    public abstract boolean shouldContinueUpload();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.connection.SshConnection
 * JD-Core Version:    0.6.2
 */