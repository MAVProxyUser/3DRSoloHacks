package com.jcraft.jsch;

public abstract interface ConfigRepository
{
  public static final Config defaultConfig = new ConfigRepository.1();
  public static final ConfigRepository nullConfig = new ConfigRepository.2();

  public abstract Config getConfig(String paramString);

  public static abstract interface Config
  {
    public abstract String getHostname();

    public abstract int getPort();

    public abstract String getUser();

    public abstract String getValue(String paramString);

    public abstract String[] getValues(String paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ConfigRepository
 * JD-Core Version:    0.6.2
 */