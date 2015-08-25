package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform
{
  private static final Platform PLATFORM = findPlatform();

  static byte[] concatLengthPrefixed(List<Protocol> paramList)
  {
    Buffer localBuffer = new Buffer();
    int i = 0;
    int j = paramList.size();
    if (i < j)
    {
      Protocol localProtocol = (Protocol)paramList.get(i);
      if (localProtocol == Protocol.HTTP_1_0);
      while (true)
      {
        i++;
        break;
        localBuffer.writeByte(localProtocol.toString().length());
        localBuffer.writeUtf8(localProtocol.toString());
      }
    }
    return localBuffer.readByteArray();
  }

  // ERROR //
  private static Platform findPlatform()
  {
    // Byte code:
    //   0: ldc 65
    //   2: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   5: pop
    //   6: iconst_1
    //   7: anewarray 67	java/lang/Class
    //   10: astore 10
    //   12: aload 10
    //   14: iconst_0
    //   15: getstatic 77	java/lang/Boolean:TYPE	Ljava/lang/Class;
    //   18: aastore
    //   19: new 79	com/squareup/okhttp/internal/OptionalMethod
    //   22: dup
    //   23: aconst_null
    //   24: ldc 81
    //   26: aload 10
    //   28: invokespecial 84	com/squareup/okhttp/internal/OptionalMethod:<init>	(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   31: astore 11
    //   33: new 79	com/squareup/okhttp/internal/OptionalMethod
    //   36: dup
    //   37: aconst_null
    //   38: ldc 86
    //   40: iconst_1
    //   41: anewarray 67	java/lang/Class
    //   44: dup
    //   45: iconst_0
    //   46: ldc 44
    //   48: aastore
    //   49: invokespecial 84	com/squareup/okhttp/internal/OptionalMethod:<init>	(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   52: astore 12
    //   54: aconst_null
    //   55: astore 13
    //   57: aconst_null
    //   58: astore 14
    //   60: ldc 88
    //   62: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   65: astore 19
    //   67: aload 19
    //   69: ldc 90
    //   71: iconst_1
    //   72: anewarray 67	java/lang/Class
    //   75: dup
    //   76: iconst_0
    //   77: ldc 92
    //   79: aastore
    //   80: invokevirtual 96	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   83: astore 13
    //   85: aload 19
    //   87: ldc 98
    //   89: iconst_1
    //   90: anewarray 67	java/lang/Class
    //   93: dup
    //   94: iconst_0
    //   95: ldc 92
    //   97: aastore
    //   98: invokevirtual 96	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   101: astore 20
    //   103: aload 20
    //   105: astore 14
    //   107: ldc 100
    //   109: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   112: pop
    //   113: new 79	com/squareup/okhttp/internal/OptionalMethod
    //   116: dup
    //   117: ldc 102
    //   119: ldc 104
    //   121: iconst_0
    //   122: anewarray 67	java/lang/Class
    //   125: invokespecial 84	com/squareup/okhttp/internal/OptionalMethod:<init>	(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   128: astore 23
    //   130: new 79	com/squareup/okhttp/internal/OptionalMethod
    //   133: dup
    //   134: aconst_null
    //   135: ldc 106
    //   137: iconst_1
    //   138: anewarray 67	java/lang/Class
    //   141: dup
    //   142: iconst_0
    //   143: ldc 102
    //   145: aastore
    //   146: invokespecial 84	com/squareup/okhttp/internal/OptionalMethod:<init>	(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)V
    //   149: astore 24
    //   151: aload 24
    //   153: astore 17
    //   155: aload 23
    //   157: astore 16
    //   159: new 108	com/squareup/okhttp/internal/Platform$Android
    //   162: dup
    //   163: aload 11
    //   165: aload 12
    //   167: aload 13
    //   169: aload 14
    //   171: aload 16
    //   173: aload 17
    //   175: invokespecial 111	com/squareup/okhttp/internal/Platform$Android:<init>	(Lcom/squareup/okhttp/internal/OptionalMethod;Lcom/squareup/okhttp/internal/OptionalMethod;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Lcom/squareup/okhttp/internal/OptionalMethod;Lcom/squareup/okhttp/internal/OptionalMethod;)V
    //   178: areturn
    //   179: astore_0
    //   180: ldc 113
    //   182: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   185: pop
    //   186: goto -180 -> 6
    //   189: astore_1
    //   190: ldc 115
    //   192: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   195: astore 4
    //   197: new 117	java/lang/StringBuilder
    //   200: dup
    //   201: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   204: ldc 115
    //   206: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: ldc 124
    //   211: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   217: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   220: astore 5
    //   222: new 117	java/lang/StringBuilder
    //   225: dup
    //   226: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   229: ldc 115
    //   231: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: ldc 127
    //   236: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   242: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   245: astore 6
    //   247: new 117	java/lang/StringBuilder
    //   250: dup
    //   251: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   254: ldc 115
    //   256: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   259: ldc 129
    //   261: invokevirtual 122	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   267: invokestatic 71	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   270: astore 7
    //   272: new 131	com/squareup/okhttp/internal/Platform$JdkWithJettyBootPlatform
    //   275: dup
    //   276: aload 4
    //   278: ldc 133
    //   280: iconst_2
    //   281: anewarray 67	java/lang/Class
    //   284: dup
    //   285: iconst_0
    //   286: ldc 135
    //   288: aastore
    //   289: dup
    //   290: iconst_1
    //   291: aload 5
    //   293: aastore
    //   294: invokevirtual 96	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   297: aload 4
    //   299: ldc 136
    //   301: iconst_1
    //   302: anewarray 67	java/lang/Class
    //   305: dup
    //   306: iconst_0
    //   307: ldc 135
    //   309: aastore
    //   310: invokevirtual 96	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   313: aload 4
    //   315: ldc 138
    //   317: iconst_1
    //   318: anewarray 67	java/lang/Class
    //   321: dup
    //   322: iconst_0
    //   323: ldc 135
    //   325: aastore
    //   326: invokevirtual 96	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   329: aload 6
    //   331: aload 7
    //   333: invokespecial 141	com/squareup/okhttp/internal/Platform$JdkWithJettyBootPlatform:<init>	(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/Class;Ljava/lang/Class;)V
    //   336: astore 8
    //   338: aload 8
    //   340: areturn
    //   341: astore_3
    //   342: new 2	com/squareup/okhttp/internal/Platform
    //   345: dup
    //   346: invokespecial 142	com/squareup/okhttp/internal/Platform:<init>	()V
    //   349: areturn
    //   350: astore_2
    //   351: goto -9 -> 342
    //   354: astore 18
    //   356: aconst_null
    //   357: astore 16
    //   359: aconst_null
    //   360: astore 17
    //   362: goto -203 -> 159
    //   365: astore 26
    //   367: aload 23
    //   369: astore 16
    //   371: aconst_null
    //   372: astore 17
    //   374: goto -215 -> 159
    //   377: astore 15
    //   379: aconst_null
    //   380: astore 14
    //   382: aconst_null
    //   383: astore 16
    //   385: aconst_null
    //   386: astore 17
    //   388: goto -229 -> 159
    //   391: astore 21
    //   393: aconst_null
    //   394: astore 16
    //   396: aconst_null
    //   397: astore 17
    //   399: goto -240 -> 159
    //   402: astore 25
    //   404: aload 23
    //   406: astore 16
    //   408: aconst_null
    //   409: astore 17
    //   411: goto -252 -> 159
    //
    // Exception table:
    //   from	to	target	type
    //   0	6	179	java/lang/ClassNotFoundException
    //   6	54	189	java/lang/ClassNotFoundException
    //   159	179	189	java/lang/ClassNotFoundException
    //   180	186	189	java/lang/ClassNotFoundException
    //   190	338	341	java/lang/ClassNotFoundException
    //   190	338	350	java/lang/NoSuchMethodException
    //   60	103	354	java/lang/NoSuchMethodException
    //   107	130	354	java/lang/NoSuchMethodException
    //   130	151	365	java/lang/NoSuchMethodException
    //   60	103	377	java/lang/ClassNotFoundException
    //   107	130	391	java/lang/ClassNotFoundException
    //   130	151	402	java/lang/ClassNotFoundException
  }

  public static Platform get()
  {
    return PLATFORM;
  }

  public void afterHandshake(SSLSocket paramSSLSocket)
  {
  }

  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
  }

  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
    throws IOException
  {
    paramSocket.connect(paramInetSocketAddress, paramInt);
  }

  public String getPrefix()
  {
    return "OkHttp";
  }

  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    return null;
  }

  public void logW(String paramString)
  {
    System.out.println(paramString);
  }

  public void tagSocket(Socket paramSocket)
    throws SocketException
  {
  }

  public URI toUriLenient(URL paramURL)
    throws URISyntaxException
  {
    return paramURL.toURI();
  }

  public void untagSocket(Socket paramSocket)
    throws SocketException
  {
  }

  private static class Android extends Platform
  {
    private final OptionalMethod<Socket> getAlpnSelectedProtocol;
    private final OptionalMethod<Socket> setAlpnProtocols;
    private final OptionalMethod<Socket> setHostname;
    private final OptionalMethod<Socket> setUseSessionTickets;
    private final Method trafficStatsTagSocket;
    private final Method trafficStatsUntagSocket;

    public Android(OptionalMethod<Socket> paramOptionalMethod1, OptionalMethod<Socket> paramOptionalMethod2, Method paramMethod1, Method paramMethod2, OptionalMethod<Socket> paramOptionalMethod3, OptionalMethod<Socket> paramOptionalMethod4)
    {
      this.setUseSessionTickets = paramOptionalMethod1;
      this.setHostname = paramOptionalMethod2;
      this.trafficStatsTagSocket = paramMethod1;
      this.trafficStatsUntagSocket = paramMethod2;
      this.getAlpnSelectedProtocol = paramOptionalMethod3;
      this.setAlpnProtocols = paramOptionalMethod4;
    }

    public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
    {
      if (paramString != null)
      {
        OptionalMethod localOptionalMethod = this.setUseSessionTickets;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Boolean.valueOf(true);
        localOptionalMethod.invokeOptionalWithoutCheckedException(paramSSLSocket, arrayOfObject2);
        this.setHostname.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
      }
      if ((this.setAlpnProtocols != null) && (this.setAlpnProtocols.isSupported(paramSSLSocket)))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = concatLengthPrefixed(paramList);
        this.setAlpnProtocols.invokeWithoutCheckedException(paramSSLSocket, arrayOfObject1);
      }
    }

    public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
      throws IOException
    {
      try
      {
        paramSocket.connect(paramInetSocketAddress, paramInt);
        return;
      }
      catch (SecurityException localSecurityException)
      {
        IOException localIOException = new IOException("Exception in connect");
        localIOException.initCause(localSecurityException);
        throw localIOException;
      }
    }

    public String getSelectedProtocol(SSLSocket paramSSLSocket)
    {
      if (this.getAlpnSelectedProtocol == null);
      while (!this.getAlpnSelectedProtocol.isSupported(paramSSLSocket))
        return null;
      byte[] arrayOfByte = (byte[])this.getAlpnSelectedProtocol.invokeWithoutCheckedException(paramSSLSocket, new Object[0]);
      if (arrayOfByte != null);
      for (String str = new String(arrayOfByte, Util.UTF_8); ; str = null)
        return str;
    }

    public void tagSocket(Socket paramSocket)
      throws SocketException
    {
      if (this.trafficStatsTagSocket == null)
        return;
      try
      {
        this.trafficStatsTagSocket.invoke(null, new Object[] { paramSocket });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException.getCause());
      }
    }

    public void untagSocket(Socket paramSocket)
      throws SocketException
    {
      if (this.trafficStatsUntagSocket == null)
        return;
      try
      {
        this.trafficStatsUntagSocket.invoke(null, new Object[] { paramSocket });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException.getCause());
      }
    }
  }

  private static class JdkWithJettyBootPlatform extends Platform
  {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;

    public JdkWithJettyBootPlatform(Method paramMethod1, Method paramMethod2, Method paramMethod3, Class<?> paramClass1, Class<?> paramClass2)
    {
      this.putMethod = paramMethod1;
      this.getMethod = paramMethod2;
      this.removeMethod = paramMethod3;
      this.clientProviderClass = paramClass1;
      this.serverProviderClass = paramClass2;
    }

    public void afterHandshake(SSLSocket paramSSLSocket)
    {
      try
      {
        this.removeMethod.invoke(null, new Object[] { paramSSLSocket });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        label19: break label19;
      }
    }

    public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
    {
      ArrayList localArrayList = new ArrayList(paramList.size());
      int i = 0;
      int j = paramList.size();
      if (i < j)
      {
        Protocol localProtocol = (Protocol)paramList.get(i);
        if (localProtocol == Protocol.HTTP_1_0);
        while (true)
        {
          i++;
          break;
          localArrayList.add(localProtocol.toString());
        }
      }
      try
      {
        ClassLoader localClassLoader = Platform.class.getClassLoader();
        Class[] arrayOfClass = new Class[2];
        arrayOfClass[0] = this.clientProviderClass;
        arrayOfClass[1] = this.serverProviderClass;
        Object localObject = Proxy.newProxyInstance(localClassLoader, arrayOfClass, new Platform.JettyNegoProvider(localArrayList));
        this.putMethod.invoke(null, new Object[] { paramSSLSocket, localObject });
        return;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new AssertionError(localInvocationTargetException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        label148: break label148;
      }
    }

    public String getSelectedProtocol(SSLSocket paramSSLSocket)
    {
      try
      {
        Platform.JettyNegoProvider localJettyNegoProvider = (Platform.JettyNegoProvider)Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[] { paramSSLSocket }));
        if ((!Platform.JettyNegoProvider.access$000(localJettyNegoProvider)) && (Platform.JettyNegoProvider.access$100(localJettyNegoProvider) == null))
        {
          Internal.logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
          return null;
        }
        if (!Platform.JettyNegoProvider.access$000(localJettyNegoProvider))
        {
          String str = Platform.JettyNegoProvider.access$100(localJettyNegoProvider);
          return str;
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new AssertionError();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        label72: break label72;
      }
      return null;
    }
  }

  private static class JettyNegoProvider
    implements InvocationHandler
  {
    private final List<String> protocols;
    private String selected;
    private boolean unsupported;

    public JettyNegoProvider(List<String> paramList)
    {
      this.protocols = paramList;
    }

    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      String str1 = paramMethod.getName();
      Class localClass = paramMethod.getReturnType();
      if (paramArrayOfObject == null)
        paramArrayOfObject = Util.EMPTY_STRING_ARRAY;
      if ((str1.equals("supports")) && (Boolean.TYPE == localClass))
        return Boolean.valueOf(true);
      if ((str1.equals("unsupported")) && (Void.TYPE == localClass))
      {
        this.unsupported = true;
        return null;
      }
      if ((str1.equals("protocols")) && (paramArrayOfObject.length == 0))
        return this.protocols;
      if (((str1.equals("selectProtocol")) || (str1.equals("select"))) && (String.class == localClass) && (paramArrayOfObject.length == 1) && ((paramArrayOfObject[0] instanceof List)))
      {
        List localList = (List)paramArrayOfObject[0];
        int i = 0;
        int j = localList.size();
        while (i < j)
        {
          if (this.protocols.contains(localList.get(i)))
          {
            String str3 = (String)localList.get(i);
            this.selected = str3;
            return str3;
          }
          i++;
        }
        String str2 = (String)this.protocols.get(0);
        this.selected = str2;
        return str2;
      }
      if (((str1.equals("protocolSelected")) || (str1.equals("selected"))) && (paramArrayOfObject.length == 1))
      {
        this.selected = ((String)paramArrayOfObject[0]);
        return null;
      }
      return paramMethod.invoke(this, paramArrayOfObject);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.Platform
 * JD-Core Version:    0.6.2
 */