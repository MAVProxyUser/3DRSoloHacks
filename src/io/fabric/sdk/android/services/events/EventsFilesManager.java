package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EventsFilesManager<T>
{
  public static final int MAX_FILES_IN_BATCH = 1;
  public static final int MAX_FILES_TO_KEEP = 100;
  public static final String ROLL_OVER_FILE_NAME_SEPARATOR = "_";
  protected final Context context;
  protected final CurrentTimeProvider currentTimeProvider;
  protected final EventsStorage eventStorage;
  protected volatile long lastRollOverTime;
  protected final int maxFilesToKeep;
  protected final List<EventsStorageListener> rollOverListeners = new CopyOnWriteArrayList();
  protected final EventTransform<T> transform;

  EventsFilesManager(Context paramContext, EventTransform<T> paramEventTransform, CurrentTimeProvider paramCurrentTimeProvider, EventsStorage paramEventsStorage)
    throws IOException
  {
    this(paramContext, paramEventTransform, paramCurrentTimeProvider, paramEventsStorage, 100);
  }

  public EventsFilesManager(Context paramContext, EventTransform<T> paramEventTransform, CurrentTimeProvider paramCurrentTimeProvider, EventsStorage paramEventsStorage, int paramInt)
    throws IOException
  {
    this.context = paramContext.getApplicationContext();
    this.transform = paramEventTransform;
    this.eventStorage = paramEventsStorage;
    this.currentTimeProvider = paramCurrentTimeProvider;
    this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
    this.maxFilesToKeep = paramInt;
  }

  private void rollFileOverIfNeeded(int paramInt)
    throws IOException
  {
    if (!this.eventStorage.canWorkingFileStore(paramInt, getMaxByteSizePerFile()))
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(this.eventStorage.getWorkingFileUsedSizeInBytes());
      arrayOfObject[1] = Integer.valueOf(paramInt);
      arrayOfObject[2] = Integer.valueOf(getMaxByteSizePerFile());
      String str = String.format(localLocale, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", arrayOfObject);
      CommonUtils.logControlled(this.context, 4, "Fabric", str);
      rollFileOver();
    }
  }

  private void triggerRollOverOnListeners(String paramString)
  {
    Iterator localIterator = this.rollOverListeners.iterator();
    while (localIterator.hasNext())
    {
      EventsStorageListener localEventsStorageListener = (EventsStorageListener)localIterator.next();
      try
      {
        localEventsStorageListener.onRollOver(paramString);
      }
      catch (Exception localException)
      {
        CommonUtils.logControlledError(this.context, "One of the roll over listeners threw an exception", localException);
      }
    }
  }

  public void deleteAllEventsFiles()
  {
    this.eventStorage.deleteFilesInRollOverDirectory(this.eventStorage.getAllFilesInRollOverDirectory());
    this.eventStorage.deleteWorkingFile();
  }

  public void deleteOldestInRollOverIfOverMax()
  {
    List localList = this.eventStorage.getAllFilesInRollOverDirectory();
    if (localList.size() <= this.maxFilesToKeep)
      return;
    int i = localList.size() - this.maxFilesToKeep;
    Context localContext = this.context;
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(localList.size());
    arrayOfObject[1] = Integer.valueOf(this.maxFilesToKeep);
    arrayOfObject[2] = Integer.valueOf(i);
    CommonUtils.logControlled(localContext, String.format(localLocale, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", arrayOfObject));
    TreeSet localTreeSet = new TreeSet(new Comparator()
    {
      public int compare(EventsFilesManager.FileWithTimestamp paramAnonymousFileWithTimestamp1, EventsFilesManager.FileWithTimestamp paramAnonymousFileWithTimestamp2)
      {
        return (int)(paramAnonymousFileWithTimestamp1.timestamp - paramAnonymousFileWithTimestamp2.timestamp);
      }
    });
    Iterator localIterator1 = localList.iterator();
    while (localIterator1.hasNext())
    {
      File localFile = (File)localIterator1.next();
      localTreeSet.add(new FileWithTimestamp(localFile, parseCreationTimestampFromFileName(localFile.getName())));
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator2 = localTreeSet.iterator();
    do
    {
      if (!localIterator2.hasNext())
        break;
      localArrayList.add(((FileWithTimestamp)localIterator2.next()).file);
    }
    while (localArrayList.size() != i);
    this.eventStorage.deleteFilesInRollOverDirectory(localArrayList);
  }

  public void deleteSentFiles(List<File> paramList)
  {
    this.eventStorage.deleteFilesInRollOverDirectory(paramList);
  }

  protected abstract String generateUniqueRollOverFileName();

  public List<File> getBatchOfFilesToSend()
  {
    return this.eventStorage.getBatchOfFilesToSend(1);
  }

  public long getLastRollOverTime()
  {
    return this.lastRollOverTime;
  }

  protected int getMaxByteSizePerFile()
  {
    return 8000;
  }

  public long parseCreationTimestampFromFileName(String paramString)
  {
    String[] arrayOfString = paramString.split("_");
    if (arrayOfString.length != 3)
      return 0L;
    try
    {
      long l = Long.valueOf(arrayOfString[2]).longValue();
      return l;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return 0L;
  }

  public void registerRollOverListener(EventsStorageListener paramEventsStorageListener)
  {
    if (paramEventsStorageListener != null)
      this.rollOverListeners.add(paramEventsStorageListener);
  }

  public boolean rollFileOver()
    throws IOException
  {
    boolean bool1 = this.eventStorage.isWorkingFileEmpty();
    boolean bool2 = false;
    String str = null;
    if (!bool1)
    {
      str = generateUniqueRollOverFileName();
      this.eventStorage.rollOver(str);
      CommonUtils.logControlled(this.context, 4, "Fabric", String.format(Locale.US, "generated new file %s", new Object[] { str }));
      this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
      bool2 = true;
    }
    triggerRollOverOnListeners(str);
    return bool2;
  }

  public void writeEvent(T paramT)
    throws IOException
  {
    byte[] arrayOfByte = this.transform.toBytes(paramT);
    rollFileOverIfNeeded(arrayOfByte.length);
    this.eventStorage.add(arrayOfByte);
  }

  static class FileWithTimestamp
  {
    final File file;
    final long timestamp;

    public FileWithTimestamp(File paramFile, long paramLong)
    {
      this.file = paramFile;
      this.timestamp = paramLong;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EventsFilesManager
 * JD-Core Version:    0.6.2
 */