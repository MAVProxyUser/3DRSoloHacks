package com.o3dr.solo.android.util;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{
  private static void addZipDir(File paramFile, ZipOutputStream paramZipOutputStream, String paramString)
    throws IOException
  {
    File[] arrayOfFile = paramFile.listFiles();
    byte[] arrayOfByte = new byte[1024];
    String str1 = ensurePrefixWithSlash(paramFile, paramString);
    int i = 0;
    if (i < arrayOfFile.length)
    {
      File localFile = arrayOfFile[i];
      if (localFile.isDirectory())
      {
        String str3 = str1 + localFile.getName() + "/";
        paramZipOutputStream.putNextEntry(new ZipEntry(str3));
        paramZipOutputStream.closeEntry();
        addZipDir(localFile, paramZipOutputStream, str3);
      }
      while (true)
      {
        i++;
        break;
        if (localFile.isFile())
        {
          String str2 = str1 + localFile.getName();
          FileInputStream localFileInputStream = new FileInputStream(localFile.getAbsolutePath());
          try
          {
            paramZipOutputStream.putNextEntry(new ZipEntry(str2));
            while (true)
            {
              int j = localFileInputStream.read(arrayOfByte);
              if (j <= 0)
                break;
              paramZipOutputStream.write(arrayOfByte, 0, j);
            }
          }
          finally
          {
            localFileInputStream.close();
          }
          paramZipOutputStream.closeEntry();
          localFileInputStream.close();
        }
      }
    }
  }

  private static void addZipPrefixes(File paramFile, ZipOutputStream paramZipOutputStream, String paramString)
    throws IOException
  {
    String[] arrayOfString = ensurePrefixWithSlash(paramFile, paramString).split("/");
    String str = "";
    for (int i = 0; i < arrayOfString.length; i++)
    {
      paramZipOutputStream.putNextEntry(new ZipEntry(str + arrayOfString[i] + "/"));
      paramZipOutputStream.closeEntry();
      str = arrayOfString[i] + "/";
    }
  }

  private static String ensurePrefixWithSlash(File paramFile, String paramString)
  {
    String str = paramString;
    if ((!TextUtils.isEmpty(str)) && (!str.equals("/")) && (!str.equals(" ")))
    {
      if (str.charAt(0) == '/')
        str = str.substring(1);
      if (str.charAt(-1 + str.length()) != '/')
        str = str + "/";
      return str;
    }
    return paramFile.getName() + "/";
  }

  public static void zipDir(File paramFile1, File paramFile2, String paramString)
    throws IOException
  {
    ZipOutputStream localZipOutputStream = new ZipOutputStream(new FileOutputStream(paramFile1));
    try
    {
      addZipPrefixes(paramFile2, localZipOutputStream, paramString);
      addZipDir(paramFile2, localZipOutputStream, paramString);
      return;
    }
    finally
    {
      localZipOutputStream.close();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.ZipUtils
 * JD-Core Version:    0.6.2
 */