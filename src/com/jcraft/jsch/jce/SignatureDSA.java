package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

public class SignatureDSA
  implements com.jcraft.jsch.SignatureDSA
{
  KeyFactory keyFactory;
  Signature signature;

  public void init()
    throws Exception
  {
    this.signature = Signature.getInstance("SHA1withDSA");
    this.keyFactory = KeyFactory.getInstance("DSA");
  }

  public void setPrvKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
    throws Exception
  {
    DSAPrivateKeySpec localDSAPrivateKeySpec = new DSAPrivateKeySpec(new BigInteger(paramArrayOfByte1), new BigInteger(paramArrayOfByte2), new BigInteger(paramArrayOfByte3), new BigInteger(paramArrayOfByte4));
    PrivateKey localPrivateKey = this.keyFactory.generatePrivate(localDSAPrivateKeySpec);
    this.signature.initSign(localPrivateKey);
  }

  public void setPubKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
    throws Exception
  {
    DSAPublicKeySpec localDSAPublicKeySpec = new DSAPublicKeySpec(new BigInteger(paramArrayOfByte1), new BigInteger(paramArrayOfByte2), new BigInteger(paramArrayOfByte3), new BigInteger(paramArrayOfByte4));
    PublicKey localPublicKey = this.keyFactory.generatePublic(localDSAPublicKeySpec);
    this.signature.initVerify(localPublicKey);
  }

  public byte[] sign()
    throws Exception
  {
    int i = 1;
    int j = 20;
    byte[] arrayOfByte1 = this.signature.sign();
    int k = 3 + 1;
    int m = 0xFF & arrayOfByte1[3];
    byte[] arrayOfByte2 = new byte[m];
    System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0, arrayOfByte2.length);
    int n = 1 + (m + 4);
    int i1 = n + 1;
    byte[] arrayOfByte3 = new byte[0xFF & arrayOfByte1[n]];
    System.arraycopy(arrayOfByte1, i1, arrayOfByte3, 0, arrayOfByte3.length);
    byte[] arrayOfByte4 = new byte[40];
    int i2;
    int i3;
    label109: int i4;
    label119: int i5;
    if (arrayOfByte2.length > j)
    {
      i2 = i;
      if (arrayOfByte2.length <= j)
        break label176;
      i3 = 0;
      if (arrayOfByte2.length <= j)
        break label187;
      i4 = j;
      System.arraycopy(arrayOfByte2, i2, arrayOfByte4, i3, i4);
      if (arrayOfByte3.length <= j)
        break label195;
      label139: if (arrayOfByte3.length <= j)
        break label200;
      i5 = j;
      label149: if (arrayOfByte3.length <= j)
        break label211;
    }
    while (true)
    {
      System.arraycopy(arrayOfByte3, i, arrayOfByte4, i5, j);
      return arrayOfByte4;
      i2 = 0;
      break;
      label176: i3 = 20 - arrayOfByte2.length;
      break label109;
      label187: i4 = arrayOfByte2.length;
      break label119;
      label195: i = 0;
      break label139;
      label200: i5 = 40 - arrayOfByte3.length;
      break label149;
      label211: j = arrayOfByte3.length;
    }
  }

  public void update(byte[] paramArrayOfByte)
    throws Exception
  {
    this.signature.update(paramArrayOfByte);
  }

  public boolean verify(byte[] paramArrayOfByte)
    throws Exception
  {
    if ((paramArrayOfByte[0] == 0) && (paramArrayOfByte[1] == 0) && (paramArrayOfByte[2] == 0))
    {
      int m = 0 + 1;
      int n = 0xFF000000 & paramArrayOfByte[0] << 24;
      int i1 = m + 1;
      int i2 = n | 0xFF0000 & paramArrayOfByte[m] << 16;
      int i3 = i1 + 1;
      int i4 = i2 | 0xFF00 & paramArrayOfByte[i1] << 8;
      (i3 + 1);
      int i5 = 4 + (i4 | 0xFF & paramArrayOfByte[i3]);
      int i6 = i5 + 1;
      int i7 = 0xFF000000 & paramArrayOfByte[i5] << 24;
      int i8 = i6 + 1;
      int i9 = i7 | 0xFF0000 & paramArrayOfByte[i6] << 16;
      int i10 = i8 + 1;
      int i11 = i9 | 0xFF00 & paramArrayOfByte[i8] << 8;
      int i12 = i10 + 1;
      int i13 = i11 | 0xFF & paramArrayOfByte[i10];
      byte[] arrayOfByte2 = new byte[i13];
      System.arraycopy(paramArrayOfByte, i12, arrayOfByte2, 0, i13);
      paramArrayOfByte = arrayOfByte2;
    }
    int i;
    if ((0x80 & paramArrayOfByte[0]) != 0)
    {
      i = 1;
      if ((0x80 & paramArrayOfByte[20]) == 0)
        break label377;
    }
    label377: for (int j = 1; ; j = 0)
    {
      byte[] arrayOfByte1 = new byte[j + (i + (6 + paramArrayOfByte.length))];
      arrayOfByte1[0] = 48;
      arrayOfByte1[1] = 44;
      arrayOfByte1[1] = ((byte)(i + arrayOfByte1[1]));
      arrayOfByte1[1] = ((byte)(j + arrayOfByte1[1]));
      arrayOfByte1[2] = 2;
      arrayOfByte1[3] = 20;
      arrayOfByte1[3] = ((byte)(i + arrayOfByte1[3]));
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, i + 4, 20);
      arrayOfByte1[(4 + arrayOfByte1[3])] = 2;
      arrayOfByte1[(5 + arrayOfByte1[3])] = 20;
      int k = 5 + arrayOfByte1[3];
      arrayOfByte1[k] = ((byte)(j + arrayOfByte1[k]));
      System.arraycopy(paramArrayOfByte, 20, arrayOfByte1, j + (6 + arrayOfByte1[3]), 20);
      return this.signature.verify(arrayOfByte1);
      i = 0;
      break;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.SignatureDSA
 * JD-Core Version:    0.6.2
 */