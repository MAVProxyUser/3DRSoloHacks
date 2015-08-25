package com.o3dr.solo.android.widget.spinnerWheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import com.o3dr.solo.android.R.styleable;
import com.o3dr.solo.android.widget.spinnerWheel.adapters.WheelViewAdapter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractWheel extends View
{
  private static final boolean DEF_IS_CYCLIC = false;
  private static final int DEF_VISIBLE_ITEMS = 4;
  private static int itemID = -1;
  private final String LOG_TAG;
  private List<OnWheelChangedListener> changingListeners;
  private List<OnWheelClickedListener> clickingListeners;
  protected int mCurrentItemIdx;
  private DataSetObserver mDataObserver;
  protected int mFirstItemIdx;
  protected boolean mIsAllVisible;
  protected boolean mIsCyclic;
  protected boolean mIsScrollingPerformed;
  protected LinearLayout mItemsLayout;
  protected int mLayoutHeight;
  protected int mLayoutWidth;
  private WheelRecycler mRecycler;
  protected WheelScroller mScroller;
  protected int mScrollingOffset;
  protected WheelViewAdapter mViewAdapter;
  protected int mVisibleItems;
  private List<OnWheelScrollListener> scrollingListeners;

  public AbstractWheel(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    StringBuilder localStringBuilder = new StringBuilder().append(AbstractWheel.class.getName()).append(" #");
    int i = 1 + itemID;
    itemID = i;
    this.LOG_TAG = i;
    this.mCurrentItemIdx = 0;
    this.mRecycler = new WheelRecycler(this);
    this.changingListeners = new LinkedList();
    this.scrollingListeners = new LinkedList();
    this.clickingListeners = new LinkedList();
    initAttributes(paramAttributeSet, paramInt);
    initData(paramContext);
  }

  private boolean addItemView(int paramInt, boolean paramBoolean)
  {
    View localView = getItemView(paramInt);
    boolean bool = false;
    if (localView != null)
    {
      if (!paramBoolean)
        break label32;
      this.mItemsLayout.addView(localView, 0);
    }
    while (true)
    {
      bool = true;
      return bool;
      label32: this.mItemsLayout.addView(localView);
    }
  }

  private void doScroll(int paramInt)
  {
    this.mScrollingOffset = (paramInt + this.mScrollingOffset);
    int i = getItemDimension();
    int j = this.mScrollingOffset / i;
    int k = this.mCurrentItemIdx - j;
    int m = this.mViewAdapter.getItemsCount();
    int n = this.mScrollingOffset % i;
    if (Math.abs(n) <= i / 2)
      n = 0;
    int i1;
    if ((this.mIsCyclic) && (m > 0))
    {
      if (n > 0)
      {
        k--;
        j++;
      }
      while (k < 0)
      {
        k += m;
        continue;
        if (n < 0)
        {
          k++;
          j--;
        }
      }
      k %= m;
      i1 = this.mScrollingOffset;
      if (k == this.mCurrentItemIdx)
        break label268;
      setCurrentItem(k, false);
    }
    while (true)
    {
      int i2 = getBaseDimension();
      this.mScrollingOffset = (i1 - j * i);
      if (this.mScrollingOffset > i2)
        this.mScrollingOffset = (i2 + this.mScrollingOffset % i2);
      return;
      if (k < 0)
      {
        j = this.mCurrentItemIdx;
        k = 0;
        break;
      }
      if (k >= m)
      {
        j = 1 + (this.mCurrentItemIdx - m);
        k = m - 1;
        break;
      }
      if ((k > 0) && (n > 0))
      {
        k--;
        j++;
        break;
      }
      if ((k >= m - 1) || (n >= 0))
        break;
      k++;
      j--;
      break;
      label268: invalidate();
    }
  }

  private View getItemView(int paramInt)
  {
    if ((this.mViewAdapter == null) || (this.mViewAdapter.getItemsCount() == 0))
      return null;
    int i = this.mViewAdapter.getItemsCount();
    if (!isValidItemIndex(paramInt))
      return this.mViewAdapter.getEmptyItem(this.mRecycler.getEmptyItem(), this.mItemsLayout);
    while (paramInt < 0)
      paramInt += i;
    int j = paramInt % i;
    return this.mViewAdapter.getItem(j, this.mRecycler.getItem(), this.mItemsLayout);
  }

  private ItemsRange getItemsRange()
  {
    if (this.mIsAllVisible)
    {
      int n = getBaseDimension();
      int i1 = getItemDimension();
      if (i1 != 0)
        this.mVisibleItems = (1 + n / i1);
    }
    int i = this.mCurrentItemIdx - this.mVisibleItems / 2;
    int j = i + this.mVisibleItems;
    int k;
    int m;
    if (this.mVisibleItems % 2 == 0)
    {
      k = 0;
      m = j - k;
      if (this.mScrollingOffset != 0)
      {
        if (this.mScrollingOffset <= 0)
          break label130;
        i--;
      }
      label87: if (!isCyclic())
      {
        if (i < 0)
          i = 0;
        if (this.mViewAdapter != null)
          break label136;
        m = 0;
      }
    }
    while (true)
    {
      return new ItemsRange(i, 1 + (m - i));
      k = 1;
      break;
      label130: m++;
      break label87;
      label136: if (m > this.mViewAdapter.getItemsCount())
        m = this.mViewAdapter.getItemsCount();
    }
  }

  public void addChangingListener(OnWheelChangedListener paramOnWheelChangedListener)
  {
    this.changingListeners.add(paramOnWheelChangedListener);
  }

  public void addClickingListener(OnWheelClickedListener paramOnWheelClickedListener)
  {
    this.clickingListeners.add(paramOnWheelClickedListener);
  }

  public void addScrollingListener(OnWheelScrollListener paramOnWheelScrollListener)
  {
    this.scrollingListeners.add(paramOnWheelScrollListener);
  }

  protected abstract void createItemsLayout();

  protected abstract WheelScroller createScroller(WheelScroller.ScrollingListener paramScrollingListener);

  protected abstract void doItemsLayout();

  protected abstract int getBaseDimension();

  public int getCurrentItem()
  {
    return this.mCurrentItemIdx;
  }

  protected abstract int getItemDimension();

  protected abstract float getMotionEventPosition(MotionEvent paramMotionEvent);

  public WheelViewAdapter getViewAdapter()
  {
    return this.mViewAdapter;
  }

  public int getVisibleItems()
  {
    return this.mVisibleItems;
  }

  protected void initAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.AbstractWheelView, paramInt, 0);
    this.mVisibleItems = localTypedArray.getInt(0, 4);
    this.mIsAllVisible = localTypedArray.getBoolean(1, false);
    this.mIsCyclic = localTypedArray.getBoolean(8, false);
    localTypedArray.recycle();
  }

  protected void initData(Context paramContext)
  {
    this.mDataObserver = new DataSetObserver()
    {
      public void onChanged()
      {
        AbstractWheel.this.invalidateItemsLayout(false);
      }

      public void onInvalidated()
      {
        AbstractWheel.this.invalidateItemsLayout(true);
      }
    };
    this.mScroller = createScroller(new WheelScroller.ScrollingListener()
    {
      public void onFinished()
      {
        if (AbstractWheel.this.mIsScrollingPerformed)
        {
          AbstractWheel.this.notifyScrollingListenersAboutEnd();
          AbstractWheel.this.mIsScrollingPerformed = false;
          AbstractWheel.this.onScrollFinished();
        }
        AbstractWheel.this.mScrollingOffset = 0;
        AbstractWheel.this.invalidate();
      }

      public void onJustify()
      {
        if (Math.abs(AbstractWheel.this.mScrollingOffset) > 1)
          AbstractWheel.this.mScroller.scroll(AbstractWheel.this.mScrollingOffset, 0);
      }

      public void onScroll(int paramAnonymousInt)
      {
        AbstractWheel.this.doScroll(paramAnonymousInt);
        int i = AbstractWheel.this.getBaseDimension();
        if (AbstractWheel.this.mScrollingOffset > i)
        {
          AbstractWheel.this.mScrollingOffset = i;
          AbstractWheel.this.mScroller.stopScrolling();
        }
        while (AbstractWheel.this.mScrollingOffset >= -i)
          return;
        AbstractWheel.this.mScrollingOffset = (-i);
        AbstractWheel.this.mScroller.stopScrolling();
      }

      public void onStarted()
      {
        AbstractWheel.this.mIsScrollingPerformed = true;
        AbstractWheel.this.notifyScrollingListenersAboutStart();
        AbstractWheel.this.onScrollStarted();
      }

      public void onTouch()
      {
        AbstractWheel.this.onScrollTouched();
      }

      public void onTouchUp()
      {
        if (!AbstractWheel.this.mIsScrollingPerformed)
          AbstractWheel.this.onScrollTouchedUp();
      }
    });
  }

  public void invalidateItemsLayout(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mRecycler.clearAll();
      if (this.mItemsLayout != null)
        this.mItemsLayout.removeAllViews();
      this.mScrollingOffset = 0;
    }
    while (true)
    {
      invalidate();
      return;
      if (this.mItemsLayout != null)
        this.mRecycler.recycleItems(this.mItemsLayout, this.mFirstItemIdx, new ItemsRange());
    }
  }

  public boolean isCyclic()
  {
    return this.mIsCyclic;
  }

  protected boolean isValidItemIndex(int paramInt)
  {
    return (this.mViewAdapter != null) && (this.mViewAdapter.getItemsCount() > 0) && ((this.mIsCyclic) || ((paramInt >= 0) && (paramInt < this.mViewAdapter.getItemsCount())));
  }

  protected void notifyChangingListeners(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.changingListeners.iterator();
    while (localIterator.hasNext())
      ((OnWheelChangedListener)localIterator.next()).onChanged(this, paramInt1, paramInt2);
  }

  protected void notifyClickListenersAboutClick(int paramInt, boolean paramBoolean)
  {
    Iterator localIterator = this.clickingListeners.iterator();
    while (localIterator.hasNext())
      ((OnWheelClickedListener)localIterator.next()).onItemClicked(this, paramInt, paramBoolean);
  }

  protected void notifyScrollingListenersAboutEnd()
  {
    Iterator localIterator = this.scrollingListeners.iterator();
    while (localIterator.hasNext())
      ((OnWheelScrollListener)localIterator.next()).onScrollingFinished(this);
  }

  protected void notifyScrollingListenersAboutStart()
  {
    Iterator localIterator = this.scrollingListeners.iterator();
    while (localIterator.hasNext())
      ((OnWheelScrollListener)localIterator.next()).onScrollingStarted(this);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramBoolean)
    {
      int i = paramInt3 - paramInt1;
      int j = paramInt4 - paramInt2;
      doItemsLayout();
      if ((this.mLayoutWidth != i) || (this.mLayoutHeight != j))
        recreateAssets(getMeasuredWidth(), getMeasuredHeight());
      this.mLayoutWidth = i;
      this.mLayoutHeight = j;
    }
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mCurrentItemIdx = localSavedState.currentItem;
    postDelayed(new Runnable()
    {
      public void run()
      {
        AbstractWheel.this.invalidateItemsLayout(false);
      }
    }
    , 100L);
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.currentItem = getCurrentItem();
    return localSavedState;
  }

  protected void onScrollFinished()
  {
  }

  protected void onScrollStarted()
  {
  }

  protected void onScrollTouched()
  {
  }

  protected void onScrollTouchedUp()
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if ((!isEnabled()) || (getViewAdapter() == null))
      return bool;
    switch (paramMotionEvent.getAction())
    {
    case 0:
    case 2:
    default:
    case 1:
    }
    int i;
    int j;
    label96: int k;
    do
    {
      do
        return this.mScroller.onTouchEvent(getParent(), paramMotionEvent);
      while (this.mIsScrollingPerformed);
      i = (int)getMotionEventPosition(paramMotionEvent) - getBaseDimension() / 2;
      if (i <= 0)
        break;
      j = i + getItemDimension() / 2;
      k = j / getItemDimension();
    }
    while (!isValidItemIndex(k + this.mCurrentItemIdx));
    int m = k + this.mCurrentItemIdx;
    if (k == 0);
    while (true)
    {
      notifyClickListenersAboutClick(m, bool);
      break;
      j = i - getItemDimension() / 2;
      break label96;
      bool = false;
    }
  }

  protected boolean rebuildItems()
  {
    ItemsRange localItemsRange = getItemsRange();
    boolean bool;
    label47: label78: int k;
    if (this.mItemsLayout != null)
    {
      int m = this.mRecycler.recycleItems(this.mItemsLayout, this.mFirstItemIdx, localItemsRange);
      if (this.mFirstItemIdx != m)
      {
        bool = true;
        this.mFirstItemIdx = m;
        if (!bool)
        {
          if ((this.mFirstItemIdx == localItemsRange.getFirst()) && (this.mItemsLayout.getChildCount() == localItemsRange.getCount()))
            break label198;
          bool = true;
        }
        if ((this.mFirstItemIdx <= localItemsRange.getFirst()) || (this.mFirstItemIdx > localItemsRange.getLast()))
          break label215;
        k = -1 + this.mFirstItemIdx;
        label108: if ((k >= localItemsRange.getFirst()) && (addItemView(k, true)))
          break label203;
      }
    }
    int i;
    while (true)
    {
      i = this.mFirstItemIdx;
      for (int j = this.mItemsLayout.getChildCount(); j < localItemsRange.getCount(); j++)
        if ((!addItemView(j + this.mFirstItemIdx, false)) && (this.mItemsLayout.getChildCount() == 0))
          i++;
      bool = false;
      break;
      createItemsLayout();
      bool = true;
      break label47;
      label198: bool = false;
      break label78;
      label203: this.mFirstItemIdx = k;
      k--;
      break label108;
      label215: this.mFirstItemIdx = localItemsRange.getFirst();
    }
    this.mFirstItemIdx = i;
    return bool;
  }

  protected abstract void recreateAssets(int paramInt1, int paramInt2);

  public void removeChangingListener(OnWheelChangedListener paramOnWheelChangedListener)
  {
    this.changingListeners.remove(paramOnWheelChangedListener);
  }

  public void removeClickingListener(OnWheelClickedListener paramOnWheelClickedListener)
  {
    this.clickingListeners.remove(paramOnWheelClickedListener);
  }

  public void removeScrollingListener(OnWheelScrollListener paramOnWheelScrollListener)
  {
    this.scrollingListeners.remove(paramOnWheelScrollListener);
  }

  public void scroll(int paramInt1, int paramInt2)
  {
    int i = paramInt1 * getItemDimension() - this.mScrollingOffset;
    onScrollTouched();
    this.mScroller.scroll(i, paramInt2);
  }

  public void setAllItemsVisible(boolean paramBoolean)
  {
    this.mIsAllVisible = paramBoolean;
    invalidateItemsLayout(false);
  }

  public void setCurrentItem(int paramInt)
  {
    setCurrentItem(paramInt, false);
  }

  public void setCurrentItem(int paramInt, boolean paramBoolean)
  {
    if ((this.mViewAdapter == null) || (this.mViewAdapter.getItemsCount() == 0));
    int i;
    do
    {
      do
      {
        return;
        i = this.mViewAdapter.getItemsCount();
        if ((paramInt >= 0) && (paramInt < i))
          break;
      }
      while (!this.mIsCyclic);
      while (paramInt < 0)
        paramInt += i;
      paramInt %= i;
    }
    while (paramInt == this.mCurrentItemIdx);
    if (paramBoolean)
    {
      int k = paramInt - this.mCurrentItemIdx;
      int m;
      if (this.mIsCyclic)
      {
        m = i + Math.min(paramInt, this.mCurrentItemIdx) - Math.max(paramInt, this.mCurrentItemIdx);
        if (m < Math.abs(k))
          if (k >= 0)
            break label136;
      }
      label136: for (k = m; ; k = -m)
      {
        scroll(k, 0);
        return;
      }
    }
    this.mScrollingOffset = 0;
    int j = this.mCurrentItemIdx;
    this.mCurrentItemIdx = paramInt;
    notifyChangingListeners(j, this.mCurrentItemIdx);
    invalidate();
  }

  public void setCyclic(boolean paramBoolean)
  {
    this.mIsCyclic = paramBoolean;
    invalidateItemsLayout(false);
  }

  public void setInterpolator(Interpolator paramInterpolator)
  {
    this.mScroller.setInterpolator(paramInterpolator);
  }

  public void setViewAdapter(WheelViewAdapter paramWheelViewAdapter)
  {
    if (this.mViewAdapter != null)
      this.mViewAdapter.unregisterDataSetObserver(this.mDataObserver);
    this.mViewAdapter = paramWheelViewAdapter;
    this.mCurrentItemIdx = 0;
    if (this.mViewAdapter != null)
      this.mViewAdapter.registerDataSetObserver(this.mDataObserver);
    invalidateItemsLayout(true);
  }

  public void setVisibleItems(int paramInt)
  {
    this.mVisibleItems = paramInt;
  }

  public void stopScrolling()
  {
    this.mScroller.stopScrolling();
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public AbstractWheel.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new AbstractWheel.SavedState(paramAnonymousParcel, null);
      }

      public AbstractWheel.SavedState[] newArray(int paramAnonymousInt)
      {
        return new AbstractWheel.SavedState[paramAnonymousInt];
      }
    };
    int currentItem;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.currentItem = paramParcel.readInt();
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.currentItem);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.AbstractWheel
 * JD-Core Version:    0.6.2
 */