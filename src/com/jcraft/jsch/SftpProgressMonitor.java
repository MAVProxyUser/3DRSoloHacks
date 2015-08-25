package com.jcraft.jsch;

public abstract interface SftpProgressMonitor
{
  public static final int GET = 1;
  public static final int PUT = 0;
  public static final long UNKNOWN_SIZE = -1L;

  public abstract boolean count(long paramLong);

  public abstract void end();

  public abstract void init(int paramInt, String paramString1, String paramString2, long paramLong);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.SftpProgressMonitor
 * JD-Core Version:    0.6.2
 */