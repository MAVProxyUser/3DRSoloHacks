package com.o3dr.solo.android.util.file;

import android.text.TextUtils;
import android.util.Log;
import java.io.File;

public class MD5Checker
{
  private static final String TAG = MD5Checker.class.getSimpleName();

  // ERROR //
  public static String calculateMD5(File paramFile)
  {
    // Byte code:
    //   0: ldc 29
    //   2: invokestatic 35	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   5: astore_3
    //   6: new 37	java/io/FileInputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial 40	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   14: astore 4
    //   16: sipush 8192
    //   19: newarray byte
    //   21: astore 5
    //   23: aload 4
    //   25: aload 5
    //   27: invokevirtual 46	java/io/InputStream:read	([B)I
    //   30: istore 10
    //   32: iload 10
    //   34: ifle +67 -> 101
    //   37: aload_3
    //   38: aload 5
    //   40: iconst_0
    //   41: iload 10
    //   43: invokevirtual 50	java/security/MessageDigest:update	([BII)V
    //   46: goto -23 -> 23
    //   49: astore 9
    //   51: new 52	java/lang/RuntimeException
    //   54: dup
    //   55: ldc 54
    //   57: aload 9
    //   59: invokespecial 57	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   62: athrow
    //   63: astore 6
    //   65: aload 4
    //   67: invokevirtual 60	java/io/InputStream:close	()V
    //   70: aload 6
    //   72: athrow
    //   73: astore_1
    //   74: getstatic 16	com/o3dr/solo/android/util/file/MD5Checker:TAG	Ljava/lang/String;
    //   77: ldc 62
    //   79: aload_1
    //   80: invokestatic 68	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   83: pop
    //   84: aconst_null
    //   85: areturn
    //   86: astore 14
    //   88: getstatic 16	com/o3dr/solo/android/util/file/MD5Checker:TAG	Ljava/lang/String;
    //   91: ldc 70
    //   93: aload 14
    //   95: invokestatic 68	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   98: pop
    //   99: aconst_null
    //   100: areturn
    //   101: ldc 72
    //   103: iconst_1
    //   104: anewarray 4	java/lang/Object
    //   107: dup
    //   108: iconst_0
    //   109: new 74	java/math/BigInteger
    //   112: dup
    //   113: iconst_1
    //   114: aload_3
    //   115: invokevirtual 78	java/security/MessageDigest:digest	()[B
    //   118: invokespecial 81	java/math/BigInteger:<init>	(I[B)V
    //   121: bipush 16
    //   123: invokevirtual 85	java/math/BigInteger:toString	(I)Ljava/lang/String;
    //   126: aastore
    //   127: invokestatic 91	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   130: bipush 32
    //   132: bipush 48
    //   134: invokevirtual 95	java/lang/String:replace	(CC)Ljava/lang/String;
    //   137: astore 11
    //   139: aload 4
    //   141: invokevirtual 60	java/io/InputStream:close	()V
    //   144: aload 11
    //   146: areturn
    //   147: astore 12
    //   149: getstatic 16	com/o3dr/solo/android/util/file/MD5Checker:TAG	Ljava/lang/String;
    //   152: ldc 97
    //   154: aload 12
    //   156: invokestatic 68	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   159: pop
    //   160: aload 11
    //   162: areturn
    //   163: astore 7
    //   165: getstatic 16	com/o3dr/solo/android/util/file/MD5Checker:TAG	Ljava/lang/String;
    //   168: ldc 97
    //   170: aload 7
    //   172: invokestatic 68	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   175: pop
    //   176: goto -106 -> 70
    //
    // Exception table:
    //   from	to	target	type
    //   23	32	49	java/io/IOException
    //   37	46	49	java/io/IOException
    //   101	139	49	java/io/IOException
    //   23	32	63	finally
    //   37	46	63	finally
    //   51	63	63	finally
    //   101	139	63	finally
    //   0	6	73	java/security/NoSuchAlgorithmException
    //   6	16	86	java/io/FileNotFoundException
    //   139	144	147	java/io/IOException
    //   65	70	163	java/io/IOException
  }

  public static boolean checkMD5(String paramString, File paramFile)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramFile == null))
    {
      Log.e(TAG, "MD5 string empty or updateFile null");
      return false;
    }
    String str = calculateMD5(paramFile);
    if (str == null)
    {
      Log.e(TAG, "calculatedDigest null");
      return false;
    }
    Log.v(TAG, "Calculated digest: " + str);
    Log.v(TAG, "Provided digest: " + paramString);
    return str.equalsIgnoreCase(paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.file.MD5Checker
 * JD-Core Version:    0.6.2
 */