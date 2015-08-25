package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.internal.zzv;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class zzn
{
  private static final String TAG = zzn.class.getSimpleName();
  private static final long zzasV = TimeUnit.SECONDS.toMillis(1L);
  private static zzn zzasW;
  private final Context mContext;
  private final Handler mHandler;
  private final Runnable zzasX = new zza(null);
  private ArrayList<String> zzasY = null;
  private ArrayList<String> zzasZ = null;
  private final Object zzoe = new Object();

  private zzn(Context paramContext)
  {
    this((Context)zzv.zzr(paramContext), new Handler(Looper.getMainLooper()));
  }

  zzn(Context paramContext, Handler paramHandler)
  {
    this.mContext = paramContext;
    this.mHandler = paramHandler;
  }

  public static zzn zzag(Context paramContext)
  {
    try
    {
      zzv.zzr(paramContext);
      int i = Build.VERSION.SDK_INT;
      if (i < 14);
      for (zzn localzzn = null; ; localzzn = zzasW)
      {
        return localzzn;
        if (zzasW == null)
          zzasW = new zzn(paramContext.getApplicationContext());
      }
    }
    finally
    {
    }
  }

  public void zzz(String paramString1, String paramString2)
  {
    synchronized (this.zzoe)
    {
      if (this.zzasY == null)
      {
        this.zzasY = new ArrayList();
        this.zzasZ = new ArrayList();
        this.mHandler.postDelayed(this.zzasX, zzasV);
      }
      this.zzasY.add(paramString1);
      this.zzasZ.add(paramString2);
      if (this.zzasY.size() >= 10000)
      {
        if (Log.isLoggable(TAG, 5))
          Log.w(TAG, "Event buffer full, flushing");
        this.zzasX.run();
        this.mHandler.removeCallbacks(this.zzasX);
        return;
      }
      return;
    }
  }

  private class zza
    implements Runnable
  {
    private zza()
    {
    }

    public void run()
    {
      synchronized (zzn.zzb(zzn.this))
      {
        Intent localIntent = new Intent("com.google.android.location.places.METHOD_CALL");
        localIntent.setPackage("com.google.android.gms");
        localIntent.putStringArrayListExtra("PLACE_IDS", zzn.zzc(zzn.this));
        localIntent.putStringArrayListExtra("METHOD_NAMES", zzn.zzd(zzn.this));
        zzn.zze(zzn.this).sendBroadcast(localIntent);
        zzn.zza(zzn.this, null);
        zzn.zzb(zzn.this, null);
        return;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzn
 * JD-Core Version:    0.6.2
 */