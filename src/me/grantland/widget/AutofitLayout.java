package me.grantland.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.WeakHashMap;

public class AutofitLayout extends FrameLayout
{
  private boolean mEnabled;
  private WeakHashMap<View, AutofitHelper> mHelpers = new WeakHashMap();
  private float mMinTextSize;
  private float mPrecision;

  public AutofitLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public AutofitLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public AutofitLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    boolean bool = true;
    int i = -1;
    float f = -1.0F;
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AutofitTextView, paramInt, 0);
      bool = localTypedArray.getBoolean(R.styleable.AutofitTextView_sizeToFit, bool);
      i = localTypedArray.getDimensionPixelSize(R.styleable.AutofitTextView_minTextSize, i);
      f = localTypedArray.getFloat(R.styleable.AutofitTextView_precision, f);
      localTypedArray.recycle();
    }
    this.mEnabled = bool;
    this.mMinTextSize = i;
    this.mPrecision = f;
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    TextView localTextView = (TextView)paramView;
    AutofitHelper localAutofitHelper = AutofitHelper.create(localTextView).setEnabled(this.mEnabled);
    if (this.mPrecision > 0.0F)
      localAutofitHelper.setPrecision(this.mPrecision);
    if (this.mMinTextSize > 0.0F)
      localAutofitHelper.setMinTextSize(0, this.mMinTextSize);
    this.mHelpers.put(localTextView, localAutofitHelper);
  }

  public AutofitHelper getAutofitHelper(int paramInt)
  {
    return (AutofitHelper)this.mHelpers.get(getChildAt(paramInt));
  }

  public AutofitHelper getAutofitHelper(TextView paramTextView)
  {
    return (AutofitHelper)this.mHelpers.get(paramTextView);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     me.grantland.widget.AutofitLayout
 * JD-Core Version:    0.6.2
 */