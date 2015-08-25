package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.utils.Utils;

public class CheckBox extends CustomView
{
  int backgroundColor = Color.parseColor("#4CAF50");
  boolean check = false;
  Check checkView;
  OnCheckListener onCheckListener;
  boolean press = false;
  int step = 0;

  public CheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
  }

  private void changeBackgroundColor(int paramInt)
  {
    ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(paramInt);
  }

  public void invalidate()
  {
    this.checkView.invalidate();
    super.invalidate();
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
    Paint localPaint;
    if (this.press)
    {
      localPaint = new Paint();
      localPaint.setAntiAlias(true);
      if (!this.check)
        break label73;
    }
    label73: for (int i = makePressColor(); ; i = Color.parseColor("#446D6D6D"))
    {
      localPaint.setColor(i);
      paramCanvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, localPaint);
      invalidate();
      return;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    invalidate();
    int i;
    if (isEnabled())
    {
      this.isLastTouch = true;
      if (paramMotionEvent.getAction() != 0)
        break label51;
      if (!this.check)
        break label42;
      i = makePressColor();
      changeBackgroundColor(i);
    }
    label42: label51: 
    do
    {
      do
      {
        return true;
        i = Color.parseColor("#446D6D6D");
        break;
      }
      while (paramMotionEvent.getAction() != 1);
      changeBackgroundColor(getResources().getColor(17170445));
      this.press = false;
    }
    while ((paramMotionEvent.getX() > getWidth()) || (paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getY() > getHeight()) || (paramMotionEvent.getY() < 0.0F));
    this.isLastTouch = false;
    if (!this.check);
    for (boolean bool = true; ; bool = false)
    {
      this.check = bool;
      if (this.onCheckListener != null)
        this.onCheckListener.onCheck(this.check);
      if (this.check)
        this.step = 0;
      if (!this.check)
        break;
      this.checkView.changeBackground();
      return true;
    }
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    setBackgroundResource(R.drawable.background_checkbox);
    setMinimumHeight(Utils.dpToPx(48.0F, getResources()));
    setMinimumWidth(Utils.dpToPx(48.0F, getResources()));
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
      setBackgroundColor(getResources().getColor(i));
    while (true)
    {
      post(new Runnable()
      {
        public void run()
        {
          CheckBox.this.setChecked(this.val$check);
          CheckBox.this.setPressed(false);
          CheckBox.this.changeBackgroundColor(CheckBox.this.getResources().getColor(17170445));
        }
      });
      this.checkView = new Check(getContext());
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(Utils.dpToPx(20.0F, getResources()), Utils.dpToPx(20.0F, getResources()));
      localLayoutParams.addRule(13, -1);
      this.checkView.setLayoutParams(localLayoutParams);
      addView(this.checkView);
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
    changeBackgroundColor(paramInt);
  }

  public void setChecked(boolean paramBoolean)
  {
    this.check = paramBoolean;
    setPressed(false);
    changeBackgroundColor(getResources().getColor(17170445));
    if (paramBoolean)
      this.step = 0;
    if (paramBoolean)
      this.checkView.changeBackground();
  }

  public void setOncheckListener(OnCheckListener paramOnCheckListener)
  {
    this.onCheckListener = paramOnCheckListener;
  }

  class Check extends View
  {
    Bitmap sprite;

    public Check(Context arg2)
    {
      super();
      setBackgroundResource(R.drawable.background_checkbox_uncheck);
      this.sprite = BitmapFactory.decodeResource(localContext.getResources(), R.drawable.sprite_check);
    }

    public void changeBackground()
    {
      if (CheckBox.this.check)
      {
        setBackgroundResource(R.drawable.background_checkbox_check);
        ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(CheckBox.this.backgroundColor);
        return;
      }
      setBackgroundResource(R.drawable.background_checkbox_uncheck);
    }

    protected void onDraw(Canvas paramCanvas)
    {
      super.onDraw(paramCanvas);
      if (CheckBox.this.check)
        if (CheckBox.this.step < 11)
        {
          CheckBox localCheckBox2 = CheckBox.this;
          localCheckBox2.step = (1 + localCheckBox2.step);
          invalidate();
        }
      while (true)
      {
        Rect localRect1 = new Rect(40 * CheckBox.this.step, 0, 40 + 40 * CheckBox.this.step, 40);
        Rect localRect2 = new Rect(0, 0, -2 + getWidth(), getHeight());
        paramCanvas.drawBitmap(this.sprite, localRect1, localRect2, null);
        return;
        if (CheckBox.this.step >= 0)
        {
          CheckBox localCheckBox1 = CheckBox.this;
          localCheckBox1.step = (-1 + localCheckBox1.step);
          invalidate();
        }
        if (CheckBox.this.step == -1)
        {
          invalidate();
          changeBackground();
        }
      }
    }
  }

  public static abstract interface OnCheckListener
  {
    public abstract void onCheck(boolean paramBoolean);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.CheckBox
 * JD-Core Version:    0.6.2
 */