package com.jcraft.jsch;

class UserAuthKeyboardInteractive extends UserAuth
{
  public boolean start(Session paramSession)
    throws Exception
  {
    super.start(paramSession);
    if ((this.userinfo != null) && (!(this.userinfo instanceof UIKeyboardInteractive)))
      return false;
    String str1 = this.username + "@" + paramSession.host;
    if (paramSession.port != 22)
      str1 = str1 + ":" + paramSession.port;
    byte[] arrayOfByte1 = paramSession.password;
    int i = 0;
    byte[] arrayOfByte2 = Util.str2byte(this.username);
    int j;
    int k;
    do
    {
      if (paramSession.auth_failures >= paramSession.max_auth_tries)
        return false;
      this.packet.reset();
      this.buf.putByte((byte)50);
      this.buf.putString(arrayOfByte2);
      this.buf.putString(Util.str2byte("ssh-connection"));
      this.buf.putString(Util.str2byte("keyboard-interactive"));
      this.buf.putString(Util.empty);
      this.buf.putString(Util.empty);
      paramSession.write(this.packet);
      j = 1;
      while (true)
      {
        this.buf = paramSession.read(this.buf);
        k = 0xFF & this.buf.getCommand();
        if (k == 52)
          return true;
        if (k != 53)
          break;
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        byte[] arrayOfByte4 = this.buf.getString();
        this.buf.getString();
        String str4 = Util.byte2str(arrayOfByte4);
        if (this.userinfo != null)
          this.userinfo.showMessage(str4);
      }
      if (k != 51)
        break;
      this.buf.getInt();
      this.buf.getByte();
      this.buf.getByte();
      byte[] arrayOfByte3 = this.buf.getString();
      if (this.buf.getByte() != 0)
        throw new JSchPartialAuthException(Util.byte2str(arrayOfByte3));
      if (j != 0)
        return false;
      paramSession.auth_failures = (1 + paramSession.auth_failures);
    }
    while (i == 0);
    throw new JSchAuthCancelException("keyboard-interactive");
    if (k == 60)
    {
      this.buf.getInt();
      this.buf.getByte();
      this.buf.getByte();
      String str2 = Util.byte2str(this.buf.getString());
      String str3 = Util.byte2str(this.buf.getString());
      Util.byte2str(this.buf.getString());
      int m = this.buf.getInt();
      String[] arrayOfString1 = new String[m];
      boolean[] arrayOfBoolean = new boolean[m];
      int n = 0;
      if (n < m)
      {
        arrayOfString1[n] = Util.byte2str(this.buf.getString());
        if (this.buf.getByte() != 0);
        for (int i4 = 1; ; i4 = 0)
        {
          arrayOfBoolean[n] = i4;
          n++;
          break;
        }
      }
      Object localObject = (byte[][])null;
      if ((arrayOfByte1 != null) && (arrayOfString1.length == 1) && (arrayOfBoolean[0] == 0) && (arrayOfString1[0].toLowerCase().indexOf("password:") >= 0))
      {
        localObject = new byte[1][];
        localObject[0] = arrayOfByte1;
        arrayOfByte1 = null;
      }
      while (true)
      {
        this.packet.reset();
        this.buf.putByte((byte)61);
        if ((m <= 0) || ((localObject != null) && (m == localObject.length)))
          break label805;
        if (localObject != null)
          break;
        this.buf.putInt(m);
        for (int i3 = 0; i3 < m; i3++)
          this.buf.putString(Util.empty);
        if (((m > 0) || (str2.length() > 0) || (str3.length() > 0)) && (this.userinfo != null))
        {
          String[] arrayOfString2 = ((UIKeyboardInteractive)this.userinfo).promptKeyboardInteractive(str1, str2, str3, arrayOfString1, arrayOfBoolean);
          if (arrayOfString2 != null)
          {
            localObject = new byte[arrayOfString2.length][];
            for (int i1 = 0; i1 < arrayOfString2.length; i1++)
              localObject[i1] = Util.str2byte(arrayOfString2[i1]);
          }
        }
      }
      this.buf.putInt(0);
      if (localObject == null)
        i = 1;
      while (true)
      {
        paramSession.write(this.packet);
        j = 0;
        break;
        label805: this.buf.putInt(m);
        for (int i2 = 0; i2 < m; i2++)
          this.buf.putString(localObject[i2]);
      }
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserAuthKeyboardInteractive
 * JD-Core Version:    0.6.2
 */