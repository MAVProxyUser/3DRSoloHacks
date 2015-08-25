package android.support.v7.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.view.Window;

class AppCompatDelegateImplV11 extends AppCompatDelegateImplV7
{
  AppCompatDelegateImplV11(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }

  View callActivityOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    View localView = super.callActivityOnCreateView(paramView, paramString, paramContext, paramAttributeSet);
    if (localView != null)
      return localView;
    if ((this.mOriginalWindowCallback instanceof LayoutInflater.Factory2))
      return ((LayoutInflater.Factory2)this.mOriginalWindowCallback).onCreateView(paramView, paramString, paramContext, paramAttributeSet);
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatDelegateImplV11
 * JD-Core Version:    0.6.2
 */