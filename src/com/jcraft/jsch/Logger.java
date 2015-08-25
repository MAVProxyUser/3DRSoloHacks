package com.jcraft.jsch;

public abstract interface Logger
{
  public static final int DEBUG = 0;
  public static final int ERROR = 3;
  public static final int FATAL = 4;
  public static final int INFO = 1;
  public static final int WARN = 2;

  public abstract boolean isEnabled(int paramInt);

  public abstract void log(int paramInt, String paramString);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Logger
 * JD-Core Version:    0.6.2
 */