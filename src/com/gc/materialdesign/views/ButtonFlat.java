package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.utils.Utils;

public class ButtonFlat extends Button
{
  TextView textButton;

  public ButtonFlat(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public String getText()
  {
    return this.textButton.getText().toString();
  }

  public TextView getTextView()
  {
    return this.textButton;
  }

  protected int makePressColor()
  {
    return Color.parseColor("#88DDDDDD");
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.x != -1.0F)
    {
      Paint localPaint = new Paint();
      localPaint.setAntiAlias(true);
      localPaint.setColor(makePressColor());
      paramCanvas.drawCircle(this.x, this.y, this.radius, localPaint);
      if (this.radius > getHeight() / this.rippleSize)
        this.radius += this.rippleSpeed;
      if (this.radius >= getWidth())
      {
        this.x = -1.0F;
        this.y = -1.0F;
        this.radius = (getHeight() / this.rippleSize);
        if ((this.onClickListener != null) && (this.clickAfterRipple))
          this.onClickListener.onClick(this);
      }
      invalidate();
    }
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
    String str;
    if (i != -1)
    {
      str = getResources().getString(i);
      if (str != null)
      {
        this.textButton = new TextView(getContext());
        this.textButton.setText(str.toUpperCase());
        this.textButton.setTextColor(this.backgroundColor);
        this.textButton.setTypeface(null, 1);
        RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        localLayoutParams.addRule(13, -1);
        this.textButton.setLayoutParams(localLayoutParams);
        addView(this.textButton);
      }
      int j = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
      if (j == -1)
        break label161;
      setBackgroundColor(getResources().getColor(j));
    }
    label161: 
    do
    {
      return;
      str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
      break;
      this.background = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
    }
    while (this.background == -1);
    setBackgroundColor(this.background);
  }

  public void setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
    if (isEnabled())
      this.beforeBackground = this.backgroundColor;
    this.textButton.setTextColor(paramInt);
  }

  protected void setDefaultProperties()
  {
    this.minHeight = 36;
    this.minWidth = 88;
    this.rippleSize = 3;
    setMinimumHeight(Utils.dpToPx(this.minHeight, getResources()));
    setMinimumWidth(Utils.dpToPx(this.minWidth, getResources()));
    setBackgroundResource(R.drawable.background_transparent);
  }

  public void setText(String paramString)
  {
    this.textButton.setText(paramString.toUpperCase());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ButtonFlat
 * JD-Core Version:    0.6.2
 */