package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.widget.ImageView;

public final class zzhf extends ImageView
{
  private Uri zzSJ;
  private int zzSK;
  private int zzSL;
  private zza zzSM;
  private int zzSN;
  private float zzSO;

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.zzSM != null)
      paramCanvas.clipPath(this.zzSM.zzi(getWidth(), getHeight()));
    super.onDraw(paramCanvas);
    if (this.zzSL != 0)
      paramCanvas.drawColor(this.zzSL);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int j;
    int i;
    switch (this.zzSN)
    {
    default:
      return;
    case 1:
      j = getMeasuredHeight();
      i = (int)(j * this.zzSO);
    case 2:
    }
    while (true)
    {
      setMeasuredDimension(i, j);
      return;
      i = getMeasuredWidth();
      j = (int)(i / this.zzSO);
    }
  }

  public void zzaK(int paramInt)
  {
    this.zzSK = paramInt;
  }

  public void zzi(Uri paramUri)
  {
    this.zzSJ = paramUri;
  }

  public int zzlC()
  {
    return this.zzSK;
  }

  public static abstract interface zza
  {
    public abstract Path zzi(int paramInt1, int paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhf
 * JD-Core Version:    0.6.2
 */