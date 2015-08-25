package com.gc.materialdesign.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.gc.materialdesign.R.anim;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ProgressBarIndeterminate extends ProgressBarDeterminate
{
  public ProgressBarIndeterminate(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    post(new Runnable()
    {
      public void run()
      {
        ProgressBarIndeterminate.this.setProgress(60);
        Animation localAnimation = AnimationUtils.loadAnimation(ProgressBarIndeterminate.this.getContext(), R.anim.progress_indeterminate_animation);
        ProgressBarIndeterminate.this.progressView.startAnimation(localAnimation);
        View localView = ProgressBarIndeterminate.this.progressView;
        float[] arrayOfFloat = new float[1];
        arrayOfFloat[0] = ProgressBarIndeterminate.this.getWidth();
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localView, "x", arrayOfFloat);
        localObjectAnimator.setDuration(1200L);
        localObjectAnimator.addListener(new Animator.AnimatorListener()
        {
          int cont = 1;
          int duration = 1200;
          int suma = 1;

          public void onAnimationCancel(Animator paramAnonymous2Animator)
          {
          }

          public void onAnimationEnd(Animator paramAnonymous2Animator)
          {
            ViewHelper.setX(ProgressBarIndeterminate.this.progressView, -ProgressBarIndeterminate.this.progressView.getWidth() / 2);
            this.cont += this.suma;
            View localView = ProgressBarIndeterminate.this.progressView;
            float[] arrayOfFloat = new float[1];
            arrayOfFloat[0] = ProgressBarIndeterminate.this.getWidth();
            ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localView, "x", arrayOfFloat);
            localObjectAnimator.setDuration(this.duration / this.cont);
            localObjectAnimator.addListener(this);
            localObjectAnimator.start();
            if ((this.cont == 3) || (this.cont == 1))
              this.suma = (-1 * this.suma);
          }

          public void onAnimationRepeat(Animator paramAnonymous2Animator)
          {
          }

          public void onAnimationStart(Animator paramAnonymous2Animator)
          {
          }
        });
        localObjectAnimator.start();
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ProgressBarIndeterminate
 * JD-Core Version:    0.6.2
 */