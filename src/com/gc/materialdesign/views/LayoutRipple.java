package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.OnClickListener;

public class LayoutRipple extends CustomView
{
  int background;
  int backgroundColor = Color.parseColor("#FFFFFF");
  View.OnClickListener onClickListener;
  float radius = -1.0F;
  Integer rippleColor;
  int rippleSize = 3;
  float rippleSpeed = 10.0F;
  float x = -1.0F;
  Float xRippleOrigin;
  float y = -1.0F;
  Float yRippleOrigin;

  public LayoutRipple(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
  }

  public Bitmap makeCircle()
  {
    Bitmap localBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawARGB(0, 0, 0, 0);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    if (this.rippleColor == null)
      this.rippleColor = Integer.valueOf(makePressColor());
    localPaint.setColor(this.rippleColor.intValue());
    float f1;
    if (this.xRippleOrigin == null)
    {
      f1 = this.x;
      this.x = f1;
      if (this.yRippleOrigin != null)
        break label230;
    }
    label230: for (float f2 = this.y; ; f2 = this.yRippleOrigin.floatValue())
    {
      this.y = f2;
      localCanvas.drawCircle(this.x, this.y, this.radius, localPaint);
      if (this.radius > getHeight() / this.rippleSize)
        this.radius += this.rippleSpeed;
      if (this.radius >= getWidth())
      {
        this.x = -1.0F;
        this.y = -1.0F;
        this.radius = (getHeight() / this.rippleSize);
        if (this.onClickListener != null)
          this.onClickListener.onClick(this);
      }
      return localBitmap;
      f1 = this.xRippleOrigin.floatValue();
      break;
    }
  }

  protected int makePressColor()
  {
    int i = 0xFF & this.backgroundColor >> 16;
    int j = 0xFF & this.backgroundColor >> 8;
    int k = 0xFF & this.backgroundColor >> 0;
    int m;
    int n;
    if (i - 30 < 0)
    {
      m = 0;
      if (j - 30 >= 0)
        break label84;
      n = 0;
      label55: if (k - 30 >= 0)
        break label93;
    }
    label84: label93: for (int i1 = 0; ; i1 = k - 30)
    {
      return Color.rgb(m, n, i1);
      m = i - 30;
      break;
      n = j - 30;
      break label55;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.x != -1.0F)
    {
      Rect localRect1 = new Rect(0, 0, getWidth(), getHeight());
      Rect localRect2 = new Rect(0, 0, getWidth(), getHeight());
      paramCanvas.drawBitmap(makeCircle(), localRect1, localRect2, null);
      invalidate();
    }
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if (!paramBoolean)
    {
      this.x = -1.0F;
      this.y = -1.0F;
    }
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return true;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    invalidate();
    if (isEnabled())
    {
      this.isLastTouch = true;
      if (paramMotionEvent.getAction() != 0)
        break label80;
      this.radius = (getHeight() / this.rippleSize);
      this.x = paramMotionEvent.getX();
      this.y = paramMotionEvent.getY();
    }
    while (true)
    {
      if (paramMotionEvent.getAction() == 3)
      {
        this.isLastTouch = false;
        this.x = -1.0F;
        this.y = -1.0F;
      }
      return true;
      label80: if (paramMotionEvent.getAction() == 2)
      {
        this.radius = (getHeight() / this.rippleSize);
        this.x = paramMotionEvent.getX();
        this.y = paramMotionEvent.getY();
        if ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getY() > getHeight()) || (paramMotionEvent.getY() < 0.0F))
        {
          this.isLastTouch = false;
          this.x = -1.0F;
          this.y = -1.0F;
        }
      }
      else if (paramMotionEvent.getAction() == 1)
      {
        if ((paramMotionEvent.getX() <= getWidth()) && (paramMotionEvent.getX() >= 0.0F) && (paramMotionEvent.getY() <= getHeight()) && (paramMotionEvent.getY() >= 0.0F))
        {
          this.radius = (1.0F + this.radius);
        }
        else
        {
          this.isLastTouch = false;
          this.x = -1.0F;
          this.y = -1.0F;
        }
      }
    }
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
    {
      setBackgroundColor(getResources().getColor(i));
      int j = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "rippleColor", -1);
      if (j == -1)
        break label120;
      setRippleColor(getResources().getColor(j));
    }
    while (true)
    {
      this.rippleSpeed = paramAttributeSet.getAttributeFloatValue("http://schemas.android.com/apk/res-auto", "rippleSpeed", 20.0F);
      return;
      this.background = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
      if (this.background != -1)
      {
        setBackgroundColor(this.background);
        break;
      }
      setBackgroundColor(this.backgroundColor);
      break;
      label120: int k = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "rippleColor", -1);
      if (k != -1)
        setRippleColor(k);
      else
        setRippleColor(makePressColor());
    }
  }

  public void setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
    super.setBackgroundColor(paramInt);
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.onClickListener = paramOnClickListener;
  }

  public void setRippleColor(int paramInt)
  {
    this.rippleColor = Integer.valueOf(paramInt);
  }

  public void setRippleSpeed(int paramInt)
  {
    this.rippleSpeed = paramInt;
  }

  public void setxRippleOrigin(Float paramFloat)
  {
    this.xRippleOrigin = paramFloat;
  }

  public void setyRippleOrigin(Float paramFloat)
  {
    this.yRippleOrigin = paramFloat;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.LayoutRipple
 * JD-Core Version:    0.6.2
 */