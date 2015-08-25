package com.google.android.gms.internal;

import android.os.ParcelFileDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class zzhy
{
  public static long zza(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    return zza(paramInputStream, paramOutputStream, false);
  }

  public static long zza(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException
  {
    return zza(paramInputStream, paramOutputStream, paramBoolean, 1024);
  }

  public static long zza(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    long l = 0L;
    try
    {
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte, 0, arrayOfByte.length);
        if (i == -1)
          break;
        l += i;
        paramOutputStream.write(arrayOfByte, 0, i);
      }
    }
    finally
    {
      if (paramBoolean)
      {
        zzb(paramInputStream);
        zzb(paramOutputStream);
      }
    }
    if (paramBoolean)
    {
      zzb(paramInputStream);
      zzb(paramOutputStream);
    }
    return l;
  }

  public static void zza(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (paramParcelFileDescriptor != null);
    try
    {
      paramParcelFileDescriptor.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static byte[] zza(InputStream paramInputStream, boolean paramBoolean)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    zza(paramInputStream, localByteArrayOutputStream, paramBoolean);
    return localByteArrayOutputStream.toByteArray();
  }

  public static void zzb(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static byte[] zzd(InputStream paramInputStream)
    throws IOException
  {
    return zza(paramInputStream, true);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhy
 * JD-Core Version:    0.6.2
 */