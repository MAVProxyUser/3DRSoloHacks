package com.gc.materialdesign.views;

import android.app.Dialog;
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
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.R.layout;
import com.gc.materialdesign.utils.Utils;
import com.nineoldandroids.view.ViewHelper;

public class Slider extends CustomView
{
  int backgroundColor = Color.parseColor("#4CAF50");
  Ball ball;
  int max = 100;
  int min = 0;
  NumberIndicator numberIndicator;
  OnValueChangedListener onValueChangedListener;
  boolean placedBall = false;
  boolean press = false;
  boolean showNumberIndicator = false;
  int value = 0;

  public Slider(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
  }

  private void placeBall()
  {
    ViewHelper.setX(this.ball, getHeight() / 2 - this.ball.getWidth() / 2);
    this.ball.xIni = ViewHelper.getX(this.ball);
    this.ball.xFin = (getWidth() - getHeight() / 2 - this.ball.getWidth() / 2);
    this.ball.xCen = (getWidth() / 2 - this.ball.getWidth() / 2);
    this.placedBall = true;
  }

  public int getMax()
  {
    return this.max;
  }

  public int getMin()
  {
    return this.min;
  }

  public OnValueChangedListener getOnValueChangedListener()
  {
    return this.onValueChangedListener;
  }

  public int getValue()
  {
    return this.value;
  }

  public void invalidate()
  {
    this.ball.invalidate();
    super.invalidate();
  }

