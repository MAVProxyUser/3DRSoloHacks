package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.utils.Utils;

public abstract class Button extends CustomView
{
  static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
  int background;
  int backgroundColor = Color.parseColor("#1E88E5");
  boolean clickAfterRipple = true;
  int minHeight;
  int minWidth;
  View.OnClickListener onClickListener;
  float radius = -1.0F;
  Integer rippleColor;
  int rippleSize = 3;
  float rippleSpeed = 12.0F;
  float x = -1.0F;
  float y = -1.0F;

  public Button(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setDefaultProperties();
    this.clickAfterRipple = paramAttributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "animate", true);
    setAttributes(paramAttributeSet);
    this.beforeBackground = this.backgroundColor;
    if (this.rippleColor == null)
      this.rippleColor = Integer.valueOf(makePressColor());
  }

  public float getRippleSpeed()
  {
    return this.rippleSpeed;
  }

  public abstract TextView getTextView();

  public Bitmap makeCircle()
  {
    Bitmap localBitmap = Bitmap.createBitmap(getWidth() - Utils.dpToPx(6.0F, getResources()), getHeight() - Utils.dpToPx(7.0F, getResources()), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawARGB(0, 0, 0, 0);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setColor(this.rippleColor.intValue());
    localCanvas.drawCircle(this.x, this.y, this.radius, localPaint);
    if (this.radius > getHeight() / this.rippleSize)
      this.radius += this.rippleSpeed;
    if (this.radius >= getWidth())
    {
      this.x = -1.0F;
      this.y = -1.0F;
      this.radius = (getHeight() / this.rippleSize);
      if ((this.onClickListener != null) && (this.clickAfterRipple))
        this.onClickListener.onClick(this);
    }
    return localBitmap;
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
        break label55;
      this.radius = (getHeight() / this.rippleSize);
      this.x = paramMotionEvent.getX();
      this.y = paramMotionEvent.getY();
    }
    label55: 
    do
    {
      do
      {
        do
        {
          return true;
          if (paramMotionEvent.getAction() != 2)
            break;
          this.radius = (getHeight() / this.rippleSize);
          this.x = paramMotionEvent.getX();
          this.y = paramMotionEvent.getY();
        }
        while ((paramMotionEvent.getX() <= getWidth()) && (paramMotionEvent.getX() >= 0.0F) && (paramMotionEvent.getY() <= getHeight()) && (paramMotionEvent.getY() >= 0.0F));
        this.isLastTouch = false;
        this.x = -1.0F;
        this.y = -1.0F;
        return true;
        if (paramMotionEvent.getAction() != 1)
          break label263;
        if ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getY() > getHeight()) || (paramMotionEvent.getY() < 0.0F))
          break;
        this.radius = (1.0F + this.radius);
      }
      while ((this.clickAfterRipple) || (this.onClickListener == null));
      this.onClickListener.onClick(this);
      return true;
      this.isLastTouch = false;
      this.x = -1.0F;
      this.y = -1.0F;
      return true;
    }
    while (paramMotionEvent.getAction() != 3);
    label263: this.isLastTouch = false;
    this.x = -1.0F;
    this.y = -1.0F;
    return true;
  }

  protected abstract void setAttributes(AttributeSet paramAttributeSet);

  public void setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
    try
    {
      ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(this.backgroundColor);
      this.rippleColor = Integer.valueOf(makePressColor());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  protected void setDefaultProperties()
  {
    setMinimumHeight(Utils.dpToPx(this.minHeight, getResources()));
    setMinimumWidth(Utils.dpToPx(this.minWidth, getResources()));
    setBackgroundResource(this.background);
    setBackgroundColor(this.backgroundColor);
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.onClickListener = paramOnClickListener;
  }

  public void setRippleSpeed(float paramFloat)
  {
    this.rippleSpeed = paramFloat;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.Button
 * JD-Core Version:    0.6.2
 */