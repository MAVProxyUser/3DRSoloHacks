package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.internal.zzhg;
import com.google.android.gms.internal.zzic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager
{
  private static final Object zzRR = new Object();
  private static HashSet<Uri> zzRS = new HashSet();
  private static ImageManager zzRT;
  private static ImageManager zzRU;
  private final Context mContext;
  private final Handler mHandler;
  private final ExecutorService zzRV;
  private final ImageManager.zzb zzRW;
  private final zzhg zzRX;
  private final Map<zza, ImageReceiver> zzRY;
  private final Map<Uri, ImageReceiver> zzRZ;
  private final Map<Uri, Long> zzSa;

  private ImageManager(Context paramContext, boolean paramBoolean)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mHandler = new Handler(Looper.getMainLooper());
    this.zzRV = Executors.newFixedThreadPool(4);
    if (paramBoolean)
    {
      this.zzRW = new ImageManager.zzb(this.mContext);
      if (zzic.zznh())
        zzlw();
    }
    while (true)
    {
      this.zzRX = new zzhg();
      this.zzRY = new HashMap();
      this.zzRZ = new HashMap();
      this.zzSa = new HashMap();
      return;
      this.zzRW = null;
    }
  }

  public static ImageManager create(Context paramContext)
  {
    return zzb(paramContext, false);
  }

  private Bitmap zza(zza.zza paramzza)
  {
    if (this.zzRW == null)
      return null;
    return (Bitmap)this.zzRW.get(paramzza);
  }

  public static ImageManager zzb(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (zzRU == null)
        zzRU = new ImageManager(paramContext, true);
      return zzRU;
    }
    if (zzRT == null)
      zzRT = new ImageManager(paramContext, false);
    return zzRT;
  }

  private void zzlw()
  {
    this.mContext.registerComponentCallbacks(new zze(this.zzRW));
  }

  public void loadImage(ImageView paramImageView, int paramInt)
  {
    zza(new zza.zzb(paramImageView, paramInt));
  }

  public void loadImage(ImageView paramImageView, Uri paramUri)
  {
    zza(new zza.zzb(paramImageView, paramUri));
  }

  public void loadImage(ImageView paramImageView, Uri paramUri, int paramInt)
  {
    zza.zzb localzzb = new zza.zzb(paramImageView, paramUri);
    localzzb.zzaI(paramInt);
    zza(localzzb);
  }

  public void loadImage(OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri)
  {
    zza(new zza.zzc(paramOnImageLoadedListener, paramUri));
  }

  public void loadImage(OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri, int paramInt)
  {
    zza.zzc localzzc = new zza.zzc(paramOnImageLoadedListener, paramUri);
    localzzc.zzaI(paramInt);
    zza(localzzc);
  }

  public void zza(zza paramzza)
  {
    zzb.zzbI("ImageManager.loadImage() must be called in the main thread");
    new zzd(paramzza).run();
  }

  private final class ImageReceiver extends ResultReceiver
  {
    private final Uri mUri;
    private final ArrayList<zza> zzSb;

    ImageReceiver(Uri arg2)
    {
      super();
      Object localObject;
      this.mUri = localObject;
      this.zzSb = new ArrayList();
    }

    public void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      ParcelFileDescriptor localParcelFileDescriptor = (ParcelFileDescriptor)paramBundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
      ImageManager.zzf(ImageManager.this).execute(new ImageManager.zzc(ImageManager.this, this.mUri, localParcelFileDescriptor));
    }

    public void zzb(zza paramzza)
    {
      zzb.zzbI("ImageReceiver.addImageRequest() must be called in the main thread");
      this.zzSb.add(paramzza);
    }

    public void zzc(zza paramzza)
    {
      zzb.zzbI("ImageReceiver.removeImageRequest() must be called in the main thread");
      this.zzSb.remove(paramzza);
    }

    public void zzlz()
    {
      Intent localIntent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
      localIntent.putExtra("com.google.android.gms.extras.uri", this.mUri);
      localIntent.putExtra("com.google.android.gms.extras.resultReceiver", this);
      localIntent.putExtra("com.google.android.gms.extras.priority", 3);
      ImageManager.zzb(ImageManager.this).sendBroadcast(localIntent);
    }
  }

  public static abstract interface OnImageLoadedListener
  {
    public abstract void onImageLoaded(Uri paramUri, Drawable paramDrawable, boolean paramBoolean);
  }

  private static final class zza
  {
    static int zza(ActivityManager paramActivityManager)
    {
      return paramActivityManager.getLargeMemoryClass();
    }
  }

  private final class zzc
    implements Runnable
  {
    private final Uri mUri;
    private final ParcelFileDescriptor zzSd;

    public zzc(Uri paramParcelFileDescriptor, ParcelFileDescriptor arg3)
    {
      this.mUri = paramParcelFileDescriptor;
      Object localObject;
      this.zzSd = localObject;
    }

    public void run()
    {
      zzb.zzbJ("LoadBitmapFromDiskRunnable can't be executed in the main thread");
      ParcelFileDescriptor localParcelFileDescriptor = this.zzSd;
      Object localObject = null;
      boolean bool = false;
      if (localParcelFileDescriptor != null);
      try
      {
        Bitmap localBitmap = BitmapFactory.decodeFileDescriptor(this.zzSd.getFileDescriptor());
        localObject = localBitmap;
      }
      catch (OutOfMemoryError localIOException)
      {
        try
        {
          this.zzSd.close();
          localCountDownLatch = new CountDownLatch(1);
          ImageManager.zzg(ImageManager.this).post(new ImageManager.zzf(ImageManager.this, this.mUri, localObject, bool, localCountDownLatch));
        }
        catch (IOException localIOException)
        {
          try
          {
            while (true)
            {
              CountDownLatch localCountDownLatch;
              localCountDownLatch.await();
              return;
              localOutOfMemoryError = localOutOfMemoryError;
              Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, localOutOfMemoryError);
              bool = true;
              localObject = null;
            }
            localIOException = localIOException;
            Log.e("ImageManager", "closed failed", localIOException);
          }
          catch (InterruptedException localInterruptedException)
          {
            Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
          }
        }
      }
    }
  }

  private final class zzd
    implements Runnable
  {
    private final zza zzSe;

    public zzd(zza arg2)
    {
      Object localObject;
      this.zzSe = localObject;
    }

    public void run()
    {
      zzb.zzbI("LoadImageRunnable must be executed on the main thread");
      ImageManager.ImageReceiver localImageReceiver1 = (ImageManager.ImageReceiver)ImageManager.zza(ImageManager.this).get(this.zzSe);
      if (localImageReceiver1 != null)
      {
        ImageManager.zza(ImageManager.this).remove(this.zzSe);
        localImageReceiver1.zzc(this.zzSe);
      }
      zza.zza localzza = this.zzSe.zzSg;
      if (localzza.uri == null)
      {
        this.zzSe.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this), true);
        return;
      }
      Bitmap localBitmap = ImageManager.zza(ImageManager.this, localzza);
      if (localBitmap != null)
      {
        this.zzSe.zza(ImageManager.zzb(ImageManager.this), localBitmap, true);
        return;
      }
      Long localLong = (Long)ImageManager.zzd(ImageManager.this).get(localzza.uri);
      if (localLong != null)
      {
        if (SystemClock.elapsedRealtime() - localLong.longValue() < 3600000L)
        {
          this.zzSe.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this), true);
          return;
        }
        ImageManager.zzd(ImageManager.this).remove(localzza.uri);
      }
      this.zzSe.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this));
      ImageManager.ImageReceiver localImageReceiver2 = (ImageManager.ImageReceiver)ImageManager.zze(ImageManager.this).get(localzza.uri);
      if (localImageReceiver2 == null)
      {
        localImageReceiver2 = new ImageManager.ImageReceiver(ImageManager.this, localzza.uri);
        ImageManager.zze(ImageManager.this).put(localzza.uri, localImageReceiver2);
      }
      localImageReceiver2.zzb(this.zzSe);
      if (!(this.zzSe instanceof zza.zzc))
        ImageManager.zza(ImageManager.this).put(this.zzSe, localImageReceiver2);
      synchronized (ImageManager.zzlx())
      {
        if (!ImageManager.zzly().contains(localzza.uri))
        {
          ImageManager.zzly().add(localzza.uri);
          localImageReceiver2.zzlz();
        }
        return;
      }
    }
  }

  private static final class zze
    implements ComponentCallbacks2
  {
    private final ImageManager.zzb zzRW;

    public zze(ImageManager.zzb paramzzb)
    {
      this.zzRW = paramzzb;
    }

    public void onConfigurationChanged(Configuration paramConfiguration)
    {
    }

    public void onLowMemory()
    {
      this.zzRW.evictAll();
    }

    public void onTrimMemory(int paramInt)
    {
      if (paramInt >= 60)
        this.zzRW.evictAll();
      while (paramInt < 20)
        return;
      this.zzRW.trimToSize(this.zzRW.size() / 2);
    }
  }

  private final class zzf
    implements Runnable
  {
    private final Bitmap mBitmap;
    private final Uri mUri;
    private boolean zzSf;
    private final CountDownLatch zzns;

    public zzf(Uri paramBitmap, Bitmap paramBoolean, boolean paramCountDownLatch, CountDownLatch arg5)
    {
      this.mUri = paramBitmap;
      this.mBitmap = paramBoolean;
      this.zzSf = paramCountDownLatch;
      Object localObject;
      this.zzns = localObject;
    }

    private void zza(ImageManager.ImageReceiver paramImageReceiver, boolean paramBoolean)
    {
      ArrayList localArrayList = ImageManager.ImageReceiver.zza(paramImageReceiver);
      int i = localArrayList.size();
      int j = 0;
      if (j < i)
      {
        zza localzza = (zza)localArrayList.get(j);
        if (paramBoolean)
          localzza.zza(ImageManager.zzb(ImageManager.this), this.mBitmap, false);
        while (true)
        {
          if (!(localzza instanceof zza.zzc))
            ImageManager.zza(ImageManager.this).remove(localzza);
          j++;
          break;
          ImageManager.zzd(ImageManager.this).put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
          localzza.zza(ImageManager.zzb(ImageManager.this), ImageManager.zzc(ImageManager.this), false);
        }
      }
    }

    public void run()
    {
      zzb.zzbI("OnBitmapLoadedRunnable must be executed in the main thread");
      boolean bool;
      if (this.mBitmap != null)
        bool = true;
      while (ImageManager.zzh(ImageManager.this) != null)
        if (this.zzSf)
        {
          ImageManager.zzh(ImageManager.this).evictAll();
          System.gc();
          this.zzSf = false;
          ImageManager.zzg(ImageManager.this).post(this);
          return;
          bool = false;
        }
        else if (bool)
        {
          ImageManager.zzh(ImageManager.this).put(new zza.zza(this.mUri), this.mBitmap);
        }
      ImageManager.ImageReceiver localImageReceiver = (ImageManager.ImageReceiver)ImageManager.zze(ImageManager.this).remove(this.mUri);
      if (localImageReceiver != null)
        zza(localImageReceiver, bool);
      this.zzns.countDown();
      synchronized (ImageManager.zzlx())
      {
        ImageManager.zzly().remove(this.mUri);
        return;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.images.ImageManager
 * JD-Core Version:    0.6.2
 */