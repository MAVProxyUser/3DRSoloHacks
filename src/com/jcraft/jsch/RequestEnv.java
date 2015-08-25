package com.jcraft.jsch;

class RequestEnv extends Request
{
  byte[] name = new byte[0];
  byte[] value = new byte[0];

  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("env"));
    if (waitForReply());
    for (int i = 1; ; i = 0)
    {
      localBuffer.putByte((byte)i);
      localBuffer.putString(this.name);
      localBuffer.putString(this.value);
      write(localPacket);
      return;
    }
  }

  void setEnv(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.name = paramArrayOfByte1;
    this.value = paramArrayOfByte2;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestEnv
 * JD-Core Version:    0.6.2
 */