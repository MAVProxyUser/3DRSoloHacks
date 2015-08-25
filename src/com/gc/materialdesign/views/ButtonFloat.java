package com.gc.materialdesign.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.gc.materialdesign.R.drawable;
import com.gc.materialdesign.utils.Utils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ButtonFloat extends Button
{
  Drawable drawableIcon;
  Integer height;
  float hidePosition;
  ImageView icon;
  public boolean isShow = false;
  float showPosition;
  int sizeIcon = 24;
  int sizeRadius = 28;
  Integer width;

  public ButtonFloat(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBackgroundResource(R.drawable.background_button_float);
    this.sizeRadius = 28;
    setDefaultProperties();
    this.icon = new ImageView(paramContext);
    this.icon.setAdjustViewBounds(true);
    this.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
    if (this.drawableIcon != null)
      this.icon.setImageDrawable(this.drawableIcon);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(Utils.dpToPx(this.sizeIcon, getResources()), Utils.dpToPx(this.sizeIcon, getResources()));
    localLayoutParams.addRule(13, -1);
    this.icon.setLayoutParams(localLayoutParams);
    addView(this.icon);
  }

  public Bitmap cropCircle(Bitmap paramBitmap)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    localPaint.setAntiAlias(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-12434878);
    localCanvas.drawCircle(paramBitmap.getWidth() / 2, paramBitmap.getHeight() / 2, paramBitmap.getWidth() / 2, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    return localBitmap;
  }

  public Drawable getDrawableIcon()
  {
    return this.drawableIcon;
  }

  public ImageView getIcon()
  {
    return this.icon;
  }

  public TextView getTextView()
  {
    return null;
  }

  public void hide()
  {
    float[] arrayOfFloat = new float[1];
    arrayOfFloat[0] = this.hidePosition;
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, "y", arrayOfFloat);
    localObjectAnimator.setInterpolator(new BounceInterpolator());
    localObjectAnimator.setDuration(1500L);
    localObjectAnimator.start();
    this.isShow = false;
  }

  public boolean isShow()
  {
    return this.isShow;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.x != -1.0F)
    {
      Rect localRect1 = new Rect(0, 0, getWidth(), getHeight());
      Rect localRect2 = new Rect(Utils.dpToPx(1.0F, getResources()), Utils.dpToPx(2.0F, getResources()), getWidth() - Utils.dpToPx(1.0F, getResources()), getHeight() - Utils.dpToPx(2.0F, getResources()));
      paramCanvas.drawBitmap(cropCircle(makeCircle()), localRect1, localRect2, null);
      invalidate();
    }
  }

  protected void setAttributes(AttributeSet paramAttributeSet)
  {
    int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
    if (i != -1)
    {
      setBackgroundColor(getResources().getColor(i));
      int j = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "rippleColor", -1);
      if (j == -1)
        break label149;
      setRippleColor(getResources().getColor(j));
    }
    while (true)
    {
      int m = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "iconDrawable", -1);
      if (m != -1)
        this.drawableIcon = getResources().getDrawable(m);
      post(new Runnable()
      {
        public void run()
        {
          ButtonFloat.this.showPosition = (ViewHelper.getY(ButtonFloat.this) - Utils.dpToPx(24.0F, ButtonFloat.this.getResources()));
          ButtonFloat.this.hidePosition = (ViewHelper.getY(ButtonFloat.this) + 3 * ButtonFloat.this.getHeight());
          if (this.val$animate)
          {
            ViewHelper.setY(ButtonFloat.this, ButtonFloat.this.hidePosition);
            ButtonFloat.this.show();
          }
        }
      });
      return;
      this.background = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
      if (this.background == -1)
        break;
      setBackgroundColor(this.background);
      break;
      label149: int k = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "rippleColor", -1);
      if (k != -1)
        setRippleColor(k);
      else
        setRippleColor(makePressColor());
    }
  }

  protected void setDefaultProperties()
  {
    this.rippleSpeed = Utils.dpToPx(2.0F, getResources());
    this.rippleSize = Utils.dpToPx(5.0F, getResources());
    setMinimumWidth(Utils.dpToPx(2 * this.sizeRadius, getResources()));
    setMinimumHeight(Utils.dpToPx(2 * this.sizeRadius, getResources()));
    this.background = R.drawable.background_button_float;
  }

  public void setDrawableIcon(Drawable paramDrawable)
  {
    this.drawableIcon = paramDrawable;
    try
    {
      this.icon.setBackground(paramDrawable);
      return;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      this.icon.setBackgroundDrawable(paramDrawable);
    }
  }

  public void setIcon(ImageView paramImageView)
  {
    this.icon = paramImageView;
  }

  public void setRippleColor(int paramInt)
  {
    this.rippleColor = Integer.valueOf(paramInt);
  }

  public void show()
  {
    float[] arrayOfFloat = new float[1];
    arrayOfFloat[0] = this.showPosition;
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, "y", arrayOfFloat);
    localObjectAnimator.setInterpolator(new BounceInterpolator());
    localObjectAnimator.setDuration(1500L);
    localObjectAnimator.start();
    this.isShow = true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.gc.materialdesign.views.ButtonFloat
 * JD-Core Version:    0.6.2
 */