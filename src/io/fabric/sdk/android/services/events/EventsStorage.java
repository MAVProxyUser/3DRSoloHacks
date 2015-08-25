package io.fabric.sdk.android.services.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract interface EventsStorage
{
  public abstract void add(byte[] paramArrayOfByte)
    throws IOException;

  public abstract boolean canWorkingFileStore(int paramInt1, int paramInt2);

  public abstract void deleteFilesInRollOverDirectory(List<File> paramList);

  public abstract void deleteWorkingFile();

  public abstract List<File> getAllFilesInRollOverDirectory();

  public abstract List<File> getBatchOfFilesToSend(int paramInt);

  public abstract File getRollOverDirectory();

  public abstract File getWorkingDirectory();

  public abstract int getWorkingFileUsedSizeInBytes();

  public abstract boolean isWorkingFileEmpty();

  public abstract void rollOver(String paramString)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EventsStorage
 * JD-Core Version:    0.6.2
 */