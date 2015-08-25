package me.grantland.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;

public class AutofitHelper
{
  private static final int DEFAULT_MIN_TEXT_SIZE = 8;
  private static final float DEFAULT_PRECISION = 0.5F;
  private static final boolean SPEW = false;
  private static final String TAG = "AutoFitTextHelper";
  private boolean mEnabled;
  private boolean mIsAutofitting;
  private ArrayList<OnTextSizeChangeListener> mListeners;
  private int mMaxLines;
  private float mMaxTextSize;
  private float mMinTextSize;
  private View.OnLayoutChangeListener mOnLayoutChangeListener = new AutofitOnLayoutChangeListener(null);
  private TextPaint mPaint;
  private float mPrecision;
  private float mTextSize;
  private TextView mTextView;
  private TextWatcher mTextWatcher = new AutofitTextWatcher(null);

  private AutofitHelper(TextView paramTextView)
  {
    float f = paramTextView.getContext().getResources().getDisplayMetrics().scaledDensity;
    this.mTextView = paramTextView;
    this.mPaint = new TextPaint();
    setRawTextSize(paramTextView.getTextSize());
    this.mMaxLines = getMaxLines(paramTextView);
    this.mMinTextSize = (8.0F * f);
    this.mMaxTextSize = this.mTextSize;
    this.mPrecision = 0.5F;
  }

  private void autofit()
  {
    float f1 = this.mTextView.getTextSize();
    this.mIsAutofitting = true;
    autofit(this.mTextView, this.mPaint, this.mMinTextSize, this.mMaxTextSize, this.mMaxLines, this.mPrecision);
    this.mIsAutofitting = false;
    float f2 = this.mTextView.getTextSize();
    if (f2 != f1)
      sendTextSizeChange(f2, f1);
  }

  private static void autofit(TextView paramTextView, TextPaint paramTextPaint, float paramFloat1, float paramFloat2, int paramInt, float paramFloat3)
  {
    if ((paramInt <= 0) || (paramInt == 2147483647));
    int i;
    do
    {
      return;
      i = paramTextView.getWidth() - paramTextView.getPaddingLeft() - paramTextView.getPaddingRight();
    }
    while (i <= 0);
    CharSequence localCharSequence = paramTextView.getText();
    TransformationMethod localTransformationMethod = paramTextView.getTransformationMethod();
    if (localTransformationMethod != null)
      localCharSequence = localTransformationMethod.getTransformation(localCharSequence, paramTextView);
    Context localContext = paramTextView.getContext();
    Resources localResources = Resources.getSystem();
    float f1 = paramFloat2;
    float f2 = f1;
    if (localContext != null)
      localResources = localContext.getResources();
    DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
    paramTextPaint.set(paramTextView.getPaint());
    paramTextPaint.setTextSize(f1);
    if (((paramInt == 1) && (paramTextPaint.measureText(localCharSequence, 0, localCharSequence.length()) > i)) || (getLineCount(localCharSequence, paramTextPaint, f1, i, localDisplayMetrics) > paramInt))
      f1 = getAutofitTextSize(localCharSequence, paramTextPaint, i, paramInt, 0.0F, f2, paramFloat3, localDisplayMetrics);
    if (f1 < paramFloat1)
      f1 = paramFloat1;
    paramTextView.setTextSize(0, f1);
  }

  public static AutofitHelper create(TextView paramTextView)
  {
    return create(paramTextView, null, 0);
  }

  public static AutofitHelper create(TextView paramTextView, AttributeSet paramAttributeSet)
  {
    return create(paramTextView, paramAttributeSet, 0);
  }

