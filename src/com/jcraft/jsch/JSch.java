package com.jcraft.jsch;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class JSch
{
  private static final Logger DEVNULL = new JSch.1();
  public static final String VERSION = "0.1.51";
  static Hashtable config = new Hashtable();
  static Logger logger = DEVNULL;
  private ConfigRepository configRepository = null;
  private IdentityRepository defaultIdentityRepository = new LocalIdentityRepository(this);
  private IdentityRepository identityRepository = this.defaultIdentityRepository;
  private HostKeyRepository known_hosts = null;
  private Vector sessionPool = new Vector();

  static
  {
    config.put("kex", "diffie-hellman-group1-sha1,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha1");
    config.put("server_host_key", "ssh-rsa,ssh-dss");
    config.put("cipher.s2c", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-cbc,aes256-cbc");
    config.put("cipher.c2s", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-cbc,aes256-cbc");
    config.put("mac.s2c", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
    config.put("mac.c2s", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
    config.put("compression.s2c", "none");
    config.put("compression.c2s", "none");
    config.put("lang.s2c", "");
    config.put("lang.c2s", "");
    config.put("compression_level", "6");
    config.put("diffie-hellman-group-exchange-sha1", "com.jcraft.jsch.DHGEX");
    config.put("diffie-hellman-group1-sha1", "com.jcraft.jsch.DHG1");
    config.put("diffie-hellman-group14-sha1", "com.jcraft.jsch.DHG14");
    config.put("diffie-hellman-group-exchange-sha256", "com.jcraft.jsch.DHGEX256");
    config.put("dh", "com.jcraft.jsch.jce.DH");
    config.put("3des-cbc", "com.jcraft.jsch.jce.TripleDESCBC");
    config.put("blowfish-cbc", "com.jcraft.jsch.jce.BlowfishCBC");
    config.put("hmac-sha1", "com.jcraft.jsch.jce.HMACSHA1");
    config.put("hmac-sha1-96", "com.jcraft.jsch.jce.HMACSHA196");
    config.put("hmac-sha2-256", "com.jcraft.jsch.jce.HMACSHA256");
    config.put("hmac-md5", "com.jcraft.jsch.jce.HMACMD5");
    config.put("hmac-md5-96", "com.jcraft.jsch.jce.HMACMD596");
    config.put("sha-1", "com.jcraft.jsch.jce.SHA1");
    config.put("sha-256", "com.jcraft.jsch.jce.SHA256");
    config.put("md5", "com.jcraft.jsch.jce.MD5");
    config.put("signature.dss", "com.jcraft.jsch.jce.SignatureDSA");
    config.put("signature.rsa", "com.jcraft.jsch.jce.SignatureRSA");
    config.put("keypairgen.dsa", "com.jcraft.jsch.jce.KeyPairGenDSA");
    config.put("keypairgen.rsa", "com.jcraft.jsch.jce.KeyPairGenRSA");
    config.put("random", "com.jcraft.jsch.jce.Random");
    config.put("none", "com.jcraft.jsch.CipherNone");
    config.put("aes128-cbc", "com.jcraft.jsch.jce.AES128CBC");
    config.put("aes192-cbc", "com.jcraft.jsch.jce.AES192CBC");
    config.put("aes256-cbc", "com.jcraft.jsch.jce.AES256CBC");
    config.put("aes128-ctr", "com.jcraft.jsch.jce.AES128CTR");
    config.put("aes192-ctr", "com.jcraft.jsch.jce.AES192CTR");
    config.put("aes256-ctr", "com.jcraft.jsch.jce.AES256CTR");
    config.put("3des-ctr", "com.jcraft.jsch.jce.TripleDESCTR");
    config.put("arcfour", "com.jcraft.jsch.jce.ARCFOUR");
    config.put("arcfour128", "com.jcraft.jsch.jce.ARCFOUR128");
    config.put("arcfour256", "com.jcraft.jsch.jce.ARCFOUR256");
    config.put("userauth.none", "com.jcraft.jsch.UserAuthNone");
    config.put("userauth.password", "com.jcraft.jsch.UserAuthPassword");
    config.put("userauth.keyboard-interactive", "com.jcraft.jsch.UserAuthKeyboardInteractive");
    config.put("userauth.publickey", "com.jcraft.jsch.UserAuthPublicKey");
    config.put("userauth.gssapi-with-mic", "com.jcraft.jsch.UserAuthGSSAPIWithMIC");
    config.put("gssapi-with-mic.krb5", "com.jcraft.jsch.jgss.GSSContextKrb5");
    config.put("zlib", "com.jcraft.jsch.jcraft.Compression");
    config.put("zlib@openssh.com", "com.jcraft.jsch.jcraft.Compression");
    config.put("pbkdf", "com.jcraft.jsch.jce.PBKDF");
    config.put("StrictHostKeyChecking", "ask");
    config.put("HashKnownHosts", "no");
    config.put("PreferredAuthentications", "gssapi-with-mic,publickey,keyboard-interactive,password");
    config.put("CheckCiphers", "aes256-ctr,aes192-ctr,aes128-ctr,aes256-cbc,aes192-cbc,aes128-cbc,3des-ctr,arcfour,arcfour128,arcfour256");
    config.put("CheckKexes", "diffie-hellman-group14-sha1");
    config.put("MaxAuthTries", "6");
    config.put("ClearAllForwardings", "no");
  }

  public static String getConfig(String paramString)
  {
    synchronized (config)
    {
      String str = (String)config.get(paramString);
      return str;
    }
  }

  static Logger getLogger()
  {
    return logger;
  }

  public static void setConfig(String paramString1, String paramString2)
  {
    config.put(paramString1, paramString2);
  }

  public static void setConfig(Hashtable paramHashtable)
  {
    synchronized (config)
    {
      Enumeration localEnumeration = paramHashtable.keys();
      if (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        config.put(str, (String)paramHashtable.get(str));
      }
    }
  }

  public static void setLogger(Logger paramLogger)
  {
    if (paramLogger == null)
      paramLogger = DEVNULL;
    logger = paramLogger;
  }

  public void addIdentity(Identity paramIdentity, byte[] paramArrayOfByte)
    throws JSchException
  {
    if (paramArrayOfByte != null);
    try
    {
      byte[] arrayOfByte = new byte[paramArrayOfByte.length];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
      paramArrayOfByte = arrayOfByte;
      paramIdentity.setPassphrase(paramArrayOfByte);
      Util.bzero(paramArrayOfByte);
      if ((this.identityRepository instanceof LocalIdentityRepository))
      {
        ((LocalIdentityRepository)this.identityRepository).add(paramIdentity);
        return;
      }
    }
    finally
    {
      Util.bzero(paramArrayOfByte);
    }
    if (((paramIdentity instanceof IdentityFile)) && (!paramIdentity.isEncrypted()))
    {
      this.identityRepository.add(((IdentityFile)paramIdentity).getKeyPair().forSSHAgent());
      return;
    }
    try
    {
      if (!(this.identityRepository instanceof IdentityRepository.Wrapper))
        setIdentityRepository(new IdentityRepository.Wrapper(this.identityRepository));
      ((IdentityRepository.Wrapper)this.identityRepository).add(paramIdentity);
      return;
    }
    finally
    {
    }
  }

  public void addIdentity(String paramString)
    throws JSchException
  {
    addIdentity(paramString, (byte[])null);
  }

  public void addIdentity(String paramString1, String paramString2)
    throws JSchException
  {
    byte[] arrayOfByte = null;
    if (paramString2 != null)
      arrayOfByte = Util.str2byte(paramString2);
    addIdentity(paramString1, arrayOfByte);
    if (arrayOfByte != null)
      Util.bzero(arrayOfByte);
  }

  public void addIdentity(String paramString1, String paramString2, byte[] paramArrayOfByte)
    throws JSchException
  {
    addIdentity(IdentityFile.newInstance(paramString1, paramString2, this), paramArrayOfByte);
  }

  public void addIdentity(String paramString, byte[] paramArrayOfByte)
    throws JSchException
  {
    addIdentity(IdentityFile.newInstance(paramString, null, this), paramArrayOfByte);
  }

  public void addIdentity(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    throws JSchException
  {
    addIdentity(IdentityFile.newInstance(paramString, paramArrayOfByte1, paramArrayOfByte2, this), paramArrayOfByte3);
  }

  protected void addSession(Session paramSession)
  {
    synchronized (this.sessionPool)
    {
      this.sessionPool.addElement(paramSession);
      return;
    }
  }

  public ConfigRepository getConfigRepository()
  {
    return this.configRepository;
  }

  public HostKeyRepository getHostKeyRepository()
  {
    if (this.known_hosts == null)
      this.known_hosts = new KnownHosts(this);
    return this.known_hosts;
  }

  public Vector getIdentityNames()
    throws JSchException
  {
    Vector localVector1 = new Vector();
    Vector localVector2 = this.identityRepository.getIdentities();
    for (int i = 0; i < localVector2.size(); i++)
      localVector1.addElement(((Identity)localVector2.elementAt(i)).getName());
    return localVector1;
  }

  public IdentityRepository getIdentityRepository()
  {
    try
    {
      IdentityRepository localIdentityRepository = this.identityRepository;
      return localIdentityRepository;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Session getSession(String paramString)
    throws JSchException
  {
    return getSession(null, paramString, 22);
  }

  public Session getSession(String paramString1, String paramString2)
    throws JSchException
  {
    return getSession(paramString1, paramString2, 22);
  }

  public Session getSession(String paramString1, String paramString2, int paramInt)
    throws JSchException
  {
    if (paramString2 == null)
      throw new JSchException("host must not be null.");
    return new Session(this, paramString1, paramString2, paramInt);
  }

  public void removeAllIdentity()
    throws JSchException
  {
    this.identityRepository.removeAll();
  }

  public void removeIdentity(Identity paramIdentity)
    throws JSchException
  {
    this.identityRepository.remove(paramIdentity.getPublicKeyBlob());
  }

  public void removeIdentity(String paramString)
    throws JSchException
  {
    Vector localVector = this.identityRepository.getIdentities();
    int i = 0;
    if (i < localVector.size())
    {
      Identity localIdentity = (Identity)localVector.elementAt(i);
      if (!localIdentity.getName().equals(paramString));
      while (true)
      {
        i++;
        break;
        if ((this.identityRepository instanceof LocalIdentityRepository))
          ((LocalIdentityRepository)this.identityRepository).remove(localIdentity);
        else
          this.identityRepository.remove(localIdentity.getPublicKeyBlob());
      }
    }
  }

  protected boolean removeSession(Session paramSession)
  {
    synchronized (this.sessionPool)
    {
      boolean bool = this.sessionPool.remove(paramSession);
      return bool;
    }
  }

  public void setConfigRepository(ConfigRepository paramConfigRepository)
  {
    this.configRepository = paramConfigRepository;
  }

  public void setHostKeyRepository(HostKeyRepository paramHostKeyRepository)
  {
    this.known_hosts = paramHostKeyRepository;
  }

  public void setIdentityRepository(IdentityRepository paramIdentityRepository)
  {
    if (paramIdentityRepository == null);
    try
    {
      for (this.identityRepository = this.defaultIdentityRepository; ; this.identityRepository = paramIdentityRepository)
        return;
    }
    finally
    {
    }
  }

  public void setKnownHosts(InputStream paramInputStream)
    throws JSchException
  {
    if (this.known_hosts == null)
      this.known_hosts = new KnownHosts(this);
    if ((this.known_hosts instanceof KnownHosts))
      synchronized (this.known_hosts)
      {
        ((KnownHosts)this.known_hosts).setKnownHosts(paramInputStream);
        return;
      }
  }

  public void setKnownHosts(String paramString)
    throws JSchException
  {
    if (this.known_hosts == null)
      this.known_hosts = new KnownHosts(this);
    if ((this.known_hosts instanceof KnownHosts))
      synchronized (this.known_hosts)
      {
        ((KnownHosts)this.known_hosts).setKnownHosts(paramString);
        return;
      }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.JSch
 * JD-Core Version:    0.6.2
 */