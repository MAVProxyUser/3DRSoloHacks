package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.text.AllCapsTransformationMethod;
import android.support.v7.internal.widget.ThemeUtils;
import android.support.v7.internal.widget.TintInfo;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

public class AppCompatButton extends Button
  implements TintableBackgroundView
{
  private static final int[] TINT_ATTRS = { 16842964 };
  private TintInfo mBackgroundTint;
  private TintInfo mInternalBackgroundTint;
  private TintManager mTintManager;

  public AppCompatButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public AppCompatButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.buttonStyle);
  }

  public AppCompatButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (TintManager.SHOULD_BE_USED)
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
      if (localTintTypedArray.hasValue(0))
      {
        ColorStateList localColorStateList2 = localTintTypedArray.getTintManager().getTintList(localTintTypedArray.getResourceId(0, -1));
        if (localColorStateList2 != null)
          setInternalBackgroundTint(localColorStateList2);
      }
      this.mTintManager = localTintTypedArray.getTintManager();
      localTintTypedArray.recycle();
    }
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    int i = localTypedArray1.getResourceId(R.styleable.AppCompatTextView_android_textAppearance, -1);
    localTypedArray1.recycle();
    if (i != -1)
    {
      TypedArray localTypedArray3 = paramContext.obtainStyledAttributes(i, R.styleable.TextAppearance);
      if (localTypedArray3.hasValue(R.styleable.TextAppearance_textAllCaps))
        setAllCaps(localTypedArray3.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
      localTypedArray3.recycle();
    }
    TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    if (localTypedArray2.hasValue(R.styleable.AppCompatTextView_textAllCaps))
      setAllCaps(localTypedArray2.getBoolean(R.styleable.AppCompatTextView_textAllCaps, false));
    localTypedArray2.recycle();
    ColorStateList localColorStateList1 = getTextColors();
    if ((localColorStateList1 != null) && (!localColorStateList1.isStateful()))
      if (Build.VERSION.SDK_INT >= 21)
        break label244;
    label244: for (int j = ThemeUtils.getDisabledThemeAttrColor(paramContext, 16842808); ; j = ThemeUtils.getThemeAttrColor(paramContext, 16842808))
    {
      setTextColor(ThemeUtils.createDisabledStateList(localColorStateList1.getDefaultColor(), j));
      return;
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

  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(Button.class.getName());
  }

  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName(Button.class.getName());
  }

  public void setAllCaps(boolean paramBoolean)
  {
    if (paramBoolean);
    for (AllCapsTransformationMethod localAllCapsTransformationMethod = new AllCapsTransformationMethod(getContext()); ; localAllCapsTransformationMethod = null)
    {
      setTransformationMethod(localAllCapsTransformationMethod);
      return;
    }
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

  public void setTextAppearance(Context paramContext, int paramInt)
  {
    super.setTextAppearance(paramContext, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.TextAppearance);
    if (localTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps))
      setAllCaps(localTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
    localTypedArray.recycle();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatButton
 * JD-Core Version:    0.6.2
 */