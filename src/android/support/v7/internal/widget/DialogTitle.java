package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R.styleable;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class DialogTitle extends TextView
{
  public DialogTitle(Context paramContext)
  {
    super(paramContext);
  }

  public DialogTitle(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public DialogTitle(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    Layout localLayout = getLayout();
    if (localLayout != null)
    {
      int i = localLayout.getLineCount();
      if ((i > 0) && (localLayout.getEllipsisCount(i - 1) > 0))
      {
        setSingleLine(false);
        setMaxLines(2);
        TypedArray localTypedArray = getContext().obtainStyledAttributes(null, R.styleable.TextAppearance, 16842817, 16973892);
        int j = localTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        if (j != 0)
          setTextSize(0, j);
        localTypedArray.recycle();
        super.onMeasure(paramInt1, paramInt2);
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.DialogTitle
 * JD-Core Version:    0.6.2
 */