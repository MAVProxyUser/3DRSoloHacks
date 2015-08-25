package com.MAVLink.Messages;

import com.MAVLink.MAVLinkPacket;

public class MAVLinkStats
{
  public int crcErrorCount;
  private int lastPacketSeq;
  public int lostPacketCount;
  public int receivedPacketCount;

  private void advanceLastPacketSequence()
  {
    this.lastPacketSeq = (0xFF & 1 + this.lastPacketSeq);
  }

  private boolean hasLostPackets(MAVLinkPacket paramMAVLinkPacket)
  {
    return (this.lastPacketSeq > 0) && (paramMAVLinkPacket.seq != this.lastPacketSeq);
  }

  private void updateLostPacketCount(MAVLinkPacket paramMAVLinkPacket)
  {
    if (paramMAVLinkPacket.seq - this.lastPacketSeq < 0);
    for (int i = 255 + (paramMAVLinkPacket.seq - this.lastPacketSeq); ; i = paramMAVLinkPacket.seq - this.lastPacketSeq)
    {
      this.lostPacketCount = (i + this.lostPacketCount);
      return;
    }
  }

  public void crcError()
  {
    this.crcErrorCount = (1 + this.crcErrorCount);
  }

  public void mavlinkResetStats()
  {
    this.lastPacketSeq = -1;
    this.lostPacketCount = 0;
    this.crcErrorCount = 0;
    this.receivedPacketCount = 0;
  }

  public void newPacket(MAVLinkPacket paramMAVLinkPacket)
  {
    advanceLastPacketSequence();
    if (hasLostPackets(paramMAVLinkPacket))
      updateLostPacketCount(paramMAVLinkPacket);
    this.lastPacketSeq = paramMAVLinkPacket.seq;
    this.receivedPacketCount = (1 + this.receivedPacketCount);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.Messages.MAVLinkStats
 * JD-Core Version:    0.6.2
 */