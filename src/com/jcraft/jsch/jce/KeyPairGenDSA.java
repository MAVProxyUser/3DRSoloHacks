package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

public class KeyPairGenDSA
  implements com.jcraft.jsch.KeyPairGenDSA
{
  byte[] g;
  byte[] p;
  byte[] q;
  byte[] x;
  byte[] y;

  public byte[] getG()
  {
    return this.g;
  }

  public byte[] getP()
  {
    return this.p;
  }

  public byte[] getQ()
  {
    return this.q;
  }

  public byte[] getX()
  {
    return this.x;
  }

  public byte[] getY()
  {
    return this.y;
  }

  public void init(int paramInt)
    throws Exception
  {
    KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance("DSA");
    localKeyPairGenerator.initialize(paramInt, new SecureRandom());
    KeyPair localKeyPair = localKeyPairGenerator.generateKeyPair();
    PublicKey localPublicKey = localKeyPair.getPublic();
    PrivateKey localPrivateKey = localKeyPair.getPrivate();
    this.x = ((DSAPrivateKey)localPrivateKey).getX().toByteArray();
    this.y = ((DSAPublicKey)localPublicKey).getY().toByteArray();
    DSAParams localDSAParams = ((DSAKey)localPrivateKey).getParams();
    this.p = localDSAParams.getP().toByteArray();
    this.q = localDSAParams.getQ().toByteArray();
    this.g = localDSAParams.getG().toByteArray();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.KeyPairGenDSA
 * JD-Core Version:    0.6.2
 */