package com.o3dr.solo.android.widget.spinnerWheel;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.o3dr.solo.android.R.styleable;
import com.o3dr.solo.android.widget.spinnerWheel.adapters.AbstractWheelTextAdapter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.beyene.sius.unit.Unit;

public class EditWheelHorizontalView<T> extends FrameLayout
  implements OnWheelChangedListener, OnWheelClickedListener, OnWheelScrollListener
{
  private static final String TAG = EditWheelHorizontalView.class.getSimpleName();
  private EditText mNumberInputText;
  private final List<OnCardWheelScrollListener<T>> mScrollingListeners = new LinkedList();
  private WheelHorizontalView<T> mSpinnerWheel;
  private T scrollingStartValue;

  public EditWheelHorizontalView(Context paramContext)
  {
    this(paramContext, null);
  }

  public EditWheelHorizontalView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public EditWheelHorizontalView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize(paramContext, paramAttributeSet);
  }

  private T getValue(int paramInt)
  {
    return this.mSpinnerWheel.getViewAdapter().getItem(paramInt);
  }

  private void hideSoftInput()
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)getContext().getSystemService("input_method");
    if ((localInputMethodManager != null) && (localInputMethodManager.isActive(this.mNumberInputText)))
    {
      localInputMethodManager.hideSoftInputFromWindow(this.mNumberInputText.getWindowToken(), 0);
      this.mNumberInputText.setVisibility(4);
    }
  }

  private void initialize(final Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.EditWheelHorizontalView, 0, 0);
    try
    {
      View localView = LayoutInflater.from(paramContext).inflate(2130903084, this, false);
      addView(localView);
      this.mSpinnerWheel = ((WheelHorizontalView)localView.findViewById(2131493070));
      this.mSpinnerWheel.addChangingListener(this);
      this.mSpinnerWheel.addClickingListener(this);
      this.mSpinnerWheel.addScrollingListener(this);
      this.mNumberInputText = ((EditText)localView.findViewById(2131493071));
      this.mNumberInputText.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            EditWheelHorizontalView.this.mNumberInputText.selectAll();
            return;
          }
          EditWheelHorizontalView.this.hideSoftInput();
        }
      });
      this.mNumberInputText.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if ((paramAnonymousInt == 6) || (paramAnonymousInt == 0))
          {
            EditWheelHorizontalView.this.hideSoftInput();
            CharSequence localCharSequence = paramAnonymousTextView.getText();
            int i;
            if (localCharSequence != null)
            {
              AbstractWheelTextAdapter localAbstractWheelTextAdapter = EditWheelHorizontalView.this.mSpinnerWheel.getViewAdapter();
              i = localAbstractWheelTextAdapter.getItemIndex(localAbstractWheelTextAdapter.parseItemText(localCharSequence));
              if (i == -1)
                Toast.makeText(paramContext, "Entered value is outside of the allowed range.", 1).show();
            }
            else
            {
              return true;
            }
            EditWheelHorizontalView.this.setCurrentItemIndex(i, true);
            return true;
          }
          return false;
        }
      });
      return;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }

  private void setCurrentItemIndex(int paramInt, boolean paramBoolean)
  {
    this.mSpinnerWheel.setCurrentItem(paramInt, paramBoolean);
  }

  private void showSoftInput(T paramT)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)getContext().getSystemService("input_method");
    if (localInputMethodManager != null)
    {
      if (!(paramT instanceof Unit))
        break label70;
      this.mNumberInputText.setText(String.valueOf(((Unit)paramT).getValue()));
    }
    while (true)
    {
      this.mNumberInputText.setVisibility(0);
      this.mNumberInputText.requestFocus();
      localInputMethodManager.showSoftInput(this.mNumberInputText, 0);
      return;
      label70: this.mNumberInputText.setText(paramT.toString());
    }
  }

  public void addScrollListener(OnCardWheelScrollListener<T> paramOnCardWheelScrollListener)
  {
    this.mScrollingListeners.add(paramOnCardWheelScrollListener);
  }

  public T getCurrentValue()
  {
    return this.mSpinnerWheel.getViewAdapter().getItem(this.mSpinnerWheel.getCurrentItem());
  }

  public void onChanged(AbstractWheel paramAbstractWheel, int paramInt1, int paramInt2)
  {
    Object localObject1 = getValue(paramInt1);
    Object localObject2 = getValue(paramInt2);
    Iterator localIterator = this.mScrollingListeners.iterator();
    while (localIterator.hasNext())
      ((OnCardWheelScrollListener)localIterator.next()).onScrollingUpdate(this, localObject1, localObject2);
  }

  public void onItemClicked(AbstractWheel paramAbstractWheel, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      showSoftInput(this.mSpinnerWheel.getViewAdapter().getItem(paramInt));
      return;
    }
    hideSoftInput();
    setCurrentItemIndex(paramInt, true);
  }

  public void onScrollingFinished(AbstractWheel paramAbstractWheel)
  {
    Object localObject = getCurrentValue();
    Iterator localIterator = this.mScrollingListeners.iterator();
    while (localIterator.hasNext())
      ((OnCardWheelScrollListener)localIterator.next()).onScrollingEnded(this, this.scrollingStartValue, localObject);
  }

  public void onScrollingStarted(AbstractWheel paramAbstractWheel)
  {
    hideSoftInput();
    this.scrollingStartValue = getCurrentValue();
    Iterator localIterator = this.mScrollingListeners.iterator();
    while (localIterator.hasNext())
      ((OnCardWheelScrollListener)localIterator.next()).onScrollingStarted(this, this.scrollingStartValue);
  }

  public void removeChangingListener(OnCardWheelScrollListener<T> paramOnCardWheelScrollListener)
  {
    this.mScrollingListeners.remove(paramOnCardWheelScrollListener);
  }

  public void setCurrentValue(T paramT)
  {
    this.mSpinnerWheel.setCurrentItem(this.mSpinnerWheel.getViewAdapter().getItemIndex(paramT));
  }

  public void setViewAdapter(AbstractWheelTextAdapter<T> paramAbstractWheelTextAdapter)
  {
    this.mSpinnerWheel.setViewAdapter(paramAbstractWheelTextAdapter);
  }

  public static abstract interface OnCardWheelScrollListener<T>
  {
    public abstract void onScrollingEnded(EditWheelHorizontalView paramEditWheelHorizontalView, T paramT1, T paramT2);

    public abstract void onScrollingStarted(EditWheelHorizontalView paramEditWheelHorizontalView, T paramT);

    public abstract void onScrollingUpdate(EditWheelHorizontalView paramEditWheelHorizontalView, T paramT1, T paramT2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.EditWheelHorizontalView
 * JD-Core Version:    0.6.2
 */