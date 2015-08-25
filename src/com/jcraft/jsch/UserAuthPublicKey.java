package com.jcraft.jsch;

import java.util.Vector;

class UserAuthPublicKey extends UserAuth
{
  public boolean start(Session paramSession)
    throws Exception
  {
    super.start(paramSession);
    byte[] arrayOfByte1;
    int i;
    synchronized (paramSession.getIdentityRepository().getIdentities())
    {
      if (???.size() <= 0)
        return false;
      arrayOfByte1 = Util.str2byte(this.username);
      i = 0;
      if (i >= ???.size())
        break label723;
      if (paramSession.auth_failures >= paramSession.max_auth_tries)
        return false;
    }
    Identity localIdentity = (Identity)???.elementAt(i);
    byte[] arrayOfByte2 = localIdentity.getPublicKeyBlob();
    int j;
    if (arrayOfByte2 != null)
    {
      this.packet.reset();
      this.buf.putByte((byte)50);
      this.buf.putString(arrayOfByte1);
      this.buf.putString(Util.str2byte("ssh-connection"));
      this.buf.putString(Util.str2byte("publickey"));
      this.buf.putByte((byte)0);
      this.buf.putString(Util.str2byte(localIdentity.getAlgName()));
      this.buf.putString(arrayOfByte2);
      paramSession.write(this.packet);
      while (true)
      {
        this.buf = paramSession.read(this.buf);
        j = 0xFF & this.buf.getCommand();
        if ((j == 60) || (j == 51) || (j != 53))
          break;
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        byte[] arrayOfByte9 = this.buf.getString();
        this.buf.getString();
        String str3 = Util.byte2str(arrayOfByte9);
        if (this.userinfo != null)
          this.userinfo.showMessage(str3);
      }
    }
    while (true)
    {
      boolean bool = localIdentity.isEncrypted();
      byte[] arrayOfByte3 = null;
      if (bool)
      {
        arrayOfByte3 = null;
        if (0 == 0)
        {
          if (this.userinfo == null)
            throw new JSchException("USERAUTH fail");
          if ((localIdentity.isEncrypted()) && (!this.userinfo.promptPassphrase("Passphrase for " + localIdentity.getName())))
            throw new JSchAuthCancelException("publickey");
          String str1 = this.userinfo.getPassphrase();
          arrayOfByte3 = null;
          if (str1 != null)
            arrayOfByte3 = Util.str2byte(str1);
        }
      }
      if (((!localIdentity.isEncrypted()) || (arrayOfByte3 != null)) && (localIdentity.setPassphrase(arrayOfByte3)))
        if ((arrayOfByte3 != null) && ((paramSession.getIdentityRepository() instanceof IdentityRepository.Wrapper)))
          ((IdentityRepository.Wrapper)paramSession.getIdentityRepository()).check();
      byte[] arrayOfByte6;
      while (true)
      {
        Util.bzero(arrayOfByte3);
        if (localIdentity.isEncrypted())
          break label958;
        if (arrayOfByte2 == null)
          arrayOfByte2 = localIdentity.getPublicKeyBlob();
        if (arrayOfByte2 == null)
          break label958;
        this.packet.reset();
        this.buf.putByte((byte)50);
        this.buf.putString(arrayOfByte1);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("publickey"));
        this.buf.putByte((byte)1);
        this.buf.putString(Util.str2byte(localIdentity.getAlgName()));
        this.buf.putString(arrayOfByte2);
        byte[] arrayOfByte4 = paramSession.getSessionId();
        int m = arrayOfByte4.length;
        byte[] arrayOfByte5 = new byte[-5 + (m + 4 + this.buf.index)];
        arrayOfByte5[0] = ((byte)(m >>> 24));
        arrayOfByte5[1] = ((byte)(m >>> 16));
        arrayOfByte5[2] = ((byte)(m >>> 8));
        arrayOfByte5[3] = ((byte)m);
        System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 4, m);
        System.arraycopy(this.buf.buffer, 5, arrayOfByte5, m + 4, -5 + this.buf.index);
        arrayOfByte6 = localIdentity.getSignature(arrayOfByte5);
        if (arrayOfByte6 != null)
          break label746;
        label723: return false;
        Util.bzero(arrayOfByte3);
        k--;
        if (k != 0)
          break;
        arrayOfByte3 = null;
      }
      label746: this.buf.putString(arrayOfByte6);
      paramSession.write(this.packet);
      int n;
      while (true)
      {
        this.buf = paramSession.read(this.buf);
        n = 0xFF & this.buf.getCommand();
        if (n == 52)
          return true;
        if (n != 53)
          break;
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        byte[] arrayOfByte7 = this.buf.getString();
        this.buf.getString();
        String str2 = Util.byte2str(arrayOfByte7);
        if (this.userinfo != null)
          this.userinfo.showMessage(str2);
      }
      if (n == 51)
      {
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        byte[] arrayOfByte8 = this.buf.getString();
        if (this.buf.getByte() != 0)
          throw new JSchPartialAuthException(Util.byte2str(arrayOfByte8));
        paramSession.auth_failures = (1 + paramSession.auth_failures);
        break label958;
        if (j == 60)
          break label964;
      }
      label958: i++;
      break;
      label964: int k = 5;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserAuthPublicKey
 * JD-Core Version:    0.6.2
 */