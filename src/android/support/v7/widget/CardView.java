package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v7.cardview.R.style;
import android.support.v7.cardview.R.styleable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class CardView extends FrameLayout
  implements CardViewDelegate
{
  private static final CardViewImpl IMPL;
  private boolean mCompatPadding;
  private final Rect mContentPadding = new Rect();
  private boolean mPreventCornerOverlap;
  private final Rect mShadowBounds = new Rect();

  static
  {
    if (Build.VERSION.SDK_INT >= 21)
      IMPL = new CardViewApi21();
    while (true)
    {
      IMPL.initStatic();
      return;
      if (Build.VERSION.SDK_INT >= 17)
        IMPL = new CardViewJellybeanMr1();
      else
        IMPL = new CardViewEclairMr1();
    }
  }

  public CardView(Context paramContext)
  {
    super(paramContext);
    initialize(paramContext, null, 0);
  }

  public CardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize(paramContext, paramAttributeSet, 0);
  }

  public CardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize(paramContext, paramAttributeSet, paramInt);
  }

  private void initialize(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CardView, paramInt, R.style.CardView_Light);
    int i = localTypedArray.getColor(R.styleable.CardView_cardBackgroundColor, 0);
    float f1 = localTypedArray.getDimension(R.styleable.CardView_cardCornerRadius, 0.0F);
    float f2 = localTypedArray.getDimension(R.styleable.CardView_cardElevation, 0.0F);
    float f3 = localTypedArray.getDimension(R.styleable.CardView_cardMaxElevation, 0.0F);
    this.mCompatPadding = localTypedArray.getBoolean(R.styleable.CardView_cardUseCompatPadding, false);
    this.mPreventCornerOverlap = localTypedArray.getBoolean(R.styleable.CardView_cardPreventCornerOverlap, true);
    int j = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPadding, 0);
    this.mContentPadding.left = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingLeft, j);
    this.mContentPadding.top = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingTop, j);
    this.mContentPadding.right = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingRight, j);
    this.mContentPadding.bottom = localTypedArray.getDimensionPixelSize(R.styleable.CardView_contentPaddingBottom, j);
    if (f2 > f3)
      f3 = f2;
    localTypedArray.recycle();
    IMPL.initialize(this, paramContext, i, f1, f2, f3);
  }

  public float getCardElevation()
  {
    return IMPL.getElevation(this);
  }

  public int getContentPaddingBottom()
  {
    return this.mContentPadding.bottom;
  }

  public int getContentPaddingLeft()
  {
    return this.mContentPadding.left;
  }

  public int getContentPaddingRight()
  {
    return this.mContentPadding.right;
  }

  public int getContentPaddingTop()
  {
    return this.mContentPadding.top;
  }

  public float getMaxCardElevation()
  {
    return IMPL.getMaxElevation(this);
  }

  public boolean getPreventCornerOverlap()
  {
    return this.mPreventCornerOverlap;
  }

  public float getRadius()
  {
    return IMPL.getRadius(this);
  }

  public boolean getUseCompatPadding()
  {
    return this.mCompatPadding;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (!(IMPL instanceof CardViewApi21))
    {
      int i = View.MeasureSpec.getMode(paramInt1);
      int j;
      switch (i)
      {
      default:
        j = View.MeasureSpec.getMode(paramInt2);
        switch (j)
        {
        default:
        case 1073741824:
        case -2147483648:
        }
        break;
      case 1073741824:
      case -2147483648:
      }
      while (true)
      {
        super.onMeasure(paramInt1, paramInt2);
        return;
        paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.max((int)Math.ceil(IMPL.getMinWidth(this)), View.MeasureSpec.getSize(paramInt1)), i);
        break;
        paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.max((int)Math.ceil(IMPL.getMinHeight(this)), View.MeasureSpec.getSize(paramInt2)), j);
      }
    }
    super.onMeasure(paramInt1, paramInt2);
  }

  public void setCardBackgroundColor(int paramInt)
  {
    IMPL.setBackgroundColor(this, paramInt);
  }

  public void setCardElevation(float paramFloat)
  {
    IMPL.setElevation(this, paramFloat);
  }

  public void setContentPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mContentPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    IMPL.updatePadding(this);
  }

  public void setMaxCardElevation(float paramFloat)
  {
    IMPL.setMaxElevation(this, paramFloat);
  }

  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }

  public void setPaddingRelative(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }

  public void setPreventCornerOverlap(boolean paramBoolean)
  {
    if (paramBoolean == this.mPreventCornerOverlap)
      return;
    this.mPreventCornerOverlap = paramBoolean;
    IMPL.onPreventCornerOverlapChanged(this);
  }

  public void setRadius(float paramFloat)
  {
    IMPL.setRadius(this, paramFloat);
  }

  public void setShadowPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mShadowBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
    super.setPadding(paramInt1 + this.mContentPadding.left, paramInt2 + this.mContentPadding.top, paramInt3 + this.mContentPadding.right, paramInt4 + this.mContentPadding.bottom);
  }

  public void setUseCompatPadding(boolean paramBoolean)
  {
    if (this.mCompatPadding == paramBoolean)
      return;
    this.mCompatPadding = paramBoolean;
    IMPL.onCompatPaddingChanged(this);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.CardView
 * JD-Core Version:    0.6.2
 */