package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.utils.Utils;

public class ButtonFloatSmall extends ButtonFloat
{
  public ButtonFloatSmall(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.sizeRadius = 20;
    this.sizeIcon = 20;
    setDefaultProperties();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(Utils.dpToPx(this.sizeIcon, getResources()), Utils.dpToPx(this.sizeIcon, getResources()));
    localLayoutParams.addRule(13, -1);
    this.icon.setLayoutParams(localLayoutParams);
  }

  protected void setDefaultProperties()
  {
    this.rippleSpeed = Utils.dpToPx(2.0F, getResources());
    this.rippleSize = 10;
    setMinimumHeight(Utils.dpToPx(2 * this.sizeRadius, getResources()));
    setMinimumWidth(Utils.dpToPx(2 * this.sizeRadius, getResources()));
    setBackgroundResource(R.drawable.background_button_float);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ButtonFloatSmall
 * JD-Core Version:    0.6.2
 */