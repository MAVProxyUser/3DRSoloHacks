package com.o3dr.solo.android.service;

import android.content.Context;
import android.os.Handler;
import com.o3dr.solo.android.util.connection.AbstractIpConnection;
import com.o3dr.solo.android.util.connection.IpConnectionListener;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractLinkManager
  implements IpConnectionListener
{
  public static final String ACTION_TLV_PACKET_RECEIVED = "com.o3dr.solo.android.action.TLV_PACKET_RECEIVED";
  public static final String EXTRA_TLV_PACKET_BYTES = "extra_tlv_packet_bytes";
  public static final String EXTRA_TLV_PACKET_TYPE = "extra_tlv_packet_type";
  private static final long RECONNECT_COUNTDOWN = 1000L;
  protected final Context context;
  private int disconnectTracker = 0;
  private final Handler handler = new Handler();
  private final AtomicBoolean isStarted = new AtomicBoolean(false);
  protected final AbstractIpConnection linkConn;
  private LinkListener linkListener;
  private final Runnable reconnectTask = new Runnable()
  {
    public void run()
    {
      AbstractLinkManager.this.linkConn.connect();
    }
  };

  public AbstractLinkManager(Context paramContext, AbstractIpConnection paramAbstractIpConnection)
  {
    this.context = paramContext;
    this.linkConn = paramAbstractIpConnection;
    this.linkConn.setIpConnectionListener(this);
  }

  public boolean isLinkConnected()
  {
    return this.linkConn.getConnectionStatus() == 2;
  }

  public void onIpConnected()
  {
    this.disconnectTracker = 0;
    this.handler.removeCallbacks(this.reconnectTask);
    if (this.linkListener != null)
      this.linkListener.onLinkConnected();
  }

  public void onIpDisconnected()
  {
    if (this.isStarted.get())
    {
      if (shouldReconnect())
      {
        Handler localHandler = this.handler;
        Runnable localRunnable = this.reconnectTask;
        int i = 1 + this.disconnectTracker;
        this.disconnectTracker = i;
        localHandler.postDelayed(localRunnable, 1000L * i);
      }
      if (this.linkListener != null)
        this.linkListener.onLinkDisconnected();
    }
  }

  protected boolean shouldReconnect()
  {
    return true;
  }

  public void start(LinkListener paramLinkListener)
  {
    this.disconnectTracker = 0;
    this.handler.removeCallbacks(this.reconnectTask);
    this.isStarted.set(true);
    this.linkConn.connect();
    this.linkListener = paramLinkListener;
  }

  public void stop()
  {
    this.handler.removeCallbacks(this.reconnectTask);
    this.isStarted.set(false);
    this.linkConn.disconnect();
  }

  public static abstract interface LinkListener
  {
    public abstract void onLinkConnected();

    public abstract void onLinkDisconnected();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.AbstractLinkManager
 * JD-Core Version:    0.6.2
 */