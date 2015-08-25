package com.crashlytics.android;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

final class Utils
{
  public static void capFileCount(File paramFile, FilenameFilter paramFilenameFilter, int paramInt, Comparator<File> paramComparator)
  {
    File[] arrayOfFile = paramFile.listFiles(paramFilenameFilter);
    int i;
    int j;
    if ((arrayOfFile != null) && (arrayOfFile.length > paramInt))
    {
      Arrays.sort(arrayOfFile, paramComparator);
      i = arrayOfFile.length;
      j = arrayOfFile.length;
    }
    for (int k = 0; ; k++)
    {
      File localFile;
      if (k < j)
      {
        localFile = arrayOfFile[k];
        if (i > paramInt);
      }
      else
      {
        return;
      }
      localFile.delete();
      i--;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.Utils
 * JD-Core Version:    0.6.2
 */