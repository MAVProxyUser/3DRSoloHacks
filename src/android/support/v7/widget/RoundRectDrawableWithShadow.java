package android.support.v7.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v7.cardview.R.color;
import android.support.v7.cardview.R.dimen;

class RoundRectDrawableWithShadow extends Drawable
{
  static final double COS_45 = 0.0D;
  static final float SHADOW_MULTIPLIER = 1.5F;
  static RoundRectHelper sRoundRectHelper;
  private boolean mAddPaddingForCorners = true;
  final RectF mCardBounds;
  float mCornerRadius;
  Paint mCornerShadowPaint;
  Path mCornerShadowPath;
  private boolean mDirty = true;
  Paint mEdgeShadowPaint;
  final int mInsetShadow;
  float mMaxShadowSize;
  Paint mPaint;
  private boolean mPrintedShadowClipWarning = false;
  float mRawMaxShadowSize;
  float mRawShadowSize;
  private final int mShadowEndColor;
  float mShadowSize;
  private final int mShadowStartColor;

  RoundRectDrawableWithShadow(Resources paramResources, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mShadowStartColor = paramResources.getColor(R.color.cardview_shadow_start_color);
    this.mShadowEndColor = paramResources.getColor(R.color.cardview_shadow_end_color);
    this.mInsetShadow = paramResources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
    this.mPaint = new Paint(5);
    this.mPaint.setColor(paramInt);
    this.mCornerShadowPaint = new Paint(5);
    this.mCornerShadowPaint.setStyle(Paint.Style.FILL);
    this.mCornerRadius = ((int)(0.5F + paramFloat1));
    this.mCardBounds = new RectF();
    this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
    this.mEdgeShadowPaint.setAntiAlias(false);
    setShadowSize(paramFloat2, paramFloat3);
  }

  private void buildComponents(Rect paramRect)
  {
    float f = 1.5F * this.mRawMaxShadowSize;
    this.mCardBounds.set(paramRect.left + this.mRawMaxShadowSize, f + paramRect.top, paramRect.right - this.mRawMaxShadowSize, paramRect.bottom - f);
    buildShadowCorners();
  }

