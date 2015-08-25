package com.o3dr.services.android.lib.util.googleApi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class GoogleApiClientManager
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
  private static final String TAG = GoogleApiClientManager.class.getSimpleName();
  private final AtomicBoolean isStarted = new AtomicBoolean(false);
  private ManagerListener listener;
  private Handler mBgHandler;
  private HandlerThread mBgHandlerThread;
  private final Context mContext;
  private final Runnable mDriverRunnable = new Runnable()
  {
    public void run()
    {
      while (true)
      {
        try
        {
          if (GoogleApiClientManager.this.isStarted.get())
          {
            if (GoogleApiClientManager.this.mGoogleApiClient.isConnected())
              break label52;
            GoogleApiClientManager.this.stop();
            continue;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          Log.v(GoogleApiClientManager.TAG, localInterruptedException.getMessage(), localInterruptedException);
        }
        return;
        label52: GoogleApiClientManager.GoogleApiClientTask localGoogleApiClientTask = (GoogleApiClientManager.GoogleApiClientTask)GoogleApiClientManager.this.mTaskQueue.take();
        if (localGoogleApiClientTask != null)
          if (GoogleApiClientManager.GoogleApiClientTask.access$400(localGoogleApiClientTask))
            GoogleApiClientManager.this.mBgHandler.post(localGoogleApiClientTask);
          else
            GoogleApiClientManager.this.mMainHandler.post(localGoogleApiClientTask);
      }
    }
  };
  private Thread mDriverThread;
  private final GoogleApiClient mGoogleApiClient;
  private final Handler mMainHandler;
  private final LinkedBlockingQueue<GoogleApiClientTask> mTaskQueue = new LinkedBlockingQueue();
  private final GoogleApiClientTask stopTask = new GoogleApiClientTask()
  {
    protected void doRun()
    {
      GoogleApiClientManager.this.stop();
    }
  };

  public GoogleApiClientManager(Context paramContext, Handler paramHandler, Api<? extends Api.ApiOptions.NotRequiredOptions>[] paramArrayOfApi)
  {
    this.mContext = paramContext;
    this.mMainHandler = paramHandler;
    GoogleApiClient.Builder localBuilder = new GoogleApiClient.Builder(paramContext);
    int i = paramArrayOfApi.length;
    for (int j = 0; j < i; j++)
      localBuilder.addApi(paramArrayOfApi[j]);
    this.mGoogleApiClient = localBuilder.addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
  }

  private void destroyBgHandler()
  {
    if ((this.mBgHandlerThread != null) && (this.mBgHandlerThread.isAlive()))
    {
      this.mBgHandlerThread.quit();
      this.mBgHandlerThread.interrupt();
      this.mBgHandlerThread = null;
    }
    this.mBgHandler = null;
  }

  private void destroyDriverThread()
  {
    if ((this.mDriverThread != null) && (this.mDriverThread.isAlive()))
    {
      this.mDriverThread.interrupt();
      this.mDriverThread = null;
    }
  }

  private void initializeBgHandler()
  {
    if ((this.mBgHandlerThread == null) || (this.mBgHandlerThread.isInterrupted()))
    {
      this.mBgHandlerThread = new HandlerThread("GAC Manager Background Thread");
      this.mBgHandlerThread.start();
      this.mBgHandler = null;
    }
    if (this.mBgHandler == null)
      this.mBgHandler = new Handler(this.mBgHandlerThread.getLooper());
  }

  private void initializeDriverThread()
  {
    if ((this.mDriverThread == null) || (this.mDriverThread.isInterrupted()))
    {
      this.mDriverThread = new Thread(this.mDriverRunnable, "GAC Manager Driver Thread");
      this.mDriverThread.start();
    }
  }

  private boolean isStarted()
  {
    return this.isStarted.get();
  }

  private void stop()
  {
    this.isStarted.set(false);
    destroyDriverThread();
    destroyBgHandler();
    this.mTaskQueue.clear();
    if ((this.mGoogleApiClient.isConnected()) || (this.mGoogleApiClient.isConnecting()))
      this.mGoogleApiClient.disconnect();
    if (this.listener != null)
      this.listener.onManagerStopped();
  }

  public boolean addTask(GoogleApiClientTask paramGoogleApiClientTask)
  {
    if (!isStarted())
    {
      Log.d(TAG, "GoogleApiClientManager is not started.");
      return false;
    }
    GoogleApiClientTask.access$802(paramGoogleApiClientTask, this.mGoogleApiClient);
    GoogleApiClientTask.access$902(paramGoogleApiClientTask, this.mTaskQueue);
    GoogleApiClientTask.access$402(paramGoogleApiClientTask, false);
    return this.mTaskQueue.offer(paramGoogleApiClientTask);
  }

  public boolean addTaskToBackground(GoogleApiClientTask paramGoogleApiClientTask)
  {
    if (!isStarted())
    {
      Log.d(TAG, "GoogleApiClientManager is not started.");
      return false;
    }
    GoogleApiClientTask.access$802(paramGoogleApiClientTask, this.mGoogleApiClient);
    GoogleApiClientTask.access$902(paramGoogleApiClientTask, this.mTaskQueue);
    GoogleApiClientTask.access$402(paramGoogleApiClientTask, true);
    return this.mTaskQueue.offer(paramGoogleApiClientTask);
  }

  public void onConnected(Bundle paramBundle)
  {
    initializeBgHandler();
    initializeDriverThread();
    if (this.listener != null)
      this.listener.onManagerStarted();
  }

  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    if (this.listener != null)
      this.listener.onGoogleApiConnectionError(paramConnectionResult);
    stop();
  }

  public void onConnectionSuspended(int paramInt)
  {
  }

  public void setManagerListener(ManagerListener paramManagerListener)
  {
    this.listener = paramManagerListener;
  }

  public void start()
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
    int j;
    if (i == 0)
    {
      j = 1;
      if (j == 0)
        break label78;
      this.mTaskQueue.clear();
      this.isStarted.set(true);
      if (!this.mGoogleApiClient.isConnected())
        break label56;
      onConnected(null);
    }
    label56: label78: 
    do
    {
      do
      {
        return;
        j = 0;
        break;
      }
      while (this.mGoogleApiClient.isConnecting());
      this.mGoogleApiClient.connect();
      return;
      Log.e(TAG, "Google Play Services is unavailable.");
    }
    while (this.listener == null);
    this.listener.onUnavailableGooglePlayServices(i);
  }

  public void stopSafely()
  {
    addTask(this.stopTask);
  }

  public static abstract class GoogleApiClientTask
    implements Runnable
  {
    private GoogleApiClient gApiClient;
    private boolean mRunOnBackgroundThread = false;
    private LinkedBlockingQueue<GoogleApiClientTask> taskQueue;

    protected abstract void doRun();

    protected GoogleApiClient getGoogleApiClient()
    {
      return this.gApiClient;
    }

    public void run()
    {
      if (!this.gApiClient.isConnected())
      {
        this.taskQueue.offer(this);
        return;
      }
      doRun();
    }
  }

  public static abstract interface ManagerListener
  {
    public abstract void onGoogleApiConnectionError(ConnectionResult paramConnectionResult);

    public abstract void onManagerStarted();

    public abstract void onManagerStopped();

    public abstract void onUnavailableGooglePlayServices(int paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager
 * JD-Core Version:    0.6.2
 */