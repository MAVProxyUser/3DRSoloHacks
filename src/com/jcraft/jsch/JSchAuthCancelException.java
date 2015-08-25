package com.jcraft.jsch;

class JSchAuthCancelException extends JSchException
{
  String method;

  JSchAuthCancelException()
  {
  }

  JSchAuthCancelException(String paramString)
  {
    super(paramString);
    this.method = paramString;
  }

  public String getMethod()
  {
    return this.method;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.JSchAuthCancelException
 * JD-Core Version:    0.6.2
 */