package com.jcraft.jsch;

import java.util.Vector;

class LocalIdentityRepository
  implements IdentityRepository
{
  private static final String name = "Local Identity Repository";
  private Vector identities = new Vector();
  private JSch jsch;

  LocalIdentityRepository(JSch paramJSch)
  {
    this.jsch = paramJSch;
  }

  public void add(Identity paramIdentity)
  {
    try
    {
      if (!this.identities.contains(paramIdentity))
        this.identities.addElement(paramIdentity);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  public boolean add(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 41
    //   4: aload_1
    //   5: aconst_null
    //   6: aload_0
    //   7: getfield 26	com/jcraft/jsch/LocalIdentityRepository:jsch	Lcom/jcraft/jsch/JSch;
    //   10: invokestatic 47	com/jcraft/jsch/IdentityFile:newInstance	(Ljava/lang/String;[B[BLcom/jcraft/jsch/JSch;)Lcom/jcraft/jsch/IdentityFile;
    //   13: astore 5
    //   15: aload_0
    //   16: getfield 24	com/jcraft/jsch/LocalIdentityRepository:identities	Ljava/util/Vector;
    //   19: aload 5
    //   21: invokevirtual 36	java/util/Vector:addElement	(Ljava/lang/Object;)V
    //   24: iconst_1
    //   25: istore 4
    //   27: aload_0
    //   28: monitorexit
    //   29: iload 4
    //   31: ireturn
    //   32: astore_3
    //   33: iconst_0
    //   34: istore 4
    //   36: goto -9 -> 27
    //   39: astore_2
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_2
    //   43: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	24	32	com/jcraft/jsch/JSchException
    //   2	24	39	finally
  }

  public Vector getIdentities()
  {
    try
    {
      Vector localVector = new Vector();
      for (int i = 0; i < this.identities.size(); i++)
        localVector.addElement(this.identities.elementAt(i));
      return localVector;
    }
    finally
    {
    }
  }

  public String getName()
  {
    return "Local Identity Repository";
  }

  public int getStatus()
  {
    return 2;
  }

  void remove(Identity paramIdentity)
  {
    try
    {
      this.identities.removeElement(paramIdentity);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean remove(byte[] paramArrayOfByte)
  {
    boolean bool;
    if (paramArrayOfByte == null)
    {
      bool = false;
      return bool;
    }
    for (int i = 0; ; i++)
      try
      {
        if (i < this.identities.size())
        {
          Identity localIdentity = (Identity)this.identities.elementAt(i);
          byte[] arrayOfByte = localIdentity.getPublicKeyBlob();
          if ((arrayOfByte == null) || (!Util.array_equals(paramArrayOfByte, arrayOfByte)))
            continue;
          this.identities.removeElement(localIdentity);
          localIdentity.clear();
          bool = true;
          break;
        }
        bool = false;
        break;
      }
      finally
      {
      }
  }

  public void removeAll()
  {
    int i = 0;
    try
    {
      while (i < this.identities.size())
      {
        ((Identity)this.identities.elementAt(i)).clear();
        i++;
      }
      this.identities.removeAllElements();
      return;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.LocalIdentityRepository
 * JD-Core Version:    0.6.2
 */