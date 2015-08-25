package com.jcraft.jsch;

class UserAuthNone extends UserAuth
{
  private static final int SSH_MSG_SERVICE_ACCEPT = 6;
  private String methods = null;

  String getMethods()
  {
    return this.methods;
  }

  public boolean start(Session paramSession)
    throws Exception
  {
    super.start(paramSession);
    this.packet.reset();
    this.buf.putByte((byte)5);
    this.buf.putString(Util.str2byte("ssh-userauth"));
    paramSession.write(this.packet);
    if (JSch.getLogger().isEnabled(1))
      JSch.getLogger().log(1, "SSH_MSG_SERVICE_REQUEST sent");
    this.buf = paramSession.read(this.buf);
    if (this.buf.getCommand() == 6);
    for (int i = 1; ; i = 0)
    {
      if (JSch.getLogger().isEnabled(1))
        JSch.getLogger().log(1, "SSH_MSG_SERVICE_ACCEPT received");
      if (i != 0)
        break;
      return false;
    }
    byte[] arrayOfByte1 = Util.str2byte(this.username);
    this.packet.reset();
    this.buf.putByte((byte)50);
    this.buf.putString(arrayOfByte1);
    this.buf.putString(Util.str2byte("ssh-connection"));
    this.buf.putString(Util.str2byte("none"));
    paramSession.write(this.packet);
    int j;
    while (true)
    {
      this.buf = paramSession.read(this.buf);
      j = 0xFF & this.buf.getCommand();
      if (j == 52)
        return true;
      if (j != 53)
        break;
      this.buf.getInt();
      this.buf.getByte();
      this.buf.getByte();
      byte[] arrayOfByte3 = this.buf.getString();
      this.buf.getString();
      String str = Util.byte2str(arrayOfByte3);
      if (this.userinfo != null)
        try
        {
          this.userinfo.showMessage(str);
        }
        catch (RuntimeException localRuntimeException)
        {
        }
    }
    if (j == 51)
    {
      this.buf.getInt();
      this.buf.getByte();
      this.buf.getByte();
      byte[] arrayOfByte2 = this.buf.getString();
      this.buf.getByte();
      this.methods = Util.byte2str(arrayOfByte2);
      return false;
    }
    throw new JSchException("USERAUTH fail (" + j + ")");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserAuthNone
 * JD-Core Version:    0.6.2
 */