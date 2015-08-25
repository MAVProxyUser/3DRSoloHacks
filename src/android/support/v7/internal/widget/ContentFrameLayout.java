package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class ContentFrameLayout extends FrameLayout
{
  private final Rect mDecorPadding = new Rect();
  private TypedValue mFixedHeightMajor;
  private TypedValue mFixedHeightMinor;
  private TypedValue mFixedWidthMajor;
  private TypedValue mFixedWidthMinor;
  private TypedValue mMinWidthMajor;
  private TypedValue mMinWidthMinor;

  public ContentFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }

  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void dispatchFitSystemWindows(Rect paramRect)
  {
    fitSystemWindows(paramRect);
  }

  public TypedValue getFixedHeightMajor()
  {
    if (this.mFixedHeightMajor == null)
      this.mFixedHeightMajor = new TypedValue();
    return this.mFixedHeightMajor;
  }

  public TypedValue getFixedHeightMinor()
  {
    if (this.mFixedHeightMinor == null)
      this.mFixedHeightMinor = new TypedValue();
    return this.mFixedHeightMinor;
  }

  public TypedValue getFixedWidthMajor()
  {
    if (this.mFixedWidthMajor == null)
      this.mFixedWidthMajor = new TypedValue();
    return this.mFixedWidthMajor;
  }

  public TypedValue getFixedWidthMinor()
  {
    if (this.mFixedWidthMinor == null)
      this.mFixedWidthMinor = new TypedValue();
    return this.mFixedWidthMinor;
  }

  public TypedValue getMinWidthMajor()
  {
    if (this.mMinWidthMajor == null)
      this.mMinWidthMajor = new TypedValue();
    return this.mMinWidthMajor;
  }

  public TypedValue getMinWidthMinor()
  {
    if (this.mMinWidthMinor == null)
      this.mMinWidthMinor = new TypedValue();
    return this.mMinWidthMinor;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    DisplayMetrics localDisplayMetrics = getContext().getResources().getDisplayMetrics();
    int i;
    TypedValue localTypedValue3;
    label58: int i10;
    label99: TypedValue localTypedValue2;
    label159: int i7;
    label190: int n;
    int i1;
    int i2;
    TypedValue localTypedValue1;
    label276: int i5;
    if (localDisplayMetrics.widthPixels < localDisplayMetrics.heightPixels)
    {
      i = 1;
      int j = View.MeasureSpec.getMode(paramInt1);
      int k = View.MeasureSpec.getMode(paramInt2);
      int m = 0;
      if (j == -2147483648)
      {
        if (i == 0)
          break label383;
        localTypedValue3 = this.mFixedWidthMinor;
        m = 0;
        if (localTypedValue3 != null)
        {
          int i8 = localTypedValue3.type;
          m = 0;
          if (i8 != 0)
          {
            if (localTypedValue3.type != 5)
              break label392;
            i10 = (int)localTypedValue3.getDimension(localDisplayMetrics);
            m = 0;
            if (i10 > 0)
            {
              paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.min(i10 - (this.mDecorPadding.left + this.mDecorPadding.right), View.MeasureSpec.getSize(paramInt1)), 1073741824);
              m = 1;
            }
          }
        }
      }
      if (k == -2147483648)
      {
        if (i == 0)
          break label430;
        localTypedValue2 = this.mFixedHeightMajor;
        if ((localTypedValue2 != null) && (localTypedValue2.type != 0))
        {
          if (localTypedValue2.type != 5)
            break label439;
          i7 = (int)localTypedValue2.getDimension(localDisplayMetrics);
          if (i7 > 0)
            paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.min(i7 - (this.mDecorPadding.top + this.mDecorPadding.bottom), View.MeasureSpec.getSize(paramInt2)), 1073741824);
        }
      }
      super.onMeasure(paramInt1, paramInt2);
      n = getMeasuredWidth();
      i1 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
      i2 = 0;
      if (m == 0)
      {
        i2 = 0;
        if (j == -2147483648)
        {
          if (i == 0)
            break label477;
          localTypedValue1 = this.mMinWidthMinor;
          i2 = 0;
          if (localTypedValue1 != null)
          {
            int i3 = localTypedValue1.type;
            i2 = 0;
            if (i3 != 0)
            {
              if (localTypedValue1.type != 5)
                break label486;
              i5 = (int)localTypedValue1.getDimension(localDisplayMetrics);
            }
          }
        }
      }
    }
    while (true)
    {
      if (i5 > 0)
        i5 -= this.mDecorPadding.left + this.mDecorPadding.right;
      i2 = 0;
      if (n < i5)
      {
        i1 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        i2 = 1;
      }
      if (i2 != 0)
        super.onMeasure(i1, paramInt2);
      return;
      i = 0;
      break;
      label383: localTypedValue3 = this.mFixedWidthMajor;
      break label58;
      label392: int i9 = localTypedValue3.type;
      i10 = 0;
      if (i9 != 6)
        break label99;
      i10 = (int)localTypedValue3.getFraction(localDisplayMetrics.widthPixels, localDisplayMetrics.widthPixels);
      break label99;
      label430: localTypedValue2 = this.mFixedHeightMinor;
      break label159;
      label439: int i6 = localTypedValue2.type;
      i7 = 0;
      if (i6 != 6)
        break label190;
      i7 = (int)localTypedValue2.getFraction(localDisplayMetrics.heightPixels, localDisplayMetrics.heightPixels);
      break label190;
      label477: localTypedValue1 = this.mMinWidthMajor;
      break label276;
      label486: int i4 = localTypedValue1.type;
      i5 = 0;
      if (i4 == 6)
        i5 = (int)localTypedValue1.getFraction(localDisplayMetrics.widthPixels, localDisplayMetrics.widthPixels);
    }
  }

  public void setDecorPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mDecorPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    if (ViewCompat.isLaidOut(this))
      requestLayout();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ContentFrameLayout
 * JD-Core Version:    0.6.2
 */