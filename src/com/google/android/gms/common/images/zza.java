package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzhd;
import com.google.android.gms.internal.zzhe;
import com.google.android.gms.internal.zzhg;
import com.google.android.gms.internal.zzhg.zza;

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
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.images.zza
 * JD-Core Version:    0.6.2
 */