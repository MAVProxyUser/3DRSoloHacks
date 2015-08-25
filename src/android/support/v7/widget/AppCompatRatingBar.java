package android.support.v7.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.RatingBar;

public class AppCompatRatingBar extends RatingBar
{
  private static final int[] TINT_ATTRS = { 16843067, 16843068 };
  private Bitmap mSampleTile;

  public AppCompatRatingBar(Context paramContext)
  {
    this(paramContext, null);
  }

  public AppCompatRatingBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.ratingBarStyle);
  }

  public AppCompatRatingBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (TintManager.SHOULD_BE_USED)
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
      Drawable localDrawable1 = localTintTypedArray.getDrawableIfKnown(0);
      if (localDrawable1 != null)
        setIndeterminateDrawable(tileifyIndeterminate(localDrawable1));
      Drawable localDrawable2 = localTintTypedArray.getDrawableIfKnown(1);
      if (localDrawable2 != null)
        setProgressDrawable(tileify(localDrawable2, false));
      localTintTypedArray.recycle();
    }
  }

  private Shape getDrawableShape()
  {
    return new RoundRectShape(new float[] { 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F }, null, null);
  }

  private Drawable tileify(Drawable paramDrawable, boolean paramBoolean)
  {
    if ((paramDrawable instanceof DrawableWrapper))
    {
      Drawable localDrawable2 = ((DrawableWrapper)paramDrawable).getWrappedDrawable();
      if (localDrawable2 != null)
      {
        Drawable localDrawable3 = tileify(localDrawable2, paramBoolean);
        ((DrawableWrapper)paramDrawable).setWrappedDrawable(localDrawable3);
      }
    }
    do
    {
      Object localObject2 = paramDrawable;
      while (true)
      {
        return localObject2;
        if (!(paramDrawable instanceof LayerDrawable))
          break;
        LayerDrawable localLayerDrawable = (LayerDrawable)paramDrawable;
        int i = localLayerDrawable.getNumberOfLayers();
        Drawable[] arrayOfDrawable = new Drawable[i];
        int j = 0;
        if (j < i)
        {
          int m = localLayerDrawable.getId(j);
          Drawable localDrawable1 = localLayerDrawable.getDrawable(j);
          if ((m == 16908301) || (m == 16908303));
          for (boolean bool = true; ; bool = false)
          {
            arrayOfDrawable[j] = tileify(localDrawable1, bool);
            j++;
            break;
          }
        }
        localObject2 = new LayerDrawable(arrayOfDrawable);
        for (int k = 0; k < i; k++)
          ((LayerDrawable)localObject2).setId(k, localLayerDrawable.getId(k));
      }
    }
    while (!(paramDrawable instanceof BitmapDrawable));
    Bitmap localBitmap = ((BitmapDrawable)paramDrawable).getBitmap();
    if (this.mSampleTile == null)
      this.mSampleTile = localBitmap;
    Object localObject1 = new ShapeDrawable(getDrawableShape());
    BitmapShader localBitmapShader = new BitmapShader(localBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    ((ShapeDrawable)localObject1).getPaint().setShader(localBitmapShader);
    if (paramBoolean)
      localObject1 = new ClipDrawable((Drawable)localObject1, 3, 1);
    return localObject1;
  }

  private Drawable tileifyIndeterminate(Drawable paramDrawable)
  {
    if ((paramDrawable instanceof AnimationDrawable))
    {
      AnimationDrawable localAnimationDrawable1 = (AnimationDrawable)paramDrawable;
      int i = localAnimationDrawable1.getNumberOfFrames();
      AnimationDrawable localAnimationDrawable2 = new AnimationDrawable();
      localAnimationDrawable2.setOneShot(localAnimationDrawable1.isOneShot());
      for (int j = 0; j < i; j++)
      {
        Drawable localDrawable = tileify(localAnimationDrawable1.getFrame(j), true);
        localDrawable.setLevel(10000);
        localAnimationDrawable2.addFrame(localDrawable, localAnimationDrawable1.getDuration(j));
      }
      localAnimationDrawable2.setLevel(10000);
      paramDrawable = localAnimationDrawable2;
    }
    return paramDrawable;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    try
    {
      super.onMeasure(paramInt1, paramInt2);
      if (this.mSampleTile != null)
        setMeasuredDimension(ViewCompat.resolveSizeAndState(this.mSampleTile.getWidth() * getNumStars(), paramInt1, 0), getMeasuredHeight());
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatRatingBar
 * JD-Core Version:    0.6.2
 */