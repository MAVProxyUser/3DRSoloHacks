package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

class RoundRectDrawable extends Drawable
{
  private final RectF mBoundsF;
  private final Rect mBoundsI;
  private boolean mInsetForPadding = false;
  private boolean mInsetForRadius = true;
  private float mPadding;
  private final Paint mPaint;
  private float mRadius;

  public RoundRectDrawable(int paramInt, float paramFloat)
  {
    this.mRadius = paramFloat;
    this.mPaint = new Paint(5);
    this.mPaint.setColor(paramInt);
    this.mBoundsF = new RectF();
    this.mBoundsI = new Rect();
  }

  private void updateBounds(Rect paramRect)
  {
    if (paramRect == null)
      paramRect = getBounds();
    this.mBoundsF.set(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
    this.mBoundsI.set(paramRect);
    if (this.mInsetForPadding)
    {
      float f1 = RoundRectDrawableWithShadow.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
      float f2 = RoundRectDrawableWithShadow.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
      this.mBoundsI.inset((int)Math.ceil(f2), (int)Math.ceil(f1));
      this.mBoundsF.set(this.mBoundsI);
    }
  }

  public void draw(Canvas paramCanvas)
  {
    paramCanvas.drawRoundRect(this.mBoundsF, this.mRadius, this.mRadius, this.mPaint);
  }

  public int getOpacity()
  {
    return -3;
  }

  public void getOutline(Outline paramOutline)
  {
    paramOutline.setRoundRect(this.mBoundsI, this.mRadius);
  }

  float getPadding()
  {
    return this.mPadding;
  }

  public float getRadius()
  {
    return this.mRadius;
  }

  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    updateBounds(paramRect);
  }

  public void setAlpha(int paramInt)
  {
  }

  public void setColor(int paramInt)
  {
    this.mPaint.setColor(paramInt);
    invalidateSelf();
  }

  public void setColorFilter(ColorFilter paramColorFilter)
  {
  }

  void setPadding(float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramFloat == this.mPadding) && (this.mInsetForPadding == paramBoolean1) && (this.mInsetForRadius == paramBoolean2))
      return;
    this.mPadding = paramFloat;
    this.mInsetForPadding = paramBoolean1;
    this.mInsetForRadius = paramBoolean2;
    updateBounds(null);
    invalidateSelf();
  }

  void setRadius(float paramFloat)
  {
    if (paramFloat == this.mRadius)
      return;
    this.mRadius = paramFloat;
    updateBounds(null);
    invalidateSelf();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RoundRectDrawable
 * JD-Core Version:    0.6.2
 */