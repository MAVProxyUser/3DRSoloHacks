package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager extends RecyclerView.LayoutManager
  implements ItemTouchHelper.ViewDropHandler
{
  private static final boolean DEBUG = false;
  public static final int HORIZONTAL = 0;
  public static final int INVALID_OFFSET = -2147483648;
  private static final float MAX_SCROLL_FACTOR = 0.33F;
  private static final String TAG = "LinearLayoutManager";
  public static final int VERTICAL = 1;
  final AnchorInfo mAnchorInfo = new AnchorInfo();
  private boolean mLastStackFromEnd;
  private LayoutState mLayoutState;
  int mOrientation;
  OrientationHelper mOrientationHelper;
  SavedState mPendingSavedState = null;
  int mPendingScrollPosition = -1;
  int mPendingScrollPositionOffset = -2147483648;
  private boolean mRecycleChildrenOnDetach;
  private boolean mReverseLayout = false;
  boolean mShouldReverseLayout = false;
  private boolean mSmoothScrollbarEnabled = true;
  private boolean mStackFromEnd = false;

  public LinearLayoutManager(Context paramContext)
  {
    this(paramContext, 1, false);
  }

  public LinearLayoutManager(Context paramContext, int paramInt, boolean paramBoolean)
  {
    setOrientation(paramInt);
    setReverseLayout(paramBoolean);
  }

  public LinearLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    RecyclerView.LayoutManager.Properties localProperties = getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setOrientation(localProperties.orientation);
    setReverseLayout(localProperties.reverseLayout);
    setStackFromEnd(localProperties.stackFromEnd);
  }

  private int computeScrollExtent(RecyclerView.State paramState)
  {
    if (getChildCount() == 0)
      return 0;
    ensureLayoutState();
    OrientationHelper localOrientationHelper = this.mOrientationHelper;
    if (!this.mSmoothScrollbarEnabled);
    for (boolean bool1 = true; ; bool1 = false)
    {
      View localView = findFirstVisibleChildClosestToStart(bool1, true);
      boolean bool2 = this.mSmoothScrollbarEnabled;
      boolean bool3 = false;
      if (!bool2)
        bool3 = true;
      return ScrollbarHelper.computeScrollExtent(paramState, localOrientationHelper, localView, findFirstVisibleChildClosestToEnd(bool3, true), this, this.mSmoothScrollbarEnabled);
    }
  }

  private int computeScrollOffset(RecyclerView.State paramState)
  {
    if (getChildCount() == 0)
      return 0;
    ensureLayoutState();
    OrientationHelper localOrientationHelper = this.mOrientationHelper;
    if (!this.mSmoothScrollbarEnabled);
    for (boolean bool1 = true; ; bool1 = false)
    {
      View localView = findFirstVisibleChildClosestToStart(bool1, true);
      boolean bool2 = this.mSmoothScrollbarEnabled;
      boolean bool3 = false;
      if (!bool2)
        bool3 = true;
      return ScrollbarHelper.computeScrollOffset(paramState, localOrientationHelper, localView, findFirstVisibleChildClosestToEnd(bool3, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }
  }

  private int computeScrollRange(RecyclerView.State paramState)
  {
    if (getChildCount() == 0)
      return 0;
    ensureLayoutState();
    OrientationHelper localOrientationHelper = this.mOrientationHelper;
    if (!this.mSmoothScrollbarEnabled);
    for (boolean bool1 = true; ; bool1 = false)
    {
      View localView = findFirstVisibleChildClosestToStart(bool1, true);
      boolean bool2 = this.mSmoothScrollbarEnabled;
      boolean bool3 = false;
      if (!bool2)
        bool3 = true;
      return ScrollbarHelper.computeScrollRange(paramState, localOrientationHelper, localView, findFirstVisibleChildClosestToEnd(bool3, true), this, this.mSmoothScrollbarEnabled);
    }
  }

  private int convertFocusDirectionToLayoutDirection(int paramInt)
  {
    int i = -1;
    int j = 1;
    int k = -2147483648;
    switch (paramInt)
    {
    default:
      i = k;
    case 1:
    case 2:
    case 33:
    case 130:
    case 17:
      do
      {
        do
        {
          return i;
          return j;
        }
        while (this.mOrientation == j);
        return k;
        if (this.mOrientation == j)
          k = j;
        return k;
      }
      while (this.mOrientation == 0);
      return k;
    case 66:
    }
    if (this.mOrientation == 0);
    while (true)
    {
      return j;
      j = k;
    }
  }

  private View findFirstReferenceChild(int paramInt)
  {
    return findReferenceChild(0, getChildCount(), paramInt);
  }

  private View findFirstVisibleChildClosestToEnd(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mShouldReverseLayout)
      return findOneVisibleChild(0, getChildCount(), paramBoolean1, paramBoolean2);
    return findOneVisibleChild(-1 + getChildCount(), -1, paramBoolean1, paramBoolean2);
  }

  private View findFirstVisibleChildClosestToStart(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mShouldReverseLayout)
      return findOneVisibleChild(-1 + getChildCount(), -1, paramBoolean1, paramBoolean2);
    return findOneVisibleChild(0, getChildCount(), paramBoolean1, paramBoolean2);
  }

  private View findLastReferenceChild(int paramInt)
  {
    return findReferenceChild(-1 + getChildCount(), -1, paramInt);
  }

  private View findReferenceChildClosestToEnd(RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout)
      return findFirstReferenceChild(paramState.getItemCount());
    return findLastReferenceChild(paramState.getItemCount());
  }

  private View findReferenceChildClosestToStart(RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout)
      return findLastReferenceChild(paramState.getItemCount());
    return findFirstReferenceChild(paramState.getItemCount());
  }

  private int fixLayoutEndGap(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = this.mOrientationHelper.getEndAfterPadding() - paramInt;
    int j;
    if (i > 0)
    {
      j = -scrollBy(-i, paramRecycler, paramState);
      int k = paramInt + j;
      if (paramBoolean)
      {
        int m = this.mOrientationHelper.getEndAfterPadding() - k;
        if (m > 0)
        {
          this.mOrientationHelper.offsetChildren(m);
          return m + j;
        }
      }
    }
    else
    {
      return 0;
    }
    return j;
  }

  private int fixLayoutStartGap(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = paramInt - this.mOrientationHelper.getStartAfterPadding();
    int j;
    if (i > 0)
    {
      j = -scrollBy(i, paramRecycler, paramState);
      int k = paramInt + j;
      if (paramBoolean)
      {
        int m = k - this.mOrientationHelper.getStartAfterPadding();
        if (m > 0)
        {
          this.mOrientationHelper.offsetChildren(-m);
          return j - m;
        }
      }
    }
    else
    {
      return 0;
    }
    return j;
  }

  private View getChildClosestToEnd()
  {
    if (this.mShouldReverseLayout);
    for (int i = 0; ; i = -1 + getChildCount())
      return getChildAt(i);
  }

  private View getChildClosestToStart()
  {
    if (this.mShouldReverseLayout);
    for (int i = -1 + getChildCount(); ; i = 0)
      return getChildAt(i);
  }

  private void layoutForPredictiveAnimations(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2)
  {
    if ((!paramState.willRunPredictiveAnimations()) || (getChildCount() == 0) || (paramState.isPreLayout()) || (!supportsPredictiveItemAnimations()))
      return;
    int i = 0;
    int j = 0;
    List localList = paramRecycler.getScrapList();
    int k = localList.size();
    int m = getPosition(getChildAt(0));
    int n = 0;
    if (n < k)
    {
      RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)localList.get(n);
      if (localViewHolder.isRemoved());
      while (true)
      {
        n++;
        break;
        int i1;
        if (localViewHolder.getLayoutPosition() < m)
        {
          i1 = 1;
          label112: if (i1 == this.mShouldReverseLayout)
            break label156;
        }
        label156: for (int i2 = -1; ; i2 = 1)
        {
          if (i2 != -1)
            break label162;
          i += this.mOrientationHelper.getDecoratedMeasurement(localViewHolder.itemView);
          break;
          i1 = 0;
          break label112;
        }
        label162: j += this.mOrientationHelper.getDecoratedMeasurement(localViewHolder.itemView);
      }
    }
    this.mLayoutState.mScrapList = localList;
    if (i > 0)
    {
      updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), paramInt1);
      this.mLayoutState.mExtra = i;
      this.mLayoutState.mAvailable = 0;
      this.mLayoutState.assignPositionFromScrapList();
      fill(paramRecycler, this.mLayoutState, paramState, false);
    }
    if (j > 0)
    {
      updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), paramInt2);
      this.mLayoutState.mExtra = j;
      this.mLayoutState.mAvailable = 0;
      this.mLayoutState.assignPositionFromScrapList();
      fill(paramRecycler, this.mLayoutState, paramState, false);
    }
    this.mLayoutState.mScrapList = null;
  }

  private void logChildren()
  {
    Log.d("LinearLayoutManager", "internal representation of views on the screen");
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      Log.d("LinearLayoutManager", "item " + getPosition(localView) + ", coord:" + this.mOrientationHelper.getDecoratedStart(localView));
    }
    Log.d("LinearLayoutManager", "==============");
  }

  private void recycleByLayoutState(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState)
  {
    if (!paramLayoutState.mRecycle)
      return;
    if (paramLayoutState.mLayoutDirection == -1)
    {
      recycleViewsFromEnd(paramRecycler, paramLayoutState.mScrollingOffset);
      return;
    }
    recycleViewsFromStart(paramRecycler, paramLayoutState.mScrollingOffset);
  }

  private void recycleChildren(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2);
    while (true)
    {
      return;
      if (paramInt2 > paramInt1)
        for (int j = paramInt2 - 1; j >= paramInt1; j--)
          removeAndRecycleViewAt(j, paramRecycler);
      else
        for (int i = paramInt1; i > paramInt2; i--)
          removeAndRecycleViewAt(i, paramRecycler);
    }
  }

  private void recycleViewsFromEnd(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    int i = getChildCount();
    if (paramInt < 0);
    while (true)
    {
      return;
      int j = this.mOrientationHelper.getEnd() - paramInt;
      if (this.mShouldReverseLayout)
        for (int m = 0; m < i; m++)
        {
          View localView2 = getChildAt(m);
          if (this.mOrientationHelper.getDecoratedStart(localView2) < j)
          {
            recycleChildren(paramRecycler, 0, m);
            return;
          }
        }
      else
        for (int k = i - 1; k >= 0; k--)
        {
          View localView1 = getChildAt(k);
          if (this.mOrientationHelper.getDecoratedStart(localView1) < j)
          {
            recycleChildren(paramRecycler, i - 1, k);
            return;
          }
        }
    }
  }

  private void recycleViewsFromStart(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    if (paramInt < 0);
    while (true)
    {
      return;
      int i = getChildCount();
      if (this.mShouldReverseLayout)
        for (int k = i - 1; k >= 0; k--)
        {
          View localView2 = getChildAt(k);
          if (this.mOrientationHelper.getDecoratedEnd(localView2) > paramInt)
          {
            recycleChildren(paramRecycler, i - 1, k);
            return;
          }
        }
      else
        for (int j = 0; j < i; j++)
        {
          View localView1 = getChildAt(j);
          if (this.mOrientationHelper.getDecoratedEnd(localView1) > paramInt)
          {
            recycleChildren(paramRecycler, 0, j);
            return;
          }
        }
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

  private boolean updateAnchorFromChildren(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if (getChildCount() == 0);
    do
    {
      return false;
      View localView1 = getFocusedChild();
      if ((localView1 != null) && (paramAnchorInfo.isViewValidAsAnchor(localView1, paramState)))
      {
        paramAnchorInfo.assignFromViewAndKeepVisibleRect(localView1);
        return true;
      }
    }
    while (this.mLastStackFromEnd != this.mStackFromEnd);
    View localView2;
    label59: int i;
    if (paramAnchorInfo.mLayoutFromEnd)
    {
      localView2 = findReferenceChildClosestToEnd(paramState);
      if (localView2 == null)
        break label162;
      paramAnchorInfo.assignFromView(localView2);
      if ((!paramState.isPreLayout()) && (supportsPredictiveItemAnimations()))
      {
        if ((this.mOrientationHelper.getDecoratedStart(localView2) < this.mOrientationHelper.getEndAfterPadding()) && (this.mOrientationHelper.getDecoratedEnd(localView2) >= this.mOrientationHelper.getStartAfterPadding()))
          break label164;
        i = 1;
        label125: if (i != 0)
          if (!paramAnchorInfo.mLayoutFromEnd)
            break label170;
      }
    }
    label162: label164: label170: for (int j = this.mOrientationHelper.getEndAfterPadding(); ; j = this.mOrientationHelper.getStartAfterPadding())
    {
      paramAnchorInfo.mCoordinate = j;
      return true;
      localView2 = findReferenceChildClosestToStart(paramState);
      break label59;
      break;
      i = 0;
      break label125;
    }
  }

  private boolean updateAnchorFromPendingData(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if ((paramState.isPreLayout()) || (this.mPendingScrollPosition == -1))
      return false;
    if ((this.mPendingScrollPosition < 0) || (this.mPendingScrollPosition >= paramState.getItemCount()))
    {
      this.mPendingScrollPosition = -1;
      this.mPendingScrollPositionOffset = -2147483648;
      return false;
    }
    paramAnchorInfo.mPosition = this.mPendingScrollPosition;
    if ((this.mPendingSavedState != null) && (this.mPendingSavedState.hasValidAnchor()))
    {
      paramAnchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
      if (paramAnchorInfo.mLayoutFromEnd)
      {
        paramAnchorInfo.mCoordinate = (this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset);
        return true;
      }
      paramAnchorInfo.mCoordinate = (this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset);
      return true;
    }
    if (this.mPendingScrollPositionOffset == -2147483648)
    {
      View localView = findViewByPosition(this.mPendingScrollPosition);
      if (localView != null)
      {
        if (this.mOrientationHelper.getDecoratedMeasurement(localView) > this.mOrientationHelper.getTotalSpace())
        {
          paramAnchorInfo.assignCoordinateFromPadding();
          return true;
        }
        if (this.mOrientationHelper.getDecoratedStart(localView) - this.mOrientationHelper.getStartAfterPadding() < 0)
        {
          paramAnchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
          paramAnchorInfo.mLayoutFromEnd = false;
          return true;
        }
        if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView) < 0)
        {
          paramAnchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
          paramAnchorInfo.mLayoutFromEnd = true;
          return true;
        }
        if (paramAnchorInfo.mLayoutFromEnd);
        for (int j = this.mOrientationHelper.getDecoratedEnd(localView) + this.mOrientationHelper.getTotalSpaceChange(); ; j = this.mOrientationHelper.getDecoratedStart(localView))
        {
          paramAnchorInfo.mCoordinate = j;
          return true;
        }
      }
      if (getChildCount() > 0)
      {
        int i = getPosition(getChildAt(0));
        if (this.mPendingScrollPosition >= i)
          break label360;
      }
      label360: for (boolean bool1 = true; ; bool1 = false)
      {
        boolean bool2 = this.mShouldReverseLayout;
        boolean bool3 = false;
        if (bool1 == bool2)
          bool3 = true;
        paramAnchorInfo.mLayoutFromEnd = bool3;
        paramAnchorInfo.assignCoordinateFromPadding();
        return true;
      }
    }
    paramAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
    if (this.mShouldReverseLayout)
    {
      paramAnchorInfo.mCoordinate = (this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset);
      return true;
    }
    paramAnchorInfo.mCoordinate = (this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset);
    return true;
  }

  private void updateAnchorInfoForLayout(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if (updateAnchorFromPendingData(paramState, paramAnchorInfo));
    while (updateAnchorFromChildren(paramState, paramAnchorInfo))
      return;
    paramAnchorInfo.assignCoordinateFromPadding();
    if (this.mStackFromEnd);
    for (int i = -1 + paramState.getItemCount(); ; i = 0)
    {
      paramAnchorInfo.mPosition = i;
      return;
    }
  }

  private void updateLayoutState(int paramInt1, int paramInt2, boolean paramBoolean, RecyclerView.State paramState)
  {
    int i = -1;
    int j = 1;
    this.mLayoutState.mExtra = getExtraLayoutSpace(paramState);
    this.mLayoutState.mLayoutDirection = paramInt1;
    int k;
    if (paramInt1 == j)
    {
      LayoutState localLayoutState4 = this.mLayoutState;
      localLayoutState4.mExtra += this.mOrientationHelper.getEndPadding();
      View localView2 = getChildClosestToEnd();
      LayoutState localLayoutState5 = this.mLayoutState;
      if (this.mShouldReverseLayout);
      while (true)
      {
        localLayoutState5.mItemDirection = i;
        this.mLayoutState.mCurrentPosition = (getPosition(localView2) + this.mLayoutState.mItemDirection);
        this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd(localView2);
        k = this.mOrientationHelper.getDecoratedEnd(localView2) - this.mOrientationHelper.getEndAfterPadding();
        this.mLayoutState.mAvailable = paramInt2;
        if (paramBoolean)
        {
          LayoutState localLayoutState3 = this.mLayoutState;
          localLayoutState3.mAvailable -= k;
        }
        this.mLayoutState.mScrollingOffset = k;
        return;
        i = j;
      }
    }
    View localView1 = getChildClosestToStart();
    LayoutState localLayoutState1 = this.mLayoutState;
    localLayoutState1.mExtra += this.mOrientationHelper.getStartAfterPadding();
    LayoutState localLayoutState2 = this.mLayoutState;
    if (this.mShouldReverseLayout);
    while (true)
    {
      localLayoutState2.mItemDirection = j;
      this.mLayoutState.mCurrentPosition = (getPosition(localView1) + this.mLayoutState.mItemDirection);
      this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart(localView1);
      k = -this.mOrientationHelper.getDecoratedStart(localView1) + this.mOrientationHelper.getStartAfterPadding();
      break;
      j = i;
    }
  }

  private void updateLayoutStateToFillEnd(int paramInt1, int paramInt2)
  {
    this.mLayoutState.mAvailable = (this.mOrientationHelper.getEndAfterPadding() - paramInt2);
    LayoutState localLayoutState = this.mLayoutState;
    if (this.mShouldReverseLayout);
    for (int i = -1; ; i = 1)
    {
      localLayoutState.mItemDirection = i;
      this.mLayoutState.mCurrentPosition = paramInt1;
      this.mLayoutState.mLayoutDirection = 1;
      this.mLayoutState.mOffset = paramInt2;
      this.mLayoutState.mScrollingOffset = -2147483648;
      return;
    }
  }

  private void updateLayoutStateToFillEnd(AnchorInfo paramAnchorInfo)
  {
    updateLayoutStateToFillEnd(paramAnchorInfo.mPosition, paramAnchorInfo.mCoordinate);
  }

  private void updateLayoutStateToFillStart(int paramInt1, int paramInt2)
  {
    this.mLayoutState.mAvailable = (paramInt2 - this.mOrientationHelper.getStartAfterPadding());
    this.mLayoutState.mCurrentPosition = paramInt1;
    LayoutState localLayoutState = this.mLayoutState;
    if (this.mShouldReverseLayout);
    for (int i = 1; ; i = -1)
    {
      localLayoutState.mItemDirection = i;
      this.mLayoutState.mLayoutDirection = -1;
      this.mLayoutState.mOffset = paramInt2;
      this.mLayoutState.mScrollingOffset = -2147483648;
      return;
    }
  }

  private void updateLayoutStateToFillStart(AnchorInfo paramAnchorInfo)
  {
    updateLayoutStateToFillStart(paramAnchorInfo.mPosition, paramAnchorInfo.mCoordinate);
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

  public PointF computeScrollVectorForPosition(int paramInt)
  {
    if (getChildCount() == 0)
      return null;
    int i = getPosition(getChildAt(0));
    int j = 0;
    if (paramInt < i)
      j = 1;
    if (j != this.mShouldReverseLayout);
    for (int k = -1; this.mOrientation == 0; k = 1)
      return new PointF(k, 0.0F);
    return new PointF(0.0F, k);
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

  LayoutState createLayoutState()
  {
    return new LayoutState();
  }

  void ensureLayoutState()
  {
    if (this.mLayoutState == null)
      this.mLayoutState = createLayoutState();
    if (this.mOrientationHelper == null)
      this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, this.mOrientation);
  }

  int fill(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = paramLayoutState.mAvailable;
    if (paramLayoutState.mScrollingOffset != -2147483648)
    {
      if (paramLayoutState.mAvailable < 0)
        paramLayoutState.mScrollingOffset += paramLayoutState.mAvailable;
      recycleByLayoutState(paramRecycler, paramLayoutState);
    }
    int j = paramLayoutState.mAvailable + paramLayoutState.mExtra;
    LayoutChunkResult localLayoutChunkResult = new LayoutChunkResult();
    if ((j > 0) && (paramLayoutState.hasMore(paramState)))
    {
      localLayoutChunkResult.resetInternal();
      layoutChunk(paramRecycler, paramState, paramLayoutState, localLayoutChunkResult);
      if (!localLayoutChunkResult.mFinished)
        break label104;
    }
    while (true)
    {
      return i - paramLayoutState.mAvailable;
      label104: paramLayoutState.mOffset += localLayoutChunkResult.mConsumed * paramLayoutState.mLayoutDirection;
      if ((!localLayoutChunkResult.mIgnoreConsumed) || (this.mLayoutState.mScrapList != null) || (!paramState.isPreLayout()))
      {
        paramLayoutState.mAvailable -= localLayoutChunkResult.mConsumed;
        j -= localLayoutChunkResult.mConsumed;
      }
      if (paramLayoutState.mScrollingOffset != -2147483648)
      {
        paramLayoutState.mScrollingOffset += localLayoutChunkResult.mConsumed;
        if (paramLayoutState.mAvailable < 0)
          paramLayoutState.mScrollingOffset += paramLayoutState.mAvailable;
        recycleByLayoutState(paramRecycler, paramLayoutState);
      }
      if ((!paramBoolean) || (!localLayoutChunkResult.mFocusable))
        break;
    }
  }

  public int findFirstCompletelyVisibleItemPosition()
  {
    View localView = findOneVisibleChild(0, getChildCount(), true, false);
    if (localView == null)
      return -1;
    return getPosition(localView);
  }

  public int findFirstVisibleItemPosition()
  {
    View localView = findOneVisibleChild(0, getChildCount(), false, true);
    if (localView == null)
      return -1;
    return getPosition(localView);
  }

  public int findLastCompletelyVisibleItemPosition()
  {
    View localView = findOneVisibleChild(-1 + getChildCount(), -1, true, false);
    if (localView == null)
      return -1;
    return getPosition(localView);
  }

  public int findLastVisibleItemPosition()
  {
    View localView = findOneVisibleChild(-1 + getChildCount(), -1, false, true);
    if (localView == null)
      return -1;
    return getPosition(localView);
  }

  View findOneVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    ensureLayoutState();
    int i = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    int k;
    Object localObject;
    int m;
    if (paramInt2 > paramInt1)
    {
      k = 1;
      localObject = null;
      m = paramInt1;
    }
    while (true)
    {
      if (m == paramInt2)
        break label137;
      View localView = getChildAt(m);
      int n = this.mOrientationHelper.getDecoratedStart(localView);
      int i1 = this.mOrientationHelper.getDecoratedEnd(localView);
      if ((n < j) && (i1 > i))
      {
        if ((!paramBoolean1) || ((n >= i) && (i1 <= j)))
        {
          return localView;
          k = -1;
          break;
        }
        if ((paramBoolean2) && (localObject == null))
          localObject = localView;
      }
      m += k;
    }
    label137: return localObject;
  }

  View findReferenceChild(int paramInt1, int paramInt2, int paramInt3)
  {
    ensureLayoutState();
    Object localObject1 = null;
    Object localObject2 = null;
    int i = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    int k;
    int m;
    label39: Object localObject3;
    if (paramInt2 > paramInt1)
    {
      k = 1;
      m = paramInt1;
      if (m == paramInt2)
        break label151;
      localObject3 = getChildAt(m);
      int n = getPosition((View)localObject3);
      if ((n >= 0) && (n < paramInt3))
      {
        if (!((RecyclerView.LayoutParams)((View)localObject3).getLayoutParams()).isItemRemoved())
          break label111;
        if (localObject1 == null)
          localObject1 = localObject3;
      }
    }
    while (true)
    {
      m += k;
      break label39;
      k = -1;
      break;
      label111: if ((this.mOrientationHelper.getDecoratedStart((View)localObject3) < j) && (this.mOrientationHelper.getDecoratedEnd((View)localObject3) >= i))
        break label160;
      if (localObject2 == null)
        localObject2 = localObject3;
    }
    label151: if (localObject2 != null);
    while (true)
    {
      localObject3 = localObject2;
      label160: return localObject3;
      localObject2 = localObject1;
    }
  }

  public View findViewByPosition(int paramInt)
  {
    int i = getChildCount();
    View localView;
    if (i == 0)
      localView = null;
    do
    {
      return localView;
      int j = paramInt - getPosition(getChildAt(0));
      if ((j < 0) || (j >= i))
        break;
      localView = getChildAt(j);
    }
    while (getPosition(localView) == paramInt);
    return super.findViewByPosition(paramInt);
  }

  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    return new RecyclerView.LayoutParams(-2, -2);
  }

  protected int getExtraLayoutSpace(RecyclerView.State paramState)
  {
    if (paramState.hasTargetScrollPosition())
      return this.mOrientationHelper.getTotalSpace();
    return 0;
  }

  public int getOrientation()
  {
    return this.mOrientation;
  }

  public boolean getRecycleChildrenOnDetach()
  {
    return this.mRecycleChildrenOnDetach;
  }

  public boolean getReverseLayout()
  {
    return this.mReverseLayout;
  }

  public boolean getStackFromEnd()
  {
    return this.mStackFromEnd;
  }

  protected boolean isLayoutRTL()
  {
    return getLayoutDirection() == 1;
  }

  public boolean isSmoothScrollbarEnabled()
  {
    return this.mSmoothScrollbarEnabled;
  }

  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LayoutState paramLayoutState, LayoutChunkResult paramLayoutChunkResult)
  {
    View localView = paramLayoutState.next(paramRecycler);
    if (localView == null)
    {
      paramLayoutChunkResult.mFinished = true;
      return;
    }
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)localView.getLayoutParams();
    boolean bool4;
    label66: int m;
    int k;
    label128: int j;
    int i;
    if (paramLayoutState.mScrapList == null)
    {
      boolean bool3 = this.mShouldReverseLayout;
      if (paramLayoutState.mLayoutDirection == -1)
      {
        bool4 = true;
        if (bool3 != bool4)
          break label231;
        addView(localView);
        measureChildWithMargins(localView, 0, 0);
        paramLayoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(localView);
        if (this.mOrientation != 1)
          break label334;
        if (!isLayoutRTL())
          break label290;
        m = getWidth() - getPaddingRight();
        k = m - this.mOrientationHelper.getDecoratedMeasurementInOther(localView);
        if (paramLayoutState.mLayoutDirection != -1)
          break label313;
        j = paramLayoutState.mOffset;
        i = paramLayoutState.mOffset - paramLayoutChunkResult.mConsumed;
      }
    }
    while (true)
    {
      layoutDecorated(localView, k + localLayoutParams.leftMargin, i + localLayoutParams.topMargin, m - localLayoutParams.rightMargin, j - localLayoutParams.bottomMargin);
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged()))
        paramLayoutChunkResult.mIgnoreConsumed = true;
      paramLayoutChunkResult.mFocusable = localView.isFocusable();
      return;
      bool4 = false;
      break;
      label231: addView(localView, 0);
      break label66;
      boolean bool1 = this.mShouldReverseLayout;
      if (paramLayoutState.mLayoutDirection == -1);
      for (boolean bool2 = true; ; bool2 = false)
      {
        if (bool1 != bool2)
          break label280;
        addDisappearingView(localView);
        break;
      }
      label280: addDisappearingView(localView, 0);
      break label66;
      label290: k = getPaddingLeft();
      m = k + this.mOrientationHelper.getDecoratedMeasurementInOther(localView);
      break label128;
      label313: i = paramLayoutState.mOffset;
      j = paramLayoutState.mOffset + paramLayoutChunkResult.mConsumed;
      continue;
      label334: i = getPaddingTop();
      j = i + this.mOrientationHelper.getDecoratedMeasurementInOther(localView);
      if (paramLayoutState.mLayoutDirection == -1)
      {
        m = paramLayoutState.mOffset;
        k = paramLayoutState.mOffset - paramLayoutChunkResult.mConsumed;
      }
      else
      {
        k = paramLayoutState.mOffset;
        m = paramLayoutState.mOffset + paramLayoutChunkResult.mConsumed;
      }
    }
  }

  void onAnchorReady(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
  }

  public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
  {
    super.onDetachedFromWindow(paramRecyclerView, paramRecycler);
    if (this.mRecycleChildrenOnDetach)
    {
      removeAndRecycleAllViews(paramRecycler);
      paramRecycler.clear();
    }
  }

  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    resolveShouldLayoutReverse();
    View localView2;
    if (getChildCount() == 0)
      localView2 = null;
    while (true)
    {
      return localView2;
      int i = convertFocusDirectionToLayoutDirection(paramInt);
      if (i == -2147483648)
        return null;
      ensureLayoutState();
      if (i == -1);
      for (View localView1 = findReferenceChildClosestToStart(paramState); localView1 == null; localView1 = findReferenceChildClosestToEnd(paramState))
        return null;
      ensureLayoutState();
      updateLayoutState(i, (int)(0.33F * this.mOrientationHelper.getTotalSpace()), false, paramState);
      this.mLayoutState.mScrollingOffset = -2147483648;
      this.mLayoutState.mRecycle = false;
      fill(paramRecycler, this.mLayoutState, paramState, true);
      if (i == -1);
      for (localView2 = getChildClosestToStart(); (localView2 == localView1) || (!localView2.isFocusable()); localView2 = getChildClosestToEnd())
        return null;
    }
  }

  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if (getChildCount() > 0)
    {
      AccessibilityRecordCompat localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAccessibilityEvent);
      localAccessibilityRecordCompat.setFromIndex(findFirstVisibleItemPosition());
      localAccessibilityRecordCompat.setToIndex(findLastVisibleItemPosition());
    }
  }

  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if ((this.mPendingSavedState != null) && (this.mPendingSavedState.hasValidAnchor()))
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
    ensureLayoutState();
    this.mLayoutState.mRecycle = false;
    resolveShouldLayoutReverse();
    this.mAnchorInfo.reset();
    this.mAnchorInfo.mLayoutFromEnd = (this.mShouldReverseLayout ^ this.mStackFromEnd);
    updateAnchorInfoForLayout(paramState, this.mAnchorInfo);
    int i = getExtraLayoutSpace(paramState);
    int k;
    int j;
    int m;
    int n;
    View localView;
    int i16;
    label192: label204: int i3;
    label427: int i11;
    int i12;
    if (this.mLayoutState.mLastScrollDelta >= 0)
    {
      k = i;
      j = 0;
      m = j + this.mOrientationHelper.getStartAfterPadding();
      n = k + this.mOrientationHelper.getEndPadding();
      if ((paramState.isPreLayout()) && (this.mPendingScrollPosition != -1) && (this.mPendingScrollPositionOffset != -2147483648))
      {
        localView = findViewByPosition(this.mPendingScrollPosition);
        if (localView != null)
        {
          if (!this.mShouldReverseLayout)
            break label554;
          i16 = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView) - this.mPendingScrollPositionOffset;
          if (i16 <= 0)
            break label585;
          m += i16;
        }
      }
      onAnchorReady(paramState, this.mAnchorInfo);
      detachAndScrapAttachedViews(paramRecycler);
      this.mLayoutState.mIsPreLayout = paramState.isPreLayout();
      if (!this.mAnchorInfo.mLayoutFromEnd)
        break label595;
      updateLayoutStateToFillStart(this.mAnchorInfo);
      this.mLayoutState.mExtra = m;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i3 = this.mLayoutState.mOffset;
      int i13 = this.mLayoutState.mCurrentPosition;
      if (this.mLayoutState.mAvailable > 0)
        n += this.mLayoutState.mAvailable;
      updateLayoutStateToFillEnd(this.mAnchorInfo);
      this.mLayoutState.mExtra = n;
      LayoutState localLayoutState2 = this.mLayoutState;
      localLayoutState2.mCurrentPosition += this.mLayoutState.mItemDirection;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i1 = this.mLayoutState.mOffset;
      if (this.mLayoutState.mAvailable > 0)
      {
        int i14 = this.mLayoutState.mAvailable;
        updateLayoutStateToFillStart(i13, i3);
        this.mLayoutState.mExtra = i14;
        fill(paramRecycler, this.mLayoutState, paramState, false);
        i3 = this.mLayoutState.mOffset;
      }
      if (getChildCount() > 0)
      {
        if (!(this.mShouldReverseLayout ^ this.mStackFromEnd))
          break label786;
        int i9 = fixLayoutEndGap(i1, paramRecycler, paramState, true);
        int i10 = i3 + i9;
        i11 = i1 + i9;
        i12 = fixLayoutStartGap(i10, paramRecycler, paramState, false);
        i3 = i10 + i12;
      }
    }
    label554: label585: label595: int i7;
    label786: int i8;
    for (int i1 = i11 + i12; ; i1 = i7 + i8)
    {
      layoutForPredictiveAnimations(paramRecycler, paramState, i3, i1);
      if (!paramState.isPreLayout())
      {
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = -2147483648;
        this.mOrientationHelper.onLayoutComplete();
      }
      this.mLastStackFromEnd = this.mStackFromEnd;
      this.mPendingSavedState = null;
      return;
      j = i;
      k = 0;
      break;
      int i15 = this.mOrientationHelper.getDecoratedStart(localView) - this.mOrientationHelper.getStartAfterPadding();
      i16 = this.mPendingScrollPositionOffset - i15;
      break label192;
      n -= i16;
      break label204;
      updateLayoutStateToFillEnd(this.mAnchorInfo);
      this.mLayoutState.mExtra = n;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i1 = this.mLayoutState.mOffset;
      int i2 = this.mLayoutState.mCurrentPosition;
      if (this.mLayoutState.mAvailable > 0)
        m += this.mLayoutState.mAvailable;
      updateLayoutStateToFillStart(this.mAnchorInfo);
      this.mLayoutState.mExtra = m;
      LayoutState localLayoutState1 = this.mLayoutState;
      localLayoutState1.mCurrentPosition += this.mLayoutState.mItemDirection;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i3 = this.mLayoutState.mOffset;
      if (this.mLayoutState.mAvailable <= 0)
        break label427;
      int i4 = this.mLayoutState.mAvailable;
      updateLayoutStateToFillEnd(i2, i1);
      this.mLayoutState.mExtra = i4;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      i1 = this.mLayoutState.mOffset;
      break label427;
      int i5 = fixLayoutStartGap(i3, paramRecycler, paramState, true);
      int i6 = i3 + i5;
      i7 = i1 + i5;
      i8 = fixLayoutEndGap(i7, paramRecycler, paramState, false);
      i3 = i6 + i8;
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
      return new SavedState(this.mPendingSavedState);
    SavedState localSavedState = new SavedState();
    if (getChildCount() > 0)
    {
      ensureLayoutState();
      boolean bool = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
      localSavedState.mAnchorLayoutFromEnd = bool;
      if (bool)
      {
        View localView2 = getChildClosestToEnd();
        localSavedState.mAnchorOffset = (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView2));
        localSavedState.mAnchorPosition = getPosition(localView2);
        return localSavedState;
      }
      View localView1 = getChildClosestToStart();
      localSavedState.mAnchorPosition = getPosition(localView1);
      localSavedState.mAnchorOffset = (this.mOrientationHelper.getDecoratedStart(localView1) - this.mOrientationHelper.getStartAfterPadding());
      return localSavedState;
    }
    localSavedState.invalidateAnchor();
    return localSavedState;
  }

  public void prepareForDrop(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
    ensureLayoutState();
    resolveShouldLayoutReverse();
    int i = getPosition(paramView1);
    int j = getPosition(paramView2);
    int k;
    if (i < j)
      k = 1;
    while (this.mShouldReverseLayout)
      if (k == 1)
      {
        scrollToPositionWithOffset(j, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(paramView2) + this.mOrientationHelper.getDecoratedMeasurement(paramView1)));
        return;
        k = -1;
      }
      else
      {
        scrollToPositionWithOffset(j, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(paramView2));
        return;
      }
    if (k == -1)
    {
      scrollToPositionWithOffset(j, this.mOrientationHelper.getDecoratedStart(paramView2));
      return;
    }
    scrollToPositionWithOffset(j, this.mOrientationHelper.getDecoratedEnd(paramView2) - this.mOrientationHelper.getDecoratedMeasurement(paramView1));
  }

  int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if ((getChildCount() == 0) || (paramInt == 0));
    int i;
    int j;
    int k;
    do
    {
      return 0;
      this.mLayoutState.mRecycle = true;
      ensureLayoutState();
      if (paramInt <= 0)
        break;
      i = 1;
      j = Math.abs(paramInt);
      updateLayoutState(i, j, true, paramState);
      k = this.mLayoutState.mScrollingOffset + fill(paramRecycler, this.mLayoutState, paramState, false);
    }
    while (k < 0);
    if (j > k);
    for (int m = i * k; ; m = paramInt)
    {
      this.mOrientationHelper.offsetChildren(-m);
      this.mLayoutState.mLastScrollDelta = m;
      return m;
      i = -1;
      break;
    }
  }

  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1)
      return 0;
    return scrollBy(paramInt, paramRecycler, paramState);
  }

  public void scrollToPosition(int paramInt)
  {
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = -2147483648;
    if (this.mPendingSavedState != null)
      this.mPendingSavedState.invalidateAnchor();
    requestLayout();
  }

  public void scrollToPositionWithOffset(int paramInt1, int paramInt2)
  {
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    if (this.mPendingSavedState != null)
      this.mPendingSavedState.invalidateAnchor();
    requestLayout();
  }

  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0)
      return 0;
    return scrollBy(paramInt, paramRecycler, paramState);
  }

  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1))
      throw new IllegalArgumentException("invalid orientation:" + paramInt);
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mOrientation)
      return;
    this.mOrientation = paramInt;
    this.mOrientationHelper = null;
    requestLayout();
  }

  public void setRecycleChildrenOnDetach(boolean paramBoolean)
  {
    this.mRecycleChildrenOnDetach = paramBoolean;
  }

  public void setReverseLayout(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    if (paramBoolean == this.mReverseLayout)
      return;
    this.mReverseLayout = paramBoolean;
    requestLayout();
  }

  public void setSmoothScrollbarEnabled(boolean paramBoolean)
  {
    this.mSmoothScrollbarEnabled = paramBoolean;
  }

  public void setStackFromEnd(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    if (this.mStackFromEnd == paramBoolean)
      return;
    this.mStackFromEnd = paramBoolean;
    requestLayout();
  }

  public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
  {
    LinearSmoothScroller local1 = new LinearSmoothScroller(paramRecyclerView.getContext())
    {
      public PointF computeScrollVectorForPosition(int paramAnonymousInt)
      {
        return LinearLayoutManager.this.computeScrollVectorForPosition(paramAnonymousInt);
      }
    };
    local1.setTargetPosition(paramInt);
    startSmoothScroll(local1);
  }

  public boolean supportsPredictiveItemAnimations()
  {
    return (this.mPendingSavedState == null) && (this.mLastStackFromEnd == this.mStackFromEnd);
  }

  void validateChildOrder()
  {
    int i = 1;
    Log.d("LinearLayoutManager", "validating child count " + getChildCount());
    if (getChildCount() < i);
    while (true)
    {
      return;
      int j = getPosition(getChildAt(0));
      int k = this.mOrientationHelper.getDecoratedStart(getChildAt(0));
      boolean bool;
      if (this.mShouldReverseLayout)
        for (int i2 = 1; i2 < getChildCount(); i2++)
        {
          View localView2 = getChildAt(i2);
          int i3 = getPosition(localView2);
          int i4 = this.mOrientationHelper.getDecoratedStart(localView2);
          if (i3 < j)
          {
            logChildren();
            StringBuilder localStringBuilder2 = new StringBuilder().append("detected invalid position. loc invalid? ");
            if (i4 < k);
            while (true)
            {
              throw new RuntimeException(i);
              bool = false;
            }
          }
          if (i4 > k)
          {
            logChildren();
            throw new RuntimeException("detected invalid location");
          }
        }
      else
        for (int m = 1; m < getChildCount(); m++)
        {
          View localView1 = getChildAt(m);
          int n = getPosition(localView1);
          int i1 = this.mOrientationHelper.getDecoratedStart(localView1);
          if (n < j)
          {
            logChildren();
            StringBuilder localStringBuilder1 = new StringBuilder().append("detected invalid position. loc invalid? ");
            if (i1 < k);
            while (true)
            {
              throw new RuntimeException(bool);
              bool = false;
            }
          }
          if (i1 < k)
          {
            logChildren();
            throw new RuntimeException("detected invalid location");
          }
        }
    }
  }

  class AnchorInfo
  {
    int mCoordinate;
    boolean mLayoutFromEnd;
    int mPosition;

    AnchorInfo()
    {
    }

    private boolean isViewValidAsAnchor(View paramView, RecyclerView.State paramState)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      return (!localLayoutParams.isItemRemoved()) && (localLayoutParams.getViewLayoutPosition() >= 0) && (localLayoutParams.getViewLayoutPosition() < paramState.getItemCount());
    }

    void assignCoordinateFromPadding()
    {
      if (this.mLayoutFromEnd);
      for (int i = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding(); ; i = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding())
      {
        this.mCoordinate = i;
        return;
      }
    }

    public void assignFromView(View paramView)
    {
      if (this.mLayoutFromEnd);
      for (this.mCoordinate = (LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView) + LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange()); ; this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView))
      {
        this.mPosition = LinearLayoutManager.this.getPosition(paramView);
        return;
      }
    }

    public void assignFromViewAndKeepVisibleRect(View paramView)
    {
      int i = LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
      if (i >= 0)
        assignFromView(paramView);
      int k;
      int i1;
      do
      {
        int j;
        do
        {
          int i2;
          int i6;
          do
          {
            do
            {
              return;
              this.mPosition = LinearLayoutManager.this.getPosition(paramView);
              if (!this.mLayoutFromEnd)
                break;
              i2 = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - i - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView);
              this.mCoordinate = (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - i2);
            }
            while (i2 <= 0);
            int i3 = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(paramView);
            int i4 = this.mCoordinate - i3;
            int i5 = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
            i6 = i4 - (i5 + Math.min(LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView) - i5, 0));
          }
          while (i6 >= 0);
          this.mCoordinate += Math.min(i2, -i6);
          return;
          j = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView);
          k = j - LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
          this.mCoordinate = j;
        }
        while (k <= 0);
        int m = j + LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(paramView);
        int n = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - i - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView);
        i1 = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - Math.min(0, n) - m;
      }
      while (i1 >= 0);
      this.mCoordinate -= Math.min(k, -i1);
    }

    void reset()
    {
      this.mPosition = -1;
      this.mCoordinate = -2147483648;
      this.mLayoutFromEnd = false;
    }

    public String toString()
    {
      return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + '}';
    }
  }

  protected static class LayoutChunkResult
  {
    public int mConsumed;
    public boolean mFinished;
    public boolean mFocusable;
    public boolean mIgnoreConsumed;

    void resetInternal()
    {
      this.mConsumed = 0;
      this.mFinished = false;
      this.mIgnoreConsumed = false;
      this.mFocusable = false;
    }
  }

  static class LayoutState
  {
    static final int INVALID_LAYOUT = -2147483648;
    static final int ITEM_DIRECTION_HEAD = -1;
    static final int ITEM_DIRECTION_TAIL = 1;
    static final int LAYOUT_END = 1;
    static final int LAYOUT_START = -1;
    static final int SCOLLING_OFFSET_NaN = -2147483648;
    static final String TAG = "LinearLayoutManager#LayoutState";
    int mAvailable;
    int mCurrentPosition;
    int mExtra = 0;
    boolean mIsPreLayout = false;
    int mItemDirection;
    int mLastScrollDelta;
    int mLayoutDirection;
    int mOffset;
    boolean mRecycle = true;
    List<RecyclerView.ViewHolder> mScrapList = null;
    int mScrollingOffset;

    private View nextViewFromScrapList()
    {
      int i = this.mScrapList.size();
      int j = 0;
      if (j < i)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mScrapList.get(j);
        if (localViewHolder.isRemoved());
        while (this.mCurrentPosition != localViewHolder.getLayoutPosition())
        {
          j++;
          break;
        }
        assignPositionFromScrapList(localViewHolder);
        return localViewHolder.itemView;
      }
      return null;
    }

    public void assignPositionFromScrapList()
    {
      assignPositionFromScrapList(null);
    }

    public void assignPositionFromScrapList(RecyclerView.ViewHolder paramViewHolder)
    {
      RecyclerView.ViewHolder localViewHolder = nextViewHolderInLimitedList(paramViewHolder);
      if (localViewHolder == null);
      for (int i = -1; ; i = localViewHolder.getLayoutPosition())
      {
        this.mCurrentPosition = i;
        return;
      }
    }

    boolean hasMore(RecyclerView.State paramState)
    {
      return (this.mCurrentPosition >= 0) && (this.mCurrentPosition < paramState.getItemCount());
    }

    void log()
    {
      Log.d("LinearLayoutManager#LayoutState", "avail:" + this.mAvailable + ", ind:" + this.mCurrentPosition + ", dir:" + this.mItemDirection + ", offset:" + this.mOffset + ", layoutDir:" + this.mLayoutDirection);
    }

    View next(RecyclerView.Recycler paramRecycler)
    {
      if (this.mScrapList != null)
        return nextViewFromScrapList();
      View localView = paramRecycler.getViewForPosition(this.mCurrentPosition);
      this.mCurrentPosition += this.mItemDirection;
      return localView;
    }

    public RecyclerView.ViewHolder nextViewHolderInLimitedList(RecyclerView.ViewHolder paramViewHolder)
    {
      int i = this.mScrapList.size();
      Object localObject = null;
      int j = 2147483647;
      int k = 0;
      if (k < i)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mScrapList.get(k);
        if ((localViewHolder == paramViewHolder) || (localViewHolder.isRemoved()));
        int m;
        do
        {
          do
          {
            k++;
            break;
            m = (localViewHolder.getLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
          }
          while ((m < 0) || (m >= j));
          localObject = localViewHolder;
          j = m;
        }
        while (m != 0);
      }
      return localObject;
    }
  }

  static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public LinearLayoutManager.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new LinearLayoutManager.SavedState(paramAnonymousParcel);
      }

      public LinearLayoutManager.SavedState[] newArray(int paramAnonymousInt)
      {
        return new LinearLayoutManager.SavedState[paramAnonymousInt];
      }
    };
    boolean mAnchorLayoutFromEnd;
    int mAnchorOffset;
    int mAnchorPosition;

    public SavedState()
    {
    }

    SavedState(Parcel paramParcel)
    {
      this.mAnchorPosition = paramParcel.readInt();
      this.mAnchorOffset = paramParcel.readInt();
      if (paramParcel.readInt() == i);
      while (true)
      {
        this.mAnchorLayoutFromEnd = i;
        return;
        i = 0;
      }
    }

    public SavedState(SavedState paramSavedState)
    {
      this.mAnchorPosition = paramSavedState.mAnchorPosition;
      this.mAnchorOffset = paramSavedState.mAnchorOffset;
      this.mAnchorLayoutFromEnd = paramSavedState.mAnchorLayoutFromEnd;
    }

    public int describeContents()
    {
      return 0;
    }

    boolean hasValidAnchor()
    {
      return this.mAnchorPosition >= 0;
    }

    void invalidateAnchor()
    {
      this.mAnchorPosition = -1;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mAnchorPosition);
      paramParcel.writeInt(this.mAnchorOffset);
      if (this.mAnchorLayoutFromEnd);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.LinearLayoutManager
 * JD-Core Version:    0.6.2
 */