package com.jcraft.jsch;

public class RequestSubsystem extends Request
{
  private String subsystem = null;

  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("subsystem"));
    if (waitForReply());
    for (int i = 1; ; i = 0)
    {
      localBuffer.putByte((byte)i);
      localBuffer.putString(Util.str2byte(this.subsystem));
      write(localPacket);
      return;
    }
  }

  public void request(Session paramSession, Channel paramChannel, String paramString, boolean paramBoolean)
    throws Exception
  {
    setReply(paramBoolean);
    this.subsystem = paramString;
    request(paramSession, paramChannel);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestSubsystem
 * JD-Core Version:    0.6.2
 */