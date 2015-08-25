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
  private static final BinaryImageMessage[] EMPTY_BINARY_IMAGE_MESSAGES = new BinaryImageMessage[0];
  private static final ProtobufMessage[] EMPTY_CHILDREN = new ProtobufMessage[0];
  private static final CustomAttributeMessage[] EMPTY_CUSTOM_ATTRIBUTE_MESSAGES = new CustomAttributeMessage[0];
  private static final FrameMessage[] EMPTY_FRAME_MESSAGES;
  private static final ThreadMessage[] EMPTY_THREAD_MESSAGES = new ThreadMessage[0];
  static final String NDK_CRASH_TYPE = "ndk-crash";

  static
  {
    EMPTY_FRAME_MESSAGES = new FrameMessage[0];
  }

  private static RepeatedMessage createBinaryImagesMessage(BinaryImageData[] paramArrayOfBinaryImageData)
  {
    if (paramArrayOfBinaryImageData != null);
    for (BinaryImageMessage[] arrayOfBinaryImageMessage = new BinaryImageMessage[paramArrayOfBinaryImageData.length]; ; arrayOfBinaryImageMessage = EMPTY_BINARY_IMAGE_MESSAGES)
      for (int i = 0; i < arrayOfBinaryImageMessage.length; i++)
        arrayOfBinaryImageMessage[i] = new BinaryImageMessage(paramArrayOfBinaryImageData[i]);
    return new RepeatedMessage(arrayOfBinaryImageMessage);
  }

  private static RepeatedMessage createCustomAttributesMessage(CustomAttributeData[] paramArrayOfCustomAttributeData)
  {
    if (paramArrayOfCustomAttributeData != null);
    for (CustomAttributeMessage[] arrayOfCustomAttributeMessage = new CustomAttributeMessage[paramArrayOfCustomAttributeData.length]; ; arrayOfCustomAttributeMessage = EMPTY_CUSTOM_ATTRIBUTE_MESSAGES)
      for (int i = 0; i < arrayOfCustomAttributeMessage.length; i++)
        arrayOfCustomAttributeMessage[i] = new CustomAttributeMessage(paramArrayOfCustomAttributeData[i]);
    return new RepeatedMessage(arrayOfCustomAttributeMessage);
  }

  private static RepeatedMessage createFramesMessage(ThreadData.FrameData[] paramArrayOfFrameData)
  {
    if (paramArrayOfFrameData != null);
    for (FrameMessage[] arrayOfFrameMessage = new FrameMessage[paramArrayOfFrameData.length]; ; arrayOfFrameMessage = EMPTY_FRAME_MESSAGES)
      for (int i = 0; i < arrayOfFrameMessage.length; i++)
        arrayOfFrameMessage[i] = new FrameMessage(paramArrayOfFrameData[i]);
    return new RepeatedMessage(arrayOfFrameMessage);
  }

  private static RepeatedMessage createThreadsMessage(ThreadData[] paramArrayOfThreadData)
  {
    if (paramArrayOfThreadData != null);
    for (ThreadMessage[] arrayOfThreadMessage = new ThreadMessage[paramArrayOfThreadData.length]; ; arrayOfThreadMessage = EMPTY_THREAD_MESSAGES)
      for (int i = 0; i < arrayOfThreadMessage.length; i++)
      {
        ThreadData localThreadData = paramArrayOfThreadData[i];
        arrayOfThreadMessage[i] = new ThreadMessage(localThreadData, createFramesMessage(localThreadData.frames));
      }
    return new RepeatedMessage(arrayOfThreadMessage);
  }

  private static EventMessage readCrashEventData(SessionEventData paramSessionEventData)
    throws IOException
  {
    if (paramSessionEventData.signal != null);
    for (SignalData localSignalData = paramSessionEventData.signal; ; localSignalData = DEFAULT_SIGNAL)
    {
      ApplicationMessage localApplicationMessage = new ApplicationMessage(new ExecutionMessage(new SignalMessage(localSignalData), createThreadsMessage(paramSessionEventData.threads), createBinaryImagesMessage(paramSessionEventData.binaryImages)), createCustomAttributesMessage(paramSessionEventData.customAttributes));
      DeviceMessage localDeviceMessage = new DeviceMessage();
      return new EventMessage(paramSessionEventData.timestamp, "ndk-crash", localApplicationMessage, localDeviceMessage);
    }
  }

  public static void writeNativeCrash(SessionEventData paramSessionEventData, CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    readCrashEventData(paramSessionEventData).write(paramCodedOutputStream);
  }

  private static final class ApplicationMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 3;

    public ApplicationMessage(NativeCrashWriter.ExecutionMessage paramExecutionMessage, NativeCrashWriter.RepeatedMessage paramRepeatedMessage)
    {
      super(new NativeCrashWriter.ProtobufMessage[] { paramExecutionMessage, paramRepeatedMessage });
    }
  }

  private static final class BinaryImageMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 4;
    private final long baseAddr;
    private final String filePath;
    private final long imageSize;
    private final String uuid;

    public BinaryImageMessage(BinaryImageData paramBinaryImageData)
    {
      super(new NativeCrashWriter.ProtobufMessage[0]);
      this.baseAddr = paramBinaryImageData.baseAddress;
      this.imageSize = paramBinaryImageData.size;
      this.filePath = paramBinaryImageData.path;
      this.uuid = paramBinaryImageData.id;
    }

    public int getPropertiesSize()
    {
      int i = CodedOutputStream.computeUInt64Size(1, this.baseAddr);
      int j = CodedOutputStream.computeUInt64Size(2, this.imageSize);
      int k = CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(this.filePath));
      return CodedOutputStream.computeBytesSize(4, ByteString.copyFromUtf8(this.uuid)) + (j + (k + i));
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeUInt64(1, this.baseAddr);
      paramCodedOutputStream.writeUInt64(2, this.imageSize);
      paramCodedOutputStream.writeBytes(3, ByteString.copyFromUtf8(this.filePath));
      paramCodedOutputStream.writeBytes(4, ByteString.copyFromUtf8(this.uuid));
    }
  }

  private static final class CustomAttributeMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 2;
    private final String key;
    private final String value;

    public CustomAttributeMessage(CustomAttributeData paramCustomAttributeData)
    {
      super(new NativeCrashWriter.ProtobufMessage[0]);
      this.key = paramCustomAttributeData.key;
      this.value = paramCustomAttributeData.value;
    }

    public int getPropertiesSize()
    {
      int i = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.key));
      if (this.value == null);
      for (String str = ""; ; str = this.value)
        return i + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(str));
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeBytes(1, ByteString.copyFromUtf8(this.key));
      if (this.value == null);
      for (String str = ""; ; str = this.value)
      {
        paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(str));
        return;
      }
    }
  }

  private static final class DeviceMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 5;

    public DeviceMessage()
    {
      super(new NativeCrashWriter.ProtobufMessage[0]);
    }

    public int getPropertiesSize()
    {
      return 0 + CodedOutputStream.computeFloatSize(1, 0.0F) + CodedOutputStream.computeInt32Size(2, 0) + CodedOutputStream.computeBoolSize(3, false) + CodedOutputStream.computeUInt32Size(4, 0) + CodedOutputStream.computeUInt64Size(5, 0L) + CodedOutputStream.computeUInt64Size(6, 0L);
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeFloat(1, 0.0F);
      paramCodedOutputStream.writeInt32(2, 0);
      paramCodedOutputStream.writeBool(3, false);
      paramCodedOutputStream.writeUInt32(4, 0);
      paramCodedOutputStream.writeUInt64(5, 0L);
      paramCodedOutputStream.writeUInt64(6, 0L);
    }
  }

  private static final class EventMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 10;
    private final String crashType;
    private final long time;

    public EventMessage(long paramLong, String paramString, NativeCrashWriter.ApplicationMessage paramApplicationMessage, NativeCrashWriter.DeviceMessage paramDeviceMessage)
    {
      super(new NativeCrashWriter.ProtobufMessage[] { paramApplicationMessage, paramDeviceMessage });
      this.time = paramLong;
      this.crashType = paramString;
    }

    public int getPropertiesSize()
    {
      return CodedOutputStream.computeUInt64Size(1, this.time) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.crashType));
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeUInt64(1, this.time);
      paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(this.crashType));
    }
  }

  private static final class ExecutionMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 1;

    public ExecutionMessage(NativeCrashWriter.SignalMessage paramSignalMessage, NativeCrashWriter.RepeatedMessage paramRepeatedMessage1, NativeCrashWriter.RepeatedMessage paramRepeatedMessage2)
    {
      super(new NativeCrashWriter.ProtobufMessage[] { paramRepeatedMessage1, paramSignalMessage, paramRepeatedMessage2 });
    }
  }

  private static final class FrameMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 3;
    private final long address;
    private final String file;
    private final int importance;
    private final long offset;
    private final String symbol;

    public FrameMessage(ThreadData.FrameData paramFrameData)
    {
      super(new NativeCrashWriter.ProtobufMessage[0]);
      this.address = paramFrameData.address;
      this.symbol = paramFrameData.symbol;
      this.file = paramFrameData.file;
      this.offset = paramFrameData.offset;
      this.importance = paramFrameData.importance;
    }

    public int getPropertiesSize()
    {
      return CodedOutputStream.computeUInt64Size(1, this.address) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.symbol)) + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(this.file)) + CodedOutputStream.computeUInt64Size(4, this.offset) + CodedOutputStream.computeUInt32Size(5, this.importance);
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeUInt64(1, this.address);
      paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(this.symbol));
      paramCodedOutputStream.writeBytes(3, ByteString.copyFromUtf8(this.file));
      paramCodedOutputStream.writeUInt64(4, this.offset);
      paramCodedOutputStream.writeUInt32(5, this.importance);
    }
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

  private static final class RepeatedMessage extends NativeCrashWriter.ProtobufMessage
  {
    private final NativeCrashWriter.ProtobufMessage[] messages;

    public RepeatedMessage(NativeCrashWriter.ProtobufMessage[] paramArrayOfProtobufMessage)
    {
      super(new NativeCrashWriter.ProtobufMessage[0]);
      this.messages = paramArrayOfProtobufMessage;
    }

    public int getSize()
    {
      int i = 0;
      NativeCrashWriter.ProtobufMessage[] arrayOfProtobufMessage = this.messages;
      int j = arrayOfProtobufMessage.length;
      for (int k = 0; k < j; k++)
        i += arrayOfProtobufMessage[k].getSize();
      return i;
    }

    public void write(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      NativeCrashWriter.ProtobufMessage[] arrayOfProtobufMessage = this.messages;
      int i = arrayOfProtobufMessage.length;
      for (int j = 0; j < i; j++)
        arrayOfProtobufMessage[j].write(paramCodedOutputStream);
    }
  }

  private static final class SignalMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 3;
    private final long sigAddr;
    private final String sigCode;
    private final String sigName;

    public SignalMessage(SignalData paramSignalData)
    {
      super(new NativeCrashWriter.ProtobufMessage[0]);
      this.sigName = paramSignalData.name;
      this.sigCode = paramSignalData.code;
      this.sigAddr = paramSignalData.faultAddress;
    }

    public int getPropertiesSize()
    {
      return CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.sigName)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.sigCode)) + CodedOutputStream.computeUInt64Size(3, this.sigAddr);
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeBytes(1, ByteString.copyFromUtf8(this.sigName));
      paramCodedOutputStream.writeBytes(2, ByteString.copyFromUtf8(this.sigCode));
      paramCodedOutputStream.writeUInt64(3, this.sigAddr);
    }
  }

  private static final class ThreadMessage extends NativeCrashWriter.ProtobufMessage
  {
    private static final int PROTOBUF_TAG = 1;
    private final int importance;

    public ThreadMessage(ThreadData paramThreadData, NativeCrashWriter.RepeatedMessage paramRepeatedMessage)
    {
      super(new NativeCrashWriter.ProtobufMessage[] { paramRepeatedMessage });
      this.importance = paramThreadData.importance;
    }

    public int getPropertiesSize()
    {
      return CodedOutputStream.computeUInt32Size(2, this.importance);
    }

    public void writeProperties(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      paramCodedOutputStream.writeUInt32(2, this.importance);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.NativeCrashWriter
 * JD-Core Version:    0.6.2
 */