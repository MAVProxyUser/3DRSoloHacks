package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R.attr;
import android.support.v7.internal.widget.TintInfo;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import java.lang.reflect.Field;

public class AppCompatSpinner extends Spinner
  implements TintableBackgroundView
{
  private static final int[] TINT_ATTRS = { 16842964, 16843126 };
  private TintInfo mBackgroundTint;
  private TintInfo mInternalBackgroundTint;
  private TintManager mTintManager;

  public AppCompatSpinner(Context paramContext)
  {
    this(paramContext, null);
  }

  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.spinnerStyle);
  }

  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray;
    Drawable localDrawable;
    if (TintManager.SHOULD_BE_USED)
    {
      localTintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
      if (localTintTypedArray.hasValue(0))
      {
        ColorStateList localColorStateList = localTintTypedArray.getTintManager().getTintList(localTintTypedArray.getResourceId(0, -1));
        if (localColorStateList != null)
          setInternalBackgroundTint(localColorStateList);
      }
      if (localTintTypedArray.hasValue(1))
      {
        localDrawable = localTintTypedArray.getDrawable(1);
        if (Build.VERSION.SDK_INT < 16)
          break label111;
        setPopupBackgroundDrawable(localDrawable);
      }
    }
    while (true)
    {
      this.mTintManager = localTintTypedArray.getTintManager();
      localTintTypedArray.recycle();
      return;
      label111: if (Build.VERSION.SDK_INT >= 11)
        setPopupBackgroundDrawableV11(this, localDrawable);
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

  @TargetApi(11)
  private static void setPopupBackgroundDrawableV11(Spinner paramSpinner, Drawable paramDrawable)
  {
    try
    {
      Field localField = Spinner.class.getDeclaredField("mPopup");
      localField.setAccessible(true);
      Object localObject = localField.get(paramSpinner);
      if ((localObject instanceof ListPopupWindow))
        ((ListPopupWindow)localObject).setBackgroundDrawable(paramDrawable);
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localNoSuchFieldException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
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
 * Qualified Name:     android.support.v7.widget.AppCompatSpinner
 * JD-Core Version:    0.6.2
 */