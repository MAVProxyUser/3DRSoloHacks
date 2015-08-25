package com.o3dr.solo.android.util.maps.mapbox.offline;

public abstract interface MapDownloaderListener
{
  public abstract void completionOfOfflineDatabaseMap();

  public abstract void httpStatusError(Throwable paramThrowable);

  public abstract void initialCountOfFiles(int paramInt);

  public abstract void networkConnectivityError(Throwable paramThrowable);

  public abstract void progressUpdate(int paramInt1, int paramInt2);

  public abstract void sqlLiteError(Throwable paramThrowable);

  public abstract void stateChanged(MapDownloader.MBXOfflineMapDownloaderState paramMBXOfflineMapDownloaderState);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloaderListener
 * JD-Core Version:    0.6.2
 */