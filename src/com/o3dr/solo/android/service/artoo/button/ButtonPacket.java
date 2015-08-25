package com.o3dr.solo.android.service.artoo.button;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ButtonPacket
  implements Parcelable
{
  public static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
  public static final Parcelable.Creator<ButtonPacket> CREATOR = new Parcelable.Creator()
  {
    public ButtonPacket createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ButtonPacket(paramAnonymousParcel, null);
    }

    public ButtonPacket[] newArray(int paramAnonymousInt)
    {
      return new ButtonPacket[paramAnonymousInt];
    }
  };
  private static final String TAG = ButtonPacket.class.getSimpleName();
  private byte buttonId = -1;
  private final ByteBuffer byteBuffer;
  private byte eventType = -1;
  private short pressedMask = -1;
  private double timestamp = -1.0D;

  private ButtonPacket(Parcel paramParcel)
  {
    this.timestamp = paramParcel.readDouble();
    this.eventType = paramParcel.readByte();
    this.buttonId = paramParcel.readByte();
    this.pressedMask = ((Short)paramParcel.readValue(Short.TYPE.getClassLoader())).shortValue();
    this.byteBuffer = ByteBuffer.allocate(12);
    this.byteBuffer.order(BYTE_ORDER);
  }

  public ButtonPacket(short paramShort, byte paramByte1, byte paramByte2, double paramDouble)
  {
    this.pressedMask = paramShort;
    this.buttonId = paramByte1;
    this.eventType = paramByte2;
    this.timestamp = paramDouble;
    this.byteBuffer = ByteBuffer.allocate(12);
    this.byteBuffer.order(BYTE_ORDER);
  }

  public static ButtonPacket parseButtonPacket(ByteBuffer paramByteBuffer)
  {
    if ((paramByteBuffer == null) || (paramByteBuffer.limit() <= 0))
      return null;
    ByteOrder localByteOrder = paramByteBuffer.order();
    try
    {
      paramByteBuffer.order(BYTE_ORDER);
      double d = paramByteBuffer.getDouble();
      byte b1 = paramByteBuffer.get();
      byte b2 = paramByteBuffer.get();
      ButtonPacket localButtonPacket = new ButtonPacket(paramByteBuffer.getShort(), b1, b2, d);
      return localButtonPacket;
    }
    catch (BufferUnderflowException localBufferUnderflowException)
    {
      Log.e(TAG, "Invalid data for button packet");
      return null;
    }
    finally
    {
      paramByteBuffer.order(localByteOrder);
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public byte getButtonId()
  {
    return this.buttonId;
  }

  public final int getEventType()
  {
    return this.eventType;
  }

  public short getPressedMask()
  {
    return this.pressedMask;
  }

  public double getTimestamp()
  {
    return this.timestamp;
  }

  public final byte[] toBytes()
  {
    this.byteBuffer.clear();
    this.byteBuffer.putDouble(this.timestamp);
    this.byteBuffer.put(this.buttonId);
    this.byteBuffer.put(this.eventType);
    this.byteBuffer.putShort(this.pressedMask);
    byte[] arrayOfByte = new byte[this.byteBuffer.position()];
    this.byteBuffer.rewind();
    this.byteBuffer.get(arrayOfByte);
    return arrayOfByte;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.timestamp);
    paramParcel.writeByte(this.eventType);
    paramParcel.writeByte(this.buttonId);
    paramParcel.writeValue(Short.valueOf(this.pressedMask));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.artoo.button.ButtonPacket
 * JD-Core Version:    0.6.2
 */