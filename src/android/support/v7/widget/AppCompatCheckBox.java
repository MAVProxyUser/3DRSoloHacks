package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.v7.appcompat.R.attr;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class AppCompatCheckBox extends CheckBox
{
  private static final int[] TINT_ATTRS = { 16843015 };
  private Drawable mButtonDrawable;
  private TintManager mTintManager;

  public AppCompatCheckBox(Context paramContext)
  {
    this(paramContext, null);
  }

  public AppCompatCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.checkboxStyle);
  }

  public AppCompatCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (TintManager.SHOULD_BE_USED)
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
      setButtonDrawable(localTintTypedArray.getDrawable(0));
      localTintTypedArray.recycle();
      this.mTintManager = localTintTypedArray.getTintManager();
    }
  }

  public int getCompoundPaddingLeft()
  {
    int i = super.getCompoundPaddingLeft();
    if ((Build.VERSION.SDK_INT < 17) && (this.mButtonDrawable != null))
      i += this.mButtonDrawable.getIntrinsicWidth();
    return i;
  }

  public void setButtonDrawable(@DrawableRes int paramInt)
  {
    if (this.mTintManager != null)
    {
      setButtonDrawable(this.mTintManager.getDrawable(paramInt));
      return;
    }
    super.setButtonDrawable(paramInt);
  }

  public void setButtonDrawable(Drawable paramDrawable)
  {
    super.setButtonDrawable(paramDrawable);
    this.mButtonDrawable = paramDrawable;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatCheckBox
 * JD-Core Version:    0.6.2
 */