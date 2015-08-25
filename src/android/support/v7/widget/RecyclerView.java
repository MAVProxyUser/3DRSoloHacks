package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.recyclerview.R.styleable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup
  implements ScrollingView, NestedScrollingChild
{
  private static final boolean DEBUG = false;
  private static final boolean DISPATCH_TEMP_DETACH = false;
  private static final boolean FORCE_INVALIDATE_DISPLAY_LIST = false;
  public static final int HORIZONTAL = 0;
  private static final int INVALID_POINTER = -1;
  public static final int INVALID_TYPE = -1;
  private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
  private static final int MAX_SCROLL_DURATION = 2000;
  public static final long NO_ID = -1L;
  public static final int NO_POSITION = -1;
  public static final int SCROLL_STATE_DRAGGING = 1;
  public static final int SCROLL_STATE_IDLE = 0;
  public static final int SCROLL_STATE_SETTLING = 2;
  private static final String TAG = "RecyclerView";
  public static final int TOUCH_SLOP_DEFAULT = 0;
  public static final int TOUCH_SLOP_PAGING = 1;
  private static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
  private static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
  private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
  private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
  private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
  private static final String TRACE_SCROLL_TAG = "RV Scroll";
  public static final int VERTICAL = 1;
  private static final Interpolator sQuinticInterpolator;
  private RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
  private final AccessibilityManager mAccessibilityManager;
  private OnItemTouchListener mActiveOnItemTouchListener;
  private Adapter mAdapter;
  AdapterHelper mAdapterHelper;
  private boolean mAdapterUpdateDuringMeasure;
  private EdgeEffectCompat mBottomGlow;
  private ChildDrawingOrderCallback mChildDrawingOrderCallback;
  ChildHelper mChildHelper;
  private boolean mClipToPadding;
  private boolean mDataSetHasChangedAfterLayout = false;
  private boolean mEatRequestLayout;
  private int mEatenAccessibilityChangeFlags;
  private boolean mFirstLayoutComplete;
  private boolean mHasFixedSize;
  private int mInitialTouchX;
  private int mInitialTouchY;
  private boolean mIsAttached;
  ItemAnimator mItemAnimator = new DefaultItemAnimator();
  private RecyclerView.ItemAnimator.ItemAnimatorListener mItemAnimatorListener = new ItemAnimatorRestoreListener(null);
  private Runnable mItemAnimatorRunner = new Runnable()
  {
    public void run()
    {
      if (RecyclerView.this.mItemAnimator != null)
        RecyclerView.this.mItemAnimator.runPendingAnimations();
      RecyclerView.access$502(RecyclerView.this, false);
    }
  };
  private final ArrayList<ItemDecoration> mItemDecorations = new ArrayList();
  boolean mItemsAddedOrRemoved = false;
  boolean mItemsChanged = false;
  private int mLastTouchX;
  private int mLastTouchY;
  private LayoutManager mLayout;
  private int mLayoutOrScrollCounter = 0;
  private boolean mLayoutRequestEaten;
  private EdgeEffectCompat mLeftGlow;
  private final int mMaxFlingVelocity;
  private final int mMinFlingVelocity;
  private final int[] mMinMaxLayoutPositions = new int[2];
  private final int[] mNestedOffsets = new int[2];
  private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver(null);
  private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
  private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList();
  private SavedState mPendingSavedState;
  private final boolean mPostUpdatesOnAnimation;
  private boolean mPostedAnimatorRunner = false;
  final Recycler mRecycler = new Recycler();
  private RecyclerListener mRecyclerListener;
  private EdgeEffectCompat mRightGlow;
  private final int[] mScrollConsumed = new int[2];
  private float mScrollFactor = 1.4E-45F;
  private OnScrollListener mScrollListener;
  private List<OnScrollListener> mScrollListeners;
  private final int[] mScrollOffset = new int[2];
  private int mScrollPointerId = -1;
  private int mScrollState = 0;
  private final NestedScrollingChildHelper mScrollingChildHelper;
  final State mState = new State();
  private final Rect mTempRect = new Rect();
  private EdgeEffectCompat mTopGlow;
  private int mTouchSlop;
  private final Runnable mUpdateChildViewsRunnable = new Runnable()
  {
    public void run()
    {
      if (!RecyclerView.this.mFirstLayoutComplete);
      do
      {
        return;
        if (RecyclerView.this.mDataSetHasChangedAfterLayout)
        {
          TraceCompat.beginSection("RV FullInvalidate");
          RecyclerView.this.dispatchLayout();
          TraceCompat.endSection();
          return;
        }
      }
      while (!RecyclerView.this.mAdapterHelper.hasPendingUpdates());
      TraceCompat.beginSection("RV PartialInvalidate");
      RecyclerView.this.eatRequestLayout();
      RecyclerView.this.mAdapterHelper.preProcess();
      if (!RecyclerView.this.mLayoutRequestEaten)
        RecyclerView.this.rebindUpdatedViewHolders();
      RecyclerView.this.resumeRequestLayout(true);
      TraceCompat.endSection();
    }
  };
  private VelocityTracker mVelocityTracker;
  private final ViewFlinger mViewFlinger = new ViewFlinger();

  static
  {
    if ((Build.VERSION.SDK_INT == 18) || (Build.VERSION.SDK_INT == 19) || (Build.VERSION.SDK_INT == 20));
    for (boolean bool = true; ; bool = false)
    {
      FORCE_INVALIDATE_DISPLAY_LIST = bool;
      Class[] arrayOfClass = new Class[4];
      arrayOfClass[0] = Context.class;
      arrayOfClass[1] = AttributeSet.class;
      arrayOfClass[2] = Integer.TYPE;
      arrayOfClass[3] = Integer.TYPE;
      LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = arrayOfClass;
      sQuinticInterpolator = new Interpolator()
      {
        public float getInterpolation(float paramAnonymousFloat)
        {
          float f = paramAnonymousFloat - 1.0F;
          return 1.0F + f * (f * (f * (f * f)));
        }
      };
      return;
    }
  }

  public RecyclerView(Context paramContext)
  {
    this(paramContext, null);
  }

  public RecyclerView(Context paramContext, @Nullable AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public RecyclerView(Context paramContext, @Nullable AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setFocusableInTouchMode(true);
    if (Build.VERSION.SDK_INT >= 16);
    for (boolean bool1 = true; ; bool1 = false)
    {
      this.mPostUpdatesOnAnimation = bool1;
      ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
      this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
      this.mMinFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
      this.mMaxFlingVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
      int i = ViewCompat.getOverScrollMode(this);
      boolean bool2 = false;
      if (i == 2)
        bool2 = true;
      setWillNotDraw(bool2);
      this.mItemAnimator.setListener(this.mItemAnimatorListener);
      initAdapterManager();
      initChildrenHelper();
      if (ViewCompat.getImportantForAccessibility(this) == 0)
        ViewCompat.setImportantForAccessibility(this, 1);
      this.mAccessibilityManager = ((AccessibilityManager)getContext().getSystemService("accessibility"));
      setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
      if (paramAttributeSet != null)
      {
        TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RecyclerView, paramInt, 0);
        String str = localTypedArray.getString(R.styleable.RecyclerView_layoutManager);
        localTypedArray.recycle();
        createLayoutManager(paramContext, str, paramAttributeSet, paramInt, 0);
      }
      this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
      setNestedScrollingEnabled(true);
      return;
    }
  }

  private void addAnimatingView(ViewHolder paramViewHolder)
  {
    View localView = paramViewHolder.itemView;
    if (localView.getParent() == this);
    for (int i = 1; ; i = 0)
    {
      this.mRecycler.unscrapView(getChildViewHolder(localView));
      if (!paramViewHolder.isTmpDetached())
        break;
      this.mChildHelper.attachViewToParent(localView, -1, localView.getLayoutParams(), true);
      return;
    }
    if (i == 0)
    {
      this.mChildHelper.addView(localView, true);
      return;
    }
    this.mChildHelper.hide(localView);
  }

  private void animateAppearance(ViewHolder paramViewHolder, Rect paramRect, int paramInt1, int paramInt2)
  {
    if ((paramRect != null) && ((paramRect.left != paramInt1) || (paramRect.top != paramInt2)))
    {
      paramViewHolder.setIsRecyclable(false);
      if (this.mItemAnimator.animateMove(paramViewHolder, paramRect.left, paramRect.top, paramInt1, paramInt2))
        postAnimationRunner();
    }
    do
    {
      return;
      paramViewHolder.setIsRecyclable(false);
    }
    while (!this.mItemAnimator.animateAdd(paramViewHolder));
    postAnimationRunner();
  }

  private void animateChange(ViewHolder paramViewHolder1, ViewHolder paramViewHolder2)
  {
    paramViewHolder1.setIsRecyclable(false);
    addAnimatingView(paramViewHolder1);
    paramViewHolder1.mShadowedHolder = paramViewHolder2;
    this.mRecycler.unscrapView(paramViewHolder1);
    int i = paramViewHolder1.itemView.getLeft();
    int j = paramViewHolder1.itemView.getTop();
    int k;
    int m;
    if ((paramViewHolder2 == null) || (paramViewHolder2.shouldIgnore()))
    {
      k = i;
      m = j;
    }
    while (true)
    {
      if (this.mItemAnimator.animateChange(paramViewHolder1, paramViewHolder2, i, j, k, m))
        postAnimationRunner();
      return;
      k = paramViewHolder2.itemView.getLeft();
      m = paramViewHolder2.itemView.getTop();
      paramViewHolder2.setIsRecyclable(false);
      paramViewHolder2.mShadowingHolder = paramViewHolder1;
    }
  }

  private void animateDisappearance(ItemHolderInfo paramItemHolderInfo)
  {
    View localView = paramItemHolderInfo.holder.itemView;
    addAnimatingView(paramItemHolderInfo.holder);
    int i = paramItemHolderInfo.left;
    int j = paramItemHolderInfo.top;
    int k = localView.getLeft();
    int m = localView.getTop();
    if ((i != k) || (j != m))
    {
      paramItemHolderInfo.holder.setIsRecyclable(false);
      localView.layout(k, m, k + localView.getWidth(), m + localView.getHeight());
      if (this.mItemAnimator.animateMove(paramItemHolderInfo.holder, i, j, k, m))
        postAnimationRunner();
    }
    do
    {
      return;
      paramItemHolderInfo.holder.setIsRecyclable(false);
    }
    while (!this.mItemAnimator.animateRemove(paramItemHolderInfo.holder));
    postAnimationRunner();
  }

  private void cancelTouch()
  {
    if (this.mVelocityTracker != null)
      this.mVelocityTracker.clear();
    stopNestedScroll();
    releaseGlows();
    setScrollState(0);
  }

  private void considerReleasingGlowsOnScroll(int paramInt1, int paramInt2)
  {
    EdgeEffectCompat localEdgeEffectCompat = this.mLeftGlow;
    boolean bool1 = false;
    if (localEdgeEffectCompat != null)
    {
      boolean bool2 = this.mLeftGlow.isFinished();
      bool1 = false;
      if (!bool2)
      {
        bool1 = false;
        if (paramInt1 > 0)
          bool1 = this.mLeftGlow.onRelease();
      }
    }
    if ((this.mRightGlow != null) && (!this.mRightGlow.isFinished()) && (paramInt1 < 0))
      bool1 |= this.mRightGlow.onRelease();
    if ((this.mTopGlow != null) && (!this.mTopGlow.isFinished()) && (paramInt2 > 0))
      bool1 |= this.mTopGlow.onRelease();
    if ((this.mBottomGlow != null) && (!this.mBottomGlow.isFinished()) && (paramInt2 < 0))
      bool1 |= this.mBottomGlow.onRelease();
    if (bool1)
      ViewCompat.postInvalidateOnAnimation(this);
  }

  private void consumePendingUpdateOperations()
  {
    this.mUpdateChildViewsRunnable.run();
  }

  private void createLayoutManager(Context paramContext, String paramString, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    if (paramString != null)
    {
      String str1 = paramString.trim();
      if (str1.length() != 0)
      {
        String str2 = getFullClassName(paramContext, str1);
        try
        {
          Object localObject1;
          if (isInEditMode())
            localObject1 = getClass().getClassLoader();
          while (true)
          {
            Class localClass = ((ClassLoader)localObject1).loadClass(str2).asSubclass(LayoutManager.class);
            try
            {
              localObject2 = localClass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
              Object[] arrayOfObject2 = new Object[4];
              arrayOfObject2[0] = paramContext;
              arrayOfObject2[1] = paramAttributeSet;
              arrayOfObject2[2] = Integer.valueOf(paramInt1);
              arrayOfObject2[3] = Integer.valueOf(paramInt2);
              arrayOfObject1 = arrayOfObject2;
              ((Constructor)localObject2).setAccessible(true);
              setLayoutManager((LayoutManager)((Constructor)localObject2).newInstance(arrayOfObject1));
              return;
              ClassLoader localClassLoader = paramContext.getClassLoader();
              localObject1 = localClassLoader;
            }
            catch (NoSuchMethodException localNoSuchMethodException1)
            {
              try
              {
                Constructor localConstructor = localClass.getConstructor(new Class[0]);
                Object localObject2 = localConstructor;
                Object[] arrayOfObject1 = null;
              }
              catch (NoSuchMethodException localNoSuchMethodException2)
              {
                localNoSuchMethodException2.initCause(localNoSuchMethodException1);
                throw new IllegalStateException(paramAttributeSet.getPositionDescription() + ": Error creating LayoutManager " + str2, localNoSuchMethodException2);
              }
            }
          }
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
          throw new IllegalStateException(paramAttributeSet.getPositionDescription() + ": Unable to find LayoutManager " + str2, localClassNotFoundException);
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          throw new IllegalStateException(paramAttributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str2, localInvocationTargetException);
        }
        catch (InstantiationException localInstantiationException)
        {
          throw new IllegalStateException(paramAttributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str2, localInstantiationException);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          throw new IllegalStateException(paramAttributeSet.getPositionDescription() + ": Cannot access non-public constructor " + str2, localIllegalAccessException);
        }
        catch (ClassCastException localClassCastException)
        {
          throw new IllegalStateException(paramAttributeSet.getPositionDescription() + ": Class is not a LayoutManager " + str2, localClassCastException);
        }
      }
    }
  }

  private void defaultOnMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    int n;
    switch (i)
    {
    default:
      n = ViewCompat.getMinimumWidth(this);
      switch (j)
      {
      default:
      case 1073741824:
      case -2147483648:
      }
      break;
    case 1073741824:
    case -2147483648:
    }
    for (int i1 = ViewCompat.getMinimumHeight(this); ; i1 = m)
    {
      setMeasuredDimension(n, i1);
      return;
      n = k;
      break;
    }
  }

  private boolean didChildRangeChange(int paramInt1, int paramInt2)
  {
    int i = this.mChildHelper.getChildCount();
    boolean bool;
    if (i == 0)
      if (paramInt1 == 0)
      {
        bool = false;
        if (paramInt2 == 0);
      }
      else
      {
        bool = true;
      }
    int j;
    do
    {
      return bool;
      j = 0;
      bool = false;
    }
    while (j >= i);
    ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(j));
    if (localViewHolder.shouldIgnore());
    int k;
    do
    {
      j++;
      break;
      k = localViewHolder.getLayoutPosition();
    }
    while ((k >= paramInt1) && (k <= paramInt2));
    return true;
  }

  private void dispatchChildAttached(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    onChildAttachedToWindow(paramView);
    if ((this.mAdapter != null) && (localViewHolder != null))
      this.mAdapter.onViewAttachedToWindow(localViewHolder);
    if (this.mOnChildAttachStateListeners != null)
      for (int i = -1 + this.mOnChildAttachStateListeners.size(); i >= 0; i--)
        ((OnChildAttachStateChangeListener)this.mOnChildAttachStateListeners.get(i)).onChildViewAttachedToWindow(paramView);
  }

  private void dispatchChildDetached(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    onChildDetachedFromWindow(paramView);
    if ((this.mAdapter != null) && (localViewHolder != null))
      this.mAdapter.onViewDetachedFromWindow(localViewHolder);
    if (this.mOnChildAttachStateListeners != null)
      for (int i = -1 + this.mOnChildAttachStateListeners.size(); i >= 0; i--)
        ((OnChildAttachStateChangeListener)this.mOnChildAttachStateListeners.get(i)).onChildViewDetachedFromWindow(paramView);
  }

  private void dispatchContentChangedIfNecessary()
  {
    int i = this.mEatenAccessibilityChangeFlags;
    this.mEatenAccessibilityChangeFlags = 0;
    if ((i != 0) && (this.mAccessibilityManager != null) && (this.mAccessibilityManager.isEnabled()))
    {
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain();
      localAccessibilityEvent.setEventType(2048);
      AccessibilityEventCompat.setContentChangeTypes(localAccessibilityEvent, i);
      sendAccessibilityEventUnchecked(localAccessibilityEvent);
    }
  }

  private boolean dispatchOnItemTouch(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    int j;
    if (this.mActiveOnItemTouchListener != null)
    {
      if (i == 0)
        this.mActiveOnItemTouchListener = null;
    }
    else
    {
      if (i == 0)
        break label110;
      j = this.mOnItemTouchListeners.size();
    }
    for (int k = 0; k < j; k++)
    {
      OnItemTouchListener localOnItemTouchListener = (OnItemTouchListener)this.mOnItemTouchListeners.get(k);
      if (localOnItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent))
      {
        this.mActiveOnItemTouchListener = localOnItemTouchListener;
        do
        {
          return true;
          this.mActiveOnItemTouchListener.onTouchEvent(this, paramMotionEvent);
        }
        while ((i != 3) && (i != 1));
        this.mActiveOnItemTouchListener = null;
        return true;
      }
    }
    label110: return false;
  }

  private boolean dispatchOnItemTouchIntercept(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((i == 3) || (i == 0))
      this.mActiveOnItemTouchListener = null;
    int j = this.mOnItemTouchListeners.size();
    for (int k = 0; k < j; k++)
    {
      OnItemTouchListener localOnItemTouchListener = (OnItemTouchListener)this.mOnItemTouchListeners.get(k);
      if ((localOnItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent)) && (i != 3))
      {
        this.mActiveOnItemTouchListener = localOnItemTouchListener;
        return true;
      }
    }
    return false;
  }

  private void findMinMaxChildLayoutPositions(int[] paramArrayOfInt)
  {
    int i = this.mChildHelper.getChildCount();
    if (i == 0)
    {
      paramArrayOfInt[0] = 0;
      paramArrayOfInt[1] = 0;
      return;
    }
    int j = 2147483647;
    int k = -2147483648;
    int m = 0;
    if (m < i)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(m));
      if (localViewHolder.shouldIgnore());
      while (true)
      {
        m++;
        break;
        int n = localViewHolder.getLayoutPosition();
        if (n < j)
          j = n;
        if (n > k)
          k = n;
      }
    }
    paramArrayOfInt[0] = j;
    paramArrayOfInt[1] = k;
  }

  private int getAdapterPositionFor(ViewHolder paramViewHolder)
  {
    if ((paramViewHolder.hasAnyOfTheFlags(524)) || (!paramViewHolder.isBound()))
      return -1;
    return this.mAdapterHelper.applyPendingUpdatesToPosition(paramViewHolder.mPosition);
  }

  static ViewHolder getChildViewHolderInt(View paramView)
  {
    if (paramView == null)
      return null;
    return ((LayoutParams)paramView.getLayoutParams()).mViewHolder;
  }

  private String getFullClassName(Context paramContext, String paramString)
  {
    if (paramString.charAt(0) == '.')
      paramString = paramContext.getPackageName() + paramString;
    while (paramString.contains("."))
      return paramString;
    return RecyclerView.class.getPackage().getName() + '.' + paramString;
  }

  private float getScrollFactor()
  {
    if (this.mScrollFactor == 1.4E-45F)
    {
      TypedValue localTypedValue = new TypedValue();
      if (getContext().getTheme().resolveAttribute(16842829, localTypedValue, true))
        this.mScrollFactor = localTypedValue.getDimension(getContext().getResources().getDisplayMetrics());
    }
    else
    {
      return this.mScrollFactor;
    }
    return 0.0F;
  }

  private void initChildrenHelper()
  {
    this.mChildHelper = new ChildHelper(new ChildHelper.Callback()
    {
      public void addView(View paramAnonymousView, int paramAnonymousInt)
      {
        RecyclerView.this.addView(paramAnonymousView, paramAnonymousInt);
        RecyclerView.this.dispatchChildAttached(paramAnonymousView);
      }

      public void attachViewToParent(View paramAnonymousView, int paramAnonymousInt, ViewGroup.LayoutParams paramAnonymousLayoutParams)
      {
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (localViewHolder != null)
        {
          if ((!localViewHolder.isTmpDetached()) && (!localViewHolder.shouldIgnore()))
            throw new IllegalArgumentException("Called attach on a child which is not detached: " + localViewHolder);
          localViewHolder.clearTmpDetachFlag();
        }
        RecyclerView.this.attachViewToParent(paramAnonymousView, paramAnonymousInt, paramAnonymousLayoutParams);
      }

      public void detachViewFromParent(int paramAnonymousInt)
      {
        View localView = getChildAt(paramAnonymousInt);
        if (localView != null)
        {
          RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
          if (localViewHolder != null)
          {
            if ((localViewHolder.isTmpDetached()) && (!localViewHolder.shouldIgnore()))
              throw new IllegalArgumentException("called detach on an already detached child " + localViewHolder);
            localViewHolder.addFlags(256);
          }
        }
        RecyclerView.this.detachViewFromParent(paramAnonymousInt);
      }

      public View getChildAt(int paramAnonymousInt)
      {
        return RecyclerView.this.getChildAt(paramAnonymousInt);
      }

      public int getChildCount()
      {
        return RecyclerView.this.getChildCount();
      }

      public RecyclerView.ViewHolder getChildViewHolder(View paramAnonymousView)
      {
        return RecyclerView.getChildViewHolderInt(paramAnonymousView);
      }

      public int indexOfChild(View paramAnonymousView)
      {
        return RecyclerView.this.indexOfChild(paramAnonymousView);
      }

      public void removeAllViews()
      {
        int i = getChildCount();
        for (int j = 0; j < i; j++)
          RecyclerView.this.dispatchChildDetached(getChildAt(j));
        RecyclerView.this.removeAllViews();
      }

      public void removeViewAt(int paramAnonymousInt)
      {
        View localView = RecyclerView.this.getChildAt(paramAnonymousInt);
        if (localView != null)
          RecyclerView.this.dispatchChildDetached(localView);
        RecyclerView.this.removeViewAt(paramAnonymousInt);
      }
    });
  }

  private void onEnterLayoutOrScroll()
  {
    this.mLayoutOrScrollCounter = (1 + this.mLayoutOrScrollCounter);
  }

  private void onExitLayoutOrScroll()
  {
    this.mLayoutOrScrollCounter = (-1 + this.mLayoutOrScrollCounter);
    if (this.mLayoutOrScrollCounter < 1)
    {
      this.mLayoutOrScrollCounter = 0;
      dispatchContentChangedIfNecessary();
    }
  }

  private void onPointerUp(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (MotionEventCompat.getPointerId(paramMotionEvent, i) == this.mScrollPointerId)
      if (i != 0)
        break label81;
    label81: for (int j = 1; ; j = 0)
    {
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, j);
      int k = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, j));
      this.mLastTouchX = k;
      this.mInitialTouchX = k;
      int m = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, j));
      this.mLastTouchY = m;
      this.mInitialTouchY = m;
      return;
    }
  }

  private void postAnimationRunner()
  {
    if ((!this.mPostedAnimatorRunner) && (this.mIsAttached))
    {
      ViewCompat.postOnAnimation(this, this.mItemAnimatorRunner);
      this.mPostedAnimatorRunner = true;
    }
  }

  private boolean predictiveItemAnimationsEnabled()
  {
    return (this.mItemAnimator != null) && (this.mLayout.supportsPredictiveItemAnimations());
  }

  private void processAdapterUpdatesAndSetAnimationFlags()
  {
    boolean bool1 = true;
    if (this.mDataSetHasChangedAfterLayout)
    {
      this.mAdapterHelper.reset();
      markKnownViewsInvalid();
      this.mLayout.onItemsChanged(this);
    }
    boolean bool2;
    label89: boolean bool3;
    label149: State localState2;
    if ((this.mItemAnimator != null) && (this.mLayout.supportsPredictiveItemAnimations()))
    {
      this.mAdapterHelper.preProcess();
      if (((!this.mItemsAddedOrRemoved) || (this.mItemsChanged)) && (!this.mItemsAddedOrRemoved) && ((!this.mItemsChanged) || (!supportsChangeAnimations())))
        break label208;
      bool2 = bool1;
      State localState1 = this.mState;
      if ((!this.mFirstLayoutComplete) || (this.mItemAnimator == null) || ((!this.mDataSetHasChangedAfterLayout) && (!bool2) && (!this.mLayout.mRequestedSimpleAnimations)) || ((this.mDataSetHasChangedAfterLayout) && (!this.mAdapter.hasStableIds())))
        break label213;
      bool3 = bool1;
      State.access$1602(localState1, bool3);
      localState2 = this.mState;
      if ((!this.mState.mRunSimpleAnimations) || (!bool2) || (this.mDataSetHasChangedAfterLayout) || (!predictiveItemAnimationsEnabled()))
        break label219;
    }
    while (true)
    {
      State.access$1402(localState2, bool1);
      return;
      this.mAdapterHelper.consumeUpdatesInOnePass();
      break;
      label208: bool2 = false;
      break label89;
      label213: bool3 = false;
      break label149;
      label219: bool1 = false;
    }
  }

  private void processDisappearingList(ArrayMap<View, Rect> paramArrayMap)
  {
    List localList = this.mState.mDisappearingViewsInLayoutPass;
    int i = -1 + localList.size();
    if (i >= 0)
    {
      View localView = (View)localList.get(i);
      ViewHolder localViewHolder = getChildViewHolderInt(localView);
      ItemHolderInfo localItemHolderInfo = (ItemHolderInfo)this.mState.mPreLayoutHolderMap.remove(localViewHolder);
      if (!this.mState.isPreLayout())
        this.mState.mPostLayoutHolderMap.remove(localViewHolder);
      if (paramArrayMap.remove(localView) != null)
        this.mLayout.removeAndRecycleView(localView, this.mRecycler);
      while (true)
      {
        i--;
        break;
        if (localItemHolderInfo != null)
          animateDisappearance(localItemHolderInfo);
        else
          animateDisappearance(new ItemHolderInfo(localViewHolder, localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom()));
      }
    }
    localList.clear();
  }

  private void pullGlows(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i;
    if (paramFloat2 < 0.0F)
    {
      ensureLeftGlow();
      boolean bool3 = this.mLeftGlow.onPull(-paramFloat2 / getWidth(), 1.0F - paramFloat3 / getHeight());
      i = 0;
      if (bool3)
        i = 1;
      if (paramFloat4 >= 0.0F)
        break label164;
      ensureTopGlow();
      if (this.mTopGlow.onPull(-paramFloat4 / getHeight(), paramFloat1 / getWidth()))
        i = 1;
    }
    while (true)
    {
      if ((i != 0) || (paramFloat2 != 0.0F) || (paramFloat4 != 0.0F))
        ViewCompat.postInvalidateOnAnimation(this);
      return;
      boolean bool1 = paramFloat2 < 0.0F;
      i = 0;
      if (!bool1)
        break;
      ensureRightGlow();
      boolean bool2 = this.mRightGlow.onPull(paramFloat2 / getWidth(), paramFloat3 / getHeight());
      i = 0;
      if (!bool2)
        break;
      i = 1;
      break;
      label164: if (paramFloat4 > 0.0F)
      {
        ensureBottomGlow();
        if (this.mBottomGlow.onPull(paramFloat4 / getHeight(), 1.0F - paramFloat1 / getWidth()))
          i = 1;
      }
    }
  }

  private void releaseGlows()
  {
    EdgeEffectCompat localEdgeEffectCompat = this.mLeftGlow;
    boolean bool = false;
    if (localEdgeEffectCompat != null)
      bool = this.mLeftGlow.onRelease();
    if (this.mTopGlow != null)
      bool |= this.mTopGlow.onRelease();
    if (this.mRightGlow != null)
      bool |= this.mRightGlow.onRelease();
    if (this.mBottomGlow != null)
      bool |= this.mBottomGlow.onRelease();
    if (bool)
      ViewCompat.postInvalidateOnAnimation(this);
  }

  private boolean removeAnimatingView(View paramView)
  {
    eatRequestLayout();
    boolean bool = this.mChildHelper.removeViewIfHidden(paramView);
    if (bool)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(paramView);
      this.mRecycler.unscrapView(localViewHolder);
      this.mRecycler.recycleViewHolderInternal(localViewHolder);
    }
    resumeRequestLayout(false);
    return bool;
  }

  private void setAdapterInternal(Adapter paramAdapter, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mAdapter != null)
    {
      this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
      this.mAdapter.onDetachedFromRecyclerView(this);
    }
    if ((!paramBoolean1) || (paramBoolean2))
    {
      if (this.mItemAnimator != null)
        this.mItemAnimator.endAnimations();
      if (this.mLayout != null)
      {
        this.mLayout.removeAndRecycleAllViews(this.mRecycler);
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
      }
      this.mRecycler.clear();
    }
    this.mAdapterHelper.reset();
    Adapter localAdapter = this.mAdapter;
    this.mAdapter = paramAdapter;
    if (paramAdapter != null)
    {
      paramAdapter.registerAdapterDataObserver(this.mObserver);
      paramAdapter.onAttachedToRecyclerView(this);
    }
    if (this.mLayout != null)
      this.mLayout.onAdapterChanged(localAdapter, this.mAdapter);
    this.mRecycler.onAdapterChanged(localAdapter, this.mAdapter, paramBoolean1);
    State.access$1202(this.mState, true);
    markKnownViewsInvalid();
  }

  private void setDataSetChangedAfterLayout()
  {
    if (this.mDataSetHasChangedAfterLayout)
      return;
    this.mDataSetHasChangedAfterLayout = true;
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()))
        localViewHolder.addFlags(512);
    }
    this.mRecycler.setAdapterPositionsAsUnknown();
  }

  private void setScrollState(int paramInt)
  {
    if (paramInt == this.mScrollState)
      return;
    this.mScrollState = paramInt;
    if (paramInt != 2)
      stopScrollersInternal();
    dispatchOnScrollStateChanged(paramInt);
  }

  private void stopScrollersInternal()
  {
    this.mViewFlinger.stop();
    if (this.mLayout != null)
      this.mLayout.stopSmoothScroller();
  }

  private boolean supportsChangeAnimations()
  {
    return (this.mItemAnimator != null) && (this.mItemAnimator.getSupportsChangeAnimations());
  }

  void absorbGlows(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
    {
      ensureLeftGlow();
      this.mLeftGlow.onAbsorb(-paramInt1);
      if (paramInt2 >= 0)
        break label69;
      ensureTopGlow();
      this.mTopGlow.onAbsorb(-paramInt2);
    }
    while (true)
    {
      if ((paramInt1 != 0) || (paramInt2 != 0))
        ViewCompat.postInvalidateOnAnimation(this);
      return;
      if (paramInt1 <= 0)
        break;
      ensureRightGlow();
      this.mRightGlow.onAbsorb(paramInt1);
      break;
      label69: if (paramInt2 > 0)
      {
        ensureBottomGlow();
        this.mBottomGlow.onAbsorb(paramInt2);
      }
    }
  }

  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    if ((this.mLayout == null) || (!this.mLayout.onAddFocusables(this, paramArrayList, paramInt1, paramInt2)))
      super.addFocusables(paramArrayList, paramInt1, paramInt2);
  }

  public void addItemDecoration(ItemDecoration paramItemDecoration)
  {
    addItemDecoration(paramItemDecoration, -1);
  }

  public void addItemDecoration(ItemDecoration paramItemDecoration, int paramInt)
  {
    if (this.mLayout != null)
      this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
    if (this.mItemDecorations.isEmpty())
      setWillNotDraw(false);
    if (paramInt < 0)
      this.mItemDecorations.add(paramItemDecoration);
    while (true)
    {
      markItemDecorInsetsDirty();
      requestLayout();
      return;
      this.mItemDecorations.add(paramInt, paramItemDecoration);
    }
  }

  public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener)
  {
    if (this.mOnChildAttachStateListeners == null)
      this.mOnChildAttachStateListeners = new ArrayList();
    this.mOnChildAttachStateListeners.add(paramOnChildAttachStateChangeListener);
  }

  public void addOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener)
  {
    this.mOnItemTouchListeners.add(paramOnItemTouchListener);
  }

  public void addOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    if (this.mScrollListeners == null)
      this.mScrollListeners = new ArrayList();
    this.mScrollListeners.add(paramOnScrollListener);
  }

  void assertInLayoutOrScroll(String paramString)
  {
    if (!isRunningLayoutOrScroll())
    {
      if (paramString == null)
        throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
      throw new IllegalStateException(paramString);
    }
  }

  void assertNotInLayoutOrScroll(String paramString)
  {
    if (isRunningLayoutOrScroll())
    {
      if (paramString == null)
        throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
      throw new IllegalStateException(paramString);
    }
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (this.mLayout.checkLayoutParams((LayoutParams)paramLayoutParams));
  }

  void clearOldPositions()
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if (!localViewHolder.shouldIgnore())
        localViewHolder.clearOldPosition();
    }
    this.mRecycler.clearOldPositions();
  }

  public void clearOnChildAttachStateChangeListeners()
  {
    if (this.mOnChildAttachStateListeners != null)
      this.mOnChildAttachStateListeners.clear();
  }

  public void clearOnScrollListeners()
  {
    if (this.mScrollListeners != null)
      this.mScrollListeners.clear();
  }

  public int computeHorizontalScrollExtent()
  {
    if (this.mLayout.canScrollHorizontally())
      return this.mLayout.computeHorizontalScrollExtent(this.mState);
    return 0;
  }

  public int computeHorizontalScrollOffset()
  {
    if (this.mLayout.canScrollHorizontally())
      return this.mLayout.computeHorizontalScrollOffset(this.mState);
    return 0;
  }

  public int computeHorizontalScrollRange()
  {
    if (this.mLayout.canScrollHorizontally())
      return this.mLayout.computeHorizontalScrollRange(this.mState);
    return 0;
  }

  public int computeVerticalScrollExtent()
  {
    if (this.mLayout.canScrollVertically())
      return this.mLayout.computeVerticalScrollExtent(this.mState);
    return 0;
  }

  public int computeVerticalScrollOffset()
  {
    if (this.mLayout.canScrollVertically())
      return this.mLayout.computeVerticalScrollOffset(this.mState);
    return 0;
  }

  public int computeVerticalScrollRange()
  {
    if (this.mLayout.canScrollVertically())
      return this.mLayout.computeVerticalScrollRange(this.mState);
    return 0;
  }

  void dispatchLayout()
  {
    if (this.mAdapter == null)
      Log.e("RecyclerView", "No adapter attached; skipping layout");
    label189: label247: label763: 
    do
    {
      return;
      if (this.mLayout == null)
      {
        Log.e("RecyclerView", "No layout manager attached; skipping layout");
        return;
      }
      this.mState.mDisappearingViewsInLayoutPass.clear();
      eatRequestLayout();
      onEnterLayoutOrScroll();
      processAdapterUpdatesAndSetAnimationFlags();
      State localState1 = this.mState;
      ArrayMap localArrayMap1;
      int i19;
      ViewHolder localViewHolder8;
      if ((this.mState.mRunSimpleAnimations) && (this.mItemsChanged) && (supportsChangeAnimations()))
      {
        localArrayMap1 = new ArrayMap();
        localState1.mOldChangedHolders = localArrayMap1;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        State.access$1502(this.mState, this.mState.mRunPredictiveAnimations);
        this.mState.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (!this.mState.mRunSimpleAnimations)
          break label299;
        this.mState.mPreLayoutHolderMap.clear();
        this.mState.mPostLayoutHolderMap.clear();
        int i18 = this.mChildHelper.getChildCount();
        i19 = 0;
        if (i19 >= i18)
          break label299;
        localViewHolder8 = getChildViewHolderInt(this.mChildHelper.getChildAt(i19));
        if ((!localViewHolder8.shouldIgnore()) && ((!localViewHolder8.isInvalid()) || (this.mAdapter.hasStableIds())))
          break label247;
      }
      while (true)
      {
        i19++;
        break label189;
        localArrayMap1 = null;
        break;
        View localView3 = localViewHolder8.itemView;
        this.mState.mPreLayoutHolderMap.put(localViewHolder8, new ItemHolderInfo(localViewHolder8, localView3.getLeft(), localView3.getTop(), localView3.getRight(), localView3.getBottom()));
      }
      ArrayMap localArrayMap3;
      boolean bool1;
      ArrayMap localArrayMap4;
      int n;
      ViewHolder localViewHolder6;
      if (this.mState.mRunPredictiveAnimations)
      {
        saveOldPositions();
        if (this.mState.mOldChangedHolders != null)
        {
          int i16 = this.mChildHelper.getChildCount();
          for (int i17 = 0; i17 < i16; i17++)
          {
            ViewHolder localViewHolder7 = getChildViewHolderInt(this.mChildHelper.getChildAt(i17));
            if ((localViewHolder7.isChanged()) && (!localViewHolder7.isRemoved()) && (!localViewHolder7.shouldIgnore()))
            {
              long l4 = getChangedHolderKey(localViewHolder7);
              this.mState.mOldChangedHolders.put(Long.valueOf(l4), localViewHolder7);
              this.mState.mPreLayoutHolderMap.remove(localViewHolder7);
            }
          }
        }
        boolean bool2 = this.mState.mStructureChanged;
        State.access$1202(this.mState, false);
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        State.access$1202(this.mState, bool2);
        localArrayMap3 = new ArrayMap();
        View localView2;
        for (int i10 = 0; ; i10++)
        {
          int i11 = this.mChildHelper.getChildCount();
          if (i10 >= i11)
            break label628;
          localView2 = this.mChildHelper.getChildAt(i10);
          if (!getChildViewHolderInt(localView2).shouldIgnore())
            break;
        }
        for (int i12 = 0; ; i12++)
        {
          int i13 = this.mState.mPreLayoutHolderMap.size();
          int i14 = i12;
          int i15 = 0;
          if (i14 < i13)
          {
            if (((ViewHolder)this.mState.mPreLayoutHolderMap.keyAt(i12)).itemView == localView2)
              i15 = 1;
          }
          else
          {
            if (i15 != 0)
              break;
            localArrayMap3.put(localView2, new Rect(localView2.getLeft(), localView2.getTop(), localView2.getRight(), localView2.getBottom()));
            break;
          }
        }
        clearOldPositions();
        this.mAdapterHelper.consumePostponedUpdates();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        State.access$1002(this.mState, 0);
        State.access$1502(this.mState, false);
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        State.access$1202(this.mState, false);
        this.mPendingSavedState = null;
        State localState2 = this.mState;
        if ((!this.mState.mRunSimpleAnimations) || (this.mItemAnimator == null))
          break label947;
        bool1 = true;
        State.access$1602(localState2, bool1);
        if (!this.mState.mRunSimpleAnimations)
          break label1650;
        if (this.mState.mOldChangedHolders == null)
          break label953;
        localArrayMap4 = new ArrayMap();
        int m = this.mChildHelper.getChildCount();
        n = 0;
        if (n >= m)
          break label1062;
        localViewHolder6 = getChildViewHolderInt(this.mChildHelper.getChildAt(n));
        if (!localViewHolder6.shouldIgnore())
          break label959;
      }
      while (true)
      {
        n++;
        break label775;
        clearOldPositions();
        this.mAdapterHelper.consumeUpdatesInOnePass();
        ArrayMap localArrayMap2 = this.mState.mOldChangedHolders;
        localArrayMap3 = null;
        if (localArrayMap2 == null)
          break;
        int i = this.mChildHelper.getChildCount();
        for (int j = 0; ; j++)
        {
          int k = j;
          localArrayMap3 = null;
          if (k >= i)
            break;
          ViewHolder localViewHolder1 = getChildViewHolderInt(this.mChildHelper.getChildAt(j));
          if ((localViewHolder1.isChanged()) && (!localViewHolder1.isRemoved()) && (!localViewHolder1.shouldIgnore()))
          {
            long l1 = getChangedHolderKey(localViewHolder1);
            this.mState.mOldChangedHolders.put(Long.valueOf(l1), localViewHolder1);
            this.mState.mPreLayoutHolderMap.remove(localViewHolder1);
          }
        }
        bool1 = false;
        break label726;
        localArrayMap4 = null;
        break label763;
        View localView1 = localViewHolder6.itemView;
        long l3 = getChangedHolderKey(localViewHolder6);
        if ((localArrayMap4 != null) && (this.mState.mOldChangedHolders.get(Long.valueOf(l3)) != null))
        {
          Long localLong2 = Long.valueOf(l3);
          localArrayMap4.put(localLong2, localViewHolder6);
        }
        else
        {
          this.mState.mPostLayoutHolderMap.put(localViewHolder6, new ItemHolderInfo(localViewHolder6, localView1.getLeft(), localView1.getTop(), localView1.getRight(), localView1.getBottom()));
        }
      }
      processDisappearingList(localArrayMap3);
      for (int i1 = -1 + this.mState.mPreLayoutHolderMap.size(); i1 >= 0; i1--)
      {
        ViewHolder localViewHolder5 = (ViewHolder)this.mState.mPreLayoutHolderMap.keyAt(i1);
        if (!this.mState.mPostLayoutHolderMap.containsKey(localViewHolder5))
        {
          ItemHolderInfo localItemHolderInfo4 = (ItemHolderInfo)this.mState.mPreLayoutHolderMap.valueAt(i1);
          this.mState.mPreLayoutHolderMap.removeAt(i1);
          this.mRecycler.unscrapView(localItemHolderInfo4.holder);
          animateDisappearance(localItemHolderInfo4);
        }
      }
      int i2 = this.mState.mPostLayoutHolderMap.size();
      if (i2 > 0)
      {
        int i7 = i2 - 1;
        if (i7 >= 0)
        {
          ViewHolder localViewHolder4 = (ViewHolder)this.mState.mPostLayoutHolderMap.keyAt(i7);
          ItemHolderInfo localItemHolderInfo3 = (ItemHolderInfo)this.mState.mPostLayoutHolderMap.valueAt(i7);
          if ((this.mState.mPreLayoutHolderMap.isEmpty()) || (!this.mState.mPreLayoutHolderMap.containsKey(localViewHolder4)))
          {
            this.mState.mPostLayoutHolderMap.removeAt(i7);
            if (localArrayMap3 == null)
              break label1337;
          }
          for (Rect localRect = (Rect)localArrayMap3.get(localViewHolder4.itemView); ; localRect = null)
          {
            int i8 = localItemHolderInfo3.left;
            int i9 = localItemHolderInfo3.top;
            animateAppearance(localViewHolder4, localRect, i8, i9);
            i7--;
            break;
          }
        }
      }
      int i3 = this.mState.mPostLayoutHolderMap.size();
      for (int i4 = 0; i4 < i3; i4++)
      {
        ViewHolder localViewHolder3 = (ViewHolder)this.mState.mPostLayoutHolderMap.keyAt(i4);
        ItemHolderInfo localItemHolderInfo1 = (ItemHolderInfo)this.mState.mPostLayoutHolderMap.valueAt(i4);
        ItemHolderInfo localItemHolderInfo2 = (ItemHolderInfo)this.mState.mPreLayoutHolderMap.get(localViewHolder3);
        if ((localItemHolderInfo2 != null) && (localItemHolderInfo1 != null) && ((localItemHolderInfo2.left != localItemHolderInfo1.left) || (localItemHolderInfo2.top != localItemHolderInfo1.top)))
        {
          localViewHolder3.setIsRecyclable(false);
          if (this.mItemAnimator.animateMove(localViewHolder3, localItemHolderInfo2.left, localItemHolderInfo2.top, localItemHolderInfo1.left, localItemHolderInfo1.top))
            postAnimationRunner();
        }
      }
      int i5;
      int i6;
      long l2;
      ViewHolder localViewHolder2;
      if (this.mState.mOldChangedHolders != null)
      {
        i5 = this.mState.mOldChangedHolders.size();
        i6 = i5 - 1;
        if (i6 < 0)
          break label1650;
        l2 = ((Long)this.mState.mOldChangedHolders.keyAt(i6)).longValue();
        localViewHolder2 = (ViewHolder)this.mState.mOldChangedHolders.get(Long.valueOf(l2));
        if (!localViewHolder2.shouldIgnore())
          break label1599;
      }
      while (true)
      {
        i6--;
        break label1528;
        i5 = 0;
        break;
        if ((this.mRecycler.mChangedScrap != null) && (this.mRecycler.mChangedScrap.contains(localViewHolder2)))
        {
          Long localLong1 = Long.valueOf(l2);
          animateChange(localViewHolder2, (ViewHolder)localArrayMap4.get(localLong1));
        }
      }
      resumeRequestLayout(false);
      this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
      State.access$1902(this.mState, this.mState.mItemCount);
      this.mDataSetHasChangedAfterLayout = false;
      State.access$1602(this.mState, false);
      State.access$1402(this.mState, false);
      onExitLayoutOrScroll();
      LayoutManager.access$1702(this.mLayout, false);
      if (this.mRecycler.mChangedScrap != null)
        this.mRecycler.mChangedScrap.clear();
      this.mState.mOldChangedHolders = null;
    }
    while (!didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1]));
    label299: label628: label1528: dispatchOnScrolled(0, 0);
    label726: label1650: return;
  }

  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return this.mScrollingChildHelper.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }

  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2)
  {
    return this.mScrollingChildHelper.dispatchNestedPreFling(paramFloat1, paramFloat2);
  }

  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return this.mScrollingChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }

  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    return this.mScrollingChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
  }

  void dispatchOnScrollStateChanged(int paramInt)
  {
    if (this.mLayout != null)
      this.mLayout.onScrollStateChanged(paramInt);
    onScrollStateChanged(paramInt);
    if (this.mScrollListener != null)
      this.mScrollListener.onScrollStateChanged(this, paramInt);
    if (this.mScrollListeners != null)
      for (int i = -1 + this.mScrollListeners.size(); i >= 0; i--)
        ((OnScrollListener)this.mScrollListeners.get(i)).onScrollStateChanged(this, paramInt);
  }

  void dispatchOnScrolled(int paramInt1, int paramInt2)
  {
    int i = getScrollX();
    int j = getScrollY();
    onScrollChanged(i, j, i, j);
    onScrolled(paramInt1, paramInt2);
    if (this.mScrollListener != null)
      this.mScrollListener.onScrolled(this, paramInt1, paramInt2);
    if (this.mScrollListeners != null)
      for (int k = -1 + this.mScrollListeners.size(); k >= 0; k--)
        ((OnScrollListener)this.mScrollListeners.get(k)).onScrolled(this, paramInt1, paramInt2);
  }

  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    dispatchThawSelfOnly(paramSparseArray);
  }

  protected void dispatchSaveInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    dispatchFreezeSelfOnly(paramSparseArray);
  }

  public void draw(Canvas paramCanvas)
  {
    int i = 1;
    super.draw(paramCanvas);
    int j = this.mItemDecorations.size();
    for (int k = 0; k < j; k++)
      ((ItemDecoration)this.mItemDecorations.get(k)).onDrawOver(paramCanvas, this, this.mState);
    EdgeEffectCompat localEdgeEffectCompat = this.mLeftGlow;
    int m = 0;
    int i8;
    if (localEdgeEffectCompat != null)
    {
      boolean bool = this.mLeftGlow.isFinished();
      m = 0;
      if (!bool)
      {
        int i7 = paramCanvas.save();
        if (!this.mClipToPadding)
          break label460;
        i8 = getPaddingBottom();
        paramCanvas.rotate(270.0F);
        paramCanvas.translate(i8 + -getHeight(), 0.0F);
        if ((this.mLeftGlow == null) || (!this.mLeftGlow.draw(paramCanvas)))
          break label466;
        m = i;
        label143: paramCanvas.restoreToCount(i7);
      }
    }
    int i6;
    label214: int i3;
    label269: int i4;
    label309: int n;
    if ((this.mTopGlow != null) && (!this.mTopGlow.isFinished()))
    {
      int i5 = paramCanvas.save();
      if (this.mClipToPadding)
        paramCanvas.translate(getPaddingLeft(), getPaddingTop());
      if ((this.mTopGlow != null) && (this.mTopGlow.draw(paramCanvas)))
      {
        i6 = i;
        m |= i6;
        paramCanvas.restoreToCount(i5);
      }
    }
    else
    {
      if ((this.mRightGlow != null) && (!this.mRightGlow.isFinished()))
      {
        int i1 = paramCanvas.save();
        int i2 = getWidth();
        if (!this.mClipToPadding)
          break label478;
        i3 = getPaddingTop();
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-i3, -i2);
        if ((this.mRightGlow == null) || (!this.mRightGlow.draw(paramCanvas)))
          break label484;
        i4 = i;
        m |= i4;
        paramCanvas.restoreToCount(i1);
      }
      if ((this.mBottomGlow != null) && (!this.mBottomGlow.isFinished()))
      {
        n = paramCanvas.save();
        paramCanvas.rotate(180.0F);
        if (!this.mClipToPadding)
          break label490;
        paramCanvas.translate(-getWidth() + getPaddingRight(), -getHeight() + getPaddingBottom());
        label385: if ((this.mBottomGlow == null) || (!this.mBottomGlow.draw(paramCanvas)))
          break label509;
      }
    }
    while (true)
    {
      m |= i;
      paramCanvas.restoreToCount(n);
      if ((m == 0) && (this.mItemAnimator != null) && (this.mItemDecorations.size() > 0) && (this.mItemAnimator.isRunning()))
        m = 1;
      if (m != 0)
        ViewCompat.postInvalidateOnAnimation(this);
      return;
      label460: i8 = 0;
      break;
      label466: m = 0;
      break label143;
      i6 = 0;
      break label214;
      label478: i3 = 0;
      break label269;
      label484: i4 = 0;
      break label309;
      label490: paramCanvas.translate(-getWidth(), -getHeight());
      break label385;
      label509: i = 0;
    }
  }

  public boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    return super.drawChild(paramCanvas, paramView, paramLong);
  }

  void eatRequestLayout()
  {
    if (!this.mEatRequestLayout)
    {
      this.mEatRequestLayout = true;
      this.mLayoutRequestEaten = false;
    }
  }

  void ensureBottomGlow()
  {
    if (this.mBottomGlow != null)
      return;
    this.mBottomGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mBottomGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
      return;
    }
    this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
  }

  void ensureLeftGlow()
  {
    if (this.mLeftGlow != null)
      return;
    this.mLeftGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mLeftGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      return;
    }
    this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
  }

  void ensureRightGlow()
  {
    if (this.mRightGlow != null)
      return;
    this.mRightGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mRightGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      return;
    }
    this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
  }

  void ensureTopGlow()
  {
    if (this.mTopGlow != null)
      return;
    this.mTopGlow = new EdgeEffectCompat(getContext());
    if (this.mClipToPadding)
    {
      this.mTopGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
      return;
    }
    this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
  }

  public View findChildViewUnder(float paramFloat1, float paramFloat2)
  {
    for (int i = -1 + this.mChildHelper.getChildCount(); i >= 0; i--)
    {
      View localView = this.mChildHelper.getChildAt(i);
      float f1 = ViewCompat.getTranslationX(localView);
      float f2 = ViewCompat.getTranslationY(localView);
      if ((paramFloat1 >= f1 + localView.getLeft()) && (paramFloat1 <= f1 + localView.getRight()) && (paramFloat2 >= f2 + localView.getTop()) && (paramFloat2 <= f2 + localView.getBottom()))
        return localView;
    }
    return null;
  }

  public ViewHolder findViewHolderForAdapterPosition(int paramInt)
  {
    ViewHolder localViewHolder;
    if (this.mDataSetHasChangedAfterLayout)
    {
      localViewHolder = null;
      return localViewHolder;
    }
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label70;
      localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder != null) && (!localViewHolder.isRemoved()) && (getAdapterPositionFor(localViewHolder) == paramInt))
        break;
    }
    label70: return null;
  }

  public ViewHolder findViewHolderForItemId(long paramLong)
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder != null) && (localViewHolder.getItemId() == paramLong))
        return localViewHolder;
    }
    return null;
  }

  public ViewHolder findViewHolderForLayoutPosition(int paramInt)
  {
    return findViewHolderForPosition(paramInt, false);
  }

  @Deprecated
  public ViewHolder findViewHolderForPosition(int paramInt)
  {
    return findViewHolderForPosition(paramInt, false);
  }

  ViewHolder findViewHolderForPosition(int paramInt, boolean paramBoolean)
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder != null) && (!localViewHolder.isRemoved()))
        if (paramBoolean)
        {
          if (localViewHolder.mPosition != paramInt);
        }
        else
          while (localViewHolder.getLayoutPosition() == paramInt)
            return localViewHolder;
    }
    return null;
  }

  public boolean fling(int paramInt1, int paramInt2)
  {
    if (this.mLayout == null)
      Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
    boolean bool1;
    boolean bool2;
    do
    {
      return false;
      bool1 = this.mLayout.canScrollHorizontally();
      bool2 = this.mLayout.canScrollVertically();
      if ((!bool1) || (Math.abs(paramInt1) < this.mMinFlingVelocity))
        paramInt1 = 0;
      if ((!bool2) || (Math.abs(paramInt2) < this.mMinFlingVelocity))
        paramInt2 = 0;
    }
    while (((paramInt1 == 0) && (paramInt2 == 0)) || (dispatchNestedPreFling(paramInt1, paramInt2)));
    if ((bool1) || (bool2));
    for (boolean bool3 = true; ; bool3 = false)
    {
      dispatchNestedFling(paramInt1, paramInt2, bool3);
      if (!bool3)
        break;
      int i = Math.max(-this.mMaxFlingVelocity, Math.min(paramInt1, this.mMaxFlingVelocity));
      int j = Math.max(-this.mMaxFlingVelocity, Math.min(paramInt2, this.mMaxFlingVelocity));
      this.mViewFlinger.fling(i, j);
      return true;
    }
  }

  public View focusSearch(View paramView, int paramInt)
  {
    View localView1 = this.mLayout.onInterceptFocusSearch(paramView, paramInt);
    if (localView1 != null)
      return localView1;
    View localView2 = FocusFinder.getInstance().findNextFocus(this, paramView, paramInt);
    if ((localView2 == null) && (this.mAdapter != null) && (this.mLayout != null))
    {
      eatRequestLayout();
      localView2 = this.mLayout.onFocusSearchFailed(paramView, paramInt, this.mRecycler, this.mState);
      resumeRequestLayout(false);
    }
    if (localView2 != null)
      return localView2;
    return super.focusSearch(paramView, paramInt);
  }

  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    if (this.mLayout == null)
      throw new IllegalStateException("RecyclerView has no LayoutManager");
    return this.mLayout.generateDefaultLayoutParams();
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    if (this.mLayout == null)
      throw new IllegalStateException("RecyclerView has no LayoutManager");
    return this.mLayout.generateLayoutParams(getContext(), paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if (this.mLayout == null)
      throw new IllegalStateException("RecyclerView has no LayoutManager");
    return this.mLayout.generateLayoutParams(paramLayoutParams);
  }

  public Adapter getAdapter()
  {
    return this.mAdapter;
  }

  public int getBaseline()
  {
    if (this.mLayout != null)
      return this.mLayout.getBaseline();
    return super.getBaseline();
  }

  long getChangedHolderKey(ViewHolder paramViewHolder)
  {
    if (this.mAdapter.hasStableIds())
      return paramViewHolder.getItemId();
    return paramViewHolder.mPosition;
  }

  public int getChildAdapterPosition(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null)
      return localViewHolder.getAdapterPosition();
    return -1;
  }

  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    if (this.mChildDrawingOrderCallback == null)
      return super.getChildDrawingOrder(paramInt1, paramInt2);
    return this.mChildDrawingOrderCallback.onGetChildDrawingOrder(paramInt1, paramInt2);
  }

  public long getChildItemId(View paramView)
  {
    if ((this.mAdapter == null) || (!this.mAdapter.hasStableIds()));
    ViewHolder localViewHolder;
    do
    {
      return -1L;
      localViewHolder = getChildViewHolderInt(paramView);
    }
    while (localViewHolder == null);
    return localViewHolder.getItemId();
  }

  public int getChildLayoutPosition(View paramView)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null)
      return localViewHolder.getLayoutPosition();
    return -1;
  }

  @Deprecated
  public int getChildPosition(View paramView)
  {
    return getChildAdapterPosition(paramView);
  }

  public ViewHolder getChildViewHolder(View paramView)
  {
    ViewParent localViewParent = paramView.getParent();
    if ((localViewParent != null) && (localViewParent != this))
      throw new IllegalArgumentException("View " + paramView + " is not a direct child of " + this);
    return getChildViewHolderInt(paramView);
  }

  public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate()
  {
    return this.mAccessibilityDelegate;
  }

  public ItemAnimator getItemAnimator()
  {
    return this.mItemAnimator;
  }

  Rect getItemDecorInsetsForChild(View paramView)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!localLayoutParams.mInsetsDirty)
      return localLayoutParams.mDecorInsets;
    Rect localRect = localLayoutParams.mDecorInsets;
    localRect.set(0, 0, 0, 0);
    int i = this.mItemDecorations.size();
    for (int j = 0; j < i; j++)
    {
      this.mTempRect.set(0, 0, 0, 0);
      ((ItemDecoration)this.mItemDecorations.get(j)).getItemOffsets(this.mTempRect, paramView, this, this.mState);
      localRect.left += this.mTempRect.left;
      localRect.top += this.mTempRect.top;
      localRect.right += this.mTempRect.right;
      localRect.bottom += this.mTempRect.bottom;
    }
    localLayoutParams.mInsetsDirty = false;
    return localRect;
  }

  public LayoutManager getLayoutManager()
  {
    return this.mLayout;
  }

  public int getMaxFlingVelocity()
  {
    return this.mMaxFlingVelocity;
  }

  public int getMinFlingVelocity()
  {
    return this.mMinFlingVelocity;
  }

  public RecycledViewPool getRecycledViewPool()
  {
    return this.mRecycler.getRecycledViewPool();
  }

  public int getScrollState()
  {
    return this.mScrollState;
  }

  public boolean hasFixedSize()
  {
    return this.mHasFixedSize;
  }

  public boolean hasNestedScrollingParent()
  {
    return this.mScrollingChildHelper.hasNestedScrollingParent();
  }

  public boolean hasPendingAdapterUpdates()
  {
    return (!this.mFirstLayoutComplete) || (this.mDataSetHasChangedAfterLayout) || (this.mAdapterHelper.hasPendingUpdates());
  }

  void initAdapterManager()
  {
    this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback()
    {
      void dispatchUpdate(AdapterHelper.UpdateOp paramAnonymousUpdateOp)
      {
        switch (paramAnonymousUpdateOp.cmd)
        {
        default:
          return;
        case 0:
          RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, paramAnonymousUpdateOp.positionStart, paramAnonymousUpdateOp.itemCount);
          return;
        case 1:
          RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, paramAnonymousUpdateOp.positionStart, paramAnonymousUpdateOp.itemCount);
          return;
        case 2:
          RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, paramAnonymousUpdateOp.positionStart, paramAnonymousUpdateOp.itemCount);
          return;
        case 3:
        }
        RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, paramAnonymousUpdateOp.positionStart, paramAnonymousUpdateOp.itemCount, 1);
      }

      public RecyclerView.ViewHolder findViewHolder(int paramAnonymousInt)
      {
        RecyclerView.ViewHolder localViewHolder = RecyclerView.this.findViewHolderForPosition(paramAnonymousInt, true);
        if (localViewHolder == null)
          localViewHolder = null;
        while (!RecyclerView.this.mChildHelper.isHidden(localViewHolder.itemView))
          return localViewHolder;
        return null;
      }

      public void markViewHoldersUpdated(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.viewRangeUpdate(paramAnonymousInt1, paramAnonymousInt2);
        RecyclerView.this.mItemsChanged = true;
      }

      public void offsetPositionsForAdd(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForInsert(paramAnonymousInt1, paramAnonymousInt2);
        RecyclerView.this.mItemsAddedOrRemoved = true;
      }

      public void offsetPositionsForMove(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForMove(paramAnonymousInt1, paramAnonymousInt2);
        RecyclerView.this.mItemsAddedOrRemoved = true;
      }

      public void offsetPositionsForRemovingInvisible(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForRemove(paramAnonymousInt1, paramAnonymousInt2, true);
        RecyclerView.this.mItemsAddedOrRemoved = true;
        RecyclerView.State.access$1012(RecyclerView.this.mState, paramAnonymousInt2);
      }

      public void offsetPositionsForRemovingLaidOutOrNewView(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        RecyclerView.this.offsetPositionRecordsForRemove(paramAnonymousInt1, paramAnonymousInt2, false);
        RecyclerView.this.mItemsAddedOrRemoved = true;
      }

      public void onDispatchFirstPass(AdapterHelper.UpdateOp paramAnonymousUpdateOp)
      {
        dispatchUpdate(paramAnonymousUpdateOp);
      }

      public void onDispatchSecondPass(AdapterHelper.UpdateOp paramAnonymousUpdateOp)
      {
        dispatchUpdate(paramAnonymousUpdateOp);
      }
    });
  }

  void invalidateGlows()
  {
    this.mBottomGlow = null;
    this.mTopGlow = null;
    this.mRightGlow = null;
    this.mLeftGlow = null;
  }

  public void invalidateItemDecorations()
  {
    if (this.mItemDecorations.size() == 0)
      return;
    if (this.mLayout != null)
      this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
    markItemDecorInsetsDirty();
    requestLayout();
  }

  public boolean isAnimating()
  {
    return (this.mItemAnimator != null) && (this.mItemAnimator.isRunning());
  }

  public boolean isAttachedToWindow()
  {
    return this.mIsAttached;
  }

  public boolean isNestedScrollingEnabled()
  {
    return this.mScrollingChildHelper.isNestedScrollingEnabled();
  }

  boolean isRunningLayoutOrScroll()
  {
    return this.mLayoutOrScrollCounter > 0;
  }

  void markItemDecorInsetsDirty()
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
      ((LayoutParams)this.mChildHelper.getUnfilteredChildAt(j).getLayoutParams()).mInsetsDirty = true;
    this.mRecycler.markItemDecorInsetsDirty();
  }

  void markKnownViewsInvalid()
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()))
        localViewHolder.addFlags(6);
    }
    markItemDecorInsetsDirty();
    this.mRecycler.markKnownViewsInvalid();
  }

  public void offsetChildrenHorizontal(int paramInt)
  {
    int i = this.mChildHelper.getChildCount();
    for (int j = 0; j < i; j++)
      this.mChildHelper.getChildAt(j).offsetLeftAndRight(paramInt);
  }

  public void offsetChildrenVertical(int paramInt)
  {
    int i = this.mChildHelper.getChildCount();
    for (int j = 0; j < i; j++)
      this.mChildHelper.getChildAt(j).offsetTopAndBottom(paramInt);
  }

  void offsetPositionRecordsForInsert(int paramInt1, int paramInt2)
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (localViewHolder.mPosition >= paramInt1))
      {
        localViewHolder.offsetPosition(paramInt2, false);
        State.access$1202(this.mState, true);
      }
    }
    this.mRecycler.offsetPositionRecordsForInsert(paramInt1, paramInt2);
    requestLayout();
  }

  void offsetPositionRecordsForMove(int paramInt1, int paramInt2)
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    int j;
    int k;
    if (paramInt1 < paramInt2)
    {
      j = paramInt1;
      k = paramInt2;
    }
    ViewHolder localViewHolder;
    for (int m = -1; ; m = 1)
    {
      for (int n = 0; ; n++)
      {
        if (n >= i)
          break label129;
        localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n));
        if ((localViewHolder != null) && (localViewHolder.mPosition >= j) && (localViewHolder.mPosition <= k))
          break;
      }
      j = paramInt2;
      k = paramInt1;
    }
    if (localViewHolder.mPosition == paramInt1)
      localViewHolder.offsetPosition(paramInt2 - paramInt1, false);
    while (true)
    {
      State.access$1202(this.mState, true);
      break;
      localViewHolder.offsetPosition(m, false);
    }
    label129: this.mRecycler.offsetPositionRecordsForMove(paramInt1, paramInt2);
    requestLayout();
  }

  void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = paramInt1 + paramInt2;
    int j = this.mChildHelper.getUnfilteredChildCount();
    int k = 0;
    if (k < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(k));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()))
      {
        if (localViewHolder.mPosition < i)
          break label84;
        localViewHolder.offsetPosition(-paramInt2, paramBoolean);
        State.access$1202(this.mState, true);
      }
      while (true)
      {
        k++;
        break;
        label84: if (localViewHolder.mPosition >= paramInt1)
        {
          localViewHolder.flagRemovedAndOffsetPosition(paramInt1 - 1, -paramInt2, paramBoolean);
          State.access$1202(this.mState, true);
        }
      }
    }
    this.mRecycler.offsetPositionRecordsForRemove(paramInt1, paramInt2, paramBoolean);
    requestLayout();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mLayoutOrScrollCounter = 0;
    this.mIsAttached = true;
    this.mFirstLayoutComplete = false;
    if (this.mLayout != null)
      this.mLayout.dispatchAttachedToWindow(this);
    this.mPostedAnimatorRunner = false;
  }

  public void onChildAttachedToWindow(View paramView)
  {
  }

  public void onChildDetachedFromWindow(View paramView)
  {
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mItemAnimator != null)
      this.mItemAnimator.endAnimations();
    this.mFirstLayoutComplete = false;
    stopScroll();
    this.mIsAttached = false;
    if (this.mLayout != null)
      this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
    removeCallbacks(this.mItemAnimatorRunner);
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = this.mItemDecorations.size();
    for (int j = 0; j < i; j++)
      ((ItemDecoration)this.mItemDecorations.get(j)).onDraw(paramCanvas, this, this.mState);
  }

  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (this.mLayout == null);
    label100: label103: 
    while (true)
    {
      return false;
      if (((0x2 & MotionEventCompat.getSource(paramMotionEvent)) != 0) && (paramMotionEvent.getAction() == 8))
      {
        float f1;
        if (this.mLayout.canScrollVertically())
        {
          f1 = MotionEventCompat.getAxisValue(paramMotionEvent, 9);
          if (!this.mLayout.canScrollHorizontally())
            break label100;
        }
        for (float f2 = MotionEventCompat.getAxisValue(paramMotionEvent, 10); ; f2 = 0.0F)
        {
          if ((f1 == 0.0F) && (f2 == 0.0F))
            break label103;
          float f3 = getScrollFactor();
          scrollBy((int)(f2 * f3), (int)(f1 * f3));
          return false;
          f1 = 0.0F;
          break;
        }
      }
    }
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (dispatchOnItemTouchIntercept(paramMotionEvent))
    {
      cancelTouch();
      return true;
    }
    boolean bool1 = this.mLayout.canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain();
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    int j = MotionEventCompat.getActionIndex(paramMotionEvent);
    switch (i)
    {
    case 4:
    default:
    case 0:
    case 5:
    case 2:
    case 6:
    case 1:
    case 3:
    }
    while (this.mScrollState == 1)
    {
      return true;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
      int i14 = (int)(0.5F + paramMotionEvent.getX());
      this.mLastTouchX = i14;
      this.mInitialTouchX = i14;
      int i15 = (int)(0.5F + paramMotionEvent.getY());
      this.mLastTouchY = i15;
      this.mInitialTouchY = i15;
      if (this.mScrollState == 2)
      {
        getParent().requestDisallowInterceptTouchEvent(true);
        setScrollState(1);
      }
      int i16 = 0;
      if (bool1)
        i16 = 0x0 | 0x1;
      if (bool2)
        i16 |= 2;
      startNestedScroll(i16);
      continue;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, j);
      int i12 = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, j));
      this.mLastTouchX = i12;
      this.mInitialTouchX = i12;
      int i13 = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, j));
      this.mLastTouchY = i13;
      this.mInitialTouchY = i13;
      continue;
      int k = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mScrollPointerId);
      if (k < 0)
      {
        Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
        return false;
      }
      int m = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, k));
      int n = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, k));
      if (this.mScrollState != 1)
      {
        int i1 = m - this.mInitialTouchX;
        int i2 = n - this.mInitialTouchY;
        int i3 = 0;
        int i11;
        if (bool1)
        {
          int i7 = Math.abs(i1);
          int i8 = this.mTouchSlop;
          i3 = 0;
          if (i7 > i8)
          {
            int i9 = this.mInitialTouchX;
            int i10 = this.mTouchSlop;
            if (i1 >= 0)
              break label524;
            i11 = -1;
            label445: this.mLastTouchX = (i9 + i11 * i10);
            i3 = 1;
          }
        }
        int i4;
        int i5;
        if ((bool2) && (Math.abs(i2) > this.mTouchSlop))
        {
          i4 = this.mInitialTouchY;
          i5 = this.mTouchSlop;
          if (i2 >= 0)
            break label530;
        }
        label524: label530: for (int i6 = -1; ; i6 = 1)
        {
          this.mLastTouchY = (i4 + i6 * i5);
          i3 = 1;
          if (i3 == 0)
            break;
          setScrollState(1);
          break;
          i11 = 1;
          break label445;
        }
        onPointerUp(paramMotionEvent);
        continue;
        this.mVelocityTracker.clear();
        stopNestedScroll();
        continue;
        cancelTouch();
      }
    }
    return false;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    eatRequestLayout();
    TraceCompat.beginSection("RV OnLayout");
    dispatchLayout();
    TraceCompat.endSection();
    resumeRequestLayout(false);
    this.mFirstLayoutComplete = true;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mAdapterUpdateDuringMeasure)
    {
      eatRequestLayout();
      processAdapterUpdatesAndSetAnimationFlags();
      if (this.mState.mRunPredictiveAnimations)
      {
        State.access$1502(this.mState, true);
        this.mAdapterUpdateDuringMeasure = false;
        resumeRequestLayout(false);
      }
    }
    else
    {
      if (this.mAdapter == null)
        break label107;
      this.mState.mItemCount = this.mAdapter.getItemCount();
      label65: if (this.mLayout != null)
        break label118;
      defaultOnMeasure(paramInt1, paramInt2);
    }
    while (true)
    {
      State.access$1502(this.mState, false);
      return;
      this.mAdapterHelper.consumeUpdatesInOnePass();
      State.access$1502(this.mState, false);
      break;
      label107: this.mState.mItemCount = 0;
      break label65;
      label118: this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
    }
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    this.mPendingSavedState = ((SavedState)paramParcelable);
    super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
    if ((this.mLayout != null) && (this.mPendingSavedState.mLayoutState != null))
      this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
  }

  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (this.mPendingSavedState != null)
    {
      localSavedState.copyFrom(this.mPendingSavedState);
      return localSavedState;
    }
    if (this.mLayout != null)
    {
      localSavedState.mLayoutState = this.mLayout.onSaveInstanceState();
      return localSavedState;
    }
    localSavedState.mLayoutState = null;
    return localSavedState;
  }

  public void onScrollStateChanged(int paramInt)
  {
  }

  public void onScrolled(int paramInt1, int paramInt2)
  {
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4))
      invalidateGlows();
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (dispatchOnItemTouch(paramMotionEvent))
    {
      cancelTouch();
      return true;
    }
    boolean bool1 = this.mLayout.canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain();
    this.mVelocityTracker.addMovement(paramMotionEvent);
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    int j = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (i == 0)
    {
      int[] arrayOfInt3 = this.mNestedOffsets;
      this.mNestedOffsets[1] = 0;
      arrayOfInt3[0] = 0;
    }
    localMotionEvent.offsetLocation(this.mNestedOffsets[0], this.mNestedOffsets[1]);
    switch (i)
    {
    case 4:
    default:
    case 0:
    case 5:
    case 2:
    case 6:
    case 1:
    case 3:
    }
    while (true)
    {
      localMotionEvent.recycle();
      return true;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
      int i8 = (int)(0.5F + paramMotionEvent.getX());
      this.mLastTouchX = i8;
      this.mInitialTouchX = i8;
      int i9 = (int)(0.5F + paramMotionEvent.getY());
      this.mLastTouchY = i9;
      this.mInitialTouchY = i9;
      int i10 = 0;
      if (bool1)
        i10 = 0x0 | 0x1;
      if (bool2)
        i10 |= 2;
      startNestedScroll(i10);
      continue;
      this.mScrollPointerId = MotionEventCompat.getPointerId(paramMotionEvent, j);
      int i6 = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, j));
      this.mLastTouchX = i6;
      this.mInitialTouchX = i6;
      int i7 = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, j));
      this.mLastTouchY = i7;
      this.mInitialTouchY = i7;
      continue;
      int k = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mScrollPointerId);
      if (k < 0)
      {
        Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
        return false;
      }
      int m = (int)(0.5F + MotionEventCompat.getX(paramMotionEvent, k));
      int n = (int)(0.5F + MotionEventCompat.getY(paramMotionEvent, k));
      int i1 = this.mLastTouchX - m;
      int i2 = this.mLastTouchY - n;
      if (dispatchNestedPreScroll(i1, i2, this.mScrollConsumed, this.mScrollOffset))
      {
        i1 -= this.mScrollConsumed[0];
        i2 -= this.mScrollConsumed[1];
        localMotionEvent.offsetLocation(this.mScrollOffset[0], this.mScrollOffset[1]);
        int[] arrayOfInt1 = this.mNestedOffsets;
        arrayOfInt1[0] += this.mScrollOffset[0];
        int[] arrayOfInt2 = this.mNestedOffsets;
        arrayOfInt2[1] += this.mScrollOffset[1];
      }
      if (this.mScrollState != 1)
      {
        int i3 = 0;
        if (bool1)
        {
          int i4 = Math.abs(i1);
          int i5 = this.mTouchSlop;
          i3 = 0;
          if (i4 > i5)
          {
            if (i1 <= 0)
              break label677;
            i1 -= this.mTouchSlop;
            label563: i3 = 1;
          }
        }
        if ((bool2) && (Math.abs(i2) > this.mTouchSlop))
        {
          if (i2 <= 0)
            break label689;
          i2 -= this.mTouchSlop;
          label596: i3 = 1;
        }
        if (i3 != 0)
          setScrollState(1);
      }
      if (this.mScrollState == 1)
      {
        this.mLastTouchX = (m - this.mScrollOffset[0]);
        this.mLastTouchY = (n - this.mScrollOffset[1]);
        if (bool1)
          label647: if (!bool2)
            break label707;
        while (scrollByInternal(i1, i2, localMotionEvent))
        {
          getParent().requestDisallowInterceptTouchEvent(true);
          break;
          label677: i1 += this.mTouchSlop;
          break label563;
          label689: i2 += this.mTouchSlop;
          break label596;
          i1 = 0;
          break label647;
          label707: i2 = 0;
        }
        onPointerUp(paramMotionEvent);
        continue;
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
        float f1;
        if (bool1)
        {
          f1 = -VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mScrollPointerId);
          label754: if (!bool2)
            break label824;
        }
        label824: for (float f2 = -VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mScrollPointerId); ; f2 = 0.0F)
        {
          if (((f1 == 0.0F) && (f2 == 0.0F)) || (!fling((int)f1, (int)f2)))
            setScrollState(0);
          this.mVelocityTracker.clear();
          releaseGlows();
          break;
          f1 = 0.0F;
          break label754;
        }
        cancelTouch();
      }
    }
  }

  void rebindUpdatedViewHolders()
  {
    int i = this.mChildHelper.getChildCount();
    int j = 0;
    if (j < i)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(j));
      if ((localViewHolder == null) || (localViewHolder.shouldIgnore()));
      while (true)
      {
        j++;
        break;
        if ((localViewHolder.isRemoved()) || (localViewHolder.isInvalid()))
        {
          requestLayout();
        }
        else if (localViewHolder.needsUpdate())
        {
          int k = this.mAdapter.getItemViewType(localViewHolder.mPosition);
          if (localViewHolder.getItemViewType() != k)
            break label130;
          if ((!localViewHolder.isChanged()) || (!supportsChangeAnimations()))
            this.mAdapter.bindViewHolder(localViewHolder, localViewHolder.mPosition);
          else
            requestLayout();
        }
      }
      label130: requestLayout();
    }
  }

  protected void removeDetachedView(View paramView, boolean paramBoolean)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null)
    {
      if (!localViewHolder.isTmpDetached())
        break label32;
      localViewHolder.clearTmpDetachFlag();
    }
    label32: 
    while (localViewHolder.shouldIgnore())
    {
      dispatchChildDetached(paramView);
      super.removeDetachedView(paramView, paramBoolean);
      return;
    }
    throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + localViewHolder);
  }

  public void removeItemDecoration(ItemDecoration paramItemDecoration)
  {
    if (this.mLayout != null)
      this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
    this.mItemDecorations.remove(paramItemDecoration);
    if (this.mItemDecorations.isEmpty())
      if (ViewCompat.getOverScrollMode(this) != 2)
        break label60;
    label60: for (boolean bool = true; ; bool = false)
    {
      setWillNotDraw(bool);
      markItemDecorInsetsDirty();
      requestLayout();
      return;
    }
  }

  public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener)
  {
    if (this.mOnChildAttachStateListeners == null)
      return;
    this.mOnChildAttachStateListeners.remove(paramOnChildAttachStateChangeListener);
  }

  public void removeOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener)
  {
    this.mOnItemTouchListeners.remove(paramOnItemTouchListener);
    if (this.mActiveOnItemTouchListener == paramOnItemTouchListener)
      this.mActiveOnItemTouchListener = null;
  }

  public void removeOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    if (this.mScrollListeners != null)
      this.mScrollListeners.remove(paramOnScrollListener);
  }

  public void requestChildFocus(View paramView1, View paramView2)
  {
    if ((!this.mLayout.onRequestChildFocus(this, this.mState, paramView1, paramView2)) && (paramView2 != null))
    {
      this.mTempRect.set(0, 0, paramView2.getWidth(), paramView2.getHeight());
      ViewGroup.LayoutParams localLayoutParams = paramView2.getLayoutParams();
      if ((localLayoutParams instanceof LayoutParams))
      {
        LayoutParams localLayoutParams1 = (LayoutParams)localLayoutParams;
        if (!localLayoutParams1.mInsetsDirty)
        {
          Rect localRect2 = localLayoutParams1.mDecorInsets;
          Rect localRect3 = this.mTempRect;
          localRect3.left -= localRect2.left;
          Rect localRect4 = this.mTempRect;
          localRect4.right += localRect2.right;
          Rect localRect5 = this.mTempRect;
          localRect5.top -= localRect2.top;
          Rect localRect6 = this.mTempRect;
          localRect6.bottom += localRect2.bottom;
        }
      }
      offsetDescendantRectToMyCoords(paramView2, this.mTempRect);
      offsetRectIntoDescendantCoords(paramView1, this.mTempRect);
      Rect localRect1 = this.mTempRect;
      boolean bool1 = this.mFirstLayoutComplete;
      boolean bool2 = false;
      if (!bool1)
        bool2 = true;
      requestChildRectangleOnScreen(paramView1, localRect1, bool2);
    }
    super.requestChildFocus(paramView1, paramView2);
  }

  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    return this.mLayout.requestChildRectangleOnScreen(this, paramView, paramRect, paramBoolean);
  }

  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    int i = this.mOnItemTouchListeners.size();
    for (int j = 0; j < i; j++)
      ((OnItemTouchListener)this.mOnItemTouchListeners.get(j)).onRequestDisallowInterceptTouchEvent(paramBoolean);
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }

  public void requestLayout()
  {
    if (!this.mEatRequestLayout)
    {
      super.requestLayout();
      return;
    }
    this.mLayoutRequestEaten = true;
  }

  void resumeRequestLayout(boolean paramBoolean)
  {
    if (this.mEatRequestLayout)
    {
      if ((paramBoolean) && (this.mLayoutRequestEaten) && (this.mLayout != null) && (this.mAdapter != null))
        dispatchLayout();
      this.mEatRequestLayout = false;
      this.mLayoutRequestEaten = false;
    }
  }

  void saveOldPositions()
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(j));
      if (!localViewHolder.shouldIgnore())
        localViewHolder.saveOldPosition();
    }
  }

  public void scrollBy(int paramInt1, int paramInt2)
  {
    if (this.mLayout == null)
      Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
    boolean bool1;
    boolean bool2;
    do
    {
      return;
      bool1 = this.mLayout.canScrollHorizontally();
      bool2 = this.mLayout.canScrollVertically();
    }
    while ((!bool1) && (!bool2));
    if (bool1)
      if (!bool2)
        break label66;
    while (true)
    {
      scrollByInternal(paramInt1, paramInt2, null);
      return;
      paramInt1 = 0;
      break;
      label66: paramInt2 = 0;
    }
  }

  boolean scrollByInternal(int paramInt1, int paramInt2, MotionEvent paramMotionEvent)
  {
    consumePendingUpdateOperations();
    Adapter localAdapter = this.mAdapter;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    if (localAdapter != null)
    {
      eatRequestLayout();
      onEnterLayoutOrScroll();
      TraceCompat.beginSection("RV Scroll");
      i = 0;
      k = 0;
      if (paramInt1 != 0)
      {
        i = this.mLayout.scrollHorizontallyBy(paramInt1, this.mRecycler, this.mState);
        k = paramInt1 - i;
      }
      j = 0;
      m = 0;
      if (paramInt2 != 0)
      {
        j = this.mLayout.scrollVerticallyBy(paramInt2, this.mRecycler, this.mState);
        m = paramInt2 - j;
      }
      TraceCompat.endSection();
      if (supportsChangeAnimations())
      {
        int n = this.mChildHelper.getChildCount();
        int i1 = 0;
        if (i1 < n)
        {
          View localView1 = this.mChildHelper.getChildAt(i1);
          ViewHolder localViewHolder1 = getChildViewHolder(localView1);
          ViewHolder localViewHolder2;
          if ((localViewHolder1 != null) && (localViewHolder1.mShadowingHolder != null))
          {
            localViewHolder2 = localViewHolder1.mShadowingHolder;
            if (localViewHolder2 == null)
              break label258;
          }
          label258: for (View localView2 = localViewHolder2.itemView; ; localView2 = null)
          {
            if (localView2 != null)
            {
              int i2 = localView1.getLeft();
              int i3 = localView1.getTop();
              if ((i2 != localView2.getLeft()) || (i3 != localView2.getTop()))
                localView2.layout(i2, i3, i2 + localView2.getWidth(), i3 + localView2.getHeight());
            }
            i1++;
            break;
          }
        }
      }
      onExitLayoutOrScroll();
      resumeRequestLayout(false);
    }
    if (!this.mItemDecorations.isEmpty())
      invalidate();
    if (dispatchNestedScroll(i, j, k, m, this.mScrollOffset))
    {
      this.mLastTouchX -= this.mScrollOffset[0];
      this.mLastTouchY -= this.mScrollOffset[1];
      paramMotionEvent.offsetLocation(this.mScrollOffset[0], this.mScrollOffset[1]);
      int[] arrayOfInt1 = this.mNestedOffsets;
      arrayOfInt1[0] += this.mScrollOffset[0];
      int[] arrayOfInt2 = this.mNestedOffsets;
      arrayOfInt2[1] += this.mScrollOffset[1];
    }
    while (true)
    {
      if ((i != 0) || (j != 0))
        dispatchOnScrolled(i, j);
      if (!awakenScrollBars())
        invalidate();
      if ((i == 0) && (j == 0))
        break;
      return true;
      if (ViewCompat.getOverScrollMode(this) != 2)
      {
        if (paramMotionEvent != null)
          pullGlows(paramMotionEvent.getX(), k, paramMotionEvent.getY(), m);
        considerReleasingGlowsOnScroll(paramInt1, paramInt2);
      }
    }
    return false;
  }

  public void scrollTo(int paramInt1, int paramInt2)
  {
    throw new UnsupportedOperationException("RecyclerView does not support scrolling to an absolute position.");
  }

  public void scrollToPosition(int paramInt)
  {
    stopScroll();
    if (this.mLayout == null)
    {
      Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    this.mLayout.scrollToPosition(paramInt);
    awakenScrollBars();
  }

  public void sendAccessibilityEventUnchecked(AccessibilityEvent paramAccessibilityEvent)
  {
    if (shouldDeferAccessibilityEvent(paramAccessibilityEvent))
      return;
    super.sendAccessibilityEventUnchecked(paramAccessibilityEvent);
  }

  public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate paramRecyclerViewAccessibilityDelegate)
  {
    this.mAccessibilityDelegate = paramRecyclerViewAccessibilityDelegate;
    ViewCompat.setAccessibilityDelegate(this, this.mAccessibilityDelegate);
  }

  public void setAdapter(Adapter paramAdapter)
  {
    setAdapterInternal(paramAdapter, false, true);
    requestLayout();
  }

  public void setChildDrawingOrderCallback(ChildDrawingOrderCallback paramChildDrawingOrderCallback)
  {
    if (paramChildDrawingOrderCallback == this.mChildDrawingOrderCallback)
      return;
    this.mChildDrawingOrderCallback = paramChildDrawingOrderCallback;
    if (this.mChildDrawingOrderCallback != null);
    for (boolean bool = true; ; bool = false)
    {
      setChildrenDrawingOrderEnabled(bool);
      return;
    }
  }

  public void setClipToPadding(boolean paramBoolean)
  {
    if (paramBoolean != this.mClipToPadding)
      invalidateGlows();
    this.mClipToPadding = paramBoolean;
    super.setClipToPadding(paramBoolean);
    if (this.mFirstLayoutComplete)
      requestLayout();
  }

  public void setHasFixedSize(boolean paramBoolean)
  {
    this.mHasFixedSize = paramBoolean;
  }

  public void setItemAnimator(ItemAnimator paramItemAnimator)
  {
    if (this.mItemAnimator != null)
    {
      this.mItemAnimator.endAnimations();
      this.mItemAnimator.setListener(null);
    }
    this.mItemAnimator = paramItemAnimator;
    if (this.mItemAnimator != null)
      this.mItemAnimator.setListener(this.mItemAnimatorListener);
  }

  public void setItemViewCacheSize(int paramInt)
  {
    this.mRecycler.setViewCacheSize(paramInt);
  }

  public void setLayoutManager(LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager == this.mLayout)
      return;
    if (this.mLayout != null)
    {
      if (this.mIsAttached)
        this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
      this.mLayout.setRecyclerView(null);
    }
    this.mRecycler.clear();
    this.mChildHelper.removeAllViewsUnfiltered();
    this.mLayout = paramLayoutManager;
    if (paramLayoutManager != null)
    {
      if (paramLayoutManager.mRecyclerView != null)
        throw new IllegalArgumentException("LayoutManager " + paramLayoutManager + " is already attached to a RecyclerView: " + paramLayoutManager.mRecyclerView);
      this.mLayout.setRecyclerView(this);
      if (this.mIsAttached)
        this.mLayout.dispatchAttachedToWindow(this);
    }
    requestLayout();
  }

  public void setNestedScrollingEnabled(boolean paramBoolean)
  {
    this.mScrollingChildHelper.setNestedScrollingEnabled(paramBoolean);
  }

  @Deprecated
  public void setOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    this.mScrollListener = paramOnScrollListener;
  }

  public void setRecycledViewPool(RecycledViewPool paramRecycledViewPool)
  {
    this.mRecycler.setRecycledViewPool(paramRecycledViewPool);
  }

  public void setRecyclerListener(RecyclerListener paramRecyclerListener)
  {
    this.mRecyclerListener = paramRecyclerListener;
  }

  public void setScrollingTouchSlop(int paramInt)
  {
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    switch (paramInt)
    {
    default:
      Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + paramInt + "; using default value");
    case 0:
      this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
      return;
    case 1:
    }
    this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(localViewConfiguration);
  }

  public void setViewCacheExtension(ViewCacheExtension paramViewCacheExtension)
  {
    this.mRecycler.setViewCacheExtension(paramViewCacheExtension);
  }

  boolean shouldDeferAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (isRunningLayoutOrScroll())
    {
      int i = 0;
      if (paramAccessibilityEvent != null)
        i = AccessibilityEventCompat.getContentChangeTypes(paramAccessibilityEvent);
      if (i == 0)
        i = 0;
      this.mEatenAccessibilityChangeFlags = (i | this.mEatenAccessibilityChangeFlags);
      return true;
    }
    return false;
  }

  public void smoothScrollBy(int paramInt1, int paramInt2)
  {
    if (this.mLayout == null)
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
    do
    {
      return;
      if (!this.mLayout.canScrollHorizontally())
        paramInt1 = 0;
      if (!this.mLayout.canScrollVertically())
        paramInt2 = 0;
    }
    while ((paramInt1 == 0) && (paramInt2 == 0));
    this.mViewFlinger.smoothScrollBy(paramInt1, paramInt2);
  }

  public void smoothScrollToPosition(int paramInt)
  {
    if (this.mLayout == null)
    {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    this.mLayout.smoothScrollToPosition(this, this.mState, paramInt);
  }

  public boolean startNestedScroll(int paramInt)
  {
    return this.mScrollingChildHelper.startNestedScroll(paramInt);
  }

  public void stopNestedScroll()
  {
    this.mScrollingChildHelper.stopNestedScroll();
  }

  public void stopScroll()
  {
    setScrollState(0);
    stopScrollersInternal();
  }

  public void swapAdapter(Adapter paramAdapter, boolean paramBoolean)
  {
    setAdapterInternal(paramAdapter, true, paramBoolean);
    setDataSetChangedAfterLayout();
    requestLayout();
  }

  void viewRangeUpdate(int paramInt1, int paramInt2)
  {
    int i = this.mChildHelper.getUnfilteredChildCount();
    int j = paramInt1 + paramInt2;
    int k = 0;
    if (k < i)
    {
      View localView = this.mChildHelper.getUnfilteredChildAt(k);
      ViewHolder localViewHolder = getChildViewHolderInt(localView);
      if ((localViewHolder == null) || (localViewHolder.shouldIgnore()));
      while (true)
      {
        k++;
        break;
        if ((localViewHolder.mPosition >= paramInt1) && (localViewHolder.mPosition < j))
        {
          localViewHolder.addFlags(2);
          if (supportsChangeAnimations())
            localViewHolder.addFlags(64);
          ((LayoutParams)localView.getLayoutParams()).mInsetsDirty = true;
        }
      }
    }
    this.mRecycler.viewRangeUpdate(paramInt1, paramInt2);
  }

  public static abstract class Adapter<VH extends RecyclerView.ViewHolder>
  {
    private boolean mHasStableIds = false;
    private final RecyclerView.AdapterDataObservable mObservable = new RecyclerView.AdapterDataObservable();

    public final void bindViewHolder(VH paramVH, int paramInt)
    {
      paramVH.mPosition = paramInt;
      if (hasStableIds())
        paramVH.mItemId = getItemId(paramInt);
      paramVH.setFlags(1, 519);
      TraceCompat.beginSection("RV OnBindView");
      onBindViewHolder(paramVH, paramInt);
      TraceCompat.endSection();
    }

    public final VH createViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      TraceCompat.beginSection("RV CreateView");
      RecyclerView.ViewHolder localViewHolder = onCreateViewHolder(paramViewGroup, paramInt);
      localViewHolder.mItemViewType = paramInt;
      TraceCompat.endSection();
      return localViewHolder;
    }

    public abstract int getItemCount();

    public long getItemId(int paramInt)
    {
      return -1L;
    }

    public int getItemViewType(int paramInt)
    {
      return 0;
    }

    public final boolean hasObservers()
    {
      return this.mObservable.hasObservers();
    }

    public final boolean hasStableIds()
    {
      return this.mHasStableIds;
    }

    public final void notifyDataSetChanged()
    {
      this.mObservable.notifyChanged();
    }

    public final void notifyItemChanged(int paramInt)
    {
      this.mObservable.notifyItemRangeChanged(paramInt, 1);
    }

    public final void notifyItemInserted(int paramInt)
    {
      this.mObservable.notifyItemRangeInserted(paramInt, 1);
    }

    public final void notifyItemMoved(int paramInt1, int paramInt2)
    {
      this.mObservable.notifyItemMoved(paramInt1, paramInt2);
    }

    public final void notifyItemRangeChanged(int paramInt1, int paramInt2)
    {
      this.mObservable.notifyItemRangeChanged(paramInt1, paramInt2);
    }

    public final void notifyItemRangeInserted(int paramInt1, int paramInt2)
    {
      this.mObservable.notifyItemRangeInserted(paramInt1, paramInt2);
    }

    public final void notifyItemRangeRemoved(int paramInt1, int paramInt2)
    {
      this.mObservable.notifyItemRangeRemoved(paramInt1, paramInt2);
    }

    public final void notifyItemRemoved(int paramInt)
    {
      this.mObservable.notifyItemRangeRemoved(paramInt, 1);
    }

    public void onAttachedToRecyclerView(RecyclerView paramRecyclerView)
    {
    }

    public abstract void onBindViewHolder(VH paramVH, int paramInt);

    public abstract VH onCreateViewHolder(ViewGroup paramViewGroup, int paramInt);

    public void onDetachedFromRecyclerView(RecyclerView paramRecyclerView)
    {
    }

    public boolean onFailedToRecycleView(VH paramVH)
    {
      return false;
    }

    public void onViewAttachedToWindow(VH paramVH)
    {
    }

    public void onViewDetachedFromWindow(VH paramVH)
    {
    }

    public void onViewRecycled(VH paramVH)
    {
    }

    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      this.mObservable.registerObserver(paramAdapterDataObserver);
    }

    public void setHasStableIds(boolean paramBoolean)
    {
      if (hasObservers())
        throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
      this.mHasStableIds = paramBoolean;
    }

    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      this.mObservable.unregisterObserver(paramAdapterDataObserver);
    }
  }

  static class AdapterDataObservable extends Observable<RecyclerView.AdapterDataObserver>
  {
    public boolean hasObservers()
    {
      return !this.mObservers.isEmpty();
    }

    public void notifyChanged()
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onChanged();
    }

    public void notifyItemMoved(int paramInt1, int paramInt2)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(paramInt1, paramInt2, 1);
    }

    public void notifyItemRangeChanged(int paramInt1, int paramInt2)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(paramInt1, paramInt2);
    }

    public void notifyItemRangeInserted(int paramInt1, int paramInt2)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(paramInt1, paramInt2);
    }

    public void notifyItemRangeRemoved(int paramInt1, int paramInt2)
    {
      for (int i = -1 + this.mObservers.size(); i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(paramInt1, paramInt2);
    }
  }

  public static abstract class AdapterDataObserver
  {
    public void onChanged()
    {
    }

    public void onItemRangeChanged(int paramInt1, int paramInt2)
    {
    }

    public void onItemRangeInserted(int paramInt1, int paramInt2)
    {
    }

    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
    }
  }

  public static abstract interface ChildDrawingOrderCallback
  {
    public abstract int onGetChildDrawingOrder(int paramInt1, int paramInt2);
  }

  public static abstract class ItemAnimator
  {
    private long mAddDuration = 120L;
    private long mChangeDuration = 250L;
    private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
    private ItemAnimatorListener mListener = null;
    private long mMoveDuration = 250L;
    private long mRemoveDuration = 120L;
    private boolean mSupportsChangeAnimations = true;

    public abstract boolean animateAdd(RecyclerView.ViewHolder paramViewHolder);

    public abstract boolean animateChange(RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    public abstract boolean animateMove(RecyclerView.ViewHolder paramViewHolder, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

    public abstract boolean animateRemove(RecyclerView.ViewHolder paramViewHolder);

    public final void dispatchAddFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      onAddFinished(paramViewHolder);
      if (this.mListener != null)
        this.mListener.onAddFinished(paramViewHolder);
    }

    public final void dispatchAddStarting(RecyclerView.ViewHolder paramViewHolder)
    {
      onAddStarting(paramViewHolder);
    }

    public final void dispatchAnimationsFinished()
    {
      int i = this.mFinishedListeners.size();
      for (int j = 0; j < i; j++)
        ((ItemAnimatorFinishedListener)this.mFinishedListeners.get(j)).onAnimationsFinished();
      this.mFinishedListeners.clear();
    }

    public final void dispatchChangeFinished(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
    {
      onChangeFinished(paramViewHolder, paramBoolean);
      if (this.mListener != null)
        this.mListener.onChangeFinished(paramViewHolder);
    }

    public final void dispatchChangeStarting(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
    {
      onChangeStarting(paramViewHolder, paramBoolean);
    }

    public final void dispatchMoveFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      onMoveFinished(paramViewHolder);
      if (this.mListener != null)
        this.mListener.onMoveFinished(paramViewHolder);
    }

    public final void dispatchMoveStarting(RecyclerView.ViewHolder paramViewHolder)
    {
      onMoveStarting(paramViewHolder);
    }

    public final void dispatchRemoveFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      onRemoveFinished(paramViewHolder);
      if (this.mListener != null)
        this.mListener.onRemoveFinished(paramViewHolder);
    }

    public final void dispatchRemoveStarting(RecyclerView.ViewHolder paramViewHolder)
    {
      onRemoveStarting(paramViewHolder);
    }

    public abstract void endAnimation(RecyclerView.ViewHolder paramViewHolder);

    public abstract void endAnimations();

    public long getAddDuration()
    {
      return this.mAddDuration;
    }

    public long getChangeDuration()
    {
      return this.mChangeDuration;
    }

    public long getMoveDuration()
    {
      return this.mMoveDuration;
    }

    public long getRemoveDuration()
    {
      return this.mRemoveDuration;
    }

    public boolean getSupportsChangeAnimations()
    {
      return this.mSupportsChangeAnimations;
    }

    public abstract boolean isRunning();

    public final boolean isRunning(ItemAnimatorFinishedListener paramItemAnimatorFinishedListener)
    {
      boolean bool = isRunning();
      if (paramItemAnimatorFinishedListener != null)
      {
        if (!bool)
          paramItemAnimatorFinishedListener.onAnimationsFinished();
      }
      else
        return bool;
      this.mFinishedListeners.add(paramItemAnimatorFinishedListener);
      return bool;
    }

    public void onAddFinished(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public void onAddStarting(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public void onChangeFinished(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
    {
    }

    public void onChangeStarting(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
    {
    }

    public void onMoveFinished(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public void onMoveStarting(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public void onRemoveFinished(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public void onRemoveStarting(RecyclerView.ViewHolder paramViewHolder)
    {
    }

    public abstract void runPendingAnimations();

    public void setAddDuration(long paramLong)
    {
      this.mAddDuration = paramLong;
    }

    public void setChangeDuration(long paramLong)
    {
      this.mChangeDuration = paramLong;
    }

    void setListener(ItemAnimatorListener paramItemAnimatorListener)
    {
      this.mListener = paramItemAnimatorListener;
    }

    public void setMoveDuration(long paramLong)
    {
      this.mMoveDuration = paramLong;
    }

    public void setRemoveDuration(long paramLong)
    {
      this.mRemoveDuration = paramLong;
    }

    public void setSupportsChangeAnimations(boolean paramBoolean)
    {
      this.mSupportsChangeAnimations = paramBoolean;
    }

    public static abstract interface ItemAnimatorFinishedListener
    {
      public abstract void onAnimationsFinished();
    }

    static abstract interface ItemAnimatorListener
    {
      public abstract void onAddFinished(RecyclerView.ViewHolder paramViewHolder);

      public abstract void onChangeFinished(RecyclerView.ViewHolder paramViewHolder);

      public abstract void onMoveFinished(RecyclerView.ViewHolder paramViewHolder);

      public abstract void onRemoveFinished(RecyclerView.ViewHolder paramViewHolder);
    }
  }

  private class ItemAnimatorRestoreListener
    implements RecyclerView.ItemAnimator.ItemAnimatorListener
  {
    private ItemAnimatorRestoreListener()
    {
    }

    public void onAddFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if (!RecyclerView.ViewHolder.access$5200(paramViewHolder))
        RecyclerView.this.removeAnimatingView(paramViewHolder.itemView);
    }

    public void onChangeFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if ((paramViewHolder.mShadowedHolder != null) && (paramViewHolder.mShadowingHolder == null))
      {
        paramViewHolder.mShadowedHolder = null;
        paramViewHolder.setFlags(-65, RecyclerView.ViewHolder.access$5300(paramViewHolder));
      }
      paramViewHolder.mShadowingHolder = null;
      if (!RecyclerView.ViewHolder.access$5200(paramViewHolder))
        RecyclerView.this.removeAnimatingView(paramViewHolder.itemView);
    }

    public void onMoveFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if (!RecyclerView.ViewHolder.access$5200(paramViewHolder))
        RecyclerView.this.removeAnimatingView(paramViewHolder.itemView);
    }

    public void onRemoveFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if ((!RecyclerView.this.removeAnimatingView(paramViewHolder.itemView)) && (paramViewHolder.isTmpDetached()))
        RecyclerView.this.removeDetachedView(paramViewHolder.itemView, false);
    }
  }

  public static abstract class ItemDecoration
  {
    @Deprecated
    public void getItemOffsets(Rect paramRect, int paramInt, RecyclerView paramRecyclerView)
    {
      paramRect.set(0, 0, 0, 0);
    }

    public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      getItemOffsets(paramRect, ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition(), paramRecyclerView);
    }

    @Deprecated
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView)
    {
    }

    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      onDraw(paramCanvas, paramRecyclerView);
    }

    @Deprecated
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView)
    {
    }

    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      onDrawOver(paramCanvas, paramRecyclerView);
    }
  }

  private static class ItemHolderInfo
  {
    int bottom;
    RecyclerView.ViewHolder holder;
    int left;
    int right;
    int top;

    ItemHolderInfo(RecyclerView.ViewHolder paramViewHolder, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.holder = paramViewHolder;
      this.left = paramInt1;
      this.top = paramInt2;
      this.right = paramInt3;
      this.bottom = paramInt4;
    }
  }

  public static abstract class LayoutManager
  {
    ChildHelper mChildHelper;
    private boolean mIsAttachedToWindow = false;
    RecyclerView mRecyclerView;
    private boolean mRequestedSimpleAnimations = false;

    @Nullable
    RecyclerView.SmoothScroller mSmoothScroller;

    private void addViewInt(View paramView, int paramInt, boolean paramBoolean)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      RecyclerView.LayoutParams localLayoutParams;
      if ((paramBoolean) || (localViewHolder.isRemoved()))
      {
        this.mRecyclerView.mState.addToDisappearingList(paramView);
        localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
        if ((!localViewHolder.wasReturnedFromScrap()) && (!localViewHolder.isScrap()))
          break label126;
        if (!localViewHolder.isScrap())
          break label118;
        localViewHolder.unScrap();
        label67: this.mChildHelper.attachViewToParent(paramView, paramInt, paramView.getLayoutParams(), false);
      }
      while (true)
      {
        if (localLayoutParams.mPendingInvalidate)
        {
          localViewHolder.itemView.invalidate();
          localLayoutParams.mPendingInvalidate = false;
        }
        return;
        this.mRecyclerView.mState.removeFromDisappearingList(paramView);
        break;
        label118: localViewHolder.clearReturnedFromScrapFlag();
        break label67;
        label126: if (paramView.getParent() == this.mRecyclerView)
        {
          int i = this.mChildHelper.indexOfChild(paramView);
          if (paramInt == -1)
            paramInt = this.mChildHelper.getChildCount();
          if (i == -1)
            throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(paramView));
          if (i != paramInt)
            this.mRecyclerView.mLayout.moveView(i, paramInt);
        }
        else
        {
          this.mChildHelper.addView(paramView, paramInt, false);
          localLayoutParams.mInsetsDirty = true;
          if ((this.mSmoothScroller != null) && (this.mSmoothScroller.isRunning()))
            this.mSmoothScroller.onChildAttachedToWindow(paramView);
        }
      }
    }

    private void detachViewInternal(int paramInt, View paramView)
    {
      this.mChildHelper.detachViewFromParent(paramInt);
    }

    public static int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      int i = Math.max(0, paramInt1 - paramInt2);
      int k;
      int j;
      if (paramBoolean)
        if (paramInt3 >= 0)
        {
          k = paramInt3;
          j = 1073741824;
        }
      while (true)
      {
        return View.MeasureSpec.makeMeasureSpec(k, j);
        j = 0;
        k = 0;
        continue;
        if (paramInt3 >= 0)
        {
          k = paramInt3;
          j = 1073741824;
        }
        else if (paramInt3 == -1)
        {
          k = i;
          j = 1073741824;
        }
        else
        {
          j = 0;
          k = 0;
          if (paramInt3 == -2)
          {
            k = i;
            j = -2147483648;
          }
        }
      }
    }

    public static Properties getProperties(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
      Properties localProperties = new Properties();
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RecyclerView, paramInt1, paramInt2);
      localProperties.orientation = localTypedArray.getInt(R.styleable.RecyclerView_android_orientation, 1);
      localProperties.spanCount = localTypedArray.getInt(R.styleable.RecyclerView_spanCount, 1);
      localProperties.reverseLayout = localTypedArray.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
      localProperties.stackFromEnd = localTypedArray.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
      localTypedArray.recycle();
      return localProperties;
    }

    private void onSmoothScrollerStopped(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      if (this.mSmoothScroller == paramSmoothScroller)
        this.mSmoothScroller = null;
    }

    private void scrapOrRecycleView(RecyclerView.Recycler paramRecycler, int paramInt, View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.shouldIgnore())
        return;
      if ((localViewHolder.isInvalid()) && (!localViewHolder.isRemoved()) && (!localViewHolder.isChanged()) && (!this.mRecyclerView.mAdapter.hasStableIds()))
      {
        removeViewAt(paramInt);
        paramRecycler.recycleViewHolderInternal(localViewHolder);
        return;
      }
      detachViewAt(paramInt);
      paramRecycler.scrapView(paramView);
    }

    public void addDisappearingView(View paramView)
    {
      addDisappearingView(paramView, -1);
    }

    public void addDisappearingView(View paramView, int paramInt)
    {
      addViewInt(paramView, paramInt, true);
    }

    public void addView(View paramView)
    {
      addView(paramView, -1);
    }

    public void addView(View paramView, int paramInt)
    {
      addViewInt(paramView, paramInt, false);
    }

    public void assertInLayoutOrScroll(String paramString)
    {
      if (this.mRecyclerView != null)
        this.mRecyclerView.assertInLayoutOrScroll(paramString);
    }

    public void assertNotInLayoutOrScroll(String paramString)
    {
      if (this.mRecyclerView != null)
        this.mRecyclerView.assertNotInLayoutOrScroll(paramString);
    }

    public void attachView(View paramView)
    {
      attachView(paramView, -1);
    }

    public void attachView(View paramView, int paramInt)
    {
      attachView(paramView, paramInt, (RecyclerView.LayoutParams)paramView.getLayoutParams());
    }

    public void attachView(View paramView, int paramInt, RecyclerView.LayoutParams paramLayoutParams)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isRemoved())
        this.mRecyclerView.mState.addToDisappearingList(paramView);
      while (true)
      {
        this.mChildHelper.attachViewToParent(paramView, paramInt, paramLayoutParams, localViewHolder.isRemoved());
        return;
        this.mRecyclerView.mState.removeFromDisappearingList(paramView);
      }
    }

    public void calculateItemDecorationsForChild(View paramView, Rect paramRect)
    {
      if (this.mRecyclerView == null)
      {
        paramRect.set(0, 0, 0, 0);
        return;
      }
      paramRect.set(this.mRecyclerView.getItemDecorInsetsForChild(paramView));
    }

    public boolean canScrollHorizontally()
    {
      return false;
    }

    public boolean canScrollVertically()
    {
      return false;
    }

    public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      return paramLayoutParams != null;
    }

    public int computeHorizontalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }

    public int computeHorizontalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }

    public int computeHorizontalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }

    public int computeVerticalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }

    public int computeVerticalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }

    public int computeVerticalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }

    public void detachAndScrapAttachedViews(RecyclerView.Recycler paramRecycler)
    {
      for (int i = -1 + getChildCount(); i >= 0; i--)
        scrapOrRecycleView(paramRecycler, i, getChildAt(i));
    }

    public void detachAndScrapView(View paramView, RecyclerView.Recycler paramRecycler)
    {
      scrapOrRecycleView(paramRecycler, this.mChildHelper.indexOfChild(paramView), paramView);
    }

    public void detachAndScrapViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      scrapOrRecycleView(paramRecycler, paramInt, getChildAt(paramInt));
    }

    public void detachView(View paramView)
    {
      int i = this.mChildHelper.indexOfChild(paramView);
      if (i >= 0)
        detachViewInternal(i, paramView);
    }

    public void detachViewAt(int paramInt)
    {
      detachViewInternal(paramInt, getChildAt(paramInt));
    }

    void dispatchAttachedToWindow(RecyclerView paramRecyclerView)
    {
      this.mIsAttachedToWindow = true;
      onAttachedToWindow(paramRecyclerView);
    }

    void dispatchDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      this.mIsAttachedToWindow = false;
      onDetachedFromWindow(paramRecyclerView, paramRecycler);
    }

    public void endAnimation(View paramView)
    {
      if (this.mRecyclerView.mItemAnimator != null)
        this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(paramView));
    }

    public View findViewByPosition(int paramInt)
    {
      int i = getChildCount();
      int j = 0;
      if (j < i)
      {
        View localView = getChildAt(j);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if (localViewHolder == null);
        while ((localViewHolder.getLayoutPosition() != paramInt) || (localViewHolder.shouldIgnore()) || ((!this.mRecyclerView.mState.isPreLayout()) && (localViewHolder.isRemoved())))
        {
          j++;
          break;
        }
        return localView;
      }
      return null;
    }

    public abstract RecyclerView.LayoutParams generateDefaultLayoutParams();

    public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      return new RecyclerView.LayoutParams(paramContext, paramAttributeSet);
    }

    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      if ((paramLayoutParams instanceof RecyclerView.LayoutParams))
        return new RecyclerView.LayoutParams((RecyclerView.LayoutParams)paramLayoutParams);
      if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams))
        return new RecyclerView.LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
      return new RecyclerView.LayoutParams(paramLayoutParams);
    }

    public int getBaseline()
    {
      return -1;
    }

    public int getBottomDecorationHeight(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.bottom;
    }

    public View getChildAt(int paramInt)
    {
      if (this.mChildHelper != null)
        return this.mChildHelper.getChildAt(paramInt);
      return null;
    }

    public int getChildCount()
    {
      if (this.mChildHelper != null)
        return this.mChildHelper.getChildCount();
      return 0;
    }

    public boolean getClipToPadding()
    {
      return (this.mRecyclerView != null) && (this.mRecyclerView.mClipToPadding);
    }

    public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      if ((this.mRecyclerView == null) || (this.mRecyclerView.mAdapter == null));
      while (!canScrollHorizontally())
        return 1;
      return this.mRecyclerView.mAdapter.getItemCount();
    }

    public int getDecoratedBottom(View paramView)
    {
      return paramView.getBottom() + getBottomDecorationHeight(paramView);
    }

    public int getDecoratedLeft(View paramView)
    {
      return paramView.getLeft() - getLeftDecorationWidth(paramView);
    }

    public int getDecoratedMeasuredHeight(View paramView)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      return paramView.getMeasuredHeight() + localRect.top + localRect.bottom;
    }

    public int getDecoratedMeasuredWidth(View paramView)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      return paramView.getMeasuredWidth() + localRect.left + localRect.right;
    }

    public int getDecoratedRight(View paramView)
    {
      return paramView.getRight() + getRightDecorationWidth(paramView);
    }

    public int getDecoratedTop(View paramView)
    {
      return paramView.getTop() - getTopDecorationHeight(paramView);
    }

    public View getFocusedChild()
    {
      View localView;
      if (this.mRecyclerView == null)
        localView = null;
      do
      {
        return localView;
        localView = this.mRecyclerView.getFocusedChild();
      }
      while ((localView != null) && (!this.mChildHelper.isHidden(localView)));
      return null;
    }

    public int getHeight()
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.getHeight();
      return 0;
    }

    public int getItemCount()
    {
      if (this.mRecyclerView != null);
      for (RecyclerView.Adapter localAdapter = this.mRecyclerView.getAdapter(); localAdapter != null; localAdapter = null)
        return localAdapter.getItemCount();
      return 0;
    }

    public int getItemViewType(View paramView)
    {
      return RecyclerView.getChildViewHolderInt(paramView).getItemViewType();
    }

    public int getLayoutDirection()
    {
      return ViewCompat.getLayoutDirection(this.mRecyclerView);
    }

    public int getLeftDecorationWidth(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.left;
    }

    public int getMinimumHeight()
    {
      return ViewCompat.getMinimumHeight(this.mRecyclerView);
    }

    public int getMinimumWidth()
    {
      return ViewCompat.getMinimumWidth(this.mRecyclerView);
    }

    public int getPaddingBottom()
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.getPaddingBottom();
      return 0;
    }

    public int getPaddingEnd()
    {
      if (this.mRecyclerView != null)
        return ViewCompat.getPaddingEnd(this.mRecyclerView);
      return 0;
    }

    public int getPaddingLeft()
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.getPaddingLeft();
      return 0;
    }

    public int getPaddingRight()
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.getPaddingRight();
      return 0;
    }

    public int getPaddingStart()
    {
      if (this.mRecyclerView != null)
        return ViewCompat.getPaddingStart(this.mRecyclerView);
      return 0;
    }

    public int getPaddingTop()
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.getPaddingTop();
      return 0;
    }

    public int getPosition(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition();
    }

    public int getRightDecorationWidth(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.right;
    }

    public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      if ((this.mRecyclerView == null) || (this.mRecyclerView.mAdapter == null));
      while (!canScrollVertically())
        return 1;
      return this.mRecyclerView.mAdapter.getItemCount();
    }

    public int getSelectionModeForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }

    public int getTopDecorationHeight(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets.top;
    }

    public int getWidth()
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.getWidth();
      return 0;
    }

    public boolean hasFocus()
    {
      return (this.mRecyclerView != null) && (this.mRecyclerView.hasFocus());
    }

    public void ignoreView(View paramView)
    {
      if ((paramView.getParent() != this.mRecyclerView) || (this.mRecyclerView.indexOfChild(paramView) == -1))
        throw new IllegalArgumentException("View should be fully attached to be ignored");
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      localViewHolder.addFlags(128);
      this.mRecyclerView.mState.onViewIgnored(localViewHolder);
    }

    public boolean isAttachedToWindow()
    {
      return this.mIsAttachedToWindow;
    }

    public boolean isFocused()
    {
      return (this.mRecyclerView != null) && (this.mRecyclerView.isFocused());
    }

    public boolean isLayoutHierarchical(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return false;
    }

    public boolean isSmoothScrolling()
    {
      return (this.mSmoothScroller != null) && (this.mSmoothScroller.isRunning());
    }

    public void layoutDecorated(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      Rect localRect = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).mDecorInsets;
      paramView.layout(paramInt1 + localRect.left, paramInt2 + localRect.top, paramInt3 - localRect.right, paramInt4 - localRect.bottom);
    }

    public void measureChild(View paramView, int paramInt1, int paramInt2)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = this.mRecyclerView.getItemDecorInsetsForChild(paramView);
      int i = paramInt1 + (localRect.left + localRect.right);
      int j = paramInt2 + (localRect.top + localRect.bottom);
      paramView.measure(getChildMeasureSpec(getWidth(), i + (getPaddingLeft() + getPaddingRight()), localLayoutParams.width, canScrollHorizontally()), getChildMeasureSpec(getHeight(), j + (getPaddingTop() + getPaddingBottom()), localLayoutParams.height, canScrollVertically()));
    }

    public void measureChildWithMargins(View paramView, int paramInt1, int paramInt2)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = this.mRecyclerView.getItemDecorInsetsForChild(paramView);
      int i = paramInt1 + (localRect.left + localRect.right);
      int j = paramInt2 + (localRect.top + localRect.bottom);
      paramView.measure(getChildMeasureSpec(getWidth(), i + (getPaddingLeft() + getPaddingRight() + localLayoutParams.leftMargin + localLayoutParams.rightMargin), localLayoutParams.width, canScrollHorizontally()), getChildMeasureSpec(getHeight(), j + (getPaddingTop() + getPaddingBottom() + localLayoutParams.topMargin + localLayoutParams.bottomMargin), localLayoutParams.height, canScrollVertically()));
    }

    public void moveView(int paramInt1, int paramInt2)
    {
      View localView = getChildAt(paramInt1);
      if (localView == null)
        throw new IllegalArgumentException("Cannot move a child from non-existing index:" + paramInt1);
      detachViewAt(paramInt1);
      attachView(localView, paramInt2);
    }

    public void offsetChildrenHorizontal(int paramInt)
    {
      if (this.mRecyclerView != null)
        this.mRecyclerView.offsetChildrenHorizontal(paramInt);
    }

    public void offsetChildrenVertical(int paramInt)
    {
      if (this.mRecyclerView != null)
        this.mRecyclerView.offsetChildrenVertical(paramInt);
    }

    public void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2)
    {
    }

    public boolean onAddFocusables(RecyclerView paramRecyclerView, ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
    {
      return false;
    }

    public void onAttachedToWindow(RecyclerView paramRecyclerView)
    {
    }

    @Deprecated
    public void onDetachedFromWindow(RecyclerView paramRecyclerView)
    {
    }

    public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      onDetachedFromWindow(paramRecyclerView);
    }

    @Nullable
    public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return null;
    }

    public void onInitializeAccessibilityEvent(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AccessibilityEvent paramAccessibilityEvent)
    {
      int i = 1;
      AccessibilityRecordCompat localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAccessibilityEvent);
      if ((this.mRecyclerView == null) || (localAccessibilityRecordCompat == null))
        return;
      if ((ViewCompat.canScrollVertically(this.mRecyclerView, i)) || (ViewCompat.canScrollVertically(this.mRecyclerView, -1)) || (ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)) || (ViewCompat.canScrollHorizontally(this.mRecyclerView, i)));
      while (true)
      {
        localAccessibilityRecordCompat.setScrollable(i);
        if (this.mRecyclerView.mAdapter == null)
          break;
        localAccessibilityRecordCompat.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
        return;
        int j = 0;
      }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramAccessibilityEvent);
    }

    void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramAccessibilityNodeInfoCompat);
    }

    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      if ((ViewCompat.canScrollVertically(this.mRecyclerView, -1)) || (ViewCompat.canScrollHorizontally(this.mRecyclerView, -1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(8192);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      if ((ViewCompat.canScrollVertically(this.mRecyclerView, 1)) || (ViewCompat.canScrollHorizontally(this.mRecyclerView, 1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(4096);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      paramAccessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(paramRecycler, paramState), getColumnCountForAccessibility(paramRecycler, paramState), isLayoutHierarchical(paramRecycler, paramState), getSelectionModeForAccessibility(paramRecycler, paramState)));
    }

    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      int i;
      if (canScrollVertically())
      {
        i = getPosition(paramView);
        if (!canScrollHorizontally())
          break label51;
      }
      label51: for (int j = getPosition(paramView); ; j = 0)
      {
        paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, j, 1, false, false));
        return;
        i = 0;
        break;
      }
    }

    void onInitializeAccessibilityNodeInfoForItem(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if ((localViewHolder != null) && (!localViewHolder.isRemoved()))
        onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramView, paramAccessibilityNodeInfoCompat);
    }

    public View onInterceptFocusSearch(View paramView, int paramInt)
    {
      return null;
    }

    public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
    }

    public void onItemsChanged(RecyclerView paramRecyclerView)
    {
    }

    public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
    }

    public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
    }

    public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
    }

    public void onMeasure(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2)
    {
      this.mRecyclerView.defaultOnMeasure(paramInt1, paramInt2);
    }

    public boolean onRequestChildFocus(RecyclerView paramRecyclerView, RecyclerView.State paramState, View paramView1, View paramView2)
    {
      return onRequestChildFocus(paramRecyclerView, paramView1, paramView2);
    }

    @Deprecated
    public boolean onRequestChildFocus(RecyclerView paramRecyclerView, View paramView1, View paramView2)
    {
      return (isSmoothScrolling()) || (paramRecyclerView.isRunningLayoutOrScroll());
    }

    public void onRestoreInstanceState(Parcelable paramParcelable)
    {
    }

    public Parcelable onSaveInstanceState()
    {
      return null;
    }

    public void onScrollStateChanged(int paramInt)
    {
    }

    boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
    {
      return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramInt, paramBundle);
    }

    public boolean performAccessibilityAction(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt, Bundle paramBundle)
    {
      if (this.mRecyclerView == null);
      while (true)
      {
        return false;
        int i = 0;
        int j = 0;
        switch (paramInt)
        {
        default:
        case 8192:
        case 4096:
        }
        while ((j != 0) || (i != 0))
        {
          this.mRecyclerView.scrollBy(i, j);
          return true;
          boolean bool3 = ViewCompat.canScrollVertically(this.mRecyclerView, -1);
          j = 0;
          if (bool3)
            j = -(getHeight() - getPaddingTop() - getPaddingBottom());
          boolean bool4 = ViewCompat.canScrollHorizontally(this.mRecyclerView, -1);
          i = 0;
          if (bool4)
          {
            i = -(getWidth() - getPaddingLeft() - getPaddingRight());
            continue;
            boolean bool1 = ViewCompat.canScrollVertically(this.mRecyclerView, 1);
            j = 0;
            if (bool1)
              j = getHeight() - getPaddingTop() - getPaddingBottom();
            boolean bool2 = ViewCompat.canScrollHorizontally(this.mRecyclerView, 1);
            i = 0;
            if (bool2)
              i = getWidth() - getPaddingLeft() - getPaddingRight();
          }
        }
      }
    }

    public boolean performAccessibilityActionForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, int paramInt, Bundle paramBundle)
    {
      return false;
    }

    boolean performAccessibilityActionForItem(View paramView, int paramInt, Bundle paramBundle)
    {
      return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, paramView, paramInt, paramBundle);
    }

    public void postOnAnimation(Runnable paramRunnable)
    {
      if (this.mRecyclerView != null)
        ViewCompat.postOnAnimation(this.mRecyclerView, paramRunnable);
    }

    public void removeAllViews()
    {
      for (int i = -1 + getChildCount(); i >= 0; i--)
        this.mChildHelper.removeViewAt(i);
    }

    public void removeAndRecycleAllViews(RecyclerView.Recycler paramRecycler)
    {
      for (int i = -1 + getChildCount(); i >= 0; i--)
        if (!RecyclerView.getChildViewHolderInt(getChildAt(i)).shouldIgnore())
          removeAndRecycleViewAt(i, paramRecycler);
    }

    void removeAndRecycleScrapInt(RecyclerView.Recycler paramRecycler)
    {
      int i = paramRecycler.getScrapCount();
      int j = i - 1;
      if (j >= 0)
      {
        View localView = paramRecycler.getScrapViewAt(j);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if (localViewHolder.shouldIgnore());
        while (true)
        {
          j--;
          break;
          if (localViewHolder.isTmpDetached())
            this.mRecyclerView.removeDetachedView(localView, false);
          paramRecycler.quickRecycleScrapView(localView);
        }
      }
      paramRecycler.clearScrap();
      if (i > 0)
        this.mRecyclerView.invalidate();
    }

    public void removeAndRecycleView(View paramView, RecyclerView.Recycler paramRecycler)
    {
      removeView(paramView);
      paramRecycler.recycleView(paramView);
    }

    public void removeAndRecycleViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      View localView = getChildAt(paramInt);
      removeViewAt(paramInt);
      paramRecycler.recycleView(localView);
    }

    public boolean removeCallbacks(Runnable paramRunnable)
    {
      if (this.mRecyclerView != null)
        return this.mRecyclerView.removeCallbacks(paramRunnable);
      return false;
    }

    public void removeDetachedView(View paramView)
    {
      this.mRecyclerView.removeDetachedView(paramView, false);
    }

    public void removeView(View paramView)
    {
      this.mChildHelper.removeView(paramView);
    }

    public void removeViewAt(int paramInt)
    {
      if (getChildAt(paramInt) != null)
        this.mChildHelper.removeViewAt(paramInt);
    }

    public boolean requestChildRectangleOnScreen(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean)
    {
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth() - getPaddingRight();
      int m = getHeight() - getPaddingBottom();
      int n = paramView.getLeft() + paramRect.left;
      int i1 = paramView.getTop() + paramRect.top;
      int i2 = n + paramRect.width();
      int i3 = i1 + paramRect.height();
      int i4 = Math.min(0, n - i);
      int i5 = Math.min(0, i1 - j);
      int i6 = Math.max(0, i2 - k);
      int i7 = Math.max(0, i3 - m);
      int i8;
      int i9;
      if (getLayoutDirection() == 1)
        if (i6 != 0)
        {
          i8 = i6;
          if (i5 == 0)
            break label211;
          i9 = i5;
          label144: if ((i8 == 0) && (i9 == 0))
            break label237;
          if (!paramBoolean)
            break label226;
          paramRecyclerView.scrollBy(i8, i9);
        }
      while (true)
      {
        return true;
        i8 = Math.max(i4, i2 - k);
        break;
        if (i4 != 0);
        for (i8 = i4; ; i8 = Math.min(n - i, i6))
          break;
        label211: i9 = Math.min(i1 - j, i7);
        break label144;
        label226: paramRecyclerView.smoothScrollBy(i8, i9);
      }
      label237: return false;
    }

    public void requestLayout()
    {
      if (this.mRecyclerView != null)
        this.mRecyclerView.requestLayout();
    }

    public void requestSimpleAnimationsInNextLayout()
    {
      this.mRequestedSimpleAnimations = true;
    }

    public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }

    public void scrollToPosition(int paramInt)
    {
    }

    public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }

    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      this.mRecyclerView.setMeasuredDimension(paramInt1, paramInt2);
    }

    void setRecyclerView(RecyclerView paramRecyclerView)
    {
      if (paramRecyclerView == null)
      {
        this.mRecyclerView = null;
        this.mChildHelper = null;
        return;
      }
      this.mRecyclerView = paramRecyclerView;
      this.mChildHelper = paramRecyclerView.mChildHelper;
    }

    public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
    {
      Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
    }

    public void startSmoothScroll(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      if ((this.mSmoothScroller != null) && (paramSmoothScroller != this.mSmoothScroller) && (this.mSmoothScroller.isRunning()))
        this.mSmoothScroller.stop();
      this.mSmoothScroller = paramSmoothScroller;
      this.mSmoothScroller.start(this.mRecyclerView, this);
    }

    public void stopIgnoringView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      localViewHolder.stopIgnoring();
      localViewHolder.resetInternal();
      localViewHolder.addFlags(4);
    }

    void stopSmoothScroller()
    {
      if (this.mSmoothScroller != null)
        this.mSmoothScroller.stop();
    }

    public boolean supportsPredictiveItemAnimations()
    {
      return false;
    }

    public static class Properties
    {
      public int orientation;
      public boolean reverseLayout;
      public int spanCount;
      public boolean stackFromEnd;
    }
  }

  public static class LayoutParams extends ViewGroup.MarginLayoutParams
  {
    final Rect mDecorInsets = new Rect();
    boolean mInsetsDirty = true;
    boolean mPendingInvalidate = false;
    RecyclerView.ViewHolder mViewHolder;

    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    public LayoutParams(LayoutParams paramLayoutParams)
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

    public int getViewAdapterPosition()
    {
      return this.mViewHolder.getAdapterPosition();
    }

    public int getViewLayoutPosition()
    {
      return this.mViewHolder.getLayoutPosition();
    }

    public int getViewPosition()
    {
      return this.mViewHolder.getPosition();
    }

    public boolean isItemChanged()
    {
      return this.mViewHolder.isChanged();
    }

    public boolean isItemRemoved()
    {
      return this.mViewHolder.isRemoved();
    }

    public boolean isViewInvalid()
    {
      return this.mViewHolder.isInvalid();
    }

    public boolean viewNeedsUpdate()
    {
      return this.mViewHolder.needsUpdate();
    }
  }

  public static abstract interface OnChildAttachStateChangeListener
  {
    public abstract void onChildViewAttachedToWindow(View paramView);

    public abstract void onChildViewDetachedFromWindow(View paramView);
  }

  public static abstract interface OnItemTouchListener
  {
    public abstract boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent);

    public abstract void onRequestDisallowInterceptTouchEvent(boolean paramBoolean);

    public abstract void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent);
  }

  public static abstract class OnScrollListener
  {
    public void onScrollStateChanged(RecyclerView paramRecyclerView, int paramInt)
    {
    }

    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
    }
  }

  public static class RecycledViewPool
  {
    private static final int DEFAULT_MAX_SCRAP = 5;
    private int mAttachCount = 0;
    private SparseIntArray mMaxScrap = new SparseIntArray();
    private SparseArray<ArrayList<RecyclerView.ViewHolder>> mScrap = new SparseArray();

    private ArrayList<RecyclerView.ViewHolder> getScrapHeapForType(int paramInt)
    {
      ArrayList localArrayList = (ArrayList)this.mScrap.get(paramInt);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList();
        this.mScrap.put(paramInt, localArrayList);
        if (this.mMaxScrap.indexOfKey(paramInt) < 0)
          this.mMaxScrap.put(paramInt, 5);
      }
      return localArrayList;
    }

    void attach(RecyclerView.Adapter paramAdapter)
    {
      this.mAttachCount = (1 + this.mAttachCount);
    }

    public void clear()
    {
      this.mScrap.clear();
    }

    void detach()
    {
      this.mAttachCount = (-1 + this.mAttachCount);
    }

    public RecyclerView.ViewHolder getRecycledView(int paramInt)
    {
      ArrayList localArrayList = (ArrayList)this.mScrap.get(paramInt);
      if ((localArrayList != null) && (!localArrayList.isEmpty()))
      {
        int i = -1 + localArrayList.size();
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)localArrayList.get(i);
        localArrayList.remove(i);
        return localViewHolder;
      }
      return null;
    }

    void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2, boolean paramBoolean)
    {
      if (paramAdapter1 != null)
        detach();
      if ((!paramBoolean) && (this.mAttachCount == 0))
        clear();
      if (paramAdapter2 != null)
        attach(paramAdapter2);
    }

    public void putRecycledView(RecyclerView.ViewHolder paramViewHolder)
    {
      int i = paramViewHolder.getItemViewType();
      ArrayList localArrayList = getScrapHeapForType(i);
      if (this.mMaxScrap.get(i) <= localArrayList.size())
        return;
      paramViewHolder.resetInternal();
      localArrayList.add(paramViewHolder);
    }

    public void setMaxRecycledViews(int paramInt1, int paramInt2)
    {
      this.mMaxScrap.put(paramInt1, paramInt2);
      ArrayList localArrayList = (ArrayList)this.mScrap.get(paramInt1);
      if (localArrayList != null)
        while (localArrayList.size() > paramInt2)
          localArrayList.remove(-1 + localArrayList.size());
    }

    int size()
    {
      int i = 0;
      for (int j = 0; j < this.mScrap.size(); j++)
      {
        ArrayList localArrayList = (ArrayList)this.mScrap.valueAt(j);
        if (localArrayList != null)
          i += localArrayList.size();
      }
      return i;
    }
  }

  public final class Recycler
  {
    private static final int DEFAULT_CACHE_SIZE = 2;
    final ArrayList<RecyclerView.ViewHolder> mAttachedScrap = new ArrayList();
    final ArrayList<RecyclerView.ViewHolder> mCachedViews = new ArrayList();
    private ArrayList<RecyclerView.ViewHolder> mChangedScrap = null;
    private RecyclerView.RecycledViewPool mRecyclerPool;
    private final List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
    private RecyclerView.ViewCacheExtension mViewCacheExtension;
    private int mViewCacheMax = 2;

    public Recycler()
    {
    }

    private void attachAccessibilityDelegate(View paramView)
    {
      if ((RecyclerView.this.mAccessibilityManager != null) && (RecyclerView.this.mAccessibilityManager.isEnabled()))
      {
        if (ViewCompat.getImportantForAccessibility(paramView) == 0)
          ViewCompat.setImportantForAccessibility(paramView, 1);
        if (!ViewCompat.hasAccessibilityDelegate(paramView))
          ViewCompat.setAccessibilityDelegate(paramView, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
      }
    }

    private void invalidateDisplayListInt(RecyclerView.ViewHolder paramViewHolder)
    {
      if ((paramViewHolder.itemView instanceof ViewGroup))
        invalidateDisplayListInt((ViewGroup)paramViewHolder.itemView, false);
    }

    private void invalidateDisplayListInt(ViewGroup paramViewGroup, boolean paramBoolean)
    {
      for (int i = -1 + paramViewGroup.getChildCount(); i >= 0; i--)
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((localView instanceof ViewGroup))
          invalidateDisplayListInt((ViewGroup)localView, true);
      }
      if (!paramBoolean)
        return;
      if (paramViewGroup.getVisibility() == 4)
      {
        paramViewGroup.setVisibility(0);
        paramViewGroup.setVisibility(4);
        return;
      }
      int j = paramViewGroup.getVisibility();
      paramViewGroup.setVisibility(4);
      paramViewGroup.setVisibility(j);
    }

    void addViewHolderToRecycledViewPool(RecyclerView.ViewHolder paramViewHolder)
    {
      ViewCompat.setAccessibilityDelegate(paramViewHolder.itemView, null);
      dispatchViewRecycled(paramViewHolder);
      paramViewHolder.mOwnerRecyclerView = null;
      getRecycledViewPool().putRecycledView(paramViewHolder);
    }

    public void bindViewToPosition(View paramView, int paramInt)
    {
      boolean bool = true;
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder == null)
        throw new IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
      int i = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
      if ((i < 0) || (i >= RecyclerView.this.mAdapter.getItemCount()))
        throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + paramInt + "(offset:" + i + ")." + "state:" + RecyclerView.this.mState.getItemCount());
      localViewHolder.mOwnerRecyclerView = RecyclerView.this;
      RecyclerView.this.mAdapter.bindViewHolder(localViewHolder, i);
      attachAccessibilityDelegate(paramView);
      if (RecyclerView.this.mState.isPreLayout())
        localViewHolder.mPreLayoutPosition = paramInt;
      ViewGroup.LayoutParams localLayoutParams = localViewHolder.itemView.getLayoutParams();
      RecyclerView.LayoutParams localLayoutParams1;
      if (localLayoutParams == null)
      {
        localLayoutParams1 = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
        localViewHolder.itemView.setLayoutParams(localLayoutParams1);
        localLayoutParams1.mInsetsDirty = bool;
        localLayoutParams1.mViewHolder = localViewHolder;
        if (localViewHolder.itemView.getParent() != null)
          break label280;
      }
      while (true)
      {
        localLayoutParams1.mPendingInvalidate = bool;
        return;
        if (!RecyclerView.this.checkLayoutParams(localLayoutParams))
        {
          localLayoutParams1 = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams(localLayoutParams);
          localViewHolder.itemView.setLayoutParams(localLayoutParams1);
          break;
        }
        localLayoutParams1 = (RecyclerView.LayoutParams)localLayoutParams;
        break;
        label280: bool = false;
      }
    }

    public void clear()
    {
      this.mAttachedScrap.clear();
      recycleAndClearCachedViews();
    }

    void clearOldPositions()
    {
      int i = this.mCachedViews.size();
      for (int j = 0; j < i; j++)
        ((RecyclerView.ViewHolder)this.mCachedViews.get(j)).clearOldPosition();
      int k = this.mAttachedScrap.size();
      for (int m = 0; m < k; m++)
        ((RecyclerView.ViewHolder)this.mAttachedScrap.get(m)).clearOldPosition();
      if (this.mChangedScrap != null)
      {
        int n = this.mChangedScrap.size();
        for (int i1 = 0; i1 < n; i1++)
          ((RecyclerView.ViewHolder)this.mChangedScrap.get(i1)).clearOldPosition();
      }
    }

    void clearScrap()
    {
      this.mAttachedScrap.clear();
    }

    public int convertPreLayoutPositionToPostLayout(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= RecyclerView.this.mState.getItemCount()))
        throw new IndexOutOfBoundsException("invalid position " + paramInt + ". State " + "item count is " + RecyclerView.this.mState.getItemCount());
      if (!RecyclerView.this.mState.isPreLayout())
        return paramInt;
      return RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
    }

    void dispatchViewRecycled(RecyclerView.ViewHolder paramViewHolder)
    {
      if (RecyclerView.this.mRecyclerListener != null)
        RecyclerView.this.mRecyclerListener.onViewRecycled(paramViewHolder);
      if (RecyclerView.this.mAdapter != null)
        RecyclerView.this.mAdapter.onViewRecycled(paramViewHolder);
      if (RecyclerView.this.mState != null)
        RecyclerView.this.mState.onViewRecycled(paramViewHolder);
    }

    RecyclerView.ViewHolder getChangedScrapViewForPosition(int paramInt)
    {
      int i;
      if (this.mChangedScrap != null)
      {
        i = this.mChangedScrap.size();
        if (i != 0);
      }
      else
      {
        return null;
      }
      for (int j = 0; j < i; j++)
      {
        RecyclerView.ViewHolder localViewHolder2 = (RecyclerView.ViewHolder)this.mChangedScrap.get(j);
        if ((!localViewHolder2.wasReturnedFromScrap()) && (localViewHolder2.getLayoutPosition() == paramInt))
        {
          localViewHolder2.addFlags(32);
          return localViewHolder2;
        }
      }
      if (RecyclerView.this.mAdapter.hasStableIds())
      {
        int k = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
        if ((k > 0) && (k < RecyclerView.this.mAdapter.getItemCount()))
        {
          long l = RecyclerView.this.mAdapter.getItemId(k);
          for (int m = 0; m < i; m++)
          {
            RecyclerView.ViewHolder localViewHolder1 = (RecyclerView.ViewHolder)this.mChangedScrap.get(m);
            if ((!localViewHolder1.wasReturnedFromScrap()) && (localViewHolder1.getItemId() == l))
            {
              localViewHolder1.addFlags(32);
              return localViewHolder1;
            }
          }
        }
      }
      return null;
    }

    RecyclerView.RecycledViewPool getRecycledViewPool()
    {
      if (this.mRecyclerPool == null)
        this.mRecyclerPool = new RecyclerView.RecycledViewPool();
      return this.mRecyclerPool;
    }

    int getScrapCount()
    {
      return this.mAttachedScrap.size();
    }

    public List<RecyclerView.ViewHolder> getScrapList()
    {
      return this.mUnmodifiableAttachedScrap;
    }

    View getScrapViewAt(int paramInt)
    {
      return ((RecyclerView.ViewHolder)this.mAttachedScrap.get(paramInt)).itemView;
    }

    RecyclerView.ViewHolder getScrapViewForId(long paramLong, int paramInt, boolean paramBoolean)
    {
      RecyclerView.ViewHolder localViewHolder;
      for (int i = -1 + this.mAttachedScrap.size(); i >= 0; i--)
      {
        localViewHolder = (RecyclerView.ViewHolder)this.mAttachedScrap.get(i);
        if ((localViewHolder.getItemId() == paramLong) && (!localViewHolder.wasReturnedFromScrap()))
        {
          if (paramInt == localViewHolder.getItemViewType())
          {
            localViewHolder.addFlags(32);
            if ((localViewHolder.isRemoved()) && (!RecyclerView.this.mState.isPreLayout()))
              localViewHolder.setFlags(2, 14);
            return localViewHolder;
          }
          if (!paramBoolean)
          {
            this.mAttachedScrap.remove(i);
            RecyclerView.this.removeDetachedView(localViewHolder.itemView, false);
            quickRecycleScrapView(localViewHolder.itemView);
          }
        }
      }
      for (int j = -1 + this.mCachedViews.size(); ; j--)
      {
        if (j < 0)
          break label223;
        localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if (localViewHolder.getItemId() == paramLong)
        {
          if (paramInt == localViewHolder.getItemViewType())
          {
            if (paramBoolean)
              break;
            this.mCachedViews.remove(j);
            return localViewHolder;
          }
          if (!paramBoolean)
            recycleCachedViewAt(j);
        }
      }
      label223: return null;
    }

    RecyclerView.ViewHolder getScrapViewForPosition(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      int i = this.mAttachedScrap.size();
      int j = 0;
      RecyclerView.ViewHolder localViewHolder2;
      int k;
      if (j < i)
      {
        localViewHolder2 = (RecyclerView.ViewHolder)this.mAttachedScrap.get(j);
        if ((localViewHolder2.wasReturnedFromScrap()) || (localViewHolder2.getLayoutPosition() != paramInt1) || (localViewHolder2.isInvalid()) || ((!RecyclerView.State.access$1500(RecyclerView.this.mState)) && (localViewHolder2.isRemoved())))
          break label275;
        if ((paramInt2 != -1) && (localViewHolder2.getItemViewType() != paramInt2))
          Log.e("RecyclerView", "Scrap view for position " + paramInt1 + " isn't dirty but has" + " wrong view type! (found " + localViewHolder2.getItemViewType() + " but expected " + paramInt2 + ")");
      }
      else
      {
        if (!paramBoolean)
        {
          View localView = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(paramInt1, paramInt2);
          if (localView != null)
            RecyclerView.this.mItemAnimator.endAnimation(RecyclerView.this.getChildViewHolder(localView));
        }
        k = this.mCachedViews.size();
      }
      for (int m = 0; ; m++)
      {
        if (m >= k)
          break label287;
        RecyclerView.ViewHolder localViewHolder1 = (RecyclerView.ViewHolder)this.mCachedViews.get(m);
        if ((!localViewHolder1.isInvalid()) && (localViewHolder1.getLayoutPosition() == paramInt1))
        {
          if (!paramBoolean)
            this.mCachedViews.remove(m);
          return localViewHolder1;
          localViewHolder2.addFlags(32);
          return localViewHolder2;
          label275: j++;
          break;
        }
      }
      label287: return null;
    }

    public View getViewForPosition(int paramInt)
    {
      return getViewForPosition(paramInt, false);
    }

    View getViewForPosition(int paramInt, boolean paramBoolean)
    {
      boolean bool1 = true;
      if ((paramInt < 0) || (paramInt >= RecyclerView.this.mState.getItemCount()))
        throw new IndexOutOfBoundsException("Invalid item position " + paramInt + "(" + paramInt + "). Item count:" + RecyclerView.this.mState.getItemCount());
      boolean bool2 = RecyclerView.this.mState.isPreLayout();
      int i = 0;
      RecyclerView.ViewHolder localViewHolder = null;
      if (bool2)
      {
        localViewHolder = getChangedScrapViewForPosition(paramInt);
        if (localViewHolder != null)
          i = bool1;
      }
      else if (localViewHolder == null)
      {
        localViewHolder = getScrapViewForPosition(paramInt, -1, paramBoolean);
        if (localViewHolder != null)
        {
          if (validateViewHolderForOffsetPosition(localViewHolder))
            break label308;
          if (!paramBoolean)
          {
            localViewHolder.addFlags(4);
            if (!localViewHolder.isScrap())
              break label292;
            RecyclerView.this.removeDetachedView(localViewHolder.itemView, false);
            localViewHolder.unScrap();
            label179: recycleViewHolderInternal(localViewHolder);
          }
          localViewHolder = null;
        }
      }
      while (true)
        if (localViewHolder == null)
        {
          int m = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
          if ((m < 0) || (m >= RecyclerView.this.mAdapter.getItemCount()))
          {
            throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + paramInt + "(offset:" + m + ")." + "state:" + RecyclerView.this.mState.getItemCount());
            i = 0;
            break;
            label292: if (!localViewHolder.wasReturnedFromScrap())
              break label179;
            localViewHolder.clearReturnedFromScrapFlag();
            break label179;
            label308: i = 1;
            continue;
          }
          int n = RecyclerView.this.mAdapter.getItemViewType(m);
          if (RecyclerView.this.mAdapter.hasStableIds())
          {
            localViewHolder = getScrapViewForId(RecyclerView.this.mAdapter.getItemId(m), n, paramBoolean);
            if (localViewHolder != null)
            {
              localViewHolder.mPosition = m;
              i = 1;
            }
          }
          if ((localViewHolder == null) && (this.mViewCacheExtension != null))
          {
            View localView = this.mViewCacheExtension.getViewForPositionAndType(this, paramInt, n);
            if (localView != null)
            {
              localViewHolder = RecyclerView.this.getChildViewHolder(localView);
              if (localViewHolder == null)
                throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
              if (localViewHolder.shouldIgnore())
                throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
            }
          }
          if (localViewHolder == null)
          {
            localViewHolder = getRecycledViewPool().getRecycledView(n);
            if (localViewHolder != null)
            {
              localViewHolder.resetInternal();
              if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST)
                invalidateDisplayListInt(localViewHolder);
            }
          }
          if (localViewHolder == null)
            localViewHolder = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, n);
        }
      int j = 0;
      ViewGroup.LayoutParams localLayoutParams;
      RecyclerView.LayoutParams localLayoutParams1;
      if ((RecyclerView.this.mState.isPreLayout()) && (localViewHolder.isBound()))
      {
        localViewHolder.mPreLayoutPosition = paramInt;
        localLayoutParams = localViewHolder.itemView.getLayoutParams();
        if (localLayoutParams != null)
          break label711;
        localLayoutParams1 = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
        localViewHolder.itemView.setLayoutParams(localLayoutParams1);
        label581: localLayoutParams1.mViewHolder = localViewHolder;
        if ((i == 0) || (j == 0))
          break label760;
      }
      while (true)
      {
        localLayoutParams1.mPendingInvalidate = bool1;
        return localViewHolder.itemView;
        if ((localViewHolder.isBound()) && (!localViewHolder.needsUpdate()))
        {
          boolean bool3 = localViewHolder.isInvalid();
          j = 0;
          if (!bool3)
            break;
        }
        int k = RecyclerView.this.mAdapterHelper.findPositionOffset(paramInt);
        localViewHolder.mOwnerRecyclerView = RecyclerView.this;
        RecyclerView.this.mAdapter.bindViewHolder(localViewHolder, k);
        attachAccessibilityDelegate(localViewHolder.itemView);
        j = 1;
        if (!RecyclerView.this.mState.isPreLayout())
          break;
        localViewHolder.mPreLayoutPosition = paramInt;
        break;
        label711: if (!RecyclerView.this.checkLayoutParams(localLayoutParams))
        {
          localLayoutParams1 = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams(localLayoutParams);
          localViewHolder.itemView.setLayoutParams(localLayoutParams1);
          break label581;
        }
        localLayoutParams1 = (RecyclerView.LayoutParams)localLayoutParams;
        break label581;
        label760: bool1 = false;
      }
    }

    void markItemDecorInsetsDirty()
    {
      int i = this.mCachedViews.size();
      for (int j = 0; j < i; j++)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)((RecyclerView.ViewHolder)this.mCachedViews.get(j)).itemView.getLayoutParams();
        if (localLayoutParams != null)
          localLayoutParams.mInsetsDirty = true;
      }
    }

    void markKnownViewsInvalid()
    {
      int i;
      int j;
      if ((RecyclerView.this.mAdapter != null) && (RecyclerView.this.mAdapter.hasStableIds()))
      {
        i = this.mCachedViews.size();
        j = 0;
      }
      while (j < i)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if (localViewHolder != null)
          localViewHolder.addFlags(6);
        j++;
        continue;
        recycleAndClearCachedViews();
      }
    }

    void offsetPositionRecordsForInsert(int paramInt1, int paramInt2)
    {
      int i = this.mCachedViews.size();
      for (int j = 0; j < i; j++)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if ((localViewHolder != null) && (localViewHolder.getLayoutPosition() >= paramInt1))
          localViewHolder.offsetPosition(paramInt2, true);
      }
    }

    void offsetPositionRecordsForMove(int paramInt1, int paramInt2)
    {
      int i;
      int j;
      int k;
      int n;
      label25: RecyclerView.ViewHolder localViewHolder;
      if (paramInt1 < paramInt2)
      {
        i = paramInt1;
        j = paramInt2;
        k = -1;
        int m = this.mCachedViews.size();
        n = 0;
        if (n >= m)
          return;
        localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(n);
        if ((localViewHolder != null) && (localViewHolder.mPosition >= i) && (localViewHolder.mPosition <= j))
          break label87;
      }
      while (true)
      {
        n++;
        break label25;
        i = paramInt2;
        j = paramInt1;
        k = 1;
        break;
        label87: if (localViewHolder.mPosition == paramInt1)
          localViewHolder.offsetPosition(paramInt2 - paramInt1, false);
        else
          localViewHolder.offsetPosition(k, false);
      }
    }

    void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      int i = paramInt1 + paramInt2;
      int j = -1 + this.mCachedViews.size();
      if (j >= 0)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if (localViewHolder != null)
        {
          if (localViewHolder.getLayoutPosition() < i)
            break label64;
          localViewHolder.offsetPosition(-paramInt2, paramBoolean);
        }
        while (true)
        {
          j--;
          break;
          label64: if (localViewHolder.getLayoutPosition() >= paramInt1)
          {
            localViewHolder.addFlags(8);
            recycleCachedViewAt(j);
          }
        }
      }
    }

    void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2, boolean paramBoolean)
    {
      clear();
      getRecycledViewPool().onAdapterChanged(paramAdapter1, paramAdapter2, paramBoolean);
    }

    void quickRecycleScrapView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      RecyclerView.ViewHolder.access$4102(localViewHolder, null);
      localViewHolder.clearReturnedFromScrapFlag();
      recycleViewHolderInternal(localViewHolder);
    }

    void recycleAndClearCachedViews()
    {
      for (int i = -1 + this.mCachedViews.size(); i >= 0; i--)
        recycleCachedViewAt(i);
      this.mCachedViews.clear();
    }

    void recycleCachedViewAt(int paramInt)
    {
      addViewHolderToRecycledViewPool((RecyclerView.ViewHolder)this.mCachedViews.get(paramInt));
      this.mCachedViews.remove(paramInt);
    }

    public void recycleView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isTmpDetached())
        RecyclerView.this.removeDetachedView(paramView, false);
      if (localViewHolder.isScrap())
        localViewHolder.unScrap();
      while (true)
      {
        recycleViewHolderInternal(localViewHolder);
        return;
        if (localViewHolder.wasReturnedFromScrap())
          localViewHolder.clearReturnedFromScrapFlag();
      }
    }

    void recycleViewHolderInternal(RecyclerView.ViewHolder paramViewHolder)
    {
      boolean bool1 = true;
      if ((paramViewHolder.isScrap()) || (paramViewHolder.itemView.getParent() != null))
      {
        StringBuilder localStringBuilder = new StringBuilder().append("Scrapped or attached views may not be recycled. isScrap:").append(paramViewHolder.isScrap()).append(" isAttached:");
        if (paramViewHolder.itemView.getParent() != null);
        while (true)
        {
          throw new IllegalArgumentException(bool1);
          bool1 = false;
        }
      }
      if (paramViewHolder.isTmpDetached())
        throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + paramViewHolder);
      if (paramViewHolder.shouldIgnore())
        throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
      boolean bool2 = RecyclerView.ViewHolder.access$4000(paramViewHolder);
      if ((RecyclerView.this.mAdapter != null) && (bool2) && (RecyclerView.this.mAdapter.onFailedToRecycleView(paramViewHolder)));
      for (boolean bool3 = bool1; ; bool3 = false)
      {
        int i;
        int j;
        if (!bool3)
        {
          boolean bool7 = paramViewHolder.isRecyclable();
          i = 0;
          j = 0;
          if (!bool7);
        }
        else
        {
          boolean bool4 = paramViewHolder.isInvalid();
          i = 0;
          if (!bool4)
          {
            boolean bool5 = paramViewHolder.isRemoved();
            i = 0;
            if (!bool5)
            {
              boolean bool6 = paramViewHolder.isChanged();
              i = 0;
              if (!bool6)
              {
                int k = this.mCachedViews.size();
                if ((k == this.mViewCacheMax) && (k > 0))
                  recycleCachedViewAt(0);
                int m = this.mViewCacheMax;
                i = 0;
                if (k < m)
                {
                  this.mCachedViews.add(paramViewHolder);
                  i = 1;
                }
              }
            }
          }
          j = 0;
          if (i == 0)
          {
            addViewHolderToRecycledViewPool(paramViewHolder);
            j = 1;
          }
        }
        RecyclerView.this.mState.onViewRecycled(paramViewHolder);
        if ((i == 0) && (j == 0) && (bool2))
          paramViewHolder.mOwnerRecyclerView = null;
        return;
      }
    }

    void recycleViewInternal(View paramView)
    {
      recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(paramView));
    }

    void scrapView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      localViewHolder.setScrapContainer(this);
      if ((!localViewHolder.isChanged()) || (!RecyclerView.this.supportsChangeAnimations()))
      {
        if ((localViewHolder.isInvalid()) && (!localViewHolder.isRemoved()) && (!RecyclerView.this.mAdapter.hasStableIds()))
          throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
        this.mAttachedScrap.add(localViewHolder);
        return;
      }
      if (this.mChangedScrap == null)
        this.mChangedScrap = new ArrayList();
      this.mChangedScrap.add(localViewHolder);
    }

    void setAdapterPositionsAsUnknown()
    {
      int i = this.mCachedViews.size();
      for (int j = 0; j < i; j++)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(j);
        if (localViewHolder != null)
          localViewHolder.addFlags(512);
      }
    }

    void setRecycledViewPool(RecyclerView.RecycledViewPool paramRecycledViewPool)
    {
      if (this.mRecyclerPool != null)
        this.mRecyclerPool.detach();
      this.mRecyclerPool = paramRecycledViewPool;
      if (paramRecycledViewPool != null)
        this.mRecyclerPool.attach(RecyclerView.this.getAdapter());
    }

    void setViewCacheExtension(RecyclerView.ViewCacheExtension paramViewCacheExtension)
    {
      this.mViewCacheExtension = paramViewCacheExtension;
    }

    public void setViewCacheSize(int paramInt)
    {
      this.mViewCacheMax = paramInt;
      for (int i = -1 + this.mCachedViews.size(); (i >= 0) && (this.mCachedViews.size() > paramInt); i--)
        recycleCachedViewAt(i);
    }

    void unscrapView(RecyclerView.ViewHolder paramViewHolder)
    {
      if ((!paramViewHolder.isChanged()) || (!RecyclerView.this.supportsChangeAnimations()) || (this.mChangedScrap == null))
        this.mAttachedScrap.remove(paramViewHolder);
      while (true)
      {
        RecyclerView.ViewHolder.access$4102(paramViewHolder, null);
        paramViewHolder.clearReturnedFromScrapFlag();
        return;
        this.mChangedScrap.remove(paramViewHolder);
      }
    }

    boolean validateViewHolderForOffsetPosition(RecyclerView.ViewHolder paramViewHolder)
    {
      if (paramViewHolder.isRemoved());
      do
      {
        return true;
        if ((paramViewHolder.mPosition < 0) || (paramViewHolder.mPosition >= RecyclerView.this.mAdapter.getItemCount()))
          throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + paramViewHolder);
        if ((!RecyclerView.this.mState.isPreLayout()) && (RecyclerView.this.mAdapter.getItemViewType(paramViewHolder.mPosition) != paramViewHolder.getItemViewType()))
          return false;
      }
      while ((!RecyclerView.this.mAdapter.hasStableIds()) || (paramViewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(paramViewHolder.mPosition)));
      return false;
    }

    void viewRangeUpdate(int paramInt1, int paramInt2)
    {
      int i = paramInt1 + paramInt2;
      int j = this.mCachedViews.size();
      int k = 0;
      if (k < j)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)this.mCachedViews.get(k);
        if (localViewHolder == null);
        while (true)
        {
          k++;
          break;
          int m = localViewHolder.getLayoutPosition();
          if ((m >= paramInt1) && (m < i))
            localViewHolder.addFlags(2);
        }
      }
    }
  }

  public static abstract interface RecyclerListener
  {
    public abstract void onViewRecycled(RecyclerView.ViewHolder paramViewHolder);
  }

  private class RecyclerViewDataObserver extends RecyclerView.AdapterDataObserver
  {
    private RecyclerViewDataObserver()
    {
    }

    public void onChanged()
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapter.hasStableIds())
      {
        RecyclerView.State.access$1202(RecyclerView.this.mState, true);
        RecyclerView.this.setDataSetChangedAfterLayout();
      }
      while (true)
      {
        if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates())
          RecyclerView.this.requestLayout();
        return;
        RecyclerView.State.access$1202(RecyclerView.this.mState, true);
        RecyclerView.this.setDataSetChangedAfterLayout();
      }
    }

    public void onItemRangeChanged(int paramInt1, int paramInt2)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(paramInt1, paramInt2))
        triggerUpdateProcessor();
    }

    public void onItemRangeInserted(int paramInt1, int paramInt2)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(paramInt1, paramInt2))
        triggerUpdateProcessor();
    }

    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(paramInt1, paramInt2, paramInt3))
        triggerUpdateProcessor();
    }

    public void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
      RecyclerView.this.assertNotInLayoutOrScroll(null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(paramInt1, paramInt2))
        triggerUpdateProcessor();
    }

    void triggerUpdateProcessor()
    {
      if ((RecyclerView.this.mPostUpdatesOnAnimation) && (RecyclerView.this.mHasFixedSize) && (RecyclerView.this.mIsAttached))
      {
        ViewCompat.postOnAnimation(RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
        return;
      }
      RecyclerView.access$3602(RecyclerView.this, true);
      RecyclerView.this.requestLayout();
    }
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public RecyclerView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new RecyclerView.SavedState(paramAnonymousParcel);
      }

      public RecyclerView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new RecyclerView.SavedState[paramAnonymousInt];
      }
    };
    Parcelable mLayoutState;

    SavedState(Parcel paramParcel)
    {
      super();
      this.mLayoutState = paramParcel.readParcelable(RecyclerView.LayoutManager.class.getClassLoader());
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    private void copyFrom(SavedState paramSavedState)
    {
      this.mLayoutState = paramSavedState.mLayoutState;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeParcelable(this.mLayoutState, 0);
    }
  }

  public class SimpleOnItemTouchListener
    implements RecyclerView.OnItemTouchListener
  {
    public SimpleOnItemTouchListener()
    {
    }

    public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
    {
      return false;
    }

    public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean)
    {
    }

    public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
    {
    }
  }

  public static abstract class SmoothScroller
  {
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean mPendingInitialRun;
    private RecyclerView mRecyclerView;
    private final Action mRecyclingAction = new Action(0, 0);
    private boolean mRunning;
    private int mTargetPosition = -1;
    private View mTargetView;

    private void onAnimation(int paramInt1, int paramInt2)
    {
      if ((!this.mRunning) || (this.mTargetPosition == -1))
        stop();
      this.mPendingInitialRun = false;
      if (this.mTargetView != null)
      {
        if (getChildPosition(this.mTargetView) != this.mTargetPosition)
          break label116;
        onTargetFound(this.mTargetView, this.mRecyclerView.mState, this.mRecyclingAction);
        this.mRecyclingAction.runIfNecessary(this.mRecyclerView);
        stop();
      }
      while (true)
      {
        if (this.mRunning)
        {
          onSeekTargetStep(paramInt1, paramInt2, this.mRecyclerView.mState, this.mRecyclingAction);
          this.mRecyclingAction.runIfNecessary(this.mRecyclerView);
        }
        return;
        label116: Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
        this.mTargetView = null;
      }
    }

    public View findViewByPosition(int paramInt)
    {
      return this.mRecyclerView.mLayout.findViewByPosition(paramInt);
    }

    public int getChildCount()
    {
      return this.mRecyclerView.mLayout.getChildCount();
    }

    public int getChildPosition(View paramView)
    {
      return this.mRecyclerView.getChildLayoutPosition(paramView);
    }

    public RecyclerView.LayoutManager getLayoutManager()
    {
      return this.mLayoutManager;
    }

    public int getTargetPosition()
    {
      return this.mTargetPosition;
    }

    public void instantScrollToPosition(int paramInt)
    {
      this.mRecyclerView.scrollToPosition(paramInt);
    }

    public boolean isPendingInitialRun()
    {
      return this.mPendingInitialRun;
    }

    public boolean isRunning()
    {
      return this.mRunning;
    }

    protected void normalize(PointF paramPointF)
    {
      double d = Math.sqrt(paramPointF.x * paramPointF.x + paramPointF.y * paramPointF.y);
      paramPointF.x = ((float)(paramPointF.x / d));
      paramPointF.y = ((float)(paramPointF.y / d));
    }

    protected void onChildAttachedToWindow(View paramView)
    {
      if (getChildPosition(paramView) == getTargetPosition())
        this.mTargetView = paramView;
    }

    protected abstract void onSeekTargetStep(int paramInt1, int paramInt2, RecyclerView.State paramState, Action paramAction);

    protected abstract void onStart();

    protected abstract void onStop();

    protected abstract void onTargetFound(View paramView, RecyclerView.State paramState, Action paramAction);

    public void setTargetPosition(int paramInt)
    {
      this.mTargetPosition = paramInt;
    }

    void start(RecyclerView paramRecyclerView, RecyclerView.LayoutManager paramLayoutManager)
    {
      this.mRecyclerView = paramRecyclerView;
      this.mLayoutManager = paramLayoutManager;
      if (this.mTargetPosition == -1)
        throw new IllegalArgumentException("Invalid target position");
      RecyclerView.State.access$4702(this.mRecyclerView.mState, this.mTargetPosition);
      this.mRunning = true;
      this.mPendingInitialRun = true;
      this.mTargetView = findViewByPosition(getTargetPosition());
      onStart();
      this.mRecyclerView.mViewFlinger.postOnAnimation();
    }

    protected final void stop()
    {
      if (!this.mRunning)
        return;
      onStop();
      RecyclerView.State.access$4702(this.mRecyclerView.mState, -1);
      this.mTargetView = null;
      this.mTargetPosition = -1;
      this.mPendingInitialRun = false;
      this.mRunning = false;
      this.mLayoutManager.onSmoothScrollerStopped(this);
      this.mLayoutManager = null;
      this.mRecyclerView = null;
    }

    public static class Action
    {
      public static final int UNDEFINED_DURATION = -2147483648;
      private boolean changed = false;
      private int consecutiveUpdates = 0;
      private int mDuration;
      private int mDx;
      private int mDy;
      private Interpolator mInterpolator;

      public Action(int paramInt1, int paramInt2)
      {
        this(paramInt1, paramInt2, -2147483648, null);
      }

      public Action(int paramInt1, int paramInt2, int paramInt3)
      {
        this(paramInt1, paramInt2, paramInt3, null);
      }

      public Action(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        this.mDx = paramInt1;
        this.mDy = paramInt2;
        this.mDuration = paramInt3;
        this.mInterpolator = paramInterpolator;
      }

      private void runIfNecessary(RecyclerView paramRecyclerView)
      {
        if (this.changed)
        {
          validate();
          if (this.mInterpolator == null)
            if (this.mDuration == -2147483648)
              paramRecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
          while (true)
          {
            this.consecutiveUpdates = (1 + this.consecutiveUpdates);
            if (this.consecutiveUpdates > 10)
              Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
            this.changed = false;
            return;
            paramRecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
            continue;
            paramRecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
          }
        }
        this.consecutiveUpdates = 0;
      }

      private void validate()
      {
        if ((this.mInterpolator != null) && (this.mDuration < 1))
          throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
        if (this.mDuration < 1)
          throw new IllegalStateException("Scroll duration must be a positive number");
      }

      public int getDuration()
      {
        return this.mDuration;
      }

      public int getDx()
      {
        return this.mDx;
      }

      public int getDy()
      {
        return this.mDy;
      }

      public Interpolator getInterpolator()
      {
        return this.mInterpolator;
      }

      public void setDuration(int paramInt)
      {
        this.changed = true;
        this.mDuration = paramInt;
      }

      public void setDx(int paramInt)
      {
        this.changed = true;
        this.mDx = paramInt;
      }

      public void setDy(int paramInt)
      {
        this.changed = true;
        this.mDy = paramInt;
      }

      public void setInterpolator(Interpolator paramInterpolator)
      {
        this.changed = true;
        this.mInterpolator = paramInterpolator;
      }

      public void update(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        this.mDx = paramInt1;
        this.mDy = paramInt2;
        this.mDuration = paramInt3;
        this.mInterpolator = paramInterpolator;
        this.changed = true;
      }
    }
  }

  public static class State
  {
    private SparseArray<Object> mData;
    private int mDeletedInvisibleItemCountSincePreviousLayout = 0;
    final List<View> mDisappearingViewsInLayoutPass = new ArrayList();
    private boolean mInPreLayout = false;
    int mItemCount = 0;
    ArrayMap<Long, RecyclerView.ViewHolder> mOldChangedHolders = new ArrayMap();
    ArrayMap<RecyclerView.ViewHolder, RecyclerView.ItemHolderInfo> mPostLayoutHolderMap = new ArrayMap();
    ArrayMap<RecyclerView.ViewHolder, RecyclerView.ItemHolderInfo> mPreLayoutHolderMap = new ArrayMap();
    private int mPreviousLayoutItemCount = 0;
    private boolean mRunPredictiveAnimations = false;
    private boolean mRunSimpleAnimations = false;
    private boolean mStructureChanged = false;
    private int mTargetPosition = -1;

    private void removeFrom(ArrayMap<Long, RecyclerView.ViewHolder> paramArrayMap, RecyclerView.ViewHolder paramViewHolder)
    {
      for (int i = -1 + paramArrayMap.size(); ; i--)
        if (i >= 0)
        {
          if (paramViewHolder == paramArrayMap.valueAt(i))
            paramArrayMap.removeAt(i);
        }
        else
          return;
    }

    void addToDisappearingList(View paramView)
    {
      if (!this.mDisappearingViewsInLayoutPass.contains(paramView))
        this.mDisappearingViewsInLayoutPass.add(paramView);
    }

    public boolean didStructureChange()
    {
      return this.mStructureChanged;
    }

    public <T> T get(int paramInt)
    {
      if (this.mData == null)
        return null;
      return this.mData.get(paramInt);
    }

    public int getItemCount()
    {
      if (this.mInPreLayout)
        return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
      return this.mItemCount;
    }

    public int getTargetScrollPosition()
    {
      return this.mTargetPosition;
    }

    public boolean hasTargetScrollPosition()
    {
      return this.mTargetPosition != -1;
    }

    public boolean isPreLayout()
    {
      return this.mInPreLayout;
    }

    public void onViewIgnored(RecyclerView.ViewHolder paramViewHolder)
    {
      onViewRecycled(paramViewHolder);
    }

    void onViewRecycled(RecyclerView.ViewHolder paramViewHolder)
    {
      this.mPreLayoutHolderMap.remove(paramViewHolder);
      this.mPostLayoutHolderMap.remove(paramViewHolder);
      if (this.mOldChangedHolders != null)
        removeFrom(this.mOldChangedHolders, paramViewHolder);
      this.mDisappearingViewsInLayoutPass.remove(paramViewHolder.itemView);
    }

    public void put(int paramInt, Object paramObject)
    {
      if (this.mData == null)
        this.mData = new SparseArray();
      this.mData.put(paramInt, paramObject);
    }

    public void remove(int paramInt)
    {
      if (this.mData == null)
        return;
      this.mData.remove(paramInt);
    }

    void removeFromDisappearingList(View paramView)
    {
      this.mDisappearingViewsInLayoutPass.remove(paramView);
    }

    State reset()
    {
      this.mTargetPosition = -1;
      if (this.mData != null)
        this.mData.clear();
      this.mItemCount = 0;
      this.mStructureChanged = false;
      return this;
    }

    public String toString()
    {
      return "State{mTargetPosition=" + this.mTargetPosition + ", mPreLayoutHolderMap=" + this.mPreLayoutHolderMap + ", mPostLayoutHolderMap=" + this.mPostLayoutHolderMap + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
    }

    public boolean willRunPredictiveAnimations()
    {
      return this.mRunPredictiveAnimations;
    }

    public boolean willRunSimpleAnimations()
    {
      return this.mRunSimpleAnimations;
    }
  }

  public static abstract class ViewCacheExtension
  {
    public abstract View getViewForPositionAndType(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2);
  }

  private class ViewFlinger
    implements Runnable
  {
    private boolean mEatRunOnAnimationRequest = false;
    private Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
    private int mLastFlingX;
    private int mLastFlingY;
    private boolean mReSchedulePostAnimationCallback = false;
    private ScrollerCompat mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);

    public ViewFlinger()
    {
    }

    private int computeScrollDuration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int i = Math.abs(paramInt1);
      int j = Math.abs(paramInt2);
      int k;
      int m;
      int n;
      if (i > j)
      {
        k = 1;
        m = (int)Math.sqrt(paramInt3 * paramInt3 + paramInt4 * paramInt4);
        n = (int)Math.sqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2);
        if (k == 0)
          break label142;
      }
      int i3;
      label142: for (int i1 = RecyclerView.this.getWidth(); ; i1 = RecyclerView.this.getHeight())
      {
        int i2 = i1 / 2;
        float f1 = Math.min(1.0F, 1.0F * n / i1);
        float f2 = i2 + i2 * distanceInfluenceForSnapDuration(f1);
        if (m <= 0)
          break label154;
        i3 = 4 * Math.round(1000.0F * Math.abs(f2 / m));
        return Math.min(i3, 2000);
        k = 0;
        break;
      }
      label154: if (k != 0);
      while (true)
      {
        i3 = (int)(300.0F * (1.0F + i / i1));
        break;
        i = j;
      }
    }

    private void disableRunOnAnimationRequests()
    {
      this.mReSchedulePostAnimationCallback = false;
      this.mEatRunOnAnimationRequest = true;
    }

    private float distanceInfluenceForSnapDuration(float paramFloat)
    {
      return (float)Math.sin((float)(0.47123891676382D * (paramFloat - 0.5F)));
    }

    private void enableRunOnAnimationRequests()
    {
      this.mEatRunOnAnimationRequest = false;
      if (this.mReSchedulePostAnimationCallback)
        postOnAnimation();
    }

    public void fling(int paramInt1, int paramInt2)
    {
      RecyclerView.this.setScrollState(2);
      this.mLastFlingY = 0;
      this.mLastFlingX = 0;
      this.mScroller.fling(0, 0, paramInt1, paramInt2, -2147483648, 2147483647, -2147483648, 2147483647);
      postOnAnimation();
    }

    void postOnAnimation()
    {
      if (this.mEatRunOnAnimationRequest)
      {
        this.mReSchedulePostAnimationCallback = true;
        return;
      }
      RecyclerView.this.removeCallbacks(this);
      ViewCompat.postOnAnimation(RecyclerView.this, this);
    }

    public void run()
    {
      disableRunOnAnimationRequests();
      RecyclerView.this.consumePendingUpdateOperations();
      ScrollerCompat localScrollerCompat = this.mScroller;
      RecyclerView.SmoothScroller localSmoothScroller = RecyclerView.this.mLayout.mSmoothScroller;
      int k;
      int m;
      int i1;
      int i2;
      int i11;
      int i4;
      int i5;
      label490: int i7;
      label514: int i8;
      label643: int i9;
      label671: int i10;
      if (localScrollerCompat.computeScrollOffset())
      {
        int i = localScrollerCompat.getCurrX();
        int j = localScrollerCompat.getCurrY();
        k = i - this.mLastFlingX;
        m = j - this.mLastFlingY;
        this.mLastFlingX = i;
        this.mLastFlingY = j;
        RecyclerView.Adapter localAdapter = RecyclerView.this.mAdapter;
        int n = 0;
        i1 = 0;
        i2 = 0;
        int i3 = 0;
        if (localAdapter != null)
        {
          RecyclerView.this.eatRequestLayout();
          RecyclerView.this.onEnterLayoutOrScroll();
          TraceCompat.beginSection("RV Scroll");
          n = 0;
          i1 = 0;
          if (k != 0)
          {
            n = RecyclerView.this.mLayout.scrollHorizontallyBy(k, RecyclerView.this.mRecycler, RecyclerView.this.mState);
            i1 = k - n;
          }
          i2 = 0;
          i3 = 0;
          if (m != 0)
          {
            i3 = RecyclerView.this.mLayout.scrollVerticallyBy(m, RecyclerView.this.mRecycler, RecyclerView.this.mState);
            i2 = m - i3;
          }
          TraceCompat.endSection();
          if (RecyclerView.this.supportsChangeAnimations())
          {
            int i12 = RecyclerView.this.mChildHelper.getChildCount();
            for (int i13 = 0; i13 < i12; i13++)
            {
              View localView1 = RecyclerView.this.mChildHelper.getChildAt(i13);
              RecyclerView.ViewHolder localViewHolder = RecyclerView.this.getChildViewHolder(localView1);
              if ((localViewHolder != null) && (localViewHolder.mShadowingHolder != null))
              {
                View localView2 = localViewHolder.mShadowingHolder.itemView;
                int i14 = localView1.getLeft();
                int i15 = localView1.getTop();
                if ((i14 != localView2.getLeft()) || (i15 != localView2.getTop()))
                  localView2.layout(i14, i15, i14 + localView2.getWidth(), i15 + localView2.getHeight());
              }
            }
          }
          if ((localSmoothScroller != null) && (!localSmoothScroller.isPendingInitialRun()) && (localSmoothScroller.isRunning()))
          {
            i11 = RecyclerView.this.mState.getItemCount();
            if (i11 != 0)
              break label736;
            localSmoothScroller.stop();
          }
          RecyclerView.this.onExitLayoutOrScroll();
          RecyclerView.this.resumeRequestLayout(false);
        }
        if (!RecyclerView.this.mItemDecorations.isEmpty())
          RecyclerView.this.invalidate();
        if (ViewCompat.getOverScrollMode(RecyclerView.this) != 2)
          RecyclerView.this.considerReleasingGlowsOnScroll(k, m);
        if ((i1 != 0) || (i2 != 0))
        {
          i4 = (int)localScrollerCompat.getCurrVelocity();
          i5 = 0;
          if (i1 != i)
          {
            if (i1 >= 0)
              break label787;
            i5 = -i4;
          }
          int i6 = i2;
          i7 = 0;
          if (i6 != j)
          {
            if (i2 >= 0)
              break label805;
            i7 = -i4;
          }
          if (ViewCompat.getOverScrollMode(RecyclerView.this) != 2)
            RecyclerView.this.absorbGlows(i5, i7);
          if (((i5 != 0) || (i1 == i) || (localScrollerCompat.getFinalX() == 0)) && ((i7 != 0) || (i2 == j) || (localScrollerCompat.getFinalY() == 0)))
            localScrollerCompat.abortAnimation();
        }
        if ((n != 0) || (i3 != 0))
          RecyclerView.this.dispatchOnScrolled(n, i3);
        if (!RecyclerView.this.awakenScrollBars())
          RecyclerView.this.invalidate();
        if ((m == 0) || (!RecyclerView.this.mLayout.canScrollVertically()) || (i3 != m))
          break label823;
        i8 = 1;
        if ((k == 0) || (!RecyclerView.this.mLayout.canScrollHorizontally()) || (n != k))
          break label829;
        i9 = 1;
        if (((k != 0) || (m != 0)) && (i9 == 0) && (i8 == 0))
          break label835;
        i10 = 1;
        label694: if ((!localScrollerCompat.isFinished()) && (i10 != 0))
          break label841;
        RecyclerView.this.setScrollState(0);
      }
      while (true)
      {
        if ((localSmoothScroller != null) && (localSmoothScroller.isPendingInitialRun()))
          localSmoothScroller.onAnimation(0, 0);
        enableRunOnAnimationRequests();
        return;
        label736: if (localSmoothScroller.getTargetPosition() >= i11)
        {
          localSmoothScroller.setTargetPosition(i11 - 1);
          localSmoothScroller.onAnimation(k - i1, m - i2);
          break;
        }
        localSmoothScroller.onAnimation(k - i1, m - i2);
        break;
        label787: if (i1 > 0)
        {
          i5 = i4;
          break label490;
        }
        i5 = 0;
        break label490;
        label805: if (i2 > 0)
        {
          i7 = i4;
          break label514;
        }
        i7 = 0;
        break label514;
        label823: i8 = 0;
        break label643;
        label829: i9 = 0;
        break label671;
        label835: i10 = 0;
        break label694;
        label841: postOnAnimation();
      }
    }

    public void smoothScrollBy(int paramInt1, int paramInt2)
    {
      smoothScrollBy(paramInt1, paramInt2, 0, 0);
    }

    public void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3)
    {
      smoothScrollBy(paramInt1, paramInt2, paramInt3, RecyclerView.sQuinticInterpolator);
    }

    public void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      smoothScrollBy(paramInt1, paramInt2, computeScrollDuration(paramInt1, paramInt2, paramInt3, paramInt4));
    }

    public void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
    {
      if (this.mInterpolator != paramInterpolator)
      {
        this.mInterpolator = paramInterpolator;
        this.mScroller = ScrollerCompat.create(RecyclerView.this.getContext(), paramInterpolator);
      }
      RecyclerView.this.setScrollState(2);
      this.mLastFlingY = 0;
      this.mLastFlingX = 0;
      this.mScroller.startScroll(0, 0, paramInt1, paramInt2, paramInt3);
      postOnAnimation();
    }

    public void stop()
    {
      RecyclerView.this.removeCallbacks(this);
      this.mScroller.abortAnimation();
    }
  }

  public static abstract class ViewHolder
  {
    static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
    static final int FLAG_BOUND = 1;
    static final int FLAG_CHANGED = 64;
    static final int FLAG_IGNORE = 128;
    static final int FLAG_INVALID = 4;
    static final int FLAG_NOT_RECYCLABLE = 16;
    static final int FLAG_REMOVED = 8;
    static final int FLAG_RETURNED_FROM_SCRAP = 32;
    static final int FLAG_TMP_DETACHED = 256;
    static final int FLAG_UPDATE = 2;
    public final View itemView;
    private int mFlags;
    private int mIsRecyclableCount = 0;
    long mItemId = -1L;
    int mItemViewType = -1;
    int mOldPosition = -1;
    RecyclerView mOwnerRecyclerView;
    int mPosition = -1;
    int mPreLayoutPosition = -1;
    private RecyclerView.Recycler mScrapContainer = null;
    ViewHolder mShadowedHolder = null;
    ViewHolder mShadowingHolder = null;

    public ViewHolder(View paramView)
    {
      if (paramView == null)
        throw new IllegalArgumentException("itemView may not be null");
      this.itemView = paramView;
    }

    private boolean doesTransientStatePreventRecycling()
    {
      return ((0x10 & this.mFlags) == 0) && (ViewCompat.hasTransientState(this.itemView));
    }

    private boolean shouldBeKeptAsChild()
    {
      return (0x10 & this.mFlags) != 0;
    }

    void addFlags(int paramInt)
    {
      this.mFlags = (paramInt | this.mFlags);
    }

    void clearOldPosition()
    {
      this.mOldPosition = -1;
      this.mPreLayoutPosition = -1;
    }

    void clearReturnedFromScrapFlag()
    {
      this.mFlags = (0xFFFFFFDF & this.mFlags);
    }

    void clearTmpDetachFlag()
    {
      this.mFlags = (0xFFFFFEFF & this.mFlags);
    }

    void flagRemovedAndOffsetPosition(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      addFlags(8);
      offsetPosition(paramInt2, paramBoolean);
      this.mPosition = paramInt1;
    }

    public final int getAdapterPosition()
    {
      if (this.mOwnerRecyclerView == null)
        return -1;
      return this.mOwnerRecyclerView.getAdapterPositionFor(this);
    }

    public final long getItemId()
    {
      return this.mItemId;
    }

    public final int getItemViewType()
    {
      return this.mItemViewType;
    }

    public final int getLayoutPosition()
    {
      if (this.mPreLayoutPosition == -1)
        return this.mPosition;
      return this.mPreLayoutPosition;
    }

    public final int getOldPosition()
    {
      return this.mOldPosition;
    }

    @Deprecated
    public final int getPosition()
    {
      if (this.mPreLayoutPosition == -1)
        return this.mPosition;
      return this.mPreLayoutPosition;
    }

    boolean hasAnyOfTheFlags(int paramInt)
    {
      return (paramInt & this.mFlags) != 0;
    }

    boolean isAdapterPositionUnknown()
    {
      return ((0x200 & this.mFlags) != 0) || (isInvalid());
    }

    boolean isBound()
    {
      return (0x1 & this.mFlags) != 0;
    }

    boolean isChanged()
    {
      return (0x40 & this.mFlags) != 0;
    }

    boolean isInvalid()
    {
      return (0x4 & this.mFlags) != 0;
    }

    public final boolean isRecyclable()
    {
      return ((0x10 & this.mFlags) == 0) && (!ViewCompat.hasTransientState(this.itemView));
    }

    boolean isRemoved()
    {
      return (0x8 & this.mFlags) != 0;
    }

    boolean isScrap()
    {
      return this.mScrapContainer != null;
    }

    boolean isTmpDetached()
    {
      return (0x100 & this.mFlags) != 0;
    }

    boolean needsUpdate()
    {
      return (0x2 & this.mFlags) != 0;
    }

    void offsetPosition(int paramInt, boolean paramBoolean)
    {
      if (this.mOldPosition == -1)
        this.mOldPosition = this.mPosition;
      if (this.mPreLayoutPosition == -1)
        this.mPreLayoutPosition = this.mPosition;
      if (paramBoolean)
        this.mPreLayoutPosition = (paramInt + this.mPreLayoutPosition);
      this.mPosition = (paramInt + this.mPosition);
      if (this.itemView.getLayoutParams() != null)
        ((RecyclerView.LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
    }

    void resetInternal()
    {
      this.mFlags = 0;
      this.mPosition = -1;
      this.mOldPosition = -1;
      this.mItemId = -1L;
      this.mPreLayoutPosition = -1;
      this.mIsRecyclableCount = 0;
      this.mShadowedHolder = null;
      this.mShadowingHolder = null;
    }

    void saveOldPosition()
    {
      if (this.mOldPosition == -1)
        this.mOldPosition = this.mPosition;
    }

    void setFlags(int paramInt1, int paramInt2)
    {
      this.mFlags = (this.mFlags & (paramInt2 ^ 0xFFFFFFFF) | paramInt1 & paramInt2);
    }

    public final void setIsRecyclable(boolean paramBoolean)
    {
      int i;
      if (paramBoolean)
      {
        i = -1 + this.mIsRecyclableCount;
        this.mIsRecyclableCount = i;
        if (this.mIsRecyclableCount >= 0)
          break label64;
        this.mIsRecyclableCount = 0;
        Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
      }
      label64: 
      do
      {
        return;
        i = 1 + this.mIsRecyclableCount;
        break;
        if ((!paramBoolean) && (this.mIsRecyclableCount == 1))
        {
          this.mFlags = (0x10 | this.mFlags);
          return;
        }
      }
      while ((!paramBoolean) || (this.mIsRecyclableCount != 0));
      this.mFlags = (0xFFFFFFEF & this.mFlags);
    }

    void setScrapContainer(RecyclerView.Recycler paramRecycler)
    {
      this.mScrapContainer = paramRecycler;
    }

    boolean shouldIgnore()
    {
      return (0x80 & this.mFlags) != 0;
    }

    void stopIgnoring()
    {
      this.mFlags = (0xFFFFFF7F & this.mFlags);
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
      if (isScrap())
        localStringBuilder.append(" scrap");
      if (isInvalid())
        localStringBuilder.append(" invalid");
      if (!isBound())
        localStringBuilder.append(" unbound");
      if (needsUpdate())
        localStringBuilder.append(" update");
      if (isRemoved())
        localStringBuilder.append(" removed");
      if (shouldIgnore())
        localStringBuilder.append(" ignored");
      if (isChanged())
        localStringBuilder.append(" changed");
      if (isTmpDetached())
        localStringBuilder.append(" tmpDetached");
      if (!isRecyclable())
        localStringBuilder.append(" not recyclable(" + this.mIsRecyclableCount + ")");
      if (isAdapterPositionUnknown())
        localStringBuilder.append("undefined adapter position");
      if (this.itemView.getParent() == null)
        localStringBuilder.append(" no parent");
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }

    void unScrap()
    {
      this.mScrapContainer.unscrapView(this);
    }

    boolean wasReturnedFromScrap()
    {
      return (0x20 & this.mFlags) != 0;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RecyclerView
 * JD-Core Version:    0.6.2
 */