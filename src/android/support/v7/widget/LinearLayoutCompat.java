package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.internal.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup
{
  public static final int HORIZONTAL = 0;
  private static final int INDEX_BOTTOM = 2;
  private static final int INDEX_CENTER_VERTICAL = 0;
  private static final int INDEX_FILL = 3;
  private static final int INDEX_TOP = 1;
  public static final int SHOW_DIVIDER_BEGINNING = 1;
  public static final int SHOW_DIVIDER_END = 4;
  public static final int SHOW_DIVIDER_MIDDLE = 2;
  public static final int SHOW_DIVIDER_NONE = 0;
  public static final int VERTICAL = 1;
  private static final int VERTICAL_GRAVITY_COUNT = 4;
  private boolean mBaselineAligned = true;
  private int mBaselineAlignedChildIndex = -1;
  private int mBaselineChildTop = 0;
  private Drawable mDivider;
  private int mDividerHeight;
  private int mDividerPadding;
  private int mDividerWidth;
  private int mGravity = 8388659;
  private int[] mMaxAscent;
  private int[] mMaxDescent;
  private int mOrientation;
  private int mShowDividers;
  private int mTotalLength;
  private boolean mUseLargestChild;
  private float mWeightSum;

  public LinearLayoutCompat(Context paramContext)
  {
    this(paramContext, null);
  }

  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.LinearLayoutCompat, paramInt, 0);
    int i = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
    if (i >= 0)
      setOrientation(i);
    int j = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
    if (j >= 0)
      setGravity(j);
    boolean bool = localTintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
    if (!bool)
      setBaselineAligned(bool);
    this.mWeightSum = localTintTypedArray.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0F);
    this.mBaselineAlignedChildIndex = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
    this.mUseLargestChild = localTintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
    setDividerDrawable(localTintTypedArray.getDrawable(R.styleable.LinearLayoutCompat_divider));
    this.mShowDividers = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
    this.mDividerPadding = localTintTypedArray.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
    localTintTypedArray.recycle();
  }

  private void forceUniformHeight(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
    for (int j = 0; j < paramInt1; j++)
    {
      View localView = getVirtualChildAt(j);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.height == -1)
        {
          int k = localLayoutParams.width;
          localLayoutParams.width = localView.getMeasuredWidth();
          measureChildWithMargins(localView, paramInt2, 0, i, 0);
          localLayoutParams.width = k;
        }
      }
    }
  }

  private void forceUniformWidth(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (int j = 0; j < paramInt1; j++)
    {
      View localView = getVirtualChildAt(j);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.width == -1)
        {
          int k = localLayoutParams.height;
          localLayoutParams.height = localView.getMeasuredHeight();
          measureChildWithMargins(localView, i, 0, paramInt2, 0);
          localLayoutParams.height = k;
        }
      }
    }
  }

  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramView.layout(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  void drawDividersHorizontal(Canvas paramCanvas)
  {
    int i = getVirtualChildCount();
    boolean bool = ViewUtils.isLayoutRtl(this);
    int j = 0;
    if (j < i)
    {
      View localView2 = getVirtualChildAt(j);
      LayoutParams localLayoutParams2;
      if ((localView2 != null) && (localView2.getVisibility() != 8) && (hasDividerBeforeChildAt(j)))
      {
        localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        if (!bool)
          break label91;
      }
      label91: for (int m = localView2.getRight() + localLayoutParams2.rightMargin; ; m = localView2.getLeft() - localLayoutParams2.leftMargin - this.mDividerWidth)
      {
        drawVerticalDivider(paramCanvas, m);
        j++;
        break;
      }
    }
    View localView1;
    int k;
    if (hasDividerBeforeChildAt(i))
    {
      localView1 = getVirtualChildAt(i - 1);
      if (localView1 != null)
        break label171;
      if (!bool)
        break label152;
      k = getPaddingLeft();
    }
    while (true)
    {
      drawVerticalDivider(paramCanvas, k);
      return;
      label152: k = getWidth() - getPaddingRight() - this.mDividerWidth;
      continue;
      label171: LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      if (bool)
        k = localView1.getLeft() - localLayoutParams1.leftMargin - this.mDividerWidth;
      else
        k = localView1.getRight() + localLayoutParams1.rightMargin;
    }
  }

  void drawDividersVertical(Canvas paramCanvas)
  {
    int i = getVirtualChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView2 = getVirtualChildAt(j);
      if ((localView2 != null) && (localView2.getVisibility() != 8) && (hasDividerBeforeChildAt(j)))
      {
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        drawHorizontalDivider(paramCanvas, localView2.getTop() - localLayoutParams2.topMargin - this.mDividerHeight);
      }
    }
    View localView1;
    if (hasDividerBeforeChildAt(i))
    {
      localView1 = getVirtualChildAt(i - 1);
      if (localView1 != null)
        break label125;
    }
    label125: LayoutParams localLayoutParams1;
    for (int k = getHeight() - getPaddingBottom() - this.mDividerHeight; ; k = localView1.getBottom() + localLayoutParams1.bottomMargin)
    {
      drawHorizontalDivider(paramCanvas, k);
      return;
      localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
    }
  }

  void drawHorizontalDivider(Canvas paramCanvas, int paramInt)
  {
    this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, paramInt, getWidth() - getPaddingRight() - this.mDividerPadding, paramInt + this.mDividerHeight);
    this.mDivider.draw(paramCanvas);
  }

  void drawVerticalDivider(Canvas paramCanvas, int paramInt)
  {
    this.mDivider.setBounds(paramInt, getPaddingTop() + this.mDividerPadding, paramInt + this.mDividerWidth, getHeight() - getPaddingBottom() - this.mDividerPadding);
    this.mDivider.draw(paramCanvas);
  }

  protected LayoutParams generateDefaultLayoutParams()
  {
    if (this.mOrientation == 0)
      return new LayoutParams(-2, -2);
    if (this.mOrientation == 1)
      return new LayoutParams(-1, -2);
    return null;
  }

  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }

  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }

  public int getBaseline()
  {
    int i = -1;
    if (this.mBaselineAlignedChildIndex < 0)
      i = super.getBaseline();
    View localView;
    int j;
    do
    {
      return i;
      if (getChildCount() <= this.mBaselineAlignedChildIndex)
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
      localView = getChildAt(this.mBaselineAlignedChildIndex);
      j = localView.getBaseline();
      if (j != i)
        break;
    }
    while (this.mBaselineAlignedChildIndex == 0);
    throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
    int k = this.mBaselineChildTop;
    if (this.mOrientation == 1)
    {
      int m = 0x70 & this.mGravity;
      if (m != 48)
        switch (m)
        {
        default:
        case 80:
        case 16:
        }
    }
    while (true)
    {
      return j + (k + ((LayoutParams)localView.getLayoutParams()).topMargin);
      k = getBottom() - getTop() - getPaddingBottom() - this.mTotalLength;
      continue;
      k += (getBottom() - getTop() - getPaddingTop() - getPaddingBottom() - this.mTotalLength) / 2;
    }
  }

  public int getBaselineAlignedChildIndex()
  {
    return this.mBaselineAlignedChildIndex;
  }

  int getChildrenSkipCount(View paramView, int paramInt)
  {
    return 0;
  }

  public Drawable getDividerDrawable()
  {
    return this.mDivider;
  }

  public int getDividerPadding()
  {
    return this.mDividerPadding;
  }

  public int getDividerWidth()
  {
    return this.mDividerWidth;
  }

  int getLocationOffset(View paramView)
  {
    return 0;
  }

  int getNextLocationOffset(View paramView)
  {
    return 0;
  }

  public int getOrientation()
  {
    return this.mOrientation;
  }

  public int getShowDividers()
  {
    return this.mShowDividers;
  }

  View getVirtualChildAt(int paramInt)
  {
    return getChildAt(paramInt);
  }

  int getVirtualChildCount()
  {
    return getChildCount();
  }

  public float getWeightSum()
  {
    return this.mWeightSum;
  }

  protected boolean hasDividerBeforeChildAt(int paramInt)
  {
    if (paramInt == 0)
      if ((0x1 & this.mShowDividers) == 0);
    do
    {
      return true;
      return false;
      if (paramInt != getChildCount())
        break;
    }
    while ((0x4 & this.mShowDividers) != 0);
    return false;
    if ((0x2 & this.mShowDividers) != 0)
      for (int i = paramInt - 1; ; i--)
      {
        boolean bool = false;
        if (i >= 0)
        {
          if (getChildAt(i).getVisibility() != 8)
            bool = true;
        }
        else
          return bool;
      }
    return false;
  }

  public boolean isBaselineAligned()
  {
    return this.mBaselineAligned;
  }

  public boolean isMeasureWithLargestChildEnabled()
  {
    return this.mUseLargestChild;
  }

  void layoutHorizontal(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = ViewUtils.isLayoutRtl(this);
    int i = getPaddingTop();
    int j = paramInt4 - paramInt2;
    int k = j - getPaddingBottom();
    int m = j - i - getPaddingBottom();
    int n = getVirtualChildCount();
    int i1 = 0x800007 & this.mGravity;
    int i2 = 0x70 & this.mGravity;
    boolean bool2 = this.mBaselineAligned;
    int[] arrayOfInt1 = this.mMaxAscent;
    int[] arrayOfInt2 = this.mMaxDescent;
    int i3;
    int i6;
    label145: int i7;
    View localView;
    switch (GravityCompat.getAbsoluteGravity(i1, ViewCompat.getLayoutDirection(this)))
    {
    default:
      i3 = getPaddingLeft();
      int i4 = 1;
      int i5 = 0;
      if (bool1)
      {
        i5 = n - 1;
        i4 = -1;
      }
      i6 = 0;
      if (i6 >= n)
        return;
      i7 = i5 + i4 * i6;
      localView = getVirtualChildAt(i7);
      if (localView == null)
        i3 += measureNullChild(i7);
      break;
    case 5:
    case 1:
    }
    while (localView.getVisibility() == 8)
    {
      i6++;
      break label145;
      i3 = paramInt3 + getPaddingLeft() - paramInt1 - this.mTotalLength;
      break;
      i3 = getPaddingLeft() + (paramInt3 - paramInt1 - this.mTotalLength) / 2;
      break;
    }
    int i8 = localView.getMeasuredWidth();
    int i9 = localView.getMeasuredHeight();
    int i10 = -1;
    LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
    if ((bool2) && (localLayoutParams.height != -1))
      i10 = localView.getBaseline();
    int i11 = localLayoutParams.gravity;
    if (i11 < 0)
      i11 = i2;
    int i12;
    switch (i11 & 0x70)
    {
    default:
      i12 = i;
    case 48:
    case 16:
    case 80:
    }
    while (true)
    {
      if (hasDividerBeforeChildAt(i7))
        i3 += this.mDividerWidth;
      int i14 = i3 + localLayoutParams.leftMargin;
      setChildFrame(localView, i14 + getLocationOffset(localView), i12, i8, i9);
      i3 = i14 + (i8 + localLayoutParams.rightMargin + getNextLocationOffset(localView));
      i6 += getChildrenSkipCount(localView, i7);
      break;
      i12 = i + localLayoutParams.topMargin;
      if (i10 != -1)
      {
        i12 += arrayOfInt1[1] - i10;
        continue;
        i12 = i + (m - i9) / 2 + localLayoutParams.topMargin - localLayoutParams.bottomMargin;
        continue;
        i12 = k - i9 - localLayoutParams.bottomMargin;
        if (i10 != -1)
        {
          int i13 = localView.getMeasuredHeight() - i10;
          i12 -= arrayOfInt2[2] - i13;
        }
      }
    }
  }

  void layoutVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingLeft();
    int j = paramInt3 - paramInt1;
    int k = j - getPaddingRight();
    int m = j - i - getPaddingRight();
    int n = getVirtualChildCount();
    int i1 = 0x70 & this.mGravity;
    int i2 = 0x800007 & this.mGravity;
    int i3;
    int i4;
    label93: View localView;
    switch (i1)
    {
    default:
      i3 = getPaddingTop();
      i4 = 0;
      if (i4 >= n)
        return;
      localView = getVirtualChildAt(i4);
      if (localView == null)
        i3 += measureNullChild(i4);
      break;
    case 80:
    case 16:
    }
    while (localView.getVisibility() == 8)
    {
      i4++;
      break label93;
      i3 = paramInt4 + getPaddingTop() - paramInt2 - this.mTotalLength;
      break;
      i3 = getPaddingTop() + (paramInt4 - paramInt2 - this.mTotalLength) / 2;
      break;
    }
    int i5 = localView.getMeasuredWidth();
    int i6 = localView.getMeasuredHeight();
    LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
    int i7 = localLayoutParams.gravity;
    if (i7 < 0)
      i7 = i2;
    int i8;
    switch (0x7 & GravityCompat.getAbsoluteGravity(i7, ViewCompat.getLayoutDirection(this)))
    {
    default:
      i8 = i + localLayoutParams.leftMargin;
    case 1:
    case 5:
    }
    while (true)
    {
      if (hasDividerBeforeChildAt(i4))
        i3 += this.mDividerHeight;
      int i9 = i3 + localLayoutParams.topMargin;
      setChildFrame(localView, i8, i9 + getLocationOffset(localView), i5, i6);
      i3 = i9 + (i6 + localLayoutParams.bottomMargin + getNextLocationOffset(localView));
      i4 += getChildrenSkipCount(localView, i4);
      break;
      i8 = i + (m - i5) / 2 + localLayoutParams.leftMargin - localLayoutParams.rightMargin;
      continue;
      i8 = k - i5 - localLayoutParams.rightMargin;
    }
  }

  void measureChildBeforeLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    measureChildWithMargins(paramView, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  void measureHorizontal(int paramInt1, int paramInt2)
  {
    this.mTotalLength = 0;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 1;
    float f1 = 0.0F;
    int i1 = getVirtualChildCount();
    int i2 = View.MeasureSpec.getMode(paramInt1);
    int i3 = View.MeasureSpec.getMode(paramInt2);
    int i4 = 0;
    int i5 = 0;
    if ((this.mMaxAscent == null) || (this.mMaxDescent == null))
    {
      this.mMaxAscent = new int[4];
      this.mMaxDescent = new int[4];
    }
    int[] arrayOfInt1 = this.mMaxAscent;
    int[] arrayOfInt2 = this.mMaxDescent;
    arrayOfInt1[3] = -1;
    arrayOfInt1[2] = -1;
    arrayOfInt1[1] = -1;
    arrayOfInt1[0] = -1;
    arrayOfInt2[3] = -1;
    arrayOfInt2[2] = -1;
    arrayOfInt2[1] = -1;
    arrayOfInt2[0] = -1;
    boolean bool1 = this.mBaselineAligned;
    boolean bool2 = this.mUseLargestChild;
    int i6;
    int i7;
    int i8;
    label156: View localView4;
    if (i2 == 1073741824)
    {
      i6 = 1;
      i7 = -2147483648;
      i8 = 0;
      if (i8 >= i1)
        break label822;
      localView4 = getVirtualChildAt(i8);
      if (localView4 != null)
        break label203;
      this.mTotalLength += measureNullChild(i8);
    }
    while (true)
    {
      i8++;
      break label156;
      i6 = 0;
      break;
      label203: if (localView4.getVisibility() != 8)
        break label229;
      i8 += getChildrenSkipCount(localView4, i8);
    }
    label229: if (hasDividerBeforeChildAt(i8))
      this.mTotalLength += this.mDividerWidth;
    LayoutParams localLayoutParams3 = (LayoutParams)localView4.getLayoutParams();
    f1 += localLayoutParams3.weight;
    label321: label342: int i33;
    int i34;
    int i35;
    int i38;
    if ((i2 == 1073741824) && (localLayoutParams3.width == 0) && (localLayoutParams3.weight > 0.0F))
      if (i6 != 0)
      {
        this.mTotalLength += localLayoutParams3.leftMargin + localLayoutParams3.rightMargin;
        if (!bool1)
          break label588;
        int i42 = View.MeasureSpec.makeMeasureSpec(0, 0);
        localView4.measure(i42, i42);
        i33 = 0;
        if (i3 != 1073741824)
        {
          int i40 = localLayoutParams3.height;
          i33 = 0;
          if (i40 == -1)
          {
            i4 = 1;
            i33 = 1;
          }
        }
        i34 = localLayoutParams3.topMargin + localLayoutParams3.bottomMargin;
        i35 = i34 + localView4.getMeasuredHeight();
        int i36 = ViewCompat.getMeasuredState(localView4);
        j = ViewUtils.combineMeasuredStates(j, i36);
        if (bool1)
        {
          int i37 = localView4.getBaseline();
          if (i37 != -1)
          {
            if (localLayoutParams3.gravity >= 0)
              break label775;
            i38 = this.mGravity;
            label445: int i39 = (0xFFFFFFFE & (i38 & 0x70) >> 4) >> 1;
            arrayOfInt1[i39] = Math.max(arrayOfInt1[i39], i37);
            arrayOfInt2[i39] = Math.max(arrayOfInt2[i39], i35 - i37);
          }
        }
        i = Math.max(i, i35);
        if ((n == 0) || (localLayoutParams3.height != -1))
          break label785;
        n = 1;
        label516: if (localLayoutParams3.weight <= 0.0F)
          break label798;
        if (i33 == 0)
          break label791;
      }
    while (true)
    {
      m = Math.max(m, i34);
      i8 += getChildrenSkipCount(localView4, i8);
      break;
      int i41 = this.mTotalLength;
      this.mTotalLength = Math.max(i41, i41 + localLayoutParams3.leftMargin + localLayoutParams3.rightMargin);
      break label321;
      label588: i5 = 1;
      break label342;
      int i29 = -2147483648;
      if ((localLayoutParams3.width == 0) && (localLayoutParams3.weight > 0.0F))
      {
        i29 = 0;
        localLayoutParams3.width = -2;
      }
      int i30;
      label640: int i31;
      if (f1 == 0.0F)
      {
        i30 = this.mTotalLength;
        measureChildBeforeLayout(localView4, i8, paramInt1, i30, paramInt2, 0);
        if (i29 != -2147483648)
          localLayoutParams3.width = i29;
        i31 = localView4.getMeasuredWidth();
        if (i6 == 0)
          break label733;
      }
      label733: int i32;
      for (this.mTotalLength += i31 + localLayoutParams3.leftMargin + localLayoutParams3.rightMargin + getNextLocationOffset(localView4); ; this.mTotalLength = Math.max(i32, i32 + i31 + localLayoutParams3.leftMargin + localLayoutParams3.rightMargin + getNextLocationOffset(localView4)))
      {
        if (!bool2)
          break label773;
        i7 = Math.max(i31, i7);
        break;
        i30 = 0;
        break label640;
        i32 = this.mTotalLength;
      }
      label773: break label342;
      label775: i38 = localLayoutParams3.gravity;
      break label445;
      label785: n = 0;
      break label516;
      label791: i34 = i35;
    }
    label798: if (i33 != 0);
    while (true)
    {
      k = Math.max(k, i34);
      break;
      i34 = i35;
    }
    label822: if ((this.mTotalLength > 0) && (hasDividerBeforeChildAt(i1)))
      this.mTotalLength += this.mDividerWidth;
    if ((arrayOfInt1[1] != -1) || (arrayOfInt1[0] != -1) || (arrayOfInt1[2] != -1) || (arrayOfInt1[3] != -1))
    {
      int i9 = Math.max(arrayOfInt1[3], Math.max(arrayOfInt1[0], Math.max(arrayOfInt1[1], arrayOfInt1[2]))) + Math.max(arrayOfInt2[3], Math.max(arrayOfInt2[0], Math.max(arrayOfInt2[1], arrayOfInt2[2])));
      i = Math.max(i, i9);
    }
    if ((bool2) && ((i2 == -2147483648) || (i2 == 0)))
    {
      this.mTotalLength = 0;
      int i27 = 0;
      if (i27 < i1)
      {
        View localView3 = getVirtualChildAt(i27);
        if (localView3 == null)
          this.mTotalLength += measureNullChild(i27);
        while (true)
        {
          i27++;
          break;
          if (localView3.getVisibility() == 8)
          {
            i27 += getChildrenSkipCount(localView3, i27);
          }
          else
          {
            LayoutParams localLayoutParams2 = (LayoutParams)localView3.getLayoutParams();
            if (i6 != 0)
            {
              this.mTotalLength += i7 + localLayoutParams2.leftMargin + localLayoutParams2.rightMargin + getNextLocationOffset(localView3);
            }
            else
            {
              int i28 = this.mTotalLength;
              this.mTotalLength = Math.max(i28, i28 + i7 + localLayoutParams2.leftMargin + localLayoutParams2.rightMargin + getNextLocationOffset(localView3));
            }
          }
        }
      }
    }
    this.mTotalLength += getPaddingLeft() + getPaddingRight();
    int i10 = ViewCompat.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), paramInt1, 0);
    int i11 = (i10 & 0xFFFFFF) - this.mTotalLength;
    if ((i5 != 0) || ((i11 != 0) && (f1 > 0.0F)))
    {
      float f2;
      int i12;
      label1257: View localView1;
      if (this.mWeightSum > 0.0F)
      {
        f2 = this.mWeightSum;
        arrayOfInt1[3] = -1;
        arrayOfInt1[2] = -1;
        arrayOfInt1[1] = -1;
        arrayOfInt1[0] = -1;
        arrayOfInt2[3] = -1;
        arrayOfInt2[2] = -1;
        arrayOfInt2[1] = -1;
        arrayOfInt2[0] = -1;
        i = -1;
        this.mTotalLength = 0;
        i12 = 0;
        if (i12 >= i1)
          break label1751;
        localView1 = getVirtualChildAt(i12);
        if ((localView1 != null) && (localView1.getVisibility() != 8))
          break label1300;
      }
      label1300: LayoutParams localLayoutParams1;
      int i22;
      int i23;
      label1487: int i16;
      label1506: int i17;
      int i18;
      label1541: label1567: int i19;
      do
      {
        i12++;
        break label1257;
        f2 = f1;
        break;
        localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
        float f3 = localLayoutParams1.weight;
        if (f3 > 0.0F)
        {
          i22 = (int)(f3 * i11 / f2);
          f2 -= f3;
          i11 -= i22;
          i23 = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom() + localLayoutParams1.topMargin + localLayoutParams1.bottomMargin, localLayoutParams1.height);
          if ((localLayoutParams1.width == 0) && (i2 == 1073741824))
            break label1649;
          int i24 = i22 + localView1.getMeasuredWidth();
          if (i24 < 0)
            i24 = 0;
          localView1.measure(View.MeasureSpec.makeMeasureSpec(i24, 1073741824), i23);
          int i25 = 0xFF000000 & ViewCompat.getMeasuredState(localView1);
          j = ViewUtils.combineMeasuredStates(j, i25);
        }
        if (i6 == 0)
          break label1677;
        this.mTotalLength += localView1.getMeasuredWidth() + localLayoutParams1.leftMargin + localLayoutParams1.rightMargin + getNextLocationOffset(localView1);
        if ((i3 == 1073741824) || (localLayoutParams1.height != -1))
          break label1722;
        i16 = 1;
        i17 = localLayoutParams1.topMargin + localLayoutParams1.bottomMargin;
        i18 = i17 + localView1.getMeasuredHeight();
        i = Math.max(i, i18);
        if (i16 == 0)
          break label1728;
        k = Math.max(k, i17);
        if ((n == 0) || (localLayoutParams1.height != -1))
          break label1735;
        n = 1;
        if (!bool1)
          break label1739;
        i19 = localView1.getBaseline();
      }
      while (i19 == -1);
      if (localLayoutParams1.gravity < 0);
      for (int i20 = this.mGravity; ; i20 = localLayoutParams1.gravity)
      {
        int i21 = (0xFFFFFFFE & (i20 & 0x70) >> 4) >> 1;
        arrayOfInt1[i21] = Math.max(arrayOfInt1[i21], i19);
        arrayOfInt2[i21] = Math.max(arrayOfInt2[i21], i18 - i19);
        break;
        label1649: if (i22 > 0);
        while (true)
        {
          localView1.measure(View.MeasureSpec.makeMeasureSpec(i22, 1073741824), i23);
          break;
          i22 = 0;
        }
        label1677: int i15 = this.mTotalLength;
        this.mTotalLength = Math.max(i15, i15 + localView1.getMeasuredWidth() + localLayoutParams1.leftMargin + localLayoutParams1.rightMargin + getNextLocationOffset(localView1));
        break label1487;
        label1722: i16 = 0;
        break label1506;
        label1728: i17 = i18;
        break label1541;
        label1735: n = 0;
        break label1567;
        label1739: break;
      }
      label1751: this.mTotalLength += getPaddingLeft() + getPaddingRight();
      if ((arrayOfInt1[1] != -1) || (arrayOfInt1[0] != -1) || (arrayOfInt1[2] != -1) || (arrayOfInt1[3] != -1))
      {
        int i13 = Math.max(arrayOfInt1[3], Math.max(arrayOfInt1[0], Math.max(arrayOfInt1[1], arrayOfInt1[2]))) + Math.max(arrayOfInt2[3], Math.max(arrayOfInt2[0], Math.max(arrayOfInt2[1], arrayOfInt2[2])));
        i = Math.max(i, i13);
      }
    }
    do
    {
      if ((n == 0) && (i3 != 1073741824))
        i = k;
      int i14 = Math.max(i + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight());
      setMeasuredDimension(i10 | 0xFF000000 & j, ViewCompat.resolveSizeAndState(i14, paramInt2, j << 16));
      if (i4 != 0)
        forceUniformHeight(i1, paramInt1);
      return;
      k = Math.max(k, m);
    }
    while ((!bool2) || (i2 == 1073741824));
    int i26 = 0;
    label1957: View localView2;
    if (i26 < i1)
    {
      localView2 = getVirtualChildAt(i26);
      if ((localView2 != null) && (localView2.getVisibility() != 8))
        break label1993;
    }
    while (true)
    {
      i26++;
      break label1957;
      break;
      label1993: if (((LayoutParams)localView2.getLayoutParams()).weight > 0.0F)
        localView2.measure(View.MeasureSpec.makeMeasureSpec(i7, 1073741824), View.MeasureSpec.makeMeasureSpec(localView2.getMeasuredHeight(), 1073741824));
    }
  }

  int measureNullChild(int paramInt)
  {
    return 0;
  }

  void measureVertical(int paramInt1, int paramInt2)
  {
    this.mTotalLength = 0;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 1;
    float f1 = 0.0F;
    int i1 = getVirtualChildCount();
    int i2 = View.MeasureSpec.getMode(paramInt1);
    int i3 = View.MeasureSpec.getMode(paramInt2);
    int i4 = 0;
    int i5 = 0;
    int i6 = this.mBaselineAlignedChildIndex;
    boolean bool = this.mUseLargestChild;
    int i7 = -2147483648;
    int i8 = 0;
    if (i8 < i1)
    {
      View localView4 = getVirtualChildAt(i8);
      if (localView4 == null)
        this.mTotalLength += measureNullChild(i8);
      while (true)
      {
        i8++;
        break;
        if (localView4.getVisibility() != 8)
          break label133;
        i8 += getChildrenSkipCount(localView4, i8);
      }
      label133: if (hasDividerBeforeChildAt(i8))
        this.mTotalLength += this.mDividerHeight;
      LayoutParams localLayoutParams3 = (LayoutParams)localView4.getLayoutParams();
      f1 += localLayoutParams3.weight;
      if ((i3 == 1073741824) && (localLayoutParams3.height == 0) && (localLayoutParams3.weight > 0.0F))
      {
        int i30 = this.mTotalLength;
        this.mTotalLength = Math.max(i30, i30 + localLayoutParams3.topMargin + localLayoutParams3.bottomMargin);
        i5 = 1;
        if ((i6 >= 0) && (i6 == i8 + 1))
          this.mBaselineChildTop = this.mTotalLength;
        if ((i8 < i6) && (localLayoutParams3.weight > 0.0F))
          throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
      }
      else
      {
        int i22 = -2147483648;
        if ((localLayoutParams3.height == 0) && (localLayoutParams3.weight > 0.0F))
        {
          i22 = 0;
          localLayoutParams3.height = -2;
        }
        if (f1 == 0.0F);
        for (int i23 = this.mTotalLength; ; i23 = 0)
        {
          measureChildBeforeLayout(localView4, i8, paramInt1, 0, paramInt2, i23);
          if (i22 != -2147483648)
            localLayoutParams3.height = i22;
          int i24 = localView4.getMeasuredHeight();
          int i25 = this.mTotalLength;
          this.mTotalLength = Math.max(i25, i25 + i24 + localLayoutParams3.topMargin + localLayoutParams3.bottomMargin + getNextLocationOffset(localView4));
          if (!bool)
            break;
          i7 = Math.max(i24, i7);
          break;
        }
      }
      int i26 = 0;
      if (i2 != 1073741824)
      {
        int i29 = localLayoutParams3.width;
        i26 = 0;
        if (i29 == -1)
        {
          i4 = 1;
          i26 = 1;
        }
      }
      int i27 = localLayoutParams3.leftMargin + localLayoutParams3.rightMargin;
      int i28 = i27 + localView4.getMeasuredWidth();
      i = Math.max(i, i28);
      j = ViewUtils.combineMeasuredStates(j, ViewCompat.getMeasuredState(localView4));
      if ((n != 0) && (localLayoutParams3.width == -1))
      {
        n = 1;
        label516: if (localLayoutParams3.weight <= 0.0F)
          break label569;
        if (i26 == 0)
          break label562;
      }
      while (true)
      {
        m = Math.max(m, i27);
        i8 += getChildrenSkipCount(localView4, i8);
        break;
        n = 0;
        break label516;
        label562: i27 = i28;
      }
      label569: if (i26 != 0);
      while (true)
      {
        k = Math.max(k, i27);
        break;
        i27 = i28;
      }
    }
    if ((this.mTotalLength > 0) && (hasDividerBeforeChildAt(i1)))
      this.mTotalLength += this.mDividerHeight;
    if ((bool) && ((i3 == -2147483648) || (i3 == 0)))
    {
      this.mTotalLength = 0;
      int i20 = 0;
      if (i20 < i1)
      {
        View localView3 = getVirtualChildAt(i20);
        if (localView3 == null)
          this.mTotalLength += measureNullChild(i20);
        while (true)
        {
          i20++;
          break;
          if (localView3.getVisibility() == 8)
          {
            i20 += getChildrenSkipCount(localView3, i20);
          }
          else
          {
            LayoutParams localLayoutParams2 = (LayoutParams)localView3.getLayoutParams();
            int i21 = this.mTotalLength;
            this.mTotalLength = Math.max(i21, i21 + i7 + localLayoutParams2.topMargin + localLayoutParams2.bottomMargin + getNextLocationOffset(localView3));
          }
        }
      }
    }
    this.mTotalLength += getPaddingTop() + getPaddingBottom();
    int i9 = ViewCompat.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), paramInt2, 0);
    int i10 = (i9 & 0xFFFFFF) - this.mTotalLength;
    if ((i5 != 0) || ((i10 != 0) && (f1 > 0.0F)))
    {
      if (this.mWeightSum > 0.0F);
      View localView1;
      for (float f2 = this.mWeightSum; ; f2 = f1)
      {
        this.mTotalLength = 0;
        for (int i11 = 0; ; i11++)
        {
          if (i11 >= i1)
            break label1211;
          localView1 = getVirtualChildAt(i11);
          if (localView1.getVisibility() != 8)
            break;
        }
      }
      LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      float f3 = localLayoutParams1.weight;
      int i16;
      int i17;
      int i12;
      int i13;
      int i14;
      if (f3 > 0.0F)
      {
        i16 = (int)(f3 * i10 / f2);
        f2 -= f3;
        i10 -= i16;
        i17 = getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localLayoutParams1.leftMargin + localLayoutParams1.rightMargin, localLayoutParams1.width);
        if ((localLayoutParams1.height != 0) || (i3 != 1073741824))
        {
          int i18 = i16 + localView1.getMeasuredHeight();
          if (i18 < 0)
            i18 = 0;
          localView1.measure(i17, View.MeasureSpec.makeMeasureSpec(i18, 1073741824));
          j = ViewUtils.combineMeasuredStates(j, 0xFFFFFF00 & ViewCompat.getMeasuredState(localView1));
        }
      }
      else
      {
        i12 = localLayoutParams1.leftMargin + localLayoutParams1.rightMargin;
        i13 = i12 + localView1.getMeasuredWidth();
        i = Math.max(i, i13);
        if ((i2 == 1073741824) || (localLayoutParams1.width != -1))
          break label1192;
        i14 = 1;
        label1088: if (i14 == 0)
          break label1198;
        label1093: k = Math.max(k, i12);
        if ((n == 0) || (localLayoutParams1.width != -1))
          break label1205;
      }
      label1192: label1198: label1205: for (n = 1; ; n = 0)
      {
        int i15 = this.mTotalLength;
        this.mTotalLength = Math.max(i15, i15 + localView1.getMeasuredHeight() + localLayoutParams1.topMargin + localLayoutParams1.bottomMargin + getNextLocationOffset(localView1));
        break;
        if (i16 > 0);
        while (true)
        {
          localView1.measure(i17, View.MeasureSpec.makeMeasureSpec(i16, 1073741824));
          break;
          i16 = 0;
        }
        i14 = 0;
        break label1088;
        i12 = i13;
        break label1093;
      }
      label1211: this.mTotalLength += getPaddingTop() + getPaddingBottom();
    }
    do
    {
      if ((n == 0) && (i2 != 1073741824))
        i = k;
      setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.max(i + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), paramInt1, j), i9);
      if (i4 != 0)
        forceUniformWidth(i1, paramInt2);
      return;
      k = Math.max(k, m);
    }
    while ((!bool) || (i3 == 1073741824));
    int i19 = 0;
    label1311: View localView2;
    if (i19 < i1)
    {
      localView2 = getVirtualChildAt(i19);
      if ((localView2 != null) && (localView2.getVisibility() != 8))
        break label1347;
    }
    while (true)
    {
      i19++;
      break label1311;
      break;
      label1347: if (((LayoutParams)localView2.getLayoutParams()).weight > 0.0F)
        localView2.measure(View.MeasureSpec.makeMeasureSpec(localView2.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i7, 1073741824));
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mDivider == null)
      return;
    if (this.mOrientation == 1)
    {
      drawDividersVertical(paramCanvas);
      return;
    }
    drawDividersHorizontal(paramCanvas);
  }

  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
    }
  }

  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mOrientation == 1)
    {
      layoutVertical(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    layoutHorizontal(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mOrientation == 1)
    {
      measureVertical(paramInt1, paramInt2);
      return;
    }
    measureHorizontal(paramInt1, paramInt2);
  }

  public void setBaselineAligned(boolean paramBoolean)
  {
    this.mBaselineAligned = paramBoolean;
  }

  public void setBaselineAlignedChildIndex(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= getChildCount()))
      throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    this.mBaselineAlignedChildIndex = paramInt;
  }

  public void setDividerDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mDivider)
      return;
    this.mDivider = paramDrawable;
    if (paramDrawable != null)
      this.mDividerWidth = paramDrawable.getIntrinsicWidth();
    for (this.mDividerHeight = paramDrawable.getIntrinsicHeight(); ; this.mDividerHeight = 0)
    {
      boolean bool = false;
      if (paramDrawable == null)
        bool = true;
      setWillNotDraw(bool);
      requestLayout();
      return;
      this.mDividerWidth = 0;
    }
  }

  public void setDividerPadding(int paramInt)
  {
    this.mDividerPadding = paramInt;
  }

  public void setGravity(int paramInt)
  {
    if (this.mGravity != paramInt)
    {
      if ((0x800007 & paramInt) == 0)
        paramInt |= 8388611;
      if ((paramInt & 0x70) == 0)
        paramInt |= 48;
      this.mGravity = paramInt;
      requestLayout();
    }
  }

  public void setHorizontalGravity(int paramInt)
  {
    int i = paramInt & 0x800007;
    if ((0x800007 & this.mGravity) != i)
    {
      this.mGravity = (i | 0xFF7FFFF8 & this.mGravity);
      requestLayout();
    }
  }

  public void setMeasureWithLargestChildEnabled(boolean paramBoolean)
  {
    this.mUseLargestChild = paramBoolean;
  }

  public void setOrientation(int paramInt)
  {
    if (this.mOrientation != paramInt)
    {
      this.mOrientation = paramInt;
      requestLayout();
    }
  }

  public void setShowDividers(int paramInt)
  {
    if (paramInt != this.mShowDividers)
      requestLayout();
    this.mShowDividers = paramInt;
  }

  public void setVerticalGravity(int paramInt)
  {
    int i = paramInt & 0x70;
    if ((0x70 & this.mGravity) != i)
    {
      this.mGravity = (i | 0xFFFFFF8F & this.mGravity);
      requestLayout();
    }
  }

  public void setWeightSum(float paramFloat)
  {
    this.mWeightSum = Math.max(0.0F, paramFloat);
  }

  public boolean shouldDelayChildPressedState()
  {
    return false;
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface DividerMode
  {
  }

  public static class LayoutParams extends ViewGroup.MarginLayoutParams
  {
    public int gravity = -1;
    public float weight;

    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.weight = 0.0F;
    }

    public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
    {
      super(paramInt2);
      this.weight = paramFloat;
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.LinearLayoutCompat_Layout);
      this.weight = localTypedArray.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0F);
      this.gravity = localTypedArray.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
      localTypedArray.recycle();
    }

    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.weight = paramLayoutParams.weight;
      this.gravity = paramLayoutParams.gravity;
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface OrientationMode
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.LinearLayoutCompat
 * JD-Core Version:    0.6.2
 */