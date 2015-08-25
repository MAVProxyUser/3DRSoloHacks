package android.support.v7.internal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.appcompat.R.styleable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AppCompatPopupWindow extends PopupWindow
{
  private static final String TAG = "AppCompatPopupWindow";
  private final boolean mOverlapAnchor;

  public AppCompatPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.PopupWindow, paramInt, 0);
    this.mOverlapAnchor = localTintTypedArray.getBoolean(R.styleable.PopupWindow_overlapAnchor, false);
    setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.PopupWindow_android_popupBackground));
    localTintTypedArray.recycle();
    if (Build.VERSION.SDK_INT < 14)
      wrapOnScrollChangedListener(this);
  }

  private static void wrapOnScrollChangedListener(final PopupWindow paramPopupWindow)
  {
    try
    {
      Field localField1 = PopupWindow.class.getDeclaredField("mAnchor");
      localField1.setAccessible(true);
      Field localField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
      localField2.setAccessible(true);
      localField2.set(paramPopupWindow, new ViewTreeObserver.OnScrollChangedListener()
      {
        public void onScrollChanged()
        {
          try
          {
            WeakReference localWeakReference = (WeakReference)this.val$fieldAnchor.get(paramPopupWindow);
            if (localWeakReference != null)
            {
              if (localWeakReference.get() == null)
                return;
              this.val$originalListener.onScrollChanged();
              return;
            }
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
          }
        }
      });
      return;
    }
    catch (Exception localException)
    {
      Log.d("AppCompatPopupWindow", "Exception while installing workaround OnScrollChangedListener", localException);
    }
  }

  public void showAsDropDown(View paramView, int paramInt1, int paramInt2)
  {
    if ((Build.VERSION.SDK_INT < 21) && (this.mOverlapAnchor))
      paramInt2 -= paramView.getHeight();
    super.showAsDropDown(paramView, paramInt1, paramInt2);
  }

  @TargetApi(19)
  public void showAsDropDown(View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((Build.VERSION.SDK_INT < 21) && (this.mOverlapAnchor))
      paramInt2 -= paramView.getHeight();
    super.showAsDropDown(paramView, paramInt1, paramInt2, paramInt3);
  }

  public void update(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((Build.VERSION.SDK_INT < 21) && (this.mOverlapAnchor))
      paramInt2 -= paramView.getHeight();
    super.update(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.AppCompatPopupWindow
 * JD-Core Version:    0.6.2
 */