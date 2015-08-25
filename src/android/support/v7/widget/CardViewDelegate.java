package android.support.v7.widget;

import android.graphics.drawable.Drawable;

abstract interface CardViewDelegate
{
  public abstract Drawable getBackground();

  public abstract boolean getPreventCornerOverlap();

  public abstract float getRadius();

  public abstract boolean getUseCompatPadding();

  public abstract void setBackgroundDrawable(Drawable paramDrawable);

  public abstract void setShadowPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.CardViewDelegate
 * JD-Core Version:    0.6.2
 */