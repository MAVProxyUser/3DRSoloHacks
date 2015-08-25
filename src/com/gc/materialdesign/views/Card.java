package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.R.id;

public class Card extends CustomView
{
  int backgroundColor = Color.parseColor("#FFFFFF");
  int paddingBottom;
  int paddingLeft;
  int paddingRight;
  int paddingTop;
  TextView textButton;

  public Card(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributes(paramAttributeSet);
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    setBackgroundResource(R.drawable.background_button_rectangle);
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
    {
      setBackgroundColor(getResources().getColor(i));
      return;
    }
    String str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background");
    if (str != null)
    {
      setBackgroundColor(Color.parseColor(str));
      return;
    }
    setBackgroundColor(this.backgroundColor);
  }

  public void setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
    ((GradientDrawable)((LayerDrawable)getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(this.backgroundColor);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.Card
 * JD-Core Version:    0.6.2
 */