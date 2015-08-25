package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import com.gc.materialdesign.utils.Utils;

public class ButtonIcon extends ButtonFloat
{
  public ButtonIcon(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBackground(new ColorDrawable(getResources().getColor(17170445)));
    this.rippleSpeed = Utils.dpToPx(6.0F, getResources());
    this.rippleSize = Utils.dpToPx(5.0F, getResources());
  }

  protected int makePressColor()
  {
    return this.backgroundColor;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.x != -1.0F)
    {
      Paint localPaint = new Paint();
      localPaint.setAntiAlias(true);
      localPaint.setColor(makePressColor());
      paramCanvas.drawCircle(this.x, this.y, this.radius, localPaint);
      if (this.radius > getHeight() / this.rippleSize)
        this.radius += this.rippleSpeed;
      if (this.radius >= getWidth() / 2 - this.rippleSpeed)
      {
        this.x = -1.0F;
        this.y = -1.0F;
        this.radius = (getHeight() / this.rippleSize);
        if ((this.onClickListener != null) && (this.clickAfterRipple))
          this.onClickListener.onClick(this);
      }
      invalidate();
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (this.x != -1.0F)
    {
      this.x = (getWidth() / 2);
      this.y = (getHeight() / 2);
    }
    return bool;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ButtonIcon
 * JD-Core Version:    0.6.2
 */