package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzhd;
import com.google.android.gms.internal.zzhe;
import com.google.android.gms.internal.zzhf;
import com.google.android.gms.internal.zzhg;
import com.google.android.gms.internal.zzhg.zza;
import java.lang.ref.WeakReference;

public abstract class zza
{
  final zza zzSg;
  protected int zzSh = 0;
  protected int zzSi = 0;
  protected boolean zzSj = false;
  protected ImageManager.OnImageLoadedListener zzSk;
  private boolean zzSl = true;
  private boolean zzSm = false;
  private boolean zzSn = true;
  protected int zzSo;

  public zza(Uri paramUri, int paramInt)
  {
    this.zzSg = new zza(paramUri);
    this.zzSi = paramInt;
  }

  private Drawable zza(Context paramContext, zzhg paramzzhg, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    if (this.zzSo > 0)
    {
      zzhg.zza localzza = new zzhg.zza(paramInt, this.zzSo);
      Drawable localDrawable = (Drawable)paramzzhg.get(localzza);
      if (localDrawable == null)
      {
        localDrawable = localResources.getDrawable(paramInt);
        if ((0x1 & this.zzSo) != 0)
          localDrawable = zza(localResources, localDrawable);
        paramzzhg.put(localzza, localDrawable);
      }
      return localDrawable;
    }
    return localResources.getDrawable(paramInt);
  }

  protected Drawable zza(Resources paramResources, Drawable paramDrawable)
  {
    return zzhe.zza(paramResources, paramDrawable);
  }

  protected zzhd zza(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    if (paramDrawable1 != null)
      if (!(paramDrawable1 instanceof zzhd));
    for (paramDrawable1 = ((zzhd)paramDrawable1).zzlA(); ; paramDrawable1 = null)
      return new zzhd(paramDrawable1, paramDrawable2);
  }

  void zza(Context paramContext, Bitmap paramBitmap, boolean paramBoolean)
  {
    zzb.zzn(paramBitmap);
    if ((0x1 & this.zzSo) != 0)
      paramBitmap = zzhe.zza(paramBitmap);
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(paramContext.getResources(), paramBitmap);
    if (this.zzSk != null)
      this.zzSk.onImageLoaded(this.zzSg.uri, localBitmapDrawable, true);
    zza(localBitmapDrawable, paramBoolean, false, true);
  }

  void zza(Context paramContext, zzhg paramzzhg)
  {
    if (this.zzSn)
    {
      int i = this.zzSh;
      Drawable localDrawable = null;
      if (i != 0)
        localDrawable = zza(paramContext, paramzzhg, this.zzSh);
      zza(localDrawable, false, true, false);
    }
  }

  void zza(Context paramContext, zzhg paramzzhg, boolean paramBoolean)
  {
    int i = this.zzSi;
    Drawable localDrawable = null;
    if (i != 0)
      localDrawable = zza(paramContext, paramzzhg, this.zzSi);
    if (this.zzSk != null)
      this.zzSk.onImageLoaded(this.zzSg.uri, localDrawable, false);
    zza(localDrawable, paramBoolean, false, false);
  }

  protected abstract void zza(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);

  public void zzaI(int paramInt)
  {
    this.zzSi = paramInt;
  }

  protected boolean zzb(boolean paramBoolean1, boolean paramBoolean2)
  {
    return (this.zzSl) && (!paramBoolean2) && ((!paramBoolean1) || (this.zzSm));
  }

  static final class zza
  {
    public final Uri uri;

    public zza(Uri paramUri)
    {
      this.uri = paramUri;
    }

    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zza))
        return false;
      if (this == paramObject)
        return true;
      return zzu.equal(((zza)paramObject).uri, this.uri);
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.uri;
      return zzu.hashCode(arrayOfObject);
    }
  }

  public static final class zzb extends zza
  {
    private WeakReference<ImageView> zzSp;

    public zzb(ImageView paramImageView, int paramInt)
    {
      super(paramInt);
      zzb.zzn(paramImageView);
      this.zzSp = new WeakReference(paramImageView);
    }

    public zzb(ImageView paramImageView, Uri paramUri)
    {
      super(0);
      zzb.zzn(paramImageView);
      this.zzSp = new WeakReference(paramImageView);
    }

    private void zza(ImageView paramImageView, Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      if ((!paramBoolean2) && (!paramBoolean3));
      for (int i = 1; (i != 0) && ((paramImageView instanceof zzhf)); i = 0)
      {
        int k = ((zzhf)paramImageView).zzlC();
        if ((this.zzSi == 0) || (k != this.zzSi))
          break;
        return;
      }
      boolean bool = zzb(paramBoolean1, paramBoolean2);
      if ((this.zzSj) && (paramDrawable != null));
      for (Object localObject = paramDrawable.getConstantState().newDrawable(); ; localObject = paramDrawable)
      {
        if (bool)
          localObject = zza(paramImageView.getDrawable(), (Drawable)localObject);
        paramImageView.setImageDrawable((Drawable)localObject);
        zzhf localzzhf;
        Uri localUri;
        if ((paramImageView instanceof zzhf))
        {
          localzzhf = (zzhf)paramImageView;
          if (!paramBoolean3)
            break label178;
          localUri = this.zzSg.uri;
          label136: localzzhf.zzi(localUri);
          if (i == 0)
            break label184;
        }
        label178: label184: for (int j = this.zzSi; ; j = 0)
        {
          localzzhf.zzaK(j);
          if (!bool)
            break;
          ((zzhd)localObject).startTransition(250);
          return;
          localUri = null;
          break label136;
        }
      }
    }

    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zzb))
        return false;
      if (this == paramObject)
        return true;
      zzb localzzb = (zzb)paramObject;
      ImageView localImageView1 = (ImageView)this.zzSp.get();
      ImageView localImageView2 = (ImageView)localzzb.zzSp.get();
      if ((localImageView2 != null) && (localImageView1 != null) && (zzu.equal(localImageView2, localImageView1)));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public int hashCode()
    {
      return 0;
    }

    protected void zza(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      ImageView localImageView = (ImageView)this.zzSp.get();
      if (localImageView != null)
        zza(localImageView, paramDrawable, paramBoolean1, paramBoolean2, paramBoolean3);
    }
  }

  public static final class zzc extends zza
  {
    private WeakReference<ImageManager.OnImageLoadedListener> zzSq;

    public zzc(ImageManager.OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri)
    {
      super(0);
      zzb.zzn(paramOnImageLoadedListener);
      this.zzSq = new WeakReference(paramOnImageLoadedListener);
    }

    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zzc))
        return false;
      if (this == paramObject)
        return true;
      zzc localzzc = (zzc)paramObject;
      ImageManager.OnImageLoadedListener localOnImageLoadedListener1 = (ImageManager.OnImageLoadedListener)this.zzSq.get();
      ImageManager.OnImageLoadedListener localOnImageLoadedListener2 = (ImageManager.OnImageLoadedListener)localzzc.zzSq.get();
      if ((localOnImageLoadedListener2 != null) && (localOnImageLoadedListener1 != null) && (zzu.equal(localOnImageLoadedListener2, localOnImageLoadedListener1)) && (zzu.equal(localzzc.zzSg, this.zzSg)));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.zzSg;
      return zzu.hashCode(arrayOfObject);
    }

    protected void zza(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      if (!paramBoolean2)
      {
        ImageManager.OnImageLoadedListener localOnImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zzSq.get();
        if (localOnImageLoadedListener != null)
          localOnImageLoadedListener.onImageLoaded(this.zzSg.uri, paramDrawable, paramBoolean3);
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.images.zza
 * JD-Core Version:    0.6.2
 */