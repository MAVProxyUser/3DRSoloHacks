package com.o3dr.solo.android.service.sololink;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.solo.android.service.AbstractLinkManager;
import com.o3dr.solo.android.service.AbstractLinkManager.LinkListener;
import com.o3dr.solo.android.service.DroneKitManager;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSetting;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSettingGetter;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSettingSetter;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShotManagerError;
import com.o3dr.solo.android.service.sololink.tlv.TLVMessageParser;
import com.o3dr.solo.android.service.sololink.tlv.TLVPacket;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.connection.AbstractIpConnection;
import com.o3dr.solo.android.util.connection.SshConnection;
import com.o3dr.solo.android.util.connection.TcpConnection;
import com.o3dr.solo.android.util.connection.UdpConnection;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class SoloLinkManager extends AbstractLinkManager
{
  public static final String ACTION_PRESET_BUTTON_LOADED = "com.o3dr.solo.android.action.PRESET_BUTTON_LOADED";
  public static final String ACTION_SOLO_LINK_CONNECTED = "com.o3dr.solo.android.action.SOLO_LINK_CONNECTED";
  public static final String ACTION_SOLO_LINK_DISCONNECTED = "com.o3dr.solo.android.action.SOLO_LINK_DISCONNECTED";
  public static final String EXTRA_PRESET_BUTTON_TYPE = "extra_preset_button_type";
  private static final int SHOT_FOLLOW_UDP_PORT = 14558;
  public static final String SOLO_LINK_DEFAULT_PASSWORD = "sololink";
  public static final String SOLO_LINK_IP = "10.1.1.10";
  public static final int SOLO_LINK_TCP_PORT = 5507;
  private static final String TAG = SoloLinkManager.class.getSimpleName();
  private static final SshConnection sshLink = new SshConnection(getSoloLinkIp(), "root", "TjSDBkAu");
  private final DroneKitManager droneMgr;
  private final UdpConnection followDataConn;
  private final LocalBroadcastManager lbm;
  private final AtomicReference<SoloButtonSetting> loadedPresetButtonA = new AtomicReference();
  private final AtomicReference<SoloButtonSetting> loadedPresetButtonB = new AtomicReference();
  private final SoloButtonSettingGetter presetButtonAGetter = new SoloButtonSettingGetter(4, 0);
  private final SoloButtonSettingGetter presetButtonBGetter = new SoloButtonSettingGetter(5, 0);
  private final ShotManager shotManager;

  public SoloLinkManager(Context paramContext, DroneKitManager paramDroneKitManager)
  {
    super(paramContext, new TcpConnection(getSoloLinkIp(), 5507));
    this.droneMgr = paramDroneKitManager;
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
    this.shotManager = new ShotManager(paramContext, this);
    try
    {
      UdpConnection localUdpConnection1 = new UdpConnection(getSoloLinkIp(), 14558, 14557);
      localUdpConnection2 = localUdpConnection1;
      this.followDataConn = localUdpConnection2;
      return;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      while (true)
      {
        Log.e(TAG, "Error while creating follow udp connection.");
        UdpConnection localUdpConnection2 = null;
      }
    }
  }

  public static String getSoloLinkIp()
  {
    return "10.1.1.10";
  }

  public static SshConnection getSshLink()
  {
    return sshLink;
  }

  private void handleReceivedPresetButton(SoloButtonSetting paramSoloButtonSetting)
  {
    int i = paramSoloButtonSetting.getButton();
    switch (i)
    {
    default:
      return;
    case 4:
      this.loadedPresetButtonA.set(paramSoloButtonSetting);
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED").putExtra("extra_preset_button_type", i));
      return;
    case 5:
    }
    this.loadedPresetButtonB.set(paramSoloButtonSetting);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED").putExtra("extra_preset_button_type", i));
  }

  private void sendFollowPacket(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.followDataConn == null)
      throw new IllegalStateException("Unable to send follow data.");
    this.followDataConn.sendPacket(paramArrayOfByte, paramInt);
  }

  private void sendPacket(byte[] paramArrayOfByte, int paramInt)
  {
    this.linkConn.sendPacket(paramArrayOfByte, paramInt);
  }

  public void disableFollowDataConnection()
  {
    if (this.followDataConn != null)
      this.followDataConn.disconnect();
  }

  public void enableFollowDataConnection()
  {
    if (this.followDataConn != null)
      this.followDataConn.connect();
  }

  public SoloButtonSetting getLoadedPresetButton(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 4:
      return (SoloButtonSetting)this.loadedPresetButtonA.get();
    case 5:
    }
    return (SoloButtonSetting)this.loadedPresetButtonB.get();
  }

  public ShotManager getShotManager()
  {
    return this.shotManager;
  }

  public boolean isLinkConnected()
  {
    return (this.droneMgr.isDroneConnected()) && (super.isLinkConnected());
  }

  public void loadPresetButtonSettings()
  {
    sendTLVPacket(this.presetButtonAGetter);
    sendTLVPacket(this.presetButtonBGetter);
  }

  public void onIpConnected()
  {
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SOLO_LINK_CONNECTED"));
    Log.d(TAG, "Connected to sololink.");
    super.onIpConnected();
    loadPresetButtonSettings();
    this.context.startService(new Intent(this.context, UpdateService.class).setAction("com.o3dr.solo.android.action.UPDATE_COMPONENTS_VERSION"));
  }

  public void onIpDisconnected()
  {
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SOLO_LINK_DISCONNECTED"));
    Log.d(TAG, "Disconnected from sololink.");
    this.shotManager.forceResetShot();
    super.onIpDisconnected();
  }

  public void onPacketReceived(ByteBuffer paramByteBuffer)
  {
    TLVPacket localTLVPacket = TLVMessageParser.parseTLVPacket(paramByteBuffer);
    if (localTLVPacket == null)
      return;
    int i = localTLVPacket.getMessageType();
    Log.d(TAG, "Received tlv message: " + i);
    if (!this.shotManager.handleMessage(localTLVPacket))
      switch (i)
      {
      default:
      case 1000:
      case 5:
      }
    while (true)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.TLV_PACKET_RECEIVED").putExtra("extra_tlv_packet_type", i).putExtra("extra_tlv_packet_bytes", localTLVPacket.toBytes()));
      return;
      Log.w(TAG, ((SoloMessageShotManagerError)localTLVPacket).getExceptionInfo());
      this.shotManager.forceResetShot();
      continue;
      handleReceivedPresetButton((SoloButtonSettingGetter)localTLVPacket);
    }
  }

  public void pushPresetButtonSettings(SoloButtonSettingSetter paramSoloButtonSettingSetter)
  {
    if ((!isLinkConnected()) || (paramSoloButtonSettingSetter == null))
      return;
    sendTLVPacket(paramSoloButtonSettingSetter);
    handleReceivedPresetButton(paramSoloButtonSettingSetter);
  }

  public void sendTLVPacket(TLVPacket paramTLVPacket)
  {
    sendTLVPacket(paramTLVPacket, false);
  }

  public void sendTLVPacket(TLVPacket paramTLVPacket, boolean paramBoolean)
  {
    if (paramTLVPacket == null)
      return;
    byte[] arrayOfByte = paramTLVPacket.toBytes();
    if (paramBoolean)
    {
      sendFollowPacket(arrayOfByte, arrayOfByte.length);
      return;
    }
    sendPacket(arrayOfByte, arrayOfByte.length);
  }

  protected boolean shouldReconnect()
  {
    return this.droneMgr.isDroneConnected();
  }

  public void start(AbstractLinkManager.LinkListener paramLinkListener)
  {
    if (!this.droneMgr.isDroneConnected())
      return;
    Log.d(TAG, "Starting solo link manager");
    super.start(paramLinkListener);
  }

  public void stop()
  {
    Log.d(TAG, "Stopping solo link manager");
    super.stop();
  }

  public boolean updateSololinkWifi(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    Log.d(TAG, String.format(Locale.US, "Updating solo wifi ssid to %s with password %s", new Object[] { paramCharSequence1, paramCharSequence2 }));
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
 * Qualified Name:     com.o3dr.solo.android.service.sololink.SoloLinkManager
 * JD-Core Version:    0.6.2
 */