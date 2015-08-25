package com.jcraft.jsch;

abstract class Request
{
  private Channel channel = null;
  private boolean reply = false;
  private Session session = null;

  void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    this.session = paramSession;
    this.channel = paramChannel;
    if (paramChannel.connectTimeout > 0)
      setReply(true);
  }

  void setReply(boolean paramBoolean)
  {
    this.reply = paramBoolean;
  }

  boolean waitForReply()
  {
    return this.reply;
  }

  void write(Packet paramPacket)
    throws Exception
  {
    if (this.reply)
      this.channel.reply = -1;
    this.session.write(paramPacket);
    if (this.reply)
    {
      long l1 = System.currentTimeMillis();
      long l2 = this.channel.connectTimeout;
      while (true)
      {
        if ((this.channel.isConnected()) && (this.channel.reply == -1));
        try
        {
          Thread.sleep(10L);
          label71: if ((l2 > 0L) && (System.currentTimeMillis() - l1 > l2))
          {
            this.channel.reply = 0;
            throw new JSchException("channel request: timeout");
            if (this.channel.reply == 0)
              throw new JSchException("failed to send channel request");
          }
        }
        catch (Exception localException)
        {
          break label71;
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Request
 * JD-Core Version:    0.6.2
 */