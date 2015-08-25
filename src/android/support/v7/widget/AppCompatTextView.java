package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.text.AllCapsTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppCompatTextView extends TextView
{
  public AppCompatTextView(Context paramContext)
  {
    this(paramContext, null);
  }

  public AppCompatTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842884);
  }

  public AppCompatTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
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
 * Qualified Name:     android.support.v7.widget.AppCompatTextView
 * JD-Core Version:    0.6.2
 */