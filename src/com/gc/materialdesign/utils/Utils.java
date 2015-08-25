package com.gc.materialdesign.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

public class Utils
{
  public static int dpToPx(float paramFloat, Resources paramResources)
  {
    return (int)TypedValue.applyDimension(1, paramFloat, paramResources.getDisplayMetrics());
  }

  public static int getRelativeLeft(View paramView)
  {
    if (paramView.getId() == 16908290)
      return paramView.getLeft();
    return paramView.getLeft() + getRelativeLeft((View)paramView.getParent());
  }

  public static int getRelativeTop(View paramView)
  {
    if (paramView.getId() == 16908290)
      return paramView.getTop();
    return paramView.getTop() + getRelativeTop((View)paramView.getParent());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.utils.Utils
 * JD-Core Version:    0.6.2
 */