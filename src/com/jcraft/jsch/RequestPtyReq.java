package com.jcraft.jsch;

class RequestPtyReq extends Request
{
  private int tcol = 80;
  private byte[] terminal_mode = Util.empty;
  private int thp = 480;
  private int trow = 24;
  private String ttype = "vt100";
  private int twp = 640;

  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("pty-req"));
    if (waitForReply());
    for (int i = 1; ; i = 0)
    {
      localBuffer.putByte((byte)i);
      localBuffer.putString(Util.str2byte(this.ttype));
      localBuffer.putInt(this.tcol);
      localBuffer.putInt(this.trow);
      localBuffer.putInt(this.twp);
      localBuffer.putInt(this.thp);
      localBuffer.putString(this.terminal_mode);
      write(localPacket);
      return;
    }
  }

  void setCode(String paramString)
  {
  }

  void setTSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.tcol = paramInt1;
    this.trow = paramInt2;
    this.twp = paramInt3;
    this.thp = paramInt4;
  }

  void setTType(String paramString)
  {
    this.ttype = paramString;
  }

  void setTerminalMode(byte[] paramArrayOfByte)
  {
    this.terminal_mode = paramArrayOfByte;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestPtyReq
 * JD-Core Version:    0.6.2
 */