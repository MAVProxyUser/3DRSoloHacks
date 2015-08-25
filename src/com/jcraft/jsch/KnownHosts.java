package com.jcraft.jsch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;

public class KnownHosts
  implements HostKeyRepository
{
  private static final String _known_hosts = "known_hosts";
  private static final byte[] cr = Util.str2byte("\n");
  private static final byte[] space = { 32 };
  private MAC hmacsha1 = null;
  private JSch jsch = null;
  private String known_hosts = null;
  private Vector pool = null;

  KnownHosts(JSch paramJSch)
  {
    this.jsch = paramJSch;
    this.pool = new Vector();
  }

  private void addInvalidLine(String paramString)
    throws JSchException
  {
    HostKey localHostKey = new HostKey(paramString, 3, null);
    this.pool.addElement(localHostKey);
  }

  private String deleteSubString(String paramString1, String paramString2)
  {
    int i = 0;
    int j = paramString2.length();
    int k = paramString1.length();
    int n;
    if (i < k)
    {
      n = paramString1.indexOf(',', i);
      if (n != -1);
    }
    else if ((paramString1.endsWith(paramString2)) && (k - i == j))
    {
      if (j != k)
        break label125;
    }
    label125: for (int m = 0; ; m = -1 + (k - j))
    {
      paramString1 = paramString1.substring(0, m);
      return paramString1;
      if (!paramString2.equals(paramString1.substring(i, n)))
      {
        i = n + 1;
        break;
      }
      return paramString1.substring(0, i) + paramString1.substring(n + 1);
    }
  }

  private MAC getHMACSHA1()
  {
    try
    {
      MAC localMAC1 = this.hmacsha1;
      if (localMAC1 == null);
      try
      {
        this.hmacsha1 = ((MAC)Class.forName(JSch.getConfig("hmac-sha1")).newInstance());
        MAC localMAC2 = this.hmacsha1;
        return localMAC2;
      }
      catch (Exception localException)
      {
        while (true)
          System.err.println("hmacsha1: " + localException);
      }
    }
    finally
    {
    }
  }

  private int getType(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte[8] == 100)
      return 1;
    if (paramArrayOfByte[8] == 114)
      return 2;
    return 3;
  }

  // ERROR //
  public void add(HostKey paramHostKey, UserInfo paramUserInfo)
  {
    // Byte code:
    //   0: aload_1
    //   1: getfield 151	com/jcraft/jsch/HostKey:type	I
    //   4: istore_3
    //   5: aload_1
    //   6: invokevirtual 154	com/jcraft/jsch/HostKey:getHost	()Ljava/lang/String;
    //   9: astore 4
    //   11: aload_1
    //   12: getfield 157	com/jcraft/jsch/HostKey:key	[B
    //   15: pop
    //   16: aload_0
    //   17: getfield 43	com/jcraft/jsch/KnownHosts:pool	Ljava/util/Vector;
    //   20: astore 6
    //   22: aload 6
    //   24: monitorenter
    //   25: iconst_0
    //   26: istore 7
    //   28: iload 7
    //   30: aload_0
    //   31: getfield 43	com/jcraft/jsch/KnownHosts:pool	Ljava/util/Vector;
    //   34: invokevirtual 160	java/util/Vector:size	()I
    //   37: if_icmpge +42 -> 79
    //   40: aload_0
    //   41: getfield 43	com/jcraft/jsch/KnownHosts:pool	Ljava/util/Vector;
    //   44: iload 7
    //   46: invokevirtual 164	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   49: checkcast 60	com/jcraft/jsch/HostKey
    //   52: checkcast 60	com/jcraft/jsch/HostKey
    //   55: astore 14
    //   57: aload 14
    //   59: aload 4
    //   61: invokevirtual 167	com/jcraft/jsch/HostKey:isMatched	(Ljava/lang/String;)Z
    //   64: ifeq +293 -> 357
    //   67: aload 14
    //   69: getfield 151	com/jcraft/jsch/HostKey:type	I
    //   72: iload_3
    //   73: if_icmpne +284 -> 357
    //   76: goto +281 -> 357
    //   79: aload 6
    //   81: monitorexit
    //   82: aload_0
    //   83: getfield 43	com/jcraft/jsch/KnownHosts:pool	Ljava/util/Vector;
    //   86: aload_1
    //   87: invokevirtual 67	java/util/Vector:addElement	(Ljava/lang/Object;)V
    //   90: aload_0
    //   91: invokevirtual 170	com/jcraft/jsch/KnownHosts:getKnownHostsRepositoryID	()Ljava/lang/String;
    //   94: astore 9
    //   96: aload 9
    //   98: ifnull +192 -> 290
    //   101: iconst_1
    //   102: istore 10
    //   104: new 172	java/io/File
    //   107: dup
    //   108: aload 9
    //   110: invokestatic 175	com/jcraft/jsch/Util:checkTilde	(Ljava/lang/String;)Ljava/lang/String;
    //   113: invokespecial 177	java/io/File:<init>	(Ljava/lang/String;)V
    //   116: astore 11
    //   118: aload 11
    //   120: invokevirtual 181	java/io/File:exists	()Z
    //   123: ifne +156 -> 279
    //   126: iconst_0
    //   127: istore 10
    //   129: aload_2
    //   130: ifnull +149 -> 279
    //   133: aload_2
    //   134: new 93	java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   141: aload 9
    //   143: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: ldc 183
    //   148: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: ldc 185
    //   153: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokeinterface 190 2 0
    //   164: istore 10
    //   166: aload 11
    //   168: invokevirtual 194	java/io/File:getParentFile	()Ljava/io/File;
    //   171: astore 13
    //   173: iload 10
    //   175: ifeq +96 -> 271
    //   178: aload 13
    //   180: ifnull +91 -> 271
    //   183: aload 13
    //   185: invokevirtual 181	java/io/File:exists	()Z
    //   188: ifne +83 -> 271
    //   191: aload_2
    //   192: new 93	java/lang/StringBuilder
    //   195: dup
    //   196: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   199: ldc 196
    //   201: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: aload 13
    //   206: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   209: ldc 183
    //   211: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: ldc 185
    //   216: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   222: invokeinterface 190 2 0
    //   227: istore 10
    //   229: iload 10
    //   231: ifeq +40 -> 271
    //   234: aload 13
    //   236: invokevirtual 199	java/io/File:mkdirs	()Z
    //   239: ifne +60 -> 299
    //   242: aload_2
    //   243: new 93	java/lang/StringBuilder
    //   246: dup
    //   247: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   250: aload 13
    //   252: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   255: ldc 201
    //   257: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   260: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   263: invokeinterface 204 2 0
    //   268: iconst_0
    //   269: istore 10
    //   271: aload 13
    //   273: ifnonnull +6 -> 279
    //   276: iconst_0
    //   277: istore 10
    //   279: iload 10
    //   281: ifeq +9 -> 290
    //   284: aload_0
    //   285: aload 9
    //   287: invokevirtual 207	com/jcraft/jsch/KnownHosts:sync	(Ljava/lang/String;)V
    //   290: return
    //   291: astore 8
    //   293: aload 6
    //   295: monitorexit
    //   296: aload 8
    //   298: athrow
    //   299: aload_2
    //   300: new 93	java/lang/StringBuilder
    //   303: dup
    //   304: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   307: aload 13
    //   309: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   312: ldc 209
    //   314: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   320: invokeinterface 204 2 0
    //   325: goto -54 -> 271
    //   328: astore 12
    //   330: getstatic 133	java/lang/System:err	Ljava/io/PrintStream;
    //   333: new 93	java/lang/StringBuilder
    //   336: dup
    //   337: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   340: ldc 211
    //   342: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   345: aload 12
    //   347: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   350: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   353: invokevirtual 143	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   356: return
    //   357: iinc 7 1
    //   360: goto -332 -> 28
    //
    // Exception table:
    //   from	to	target	type
    //   28	76	291	finally
    //   79	82	291	finally
    //   293	296	291	finally
    //   284	290	328	java/lang/Exception
  }

  public int check(String paramString, byte[] paramArrayOfByte)
  {
    int i = 1;
    if (paramString == null)
      return i;
    int j = getType(paramArrayOfByte);
    Vector localVector = this.pool;
    for (int k = 0; ; k++)
    {
      try
      {
        if (k >= this.pool.size())
          break;
        HostKey localHostKey = (HostKey)this.pool.elementAt(k);
        if ((!localHostKey.isMatched(paramString)) || (localHostKey.type != j))
          continue;
        if (Util.array_equals(localHostKey.key, paramArrayOfByte))
          return 0;
      }
      finally
      {
      }
      i = 2;
    }
    if ((i == 1) && (paramString.startsWith("[")) && (paramString.indexOf("]:") > 1))
      return check(paramString.substring(1, paramString.indexOf("]:")), paramArrayOfByte);
    return i;
  }

  HostKey createHashedHostKey(String paramString, byte[] paramArrayOfByte)
    throws JSchException
  {
    HashedHostKey localHashedHostKey = new HashedHostKey(paramString, paramArrayOfByte);
    localHashedHostKey.hash();
    return localHashedHostKey;
  }

  void dump(OutputStream paramOutputStream)
    throws IOException
  {
    while (true)
    {
      Vector localVector;
      int i;
      try
      {
        localVector = this.pool;
        i = 0;
        try
        {
          if (i < this.pool.size())
          {
            HostKey localHostKey = (HostKey)this.pool.elementAt(i);
            String str1 = localHostKey.getMarker();
            String str2 = localHostKey.getHost();
            String str3 = localHostKey.getType();
            String str4 = localHostKey.getComment();
            if (str3.equals("UNKNOWN"))
            {
              paramOutputStream.write(Util.str2byte(str2));
              paramOutputStream.write(cr);
            }
            else
            {
              if (str1.length() != 0)
              {
                paramOutputStream.write(Util.str2byte(str1));
                paramOutputStream.write(space);
              }
              paramOutputStream.write(Util.str2byte(str2));
              paramOutputStream.write(space);
              paramOutputStream.write(Util.str2byte(str3));
              paramOutputStream.write(space);
              paramOutputStream.write(Util.str2byte(localHostKey.getKey()));
              if (str4 != null)
              {
                paramOutputStream.write(space);
                paramOutputStream.write(Util.str2byte(str4));
              }
              paramOutputStream.write(cr);
            }
          }
        }
        finally
        {
        }
      }
      catch (Exception localException)
      {
        System.err.println(localException);
        return;
      }
      return;
      i++;
    }
  }

  public HostKey[] getHostKey()
  {
    return getHostKey(null, (String)null);
  }

  public HostKey[] getHostKey(String paramString1, String paramString2)
  {
    while (true)
    {
      ArrayList localArrayList;
      int i;
      synchronized (this.pool)
      {
        localArrayList = new ArrayList();
        i = 0;
        if (i < this.pool.size())
        {
          HostKey localHostKey = (HostKey)this.pool.elementAt(i);
          if ((localHostKey.type == 3) || ((paramString1 != null) && ((!localHostKey.isMatched(paramString1)) || ((paramString2 != null) && (!localHostKey.getType().equals(paramString2))))))
            break label242;
          localArrayList.add(localHostKey);
        }
      }
      Object localObject2 = new HostKey[localArrayList.size()];
      for (int j = 0; j < localArrayList.size(); j++)
        localObject2[j] = ((HostKey)localArrayList.get(j));
      if ((paramString1 != null) && (paramString1.startsWith("[")) && (paramString1.indexOf("]:") > 1))
      {
        HostKey[] arrayOfHostKey1 = getHostKey(paramString1.substring(1, paramString1.indexOf("]:")), paramString2);
        if (arrayOfHostKey1.length > 0)
        {
          HostKey[] arrayOfHostKey2 = new HostKey[localObject2.length + arrayOfHostKey1.length];
          System.arraycopy(localObject2, 0, arrayOfHostKey2, 0, localObject2.length);
          System.arraycopy(arrayOfHostKey1, 0, arrayOfHostKey2, localObject2.length, arrayOfHostKey1.length);
          localObject2 = arrayOfHostKey2;
        }
      }
      return localObject2;
      label242: i++;
    }
  }

  String getKnownHostsFile()
  {
    return this.known_hosts;
  }

  public String getKnownHostsRepositoryID()
  {
    return this.known_hosts;
  }

  public void remove(String paramString1, String paramString2)
  {
    remove(paramString1, paramString2, null);
  }

  public void remove(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    int i = 0;
    Vector localVector = this.pool;
    for (int j = 0; ; j++)
    {
      try
      {
        if (j < this.pool.size())
        {
          HostKey localHostKey = (HostKey)this.pool.elementAt(j);
          if ((paramString1 != null) && ((!localHostKey.isMatched(paramString1)) || ((paramString2 != null) && ((!localHostKey.getType().equals(paramString2)) || ((paramArrayOfByte != null) && (!Util.array_equals(paramArrayOfByte, localHostKey.key)))))))
            continue;
          String str = localHostKey.getHost();
          if ((str.equals(paramString1)) || (((localHostKey instanceof HashedHostKey)) && (((HashedHostKey)localHostKey).isHashed())))
            this.pool.removeElement(localHostKey);
          else
            localHostKey.host = deleteSubString(str, paramString1);
        }
      }
      finally
      {
      }
      if (i != 0);
      try
      {
        sync();
        return;
      }
      catch (Exception localException)
      {
        return;
      }
      i = 1;
    }
  }

  void setKnownHosts(InputStream paramInputStream)
    throws JSchException
  {
    this.pool.removeAllElements();
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject2;
    int j;
    int i;
    try
    {
      localObject2 = new byte[1024];
      break label1037;
      j = paramInputStream.read();
      if (j == -1)
      {
        if (i != 0)
          break label94;
        if (0 == 0)
          break label928;
        throw new JSchException("KnownHosts: invalid format");
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      if ((localException instanceof JSchException))
        throw ((JSchException)localException);
    }
    finally
    {
    }
    while (true)
    {
      label94: int m;
      int n;
      int i3;
      int i6;
      int i9;
      int i16;
      int i13;
      int i15;
      try
      {
        paramInputStream.close();
        throw localObject1;
        if (j == 13)
          break;
        int i20;
        if (j == 10)
        {
          m = 0;
          if (m >= i)
            continue;
          i20 = localObject2[m];
          if (i20 == 32)
            break label1043;
          if (i20 == 9)
            break label1043;
        }
        else
        {
          if (localObject2.length <= i)
          {
            if (i > 10240)
              continue;
            byte[] arrayOfByte = new byte[2 * localObject2.length];
            System.arraycopy(localObject2, 0, arrayOfByte, 0, localObject2.length);
            localObject2 = arrayOfByte;
          }
          int k = i + 1;
          localObject2[i] = ((byte)j);
          i = k;
          break;
        }
        if (i20 == 35)
        {
          addInvalidLine(Util.byte2str((byte[])localObject2, 0, i));
          break label1037;
        }
        if (m >= i)
        {
          addInvalidLine(Util.byte2str((byte[])localObject2, 0, i));
          break label1037;
        }
        localStringBuffer.setLength(0);
        n = m;
        if (n >= i)
          break label1030;
        i1 = n + 1;
        int i19 = localObject2[n];
        String str1;
        if ((i19 == 32) || (i19 == 9))
        {
          str1 = localStringBuffer.toString();
          if ((i1 >= i) || (str1.length() == 0))
          {
            addInvalidLine(Util.byte2str((byte[])localObject2, 0, i));
            break label1037;
          }
        }
        else
        {
          localStringBuffer.append((char)i19);
          n = i1;
          continue;
        }
        if (i1 < i)
        {
          int i2 = localObject2[i1];
          if ((i2 == 32) || (i2 == 9))
            break label1049;
        }
        String str2 = "";
        if (str1.charAt(0) == '@')
        {
          str2 = str1;
          localStringBuffer.setLength(0);
          i3 = i1;
          if (i3 >= i)
            break label1023;
          i1 = i3 + 1;
          int i18 = localObject2[i3];
          if ((i18 == 32) || (i18 == 9))
          {
            str1 = localStringBuffer.toString();
            if ((i1 >= i) || (str1.length() == 0))
            {
              addInvalidLine(Util.byte2str((byte[])localObject2, 0, i));
              break label1037;
            }
          }
          else
          {
            localStringBuffer.append((char)i18);
            i3 = i1;
            continue;
          }
          if (i1 < i)
          {
            int i4 = localObject2[i1];
            if ((i4 == 32) || (i4 == 9))
              break label1055;
          }
        }
        localStringBuffer.setLength(0);
        int i5 = -1;
        i6 = i1;
        if (i6 >= i)
          break label1016;
        i7 = i6 + 1;
        int i17 = localObject2[i6];
        if ((i17 == 32) || (i17 == 9))
        {
          if (localStringBuffer.toString().equals("ssh-dss"))
          {
            i5 = 1;
            if (i7 < i)
              continue;
            addInvalidLine(Util.byte2str((byte[])localObject2, 0, i));
            break label1037;
          }
        }
        else
        {
          localStringBuffer.append((char)i17);
          i6 = i7;
          continue;
        }
        if (!localStringBuffer.toString().equals("ssh-rsa"))
          break label1061;
        i5 = 2;
        continue;
        if (i7 < i)
        {
          int i8 = localObject2[i7];
          if ((i8 == 32) || (i8 == 9))
            break label1068;
        }
        localStringBuffer.setLength(0);
        i9 = i7;
        if (i9 >= i)
          break label1009;
        i10 = i9 + 1;
        i16 = localObject2[i9];
        if (i16 != 13)
          break label1074;
        i9 = i10;
        continue;
        String str3 = localStringBuffer.toString();
        if (str3.length() == 0)
        {
          addInvalidLine(Util.byte2str((byte[])localObject2, 0, i));
          break label1037;
          if ((i16 == 32) || (i16 == 9))
            continue;
          localStringBuffer.append((char)i16);
          i9 = i10;
          continue;
        }
        if (i10 < i)
        {
          int i11 = localObject2[i10];
          if ((i11 == 32) || (i11 == 9))
            break label1084;
        }
        int i12 = i10;
        String str4 = null;
        int i14;
        if (i12 < i)
        {
          localStringBuffer.setLength(0);
          i13 = i10;
          if (i13 < i)
          {
            i14 = i13 + 1;
            i15 = localObject2[i13];
            if (i15 != 13)
              break label1090;
            i13 = i14;
            continue;
            str4 = localStringBuffer.toString();
          }
        }
        else
        {
          HashedHostKey localHashedHostKey = new HashedHostKey(str2, str1, i5, Util.fromBase64(Util.str2byte(str3), 0, str3.length()), str4);
          this.pool.addElement(localHashedHostKey);
          break label1037;
          localStringBuffer.append((char)i15);
          i13 = i14;
          continue;
          try
          {
            label928: paramInputStream.close();
            return;
          }
          catch (IOException localIOException2)
          {
            throw new JSchException(localIOException2.toString(), localIOException2);
          }
          if ((localException instanceof Throwable))
            throw new JSchException(localException.toString(), localException);
          throw new JSchException(localException.toString());
        }
      }
      catch (IOException localIOException1)
      {
        throw new JSchException(localIOException1.toString(), localIOException1);
      }
      continue;
      label1009: int i10 = i9;
      continue;
      label1016: int i7 = i6;
      continue;
      label1023: int i1 = i3;
      continue;
      label1030: i1 = n;
      continue;
      label1037: i = 0;
      break;
      label1043: m++;
      continue;
      label1049: i1++;
      continue;
      label1055: i1++;
      continue;
      label1061: i7 = i;
      continue;
      label1068: i7++;
      continue;
      label1074: if (i16 == 10)
      {
        continue;
        label1084: i10++;
        continue;
        label1090: if (i15 != 10);
      }
    }
  }

  void setKnownHosts(String paramString)
    throws JSchException
  {
    try
    {
      this.known_hosts = paramString;
      setKnownHosts(new FileInputStream(Util.checkTilde(paramString)));
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      throw new JSchException(localFileNotFoundException.toString(), localFileNotFoundException);
    }
  }

  protected void sync()
    throws IOException
  {
    if (this.known_hosts != null)
      sync(this.known_hosts);
  }

  protected void sync(String paramString)
    throws IOException
  {
    if (paramString == null);
    while (true)
    {
      return;
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(Util.checkTilde(paramString));
        dump(localFileOutputStream);
        localFileOutputStream.close();
      }
      finally
      {
      }
    }
  }

  class HashedHostKey extends HostKey
  {
    private static final String HASH_DELIM = "|";
    private static final String HASH_MAGIC = "|1|";
    byte[] hash = null;
    private boolean hashed = false;
    byte[] salt = null;

    HashedHostKey(String paramInt, int paramArrayOfByte, byte[] arg4)
      throws JSchException
    {
      this("", paramInt, paramArrayOfByte, arrayOfByte, null);
    }

    HashedHostKey(String paramString1, String paramInt, int paramArrayOfByte, byte[] paramString2, String arg6)
      throws JSchException
    {
      super(paramInt, paramArrayOfByte, paramString2, str1);
      if ((this.host.startsWith("|1|")) && (this.host.substring("|1|".length()).indexOf("|") > 0))
      {
        String str2 = this.host.substring("|1|".length());
        String str3 = str2.substring(0, str2.indexOf("|"));
        String str4 = str2.substring(1 + str2.indexOf("|"));
        this.salt = Util.fromBase64(Util.str2byte(str3), 0, str3.length());
        this.hash = Util.fromBase64(Util.str2byte(str4), 0, str4.length());
        if ((this.salt.length != 20) || (this.hash.length != 20))
        {
          this.salt = null;
          this.hash = null;
        }
      }
      else
      {
        return;
      }
      this.hashed = true;
    }

    HashedHostKey(String paramArrayOfByte, byte[] arg3)
      throws JSchException
    {
      this(paramArrayOfByte, 0, arrayOfByte);
    }

    void hash()
    {
      if (this.hashed)
        return;
      MAC localMAC = KnownHosts.this.getHMACSHA1();
      if (this.salt == null)
        synchronized (Session.random)
        {
          this.salt = new byte[localMAC.getBlockSize()];
          ???.fill(this.salt, 0, this.salt.length);
        }
      try
      {
        try
        {
          localMAC.init(this.salt);
          byte[] arrayOfByte = Util.str2byte(this.host);
          localMAC.update(arrayOfByte, 0, arrayOfByte.length);
          this.hash = new byte[localMAC.getBlockSize()];
          localMAC.doFinal(this.hash, 0);
          label121: this.host = ("|1|" + Util.byte2str(Util.toBase64(this.salt, 0, this.salt.length)) + "|" + Util.byte2str(Util.toBase64(this.hash, 0, this.hash.length)));
          this.hashed = true;
          return;
          localObject2 = finally;
          throw localObject2;
        }
        finally
        {
        }
      }
      catch (Exception localException)
      {
        break label121;
      }
    }

    boolean isHashed()
    {
      return this.hashed;
    }

    boolean isMatched(String paramString)
    {
      if (!this.hashed)
        return super.isMatched(paramString);
      try
      {
        synchronized (KnownHosts.this.getHMACSHA1())
        {
          ???.init(this.salt);
          byte[] arrayOfByte1 = Util.str2byte(paramString);
          ???.update(arrayOfByte1, 0, arrayOfByte1.length);
          byte[] arrayOfByte2 = new byte[???.getBlockSize()];
          ???.doFinal(arrayOfByte2, 0);
          boolean bool = Util.array_equals(this.hash, arrayOfByte2);
          return bool;
        }
      }
      catch (Exception localException)
      {
        System.out.println(localException);
      }
      return false;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.KnownHosts
 * JD-Core Version:    0.6.2
 */