package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R.attr;
import android.support.v7.internal.widget.TintContextWrapper;
import android.support.v7.internal.widget.TintInfo;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

public class AppCompatEditText extends EditText
  implements TintableBackgroundView
{
  private static final int[] TINT_ATTRS = { 16842964 };
  private TintInfo mBackgroundTint;
  private TintInfo mInternalBackgroundTint;
  private TintManager mTintManager;

  public AppCompatEditText(Context paramContext)
  {
    this(paramContext, null);
  }

  public AppCompatEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.editTextStyle);
  }

  public AppCompatEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    if (TintManager.SHOULD_BE_USED)
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
      if (localTintTypedArray.hasValue(0))
      {
        ColorStateList localColorStateList = localTintTypedArray.getTintManager().getTintList(localTintTypedArray.getResourceId(0, -1));
        if (localColorStateList != null)
          setInternalBackgroundTint(localColorStateList);
      }
      this.mTintManager = localTintTypedArray.getTintManager();
      localTintTypedArray.recycle();
    }
  }

  private void applySupportBackgroundTint()
  {
    if (getBackground() != null)
    {
      if (this.mBackgroundTint == null)
        break label23;
      TintManager.tintViewBackground(this, this.mBackgroundTint);
    }
    label23: 
    while (this.mInternalBackgroundTint == null)
      return;
    TintManager.tintViewBackground(this, this.mInternalBackgroundTint);
  }

  private void setInternalBackgroundTint(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null)
    {
      if (this.mInternalBackgroundTint == null)
        this.mInternalBackgroundTint = new TintInfo();
      this.mInternalBackgroundTint.mTintList = paramColorStateList;
      this.mInternalBackgroundTint.mHasTintList = true;
    }
    while (true)
    {
      applySupportBackgroundTint();
      return;
      this.mInternalBackgroundTint = null;
    }
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    applySupportBackgroundTint();
  }

  @Nullable
  public ColorStateList getSupportBackgroundTintList()
  {
    if (this.mBackgroundTint != null)
      return this.mBackgroundTint.mTintList;
    return null;
  }

  @Nullable
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    if (this.mBackgroundTint != null)
      return this.mBackgroundTint.mTintMode;
    return null;
  }

  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    setInternalBackgroundTint(null);
  }

  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    if (this.mTintManager != null);
    for (ColorStateList localColorStateList = this.mTintManager.getTintList(paramInt); ; localColorStateList = null)
    {
      setInternalBackgroundTint(localColorStateList);
      return;
    }
  }

  public void setSupportBackgroundTintList(@Nullable ColorStateList paramColorStateList)
  {
    if (this.mBackgroundTint == null)
      this.mBackgroundTint = new TintInfo();
    this.mBackgroundTint.mTintList = paramColorStateList;
    this.mBackgroundTint.mHasTintList = true;
    applySupportBackgroundTint();
  }

  public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode paramMode)
  {
    if (this.mBackgroundTint == null)
      this.mBackgroundTint = new TintInfo();
    this.mBackgroundTint.mTintMode = paramMode;
    this.mBackgroundTint.mHasTintMode = true;
    applySupportBackgroundTint();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatEditText
 * JD-Core Version:    0.6.2
 */