package com.jcraft.jsch.jgss;

import org.ietf.jgss.GSSException;
import org.ietf.jgss.MessageProp;

public class GSSContextKrb5
  implements com.jcraft.jsch.GSSContext
{
  private static final String pUseSubjectCredsOnly = "javax.security.auth.useSubjectCredsOnly";
  private static String useSubjectCredsOnly = getSystemProperty("javax.security.auth.useSubjectCredsOnly");
  private org.ietf.jgss.GSSContext context = null;

  private static String getSystemProperty(String paramString)
  {
    try
    {
      String str = System.getProperty(paramString);
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private static void setSystemProperty(String paramString1, String paramString2)
  {
    try
    {
      System.setProperty(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  // ERROR //
  public void create(String paramString1, String paramString2)
    throws com.jcraft.jsch.JSchException
  {
    // Byte code:
    //   0: new 48	org/ietf/jgss/Oid
    //   3: dup
    //   4: ldc 50
    //   6: invokespecial 53	org/ietf/jgss/Oid:<init>	(Ljava/lang/String;)V
    //   9: astore_3
    //   10: new 48	org/ietf/jgss/Oid
    //   13: dup
    //   14: ldc 55
    //   16: invokespecial 53	org/ietf/jgss/Oid:<init>	(Ljava/lang/String;)V
    //   19: astore 4
    //   21: invokestatic 61	org/ietf/jgss/GSSManager:getInstance	()Lorg/ietf/jgss/GSSManager;
    //   24: astore 6
    //   26: aload_2
    //   27: astore 7
    //   29: aload 7
    //   31: invokestatic 67	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   34: invokevirtual 71	java/net/InetAddress:getCanonicalHostName	()Ljava/lang/String;
    //   37: astore 9
    //   39: aload 9
    //   41: astore 7
    //   43: aload_0
    //   44: aload 6
    //   46: aload 6
    //   48: new 73	java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   55: ldc 76
    //   57: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: aload 7
    //   62: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: invokevirtual 83	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   68: aload 4
    //   70: invokevirtual 87	org/ietf/jgss/GSSManager:createName	(Ljava/lang/String;Lorg/ietf/jgss/Oid;)Lorg/ietf/jgss/GSSName;
    //   73: aload_3
    //   74: aconst_null
    //   75: iconst_0
    //   76: invokevirtual 91	org/ietf/jgss/GSSManager:createContext	(Lorg/ietf/jgss/GSSName;Lorg/ietf/jgss/Oid;Lorg/ietf/jgss/GSSCredential;I)Lorg/ietf/jgss/GSSContext;
    //   79: putfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   82: aload_0
    //   83: getfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   86: iconst_1
    //   87: invokeinterface 97 2 0
    //   92: aload_0
    //   93: getfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   96: iconst_1
    //   97: invokeinterface 100 2 0
    //   102: aload_0
    //   103: getfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   106: iconst_1
    //   107: invokeinterface 103 2 0
    //   112: aload_0
    //   113: getfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   116: iconst_1
    //   117: invokeinterface 106 2 0
    //   122: aload_0
    //   123: getfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   126: iconst_0
    //   127: invokeinterface 109 2 0
    //   132: return
    //   133: astore 5
    //   135: new 42	com/jcraft/jsch/JSchException
    //   138: dup
    //   139: aload 5
    //   141: invokevirtual 110	org/ietf/jgss/GSSException:toString	()Ljava/lang/String;
    //   144: invokespecial 111	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   147: athrow
    //   148: astore 8
    //   150: goto -107 -> 43
    //
    // Exception table:
    //   from	to	target	type
    //   0	26	133	org/ietf/jgss/GSSException
    //   29	39	133	org/ietf/jgss/GSSException
    //   43	132	133	org/ietf/jgss/GSSException
    //   29	39	148	java/net/UnknownHostException
  }

  public void dispose()
  {
    try
    {
      this.context.dispose();
      return;
    }
    catch (GSSException localGSSException)
    {
    }
  }

  public byte[] getMIC(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      MessageProp localMessageProp = new MessageProp(0, true);
      byte[] arrayOfByte = this.context.getMIC(paramArrayOfByte, paramInt1, paramInt2, localMessageProp);
      return arrayOfByte;
    }
    catch (GSSException localGSSException)
    {
    }
    return null;
  }

  // ERROR //
  public byte[] init(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws com.jcraft.jsch.JSchException
  {
    // Byte code:
    //   0: getstatic 21	com/jcraft/jsch/jgss/GSSContextKrb5:useSubjectCredsOnly	Ljava/lang/String;
    //   3: ifnonnull +10 -> 13
    //   6: ldc 10
    //   8: ldc 129
    //   10: invokestatic 131	com/jcraft/jsch/jgss/GSSContextKrb5:setSystemProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: aload_0
    //   14: getfield 26	com/jcraft/jsch/jgss/GSSContextKrb5:context	Lorg/ietf/jgss/GSSContext;
    //   17: aload_1
    //   18: iconst_0
    //   19: iload_3
    //   20: invokeinterface 134 4 0
    //   25: astore 7
    //   27: getstatic 21	com/jcraft/jsch/jgss/GSSContextKrb5:useSubjectCredsOnly	Ljava/lang/String;
    //   30: ifnonnull +10 -> 40
    //   33: ldc 10
    //   35: ldc 136
    //   37: invokestatic 131	com/jcraft/jsch/jgss/GSSContextKrb5:setSystemProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   40: aload 7
    //   42: areturn
    //   43: astore 6
    //   45: new 42	com/jcraft/jsch/JSchException
    //   48: dup
    //   49: aload 6
    //   51: invokevirtual 110	org/ietf/jgss/GSSException:toString	()Ljava/lang/String;
    //   54: invokespecial 111	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   57: athrow
    //   58: astore 5
    //   60: getstatic 21	com/jcraft/jsch/jgss/GSSContextKrb5:useSubjectCredsOnly	Ljava/lang/String;
    //   63: ifnonnull +10 -> 73
    //   66: ldc 10
    //   68: ldc 136
    //   70: invokestatic 131	com/jcraft/jsch/jgss/GSSContextKrb5:setSystemProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   73: aload 5
    //   75: athrow
    //   76: astore 4
    //   78: new 42	com/jcraft/jsch/JSchException
    //   81: dup
    //   82: aload 4
    //   84: invokevirtual 137	java/lang/SecurityException:toString	()Ljava/lang/String;
    //   87: invokespecial 111	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   90: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   0	13	43	org/ietf/jgss/GSSException
    //   13	27	43	org/ietf/jgss/GSSException
    //   0	13	58	finally
    //   13	27	58	finally
    //   45	58	58	finally
    //   78	91	58	finally
    //   0	13	76	java/lang/SecurityException
    //   13	27	76	java/lang/SecurityException
  }

  public boolean isEstablished()
  {
    return this.context.isEstablished();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jgss.GSSContextKrb5
 * JD-Core Version:    0.6.2
 */