package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.gc.materialdesign.utils.Utils;

public class ProgressBarCircularIndeterminate extends CustomView
{
  static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
  int arcD = 1;
  int arcO = 0;
  int backgroundColor = Color.parseColor("#1E88E5");
  int cont = 0;
  boolean firstAnimationOver = false;
  int limite = 0;
  float radius1 = 0.0F;
  float radius2 = 0.0F;
  float rotateAngle = 0.0F;

  public ProgressBarCircularIndeterminate(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
  }

  private void drawFirstAnimation(Canvas paramCanvas)
  {
    if (this.radius1 < getWidth() / 2)
    {
      Paint localPaint1 = new Paint();
      localPaint1.setAntiAlias(true);
      localPaint1.setColor(makePressColor());
      if (this.radius1 >= getWidth() / 2);
      for (float f1 = getWidth() / 2.0F; ; f1 = 1.0F + this.radius1)
      {
        this.radius1 = f1;
        paramCanvas.drawCircle(getWidth() / 2, getHeight() / 2, this.radius1, localPaint1);
        return;
      }
    }
    Bitmap localBitmap = Bitmap.createBitmap(paramCanvas.getWidth(), paramCanvas.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint2 = new Paint();
    localPaint2.setAntiAlias(true);
    localPaint2.setColor(makePressColor());
    localCanvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, localPaint2);
    Paint localPaint3 = new Paint();
    localPaint3.setAntiAlias(true);
    localPaint3.setColor(getResources().getColor(17170445));
    localPaint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    if (this.cont >= 50)
    {
      if (this.radius2 >= getWidth() / 2);
      for (float f3 = getWidth() / 2.0F; ; f3 = 1.0F + this.radius2)
      {
        this.radius2 = f3;
        localCanvas.drawCircle(getWidth() / 2, getHeight() / 2, this.radius2, localPaint3);
        paramCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, new Paint());
        if (this.radius2 >= getWidth() / 2 - Utils.dpToPx(4.0F, getResources()))
          this.cont = (1 + this.cont);
        if (this.radius2 < getWidth() / 2)
          break;
        this.firstAnimationOver = true;
        return;
      }
    }
    if (this.radius2 >= getWidth() / 2 - Utils.dpToPx(4.0F, getResources()));
    for (float f2 = getWidth() / 2.0F - Utils.dpToPx(4.0F, getResources()); ; f2 = 1.0F + this.radius2)
    {
      this.radius2 = f2;
      break;
    }
  }

  private void drawSecondAnimation(Canvas paramCanvas)
  {
    if (this.arcO == this.limite)
      this.arcD = (6 + this.arcD);
    if ((this.arcD >= 290) || (this.arcO > this.limite))
    {
      this.arcO = (6 + this.arcO);
      this.arcD = (-6 + this.arcD);
    }
    if (this.arcO > 290 + this.limite)
    {
      this.limite = this.arcO;
      this.arcO = this.limite;
      this.arcD = 1;
    }
    this.rotateAngle = (4.0F + this.rotateAngle);
    paramCanvas.rotate(this.rotateAngle, getWidth() / 2, getHeight() / 2);
    Bitmap localBitmap = Bitmap.createBitmap(paramCanvas.getWidth(), paramCanvas.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint1 = new Paint();
    localPaint1.setAntiAlias(true);
    localPaint1.setColor(this.backgroundColor);
    localCanvas.drawArc(new RectF(0.0F, 0.0F, getWidth(), getHeight()), this.arcO, this.arcD, true, localPaint1);
    Paint localPaint2 = new Paint();
    localPaint2.setAntiAlias(true);
    localPaint2.setColor(getResources().getColor(17170445));
    localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    localCanvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - Utils.dpToPx(4.0F, getResources()), localPaint2);
    paramCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, new Paint());
  }

  protected int makePressColor()
  {
    return Color.argb(128, 0xFF & this.backgroundColor >> 16, 0xFF & this.backgroundColor >> 8, 0xFF & this.backgroundColor >> 0);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (!this.firstAnimationOver)
      drawFirstAnimation(paramCanvas);
    if (this.cont > 0)
      drawSecondAnimation(paramCanvas);
    invalidate();
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    setMinimumHeight(Utils.dpToPx(32.0F, getResources()));
    setMinimumWidth(Utils.dpToPx(32.0F, getResources()));
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
      setBackgroundColor(getResources().getColor(i));
    while (true)
    {
      setMinimumHeight(Utils.dpToPx(3.0F, getResources()));
      return;
      int j = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
      if (j != -1)
        setBackgroundColor(j);
      else
        setBackgroundColor(Color.parseColor("#1E88E5"));
    }
  }

  public void setBackgroundColor(int paramInt)
  {
    super.setBackgroundColor(getResources().getColor(17170445));
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
    this.backgroundColor = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ProgressBarCircularIndeterminate
 * JD-Core Version:    0.6.2
 */