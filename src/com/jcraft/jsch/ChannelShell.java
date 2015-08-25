package com.jcraft.jsch;

public class ChannelShell extends ChannelSession
{
  ChannelShell()
  {
    this.pty = true;
  }

  void init()
    throws JSchException
  {
    this.io.setInputStream(getSession().in);
    this.io.setOutputStream(getSession().out);
  }

  public void start()
    throws JSchException
  {
    Session localSession = getSession();
    try
    {
      sendRequests();
      new RequestShell().request(localSession, this);
      if (this.io.in != null)
      {
        this.thread = new Thread(this);
        this.thread.setName("Shell for " + localSession.host);
        if (localSession.daemon_thread)
          this.thread.setDaemon(localSession.daemon_thread);
        this.thread.start();
      }
      return;
    }
    catch (Exception localException)
    {
      if ((localException instanceof JSchException))
        throw ((JSchException)localException);
      if ((localException instanceof Throwable))
        throw new JSchException("ChannelShell", localException);
    }
    throw new JSchException("ChannelShell");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelShell
 * JD-Core Version:    0.6.2
 */