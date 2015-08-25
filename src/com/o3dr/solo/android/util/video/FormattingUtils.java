package com.o3dr.solo.android.util.video;

public class FormattingUtils
{
  private static String formatByte(byte paramByte)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Byte.valueOf(paramByte);
    return String.format("%02X", arrayOfObject);
  }

  private static String formatByteBuffer(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramInt == 0))
    {
      str1 = "";
      return str1;
    }
    String str1 = "\n";
    int i = 0;
    label18: if (i < paramInt)
      if (i % 16 != 0)
        break label82;
    label82: for (String str2 = str1 + "\n"; ; str2 = str1 + " ")
    {
      str1 = str2 + formatByte(paramArrayOfByte[i]);
      i++;
      break label18;
      break;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.video.FormattingUtils
 * JD-Core Version:    0.6.2
 */