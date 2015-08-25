package com.nostra13.universalimageloader.cache.disc.impl.ext;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DiskLruCache
  implements Closeable
{
  static final long ANY_SEQUENCE_NUMBER = -1L;
  private static final String CLEAN = "CLEAN";
  private static final String DIRTY = "DIRTY";
  static final String JOURNAL_FILE = "journal";
  static final String JOURNAL_FILE_BACKUP = "journal.bkp";
  static final String JOURNAL_FILE_TEMP = "journal.tmp";
  static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,64}");
  static final String MAGIC = "libcore.io.DiskLruCache";
  private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream()
  {
    public void write(int paramAnonymousInt)
      throws IOException
    {
    }
  };
  private static final String READ = "READ";
  private static final String REMOVE = "REMOVE";
  static final String VERSION_1 = "1";
  private final int appVersion;
  private final Callable<Void> cleanupCallable = new Callable()
  {
    public Void call()
      throws Exception
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.this.journalWriter == null)
          return null;
        DiskLruCache.this.trimToSize();
        DiskLruCache.this.trimToFileCount();
        if (DiskLruCache.this.journalRebuildRequired())
        {
          DiskLruCache.this.rebuildJournal();
          DiskLruCache.access$502(DiskLruCache.this, 0);
        }
        return null;
      }
    }
  };
  private final File directory;
  final ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  private int fileCount = 0;
  private final File journalFile;
  private final File journalFileBackup;
  private final File journalFileTmp;
  private Writer journalWriter;
  private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75F, true);
  private int maxFileCount;
  private long maxSize;
  private long nextSequenceNumber = 0L;
  private int redundantOpCount;
  private long size = 0L;
  private final int valueCount;

  private DiskLruCache(File paramFile, int paramInt1, int paramInt2, long paramLong, int paramInt3)
  {
    this.directory = paramFile;
    this.appVersion = paramInt1;
    this.journalFile = new File(paramFile, "journal");
    this.journalFileTmp = new File(paramFile, "journal.tmp");
    this.journalFileBackup = new File(paramFile, "journal.bkp");
    this.valueCount = paramInt2;
    this.maxSize = paramLong;
    this.maxFileCount = paramInt3;
  }

  private void checkNotClosed()
  {
    if (this.journalWriter == null)
      throw new IllegalStateException("cache is closed");
  }

  private void completeEdit(Editor paramEditor, boolean paramBoolean)
    throws IOException
  {
    Entry localEntry;
    try
    {
      localEntry = paramEditor.entry;
      if (localEntry.currentEditor != paramEditor)
        throw new IllegalStateException();
    }
    finally
    {
    }
    if ((paramBoolean) && (!localEntry.readable))
      for (int j = 0; j < this.valueCount; j++)
      {
        if (paramEditor.written[j] == 0)
        {
          paramEditor.abort();
          throw new IllegalStateException("Newly created entry didn't create value for index " + j);
        }
        if (!localEntry.getDirtyFile(j).exists())
        {
          paramEditor.abort();
          return;
        }
      }
    for (int i = 0; ; i++)
      if (i < this.valueCount)
      {
        File localFile1 = localEntry.getDirtyFile(i);
        if (paramBoolean)
        {
          if (localFile1.exists())
          {
            File localFile2 = localEntry.getCleanFile(i);
            localFile1.renameTo(localFile2);
            long l2 = localEntry.lengths[i];
            long l3 = localFile2.length();
            localEntry.lengths[i] = l3;
            this.size = (l3 + (this.size - l2));
            this.fileCount = (1 + this.fileCount);
          }
        }
        else
          deleteIfExists(localFile1);
      }
      else
      {
        this.redundantOpCount = (1 + this.redundantOpCount);
        Entry.access$802(localEntry, null);
        if ((paramBoolean | localEntry.readable))
        {
          Entry.access$702(localEntry, true);
          this.journalWriter.write("CLEAN " + localEntry.key + localEntry.getLengths() + '\n');
          if (paramBoolean)
          {
            long l1 = this.nextSequenceNumber;
            this.nextSequenceNumber = (1L + l1);
            Entry.access$1302(localEntry, l1);
          }
        }
        while (true)
        {
          this.journalWriter.flush();
          if ((this.size <= this.maxSize) && (this.fileCount <= this.maxFileCount) && (!journalRebuildRequired()))
            break;
          this.executorService.submit(this.cleanupCallable);
          break;
          this.lruEntries.remove(localEntry.key);
          this.journalWriter.write("REMOVE " + localEntry.key + '\n');
        }
      }
  }

  private static void deleteIfExists(File paramFile)
    throws IOException
  {
    if ((paramFile.exists()) && (!paramFile.delete()))
      throw new IOException();
  }

  private Editor edit(String paramString, long paramLong)
    throws IOException
  {
    while (true)
    {
      Entry localEntry;
      Editor localEditor1;
      try
      {
        checkNotClosed();
        validateKey(paramString);
        localEntry = (Entry)this.lruEntries.get(paramString);
        if (paramLong != -1L)
        {
          localEditor1 = null;
          if (localEntry != null)
          {
            long l = localEntry.sequenceNumber;
            boolean bool = l < paramLong;
            localEditor1 = null;
            if (!bool);
          }
          else
          {
            return localEditor1;
          }
        }
        if (localEntry == null)
        {
          localEntry = new Entry(paramString, null);
          this.lruEntries.put(paramString, localEntry);
          localEditor1 = new Editor(localEntry, null);
          Entry.access$802(localEntry, localEditor1);
          this.journalWriter.write("DIRTY " + paramString + '\n');
          this.journalWriter.flush();
          continue;
        }
      }
      finally
      {
      }
      Editor localEditor2 = localEntry.currentEditor;
      if (localEditor2 != null)
        localEditor1 = null;
    }
  }

  private static String inputStreamToString(InputStream paramInputStream)
    throws IOException
  {
    return Util.readFully(new InputStreamReader(paramInputStream, Util.UTF_8));
  }

  private boolean journalRebuildRequired()
  {
    return (this.redundantOpCount >= 2000) && (this.redundantOpCount >= this.lruEntries.size());
  }

  public static DiskLruCache open(File paramFile, int paramInt1, int paramInt2, long paramLong, int paramInt3)
    throws IOException
  {
    if (paramLong <= 0L)
      throw new IllegalArgumentException("maxSize <= 0");
    if (paramInt3 <= 0)
      throw new IllegalArgumentException("maxFileCount <= 0");
    if (paramInt2 <= 0)
      throw new IllegalArgumentException("valueCount <= 0");
    File localFile1 = new File(paramFile, "journal.bkp");
    File localFile2;
    if (localFile1.exists())
    {
      localFile2 = new File(paramFile, "journal");
      if (!localFile2.exists())
        break label168;
      localFile1.delete();
    }
    while (true)
    {
      DiskLruCache localDiskLruCache1 = new DiskLruCache(paramFile, paramInt1, paramInt2, paramLong, paramInt3);
      if (localDiskLruCache1.journalFile.exists())
        try
        {
          localDiskLruCache1.readJournal();
          localDiskLruCache1.processJournal();
          localDiskLruCache1.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localDiskLruCache1.journalFile, true), Util.US_ASCII));
          return localDiskLruCache1;
          label168: renameTo(localFile1, localFile2, false);
        }
        catch (IOException localIOException)
        {
          System.out.println("DiskLruCache " + paramFile + " is corrupt: " + localIOException.getMessage() + ", removing");
          localDiskLruCache1.delete();
        }
    }
    paramFile.mkdirs();
    DiskLruCache localDiskLruCache2 = new DiskLruCache(paramFile, paramInt1, paramInt2, paramLong, paramInt3);
    localDiskLruCache2.rebuildJournal();
    return localDiskLruCache2;
  }

  private void processJournal()
    throws IOException
  {
    deleteIfExists(this.journalFileTmp);
    Iterator localIterator = this.lruEntries.values().iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      if (localEntry.currentEditor == null)
      {
        for (int j = 0; j < this.valueCount; j++)
        {
          this.size += localEntry.lengths[j];
          this.fileCount = (1 + this.fileCount);
        }
      }
      else
      {
        Entry.access$802(localEntry, null);
        for (int i = 0; i < this.valueCount; i++)
        {
          deleteIfExists(localEntry.getCleanFile(i));
          deleteIfExists(localEntry.getDirtyFile(i));
        }
        localIterator.remove();
      }
    }
  }

  // ERROR //
  private void readJournal()
    throws IOException
  {
    // Byte code:
    //   0: new 449	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader
    //   3: dup
    //   4: new 451	java/io/FileInputStream
    //   7: dup
    //   8: aload_0
    //   9: getfield 133	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache:journalFile	Ljava/io/File;
    //   12: invokespecial 453	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   15: getstatic 387	com/nostra13/universalimageloader/cache/disc/impl/ext/Util:US_ASCII	Ljava/nio/charset/Charset;
    //   18: invokespecial 454	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:<init>	(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   21: astore_1
    //   22: aload_1
    //   23: invokevirtual 457	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:readLine	()Ljava/lang/String;
    //   26: astore_3
    //   27: aload_1
    //   28: invokevirtual 457	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:readLine	()Ljava/lang/String;
    //   31: astore 4
    //   33: aload_1
    //   34: invokevirtual 457	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:readLine	()Ljava/lang/String;
    //   37: astore 5
    //   39: aload_1
    //   40: invokevirtual 457	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:readLine	()Ljava/lang/String;
    //   43: astore 6
    //   45: aload_1
    //   46: invokevirtual 457	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:readLine	()Ljava/lang/String;
    //   49: astore 7
    //   51: ldc 29
    //   53: aload_3
    //   54: invokevirtual 463	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   57: ifeq +54 -> 111
    //   60: ldc 38
    //   62: aload 4
    //   64: invokevirtual 463	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   67: ifeq +44 -> 111
    //   70: aload_0
    //   71: getfield 126	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache:appVersion	I
    //   74: invokestatic 468	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   77: aload 5
    //   79: invokevirtual 463	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   82: ifeq +29 -> 111
    //   85: aload_0
    //   86: getfield 139	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache:valueCount	I
    //   89: invokestatic 468	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   92: aload 6
    //   94: invokevirtual 463	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   97: ifeq +14 -> 111
    //   100: ldc_w 470
    //   103: aload 7
    //   105: invokevirtual 463	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   108: ifne +77 -> 185
    //   111: new 150	java/io/IOException
    //   114: dup
    //   115: new 228	java/lang/StringBuilder
    //   118: dup
    //   119: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   122: ldc_w 472
    //   125: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload_3
    //   129: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: ldc_w 474
    //   135: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: aload 4
    //   140: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: ldc_w 474
    //   146: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: aload 6
    //   151: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: ldc_w 474
    //   157: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: aload 7
    //   162: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: ldc_w 476
    //   168: invokevirtual 235	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: invokevirtual 242	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   174: invokespecial 477	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   177: athrow
    //   178: astore_2
    //   179: aload_1
    //   180: invokestatic 481	com/nostra13/universalimageloader/cache/disc/impl/ext/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   183: aload_2
    //   184: athrow
    //   185: iconst_0
    //   186: istore 8
    //   188: aload_0
    //   189: aload_1
    //   190: invokevirtual 457	com/nostra13/universalimageloader/cache/disc/impl/ext/StrictLineReader:readLine	()Ljava/lang/String;
    //   193: invokespecial 484	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache:readJournalLine	(Ljava/lang/String;)V
    //   196: iinc 8 1
    //   199: goto -11 -> 188
    //   202: astore 9
    //   204: aload_0
    //   205: iload 8
    //   207: aload_0
    //   208: getfield 95	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache:lruEntries	Ljava/util/LinkedHashMap;
    //   211: invokevirtual 356	java/util/LinkedHashMap:size	()I
    //   214: isub
    //   215: putfield 194	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache:redundantOpCount	I
    //   218: aload_1
    //   219: invokestatic 481	com/nostra13/universalimageloader/cache/disc/impl/ext/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   222: return
    //
    // Exception table:
    //   from	to	target	type
    //   22	111	178	finally
    //   111	178	178	finally
    //   188	196	178	finally
    //   204	218	178	finally
    //   188	196	202	java/io/EOFException
  }

  private void readJournalLine(String paramString)
    throws IOException
  {
    int i = paramString.indexOf(' ');
    if (i == -1)
      throw new IOException("unexpected journal line: " + paramString);
    int j = i + 1;
    int k = paramString.indexOf(' ', j);
    String str;
    if (k == -1)
    {
      str = paramString.substring(j);
      if ((i != "REMOVE".length()) || (!paramString.startsWith("REMOVE")))
        break label104;
      this.lruEntries.remove(str);
    }
    label104: 
    do
    {
      return;
      str = paramString.substring(j, k);
      Entry localEntry = (Entry)this.lruEntries.get(str);
      if (localEntry == null)
      {
        localEntry = new Entry(str, null);
        this.lruEntries.put(str, localEntry);
      }
      if ((k != -1) && (i == "CLEAN".length()) && (paramString.startsWith("CLEAN")))
      {
        String[] arrayOfString = paramString.substring(k + 1).split(" ");
        Entry.access$702(localEntry, true);
        Entry.access$802(localEntry, null);
        localEntry.setLengths(arrayOfString);
        return;
      }
      if ((k == -1) && (i == "DIRTY".length()) && (paramString.startsWith("DIRTY")))
      {
        Entry.access$802(localEntry, new Editor(localEntry, null));
        return;
      }
    }
    while ((k == -1) && (i == "READ".length()) && (paramString.startsWith("READ")));
    throw new IOException("unexpected journal line: " + paramString);
  }

  private void rebuildJournal()
    throws IOException
  {
    BufferedWriter localBufferedWriter;
    while (true)
    {
      Entry localEntry;
      try
      {
        if (this.journalWriter != null)
          this.journalWriter.close();
        localBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), Util.US_ASCII));
        try
        {
          localBufferedWriter.write("libcore.io.DiskLruCache");
          localBufferedWriter.write("\n");
          localBufferedWriter.write("1");
          localBufferedWriter.write("\n");
          localBufferedWriter.write(Integer.toString(this.appVersion));
          localBufferedWriter.write("\n");
          localBufferedWriter.write(Integer.toString(this.valueCount));
          localBufferedWriter.write("\n");
          localBufferedWriter.write("\n");
          Iterator localIterator = this.lruEntries.values().iterator();
          if (!localIterator.hasNext())
            break;
          localEntry = (Entry)localIterator.next();
          if (localEntry.currentEditor != null)
          {
            localBufferedWriter.write("DIRTY " + localEntry.key + '\n');
            continue;
          }
        }
        finally
        {
          localBufferedWriter.close();
        }
      }
      finally
      {
      }
      localBufferedWriter.write("CLEAN " + localEntry.key + localEntry.getLengths() + '\n');
    }
    localBufferedWriter.close();
    if (this.journalFile.exists())
      renameTo(this.journalFile, this.journalFileBackup, true);
    renameTo(this.journalFileTmp, this.journalFile, false);
    this.journalFileBackup.delete();
    this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), Util.US_ASCII));
  }

  private static void renameTo(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean)
      deleteIfExists(paramFile2);
    if (!paramFile1.renameTo(paramFile2))
      throw new IOException();
  }

  private void trimToFileCount()
    throws IOException
  {
    while (this.fileCount > this.maxFileCount)
      remove((String)((Map.Entry)this.lruEntries.entrySet().iterator().next()).getKey());
  }

  private void trimToSize()
    throws IOException
  {
    while (this.size > this.maxSize)
      remove((String)((Map.Entry)this.lruEntries.entrySet().iterator().next()).getKey());
  }

  private void validateKey(String paramString)
  {
    if (!LEGAL_KEY_PATTERN.matcher(paramString).matches())
      throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + paramString + "\"");
  }

  public void close()
    throws IOException
  {
    while (true)
    {
      try
      {
        Writer localWriter = this.journalWriter;
        if (localWriter == null)
          return;
        Iterator localIterator = new ArrayList(this.lruEntries.values()).iterator();
        if (localIterator.hasNext())
        {
          Entry localEntry = (Entry)localIterator.next();
          if (localEntry.currentEditor == null)
            continue;
          localEntry.currentEditor.abort();
          continue;
        }
      }
      finally
      {
      }
      trimToSize();
      trimToFileCount();
      this.journalWriter.close();
      this.journalWriter = null;
    }
  }

  public void delete()
    throws IOException
  {
    close();
    Util.deleteContents(this.directory);
  }

  public Editor edit(String paramString)
    throws IOException
  {
    return edit(paramString, -1L);
  }

  public long fileCount()
  {
    try
    {
      int i = this.fileCount;
      long l = i;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void flush()
    throws IOException
  {
    try
    {
      checkNotClosed();
      trimToSize();
      trimToFileCount();
      this.journalWriter.flush();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Snapshot get(String paramString)
    throws IOException
  {
    try
    {
      checkNotClosed();
      validateKey(paramString);
      Entry localEntry = (Entry)this.lruEntries.get(paramString);
      Snapshot localSnapshot = null;
      if (localEntry == null);
      while (true)
      {
        return localSnapshot;
        boolean bool = localEntry.readable;
        localSnapshot = null;
        if (bool)
        {
          File[] arrayOfFile = new File[this.valueCount];
          InputStream[] arrayOfInputStream = new InputStream[this.valueCount];
          int i = 0;
          while (true)
          {
            int j;
            int k;
            try
            {
              if (i >= this.valueCount)
                break label164;
              File localFile = localEntry.getCleanFile(i);
              arrayOfFile[i] = localFile;
              arrayOfInputStream[i] = new FileInputStream(localFile);
              i++;
              continue;
            }
            catch (FileNotFoundException localFileNotFoundException)
            {
              j = 0;
              k = this.valueCount;
              localSnapshot = null;
            }
            if (j >= k)
              break;
            InputStream localInputStream = arrayOfInputStream[j];
            localSnapshot = null;
            if (localInputStream == null)
              break;
            Util.closeQuietly(arrayOfInputStream[j]);
            j++;
          }
          label164: this.redundantOpCount = (1 + this.redundantOpCount);
          this.journalWriter.append("READ " + paramString + '\n');
          if (journalRebuildRequired())
            this.executorService.submit(this.cleanupCallable);
          localSnapshot = new Snapshot(paramString, localEntry.sequenceNumber, arrayOfFile, arrayOfInputStream, localEntry.lengths, null);
        }
      }
    }
    finally
    {
    }
  }

  public File getDirectory()
  {
    return this.directory;
  }

  public int getMaxFileCount()
  {
    try
    {
      int i = this.maxFileCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public long getMaxSize()
  {
    try
    {
      long l = this.maxSize;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean isClosed()
  {
    try
    {
      Writer localWriter = this.journalWriter;
      if (localWriter == null)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  public boolean remove(String paramString)
    throws IOException
  {
    while (true)
    {
      Entry localEntry;
      int i;
      try
      {
        checkNotClosed();
        validateKey(paramString);
        localEntry = (Entry)this.lruEntries.get(paramString);
        if (localEntry != null)
        {
          Editor localEditor = localEntry.currentEditor;
          if (localEditor == null);
        }
        else
        {
          bool = false;
          return bool;
        }
        i = 0;
        if (i >= this.valueCount)
          break label156;
        File localFile = localEntry.getCleanFile(i);
        if ((localFile.exists()) && (!localFile.delete()))
          throw new IOException("failed to delete " + localFile);
      }
      finally
      {
      }
      this.size -= localEntry.lengths[i];
      this.fileCount = (-1 + this.fileCount);
      localEntry.lengths[i] = 0L;
      i++;
      continue;
      label156: this.redundantOpCount = (1 + this.redundantOpCount);
      this.journalWriter.append("REMOVE " + paramString + '\n');
      this.lruEntries.remove(paramString);
      if (journalRebuildRequired())
        this.executorService.submit(this.cleanupCallable);
      boolean bool = true;
    }
  }

  public void setMaxSize(long paramLong)
  {
    try
    {
      this.maxSize = paramLong;
      this.executorService.submit(this.cleanupCallable);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public long size()
  {
    try
    {
      long l = this.size;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final class Editor
  {
    private boolean committed;
    private final DiskLruCache.Entry entry;
    private boolean hasErrors;
    private final boolean[] written;

    private Editor(DiskLruCache.Entry arg2)
    {
      DiskLruCache.Entry localEntry;
      this.entry = localEntry;
      if (DiskLruCache.Entry.access$700(localEntry));
      for (boolean[] arrayOfBoolean = null; ; arrayOfBoolean = new boolean[DiskLruCache.this.valueCount])
      {
        this.written = arrayOfBoolean;
        return;
      }
    }

    public void abort()
      throws IOException
    {
      DiskLruCache.this.completeEdit(this, false);
    }

    public void abortUnlessCommitted()
    {
      if (!this.committed);
      try
      {
        abort();
        return;
      }
      catch (IOException localIOException)
      {
      }
    }

    public void commit()
      throws IOException
    {
      if (this.hasErrors)
      {
        DiskLruCache.this.completeEdit(this, false);
        DiskLruCache.this.remove(DiskLruCache.Entry.access$1200(this.entry));
      }
      while (true)
      {
        this.committed = true;
        return;
        DiskLruCache.this.completeEdit(this, true);
      }
    }

    public String getString(int paramInt)
      throws IOException
    {
      InputStream localInputStream = newInputStream(paramInt);
      if (localInputStream != null)
        return DiskLruCache.inputStreamToString(localInputStream);
      return null;
    }

    public InputStream newInputStream(int paramInt)
      throws IOException
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.Entry.access$800(this.entry) != this)
          throw new IllegalStateException();
      }
      if (!DiskLruCache.Entry.access$700(this.entry))
        return null;
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(this.entry.getCleanFile(paramInt));
        return localFileInputStream;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
      }
      return null;
    }

    public OutputStream newOutputStream(int paramInt)
      throws IOException
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.Entry.access$800(this.entry) != this)
          throw new IllegalStateException();
      }
      if (!DiskLruCache.Entry.access$700(this.entry))
        this.written[paramInt] = true;
      File localFile = this.entry.getDirtyFile(paramInt);
      try
      {
        localFileOutputStream = new FileOutputStream(localFile);
        FaultHidingOutputStream localFaultHidingOutputStream = new FaultHidingOutputStream(localFileOutputStream, null);
        return localFaultHidingOutputStream;
      }
      catch (FileNotFoundException localFileNotFoundException1)
      {
        while (true)
        {
          FileOutputStream localFileOutputStream;
          DiskLruCache.this.directory.mkdirs();
          try
          {
            localFileOutputStream = new FileOutputStream(localFile);
          }
          catch (FileNotFoundException localFileNotFoundException2)
          {
            OutputStream localOutputStream = DiskLruCache.NULL_OUTPUT_STREAM;
            return localOutputStream;
          }
        }
      }
    }

    // ERROR //
    public void set(int paramInt, String paramString)
      throws IOException
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: new 127	java/io/OutputStreamWriter
      //   5: dup
      //   6: aload_0
      //   7: iload_1
      //   8: invokevirtual 129	com/nostra13/universalimageloader/cache/disc/impl/ext/DiskLruCache$Editor:newOutputStream	(I)Ljava/io/OutputStream;
      //   11: getstatic 135	com/nostra13/universalimageloader/cache/disc/impl/ext/Util:UTF_8	Ljava/nio/charset/Charset;
      //   14: invokespecial 138	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      //   17: astore 4
      //   19: aload 4
      //   21: aload_2
      //   22: invokevirtual 144	java/io/Writer:write	(Ljava/lang/String;)V
      //   25: aload 4
      //   27: invokestatic 148	com/nostra13/universalimageloader/cache/disc/impl/ext/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   30: return
      //   31: astore 5
      //   33: aload_3
      //   34: invokestatic 148	com/nostra13/universalimageloader/cache/disc/impl/ext/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   37: aload 5
      //   39: athrow
      //   40: astore 5
      //   42: aload 4
      //   44: astore_3
      //   45: goto -12 -> 33
      //
      // Exception table:
      //   from	to	target	type
      //   2	19	31	finally
      //   19	25	40	finally
    }

    private class FaultHidingOutputStream extends FilterOutputStream
    {
      private FaultHidingOutputStream(OutputStream arg2)
      {
        super();
      }

      public void close()
      {
        try
        {
          this.out.close();
          return;
        }
        catch (IOException localIOException)
        {
          DiskLruCache.Editor.access$2402(DiskLruCache.Editor.this, true);
        }
      }

      public void flush()
      {
        try
        {
          this.out.flush();
          return;
        }
        catch (IOException localIOException)
        {
          DiskLruCache.Editor.access$2402(DiskLruCache.Editor.this, true);
        }
      }

      public void write(int paramInt)
      {
        try
        {
          this.out.write(paramInt);
          return;
        }
        catch (IOException localIOException)
        {
          DiskLruCache.Editor.access$2402(DiskLruCache.Editor.this, true);
        }
      }

      public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      {
        try
        {
          this.out.write(paramArrayOfByte, paramInt1, paramInt2);
          return;
        }
        catch (IOException localIOException)
        {
          DiskLruCache.Editor.access$2402(DiskLruCache.Editor.this, true);
        }
      }
    }
  }

  private final class Entry
  {
    private DiskLruCache.Editor currentEditor;
    private final String key;
    private final long[] lengths;
    private boolean readable;
    private long sequenceNumber;

    private Entry(String arg2)
    {
      Object localObject;
      this.key = localObject;
      this.lengths = new long[DiskLruCache.this.valueCount];
    }

    private IOException invalidLengths(String[] paramArrayOfString)
      throws IOException
    {
      throw new IOException("unexpected journal line: " + Arrays.toString(paramArrayOfString));
    }

    private void setLengths(String[] paramArrayOfString)
      throws IOException
    {
      if (paramArrayOfString.length != DiskLruCache.this.valueCount)
        throw invalidLengths(paramArrayOfString);
      int i = 0;
      try
      {
        while (i < paramArrayOfString.length)
        {
          this.lengths[i] = Long.parseLong(paramArrayOfString[i]);
          i++;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw invalidLengths(paramArrayOfString);
      }
    }

    public File getCleanFile(int paramInt)
    {
      return new File(DiskLruCache.this.directory, this.key + "." + paramInt);
    }

    public File getDirtyFile(int paramInt)
    {
      return new File(DiskLruCache.this.directory, this.key + "." + paramInt + ".tmp");
    }

    public String getLengths()
      throws IOException
    {
      StringBuilder localStringBuilder = new StringBuilder();
      for (long l : this.lengths)
        localStringBuilder.append(' ').append(l);
      return localStringBuilder.toString();
    }
  }

  public final class Snapshot
    implements Closeable
  {
    private File[] files;
    private final InputStream[] ins;
    private final String key;
    private final long[] lengths;
    private final long sequenceNumber;

    private Snapshot(String paramLong, long arg3, File[] paramArrayOfInputStream, InputStream[] paramArrayOfLong, long[] arg7)
    {
      this.key = paramLong;
      this.sequenceNumber = ???;
      this.files = paramArrayOfInputStream;
      this.ins = paramArrayOfLong;
      Object localObject;
      this.lengths = localObject;
    }

    public void close()
    {
      InputStream[] arrayOfInputStream = this.ins;
      int i = arrayOfInputStream.length;
      for (int j = 0; j < i; j++)
        Util.closeQuietly(arrayOfInputStream[j]);
    }

    public DiskLruCache.Editor edit()
      throws IOException
    {
      return DiskLruCache.this.edit(this.key, this.sequenceNumber);
    }

    public File getFile(int paramInt)
    {
      return this.files[paramInt];
    }

    public InputStream getInputStream(int paramInt)
    {
      return this.ins[paramInt];
    }

    public long getLength(int paramInt)
    {
      return this.lengths[paramInt];
    }

    public String getString(int paramInt)
      throws IOException
    {
      return DiskLruCache.inputStreamToString(getInputStream(paramInt));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache
 * JD-Core Version:    0.6.2
 */