  private void buildShadowCorners()
  {
    RectF localRectF1 = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
    RectF localRectF2 = new RectF(localRectF1);
    localRectF2.inset(-this.mShadowSize, -this.mShadowSize);
    if (this.mCornerShadowPath == null)
      this.mCornerShadowPath = new Path();
    while (true)
    {
      this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
      this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0F);
      this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0F);
      this.mCornerShadowPath.arcTo(localRectF2, 180.0F, 90.0F, false);
      this.mCornerShadowPath.arcTo(localRectF1, 270.0F, -90.0F, false);
      this.mCornerShadowPath.close();
      float f1 = this.mCornerRadius / (this.mCornerRadius + this.mShadowSize);
      Paint localPaint1 = this.mCornerShadowPaint;
      float f2 = this.mCornerRadius + this.mShadowSize;
      int[] arrayOfInt1 = new int[3];
      arrayOfInt1[0] = this.mShadowStartColor;
      arrayOfInt1[1] = this.mShadowStartColor;
      arrayOfInt1[2] = this.mShadowEndColor;
      localPaint1.setShader(new RadialGradient(0.0F, 0.0F, f2, arrayOfInt1, new float[] { 0.0F, f1, 1.0F }, Shader.TileMode.CLAMP));
      Paint localPaint2 = this.mEdgeShadowPaint;
      float f3 = -this.mCornerRadius + this.mShadowSize;
      float f4 = -this.mCornerRadius - this.mShadowSize;
      int[] arrayOfInt2 = new int[3];
      arrayOfInt2[0] = this.mShadowStartColor;
      arrayOfInt2[1] = this.mShadowStartColor;
      arrayOfInt2[2] = this.mShadowEndColor;
      localPaint2.setShader(new LinearGradient(0.0F, f3, 0.0F, f4, arrayOfInt2, new float[] { 0.0F, 0.5F, 1.0F }, Shader.TileMode.CLAMP));
      this.mEdgeShadowPaint.setAntiAlias(false);
      return;
      this.mCornerShadowPath.reset();
    }
  }

  static float calculateHorizontalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
      paramFloat1 = (float)(paramFloat1 + (1.0D - COS_45) * paramFloat2);
    return paramFloat1;
  }

  static float calculateVerticalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
      return (float)(1.5F * paramFloat1 + (1.0D - COS_45) * paramFloat2);
    return 1.5F * paramFloat1;
  }

  private void drawShadow(Canvas paramCanvas)
  {
    float f1 = -this.mCornerRadius - this.mShadowSize;
    float f2 = this.mCornerRadius + this.mInsetShadow + this.mRawShadowSize / 2.0F;
    int i;
    if (this.mCardBounds.width() - 2.0F * f2 > 0.0F)
    {
      i = 1;
      if (this.mCardBounds.height() - 2.0F * f2 <= 0.0F)
        break label405;
    }
    label405: for (int j = 1; ; j = 0)
    {
      int k = paramCanvas.save();
      paramCanvas.translate(f2 + this.mCardBounds.left, f2 + this.mCardBounds.top);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (i != 0)
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.width() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      paramCanvas.restoreToCount(k);
      int m = paramCanvas.save();
      paramCanvas.translate(this.mCardBounds.right - f2, this.mCardBounds.bottom - f2);
      paramCanvas.rotate(180.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (i != 0)
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.width() - 2.0F * f2, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint);
      paramCanvas.restoreToCount(m);
      int n = paramCanvas.save();
      paramCanvas.translate(f2 + this.mCardBounds.left, this.mCardBounds.bottom - f2);
      paramCanvas.rotate(270.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (j != 0)
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.height() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      paramCanvas.restoreToCount(n);
      int i1 = paramCanvas.save();
      paramCanvas.translate(this.mCardBounds.right - f2, f2 + this.mCardBounds.top);
      paramCanvas.rotate(90.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (j != 0)
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.height() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      paramCanvas.restoreToCount(i1);
      return;
      i = 0;
      break;
    }
  }

  private int toEven(float paramFloat)
  {
    int i = (int)(0.5F + paramFloat);
    if (i % 2 == 1)
      i--;
    return i;
  }

  public void draw(Canvas paramCanvas)
  {
    if (this.mDirty)
    {
      buildComponents(getBounds());
      this.mDirty = false;
    }
    paramCanvas.translate(0.0F, this.mRawShadowSize / 2.0F);
    drawShadow(paramCanvas);
    paramCanvas.translate(0.0F, -this.mRawShadowSize / 2.0F);
    sRoundRectHelper.drawRoundRect(paramCanvas, this.mCardBounds, this.mCornerRadius, this.mPaint);
  }

  float getCornerRadius()
  {
    return this.mCornerRadius;
  }

  void getMaxShadowAndCornerPadding(Rect paramRect)
  {
    getPadding(paramRect);
  }

  float getMaxShadowSize()
  {
    return this.mRawMaxShadowSize;
  }

  float getMinHeight()
  {
    return 2.0F * Math.max(this.mRawMaxShadowSize, this.mCornerRadius + this.mInsetShadow + 1.5F * this.mRawMaxShadowSize / 2.0F) + 2.0F * (1.5F * this.mRawMaxShadowSize + this.mInsetShadow);
  }

  float getMinWidth()
  {
    return 2.0F * Math.max(this.mRawMaxShadowSize, this.mCornerRadius + this.mInsetShadow + this.mRawMaxShadowSize / 2.0F) + 2.0F * (this.mRawMaxShadowSize + this.mInsetShadow);
  }

  public int getOpacity()
  {
    return -3;
  }

  public boolean getPadding(Rect paramRect)
  {
    int i = (int)Math.ceil(calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
    int j = (int)Math.ceil(calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
    paramRect.set(j, i, j, i);
    return true;
  }

  float getShadowSize()
  {
    return this.mRawShadowSize;
  }

  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.mDirty = true;
  }

  public void setAddPaddingForCorners(boolean paramBoolean)
  {
    this.mAddPaddingForCorners = paramBoolean;
    invalidateSelf();
  }

  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
    this.mCornerShadowPaint.setAlpha(paramInt);
    this.mEdgeShadowPaint.setAlpha(paramInt);
  }

  public void setColor(int paramInt)
  {
    this.mPaint.setColor(paramInt);
    invalidateSelf();
  }

  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
    this.mCornerShadowPaint.setColorFilter(paramColorFilter);
    this.mEdgeShadowPaint.setColorFilter(paramColorFilter);
  }

  void setCornerRadius(float paramFloat)
  {
    float f = (int)(0.5F + paramFloat);
    if (this.mCornerRadius == f)
      return;
    this.mCornerRadius = f;
    this.mDirty = true;
    invalidateSelf();
  }

  void setMaxShadowSize(float paramFloat)
  {
    setShadowSize(this.mRawShadowSize, paramFloat);
  }

  void setShadowSize(float paramFloat)
  {
    setShadowSize(paramFloat, this.mRawMaxShadowSize);
  }

  void setShadowSize(float paramFloat1, float paramFloat2)
  {
    if ((paramFloat1 < 0.0F) || (paramFloat2 < 0.0F))
      throw new IllegalArgumentException("invalid shadow size");
    float f1 = toEven(paramFloat1);
    float f2 = toEven(paramFloat2);
    if (f1 > f2)
    {
      f1 = f2;
      if (!this.mPrintedShadowClipWarning)
        this.mPrintedShadowClipWarning = true;
    }
    if ((this.mRawShadowSize == f1) && (this.mRawMaxShadowSize == f2))
      return;
    this.mRawShadowSize = f1;
    this.mRawMaxShadowSize = f2;
    this.mShadowSize = ((int)(0.5F + (1.5F * f1 + this.mInsetShadow)));
    this.mMaxShadowSize = (f2 + this.mInsetShadow);
    this.mDirty = true;
    invalidateSelf();
  }

  static abstract interface RoundRectHelper
  {
    public abstract void drawRoundRect(Canvas paramCanvas, RectF paramRectF, float paramFloat, Paint paramPaint);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RoundRectDrawableWithShadow
 * JD-Core Version:    0.6.2
 */