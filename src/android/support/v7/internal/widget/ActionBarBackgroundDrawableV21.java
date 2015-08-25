package android.support.v7.internal.widget;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

class ActionBarBackgroundDrawableV21 extends ActionBarBackgroundDrawable
{
  public ActionBarBackgroundDrawableV21(ActionBarContainer paramActionBarContainer)
  {
    super(paramActionBarContainer);
  }

  public void getOutline(@NonNull Outline paramOutline)
  {
    if (this.mContainer.mIsSplit)
      if (this.mContainer.mSplitBackground != null)
        this.mContainer.mSplitBackground.getOutline(paramOutline);
    while (this.mContainer.mBackground == null)
      return;
    this.mContainer.mBackground.getOutline(paramOutline);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ActionBarBackgroundDrawableV21
 * JD-Core Version:    0.6.2
 */