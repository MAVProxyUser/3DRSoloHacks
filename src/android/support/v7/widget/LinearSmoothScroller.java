package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public abstract class LinearSmoothScroller extends RecyclerView.SmoothScroller
{
  private static final boolean DEBUG = false;
  private static final float MILLISECONDS_PER_INCH = 25.0F;
  public static final int SNAP_TO_ANY = 0;
  public static final int SNAP_TO_END = 1;
  public static final int SNAP_TO_START = -1;
  private static final String TAG = "LinearSmoothScroller";
  private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2F;
  private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
  private final float MILLISECONDS_PER_PX = calculateSpeedPerPixel(paramContext.getResources().getDisplayMetrics());
  protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
  protected int mInterimTargetDx = 0;
  protected int mInterimTargetDy = 0;
  protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
  protected PointF mTargetVector;

  public LinearSmoothScroller(Context paramContext)
  {
  }

  private int clampApplyScroll(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - paramInt2;
    if (paramInt1 * i <= 0)
      i = 0;
    return i;
  }

  public int calculateDtToFit(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i;
    switch (paramInt5)
    {
    default:
      throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
    case -1:
      i = paramInt3 - paramInt1;
    case 1:
    case 0:
    }
    do
    {
      return i;
      return paramInt4 - paramInt2;
      i = paramInt3 - paramInt1;
    }
    while (i > 0);
    int j = paramInt4 - paramInt2;
    if (j < 0)
      return j;
    return 0;
  }

  public int calculateDxToMakeVisible(View paramView, int paramInt)
  {
    RecyclerView.LayoutManager localLayoutManager = getLayoutManager();
    if (!localLayoutManager.canScrollHorizontally())
      return 0;
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    return calculateDtToFit(localLayoutManager.getDecoratedLeft(paramView) - localLayoutParams.leftMargin, localLayoutManager.getDecoratedRight(paramView) + localLayoutParams.rightMargin, localLayoutManager.getPaddingLeft(), localLayoutManager.getWidth() - localLayoutManager.getPaddingRight(), paramInt);
  }

  public int calculateDyToMakeVisible(View paramView, int paramInt)
  {
    RecyclerView.LayoutManager localLayoutManager = getLayoutManager();
    if (!localLayoutManager.canScrollVertically())
      return 0;
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    return calculateDtToFit(localLayoutManager.getDecoratedTop(paramView) - localLayoutParams.topMargin, localLayoutManager.getDecoratedBottom(paramView) + localLayoutParams.bottomMargin, localLayoutManager.getPaddingTop(), localLayoutManager.getHeight() - localLayoutManager.getPaddingBottom(), paramInt);
  }

  protected float calculateSpeedPerPixel(DisplayMetrics paramDisplayMetrics)
  {
    return 25.0F / paramDisplayMetrics.densityDpi;
  }

  protected int calculateTimeForDeceleration(int paramInt)
  {
    return (int)Math.ceil(calculateTimeForScrolling(paramInt) / 0.3356D);
  }

  protected int calculateTimeForScrolling(int paramInt)
  {
    return (int)Math.ceil(Math.abs(paramInt) * this.MILLISECONDS_PER_PX);
  }

  public abstract PointF computeScrollVectorForPosition(int paramInt);

  protected int getHorizontalSnapPreference()
  {
    if ((this.mTargetVector == null) || (this.mTargetVector.x == 0.0F))
      return 0;
    if (this.mTargetVector.x > 0.0F)
      return 1;
    return -1;
  }

  protected int getVerticalSnapPreference()
  {
    if ((this.mTargetVector == null) || (this.mTargetVector.y == 0.0F))
      return 0;
    if (this.mTargetVector.y > 0.0F)
      return 1;
    return -1;
  }

  protected void onSeekTargetStep(int paramInt1, int paramInt2, RecyclerView.State paramState, RecyclerView.SmoothScroller.Action paramAction)
  {
    if (getChildCount() == 0)
      stop();
    do
    {
      return;
      this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, paramInt1);
      this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, paramInt2);
    }
    while ((this.mInterimTargetDx != 0) || (this.mInterimTargetDy != 0));
    updateActionForInterimTarget(paramAction);
  }

  protected void onStart()
  {
  }

  protected void onStop()
  {
    this.mInterimTargetDy = 0;
    this.mInterimTargetDx = 0;
    this.mTargetVector = null;
  }

  protected void onTargetFound(View paramView, RecyclerView.State paramState, RecyclerView.SmoothScroller.Action paramAction)
  {
    int i = calculateDxToMakeVisible(paramView, getHorizontalSnapPreference());
    int j = calculateDyToMakeVisible(paramView, getVerticalSnapPreference());
    int k = calculateTimeForDeceleration((int)Math.sqrt(i * i + j * j));
    if (k > 0)
      paramAction.update(-i, -j, k, this.mDecelerateInterpolator);
  }

  protected void updateActionForInterimTarget(RecyclerView.SmoothScroller.Action paramAction)
  {
    PointF localPointF = computeScrollVectorForPosition(getTargetPosition());
    if ((localPointF == null) || ((localPointF.x == 0.0F) && (localPointF.y == 0.0F)))
    {
      Log.e("LinearSmoothScroller", "To support smooth scrolling, you should override \nLayoutManager#computeScrollVectorForPosition.\nFalling back to instant scroll");
      int i = getTargetPosition();
      stop();
      instantScrollToPosition(i);
      return;
    }
    normalize(localPointF);
    this.mTargetVector = localPointF;
    this.mInterimTargetDx = ((int)(10000.0F * localPointF.x));
    this.mInterimTargetDy = ((int)(10000.0F * localPointF.y));
    int j = calculateTimeForScrolling(10000);
    paramAction.update((int)(1.2F * this.mInterimTargetDx), (int)(1.2F * this.mInterimTargetDy), (int)(1.2F * j), this.mLinearInterpolator);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.LinearSmoothScroller
 * JD-Core Version:    0.6.2
 */