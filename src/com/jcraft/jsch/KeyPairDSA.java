package com.jcraft.jsch;

import java.math.BigInteger;

public class KeyPairDSA extends KeyPair
{
  private static final byte[] begin = Util.str2byte("-----BEGIN DSA PRIVATE KEY-----");
  private static final byte[] end = Util.str2byte("-----END DSA PRIVATE KEY-----");
  private static final byte[] sshdss = Util.str2byte("ssh-dss");
  private byte[] G_array;
  private byte[] P_array;
  private byte[] Q_array;
  private int key_size = 1024;
  private byte[] prv_array;
  private byte[] pub_array;

  public KeyPairDSA(JSch paramJSch)
  {
    this(paramJSch, null, null, null, null, null);
  }

  public KeyPairDSA(JSch paramJSch, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, byte[] paramArrayOfByte5)
  {
    super(paramJSch);
    this.P_array = paramArrayOfByte1;
    this.Q_array = paramArrayOfByte2;
    this.G_array = paramArrayOfByte3;
    this.pub_array = paramArrayOfByte4;
    this.prv_array = paramArrayOfByte5;
    if (paramArrayOfByte1 != null)
      this.key_size = new BigInteger(paramArrayOfByte1).bitLength();
  }

  static KeyPair fromSSHAgent(JSch paramJSch, Buffer paramBuffer)
    throws JSchException
  {
    byte[][] arrayOfByte = paramBuffer.getBytes(7, "invalid key format");
    KeyPairDSA localKeyPairDSA = new KeyPairDSA(paramJSch, arrayOfByte[1], arrayOfByte[2], arrayOfByte[3], arrayOfByte[4], arrayOfByte[5]);
    localKeyPairDSA.publicKeyComment = new String(arrayOfByte[6]);
    localKeyPairDSA.vendor = 0;
    return localKeyPairDSA;
  }

  public void dispose()
  {
    super.dispose();
    Util.bzero(this.prv_array);
  }

