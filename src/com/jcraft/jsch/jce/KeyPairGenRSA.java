package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyPairGenRSA
  implements com.jcraft.jsch.KeyPairGenRSA
{
  byte[] c;
  byte[] d;
  byte[] e;
  byte[] ep;
  byte[] eq;
  byte[] n;
  byte[] p;
  byte[] q;

  public byte[] getC()
  {
    return this.c;
  }

  public byte[] getD()
  {
    return this.d;
  }

  public byte[] getE()
  {
    return this.e;
  }

  public byte[] getEP()
  {
    return this.ep;
  }

  public byte[] getEQ()
  {
    return this.eq;
  }

  public byte[] getN()
  {
    return this.n;
  }

  public byte[] getP()
  {
    return this.p;
  }

  public byte[] getQ()
  {
    return this.q;
  }

  public void init(int paramInt)
    throws Exception
  {
    KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
    localKeyPairGenerator.initialize(paramInt, new SecureRandom());
    KeyPair localKeyPair = localKeyPairGenerator.generateKeyPair();
    PublicKey localPublicKey = localKeyPair.getPublic();
    PrivateKey localPrivateKey = localKeyPair.getPrivate();
    this.d = ((RSAPrivateKey)localPrivateKey).getPrivateExponent().toByteArray();
    this.e = ((RSAPublicKey)localPublicKey).getPublicExponent().toByteArray();
    this.n = ((RSAPrivateKey)localPrivateKey).getModulus().toByteArray();
    this.c = ((RSAPrivateCrtKey)localPrivateKey).getCrtCoefficient().toByteArray();
    this.ep = ((RSAPrivateCrtKey)localPrivateKey).getPrimeExponentP().toByteArray();
    this.eq = ((RSAPrivateCrtKey)localPrivateKey).getPrimeExponentQ().toByteArray();
    this.p = ((RSAPrivateCrtKey)localPrivateKey).getPrimeP().toByteArray();
    this.q = ((RSAPrivateCrtKey)localPrivateKey).getPrimeQ().toByteArray();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.KeyPairGenRSA
 * JD-Core Version:    0.6.2
 */