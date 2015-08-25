package com.jcraft.jsch;

import java.util.Vector;

public abstract interface IdentityRepository
{
  public static final int NOTRUNNING = 1;
  public static final int RUNNING = 2;
  public static final int UNAVAILABLE;

  public abstract boolean add(byte[] paramArrayOfByte);

  public abstract Vector getIdentities();

  public abstract String getName();

  public abstract int getStatus();

  public abstract boolean remove(byte[] paramArrayOfByte);

  public abstract void removeAll();

  public static class Wrapper
    implements IdentityRepository
  {
    private Vector cache = new Vector();
    private IdentityRepository ir;
    private boolean keep_in_cache = false;

    Wrapper(IdentityRepository paramIdentityRepository)
    {
      this(paramIdentityRepository, false);
    }

    Wrapper(IdentityRepository paramIdentityRepository, boolean paramBoolean)
    {
      this.ir = paramIdentityRepository;
      this.keep_in_cache = paramBoolean;
    }

    void add(Identity paramIdentity)
    {
      if ((!this.keep_in_cache) && (!paramIdentity.isEncrypted()) && ((paramIdentity instanceof IdentityFile)));
      try
      {
        this.ir.add(((IdentityFile)paramIdentity).getKeyPair().forSSHAgent());
        return;
        this.cache.addElement(paramIdentity);
        return;
      }
      catch (JSchException localJSchException)
      {
      }
    }

    public boolean add(byte[] paramArrayOfByte)
    {
      return this.ir.add(paramArrayOfByte);
    }

    void check()
    {
      if (this.cache.size() > 0)
      {
        Object[] arrayOfObject = this.cache.toArray();
        for (int i = 0; i < arrayOfObject.length; i++)
        {
          Identity localIdentity = (Identity)arrayOfObject[i];
          this.cache.removeElement(localIdentity);
          add(localIdentity);
        }
      }
    }

    public Vector getIdentities()
    {
      Vector localVector1 = new Vector();
      for (int i = 0; i < this.cache.size(); i++)
        localVector1.add((Identity)this.cache.elementAt(i));
      Vector localVector2 = this.ir.getIdentities();
      for (int j = 0; j < localVector2.size(); j++)
        localVector1.add(localVector2.elementAt(j));
      return localVector1;
    }

    public String getName()
    {
      return this.ir.getName();
    }

    public int getStatus()
    {
      return this.ir.getStatus();
    }

    public boolean remove(byte[] paramArrayOfByte)
    {
      return this.ir.remove(paramArrayOfByte);
    }

    public void removeAll()
    {
      this.cache.removeAllElements();
      this.ir.removeAll();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.IdentityRepository
 * JD-Core Version:    0.6.2
 */