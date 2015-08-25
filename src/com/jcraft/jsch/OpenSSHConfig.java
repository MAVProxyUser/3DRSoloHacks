package com.jcraft.jsch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Vector;

public class OpenSSHConfig
  implements ConfigRepository
{
  private static final Hashtable keymap = new Hashtable();
  private final Hashtable config = new Hashtable();
  private final Vector hosts = new Vector();

  static
  {
    keymap.put("kex", "KexAlgorithms");
    keymap.put("server_host_key", "HostKeyAlgorithms");
    keymap.put("cipher.c2s", "Ciphers");
    keymap.put("cipher.s2c", "Ciphers");
    keymap.put("mac.c2s", "Macs");
    keymap.put("mac.s2c", "Macs");
    keymap.put("compression.s2c", "Compression");
    keymap.put("compression.c2s", "Compression");
    keymap.put("compression_level", "CompressionLevel");
    keymap.put("MaxAuthTries", "NumberOfPasswordPrompts");
  }

  OpenSSHConfig(Reader paramReader)
    throws IOException
  {
    _parse(paramReader);
  }

  private void _parse(Reader paramReader)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(paramReader);
    String str1 = "";
    Vector localVector = new Vector();
    while (true)
    {
      String str2 = localBufferedReader.readLine();
      if (str2 == null)
        break;
      String str3 = str2.trim();
      if ((str3.length() != 0) && (!str3.startsWith("#")))
      {
        String[] arrayOfString = str3.split("[= \t]", 2);
        for (int i = 0; i < arrayOfString.length; i++)
          arrayOfString[i] = arrayOfString[i].trim();
        if (arrayOfString.length > 1)
          if (arrayOfString[0].equals("Host"))
          {
            this.config.put(str1, localVector);
            this.hosts.addElement(str1);
            str1 = arrayOfString[1];
            localVector = new Vector();
          }
          else
          {
            localVector.addElement(arrayOfString);
          }
      }
    }
    this.config.put(str1, localVector);
    this.hosts.addElement(str1);
  }

  public static OpenSSHConfig parse(String paramString)
    throws IOException
  {
    StringReader localStringReader = new StringReader(paramString);
    try
    {
      OpenSSHConfig localOpenSSHConfig = new OpenSSHConfig(localStringReader);
      return localOpenSSHConfig;
    }
    finally
    {
      localStringReader.close();
    }
  }

  public static OpenSSHConfig parseFile(String paramString)
    throws IOException
  {
    FileReader localFileReader = new FileReader(Util.checkTilde(paramString));
    try
    {
      OpenSSHConfig localOpenSSHConfig = new OpenSSHConfig(localFileReader);
      return localOpenSSHConfig;
    }
    finally
    {
      localFileReader.close();
    }
  }

  public ConfigRepository.Config getConfig(String paramString)
  {
    return new MyConfig(paramString);
  }

  class MyConfig
    implements ConfigRepository.Config
  {
    private Vector _configs = new Vector();
    private String host;

    MyConfig(String arg2)
    {
      String str1;
      this.host = str1;
      this._configs.addElement(OpenSSHConfig.this.config.get(""));
      byte[] arrayOfByte = Util.str2byte(str1);
      if (OpenSSHConfig.this.hosts.size() > 1)
        for (int i = 1; i < OpenSSHConfig.this.hosts.size(); i++)
        {
          String[] arrayOfString = ((String)OpenSSHConfig.this.hosts.elementAt(i)).split("[ \t]");
          int j = 0;
          if (j < arrayOfString.length)
          {
            String str2 = arrayOfString[j].trim();
            boolean bool = str2.startsWith("!");
            int k = 0;
            if (bool)
            {
              k = 1;
              str2 = str2.substring(1).trim();
            }
            if (Util.glob(Util.str2byte(str2), arrayOfByte))
              if (k == 0)
                this._configs.addElement(OpenSSHConfig.this.config.get((String)OpenSSHConfig.this.hosts.elementAt(i)));
            while (true)
            {
              j++;
              break;
              if (k != 0)
                this._configs.addElement(OpenSSHConfig.this.config.get((String)OpenSSHConfig.this.hosts.elementAt(i)));
            }
          }
        }
    }

    private String find(String paramString)
    {
      if (OpenSSHConfig.keymap.get(paramString) != null)
        paramString = (String)OpenSSHConfig.keymap.get(paramString);
      String str = null;
      for (int i = 0; ; i++)
      {
        Vector localVector;
        if (i < this._configs.size())
          localVector = (Vector)this._configs.elementAt(i);
        for (int j = 0; ; j++)
          if (j < localVector.size())
          {
            String[] arrayOfString = (String[])localVector.elementAt(j);
            if (arrayOfString[0].equals(paramString))
              str = arrayOfString[1];
          }
          else
          {
            if (str == null)
              break;
            return str;
          }
      }
    }

    private String[] multiFind(String paramString)
    {
      Vector localVector1 = new Vector();
      for (int i = 0; i < this._configs.size(); i++)
      {
        Vector localVector2 = (Vector)this._configs.elementAt(i);
        for (int j = 0; j < localVector2.size(); j++)
        {
          String[] arrayOfString2 = (String[])localVector2.elementAt(j);
          if (arrayOfString2[0].equals(paramString))
          {
            String str = arrayOfString2[1];
            if (str != null)
            {
              localVector1.remove(str);
              localVector1.addElement(str);
            }
          }
        }
      }
      String[] arrayOfString1 = new String[localVector1.size()];
      localVector1.toArray(arrayOfString1);
      return arrayOfString1;
    }

    public String getHostname()
    {
      return find("Hostname");
    }

    public int getPort()
    {
      String str = find("Port");
      try
      {
        int i = Integer.parseInt(str);
        return i;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
      return -1;
    }

    public String getUser()
    {
      return find("User");
    }

    public String getValue(String paramString)
    {
      if ((paramString.equals("compression.s2c")) || (paramString.equals("compression.c2s")))
      {
        String str = find(paramString);
        if ((str == null) || (str.equals("no")))
          return "none,zlib@openssh.com,zlib";
        return "zlib@openssh.com,zlib,none";
      }
      return find(paramString);
    }

    public String[] getValues(String paramString)
    {
      return multiFind(paramString);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.OpenSSHConfig
 * JD-Core Version:    0.6.2
 */