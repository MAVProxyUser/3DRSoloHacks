package com.google.android.gms.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zznz
{
  private static void zza(String paramString, Object paramObject, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
    throws IllegalAccessException, InvocationTargetException
  {
    if (paramObject == null);
    while (true)
    {
      return;
      int i;
      Class localClass1;
      label183: label231: int n;
      label258: String str4;
      if ((paramObject instanceof zzny))
      {
        i = paramStringBuffer1.length();
        if (paramString != null)
        {
          paramStringBuffer2.append(paramStringBuffer1).append(zzeC(paramString)).append(" <\n");
          paramStringBuffer1.append("  ");
        }
        localClass1 = paramObject.getClass();
        Field[] arrayOfField = localClass1.getFields();
        int j = arrayOfField.length;
        int k = 0;
        if (k < j)
        {
          Field localField = arrayOfField[k];
          int i1 = localField.getModifiers();
          String str5 = localField.getName();
          Object localObject;
          if (((i1 & 0x1) == 1) && ((i1 & 0x8) != 8) && (!str5.startsWith("_")) && (!str5.endsWith("_")))
          {
            Class localClass2 = localField.getType();
            localObject = localField.get(paramObject);
            if (!localClass2.isArray())
              break label231;
            if (localClass2.getComponentType() != Byte.TYPE)
              break label183;
            zza(str5, localObject, paramStringBuffer1, paramStringBuffer2);
          }
          while (true)
          {
            k++;
            break;
            if (localObject == null);
            for (int i2 = 0; ; i2 = Array.getLength(localObject))
            {
              for (int i3 = 0; i3 < i2; i3++)
                zza(str5, Array.get(localObject, i3), paramStringBuffer1, paramStringBuffer2);
              break;
            }
            zza(str5, localObject, paramStringBuffer1, paramStringBuffer2);
          }
        }
        Method[] arrayOfMethod = localClass1.getMethods();
        int m = arrayOfMethod.length;
        n = 0;
        if (n < m)
        {
          String str3 = arrayOfMethod[n].getName();
          if (str3.startsWith("set"))
            str4 = str3.substring(3);
        }
      }
      try
      {
        Method localMethod1 = localClass1.getMethod("has" + str4, new Class[0]);
        if (!((Boolean)localMethod1.invoke(paramObject, new Object[0])).booleanValue());
        while (true)
          while (true)
          {
            label343: n++;
            break label258;
            try
            {
              Method localMethod2 = localClass1.getMethod("get" + str4, new Class[0]);
              zza(str4, localMethod2.invoke(paramObject, new Object[0]), paramStringBuffer1, paramStringBuffer2);
              continue;
              if (paramString == null)
                break;
              paramStringBuffer1.setLength(i);
              paramStringBuffer2.append(paramStringBuffer1).append(">\n");
              return;
              String str1 = zzeC(paramString);
              paramStringBuffer2.append(paramStringBuffer1).append(str1).append(": ");
              if ((paramObject instanceof String))
              {
                String str2 = zzeD((String)paramObject);
                paramStringBuffer2.append("\"").append(str2).append("\"");
              }
              while (true)
              {
                paramStringBuffer2.append("\n");
                return;
                if ((paramObject instanceof byte[]))
                  zza((byte[])paramObject, paramStringBuffer2);
                else
                  paramStringBuffer2.append(paramObject);
              }
            }
            catch (NoSuchMethodException localNoSuchMethodException2)
            {
            }
          }
      }
      catch (NoSuchMethodException localNoSuchMethodException1)
      {
        break label343;
      }
    }
  }

  private static void zza(byte[] paramArrayOfByte, StringBuffer paramStringBuffer)
  {
    if (paramArrayOfByte == null)
    {
      paramStringBuffer.append("\"\"");
      return;
    }
    paramStringBuffer.append('"');
    int i = 0;
    if (i < paramArrayOfByte.length)
    {
      int j = 0xFF & paramArrayOfByte[i];
      if ((j == 92) || (j == 34))
        paramStringBuffer.append('\\').append((char)j);
      while (true)
      {
        i++;
        break;
        if ((j >= 32) && (j < 127))
        {
          paramStringBuffer.append((char)j);
        }
        else
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(j);
          paramStringBuffer.append(String.format("\\%03o", arrayOfObject));
        }
      }
    }
    paramStringBuffer.append('"');
  }

  private static String zzbY(String paramString)
  {
    int i = paramString.length();
    StringBuilder localStringBuilder = new StringBuilder(i);
    int j = 0;
    if (j < i)
    {
      char c = paramString.charAt(j);
      if ((c >= ' ') && (c <= '~') && (c != '"') && (c != '\''))
        localStringBuilder.append(c);
      while (true)
      {
        j++;
        break;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(c);
        localStringBuilder.append(String.format("\\u%04x", arrayOfObject));
      }
    }
    return localStringBuilder.toString();
  }

  private static String zzeC(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramString.length())
    {
      char c = paramString.charAt(i);
      if (i == 0)
        localStringBuffer.append(Character.toLowerCase(c));
      while (true)
      {
        i++;
        break;
        if (Character.isUpperCase(c))
          localStringBuffer.append('_').append(Character.toLowerCase(c));
        else
          localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }

  private static String zzeD(String paramString)
  {
    if ((!paramString.startsWith("http")) && (paramString.length() > 200))
      paramString = paramString.substring(0, 200) + "[...]";
    return zzbY(paramString);
  }

  public static <T extends zzny> String zzg(T paramT)
  {
    if (paramT == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      zza(null, paramT, new StringBuffer(), localStringBuffer);
      return localStringBuffer.toString();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      return "Error printing proto: " + localIllegalAccessException.getMessage();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return "Error printing proto: " + localInvocationTargetException.getMessage();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznz
 * JD-Core Version:    0.6.2
 */