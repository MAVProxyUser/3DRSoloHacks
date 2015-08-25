package com.google.android.gms.internal;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public final class zzhe
{
  public static Bitmap zza(Bitmap paramBitmap)
  {
    if (paramBitmap == null)
      return null;
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int m;
    int k;
    if (i >= j)
    {
      m = i / 2 - j / 2;
      k = 0;
    }
    while (true)
    {
      Bitmap localBitmap = Bitmap.createBitmap(j, j, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint(1);
      localPaint.setColor(-16777216);
      localCanvas.drawCircle(j / 2, j / 2, j / 2, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, m, k, localPaint);
      return localBitmap;
      k = j / 2 - i / 2;
      j = i;
      m = 0;
    }
  }

  public static Drawable zza(Resources paramResources, Drawable paramDrawable)
  {
    return new BitmapDrawable(paramResources, zza(zzb(paramDrawable)));
  }

  private static Bitmap zzb(Drawable paramDrawable)
  {
    if (paramDrawable == null)
      return null;
    if ((paramDrawable instanceof BitmapDrawable))
      return ((BitmapDrawable)paramDrawable).getBitmap();
    Bitmap localBitmap = Bitmap.createBitmap(paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    paramDrawable.setBounds(0, 0, localCanvas.getWidth(), localCanvas.getHeight());
    paramDrawable.draw(localCanvas);
    return localBitmap;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhe
 * JD-Core Version:    0.6.2
 */