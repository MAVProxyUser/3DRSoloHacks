package com.nineoldandroids.view.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class AnimatorProxy extends Animation
{
  public static final boolean NEEDS_PROXY;
  private static final WeakHashMap<View, AnimatorProxy> PROXIES;
  private final RectF mAfter = new RectF();
  private float mAlpha = 1.0F;
  private final RectF mBefore = new RectF();
  private final Camera mCamera = new Camera();
  private boolean mHasPivot;
  private float mPivotX;
  private float mPivotY;
  private float mRotationX;
  private float mRotationY;
  private float mRotationZ;
  private float mScaleX = 1.0F;
  private float mScaleY = 1.0F;
  private final Matrix mTempMatrix = new Matrix();
  private float mTranslationX;
  private float mTranslationY;
  private final WeakReference<View> mView;

  static
  {
    if (Integer.valueOf(Build.VERSION.SDK).intValue() < 11);
    for (boolean bool = true; ; bool = false)
    {
      NEEDS_PROXY = bool;
      PROXIES = new WeakHashMap();
      return;
    }
  }

  private AnimatorProxy(View paramView)
  {
    setDuration(0L);
    setFillAfter(true);
    paramView.setAnimation(this);
    this.mView = new WeakReference(paramView);
  }

  private void computeRect(RectF paramRectF, View paramView)
  {
    paramRectF.set(0.0F, 0.0F, paramView.getWidth(), paramView.getHeight());
    Matrix localMatrix = this.mTempMatrix;
    localMatrix.reset();
    transformMatrix(localMatrix, paramView);
    this.mTempMatrix.mapRect(paramRectF);
    paramRectF.offset(paramView.getLeft(), paramView.getTop());
    if (paramRectF.right < paramRectF.left)
    {
      float f2 = paramRectF.right;
      paramRectF.right = paramRectF.left;
      paramRectF.left = f2;
    }
    if (paramRectF.bottom < paramRectF.top)
    {
      float f1 = paramRectF.top;
      paramRectF.top = paramRectF.bottom;
      paramRectF.bottom = f1;
    }
  }

  private void invalidateAfterUpdate()
  {
    View localView = (View)this.mView.get();
    if ((localView == null) || (localView.getParent() == null))
      return;
    RectF localRectF = this.mAfter;
    computeRect(localRectF, localView);
    localRectF.union(this.mBefore);
    ((View)localView.getParent()).invalidate((int)Math.floor(localRectF.left), (int)Math.floor(localRectF.top), (int)Math.ceil(localRectF.right), (int)Math.ceil(localRectF.bottom));
  }

  private void prepareForUpdate()
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      computeRect(this.mBefore, localView);
  }

  private void transformMatrix(Matrix paramMatrix, View paramView)
  {
    float f1 = paramView.getWidth();
    float f2 = paramView.getHeight();
    boolean bool = this.mHasPivot;
    float f3;
    if (bool)
    {
      f3 = this.mPivotX;
      if (!bool)
        break label233;
    }
    label233: for (float f4 = this.mPivotY; ; f4 = f2 / 2.0F)
    {
      float f5 = this.mRotationX;
      float f6 = this.mRotationY;
      float f7 = this.mRotationZ;
      if ((f5 != 0.0F) || (f6 != 0.0F) || (f7 != 0.0F))
      {
        Camera localCamera = this.mCamera;
        localCamera.save();
        localCamera.rotateX(f5);
        localCamera.rotateY(f6);
        localCamera.rotateZ(-f7);
        localCamera.getMatrix(paramMatrix);
        localCamera.restore();
        paramMatrix.preTranslate(-f3, -f4);
        paramMatrix.postTranslate(f3, f4);
      }
      float f8 = this.mScaleX;
      float f9 = this.mScaleY;
      if ((f8 != 1.0F) || (f9 != 1.0F))
      {
        paramMatrix.postScale(f8, f9);
        paramMatrix.postTranslate(-(f3 / f1) * (f8 * f1 - f1), -(f4 / f2) * (f9 * f2 - f2));
      }
      paramMatrix.postTranslate(this.mTranslationX, this.mTranslationY);
      return;
      f3 = f1 / 2.0F;
      break;
    }
  }

  public static AnimatorProxy wrap(View paramView)
  {
    AnimatorProxy localAnimatorProxy = (AnimatorProxy)PROXIES.get(paramView);
    if ((localAnimatorProxy == null) || (localAnimatorProxy != paramView.getAnimation()))
    {
      localAnimatorProxy = new AnimatorProxy(paramView);
      PROXIES.put(paramView, localAnimatorProxy);
    }
    return localAnimatorProxy;
  }

  protected void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
    {
      paramTransformation.setAlpha(this.mAlpha);
      transformMatrix(paramTransformation.getMatrix(), localView);
    }
  }

  public float getAlpha()
  {
    return this.mAlpha;
  }

  public float getPivotX()
  {
    return this.mPivotX;
  }

  public float getPivotY()
  {
    return this.mPivotY;
  }

  public float getRotation()
  {
    return this.mRotationZ;
  }

  public float getRotationX()
  {
    return this.mRotationX;
  }

  public float getRotationY()
  {
    return this.mRotationY;
  }

  public float getScaleX()
  {
    return this.mScaleX;
  }

  public float getScaleY()
  {
    return this.mScaleY;
  }

  public int getScrollX()
  {
    View localView = (View)this.mView.get();
    if (localView == null)
      return 0;
    return localView.getScrollX();
  }

  public int getScrollY()
  {
    View localView = (View)this.mView.get();
    if (localView == null)
      return 0;
    return localView.getScrollY();
  }

  public float getTranslationX()
  {
    return this.mTranslationX;
  }

  public float getTranslationY()
  {
    return this.mTranslationY;
  }

  public float getX()
  {
    View localView = (View)this.mView.get();
    if (localView == null)
      return 0.0F;
    return localView.getLeft() + this.mTranslationX;
  }

  public float getY()
  {
    View localView = (View)this.mView.get();
    if (localView == null)
      return 0.0F;
    return localView.getTop() + this.mTranslationY;
  }

  public void setAlpha(float paramFloat)
  {
    if (this.mAlpha != paramFloat)
    {
      this.mAlpha = paramFloat;
      View localView = (View)this.mView.get();
      if (localView != null)
        localView.invalidate();
    }
  }

  public void setPivotX(float paramFloat)
  {
    if ((!this.mHasPivot) || (this.mPivotX != paramFloat))
    {
      prepareForUpdate();
      this.mHasPivot = true;
      this.mPivotX = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setPivotY(float paramFloat)
  {
    if ((!this.mHasPivot) || (this.mPivotY != paramFloat))
    {
      prepareForUpdate();
      this.mHasPivot = true;
      this.mPivotY = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setRotation(float paramFloat)
  {
    if (this.mRotationZ != paramFloat)
    {
      prepareForUpdate();
      this.mRotationZ = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setRotationX(float paramFloat)
  {
    if (this.mRotationX != paramFloat)
    {
      prepareForUpdate();
      this.mRotationX = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setRotationY(float paramFloat)
  {
    if (this.mRotationY != paramFloat)
    {
      prepareForUpdate();
      this.mRotationY = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setScaleX(float paramFloat)
  {
    if (this.mScaleX != paramFloat)
    {
      prepareForUpdate();
      this.mScaleX = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setScaleY(float paramFloat)
  {
    if (this.mScaleY != paramFloat)
    {
      prepareForUpdate();
      this.mScaleY = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setScrollX(int paramInt)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.scrollTo(paramInt, localView.getScrollY());
  }

  public void setScrollY(int paramInt)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.scrollTo(localView.getScrollX(), paramInt);
  }

  public void setTranslationX(float paramFloat)
  {
    if (this.mTranslationX != paramFloat)
    {
      prepareForUpdate();
      this.mTranslationX = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setTranslationY(float paramFloat)
  {
    if (this.mTranslationY != paramFloat)
    {
      prepareForUpdate();
      this.mTranslationY = paramFloat;
      invalidateAfterUpdate();
    }
  }

  public void setX(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      setTranslationX(paramFloat - localView.getLeft());
  }

  public void setY(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      setTranslationY(paramFloat - localView.getTop());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.view.animation.AnimatorProxy
 * JD-Core Version:    0.6.2
 */