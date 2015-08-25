package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelSubsystem extends ChannelSession
{
  boolean pty = false;
  String subsystem = "";
  boolean want_reply = true;
  boolean xforwading = false;

  public InputStream getErrStream()
    throws IOException
  {
    return getExtInputStream();
  }

  void init()
    throws JSchException
  {
    this.io.setInputStream(getSession().in);
    this.io.setOutputStream(getSession().out);
  }

  public void setErrStream(OutputStream paramOutputStream)
  {
    setExtOutputStream(paramOutputStream);
  }

  public void setPty(boolean paramBoolean)
  {
    this.pty = paramBoolean;
  }

  public void setSubsystem(String paramString)
  {
    this.subsystem = paramString;
  }

  public void setWantReply(boolean paramBoolean)
  {
    this.want_reply = paramBoolean;
  }

  public void setXForwarding(boolean paramBoolean)
  {
    this.xforwading = paramBoolean;
  }

  public void start()
    throws JSchException
  {
    Session localSession = getSession();
    try
    {
      if (this.xforwading)
        new RequestX11().request(localSession, this);
      if (this.pty)
        new RequestPtyReq().request(localSession, this);
      ((RequestSubsystem)new RequestSubsystem()).request(localSession, this, this.subsystem, this.want_reply);
      if (this.io.in != null)
      {
        this.thread = new Thread(this);
        this.thread.setName("Subsystem for " + localSession.host);
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
        throw new JSchException("ChannelSubsystem", localException);
    }
    throw new JSchException("ChannelSubsystem");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelSubsystem
 * JD-Core Version:    0.6.2
 */