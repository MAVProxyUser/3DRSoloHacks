package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

public class TintTypedArray
{
  private final Context mContext;
  private TintManager mTintManager;
  private final TypedArray mWrapped;

  private TintTypedArray(Context paramContext, TypedArray paramTypedArray)
  {
    this.mContext = paramContext;
    this.mWrapped = paramTypedArray;
  }

  public static TintTypedArray obtainStyledAttributes(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfInt)
  {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt));
  }

  public static TintTypedArray obtainStyledAttributes(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt, paramInt1, paramInt2));
  }

  public boolean getBoolean(int paramInt, boolean paramBoolean)
  {
    return this.mWrapped.getBoolean(paramInt, paramBoolean);
  }

  public int getChangingConfigurations()
  {
    return this.mWrapped.getChangingConfigurations();
  }

  public int getColor(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getColor(paramInt1, paramInt2);
  }

  public ColorStateList getColorStateList(int paramInt)
  {
    return this.mWrapped.getColorStateList(paramInt);
  }

  public float getDimension(int paramInt, float paramFloat)
  {
    return this.mWrapped.getDimension(paramInt, paramFloat);
  }

  public int getDimensionPixelOffset(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getDimensionPixelOffset(paramInt1, paramInt2);
  }

  public int getDimensionPixelSize(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getDimensionPixelSize(paramInt1, paramInt2);
  }

  public Drawable getDrawable(int paramInt)
  {
    if (this.mWrapped.hasValue(paramInt))
    {
      int i = this.mWrapped.getResourceId(paramInt, 0);
      if (i != 0)
        return getTintManager().getDrawable(i);
    }
    return this.mWrapped.getDrawable(paramInt);
  }

  public Drawable getDrawableIfKnown(int paramInt)
  {
    if (this.mWrapped.hasValue(paramInt))
    {
      int i = this.mWrapped.getResourceId(paramInt, 0);
      if (i != 0)
        return getTintManager().getDrawable(i, true);
    }
    return null;
  }

  public float getFloat(int paramInt, float paramFloat)
  {
    return this.mWrapped.getFloat(paramInt, paramFloat);
  }

  public float getFraction(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    return this.mWrapped.getFraction(paramInt1, paramInt2, paramInt3, paramFloat);
  }

  public int getIndex(int paramInt)
  {
    return this.mWrapped.getIndex(paramInt);
  }

  public int getIndexCount()
  {
    return this.mWrapped.getIndexCount();
  }

  public int getInt(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getInt(paramInt1, paramInt2);
  }

  public int getInteger(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getInteger(paramInt1, paramInt2);
  }

  public int getLayoutDimension(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getLayoutDimension(paramInt1, paramInt2);
  }

  public int getLayoutDimension(int paramInt, String paramString)
  {
    return this.mWrapped.getLayoutDimension(paramInt, paramString);
  }

  public String getNonResourceString(int paramInt)
  {
    return this.mWrapped.getNonResourceString(paramInt);
  }

  public String getPositionDescription()
  {
    return this.mWrapped.getPositionDescription();
  }

  public int getResourceId(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getResourceId(paramInt1, paramInt2);
  }

  public Resources getResources()
  {
    return this.mWrapped.getResources();
  }

  public String getString(int paramInt)
  {
    return this.mWrapped.getString(paramInt);
  }

  public CharSequence getText(int paramInt)
  {
    return this.mWrapped.getText(paramInt);
  }

  public CharSequence[] getTextArray(int paramInt)
  {
    return this.mWrapped.getTextArray(paramInt);
  }

  public TintManager getTintManager()
  {
    if (this.mTintManager == null)
      this.mTintManager = TintManager.get(this.mContext);
    return this.mTintManager;
  }

  public int getType(int paramInt)
  {
    return this.mWrapped.getType(paramInt);
  }

  public boolean getValue(int paramInt, TypedValue paramTypedValue)
  {
    return this.mWrapped.getValue(paramInt, paramTypedValue);
  }

  public boolean hasValue(int paramInt)
  {
    return this.mWrapped.hasValue(paramInt);
  }

  public int length()
  {
    return this.mWrapped.length();
  }

  public TypedValue peekValue(int paramInt)
  {
    return this.mWrapped.peekValue(paramInt);
  }

  public void recycle()
  {
    this.mWrapped.recycle();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.TintTypedArray
 * JD-Core Version:    0.6.2
 */