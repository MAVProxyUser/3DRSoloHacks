package com.o3dr.solo.android.widget.spinnerWheel.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class AbstractWheelTextAdapter<T> extends AbstractWheelAdapter
{
  public static final int DEFAULT_TEXT_COLOR = -15724528;
  public static final int DEFAULT_TEXT_SIZE = 24;
  public static final int LABEL_COLOR = -9437072;
  protected static final int NO_RESOURCE = 0;
  public static final int TEXT_VIEW_ITEM_RESOURCE = -1;
  protected Context context;
  protected int emptyItemResourceId;
  protected LayoutInflater inflater;
  protected int itemResourceId;
  protected int itemTextResourceId;
  private int textColor = -15724528;
  private int textSize = 24;
  private Typeface textTypeface;

  protected AbstractWheelTextAdapter(Context paramContext)
  {
    this(paramContext, -1);
  }

  protected AbstractWheelTextAdapter(Context paramContext, int paramInt)
  {
    this(paramContext, paramInt, 0);
  }

  protected AbstractWheelTextAdapter(Context paramContext, int paramInt1, int paramInt2)
  {
    this.context = paramContext;
    this.itemResourceId = paramInt1;
    this.itemTextResourceId = paramInt2;
    this.inflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }

  private TextView getTextView(View paramView, int paramInt)
  {
    if (paramInt == 0);
    try
    {
      if ((paramView instanceof TextView))
        return (TextView)paramView;
      if (paramInt != 0)
      {
        TextView localTextView = (TextView)paramView.findViewById(paramInt);
        return localTextView;
      }
    }
    catch (ClassCastException localClassCastException)
    {
      Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
      throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a TextView", localClassCastException);
    }
    return null;
  }

  private View getView(int paramInt, ViewGroup paramViewGroup)
  {
    switch (paramInt)
    {
    default:
      return this.inflater.inflate(paramInt, paramViewGroup, false);
    case 0:
      return null;
    case -1:
    }
    return new TextView(this.context);
  }

  protected void configureTextView(TextView paramTextView)
  {
    if (this.itemResourceId == -1)
    {
      paramTextView.setTextColor(this.textColor);
      paramTextView.setGravity(17);
      paramTextView.setTextSize(this.textSize);
      paramTextView.setLines(1);
    }
    if (this.textTypeface != null)
    {
      paramTextView.setTypeface(this.textTypeface);
      return;
    }
    paramTextView.setTypeface(Typeface.SANS_SERIF, 1);
  }

  public View getEmptyItem(View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = getView(this.emptyItemResourceId, paramViewGroup);
    if ((paramView instanceof TextView))
      configureTextView((TextView)paramView);
    return paramView;
  }

  public int getEmptyItemResource()
  {
    return this.emptyItemResourceId;
  }

  public View getItem(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if ((paramInt >= 0) && (paramInt < getItemsCount()))
    {
      if (paramView == null)
        paramView = getView(this.itemResourceId, paramViewGroup);
      TextView localTextView = getTextView(paramView, this.itemTextResourceId);
      if (localTextView != null)
      {
        Object localObject = getItemText(paramInt);
        if (localObject == null)
          localObject = "";
        localTextView.setText((CharSequence)localObject);
        configureTextView(localTextView);
      }
      return paramView;
    }
    return null;
  }

  public abstract T getItem(int paramInt);

  public abstract int getItemIndex(T paramT);

  public int getItemResource()
  {
    return this.itemResourceId;
  }

  protected abstract CharSequence getItemText(int paramInt);

  public int getItemTextResource()
  {
    return this.itemTextResourceId;
  }

  public int getTextColor()
  {
    return this.textColor;
  }

  public int getTextSize()
  {
    return this.textSize;
  }

  public abstract T parseItemText(CharSequence paramCharSequence);

  public void setEmptyItemResource(int paramInt)
  {
    this.emptyItemResourceId = paramInt;
  }

  public void setItemResource(int paramInt)
  {
    this.itemResourceId = paramInt;
  }

  public void setItemTextResource(int paramInt)
  {
    this.itemTextResourceId = paramInt;
  }

  public void setTextColor(int paramInt)
  {
    this.textColor = paramInt;
  }

  public void setTextSize(int paramInt)
  {
    this.textSize = paramInt;
  }

  public void setTextTypeface(Typeface paramTypeface)
  {
    this.textTypeface = paramTypeface;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.adapters.AbstractWheelTextAdapter
 * JD-Core Version:    0.6.2
 */