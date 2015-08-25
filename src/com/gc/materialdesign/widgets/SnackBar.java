package com.gc.materialdesign.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.gc.materialdesign.R.anim;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.R.layout;
import com.gc.materialdesign.views.ButtonFlat;

public class SnackBar extends Dialog
{
  Activity activity;
  int backgroundButton = Color.parseColor("#1E88E5");
  int backgroundSnackBar = Color.parseColor("#333333");
  ButtonFlat button;
  String buttonText;
  Thread dismissTimer = new Thread(new Runnable()
  {
    public void run()
    {
      try
      {
        Thread.sleep(SnackBar.this.mTimer);
        SnackBar.this.handler.sendMessage(new Message());
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          localInterruptedException.printStackTrace();
      }
    }
  });
  Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      if (SnackBar.this.onHideListener != null)
        SnackBar.this.onHideListener.onHide();
      SnackBar.this.dismiss();
      return false;
    }
  });
  private boolean mIndeterminate = false;
  private int mTimer = 3000;
  View.OnClickListener onClickListener;
  OnHideListener onHideListener;
  String text;
  float textSize = 14.0F;
  View view;

  public SnackBar(Activity paramActivity, String paramString)
  {
    super(paramActivity, 16973839);
    this.activity = paramActivity;
    this.text = paramString;
  }

  public SnackBar(Activity paramActivity, String paramString1, String paramString2, View.OnClickListener paramOnClickListener)
  {
    super(paramActivity, 16973839);
    this.activity = paramActivity;
    this.text = paramString1;
    this.buttonText = paramString2;
    this.onClickListener = paramOnClickListener;
  }

  public void dismiss()
  {
    Animation localAnimation = AnimationUtils.loadAnimation(this.activity, R.anim.snackbar_hide_animation);
    localAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        SnackBar.this.dismiss();
      }

      public void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
    this.view.startAnimation(localAnimation);
  }

  public int getDismissTimer()
  {
    return this.mTimer;
  }

  public boolean isIndeterminate()
  {
    return this.mIndeterminate;
  }

  public void onBackPressed()
  {
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(R.layout.snackbar);
    setCanceledOnTouchOutside(false);
    ((TextView)findViewById(R.id.text)).setText(this.text);
    ((TextView)findViewById(R.id.text)).setTextSize(this.textSize);
    this.button = ((ButtonFlat)findViewById(R.id.buttonflat));
    if ((this.text == null) || (this.onClickListener == null))
      this.button.setVisibility(8);
    while (true)
    {
      this.view = findViewById(R.id.snackbar);
      this.view.setBackgroundColor(this.backgroundSnackBar);
      return;
      this.button.setText(this.buttonText);
      this.button.setBackgroundColor(this.backgroundButton);
      this.button.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          SnackBar.this.dismiss();
          SnackBar.this.onClickListener.onClick(paramAnonymousView);
        }
      });
    }
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
      dismiss();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.activity.dispatchTouchEvent(paramMotionEvent);
  }

  public void setBackgroundSnackBar(int paramInt)
  {
    this.backgroundSnackBar = paramInt;
    if (this.view != null)
      this.view.setBackgroundColor(paramInt);
  }

  public void setColorButton(int paramInt)
  {
    this.backgroundButton = paramInt;
    if (this.button != null)
      this.button.setBackgroundColor(paramInt);
  }

  public void setDismissTimer(int paramInt)
  {
    this.mTimer = paramInt;
  }

  public void setIndeterminate(boolean paramBoolean)
  {
    this.mIndeterminate = paramBoolean;
  }

  public void setMessageTextSize(float paramFloat)
  {
    this.textSize = paramFloat;
  }

  public void setOnhideListener(OnHideListener paramOnHideListener)
  {
    this.onHideListener = paramOnHideListener;
  }

  public void show()
  {
    super.show();
    this.view.setVisibility(0);
    this.view.startAnimation(AnimationUtils.loadAnimation(this.activity, R.anim.snackbar_show_animation));
    if (!this.mIndeterminate)
      this.dismissTimer.start();
  }

  public static abstract interface OnHideListener
  {
    public abstract void onHide();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.widgets.SnackBar
 * JD-Core Version:    0.6.2
 */