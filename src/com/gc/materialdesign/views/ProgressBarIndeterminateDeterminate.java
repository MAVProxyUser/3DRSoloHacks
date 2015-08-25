package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ProgressBarIndeterminateDeterminate extends ProgressBarDeterminate
{
  ObjectAnimator animation;
  boolean firstProgress = true;
  boolean runAnimation = true;

  public ProgressBarIndeterminateDeterminate(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    post(new Runnable()
    {
      public void run()
      {
        ProgressBarIndeterminateDeterminate.this.setProgress(60);
        ViewHelper.setX(ProgressBarIndeterminateDeterminate.this.progressView, ProgressBarIndeterminateDeterminate.this.getWidth() + ProgressBarIndeterminateDeterminate.this.progressView.getWidth() / 2);
        ProgressBarIndeterminateDeterminate localProgressBarIndeterminateDeterminate = ProgressBarIndeterminateDeterminate.this;
        View localView = ProgressBarIndeterminateDeterminate.this.progressView;
        float[] arrayOfFloat = new float[1];
        arrayOfFloat[0] = (-ProgressBarIndeterminateDeterminate.this.progressView.getWidth() / 2);
        localProgressBarIndeterminateDeterminate.animation = ObjectAnimator.ofFloat(localView, "x", arrayOfFloat);
        ProgressBarIndeterminateDeterminate.this.animation.setDuration(1200L);
        ProgressBarIndeterminateDeterminate.this.animation.addListener(new Animator.AnimatorListener()
        {
          int cont = 1;
          int duration = 1200;
          int suma = 1;

          public void onAnimationCancel(Animator paramAnonymous2Animator)
          {
          }

          public void onAnimationEnd(Animator paramAnonymous2Animator)
          {
            if (ProgressBarIndeterminateDeterminate.this.runAnimation)
            {
              ViewHelper.setX(ProgressBarIndeterminateDeterminate.this.progressView, ProgressBarIndeterminateDeterminate.this.getWidth() + ProgressBarIndeterminateDeterminate.this.progressView.getWidth() / 2);
              this.cont += this.suma;
              ProgressBarIndeterminateDeterminate localProgressBarIndeterminateDeterminate = ProgressBarIndeterminateDeterminate.this;
              View localView = ProgressBarIndeterminateDeterminate.this.progressView;
              float[] arrayOfFloat = new float[1];
              arrayOfFloat[0] = (-ProgressBarIndeterminateDeterminate.this.progressView.getWidth() / 2);
              localProgressBarIndeterminateDeterminate.animation = ObjectAnimator.ofFloat(localView, "x", arrayOfFloat);
              ProgressBarIndeterminateDeterminate.this.animation.setDuration(this.duration / this.cont);
              ProgressBarIndeterminateDeterminate.this.animation.addListener(this);
              ProgressBarIndeterminateDeterminate.this.animation.start();
              if ((this.cont == 3) || (this.cont == 1))
                this.suma = (-1 * this.suma);
            }
          }

          public void onAnimationRepeat(Animator paramAnonymous2Animator)
          {
          }

          public void onAnimationStart(Animator paramAnonymous2Animator)
          {
          }
        });
        ProgressBarIndeterminateDeterminate.this.animation.start();
      }
    });
  }

  private void stopIndeterminate()
  {
    this.animation.cancel();
    ViewHelper.setX(this.progressView, 0.0F);
    this.runAnimation = false;
  }

  public void setProgress(int paramInt)
  {
    if (this.firstProgress)
      this.firstProgress = false;
    while (true)
    {
      super.setProgress(paramInt);
      return;
      stopIndeterminate();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ProgressBarIndeterminateDeterminate
 * JD-Core Version:    0.6.2
 */