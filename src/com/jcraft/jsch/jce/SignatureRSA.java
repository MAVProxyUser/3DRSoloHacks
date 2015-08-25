package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class SignatureRSA
  implements com.jcraft.jsch.SignatureRSA
{
  KeyFactory keyFactory;
  Signature signature;

  public void init()
    throws Exception
  {
    this.signature = Signature.getInstance("SHA1withRSA");
    this.keyFactory = KeyFactory.getInstance("RSA");
  }

  public void setPrvKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    RSAPrivateKeySpec localRSAPrivateKeySpec = new RSAPrivateKeySpec(new BigInteger(paramArrayOfByte2), new BigInteger(paramArrayOfByte1));
    PrivateKey localPrivateKey = this.keyFactory.generatePrivate(localRSAPrivateKeySpec);
    this.signature.initSign(localPrivateKey);
  }

  public void setPubKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    RSAPublicKeySpec localRSAPublicKeySpec = new RSAPublicKeySpec(new BigInteger(paramArrayOfByte2), new BigInteger(paramArrayOfByte1));
    PublicKey localPublicKey = this.keyFactory.generatePublic(localRSAPublicKeySpec);
    this.signature.initVerify(localPublicKey);
  }

  public byte[] sign()
    throws Exception
  {
    return this.signature.sign();
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
      int i = 0 + 1;
      int j = 0xFF000000 & paramArrayOfByte[0] << 24;
      int k = i + 1;
      int m = j | 0xFF0000 & paramArrayOfByte[i] << 16;
      int n = k + 1;
      int i1 = m | 0xFF00 & paramArrayOfByte[k] << 8;
      (n + 1);
      int i2 = 4 + (i1 | 0xFF & paramArrayOfByte[n]);
      int i3 = i2 + 1;
      int i4 = 0xFF000000 & paramArrayOfByte[i2] << 24;
      int i5 = i3 + 1;
      int i6 = i4 | 0xFF0000 & paramArrayOfByte[i3] << 16;
      int i7 = i5 + 1;
      int i8 = i6 | 0xFF00 & paramArrayOfByte[i5] << 8;
      int i9 = i7 + 1;
      int i10 = i8 | 0xFF & paramArrayOfByte[i7];
      byte[] arrayOfByte = new byte[i10];
      System.arraycopy(paramArrayOfByte, i9, arrayOfByte, 0, i10);
      paramArrayOfByte = arrayOfByte;
    }
    return this.signature.verify(paramArrayOfByte);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.SignatureRSA
 * JD-Core Version:    0.6.2
 */