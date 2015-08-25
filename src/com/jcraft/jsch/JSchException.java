package com.jcraft.jsch;

public class JSchException extends Exception
{
  private Throwable cause = null;

  public JSchException()
  {
  }

  public JSchException(String paramString)
  {
    super(paramString);
  }

  public JSchException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
  }

  public Throwable getCause()
  {
    return this.cause;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.JSchException
 * JD-Core Version:    0.6.2
 */