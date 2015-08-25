package android.support.v7.internal.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils
{
  private static final String TAG = "ViewUtils";
  private static Method sComputeFitSystemWindowsMethod;

  static
  {
    if (Build.VERSION.SDK_INT >= 18);
    try
    {
      sComputeFitSystemWindowsMethod = View.class.getDeclaredMethod("computeFitSystemWindows", new Class[] { Rect.class, Rect.class });
      if (!sComputeFitSystemWindowsMethod.isAccessible())
        sComputeFitSystemWindowsMethod.setAccessible(true);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.d("ViewUtils", "Could not find method computeFitSystemWindows. Oh well.");
    }
  }

  public static int combineMeasuredStates(int paramInt1, int paramInt2)
  {
    return paramInt1 | paramInt2;
  }

  public static void computeFitSystemWindows(View paramView, Rect paramRect1, Rect paramRect2)
  {
    if (sComputeFitSystemWindowsMethod != null);
    try
    {
      sComputeFitSystemWindowsMethod.invoke(paramView, new Object[] { paramRect1, paramRect2 });
      return;
    }
    catch (Exception localException)
    {
      Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", localException);
    }
  }

  public static boolean isLayoutRtl(View paramView)
  {
    return ViewCompat.getLayoutDirection(paramView) == 1;
  }

  public static void makeOptionalFitsSystemWindows(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16);
    try
    {
      Method localMethod = paramView.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
      if (!localMethod.isAccessible())
        localMethod.setAccessible(true);
      localMethod.invoke(paramView, new Object[0]);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", localInvocationTargetException);
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", localIllegalAccessException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ViewUtils
 * JD-Core Version:    0.6.2
 */