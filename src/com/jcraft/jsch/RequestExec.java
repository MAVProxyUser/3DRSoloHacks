package com.jcraft.jsch;

class RequestExec extends Request
{
  private byte[] command = new byte[0];

  RequestExec(byte[] paramArrayOfByte)
  {
    this.command = paramArrayOfByte;
  }

  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("exec"));
    if (waitForReply());
    for (int i = 1; ; i = 0)
    {
      localBuffer.putByte((byte)i);
      localBuffer.checkFreeSize(4 + this.command.length);
      localBuffer.putString(this.command);
      write(localPacket);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestExec
 * JD-Core Version:    0.6.2
 */