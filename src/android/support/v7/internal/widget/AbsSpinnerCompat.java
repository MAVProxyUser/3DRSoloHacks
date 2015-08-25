package android.support.v7.internal.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.SpinnerAdapter;

abstract class AbsSpinnerCompat extends AdapterViewCompat<SpinnerAdapter>
{
  SpinnerAdapter mAdapter;
  private DataSetObserver mDataSetObserver;
  int mHeightMeasureSpec;
  final RecycleBin mRecycler = new RecycleBin();
  int mSelectionBottomPadding = 0;
  int mSelectionLeftPadding = 0;
  int mSelectionRightPadding = 0;
  int mSelectionTopPadding = 0;
  final Rect mSpinnerPadding = new Rect();
  private Rect mTouchFrame;
  int mWidthMeasureSpec;

  AbsSpinnerCompat(Context paramContext)
  {
    super(paramContext);
    initAbsSpinner();
  }

  AbsSpinnerCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  AbsSpinnerCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initAbsSpinner();
  }

  private void initAbsSpinner()
  {
    setFocusable(true);
    setWillNotDraw(false);
  }

  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new ViewGroup.LayoutParams(-1, -2);
  }

  public SpinnerAdapter getAdapter()
  {
    return this.mAdapter;
  }

  int getChildHeight(View paramView)
  {
    return paramView.getMeasuredHeight();
  }

  int getChildWidth(View paramView)
  {
    return paramView.getMeasuredWidth();
  }

  public int getCount()
  {
    return this.mItemCount;
  }

  public View getSelectedView()
  {
    if ((this.mItemCount > 0) && (this.mSelectedPosition >= 0))
      return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    return null;
  }

  abstract void layout(int paramInt, boolean paramBoolean);

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingTop();
    int m = getPaddingRight();
    int n = getPaddingBottom();
    Rect localRect1 = this.mSpinnerPadding;
    label66: label88: Rect localRect4;
    if (j > this.mSelectionLeftPadding)
    {
      localRect1.left = j;
      Rect localRect2 = this.mSpinnerPadding;
      if (k <= this.mSelectionTopPadding)
        break label435;
      localRect2.top = k;
      Rect localRect3 = this.mSpinnerPadding;
      if (m <= this.mSelectionRightPadding)
        break label444;
      localRect3.right = m;
      localRect4 = this.mSpinnerPadding;
      if (n <= this.mSelectionBottomPadding)
        break label453;
    }
    while (true)
    {
      localRect4.bottom = n;
      if (this.mDataChanged)
        handleDataChanged();
      int i1 = 1;
      int i2 = getSelectedItemPosition();
      int i3 = 0;
      int i4 = 0;
      if (i2 >= 0)
      {
        SpinnerAdapter localSpinnerAdapter = this.mAdapter;
        i3 = 0;
        i4 = 0;
        if (localSpinnerAdapter != null)
        {
          int i8 = this.mAdapter.getCount();
          i3 = 0;
          i4 = 0;
          if (i2 < i8)
          {
            View localView = this.mRecycler.get(i2);
            if (localView == null)
              localView = this.mAdapter.getView(i2, null, this);
            i3 = 0;
            i4 = 0;
            if (localView != null)
            {
              this.mRecycler.put(i2, localView);
              if (localView.getLayoutParams() == null)
              {
                this.mBlockLayoutRequests = true;
                localView.setLayoutParams(generateDefaultLayoutParams());
                this.mBlockLayoutRequests = false;
              }
              measureChild(localView, paramInt1, paramInt2);
              i3 = getChildHeight(localView) + this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
              i4 = getChildWidth(localView) + this.mSpinnerPadding.left + this.mSpinnerPadding.right;
              i1 = 0;
            }
          }
        }
      }
      if (i1 != 0)
      {
        i3 = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
        if (i == 0)
          i4 = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
      }
      int i5 = Math.max(i3, getSuggestedMinimumHeight());
      int i6 = Math.max(i4, getSuggestedMinimumWidth());
      int i7 = ViewCompat.resolveSizeAndState(i5, paramInt2, 0);
      setMeasuredDimension(ViewCompat.resolveSizeAndState(i6, paramInt1, 0), i7);
      this.mHeightMeasureSpec = paramInt2;
      this.mWidthMeasureSpec = paramInt1;
      return;
      j = this.mSelectionLeftPadding;
      break;
      label435: k = this.mSelectionTopPadding;
      break label66;
      label444: m = this.mSelectionRightPadding;
      break label88;
      label453: n = this.mSelectionBottomPadding;
    }
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (localSavedState.selectedId >= 0L)
    {
      this.mDataChanged = true;
      this.mNeedSync = true;
      this.mSyncRowId = localSavedState.selectedId;
      this.mSyncPosition = localSavedState.position;
      this.mSyncMode = 0;
      requestLayout();
    }
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.selectedId = getSelectedItemId();
    if (localSavedState.selectedId >= 0L)
    {
      localSavedState.position = getSelectedItemPosition();
      return localSavedState;
    }
    localSavedState.position = -1;
    return localSavedState;
  }

  public int pointToPosition(int paramInt1, int paramInt2)
  {
    Rect localRect = this.mTouchFrame;
    if (localRect == null)
    {
      this.mTouchFrame = new Rect();
      localRect = this.mTouchFrame;
    }
    for (int i = -1 + getChildCount(); i >= 0; i--)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        localView.getHitRect(localRect);
        if (localRect.contains(paramInt1, paramInt2))
          return i + this.mFirstPosition;
      }
    }
    return -1;
  }

  void recycleAllViews()
  {
    int i = getChildCount();
    RecycleBin localRecycleBin = this.mRecycler;
    int j = this.mFirstPosition;
    for (int k = 0; k < i; k++)
    {
      View localView = getChildAt(k);
      localRecycleBin.put(j + k, localView);
    }
  }

  public void requestLayout()
  {
    if (!this.mBlockLayoutRequests)
      super.requestLayout();
  }

  void resetList()
  {
    this.mDataChanged = false;
    this.mNeedSync = false;
    removeAllViewsInLayout();
    this.mOldSelectedPosition = -1;
    this.mOldSelectedRowId = -9223372036854775808L;
    setSelectedPositionInt(-1);
    setNextSelectedPositionInt(-1);
    invalidate();
  }

  public void setAdapter(SpinnerAdapter paramSpinnerAdapter)
  {
    int i = -1;
    if (this.mAdapter != null)
    {
      this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
      resetList();
    }
    this.mAdapter = paramSpinnerAdapter;
    this.mOldSelectedPosition = i;
    this.mOldSelectedRowId = -9223372036854775808L;
    if (this.mAdapter != null)
    {
      this.mOldItemCount = this.mItemCount;
      this.mItemCount = this.mAdapter.getCount();
      checkFocus();
      this.mDataSetObserver = new AdapterViewCompat.AdapterDataSetObserver(this);
      this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
      if (this.mItemCount > 0)
        i = 0;
      setSelectedPositionInt(i);
      setNextSelectedPositionInt(i);
      if (this.mItemCount == 0)
        checkSelectionChanged();
    }
    while (true)
    {
      requestLayout();
      return;
      checkFocus();
      resetList();
      checkSelectionChanged();
    }
  }

  public void setSelection(int paramInt)
  {
    setNextSelectedPositionInt(paramInt);
    requestLayout();
    invalidate();
  }

  public void setSelection(int paramInt, boolean paramBoolean)
  {
    if ((paramBoolean) && (this.mFirstPosition <= paramInt) && (paramInt <= -1 + (this.mFirstPosition + getChildCount())));
    for (boolean bool = true; ; bool = false)
    {
      setSelectionInt(paramInt, bool);
      return;
    }
  }

  void setSelectionInt(int paramInt, boolean paramBoolean)
  {
    if (paramInt != this.mOldSelectedPosition)
    {
      this.mBlockLayoutRequests = true;
      int i = paramInt - this.mSelectedPosition;
      setNextSelectedPositionInt(paramInt);
      layout(i, paramBoolean);
      this.mBlockLayoutRequests = false;
    }
  }

  class RecycleBin
  {
    private final SparseArray<View> mScrapHeap = new SparseArray();

    RecycleBin()
    {
    }

    void clear()
    {
      SparseArray localSparseArray = this.mScrapHeap;
      int i = localSparseArray.size();
      for (int j = 0; j < i; j++)
      {
        View localView = (View)localSparseArray.valueAt(j);
        if (localView != null)
          AbsSpinnerCompat.this.removeDetachedView(localView, true);
      }
      localSparseArray.clear();
    }

    View get(int paramInt)
    {
      View localView = (View)this.mScrapHeap.get(paramInt);
      if (localView != null)
        this.mScrapHeap.delete(paramInt);
      return localView;
    }

    public void put(int paramInt, View paramView)
    {
      this.mScrapHeap.put(paramInt, paramView);
    }
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public AbsSpinnerCompat.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new AbsSpinnerCompat.SavedState(paramAnonymousParcel);
      }

      public AbsSpinnerCompat.SavedState[] newArray(int paramAnonymousInt)
      {
        return new AbsSpinnerCompat.SavedState[paramAnonymousInt];
      }
    };
    int position;
    long selectedId;

    SavedState(Parcel paramParcel)
    {
      super();
      this.selectedId = paramParcel.readLong();
      this.position = paramParcel.readInt();
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " position=" + this.position + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeLong(this.selectedId);
      paramParcel.writeInt(this.position);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.AbsSpinnerCompat
 * JD-Core Version:    0.6.2
 */