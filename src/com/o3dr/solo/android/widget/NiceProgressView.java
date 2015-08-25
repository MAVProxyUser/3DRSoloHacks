package com.o3dr.solo.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import java.util.Arrays;

public class NiceProgressView extends ProgressBar
{
  final int[][] colors = { { 224, 187, 63 }, { 224, 46, 25 }, { 51, 130, 49 } };
  int[] currentColor = { 0, 0, 0 };
  int maxvalue = 320;
  int nextcolor = 1;
  Paint p;
  RectF rectF;
  boolean reverse = false;
  int start = 0;
  int value = 320;

  public NiceProgressView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public NiceProgressView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public NiceProgressView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private void changeColors(int paramInt)
  {
    if (this.currentColor[paramInt] > this.colors[this.nextcolor][paramInt])
    {
      int[] arrayOfInt2 = this.currentColor;
      arrayOfInt2[paramInt] = (-1 + arrayOfInt2[paramInt]);
    }
    if (this.currentColor[paramInt] < this.colors[this.nextcolor][paramInt])
    {
      int[] arrayOfInt1 = this.currentColor;
      arrayOfInt1[paramInt] = (1 + arrayOfInt1[paramInt]);
    }
  }

  private void init()
  {
    this.p = new Paint();
    this.p.setStrokeWidth(8.0F);
    this.p.setStrokeCap(Paint.Cap.ROUND);
    this.p.setAntiAlias(true);
    this.p.setStyle(Paint.Style.STROKE);
    this.p.setColor(Color.argb(255, this.colors[0][0], this.colors[0][1], this.colors[0][2]));
    this.currentColor = Arrays.copyOf(this.colors[0], this.colors[0].length);
  }

  private void transformColor()
  {
    changeColors(0);
    changeColors(1);
    changeColors(2);
    if ((this.currentColor[0] == this.colors[this.nextcolor][0]) && (this.currentColor[1] == this.colors[this.nextcolor][1]) && (this.currentColor[2] == this.colors[this.nextcolor][2]))
    {
      if (this.nextcolor == 2)
        this.nextcolor = 0;
    }
    else
      return;
    this.nextcolor = (1 + this.nextcolor);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.reverse)
    {
      this.start = (15 + this.start);
      if (this.start == 360)
        this.start = 1;
      if (this.reverse)
        break label164;
      this.value = (-10 + this.value);
      label51: if ((this.value == 0) || (this.value == this.maxvalue))
        if (this.reverse)
          break label178;
    }
    label164: label178: for (boolean bool = true; ; bool = false)
    {
      this.reverse = bool;
      transformColor();
      this.p.setColor(Color.argb(255, this.currentColor[0], this.currentColor[1], this.currentColor[2]));
      paramCanvas.drawArc(this.rectF, this.start, this.maxvalue - this.value, false, this.p);
      invalidate();
      return;
      this.start = (5 + this.start);
      break;
      this.value = (10 + this.value);
      break label51;
    }
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.rectF = new RectF(5.0F, 5.0F, paramInt1 - 5, paramInt2 - 5);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.NiceProgressView
 * JD-Core Version:    0.6.2
 */