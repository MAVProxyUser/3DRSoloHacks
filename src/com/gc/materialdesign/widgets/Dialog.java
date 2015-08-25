package com.gc.materialdesign.widgets;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gc.materialdesign.R.anim;
import com.gc.materialdesign.R.id;
import com.gc.materialdesign.R.layout;
import com.gc.materialdesign.views.ButtonFlat;

public class Dialog extends android.app.Dialog
{
  View backView;
  ButtonFlat buttonAccept;
  ButtonFlat buttonCancel;
  Context context;
  String message;
  TextView messageTextView;
  View.OnClickListener onAcceptButtonClickListener;
  View.OnClickListener onCancelButtonClickListener;
  String title;
  TextView titleTextView;
  View view;

  public Dialog(Context paramContext, String paramString1, String paramString2)
  {
    super(paramContext, 16973839);
    this.context = paramContext;
    this.message = paramString2;
    this.title = paramString1;
  }

  public void dismiss()
  {
    Animation localAnimation1 = AnimationUtils.loadAnimation(this.context, R.anim.dialog_main_hide_amination);
    localAnimation1.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        Dialog.this.view.post(new Runnable()
        {
          public void run()
          {
            Dialog.this.dismiss();
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

  public ButtonFlat getButtonAccept()
  {
    return this.buttonAccept;
  }

  public ButtonFlat getButtonCancel()
  {
    return this.buttonCancel;
  }

  public String getMessage()
  {
    return this.message;
  }

  public TextView getMessageTextView()
  {
    return this.messageTextView;
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
    setContentView(R.layout.dialog);
    this.view = ((RelativeLayout)findViewById(R.id.contentDialog));
    this.backView = ((RelativeLayout)findViewById(R.id.dialog_rootView));
    this.backView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if ((paramAnonymousMotionEvent.getX() < Dialog.this.view.getLeft()) || (paramAnonymousMotionEvent.getX() > Dialog.this.view.getRight()) || (paramAnonymousMotionEvent.getY() > Dialog.this.view.getBottom()) || (paramAnonymousMotionEvent.getY() < Dialog.this.view.getTop()))
          Dialog.this.dismiss();
        return false;
      }
    });
    this.titleTextView = ((TextView)findViewById(R.id.title));
    setTitle(this.title);
    this.messageTextView = ((TextView)findViewById(R.id.message));
    setMessage(this.message);
    this.buttonAccept = ((ButtonFlat)findViewById(R.id.button_accept));
    this.buttonAccept.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Dialog.this.dismiss();
        if (Dialog.this.onAcceptButtonClickListener != null)
          Dialog.this.onAcceptButtonClickListener.onClick(paramAnonymousView);
      }
    });
    this.buttonCancel = ((ButtonFlat)findViewById(R.id.button_cancel));
    this.buttonCancel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Dialog.this.dismiss();
        if (Dialog.this.onCancelButtonClickListener != null)
          Dialog.this.onCancelButtonClickListener.onClick(paramAnonymousView);
      }
    });
  }

  public void setButtonAccept(ButtonFlat paramButtonFlat)
  {
    this.buttonAccept = paramButtonFlat;
  }

  public void setButtonCancel(ButtonFlat paramButtonFlat)
  {
    this.buttonCancel = paramButtonFlat;
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
    this.messageTextView.setText(paramString);
  }

  public void setMessageTextView(TextView paramTextView)
  {
    this.messageTextView = paramTextView;
  }

  public void setOnAcceptButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    this.onAcceptButtonClickListener = paramOnClickListener;
    if (this.buttonAccept != null)
      this.buttonAccept.setOnClickListener(paramOnClickListener);
  }

  public void setOnCancelButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    this.onCancelButtonClickListener = paramOnClickListener;
    if (this.buttonCancel != null)
      this.buttonCancel.setOnClickListener(paramOnClickListener);
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
 * Qualified Name:     com.gc.materialdesign.widgets.Dialog
 * JD-Core Version:    0.6.2
 */