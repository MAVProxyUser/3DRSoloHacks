package com.jcraft.jsch;

class RequestAgentForwarding extends Request
{
  public void request(Session paramSession, Channel paramChannel)
    throws Exception
  {
    super.request(paramSession, paramChannel);
    setReply(false);
    Buffer localBuffer = new Buffer();
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)98);
    localBuffer.putInt(paramChannel.getRecipient());
    localBuffer.putString(Util.str2byte("auth-agent-req@openssh.com"));
    boolean bool = waitForReply();
    int i = 0;
    if (bool)
      i = 1;
    localBuffer.putByte((byte)i);
    write(localPacket);
    paramSession.agent_forwarding = true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.RequestAgentForwarding
 * JD-Core Version:    0.6.2
 */