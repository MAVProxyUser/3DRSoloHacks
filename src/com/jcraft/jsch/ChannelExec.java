package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelExec extends ChannelSession
{
  byte[] command = new byte[0];

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

  public void setCommand(String paramString)
  {
    this.command = Util.str2byte(paramString);
  }

  public void setCommand(byte[] paramArrayOfByte)
  {
    this.command = paramArrayOfByte;
  }

  public void setErrStream(OutputStream paramOutputStream)
  {
    setExtOutputStream(paramOutputStream);
  }

  public void setErrStream(OutputStream paramOutputStream, boolean paramBoolean)
  {
    setExtOutputStream(paramOutputStream, paramBoolean);
  }

  public void start()
    throws JSchException
  {
    Session localSession = getSession();
    try
    {
      sendRequests();
      new RequestExec(this.command).request(localSession, this);
      if (this.io.in != null)
      {
        this.thread = new Thread(this);
        this.thread.setName("Exec thread " + localSession.getHost());
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
        throw new JSchException("ChannelExec", localException);
    }
    throw new JSchException("ChannelExec");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelExec
 * JD-Core Version:    0.6.2
 */