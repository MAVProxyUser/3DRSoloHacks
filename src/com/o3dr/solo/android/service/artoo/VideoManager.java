package com.o3dr.solo.android.service.artoo;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import com.o3dr.solo.android.service.AbstractLinkManager;
import com.o3dr.solo.android.service.AbstractLinkManager.LinkListener;
import com.o3dr.solo.android.util.connection.UdpConnection;
import com.o3dr.solo.android.util.video.DecoderListener;
import com.o3dr.solo.android.util.video.MediaCodecManager;
import java.io.IOException;
import java.nio.ByteBuffer;

public class VideoManager extends AbstractLinkManager
{
  private static final String TAG = VideoManager.class.getSimpleName();
  private static final int UDP_BUFFER_SIZE = 1500;
  private final MediaCodecManager mediaCodecManager = new MediaCodecManager();

  VideoManager(Context paramContext)
  {
    super(paramContext, new UdpConnection(5600, 1500));
  }

  public void onIpConnected()
  {
    Log.d(TAG, "Connected to video stream");
    super.onIpConnected();
  }

  public void onIpDisconnected()
  {
    Log.d(TAG, "Video stream disconnected");
    super.onIpDisconnected();
  }

  public void onPacketReceived(ByteBuffer paramByteBuffer)
  {
    this.mediaCodecManager.onInputDataReceived(paramByteBuffer.array(), paramByteBuffer.limit());
  }

  public void start(AbstractLinkManager.LinkListener paramLinkListener)
  {
    Log.d(TAG, "Starting video manager");
    super.start(paramLinkListener);
  }

  void startDecoding(final Surface paramSurface, final DecoderListener paramDecoderListener)
  {
    Log.i(TAG, "Setting up video stream decoding.");
    this.mediaCodecManager.stopDecoding(new DecoderListener()
    {
      public void onDecodingEnded()
      {
        try
        {
          Log.i(VideoManager.TAG, "Video decoding set up complete. Starting...");
          VideoManager.this.mediaCodecManager.startDecoding(paramSurface, paramDecoderListener);
          return;
        }
        catch (IOException localIOException)
        {
          do
            Log.e(VideoManager.TAG, "Unable to create media codec.", localIOException);
          while (paramDecoderListener == null);
          paramDecoderListener.onDecodingError();
          return;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          label29: break label29;
        }
      }

      public void onDecodingError()
      {
      }

      public void onDecodingStarted()
      {
      }
    });
  }

  public void stop()
  {
    Log.d(TAG, "Stopping video manager");
    super.stop();
  }

  void stopDecoding(DecoderListener paramDecoderListener)
  {
    Log.i(TAG, "Aborting video decoding process.");
    this.mediaCodecManager.stopDecoding(paramDecoderListener);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.artoo.VideoManager
 * JD-Core Version:    0.6.2
 */