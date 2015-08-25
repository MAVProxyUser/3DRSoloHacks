package com.jcraft.jsch;

class UserAuthPassword extends UserAuth
{
  private final int SSH_MSG_USERAUTH_PASSWD_CHANGEREQ = 60;

  public boolean start(Session paramSession)
    throws Exception
  {
    super.start(paramSession);
    byte[] arrayOfByte1 = paramSession.password;
    String str1 = this.username + "@" + paramSession.host;
    if (paramSession.port != 22)
      str1 = str1 + ":" + paramSession.port;
    label228: 
    do
    {
      while (true)
      {
        try
        {
          int i = paramSession.auth_failures;
          int j = paramSession.max_auth_tries;
          if (i >= j)
          {
            bool = false;
            return bool;
          }
          if (arrayOfByte1 != null)
            break label228;
          UserInfo localUserInfo = this.userinfo;
          if (localUserInfo == null)
          {
            bool = false;
            return false;
          }
          if (!this.userinfo.promptPassword("Password for " + str1))
            throw new JSchAuthCancelException("password");
        }
        finally
        {
          if (arrayOfByte1 != null)
            Util.bzero(arrayOfByte1);
        }
        String str3 = this.userinfo.getPassword();
        if (str3 == null)
          throw new JSchAuthCancelException("password");
        arrayOfByte1 = Util.str2byte(str3);
        byte[] arrayOfByte2 = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte)50);
        this.buf.putString(arrayOfByte2);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("password"));
        this.buf.putByte((byte)0);
        this.buf.putString(arrayOfByte1);
        paramSession.write(this.packet);
        int m;
        while (true)
        {
          this.buf = paramSession.read(this.buf);
          int k = this.buf.getCommand();
          m = k & 0xFF;
          if (m == 52)
          {
            bool = true;
            if (arrayOfByte1 == null)
              break;
            Util.bzero(arrayOfByte1);
            return bool;
          }
          if (m == 53)
          {
            this.buf.getInt();
            this.buf.getByte();
            this.buf.getByte();
            byte[] arrayOfByte6 = this.buf.getString();
            this.buf.getString();
            String str2 = Util.byte2str(arrayOfByte6);
            if (this.userinfo != null)
              this.userinfo.showMessage(str2);
          }
          else
          {
            if (m != 60)
              break label697;
            this.buf.getInt();
            this.buf.getByte();
            this.buf.getByte();
            byte[] arrayOfByte3 = this.buf.getString();
            this.buf.getString();
            if ((this.userinfo == null) || (!(this.userinfo instanceof UIKeyboardInteractive)))
            {
              if (this.userinfo != null)
                this.userinfo.showMessage("Password must be changed.");
              bool = false;
              if (arrayOfByte1 == null)
                break;
              Util.bzero(arrayOfByte1);
              return false;
            }
            UIKeyboardInteractive localUIKeyboardInteractive = (UIKeyboardInteractive)this.userinfo;
            String[] arrayOfString1 = { "New Password: " };
            boolean[] arrayOfBoolean = { false };
            String[] arrayOfString2 = localUIKeyboardInteractive.promptKeyboardInteractive(str1, "Password Change Required", Util.byte2str(arrayOfByte3), arrayOfString1, arrayOfBoolean);
            if (arrayOfString2 == null)
              throw new JSchAuthCancelException("password");
            byte[] arrayOfByte4 = Util.str2byte(arrayOfString2[0]);
            this.packet.reset();
            this.buf.putByte((byte)50);
            this.buf.putString(arrayOfByte2);
            this.buf.putString(Util.str2byte("ssh-connection"));
            this.buf.putString(Util.str2byte("password"));
            this.buf.putByte((byte)1);
            this.buf.putString(arrayOfByte1);
            this.buf.putString(arrayOfByte4);
            Util.bzero(arrayOfByte4);
            paramSession.write(this.packet);
          }
        }
        if (m != 51)
          break;
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        byte[] arrayOfByte5 = this.buf.getString();
        if (this.buf.getByte() != 0)
          throw new JSchPartialAuthException(Util.byte2str(arrayOfByte5));
        paramSession.auth_failures = (1 + paramSession.auth_failures);
        if (arrayOfByte1 != null)
        {
          Util.bzero(arrayOfByte1);
          arrayOfByte1 = null;
        }
      }
      boolean bool = false;
    }
    while (arrayOfByte1 == null);
    label697: Util.bzero(arrayOfByte1);
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserAuthPassword
 * JD-Core Version:    0.6.2
 */