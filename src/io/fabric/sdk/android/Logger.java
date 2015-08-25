package io.fabric.sdk.android;

public abstract interface Logger
{
  public abstract void d(String paramString1, String paramString2);

  public abstract void d(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void e(String paramString1, String paramString2);

  public abstract void e(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract int getLogLevel();

  public abstract void i(String paramString1, String paramString2);

  public abstract void i(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract boolean isLoggable(String paramString, int paramInt);

  public abstract void log(int paramInt, String paramString1, String paramString2);

  public abstract void log(int paramInt, String paramString1, String paramString2, boolean paramBoolean);

  public abstract void setLogLevel(int paramInt);

  public abstract void v(String paramString1, String paramString2);

  public abstract void v(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void w(String paramString1, String paramString2);

  public abstract void w(String paramString1, String paramString2, Throwable paramThrowable);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.Logger
 * JD-Core Version:    0.6.2
 */