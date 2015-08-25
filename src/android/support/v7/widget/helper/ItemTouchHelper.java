package android.support.v7.widget.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.animation.AnimatorListenerCompat;
import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R.dimen;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ChildDrawingOrderCallback;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper extends RecyclerView.ItemDecoration
  implements RecyclerView.OnChildAttachStateChangeListener
{
  private static final int ACTION_MODE_DRAG_MASK = 16711680;
  private static final int ACTION_MODE_IDLE_MASK = 255;
  private static final int ACTION_MODE_SWIPE_MASK = 65280;
  public static final int ACTION_STATE_DRAG = 2;
  public static final int ACTION_STATE_IDLE = 0;
  public static final int ACTION_STATE_SWIPE = 1;
  private static final int ACTIVE_POINTER_ID_NONE = -1;
  public static final int ANIMATION_TYPE_DRAG = 8;
  public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
  public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
  private static final boolean DEBUG = false;
  private static final int DIRECTION_FLAG_COUNT = 8;
  public static final int DOWN = 2;
  public static final int END = 32;
  public static final int LEFT = 4;
  public static final int RIGHT = 8;
  public static final int START = 16;
  private static final String TAG = "ItemTouchHelper";
  public static final int UP = 1;
  int mActionState = 0;
  int mActivePointerId = -1;
  Callback mCallback;
  private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
  private List<Integer> mDistances;
  private long mDragScrollStartTimeInMs;
  float mDx;
  float mDy;
  private GestureDetectorCompat mGestureDetector;
  float mInitialTouchX;
  float mInitialTouchY;
  private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener()
  {
    public boolean onInterceptTouchEvent(RecyclerView paramAnonymousRecyclerView, MotionEvent paramAnonymousMotionEvent)
    {
      ItemTouchHelper.this.mGestureDetector.onTouchEvent(paramAnonymousMotionEvent);
      int i = MotionEventCompat.getActionMasked(paramAnonymousMotionEvent);
      if (i == 0)
      {
        ItemTouchHelper.this.mActivePointerId = MotionEventCompat.getPointerId(paramAnonymousMotionEvent, 0);
        ItemTouchHelper.this.mInitialTouchX = paramAnonymousMotionEvent.getX();
        ItemTouchHelper.this.mInitialTouchY = paramAnonymousMotionEvent.getY();
        ItemTouchHelper.this.obtainVelocityTracker();
        if (ItemTouchHelper.this.mSelected == null)
        {
          ItemTouchHelper.RecoverAnimation localRecoverAnimation = ItemTouchHelper.this.findAnimation(paramAnonymousMotionEvent);
          if (localRecoverAnimation != null)
          {
            ItemTouchHelper localItemTouchHelper1 = ItemTouchHelper.this;
            localItemTouchHelper1.mInitialTouchX -= localRecoverAnimation.mX;
            ItemTouchHelper localItemTouchHelper2 = ItemTouchHelper.this;
            localItemTouchHelper2.mInitialTouchY -= localRecoverAnimation.mY;
            ItemTouchHelper.this.endRecoverAnimation(localRecoverAnimation.mViewHolder, true);
            if (ItemTouchHelper.this.mPendingCleanup.remove(localRecoverAnimation.mViewHolder.itemView))
              ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, localRecoverAnimation.mViewHolder);
            ItemTouchHelper.this.select(localRecoverAnimation.mViewHolder, localRecoverAnimation.mActionState);
            ItemTouchHelper.this.updateDxDy(paramAnonymousMotionEvent, ItemTouchHelper.this.mSelectedFlags, 0);
          }
        }
      }
      while (true)
      {
        if (ItemTouchHelper.this.mVelocityTracker != null)
          ItemTouchHelper.this.mVelocityTracker.addMovement(paramAnonymousMotionEvent);
        if (ItemTouchHelper.this.mSelected == null)
          break;
        return true;
        if ((i == 3) || (i == 1))
        {
          ItemTouchHelper.this.mActivePointerId = -1;
          ItemTouchHelper.this.select(null, 0);
        }
        else if (ItemTouchHelper.this.mActivePointerId != -1)
        {
          int j = MotionEventCompat.findPointerIndex(paramAnonymousMotionEvent, ItemTouchHelper.this.mActivePointerId);
          if (j >= 0)
            ItemTouchHelper.this.checkSelectForSwipe(i, paramAnonymousMotionEvent, j);
        }
      }
      return false;
    }

    public void onRequestDisallowInterceptTouchEvent(boolean paramAnonymousBoolean)
    {
      if (!paramAnonymousBoolean)
        return;
      ItemTouchHelper.this.select(null, 0);
    }

    public void onTouchEvent(RecyclerView paramAnonymousRecyclerView, MotionEvent paramAnonymousMotionEvent)
    {
      ItemTouchHelper.this.mGestureDetector.onTouchEvent(paramAnonymousMotionEvent);
      if (ItemTouchHelper.this.mVelocityTracker != null)
        ItemTouchHelper.this.mVelocityTracker.addMovement(paramAnonymousMotionEvent);
      if (ItemTouchHelper.this.mActivePointerId == -1);
      int k;
      do
      {
        int j;
        RecyclerView.ViewHolder localViewHolder;
        do
        {
          int i;
          do
          {
            return;
            i = MotionEventCompat.getActionMasked(paramAnonymousMotionEvent);
            j = MotionEventCompat.findPointerIndex(paramAnonymousMotionEvent, ItemTouchHelper.this.mActivePointerId);
            if (j >= 0)
              ItemTouchHelper.this.checkSelectForSwipe(i, paramAnonymousMotionEvent, j);
            localViewHolder = ItemTouchHelper.this.mSelected;
          }
          while (localViewHolder == null);
          switch (i)
          {
          case 4:
          case 5:
          default:
            return;
          case 1:
          case 3:
            if (ItemTouchHelper.this.mVelocityTracker != null)
              ItemTouchHelper.this.mVelocityTracker.computeCurrentVelocity(1000, ItemTouchHelper.this.mRecyclerView.getMaxFlingVelocity());
            ItemTouchHelper.this.select(null, 0);
            ItemTouchHelper.this.mActivePointerId = -1;
            return;
          case 2:
          case 6:
          }
        }
        while (j < 0);
        ItemTouchHelper.this.updateDxDy(paramAnonymousMotionEvent, ItemTouchHelper.this.mSelectedFlags, j);
        ItemTouchHelper.this.moveIfNecessary(localViewHolder);
        ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
        ItemTouchHelper.this.mScrollRunnable.run();
        ItemTouchHelper.this.mRecyclerView.invalidate();
        return;
        k = MotionEventCompat.getActionIndex(paramAnonymousMotionEvent);
      }
      while (MotionEventCompat.getPointerId(paramAnonymousMotionEvent, k) != ItemTouchHelper.this.mActivePointerId);
      if (ItemTouchHelper.this.mVelocityTracker != null)
        ItemTouchHelper.this.mVelocityTracker.computeCurrentVelocity(1000, ItemTouchHelper.this.mRecyclerView.getMaxFlingVelocity());
      int m = 0;
      if (k == 0)
        m = 1;
      ItemTouchHelper.this.mActivePointerId = MotionEventCompat.getPointerId(paramAnonymousMotionEvent, m);
      ItemTouchHelper.this.updateDxDy(paramAnonymousMotionEvent, ItemTouchHelper.this.mSelectedFlags, k);
    }
  };
  private View mOverdrawChild = null;
  private int mOverdrawChildPosition = -1;
  final List<View> mPendingCleanup = new ArrayList();
  List<RecoverAnimation> mRecoverAnimations = new ArrayList();
  private RecyclerView mRecyclerView;
  private final Runnable mScrollRunnable = new Runnable()
  {
    public void run()
    {
      if ((ItemTouchHelper.this.mSelected != null) && (ItemTouchHelper.this.scrollIfNecessary()))
      {
        if (ItemTouchHelper.this.mSelected != null)
          ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
        ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
        ViewCompat.postOnAnimation(ItemTouchHelper.this.mRecyclerView, this);
      }
    }
  };
  RecyclerView.ViewHolder mSelected = null;
  int mSelectedFlags;
  float mSelectedStartX;
  float mSelectedStartY;
  private int mSlop;
  private List<RecyclerView.ViewHolder> mSwapTargets;
  private final float[] mTmpPosition = new float[2];
  private Rect mTmpRect;
  private VelocityTracker mVelocityTracker;

  public ItemTouchHelper(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }

  private void addChildDrawingOrderCallback()
  {
    if (Build.VERSION.SDK_INT >= 21)
      return;
    if (this.mChildDrawingOrderCallback == null)
      this.mChildDrawingOrderCallback = new RecyclerView.ChildDrawingOrderCallback()
      {
        public int onGetChildDrawingOrder(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          if (ItemTouchHelper.this.mOverdrawChild == null);
          int i;
          do
          {
            return paramAnonymousInt2;
            i = ItemTouchHelper.this.mOverdrawChildPosition;
            if (i == -1)
            {
              i = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
              ItemTouchHelper.access$2302(ItemTouchHelper.this, i);
            }
            if (paramAnonymousInt2 == paramAnonymousInt1 - 1)
              return i;
          }
          while (paramAnonymousInt2 < i);
          return paramAnonymousInt2 + 1;
        }
      };
    this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
  }

  private int checkHorizontalSwipe(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if ((paramInt & 0xC) != 0)
    {
      int i;
      float f2;
      if (this.mDx > 0.0F)
      {
        i = 8;
        if ((this.mVelocityTracker == null) || (this.mActivePointerId <= -1))
          break label102;
        f2 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
        if (f2 <= 0.0F)
          break label96;
      }
      label96: for (int j = 8; ; j = 4)
      {
        if (((j & paramInt) == 0) || (i != j) || (Math.abs(f2) < this.mRecyclerView.getMinFlingVelocity()))
          break label102;
        return j;
        i = 4;
        break;
      }
      label102: float f1 = this.mRecyclerView.getWidth() * this.mCallback.getSwipeThreshold(paramViewHolder);
      if (((paramInt & i) != 0) && (Math.abs(this.mDx) > f1))
        return i;
    }
    return 0;
  }

  private boolean checkSelectForSwipe(int paramInt1, MotionEvent paramMotionEvent, int paramInt2)
  {
    if ((this.mSelected != null) || (paramInt1 != 2) || (this.mActionState == 2) || (!this.mCallback.isItemViewSwipeEnabled()))
      return false;
    if (this.mRecyclerView.getScrollState() == 1)
      return false;
    RecyclerView.ViewHolder localViewHolder = findSwipedView(paramMotionEvent);
    if (localViewHolder == null)
      return false;
    int i = (0xFF00 & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, localViewHolder)) >> 8;
    if (i == 0)
      return false;
    float f1 = MotionEventCompat.getX(paramMotionEvent, paramInt2);
    float f2 = MotionEventCompat.getY(paramMotionEvent, paramInt2);
    float f3 = f1 - this.mInitialTouchX;
    float f4 = f2 - this.mInitialTouchY;
    float f5 = Math.abs(f3);
    float f6 = Math.abs(f4);
    if ((f5 < this.mSlop) && (f6 < this.mSlop))
      return false;
    if (f5 > f6)
    {
      if ((f3 < 0.0F) && ((i & 0x4) == 0))
        return false;
      if ((f3 > 0.0F) && ((i & 0x8) == 0))
        return false;
    }
    else
    {
      if ((f4 < 0.0F) && ((i & 0x1) == 0))
        return false;
      if ((f4 > 0.0F) && ((i & 0x2) == 0))
        return false;
    }
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
    select(localViewHolder, 1);
    return true;
  }

  private int checkVerticalSwipe(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if ((paramInt & 0x3) != 0)
    {
      int i;
      float f2;
      if (this.mDy > 0.0F)
      {
        i = 2;
        if ((this.mVelocityTracker == null) || (this.mActivePointerId <= -1))
          break label99;
        f2 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
        if (f2 <= 0.0F)
          break label93;
      }
      label93: for (int j = 2; ; j = 1)
      {
        if (((j & paramInt) == 0) || (j != i) || (Math.abs(f2) < this.mRecyclerView.getMinFlingVelocity()))
          break label99;
        return j;
        i = 1;
        break;
      }
      label99: float f1 = this.mRecyclerView.getHeight() * this.mCallback.getSwipeThreshold(paramViewHolder);
      if (((paramInt & i) != 0) && (Math.abs(this.mDy) > f1))
        return i;
    }
    return 0;
  }

  private void destroyCallbacks()
  {
    this.mRecyclerView.removeItemDecoration(this);
    this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
    this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
    for (int i = -1 + this.mRecoverAnimations.size(); i >= 0; i--)
    {
      RecoverAnimation localRecoverAnimation = (RecoverAnimation)this.mRecoverAnimations.get(0);
      this.mCallback.clearView(this.mRecyclerView, localRecoverAnimation.mViewHolder);
    }
    this.mRecoverAnimations.clear();
    this.mOverdrawChild = null;
    this.mOverdrawChildPosition = -1;
    releaseVelocityTracker();
  }

  private int endRecoverAnimation(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
  {
    for (int i = -1 + this.mRecoverAnimations.size(); i >= 0; i--)
    {
      RecoverAnimation localRecoverAnimation = (RecoverAnimation)this.mRecoverAnimations.get(i);
      if (localRecoverAnimation.mViewHolder == paramViewHolder)
      {
        localRecoverAnimation.mOverridden = (paramBoolean | localRecoverAnimation.mOverridden);
        if (!localRecoverAnimation.mEnded)
          localRecoverAnimation.cancel();
        this.mRecoverAnimations.remove(i);
        localRecoverAnimation.mViewHolder.setIsRecyclable(true);
        return localRecoverAnimation.mAnimationType;
      }
    }
    return 0;
  }

  private RecoverAnimation findAnimation(MotionEvent paramMotionEvent)
  {
    RecoverAnimation localRecoverAnimation;
    if (this.mRecoverAnimations.isEmpty())
    {
      localRecoverAnimation = null;
      return localRecoverAnimation;
    }
    View localView = findChildView(paramMotionEvent);
    for (int i = -1 + this.mRecoverAnimations.size(); ; i--)
    {
      if (i < 0)
        break label73;
      localRecoverAnimation = (RecoverAnimation)this.mRecoverAnimations.get(i);
      if (localRecoverAnimation.mViewHolder.itemView == localView)
        break;
    }
    label73: return null;
  }

  private View findChildView(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    if (this.mSelected != null)
    {
      View localView2 = this.mSelected.itemView;
      if (hitTest(localView2, f1, f2, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy))
        return localView2;
    }
    for (int i = -1 + this.mRecoverAnimations.size(); i >= 0; i--)
    {
      RecoverAnimation localRecoverAnimation = (RecoverAnimation)this.mRecoverAnimations.get(i);
      View localView1 = localRecoverAnimation.mViewHolder.itemView;
      if (hitTest(localView1, f1, f2, localRecoverAnimation.mX, localRecoverAnimation.mY))
        return localView1;
    }
    return this.mRecyclerView.findChildViewUnder(f1, f2);
  }

  private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder paramViewHolder)
  {
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    int i4;
    label135: View localView;
    if (this.mSwapTargets == null)
    {
      this.mSwapTargets = new ArrayList();
      this.mDistances = new ArrayList();
      int i = this.mCallback.getBoundingBoxMargin();
      j = Math.round(this.mSelectedStartX + this.mDx) - i;
      k = Math.round(this.mSelectedStartY + this.mDy) - i;
      m = j + paramViewHolder.itemView.getWidth() + i * 2;
      n = k + paramViewHolder.itemView.getHeight() + i * 2;
      i1 = (j + m) / 2;
      i2 = (k + n) / 2;
      RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
      int i3 = localLayoutManager.getChildCount();
      i4 = 0;
      if (i4 >= i3)
        break label407;
      localView = localLayoutManager.getChildAt(i4);
      if (localView != paramViewHolder.itemView)
        break label187;
    }
    while (true)
    {
      i4++;
      break label135;
      this.mSwapTargets.clear();
      this.mDistances.clear();
      break;
      label187: if ((localView.getBottom() >= k) && (localView.getTop() <= n) && (localView.getRight() >= j) && (localView.getLeft() <= m))
      {
        RecyclerView.ViewHolder localViewHolder = this.mRecyclerView.getChildViewHolder(localView);
        if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, localViewHolder))
        {
          int i5 = Math.abs(i1 - (localView.getLeft() + localView.getRight()) / 2);
          int i6 = Math.abs(i2 - (localView.getTop() + localView.getBottom()) / 2);
          int i7 = i5 * i5 + i6 * i6;
          int i8 = 0;
          int i9 = this.mSwapTargets.size();
          for (int i10 = 0; (i10 < i9) && (i7 > ((Integer)this.mDistances.get(i10)).intValue()); i10++)
            i8++;
          this.mSwapTargets.add(i8, localViewHolder);
          List localList = this.mDistances;
          Integer localInteger = Integer.valueOf(i7);
          localList.add(i8, localInteger);
        }
      }
    }
    label407: return this.mSwapTargets;
  }

  private RecyclerView.ViewHolder findSwipedView(MotionEvent paramMotionEvent)
  {
    RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
    if (this.mActivePointerId == -1);
    View localView;
    do
    {
      float f3;
      float f4;
      do
      {
        return null;
        int i = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId);
        float f1 = MotionEventCompat.getX(paramMotionEvent, i) - this.mInitialTouchX;
        float f2 = MotionEventCompat.getY(paramMotionEvent, i) - this.mInitialTouchY;
        f3 = Math.abs(f1);
        f4 = Math.abs(f2);
      }
      while (((f3 < this.mSlop) && (f4 < this.mSlop)) || ((f3 > f4) && (localLayoutManager.canScrollHorizontally())) || ((f4 > f3) && (localLayoutManager.canScrollVertically())));
      localView = findChildView(paramMotionEvent);
    }
    while (localView == null);
    return this.mRecyclerView.getChildViewHolder(localView);
  }

  private void getSelectedDxDy(float[] paramArrayOfFloat)
  {
    if ((0xC & this.mSelectedFlags) != 0)
      paramArrayOfFloat[0] = (this.mSelectedStartX + this.mDx - this.mSelected.itemView.getLeft());
    while ((0x3 & this.mSelectedFlags) != 0)
    {
      paramArrayOfFloat[1] = (this.mSelectedStartY + this.mDy - this.mSelected.itemView.getTop());
      return;
      paramArrayOfFloat[0] = ViewCompat.getTranslationX(this.mSelected.itemView);
    }
    paramArrayOfFloat[1] = ViewCompat.getTranslationY(this.mSelected.itemView);
  }

  private boolean hasRunningRecoverAnim()
  {
    int i = this.mRecoverAnimations.size();
    for (int j = 0; j < i; j++)
      if (!((RecoverAnimation)this.mRecoverAnimations.get(j)).mEnded)
        return true;
    return false;
  }

  private static boolean hitTest(View paramView, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return (paramFloat1 >= paramFloat3) && (paramFloat1 <= paramFloat3 + paramView.getWidth()) && (paramFloat2 >= paramFloat4) && (paramFloat2 <= paramFloat4 + paramView.getHeight());
  }

  private void initGestureDetector()
  {
    if (this.mGestureDetector != null)
      return;
    this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), new ItemTouchHelperGestureListener(null));
  }

  private void moveIfNecessary(RecyclerView.ViewHolder paramViewHolder)
  {
    if (this.mRecyclerView.isLayoutRequested());
    label10: int i;
    int j;
    RecyclerView.ViewHolder localViewHolder;
    int k;
    int m;
    do
    {
      List localList;
      do
      {
        float f;
        do
        {
          break label10;
          do
            return;
          while (this.mActionState != 2);
          f = this.mCallback.getMoveThreshold(paramViewHolder);
          i = (int)(this.mSelectedStartX + this.mDx);
          j = (int)(this.mSelectedStartY + this.mDy);
        }
        while ((Math.abs(j - paramViewHolder.itemView.getTop()) < f * paramViewHolder.itemView.getHeight()) && (Math.abs(i - paramViewHolder.itemView.getLeft()) < f * paramViewHolder.itemView.getWidth()));
        localList = findSwapTargets(paramViewHolder);
      }
      while (localList.size() == 0);
      localViewHolder = this.mCallback.chooseDropTarget(paramViewHolder, localList, i, j);
      if (localViewHolder == null)
      {
        this.mSwapTargets.clear();
        this.mDistances.clear();
        return;
      }
      k = localViewHolder.getAdapterPosition();
      m = paramViewHolder.getAdapterPosition();
    }
    while (!this.mCallback.onMove(this.mRecyclerView, paramViewHolder, localViewHolder));
    this.mCallback.onMoved(this.mRecyclerView, paramViewHolder, m, localViewHolder, k, i, j);
  }

  private void obtainVelocityTracker()
  {
    if (this.mVelocityTracker != null)
      this.mVelocityTracker.recycle();
    this.mVelocityTracker = VelocityTracker.obtain();
  }

  private void postDispatchSwipe(final RecoverAnimation paramRecoverAnimation, final int paramInt)
  {
    this.mRecyclerView.post(new Runnable()
    {
      public void run()
      {
        if ((ItemTouchHelper.this.mRecyclerView != null) && (ItemTouchHelper.this.mRecyclerView.isAttachedToWindow()) && (!paramRecoverAnimation.mOverridden) && (paramRecoverAnimation.mViewHolder.getAdapterPosition() != -1))
        {
          RecyclerView.ItemAnimator localItemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
          if (((localItemAnimator == null) || (!localItemAnimator.isRunning(null))) && (!ItemTouchHelper.this.hasRunningRecoverAnim()))
            ItemTouchHelper.this.mCallback.onSwiped(paramRecoverAnimation.mViewHolder, paramInt);
        }
        else
        {
          return;
        }
        ItemTouchHelper.this.mRecyclerView.post(this);
      }
    });
  }

  private void releaseVelocityTracker()
  {
    if (this.mVelocityTracker != null)
    {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }

  private void removeChildDrawingOrderCallbackIfNecessary(View paramView)
  {
    if (paramView == this.mOverdrawChild)
    {
      this.mOverdrawChild = null;
      if (this.mChildDrawingOrderCallback != null)
        this.mRecyclerView.setChildDrawingOrderCallback(null);
    }
  }

  private boolean scrollIfNecessary()
  {
    if (this.mSelected == null)
    {
      this.mDragScrollStartTimeInMs = -9223372036854775808L;
      return false;
    }
    long l1 = System.currentTimeMillis();
    long l2;
    int i;
    int i1;
    label141: int j;
    int k;
    if (this.mDragScrollStartTimeInMs == -9223372036854775808L)
    {
      l2 = 0L;
      RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
      if (this.mTmpRect == null)
        this.mTmpRect = new Rect();
      localLayoutManager.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
      boolean bool1 = localLayoutManager.canScrollHorizontally();
      i = 0;
      if (bool1)
      {
        i1 = (int)(this.mSelectedStartX + this.mDx);
        int i2 = i1 - this.mTmpRect.left - this.mRecyclerView.getPaddingLeft();
        if ((this.mDx >= 0.0F) || (i2 >= 0))
          break label331;
        i = i2;
      }
      boolean bool2 = localLayoutManager.canScrollVertically();
      j = 0;
      if (bool2)
      {
        k = (int)(this.mSelectedStartY + this.mDy);
        int m = k - this.mTmpRect.top - this.mRecyclerView.getPaddingTop();
        if ((this.mDy >= 0.0F) || (m >= 0))
          break label401;
        j = m;
      }
    }
    while (true)
    {
      if (i != 0)
        i = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), i, this.mRecyclerView.getWidth(), l2);
      if (j != 0)
        j = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), j, this.mRecyclerView.getHeight(), l2);
      if ((i == 0) && (j == 0))
        break label471;
      if (this.mDragScrollStartTimeInMs == -9223372036854775808L)
        this.mDragScrollStartTimeInMs = l1;
      this.mRecyclerView.scrollBy(i, j);
      return true;
      l2 = l1 - this.mDragScrollStartTimeInMs;
      break;
      label331: boolean bool4 = this.mDx < 0.0F;
      i = 0;
      if (!bool4)
        break label141;
      int i3 = i1 + this.mSelected.itemView.getWidth() + this.mTmpRect.right - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight());
      i = 0;
      if (i3 <= 0)
        break label141;
      i = i3;
      break label141;
      label401: boolean bool3 = this.mDy < 0.0F;
      j = 0;
      if (bool3)
      {
        int n = k + this.mSelected.itemView.getHeight() + this.mTmpRect.bottom - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
        j = 0;
        if (n > 0)
          j = n;
      }
    }
    label471: this.mDragScrollStartTimeInMs = -9223372036854775808L;
    return false;
  }

  private void select(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if ((paramViewHolder == this.mSelected) && (paramInt == this.mActionState))
      return;
    this.mDragScrollStartTimeInMs = -9223372036854775808L;
    int i = this.mActionState;
    endRecoverAnimation(paramViewHolder, true);
    this.mActionState = paramInt;
    if (paramInt == 2)
    {
      this.mOverdrawChild = paramViewHolder.itemView;
      addChildDrawingOrderCallback();
    }
    int j = -1 + (1 << 8 + paramInt * 8);
    RecyclerView.ViewHolder localViewHolder1 = this.mSelected;
    int k = 0;
    final RecyclerView.ViewHolder localViewHolder2;
    final int m;
    float f2;
    float f1;
    label182: int n;
    label191: ViewParent localViewParent;
    if (localViewHolder1 != null)
    {
      localViewHolder2 = this.mSelected;
      if (localViewHolder2.itemView.getParent() == null)
        break label519;
      if (i == 2)
      {
        m = 0;
        releaseVelocityTracker();
      }
    }
    else
    {
      switch (m)
      {
      default:
        f2 = 0.0F;
        f1 = 0.0F;
        if (i == 2)
        {
          n = 8;
          getSelectedDxDy(this.mTmpPosition);
          float f3 = this.mTmpPosition[0];
          float f4 = this.mTmpPosition[1];
          RecoverAnimation local3 = new RecoverAnimation(localViewHolder2, n, i, f3, f4, f2, f1, m)
          {
            public void onAnimationEnd(ValueAnimatorCompat paramAnonymousValueAnimatorCompat)
            {
              super.onAnimationEnd(paramAnonymousValueAnimatorCompat);
              if (this.mOverridden);
              while (true)
              {
                return;
                if (m <= 0)
                  ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, localViewHolder2);
                while (ItemTouchHelper.this.mOverdrawChild == localViewHolder2.itemView)
                {
                  ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(localViewHolder2.itemView);
                  return;
                  ItemTouchHelper.this.mPendingCleanup.add(localViewHolder2.itemView);
                  this.mIsPendingCleanup = true;
                  if (m > 0)
                    ItemTouchHelper.this.postDispatchSwipe(this, m);
                }
              }
            }
          };
          local3.setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, n, f2 - f3, f1 - f4));
          this.mRecoverAnimations.add(local3);
          local3.start();
          k = 1;
          label290: this.mSelected = null;
          if (paramViewHolder != null)
          {
            this.mSelectedFlags = ((j & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, paramViewHolder)) >> 8 * this.mActionState);
            this.mSelectedStartX = paramViewHolder.itemView.getLeft();
            this.mSelectedStartY = paramViewHolder.itemView.getTop();
            this.mSelected = paramViewHolder;
            if (paramInt == 2)
              this.mSelected.itemView.performHapticFeedback(0);
          }
          localViewParent = this.mRecyclerView.getParent();
          if (localViewParent != null)
            if (this.mSelected == null)
              break label547;
        }
        break;
      case 4:
      case 8:
      case 16:
      case 32:
      case 1:
      case 2:
      }
    }
    label519: label547: for (boolean bool = true; ; bool = false)
    {
      localViewParent.requestDisallowInterceptTouchEvent(bool);
      if (k == 0)
        this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
      this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
      this.mRecyclerView.invalidate();
      return;
      m = swipeIfNecessary(localViewHolder2);
      break;
      f2 = Math.signum(this.mDx) * this.mRecyclerView.getWidth();
      f1 = 0.0F;
      break label182;
      f1 = Math.signum(this.mDy) * this.mRecyclerView.getHeight();
      f2 = 0.0F;
      break label182;
      if (m > 0)
      {
        n = 2;
        break label191;
      }
      n = 4;
      break label191;
      removeChildDrawingOrderCallbackIfNecessary(localViewHolder2.itemView);
      this.mCallback.clearView(this.mRecyclerView, localViewHolder2);
      k = 0;
      break label290;
    }
  }

  private void setupCallbacks()
  {
    this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
    this.mRecyclerView.addItemDecoration(this);
    this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
    this.mRecyclerView.addOnChildAttachStateChangeListener(this);
    initGestureDetector();
  }

  private int swipeIfNecessary(RecyclerView.ViewHolder paramViewHolder)
  {
    int m;
    if (this.mActionState == 2)
      m = 0;
    int k;
    label131: 
    do
    {
      int j;
      do
      {
        do
        {
          return m;
          int i = this.mCallback.getMovementFlags(this.mRecyclerView, paramViewHolder);
          j = (0xFF00 & this.mCallback.convertToAbsoluteDirection(i, ViewCompat.getLayoutDirection(this.mRecyclerView))) >> 8;
          if (j == 0)
            return 0;
          k = (i & 0xFF00) >> 8;
          if (Math.abs(this.mDx) <= Math.abs(this.mDy))
            break label131;
          m = checkHorizontalSwipe(paramViewHolder, j);
          if (m <= 0)
            break;
        }
        while ((k & m) != 0);
        return Callback.convertToRelativeDirection(m, ViewCompat.getLayoutDirection(this.mRecyclerView));
        m = checkVerticalSwipe(paramViewHolder, j);
      }
      while (m > 0);
      do
      {
        return 0;
        m = checkVerticalSwipe(paramViewHolder, j);
        if (m > 0)
          break;
        m = checkHorizontalSwipe(paramViewHolder, j);
      }
      while (m <= 0);
    }
    while ((k & m) != 0);
    return Callback.convertToRelativeDirection(m, ViewCompat.getLayoutDirection(this.mRecyclerView));
  }

  private void updateDxDy(MotionEvent paramMotionEvent, int paramInt1, int paramInt2)
  {
    float f1 = MotionEventCompat.getX(paramMotionEvent, paramInt2);
    float f2 = MotionEventCompat.getY(paramMotionEvent, paramInt2);
    this.mDx = (f1 - this.mInitialTouchX);
    this.mDy = (f2 - this.mInitialTouchY);
    if ((paramInt1 & 0x4) == 0)
      this.mDx = Math.max(0.0F, this.mDx);
    if ((paramInt1 & 0x8) == 0)
      this.mDx = Math.min(0.0F, this.mDx);
    if ((paramInt1 & 0x1) == 0)
      this.mDy = Math.max(0.0F, this.mDy);
    if ((paramInt1 & 0x2) == 0)
      this.mDy = Math.min(0.0F, this.mDy);
  }

  public void attachToRecyclerView(RecyclerView paramRecyclerView)
  {
    if (this.mRecyclerView == paramRecyclerView);
    do
    {
      return;
      if (this.mRecyclerView != null)
        destroyCallbacks();
      this.mRecyclerView = paramRecyclerView;
    }
    while (this.mRecyclerView == null);
    setupCallbacks();
  }

  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    paramRect.setEmpty();
  }

  public void onChildViewAttachedToWindow(View paramView)
  {
  }

  public void onChildViewDetachedFromWindow(View paramView)
  {
    removeChildDrawingOrderCallbackIfNecessary(paramView);
    RecyclerView.ViewHolder localViewHolder = this.mRecyclerView.getChildViewHolder(paramView);
    if (localViewHolder == null);
    do
    {
      return;
      if ((this.mSelected != null) && (localViewHolder == this.mSelected))
      {
        select(null, 0);
        return;
      }
      endRecoverAnimation(localViewHolder, false);
    }
    while (!this.mPendingCleanup.remove(localViewHolder.itemView));
    this.mCallback.clearView(this.mRecyclerView, localViewHolder);
  }

  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    this.mOverdrawChildPosition = -1;
    RecyclerView.ViewHolder localViewHolder = this.mSelected;
    float f1 = 0.0F;
    float f2 = 0.0F;
    if (localViewHolder != null)
    {
      getSelectedDxDy(this.mTmpPosition);
      f1 = this.mTmpPosition[0];
      f2 = this.mTmpPosition[1];
    }
    this.mCallback.onDraw(paramCanvas, paramRecyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f1, f2);
  }

  public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    RecyclerView.ViewHolder localViewHolder = this.mSelected;
    float f1 = 0.0F;
    float f2 = 0.0F;
    if (localViewHolder != null)
    {
      getSelectedDxDy(this.mTmpPosition);
      f1 = this.mTmpPosition[0];
      f2 = this.mTmpPosition[1];
    }
    this.mCallback.onDrawOver(paramCanvas, paramRecyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f1, f2);
  }

  public void startDrag(RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mCallback.hasDragFlag(this.mRecyclerView, paramViewHolder))
    {
      Log.e("ItemTouchHelper", "Start drag has been called but swiping is not enabled");
      return;
    }
    if (paramViewHolder.itemView.getParent() != this.mRecyclerView)
    {
      Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
      return;
    }
    obtainVelocityTracker();
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    select(paramViewHolder, 2);
  }

  public void startSwipe(RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, paramViewHolder))
    {
      Log.e("ItemTouchHelper", "Start swipe has been called but dragging is not enabled");
      return;
    }
    if (paramViewHolder.itemView.getParent() != this.mRecyclerView)
    {
      Log.e("ItemTouchHelper", "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
      return;
    }
    obtainVelocityTracker();
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    select(paramViewHolder, 1);
  }

  public static abstract class Callback
  {
    private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
    public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
    public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
    private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000L;
    static final int RELATIVE_DIR_FLAGS = 3158064;
    private static final Interpolator sDragScrollInterpolator = new Interpolator()
    {
      public float getInterpolation(float paramAnonymousFloat)
      {
        return paramAnonymousFloat * (paramAnonymousFloat * (paramAnonymousFloat * (paramAnonymousFloat * paramAnonymousFloat)));
      }
    };
    private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator()
    {
      public float getInterpolation(float paramAnonymousFloat)
      {
        float f = paramAnonymousFloat - 1.0F;
        return 1.0F + f * (f * (f * (f * f)));
      }
    };
    private static final ItemTouchUIUtil sUICallback = new ItemTouchUIUtilImpl.Gingerbread();
    private int mCachedMaxScrollSpeed = -1;

    static
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        sUICallback = new ItemTouchUIUtilImpl.Lollipop();
        return;
      }
      if (Build.VERSION.SDK_INT >= 11)
      {
        sUICallback = new ItemTouchUIUtilImpl.Honeycomb();
        return;
      }
    }

    public static int convertToRelativeDirection(int paramInt1, int paramInt2)
    {
      int i = paramInt1 & 0xC0C0C;
      if (i == 0)
        return paramInt1;
      int j = paramInt1 & (i ^ 0xFFFFFFFF);
      if (paramInt2 == 0)
        return j | i << 2;
      return j | 0xFFF3F3F3 & i << 1 | (0xC0C0C & i << 1) << 2;
    }

    public static ItemTouchUIUtil getDefaultUIUtil()
    {
      return sUICallback;
    }

    private int getMaxDragScroll(RecyclerView paramRecyclerView)
    {
      if (this.mCachedMaxScrollSpeed == -1)
        this.mCachedMaxScrollSpeed = paramRecyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
      return this.mCachedMaxScrollSpeed;
    }

    private boolean hasDragFlag(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return (0xFF0000 & getAbsoluteMovementFlags(paramRecyclerView, paramViewHolder)) != 0;
    }

    private boolean hasSwipeFlag(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return (0xFF00 & getAbsoluteMovementFlags(paramRecyclerView, paramViewHolder)) != 0;
    }

    public static int makeFlag(int paramInt1, int paramInt2)
    {
      return paramInt2 << paramInt1 * 8;
    }

    public static int makeMovementFlags(int paramInt1, int paramInt2)
    {
      return makeFlag(0, paramInt2 | paramInt1) | makeFlag(1, paramInt2) | makeFlag(2, paramInt1);
    }

    private void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, List<ItemTouchHelper.RecoverAnimation> paramList, int paramInt, float paramFloat1, float paramFloat2)
    {
      int i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        ItemTouchHelper.RecoverAnimation localRecoverAnimation = (ItemTouchHelper.RecoverAnimation)paramList.get(j);
        localRecoverAnimation.update();
        int m = paramCanvas.save();
        onChildDraw(paramCanvas, paramRecyclerView, localRecoverAnimation.mViewHolder, localRecoverAnimation.mX, localRecoverAnimation.mY, localRecoverAnimation.mActionState, false);
        paramCanvas.restoreToCount(m);
      }
      if (paramViewHolder != null)
      {
        int k = paramCanvas.save();
        onChildDraw(paramCanvas, paramRecyclerView, paramViewHolder, paramFloat1, paramFloat2, paramInt, true);
        paramCanvas.restoreToCount(k);
      }
    }

    private void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, List<ItemTouchHelper.RecoverAnimation> paramList, int paramInt, float paramFloat1, float paramFloat2)
    {
      int i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        ItemTouchHelper.RecoverAnimation localRecoverAnimation2 = (ItemTouchHelper.RecoverAnimation)paramList.get(j);
        int i1 = paramCanvas.save();
        onChildDrawOver(paramCanvas, paramRecyclerView, localRecoverAnimation2.mViewHolder, localRecoverAnimation2.mX, localRecoverAnimation2.mY, localRecoverAnimation2.mActionState, false);
        paramCanvas.restoreToCount(i1);
      }
      if (paramViewHolder != null)
      {
        int n = paramCanvas.save();
        onChildDrawOver(paramCanvas, paramRecyclerView, paramViewHolder, paramFloat1, paramFloat2, paramInt, true);
        paramCanvas.restoreToCount(n);
      }
      int k = 0;
      int m = i - 1;
      if (m >= 0)
      {
        ItemTouchHelper.RecoverAnimation localRecoverAnimation1 = (ItemTouchHelper.RecoverAnimation)paramList.get(m);
        if ((ItemTouchHelper.RecoverAnimation.access$1900(localRecoverAnimation1)) && (!localRecoverAnimation1.mIsPendingCleanup))
        {
          paramList.remove(m);
          localRecoverAnimation1.mViewHolder.setIsRecyclable(true);
        }
        while (true)
        {
          m--;
          break;
          if (!ItemTouchHelper.RecoverAnimation.access$1900(localRecoverAnimation1))
            k = 1;
        }
      }
      if (k != 0)
        paramRecyclerView.invalidate();
    }

    public boolean canDropOver(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2)
    {
      return true;
    }

    public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder paramViewHolder, List<RecyclerView.ViewHolder> paramList, int paramInt1, int paramInt2)
    {
      int i = paramInt1 + paramViewHolder.itemView.getWidth();
      int j = paramInt2 + paramViewHolder.itemView.getHeight();
      Object localObject = null;
      int k = -1;
      int m = paramInt1 - paramViewHolder.itemView.getLeft();
      int n = paramInt2 - paramViewHolder.itemView.getTop();
      int i1 = paramList.size();
      for (int i2 = 0; i2 < i1; i2++)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)paramList.get(i2);
        if (m > 0)
        {
          int i9 = localViewHolder.itemView.getRight() - i;
          if ((i9 < 0) && (localViewHolder.itemView.getRight() > paramViewHolder.itemView.getRight()))
          {
            int i10 = Math.abs(i9);
            if (i10 > k)
            {
              k = i10;
              localObject = localViewHolder;
            }
          }
        }
        if (m < 0)
        {
          int i7 = localViewHolder.itemView.getLeft() - paramInt1;
          if ((i7 > 0) && (localViewHolder.itemView.getLeft() < paramViewHolder.itemView.getLeft()))
          {
            int i8 = Math.abs(i7);
            if (i8 > k)
            {
              k = i8;
              localObject = localViewHolder;
            }
          }
        }
        if (n < 0)
        {
          int i5 = localViewHolder.itemView.getTop() - paramInt2;
          if ((i5 > 0) && (localViewHolder.itemView.getTop() < paramViewHolder.itemView.getTop()))
          {
            int i6 = Math.abs(i5);
            if (i6 > k)
            {
              k = i6;
              localObject = localViewHolder;
            }
          }
        }
        if (n > 0)
        {
          int i3 = localViewHolder.itemView.getBottom() - j;
          if ((i3 < 0) && (localViewHolder.itemView.getBottom() > paramViewHolder.itemView.getBottom()))
          {
            int i4 = Math.abs(i3);
            if (i4 > k)
            {
              k = i4;
              localObject = localViewHolder;
            }
          }
        }
      }
      return localObject;
    }

    public void clearView(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      sUICallback.clearView(paramViewHolder.itemView);
    }

    public int convertToAbsoluteDirection(int paramInt1, int paramInt2)
    {
      int i = paramInt1 & 0x303030;
      if (i == 0)
        return paramInt1;
      int j = paramInt1 & (i ^ 0xFFFFFFFF);
      if (paramInt2 == 0)
        return j | i >> 2;
      return j | 0xFFCFCFCF & i >> 1 | (0x303030 & i >> 1) >> 2;
    }

    final int getAbsoluteMovementFlags(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return convertToAbsoluteDirection(getMovementFlags(paramRecyclerView, paramViewHolder), ViewCompat.getLayoutDirection(paramRecyclerView));
    }

    public long getAnimationDuration(RecyclerView paramRecyclerView, int paramInt, float paramFloat1, float paramFloat2)
    {
      RecyclerView.ItemAnimator localItemAnimator = paramRecyclerView.getItemAnimator();
      if (localItemAnimator == null)
      {
        if (paramInt == 8)
          return 200L;
        return 250L;
      }
      if (paramInt == 8)
        return localItemAnimator.getMoveDuration();
      return localItemAnimator.getRemoveDuration();
    }

    public int getBoundingBoxMargin()
    {
      return 0;
    }

    public float getMoveThreshold(RecyclerView.ViewHolder paramViewHolder)
    {
      return 0.5F;
    }

    public abstract int getMovementFlags(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder);

    public float getSwipeThreshold(RecyclerView.ViewHolder paramViewHolder)
    {
      return 0.5F;
    }

    public int interpolateOutOfBoundsScroll(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
      int i = getMaxDragScroll(paramRecyclerView);
      int j = Math.abs(paramInt2);
      int k = (int)Math.signum(paramInt2);
      float f1 = Math.min(1.0F, 1.0F * j / paramInt1);
      int m = (int)(k * i * sDragViewScrollCapInterpolator.getInterpolation(f1));
      if (paramLong > 2000L);
      int n;
      for (float f2 = 1.0F; ; f2 = (float)paramLong / 2000.0F)
      {
        n = (int)(m * sDragScrollInterpolator.getInterpolation(f2));
        if (n != 0)
          break label109;
        if (paramInt2 <= 0)
          break;
        return 1;
      }
      return -1;
      label109: return n;
    }

    public boolean isItemViewSwipeEnabled()
    {
      return true;
    }

    public boolean isLongPressDragEnabled()
    {
      return true;
    }

    public void onChildDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
    {
      sUICallback.onDraw(paramCanvas, paramRecyclerView, paramViewHolder.itemView, paramFloat1, paramFloat2, paramInt, paramBoolean);
    }

    public void onChildDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
    {
      sUICallback.onDrawOver(paramCanvas, paramRecyclerView, paramViewHolder.itemView, paramFloat1, paramFloat2, paramInt, paramBoolean);
    }

    public abstract boolean onMove(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2);

    public void onMoved(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder1, int paramInt1, RecyclerView.ViewHolder paramViewHolder2, int paramInt2, int paramInt3, int paramInt4)
    {
      RecyclerView.LayoutManager localLayoutManager = paramRecyclerView.getLayoutManager();
      if ((localLayoutManager instanceof ItemTouchHelper.ViewDropHandler))
        ((ItemTouchHelper.ViewDropHandler)localLayoutManager).prepareForDrop(paramViewHolder1.itemView, paramViewHolder2.itemView, paramInt3, paramInt4);
      do
      {
        do
        {
          return;
          if (localLayoutManager.canScrollHorizontally())
          {
            if (localLayoutManager.getDecoratedLeft(paramViewHolder2.itemView) <= paramRecyclerView.getPaddingLeft())
              paramRecyclerView.scrollToPosition(paramInt2);
            if (localLayoutManager.getDecoratedRight(paramViewHolder2.itemView) >= paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight())
              paramRecyclerView.scrollToPosition(paramInt2);
          }
        }
        while (!localLayoutManager.canScrollVertically());
        if (localLayoutManager.getDecoratedTop(paramViewHolder2.itemView) <= paramRecyclerView.getPaddingTop())
          paramRecyclerView.scrollToPosition(paramInt2);
      }
      while (localLayoutManager.getDecoratedBottom(paramViewHolder2.itemView) < paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom());
      paramRecyclerView.scrollToPosition(paramInt2);
    }

    public void onSelectedChanged(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
      if (paramViewHolder != null)
        sUICallback.onSelected(paramViewHolder.itemView);
    }

    public abstract void onSwiped(RecyclerView.ViewHolder paramViewHolder, int paramInt);
  }

  private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener
  {
    private ItemTouchHelperGestureListener()
    {
    }

    public boolean onDown(MotionEvent paramMotionEvent)
    {
      return true;
    }

    public void onLongPress(MotionEvent paramMotionEvent)
    {
      View localView = ItemTouchHelper.this.findChildView(paramMotionEvent);
      RecyclerView.ViewHolder localViewHolder;
      if (localView != null)
      {
        localViewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(localView);
        if ((localViewHolder != null) && (ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, localViewHolder)))
          break label51;
      }
      label51: 
      do
      {
        do
          return;
        while (MotionEventCompat.getPointerId(paramMotionEvent, 0) != ItemTouchHelper.this.mActivePointerId);
        int i = MotionEventCompat.findPointerIndex(paramMotionEvent, ItemTouchHelper.this.mActivePointerId);
        float f1 = MotionEventCompat.getX(paramMotionEvent, i);
        float f2 = MotionEventCompat.getY(paramMotionEvent, i);
        ItemTouchHelper.this.mInitialTouchX = f1;
        ItemTouchHelper.this.mInitialTouchY = f2;
        ItemTouchHelper localItemTouchHelper = ItemTouchHelper.this;
        ItemTouchHelper.this.mDy = 0.0F;
        localItemTouchHelper.mDx = 0.0F;
      }
      while (!ItemTouchHelper.this.mCallback.isLongPressDragEnabled());
      ItemTouchHelper.this.select(localViewHolder, 2);
    }
  }

  private class RecoverAnimation
    implements AnimatorListenerCompat
  {
    final int mActionState;
    private final int mAnimationType;
    private boolean mEnded = false;
    private float mFraction;
    public boolean mIsPendingCleanup;
    boolean mOverridden = false;
    final float mStartDx;
    final float mStartDy;
    final float mTargetX;
    final float mTargetY;
    private final ValueAnimatorCompat mValueAnimator;
    final RecyclerView.ViewHolder mViewHolder;
    float mX;
    float mY;

    public RecoverAnimation(RecyclerView.ViewHolder paramInt1, int paramInt2, int paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float arg8)
    {
      this.mActionState = paramFloat1;
      this.mAnimationType = paramInt2;
      this.mViewHolder = paramInt1;
      this.mStartDx = paramFloat2;
      this.mStartDy = paramFloat3;
      this.mTargetX = paramFloat4;
      Object localObject;
      this.mTargetY = localObject;
      this.mValueAnimator = AnimatorCompatHelper.emptyValueAnimator();
      this.mValueAnimator.addUpdateListener(new AnimatorUpdateListenerCompat()
      {
        public void onAnimationUpdate(ValueAnimatorCompat paramAnonymousValueAnimatorCompat)
        {
          ItemTouchHelper.RecoverAnimation.this.setFraction(paramAnonymousValueAnimatorCompat.getAnimatedFraction());
        }
      });
      this.mValueAnimator.setTarget(paramInt1.itemView);
      this.mValueAnimator.addListener(this);
      setFraction(0.0F);
    }

    public void cancel()
    {
      this.mValueAnimator.cancel();
    }

    public void onAnimationCancel(ValueAnimatorCompat paramValueAnimatorCompat)
    {
      setFraction(1.0F);
    }

    public void onAnimationEnd(ValueAnimatorCompat paramValueAnimatorCompat)
    {
      this.mEnded = true;
    }

    public void onAnimationRepeat(ValueAnimatorCompat paramValueAnimatorCompat)
    {
    }

    public void onAnimationStart(ValueAnimatorCompat paramValueAnimatorCompat)
    {
    }

    public void setDuration(long paramLong)
    {
      this.mValueAnimator.setDuration(paramLong);
    }

    public void setFraction(float paramFloat)
    {
      this.mFraction = paramFloat;
    }

    public void start()
    {
      this.mViewHolder.setIsRecyclable(false);
      this.mValueAnimator.start();
    }

    public void update()
    {
      if (this.mStartDx == this.mTargetX);
      for (this.mX = ViewCompat.getTranslationX(this.mViewHolder.itemView); this.mStartDy == this.mTargetY; this.mX = (this.mStartDx + this.mFraction * (this.mTargetX - this.mStartDx)))
      {
        this.mY = ViewCompat.getTranslationY(this.mViewHolder.itemView);
        return;
      }
      this.mY = (this.mStartDy + this.mFraction * (this.mTargetY - this.mStartDy));
    }
  }

  public static abstract class SimpleCallback extends ItemTouchHelper.Callback
  {
    private int mDefaultDragDirs;
    private int mDefaultSwipeDirs;

    public SimpleCallback(int paramInt1, int paramInt2)
    {
      this.mDefaultSwipeDirs = paramInt2;
      this.mDefaultDragDirs = paramInt1;
    }

    public int getDragDirs(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return this.mDefaultDragDirs;
    }

    public int getMovementFlags(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return makeMovementFlags(getDragDirs(paramRecyclerView, paramViewHolder), getSwipeDirs(paramRecyclerView, paramViewHolder));
    }

    public int getSwipeDirs(RecyclerView paramRecyclerView, RecyclerView.ViewHolder paramViewHolder)
    {
      return this.mDefaultSwipeDirs;
    }

    public void setDefaultDragDirs(int paramInt)
    {
      this.mDefaultDragDirs = paramInt;
    }

    public void setDefaultSwipeDirs(int paramInt)
    {
      this.mDefaultSwipeDirs = paramInt;
    }
  }

  public static abstract interface ViewDropHandler
  {
    public abstract void prepareForDrop(View paramView1, View paramView2, int paramInt1, int paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.helper.ItemTouchHelper
 * JD-Core Version:    0.6.2
 */