  public boolean isShowNumberIndicator()
  {
    return this.showNumberIndicator;
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
    if (this.value == this.min)
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramCanvas.getWidth(), paramCanvas.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint3 = new Paint();
      localPaint3.setColor(Color.parseColor("#B0B0B0"));
      localPaint3.setStrokeWidth(Utils.dpToPx(2.0F, getResources()));
      localCanvas.drawLine(getHeight() / 2, getHeight() / 2, getWidth() - getHeight() / 2, getHeight() / 2, localPaint3);
      Paint localPaint4 = new Paint();
      localPaint4.setColor(getResources().getColor(17170445));
      localPaint4.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
      localCanvas.drawCircle(ViewHelper.getX(this.ball) + this.ball.getWidth() / 2, ViewHelper.getY(this.ball) + this.ball.getHeight() / 2, this.ball.getWidth() / 2, localPaint4);
      paramCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, new Paint());
    }
    while (true)
    {
      if ((this.press) && (!this.showNumberIndicator))
      {
        Paint localPaint2 = new Paint();
        localPaint2.setColor(this.backgroundColor);
        localPaint2.setAntiAlias(true);
        paramCanvas.drawCircle(ViewHelper.getX(this.ball) + this.ball.getWidth() / 2, getHeight() / 2, getHeight() / 3, localPaint2);
      }
      invalidate();
      return;
      Paint localPaint1 = new Paint();
      localPaint1.setColor(Color.parseColor("#B0B0B0"));
      localPaint1.setStrokeWidth(Utils.dpToPx(2.0F, getResources()));
      paramCanvas.drawLine(getHeight() / 2, getHeight() / 2, getWidth() - getHeight() / 2, getHeight() / 2, localPaint1);
      localPaint1.setColor(this.backgroundColor);
      float f = (this.ball.xFin - this.ball.xIni) / (this.max - this.min);
      int i = this.value - this.min;
      paramCanvas.drawLine(getHeight() / 2, getHeight() / 2, f * i + getHeight() / 2, getHeight() / 2, localPaint1);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.isLastTouch = true;
    if (isEnabled())
    {
      if ((paramMotionEvent.getAction() != 0) && (paramMotionEvent.getAction() != 2))
        break label366;
      if ((this.numberIndicator != null) && (!this.numberIndicator.isShowing()))
        this.numberIndicator.show();
      if ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F))
        break label340;
      this.press = true;
      f1 = (this.ball.xFin - this.ball.xIni) / (this.max - this.min);
      if (paramMotionEvent.getX() <= this.ball.xFin)
        break label293;
      i = this.max;
      if (this.value != i)
      {
        this.value = i;
        if (this.onValueChangedListener != null)
          this.onValueChangedListener.onValueChanged(i);
      }
      f2 = paramMotionEvent.getX();
      if (f2 < this.ball.xIni)
        f2 = this.ball.xIni;
      if (f2 > this.ball.xFin)
        f2 = this.ball.xFin;
      ViewHelper.setX(this.ball, f2);
      this.ball.changeBackground();
      if (this.numberIndicator != null)
      {
        this.numberIndicator.indicator.x = f2;
        this.numberIndicator.indicator.finalY = (Utils.getRelativeTop(this) - getHeight() / 2);
        this.numberIndicator.indicator.finalSize = (getHeight() / 2);
        this.numberIndicator.numberIndicator.setText("");
      }
    }
    label293: label340: label366: 
    while ((paramMotionEvent.getAction() != 1) && (paramMotionEvent.getAction() != 3))
    {
      do
      {
        while (true)
        {
          float f1;
          int i;
          float f2;
          return true;
          if (paramMotionEvent.getX() < this.ball.xIni)
            i = this.min;
          else
            i = this.min + (int)((paramMotionEvent.getX() - this.ball.xIni) / f1);
        }
        this.press = false;
        this.isLastTouch = false;
      }
      while (this.numberIndicator == null);
      this.numberIndicator.dismiss();
      return true;
    }
    if (this.numberIndicator != null)
      this.numberIndicator.dismiss();
    this.isLastTouch = false;
    this.press = false;
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
      this.showNumberIndicator = paramAttributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "showNumberIndicator", false);
      this.min = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "min", 0);
      this.max = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "max", 0);
      this.value = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "value", this.min);
      this.ball = new Ball(getContext());
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(Utils.dpToPx(20.0F, getResources()), Utils.dpToPx(20.0F, getResources()));
      localLayoutParams.addRule(15, -1);
      this.ball.setLayoutParams(localLayoutParams);
      addView(this.ball);
      if (this.showNumberIndicator)
        this.numberIndicator = new NumberIndicator(getContext());
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

  public void setMax(int paramInt)
  {
    this.max = paramInt;
  }

  public void setMin(int paramInt)
  {
    this.min = paramInt;
  }

  public void setOnValueChangedListener(OnValueChangedListener paramOnValueChangedListener)
  {
    this.onValueChangedListener = paramOnValueChangedListener;
  }

  public void setShowNumberIndicator(boolean paramBoolean)
  {
    this.showNumberIndicator = paramBoolean;
    if (paramBoolean);
    for (NumberIndicator localNumberIndicator = new NumberIndicator(getContext()); ; localNumberIndicator = null)
    {
      this.numberIndicator = localNumberIndicator;
      return;
    }
  }

  public void setValue(final int paramInt)
  {
    if (!this.placedBall)
    {
      post(new Runnable()
      {
        public void run()
        {
          Slider.this.setValue(paramInt);
        }
      });
      return;
    }
    this.value = paramInt;
    float f = (this.ball.xFin - this.ball.xIni) / this.max;
    ViewHelper.setX(this.ball, f * paramInt + getHeight() / 2 - this.ball.getWidth() / 2);
    this.ball.changeBackground();
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

    public void changeBackground()
    {
      if (Slider.this.value != Slider.this.min)
      {
        setBackgroundResource(R.drawable.background_checkbox);
        ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(Slider.this.backgroundColor);
        return;
      }
      setBackgroundResource(R.drawable.background_switch_ball_uncheck);
    }
  }

  class Indicator extends RelativeLayout
  {
    boolean animate = true;
    float finalSize = 0.0F;
    float finalY = 0.0F;
    boolean numberIndicatorResize = false;
    float size = 0.0F;
    float x = 0.0F;
    float y = 0.0F;

    public Indicator(Context arg2)
    {
      super();
      setBackgroundColor(getResources().getColor(17170445));
    }

    protected void onDraw(Canvas paramCanvas)
    {
      super.onDraw(paramCanvas);
      if (!this.numberIndicatorResize)
      {
        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)Slider.this.numberIndicator.numberIndicator.getLayoutParams();
        localLayoutParams.height = (2 * (int)this.finalSize);
        localLayoutParams.width = (2 * (int)this.finalSize);
        Slider.this.numberIndicator.numberIndicator.setLayoutParams(localLayoutParams);
      }
      Paint localPaint = new Paint();
      localPaint.setAntiAlias(true);
      localPaint.setColor(Slider.this.backgroundColor);
      if (this.animate)
      {
        if (this.y == 0.0F)
          this.y = (this.finalY + 2.0F * this.finalSize);
        this.y -= Utils.dpToPx(6.0F, getResources());
        this.size += Utils.dpToPx(2.0F, getResources());
      }
      paramCanvas.drawCircle(ViewHelper.getX(Slider.this.ball) + Utils.getRelativeLeft((View)Slider.this.ball.getParent()) + Slider.this.ball.getWidth() / 2, this.y, this.size, localPaint);
      if ((this.animate) && (this.size >= this.finalSize))
        this.animate = false;
      if (!this.animate)
      {
        ViewHelper.setX(Slider.this.numberIndicator.numberIndicator, ViewHelper.getX(Slider.this.ball) + Utils.getRelativeLeft((View)Slider.this.ball.getParent()) + Slider.this.ball.getWidth() / 2 - this.size);
        ViewHelper.setY(Slider.this.numberIndicator.numberIndicator, this.y - this.size);
        Slider.this.numberIndicator.numberIndicator.setText(Slider.this.value + "");
      }
      invalidate();
    }
  }

  class NumberIndicator extends Dialog
  {
    Slider.Indicator indicator;
    TextView numberIndicator;

    public NumberIndicator(Context arg2)
    {
      super(16973839);
    }

    public void dismiss()
    {
      super.dismiss();
      this.indicator.y = 0.0F;
      this.indicator.size = 0.0F;
      this.indicator.animate = true;
    }

    public void onBackPressed()
    {
    }

    protected void onCreate(Bundle paramBundle)
    {
      requestWindowFeature(1);
      super.onCreate(paramBundle);
      setContentView(R.layout.number_indicator_spinner);
      setCanceledOnTouchOutside(false);
      RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(R.id.number_indicator_spinner_content);
      this.indicator = new Slider.Indicator(Slider.this, getContext());
      localRelativeLayout.addView(this.indicator);
      this.numberIndicator = new TextView(getContext());
      this.numberIndicator.setTextColor(-1);
      this.numberIndicator.setGravity(17);
      localRelativeLayout.addView(this.numberIndicator);
      this.indicator.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    }
  }

  public static abstract interface OnValueChangedListener
  {
    public abstract void onValueChanged(int paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.Slider
 * JD-Core Version:    0.6.2
 */