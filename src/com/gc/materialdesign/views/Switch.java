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
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.utils.Utils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class Switch extends CustomView
{
  int backgroundColor = Color.parseColor("#4CAF50");
  Ball ball;
  boolean check = false;
  boolean eventCheck = false;
  OnCheckListener onCheckListener;
  boolean placedBall = false;
  boolean press = false;

  public Switch(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
    setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (Switch.this.check)
        {
          Switch.this.setChecked(false);
          return;
        }
        Switch.this.setChecked(true);
      }
    });
  }

  private void placeBall()
  {
    ViewHelper.setX(this.ball, getHeight() / 2 - this.ball.getWidth() / 2);
    this.ball.xIni = ViewHelper.getX(this.ball);
    this.ball.xFin = (getWidth() - getHeight() / 2 - this.ball.getWidth() / 2);
    this.ball.xCen = (getWidth() / 2 - this.ball.getWidth() / 2);
    this.placedBall = true;
    this.ball.animateCheck();
  }

  public boolean isCheck()
  {
    return this.check;
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
        break label86;
      n = 0;
      label55: if (k - 30 >= 0)
        break label95;
    }
    label86: label95: for (int i1 = 0; ; i1 = k - 30)
    {
      return Color.argb(70, m, n, i1);
      m = i - 30;
      break;
      n = j - 30;
      break label55;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (!this.placedBall)
      placeBall();
    Bitmap localBitmap = Bitmap.createBitmap(paramCanvas.getWidth(), paramCanvas.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint1 = new Paint();
    localPaint1.setAntiAlias(true);
    int i;
    if (this.check)
    {
      i = this.backgroundColor;
      localPaint1.setColor(i);
      localPaint1.setStrokeWidth(Utils.dpToPx(2.0F, getResources()));
      localCanvas.drawLine(getHeight() / 2, getHeight() / 2, getWidth() - getHeight() / 2, getHeight() / 2, localPaint1);
      Paint localPaint2 = new Paint();
      localPaint2.setAntiAlias(true);
      localPaint2.setColor(getResources().getColor(17170445));
      localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
      localCanvas.drawCircle(ViewHelper.getX(this.ball) + this.ball.getWidth() / 2, ViewHelper.getY(this.ball) + this.ball.getHeight() / 2, this.ball.getWidth() / 2, localPaint2);
      paramCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, new Paint());
      if (this.press)
        if (!this.check)
          break label319;
    }
    label319: for (int j = makePressColor(); ; j = Color.parseColor("#446D6D6D"))
    {
      localPaint1.setColor(j);
      paramCanvas.drawCircle(ViewHelper.getX(this.ball) + this.ball.getWidth() / 2, getHeight() / 2, getHeight() / 2, localPaint1);
      invalidate();
      return;
      i = Color.parseColor("#B0B0B0");
      break;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (isEnabled())
    {
      this.isLastTouch = true;
      if (paramMotionEvent.getAction() != 0)
        break label26;
      this.press = true;
    }
    label26: 
    do
    {
      do
      {
        return true;
        if (paramMotionEvent.getAction() == 2)
        {
          float f = paramMotionEvent.getX();
          if (f < this.ball.xIni)
            f = this.ball.xIni;
          if (f > this.ball.xFin)
            f = this.ball.xFin;
          if (f > this.ball.xCen);
          for (this.check = true; ; this.check = false)
          {
            ViewHelper.setX(this.ball, f);
            this.ball.changeBackground();
            if ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F))
              break;
            this.isLastTouch = false;
            this.press = false;
            return true;
          }
        }
      }
      while ((paramMotionEvent.getAction() != 1) && (paramMotionEvent.getAction() != 3));
      this.press = false;
      this.isLastTouch = false;
      if (this.eventCheck != this.check)
      {
        this.eventCheck = this.check;
        if (this.onCheckListener != null)
          this.onCheckListener.onCheck(this.check);
      }
    }
    while ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F));
    this.ball.animateCheck();
    return true;
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    setBackgroundResource(R.drawable.background_transparent);
    setMinimumHeight(Utils.dpToPx(48.0F, getResources()));
    setMinimumWidth(Utils.dpToPx(80.0F, getResources()));
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
      setBackgroundColor(getResources().getColor(i));
    while (true)
    {
      this.check = paramAttributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "check", false);
      this.eventCheck = this.check;
      this.ball = new Ball(getContext());
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(Utils.dpToPx(20.0F, getResources()), Utils.dpToPx(20.0F, getResources()));
      localLayoutParams.addRule(15, -1);
      this.ball.setLayoutParams(localLayoutParams);
      addView(this.ball);
      return;
      int j = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
      if (j != -1)
        setBackgroundColor(j);
    }
  }

  public void setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
  }

  public void setChecked(boolean paramBoolean)
  {
    this.check = paramBoolean;
    this.ball.animateCheck();
  }

  public void setOncheckListener(OnCheckListener paramOnCheckListener)
  {
    this.onCheckListener = paramOnCheckListener;
  }

  class Ball extends View
  {
    float xCen;
    float xFin;
    float xIni;

    public Ball(Context arg2)
    {
      super();
      setBackgroundResource(R.drawable.background_switch_ball_uncheck);
    }

    public void animateCheck()
    {
      changeBackground();
      float[] arrayOfFloat2;
      if (Switch.this.check)
      {
        arrayOfFloat2 = new float[1];
        arrayOfFloat2[0] = Switch.this.ball.xFin;
      }
      float[] arrayOfFloat1;
      for (ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, "x", arrayOfFloat2); ; localObjectAnimator = ObjectAnimator.ofFloat(this, "x", arrayOfFloat1))
      {
        localObjectAnimator.setDuration(300L);
        localObjectAnimator.start();
        return;
        arrayOfFloat1 = new float[1];
        arrayOfFloat1[0] = Switch.this.ball.xIni;
      }
    }

    public void changeBackground()
    {
      if (Switch.this.check)
      {
        setBackgroundResource(R.drawable.background_checkbox);
        ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(Switch.this.backgroundColor);
        return;
      }
      setBackgroundResource(R.drawable.background_switch_ball_uncheck);
    }
  }

  public static abstract interface OnCheckListener
  {
    public abstract void onCheck(boolean paramBoolean);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.Switch
 * JD-Core Version:    0.6.2
 */