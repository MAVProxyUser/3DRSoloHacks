package com.o3dr.solo.android.service.sololink.tlv;

import android.util.Log;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TLVMessageParser
{
  private static final String TAG = TLVMessageParser.class.getSimpleName();

  public static TLVPacket parseTLVPacket(ByteBuffer paramByteBuffer)
  {
    if ((paramByteBuffer == null) || (paramByteBuffer.limit() <= 0))
      return null;
    ByteOrder localByteOrder = paramByteBuffer.order();
    int i = -1;
    try
    {
      paramByteBuffer.order(TLVPacket.TLV_BYTE_ORDER);
      i = paramByteBuffer.getInt();
      int j = paramByteBuffer.getInt();
      String str = TAG;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = Integer.valueOf(j);
      Log.d(str, String.format("Received message %d of with value of length %d", arrayOfObject));
      switch (i)
      {
      default:
        return null;
      case 0:
      case 1:
        int i2 = paramByteBuffer.getInt();
        if (i == 0)
        {
          SoloMessageShotGetter localSoloMessageShotGetter = new SoloMessageShotGetter(i2);
          return localSoloMessageShotGetter;
        }
        SoloMessageShotSetter localSoloMessageShotSetter = new SoloMessageShotSetter(i2);
        return localSoloMessageShotSetter;
      case 2:
        SoloMessageLocation localSoloMessageLocation = new SoloMessageLocation(paramByteBuffer.getDouble(), paramByteBuffer.getDouble(), paramByteBuffer.getFloat());
        return localSoloMessageLocation;
      case 3:
        SoloMessageRecordPosition localSoloMessageRecordPosition = new SoloMessageRecordPosition();
        return localSoloMessageRecordPosition;
      case 4:
        SoloCableCamOptions localSoloCableCamOptions = new SoloCableCamOptions(paramByteBuffer.getShort(), paramByteBuffer.getShort(), paramByteBuffer.getFloat());
        return localSoloCableCamOptions;
      case 5:
      case 6:
        int k = paramByteBuffer.getInt();
        int m = paramByteBuffer.getInt();
        int n = paramByteBuffer.getInt();
        int i1 = paramByteBuffer.getInt();
        if (i == 5)
        {
          SoloButtonSettingGetter localSoloButtonSettingGetter = new SoloButtonSettingGetter(k, m, n, i1);
          return localSoloButtonSettingGetter;
        }
        SoloButtonSettingSetter localSoloButtonSettingSetter = new SoloButtonSettingSetter(k, m, n, i1);
        return localSoloButtonSettingSetter;
      case 19:
        SoloFollowOptions localSoloFollowOptions = new SoloFollowOptions(paramByteBuffer.getFloat(), paramByteBuffer.getInt());
        return localSoloFollowOptions;
      case 20:
        SoloShotOptions localSoloShotOptions = new SoloShotOptions(paramByteBuffer.getFloat());
        return localSoloShotOptions;
      case 21:
        SoloShotError localSoloShotError = new SoloShotError(paramByteBuffer.getInt());
        return localSoloShotError;
      case 1000:
        byte[] arrayOfByte = new byte[j];
        paramByteBuffer.get(arrayOfByte);
        SoloMessageShotManagerError localSoloMessageShotManagerError = new SoloMessageShotManagerError(new String(arrayOfByte));
        return localSoloMessageShotManagerError;
      case 1001:
        SoloCableCamWaypoint localSoloCableCamWaypoint = new SoloCableCamWaypoint(paramByteBuffer.getDouble(), paramByteBuffer.getDouble(), paramByteBuffer.getFloat(), paramByteBuffer.getFloat(), paramByteBuffer.getFloat());
        return localSoloCableCamWaypoint;
      case 2003:
      }
      ArtooMessageInputReport localArtooMessageInputReport = new ArtooMessageInputReport(paramByteBuffer.getDouble(), paramByteBuffer.getShort(), paramByteBuffer.getShort(), paramByteBuffer.getShort());
      return localArtooMessageInputReport;
    }
    catch (BufferUnderflowException localBufferUnderflowException)
    {
      Log.e(TAG, "Invalid data for tlv packet of type " + i);
      return null;
    }
    finally
    {
      paramByteBuffer.order(localByteOrder);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.TLVMessageParser
 * JD-Core Version:    0.6.2
 */