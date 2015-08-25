package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.utils.Utils;

public class ProgressBarDeterminate extends CustomView
{
  int backgroundColor = Color.parseColor("#1E88E5");
  int max = 100;
  int min = 0;
  int pendindProgress = -1;
  int progress = 0;
  View progressView;

  public ProgressBarDeterminate(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
  }

  public int getProgress()
  {
    return this.progress;
  }

  protected int makePressColor()
  {
    return Color.argb(128, 0xFF & this.backgroundColor >> 16, 0xFF & this.backgroundColor >> 8, 0xFF & this.backgroundColor >> 0);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.pendindProgress != -1)
      setProgress(this.pendindProgress);
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    this.progressView = new View(getContext());
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(1, 1);
    this.progressView.setLayoutParams(localLayoutParams);
    this.progressView.setBackgroundResource(R.drawable.background_progress);
    addView(this.progressView);
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
      setBackgroundColor(getResources().getColor(i));
    while (true)
    {
      this.min = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "min", 0);
      this.max = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "max", 100);
      this.progress = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "progress", this.min);
      setMinimumHeight(Utils.dpToPx(3.0F, getResources()));
      post(new Runnable()
      {
        public void run()
        {
          RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)ProgressBarDeterminate.this.progressView.getLayoutParams();
          localLayoutParams.height = ProgressBarDeterminate.this.getHeight();
          ProgressBarDeterminate.this.progressView.setLayoutParams(localLayoutParams);
        }
      });
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
    this.backgroundColor = paramInt;
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
    ((GradientDrawable)((LayerDrawable)this.progressView.getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(paramInt);
    super.setBackgroundColor(makePressColor());
  }

  public void setMax(int paramInt)
  {
    this.max = paramInt;
  }

  public void setMin(int paramInt)
  {
    this.min = paramInt;
  }

  public void setProgress(int paramInt)
  {
    if (getWidth() == 0)
    {
      this.pendindProgress = paramInt;
      return;
    }
    this.progress = paramInt;
    if (paramInt > this.max)
      paramInt = this.max;
    if (paramInt < this.min)
      paramInt = this.min;
    int i = this.max - this.min;
    int j = (int)(paramInt / i * getWidth());
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.progressView.getLayoutParams();
    localLayoutParams.width = j;
    localLayoutParams.height = getHeight();
    this.progressView.setLayoutParams(localLayoutParams);
    this.pendindProgress = -1;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ProgressBarDeterminate
 * JD-Core Version:    0.6.2
 */