package com.getbase.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;

public class AddFloatingActionButton extends FloatingActionButton
{
  int mPlusColor;

  public AddFloatingActionButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public AddFloatingActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public AddFloatingActionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  Drawable getIconDrawable()
  {
    final float f1 = getDimension(R.dimen.fab_icon_size);
    final float f2 = f1 / 2.0F;
    float f3 = getDimension(R.dimen.fab_plus_icon_size);
    final float f4 = getDimension(R.dimen.fab_plus_icon_stroke) / 2.0F;
    ShapeDrawable localShapeDrawable = new ShapeDrawable(new Shape()
    {
      public void draw(Canvas paramAnonymousCanvas, Paint paramAnonymousPaint)
      {
        paramAnonymousCanvas.drawRect(this.val$plusOffset, f2 - f4, f1 - this.val$plusOffset, f2 + f4, paramAnonymousPaint);
        paramAnonymousCanvas.drawRect(f2 - f4, this.val$plusOffset, f2 + f4, f1 - this.val$plusOffset, paramAnonymousPaint);
      }
    });
    Paint localPaint = localShapeDrawable.getPaint();
    localPaint.setColor(this.mPlusColor);
    localPaint.setStyle(Paint.Style.FILL);
    localPaint.setAntiAlias(true);
    return localShapeDrawable;
  }

  public int getPlusColor()
  {
    return this.mPlusColor;
  }

  void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AddFloatingActionButton, 0, 0);
    this.mPlusColor = localTypedArray.getColor(R.styleable.AddFloatingActionButton_fab_plusIconColor, getColor(17170443));
    localTypedArray.recycle();
    super.init(paramContext, paramAttributeSet);
  }

  public void setIcon(int paramInt)
  {
    throw new UnsupportedOperationException("Use FloatingActionButton if you want to use custom icon");
  }

  public void setPlusColor(int paramInt)
  {
    if (this.mPlusColor != paramInt)
    {
      this.mPlusColor = paramInt;
      updateBackground();
    }
  }

  public void setPlusColorResId(int paramInt)
  {
    setPlusColor(getColor(paramInt));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.getbase.floatingactionbutton.AddFloatingActionButton
 * JD-Core Version:    0.6.2
 */