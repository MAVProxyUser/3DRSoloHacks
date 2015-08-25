package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class QueueFileEventStorage
  implements EventsStorage
{
  private final Context context;
  private QueueFile queueFile;
  private File targetDirectory;
  private final String targetDirectoryName;
  private final File workingDirectory;
  private final File workingFile;

  public QueueFileEventStorage(Context paramContext, File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    this.context = paramContext;
    this.workingDirectory = paramFile;
    this.targetDirectoryName = paramString2;
    this.workingFile = new File(this.workingDirectory, paramString1);
    this.queueFile = new QueueFile(this.workingFile);
    createTargetDirectory();
  }

  private void createTargetDirectory()
  {
    this.targetDirectory = new File(this.workingDirectory, this.targetDirectoryName);
    if (!this.targetDirectory.exists())
      this.targetDirectory.mkdirs();
  }

  // ERROR //
  private void move(File paramFile1, File paramFile2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 59	java/io/FileInputStream
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 60	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   13: astore 5
    //   15: aload_0
    //   16: aload_2
    //   17: invokevirtual 64	io/fabric/sdk/android/services/events/QueueFileEventStorage:getMoveOutputStream	(Ljava/io/File;)Ljava/io/OutputStream;
    //   20: astore_3
    //   21: aload 5
    //   23: aload_3
    //   24: sipush 1024
    //   27: newarray byte
    //   29: invokestatic 70	io/fabric/sdk/android/services/common/CommonUtils:copyStream	(Ljava/io/InputStream;Ljava/io/OutputStream;[B)V
    //   32: aload 5
    //   34: ldc 72
    //   36: invokestatic 76	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   39: aload_3
    //   40: ldc 78
    //   42: invokestatic 76	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   45: aload_1
    //   46: invokevirtual 81	java/io/File:delete	()Z
    //   49: pop
    //   50: return
    //   51: astore 6
    //   53: aload 4
    //   55: ldc 72
    //   57: invokestatic 76	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   60: aload_3
    //   61: ldc 78
    //   63: invokestatic 76	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   66: aload_1
    //   67: invokevirtual 81	java/io/File:delete	()Z
    //   70: pop
    //   71: aload 6
    //   73: athrow
    //   74: astore 6
    //   76: aload 5
    //   78: astore 4
    //   80: goto -27 -> 53
    //
    // Exception table:
    //   from	to	target	type
    //   5	15	51	finally
    //   15	32	74	finally
  }

  public void add(byte[] paramArrayOfByte)
    throws IOException
  {
    this.queueFile.add(paramArrayOfByte);
  }

  public boolean canWorkingFileStore(int paramInt1, int paramInt2)
  {
    return this.queueFile.hasSpaceFor(paramInt1, paramInt2);
  }

  public void deleteFilesInRollOverDirectory(List<File> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      File localFile = (File)localIterator.next();
      Context localContext = this.context;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localFile.getName();
      CommonUtils.logControlled(localContext, String.format("deleting sent analytics file %s", arrayOfObject));
      localFile.delete();
    }
  }

  public void deleteWorkingFile()
  {
    try
    {
      this.queueFile.close();
      label7: this.workingFile.delete();
      return;
    }
    catch (IOException localIOException)
    {
      break label7;
    }
  }

  public List<File> getAllFilesInRollOverDirectory()
  {
    return Arrays.asList(this.targetDirectory.listFiles());
  }

  public List<File> getBatchOfFilesToSend(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = this.targetDirectory.listFiles();
    int i = arrayOfFile.length;
    for (int j = 0; ; j++)
      if (j < i)
      {
        localArrayList.add(arrayOfFile[j]);
        if (localArrayList.size() < paramInt);
      }
      else
      {
        return localArrayList;
      }
  }

  public OutputStream getMoveOutputStream(File paramFile)
    throws IOException
  {
    return new FileOutputStream(paramFile);
  }

  public File getRollOverDirectory()
  {
    return this.targetDirectory;
  }

  public File getWorkingDirectory()
  {
    return this.workingDirectory;
  }

  public int getWorkingFileUsedSizeInBytes()
  {
    return this.queueFile.usedBytes();
  }

  public boolean isWorkingFileEmpty()
  {
    return this.queueFile.isEmpty();
  }

  public void rollOver(String paramString)
    throws IOException
  {
    this.queueFile.close();
    move(this.workingFile, new File(this.targetDirectory, paramString));
    this.queueFile = new QueueFile(this.workingFile);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.QueueFileEventStorage
 * JD-Core Version:    0.6.2
 */