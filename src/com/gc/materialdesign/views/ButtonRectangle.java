package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.utils.Utils;

public class ButtonRectangle extends Button
{
  Integer height;
  int paddingBottom;
  int paddingLeft;
  int paddingRight;
  int paddingTop;
  TextView textButton;
  Integer width;

  public ButtonRectangle(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setDefaultProperties();
  }

  public String getText()
  {
    return this.textButton.getText().toString();
  }

  public TextView getTextView()
  {
    return this.textButton;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.x != -1.0F)
    {
      Rect localRect1 = new Rect(0, 0, getWidth() - Utils.dpToPx(6.0F, getResources()), getHeight() - Utils.dpToPx(7.0F, getResources()));
      Rect localRect2 = new Rect(Utils.dpToPx(6.0F, getResources()), Utils.dpToPx(6.0F, getResources()), getWidth() - Utils.dpToPx(6.0F, getResources()), getHeight() - Utils.dpToPx(7.0F, getResources()));
      paramCanvas.drawBitmap(makeCircle(), localRect1, localRect2, null);
      invalidate();
    }
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    int j;
    if (i != -1)
    {
      setBackgroundColor(getResources().getColor(i));
      paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "padding");
      j = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
      if (j == -1)
        break label254;
    }
    label254: for (String str = getResources().getString(j); ; str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "text"))
    {
      if (str != null)
      {
        this.textButton = new TextView(getContext());
        this.textButton.setText(str);
        this.textButton.setTextColor(-1);
        this.textButton.setTypeface(null, 1);
        RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        localLayoutParams.addRule(13, -1);
        localLayoutParams.setMargins(Utils.dpToPx(5.0F, getResources()), Utils.dpToPx(5.0F, getResources()), Utils.dpToPx(5.0F, getResources()), Utils.dpToPx(5.0F, getResources()));
        this.textButton.setLayoutParams(localLayoutParams);
        addView(this.textButton);
      }
      this.rippleSpeed = paramAttributeSet.getAttributeFloatValue("http://schemas.android.com/apk/res-auto", "rippleSpeed", Utils.dpToPx(6.0F, getResources()));
      return;
      this.background = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
      if (this.background == -1)
        break;
      setBackgroundColor(this.background);
      break;
    }
  }

  protected void setDefaultProperties()
  {
    this.minWidth = 80;
    this.minHeight = 36;
    this.background = R.drawable.background_button_rectangle;
    super.setDefaultProperties();
  }

  public void setText(String paramString)
  {
    this.textButton.setText(paramString);
  }

  public void setTextColor(int paramInt)
  {
    this.textButton.setTextColor(paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ButtonRectangle
 * JD-Core Version:    0.6.2
 */