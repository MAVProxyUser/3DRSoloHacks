package com.crashlytics.android;

import android.os.Process;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

class CLSUUID
{
  private static String _clsId;
  private static final AtomicLong _sequenceNumber = new AtomicLong(0L);

  public CLSUUID(IdManager paramIdManager)
  {
    byte[] arrayOfByte = new byte[10];
    populateTime(arrayOfByte);
    populateSequenceNumber(arrayOfByte);
    populatePID(arrayOfByte);
    String str1 = CommonUtils.sha1(paramIdManager.getAppInstallIdentifier());
    String str2 = CommonUtils.hexify(arrayOfByte);
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = str2.substring(0, 12);
    arrayOfObject[1] = str2.substring(12, 16);
    arrayOfObject[2] = str2.subSequence(16, 20);
    arrayOfObject[3] = str1.substring(0, 12);
    _clsId = String.format(localLocale, "%s-%s-%s-%s", arrayOfObject).toUpperCase(Locale.US);
  }

  private static byte[] convertLongToFourByteBuffer(long paramLong)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
    localByteBuffer.putInt((int)paramLong);
    localByteBuffer.order(ByteOrder.BIG_ENDIAN);
    localByteBuffer.position(0);
    return localByteBuffer.array();
  }

  private static byte[] convertLongToTwoByteBuffer(long paramLong)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(2);
    localByteBuffer.putShort((short)(int)paramLong);
    localByteBuffer.order(ByteOrder.BIG_ENDIAN);
    localByteBuffer.position(0);
    return localByteBuffer.array();
  }

  private void populatePID(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = convertLongToTwoByteBuffer(Integer.valueOf(Process.myPid()).shortValue());
    paramArrayOfByte[8] = arrayOfByte[0];
    paramArrayOfByte[9] = arrayOfByte[1];
  }

  private void populateSequenceNumber(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = convertLongToTwoByteBuffer(_sequenceNumber.incrementAndGet());
    paramArrayOfByte[6] = arrayOfByte[0];
    paramArrayOfByte[7] = arrayOfByte[1];
  }

  private void populateTime(byte[] paramArrayOfByte)
  {
    long l1 = new Date().getTime();
    long l2 = l1 / 1000L;
    long l3 = l1 % 1000L;
    byte[] arrayOfByte1 = convertLongToFourByteBuffer(l2);
    paramArrayOfByte[0] = arrayOfByte1[0];
    paramArrayOfByte[1] = arrayOfByte1[1];
    paramArrayOfByte[2] = arrayOfByte1[2];
    paramArrayOfByte[3] = arrayOfByte1[3];
    byte[] arrayOfByte2 = convertLongToTwoByteBuffer(l3);
    paramArrayOfByte[4] = arrayOfByte2[0];
    paramArrayOfByte[5] = arrayOfByte2[1];
  }

  public String toString()
  {
    return _clsId;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.CLSUUID
 * JD-Core Version:    0.6.2
 */