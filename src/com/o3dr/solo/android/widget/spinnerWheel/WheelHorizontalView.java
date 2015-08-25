package com.o3dr.solo.android.widget.spinnerWheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.o3dr.solo.android.R.styleable;
import com.o3dr.solo.android.widget.spinnerWheel.adapters.AbstractWheelTextAdapter;
import com.o3dr.solo.android.widget.spinnerWheel.adapters.NumericWheelAdapter;

public class WheelHorizontalView<T> extends AbstractWheelView
{
  private final String LOG_TAG = WheelHorizontalView.class.getSimpleName();
  private int itemWidth = 0;
  protected int mSelectionDividerWidth;

  public WheelHorizontalView(Context paramContext)
  {
    this(paramContext, null);
  }

  public WheelHorizontalView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public WheelHorizontalView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode())
    {
      NumericWheelAdapter localNumericWheelAdapter = new NumericWheelAdapter(paramContext, 0, 100);
      localNumericWheelAdapter.setItemResource(2130903140);
      localNumericWheelAdapter.setItemTextResource(2131493371);
      setViewAdapter(localNumericWheelAdapter);
      setCurrentItem(50);
    }
  }

  private int calculateLayoutHeight(int paramInt1, int paramInt2)
  {
    this.mItemsLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.mItemsLayout.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(paramInt1, 0));
    int i = this.mItemsLayout.getMeasuredHeight();
    if (paramInt2 == 1073741824);
    for (int j = paramInt1; ; j = paramInt1)
      do
      {
        this.mItemsLayout.measure(View.MeasureSpec.makeMeasureSpec(400, 1073741824), View.MeasureSpec.makeMeasureSpec(j - 2 * this.mItemsPadding, 1073741824));
        return j;
        j = Math.max(i + 2 * this.mItemsPadding, getSuggestedMinimumHeight());
      }
      while ((paramInt2 != -2147483648) || (paramInt1 >= j));
  }

  protected void createItemsLayout()
  {
    if (this.mItemsLayout == null)
    {
      this.mItemsLayout = new LinearLayout(getContext());
      this.mItemsLayout.setOrientation(0);
    }
  }

  protected WheelScroller createScroller(WheelScroller.ScrollingListener paramScrollingListener)
  {
    return new WheelHorizontalScroller(getContext(), paramScrollingListener);
  }

  protected void doItemsLayout()
  {
    this.mItemsLayout.layout(0, 0, getMeasuredWidth(), getMeasuredHeight() - 2 * this.mItemsPadding);
  }

  protected void drawItems(Canvas paramCanvas)
  {
    paramCanvas.save();
    int i = getMeasuredWidth();
    int j = getMeasuredHeight();
    int k = getItemDimension();
    this.mSpinBitmap.eraseColor(0);
    Canvas localCanvas1 = new Canvas(this.mSpinBitmap);
    Canvas localCanvas2 = new Canvas(this.mSpinBitmap);
    localCanvas1.translate(-(k * (this.mCurrentItemIdx - this.mFirstItemIdx) + (k - getWidth()) / 2) + this.mScrollingOffset, this.mItemsPadding);
    this.mItemsLayout.draw(localCanvas1);
    this.mSeparatorsBitmap.eraseColor(0);
    Canvas localCanvas3 = new Canvas(this.mSeparatorsBitmap);
    if (this.mSelectionDivider != null)
    {
      int m = (getWidth() - k - this.mSelectionDividerWidth) / 2;
      int n = m + this.mSelectionDividerWidth;
      localCanvas3.save();
      localCanvas3.clipRect(m, 0, n, j);
      this.mSelectionDivider.setBounds(m, 0, n, j);
      this.mSelectionDivider.draw(localCanvas3);
      localCanvas3.restore();
      localCanvas3.save();
      int i1 = m + k;
      int i2 = n + k;
      localCanvas3.clipRect(i1, 0, i2, j);
      this.mSelectionDivider.setBounds(i1, 0, i2, j);
      this.mSelectionDivider.draw(localCanvas3);
      localCanvas3.restore();
    }
    localCanvas2.drawRect(0.0F, 0.0F, i, j, this.mSelectorWheelPaint);
    localCanvas3.drawRect(0.0F, 0.0F, i, j, this.mSeparatorsPaint);
    paramCanvas.drawBitmap(this.mSpinBitmap, 0.0F, 0.0F, null);
    paramCanvas.drawBitmap(this.mSeparatorsBitmap, 0.0F, 0.0F, null);
    paramCanvas.restore();
  }

  protected int getBaseDimension()
  {
    return getWidth();
  }

  protected int getItemDimension()
  {
    if (this.itemWidth != 0)
      return this.itemWidth;
    if ((this.mItemsLayout != null) && (this.mItemsLayout.getChildAt(0) != null))
    {
      this.itemWidth = this.mItemsLayout.getChildAt(0).getMeasuredWidth();
      return this.itemWidth;
    }
    return getBaseDimension() / this.mVisibleItems;
  }

  protected float getMotionEventPosition(MotionEvent paramMotionEvent)
  {
    return paramMotionEvent.getX();
  }

  public AbstractWheelTextAdapter<T> getViewAdapter()
  {
    return (AbstractWheelTextAdapter)this.mViewAdapter;
  }

  protected void initAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    super.initAttributes(paramAttributeSet, paramInt);
    TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.WheelHorizontalView, paramInt, 0);
    this.mSelectionDividerWidth = localTypedArray.getDimensionPixelSize(0, 2);
    localTypedArray.recycle();
  }

  protected void measureLayout()
  {
    this.mItemsLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.mItemsLayout.measure(View.MeasureSpec.makeMeasureSpec(getWidth() + getItemDimension(), 0), View.MeasureSpec.makeMeasureSpec(getHeight(), -2147483648));
  }

  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    rebuildItems();
    int n = calculateLayoutHeight(m, j);
    int i1;
    if (i == 1073741824)
      i1 = k;
    while (true)
    {
      setMeasuredDimension(i1, n);
      return;
      i1 = Math.max(getItemDimension() * (this.mVisibleItems - this.mItemOffsetPercent / 100), getSuggestedMinimumWidth());
      if (i == -2147483648)
        i1 = Math.min(i1, k);
    }
  }

  public void setSelectionDividerWidth(int paramInt)
  {
    this.mSelectionDividerWidth = paramInt;
  }

  public void setSelectorPaintCoeff(float paramFloat)
  {
    int i = getMeasuredWidth();
    int j = getItemDimension();
    float f1 = (1.0F - j / i) / 2.0F;
    float f2 = (1.0F + j / i) / 2.0F;
    float f3 = this.mItemsDimmedAlpha * (1.0F - paramFloat);
    float f4 = f3 + 255.0F * paramFloat;
    int[] arrayOfInt2;
    float[] arrayOfFloat2;
    if (this.mVisibleItems == 2)
    {
      int m = Math.round(f4) << 24;
      int n = Math.round(f3) << 24;
      arrayOfInt2 = new int[] { n, m, -16777216, -16777216, m, n };
      arrayOfFloat2 = new float[] { 0.0F, f1, f1, f2, f2, 1.0F };
    }
    int[] arrayOfInt1;
    float[] arrayOfFloat1;
    for (LinearGradient localLinearGradient = new LinearGradient(0.0F, 0.0F, i, 0.0F, arrayOfInt2, arrayOfFloat2, Shader.TileMode.CLAMP); ; localLinearGradient = new LinearGradient(0.0F, 0.0F, i, 0.0F, arrayOfInt1, arrayOfFloat1, Shader.TileMode.CLAMP))
    {
      this.mSelectorWheelPaint.setShader(localLinearGradient);
      invalidate();
      return;
      float f5 = (1.0F - j * 3 / i) / 2.0F;
      float f6 = (1.0F + j * 3 / i) / 2.0F;
      float f7 = paramFloat * (255.0F * f5 / f1);
      float f8 = f3 + f7;
      (Math.round(f4) << 24);
      int k = Math.round(f8) << 24;
      (Math.round(f7) << 24);
      arrayOfInt1 = new int[] { k, k, k, k, -16777216, -16777216, k, k, k, k };
      arrayOfFloat1 = new float[] { 0.0F, f5, f5, f1, f1, f2, f2, f6, f6, 1.0F };
    }
  }

  public void setViewAdapter(AbstractWheelTextAdapter<T> paramAbstractWheelTextAdapter)
  {
    super.setViewAdapter(paramAbstractWheelTextAdapter);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.WheelHorizontalView
 * JD-Core Version:    0.6.2
 */