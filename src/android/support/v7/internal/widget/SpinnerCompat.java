package android.support.v7.internal.widget;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SpinnerAdapter;

class SpinnerCompat extends AbsSpinnerCompat
  implements DialogInterface.OnClickListener
{
  private static final int MAX_ITEMS_MEASURED = 15;
  public static final int MODE_DIALOG = 0;
  public static final int MODE_DROPDOWN = 1;
  private static final int MODE_THEME = -1;
  private static final String TAG = "Spinner";
  private boolean mDisableChildrenWhenDisabled;
  int mDropDownWidth;
  private ListPopupWindow.ForwardingListener mForwardingListener;
  private int mGravity;
  private SpinnerPopup mPopup;
  private DropDownAdapter mTempAdapter;
  private Rect mTempRect = new Rect();
  private final TintManager mTintManager;

  SpinnerCompat(Context paramContext)
  {
    this(paramContext, null);
  }

  SpinnerCompat(Context paramContext, int paramInt)
  {
    this(paramContext, null, R.attr.spinnerStyle, paramInt);
  }

  SpinnerCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.spinnerStyle);
  }

  SpinnerCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, -1);
  }

  SpinnerCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.Spinner, paramInt1, 0);
    if (localTintTypedArray.hasValue(R.styleable.Spinner_android_background))
      setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.Spinner_android_background));
    if (paramInt2 == -1)
      paramInt2 = localTintTypedArray.getInt(R.styleable.Spinner_spinnerMode, 0);
    switch (paramInt2)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      this.mGravity = localTintTypedArray.getInt(R.styleable.Spinner_android_gravity, 17);
      this.mPopup.setPromptText(localTintTypedArray.getString(R.styleable.Spinner_prompt));
      this.mDisableChildrenWhenDisabled = localTintTypedArray.getBoolean(R.styleable.Spinner_disableChildrenWhenDisabled, false);
      localTintTypedArray.recycle();
      if (this.mTempAdapter != null)
      {
        this.mPopup.setAdapter(this.mTempAdapter);
        this.mTempAdapter = null;
      }
      this.mTintManager = localTintTypedArray.getTintManager();
      return;
      this.mPopup = new DialogPopup(null);
      continue;
      final DropdownPopup localDropdownPopup = new DropdownPopup(paramContext, paramAttributeSet, paramInt1);
      this.mDropDownWidth = localTintTypedArray.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
      localDropdownPopup.setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.Spinner_android_popupBackground));
      this.mPopup = localDropdownPopup;
      this.mForwardingListener = new ListPopupWindow.ForwardingListener(this)
      {
        public ListPopupWindow getPopup()
        {
          return localDropdownPopup;
        }

        public boolean onForwardingStarted()
        {
          if (!SpinnerCompat.this.mPopup.isShowing())
            SpinnerCompat.this.mPopup.show();
          return true;
        }
      };
    }
  }

  private View makeView(int paramInt, boolean paramBoolean)
  {
    if (!this.mDataChanged)
    {
      View localView2 = this.mRecycler.get(paramInt);
      if (localView2 != null)
      {
        setUpChild(localView2, paramBoolean);
        return localView2;
      }
    }
    View localView1 = this.mAdapter.getView(paramInt, null, this);
    setUpChild(localView1, paramBoolean);
    return localView1;
  }

  private void setUpChild(View paramView, boolean paramBoolean)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (localLayoutParams == null)
      localLayoutParams = generateDefaultLayoutParams();
    if (paramBoolean)
      addViewInLayout(paramView, 0, localLayoutParams);
    paramView.setSelected(hasFocus());
    if (this.mDisableChildrenWhenDisabled)
      paramView.setEnabled(isEnabled());
    int i = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, localLayoutParams.height);
    paramView.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, localLayoutParams.width), i);
    int j = this.mSpinnerPadding.top + (getMeasuredHeight() - this.mSpinnerPadding.bottom - this.mSpinnerPadding.top - paramView.getMeasuredHeight()) / 2;
    int k = j + paramView.getMeasuredHeight();
    paramView.layout(0, j, 0 + paramView.getMeasuredWidth(), k);
  }

  public int getBaseline()
  {
    int i = -1;
    View localView;
    if (getChildCount() > 0)
      localView = getChildAt(0);
    while (true)
    {
      if (localView != null)
      {
        int k = localView.getBaseline();
        if (k >= 0)
          i = k + localView.getTop();
      }
      return i;
      SpinnerAdapter localSpinnerAdapter = this.mAdapter;
      localView = null;
      if (localSpinnerAdapter != null)
      {
        int j = this.mAdapter.getCount();
        localView = null;
        if (j > 0)
        {
          localView = makeView(0, false);
          this.mRecycler.put(0, localView);
        }
      }
    }
  }

  public int getDropDownHorizontalOffset()
  {
    return this.mPopup.getHorizontalOffset();
  }

  public int getDropDownVerticalOffset()
  {
    return this.mPopup.getVerticalOffset();
  }

  public int getDropDownWidth()
  {
    return this.mDropDownWidth;
  }

  public Drawable getPopupBackground()
  {
    return this.mPopup.getBackground();
  }

  public CharSequence getPrompt()
  {
    return this.mPopup.getHintText();
  }

  void layout(int paramInt, boolean paramBoolean)
  {
    int i = this.mSpinnerPadding.left;
    int j = getRight() - getLeft() - this.mSpinnerPadding.left - this.mSpinnerPadding.right;
    if (this.mDataChanged)
      handleDataChanged();
    if (this.mItemCount == 0)
    {
      resetList();
      return;
    }
    if (this.mNextSelectedPosition >= 0)
      setSelectedPositionInt(this.mNextSelectedPosition);
    recycleAllViews();
    removeAllViewsInLayout();
    this.mFirstPosition = this.mSelectedPosition;
    View localView;
    int k;
    int m;
    if (this.mAdapter != null)
    {
      localView = makeView(this.mSelectedPosition, true);
      k = localView.getMeasuredWidth();
      m = i;
      int n = ViewCompat.getLayoutDirection(this);
      switch (0x7 & GravityCompat.getAbsoluteGravity(this.mGravity, n))
      {
      default:
      case 1:
      case 5:
      }
    }
    while (true)
    {
      localView.offsetLeftAndRight(m);
      this.mRecycler.clear();
      invalidate();
      checkSelectionChanged();
      this.mDataChanged = false;
      this.mNeedSync = false;
      setNextSelectedPositionInt(this.mSelectedPosition);
      return;
      m = i + j / 2 - k / 2;
      continue;
      m = i + j - k;
    }
  }

  int measureContentWidth(SpinnerAdapter paramSpinnerAdapter, Drawable paramDrawable)
  {
    int i;
    if (paramSpinnerAdapter == null)
      i = 0;
    do
    {
      return i;
      i = 0;
      View localView = null;
      int j = 0;
      int k = View.MeasureSpec.makeMeasureSpec(0, 0);
      int m = View.MeasureSpec.makeMeasureSpec(0, 0);
      int n = Math.max(0, getSelectedItemPosition());
      int i1 = Math.min(paramSpinnerAdapter.getCount(), n + 15);
      for (int i2 = Math.max(0, n - (15 - (i1 - n))); i2 < i1; i2++)
      {
        int i3 = paramSpinnerAdapter.getItemViewType(i2);
        if (i3 != j)
        {
          j = i3;
          localView = null;
        }
        localView = paramSpinnerAdapter.getView(i2, localView, this);
        if (localView.getLayoutParams() == null)
          localView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        localView.measure(k, m);
        i = Math.max(i, localView.getMeasuredWidth());
      }
    }
    while (paramDrawable == null);
    paramDrawable.getPadding(this.mTempRect);
    return i + (this.mTempRect.left + this.mTempRect.right);
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    setSelection(paramInt);
    paramDialogInterface.dismiss();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if ((this.mPopup != null) && (this.mPopup.isShowing()))
      this.mPopup.dismiss();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mInLayout = true;
    layout(0, false);
    this.mInLayout = false;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mPopup != null) && (View.MeasureSpec.getMode(paramInt1) == -2147483648))
      setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), measureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight());
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (localSavedState.showDropdown)
    {
      ViewTreeObserver localViewTreeObserver = getViewTreeObserver();
      if (localViewTreeObserver != null)
        localViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            if (!SpinnerCompat.this.mPopup.isShowing())
              SpinnerCompat.this.mPopup.show();
            ViewTreeObserver localViewTreeObserver = SpinnerCompat.this.getViewTreeObserver();
            if (localViewTreeObserver != null)
              localViewTreeObserver.removeGlobalOnLayoutListener(this);
          }
        });
    }
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if ((this.mPopup != null) && (this.mPopup.isShowing()));
    for (boolean bool = true; ; bool = false)
    {
      localSavedState.showDropdown = bool;
      return localSavedState;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mForwardingListener != null) && (this.mForwardingListener.onTouch(this, paramMotionEvent)))
      return true;
    return super.onTouchEvent(paramMotionEvent);
  }

  public boolean performClick()
  {
    boolean bool = super.performClick();
    if (!bool)
    {
      bool = true;
      if (!this.mPopup.isShowing())
        this.mPopup.show();
    }
    return bool;
  }

  public void setAdapter(SpinnerAdapter paramSpinnerAdapter)
  {
    super.setAdapter(paramSpinnerAdapter);
    this.mRecycler.clear();
    if ((getContext().getApplicationInfo().targetSdkVersion >= 21) && (paramSpinnerAdapter != null) && (paramSpinnerAdapter.getViewTypeCount() != 1))
      throw new IllegalArgumentException("Spinner adapter view type count must be 1");
    if (this.mPopup != null)
    {
      this.mPopup.setAdapter(new DropDownAdapter(paramSpinnerAdapter));
      return;
    }
    this.mTempAdapter = new DropDownAdapter(paramSpinnerAdapter);
  }

  public void setDropDownHorizontalOffset(int paramInt)
  {
    this.mPopup.setHorizontalOffset(paramInt);
  }

  public void setDropDownVerticalOffset(int paramInt)
  {
    this.mPopup.setVerticalOffset(paramInt);
  }

  public void setDropDownWidth(int paramInt)
  {
    if (!(this.mPopup instanceof DropdownPopup))
    {
      Log.e("Spinner", "Cannot set dropdown width for MODE_DIALOG, ignoring");
      return;
    }
    this.mDropDownWidth = paramInt;
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if (this.mDisableChildrenWhenDisabled)
    {
      int i = getChildCount();
      for (int j = 0; j < i; j++)
        getChildAt(j).setEnabled(paramBoolean);
    }
  }

  public void setGravity(int paramInt)
  {
    if (this.mGravity != paramInt)
    {
      if ((paramInt & 0x7) == 0)
        paramInt |= 8388611;
      this.mGravity = paramInt;
      requestLayout();
    }
  }

  public void setOnItemClickListener(AdapterViewCompat.OnItemClickListener paramOnItemClickListener)
  {
    throw new RuntimeException("setOnItemClickListener cannot be used with a spinner.");
  }

  void setOnItemClickListenerInt(AdapterViewCompat.OnItemClickListener paramOnItemClickListener)
  {
    super.setOnItemClickListener(paramOnItemClickListener);
  }

  public void setPopupBackgroundDrawable(Drawable paramDrawable)
  {
    if (!(this.mPopup instanceof DropdownPopup))
    {
      Log.e("Spinner", "setPopupBackgroundDrawable: incompatible spinner mode; ignoring...");
      return;
    }
    ((DropdownPopup)this.mPopup).setBackgroundDrawable(paramDrawable);
  }

  public void setPopupBackgroundResource(int paramInt)
  {
    setPopupBackgroundDrawable(this.mTintManager.getDrawable(paramInt));
  }

  public void setPrompt(CharSequence paramCharSequence)
  {
    this.mPopup.setPromptText(paramCharSequence);
  }

  public void setPromptId(int paramInt)
  {
    setPrompt(getContext().getText(paramInt));
  }

  private class DialogPopup
    implements SpinnerCompat.SpinnerPopup, DialogInterface.OnClickListener
  {
    private ListAdapter mListAdapter;
    private AlertDialog mPopup;
    private CharSequence mPrompt;

    private DialogPopup()
    {
    }

    public void dismiss()
    {
      if (this.mPopup != null)
      {
        this.mPopup.dismiss();
        this.mPopup = null;
      }
    }

    public Drawable getBackground()
    {
      return null;
    }

    public CharSequence getHintText()
    {
      return this.mPrompt;
    }

    public int getHorizontalOffset()
    {
      return 0;
    }

    public int getVerticalOffset()
    {
      return 0;
    }

    public boolean isShowing()
    {
      if (this.mPopup != null)
        return this.mPopup.isShowing();
      return false;
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      SpinnerCompat.this.setSelection(paramInt);
      if (SpinnerCompat.this.mOnItemClickListener != null)
        SpinnerCompat.this.performItemClick(null, paramInt, this.mListAdapter.getItemId(paramInt));
      dismiss();
    }

    public void setAdapter(ListAdapter paramListAdapter)
    {
      this.mListAdapter = paramListAdapter;
    }

    public void setBackgroundDrawable(Drawable paramDrawable)
    {
      Log.e("Spinner", "Cannot set popup background for MODE_DIALOG, ignoring");
    }

    public void setHorizontalOffset(int paramInt)
    {
      Log.e("Spinner", "Cannot set horizontal offset for MODE_DIALOG, ignoring");
    }

    public void setPromptText(CharSequence paramCharSequence)
    {
      this.mPrompt = paramCharSequence;
    }

    public void setVerticalOffset(int paramInt)
    {
      Log.e("Spinner", "Cannot set vertical offset for MODE_DIALOG, ignoring");
    }

    public void show()
    {
      if (this.mListAdapter == null)
        return;
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(SpinnerCompat.this.getContext());
      if (this.mPrompt != null)
        localBuilder.setTitle(this.mPrompt);
      this.mPopup = localBuilder.setSingleChoiceItems(this.mListAdapter, SpinnerCompat.this.getSelectedItemPosition(), this).create();
      this.mPopup.show();
    }
  }

  private static class DropDownAdapter
    implements ListAdapter, SpinnerAdapter
  {
    private SpinnerAdapter mAdapter;
    private ListAdapter mListAdapter;

    public DropDownAdapter(SpinnerAdapter paramSpinnerAdapter)
    {
      this.mAdapter = paramSpinnerAdapter;
      if ((paramSpinnerAdapter instanceof SpinnerAdapter))
        this.mListAdapter = ((SpinnerAdapter)paramSpinnerAdapter);
    }

    public boolean areAllItemsEnabled()
    {
      ListAdapter localListAdapter = this.mListAdapter;
      if (localListAdapter != null)
        return localListAdapter.areAllItemsEnabled();
      return true;
    }

    public int getCount()
    {
      if (this.mAdapter == null)
        return 0;
      return this.mAdapter.getCount();
    }

    public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (this.mAdapter == null)
        return null;
      return this.mAdapter.getDropDownView(paramInt, paramView, paramViewGroup);
    }

    public Object getItem(int paramInt)
    {
      if (this.mAdapter == null)
        return null;
      return this.mAdapter.getItem(paramInt);
    }

    public long getItemId(int paramInt)
    {
      if (this.mAdapter == null)
        return -1L;
      return this.mAdapter.getItemId(paramInt);
    }

    public int getItemViewType(int paramInt)
    {
      return 0;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return getDropDownView(paramInt, paramView, paramViewGroup);
    }

    public int getViewTypeCount()
    {
      return 1;
    }

    public boolean hasStableIds()
    {
      return (this.mAdapter != null) && (this.mAdapter.hasStableIds());
    }

    public boolean isEmpty()
    {
      return getCount() == 0;
    }

    public boolean isEnabled(int paramInt)
    {
      ListAdapter localListAdapter = this.mListAdapter;
      if (localListAdapter != null)
        return localListAdapter.isEnabled(paramInt);
      return true;
    }

    public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      if (this.mAdapter != null)
        this.mAdapter.registerDataSetObserver(paramDataSetObserver);
    }

    public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      if (this.mAdapter != null)
        this.mAdapter.unregisterDataSetObserver(paramDataSetObserver);
    }
  }

  private class DropdownPopup extends ListPopupWindow
    implements SpinnerCompat.SpinnerPopup
  {
    private ListAdapter mAdapter;
    private CharSequence mHintText;

    public DropdownPopup(Context paramAttributeSet, AttributeSet paramInt, int arg4)
    {
      super(paramInt, i);
      setAnchorView(SpinnerCompat.this);
      setModal(true);
      setPromptPosition(0);
      setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          SpinnerCompat.this.setSelection(paramAnonymousInt);
          if (SpinnerCompat.this.mOnItemClickListener != null)
            SpinnerCompat.this.performItemClick(paramAnonymousView, paramAnonymousInt, SpinnerCompat.DropdownPopup.this.mAdapter.getItemId(paramAnonymousInt));
          SpinnerCompat.DropdownPopup.this.dismiss();
        }
      });
    }

    void computeContentWidth()
    {
      Drawable localDrawable = getBackground();
      int i;
      int j;
      int k;
      int m;
      if (localDrawable != null)
      {
        localDrawable.getPadding(SpinnerCompat.this.mTempRect);
        if (ViewUtils.isLayoutRtl(SpinnerCompat.this))
        {
          i = SpinnerCompat.this.mTempRect.right;
          j = SpinnerCompat.this.getPaddingLeft();
          k = SpinnerCompat.this.getPaddingRight();
          m = SpinnerCompat.this.getWidth();
          if (SpinnerCompat.this.mDropDownWidth != -2)
            break label244;
          int i1 = SpinnerCompat.this.measureContentWidth((SpinnerAdapter)this.mAdapter, getBackground());
          int i2 = SpinnerCompat.this.getContext().getResources().getDisplayMetrics().widthPixels - SpinnerCompat.this.mTempRect.left - SpinnerCompat.this.mTempRect.right;
          if (i1 > i2)
            i1 = i2;
          setContentWidth(Math.max(i1, m - j - k));
          label169: if (!ViewUtils.isLayoutRtl(SpinnerCompat.this))
            break label284;
        }
      }
      label284: for (int n = i + (m - k - getWidth()); ; n = i + j)
      {
        setHorizontalOffset(n);
        return;
        i = -SpinnerCompat.this.mTempRect.left;
        break;
        Rect localRect = SpinnerCompat.this.mTempRect;
        SpinnerCompat.this.mTempRect.right = 0;
        localRect.left = 0;
        i = 0;
        break;
        label244: if (SpinnerCompat.this.mDropDownWidth == -1)
        {
          setContentWidth(m - j - k);
          break label169;
        }
        setContentWidth(SpinnerCompat.this.mDropDownWidth);
        break label169;
      }
    }

    public CharSequence getHintText()
    {
      return this.mHintText;
    }

    public void setAdapter(ListAdapter paramListAdapter)
    {
      super.setAdapter(paramListAdapter);
      this.mAdapter = paramListAdapter;
    }

    public void setPromptText(CharSequence paramCharSequence)
    {
      this.mHintText = paramCharSequence;
    }

    public void show(int paramInt1, int paramInt2)
    {
      boolean bool = isShowing();
      computeContentWidth();
      setInputMethodMode(2);
      super.show();
      getListView().setChoiceMode(1);
      setSelection(SpinnerCompat.this.getSelectedItemPosition());
      if (bool);
      ViewTreeObserver localViewTreeObserver;
      do
      {
        return;
        localViewTreeObserver = SpinnerCompat.this.getViewTreeObserver();
      }
      while (localViewTreeObserver == null);
      final ViewTreeObserver.OnGlobalLayoutListener local2 = new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public void onGlobalLayout()
        {
          SpinnerCompat.DropdownPopup.this.computeContentWidth();
          SpinnerCompat.DropdownPopup.this.show();
        }
      };
      localViewTreeObserver.addOnGlobalLayoutListener(local2);
      setOnDismissListener(new PopupWindow.OnDismissListener()
      {
        public void onDismiss()
        {
          ViewTreeObserver localViewTreeObserver = SpinnerCompat.this.getViewTreeObserver();
          if (localViewTreeObserver != null)
            localViewTreeObserver.removeGlobalOnLayoutListener(local2);
        }
      });
    }
  }

  static class SavedState extends AbsSpinnerCompat.SavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public SpinnerCompat.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SpinnerCompat.SavedState(paramAnonymousParcel, null);
      }

      public SpinnerCompat.SavedState[] newArray(int paramAnonymousInt)
      {
        return new SpinnerCompat.SavedState[paramAnonymousInt];
      }
    };
    boolean showDropdown;

    private SavedState(Parcel paramParcel)
    {
      super();
      if (paramParcel.readByte() != 0);
      for (boolean bool = true; ; bool = false)
      {
        this.showDropdown = bool;
        return;
      }
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      if (this.showDropdown);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeByte((byte)i);
        return;
      }
    }
  }

  private static abstract interface SpinnerPopup
  {
    public abstract void dismiss();

    public abstract Drawable getBackground();

    public abstract CharSequence getHintText();

    public abstract int getHorizontalOffset();

    public abstract int getVerticalOffset();

    public abstract boolean isShowing();

    public abstract void setAdapter(ListAdapter paramListAdapter);

    public abstract void setBackgroundDrawable(Drawable paramDrawable);

    public abstract void setHorizontalOffset(int paramInt);

    public abstract void setPromptText(CharSequence paramCharSequence);

    public abstract void setVerticalOffset(int paramInt);

    public abstract void show();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.SpinnerCompat
 * JD-Core Version:    0.6.2
 */