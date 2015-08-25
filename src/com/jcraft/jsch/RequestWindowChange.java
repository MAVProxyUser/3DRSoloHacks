package com.jcraft.jsch;

class RequestWindowChange extends Request
{
  int height_pixels = 480;
  int height_rows = 24;
  int width_columns = 80;
  int width_pixels = 640;

  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("window-change"));
    if (waitForReply());
    for (int i = 1; ; i = 0)
    {
      localBuffer.putByte((byte)i);
      localBuffer.putInt(this.width_columns);
      localBuffer.putInt(this.height_rows);
      localBuffer.putInt(this.width_pixels);
      localBuffer.putInt(this.height_pixels);
      write(localPacket);
      return;
    }
  }

  void setSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.width_columns = paramInt1;
    this.height_rows = paramInt2;
    this.width_pixels = paramInt3;
    this.height_pixels = paramInt4;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestWindowChange
 * JD-Core Version:    0.6.2
 */