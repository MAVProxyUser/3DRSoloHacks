package android.support.v4.animation;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

class DonutAnimatorCompatProvider
  implements AnimatorProvider
{
  public ValueAnimatorCompat emptyValueAnimator()
  {
    return new DonutFloatValueAnimator();
  }

  private static class DonutFloatValueAnimator
    implements ValueAnimatorCompat
  {
    private long mDuration = 200L;
    private boolean mEnded = false;
    private float mFraction = 0.0F;
    List<AnimatorListenerCompat> mListeners = new ArrayList();
    private Runnable mLoopRunnable = new Runnable()
    {
      public void run()
      {
        float f = 1.0F * (float)(DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.getTime() - DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.mStartTime) / (float)DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.mDuration;
        if ((f > 1.0F) || (DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.mTarget.getParent() == null))
          f = 1.0F;
        DonutAnimatorCompatProvider.DonutFloatValueAnimator.access$302(DonutAnimatorCompatProvider.DonutFloatValueAnimator.this, f);
        DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.notifyUpdateListeners();
        if (DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.mFraction >= 1.0F)
        {
          DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.dispatchEnd();
          return;
        }
        DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.mTarget.postDelayed(DonutAnimatorCompatProvider.DonutFloatValueAnimator.this.mLoopRunnable, 16L);
      }
    };
    private long mStartTime;
    private boolean mStarted = false;
    View mTarget;
    List<AnimatorUpdateListenerCompat> mUpdateListeners = new ArrayList();

    private void dispatchCancel()
    {
      for (int i = -1 + this.mListeners.size(); i >= 0; i--)
        ((AnimatorListenerCompat)this.mListeners.get(i)).onAnimationCancel(this);
    }

    private void dispatchEnd()
    {
      for (int i = -1 + this.mListeners.size(); i >= 0; i--)
        ((AnimatorListenerCompat)this.mListeners.get(i)).onAnimationEnd(this);
    }

    private void dispatchStart()
    {
      for (int i = -1 + this.mListeners.size(); i >= 0; i--)
        ((AnimatorListenerCompat)this.mListeners.get(i)).onAnimationStart(this);
    }

    private long getTime()
    {
      return this.mTarget.getDrawingTime();
    }

    private void notifyUpdateListeners()
    {
      for (int i = -1 + this.mUpdateListeners.size(); i >= 0; i--)
        ((AnimatorUpdateListenerCompat)this.mUpdateListeners.get(i)).onAnimationUpdate(this);
    }

    public void addListener(AnimatorListenerCompat paramAnimatorListenerCompat)
    {
      this.mListeners.add(paramAnimatorListenerCompat);
    }

    public void addUpdateListener(AnimatorUpdateListenerCompat paramAnimatorUpdateListenerCompat)
    {
      this.mUpdateListeners.add(paramAnimatorUpdateListenerCompat);
    }

    public void cancel()
    {
      if (this.mEnded)
        return;
      this.mEnded = true;
      if (this.mStarted)
        dispatchCancel();
      dispatchEnd();
    }

    public float getAnimatedFraction()
    {
      return this.mFraction;
    }

    public void setDuration(long paramLong)
    {
      if (!this.mStarted)
        this.mDuration = paramLong;
    }

    public void setTarget(View paramView)
    {
      this.mTarget = paramView;
    }

    public void start()
    {
      if (this.mStarted)
        return;
      this.mStarted = true;
      dispatchStart();
      this.mFraction = 0.0F;
      this.mStartTime = getTime();
      this.mTarget.postDelayed(this.mLoopRunnable, 16L);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.animation.DonutAnimatorCompatProvider
 * JD-Core Version:    0.6.2
 */