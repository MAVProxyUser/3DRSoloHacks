package com.jcraft.jsch;

import java.io.PrintStream;

public class DHG1 extends KeyExchange
{
  static final int DSS = 1;
  static final int RSA = 0;
  private static final int SSH_MSG_KEXDH_INIT = 30;
  private static final int SSH_MSG_KEXDH_REPLY = 31;
  static final byte[] g = { 2 };
  static final byte[] p = { 0, -1, -1, -1, -1, -1, -1, -1, -1, -55, 15, -38, -94, 33, 104, -62, 52, -60, -58, 98, -117, -128, -36, 28, -47, 41, 2, 78, 8, -118, 103, -52, 116, 2, 11, -66, -90, 59, 19, -101, 34, 81, 74, 8, 121, -114, 52, 4, -35, -17, -107, 25, -77, -51, 58, 67, 27, 48, 43, 10, 109, -14, 95, 20, 55, 79, -31, 53, 109, 109, 81, -62, 69, -28, -123, -75, 118, 98, 94, 126, -58, -12, 76, 66, -23, -90, 55, -19, 107, 11, -1, 92, -74, -12, 6, -73, -19, -18, 56, 107, -5, 90, -119, -97, -91, -82, -97, 36, 17, 124, 75, 31, -26, 73, 40, 102, 81, -20, -26, 83, -127, -1, -1, -1, -1, -1, -1, -1, -1 };
  byte[] I_C;
  byte[] I_S;
  byte[] V_C;
  byte[] V_S;
  private Buffer buf;
  DH dh;
  byte[] e;
  private Packet packet;
  private int state;
  private int type = 0;

  public String getKeyType()
  {
    if (this.type == 1)
      return "DSA";
    return "RSA";
  }

  public int getState()
  {
    return this.state;
  }

