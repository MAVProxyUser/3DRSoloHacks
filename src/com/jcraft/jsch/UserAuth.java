package com.jcraft.jsch;

public abstract class UserAuth
{
  protected static final int SSH_MSG_USERAUTH_BANNER = 53;
  protected static final int SSH_MSG_USERAUTH_FAILURE = 51;
  protected static final int SSH_MSG_USERAUTH_INFO_REQUEST = 60;
  protected static final int SSH_MSG_USERAUTH_INFO_RESPONSE = 61;
  protected static final int SSH_MSG_USERAUTH_PK_OK = 60;
  protected static final int SSH_MSG_USERAUTH_REQUEST = 50;
  protected static final int SSH_MSG_USERAUTH_SUCCESS = 52;
  protected Buffer buf;
  protected Packet packet;
  protected UserInfo userinfo;
  protected String username;

  public boolean start(Session paramSession)
    throws Exception
  {
    this.userinfo = paramSession.getUserInfo();
    this.packet = paramSession.packet;
    this.buf = this.packet.getBuffer();
    this.username = paramSession.getUserName();
    return true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.UserAuth
 * JD-Core Version:    0.6.2
 */