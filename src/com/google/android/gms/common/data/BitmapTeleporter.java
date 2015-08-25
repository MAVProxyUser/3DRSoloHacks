package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BitmapTeleporter
  implements SafeParcelable
{
  public static final Parcelable.Creator<BitmapTeleporter> CREATOR = new zza();
  final int zzMG;
  private Bitmap zzRq;
  private boolean zzRr;
  private File zzRs;
  final int zzzH;
  ParcelFileDescriptor zzzI;

  BitmapTeleporter(int paramInt1, ParcelFileDescriptor paramParcelFileDescriptor, int paramInt2)
  {
    this.zzzH = paramInt1;
    this.zzzI = paramParcelFileDescriptor;
    this.zzMG = paramInt2;
    this.zzRq = null;
    this.zzRr = false;
  }

  public BitmapTeleporter(Bitmap paramBitmap)
  {
    this.zzzH = 1;
    this.zzzI = null;
    this.zzMG = 0;
    this.zzRq = paramBitmap;
    this.zzRr = true;
  }

  private void zza(Closeable paramCloseable)
  {
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
      Log.w("BitmapTeleporter", "Could not close stream", localIOException);
    }
  }

  // ERROR //
  private java.io.FileOutputStream zzlo()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 67	com/google/android/gms/common/data/BitmapTeleporter:zzRs	Ljava/io/File;
    //   4: ifnonnull +13 -> 17
    //   7: new 69	java/lang/IllegalStateException
    //   10: dup
    //   11: ldc 71
    //   13: invokespecial 74	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   16: athrow
    //   17: ldc 76
    //   19: ldc 78
    //   21: aload_0
    //   22: getfield 67	com/google/android/gms/common/data/BitmapTeleporter:zzRs	Ljava/io/File;
    //   25: invokestatic 84	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/io/File;
    //   28: astore_2
    //   29: new 86	java/io/FileOutputStream
    //   32: dup
    //   33: aload_2
    //   34: invokespecial 89	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   37: astore_3
    //   38: aload_0
    //   39: aload_2
    //   40: ldc 90
    //   42: invokestatic 96	android/os/ParcelFileDescriptor:open	(Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;
    //   45: putfield 35	com/google/android/gms/common/data/BitmapTeleporter:zzzI	Landroid/os/ParcelFileDescriptor;
    //   48: aload_2
    //   49: invokevirtual 100	java/io/File:delete	()Z
    //   52: pop
    //   53: aload_3
    //   54: areturn
    //   55: astore_1
    //   56: new 69	java/lang/IllegalStateException
    //   59: dup
    //   60: ldc 102
    //   62: aload_1
    //   63: invokespecial 105	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   66: athrow
    //   67: astore 4
    //   69: new 69	java/lang/IllegalStateException
    //   72: dup
    //   73: ldc 107
    //   75: invokespecial 74	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   78: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   17	29	55	java/io/IOException
    //   29	48	67	java/io/FileNotFoundException
  }

  public int describeContents()
  {
    return 0;
  }

  public void release()
  {
    if (!this.zzRr);
    try
    {
      this.zzzI.close();
      return;
    }
    catch (IOException localIOException)
    {
      Log.w("BitmapTeleporter", "Could not close PFD", localIOException);
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    Bitmap localBitmap;
    byte[] arrayOfByte;
    DataOutputStream localDataOutputStream;
    if (this.zzzI == null)
    {
      localBitmap = this.zzRq;
      ByteBuffer localByteBuffer = ByteBuffer.allocate(localBitmap.getRowBytes() * localBitmap.getHeight());
      localBitmap.copyPixelsToBuffer(localByteBuffer);
      arrayOfByte = localByteBuffer.array();
      localDataOutputStream = new DataOutputStream(zzlo());
    }
    try
    {
      localDataOutputStream.writeInt(arrayOfByte.length);
      localDataOutputStream.writeInt(localBitmap.getWidth());
      localDataOutputStream.writeInt(localBitmap.getHeight());
      localDataOutputStream.writeUTF(localBitmap.getConfig().toString());
      localDataOutputStream.write(arrayOfByte);
      zza(localDataOutputStream);
      zza.zza(this, paramParcel, paramInt | 0x1);
      this.zzzI = null;
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("Could not write into unlinked file", localIOException);
    }
    finally
    {
      zza(localDataOutputStream);
    }
  }

  public void zzc(File paramFile)
  {
    if (paramFile == null)
      throw new NullPointerException("Cannot set null temp directory");
    this.zzRs = paramFile;
  }

  public Bitmap zzln()
  {
    DataInputStream localDataInputStream;
    if (!this.zzRr)
      localDataInputStream = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(this.zzzI));
    try
    {
      byte[] arrayOfByte = new byte[localDataInputStream.readInt()];
      int i = localDataInputStream.readInt();
      int j = localDataInputStream.readInt();
      Bitmap.Config localConfig = Bitmap.Config.valueOf(localDataInputStream.readUTF());
      localDataInputStream.read(arrayOfByte);
      zza(localDataInputStream);
      ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte);
      Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
      localBitmap.copyPixelsFromBuffer(localByteBuffer);
      this.zzRq = localBitmap;
      this.zzRr = true;
      return this.zzRq;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("Could not read from parcel file descriptor", localIOException);
    }
    finally
    {
      zza(localDataInputStream);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.BitmapTeleporter
 * JD-Core Version:    0.6.2
 */