  public byte[] forSSHAgent()
    throws JSchException
  {
    if (isEncrypted())
      throw new JSchException("key is encrypted.");
    Buffer localBuffer = new Buffer();
    localBuffer.putString(sshdss);
    localBuffer.putString(this.P_array);
    localBuffer.putString(this.Q_array);
    localBuffer.putString(this.G_array);
    localBuffer.putString(this.pub_array);
    localBuffer.putString(this.prv_array);
    localBuffer.putString(Util.str2byte(this.publicKeyComment));
    byte[] arrayOfByte = new byte[localBuffer.getLength()];
    localBuffer.getByte(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  void generate(int paramInt)
    throws JSchException
  {
    this.key_size = paramInt;
    try
    {
      KeyPairGenDSA localKeyPairGenDSA = (KeyPairGenDSA)Class.forName(JSch.getConfig("keypairgen.dsa")).newInstance();
      localKeyPairGenDSA.init(paramInt);
      this.P_array = localKeyPairGenDSA.getP();
      this.Q_array = localKeyPairGenDSA.getQ();
      this.G_array = localKeyPairGenDSA.getG();
      this.pub_array = localKeyPairGenDSA.getY();
      this.prv_array = localKeyPairGenDSA.getX();
      return;
    }
    catch (Exception localException)
    {
      if ((localException instanceof Throwable))
        throw new JSchException(localException.toString(), localException);
      throw new JSchException(localException.toString());
    }
  }

  byte[] getBegin()
  {
    return begin;
  }

  byte[] getEnd()
  {
    return end;
  }

  public int getKeySize()
  {
    return this.key_size;
  }

  public int getKeyType()
  {
    return 1;
  }

  byte[] getKeyTypeName()
  {
    return sshdss;
  }

  byte[] getPrivateKey()
  {
    int i = 1 + (1 + (1 + (1 + (1 + (1 + (1 + countLength(1))) + countLength(this.P_array.length) + this.P_array.length) + countLength(this.Q_array.length) + this.Q_array.length) + countLength(this.G_array.length) + this.G_array.length) + countLength(this.pub_array.length) + this.pub_array.length) + countLength(this.prv_array.length) + this.prv_array.length;
    byte[] arrayOfByte = new byte[i + (1 + countLength(i))];
    writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeINTEGER(arrayOfByte, writeSEQUENCE(arrayOfByte, 0, i), new byte[1]), this.P_array), this.Q_array), this.G_array), this.pub_array), this.prv_array);
    return arrayOfByte;
  }

  public byte[] getPublicKeyBlob()
  {
    byte[] arrayOfByte = super.getPublicKeyBlob();
    if (arrayOfByte != null)
      return arrayOfByte;
    if (this.P_array == null)
      return null;
    byte[][] arrayOfByte1 = new byte[5][];
    arrayOfByte1[0] = sshdss;
    arrayOfByte1[1] = this.P_array;
    arrayOfByte1[2] = this.Q_array;
    arrayOfByte1[3] = this.G_array;
    arrayOfByte1[4] = this.pub_array;
    return Buffer.fromBytes(arrayOfByte1).buffer;
  }

  public byte[] getSignature(byte[] paramArrayOfByte)
  {
    try
    {
      SignatureDSA localSignatureDSA = (SignatureDSA)Class.forName(JSch.getConfig("signature.dss")).newInstance();
      localSignatureDSA.init();
      localSignatureDSA.setPrvKey(this.prv_array, this.P_array, this.Q_array, this.G_array);
      localSignatureDSA.update(paramArrayOfByte);
      byte[] arrayOfByte1 = localSignatureDSA.sign();
      byte[][] arrayOfByte = new byte[2][];
      arrayOfByte[0] = sshdss;
      arrayOfByte[1] = arrayOfByte1;
      byte[] arrayOfByte2 = Buffer.fromBytes(arrayOfByte).buffer;
      return arrayOfByte2;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public Signature getVerifier()
  {
    try
    {
      SignatureDSA localSignatureDSA = (SignatureDSA)Class.forName(JSch.getConfig("signature.dss")).newInstance();
      localSignatureDSA.init();
      if ((this.pub_array == null) && (this.P_array == null) && (getPublicKeyBlob() != null))
      {
        Buffer localBuffer = new Buffer(getPublicKeyBlob());
        localBuffer.getString();
        this.P_array = localBuffer.getString();
        this.Q_array = localBuffer.getString();
        this.G_array = localBuffer.getString();
        this.pub_array = localBuffer.getString();
      }
      localSignatureDSA.setPubKey(this.pub_array, this.P_array, this.Q_array, this.G_array);
      return localSignatureDSA;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  boolean parse(byte[] paramArrayOfByte)
  {
    int i = 1;
    boolean bool;
    try
    {
      if (this.vendor == i)
      {
        if (paramArrayOfByte[0] == 48)
          break label1003;
        Buffer localBuffer2 = new Buffer(paramArrayOfByte);
        localBuffer2.getInt();
        this.P_array = localBuffer2.getMPIntBits();
        this.G_array = localBuffer2.getMPIntBits();
        this.Q_array = localBuffer2.getMPIntBits();
        this.pub_array = localBuffer2.getMPIntBits();
        this.prv_array = localBuffer2.getMPIntBits();
        if (this.P_array != null)
        {
          this.key_size = new BigInteger(this.P_array).bitLength();
          return i;
        }
      }
      else
      {
        if (this.vendor == 2)
        {
          Buffer localBuffer1 = new Buffer(paramArrayOfByte);
          localBuffer1.skip(paramArrayOfByte.length);
          try
          {
            this.prv_array = localBuffer1.getBytes(1, "")[0];
            return i;
          }
          catch (JSchException localJSchException)
          {
            return false;
          }
        }
        if (paramArrayOfByte[0] != 48)
          return false;
        int j = 0 + 1;
        int k = j + 1;
        int m = 0xFF & paramArrayOfByte[j];
        if ((m & 0x80) != 0)
        {
          int i54 = m & 0x7F;
          int i55 = 0;
          int i56 = i54;
          while (true)
          {
            int i57 = i56 - 1;
            if (i56 <= 0)
              break;
            int i58 = i55 << 8;
            int i59 = k + 1;
            i55 = i58 + (0xFF & paramArrayOfByte[k]);
            i56 = i57;
            k = i59;
          }
        }
        int n = k;
        if (paramArrayOfByte[n] != 2)
          return false;
        int i1 = n + 1;
        int i2 = i1 + 1;
        int i3 = 0xFF & paramArrayOfByte[i1];
        if ((i3 & 0x80) != 0)
        {
          int i49 = i3 & 0x7F;
          i3 = 0;
          int i50 = i49;
          while (true)
          {
            int i51 = i50 - 1;
            if (i50 <= 0)
              break;
            int i52 = i3 << 8;
            int i53 = i2 + 1;
            i3 = i52 + (0xFF & paramArrayOfByte[i2]);
            i50 = i51;
            i2 = i53;
          }
        }
        int i4 = 1 + (i3 + i2);
        int i5 = i4 + 1;
        int i6 = 0xFF & paramArrayOfByte[i4];
        if ((i6 & 0x80) != 0)
        {
          int i44 = i6 & 0x7F;
          i6 = 0;
          int i45 = i44;
          while (true)
          {
            int i46 = i45 - 1;
            if (i45 <= 0)
              break;
            int i47 = i6 << 8;
            int i48 = i5 + 1;
            i6 = i47 + (0xFF & paramArrayOfByte[i5]);
            i45 = i46;
            i5 = i48;
          }
        }
        int i7 = i5;
        this.P_array = new byte[i6];
        System.arraycopy(paramArrayOfByte, i7, this.P_array, 0, i6);
        int i8 = 1 + (i7 + i6);
        int i9 = i8 + 1;
        int i10 = 0xFF & paramArrayOfByte[i8];
        if ((i10 & 0x80) != 0)
        {
          int i39 = i10 & 0x7F;
          i10 = 0;
          int i40 = i39;
          while (true)
          {
            int i41 = i40 - 1;
            if (i40 <= 0)
              break;
            int i42 = i10 << 8;
            int i43 = i9 + 1;
            i10 = i42 + (0xFF & paramArrayOfByte[i9]);
            i40 = i41;
            i9 = i43;
          }
        }
        int i11 = i9;
        this.Q_array = new byte[i10];
        System.arraycopy(paramArrayOfByte, i11, this.Q_array, 0, i10);
        int i12 = 1 + (i11 + i10);
        int i13 = i12 + 1;
        int i14 = 0xFF & paramArrayOfByte[i12];
        if ((i14 & 0x80) != 0)
        {
          int i34 = i14 & 0x7F;
          i14 = 0;
          int i35 = i34;
          while (true)
          {
            int i36 = i35 - 1;
            if (i35 <= 0)
              break;
            int i37 = i14 << 8;
            int i38 = i13 + 1;
            i14 = i37 + (0xFF & paramArrayOfByte[i13]);
            i35 = i36;
            i13 = i38;
          }
        }
        int i15 = i13;
        this.G_array = new byte[i14];
        System.arraycopy(paramArrayOfByte, i15, this.G_array, 0, i14);
        int i16 = 1 + (i15 + i14);
        int i17 = i16 + 1;
        int i18 = 0xFF & paramArrayOfByte[i16];
        if ((i18 & 0x80) != 0)
        {
          int i29 = i18 & 0x7F;
          i18 = 0;
          int i30 = i29;
          while (true)
          {
            int i31 = i30 - 1;
            if (i30 <= 0)
              break;
            int i32 = i18 << 8;
            int i33 = i17 + 1;
            i18 = i32 + (0xFF & paramArrayOfByte[i17]);
            i30 = i31;
            i17 = i33;
          }
        }
        int i19 = i17;
        this.pub_array = new byte[i18];
        System.arraycopy(paramArrayOfByte, i19, this.pub_array, 0, i18);
        int i20 = 1 + (i19 + i18);
        int i21 = i20 + 1;
        int i22 = 0xFF & paramArrayOfByte[i20];
        if ((i22 & 0x80) != 0)
        {
          int i24 = i22 & 0x7F;
          i22 = 0;
          int i25 = i24;
          while (true)
          {
            int i26 = i25 - 1;
            if (i25 <= 0)
              break;
            int i27 = i22 << 8;
            int i28 = i21 + 1;
            i22 = i27 + (0xFF & paramArrayOfByte[i21]);
            i25 = i26;
            i21 = i28;
          }
        }
        int i23 = i21;
        this.prv_array = new byte[i22];
        System.arraycopy(paramArrayOfByte, i23, this.prv_array, 0, i22);
        (i23 + i22);
        if (this.P_array != null)
        {
          this.key_size = new BigInteger(this.P_array).bitLength();
          return i;
        }
      }
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
    label1003: return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.KeyPairDSA
 * JD-Core Version:    0.6.2
 */