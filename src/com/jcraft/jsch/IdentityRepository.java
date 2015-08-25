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
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.IdentityRepository
 * JD-Core Version:    0.6.2
 */