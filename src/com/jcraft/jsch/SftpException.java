package com.jcraft.jsch;

public class SftpException extends Exception
{
  private Throwable cause = null;
  public int id;

  public SftpException(int paramInt, String paramString)
  {
    super(paramString);
    this.id = paramInt;
  }

  public SftpException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.id = paramInt;
    this.cause = paramThrowable;
  }

  public Throwable getCause()
  {
    return this.cause;
  }

  public String toString()
  {
    return this.id + ": " + getMessage();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.SftpException
 * JD-Core Version:    0.6.2
 */