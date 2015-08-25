package com.jcraft.jsch;

public abstract interface ConfigRepository
{
  public static final Config defaultConfig = new Config()
  {
    public String getHostname()
    {
      return null;
    }

    public int getPort()
    {
      return -1;
    }

    public String getUser()
    {
      return null;
    }

    public String getValue(String paramAnonymousString)
    {
      return null;
    }

    public String[] getValues(String paramAnonymousString)
    {
      return null;
    }
  };
  public static final ConfigRepository nullConfig = new ConfigRepository()
  {
    public ConfigRepository.Config getConfig(String paramAnonymousString)
    {
      return defaultConfig;
    }
  };

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