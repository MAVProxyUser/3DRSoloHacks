package com.jcraft.jsch;

class RequestX11 extends Request
{
  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("x11-req"));
    if (waitForReply());
    for (int i = 1; ; i = 0)
    {
      localBuffer.putByte((byte)i);
      localBuffer.putByte((byte)0);
      localBuffer.putString(Util.str2byte("MIT-MAGIC-COOKIE-1"));
      localBuffer.putString(ChannelX11.getFakedCookie(paramSession));
      localBuffer.putInt(0);
      write(localPacket);
      paramSession.x11_forwarding = true;
      return;
    }
  }

  public void setCookie(String paramString)
  {
    ChannelX11.cookie = Util.str2byte(paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestX11
 * JD-Core Version:    0.6.2
 */