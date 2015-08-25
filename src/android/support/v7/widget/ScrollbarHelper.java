package android.support.v7.widget;

import android.view.View;

class ScrollbarHelper
{
  static int computeScrollExtent(RecyclerView.State paramState, OrientationHelper paramOrientationHelper, View paramView1, View paramView2, RecyclerView.LayoutManager paramLayoutManager, boolean paramBoolean)
  {
    if ((paramLayoutManager.getChildCount() == 0) || (paramState.getItemCount() == 0) || (paramView1 == null) || (paramView2 == null))
      return 0;
    if (!paramBoolean)
      return 1 + Math.abs(paramLayoutManager.getPosition(paramView1) - paramLayoutManager.getPosition(paramView2));
    int i = paramOrientationHelper.getDecoratedEnd(paramView2) - paramOrientationHelper.getDecoratedStart(paramView1);
    return Math.min(paramOrientationHelper.getTotalSpace(), i);
  }

  static int computeScrollOffset(RecyclerView.State paramState, OrientationHelper paramOrientationHelper, View paramView1, View paramView2, RecyclerView.LayoutManager paramLayoutManager, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramLayoutManager.getChildCount();
    int j = 0;
    if (i != 0)
    {
      int k = paramState.getItemCount();
      j = 0;
      if (k != 0)
      {
        j = 0;
        if (paramView1 != null)
        {
          j = 0;
          if (paramView2 != null)
            break label46;
        }
      }
    }
    while (true)
    {
      return j;
      label46: int m = Math.min(paramLayoutManager.getPosition(paramView1), paramLayoutManager.getPosition(paramView2));
      int n = Math.max(paramLayoutManager.getPosition(paramView1), paramLayoutManager.getPosition(paramView2));
      if (paramBoolean2);
      for (j = Math.max(0, -1 + (paramState.getItemCount() - n)); paramBoolean1; j = Math.max(0, m))
      {
        int i1 = Math.abs(paramOrientationHelper.getDecoratedEnd(paramView2) - paramOrientationHelper.getDecoratedStart(paramView1));
        int i2 = 1 + Math.abs(paramLayoutManager.getPosition(paramView1) - paramLayoutManager.getPosition(paramView2));
        return Math.round(i1 / i2 * j + (paramOrientationHelper.getStartAfterPadding() - paramOrientationHelper.getDecoratedStart(paramView1)));
      }
    }
  }

  static int computeScrollRange(RecyclerView.State paramState, OrientationHelper paramOrientationHelper, View paramView1, View paramView2, RecyclerView.LayoutManager paramLayoutManager, boolean paramBoolean)
  {
    if ((paramLayoutManager.getChildCount() == 0) || (paramState.getItemCount() == 0) || (paramView1 == null) || (paramView2 == null))
      return 0;
    if (!paramBoolean)
      return paramState.getItemCount();
    int i = paramOrientationHelper.getDecoratedEnd(paramView2) - paramOrientationHelper.getDecoratedStart(paramView1);
    int j = 1 + Math.abs(paramLayoutManager.getPosition(paramView1) - paramLayoutManager.getPosition(paramView2));
    return (int)(i / j * paramState.getItemCount());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ScrollbarHelper
 * JD-Core Version:    0.6.2
 */