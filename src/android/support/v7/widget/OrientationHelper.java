package android.support.v7.widget;

import android.view.View;

public abstract class OrientationHelper
{
  public static final int HORIZONTAL = 0;
  private static final int INVALID_SIZE = -2147483648;
  public static final int VERTICAL = 1;
  private int mLastTotalSpace = -2147483648;
  protected final RecyclerView.LayoutManager mLayoutManager;

  private OrientationHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    this.mLayoutManager = paramLayoutManager;
  }

  public static OrientationHelper createHorizontalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    // Byte code:
    //   0: new 30	android/support/v7/widget/OrientationHelper$1
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 31	android/support/v7/widget/OrientationHelper$1:<init>	(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V
    //   8: areturn
  }

  public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager paramLayoutManager, int paramInt)
  {
    switch (paramInt)
    {
    default:
      throw new IllegalArgumentException("invalid orientation");
    case 0:
      return createHorizontalHelper(paramLayoutManager);
    case 1:
    }
    return createVerticalHelper(paramLayoutManager);
  }

  public static OrientationHelper createVerticalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    // Byte code:
    //   0: new 47	android/support/v7/widget/OrientationHelper$2
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 48	android/support/v7/widget/OrientationHelper$2:<init>	(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V
    //   8: areturn
  }

  public abstract int getDecoratedEnd(View paramView);

  public abstract int getDecoratedMeasurement(View paramView);

  public abstract int getDecoratedMeasurementInOther(View paramView);

  public abstract int getDecoratedStart(View paramView);

  public abstract int getEnd();

  public abstract int getEndAfterPadding();

  public abstract int getEndPadding();

  public abstract int getStartAfterPadding();

  public abstract int getTotalSpace();

  public int getTotalSpaceChange()
  {
    if (-2147483648 == this.mLastTotalSpace)
      return 0;
    return getTotalSpace() - this.mLastTotalSpace;
  }

  public abstract void offsetChild(View paramView, int paramInt);

  public abstract void offsetChildren(int paramInt);

  public void onLayoutComplete()
  {
    this.mLastTotalSpace = getTotalSpace();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.OrientationHelper
 * JD-Core Version:    0.6.2
 */