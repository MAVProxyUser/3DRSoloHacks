package com.jcraft.jsch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

class Util
{
  private static final byte[] b64 = str2byte("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=");
  private static String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
  static final byte[] empty = str2byte("");

  static boolean array_equals(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = paramArrayOfByte1.length;
    if (i != paramArrayOfByte2.length)
      return false;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label33;
      if (paramArrayOfByte1[j] != paramArrayOfByte2[j])
        break;
    }
    label33: return true;
  }

  static String byte2str(byte[] paramArrayOfByte)
  {
    return byte2str(paramArrayOfByte, 0, paramArrayOfByte.length, "UTF-8");
  }

  static String byte2str(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return byte2str(paramArrayOfByte, paramInt1, paramInt2, "UTF-8");
  }

  static String byte2str(byte[] paramArrayOfByte, int paramInt1, int paramInt2, String paramString)
  {
    try
    {
      String str = new String(paramArrayOfByte, paramInt1, paramInt2, paramString);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return new String(paramArrayOfByte, paramInt1, paramInt2);
  }

  static String byte2str(byte[] paramArrayOfByte, String paramString)
  {
    return byte2str(paramArrayOfByte, 0, paramArrayOfByte.length, paramString);
  }

  static void bzero(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null);
    while (true)
    {
      return;
      for (int i = 0; i < paramArrayOfByte.length; i++)
        paramArrayOfByte[i] = 0;
    }
  }

  static String checkTilde(String paramString)
  {
    try
    {
      if (paramString.startsWith("~"))
      {
        String str = paramString.replace("~", System.getProperty("user.home"));
        paramString = str;
      }
      return paramString;
    }
    catch (SecurityException localSecurityException)
    {
    }
    return paramString;
  }

  static Socket createSocket(final String paramString, final int paramInt1, int paramInt2)
    throws JSchException
  {
    if (paramInt2 == 0)
      try
      {
        Socket localSocket = new Socket(paramString, paramInt1);
        return localSocket;
      }
      catch (Exception localException)
      {
        String str3 = localException.toString();
        if ((localException instanceof Throwable))
          throw new JSchException(str3, localException);
        throw new JSchException(str3);
      }
    Socket[] arrayOfSocket = new Socket[1];
    final Exception[] arrayOfException = new Exception[1];
    String str1 = "";
    Thread localThread = new Thread(new Runnable()
    {
      public void run()
      {
        this.val$sockp[0] = null;
        try
        {
          this.val$sockp[0] = new Socket(paramString, paramInt1);
          return;
        }
        catch (Exception localException1)
        {
          arrayOfException[0] = localException1;
          if ((this.val$sockp[0] == null) || (!this.val$sockp[0].isConnected()));
        }
        try
        {
          this.val$sockp[0].close();
          label67: this.val$sockp[0] = null;
          return;
        }
        catch (Exception localException2)
        {
          break label67;
        }
      }
    });
    localThread.setName("Opening Socket " + paramString);
    localThread.start();
    long l = paramInt2;
    try
    {
      localThread.join(l);
      str1 = "timeout: ";
      label137: if ((arrayOfSocket[0] != null) && (arrayOfSocket[0].isConnected()))
        return arrayOfSocket[0];
      String str2 = str1 + "socket is not established";
      if (arrayOfException[0] != null)
        str2 = arrayOfException[0].toString();
      localThread.interrupt();
      throw new JSchException(str2, arrayOfException[0]);
    }
    catch (InterruptedException localInterruptedException)
    {
      break label137;
    }
  }

  static String diffString(String paramString, String[] paramArrayOfString)
  {
    String[] arrayOfString = split(paramString, ",");
    String str = null;
    int i = 0;
    if (i < arrayOfString.length)
    {
      int j = 0;
      label22: if (j < paramArrayOfString.length)
        if (!arrayOfString[i].equals(paramArrayOfString[j]));
      while (true)
      {
        i++;
        break;
        j++;
        break label22;
        if (str == null)
          str = arrayOfString[i];
        else
          str = str + "," + arrayOfString[i];
      }
    }
    return str;
  }

  static byte[] fromBase64(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws JSchException
  {
    try
    {
      byte[] arrayOfByte1 = new byte[paramInt2];
      int i = 0;
      for (int j = paramInt1; ; j += 4)
      {
        if (j < paramInt1 + paramInt2)
        {
          arrayOfByte1[i] = ((byte)(val(paramArrayOfByte[j]) << 2 | (0x30 & val(paramArrayOfByte[(j + 1)])) >>> 4));
          if (paramArrayOfByte[(j + 2)] != 61)
            break label83;
          i++;
        }
        while (true)
        {
          byte[] arrayOfByte2 = new byte[i];
          System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
          return arrayOfByte2;
          label83: arrayOfByte1[(i + 1)] = ((byte)((0xF & val(paramArrayOfByte[(j + 1)])) << 4 | (0x3C & val(paramArrayOfByte[(j + 2)])) >>> 2));
          if (paramArrayOfByte[(j + 3)] != 61)
            break;
          i += 2;
        }
        arrayOfByte1[(i + 2)] = ((byte)((0x3 & val(paramArrayOfByte[(j + 2)])) << 6 | 0x3F & val(paramArrayOfByte[(j + 3)])));
        i += 3;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new JSchException("fromBase64: invalid base64 data", localArrayIndexOutOfBoundsException);
    }
  }

  static byte[] fromFile(String paramString)
    throws IOException
  {
    String str = checkTilde(paramString);
    File localFile = new File(str);
    FileInputStream localFileInputStream = new FileInputStream(str);
    try
    {
      byte[] arrayOfByte = new byte[(int)localFile.length()];
      int i = 0;
      while (true)
      {
        int j = localFileInputStream.read(arrayOfByte, i, arrayOfByte.length - i);
        if (j <= 0)
        {
          localFileInputStream.close();
          return arrayOfByte;
        }
        i += j;
      }
    }
    finally
    {
      if (localFileInputStream != null)
        localFileInputStream.close();
    }
  }

  static String getFingerPrint(HASH paramHASH, byte[] paramArrayOfByte)
  {
    while (true)
    {
      int i;
      try
      {
        paramHASH.init();
        paramHASH.update(paramArrayOfByte, 0, paramArrayOfByte.length);
        byte[] arrayOfByte = paramHASH.digest();
        StringBuffer localStringBuffer = new StringBuffer();
        i = 0;
        if (i < arrayOfByte.length)
        {
          int j = 0xFF & arrayOfByte[i];
          localStringBuffer.append(chars[(0xF & j >>> 4)]);
          localStringBuffer.append(chars[(j & 0xF)]);
          if (i + 1 < arrayOfByte.length)
            localStringBuffer.append(":");
        }
        else
        {
          String str = localStringBuffer.toString();
          return str;
        }
      }
      catch (Exception localException)
      {
        return "???";
      }
      i++;
    }
  }

  private static boolean glob(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = paramArrayOfByte1.length;
    if (i == 0)
      return false;
    int j = paramArrayOfByte2.length;
    int k = paramInt1;
    int m = paramInt2;
    do
    {
      do
      {
        while (true)
        {
          if ((k >= i) || (m >= j))
            break label416;
          if (paramArrayOfByte1[k] == 92)
          {
            if (k + 1 == i)
              return false;
            int i4 = k + 1;
            if (paramArrayOfByte1[i4] != paramArrayOfByte2[m])
              return false;
            k = i4 + skipUTF8Char(paramArrayOfByte1[i4]);
            m += skipUTF8Char(paramArrayOfByte2[m]);
          }
          else
          {
            if (paramArrayOfByte1[k] == 42)
            {
              while ((k < i) && (paramArrayOfByte1[k] == 42))
                k++;
              if (i == k)
                return true;
              int i2 = paramArrayOfByte1[k];
              if (i2 == 63)
              {
                while (m < j)
                {
                  if (glob(paramArrayOfByte1, k, paramArrayOfByte2, m))
                    return true;
                  m += skipUTF8Char(paramArrayOfByte2[m]);
                }
                return false;
              }
              if (i2 == 92)
              {
                if (k + 1 == i)
                  return false;
                int i3 = k + 1;
                byte b = paramArrayOfByte1[i3];
                while (m < j)
                {
                  if ((b == paramArrayOfByte2[m]) && (glob(paramArrayOfByte1, i3 + skipUTF8Char(b), paramArrayOfByte2, m + skipUTF8Char(paramArrayOfByte2[m]))))
                    return true;
                  m += skipUTF8Char(paramArrayOfByte2[m]);
                }
                return false;
              }
              do
              {
                m += skipUTF8Char(paramArrayOfByte2[m]);
                if (m >= j)
                  break;
              }
              while ((i2 != paramArrayOfByte2[m]) || (!glob(paramArrayOfByte1, k, paramArrayOfByte2, m)));
              return true;
              return false;
            }
            if (paramArrayOfByte1[k] != 63)
              break;
            k++;
            m += skipUTF8Char(paramArrayOfByte2[m]);
          }
        }
        if (paramArrayOfByte1[k] != paramArrayOfByte2[m])
          return false;
        k += skipUTF8Char(paramArrayOfByte1[k]);
        m += skipUTF8Char(paramArrayOfByte2[m]);
      }
      while (m < j);
      if (k >= i)
        return true;
    }
    while (paramArrayOfByte1[k] != 42);
    label416: if ((k == i) && (m == j))
      return true;
    if ((m >= j) && (paramArrayOfByte1[k] == 42));
    int i1;
    for (int n = k; n < i; n = i1)
    {
      i1 = n + 1;
      if (paramArrayOfByte1[n] != 42)
      {
        return false;
        return false;
      }
    }
    return true;
  }

  static boolean glob(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return glob0(paramArrayOfByte1, 0, paramArrayOfByte2, 0);
  }

  private static boolean glob0(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if ((paramArrayOfByte2.length > 0) && (paramArrayOfByte2[0] == 46))
    {
      if ((paramArrayOfByte1.length > 0) && (paramArrayOfByte1[0] == 46))
      {
        if ((paramArrayOfByte1.length == 2) && (paramArrayOfByte1[1] == 42))
          return true;
        return glob(paramArrayOfByte1, paramInt1 + 1, paramArrayOfByte2, paramInt2 + 1);
      }
      return false;
    }
    return glob(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
  }

  static String quote(String paramString)
  {
    byte[] arrayOfByte1 = str2byte(paramString);
    int i = 0;
    for (int j = 0; j < arrayOfByte1.length; j++)
    {
      int i3 = arrayOfByte1[j];
      if ((i3 == 92) || (i3 == 63) || (i3 == 42))
        i++;
    }
    if (i == 0)
      return paramString;
    byte[] arrayOfByte2 = new byte[i + arrayOfByte1.length];
    int k = 0;
    int i2;
    for (int m = 0; k < arrayOfByte1.length; m = i2)
    {
      int n = arrayOfByte1[k];
      if ((n == 92) || (n == 63) || (n == 42))
      {
        int i1 = m + 1;
        arrayOfByte2[m] = 92;
        m = i1;
      }
      i2 = m + 1;
      arrayOfByte2[m] = n;
      k++;
    }
    return byte2str(arrayOfByte2);
  }

  private static int skipUTF8Char(byte paramByte)
  {
    if ((byte)(paramByte & 0x80) == 0);
    do
    {
      return 1;
      if ((byte)(paramByte & 0xE0) == -64)
        return 2;
    }
    while ((byte)(paramByte & 0xF0) != -32);
    return 3;
  }

  static String[] split(String paramString1, String paramString2)
  {
    String[] arrayOfString;
    if (paramString1 == null)
      arrayOfString = null;
    while (true)
    {
      return arrayOfString;
      byte[] arrayOfByte = str2byte(paramString1);
      Vector localVector = new Vector();
      int j;
      for (int i = 0; ; i = j + 1)
      {
        j = paramString1.indexOf(paramString2, i);
        if (j < 0)
          break;
        localVector.addElement(byte2str(arrayOfByte, i, j - i));
      }
      localVector.addElement(byte2str(arrayOfByte, i, arrayOfByte.length - i));
      arrayOfString = new String[localVector.size()];
      for (int k = 0; k < arrayOfString.length; k++)
        arrayOfString[k] = ((String)(String)localVector.elementAt(k));
    }
  }

  static byte[] str2byte(String paramString)
  {
    return str2byte(paramString, "UTF-8");
  }

  static byte[] str2byte(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    try
    {
      byte[] arrayOfByte = paramString1.getBytes(paramString2);
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return paramString1.getBytes();
  }

  static byte[] toBase64(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte1 = new byte[paramInt2 * 2];
    int i = paramInt1 + 3 * (paramInt2 / 3);
    int j = paramInt1;
    int k = 0;
    while (j < i)
    {
      int i13 = 0x3F & paramArrayOfByte[j] >>> 2;
      int i14 = k + 1;
      arrayOfByte1[k] = b64[i13];
      int i15 = (0x3 & paramArrayOfByte[j]) << 4 | 0xF & paramArrayOfByte[(j + 1)] >>> 4;
      int i16 = i14 + 1;
      arrayOfByte1[i14] = b64[i15];
      int i17 = (0xF & paramArrayOfByte[(j + 1)]) << 2 | 0x3 & paramArrayOfByte[(j + 2)] >>> 6;
      int i18 = i16 + 1;
      arrayOfByte1[i16] = b64[i17];
      int i19 = 0x3F & paramArrayOfByte[(j + 2)];
      k = i18 + 1;
      arrayOfByte1[i18] = b64[i19];
      j += 3;
    }
    int m = paramInt1 + paramInt2 - i;
    int i12;
    if (m == 1)
    {
      int i7 = 0x3F & paramArrayOfByte[j] >>> 2;
      int i8 = k + 1;
      arrayOfByte1[k] = b64[i7];
      int i9 = 0x3F & (0x3 & paramArrayOfByte[j]) << 4;
      int i10 = i8 + 1;
      arrayOfByte1[i8] = b64[i9];
      int i11 = i10 + 1;
      arrayOfByte1[i10] = 61;
      i12 = i11 + 1;
      arrayOfByte1[i11] = 61;
    }
    for (int n = i12; ; n = k)
    {
      byte[] arrayOfByte2 = new byte[n];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, n);
      return arrayOfByte2;
      if (m == 2)
      {
        int i1 = 0x3F & paramArrayOfByte[j] >>> 2;
        int i2 = k + 1;
        arrayOfByte1[k] = b64[i1];
        int i3 = (0x3 & paramArrayOfByte[j]) << 4 | 0xF & paramArrayOfByte[(j + 1)] >>> 4;
        int i4 = i2 + 1;
        arrayOfByte1[i2] = b64[i3];
        int i5 = 0x3F & (0xF & paramArrayOfByte[(j + 1)]) << 2;
        int i6 = i4 + 1;
        arrayOfByte1[i4] = b64[i5];
        k = i6 + 1;
        arrayOfByte1[i6] = 61;
      }
    }
  }

  static String toHex(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramArrayOfByte.length)
    {
      String str1 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      StringBuilder localStringBuilder = new StringBuilder().append("0x");
      if (str1.length() == 1);
      for (String str2 = "0"; ; str2 = "")
      {
        localStringBuffer.append(str2 + str1);
        if (i + 1 < paramArrayOfByte.length)
          localStringBuffer.append(":");
        i++;
        break;
      }
    }
    return localStringBuffer.toString();
  }

  static String unquote(String paramString)
  {
    byte[] arrayOfByte1 = str2byte(paramString);
    byte[] arrayOfByte2 = unquote(arrayOfByte1);
    if (arrayOfByte1.length == arrayOfByte2.length)
      return paramString;
    return byte2str(arrayOfByte2);
  }

  static byte[] unquote(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    int j = 0;
    while (true)
    {
      if (j < i)
      {
        if (paramArrayOfByte[j] != 92)
          break label57;
        if (j + 1 != i);
      }
      else
      {
        if (i != paramArrayOfByte.length)
          break;
        return paramArrayOfByte;
      }
      System.arraycopy(paramArrayOfByte, j + 1, paramArrayOfByte, j, paramArrayOfByte.length - (j + 1));
      i--;
      j++;
      continue;
      label57: j++;
    }
    byte[] arrayOfByte = new byte[i];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);
    return arrayOfByte;
  }

  private static byte val(byte paramByte)
  {
    if (paramByte == 61);
    while (true)
    {
      return 0;
      for (int i = 0; i < b64.length; i++)
        if (paramByte == b64[i])
          return (byte)i;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Util
 * JD-Core Version:    0.6.2
 */