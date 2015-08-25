package com.o3dr.solo.android.widget.spinnerWheel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.o3dr.solo.android.R.styleable;
import com.o3dr.solo.android.widget.spinnerWheel.adapters.WheelViewAdapter;

public abstract class AbstractWheelView extends AbstractWheel
{
  protected static final int DEF_ITEMS_DIMMED_ALPHA = 50;
  protected static final int DEF_ITEM_OFFSET_PERCENT = 10;
  protected static final int DEF_ITEM_PADDING = 10;
  protected static final int DEF_SELECTION_DIVIDER_ACTIVE_ALPHA = 70;
  protected static final int DEF_SELECTION_DIVIDER_DIMMED_ALPHA = 70;
  protected static final int DEF_SELECTION_DIVIDER_SIZE = 2;
  protected static final String PROPERTY_SELECTOR_PAINT_COEFF = "selectorPaintCoeff";
  protected static final String PROPERTY_SEPARATORS_PAINT_ALPHA = "separatorsPaintAlpha";
  private static int itemID = -1;
  private final String LOG_TAG;
  protected Animator mDimSelectorWheelAnimator;
  protected Animator mDimSeparatorsAnimator;
  protected int mItemOffsetPercent;
  protected int mItemsDimmedAlpha;
  protected int mItemsPadding;
  protected Drawable mSelectionDivider;
  protected int mSelectionDividerActiveAlpha;
  protected int mSelectionDividerDimmedAlpha;
  protected Paint mSelectorWheelPaint;
  protected Bitmap mSeparatorsBitmap;
  protected Paint mSeparatorsPaint;
  protected Bitmap mSpinBitmap;

  public AbstractWheelView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    StringBuilder localStringBuilder = new StringBuilder().append(AbstractWheelView.class.getName()).append(" #");
    int i = 1 + itemID;
    itemID = i;
    this.LOG_TAG = i;
  }

  private void fadeSelectorWheel(long paramLong)
  {
    this.mDimSelectorWheelAnimator.setDuration(paramLong);
    this.mDimSelectorWheelAnimator.start();
  }

  private void lightSeparators(long paramLong)
  {
    this.mDimSeparatorsAnimator.setDuration(paramLong);
    this.mDimSeparatorsAnimator.start();
  }

  protected abstract void drawItems(Canvas paramCanvas);

  protected void initAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    super.initAttributes(paramAttributeSet, paramInt);
    TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.AbstractWheelView, paramInt, 0);
    this.mItemsDimmedAlpha = localTypedArray.getInt(7, 50);
    this.mSelectionDividerActiveAlpha = localTypedArray.getInt(5, 70);
    this.mSelectionDividerDimmedAlpha = localTypedArray.getInt(4, 70);
    this.mItemOffsetPercent = localTypedArray.getInt(2, 10);
    this.mItemsPadding = localTypedArray.getDimensionPixelSize(3, 10);
    this.mSelectionDivider = localTypedArray.getDrawable(6);
    localTypedArray.recycle();
  }

  protected void initData(Context paramContext)
  {
    super.initData(paramContext);
    this.mDimSelectorWheelAnimator = ObjectAnimator.ofFloat(this, "selectorPaintCoeff", new float[] { 1.0F, 0.0F });
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = this.mSelectionDividerActiveAlpha;
    arrayOfInt[1] = this.mSelectionDividerDimmedAlpha;
    this.mDimSeparatorsAnimator = ObjectAnimator.ofInt(this, "separatorsPaintAlpha", arrayOfInt);
    this.mSeparatorsPaint = new Paint();
    this.mSeparatorsPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    this.mSeparatorsPaint.setAlpha(this.mSelectionDividerDimmedAlpha);
    this.mSelectorWheelPaint = new Paint();
    this.mSelectorWheelPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
  }

  protected abstract void measureLayout();

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mViewAdapter != null) && (this.mViewAdapter.getItemsCount() > 0))
    {
      if (rebuildItems())
        measureLayout();
      doItemsLayout();
      drawItems(paramCanvas);
    }
  }

  protected void onScrollFinished()
  {
    fadeSelectorWheel(500L);
    lightSeparators(500L);
  }

  protected void onScrollTouched()
  {
    this.mDimSelectorWheelAnimator.cancel();
    this.mDimSeparatorsAnimator.cancel();
    setSelectorPaintCoeff(1.0F);
    setSeparatorsPaintAlpha(this.mSelectionDividerActiveAlpha);
  }

  protected void onScrollTouchedUp()
  {
    super.onScrollTouchedUp();
    fadeSelectorWheel(750L);
    lightSeparators(750L);
  }

  protected void recreateAssets(int paramInt1, int paramInt2)
  {
    if ((paramInt1 > 0) && (paramInt2 > 0))
    {
      this.mSpinBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      this.mSeparatorsBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    }
    setSelectorPaintCoeff(0.0F);
  }

  public void setSelectionDivider(Drawable paramDrawable)
  {
    this.mSelectionDivider = paramDrawable;
  }

  public abstract void setSelectorPaintCoeff(float paramFloat);

  public void setSeparatorsPaintAlpha(int paramInt)
  {
    this.mSeparatorsPaint.setAlpha(paramInt);
    invalidate();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.AbstractWheelView
 * JD-Core Version:    0.6.2
 */