  public static AutofitHelper create(TextView paramTextView, AttributeSet paramAttributeSet, int paramInt)
  {
    AutofitHelper localAutofitHelper = new AutofitHelper(paramTextView);
    boolean bool = true;
    if (paramAttributeSet != null)
    {
      Context localContext = paramTextView.getContext();
      int i = (int)localAutofitHelper.getMinTextSize();
      float f1 = localAutofitHelper.getPrecision();
      TypedArray localTypedArray = localContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AutofitTextView, paramInt, 0);
      bool = localTypedArray.getBoolean(R.styleable.AutofitTextView_sizeToFit, bool);
      int j = localTypedArray.getDimensionPixelSize(R.styleable.AutofitTextView_minTextSize, i);
      float f2 = localTypedArray.getFloat(R.styleable.AutofitTextView_precision, f1);
      localTypedArray.recycle();
      localAutofitHelper.setMinTextSize(0, j).setPrecision(f2);
    }
    localAutofitHelper.setEnabled(bool);
    return localAutofitHelper;
  }

  private static float getAutofitTextSize(CharSequence paramCharSequence, TextPaint paramTextPaint, float paramFloat1, int paramInt, float paramFloat2, float paramFloat3, float paramFloat4, DisplayMetrics paramDisplayMetrics)
  {
    float f1 = (paramFloat2 + paramFloat3) / 2.0F;
    int i = 1;
    paramTextPaint.setTextSize(TypedValue.applyDimension(0, f1, paramDisplayMetrics));
    StaticLayout localStaticLayout = null;
    if (paramInt != 1)
    {
      localStaticLayout = new StaticLayout(paramCharSequence, paramTextPaint, (int)paramFloat1, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
      i = localStaticLayout.getLineCount();
    }
    if (i > paramInt)
    {
      if (paramFloat3 - paramFloat2 < paramFloat4)
        return paramFloat2;
      return getAutofitTextSize(paramCharSequence, paramTextPaint, paramFloat1, paramInt, paramFloat2, f1, paramFloat4, paramDisplayMetrics);
    }
    if (i < paramInt)
      return getAutofitTextSize(paramCharSequence, paramTextPaint, paramFloat1, paramInt, f1, paramFloat3, paramFloat4, paramDisplayMetrics);
    float f2 = 0.0F;
    if (paramInt == 1)
      f2 = paramTextPaint.measureText(paramCharSequence, 0, paramCharSequence.length());
    while (paramFloat3 - paramFloat2 >= paramFloat4)
    {
      if (f2 <= paramFloat1)
        break label210;
      return getAutofitTextSize(paramCharSequence, paramTextPaint, paramFloat1, paramInt, paramFloat2, f1, paramFloat4, paramDisplayMetrics);
      for (int j = 0; j < i; j++)
        if (localStaticLayout.getLineWidth(j) > f2)
          f2 = localStaticLayout.getLineWidth(j);
    }
    label210: if (f2 < paramFloat1)
      return getAutofitTextSize(paramCharSequence, paramTextPaint, paramFloat1, paramInt, f1, paramFloat3, paramFloat4, paramDisplayMetrics);
    return f1;
  }

  private static int getLineCount(CharSequence paramCharSequence, TextPaint paramTextPaint, float paramFloat1, float paramFloat2, DisplayMetrics paramDisplayMetrics)
  {
    paramTextPaint.setTextSize(TypedValue.applyDimension(0, paramFloat1, paramDisplayMetrics));
    return new StaticLayout(paramCharSequence, paramTextPaint, (int)paramFloat2, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true).getLineCount();
  }

  private static int getMaxLines(TextView paramTextView)
  {
    int i = -1;
    TransformationMethod localTransformationMethod = paramTextView.getTransformationMethod();
    if ((localTransformationMethod != null) && ((localTransformationMethod instanceof SingleLineTransformationMethod)))
      i = 1;
    while (Build.VERSION.SDK_INT < 16)
      return i;
    return paramTextView.getMaxLines();
  }

  private void sendTextSizeChange(float paramFloat1, float paramFloat2)
  {
    if (this.mListeners == null);
    while (true)
    {
      return;
      Iterator localIterator = this.mListeners.iterator();
      while (localIterator.hasNext())
        ((OnTextSizeChangeListener)localIterator.next()).onTextSizeChange(paramFloat1, paramFloat2);
    }
  }

  private void setRawMaxTextSize(float paramFloat)
  {
    if (paramFloat != this.mMaxTextSize)
    {
      this.mMaxTextSize = paramFloat;
      autofit();
    }
  }

  private void setRawMinTextSize(float paramFloat)
  {
    if (paramFloat != this.mMinTextSize)
    {
      this.mMinTextSize = paramFloat;
      autofit();
    }
  }

  private void setRawTextSize(float paramFloat)
  {
    if (this.mTextSize != paramFloat)
      this.mTextSize = paramFloat;
  }

  public AutofitHelper addOnTextSizeChangeListener(OnTextSizeChangeListener paramOnTextSizeChangeListener)
  {
    if (this.mListeners == null)
      this.mListeners = new ArrayList();
    this.mListeners.add(paramOnTextSizeChangeListener);
    return this;
  }

  public int getMaxLines()
  {
    return this.mMaxLines;
  }

  public float getMaxTextSize()
  {
    return this.mMaxTextSize;
  }

  public float getMinTextSize()
  {
    return this.mMinTextSize;
  }

  public float getPrecision()
  {
    return this.mPrecision;
  }

  public float getTextSize()
  {
    return this.mTextSize;
  }

  public boolean isEnabled()
  {
    return this.mEnabled;
  }

  public AutofitHelper removeOnTextSizeChangeListener(OnTextSizeChangeListener paramOnTextSizeChangeListener)
  {
    if (this.mListeners != null)
      this.mListeners.remove(paramOnTextSizeChangeListener);
    return this;
  }

  public AutofitHelper setEnabled(boolean paramBoolean)
  {
    if (this.mEnabled != paramBoolean)
    {
      this.mEnabled = paramBoolean;
      if (paramBoolean)
      {
        this.mTextView.addTextChangedListener(this.mTextWatcher);
        this.mTextView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        autofit();
      }
    }
    else
    {
      return this;
    }
    this.mTextView.removeTextChangedListener(this.mTextWatcher);
    this.mTextView.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
    this.mTextView.setTextSize(0, this.mTextSize);
    return this;
  }

  public AutofitHelper setMaxLines(int paramInt)
  {
    if (this.mMaxLines != paramInt)
    {
      this.mMaxLines = paramInt;
      autofit();
    }
    return this;
  }

  public AutofitHelper setMaxTextSize(float paramFloat)
  {
    return setMaxTextSize(2, paramFloat);
  }

  public AutofitHelper setMaxTextSize(int paramInt, float paramFloat)
  {
    Context localContext = this.mTextView.getContext();
    Resources localResources = Resources.getSystem();
    if (localContext != null)
      localResources = localContext.getResources();
    setRawMaxTextSize(TypedValue.applyDimension(paramInt, paramFloat, localResources.getDisplayMetrics()));
    return this;
  }

  public AutofitHelper setMinTextSize(float paramFloat)
  {
    return setMinTextSize(2, paramFloat);
  }

  public AutofitHelper setMinTextSize(int paramInt, float paramFloat)
  {
    Context localContext = this.mTextView.getContext();
    Resources localResources = Resources.getSystem();
    if (localContext != null)
      localResources = localContext.getResources();
    setRawMinTextSize(TypedValue.applyDimension(paramInt, paramFloat, localResources.getDisplayMetrics()));
    return this;
  }

  public AutofitHelper setPrecision(float paramFloat)
  {
    if (this.mPrecision != paramFloat)
    {
      this.mPrecision = paramFloat;
      autofit();
    }
    return this;
  }

  public void setTextSize(float paramFloat)
  {
    setTextSize(2, paramFloat);
  }

  public void setTextSize(int paramInt, float paramFloat)
  {
    if (this.mIsAutofitting)
      return;
    Context localContext = this.mTextView.getContext();
    Resources localResources = Resources.getSystem();
    if (localContext != null)
      localResources = localContext.getResources();
    setRawTextSize(TypedValue.applyDimension(paramInt, paramFloat, localResources.getDisplayMetrics()));
  }

  private class AutofitOnLayoutChangeListener
    implements View.OnLayoutChangeListener
  {
    private AutofitOnLayoutChangeListener()
    {
    }

    public void onLayoutChange(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
    {
      AutofitHelper.this.autofit();
    }
  }

  private class AutofitTextWatcher
    implements TextWatcher
  {
    private AutofitTextWatcher()
    {
    }

    public void afterTextChanged(Editable paramEditable)
    {
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      AutofitHelper.this.autofit();
    }
  }

  public static abstract interface OnTextSizeChangeListener
  {
    public abstract void onTextSizeChange(float paramFloat1, float paramFloat2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     me.grantland.widget.AutofitHelper
 * JD-Core Version:    0.6.2
 */