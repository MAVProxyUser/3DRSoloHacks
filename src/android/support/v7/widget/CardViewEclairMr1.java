package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

class CardViewEclairMr1
  implements CardViewImpl
{
  final RectF sCornerRect = new RectF();

  private RoundRectDrawableWithShadow getShadowBackground(CardViewDelegate paramCardViewDelegate)
  {
    return (RoundRectDrawableWithShadow)paramCardViewDelegate.getBackground();
  }

  RoundRectDrawableWithShadow createBackground(Context paramContext, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return new RoundRectDrawableWithShadow(paramContext.getResources(), paramInt, paramFloat1, paramFloat2, paramFloat3);
  }

  public float getElevation(CardViewDelegate paramCardViewDelegate)
  {
    return getShadowBackground(paramCardViewDelegate).getShadowSize();
  }

  public float getMaxElevation(CardViewDelegate paramCardViewDelegate)
  {
    return getShadowBackground(paramCardViewDelegate).getMaxShadowSize();
  }

  public float getMinHeight(CardViewDelegate paramCardViewDelegate)
  {
    return getShadowBackground(paramCardViewDelegate).getMinHeight();
  }

  public float getMinWidth(CardViewDelegate paramCardViewDelegate)
  {
    return getShadowBackground(paramCardViewDelegate).getMinWidth();
  }

  public float getRadius(CardViewDelegate paramCardViewDelegate)
  {
    return getShadowBackground(paramCardViewDelegate).getCornerRadius();
  }

  public void initStatic()
  {
    RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper()
    {
      public void drawRoundRect(Canvas paramAnonymousCanvas, RectF paramAnonymousRectF, float paramAnonymousFloat, Paint paramAnonymousPaint)
      {
        float f1 = paramAnonymousFloat * 2.0F;
        float f2 = paramAnonymousRectF.width() - f1 - 1.0F;
        float f3 = paramAnonymousRectF.height() - f1 - 1.0F;
        if (paramAnonymousFloat >= 1.0F)
        {
          paramAnonymousFloat += 0.5F;
          CardViewEclairMr1.this.sCornerRect.set(-paramAnonymousFloat, -paramAnonymousFloat, paramAnonymousFloat, paramAnonymousFloat);
          int i = paramAnonymousCanvas.save();
          paramAnonymousCanvas.translate(paramAnonymousFloat + paramAnonymousRectF.left, paramAnonymousFloat + paramAnonymousRectF.top);
          paramAnonymousCanvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.translate(f2, 0.0F);
          paramAnonymousCanvas.rotate(90.0F);
          paramAnonymousCanvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.translate(f3, 0.0F);
          paramAnonymousCanvas.rotate(90.0F);
          paramAnonymousCanvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.translate(f2, 0.0F);
          paramAnonymousCanvas.rotate(90.0F);
          paramAnonymousCanvas.drawArc(CardViewEclairMr1.this.sCornerRect, 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.restoreToCount(i);
          paramAnonymousCanvas.drawRect(paramAnonymousFloat + paramAnonymousRectF.left - 1.0F, paramAnonymousRectF.top, 1.0F + (paramAnonymousRectF.right - paramAnonymousFloat), paramAnonymousFloat + paramAnonymousRectF.top, paramAnonymousPaint);
          paramAnonymousCanvas.drawRect(paramAnonymousFloat + paramAnonymousRectF.left - 1.0F, 1.0F + (paramAnonymousRectF.bottom - paramAnonymousFloat), 1.0F + (paramAnonymousRectF.right - paramAnonymousFloat), paramAnonymousRectF.bottom, paramAnonymousPaint);
        }
        paramAnonymousCanvas.drawRect(paramAnonymousRectF.left, paramAnonymousRectF.top + Math.max(0.0F, paramAnonymousFloat - 1.0F), paramAnonymousRectF.right, 1.0F + (paramAnonymousRectF.bottom - paramAnonymousFloat), paramAnonymousPaint);
      }
    };
  }

  public void initialize(CardViewDelegate paramCardViewDelegate, Context paramContext, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    RoundRectDrawableWithShadow localRoundRectDrawableWithShadow = createBackground(paramContext, paramInt, paramFloat1, paramFloat2, paramFloat3);
    localRoundRectDrawableWithShadow.setAddPaddingForCorners(paramCardViewDelegate.getPreventCornerOverlap());
    paramCardViewDelegate.setBackgroundDrawable(localRoundRectDrawableWithShadow);
    updatePadding(paramCardViewDelegate);
  }

  public void onCompatPaddingChanged(CardViewDelegate paramCardViewDelegate)
  {
  }

  public void onPreventCornerOverlapChanged(CardViewDelegate paramCardViewDelegate)
  {
    getShadowBackground(paramCardViewDelegate).setAddPaddingForCorners(paramCardViewDelegate.getPreventCornerOverlap());
    updatePadding(paramCardViewDelegate);
  }

  public void setBackgroundColor(CardViewDelegate paramCardViewDelegate, int paramInt)
  {
    getShadowBackground(paramCardViewDelegate).setColor(paramInt);
  }

  public void setElevation(CardViewDelegate paramCardViewDelegate, float paramFloat)
  {
    getShadowBackground(paramCardViewDelegate).setShadowSize(paramFloat);
  }

  public void setMaxElevation(CardViewDelegate paramCardViewDelegate, float paramFloat)
  {
    getShadowBackground(paramCardViewDelegate).setMaxShadowSize(paramFloat);
    updatePadding(paramCardViewDelegate);
  }

  public void setRadius(CardViewDelegate paramCardViewDelegate, float paramFloat)
  {
    getShadowBackground(paramCardViewDelegate).setCornerRadius(paramFloat);
    updatePadding(paramCardViewDelegate);
  }

  public void updatePadding(CardViewDelegate paramCardViewDelegate)
  {
    Rect localRect = new Rect();
    getShadowBackground(paramCardViewDelegate).getMaxShadowAndCornerPadding(localRect);
    ((View)paramCardViewDelegate).setMinimumHeight((int)Math.ceil(getMinHeight(paramCardViewDelegate)));
    ((View)paramCardViewDelegate).setMinimumWidth((int)Math.ceil(getMinWidth(paramCardViewDelegate)));
    paramCardViewDelegate.setShadowPadding(localRect.left, localRect.top, localRect.right, localRect.bottom);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.CardViewEclairMr1
 * JD-Core Version:    0.6.2
 */