package com.getbase.floatingactionbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.ShapeDrawable.ShaderFactory;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FloatingActionButton extends ImageButton
{
  public static final int SIZE_MINI = 1;
  public static final int SIZE_NORMAL;
  private float mCircleSize;
  int mColorDisabled;
  int mColorNormal;
  int mColorPressed;
  private int mDrawableSize;
  private int mIcon;
  private Drawable mIconDrawable;
  private float mShadowOffset;
  private float mShadowRadius;
  private int mSize;
  boolean mStrokeVisible;
  String mTitle;

  public FloatingActionButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public FloatingActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public FloatingActionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private int adjustColorBrightness(int paramInt, float paramFloat)
  {
    float[] arrayOfFloat = new float[3];
    Color.colorToHSV(paramInt, arrayOfFloat);
    arrayOfFloat[2] = Math.min(paramFloat * arrayOfFloat[2], 1.0F);
    return Color.HSVToColor(Color.alpha(paramInt), arrayOfFloat);
  }

  private Drawable createCircleDrawable(int paramInt, float paramFloat)
  {
    int i = Color.alpha(paramInt);
    int j = opaque(paramInt);
    ShapeDrawable localShapeDrawable = new ShapeDrawable(new OvalShape());
    Paint localPaint = localShapeDrawable.getPaint();
    localPaint.setAntiAlias(true);
    localPaint.setColor(j);
    Drawable[] arrayOfDrawable = new Drawable[2];
    arrayOfDrawable[0] = localShapeDrawable;
    arrayOfDrawable[1] = createInnerStrokesDrawable(j, paramFloat);
    if ((i == 255) || (!this.mStrokeVisible));
    for (Object localObject = new LayerDrawable(arrayOfDrawable); ; localObject = new TranslucentLayerDrawable(i, arrayOfDrawable))
    {
      int k = (int)(paramFloat / 2.0F);
      ((LayerDrawable)localObject).setLayerInset(1, k, k, k, k);
      return localObject;
    }
  }

  private StateListDrawable createFillDrawable(float paramFloat)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    localStateListDrawable.addState(new int[] { -16842910 }, createCircleDrawable(this.mColorDisabled, paramFloat));
    localStateListDrawable.addState(new int[] { 16842919 }, createCircleDrawable(this.mColorPressed, paramFloat));
    localStateListDrawable.addState(new int[0], createCircleDrawable(this.mColorNormal, paramFloat));
    return localStateListDrawable;
  }

  private Drawable createInnerStrokesDrawable(final int paramInt, float paramFloat)
  {
    if (!this.mStrokeVisible)
      return new ColorDrawable(0);
    ShapeDrawable localShapeDrawable = new ShapeDrawable(new OvalShape());
    final int i = darkenColor(paramInt);
    final int j = halfTransparent(i);
    final int k = lightenColor(paramInt);
    final int m = halfTransparent(k);
    Paint localPaint = localShapeDrawable.getPaint();
    localPaint.setAntiAlias(true);
    localPaint.setStrokeWidth(paramFloat);
    localPaint.setStyle(Paint.Style.STROKE);
    localShapeDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory()
    {
      public Shader resize(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        float f1 = paramAnonymousInt1 / 2;
        float f2 = paramAnonymousInt1 / 2;
        float f3 = paramAnonymousInt2;
        int[] arrayOfInt = new int[5];
        arrayOfInt[0] = k;
        arrayOfInt[1] = m;
        arrayOfInt[2] = paramInt;
        arrayOfInt[3] = j;
        arrayOfInt[4] = i;
        return new LinearGradient(f1, 0.0F, f2, f3, arrayOfInt, new float[] { 0.0F, 0.2F, 0.5F, 0.8F, 1.0F }, Shader.TileMode.CLAMP);
      }
    });
    return localShapeDrawable;
  }

  private Drawable createOuterStrokeDrawable(float paramFloat)
  {
    ShapeDrawable localShapeDrawable = new ShapeDrawable(new OvalShape());
    Paint localPaint = localShapeDrawable.getPaint();
    localPaint.setAntiAlias(true);
    localPaint.setStrokeWidth(paramFloat);
    localPaint.setStyle(Paint.Style.STROKE);
    localPaint.setColor(-16777216);
    localPaint.setAlpha(opacityToAlpha(0.02F));
    return localShapeDrawable;
  }

  private int darkenColor(int paramInt)
  {
    return adjustColorBrightness(paramInt, 0.9F);
  }

  private int halfTransparent(int paramInt)
  {
    return Color.argb(Color.alpha(paramInt) / 2, Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt));
  }

  private int lightenColor(int paramInt)
  {
    return adjustColorBrightness(paramInt, 1.1F);
  }

  private int opacityToAlpha(float paramFloat)
  {
    return (int)(255.0F * paramFloat);
  }

  private int opaque(int paramInt)
  {
    return Color.rgb(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt));
  }

  @SuppressLint({"NewApi"})
  private void setBackgroundCompat(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      setBackground(paramDrawable);
      return;
    }
    setBackgroundDrawable(paramDrawable);
  }

  private void updateCircleSize()
  {
    if (this.mSize == 0);
    for (int i = R.dimen.fab_size_normal; ; i = R.dimen.fab_size_mini)
    {
      this.mCircleSize = getDimension(i);
      return;
    }
  }

  private void updateDrawableSize()
  {
    this.mDrawableSize = ((int)(this.mCircleSize + 2.0F * this.mShadowRadius));
  }

  int getColor(int paramInt)
  {
    return getResources().getColor(paramInt);
  }

  public int getColorDisabled()
  {
    return this.mColorDisabled;
  }

  public int getColorNormal()
  {
    return this.mColorNormal;
  }

  public int getColorPressed()
  {
    return this.mColorPressed;
  }

  float getDimension(int paramInt)
  {
    return getResources().getDimension(paramInt);
  }

  Drawable getIconDrawable()
  {
    if (this.mIconDrawable != null)
      return this.mIconDrawable;
    if (this.mIcon != 0)
      return getResources().getDrawable(this.mIcon);
    return new ColorDrawable(0);
  }

  TextView getLabelView()
  {
    return (TextView)getTag(R.id.fab_label);
  }

  public int getSize()
  {
    return this.mSize;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FloatingActionButton, 0, 0);
    this.mColorNormal = localTypedArray.getColor(R.styleable.FloatingActionButton_fab_colorNormal, getColor(17170451));
    this.mColorPressed = localTypedArray.getColor(R.styleable.FloatingActionButton_fab_colorPressed, getColor(17170450));
    this.mColorDisabled = localTypedArray.getColor(R.styleable.FloatingActionButton_fab_colorDisabled, getColor(17170432));
    this.mSize = localTypedArray.getInt(R.styleable.FloatingActionButton_fab_size, 0);
    this.mIcon = localTypedArray.getResourceId(R.styleable.FloatingActionButton_fab_icon, 0);
    this.mTitle = localTypedArray.getString(R.styleable.FloatingActionButton_fab_title);
    this.mStrokeVisible = localTypedArray.getBoolean(R.styleable.FloatingActionButton_fab_stroke_visible, true);
    localTypedArray.recycle();
    updateCircleSize();
    this.mShadowRadius = getDimension(R.dimen.fab_shadow_radius);
    this.mShadowOffset = getDimension(R.dimen.fab_shadow_offset);
    updateDrawableSize();
    updateBackground();
  }

  public boolean isStrokeVisible()
  {
    return this.mStrokeVisible;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(this.mDrawableSize, this.mDrawableSize);
  }

  public void setColorDisabled(int paramInt)
  {
    if (this.mColorDisabled != paramInt)
    {
      this.mColorDisabled = paramInt;
      updateBackground();
    }
  }

  public void setColorDisabledResId(int paramInt)
  {
    setColorDisabled(getColor(paramInt));
  }

  public void setColorNormal(int paramInt)
  {
    if (this.mColorNormal != paramInt)
    {
      this.mColorNormal = paramInt;
      updateBackground();
    }
  }

  public void setColorNormalResId(int paramInt)
  {
    setColorNormal(getColor(paramInt));
  }

  public void setColorPressed(int paramInt)
  {
    if (this.mColorPressed != paramInt)
    {
      this.mColorPressed = paramInt;
      updateBackground();
    }
  }

  public void setColorPressedResId(int paramInt)
  {
    setColorPressed(getColor(paramInt));
  }

  public void setIcon(int paramInt)
  {
    if (this.mIcon != paramInt)
    {
      this.mIcon = paramInt;
      this.mIconDrawable = null;
      updateBackground();
    }
  }

  public void setIconDrawable(@NonNull Drawable paramDrawable)
  {
    if (this.mIconDrawable != paramDrawable)
    {
      this.mIcon = 0;
      this.mIconDrawable = paramDrawable;
      updateBackground();
    }
  }

  public void setSize(int paramInt)
  {
    if ((paramInt != 1) && (paramInt != 0))
      throw new IllegalArgumentException("Use @FAB_SIZE constants only!");
    if (this.mSize != paramInt)
    {
      this.mSize = paramInt;
      updateCircleSize();
      updateDrawableSize();
      updateBackground();
    }
  }

  public void setStrokeVisible(boolean paramBoolean)
  {
    if (this.mStrokeVisible != paramBoolean)
    {
      this.mStrokeVisible = paramBoolean;
      updateBackground();
    }
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
    TextView localTextView = getLabelView();
    if (localTextView != null)
      localTextView.setText(paramString);
  }

  public void setVisibility(int paramInt)
  {
    TextView localTextView = getLabelView();
    if (localTextView != null)
      localTextView.setVisibility(paramInt);
    super.setVisibility(paramInt);
  }

  void updateBackground()
  {
    float f1 = getDimension(R.dimen.fab_stroke_width);
    float f2 = f1 / 2.0F;
    Drawable[] arrayOfDrawable = new Drawable[4];
    Resources localResources = getResources();
    if (this.mSize == 0);
    for (int i = R.drawable.fab_bg_normal; ; i = R.drawable.fab_bg_mini)
    {
      arrayOfDrawable[0] = localResources.getDrawable(i);
      arrayOfDrawable[1] = createFillDrawable(f1);
      arrayOfDrawable[2] = createOuterStrokeDrawable(f1);
      arrayOfDrawable[3] = getIconDrawable();
      LayerDrawable localLayerDrawable = new LayerDrawable(arrayOfDrawable);
      int j = (int)(this.mCircleSize - getDimension(R.dimen.fab_icon_size)) / 2;
      int k = (int)this.mShadowRadius;
      int m = (int)(this.mShadowRadius - this.mShadowOffset);
      int n = (int)(this.mShadowRadius + this.mShadowOffset);
      localLayerDrawable.setLayerInset(1, k, m, k, n);
      localLayerDrawable.setLayerInset(2, (int)(k - f2), (int)(m - f2), (int)(k - f2), (int)(n - f2));
      localLayerDrawable.setLayerInset(3, k + j, m + j, k + j, n + j);
      setBackgroundCompat(localLayerDrawable);
      return;
    }
  }

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L})
  public static @interface FAB_SIZE
  {
  }

  private static class TranslucentLayerDrawable extends LayerDrawable
  {
    private final int mAlpha;

    public TranslucentLayerDrawable(int paramInt, Drawable[] paramArrayOfDrawable)
    {
      super();
      this.mAlpha = paramInt;
    }

    public void draw(Canvas paramCanvas)
    {
      Rect localRect = getBounds();
      paramCanvas.saveLayerAlpha(localRect.left, localRect.top, localRect.right, localRect.bottom, this.mAlpha, 31);
      super.draw(paramCanvas);
      paramCanvas.restore();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.getbase.floatingactionbutton.FloatingActionButton
 * JD-Core Version:    0.6.2
 */