package com.jcraft.jsch;

class JSchPartialAuthException extends JSchException
{
  String methods;

  public JSchPartialAuthException()
  {
  }

  public JSchPartialAuthException(String paramString)
  {
    super(paramString);
    this.methods = paramString;
  }

  public String getMethods()
  {
    return this.methods;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.JSchPartialAuthException
 * JD-Core Version:    0.6.2
 */