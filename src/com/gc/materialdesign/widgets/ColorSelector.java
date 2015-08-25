package com.gc.materialdesign.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.gc.materialdesign.R.anim;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.R.layout;
import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Slider.OnValueChangedListener;

public class ColorSelector extends Dialog
  implements Slider.OnValueChangedListener
{
  View backView;
  Slider blue;
  int color = -16777216;
  View colorView;
  Context context;
  Slider green;
  OnColorSelectedListener onColorSelectedListener;
  Slider red;
  View view;

  public ColorSelector(Context paramContext, Integer paramInteger, OnColorSelectedListener paramOnColorSelectedListener)
  {
    super(paramContext, 16973839);
    this.context = paramContext;
    this.onColorSelectedListener = paramOnColorSelectedListener;
    if (paramInteger != null)
      this.color = paramInteger.intValue();
    setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        if (ColorSelector.this.onColorSelectedListener != null)
          ColorSelector.this.onColorSelectedListener.onColorSelected(ColorSelector.this.color);
      }
    });
  }

  public void dismiss()
  {
    Animation localAnimation1 = AnimationUtils.loadAnimation(this.context, R.anim.dialog_main_hide_amination);
    localAnimation1.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        ColorSelector.this.view.post(new Runnable()
        {
          public void run()
          {
            ColorSelector.this.dismiss();
          }
        });
      }

      public void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
    Animation localAnimation2 = AnimationUtils.loadAnimation(this.context, R.anim.dialog_root_hide_amin);
    this.view.startAnimation(localAnimation1);
    this.backView.startAnimation(localAnimation2);
  }

  protected void onCreate(Bundle paramBundle)
  {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    setContentView(R.layout.color_selector);
    this.view = ((LinearLayout)findViewById(R.id.contentSelector));
    this.backView = ((RelativeLayout)findViewById(R.id.rootSelector));
    this.backView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if ((paramAnonymousMotionEvent.getX() < ColorSelector.this.view.getLeft()) || (paramAnonymousMotionEvent.getX() > ColorSelector.this.view.getRight()) || (paramAnonymousMotionEvent.getY() > ColorSelector.this.view.getBottom()) || (paramAnonymousMotionEvent.getY() < ColorSelector.this.view.getTop()))
          ColorSelector.this.dismiss();
        return false;
      }
    });
    this.colorView = findViewById(R.id.viewColor);
    this.colorView.setBackgroundColor(this.color);
    this.colorView.post(new Runnable()
    {
      public void run()
      {
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)ColorSelector.this.colorView.getLayoutParams();
        localLayoutParams.height = ColorSelector.this.colorView.getWidth();
        ColorSelector.this.colorView.setLayoutParams(localLayoutParams);
      }
    });
    this.red = ((Slider)findViewById(R.id.red));
    this.green = ((Slider)findViewById(R.id.green));
    this.blue = ((Slider)findViewById(R.id.blue));
    int i = 0xFF & this.color >> 16;
    int j = 0xFF & this.color >> 8;
    int k = 0xFF & this.color >> 0;
    this.red.setValue(i);
    this.green.setValue(j);
    this.blue.setValue(k);
    this.red.setOnValueChangedListener(this);
    this.green.setOnValueChangedListener(this);
    this.blue.setOnValueChangedListener(this);
  }

  public void onValueChanged(int paramInt)
  {
    this.color = Color.rgb(this.red.getValue(), this.green.getValue(), this.blue.getValue());
    this.colorView.setBackgroundColor(this.color);
  }

  public void show()
  {
    super.show();
    this.view.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.dialog_main_show_amination));
    this.backView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.dialog_root_show_amin));
  }

  public static abstract interface OnColorSelectedListener
  {
    public abstract void onColorSelected(int paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.widgets.ColorSelector
 * JD-Core Version:    0.6.2
 */