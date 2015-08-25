package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.text.AllCapsTransformationMethod;
import android.support.v7.internal.widget.DrawableUtils;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.internal.widget.ViewUtils;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CompoundButton;
import java.util.List;

public class SwitchCompat extends CompoundButton
{
  private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int MONOSPACE = 3;
  private static final int SANS = 1;
  private static final int SERIF = 2;
  private static final int THUMB_ANIMATION_DURATION = 250;
  private static final int TOUCH_MODE_DOWN = 1;
  private static final int TOUCH_MODE_DRAGGING = 2;
  private static final int TOUCH_MODE_IDLE;
  private int mMinFlingVelocity;
  private Layout mOffLayout;
  private Layout mOnLayout;
  private Animation mPositionAnimator;
  private boolean mShowText;
  private boolean mSplitTrack;
  private int mSwitchBottom;
  private int mSwitchHeight;
  private int mSwitchLeft;
  private int mSwitchMinWidth;
  private int mSwitchPadding;
  private int mSwitchRight;
  private int mSwitchTop;
  private TransformationMethod mSwitchTransformationMethod;
  private int mSwitchWidth;
  private final Rect mTempRect = new Rect();
  private ColorStateList mTextColors;
  private CharSequence mTextOff;
  private CharSequence mTextOn;
  private TextPaint mTextPaint = new TextPaint(1);
  private Drawable mThumbDrawable;
  private float mThumbPosition;
  private int mThumbTextPadding;
  private int mThumbWidth;
  private final TintManager mTintManager;
  private int mTouchMode;
  private int mTouchSlop;
  private float mTouchX;
  private float mTouchY;
  private Drawable mTrackDrawable;
  private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

