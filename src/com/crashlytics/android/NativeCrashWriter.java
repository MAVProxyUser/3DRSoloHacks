package com.crashlytics.android;

import com.crashlytics.android.internal.models.BinaryImageData;
import com.crashlytics.android.internal.models.CustomAttributeData;
import com.crashlytics.android.internal.models.SessionEventData;
import com.crashlytics.android.internal.models.SignalData;
import com.crashlytics.android.internal.models.ThreadData;
import com.crashlytics.android.internal.models.ThreadData.FrameData;
import java.io.IOException;

class NativeCrashWriter
{
  private static final SignalData DEFAULT_SIGNAL = new SignalData("", "", 0L);
  private static final NativeCrashWriter.BinaryImageMessage[] EMPTY_BINARY_IMAGE_MESSAGES = new NativeCrashWriter.BinaryImageMessage[0];
  private static final ProtobufMessage[] EMPTY_CHILDREN = new ProtobufMessage[0];
  private static final NativeCrashWriter.CustomAttributeMessage[] EMPTY_CUSTOM_ATTRIBUTE_MESSAGES = new NativeCrashWriter.CustomAttributeMessage[0];
  private static final NativeCrashWriter.FrameMessage[] EMPTY_FRAME_MESSAGES;
  private static final NativeCrashWriter.ThreadMessage[] EMPTY_THREAD_MESSAGES = new NativeCrashWriter.ThreadMessage[0];
  static final String NDK_CRASH_TYPE = "ndk-crash";

  static
  {
    EMPTY_FRAME_MESSAGES = new NativeCrashWriter.FrameMessage[0];
  }

  private static NativeCrashWriter.RepeatedMessage createBinaryImagesMessage(BinaryImageData[] paramArrayOfBinaryImageData)
  {
    if (paramArrayOfBinaryImageData != null);
    for (NativeCrashWriter.BinaryImageMessage[] arrayOfBinaryImageMessage = new NativeCrashWriter.BinaryImageMessage[paramArrayOfBinaryImageData.length]; ; arrayOfBinaryImageMessage = EMPTY_BINARY_IMAGE_MESSAGES)
      for (int i = 0; i < arrayOfBinaryImageMessage.length; i++)
        arrayOfBinaryImageMessage[i] = new NativeCrashWriter.BinaryImageMessage(paramArrayOfBinaryImageData[i]);
    return new NativeCrashWriter.RepeatedMessage(arrayOfBinaryImageMessage);
  }

  private static NativeCrashWriter.RepeatedMessage createCustomAttributesMessage(CustomAttributeData[] paramArrayOfCustomAttributeData)
  {
    if (paramArrayOfCustomAttributeData != null);
    for (NativeCrashWriter.CustomAttributeMessage[] arrayOfCustomAttributeMessage = new NativeCrashWriter.CustomAttributeMessage[paramArrayOfCustomAttributeData.length]; ; arrayOfCustomAttributeMessage = EMPTY_CUSTOM_ATTRIBUTE_MESSAGES)
      for (int i = 0; i < arrayOfCustomAttributeMessage.length; i++)
        arrayOfCustomAttributeMessage[i] = new NativeCrashWriter.CustomAttributeMessage(paramArrayOfCustomAttributeData[i]);
    return new NativeCrashWriter.RepeatedMessage(arrayOfCustomAttributeMessage);
  }

  private static NativeCrashWriter.RepeatedMessage createFramesMessage(ThreadData.FrameData[] paramArrayOfFrameData)
  {
    if (paramArrayOfFrameData != null);
    for (NativeCrashWriter.FrameMessage[] arrayOfFrameMessage = new NativeCrashWriter.FrameMessage[paramArrayOfFrameData.length]; ; arrayOfFrameMessage = EMPTY_FRAME_MESSAGES)
      for (int i = 0; i < arrayOfFrameMessage.length; i++)
        arrayOfFrameMessage[i] = new NativeCrashWriter.FrameMessage(paramArrayOfFrameData[i]);
    return new NativeCrashWriter.RepeatedMessage(arrayOfFrameMessage);
  }

  private static NativeCrashWriter.RepeatedMessage createThreadsMessage(ThreadData[] paramArrayOfThreadData)
  {
    if (paramArrayOfThreadData != null);
    for (NativeCrashWriter.ThreadMessage[] arrayOfThreadMessage = new NativeCrashWriter.ThreadMessage[paramArrayOfThreadData.length]; ; arrayOfThreadMessage = EMPTY_THREAD_MESSAGES)
      for (int i = 0; i < arrayOfThreadMessage.length; i++)
      {
        ThreadData localThreadData = paramArrayOfThreadData[i];
        arrayOfThreadMessage[i] = new NativeCrashWriter.ThreadMessage(localThreadData, createFramesMessage(localThreadData.frames));
      }
    return new NativeCrashWriter.RepeatedMessage(arrayOfThreadMessage);
  }

  private static NativeCrashWriter.EventMessage readCrashEventData(SessionEventData paramSessionEventData)
    throws IOException
  {
    if (paramSessionEventData.signal != null);
    for (SignalData localSignalData = paramSessionEventData.signal; ; localSignalData = DEFAULT_SIGNAL)
    {
      NativeCrashWriter.ApplicationMessage localApplicationMessage = new NativeCrashWriter.ApplicationMessage(new NativeCrashWriter.ExecutionMessage(new NativeCrashWriter.SignalMessage(localSignalData), createThreadsMessage(paramSessionEventData.threads), createBinaryImagesMessage(paramSessionEventData.binaryImages)), createCustomAttributesMessage(paramSessionEventData.customAttributes));
      NativeCrashWriter.DeviceMessage localDeviceMessage = new NativeCrashWriter.DeviceMessage();
      return new NativeCrashWriter.EventMessage(paramSessionEventData.timestamp, "ndk-crash", localApplicationMessage, localDeviceMessage);
    }
  }

  public static void writeNativeCrash(SessionEventData paramSessionEventData, CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    readCrashEventData(paramSessionEventData).write(paramCodedOutputStream);
  }

  private static abstract class ProtobufMessage
  {
    private final ProtobufMessage[] children;
    private final int tag;

    public ProtobufMessage(int paramInt, ProtobufMessage[] paramArrayOfProtobufMessage)
    {
      this.tag = paramInt;
      if (paramArrayOfProtobufMessage != null);
      while (true)
      {
        this.children = paramArrayOfProtobufMessage;
        return;
        paramArrayOfProtobufMessage = NativeCrashWriter.EMPTY_CHILDREN;
      }
    }

    public int getPropertiesSize()
    {
      return 0;
    }

    public int getSize()
    {
      int i = getSizeNoTag();
      return i + CodedOutputStream.computeRawVarint32Size(i) + CodedOutputStream.computeTagSize(this.tag);
    }

    public int getSizeNoTag()
    {
      int i = getPropertiesSize();
      ProtobufMessage[] arrayOfProtobufMessage = this.children;
      int j = arrayOfProtobufMessage.length;
      for (int k = 0; k < j; k++)
        i += arrayOfProtobufMessage[k].getSize();
      return i;
    }

    public void write(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeTag(this.tag, 2);
      paramCodedOutputStream.writeRawVarint32(getSizeNoTag());
      writeProperties(paramCodedOutputStream);
      ProtobufMessage[] arrayOfProtobufMessage = this.children;
      int i = arrayOfProtobufMessage.length;
      for (int j = 0; j < i; j++)
        arrayOfProtobufMessage[j].write(paramCodedOutputStream);
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.NativeCrashWriter
 * JD-Core Version:    0.6.2
 */