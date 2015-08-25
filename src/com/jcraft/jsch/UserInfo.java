package com.jcraft.jsch;

public abstract interface UserInfo
{
  public abstract String getPassphrase();

  public abstract String getPassword();

  public abstract boolean promptPassphrase(String paramString);

  public abstract boolean promptPassword(String paramString);

  public abstract boolean promptYesNo(String paramString);

  public abstract void showMessage(String paramString);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserInfo
 * JD-Core Version:    0.6.2
 */