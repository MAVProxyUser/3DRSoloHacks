package com.o3dr.solo.android.service.sololink.tlv;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class TLVPacket
{
  public static final ByteOrder TLV_BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
  private final ByteBuffer byteBuffer;
  private final int messageLength;
  private final int messageType;

  public TLVPacket(int paramInt1, int paramInt2)
  {
    this.messageType = paramInt1;
    this.messageLength = paramInt2;
    this.byteBuffer = ByteBuffer.allocate(8 + this.messageLength);
    this.byteBuffer.order(TLV_BYTE_ORDER);
  }

  public final int getMessageType()
  {
    return this.messageType;
  }

  protected abstract void getMessageValue(ByteBuffer paramByteBuffer);

  public final byte[] toBytes()
  {
    this.byteBuffer.clear();
    this.byteBuffer.putInt(this.messageType);
    this.byteBuffer.putInt(this.messageLength);
    getMessageValue(this.byteBuffer);
    byte[] arrayOfByte = new byte[this.byteBuffer.position()];
    this.byteBuffer.rewind();
    this.byteBuffer.get(arrayOfByte);
    return arrayOfByte;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.TLVPacket
 * JD-Core Version:    0.6.2
 */