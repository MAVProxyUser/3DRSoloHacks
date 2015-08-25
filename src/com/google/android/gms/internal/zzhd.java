package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;

public final class zzhd extends Drawable
  implements Drawable.Callback
{
  private int mFrom;
  private long zzGX;
  private Drawable zzSA;
  private boolean zzSB;
  private boolean zzSC;
  private boolean zzSD;
  private int zzSE;
  private boolean zzSl = true;
  private int zzSs = 0;
  private int zzSt;
  private int zzSu = 255;
  private int zzSv;
  private int zzSw = 0;
  private boolean zzSx;
  private zzb zzSy;
  private Drawable zzSz;

  public zzhd(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    this(null);
    if (paramDrawable1 == null)
      paramDrawable1 = zza.zzlB();
    this.zzSz = paramDrawable1;
    paramDrawable1.setCallback(this);
    zzb localzzb1 = this.zzSy;
    localzzb1.zzSI |= paramDrawable1.getChangingConfigurations();
    if (paramDrawable2 == null)
      paramDrawable2 = zza.zzlB();
    this.zzSA = paramDrawable2;
    paramDrawable2.setCallback(this);
    zzb localzzb2 = this.zzSy;
    localzzb2.zzSI |= paramDrawable2.getChangingConfigurations();
  }

  zzhd(zzb paramzzb)
  {
    this.zzSy = new zzb(paramzzb);
  }

  public boolean canConstantState()
  {
    if (!this.zzSB)
      if ((this.zzSz.getConstantState() == null) || (this.zzSA.getConstantState() == null))
        break label44;
    label44: for (boolean bool = true; ; bool = false)
    {
      this.zzSC = bool;
      this.zzSB = true;
      return this.zzSC;
    }
  }

  public void draw(Canvas paramCanvas)
  {
    int i = 1;
    switch (this.zzSs)
    {
    default:
    case 1:
    case 2:
    }
    int k;
    boolean bool;
    Drawable localDrawable1;
    Drawable localDrawable2;
    do
      for (int j = i; ; j = 0)
      {
        k = this.zzSw;
        bool = this.zzSl;
        localDrawable1 = this.zzSz;
        localDrawable2 = this.zzSA;
        if (j == 0)
          break;
        if ((!bool) || (k == 0))
          localDrawable1.draw(paramCanvas);
        if (k == this.zzSu)
        {
          localDrawable2.setAlpha(this.zzSu);
          localDrawable2.draw(paramCanvas);
        }
        return;
        this.zzGX = SystemClock.uptimeMillis();
        this.zzSs = 2;
      }
    while (this.zzGX < 0L);
    float f1 = (float)(SystemClock.uptimeMillis() - this.zzGX) / this.zzSv;
    if (f1 >= 1.0F);
    while (true)
    {
      if (i != 0)
        this.zzSs = 0;
      float f2 = Math.min(f1, 1.0F);
      this.zzSw = ((int)(this.mFrom + f2 * (this.zzSt - this.mFrom)));
      break;
      i = 0;
    }
    if (bool)
      localDrawable1.setAlpha(this.zzSu - k);
    localDrawable1.draw(paramCanvas);
    if (bool)
      localDrawable1.setAlpha(this.zzSu);
    if (k > 0)
    {
      localDrawable2.setAlpha(k);
      localDrawable2.draw(paramCanvas);
      localDrawable2.setAlpha(this.zzSu);
    }
    invalidateSelf();
  }

  public int getChangingConfigurations()
  {
    return super.getChangingConfigurations() | this.zzSy.zzSH | this.zzSy.zzSI;
  }

  public Drawable.ConstantState getConstantState()
  {
    if (canConstantState())
    {
      this.zzSy.zzSH = getChangingConfigurations();
      return this.zzSy;
    }
    return null;
  }

  public int getIntrinsicHeight()
  {
    return Math.max(this.zzSz.getIntrinsicHeight(), this.zzSA.getIntrinsicHeight());
  }

  public int getIntrinsicWidth()
  {
    return Math.max(this.zzSz.getIntrinsicWidth(), this.zzSA.getIntrinsicWidth());
  }

  public int getOpacity()
  {
    if (!this.zzSD)
    {
      this.zzSE = Drawable.resolveOpacity(this.zzSz.getOpacity(), this.zzSA.getOpacity());
      this.zzSD = true;
    }
    return this.zzSE;
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    if (zzic.zzne())
    {
      Drawable.Callback localCallback = getCallback();
      if (localCallback != null)
        localCallback.invalidateDrawable(this);
    }
  }

  public Drawable mutate()
  {
    if ((!this.zzSx) && (super.mutate() == this))
    {
      if (!canConstantState())
        throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
      this.zzSz.mutate();
      this.zzSA.mutate();
      this.zzSx = true;
    }
    return this;
  }

  protected void onBoundsChange(Rect paramRect)
  {
    this.zzSz.setBounds(paramRect);
    this.zzSA.setBounds(paramRect);
  }

  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    if (zzic.zzne())
    {
      Drawable.Callback localCallback = getCallback();
      if (localCallback != null)
        localCallback.scheduleDrawable(this, paramRunnable, paramLong);
    }
  }

  public void setAlpha(int paramInt)
  {
    if (this.zzSw == this.zzSu)
      this.zzSw = paramInt;
    this.zzSu = paramInt;
    invalidateSelf();
  }

  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.zzSz.setColorFilter(paramColorFilter);
    this.zzSA.setColorFilter(paramColorFilter);
  }

  public void startTransition(int paramInt)
  {
    this.mFrom = 0;
    this.zzSt = this.zzSu;
    this.zzSw = 0;
    this.zzSv = paramInt;
    this.zzSs = 1;
    invalidateSelf();
  }

  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    if (zzic.zzne())
    {
      Drawable.Callback localCallback = getCallback();
      if (localCallback != null)
        localCallback.unscheduleDrawable(this, paramRunnable);
    }
  }

  public Drawable zzlA()
  {
    return this.zzSA;
  }

  private static final class zza extends Drawable
  {
    private static final zza zzSF = new zza();
    private static final zza zzSG = new zza(null);

    public void draw(Canvas paramCanvas)
    {
    }

    public Drawable.ConstantState getConstantState()
    {
      return zzSG;
    }

    public int getOpacity()
    {
      return -2;
    }

    public void setAlpha(int paramInt)
    {
    }

    public void setColorFilter(ColorFilter paramColorFilter)
    {
    }

    private static final class zza extends Drawable.ConstantState
    {
      public int getChangingConfigurations()
      {
        return 0;
      }

      public Drawable newDrawable()
      {
        return zzhd.zza.zzlB();
      }
    }
  }

  static final class zzb extends Drawable.ConstantState
  {
    int zzSH;
    int zzSI;

    zzb(zzb paramzzb)
    {
      if (paramzzb != null)
      {
        this.zzSH = paramzzb.zzSH;
        this.zzSI = paramzzb.zzSI;
      }
    }

    public int getChangingConfigurations()
    {
      return this.zzSH;
    }

    public Drawable newDrawable()
    {
      return new zzhd(this);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhd
 * JD-Core Version:    0.6.2
 */