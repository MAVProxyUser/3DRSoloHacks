package me.grantland.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutofitTextView extends TextView
  implements AutofitHelper.OnTextSizeChangeListener
{
  private AutofitHelper mHelper;

  public AutofitTextView(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public AutofitTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public AutofitTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this.mHelper = AutofitHelper.create(this, paramAttributeSet, paramInt).addOnTextSizeChangeListener(this);
  }

  public AutofitHelper getAutofitHelper()
  {
    return this.mHelper;
  }

  public float getMaxTextSize()
  {
    return this.mHelper.getMaxTextSize();
  }

  public float getMinTextSize()
  {
    return this.mHelper.getMinTextSize();
  }

  public float getPrecision()
  {
    return this.mHelper.getPrecision();
  }

  public boolean isSizeToFit()
  {
    return this.mHelper.isEnabled();
  }

  public void onTextSizeChange(float paramFloat1, float paramFloat2)
  {
  }

  public void setLines(int paramInt)
  {
    super.setLines(paramInt);
    if (this.mHelper != null)
      this.mHelper.setMaxLines(paramInt);
  }

  public void setMaxLines(int paramInt)
  {
    super.setMaxLines(paramInt);
    if (this.mHelper != null)
      this.mHelper.setMaxLines(paramInt);
  }

  public void setMaxTextSize(float paramFloat)
  {
    this.mHelper.setMaxTextSize(paramFloat);
  }

  public void setMaxTextSize(int paramInt, float paramFloat)
  {
    this.mHelper.setMaxTextSize(paramInt, paramFloat);
  }

  public void setMinTextSize(int paramInt)
  {
    this.mHelper.setMinTextSize(2, paramInt);
  }

  public void setMinTextSize(int paramInt, float paramFloat)
  {
    this.mHelper.setMinTextSize(paramInt, paramFloat);
  }

  public void setPrecision(float paramFloat)
  {
    this.mHelper.setPrecision(paramFloat);
  }

  public void setSizeToFit()
  {
    setSizeToFit(true);
  }

  public void setSizeToFit(boolean paramBoolean)
  {
    this.mHelper.setEnabled(paramBoolean);
  }

  public void setTextSize(int paramInt, float paramFloat)
  {
    super.setTextSize(paramInt, paramFloat);
    if (this.mHelper != null)
      this.mHelper.setTextSize(paramInt, paramFloat);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     me.grantland.widget.AutofitTextView
 * JD-Core Version:    0.6.2
 */