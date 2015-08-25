package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager
{
  private static final boolean DEBUG = false;

  @Deprecated
  public static final int GAP_HANDLING_LAZY = 1;
  public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
  public static final int GAP_HANDLING_NONE = 0;
  public static final int HORIZONTAL = 0;
  private static final int INVALID_OFFSET = -2147483648;
  public static final String TAG = "StaggeredGridLayoutManager";
  public static final int VERTICAL = 1;
  private final AnchorInfo mAnchorInfo = new AnchorInfo(null);
  private final Runnable mCheckForGapsRunnable = new Runnable()
  {
    public void run()
    {
      StaggeredGridLayoutManager.this.checkForGaps();
    }
  };
  private int mFullSizeSpec;
  private int mGapStrategy = 2;
  private int mHeightSpec;
  private boolean mLaidOutInvalidFullSpan = false;
  private boolean mLastLayoutFromEnd;
  private boolean mLastLayoutRTL;
  private LayoutState mLayoutState;
  LazySpanLookup mLazySpanLookup = new LazySpanLookup();
  private int mOrientation;
  private SavedState mPendingSavedState;
  int mPendingScrollPosition = -1;
  int mPendingScrollPositionOffset = -2147483648;
  OrientationHelper mPrimaryOrientation;
  private BitSet mRemainingSpans;
  private boolean mReverseLayout = false;
  OrientationHelper mSecondaryOrientation;
  boolean mShouldReverseLayout = false;
  private int mSizePerSpan;
  private boolean mSmoothScrollbarEnabled = true;
  private int mSpanCount = -1;
  private Span[] mSpans;
  private final Rect mTmpRect = new Rect();
  private int mWidthSpec;

  public StaggeredGridLayoutManager(int paramInt1, int paramInt2)
  {
    this.mOrientation = paramInt2;
    setSpanCount(paramInt1);
  }

  public StaggeredGridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    RecyclerView.LayoutManager.Properties localProperties = getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setOrientation(localProperties.orientation);
    setSpanCount(localProperties.spanCount);
    setReverseLayout(localProperties.reverseLayout);
  }

  private void appendViewToAllSpans(View paramView)
  {
    for (int i = -1 + this.mSpanCount; i >= 0; i--)
      this.mSpans[i].appendToSpan(paramView);
  }

  private void applyPendingSavedState(AnchorInfo paramAnchorInfo)
  {
    if (this.mPendingSavedState.mSpanOffsetsSize > 0)
      if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount)
      {
        int i = 0;
        if (i < this.mSpanCount)
        {
          this.mSpans[i].clear();
          int j = this.mPendingSavedState.mSpanOffsets[i];
          if (j != -2147483648)
          {
            if (!this.mPendingSavedState.mAnchorLayoutFromEnd)
              break label95;
            j += this.mPrimaryOrientation.getEndAfterPadding();
          }
          while (true)
          {
            this.mSpans[i].setLine(j);
            i++;
            break;
            label95: j += this.mPrimaryOrientation.getStartAfterPadding();
          }
        }
      }
      else
      {
        this.mPendingSavedState.invalidateSpanInfo();
        this.mPendingSavedState.mAnchorPosition = this.mPendingSavedState.mVisibleAnchorPosition;
      }
    this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
    setReverseLayout(this.mPendingSavedState.mReverseLayout);
    resolveShouldLayoutReverse();
    if (this.mPendingSavedState.mAnchorPosition != -1)
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
    for (paramAnchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd; ; paramAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout)
    {
      if (this.mPendingSavedState.mSpanLookupSize > 1)
      {
        this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
        this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
      }
      return;
    }
  }

  private void attachViewToSpans(View paramView, LayoutParams paramLayoutParams, LayoutState paramLayoutState)
  {
    if (paramLayoutState.mLayoutDirection == 1)
    {
      if (paramLayoutParams.mFullSpan)
      {
        appendViewToAllSpans(paramView);
        return;
      }
      paramLayoutParams.mSpan.appendToSpan(paramView);
      return;
    }
    if (paramLayoutParams.mFullSpan)
    {
      prependViewToAllSpans(paramView);
      return;
    }
    paramLayoutParams.mSpan.prependToSpan(paramView);
  }

  private int calculateScrollDirectionForPosition(int paramInt)
  {
    int i = -1;
    if (getChildCount() == 0)
    {
      if (this.mShouldReverseLayout)
        return 1;
      return i;
    }
    int j;
    if (paramInt < getFirstChildPosition())
    {
      j = 1;
      if (j == this.mShouldReverseLayout)
        break label45;
    }
    while (true)
    {
      return i;
      j = 0;
      break;
      label45: i = 1;
    }
  }

  private boolean checkForGaps()
  {
    if ((getChildCount() == 0) || (this.mGapStrategy == 0) || (!isAttachedToWindow()))
      return false;
    int i;
    if (this.mShouldReverseLayout)
      i = getLastChildPosition();
    for (int j = getFirstChildPosition(); (i == 0) && (hasGapsToFix() != null); j = getLastChildPosition())
    {
      this.mLazySpanLookup.clear();
      requestSimpleAnimationsInNextLayout();
      requestLayout();
      return true;
      i = getFirstChildPosition();
    }
    if (!this.mLaidOutInvalidFullSpan)
      return false;
    if (this.mShouldReverseLayout);
    StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem1;
    for (int k = -1; ; k = 1)
    {
      localFullSpanItem1 = this.mLazySpanLookup.getFirstFullSpanItemInRange(i, j + 1, k, true);
      if (localFullSpanItem1 != null)
        break;
      this.mLaidOutInvalidFullSpan = false;
      this.mLazySpanLookup.forceInvalidateAfter(j + 1);
      return false;
    }
    StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem2 = this.mLazySpanLookup.getFirstFullSpanItemInRange(i, localFullSpanItem1.mPosition, k * -1, true);
    if (localFullSpanItem2 == null)
      this.mLazySpanLookup.forceInvalidateAfter(localFullSpanItem1.mPosition);
    while (true)
    {
      requestSimpleAnimationsInNextLayout();
      requestLayout();
      return true;
      this.mLazySpanLookup.forceInvalidateAfter(1 + localFullSpanItem2.mPosition);
    }
  }

  private boolean checkSpanForGap(Span paramSpan)
  {
    if (this.mShouldReverseLayout)
    {
      if (paramSpan.getEndLine() >= this.mPrimaryOrientation.getEndAfterPadding());
    }
    else
      while (paramSpan.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding())
        return true;
    return false;
  }

  private int computeScrollExtent(RecyclerView.State paramState)
  {
    if (getChildCount() == 0)
      return 0;
    ensureOrientationHelper();
    OrientationHelper localOrientationHelper = this.mPrimaryOrientation;
    if (!this.mSmoothScrollbarEnabled);
    for (boolean bool1 = true; ; bool1 = false)
    {
      View localView = findFirstVisibleItemClosestToStart(bool1, true);
      boolean bool2 = this.mSmoothScrollbarEnabled;
      boolean bool3 = false;
      if (!bool2)
        bool3 = true;
      return ScrollbarHelper.computeScrollExtent(paramState, localOrientationHelper, localView, findFirstVisibleItemClosestToEnd(bool3, true), this, this.mSmoothScrollbarEnabled);
    }
  }

  private int computeScrollOffset(RecyclerView.State paramState)
  {
    if (getChildCount() == 0)
      return 0;
    ensureOrientationHelper();
    OrientationHelper localOrientationHelper = this.mPrimaryOrientation;
    if (!this.mSmoothScrollbarEnabled);
    for (boolean bool1 = true; ; bool1 = false)
    {
      View localView = findFirstVisibleItemClosestToStart(bool1, true);
      boolean bool2 = this.mSmoothScrollbarEnabled;
      boolean bool3 = false;
      if (!bool2)
        bool3 = true;
      return ScrollbarHelper.computeScrollOffset(paramState, localOrientationHelper, localView, findFirstVisibleItemClosestToEnd(bool3, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }
  }

  private int computeScrollRange(RecyclerView.State paramState)
  {
    if (getChildCount() == 0)
      return 0;
    ensureOrientationHelper();
    OrientationHelper localOrientationHelper = this.mPrimaryOrientation;
    if (!this.mSmoothScrollbarEnabled);
    for (boolean bool1 = true; ; bool1 = false)
    {
      View localView = findFirstVisibleItemClosestToStart(bool1, true);
      boolean bool2 = this.mSmoothScrollbarEnabled;
      boolean bool3 = false;
      if (!bool2)
        bool3 = true;
      return ScrollbarHelper.computeScrollRange(paramState, localOrientationHelper, localView, findFirstVisibleItemClosestToEnd(bool3, true), this, this.mSmoothScrollbarEnabled);
    }
  }

  private StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int paramInt)
  {
    StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem = new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem();
    localFullSpanItem.mGapPerSpan = new int[this.mSpanCount];
    for (int i = 0; i < this.mSpanCount; i++)
      localFullSpanItem.mGapPerSpan[i] = (paramInt - this.mSpans[i].getEndLine(paramInt));
    return localFullSpanItem;
  }

  private StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int paramInt)
  {
    StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem = new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem();
    localFullSpanItem.mGapPerSpan = new int[this.mSpanCount];
    for (int i = 0; i < this.mSpanCount; i++)
      localFullSpanItem.mGapPerSpan[i] = (this.mSpans[i].getStartLine(paramInt) - paramInt);
    return localFullSpanItem;
  }

  private void ensureOrientationHelper()
  {
    if (this.mPrimaryOrientation == null)
    {
      this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
      this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
      this.mLayoutState = new LayoutState();
    }
  }

  private int fill(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState, RecyclerView.State paramState)
  {
    this.mRemainingSpans.set(0, this.mSpanCount, true);
    int i;
    int j;
    label58: int k;
    label61: View localView;
    LayoutParams localLayoutParams;
    int i1;
    int i2;
    int i3;
    label123: Span localSpan;
    label144: label155: label176: int i5;
    label208: int i4;
    int i6;
    label317: int i7;
    if (paramLayoutState.mLayoutDirection == 1)
    {
      i = paramLayoutState.mEndLine + paramLayoutState.mAvailable;
      updateAllRemainingSpans(paramLayoutState.mLayoutDirection, i);
      if (!this.mShouldReverseLayout)
        break label403;
      j = this.mPrimaryOrientation.getEndAfterPadding();
      k = 0;
      if ((!paramLayoutState.hasMore(paramState)) || (this.mRemainingSpans.isEmpty()))
        break label693;
      localView = paramLayoutState.next(paramRecycler);
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      i1 = localLayoutParams.getViewLayoutPosition();
      i2 = this.mLazySpanLookup.getSpan(i1);
      if (i2 != -1)
        break label415;
      i3 = 1;
      if (i3 == 0)
        break label431;
      if (!localLayoutParams.mFullSpan)
        break label421;
      localSpan = this.mSpans[0];
      this.mLazySpanLookup.setSpan(i1, localSpan);
      localLayoutParams.mSpan = localSpan;
      if (paramLayoutState.mLayoutDirection != 1)
        break label443;
      addView(localView);
      measureChildWithDecorationsAndMargin(localView, localLayoutParams);
      if (paramLayoutState.mLayoutDirection != 1)
        break label465;
      if (!localLayoutParams.mFullSpan)
        break label453;
      i5 = getMaxEnd(j);
      i4 = i5 + this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      if ((i3 != 0) && (localLayoutParams.mFullSpan))
      {
        StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem3 = createFullSpanItemFromEnd(i5);
        localFullSpanItem3.mGapDir = -1;
        localFullSpanItem3.mPosition = i1;
        this.mLazySpanLookup.addFullSpanItem(localFullSpanItem3);
      }
      if ((localLayoutParams.mFullSpan) && (paramLayoutState.mItemDirection == -1))
      {
        if (i3 == 0)
          break label553;
        this.mLaidOutInvalidFullSpan = true;
      }
      attachViewToSpans(localView, localLayoutParams, paramLayoutState);
      if (!localLayoutParams.mFullSpan)
        break label631;
      i6 = this.mSecondaryOrientation.getStartAfterPadding();
      i7 = i6 + this.mSecondaryOrientation.getDecoratedMeasurement(localView);
      if (this.mOrientation != 1)
        break label654;
      layoutDecoratedWithMargins(localView, i6, i5, i7, i4);
      label353: if (!localLayoutParams.mFullSpan)
        break label671;
      updateAllRemainingSpans(this.mLayoutState.mLayoutDirection, i);
    }
    while (true)
    {
      recycle(paramRecycler, this.mLayoutState);
      k = 1;
      break label61;
      i = paramLayoutState.mStartLine - paramLayoutState.mAvailable;
      break;
      label403: j = this.mPrimaryOrientation.getStartAfterPadding();
      break label58;
      label415: i3 = 0;
      break label123;
      label421: localSpan = getNextSpan(paramLayoutState);
      break label144;
      label431: localSpan = this.mSpans[i2];
      break label155;
      label443: addView(localView, 0);
      break label176;
      label453: i5 = localSpan.getEndLine(j);
      break label208;
      label465: if (localLayoutParams.mFullSpan);
      for (i4 = getMinStart(j); ; i4 = localSpan.getStartLine(j))
      {
        i5 = i4 - this.mPrimaryOrientation.getDecoratedMeasurement(localView);
        if ((i3 == 0) || (!localLayoutParams.mFullSpan))
          break;
        StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem1 = createFullSpanItemFromStart(i4);
        localFullSpanItem1.mGapDir = 1;
        localFullSpanItem1.mPosition = i1;
        this.mLazySpanLookup.addFullSpanItem(localFullSpanItem1);
        break;
      }
      label553: if (paramLayoutState.mLayoutDirection == 1)
      {
        if (!areAllEndsEqual());
        for (i9 = 1; ; i9 = 0)
        {
          label571: if (i9 == 0)
            break label623;
          StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem2 = this.mLazySpanLookup.getFullSpanItem(i1);
          if (localFullSpanItem2 != null)
            localFullSpanItem2.mHasUnwantedGapAfter = true;
          this.mLaidOutInvalidFullSpan = true;
          break;
        }
      }
      if (!areAllStartsEqual());
      for (int i9 = 1; ; i9 = 0)
      {
        break label571;
        label623: break;
      }
      label631: i6 = localSpan.mIndex * this.mSizePerSpan + this.mSecondaryOrientation.getStartAfterPadding();
      break label317;
      label654: layoutDecoratedWithMargins(localView, i5, i6, i4, i7);
      break label353;
      label671: int i8 = this.mLayoutState.mLayoutDirection;
      updateRemainingSpans(localSpan, i8, i);
    }
    label693: if (k == 0)
      recycle(paramRecycler, this.mLayoutState);
    int n;
    if (this.mLayoutState.mLayoutDirection == -1)
      n = getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
    for (int m = this.mPrimaryOrientation.getStartAfterPadding() - n; m > 0; m = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding())
      return Math.min(paramLayoutState.mAvailable, m);
    return 0;
  }

  private int findFirstReferenceChildPosition(int paramInt)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      int k = getPosition(getChildAt(j));
      if ((k >= 0) && (k < paramInt))
        return k;
    }
    return 0;
  }

  private int findLastReferenceChildPosition(int paramInt)
  {
    for (int i = -1 + getChildCount(); i >= 0; i--)
    {
      int j = getPosition(getChildAt(i));
      if ((j >= 0) && (j < paramInt))
        return j;
    }
    return 0;
  }

  private void fixEndGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding());
    int j = this.mPrimaryOrientation.getEndAfterPadding() - i;
    if (j > 0)
    {
      int k = j - -scrollBy(-j, paramRecycler, paramState);
      if ((paramBoolean) && (k > 0))
        this.mPrimaryOrientation.offsetChildren(k);
    }
  }

  private void fixStartGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = getMinStart(this.mPrimaryOrientation.getStartAfterPadding()) - this.mPrimaryOrientation.getStartAfterPadding();
    if (i > 0)
    {
      int j = i - scrollBy(i, paramRecycler, paramState);
      if ((paramBoolean) && (j > 0))
        this.mPrimaryOrientation.offsetChildren(-j);
    }
  }

  private int getFirstChildPosition()
  {
    if (getChildCount() == 0)
      return 0;
    return getPosition(getChildAt(0));
  }

  private int getLastChildPosition()
  {
    int i = getChildCount();
    if (i == 0)
      return 0;
    return getPosition(getChildAt(i - 1));
  }

  private int getMaxEnd(int paramInt)
  {
    int i = this.mSpans[0].getEndLine(paramInt);
    for (int j = 1; j < this.mSpanCount; j++)
    {
      int k = this.mSpans[j].getEndLine(paramInt);
      if (k > i)
        i = k;
    }
    return i;
  }

  private int getMaxStart(int paramInt)
  {
    int i = this.mSpans[0].getStartLine(paramInt);
    for (int j = 1; j < this.mSpanCount; j++)
    {
      int k = this.mSpans[j].getStartLine(paramInt);
      if (k > i)
        i = k;
    }
    return i;
  }

  private int getMinEnd(int paramInt)
  {
    int i = this.mSpans[0].getEndLine(paramInt);
    for (int j = 1; j < this.mSpanCount; j++)
    {
      int k = this.mSpans[j].getEndLine(paramInt);
      if (k < i)
        i = k;
    }
    return i;
  }

  private int getMinStart(int paramInt)
  {
    int i = this.mSpans[0].getStartLine(paramInt);
    for (int j = 1; j < this.mSpanCount; j++)
    {
      int k = this.mSpans[j].getStartLine(paramInt);
      if (k < i)
        i = k;
    }
    return i;
  }

  private Span getNextSpan(LayoutState paramLayoutState)
  {
    int k;
    int i;
    int j;
    if (preferLastSpan(paramLayoutState.mLayoutDirection))
    {
      k = -1 + this.mSpanCount;
      i = -1;
      j = -1;
    }
    while (paramLayoutState.mLayoutDirection == 1)
    {
      localObject2 = null;
      int i3 = 2147483647;
      int i4 = this.mPrimaryOrientation.getStartAfterPadding();
      int i5 = k;
      while (i5 != i)
      {
        Span localSpan2 = this.mSpans[i5];
        int i6 = localSpan2.getEndLine(i4);
        if (i6 < i3)
        {
          localObject2 = localSpan2;
          i3 = i6;
        }
        i5 += j;
      }
      i = this.mSpanCount;
      j = 1;
      k = 0;
    }
    Object localObject1 = null;
    int m = -2147483648;
    int n = this.mPrimaryOrientation.getEndAfterPadding();
    int i1 = k;
    while (i1 != i)
    {
      Span localSpan1 = this.mSpans[i1];
      int i2 = localSpan1.getStartLine(n);
      if (i2 > m)
      {
        localObject1 = localSpan1;
        m = i2;
      }
      i1 += j;
    }
    Object localObject2 = localObject1;
    return localObject2;
  }

  private int getSpecForDimension(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
      return paramInt2;
    return View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824);
  }

  private void handleUpdate(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    int k;
    int j;
    if (this.mShouldReverseLayout)
    {
      i = getLastChildPosition();
      if (paramInt3 != 3)
        break label100;
      if (paramInt1 >= paramInt2)
        break label89;
      k = paramInt2 + 1;
      j = paramInt1;
      label31: this.mLazySpanLookup.invalidateAfter(j);
      switch (paramInt3)
      {
      case 2:
      default:
        label72: if (k > i)
          break;
      case 0:
      case 1:
      case 3:
      }
    }
    while (true)
    {
      return;
      i = getFirstChildPosition();
      break;
      label89: k = paramInt1 + 1;
      j = paramInt2;
      break label31;
      label100: j = paramInt1;
      k = paramInt1 + paramInt2;
      break label31;
      this.mLazySpanLookup.offsetForAddition(paramInt1, paramInt2);
      break label72;
      this.mLazySpanLookup.offsetForRemoval(paramInt1, paramInt2);
      break label72;
      this.mLazySpanLookup.offsetForRemoval(paramInt1, 1);
      this.mLazySpanLookup.offsetForAddition(paramInt2, 1);
      break label72;
      if (this.mShouldReverseLayout);
      for (int m = getFirstChildPosition(); j <= m; m = getLastChildPosition())
      {
        requestLayout();
        return;
      }
    }
  }

  private void layoutDecoratedWithMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    layoutDecorated(paramView, paramInt1 + localLayoutParams.leftMargin, paramInt2 + localLayoutParams.topMargin, paramInt3 - localLayoutParams.rightMargin, paramInt4 - localLayoutParams.bottomMargin);
  }

  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2)
  {
    calculateItemDecorationsForChild(paramView, this.mTmpRect);
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    paramView.measure(updateSpecWithExtra(paramInt1, localLayoutParams.leftMargin + this.mTmpRect.left, localLayoutParams.rightMargin + this.mTmpRect.right), updateSpecWithExtra(paramInt2, localLayoutParams.topMargin + this.mTmpRect.top, localLayoutParams.bottomMargin + this.mTmpRect.bottom));
  }

  private void measureChildWithDecorationsAndMargin(View paramView, LayoutParams paramLayoutParams)
  {
    if (paramLayoutParams.mFullSpan)
    {
      if (this.mOrientation == 1)
      {
        measureChildWithDecorationsAndMargin(paramView, this.mFullSizeSpec, getSpecForDimension(paramLayoutParams.height, this.mHeightSpec));
        return;
      }
      measureChildWithDecorationsAndMargin(paramView, getSpecForDimension(paramLayoutParams.width, this.mWidthSpec), this.mFullSizeSpec);
      return;
    }
    if (this.mOrientation == 1)
    {
      measureChildWithDecorationsAndMargin(paramView, this.mWidthSpec, getSpecForDimension(paramLayoutParams.height, this.mHeightSpec));
      return;
    }
    measureChildWithDecorationsAndMargin(paramView, getSpecForDimension(paramLayoutParams.width, this.mWidthSpec), this.mHeightSpec);
  }

  private boolean preferLastSpan(int paramInt)
  {
    int k;
    if (this.mOrientation == 0)
      if (paramInt == -1)
      {
        k = 1;
        if (k == this.mShouldReverseLayout)
          break label32;
      }
    label32: label66: label69: 
    while (true)
    {
      return true;
      k = 0;
      break;
      return false;
      int i;
      if (paramInt == -1)
      {
        i = 1;
        if (i != this.mShouldReverseLayout)
          break label66;
      }
      for (int j = 1; ; j = 0)
      {
        if (j == isLayoutRTL())
          break label69;
        return false;
        i = 0;
        break;
      }
    }
  }

  private void prependViewToAllSpans(View paramView)
  {
    for (int i = -1 + this.mSpanCount; i >= 0; i--)
      this.mSpans[i].prependToSpan(paramView);
  }

  private void recycle(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState)
  {
    if (paramLayoutState.mAvailable == 0)
    {
      if (paramLayoutState.mLayoutDirection == -1)
      {
        recycleFromEnd(paramRecycler, paramLayoutState.mEndLine);
        return;
      }
      recycleFromStart(paramRecycler, paramLayoutState.mStartLine);
      return;
    }
    if (paramLayoutState.mLayoutDirection == -1)
    {
      int k = paramLayoutState.mStartLine - getMaxStart(paramLayoutState.mStartLine);
      if (k < 0);
      for (int m = paramLayoutState.mEndLine; ; m = paramLayoutState.mEndLine - Math.min(k, paramLayoutState.mAvailable))
      {
        recycleFromEnd(paramRecycler, m);
        return;
      }
    }
    int i = getMinEnd(paramLayoutState.mEndLine) - paramLayoutState.mEndLine;
    if (i < 0);
    for (int j = paramLayoutState.mStartLine; ; j = paramLayoutState.mStartLine + Math.min(i, paramLayoutState.mAvailable))
    {
      recycleFromStart(paramRecycler, j);
      return;
    }
  }

  private void recycleFromEnd(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    for (int i = -1 + getChildCount(); ; i--)
    {
      View localView;
      LayoutParams localLayoutParams;
      if (i >= 0)
      {
        localView = getChildAt(i);
        if (this.mPrimaryOrientation.getDecoratedStart(localView) >= paramInt)
        {
          localLayoutParams = (LayoutParams)localView.getLayoutParams();
          if (!localLayoutParams.mFullSpan)
            break label113;
          j = 0;
          if (j >= this.mSpanCount)
            break label85;
          if (this.mSpans[j].mViews.size() != 1)
            break label79;
        }
      }
      label79: label85: label113: 
      while (localLayoutParams.mSpan.mViews.size() == 1)
      {
        while (true)
        {
          int j;
          return;
          j++;
        }
        for (int k = 0; k < this.mSpanCount; k++)
          this.mSpans[k].popEnd();
      }
      localLayoutParams.mSpan.popEnd();
      removeAndRecycleView(localView, paramRecycler);
    }
  }

  private void recycleFromStart(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    while (true)
    {
      View localView;
      LayoutParams localLayoutParams;
      if (getChildCount() > 0)
      {
        localView = getChildAt(0);
        if (this.mPrimaryOrientation.getDecoratedEnd(localView) <= paramInt)
        {
          localLayoutParams = (LayoutParams)localView.getLayoutParams();
          if (!localLayoutParams.mFullSpan)
            break label106;
          i = 0;
          if (i >= this.mSpanCount)
            break label78;
          if (this.mSpans[i].mViews.size() != 1)
            break label72;
        }
      }
      label72: label78: label106: 
      while (localLayoutParams.mSpan.mViews.size() == 1)
      {
        while (true)
        {
          int i;
          return;
          i++;
        }
        for (int j = 0; j < this.mSpanCount; j++)
          this.mSpans[j].popStart();
      }
      localLayoutParams.mSpan.popStart();
      removeAndRecycleView(localView, paramRecycler);
    }
  }

  private void resolveShouldLayoutReverse()
  {
    int i = 1;
    if ((this.mOrientation == i) || (!isLayoutRTL()))
    {
      this.mShouldReverseLayout = this.mReverseLayout;
      return;
    }
    if (!this.mReverseLayout);
    while (true)
    {
      this.mShouldReverseLayout = i;
      return;
      i = 0;
    }
  }

  private void setLayoutStateDirection(int paramInt)
  {
    int i = 1;
    this.mLayoutState.mLayoutDirection = paramInt;
    LayoutState localLayoutState = this.mLayoutState;
    int j = this.mShouldReverseLayout;
    if (paramInt == -1)
    {
      int k = i;
      if (j != k)
        break label48;
    }
    while (true)
    {
      localLayoutState.mItemDirection = i;
      return;
      int m = 0;
      break;
      label48: i = -1;
    }
  }

  private void updateAllRemainingSpans(int paramInt1, int paramInt2)
  {
    int i = 0;
    if (i < this.mSpanCount)
    {
      if (this.mSpans[i].mViews.isEmpty());
      while (true)
      {
        i++;
        break;
        updateRemainingSpans(this.mSpans[i], paramInt1, paramInt2);
      }
    }
  }

  private boolean updateAnchorFromChildren(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if (this.mLastLayoutFromEnd);
    for (int i = findLastReferenceChildPosition(paramState.getItemCount()); ; i = findFirstReferenceChildPosition(paramState.getItemCount()))
    {
      paramAnchorInfo.mPosition = i;
      paramAnchorInfo.mOffset = -2147483648;
      return true;
    }
  }

  private void updateLayoutState(int paramInt, RecyclerView.State paramState)
  {
    this.mLayoutState.mAvailable = 0;
    this.mLayoutState.mCurrentPosition = paramInt;
    boolean bool1 = isSmoothScrolling();
    int i = 0;
    int j = 0;
    if (bool1)
    {
      int k = paramState.getTargetScrollPosition();
      i = 0;
      j = 0;
      if (k != -1)
      {
        boolean bool2 = this.mShouldReverseLayout;
        boolean bool3 = false;
        if (k < paramInt)
          bool3 = true;
        if (bool2 != bool3)
          break label125;
      }
    }
    for (i = this.mPrimaryOrientation.getTotalSpace(); getClipToPadding(); i = 0)
    {
      this.mLayoutState.mStartLine = (this.mPrimaryOrientation.getStartAfterPadding() - j);
      this.mLayoutState.mEndLine = (i + this.mPrimaryOrientation.getEndAfterPadding());
      return;
      label125: j = this.mPrimaryOrientation.getTotalSpace();
    }
    this.mLayoutState.mEndLine = (i + this.mPrimaryOrientation.getEnd());
    this.mLayoutState.mStartLine = (-j);
  }

  private void updateRemainingSpans(Span paramSpan, int paramInt1, int paramInt2)
  {
    int i = paramSpan.getDeletedSize();
    if (paramInt1 == -1)
      if (i + paramSpan.getStartLine() <= paramInt2)
        this.mRemainingSpans.set(paramSpan.mIndex, false);
    while (paramSpan.getEndLine() - i < paramInt2)
      return;
    this.mRemainingSpans.set(paramSpan.mIndex, false);
  }

  private int updateSpecWithExtra(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 == 0) && (paramInt3 == 0));
    int i;
    do
    {
      return paramInt1;
      i = View.MeasureSpec.getMode(paramInt1);
    }
    while ((i != -2147483648) && (i != 1073741824));
    return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt1) - paramInt2 - paramInt3, i);
  }

  boolean areAllEndsEqual()
  {
    int i = this.mSpans[0].getEndLine(-2147483648);
    for (int j = 1; j < this.mSpanCount; j++)
      if (this.mSpans[j].getEndLine(-2147483648) != i)
        return false;
    return true;
  }

  boolean areAllStartsEqual()
  {
    int i = this.mSpans[0].getStartLine(-2147483648);
    for (int j = 1; j < this.mSpanCount; j++)
      if (this.mSpans[j].getStartLine(-2147483648) != i)
        return false;
    return true;
  }

  public void assertNotInLayoutOrScroll(String paramString)
  {
    if (this.mPendingSavedState == null)
      super.assertNotInLayoutOrScroll(paramString);
  }

  public boolean canScrollHorizontally()
  {
    return this.mOrientation == 0;
  }

  public boolean canScrollVertically()
  {
    return this.mOrientation == 1;
  }

  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  public int computeHorizontalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }

  public int computeHorizontalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }

  public int computeHorizontalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }

  public int computeVerticalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }

  public int computeVerticalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }

  public int computeVerticalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }

  public int[] findFirstCompletelyVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      paramArrayOfInt = new int[this.mSpanCount];
    while (paramArrayOfInt.length >= this.mSpanCount)
      for (int i = 0; i < this.mSpanCount; i++)
        paramArrayOfInt[i] = this.mSpans[i].findFirstCompletelyVisibleItemPosition();
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
    return paramArrayOfInt;
  }

  View findFirstVisibleItemClosestToEnd(boolean paramBoolean1, boolean paramBoolean2)
  {
    ensureOrientationHelper();
    int i = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    Object localObject = null;
    int k = -1 + getChildCount();
    if (k >= 0)
    {
      View localView = getChildAt(k);
      int m = this.mPrimaryOrientation.getDecoratedStart(localView);
      int n = this.mPrimaryOrientation.getDecoratedEnd(localView);
      if ((n <= i) || (m >= j));
      while (true)
      {
        k--;
        break;
        if ((n <= j) || (!paramBoolean1))
          return localView;
        if ((paramBoolean2) && (localObject == null))
          localObject = localView;
      }
    }
    return localObject;
  }

  View findFirstVisibleItemClosestToStart(boolean paramBoolean1, boolean paramBoolean2)
  {
    ensureOrientationHelper();
    int i = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    int k = getChildCount();
    Object localObject = null;
    int m = 0;
    if (m < k)
    {
      View localView = getChildAt(m);
      int n = this.mPrimaryOrientation.getDecoratedStart(localView);
      if ((this.mPrimaryOrientation.getDecoratedEnd(localView) <= i) || (n >= j));
      while (true)
      {
        m++;
        break;
        if ((n >= i) || (!paramBoolean1))
          return localView;
        if ((paramBoolean2) && (localObject == null))
          localObject = localView;
      }
    }
    return localObject;
  }

  int findFirstVisibleItemPositionInt()
  {
    if (this.mShouldReverseLayout);
    for (View localView = findFirstVisibleItemClosestToEnd(true, true); localView == null; localView = findFirstVisibleItemClosestToStart(true, true))
      return -1;
    return getPosition(localView);
  }

  public int[] findFirstVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      paramArrayOfInt = new int[this.mSpanCount];
    while (paramArrayOfInt.length >= this.mSpanCount)
      for (int i = 0; i < this.mSpanCount; i++)
        paramArrayOfInt[i] = this.mSpans[i].findFirstVisibleItemPosition();
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
    return paramArrayOfInt;
  }

  public int[] findLastCompletelyVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      paramArrayOfInt = new int[this.mSpanCount];
    while (paramArrayOfInt.length >= this.mSpanCount)
      for (int i = 0; i < this.mSpanCount; i++)
        paramArrayOfInt[i] = this.mSpans[i].findLastCompletelyVisibleItemPosition();
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
    return paramArrayOfInt;
  }

  public int[] findLastVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      paramArrayOfInt = new int[this.mSpanCount];
    while (paramArrayOfInt.length >= this.mSpanCount)
      for (int i = 0; i < this.mSpanCount; i++)
        paramArrayOfInt[i] = this.mSpans[i].findLastVisibleItemPosition();
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
    return paramArrayOfInt;
  }

  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }

  public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new LayoutParams(paramContext, paramAttributeSet);
  }

  public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams))
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    return new LayoutParams(paramLayoutParams);
  }

  public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1)
      return this.mSpanCount;
    return super.getColumnCountForAccessibility(paramRecycler, paramState);
  }

  public int getGapStrategy()
  {
    return this.mGapStrategy;
  }

  public int getOrientation()
  {
    return this.mOrientation;
  }

  public boolean getReverseLayout()
  {
    return this.mReverseLayout;
  }

  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0)
      return this.mSpanCount;
    return super.getRowCountForAccessibility(paramRecycler, paramState);
  }

  public int getSpanCount()
  {
    return this.mSpanCount;
  }

  View hasGapsToFix()
  {
    int i = -1 + getChildCount();
    BitSet localBitSet = new BitSet(this.mSpanCount);
    localBitSet.set(0, this.mSpanCount, true);
    int j;
    int m;
    int k;
    if ((this.mOrientation == 1) && (isLayoutRTL()))
    {
      j = 1;
      if (!this.mShouldReverseLayout)
        break label135;
      m = i;
      k = 0 - 1;
      label61: if (m >= k)
        break label146;
    }
    int i1;
    View localView1;
    LayoutParams localLayoutParams1;
    label135: label146: for (int n = 1; ; n = -1)
    {
      i1 = m;
      if (i1 == k)
        break label364;
      localView1 = getChildAt(i1);
      localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      if (!localBitSet.get(localLayoutParams1.mSpan.mIndex))
        break label164;
      if (!checkSpanForGap(localLayoutParams1.mSpan))
        break label152;
      label127: return localView1;
      j = -1;
      break;
      k = i + 1;
      m = 0;
      break label61;
    }
    label152: localBitSet.clear(localLayoutParams1.mSpan.mIndex);
    label164: if (localLayoutParams1.mFullSpan);
    label172: label350: label352: label358: label362: 
    while (true)
    {
      i1 += n;
      break;
      if (i1 + n != k)
      {
        View localView2 = getChildAt(i1 + n);
        int i4;
        label252: int i5;
        if (this.mShouldReverseLayout)
        {
          int i7 = this.mPrimaryOrientation.getDecoratedEnd(localView1);
          int i8 = this.mPrimaryOrientation.getDecoratedEnd(localView2);
          if (i7 < i8)
            break label127;
          i4 = 0;
          if (i7 == i8)
            i4 = 1;
          if (i4 == 0)
            break label350;
          LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
          if (localLayoutParams1.mSpan.mIndex - localLayoutParams2.mSpan.mIndex >= 0)
            break label352;
          i5 = 1;
          if (j >= 0)
            break label358;
        }
        for (int i6 = 1; ; i6 = 0)
        {
          if (i5 == i6)
            break label362;
          return localView1;
          int i2 = this.mPrimaryOrientation.getDecoratedStart(localView1);
          int i3 = this.mPrimaryOrientation.getDecoratedStart(localView2);
          if (i2 > i3)
            break;
          i4 = 0;
          if (i2 != i3)
            break label252;
          i4 = 1;
          break label252;
          break label172;
          i5 = 0;
          break label290;
        }
      }
    }
    label290: label364: return null;
  }

  public void invalidateSpanAssignments()
  {
    this.mLazySpanLookup.clear();
    requestLayout();
  }

  boolean isLayoutRTL()
  {
    return getLayoutDirection() == 1;
  }

  public void offsetChildrenHorizontal(int paramInt)
  {
    super.offsetChildrenHorizontal(paramInt);
    for (int i = 0; i < this.mSpanCount; i++)
      this.mSpans[i].onOffset(paramInt);
  }

  public void offsetChildrenVertical(int paramInt)
  {
    super.offsetChildrenVertical(paramInt);
    for (int i = 0; i < this.mSpanCount; i++)
      this.mSpans[i].onOffset(paramInt);
  }

  public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
  {
    removeCallbacks(this.mCheckForGapsRunnable);
    for (int i = 0; i < this.mSpanCount; i++)
      this.mSpans[i].clear();
  }

  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    AccessibilityRecordCompat localAccessibilityRecordCompat;
    View localView1;
    View localView2;
    if (getChildCount() > 0)
    {
      localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAccessibilityEvent);
      localView1 = findFirstVisibleItemClosestToStart(false, true);
      localView2 = findFirstVisibleItemClosestToEnd(false, true);
      if ((localView1 != null) && (localView2 != null));
    }
    else
    {
      return;
    }
    int i = getPosition(localView1);
    int j = getPosition(localView2);
    if (i < j)
    {
      localAccessibilityRecordCompat.setFromIndex(i);
      localAccessibilityRecordCompat.setToIndex(j);
      return;
    }
    localAccessibilityRecordCompat.setFromIndex(j);
    localAccessibilityRecordCompat.setToIndex(i);
  }

  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (!(localLayoutParams instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    LayoutParams localLayoutParams1 = (LayoutParams)localLayoutParams;
    if (this.mOrientation == 0)
    {
      int k = localLayoutParams1.getSpanIndex();
      if (localLayoutParams1.mFullSpan);
      for (int m = this.mSpanCount; ; m = 1)
      {
        paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(k, m, -1, -1, localLayoutParams1.mFullSpan, false));
        return;
      }
    }
    int i = localLayoutParams1.getSpanIndex();
    if (localLayoutParams1.mFullSpan);
    for (int j = this.mSpanCount; ; j = 1)
    {
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, i, j, localLayoutParams1.mFullSpan, false));
      return;
    }
  }

  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 0);
  }

  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mLazySpanLookup.clear();
    requestLayout();
  }

  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    handleUpdate(paramInt1, paramInt2, 3);
  }

  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 1);
  }

  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 2);
  }

  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    int i = 1;
    ensureOrientationHelper();
    AnchorInfo localAnchorInfo = this.mAnchorInfo;
    localAnchorInfo.reset();
    if (this.mPendingSavedState != null)
      applyPendingSavedState(localAnchorInfo);
    while (true)
    {
      updateAnchorInfoForLayout(paramState, localAnchorInfo);
      if ((this.mPendingSavedState == null) && ((localAnchorInfo.mLayoutFromEnd != this.mLastLayoutFromEnd) || (isLayoutRTL() != this.mLastLayoutRTL)))
      {
        this.mLazySpanLookup.clear();
        localAnchorInfo.mInvalidateOffsets = i;
      }
      if ((getChildCount() <= 0) || ((this.mPendingSavedState != null) && (this.mPendingSavedState.mSpanOffsetsSize >= i)))
        break label219;
      if (!localAnchorInfo.mInvalidateOffsets)
        break;
      for (int m = 0; m < this.mSpanCount; m++)
      {
        this.mSpans[m].clear();
        if (localAnchorInfo.mOffset != -2147483648)
          this.mSpans[m].setLine(localAnchorInfo.mOffset);
      }
      resolveShouldLayoutReverse();
      localAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
    }
    for (int k = 0; k < this.mSpanCount; k++)
      this.mSpans[k].cacheReferenceLineAndClear(this.mShouldReverseLayout, localAnchorInfo.mOffset);
    label219: detachAndScrapAttachedViews(paramRecycler);
    this.mLaidOutInvalidFullSpan = false;
    updateMeasureSpecs();
    updateLayoutState(localAnchorInfo.mPosition, paramState);
    if (localAnchorInfo.mLayoutFromEnd)
    {
      setLayoutStateDirection(-1);
      fill(paramRecycler, this.mLayoutState, paramState);
      setLayoutStateDirection(i);
      this.mLayoutState.mCurrentPosition = (localAnchorInfo.mPosition + this.mLayoutState.mItemDirection);
      fill(paramRecycler, this.mLayoutState, paramState);
      if (getChildCount() > 0)
      {
        if (!this.mShouldReverseLayout)
          break label476;
        fixEndGap(paramRecycler, paramState, i);
        fixStartGap(paramRecycler, paramState, false);
      }
      label331: if (!paramState.isPreLayout())
        if ((this.mGapStrategy == 0) || (getChildCount() <= 0) || ((!this.mLaidOutInvalidFullSpan) && (hasGapsToFix() == null)))
          break label493;
    }
    while (true)
    {
      if (i != 0)
      {
        removeCallbacks(this.mCheckForGapsRunnable);
        postOnAnimation(this.mCheckForGapsRunnable);
      }
      this.mPendingScrollPosition = -1;
      this.mPendingScrollPositionOffset = -2147483648;
      this.mLastLayoutFromEnd = localAnchorInfo.mLayoutFromEnd;
      this.mLastLayoutRTL = isLayoutRTL();
      this.mPendingSavedState = null;
      return;
      setLayoutStateDirection(i);
      fill(paramRecycler, this.mLayoutState, paramState);
      setLayoutStateDirection(-1);
      this.mLayoutState.mCurrentPosition = (localAnchorInfo.mPosition + this.mLayoutState.mItemDirection);
      fill(paramRecycler, this.mLayoutState, paramState);
      break;
      label476: fixStartGap(paramRecycler, paramState, i);
      fixEndGap(paramRecycler, paramState, false);
      break label331;
      label493: int j = 0;
    }
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      this.mPendingSavedState = ((SavedState)paramParcelable);
      requestLayout();
    }
  }

  public Parcelable onSaveInstanceState()
  {
    if (this.mPendingSavedState != null)
    {
      localSavedState = new SavedState(this.mPendingSavedState);
      return localSavedState;
    }
    SavedState localSavedState = new SavedState();
    localSavedState.mReverseLayout = this.mReverseLayout;
    localSavedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
    localSavedState.mLastLayoutRTL = this.mLastLayoutRTL;
    label101: int i;
    label124: int j;
    label157: int k;
    if ((this.mLazySpanLookup != null) && (this.mLazySpanLookup.mData != null))
    {
      localSavedState.mSpanLookup = this.mLazySpanLookup.mData;
      localSavedState.mSpanLookupSize = localSavedState.mSpanLookup.length;
      localSavedState.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
      if (getChildCount() <= 0)
        break label269;
      ensureOrientationHelper();
      if (!this.mLastLayoutFromEnd)
        break label226;
      i = getLastChildPosition();
      localSavedState.mAnchorPosition = i;
      localSavedState.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
      localSavedState.mSpanOffsetsSize = this.mSpanCount;
      localSavedState.mSpanOffsets = new int[this.mSpanCount];
      j = 0;
      if (j < this.mSpanCount)
      {
        if (!this.mLastLayoutFromEnd)
          break label234;
        k = this.mSpans[j].getEndLine(-2147483648);
        if (k != -2147483648)
          k -= this.mPrimaryOrientation.getEndAfterPadding();
      }
    }
    while (true)
    {
      localSavedState.mSpanOffsets[j] = k;
      j++;
      break label157;
      break;
      localSavedState.mSpanLookupSize = 0;
      break label101;
      label226: i = getFirstChildPosition();
      break label124;
      label234: k = this.mSpans[j].getStartLine(-2147483648);
      if (k != -2147483648)
        k -= this.mPrimaryOrientation.getStartAfterPadding();
    }
    label269: localSavedState.mAnchorPosition = -1;
    localSavedState.mVisibleAnchorPosition = -1;
    localSavedState.mSpanOffsetsSize = 0;
    return localSavedState;
  }

  public void onScrollStateChanged(int paramInt)
  {
    if (paramInt == 0)
      checkForGaps();
  }

  int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    ensureOrientationHelper();
    int i;
    int j;
    int m;
    int n;
    if (paramInt > 0)
    {
      i = 1;
      j = getLastChildPosition();
      updateLayoutState(j, paramState);
      setLayoutStateDirection(i);
      this.mLayoutState.mCurrentPosition = (j + this.mLayoutState.mItemDirection);
      int k = Math.abs(paramInt);
      this.mLayoutState.mAvailable = k;
      m = fill(paramRecycler, this.mLayoutState, paramState);
      if (k >= m)
        break label117;
      n = paramInt;
    }
    while (true)
    {
      this.mPrimaryOrientation.offsetChildren(-n);
      this.mLastLayoutFromEnd = this.mShouldReverseLayout;
      return n;
      i = -1;
      j = getFirstChildPosition();
      break;
      label117: if (paramInt < 0)
        n = -m;
      else
        n = m;
    }
  }

  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return scrollBy(paramInt, paramRecycler, paramState);
  }

  public void scrollToPosition(int paramInt)
  {
    if ((this.mPendingSavedState != null) && (this.mPendingSavedState.mAnchorPosition != paramInt))
      this.mPendingSavedState.invalidateAnchorPositionInfo();
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = -2147483648;
    requestLayout();
  }

  public void scrollToPositionWithOffset(int paramInt1, int paramInt2)
  {
    if (this.mPendingSavedState != null)
      this.mPendingSavedState.invalidateAnchorPositionInfo();
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    requestLayout();
  }

  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return scrollBy(paramInt, paramRecycler, paramState);
  }

  public void setGapStrategy(int paramInt)
  {
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mGapStrategy)
      return;
    if ((paramInt != 0) && (paramInt != 2))
      throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
    this.mGapStrategy = paramInt;
    requestLayout();
  }

  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1))
      throw new IllegalArgumentException("invalid orientation.");
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mOrientation)
      return;
    this.mOrientation = paramInt;
    if ((this.mPrimaryOrientation != null) && (this.mSecondaryOrientation != null))
    {
      OrientationHelper localOrientationHelper = this.mPrimaryOrientation;
      this.mPrimaryOrientation = this.mSecondaryOrientation;
      this.mSecondaryOrientation = localOrientationHelper;
    }
    requestLayout();
  }

  public void setReverseLayout(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    if ((this.mPendingSavedState != null) && (this.mPendingSavedState.mReverseLayout != paramBoolean))
      this.mPendingSavedState.mReverseLayout = paramBoolean;
    this.mReverseLayout = paramBoolean;
    requestLayout();
  }

  public void setSpanCount(int paramInt)
  {
    assertNotInLayoutOrScroll(null);
    if (paramInt != this.mSpanCount)
    {
      invalidateSpanAssignments();
      this.mSpanCount = paramInt;
      this.mRemainingSpans = new BitSet(this.mSpanCount);
      this.mSpans = new Span[this.mSpanCount];
      for (int i = 0; i < this.mSpanCount; i++)
        this.mSpans[i] = new Span(i, null);
      requestLayout();
    }
  }

  public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
  {
    LinearSmoothScroller local2 = new LinearSmoothScroller(paramRecyclerView.getContext())
    {
      public PointF computeScrollVectorForPosition(int paramAnonymousInt)
      {
        int i = StaggeredGridLayoutManager.this.calculateScrollDirectionForPosition(paramAnonymousInt);
        if (i == 0)
          return null;
        if (StaggeredGridLayoutManager.this.mOrientation == 0)
          return new PointF(i, 0.0F);
        return new PointF(0.0F, i);
      }
    };
    local2.setTargetPosition(paramInt);
    startSmoothScroll(local2);
  }

  public boolean supportsPredictiveItemAnimations()
  {
    return this.mPendingSavedState == null;
  }

  boolean updateAnchorFromPendingData(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if ((paramState.isPreLayout()) || (this.mPendingScrollPosition == -1))
      return false;
    if ((this.mPendingScrollPosition < 0) || (this.mPendingScrollPosition >= paramState.getItemCount()))
    {
      this.mPendingScrollPosition = -1;
      this.mPendingScrollPositionOffset = -2147483648;
      return false;
    }
    if ((this.mPendingSavedState == null) || (this.mPendingSavedState.mAnchorPosition == -1) || (this.mPendingSavedState.mSpanOffsetsSize < 1))
    {
      View localView = findViewByPosition(this.mPendingScrollPosition);
      if (localView != null)
      {
        if (this.mShouldReverseLayout);
        for (int j = getLastChildPosition(); ; j = getFirstChildPosition())
        {
          paramAnchorInfo.mPosition = j;
          if (this.mPendingScrollPositionOffset == -2147483648)
            break label188;
          if (!paramAnchorInfo.mLayoutFromEnd)
            break;
          paramAnchorInfo.mOffset = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd(localView));
          return true;
        }
        paramAnchorInfo.mOffset = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart(localView));
        return true;
        label188: if (this.mPrimaryOrientation.getDecoratedMeasurement(localView) > this.mPrimaryOrientation.getTotalSpace())
        {
          if (paramAnchorInfo.mLayoutFromEnd);
          for (int n = this.mPrimaryOrientation.getEndAfterPadding(); ; n = this.mPrimaryOrientation.getStartAfterPadding())
          {
            paramAnchorInfo.mOffset = n;
            return true;
          }
        }
        int k = this.mPrimaryOrientation.getDecoratedStart(localView) - this.mPrimaryOrientation.getStartAfterPadding();
        if (k < 0)
        {
          paramAnchorInfo.mOffset = (-k);
          return true;
        }
        int m = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(localView);
        if (m < 0)
        {
          paramAnchorInfo.mOffset = m;
          return true;
        }
        paramAnchorInfo.mOffset = -2147483648;
        return true;
      }
      paramAnchorInfo.mPosition = this.mPendingScrollPosition;
      if (this.mPendingScrollPositionOffset == -2147483648)
      {
        int i = calculateScrollDirectionForPosition(paramAnchorInfo.mPosition);
        boolean bool = false;
        if (i == 1)
          bool = true;
        paramAnchorInfo.mLayoutFromEnd = bool;
        paramAnchorInfo.assignCoordinateFromPadding();
      }
      while (true)
      {
        paramAnchorInfo.mInvalidateOffsets = true;
        return true;
        paramAnchorInfo.assignCoordinateFromPadding(this.mPendingScrollPositionOffset);
      }
    }
    paramAnchorInfo.mOffset = -2147483648;
    paramAnchorInfo.mPosition = this.mPendingScrollPosition;
    return true;
  }

  void updateAnchorInfoForLayout(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if (updateAnchorFromPendingData(paramState, paramAnchorInfo));
    while (updateAnchorFromChildren(paramState, paramAnchorInfo))
      return;
    paramAnchorInfo.assignCoordinateFromPadding();
    paramAnchorInfo.mPosition = 0;
  }

  void updateMeasureSpecs()
  {
    this.mSizePerSpan = (this.mSecondaryOrientation.getTotalSpace() / this.mSpanCount);
    this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec(this.mSecondaryOrientation.getTotalSpace(), 1073741824);
    if (this.mOrientation == 1)
    {
      this.mWidthSpec = View.MeasureSpec.makeMeasureSpec(this.mSizePerSpan, 1073741824);
      this.mHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
      return;
    }
    this.mHeightSpec = View.MeasureSpec.makeMeasureSpec(this.mSizePerSpan, 1073741824);
    this.mWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
  }

  private class AnchorInfo
  {
    boolean mInvalidateOffsets;
    boolean mLayoutFromEnd;
    int mOffset;
    int mPosition;

    private AnchorInfo()
    {
    }

    void assignCoordinateFromPadding()
    {
      if (this.mLayoutFromEnd);
      for (int i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding(); ; i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding())
      {
        this.mOffset = i;
        return;
      }
    }

    void assignCoordinateFromPadding(int paramInt)
    {
      if (this.mLayoutFromEnd)
      {
        this.mOffset = (StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - paramInt);
        return;
      }
      this.mOffset = (paramInt + StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding());
    }

    void reset()
    {
      this.mPosition = -1;
      this.mOffset = -2147483648;
      this.mLayoutFromEnd = false;
      this.mInvalidateOffsets = false;
    }
  }

  public static class LayoutParams extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    boolean mFullSpan;
    StaggeredGridLayoutManager.Span mSpan;

    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    public LayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      super();
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }

    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }

    public final int getSpanIndex()
    {
      if (this.mSpan == null)
        return -1;
      return this.mSpan.mIndex;
    }

    public boolean isFullSpan()
    {
      return this.mFullSpan;
    }

    public void setFullSpan(boolean paramBoolean)
    {
      this.mFullSpan = paramBoolean;
    }
  }

  static class LazySpanLookup
  {
    private static final int MIN_SIZE = 10;
    int[] mData;
    List<FullSpanItem> mFullSpanItems;

    private int invalidateFullSpansAfter(int paramInt)
    {
      if (this.mFullSpanItems == null)
        return -1;
      FullSpanItem localFullSpanItem1 = getFullSpanItem(paramInt);
      if (localFullSpanItem1 != null)
        this.mFullSpanItems.remove(localFullSpanItem1);
      int i = -1;
      int j = this.mFullSpanItems.size();
      for (int k = 0; ; k++)
        if (k < j)
        {
          if (((FullSpanItem)this.mFullSpanItems.get(k)).mPosition >= paramInt)
            i = k;
        }
        else
        {
          if (i == -1)
            break;
          FullSpanItem localFullSpanItem2 = (FullSpanItem)this.mFullSpanItems.get(i);
          this.mFullSpanItems.remove(i);
          return localFullSpanItem2.mPosition;
        }
    }

    private void offsetFullSpansForAddition(int paramInt1, int paramInt2)
    {
      if (this.mFullSpanItems == null)
        return;
      int i = -1 + this.mFullSpanItems.size();
      label20: FullSpanItem localFullSpanItem;
      if (i >= 0)
      {
        localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(i);
        if (localFullSpanItem.mPosition >= paramInt1)
          break label54;
      }
      while (true)
      {
        i--;
        break label20;
        break;
        label54: localFullSpanItem.mPosition = (paramInt2 + localFullSpanItem.mPosition);
      }
    }

    private void offsetFullSpansForRemoval(int paramInt1, int paramInt2)
    {
      if (this.mFullSpanItems == null)
        return;
      int i = paramInt1 + paramInt2;
      int j = -1 + this.mFullSpanItems.size();
      label25: FullSpanItem localFullSpanItem;
      if (j >= 0)
      {
        localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(j);
        if (localFullSpanItem.mPosition >= paramInt1)
          break label61;
      }
      while (true)
      {
        j--;
        break label25;
        break;
        label61: if (localFullSpanItem.mPosition < i)
          this.mFullSpanItems.remove(j);
        else
          localFullSpanItem.mPosition -= paramInt2;
      }
    }

    public void addFullSpanItem(FullSpanItem paramFullSpanItem)
    {
      if (this.mFullSpanItems == null)
        this.mFullSpanItems = new ArrayList();
      int i = this.mFullSpanItems.size();
      for (int j = 0; j < i; j++)
      {
        FullSpanItem localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(j);
        if (localFullSpanItem.mPosition == paramFullSpanItem.mPosition)
          this.mFullSpanItems.remove(j);
        if (localFullSpanItem.mPosition >= paramFullSpanItem.mPosition)
        {
          this.mFullSpanItems.add(j, paramFullSpanItem);
          return;
        }
      }
      this.mFullSpanItems.add(paramFullSpanItem);
    }

    void clear()
    {
      if (this.mData != null)
        Arrays.fill(this.mData, -1);
      this.mFullSpanItems = null;
    }

    void ensureSize(int paramInt)
    {
      if (this.mData == null)
      {
        this.mData = new int[1 + Math.max(paramInt, 10)];
        Arrays.fill(this.mData, -1);
      }
      while (paramInt < this.mData.length)
        return;
      int[] arrayOfInt = this.mData;
      this.mData = new int[sizeForPosition(paramInt)];
      System.arraycopy(arrayOfInt, 0, this.mData, 0, arrayOfInt.length);
      Arrays.fill(this.mData, arrayOfInt.length, this.mData.length, -1);
    }

    int forceInvalidateAfter(int paramInt)
    {
      if (this.mFullSpanItems != null)
        for (int i = -1 + this.mFullSpanItems.size(); i >= 0; i--)
          if (((FullSpanItem)this.mFullSpanItems.get(i)).mPosition >= paramInt)
            this.mFullSpanItems.remove(i);
      return invalidateAfter(paramInt);
    }

    public FullSpanItem getFirstFullSpanItemInRange(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      FullSpanItem localFullSpanItem;
      if (this.mFullSpanItems == null)
      {
        localFullSpanItem = null;
        return localFullSpanItem;
      }
      int i = this.mFullSpanItems.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label102;
        localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(j);
        if (localFullSpanItem.mPosition >= paramInt2)
          return null;
        if ((localFullSpanItem.mPosition >= paramInt1) && ((paramInt3 == 0) || (localFullSpanItem.mGapDir == paramInt3) || ((paramBoolean) && (localFullSpanItem.mHasUnwantedGapAfter))))
          break;
      }
      label102: return null;
    }

    public FullSpanItem getFullSpanItem(int paramInt)
    {
      FullSpanItem localFullSpanItem;
      if (this.mFullSpanItems == null)
      {
        localFullSpanItem = null;
        return localFullSpanItem;
      }
      for (int i = -1 + this.mFullSpanItems.size(); ; i--)
      {
        if (i < 0)
          break label55;
        localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(i);
        if (localFullSpanItem.mPosition == paramInt)
          break;
      }
      label55: return null;
    }

    int getSpan(int paramInt)
    {
      if ((this.mData == null) || (paramInt >= this.mData.length))
        return -1;
      return this.mData[paramInt];
    }

    int invalidateAfter(int paramInt)
    {
      if (this.mData == null);
      while (paramInt >= this.mData.length)
        return -1;
      int i = invalidateFullSpansAfter(paramInt);
      if (i == -1)
      {
        Arrays.fill(this.mData, paramInt, this.mData.length, -1);
        return this.mData.length;
      }
      Arrays.fill(this.mData, paramInt, i + 1, -1);
      return i + 1;
    }

    void offsetForAddition(int paramInt1, int paramInt2)
    {
      if ((this.mData == null) || (paramInt1 >= this.mData.length))
        return;
      ensureSize(paramInt1 + paramInt2);
      System.arraycopy(this.mData, paramInt1, this.mData, paramInt1 + paramInt2, this.mData.length - paramInt1 - paramInt2);
      Arrays.fill(this.mData, paramInt1, paramInt1 + paramInt2, -1);
      offsetFullSpansForAddition(paramInt1, paramInt2);
    }

    void offsetForRemoval(int paramInt1, int paramInt2)
    {
      if ((this.mData == null) || (paramInt1 >= this.mData.length))
        return;
      ensureSize(paramInt1 + paramInt2);
      System.arraycopy(this.mData, paramInt1 + paramInt2, this.mData, paramInt1, this.mData.length - paramInt1 - paramInt2);
      Arrays.fill(this.mData, this.mData.length - paramInt2, this.mData.length, -1);
      offsetFullSpansForRemoval(paramInt1, paramInt2);
    }

    void setSpan(int paramInt, StaggeredGridLayoutManager.Span paramSpan)
    {
      ensureSize(paramInt);
      this.mData[paramInt] = paramSpan.mIndex;
    }

    int sizeForPosition(int paramInt)
    {
      int i = this.mData.length;
      while (i <= paramInt)
        i *= 2;
      return i;
    }

    static class FullSpanItem
      implements Parcelable
    {
      public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator()
      {
        public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFromParcel(Parcel paramAnonymousParcel)
        {
          return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem(paramAnonymousParcel);
        }

        public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[] newArray(int paramAnonymousInt)
        {
          return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[paramAnonymousInt];
        }
      };
      int mGapDir;
      int[] mGapPerSpan;
      boolean mHasUnwantedGapAfter;
      int mPosition;

      public FullSpanItem()
      {
      }

      public FullSpanItem(Parcel paramParcel)
      {
        this.mPosition = paramParcel.readInt();
        this.mGapDir = paramParcel.readInt();
        if (paramParcel.readInt() == i);
        while (true)
        {
          this.mHasUnwantedGapAfter = i;
          int j = paramParcel.readInt();
          if (j > 0)
          {
            this.mGapPerSpan = new int[j];
            paramParcel.readIntArray(this.mGapPerSpan);
          }
          return;
          i = 0;
        }
      }

      public int describeContents()
      {
        return 0;
      }

      int getGapForSpan(int paramInt)
      {
        if (this.mGapPerSpan == null)
          return 0;
        return this.mGapPerSpan[paramInt];
      }

      public void invalidateSpanGaps()
      {
        this.mGapPerSpan = null;
      }

      public String toString()
      {
        return "FullSpanItem{mPosition=" + this.mPosition + ", mGapDir=" + this.mGapDir + ", mHasUnwantedGapAfter=" + this.mHasUnwantedGapAfter + ", mGapPerSpan=" + Arrays.toString(this.mGapPerSpan) + '}';
      }

      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        paramParcel.writeInt(this.mPosition);
        paramParcel.writeInt(this.mGapDir);
        if (this.mHasUnwantedGapAfter);
        for (int i = 1; ; i = 0)
        {
          paramParcel.writeInt(i);
          if ((this.mGapPerSpan == null) || (this.mGapPerSpan.length <= 0))
            break;
          paramParcel.writeInt(this.mGapPerSpan.length);
          paramParcel.writeIntArray(this.mGapPerSpan);
          return;
        }
        paramParcel.writeInt(0);
      }
    }
  }

  static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public StaggeredGridLayoutManager.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new StaggeredGridLayoutManager.SavedState(paramAnonymousParcel);
      }

      public StaggeredGridLayoutManager.SavedState[] newArray(int paramAnonymousInt)
      {
        return new StaggeredGridLayoutManager.SavedState[paramAnonymousInt];
      }
    };
    boolean mAnchorLayoutFromEnd;
    int mAnchorPosition;
    List<StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem> mFullSpanItems;
    boolean mLastLayoutRTL;
    boolean mReverseLayout;
    int[] mSpanLookup;
    int mSpanLookupSize;
    int[] mSpanOffsets;
    int mSpanOffsetsSize;
    int mVisibleAnchorPosition;

    public SavedState()
    {
    }

    SavedState(Parcel paramParcel)
    {
      this.mAnchorPosition = paramParcel.readInt();
      this.mVisibleAnchorPosition = paramParcel.readInt();
      this.mSpanOffsetsSize = paramParcel.readInt();
      if (this.mSpanOffsetsSize > 0)
      {
        this.mSpanOffsets = new int[this.mSpanOffsetsSize];
        paramParcel.readIntArray(this.mSpanOffsets);
      }
      this.mSpanLookupSize = paramParcel.readInt();
      if (this.mSpanLookupSize > 0)
      {
        this.mSpanLookup = new int[this.mSpanLookupSize];
        paramParcel.readIntArray(this.mSpanLookup);
      }
      int j;
      int k;
      if (paramParcel.readInt() == i)
      {
        j = i;
        this.mReverseLayout = j;
        if (paramParcel.readInt() != i)
          break label152;
        k = i;
        label114: this.mAnchorLayoutFromEnd = k;
        if (paramParcel.readInt() != i)
          break label158;
      }
      while (true)
      {
        this.mLastLayoutRTL = i;
        this.mFullSpanItems = paramParcel.readArrayList(StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem.class.getClassLoader());
        return;
        j = 0;
        break;
        label152: k = 0;
        break label114;
        label158: i = 0;
      }
    }

    public SavedState(SavedState paramSavedState)
    {
      this.mSpanOffsetsSize = paramSavedState.mSpanOffsetsSize;
      this.mAnchorPosition = paramSavedState.mAnchorPosition;
      this.mVisibleAnchorPosition = paramSavedState.mVisibleAnchorPosition;
      this.mSpanOffsets = paramSavedState.mSpanOffsets;
      this.mSpanLookupSize = paramSavedState.mSpanLookupSize;
      this.mSpanLookup = paramSavedState.mSpanLookup;
      this.mReverseLayout = paramSavedState.mReverseLayout;
      this.mAnchorLayoutFromEnd = paramSavedState.mAnchorLayoutFromEnd;
      this.mLastLayoutRTL = paramSavedState.mLastLayoutRTL;
      this.mFullSpanItems = paramSavedState.mFullSpanItems;
    }

    public int describeContents()
    {
      return 0;
    }

    void invalidateAnchorPositionInfo()
    {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mAnchorPosition = -1;
      this.mVisibleAnchorPosition = -1;
    }

    void invalidateSpanInfo()
    {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mSpanLookupSize = 0;
      this.mSpanLookup = null;
      this.mFullSpanItems = null;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      int i = 1;
      paramParcel.writeInt(this.mAnchorPosition);
      paramParcel.writeInt(this.mVisibleAnchorPosition);
      paramParcel.writeInt(this.mSpanOffsetsSize);
      if (this.mSpanOffsetsSize > 0)
        paramParcel.writeIntArray(this.mSpanOffsets);
      paramParcel.writeInt(this.mSpanLookupSize);
      if (this.mSpanLookupSize > 0)
        paramParcel.writeIntArray(this.mSpanLookup);
      int j;
      int k;
      if (this.mReverseLayout)
      {
        j = i;
        paramParcel.writeInt(j);
        if (!this.mAnchorLayoutFromEnd)
          break label123;
        k = i;
        label90: paramParcel.writeInt(k);
        if (!this.mLastLayoutRTL)
          break label129;
      }
      while (true)
      {
        paramParcel.writeInt(i);
        paramParcel.writeList(this.mFullSpanItems);
        return;
        j = 0;
        break;
        label123: k = 0;
        break label90;
        label129: i = 0;
      }
    }
  }

  class Span
  {
    static final int INVALID_LINE = -2147483648;
    int mCachedEnd = -2147483648;
    int mCachedStart = -2147483648;
    int mDeletedSize = 0;
    final int mIndex;
    private ArrayList<View> mViews = new ArrayList();

    private Span(int arg2)
    {
      int i;
      this.mIndex = i;
    }

    void appendToSpan(View paramView)
    {
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(paramView);
      localLayoutParams.mSpan = this;
      this.mViews.add(paramView);
      this.mCachedEnd = -2147483648;
      if (this.mViews.size() == 1)
        this.mCachedStart = -2147483648;
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged()))
        this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(paramView);
    }

    void cacheReferenceLineAndClear(boolean paramBoolean, int paramInt)
    {
      int i;
      if (paramBoolean)
      {
        i = getEndLine(-2147483648);
        clear();
        if (i != -2147483648)
          break label32;
      }
      label32: 
      while (((paramBoolean) && (i < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding())) || ((!paramBoolean) && (i > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding())))
      {
        return;
        i = getStartLine(-2147483648);
        break;
      }
      if (paramInt != -2147483648)
        i += paramInt;
      this.mCachedEnd = i;
      this.mCachedStart = i;
    }

    void calculateCachedEnd()
    {
      View localView = (View)this.mViews.get(-1 + this.mViews.size());
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(localView);
      this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView);
      if (localLayoutParams.mFullSpan)
      {
        StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(localLayoutParams.getViewLayoutPosition());
        if ((localFullSpanItem != null) && (localFullSpanItem.mGapDir == 1))
          this.mCachedEnd += localFullSpanItem.getGapForSpan(this.mIndex);
      }
    }

    void calculateCachedStart()
    {
      View localView = (View)this.mViews.get(0);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(localView);
      this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView);
      if (localLayoutParams.mFullSpan)
      {
        StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(localLayoutParams.getViewLayoutPosition());
        if ((localFullSpanItem != null) && (localFullSpanItem.mGapDir == -1))
          this.mCachedStart -= localFullSpanItem.getGapForSpan(this.mIndex);
      }
    }

    void clear()
    {
      this.mViews.clear();
      invalidateCache();
      this.mDeletedSize = 0;
    }

    public int findFirstCompletelyVisibleItemPosition()
    {
      if (StaggeredGridLayoutManager.this.mReverseLayout)
        return findOneVisibleChild(-1 + this.mViews.size(), -1, true);
      return findOneVisibleChild(0, this.mViews.size(), true);
    }

    public int findFirstVisibleItemPosition()
    {
      if (StaggeredGridLayoutManager.this.mReverseLayout)
        return findOneVisibleChild(-1 + this.mViews.size(), -1, false);
      return findOneVisibleChild(0, this.mViews.size(), false);
    }

    public int findLastCompletelyVisibleItemPosition()
    {
      if (StaggeredGridLayoutManager.this.mReverseLayout)
        return findOneVisibleChild(0, this.mViews.size(), true);
      return findOneVisibleChild(-1 + this.mViews.size(), -1, true);
    }

    public int findLastVisibleItemPosition()
    {
      if (StaggeredGridLayoutManager.this.mReverseLayout)
        return findOneVisibleChild(0, this.mViews.size(), false);
      return findOneVisibleChild(-1 + this.mViews.size(), -1, false);
    }

    int findOneVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      int i = -1;
      int j = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      int k = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      int m;
      int n;
      if (paramInt2 > paramInt1)
      {
        m = 1;
        n = paramInt1;
      }
      while (true)
      {
        View localView;
        if (n != paramInt2)
        {
          localView = (View)this.mViews.get(n);
          int i1 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView);
          int i2 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView);
          if ((i1 >= k) || (i2 <= j))
            break label149;
          if (paramBoolean)
          {
            if ((i1 < j) || (i2 > k))
              break label149;
            i = StaggeredGridLayoutManager.this.getPosition(localView);
          }
        }
        else
        {
          return i;
          m = i;
          break;
        }
        return StaggeredGridLayoutManager.this.getPosition(localView);
        label149: n += m;
      }
    }

    public int getDeletedSize()
    {
      return this.mDeletedSize;
    }

    int getEndLine()
    {
      if (this.mCachedEnd != -2147483648)
        return this.mCachedEnd;
      calculateCachedEnd();
      return this.mCachedEnd;
    }

    int getEndLine(int paramInt)
    {
      if (this.mCachedEnd != -2147483648)
        paramInt = this.mCachedEnd;
      while (this.mViews.size() == 0)
        return paramInt;
      calculateCachedEnd();
      return this.mCachedEnd;
    }

    StaggeredGridLayoutManager.LayoutParams getLayoutParams(View paramView)
    {
      return (StaggeredGridLayoutManager.LayoutParams)paramView.getLayoutParams();
    }

    int getNormalizedOffset(int paramInt1, int paramInt2, int paramInt3)
    {
      if (this.mViews.size() == 0)
        paramInt1 = 0;
      int j;
      do
      {
        return paramInt1;
        if (paramInt1 >= 0)
          break;
        j = getEndLine() - paramInt3;
        if (j <= 0)
          return 0;
      }
      while (-paramInt1 <= j);
      return -j;
      int i = paramInt2 - getStartLine();
      if (i <= 0)
        return 0;
      if (i < paramInt1);
      while (true)
      {
        return i;
        i = paramInt1;
      }
    }

    int getStartLine()
    {
      if (this.mCachedStart != -2147483648)
        return this.mCachedStart;
      calculateCachedStart();
      return this.mCachedStart;
    }

    int getStartLine(int paramInt)
    {
      if (this.mCachedStart != -2147483648)
        paramInt = this.mCachedStart;
      while (this.mViews.size() == 0)
        return paramInt;
      calculateCachedStart();
      return this.mCachedStart;
    }

    void invalidateCache()
    {
      this.mCachedStart = -2147483648;
      this.mCachedEnd = -2147483648;
    }

    boolean isEmpty(int paramInt1, int paramInt2)
    {
      int i = this.mViews.size();
      for (int j = 0; j < i; j++)
      {
        View localView = (View)this.mViews.get(j);
        if ((StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView) < paramInt2) && (StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView) > paramInt1))
          return false;
      }
      return true;
    }

    void onOffset(int paramInt)
    {
      if (this.mCachedStart != -2147483648)
        this.mCachedStart = (paramInt + this.mCachedStart);
      if (this.mCachedEnd != -2147483648)
        this.mCachedEnd = (paramInt + this.mCachedEnd);
    }

    void popEnd()
    {
      int i = this.mViews.size();
      View localView = (View)this.mViews.remove(i - 1);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(localView);
      localLayoutParams.mSpan = null;
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged()))
        this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      if (i == 1)
        this.mCachedStart = -2147483648;
      this.mCachedEnd = -2147483648;
    }

    void popStart()
    {
      View localView = (View)this.mViews.remove(0);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(localView);
      localLayoutParams.mSpan = null;
      if (this.mViews.size() == 0)
        this.mCachedEnd = -2147483648;
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged()))
        this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      this.mCachedStart = -2147483648;
    }

    void prependToSpan(View paramView)
    {
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(paramView);
      localLayoutParams.mSpan = this;
      this.mViews.add(0, paramView);
      this.mCachedStart = -2147483648;
      if (this.mViews.size() == 1)
        this.mCachedEnd = -2147483648;
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged()))
        this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(paramView);
    }

    void setLine(int paramInt)
    {
      this.mCachedStart = paramInt;
      this.mCachedEnd = paramInt;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.StaggeredGridLayoutManager
 * JD-Core Version:    0.6.2
 */