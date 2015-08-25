package com.gc.materialdesign.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gc.materialdesign.R.anim;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.R.layout;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

public class ProgressDialog extends Dialog
{
  View backView;
  Context context;
  int progressColor = -1;
  String title;
  TextView titleTextView;
  View view;

  public ProgressDialog(Context paramContext, String paramString)
  {
    super(paramContext, 16973839);
    this.title = paramString;
    this.context = paramContext;
  }

  public ProgressDialog(Context paramContext, String paramString, int paramInt)
  {
    super(paramContext, 16973839);
    this.title = paramString;
    this.progressColor = paramInt;
    this.context = paramContext;
  }

  public void dismiss()
  {
    Animation localAnimation1 = AnimationUtils.loadAnimation(this.context, R.anim.dialog_main_hide_amination);
    localAnimation1.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        ProgressDialog.this.view.post(new Runnable()
        {
          public void run()
          {
            ProgressDialog.this.dismiss();
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

  public String getTitle()
  {
    return this.title;
  }

  public TextView getTitleTextView()
  {
    return this.titleTextView;
  }

  protected void onCreate(Bundle paramBundle)
  {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    setContentView(R.layout.progress_dialog);
    this.view = ((RelativeLayout)findViewById(R.id.contentDialog));
    this.backView = ((RelativeLayout)findViewById(R.id.dialog_rootView));
    this.backView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if ((paramAnonymousMotionEvent.getX() < ProgressDialog.this.view.getLeft()) || (paramAnonymousMotionEvent.getX() > ProgressDialog.this.view.getRight()) || (paramAnonymousMotionEvent.getY() > ProgressDialog.this.view.getBottom()) || (paramAnonymousMotionEvent.getY() < ProgressDialog.this.view.getTop()))
          ProgressDialog.this.dismiss();
        return false;
      }
    });
    this.titleTextView = ((TextView)findViewById(R.id.title));
    setTitle(this.title);
    if (this.progressColor != -1)
      ((ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndetermininate)).setBackgroundColor(this.progressColor);
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
    if (paramString == null)
    {
      this.titleTextView.setVisibility(8);
      return;
    }
    this.titleTextView.setVisibility(0);
    this.titleTextView.setText(paramString);
  }

  public void setTitleTextView(TextView paramTextView)
  {
    this.titleTextView = paramTextView;
  }

  public void show()
  {
    super.show();
    this.view.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.dialog_main_show_amination));
    this.backView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.dialog_root_show_amin));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.widgets.ProgressDialog
 * JD-Core Version:    0.6.2
 */