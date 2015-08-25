package com.o3dr.solo.android.util.video;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MediaCodecManager
{
  public static final int DEFAULT_VIDEO_HEIGHT = 1080;
  public static final int DEFAULT_VIDEO_WIDTH = 1920;
  private static final String MIME_TYPE = "video/avc";
  private static final String TAG = MediaCodecManager.class.getSimpleName();
  private final AtomicBoolean decodedFirstFrame = new AtomicBoolean(false);
  private final AtomicReference<DecoderListener> decoderListenerRef = new AtomicReference();
  private final Runnable decodingEndedNotification = new Runnable()
  {
    public void run()
    {
      DecoderListener localDecoderListener = (DecoderListener)MediaCodecManager.this.decoderListenerRef.get();
      if (localDecoderListener != null)
        localDecoderListener.onDecodingEnded();
    }
  };
  private final Runnable decodingErrorNotification = new Runnable()
  {
    public void run()
    {
      DecoderListener localDecoderListener = (DecoderListener)MediaCodecManager.this.decoderListenerRef.get();
      if (localDecoderListener != null)
        localDecoderListener.onDecodingError();
    }
  };
  private final Runnable decodingStartedNotification = new Runnable()
  {
    public void run()
    {
      DecoderListener localDecoderListener = (DecoderListener)MediaCodecManager.this.decoderListenerRef.get();
      if (localDecoderListener != null)
        localDecoderListener.onDecodingStarted();
    }
  };
  private DequeueCodec dequeueRunner;
  private final Handler handler = new Handler();
  private final AtomicBoolean isDecoding = new AtomicBoolean(false);
  private final AtomicReference<MediaCodec> mediaCodecRef = new AtomicReference();
  private final NALUChunkAssembler naluChunkAssembler = new NALUChunkAssembler();
  private final AtomicBoolean processInputData = new AtomicBoolean(false);
  private final AtomicBoolean sendCompletionFlag = new AtomicBoolean(false);
  private final Runnable stopSafely = new Runnable()
  {
    public void run()
    {
      MediaCodecManager.this.processInputData.set(false);
      MediaCodecManager.this.sendCompletionFlag.set(false);
      MediaCodecManager.this.naluChunkAssembler.reset();
      if ((MediaCodecManager.this.dequeueRunner != null) && (MediaCodecManager.this.dequeueRunner.isAlive()))
      {
        Log.d(MediaCodecManager.TAG, "Interrupting dequeue runner thread.");
        MediaCodecManager.this.dequeueRunner.interrupt();
      }
      MediaCodecManager.access$302(MediaCodecManager.this, null);
      MediaCodec localMediaCodec = (MediaCodec)MediaCodecManager.this.mediaCodecRef.get();
      if (localMediaCodec != null)
      {
        localMediaCodec.stop();
        localMediaCodec.release();
        MediaCodecManager.this.mediaCodecRef.set(null);
      }
      MediaCodecManager.this.isDecoding.set(false);
      MediaCodecManager.this.handler.post(MediaCodecManager.this.decodingEndedNotification);
    }
  };

  private void notifyDecodingEnded()
  {
    this.handler.post(this.stopSafely);
  }

  private void notifyDecodingError()
  {
    this.handler.post(this.decodingErrorNotification);
  }

  private void notifyDecodingStarted()
  {
    this.handler.post(this.decodingStartedNotification);
  }

  private boolean processNALUChunk(NALUChunk paramNALUChunk)
  {
    if (paramNALUChunk == null)
      return false;
    MediaCodec localMediaCodec = (MediaCodec)this.mediaCodecRef.get();
    if (localMediaCodec == null)
      return false;
    while (true)
    {
      ByteBuffer localByteBuffer1;
      int m;
      try
      {
        int i = localMediaCodec.dequeueInputBuffer(-1L);
        if (i >= 0)
        {
          if (Build.VERSION.SDK_INT >= 21)
          {
            localByteBuffer1 = localMediaCodec.getInputBuffer(i);
          }
          else
          {
            localByteBuffer1 = localMediaCodec.getInputBuffers()[i];
            break label190;
            localByteBuffer1.clear();
            int j = 0;
            int k = paramNALUChunk.payloads.length;
            m = 0;
            if (m < k)
            {
              ByteBuffer localByteBuffer2 = paramNALUChunk.payloads[m];
              if (localByteBuffer2.capacity() == 0)
                break label197;
              localByteBuffer1.order(localByteBuffer2.order());
              int n = localByteBuffer2.position();
              localByteBuffer1.put(localByteBuffer2.array(), 0, n);
              j += n;
              break label197;
            }
            localMediaCodec.queueInputBuffer(i, 0, j, paramNALUChunk.presentationTime, paramNALUChunk.flags);
          }
        }
        else
          return true;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e(TAG, localIllegalStateException.getMessage(), localIllegalStateException);
        return false;
      }
      label190: if (localByteBuffer1 == null)
      {
        return false;
        label197: m++;
      }
    }
  }

  public void onInputDataReceived(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.isDecoding.get())
    {
      if (!this.processInputData.get())
        break label44;
      localNALUChunk = this.naluChunkAssembler.assembleNALUChunk(paramArrayOfByte, paramInt);
      if (localNALUChunk != null)
        processNALUChunk(localNALUChunk);
    }
    label44: 
    while (!this.sendCompletionFlag.get())
    {
      NALUChunk localNALUChunk;
      return;
    }
    Log.d(TAG, "Sending end of stream data.");
    AtomicBoolean localAtomicBoolean = this.sendCompletionFlag;
    if (!processNALUChunk(this.naluChunkAssembler.getEndOfStream()));
    for (boolean bool = true; ; bool = false)
    {
      localAtomicBoolean.set(bool);
      return;
    }
  }

  public void startDecoding(Surface paramSurface, DecoderListener paramDecoderListener)
    throws IOException
  {
    if (paramSurface == null)
      throw new IllegalStateException("Surface argument must be non-null.");
    if (this.isDecoding.compareAndSet(false, true))
    {
      Log.i(TAG, "Starting decoding...");
      this.naluChunkAssembler.reset();
      this.decoderListenerRef.set(paramDecoderListener);
      MediaFormat localMediaFormat = MediaFormat.createVideoFormat("video/avc", 1920, 1080);
      MediaCodec localMediaCodec = MediaCodec.createDecoderByType("video/avc");
      localMediaCodec.configure(localMediaFormat, paramSurface, null, 0);
      localMediaCodec.start();
      this.mediaCodecRef.set(localMediaCodec);
      this.processInputData.set(true);
      this.dequeueRunner = new DequeueCodec(null);
      this.dequeueRunner.start();
    }
  }

  public void stopDecoding(DecoderListener paramDecoderListener)
  {
    Log.i(TAG, "Stopping input data processing...");
    this.decoderListenerRef.set(paramDecoderListener);
    if (!this.isDecoding.get())
      if (paramDecoderListener != null)
        notifyDecodingEnded();
    do
    {
      return;
      if (!this.decodedFirstFrame.get())
        break;
    }
    while (!this.processInputData.compareAndSet(true, false));
    this.sendCompletionFlag.set(true);
    return;
    this.handler.post(this.stopSafely);
  }

  private class DequeueCodec extends Thread
  {
    private DequeueCodec()
    {
    }

    public void run()
    {
      MediaCodec localMediaCodec = (MediaCodec)MediaCodecManager.this.mediaCodecRef.get();
      if (localMediaCodec == null)
        throw new IllegalStateException("Start decoding hasn't been called yet.");
      Log.i(MediaCodecManager.TAG, "Starting dequeue codec runner.");
      MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
      MediaCodecManager.this.decodedFirstFrame.set(false);
      int i = 1;
      while (true)
      {
        if (i != 0);
        try
        {
          int j = localMediaCodec.dequeueOutputBuffer(localBufferInfo, -1L);
          if (j < 0)
            continue;
          if (localBufferInfo.size != 0)
          {
            bool = true;
            localMediaCodec.releaseOutputBuffer(j, bool);
            if (MediaCodecManager.this.decodedFirstFrame.compareAndSet(false, true))
            {
              MediaCodecManager.this.notifyDecodingStarted();
              Log.i(MediaCodecManager.TAG, "Received first decoded frame of size " + localBufferInfo.size);
            }
            if ((0x4 & localBufferInfo.flags) != 0)
              break label234;
            i = 1;
            if (i != 0)
              continue;
            Log.i(MediaCodecManager.TAG, "Received end of stream flag.");
            continue;
          }
        }
        catch (IllegalStateException localIllegalStateException)
        {
          while (true)
          {
            if (!isInterrupted())
            {
              Log.e(MediaCodecManager.TAG, "Decoding error!", localIllegalStateException);
              MediaCodecManager.this.notifyDecodingError();
            }
            return;
            boolean bool = false;
            continue;
            label234: i = 0;
          }
          return;
        }
        finally
        {
          if (!isInterrupted())
            MediaCodecManager.this.notifyDecodingEnded();
          Log.i(MediaCodecManager.TAG, "Stopping dequeue codec runner.");
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.video.MediaCodecManager
 * JD-Core Version:    0.6.2
 */