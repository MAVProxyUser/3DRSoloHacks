package android.support.v4.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

class HoneycombMr1AnimatorCompatProvider
  implements AnimatorProvider
{
  public ValueAnimatorCompat emptyValueAnimator()
  {
    return new HoneycombValueAnimatorCompat(ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F }));
  }

  static class AnimatorListenerCompatWrapper
    implements Animator.AnimatorListener
  {
    final ValueAnimatorCompat mValueAnimatorCompat;
    final AnimatorListenerCompat mWrapped;

    public AnimatorListenerCompatWrapper(AnimatorListenerCompat paramAnimatorListenerCompat, ValueAnimatorCompat paramValueAnimatorCompat)
    {
      this.mWrapped = paramAnimatorListenerCompat;
      this.mValueAnimatorCompat = paramValueAnimatorCompat;
    }

    public void onAnimationCancel(Animator paramAnimator)
    {
      this.mWrapped.onAnimationCancel(this.mValueAnimatorCompat);
    }

    public void onAnimationEnd(Animator paramAnimator)
    {
      this.mWrapped.onAnimationEnd(this.mValueAnimatorCompat);
    }

    public void onAnimationRepeat(Animator paramAnimator)
    {
      this.mWrapped.onAnimationRepeat(this.mValueAnimatorCompat);
    }

    public void onAnimationStart(Animator paramAnimator)
    {
      this.mWrapped.onAnimationStart(this.mValueAnimatorCompat);
    }
  }

  static class HoneycombValueAnimatorCompat
    implements ValueAnimatorCompat
  {
    final Animator mWrapped;

    public HoneycombValueAnimatorCompat(Animator paramAnimator)
    {
      this.mWrapped = paramAnimator;
    }

    public void addListener(AnimatorListenerCompat paramAnimatorListenerCompat)
    {
      this.mWrapped.addListener(new HoneycombMr1AnimatorCompatProvider.AnimatorListenerCompatWrapper(paramAnimatorListenerCompat, this));
    }

    public void addUpdateListener(final AnimatorUpdateListenerCompat paramAnimatorUpdateListenerCompat)
    {
      if ((this.mWrapped instanceof ValueAnimator))
        ((ValueAnimator)this.mWrapped).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
          public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
          {
            paramAnimatorUpdateListenerCompat.onAnimationUpdate(HoneycombMr1AnimatorCompatProvider.HoneycombValueAnimatorCompat.this);
          }
        });
    }

    public void cancel()
    {
      this.mWrapped.cancel();
    }

    public float getAnimatedFraction()
    {
      return ((ValueAnimator)this.mWrapped).getAnimatedFraction();
    }

    public void setDuration(long paramLong)
    {
      this.mWrapped.setDuration(paramLong);
    }

    public void setTarget(View paramView)
    {
      this.mWrapped.setTarget(paramView);
    }

    public void start()
    {
      this.mWrapped.start();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.animation.HoneycombMr1AnimatorCompatProvider
 * JD-Core Version:    0.6.2
 */