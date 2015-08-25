package com.jcraft.jsch;

public abstract interface HostKeyRepository
{
  public static final int CHANGED = 2;
  public static final int NOT_INCLUDED = 1;
  public static final int OK;

  public abstract void add(HostKey paramHostKey, UserInfo paramUserInfo);

  public abstract int check(String paramString, byte[] paramArrayOfByte);

  public abstract HostKey[] getHostKey();

  public abstract HostKey[] getHostKey(String paramString1, String paramString2);

  public abstract String getKnownHostsRepositoryID();

  public abstract void remove(String paramString1, String paramString2);

  public abstract void remove(String paramString1, String paramString2, byte[] paramArrayOfByte);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.HostKeyRepository
 * JD-Core Version:    0.6.2
 */