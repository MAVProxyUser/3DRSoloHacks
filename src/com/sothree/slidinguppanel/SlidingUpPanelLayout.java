package com.sothree.slidinguppanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.sothree.slidinguppanel.library.R.drawable;
import com.sothree.slidinguppanel.library.R.styleable;

public class SlidingUpPanelLayout extends SlidingPanelLayout
{
  private static final int DEFAULT_PANEL_HEIGHT = 68;
  private static final int DEFAULT_SHADOW_HEIGHT = 4;
  private static final String TAG = SlidingUpPanelLayout.class.getSimpleName();
  private int mPanelHeight = -1;
  private int mShadowHeight = -1;

  public SlidingUpPanelLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public SlidingUpPanelLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public SlidingUpPanelLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (isInEditMode())
    {
      this.mShadowDrawable = null;
      this.mDragHelper = null;
      return;
    }
    if (paramAttributeSet != null)
    {
      paramContext.obtainStyledAttributes(paramAttributeSet, DEFAULT_ATTRS).recycle();
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SlidingUpPanelLayout);
      if (localTypedArray != null)
      {
        this.mPanelHeight = localTypedArray.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_umanoPanelHeight, -1);
        this.mShadowHeight = localTypedArray.getDimensionPixelSize(R.styleable.SlidingUpPanelLayout_umanoShadowHeight, -1);
      }
      localTypedArray.recycle();
    }
    float f = paramContext.getResources().getDisplayMetrics().density;
    if (this.mPanelHeight == -1)
      this.mPanelHeight = ((int)(0.5F + 68.0F * f));
    if (this.mShadowHeight == -1)
      this.mShadowHeight = ((int)(0.5F + 4.0F * f));
    if (this.mShadowHeight > 0)
      if (this.mIsSliding)
        this.mShadowDrawable = getResources().getDrawable(R.drawable.above_shadow);
    while (true)
    {
      this.mDragHelper = ViewDragHelper.create(this, 0.5F, new DragHelperCallback(null));
      this.mDragHelper.setMinVelocity(f * this.mMinFlingVelocity);
      return;
      this.mShadowDrawable = getResources().getDrawable(R.drawable.below_shadow);
      continue;
      this.mShadowDrawable = null;
    }
  }

  private int computePanelTopPosition(float paramFloat)
  {
    if (this.mSlideableView != null);
    int j;
    for (int i = this.mSlideableView.getMeasuredHeight(); ; i = 0)
    {
      j = (int)(paramFloat * this.mSlideRange);
      if (!this.mIsSliding)
        break;
      return getMeasuredHeight() - getPaddingBottom() - this.mPanelHeight - j;
    }
    return j + (getPaddingTop() - i + this.mPanelHeight);
  }

  private float computeSlideOffset(int paramInt)
  {
    int i = computePanelTopPosition(0.0F);
    if (this.mIsSliding)
      return (i - paramInt) / this.mSlideRange;
    return (paramInt - i) / this.mSlideRange;
  }

  @SuppressLint({"NewApi"})
  private void onPanelDragged(int paramInt)
  {
    this.mSlideState = SlidingPanelLayout.PanelState.DRAGGING;
    this.mSlideOffset = computeSlideOffset(paramInt);
    int k;
    SlidingPanelLayout.LayoutParams localLayoutParams;
    int i;
    if ((this.mParallaxOffset > 0) && (this.mSlideOffset >= 0.0F))
    {
      k = getCurrentParalaxOffset();
      if (Build.VERSION.SDK_INT >= 11)
        this.mMainView.setTranslationY(k);
    }
    else
    {
      dispatchOnPanelSlide(this.mSlideableView);
      localLayoutParams = (SlidingPanelLayout.LayoutParams)this.mMainView.getLayoutParams();
      i = getHeight() - getPaddingBottom() - getPaddingTop() - this.mPanelHeight;
      if ((this.mSlideOffset > 0.0F) || (this.mOverlayContent))
        break label180;
      if (!this.mIsSliding)
        break label156;
      j = paramInt - getPaddingBottom();
      localLayoutParams.height = j;
      this.mMainView.requestLayout();
    }
    label156: label180: 
    while ((localLayoutParams.height == i) || (this.mOverlayContent))
      while (true)
      {
        return;
        AnimatorProxy.wrap(this.mMainView).setTranslationY(k);
        break;
        int j = getHeight() - getPaddingBottom() - this.mSlideableView.getMeasuredHeight() - paramInt;
      }
    localLayoutParams.height = i;
    this.mMainView.requestLayout();
  }

  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i;
    int j;
    if (this.mShadowDrawable != null)
    {
      i = this.mSlideableView.getRight();
      if (!this.mIsSliding)
        break label80;
      j = this.mSlideableView.getTop() - this.mShadowHeight;
    }
    for (int k = this.mSlideableView.getTop(); ; k = this.mSlideableView.getBottom() + this.mShadowHeight)
    {
      int m = this.mSlideableView.getLeft();
      this.mShadowDrawable.setBounds(m, j, i, k);
      this.mShadowDrawable.draw(paramCanvas);
      return;
      label80: j = this.mSlideableView.getBottom();
    }
  }

  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    int i = paramCanvas.save(2);
    boolean bool;
    if (this.mSlideableView != paramView)
    {
      paramCanvas.getClipBounds(this.mTmpRect);
      if (!this.mOverlayContent)
      {
        if (this.mIsSliding)
          this.mTmpRect.bottom = Math.min(this.mTmpRect.bottom, this.mSlideableView.getTop());
      }
      else
      {
        if (this.mClipPanel)
          paramCanvas.clipRect(this.mTmpRect);
        bool = super.drawChild(paramCanvas, paramView, paramLong);
        if ((this.mCoveredFadeColor != 0) && (this.mSlideOffset > 0.0F))
        {
          int j = (int)(((0xFF000000 & this.mCoveredFadeColor) >>> 24) * this.mSlideOffset) << 24 | 0xFFFFFF & this.mCoveredFadeColor;
          this.mCoveredFadePaint.setColor(j);
          paramCanvas.drawRect(this.mTmpRect, this.mCoveredFadePaint);
        }
      }
    }
    while (true)
    {
      paramCanvas.restoreToCount(i);
      return bool;
      this.mTmpRect.top = Math.max(this.mTmpRect.top, this.mSlideableView.getBottom());
      break;
      bool = super.drawChild(paramCanvas, paramView, paramLong);
    }
  }

  public int getPanelHeight()
  {
    return this.mPanelHeight;
  }

  public int getShadowHeight()
  {
    return this.mShadowHeight;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    if ((!isEnabled()) || (!isTouchEnabled()) || ((this.mIsUnableToDrag) && (i != 0)))
    {
      this.mDragHelper.cancel();
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    if ((i == 3) || (i == 1))
    {
      this.mDragHelper.cancel();
      return false;
    }
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    switch (i)
    {
    case 1:
    default:
    case 0:
    case 2:
    }
    float f3;
    float f4;
    int j;
    do
    {
      while (true)
      {
        return this.mDragHelper.shouldInterceptTouchEvent(paramMotionEvent);
        this.mIsUnableToDrag = false;
        this.mInitialMotionX = f1;
        this.mInitialMotionY = f2;
      }
      f3 = Math.abs(f1 - this.mInitialMotionX);
      f4 = Math.abs(f2 - this.mInitialMotionY);
      j = this.mDragHelper.getTouchSlop();
      if ((this.mIsUsingDragViewTouchEvents) && (f3 > j) && (f4 < j))
        return super.onInterceptTouchEvent(paramMotionEvent);
    }
    while (((f4 <= j) || (f3 <= f4)) && (isDragViewUnder((int)this.mInitialMotionX, (int)this.mInitialMotionY)));
    this.mDragHelper.cancel();
    this.mIsUnableToDrag = true;
    return false;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingLeft();
    int j = getPaddingTop();
    int k = getChildCount();
    if (this.mFirstLayout);
    int m;
    label72: View localView;
    switch (1.$SwitchMap$com$sothree$slidinguppanel$SlidingPanelLayout$PanelState[this.mSlideState.ordinal()])
    {
    default:
      this.mSlideOffset = 0.0F;
      m = 0;
      if (m >= k)
        break label280;
      localView = getChildAt(m);
      if ((localView.getVisibility() != 8) || ((m != 0) && (!this.mFirstLayout)))
        break;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      m++;
      break label72;
      this.mSlideOffset = 1.0F;
      break;
      this.mSlideOffset = this.mAnchorPoint;
      break;
      int i3 = computePanelTopPosition(0.0F);
      if (this.mIsSliding);
      for (int i4 = this.mPanelHeight; ; i4 = -this.mPanelHeight)
      {
        this.mSlideOffset = computeSlideOffset(i3 + i4);
        break;
      }
      int n = localView.getMeasuredHeight();
      int i1 = j;
      if (localView == this.mSlideableView)
        i1 = computePanelTopPosition(this.mSlideOffset);
      if ((!this.mIsSliding) && (localView == this.mMainView) && (!this.mOverlayContent))
        i1 = computePanelTopPosition(this.mSlideOffset) + this.mSlideableView.getMeasuredHeight();
      int i2 = i1 + n;
      localView.layout(i, i1, i + localView.getMeasuredWidth(), i2);
    }
    label280: if (this.mFirstLayout)
      updateObscuredViewVisibility();
    this.mFirstLayout = false;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt2);
    if (i != 1073741824)
      throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
    if (k != 1073741824)
      throw new IllegalStateException("Height must have an exact value or MATCH_PARENT");
    int n = getChildCount();
    if (n != 2)
      throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
    this.mMainView = getChildAt(0);
    this.mSlideableView = getChildAt(1);
    if (this.mDragView == null)
      setDragView(this.mSlideableView);
    if (this.mSlideableView.getVisibility() != 0)
      this.mSlideState = SlidingPanelLayout.PanelState.HIDDEN;
    int i1 = m - getPaddingTop() - getPaddingBottom();
    int i2 = 0;
    while (i2 < n)
    {
      View localView = getChildAt(i2);
      SlidingPanelLayout.LayoutParams localLayoutParams = (SlidingPanelLayout.LayoutParams)localView.getLayoutParams();
      if ((localView.getVisibility() == 8) && (i2 == 0))
      {
        i2++;
      }
      else
      {
        int i3 = i1;
        if ((localView == this.mMainView) && (!this.mOverlayContent) && (this.mSlideState != SlidingPanelLayout.PanelState.HIDDEN))
          i3 -= this.mPanelHeight;
        int i4;
        label255: int i5;
        if (localLayoutParams.width == -2)
        {
          i4 = View.MeasureSpec.makeMeasureSpec(j, -2147483648);
          if (localLayoutParams.height != -2)
            break label350;
          i5 = View.MeasureSpec.makeMeasureSpec(i3, -2147483648);
        }
        while (true)
        {
          localView.measure(i4, i5);
          if (localView != this.mSlideableView)
            break;
          this.mSlideRange = (this.mSlideableView.getMeasuredHeight() - this.mPanelHeight);
          break;
          if (localLayoutParams.width == -1)
          {
            i4 = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
            break label255;
          }
          i4 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
          break label255;
          label350: if (localLayoutParams.height == -1)
            i5 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
          else
            i5 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
        }
      }
    }
    setMeasuredDimension(j, m);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt2 != paramInt4)
      this.mFirstLayout = true;
  }

  public void setGravity(int paramInt)
  {
    if ((paramInt != 48) && (paramInt != 80))
      throw new IllegalArgumentException("gravity must be set to either top or bottom");
    if (paramInt == 80);
    for (boolean bool = true; ; bool = false)
    {
      this.mIsSliding = bool;
      if (!this.mFirstLayout)
        requestLayout();
      return;
    }
  }

  public void setPanelHeight(int paramInt)
  {
    this.mPanelHeight = paramInt;
    if (!this.mFirstLayout)
      requestLayout();
  }

  public void setPanelState(SlidingPanelLayout.PanelState paramPanelState)
  {
    if ((paramPanelState == null) || (paramPanelState == SlidingPanelLayout.PanelState.DRAGGING))
      throw new IllegalArgumentException("Panel state cannot be null or DRAGGING.");
    if ((!isEnabled()) || (this.mSlideableView == null) || (paramPanelState == this.mSlideState) || (this.mSlideState == SlidingPanelLayout.PanelState.DRAGGING))
      return;
    if (this.mFirstLayout)
    {
      this.mSlideState = paramPanelState;
      return;
    }
    if (this.mSlideState == SlidingPanelLayout.PanelState.HIDDEN)
    {
      this.mSlideableView.setVisibility(0);
      requestLayout();
    }
    switch (1.$SwitchMap$com$sothree$slidinguppanel$SlidingPanelLayout$PanelState[paramPanelState.ordinal()])
    {
    default:
      return;
    case 1:
      smoothSlideTo(1.0F, 0);
      return;
    case 2:
      smoothSlideTo(this.mAnchorPoint, 0);
      return;
    case 4:
      smoothSlideTo(0.0F, 0);
      return;
    case 3:
    }
    int i = computePanelTopPosition(0.0F);
    if (this.mIsSliding);
    for (int j = this.mPanelHeight; ; j = -this.mPanelHeight)
    {
      smoothSlideTo(computeSlideOffset(i + j), 0);
      return;
    }
  }

  public void setShadowHeight(int paramInt)
  {
    this.mShadowHeight = paramInt;
    if (!this.mFirstLayout)
      invalidate();
  }

  boolean smoothSlideTo(float paramFloat, int paramInt)
  {
    if (!isEnabled());
    int i;
    do
    {
      return false;
      i = computePanelTopPosition(paramFloat);
    }
    while (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, this.mSlideableView.getLeft(), i));
    setAllChildrenVisible();
    ViewCompat.postInvalidateOnAnimation(this);
    return true;
  }

  private class DragHelperCallback extends ViewDragHelper.Callback
  {
    private DragHelperCallback()
    {
    }

    public int clampViewPositionVertical(View paramView, int paramInt1, int paramInt2)
    {
      int i = SlidingUpPanelLayout.this.computePanelTopPosition(0.0F);
      int j = SlidingUpPanelLayout.this.computePanelTopPosition(1.0F);
      if (SlidingUpPanelLayout.this.mIsSliding)
        return Math.min(Math.max(paramInt1, j), i);
      return Math.min(Math.max(paramInt1, i), j);
    }

    public int getViewVerticalDragRange(View paramView)
    {
      return SlidingUpPanelLayout.this.mSlideRange;
    }

    public void onViewCaptured(View paramView, int paramInt)
    {
      SlidingUpPanelLayout.this.setAllChildrenVisible();
    }

    public void onViewDragStateChanged(int paramInt)
    {
      if (SlidingUpPanelLayout.this.mDragHelper.getViewDragState() == 0)
      {
        SlidingUpPanelLayout.this.mSlideOffset = SlidingUpPanelLayout.this.computeSlideOffset(SlidingUpPanelLayout.this.mSlideableView.getTop());
        if (SlidingUpPanelLayout.this.mSlideOffset != 1.0F)
          break label94;
        if (SlidingUpPanelLayout.this.mSlideState != SlidingPanelLayout.PanelState.EXPANDED)
        {
          SlidingUpPanelLayout.this.updateObscuredViewVisibility();
          SlidingUpPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.EXPANDED;
          SlidingUpPanelLayout.this.dispatchOnPanelExpanded(SlidingUpPanelLayout.this.mSlideableView);
        }
      }
      label94: 
      do
      {
        do
        {
          return;
          if (SlidingUpPanelLayout.this.mSlideOffset != 0.0F)
            break;
        }
        while (SlidingUpPanelLayout.this.mSlideState == SlidingPanelLayout.PanelState.COLLAPSED);
        SlidingUpPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.COLLAPSED;
        SlidingUpPanelLayout.this.dispatchOnPanelCollapsed(SlidingUpPanelLayout.this.mSlideableView);
        return;
        if (SlidingUpPanelLayout.this.mSlideOffset < 0.0F)
        {
          SlidingUpPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.HIDDEN;
          SlidingUpPanelLayout.this.mSlideableView.setVisibility(4);
          SlidingUpPanelLayout.this.dispatchOnPanelHidden(SlidingUpPanelLayout.this.mSlideableView);
          return;
        }
      }
      while (SlidingUpPanelLayout.this.mSlideState == SlidingPanelLayout.PanelState.ANCHORED);
      SlidingUpPanelLayout.this.updateObscuredViewVisibility();
      SlidingUpPanelLayout.this.mSlideState = SlidingPanelLayout.PanelState.ANCHORED;
      SlidingUpPanelLayout.this.dispatchOnPanelAnchored(SlidingUpPanelLayout.this.mSlideableView);
    }

    public void onViewPositionChanged(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      SlidingUpPanelLayout.this.onPanelDragged(paramInt2);
      SlidingUpPanelLayout.this.invalidate();
    }

    public void onViewReleased(View paramView, float paramFloat1, float paramFloat2)
    {
      float f;
      int i;
      if (SlidingUpPanelLayout.this.mIsSliding)
      {
        f = -paramFloat2;
        if (f <= 0.0F)
          break label62;
        i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0F);
      }
      while (true)
      {
        SlidingUpPanelLayout.this.mDragHelper.settleCapturedViewAt(paramView.getLeft(), i);
        SlidingUpPanelLayout.this.invalidate();
        return;
        f = paramFloat2;
        break;
        label62: if (f < 0.0F)
          i = SlidingUpPanelLayout.this.computePanelTopPosition(0.0F);
        else if ((SlidingUpPanelLayout.this.mAnchorPoint != 1.0F) && (SlidingUpPanelLayout.this.mSlideOffset >= (1.0F + SlidingUpPanelLayout.this.mAnchorPoint) / 2.0F))
          i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0F);
        else if ((SlidingUpPanelLayout.this.mAnchorPoint == 1.0F) && (SlidingUpPanelLayout.this.mSlideOffset >= 0.5F))
          i = SlidingUpPanelLayout.this.computePanelTopPosition(1.0F);
        else if ((SlidingUpPanelLayout.this.mAnchorPoint != 1.0F) && (SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mAnchorPoint))
          i = SlidingUpPanelLayout.this.computePanelTopPosition(SlidingUpPanelLayout.this.mAnchorPoint);
        else if ((SlidingUpPanelLayout.this.mAnchorPoint != 1.0F) && (SlidingUpPanelLayout.this.mSlideOffset >= SlidingUpPanelLayout.this.mAnchorPoint / 2.0F))
          i = SlidingUpPanelLayout.this.computePanelTopPosition(SlidingUpPanelLayout.this.mAnchorPoint);
        else
          i = SlidingUpPanelLayout.this.computePanelTopPosition(0.0F);
      }
    }

    public boolean tryCaptureView(View paramView, int paramInt)
    {
      if (SlidingUpPanelLayout.this.mIsUnableToDrag);
      while (paramView != SlidingUpPanelLayout.this.mSlideableView)
        return false;
      return true;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.sothree.slidinguppanel.SlidingUpPanelLayout
 * JD-Core Version:    0.6.2
 */