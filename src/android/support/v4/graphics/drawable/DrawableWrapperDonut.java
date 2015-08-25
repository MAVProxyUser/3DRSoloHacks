package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;

class DrawableWrapperDonut extends Drawable
  implements Drawable.Callback, DrawableWrapper
{
  static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
  private boolean mColorFilterSet;
  private int mCurrentColor;
  private PorterDuff.Mode mCurrentMode;
  Drawable mDrawable;
  private ColorStateList mTintList;
  private PorterDuff.Mode mTintMode = DEFAULT_MODE;

  DrawableWrapperDonut(Drawable paramDrawable)
  {
    setWrappedDrawable(paramDrawable);
  }

  private boolean updateTint(int[] paramArrayOfInt)
  {
    if ((this.mTintList != null) && (this.mTintMode != null))
    {
      int i = this.mTintList.getColorForState(paramArrayOfInt, this.mTintList.getDefaultColor());
      PorterDuff.Mode localMode = this.mTintMode;
      if ((!this.mColorFilterSet) || (i != this.mCurrentColor) || (localMode != this.mCurrentMode))
      {
        setColorFilter(i, localMode);
        this.mCurrentColor = i;
        this.mCurrentMode = localMode;
        this.mColorFilterSet = true;
        return true;
      }
    }
    return false;
  }

  public void draw(Canvas paramCanvas)
  {
    this.mDrawable.draw(paramCanvas);
  }

  public int getChangingConfigurations()
  {
    return this.mDrawable.getChangingConfigurations();
  }

  public Drawable getCurrent()
  {
    return this.mDrawable.getCurrent();
  }

  public int getIntrinsicHeight()
  {
    return this.mDrawable.getIntrinsicHeight();
  }

  public int getIntrinsicWidth()
  {
    return this.mDrawable.getIntrinsicWidth();
  }

  public int getMinimumHeight()
  {
    return this.mDrawable.getMinimumHeight();
  }

  public int getMinimumWidth()
  {
    return this.mDrawable.getMinimumWidth();
  }

  public int getOpacity()
  {
    return this.mDrawable.getOpacity();
  }

  public boolean getPadding(Rect paramRect)
  {
    return this.mDrawable.getPadding(paramRect);
  }

  public int[] getState()
  {
    return this.mDrawable.getState();
  }

  public Region getTransparentRegion()
  {
    return this.mDrawable.getTransparentRegion();
  }

  public Drawable getWrappedDrawable()
  {
    return this.mDrawable;
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    invalidateSelf();
  }

  public boolean isStateful()
  {
    return ((this.mTintList != null) && (this.mTintList.isStateful())) || (this.mDrawable.isStateful());
  }

  public Drawable mutate()
  {
    Drawable localDrawable1 = this.mDrawable;
    Drawable localDrawable2 = localDrawable1.mutate();
    if (localDrawable2 != localDrawable1)
      setWrappedDrawable(localDrawable2);
    return this;
  }

  protected void onBoundsChange(Rect paramRect)
  {
    this.mDrawable.setBounds(paramRect);
  }

  protected boolean onLevelChange(int paramInt)
  {
    return this.mDrawable.setLevel(paramInt);
  }

  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    scheduleSelf(paramRunnable, paramLong);
  }

  public void setAlpha(int paramInt)
  {
    this.mDrawable.setAlpha(paramInt);
  }

  public void setChangingConfigurations(int paramInt)
  {
    this.mDrawable.setChangingConfigurations(paramInt);
  }

  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mDrawable.setColorFilter(paramColorFilter);
  }

  public void setDither(boolean paramBoolean)
  {
    this.mDrawable.setDither(paramBoolean);
  }

  public void setFilterBitmap(boolean paramBoolean)
  {
    this.mDrawable.setFilterBitmap(paramBoolean);
  }

  public boolean setState(int[] paramArrayOfInt)
  {
    boolean bool = this.mDrawable.setState(paramArrayOfInt);
    return (updateTint(paramArrayOfInt)) || (bool);
  }

  public void setTint(int paramInt)
  {
    setTintList(ColorStateList.valueOf(paramInt));
  }

  public void setTintList(ColorStateList paramColorStateList)
  {
    this.mTintList = paramColorStateList;
    updateTint(getState());
  }

  public void setTintMode(PorterDuff.Mode paramMode)
  {
    this.mTintMode = paramMode;
    updateTint(getState());
  }

  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    return (super.setVisible(paramBoolean1, paramBoolean2)) || (this.mDrawable.setVisible(paramBoolean1, paramBoolean2));
  }

  public void setWrappedDrawable(Drawable paramDrawable)
  {
    if (this.mDrawable != null)
      this.mDrawable.setCallback(null);
    this.mDrawable = paramDrawable;
    if (paramDrawable != null)
      paramDrawable.setCallback(this);
    invalidateSelf();
  }

  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    unscheduleSelf(paramRunnable);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.graphics.drawable.DrawableWrapperDonut
 * JD-Core Version:    0.6.2
 */