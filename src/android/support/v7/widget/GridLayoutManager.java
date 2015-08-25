package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager
{
  private static final boolean DEBUG = false;
  public static final int DEFAULT_SPAN_COUNT = -1;
  static final int MAIN_DIR_SPEC = 0;
  private static final String TAG = "GridLayoutManager";
  int[] mCachedBorders;
  final Rect mDecorInsets = new Rect();
  boolean mPendingSpanCountChange = false;
  final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
  final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
  View[] mSet;
  int mSpanCount = -1;
  SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();

  public GridLayoutManager(Context paramContext, int paramInt)
  {
    super(paramContext);
    setSpanCount(paramInt);
  }

  public GridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramContext, paramInt2, paramBoolean);
    setSpanCount(paramInt1);
  }

  public GridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setSpanCount(getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2).spanCount);
  }

  private void assignSpans(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    int j;
    int k;
    int n;
    int m;
    label40: int i1;
    label44: LayoutParams localLayoutParams;
    if (paramBoolean)
    {
      i = 0;
      j = paramInt1;
      k = 1;
      if ((this.mOrientation != 1) || (!isLayoutRTL()))
        break label156;
      n = -1 + this.mSpanCount;
      m = -1;
      i1 = i;
      if (i1 == j)
        return;
      View localView = this.mSet[i1];
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      LayoutParams.access$102(localLayoutParams, getSpanSize(paramRecycler, paramState, getPosition(localView)));
      if ((m != -1) || (localLayoutParams.mSpanSize <= 1))
        break label165;
      LayoutParams.access$002(localLayoutParams, n - (-1 + localLayoutParams.mSpanSize));
    }
    while (true)
    {
      n += m * localLayoutParams.mSpanSize;
      i1 += k;
      break label44;
      i = paramInt1 - 1;
      j = -1;
      k = -1;
      break;
      label156: m = 1;
      n = 0;
      break label40;
      label165: LayoutParams.access$002(localLayoutParams, n);
    }
  }

  private void cachePreLayoutSpanMapping()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(j).getLayoutParams();
      int k = localLayoutParams.getViewLayoutPosition();
      this.mPreLayoutSpanSizeCache.put(k, localLayoutParams.getSpanSize());
      this.mPreLayoutSpanIndexCache.put(k, localLayoutParams.getSpanIndex());
    }
  }

  private void calculateItemBorders(int paramInt)
  {
    if ((this.mCachedBorders == null) || (this.mCachedBorders.length != 1 + this.mSpanCount) || (this.mCachedBorders[(-1 + this.mCachedBorders.length)] != paramInt))
      this.mCachedBorders = new int[1 + this.mSpanCount];
    this.mCachedBorders[0] = 0;
    int i = paramInt / this.mSpanCount;
    int j = paramInt % this.mSpanCount;
    int k = 0;
    int m = 0;
    for (int n = 1; n <= this.mSpanCount; n++)
    {
      int i1 = i;
      m += j;
      if ((m > 0) && (this.mSpanCount - m < j))
      {
        i1++;
        m -= this.mSpanCount;
      }
      k += i1;
      this.mCachedBorders[n] = k;
    }
  }

  private void clearPreLayoutSpanMappingCache()
  {
    this.mPreLayoutSpanSizeCache.clear();
    this.mPreLayoutSpanIndexCache.clear();
  }

  private void ensureAnchorIsInFirstSpan(LinearLayoutManager.AnchorInfo paramAnchorInfo)
  {
    for (int i = this.mSpanSizeLookup.getCachedSpanIndex(paramAnchorInfo.mPosition, this.mSpanCount); (i > 0) && (paramAnchorInfo.mPosition > 0); i = this.mSpanSizeLookup.getCachedSpanIndex(paramAnchorInfo.mPosition, this.mSpanCount))
      paramAnchorInfo.mPosition = (-1 + paramAnchorInfo.mPosition);
  }

  private int getMainDirSpec(int paramInt)
  {
    if (paramInt < 0)
      return MAIN_DIR_SPEC;
    return View.MeasureSpec.makeMeasureSpec(paramInt, 1073741824);
  }

  private int getSpanGroupIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout())
      return this.mSpanSizeLookup.getSpanGroupIndex(paramInt, this.mSpanCount);
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
  }

  private int getSpanIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    int i;
    if (!paramState.isPreLayout())
      i = this.mSpanSizeLookup.getCachedSpanIndex(paramInt, this.mSpanCount);
    do
    {
      return i;
      i = this.mPreLayoutSpanIndexCache.get(paramInt, -1);
    }
    while (i != -1);
    int j = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (j == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getCachedSpanIndex(j, this.mSpanCount);
  }

  private int getSpanSize(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    int i;
    if (!paramState.isPreLayout())
      i = this.mSpanSizeLookup.getSpanSize(paramInt);
    do
    {
      return i;
      i = this.mPreLayoutSpanSizeCache.get(paramInt, -1);
    }
    while (i != -1);
    int j = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (j == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 1;
    }
    return this.mSpanSizeLookup.getSpanSize(j);
  }

  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2)
  {
    calculateItemDecorationsForChild(paramView, this.mDecorInsets);
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    paramView.measure(updateSpecWithExtra(paramInt1, localLayoutParams.leftMargin + this.mDecorInsets.left, localLayoutParams.rightMargin + this.mDecorInsets.right), updateSpecWithExtra(paramInt2, localLayoutParams.topMargin + this.mDecorInsets.top, localLayoutParams.bottomMargin + this.mDecorInsets.bottom));
  }

  private void updateMeasurements()
  {
    if (getOrientation() == 1);
    for (int i = getWidth() - getPaddingRight() - getPaddingLeft(); ; i = getHeight() - getPaddingBottom() - getPaddingTop())
    {
      calculateItemBorders(i);
      return;
    }
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

  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
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
        break label170;
      localObject3 = getChildAt(m);
      int n = getPosition((View)localObject3);
      if ((n >= 0) && (n < paramInt3) && (this.mSpanSizeLookup.getCachedSpanIndex(n, this.mSpanCount) == 0))
        break label104;
    }
    while (true)
    {
      m += k;
      break label39;
      k = -1;
      break;
      label104: if (((RecyclerView.LayoutParams)((View)localObject3).getLayoutParams()).isItemRemoved())
      {
        if (localObject1 == null)
          localObject1 = localObject3;
      }
      else
      {
        if ((this.mOrientationHelper.getDecoratedStart((View)localObject3) < j) && (this.mOrientationHelper.getDecoratedEnd((View)localObject3) >= i))
          break label179;
        if (localObject2 == null)
          localObject2 = localObject3;
      }
    }
    label170: if (localObject2 != null);
    while (true)
    {
      localObject3 = localObject2;
      label179: return localObject3;
      localObject2 = localObject1;
    }
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
    if (paramState.getItemCount() < 1)
      return 0;
    return getSpanGroupIndex(paramRecycler, paramState, -1 + paramState.getItemCount());
  }

  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0)
      return this.mSpanCount;
    if (paramState.getItemCount() < 1)
      return 0;
    return getSpanGroupIndex(paramRecycler, paramState, -1 + paramState.getItemCount());
  }

  public int getSpanCount()
  {
    return this.mSpanCount;
  }

  public SpanSizeLookup getSpanSizeLookup()
  {
    return this.mSpanSizeLookup;
  }

  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, LinearLayoutManager.LayoutChunkResult paramLayoutChunkResult)
  {
    boolean bool;
    int i;
    int j;
    int k;
    if (paramLayoutState.mItemDirection == 1)
    {
      bool = true;
      i = this.mSpanCount;
      j = 0;
      k = 0;
      if (!bool)
        i = getSpanIndex(paramRecycler, paramState, paramLayoutState.mCurrentPosition) + getSpanSize(paramRecycler, paramState, paramLayoutState.mCurrentPosition);
    }
    while (true)
    {
      int i12;
      if ((j < this.mSpanCount) && (paramLayoutState.hasMore(paramState)) && (i > 0))
      {
        int i11 = paramLayoutState.mCurrentPosition;
        i12 = getSpanSize(paramRecycler, paramState, i11);
        if (i12 > this.mSpanCount)
        {
          throw new IllegalArgumentException("Item at position " + i11 + " requires " + i12 + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
          bool = false;
          break;
        }
        i -= i12;
        if (i >= 0)
          break label187;
      }
      label187: View localView4;
      do
      {
        if (j != 0)
          break;
        paramLayoutChunkResult.mFinished = true;
        return;
        localView4 = paramLayoutState.next(paramRecycler);
      }
      while (localView4 == null);
      k += i12;
      this.mSet[j] = localView4;
      j++;
    }
    int m = 0;
    assignSpans(paramRecycler, paramState, j, k, bool);
    int n = 0;
    if (n < j)
    {
      View localView3 = this.mSet[n];
      label273: LayoutParams localLayoutParams3;
      int i9;
      if (paramLayoutState.mScrapList == null)
        if (bool)
        {
          addView(localView3);
          localLayoutParams3 = (LayoutParams)localView3.getLayoutParams();
          i9 = View.MeasureSpec.makeMeasureSpec(this.mCachedBorders[(localLayoutParams3.mSpanIndex + localLayoutParams3.mSpanSize)] - this.mCachedBorders[localLayoutParams3.mSpanIndex], 1073741824);
          if (this.mOrientation != 1)
            break label404;
          measureChildWithDecorationsAndMargin(localView3, i9, getMainDirSpec(localLayoutParams3.height));
        }
      while (true)
      {
        int i10 = this.mOrientationHelper.getDecoratedMeasurement(localView3);
        if (i10 > m)
          m = i10;
        n++;
        break;
        addView(localView3, 0);
        break label273;
        if (bool)
        {
          addDisappearingView(localView3);
          break label273;
        }
        addDisappearingView(localView3, 0);
        break label273;
        label404: measureChildWithDecorationsAndMargin(localView3, getMainDirSpec(localLayoutParams3.width), i9);
      }
    }
    int i1 = getMainDirSpec(m);
    int i2 = 0;
    if (i2 < j)
    {
      View localView2 = this.mSet[i2];
      int i8;
      if (this.mOrientationHelper.getDecoratedMeasurement(localView2) != m)
      {
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        i8 = View.MeasureSpec.makeMeasureSpec(this.mCachedBorders[(localLayoutParams2.mSpanIndex + localLayoutParams2.mSpanSize)] - this.mCachedBorders[localLayoutParams2.mSpanIndex], 1073741824);
        if (this.mOrientation != 1)
          break label533;
        measureChildWithDecorationsAndMargin(localView2, i8, i1);
      }
      while (true)
      {
        i2++;
        break;
        label533: measureChildWithDecorationsAndMargin(localView2, i1, i8);
      }
    }
    paramLayoutChunkResult.mConsumed = m;
    int i3 = 0;
    int i4 = 0;
    int i5;
    int i6;
    int i7;
    label591: View localView1;
    LayoutParams localLayoutParams1;
    if (this.mOrientation == 1)
      if (paramLayoutState.mLayoutDirection == -1)
      {
        i5 = paramLayoutState.mOffset;
        i6 = i5 - m;
        i7 = 0;
        if (i7 >= j)
          break label846;
        localView1 = this.mSet[i7];
        localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
        if (this.mOrientation != 1)
          break label812;
        i3 = getPaddingLeft() + this.mCachedBorders[localLayoutParams1.mSpanIndex];
        i4 = i3 + this.mOrientationHelper.getDecoratedMeasurementInOther(localView1);
      }
    while (true)
    {
      layoutDecorated(localView1, i3 + localLayoutParams1.leftMargin, i6 + localLayoutParams1.topMargin, i4 - localLayoutParams1.rightMargin, i5 - localLayoutParams1.bottomMargin);
      if ((localLayoutParams1.isItemRemoved()) || (localLayoutParams1.isItemChanged()))
        paramLayoutChunkResult.mIgnoreConsumed = true;
      paramLayoutChunkResult.mFocusable |= localView1.isFocusable();
      i7++;
      break label591;
      i6 = paramLayoutState.mOffset;
      i5 = i6 + m;
      i3 = 0;
      i4 = 0;
      break;
      if (paramLayoutState.mLayoutDirection == -1)
      {
        i4 = paramLayoutState.mOffset;
        i3 = i4 - m;
        i5 = 0;
        i6 = 0;
        break;
      }
      i3 = paramLayoutState.mOffset;
      i4 = i3 + m;
      i5 = 0;
      i6 = 0;
      break;
      label812: i6 = getPaddingTop() + this.mCachedBorders[localLayoutParams1.mSpanIndex];
      i5 = i6 + this.mOrientationHelper.getDecoratedMeasurementInOther(localView1);
    }
    label846: Arrays.fill(this.mSet, null);
  }

  void onAnchorReady(RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo)
  {
    super.onAnchorReady(paramState, paramAnchorInfo);
    updateMeasurements();
    if ((paramState.getItemCount() > 0) && (!paramState.isPreLayout()))
      ensureAnchorIsInFirstSpan(paramAnchorInfo);
    if ((this.mSet == null) || (this.mSet.length != this.mSpanCount))
      this.mSet = new View[this.mSpanCount];
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
    int i = getSpanGroupIndex(paramRecycler, paramState, localLayoutParams1.getViewLayoutPosition());
    if (this.mOrientation == 0)
    {
      int m = localLayoutParams1.getSpanIndex();
      int n = localLayoutParams1.getSpanSize();
      if ((this.mSpanCount > 1) && (localLayoutParams1.getSpanSize() == this.mSpanCount));
      for (boolean bool2 = true; ; bool2 = false)
      {
        paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(m, n, i, 1, bool2, false));
        return;
      }
    }
    int j = localLayoutParams1.getSpanIndex();
    int k = localLayoutParams1.getSpanSize();
    if ((this.mSpanCount > 1) && (localLayoutParams1.getSpanSize() == this.mSpanCount));
    for (boolean bool1 = true; ; bool1 = false)
    {
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, j, k, bool1, false));
      return;
    }
  }

  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }

  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }

  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }

  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }

  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }

  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (paramState.isPreLayout())
      cachePreLayoutSpanMapping();
    super.onLayoutChildren(paramRecycler, paramState);
    clearPreLayoutSpanMappingCache();
    if (!paramState.isPreLayout())
      this.mPendingSpanCountChange = false;
  }

  public void setSpanCount(int paramInt)
  {
    if (paramInt == this.mSpanCount)
      return;
    this.mPendingSpanCountChange = true;
    if (paramInt < 1)
      throw new IllegalArgumentException("Span count should be at least 1. Provided " + paramInt);
    this.mSpanCount = paramInt;
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }

  public void setSpanSizeLookup(SpanSizeLookup paramSpanSizeLookup)
  {
    this.mSpanSizeLookup = paramSpanSizeLookup;
  }

  public void setStackFromEnd(boolean paramBoolean)
  {
    if (paramBoolean)
      throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    super.setStackFromEnd(false);
  }

  public boolean supportsPredictiveItemAnimations()
  {
    return (this.mPendingSavedState == null) && (!this.mPendingSpanCountChange);
  }

  public static final class DefaultSpanSizeLookup extends GridLayoutManager.SpanSizeLookup
  {
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      return paramInt1 % paramInt2;
    }

    public int getSpanSize(int paramInt)
    {
      return 1;
    }
  }

  public static class LayoutParams extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    private int mSpanIndex = -1;
    private int mSpanSize = 0;

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

    public int getSpanIndex()
    {
      return this.mSpanIndex;
    }

    public int getSpanSize()
    {
      return this.mSpanSize;
    }
  }

  public static abstract class SpanSizeLookup
  {
    private boolean mCacheSpanIndices = false;
    final SparseIntArray mSpanIndexCache = new SparseIntArray();

    int findReferenceIndexFromCache(int paramInt)
    {
      int i = 0;
      int j = -1 + this.mSpanIndexCache.size();
      while (i <= j)
      {
        int m = i + j >>> 1;
        if (this.mSpanIndexCache.keyAt(m) < paramInt)
          i = m + 1;
        else
          j = m - 1;
      }
      int k = i - 1;
      if ((k >= 0) && (k < this.mSpanIndexCache.size()))
        return this.mSpanIndexCache.keyAt(k);
      return -1;
    }

    int getCachedSpanIndex(int paramInt1, int paramInt2)
    {
      int i;
      if (!this.mCacheSpanIndices)
        i = getSpanIndex(paramInt1, paramInt2);
      do
      {
        return i;
        i = this.mSpanIndexCache.get(paramInt1, -1);
      }
      while (i != -1);
      int j = getSpanIndex(paramInt1, paramInt2);
      this.mSpanIndexCache.put(paramInt1, j);
      return j;
    }

    public int getSpanGroupIndex(int paramInt1, int paramInt2)
    {
      int i = 0;
      int j = 0;
      int k = getSpanSize(paramInt1);
      int m = 0;
      if (m < paramInt1)
      {
        int n = getSpanSize(m);
        i += n;
        if (i == paramInt2)
        {
          i = 0;
          j++;
        }
        while (true)
        {
          m++;
          break;
          if (i > paramInt2)
          {
            i = n;
            j++;
          }
        }
      }
      if (i + k > paramInt2)
        j++;
      return j;
    }

    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      int i = getSpanSize(paramInt1);
      int j;
      if (i == paramInt2)
        j = 0;
      do
      {
        return j;
        boolean bool = this.mCacheSpanIndices;
        j = 0;
        int k = 0;
        if (bool)
        {
          int i1 = this.mSpanIndexCache.size();
          j = 0;
          k = 0;
          if (i1 > 0)
          {
            int i2 = findReferenceIndexFromCache(paramInt1);
            j = 0;
            k = 0;
            if (i2 >= 0)
            {
              j = this.mSpanIndexCache.get(i2) + getSpanSize(i2);
              k = i2 + 1;
            }
          }
        }
        int m = k;
        if (m < paramInt1)
        {
          int n = getSpanSize(m);
          j += n;
          if (j == paramInt2)
            j = 0;
          while (true)
          {
            m++;
            break;
            if (j > paramInt2)
              j = n;
          }
        }
      }
      while (j + i <= paramInt2);
      return 0;
    }

    public abstract int getSpanSize(int paramInt);

    public void invalidateSpanIndexCache()
    {
      this.mSpanIndexCache.clear();
    }

    public boolean isSpanIndexCacheEnabled()
    {
      return this.mCacheSpanIndices;
    }

    public void setSpanIndexCacheEnabled(boolean paramBoolean)
    {
      this.mCacheSpanIndices = paramBoolean;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.GridLayoutManager
 * JD-Core Version:    0.6.2
 */