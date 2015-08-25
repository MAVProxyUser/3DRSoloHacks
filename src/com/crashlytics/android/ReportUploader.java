package com.crashlytics.android;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ReportUploader
{
  private static final String CLS_FILE_EXT = ".cls";
  static final Map<String, String> HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
  private static final short[] RETRY_INTERVALS = { 10, 20, 30, 60, 120, 300 };
  private static final FilenameFilter crashFileFilter = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return (paramAnonymousString.endsWith(".cls")) && (!paramAnonymousString.contains("Session"));
    }
  };
  private final CreateReportSpiCall createReportCall;
  private final Object fileAccessLock = new Object();
  private Thread uploadThread;

  public ReportUploader(CreateReportSpiCall paramCreateReportSpiCall)
  {
    if (paramCreateReportSpiCall == null)
      throw new IllegalArgumentException("createReportCall must not be null.");
    this.createReportCall = paramCreateReportSpiCall;
  }

  List<Report> findReports()
  {
    Fabric.getLogger().d("Fabric", "Checking for crash reports...");
    LinkedList localLinkedList;
    synchronized (this.fileAccessLock)
    {
      File[] arrayOfFile = Crashlytics.getInstance().getSdkDirectory().listFiles(crashFileFilter);
      localLinkedList = new LinkedList();
      int i = arrayOfFile.length;
      int j = 0;
      if (j < i)
      {
        File localFile = arrayOfFile[j];
        Fabric.getLogger().d("Fabric", "Found crash report " + localFile.getPath());
        localLinkedList.add(new SessionReport(localFile));
        j++;
      }
    }
    if (localLinkedList.isEmpty())
      Fabric.getLogger().d("Fabric", "No reports found.");
    return localLinkedList;
  }

  boolean forceUpload(Report paramReport)
  {
    synchronized (this.fileAccessLock)
    {
      try
      {
        CreateReportRequest localCreateReportRequest = new CreateReportRequest(ApiKey.getApiKey(Crashlytics.getInstance().getContext(), Fabric.isDebuggable()), paramReport);
        boolean bool2 = this.createReportCall.invoke(localCreateReportRequest);
        Logger localLogger = Fabric.getLogger();
        StringBuilder localStringBuilder = new StringBuilder().append("Crashlytics report upload ");
        if (bool2);
        for (String str = "complete: "; ; str = "FAILED: ")
        {
          localLogger.i("Fabric", str + paramReport.getFileName());
          bool1 = false;
          if (bool2)
          {
            paramReport.remove();
            bool1 = true;
          }
          return bool1;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          Fabric.getLogger().e("Fabric", "Error occurred sending report " + paramReport, localException);
          boolean bool1 = false;
        }
      }
    }
  }

  boolean isUploading()
  {
    return this.uploadThread != null;
  }

  public void uploadReports()
  {
    uploadReports(0.0F);
  }

  public void uploadReports(float paramFloat)
  {
    try
    {
      if (this.uploadThread == null)
      {
        this.uploadThread = new Thread(new ReportUploader.Worker(this, paramFloat), "Crashlytics Report Uploader");
        this.uploadThread.start();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.ReportUploader
 * JD-Core Version:    0.6.2
 */