  public SwitchCompat(Context paramContext)
  {
    this(paramContext, null);
  }

  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.switchStyle);
  }

  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getResources();
    this.mTextPaint.density = localResources.getDisplayMetrics().density;
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.SwitchCompat, paramInt, 0);
    this.mThumbDrawable = localTintTypedArray.getDrawable(R.styleable.SwitchCompat_android_thumb);
    if (this.mThumbDrawable != null)
      this.mThumbDrawable.setCallback(this);
    this.mTrackDrawable = localTintTypedArray.getDrawable(R.styleable.SwitchCompat_track);
    if (this.mTrackDrawable != null)
      this.mTrackDrawable.setCallback(this);
    this.mTextOn = localTintTypedArray.getText(R.styleable.SwitchCompat_android_textOn);
    this.mTextOff = localTintTypedArray.getText(R.styleable.SwitchCompat_android_textOff);
    this.mShowText = localTintTypedArray.getBoolean(R.styleable.SwitchCompat_showText, true);
    this.mThumbTextPadding = localTintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
    this.mSwitchMinWidth = localTintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
    this.mSwitchPadding = localTintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
    this.mSplitTrack = localTintTypedArray.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
    int i = localTintTypedArray.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
    if (i != 0)
      setSwitchTextAppearance(paramContext, i);
    this.mTintManager = localTintTypedArray.getTintManager();
    localTintTypedArray.recycle();
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    refreshDrawableState();
    setChecked(isChecked());
  }

  private void animateThumbToCheckedState(boolean paramBoolean)
  {
    final float f1 = this.mThumbPosition;
    if (paramBoolean);
    for (float f2 = 1.0F; ; f2 = 0.0F)
    {
      this.mPositionAnimator = new Animation()
      {
        protected void applyTransformation(float paramAnonymousFloat, Transformation paramAnonymousTransformation)
        {
          SwitchCompat.this.setThumbPosition(f1 + paramAnonymousFloat * this.val$diff);
        }
      };
      this.mPositionAnimator.setDuration(250L);
      startAnimation(this.mPositionAnimator);
      return;
    }
  }

  private void cancelPositionAnimator()
  {
    if (this.mPositionAnimator != null)
    {
      clearAnimation();
      this.mPositionAnimator = null;
    }
  }

  private void cancelSuperTouch(MotionEvent paramMotionEvent)
  {
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    localMotionEvent.setAction(3);
    super.onTouchEvent(localMotionEvent);
    localMotionEvent.recycle();
  }

  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2)
      return paramFloat2;
    if (paramFloat1 > paramFloat3)
      return paramFloat3;
    return paramFloat1;
  }

  private boolean getTargetCheckedState()
  {
    return this.mThumbPosition > 0.5F;
  }

  private int getThumbOffset()
  {
    if (ViewUtils.isLayoutRtl(this));
    for (float f = 1.0F - this.mThumbPosition; ; f = this.mThumbPosition)
      return (int)(0.5F + f * getThumbScrollRange());
  }

  private int getThumbScrollRange()
  {
    if (this.mTrackDrawable != null)
    {
      Rect localRect1 = this.mTempRect;
      this.mTrackDrawable.getPadding(localRect1);
      if (this.mThumbDrawable != null);
      for (Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable); ; localRect2 = DrawableUtils.INSETS_NONE)
        return this.mSwitchWidth - this.mThumbWidth - localRect1.left - localRect1.right - localRect2.left - localRect2.right;
    }
    return 0;
  }

  private boolean hitThumb(float paramFloat1, float paramFloat2)
  {
    if (this.mThumbDrawable == null);
    int j;
    int k;
    int m;
    int n;
    do
    {
      return false;
      int i = getThumbOffset();
      this.mThumbDrawable.getPadding(this.mTempRect);
      j = this.mSwitchTop - this.mTouchSlop;
      k = i + this.mSwitchLeft - this.mTouchSlop;
      m = k + this.mThumbWidth + this.mTempRect.left + this.mTempRect.right + this.mTouchSlop;
      n = this.mSwitchBottom + this.mTouchSlop;
    }
    while ((paramFloat1 <= k) || (paramFloat1 >= m) || (paramFloat2 <= j) || (paramFloat2 >= n));
    return true;
  }

  private Layout makeLayout(CharSequence paramCharSequence)
  {
    CharSequence localCharSequence;
    TextPaint localTextPaint;
    if (this.mSwitchTransformationMethod != null)
    {
      localCharSequence = this.mSwitchTransformationMethod.getTransformation(paramCharSequence, this);
      localTextPaint = this.mTextPaint;
      if (localCharSequence == null)
        break label66;
    }
    label66: for (int i = (int)Math.ceil(Layout.getDesiredWidth(localCharSequence, this.mTextPaint)); ; i = 0)
    {
      return new StaticLayout(localCharSequence, localTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
      localCharSequence = paramCharSequence;
      break;
    }
  }

  private void setSwitchTypefaceByIndex(int paramInt1, int paramInt2)
  {
    Typeface localTypeface = null;
    switch (paramInt1)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      setSwitchTypeface(localTypeface, paramInt2);
      return;
      localTypeface = Typeface.SANS_SERIF;
      continue;
      localTypeface = Typeface.SERIF;
      continue;
      localTypeface = Typeface.MONOSPACE;
    }
  }

  private void setThumbPosition(float paramFloat)
  {
    this.mThumbPosition = paramFloat;
    invalidate();
  }

  private void stopDrag(MotionEvent paramMotionEvent)
  {
    this.mTouchMode = 0;
    int i;
    boolean bool1;
    float f;
    boolean bool2;
    if ((paramMotionEvent.getAction() == 1) && (isEnabled()))
    {
      i = 1;
      bool1 = isChecked();
      if (i == 0)
        break label143;
      this.mVelocityTracker.computeCurrentVelocity(1000);
      f = this.mVelocityTracker.getXVelocity();
      if (Math.abs(f) <= this.mMinFlingVelocity)
        break label134;
      if (!ViewUtils.isLayoutRtl(this))
        break label115;
      if (f >= 0.0F)
        break label109;
      bool2 = true;
    }
    label134: label143: 
    while (true)
    {
      if (bool2 != bool1)
      {
        playSoundEffect(0);
        setChecked(bool2);
      }
      cancelSuperTouch(paramMotionEvent);
      return;
      i = 0;
      break;
      label109: bool2 = false;
      continue;
      label115: if (f > 0.0F)
      {
        bool2 = true;
      }
      else
      {
        bool2 = false;
        continue;
        bool2 = getTargetCheckedState();
        continue;
        bool2 = bool1;
      }
    }
  }

  public void draw(Canvas paramCanvas)
  {
    Rect localRect1 = this.mTempRect;
    int i = this.mSwitchLeft;
    int j = this.mSwitchTop;
    int k = this.mSwitchRight;
    int m = this.mSwitchBottom;
    int n = i + getThumbOffset();
    if (this.mThumbDrawable != null);
    for (Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable); ; localRect2 = DrawableUtils.INSETS_NONE)
    {
      if (this.mTrackDrawable != null)
      {
        this.mTrackDrawable.getPadding(localRect1);
        n += localRect1.left;
        int i3 = i;
        int i4 = j;
        int i5 = k;
        int i6 = m;
        if ((localRect2 != null) && (!localRect2.isEmpty()))
        {
          if (localRect2.left > localRect1.left)
            i3 += localRect2.left - localRect1.left;
          if (localRect2.top > localRect1.top)
            i4 += localRect2.top - localRect1.top;
          if (localRect2.right > localRect1.right)
            i5 -= localRect2.right - localRect1.right;
          if (localRect2.bottom > localRect1.bottom)
            i6 -= localRect2.bottom - localRect1.bottom;
        }
        this.mTrackDrawable.setBounds(i3, i4, i5, i6);
      }
      if (this.mThumbDrawable != null)
      {
        this.mThumbDrawable.getPadding(localRect1);
        int i1 = n - localRect1.left;
        int i2 = n + this.mThumbWidth + localRect1.right;
        this.mThumbDrawable.setBounds(i1, j, i2, m);
        Drawable localDrawable = getBackground();
        if (localDrawable != null)
          DrawableCompat.setHotspotBounds(localDrawable, i1, j, i2, m);
      }
      super.draw(paramCanvas);
      return;
    }
  }

  public void drawableHotspotChanged(float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21)
      super.drawableHotspotChanged(paramFloat1, paramFloat2);
    if (this.mThumbDrawable != null)
      DrawableCompat.setHotspot(this.mThumbDrawable, paramFloat1, paramFloat2);
    if (this.mTrackDrawable != null)
      DrawableCompat.setHotspot(this.mTrackDrawable, paramFloat1, paramFloat2);
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    if (this.mThumbDrawable != null)
      this.mThumbDrawable.setState(arrayOfInt);
    if (this.mTrackDrawable != null)
      this.mTrackDrawable.setState(arrayOfInt);
    invalidate();
  }

  public int getCompoundPaddingLeft()
  {
    int i;
    if (!ViewUtils.isLayoutRtl(this))
      i = super.getCompoundPaddingLeft();
    do
    {
      return i;
      i = super.getCompoundPaddingLeft() + this.mSwitchWidth;
    }
    while (TextUtils.isEmpty(getText()));
    return i + this.mSwitchPadding;
  }

  public int getCompoundPaddingRight()
  {
    int i;
    if (ViewUtils.isLayoutRtl(this))
      i = super.getCompoundPaddingRight();
    do
    {
      return i;
      i = super.getCompoundPaddingRight() + this.mSwitchWidth;
    }
    while (TextUtils.isEmpty(getText()));
    return i + this.mSwitchPadding;
  }

  public boolean getShowText()
  {
    return this.mShowText;
  }

  public boolean getSplitTrack()
  {
    return this.mSplitTrack;
  }

  public int getSwitchMinWidth()
  {
    return this.mSwitchMinWidth;
  }

  public int getSwitchPadding()
  {
    return this.mSwitchPadding;
  }

  public CharSequence getTextOff()
  {
    return this.mTextOff;
  }

  public CharSequence getTextOn()
  {
    return this.mTextOn;
  }

  public Drawable getThumbDrawable()
  {
    return this.mThumbDrawable;
  }

  public int getThumbTextPadding()
  {
    return this.mThumbTextPadding;
  }

  public Drawable getTrackDrawable()
  {
    return this.mTrackDrawable;
  }

  public void jumpDrawablesToCurrentState()
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      super.jumpDrawablesToCurrentState();
      if (this.mThumbDrawable != null)
        this.mThumbDrawable.jumpToCurrentState();
      if (this.mTrackDrawable != null)
        this.mTrackDrawable.jumpToCurrentState();
      if ((this.mPositionAnimator != null) && (!this.mPositionAnimator.hasEnded()))
      {
        clearAnimation();
        this.mPositionAnimator = null;
      }
    }
  }

  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    return arrayOfInt;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Rect localRect1 = this.mTempRect;
    Drawable localDrawable1 = this.mTrackDrawable;
    int k;
    int m;
    label144: int n;
    Layout localLayout;
    label174: Rect localRect2;
    if (localDrawable1 != null)
    {
      localDrawable1.getPadding(localRect1);
      int i = this.mSwitchTop;
      int j = this.mSwitchBottom;
      k = i + localRect1.top;
      m = j - localRect1.bottom;
      Drawable localDrawable2 = this.mThumbDrawable;
      if (localDrawable1 != null)
      {
        if ((!this.mSplitTrack) || (localDrawable2 == null))
          break label304;
        Rect localRect3 = DrawableUtils.getOpticalBounds(localDrawable2);
        localDrawable2.copyBounds(localRect1);
        localRect1.left += localRect3.left;
        localRect1.right -= localRect3.right;
        int i4 = paramCanvas.save();
        paramCanvas.clipRect(localRect1, Region.Op.DIFFERENCE);
        localDrawable1.draw(paramCanvas);
        paramCanvas.restoreToCount(i4);
      }
      n = paramCanvas.save();
      if (localDrawable2 != null)
        localDrawable2.draw(paramCanvas);
      if (!getTargetCheckedState())
        break label312;
      localLayout = this.mOnLayout;
      if (localLayout != null)
      {
        int[] arrayOfInt = getDrawableState();
        if (this.mTextColors != null)
          this.mTextPaint.setColor(this.mTextColors.getColorForState(arrayOfInt, 0));
        this.mTextPaint.drawableState = arrayOfInt;
        if (localDrawable2 == null)
          break label321;
        localRect2 = localDrawable2.getBounds();
      }
    }
    label304: label312: label321: for (int i1 = localRect2.left + localRect2.right; ; i1 = getWidth())
    {
      int i2 = i1 / 2 - localLayout.getWidth() / 2;
      int i3 = (k + m) / 2 - localLayout.getHeight() / 2;
      paramCanvas.translate(i2, i3);
      localLayout.draw(paramCanvas);
      paramCanvas.restoreToCount(n);
      return;
      localRect1.setEmpty();
      break;
      localDrawable1.draw(paramCanvas);
      break label144;
      localLayout = this.mOffLayout;
      break label174;
    }
  }

  @TargetApi(14)
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName("android.widget.Switch");
  }

  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName("android.widget.Switch");
      if (!isChecked())
        break label56;
    }
    CharSequence localCharSequence2;
    label56: for (CharSequence localCharSequence1 = this.mTextOn; ; localCharSequence1 = this.mTextOff)
    {
      if (!TextUtils.isEmpty(localCharSequence1))
      {
        localCharSequence2 = paramAccessibilityNodeInfo.getText();
        if (!TextUtils.isEmpty(localCharSequence2))
          break;
        paramAccessibilityNodeInfo.setText(localCharSequence1);
      }
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(localCharSequence2).append(' ').append(localCharSequence1);
    paramAccessibilityNodeInfo.setText(localStringBuilder);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    Drawable localDrawable = this.mThumbDrawable;
    int i = 0;
    int j = 0;
    Rect localRect1;
    int m;
    int k;
    label125: int i1;
    int n;
    if (localDrawable != null)
    {
      localRect1 = this.mTempRect;
      if (this.mTrackDrawable != null)
      {
        this.mTrackDrawable.getPadding(localRect1);
        Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        i = Math.max(0, localRect2.left - localRect1.left);
        j = Math.max(0, localRect2.right - localRect1.right);
      }
    }
    else
    {
      if (!ViewUtils.isLayoutRtl(this))
        break label208;
      m = i + getPaddingLeft();
      k = m + this.mSwitchWidth - i - j;
      switch (0x70 & getGravity())
      {
      default:
        i1 = getPaddingTop();
        n = i1 + this.mSwitchHeight;
      case 16:
      case 80:
      }
    }
    while (true)
    {
      this.mSwitchLeft = m;
      this.mSwitchTop = i1;
      this.mSwitchBottom = n;
      this.mSwitchRight = k;
      return;
      localRect1.setEmpty();
      break;
      label208: k = getWidth() - getPaddingRight() - j;
      m = j + (i + (k - this.mSwitchWidth));
      break label125;
      i1 = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - this.mSwitchHeight / 2;
      n = i1 + this.mSwitchHeight;
      continue;
      n = getHeight() - getPaddingBottom();
      i1 = n - this.mSwitchHeight;
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mShowText)
    {
      if (this.mOnLayout == null)
        this.mOnLayout = makeLayout(this.mTextOn);
      if (this.mOffLayout == null)
        this.mOffLayout = makeLayout(this.mTextOff);
    }
    Rect localRect1 = this.mTempRect;
    int j;
    int i;
    int k;
    if (this.mThumbDrawable != null)
    {
      this.mThumbDrawable.getPadding(localRect1);
      j = this.mThumbDrawable.getIntrinsicWidth() - localRect1.left - localRect1.right;
      i = this.mThumbDrawable.getIntrinsicHeight();
      if (!this.mShowText)
        break label292;
      k = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + 2 * this.mThumbTextPadding;
      label127: this.mThumbWidth = Math.max(k, j);
      if (this.mTrackDrawable == null)
        break label298;
      this.mTrackDrawable.getPadding(localRect1);
    }
    for (int m = this.mTrackDrawable.getIntrinsicHeight(); ; m = 0)
    {
      int n = localRect1.left;
      int i1 = localRect1.right;
      if (this.mThumbDrawable != null)
      {
        Rect localRect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
        n = Math.max(n, localRect2.left);
        i1 = Math.max(i1, localRect2.right);
      }
      int i2 = Math.max(this.mSwitchMinWidth, i1 + (n + 2 * this.mThumbWidth));
      int i3 = Math.max(m, i);
      this.mSwitchWidth = i2;
      this.mSwitchHeight = i3;
      super.onMeasure(paramInt1, paramInt2);
      if (getMeasuredHeight() < i3)
        setMeasuredDimension(ViewCompat.getMeasuredWidthAndState(this), i3);
      return;
      i = 0;
      j = 0;
      break;
      label292: k = 0;
      break label127;
      label298: localRect1.setEmpty();
    }
  }

  @TargetApi(14)
  public void onPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onPopulateAccessibilityEvent(paramAccessibilityEvent);
    if (isChecked());
    for (CharSequence localCharSequence = this.mTextOn; ; localCharSequence = this.mTextOff)
    {
      if (localCharSequence != null)
        paramAccessibilityEvent.getText().add(localCharSequence);
      return;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (MotionEventCompat.getActionMasked(paramMotionEvent))
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      return super.onTouchEvent(paramMotionEvent);
      float f7 = paramMotionEvent.getX();
      float f8 = paramMotionEvent.getY();
      if ((isEnabled()) && (hitThumb(f7, f8)))
      {
        this.mTouchMode = 1;
        this.mTouchX = f7;
        this.mTouchY = f8;
        continue;
        switch (this.mTouchMode)
        {
        case 0:
        default:
          break;
        case 1:
          float f5 = paramMotionEvent.getX();
          float f6 = paramMotionEvent.getY();
          if ((Math.abs(f5 - this.mTouchX) > this.mTouchSlop) || (Math.abs(f6 - this.mTouchY) > this.mTouchSlop))
          {
            this.mTouchMode = 2;
            getParent().requestDisallowInterceptTouchEvent(true);
            this.mTouchX = f5;
            this.mTouchY = f6;
            return true;
          }
          break;
        case 2:
          float f1 = paramMotionEvent.getX();
          int i = getThumbScrollRange();
          float f2 = f1 - this.mTouchX;
          if (i != 0)
          {
            f3 = f2 / i;
            if (ViewUtils.isLayoutRtl(this))
              f3 = -f3;
            float f4 = constrain(f3 + this.mThumbPosition, 0.0F, 1.0F);
            if (f4 != this.mThumbPosition)
            {
              this.mTouchX = f1;
              setThumbPosition(f4);
            }
            return true;
          }
          if (f2 > 0.0F);
          for (float f3 = 1.0F; ; f3 = -1.0F)
            break;
          if (this.mTouchMode == 2)
          {
            stopDrag(paramMotionEvent);
            super.onTouchEvent(paramMotionEvent);
            return true;
          }
          this.mTouchMode = 0;
          this.mVelocityTracker.clear();
        }
      }
    }
  }

  public void setChecked(boolean paramBoolean)
  {
    super.setChecked(paramBoolean);
    boolean bool = isChecked();
    if ((getWindowToken() != null) && (ViewCompat.isLaidOut(this)))
    {
      animateThumbToCheckedState(bool);
      return;
    }
    cancelPositionAnimator();
    if (bool);
    for (float f = 1.0F; ; f = 0.0F)
    {
      setThumbPosition(f);
      return;
    }
  }

  public void setShowText(boolean paramBoolean)
  {
    if (this.mShowText != paramBoolean)
    {
      this.mShowText = paramBoolean;
      requestLayout();
    }
  }

  public void setSplitTrack(boolean paramBoolean)
  {
    this.mSplitTrack = paramBoolean;
    invalidate();
  }

  public void setSwitchMinWidth(int paramInt)
  {
    this.mSwitchMinWidth = paramInt;
    requestLayout();
  }

  public void setSwitchPadding(int paramInt)
  {
    this.mSwitchPadding = paramInt;
    requestLayout();
  }

  public void setSwitchTextAppearance(Context paramContext, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.TextAppearance);
    ColorStateList localColorStateList = localTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
    if (localColorStateList != null)
    {
      this.mTextColors = localColorStateList;
      int i = localTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
      if ((i != 0) && (i != this.mTextPaint.getTextSize()))
      {
        this.mTextPaint.setTextSize(i);
        requestLayout();
      }
      setSwitchTypefaceByIndex(localTypedArray.getInt(R.styleable.TextAppearance_android_typeface, -1), localTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, -1));
      if (!localTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false))
        break label134;
    }
    label134: for (this.mSwitchTransformationMethod = new AllCapsTransformationMethod(getContext()); ; this.mSwitchTransformationMethod = null)
    {
      localTypedArray.recycle();
      return;
      this.mTextColors = getTextColors();
      break;
    }
  }

  public void setSwitchTypeface(Typeface paramTypeface)
  {
    if (this.mTextPaint.getTypeface() != paramTypeface)
    {
      this.mTextPaint.setTypeface(paramTypeface);
      requestLayout();
      invalidate();
    }
  }

  public void setSwitchTypeface(Typeface paramTypeface, int paramInt)
  {
    if (paramInt > 0)
    {
      Typeface localTypeface;
      int i;
      label28: TextPaint localTextPaint2;
      if (paramTypeface == null)
      {
        localTypeface = Typeface.defaultFromStyle(paramInt);
        setSwitchTypeface(localTypeface);
        if (localTypeface == null)
          break label101;
        i = localTypeface.getStyle();
        int j = paramInt & (i ^ 0xFFFFFFFF);
        TextPaint localTextPaint1 = this.mTextPaint;
        int k = j & 0x1;
        boolean bool = false;
        if (k != 0)
          bool = true;
        localTextPaint1.setFakeBoldText(bool);
        localTextPaint2 = this.mTextPaint;
        if ((j & 0x2) == 0)
          break label107;
      }
      label101: label107: for (float f = -0.25F; ; f = 0.0F)
      {
        localTextPaint2.setTextSkewX(f);
        return;
        localTypeface = Typeface.create(paramTypeface, paramInt);
        break;
        i = 0;
        break label28;
      }
    }
    this.mTextPaint.setFakeBoldText(false);
    this.mTextPaint.setTextSkewX(0.0F);
    setSwitchTypeface(paramTypeface);
  }

  public void setTextOff(CharSequence paramCharSequence)
  {
    this.mTextOff = paramCharSequence;
    requestLayout();
  }

  public void setTextOn(CharSequence paramCharSequence)
  {
    this.mTextOn = paramCharSequence;
    requestLayout();
  }

  public void setThumbDrawable(Drawable paramDrawable)
  {
    this.mThumbDrawable = paramDrawable;
    requestLayout();
  }

  public void setThumbResource(int paramInt)
  {
    setThumbDrawable(this.mTintManager.getDrawable(paramInt));
  }

  public void setThumbTextPadding(int paramInt)
  {
    this.mThumbTextPadding = paramInt;
    requestLayout();
  }

  public void setTrackDrawable(Drawable paramDrawable)
  {
    this.mTrackDrawable = paramDrawable;
    requestLayout();
  }

  public void setTrackResource(int paramInt)
  {
    setTrackDrawable(this.mTintManager.getDrawable(paramInt));
  }

  public void toggle()
  {
    if (!isChecked());
    for (boolean bool = true; ; bool = false)
    {
      setChecked(bool);
      return;
    }
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    return (super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mThumbDrawable) || (paramDrawable == this.mTrackDrawable);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.SwitchCompat
 * JD-Core Version:    0.6.2
 */