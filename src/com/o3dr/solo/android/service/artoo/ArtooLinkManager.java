package com.o3dr.solo.android.service.artoo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import com.o3dr.solo.android.service.AbstractLinkManager;
import com.o3dr.solo.android.service.AbstractLinkManager.LinkListener;
import com.o3dr.solo.android.service.artoo.button.ButtonPacket;
import com.o3dr.solo.android.service.sololink.tlv.TLVMessageParser;
import com.o3dr.solo.android.service.sololink.tlv.TLVPacket;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.NetworkUtils;
import com.o3dr.solo.android.util.connection.IpConnectionListener;
import com.o3dr.solo.android.util.connection.SshConnection;
import com.o3dr.solo.android.util.connection.TcpConnection;
import com.o3dr.solo.android.util.video.DecoderListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ArtooLinkManager extends AbstractLinkManager
{
  public static final String ACTION_BUTTON_PACKET_RECEIVED = "com.o3dr.solo.android.action.ACTION_BUTTON_PACKET_RECEIVED";
  public static final String ACTION_SOLOLINK_WIFI_INFO_UPDATED = "com.o3dr.solo.android.action.SOLOLINK_WIFI_INFO_UPDATED";
  private static final int ARTOO_BATTERY_PORT = 5021;
  private static final int ARTOO_BUTTON_PORT = 5016;
  public static final String ARTOO_IP = "10.1.1.1";
  public static final int ARTOO_UDP_PORT = 5600;
  private static final int ARTOO_VIDEO_HANDSHAKE_PORT = 5502;
  public static final String EXTRA_BUTTON_PACKET = "extra_button_packet";
  private static final long RECONNECT_COUNTDOWN = 1000L;
  public static final String SOLOLINK_SSID_CONFIG_PATH = "/usr/bin/sololink_config";
  private static final String TAG = ArtooLinkManager.class.getSimpleName();
  private static final SshConnection sshLink = new SshConnection("10.1.1.1", "root", "TjSDBkAu");
  private final TcpConnection batteryConnection;
  private final Handler handler = new Handler();
  private final AtomicBoolean isBatteryStarted = new AtomicBoolean(false);
  private final AtomicBoolean isVideoHandshakeStarted = new AtomicBoolean(false);
  private final LocalBroadcastManager lbm;
  private final Runnable reconnectBatteryTask = new Runnable()
  {
    public void run()
    {
      ArtooLinkManager.this.batteryConnection.connect();
    }
  };
  private final Runnable reconnectVideoHandshake = new Runnable()
  {
    public void run()
    {
      ArtooLinkManager.this.videoHandshake.connect();
    }
  };
  private final AtomicReference<Pair<String, String>> sololinkWifiInfo = new AtomicReference(Pair.create("", ""));
  private final Runnable startVideoMgr = new Runnable()
  {
    public void run()
    {
      ArtooLinkManager.this.videoMgr.start(null);
    }
  };
  private final TcpConnection videoHandshake;
  private final VideoManager videoMgr;

  public ArtooLinkManager(Context paramContext)
  {
    super(paramContext, new TcpConnection("10.1.1.1", 5016));
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
    this.videoMgr = new VideoManager(paramContext);
    this.videoHandshake = new TcpConnection("10.1.1.1", 5502);
    this.videoHandshake.setIpConnectionListener(new IpConnectionListener()
    {
      private int disconnectTracker = 0;

      public void onIpConnected()
      {
        this.disconnectTracker = 0;
        ArtooLinkManager.this.handler.removeCallbacks(ArtooLinkManager.this.reconnectVideoHandshake);
        Log.d(ArtooLinkManager.TAG, "Artoo link connected. Starting video stream...");
        ArtooLinkManager.this.handler.post(ArtooLinkManager.this.startVideoMgr);
      }

      public void onIpDisconnected()
      {
        if (ArtooLinkManager.this.isVideoHandshakeStarted.get())
        {
          Handler localHandler = ArtooLinkManager.this.handler;
          Runnable localRunnable = ArtooLinkManager.this.reconnectVideoHandshake;
          int i = 1 + this.disconnectTracker;
          this.disconnectTracker = i;
          localHandler.postDelayed(localRunnable, 1000L * i);
        }
      }

      public void onPacketReceived(ByteBuffer paramAnonymousByteBuffer)
      {
      }
    });
    this.batteryConnection = new TcpConnection("10.1.1.1", 5021);
    this.batteryConnection.setIpConnectionListener(new IpConnectionListener()
    {
      private int disconnectTracker = 0;

      public void onIpConnected()
      {
        this.disconnectTracker = 0;
        ArtooLinkManager.this.handler.removeCallbacks(ArtooLinkManager.this.reconnectBatteryTask);
      }

      public void onIpDisconnected()
      {
        if (ArtooLinkManager.this.isBatteryStarted.get())
        {
          Handler localHandler = ArtooLinkManager.this.handler;
          Runnable localRunnable = ArtooLinkManager.this.reconnectBatteryTask;
          int i = 1 + this.disconnectTracker;
          this.disconnectTracker = i;
          localHandler.postDelayed(localRunnable, 1000L * i);
        }
      }

      public void onPacketReceived(ByteBuffer paramAnonymousByteBuffer)
      {
        TLVPacket localTLVPacket = TLVMessageParser.parseTLVPacket(paramAnonymousByteBuffer);
        if (localTLVPacket == null)
          return;
        int i = localTLVPacket.getMessageType();
        Log.d(ArtooLinkManager.TAG, "Received tlv message: " + i);
        ArtooLinkManager.this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.TLV_PACKET_RECEIVED").putExtra("extra_tlv_packet_type", i).putExtra("extra_tlv_packet_bytes", localTLVPacket.toBytes()));
      }
    });
  }

  public static SshConnection getSshLink()
  {
    return sshLink;
  }

  private void loadSololinkWifiInfo()
  {
    try
    {
      String str1 = sshLink.execute("/usr/bin/sololink_config --get-wifi-ssid");
      String str2 = sshLink.execute("/usr/bin/sololink_config --get-wifi-password");
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
      {
        Pair localPair = Pair.create(str1.trim(), str2.trim());
        this.sololinkWifiInfo.set(localPair);
        this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SOLOLINK_WIFI_INFO_UPDATED"));
      }
      return;
    }
    catch (IOException localIOException)
    {
      Log.e(TAG, "Unable to retrieve sololink wifi info.", localIOException);
    }
  }

  public Pair<String, String> getSoloLinkWifiInfo()
  {
    return (Pair)this.sololinkWifiInfo.get();
  }

  public boolean isLinkConnected()
  {
    return NetworkUtils.isOnSololinkNetwork(this.context);
  }

  public void onIpConnected()
  {
    super.onIpConnected();
    Log.d(TAG, "Artoo link connected.");
    loadSololinkWifiInfo();
    this.handler.removeCallbacks(this.reconnectVideoHandshake);
    this.isVideoHandshakeStarted.set(true);
    this.videoHandshake.connect();
    this.context.startService(new Intent(this.context, UpdateService.class).setAction("com.o3dr.solo.android.action.UPDATE_COMPONENTS_VERSION"));
  }

  public void onIpDisconnected()
  {
    Log.d(TAG, "Artoo link disconnected.");
    this.handler.removeCallbacks(this.startVideoMgr);
    this.videoMgr.stop();
    this.handler.removeCallbacks(this.reconnectVideoHandshake);
    this.isVideoHandshakeStarted.set(false);
    this.videoHandshake.disconnect();
    super.onIpDisconnected();
  }

  public void onPacketReceived(ByteBuffer paramByteBuffer)
  {
    ButtonPacket localButtonPacket = ButtonPacket.parseButtonPacket(paramByteBuffer);
    if (localButtonPacket == null)
      return;
    int i = localButtonPacket.getButtonId();
    Log.d(TAG, "Button pressed: " + i);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ACTION_BUTTON_PACKET_RECEIVED").putExtra("extra_button_packet", localButtonPacket));
  }

  public void start(AbstractLinkManager.LinkListener paramLinkListener)
  {
    Log.d(TAG, "Starting artoo link manager");
    super.start(paramLinkListener);
    this.handler.removeCallbacks(this.reconnectBatteryTask);
  }

  public void startDecoding(Surface paramSurface, DecoderListener paramDecoderListener)
  {
    this.videoMgr.startDecoding(paramSurface, paramDecoderListener);
  }

  public void startVideoManager()
  {
    this.handler.post(this.startVideoMgr);
  }

  public void stop()
  {
    Log.d(TAG, "Stopping artoo link manager");
    this.handler.removeCallbacks(this.startVideoMgr);
    this.videoMgr.stop();
    this.handler.removeCallbacks(this.reconnectVideoHandshake);
    this.isVideoHandshakeStarted.set(false);
    this.videoHandshake.disconnect();
    this.handler.removeCallbacks(this.reconnectBatteryTask);
    this.isBatteryStarted.set(false);
    this.batteryConnection.disconnect();
    super.stop();
  }

  public void stopDecoding(DecoderListener paramDecoderListener)
  {
    this.videoMgr.stopDecoding(paramDecoderListener);
  }

  public void stopVideoManager()
  {
    this.videoMgr.stop();
  }

  public boolean updateSololinkWifi(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Log.d(TAG, String.format(Locale.US, "Updating artoo wifi ssid to %s with password %s", new Object[] { paramCharSequence1, paramCharSequence2 }));
    try
    {
      sshLink.execute("/usr/bin/sololink_config --set-wifi-ssid " + paramCharSequence1);
      sshLink.execute("/usr/bin/sololink_config --set-wifi-password " + paramCharSequence2);
      sshLink.execute("/usr/bin/sololink_config --reboot");
      return true;
    }
    catch (IOException localIOException)
    {
      Log.e(TAG, "Error occurred while updating the sololink wifi ssid.", localIOException);
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.artoo.ArtooLinkManager
 * JD-Core Version:    0.6.2
 */