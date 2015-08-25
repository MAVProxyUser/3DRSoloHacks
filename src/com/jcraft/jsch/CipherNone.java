package com.jcraft.jsch;

public class CipherNone
  implements Cipher
{
  private static final int bsize = 16;
  private static final int ivsize = 8;

  public int getBlockSize()
  {
    return 16;
  }

  public int getIVSize()
  {
    return 8;
  }

  public void init(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
  }

  public boolean isCBC()
  {
    return false;
  }

  public void update(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws Exception
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.CipherNone
 * JD-Core Version:    0.6.2
 */