package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class DH
  implements com.jcraft.jsch.DH
{
  BigInteger K;
  byte[] K_array;
  BigInteger e;
  byte[] e_array;
  BigInteger f;
  BigInteger g;
  private KeyAgreement myKeyAgree;
  private KeyPairGenerator myKpairGen;
  BigInteger p;

  public byte[] getE()
    throws Exception
  {
    if (this.e == null)
    {
      DHParameterSpec localDHParameterSpec = new DHParameterSpec(this.p, this.g);
      this.myKpairGen.initialize(localDHParameterSpec);
      KeyPair localKeyPair = this.myKpairGen.generateKeyPair();
      this.myKeyAgree.init(localKeyPair.getPrivate());
      localKeyPair.getPublic().getEncoded();
      this.e = ((DHPublicKey)localKeyPair.getPublic()).getY();
      this.e_array = this.e.toByteArray();
    }
    return this.e_array;
  }

  public byte[] getK()
    throws Exception
  {
    if (this.K == null)
    {
      PublicKey localPublicKey = KeyFactory.getInstance("DH").generatePublic(new DHPublicKeySpec(this.f, this.p, this.g));
      this.myKeyAgree.doPhase(localPublicKey, true);
      byte[] arrayOfByte = this.myKeyAgree.generateSecret();
      this.K = new BigInteger(arrayOfByte);
      this.K_array = this.K.toByteArray();
      this.K_array = arrayOfByte;
    }
    return this.K_array;
  }

  public void init()
    throws Exception
  {
    this.myKpairGen = KeyPairGenerator.getInstance("DH");
    this.myKeyAgree = KeyAgreement.getInstance("DH");
  }

  void setF(BigInteger paramBigInteger)
  {
    this.f = paramBigInteger;
  }

  public void setF(byte[] paramArrayOfByte)
  {
    setF(new BigInteger(paramArrayOfByte));
  }

  void setG(BigInteger paramBigInteger)
  {
    this.g = paramBigInteger;
  }

  public void setG(byte[] paramArrayOfByte)
  {
    setG(new BigInteger(paramArrayOfByte));
  }

  void setP(BigInteger paramBigInteger)
  {
    this.p = paramBigInteger;
  }

  public void setP(byte[] paramArrayOfByte)
  {
    setP(new BigInteger(paramArrayOfByte));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.DH
 * JD-Core Version:    0.6.2
 */