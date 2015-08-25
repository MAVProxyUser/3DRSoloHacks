package com.crashlytics.android;

public class CrashlyticsMissingDependencyException extends RuntimeException
{
  private static final long serialVersionUID = -1151536370019872859L;

  CrashlyticsMissingDependencyException(String paramString)
  {
    super(buildExceptionMessage(paramString));
  }

  private static String buildExceptionMessage(String paramString)
  {
    return "\n" + paramString + "\n";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.CrashlyticsMissingDependencyException
 * JD-Core Version:    0.6.2
 */