  public void init(Session paramSession, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
    throws Exception
  {
    this.session = paramSession;
    this.V_S = paramArrayOfByte1;
    this.V_C = paramArrayOfByte2;
    this.I_S = paramArrayOfByte3;
    this.I_C = paramArrayOfByte4;
    try
    {
      this.sha = ((HASH)Class.forName(paramSession.getConfig("sha-1")).newInstance());
      this.sha.init();
      this.buf = new Buffer();
      this.packet = new Packet(this.buf);
    }
    catch (Exception localException1)
    {
      try
      {
        this.dh = ((DH)Class.forName(paramSession.getConfig("dh")).newInstance());
        this.dh.init();
        this.dh.setP(p);
        this.dh.setG(g);
        this.e = this.dh.getE();
        this.packet.reset();
        this.buf.putByte((byte)30);
        this.buf.putMPInt(this.e);
        paramSession.write(this.packet);
        if (JSch.getLogger().isEnabled(1))
        {
          JSch.getLogger().log(1, "SSH_MSG_KEXDH_INIT sent");
          JSch.getLogger().log(1, "expecting SSH_MSG_KEXDH_REPLY");
        }
        this.state = 31;
        return;
        localException1 = localException1;
        System.err.println(localException1);
      }
      catch (Exception localException2)
      {
        throw localException2;
      }
    }
  }

  public boolean next(Buffer paramBuffer)
    throws Exception
  {
    switch (this.state)
    {
    default:
      return false;
    case 31:
    }
    paramBuffer.getInt();
    paramBuffer.getByte();
    int i = paramBuffer.getByte();
    if (i != 31)
    {
      System.err.println("type: must be 31 " + i);
      return false;
    }
    this.K_S = paramBuffer.getString();
    byte[] arrayOfByte1 = paramBuffer.getMPInt();
    byte[] arrayOfByte2 = paramBuffer.getString();
    this.dh.setF(arrayOfByte1);
    this.K = normalize(this.dh.getK());
    this.buf.reset();
    this.buf.putString(this.V_C);
    this.buf.putString(this.V_S);
    this.buf.putString(this.I_C);
    this.buf.putString(this.I_S);
    this.buf.putString(this.K_S);
    this.buf.putMPInt(this.e);
    this.buf.putMPInt(arrayOfByte1);
    this.buf.putMPInt(this.K);
    byte[] arrayOfByte3 = new byte[this.buf.getLength()];
    this.buf.getByte(arrayOfByte3);
    this.sha.update(arrayOfByte3, 0, arrayOfByte3.length);
    this.H = this.sha.digest();
    byte[] arrayOfByte4 = this.K_S;
    int j = 0 + 1;
    int k = 0xFF000000 & arrayOfByte4[0] << 24;
    byte[] arrayOfByte5 = this.K_S;
    int m = j + 1;
    int n = k | 0xFF0000 & arrayOfByte5[j] << 16;
    byte[] arrayOfByte6 = this.K_S;
    int i1 = m + 1;
    int i2 = n | 0xFF00 & arrayOfByte6[m] << 8;
    byte[] arrayOfByte7 = this.K_S;
    int i3 = i1 + 1;
    int i4 = i2 | 0xFF & arrayOfByte7[i1];
    String str = Util.byte2str(this.K_S, i3, i4);
    int i5 = i4 + 4;
    byte[] arrayOfByte33;
    byte[] arrayOfByte38;
    SignatureRSA localSignatureRSA;
    if (str.equals("ssh-rsa"))
    {
      this.type = 0;
      byte[] arrayOfByte29 = this.K_S;
      int i41 = i5 + 1;
      int i42 = 0xFF000000 & arrayOfByte29[i5] << 24;
      byte[] arrayOfByte30 = this.K_S;
      int i43 = i41 + 1;
      int i44 = i42 | 0xFF0000 & arrayOfByte30[i41] << 16;
      byte[] arrayOfByte31 = this.K_S;
      int i45 = i43 + 1;
      int i46 = i44 | 0xFF00 & arrayOfByte31[i43] << 8;
      byte[] arrayOfByte32 = this.K_S;
      int i47 = i45 + 1;
      int i48 = i46 | 0xFF & arrayOfByte32[i45];
      arrayOfByte33 = new byte[i48];
      System.arraycopy(this.K_S, i47, arrayOfByte33, 0, i48);
      int i49 = i47 + i48;
      byte[] arrayOfByte34 = this.K_S;
      int i50 = i49 + 1;
      int i51 = 0xFF000000 & arrayOfByte34[i49] << 24;
      byte[] arrayOfByte35 = this.K_S;
      int i52 = i50 + 1;
      int i53 = i51 | 0xFF0000 & arrayOfByte35[i50] << 16;
      byte[] arrayOfByte36 = this.K_S;
      int i54 = i52 + 1;
      int i55 = i53 | 0xFF00 & arrayOfByte36[i52] << 8;
      byte[] arrayOfByte37 = this.K_S;
      int i56 = i54 + 1;
      int i57 = i55 | 0xFF & arrayOfByte37[i54];
      arrayOfByte38 = new byte[i57];
      System.arraycopy(this.K_S, i56, arrayOfByte38, 0, i57);
      (i56 + i57);
      localSignatureRSA = null;
    }
    while (true)
    {
      boolean bool;
      try
      {
        localSignatureRSA = (SignatureRSA)Class.forName(this.session.getConfig("signature.rsa")).newInstance();
        localSignatureRSA.init();
        localSignatureRSA.setPubKey(arrayOfByte33, arrayOfByte38);
        byte[] arrayOfByte39 = this.H;
        localSignatureRSA.update(arrayOfByte39);
        bool = localSignatureRSA.verify(arrayOfByte2);
        if (JSch.getLogger().isEnabled(1))
          JSch.getLogger().log(1, "ssh_rsa_verify: signature " + bool);
        this.state = 0;
        return bool;
      }
      catch (Exception localException2)
      {
        System.err.println(localException2);
        continue;
      }
      if (str.equals("ssh-dss"))
      {
        this.type = 1;
        byte[] arrayOfByte8 = this.K_S;
        int i6 = i5 + 1;
        int i7 = 0xFF000000 & arrayOfByte8[i5] << 24;
        byte[] arrayOfByte9 = this.K_S;
        int i8 = i6 + 1;
        int i9 = i7 | 0xFF0000 & arrayOfByte9[i6] << 16;
        byte[] arrayOfByte10 = this.K_S;
        int i10 = i8 + 1;
        int i11 = i9 | 0xFF00 & arrayOfByte10[i8] << 8;
        byte[] arrayOfByte11 = this.K_S;
        int i12 = i10 + 1;
        int i13 = i11 | 0xFF & arrayOfByte11[i10];
        byte[] arrayOfByte12 = new byte[i13];
        System.arraycopy(this.K_S, i12, arrayOfByte12, 0, i13);
        int i14 = i12 + i13;
        byte[] arrayOfByte13 = this.K_S;
        int i15 = i14 + 1;
        int i16 = 0xFF000000 & arrayOfByte13[i14] << 24;
        byte[] arrayOfByte14 = this.K_S;
        int i17 = i15 + 1;
        int i18 = i16 | 0xFF0000 & arrayOfByte14[i15] << 16;
        byte[] arrayOfByte15 = this.K_S;
        int i19 = i17 + 1;
        int i20 = i18 | 0xFF00 & arrayOfByte15[i17] << 8;
        byte[] arrayOfByte16 = this.K_S;
        int i21 = i19 + 1;
        int i22 = i20 | 0xFF & arrayOfByte16[i19];
        byte[] arrayOfByte17 = new byte[i22];
        System.arraycopy(this.K_S, i21, arrayOfByte17, 0, i22);
        int i23 = i21 + i22;
        byte[] arrayOfByte18 = this.K_S;
        int i24 = i23 + 1;
        int i25 = 0xFF000000 & arrayOfByte18[i23] << 24;
        byte[] arrayOfByte19 = this.K_S;
        int i26 = i24 + 1;
        int i27 = i25 | 0xFF0000 & arrayOfByte19[i24] << 16;
        byte[] arrayOfByte20 = this.K_S;
        int i28 = i26 + 1;
        int i29 = i27 | 0xFF00 & arrayOfByte20[i26] << 8;
        byte[] arrayOfByte21 = this.K_S;
        int i30 = i28 + 1;
        int i31 = i29 | 0xFF & arrayOfByte21[i28];
        byte[] arrayOfByte22 = new byte[i31];
        System.arraycopy(this.K_S, i30, arrayOfByte22, 0, i31);
        int i32 = i30 + i31;
        byte[] arrayOfByte23 = this.K_S;
        int i33 = i32 + 1;
        int i34 = 0xFF000000 & arrayOfByte23[i32] << 24;
        byte[] arrayOfByte24 = this.K_S;
        int i35 = i33 + 1;
        int i36 = i34 | 0xFF0000 & arrayOfByte24[i33] << 16;
        byte[] arrayOfByte25 = this.K_S;
        int i37 = i35 + 1;
        int i38 = i36 | 0xFF00 & arrayOfByte25[i35] << 8;
        byte[] arrayOfByte26 = this.K_S;
        int i39 = i37 + 1;
        int i40 = i38 | 0xFF & arrayOfByte26[i37];
        byte[] arrayOfByte27 = new byte[i40];
        System.arraycopy(this.K_S, i39, arrayOfByte27, 0, i40);
        (i39 + i40);
        SignatureDSA localSignatureDSA = null;
        try
        {
          localSignatureDSA = (SignatureDSA)Class.forName(this.session.getConfig("signature.dss")).newInstance();
          localSignatureDSA.init();
          localSignatureDSA.setPubKey(arrayOfByte27, arrayOfByte12, arrayOfByte17, arrayOfByte22);
          byte[] arrayOfByte28 = this.H;
          localSignatureDSA.update(arrayOfByte28);
          bool = localSignatureDSA.verify(arrayOfByte2);
          if (!JSch.getLogger().isEnabled(1))
            continue;
          JSch.getLogger().log(1, "ssh_dss_verify: signature " + bool);
        }
        catch (Exception localException1)
        {
          while (true)
            System.err.println(localException1);
        }
      }
      else
      {
        System.err.println("unknown alg");
        bool = false;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.DHG1
 * JD-Core Version:    0.6.2
 */