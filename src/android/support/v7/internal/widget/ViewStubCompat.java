package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.appcompat.R.styleable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

public final class ViewStubCompat extends View
{
  private OnInflateListener mInflateListener;
  private int mInflatedId;
  private WeakReference<View> mInflatedViewRef;
  private LayoutInflater mInflater;
  private int mLayoutResource = 0;

  public ViewStubCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public ViewStubCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ViewStubCompat, paramInt, 0);
    this.mInflatedId = localTypedArray.getResourceId(R.styleable.ViewStubCompat_android_inflatedId, -1);
    this.mLayoutResource = localTypedArray.getResourceId(R.styleable.ViewStubCompat_android_layout, 0);
    setId(localTypedArray.getResourceId(R.styleable.ViewStubCompat_android_id, -1));
    localTypedArray.recycle();
    setVisibility(8);
    setWillNotDraw(true);
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
  }

  public void draw(Canvas paramCanvas)
  {
  }

  public int getInflatedId()
  {
    return this.mInflatedId;
  }

  public LayoutInflater getLayoutInflater()
  {
    return this.mInflater;
  }

  public int getLayoutResource()
  {
    return this.mLayoutResource;
  }

  public View inflate()
  {
    ViewParent localViewParent = getParent();
    if ((localViewParent != null) && ((localViewParent instanceof ViewGroup)))
    {
      if (this.mLayoutResource != 0)
      {
        ViewGroup localViewGroup = (ViewGroup)localViewParent;
        LayoutInflater localLayoutInflater;
        View localView;
        int i;
        if (this.mInflater != null)
        {
          localLayoutInflater = this.mInflater;
          localView = localLayoutInflater.inflate(this.mLayoutResource, localViewGroup, false);
          if (this.mInflatedId != -1)
            localView.setId(this.mInflatedId);
          i = localViewGroup.indexOfChild(this);
          localViewGroup.removeViewInLayout(this);
          ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
          if (localLayoutParams == null)
            break label148;
          localViewGroup.addView(localView, i, localLayoutParams);
        }
        while (true)
        {
          this.mInflatedViewRef = new WeakReference(localView);
          if (this.mInflateListener != null)
            this.mInflateListener.onInflate(this, localView);
          return localView;
          localLayoutInflater = LayoutInflater.from(getContext());
          break;
          label148: localViewGroup.addView(localView, i);
        }
      }
      throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
    }
    throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(0, 0);
  }

  public void setInflatedId(int paramInt)
  {
    this.mInflatedId = paramInt;
  }

  public void setLayoutInflater(LayoutInflater paramLayoutInflater)
  {
    this.mInflater = paramLayoutInflater;
  }

  public void setLayoutResource(int paramInt)
  {
    this.mLayoutResource = paramInt;
  }

  public void setOnInflateListener(OnInflateListener paramOnInflateListener)
  {
    this.mInflateListener = paramOnInflateListener;
  }

  public void setVisibility(int paramInt)
  {
    if (this.mInflatedViewRef != null)
    {
      View localView = (View)this.mInflatedViewRef.get();
      if (localView != null)
        localView.setVisibility(paramInt);
    }
    do
    {
      return;
      throw new IllegalStateException("setVisibility called on un-referenced view");
      super.setVisibility(paramInt);
    }
    while ((paramInt != 0) && (paramInt != 4));
    inflate();
  }

  public static abstract interface OnInflateListener
  {
    public abstract void onInflate(ViewStubCompat paramViewStubCompat, View paramView);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ViewStubCompat
 * JD-Core Version:    0.6.2
 */