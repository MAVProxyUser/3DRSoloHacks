package com.crashlytics.android;

class CreateReportRequest
{
  public final String apiKey;
  public final Report report;

  public CreateReportRequest(String paramString, Report paramReport)
  {
    this.apiKey = paramString;
    this.report = paramReport;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.CreateReportRequest
 * JD-Core Version:    0.6.2
 */