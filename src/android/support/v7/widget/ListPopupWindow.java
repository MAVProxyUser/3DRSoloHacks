package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.widget.AppCompatPopupWindow;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import java.lang.reflect.Method;

public class ListPopupWindow
{
  private static final boolean DEBUG = false;
  private static final int EXPAND_LIST_TIMEOUT = 250;
  public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
  public static final int INPUT_METHOD_NEEDED = 1;
  public static final int INPUT_METHOD_NOT_NEEDED = 2;
  public static final int MATCH_PARENT = -1;
  public static final int POSITION_PROMPT_ABOVE = 0;
  public static final int POSITION_PROMPT_BELOW = 1;
  private static final String TAG = "ListPopupWindow";
  public static final int WRAP_CONTENT = -2;
  private static Method sClipToWindowEnabledMethod;
  private ListAdapter mAdapter;
  private Context mContext;
  private boolean mDropDownAlwaysVisible = false;
  private View mDropDownAnchorView;
  private int mDropDownGravity = 0;
  private int mDropDownHeight = -2;
  private int mDropDownHorizontalOffset;
  private ListPopupWindow.DropDownListView mDropDownList;
  private Drawable mDropDownListHighlight;
  private int mDropDownVerticalOffset;
  private boolean mDropDownVerticalOffsetSet;
  private int mDropDownWidth = -2;
  private boolean mForceIgnoreOutsideTouch = false;
  private Handler mHandler = new Handler();
  private final ListSelectorHider mHideSelector = new ListSelectorHider(null);
  private AdapterView.OnItemClickListener mItemClickListener;
  private AdapterView.OnItemSelectedListener mItemSelectedListener;
  private int mLayoutDirection;
  int mListItemExpandMaximum = 2147483647;
  private boolean mModal;
  private DataSetObserver mObserver;
  private PopupWindow mPopup;
  private int mPromptPosition = 0;
  private View mPromptView;
  private final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable(null);
  private final PopupScrollListener mScrollListener = new PopupScrollListener(null);
  private Runnable mShowDropDownRunnable;
  private Rect mTempRect = new Rect();
  private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor(null);

  static
  {
    try
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Boolean.TYPE;
      sClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", arrayOfClass);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
    }
  }

  public ListPopupWindow(Context paramContext)
  {
    this(paramContext, null, R.attr.listPopupWindowStyle);
  }

  public ListPopupWindow(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.listPopupWindowStyle);
  }

  public ListPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }

  public ListPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this.mContext = paramContext;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ListPopupWindow, paramInt1, paramInt2);
    this.mDropDownHorizontalOffset = localTypedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
    this.mDropDownVerticalOffset = localTypedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
    if (this.mDropDownVerticalOffset != 0)
      this.mDropDownVerticalOffsetSet = true;
    localTypedArray.recycle();
    this.mPopup = new AppCompatPopupWindow(paramContext, paramAttributeSet, paramInt1);
    this.mPopup.setInputMethodMode(1);
    this.mLayoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(this.mContext.getResources().getConfiguration().locale);
  }

  private int buildDropDown()
  {
    boolean bool;
    Object localObject;
    View localView2;
    int i;
    LinearLayout localLinearLayout;
    LinearLayout.LayoutParams localLayoutParams2;
    label253: int j;
    if (this.mDropDownList == null)
    {
      Context localContext = this.mContext;
      this.mShowDropDownRunnable = new Runnable()
      {
        public void run()
        {
          View localView = ListPopupWindow.this.getAnchorView();
          if ((localView != null) && (localView.getWindowToken() != null))
            ListPopupWindow.this.show();
        }
      };
      if (!this.mModal)
      {
        bool = true;
        this.mDropDownList = new ListPopupWindow.DropDownListView(localContext, bool);
        if (this.mDropDownListHighlight != null)
          this.mDropDownList.setSelector(this.mDropDownListHighlight);
        this.mDropDownList.setAdapter(this.mAdapter);
        this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
        this.mDropDownList.setFocusable(true);
        this.mDropDownList.setFocusableInTouchMode(true);
        this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            if (paramAnonymousInt != -1)
            {
              ListPopupWindow.DropDownListView localDropDownListView = ListPopupWindow.this.mDropDownList;
              if (localDropDownListView != null)
                ListPopupWindow.DropDownListView.access$502(localDropDownListView, false);
            }
          }

          public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
          {
          }
        });
        this.mDropDownList.setOnScrollListener(this.mScrollListener);
        if (this.mItemSelectedListener != null)
          this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
        localObject = this.mDropDownList;
        localView2 = this.mPromptView;
        i = 0;
        if (localView2 != null)
        {
          localLinearLayout = new LinearLayout(localContext);
          localLinearLayout.setOrientation(1);
          localLayoutParams2 = new LinearLayout.LayoutParams(-1, 0, 1.0F);
        }
        switch (this.mPromptPosition)
        {
        default:
          Log.e("ListPopupWindow", "Invalid hint position " + this.mPromptPosition);
          localView2.measure(View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, -2147483648), 0);
          LinearLayout.LayoutParams localLayoutParams3 = (LinearLayout.LayoutParams)localView2.getLayoutParams();
          i = localView2.getMeasuredHeight() + localLayoutParams3.topMargin + localLayoutParams3.bottomMargin;
          localObject = localLinearLayout;
          this.mPopup.setContentView((View)localObject);
          label310: Drawable localDrawable = this.mPopup.getBackground();
          if (localDrawable != null)
          {
            localDrawable.getPadding(this.mTempRect);
            j = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet)
              this.mDropDownVerticalOffset = (-this.mTempRect.top);
            label370: if (this.mPopup.getInputMethodMode() != 2)
              break label527;
          }
          break;
        case 1:
        case 0:
        }
      }
    }
    int k;
    label527: 
    while (true)
    {
      k = this.mPopup.getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset);
      if ((!this.mDropDownAlwaysVisible) && (this.mDropDownHeight != -1))
        break label530;
      return k + j;
      bool = false;
      break;
      localLinearLayout.addView((View)localObject, localLayoutParams2);
      localLinearLayout.addView(localView2);
      break label253;
      localLinearLayout.addView(localView2);
      localLinearLayout.addView((View)localObject, localLayoutParams2);
      break label253;
      ((ViewGroup)this.mPopup.getContentView());
      View localView1 = this.mPromptView;
      i = 0;
      if (localView1 == null)
        break label310;
      LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)localView1.getLayoutParams();
      i = localView1.getMeasuredHeight() + localLayoutParams1.topMargin + localLayoutParams1.bottomMargin;
      break label310;
      this.mTempRect.setEmpty();
      j = 0;
      break label370;
    }
    label530: int m;
    switch (this.mDropDownWidth)
    {
    default:
      m = View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, 1073741824);
    case -2:
    case -1:
    }
    while (true)
    {
      int n = this.mDropDownList.measureHeightOfChildrenCompat(m, 0, -1, k - i, -1);
      if (n > 0)
        i += j;
      return n + i;
      m = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), -2147483648);
      continue;
      m = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824);
    }
  }

  private static boolean isConfirmKey(int paramInt)
  {
    return (paramInt == 66) || (paramInt == 23);
  }

  private void removePromptView()
  {
    if (this.mPromptView != null)
    {
      ViewParent localViewParent = this.mPromptView.getParent();
      if ((localViewParent instanceof ViewGroup))
        ((ViewGroup)localViewParent).removeView(this.mPromptView);
    }
  }

  private void setPopupClipToScreenEnabled(boolean paramBoolean)
  {
    if (sClipToWindowEnabledMethod != null);
    try
    {
      Method localMethod = sClipToWindowEnabledMethod;
      PopupWindow localPopupWindow = this.mPopup;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      localMethod.invoke(localPopupWindow, arrayOfObject);
      return;
    }
    catch (Exception localException)
    {
      Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
    }
  }

  public void clearListSelection()
  {
    ListPopupWindow.DropDownListView localDropDownListView = this.mDropDownList;
    if (localDropDownListView != null)
    {
      ListPopupWindow.DropDownListView.access$502(localDropDownListView, true);
      localDropDownListView.requestLayout();
    }
  }

  public View.OnTouchListener createDragToOpenListener(View paramView)
  {
    return new ListPopupWindow.1(this, paramView);
  }

  public void dismiss()
  {
    this.mPopup.dismiss();
    removePromptView();
    this.mPopup.setContentView(null);
    this.mDropDownList = null;
    this.mHandler.removeCallbacks(this.mResizePopupRunnable);
  }

  public View getAnchorView()
  {
    return this.mDropDownAnchorView;
  }

  public int getAnimationStyle()
  {
    return this.mPopup.getAnimationStyle();
  }

  public Drawable getBackground()
  {
    return this.mPopup.getBackground();
  }

  public int getHeight()
  {
    return this.mDropDownHeight;
  }

  public int getHorizontalOffset()
  {
    return this.mDropDownHorizontalOffset;
  }

  public int getInputMethodMode()
  {
    return this.mPopup.getInputMethodMode();
  }

  public ListView getListView()
  {
    return this.mDropDownList;
  }

  public int getPromptPosition()
  {
    return this.mPromptPosition;
  }

  public Object getSelectedItem()
  {
    if (!isShowing())
      return null;
    return this.mDropDownList.getSelectedItem();
  }

  public long getSelectedItemId()
  {
    if (!isShowing())
      return -9223372036854775808L;
    return this.mDropDownList.getSelectedItemId();
  }

  public int getSelectedItemPosition()
  {
    if (!isShowing())
      return -1;
    return this.mDropDownList.getSelectedItemPosition();
  }

  public View getSelectedView()
  {
    if (!isShowing())
      return null;
    return this.mDropDownList.getSelectedView();
  }

  public int getSoftInputMode()
  {
    return this.mPopup.getSoftInputMode();
  }

  public int getVerticalOffset()
  {
    if (!this.mDropDownVerticalOffsetSet)
      return 0;
    return this.mDropDownVerticalOffset;
  }

  public int getWidth()
  {
    return this.mDropDownWidth;
  }

  public boolean isDropDownAlwaysVisible()
  {
    return this.mDropDownAlwaysVisible;
  }

  public boolean isInputMethodNotNeeded()
  {
    return this.mPopup.getInputMethodMode() == 2;
  }

  public boolean isModal()
  {
    return this.mModal;
  }

  public boolean isShowing()
  {
    return this.mPopup.isShowing();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i;
    int j;
    int k;
    int m;
    if ((isShowing()) && (paramInt != 62) && ((this.mDropDownList.getSelectedItemPosition() >= 0) || (!isConfirmKey(paramInt))))
    {
      i = this.mDropDownList.getSelectedItemPosition();
      ListAdapter localListAdapter;
      if (!this.mPopup.isAboveAnchor())
      {
        j = 1;
        localListAdapter = this.mAdapter;
        k = 2147483647;
        m = -2147483648;
        if (localListAdapter != null)
        {
          boolean bool = localListAdapter.areAllItemsEnabled();
          if (!bool)
            break label162;
          k = 0;
          label88: if (!bool)
            break label176;
        }
      }
      label162: label176: for (m = -1 + localListAdapter.getCount(); ; m = this.mDropDownList.lookForSelectablePosition(-1 + localListAdapter.getCount(), false))
      {
        if (((j == 0) || (paramInt != 19) || (i > k)) && ((j != 0) || (paramInt != 20) || (i < m)))
          break label198;
        clearListSelection();
        this.mPopup.setInputMethodMode(1);
        show();
        return true;
        j = 0;
        break;
        k = this.mDropDownList.lookForSelectablePosition(0, true);
        break label88;
      }
      label198: ListPopupWindow.DropDownListView.access$502(this.mDropDownList, false);
      if (!this.mDropDownList.onKeyDown(paramInt, paramKeyEvent))
        break label286;
      this.mPopup.setInputMethodMode(2);
      this.mDropDownList.requestFocusFromTouch();
      show();
      switch (paramInt)
      {
      case 19:
      case 20:
      case 23:
      case 66:
      }
    }
    label286: 
    do
    {
      do
      {
        return false;
        if ((j == 0) || (paramInt != 20))
          break;
      }
      while (i != m);
      return true;
    }
    while ((j != 0) || (paramInt != 19) || (i != k));
    return true;
  }

  public boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (isShowing()))
    {
      View localView = this.mDropDownAnchorView;
      if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getRepeatCount() == 0))
      {
        KeyEvent.DispatcherState localDispatcherState2 = localView.getKeyDispatcherState();
        if (localDispatcherState2 != null)
          localDispatcherState2.startTracking(paramKeyEvent, this);
        return true;
      }
      if (paramKeyEvent.getAction() == 1)
      {
        KeyEvent.DispatcherState localDispatcherState1 = localView.getKeyDispatcherState();
        if (localDispatcherState1 != null)
          localDispatcherState1.handleUpEvent(paramKeyEvent);
        if ((paramKeyEvent.isTracking()) && (!paramKeyEvent.isCanceled()))
        {
          dismiss();
          return true;
        }
      }
    }
    return false;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((isShowing()) && (this.mDropDownList.getSelectedItemPosition() >= 0))
    {
      boolean bool = this.mDropDownList.onKeyUp(paramInt, paramKeyEvent);
      if ((bool) && (isConfirmKey(paramInt)))
        dismiss();
      return bool;
    }
    return false;
  }

  public boolean performItemClick(int paramInt)
  {
    if (isShowing())
    {
      if (this.mItemClickListener != null)
      {
        ListPopupWindow.DropDownListView localDropDownListView = this.mDropDownList;
        View localView = localDropDownListView.getChildAt(paramInt - localDropDownListView.getFirstVisiblePosition());
        ListAdapter localListAdapter = localDropDownListView.getAdapter();
        this.mItemClickListener.onItemClick(localDropDownListView, localView, paramInt, localListAdapter.getItemId(paramInt));
      }
      return true;
    }
    return false;
  }

  public void postShow()
  {
    this.mHandler.post(this.mShowDropDownRunnable);
  }

  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (this.mObserver == null)
      this.mObserver = new PopupDataSetObserver(null);
    while (true)
    {
      this.mAdapter = paramListAdapter;
      if (this.mAdapter != null)
        paramListAdapter.registerDataSetObserver(this.mObserver);
      if (this.mDropDownList != null)
        this.mDropDownList.setAdapter(this.mAdapter);
      return;
      if (this.mAdapter != null)
        this.mAdapter.unregisterDataSetObserver(this.mObserver);
    }
  }

  public void setAnchorView(View paramView)
  {
    this.mDropDownAnchorView = paramView;
  }

  public void setAnimationStyle(int paramInt)
  {
    this.mPopup.setAnimationStyle(paramInt);
  }

  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mPopup.setBackgroundDrawable(paramDrawable);
  }

  public void setContentWidth(int paramInt)
  {
    Drawable localDrawable = this.mPopup.getBackground();
    if (localDrawable != null)
    {
      localDrawable.getPadding(this.mTempRect);
      this.mDropDownWidth = (paramInt + (this.mTempRect.left + this.mTempRect.right));
      return;
    }
    setWidth(paramInt);
  }

  public void setDropDownAlwaysVisible(boolean paramBoolean)
  {
    this.mDropDownAlwaysVisible = paramBoolean;
  }

  public void setDropDownGravity(int paramInt)
  {
    this.mDropDownGravity = paramInt;
  }

  public void setForceIgnoreOutsideTouch(boolean paramBoolean)
  {
    this.mForceIgnoreOutsideTouch = paramBoolean;
  }

  public void setHeight(int paramInt)
  {
    this.mDropDownHeight = paramInt;
  }

  public void setHorizontalOffset(int paramInt)
  {
    this.mDropDownHorizontalOffset = paramInt;
  }

  public void setInputMethodMode(int paramInt)
  {
    this.mPopup.setInputMethodMode(paramInt);
  }

  void setListItemExpandMax(int paramInt)
  {
    this.mListItemExpandMaximum = paramInt;
  }

  public void setListSelector(Drawable paramDrawable)
  {
    this.mDropDownListHighlight = paramDrawable;
  }

  public void setModal(boolean paramBoolean)
  {
    this.mModal = paramBoolean;
    this.mPopup.setFocusable(paramBoolean);
  }

  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.mPopup.setOnDismissListener(paramOnDismissListener);
  }

  public void setOnItemClickListener(AdapterView.OnItemClickListener paramOnItemClickListener)
  {
    this.mItemClickListener = paramOnItemClickListener;
  }

  public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.mItemSelectedListener = paramOnItemSelectedListener;
  }

  public void setPromptPosition(int paramInt)
  {
    this.mPromptPosition = paramInt;
  }

  public void setPromptView(View paramView)
  {
    boolean bool = isShowing();
    if (bool)
      removePromptView();
    this.mPromptView = paramView;
    if (bool)
      show();
  }

  public void setSelection(int paramInt)
  {
    ListPopupWindow.DropDownListView localDropDownListView = this.mDropDownList;
    if ((isShowing()) && (localDropDownListView != null))
    {
      ListPopupWindow.DropDownListView.access$502(localDropDownListView, false);
      localDropDownListView.setSelection(paramInt);
      if ((Build.VERSION.SDK_INT >= 11) && (localDropDownListView.getChoiceMode() != 0))
        localDropDownListView.setItemChecked(paramInt, true);
    }
  }

  public void setSoftInputMode(int paramInt)
  {
    this.mPopup.setSoftInputMode(paramInt);
  }

  public void setVerticalOffset(int paramInt)
  {
    this.mDropDownVerticalOffset = paramInt;
    this.mDropDownVerticalOffsetSet = true;
  }

  public void setWidth(int paramInt)
  {
    this.mDropDownWidth = paramInt;
  }

  public void show()
  {
    boolean bool1 = true;
    int i = -1;
    int j = buildDropDown();
    boolean bool2 = isInputMethodNotNeeded();
    if (this.mPopup.isShowing())
    {
      int n;
      int i1;
      if (this.mDropDownWidth == i)
      {
        n = -1;
        if (this.mDropDownHeight != i)
          break label221;
        if (!bool2)
          break label176;
        i1 = j;
        label52: if (!bool2)
          break label187;
        PopupWindow localPopupWindow4 = this.mPopup;
        if (this.mDropDownWidth != i)
          break label182;
        label71: localPopupWindow4.setWindowLayoutMode(i, 0);
      }
      while (true)
      {
        PopupWindow localPopupWindow2 = this.mPopup;
        boolean bool3 = this.mForceIgnoreOutsideTouch;
        boolean bool4 = false;
        if (!bool3)
        {
          boolean bool5 = this.mDropDownAlwaysVisible;
          bool4 = false;
          if (!bool5)
            bool4 = bool1;
        }
        localPopupWindow2.setOutsideTouchable(bool4);
        this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, n, i1);
        return;
        if (this.mDropDownWidth == -2)
        {
          n = getAnchorView().getWidth();
          break;
        }
        n = this.mDropDownWidth;
        break;
        label176: i1 = i;
        break label52;
        label182: i = 0;
        break label71;
        label187: PopupWindow localPopupWindow3 = this.mPopup;
        if (this.mDropDownWidth == i);
        for (int i2 = i; ; i2 = 0)
        {
          localPopupWindow3.setWindowLayoutMode(i2, i);
          break;
        }
        label221: if (this.mDropDownHeight == -2)
          i1 = j;
        else
          i1 = this.mDropDownHeight;
      }
    }
    int k;
    label256: int m;
    label267: PopupWindow localPopupWindow1;
    if (this.mDropDownWidth == i)
    {
      k = -1;
      if (this.mDropDownHeight != i)
        break label438;
      m = -1;
      this.mPopup.setWindowLayoutMode(k, m);
      setPopupClipToScreenEnabled(bool1);
      localPopupWindow1 = this.mPopup;
      if ((this.mForceIgnoreOutsideTouch) || (this.mDropDownAlwaysVisible))
        break label478;
    }
    while (true)
    {
      localPopupWindow1.setOutsideTouchable(bool1);
      this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
      PopupWindowCompat.showAsDropDown(this.mPopup, getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
      this.mDropDownList.setSelection(i);
      if ((!this.mModal) || (this.mDropDownList.isInTouchMode()))
        clearListSelection();
      if (this.mModal)
        break;
      this.mHandler.post(this.mHideSelector);
      return;
      if (this.mDropDownWidth == -2)
      {
        this.mPopup.setWidth(getAnchorView().getWidth());
        k = 0;
        break label256;
      }
      this.mPopup.setWidth(this.mDropDownWidth);
      k = 0;
      break label256;
      label438: if (this.mDropDownHeight == -2)
      {
        this.mPopup.setHeight(j);
        m = 0;
        break label267;
      }
      this.mPopup.setHeight(this.mDropDownHeight);
      m = 0;
      break label267;
      label478: bool1 = false;
    }
  }

  public static abstract class ForwardingListener
    implements View.OnTouchListener
  {
    private int mActivePointerId;
    private Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    private final View mSrc;
    private final int mTapTimeout;
    private final int[] mTmpLocation = new int[2];
    private Runnable mTriggerLongPress;
    private boolean mWasLongPress;

    public ForwardingListener(View paramView)
    {
      this.mSrc = paramView;
      this.mScaledTouchSlop = ViewConfiguration.get(paramView.getContext()).getScaledTouchSlop();
      this.mTapTimeout = ViewConfiguration.getTapTimeout();
      this.mLongPressTimeout = ((this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2);
    }

    private void clearCallbacks()
    {
      if (this.mTriggerLongPress != null)
        this.mSrc.removeCallbacks(this.mTriggerLongPress);
      if (this.mDisallowIntercept != null)
        this.mSrc.removeCallbacks(this.mDisallowIntercept);
    }

    private void onLongPress()
    {
      clearCallbacks();
      View localView = this.mSrc;
      if ((!localView.isEnabled()) || (localView.isLongClickable()));
      while (!onForwardingStarted())
        return;
      localView.getParent().requestDisallowInterceptTouchEvent(true);
      long l = SystemClock.uptimeMillis();
      MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      localView.onTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      this.mForwarding = true;
      this.mWasLongPress = true;
    }

    private boolean onTouchForwarded(MotionEvent paramMotionEvent)
    {
      int i = 1;
      View localView = this.mSrc;
      ListPopupWindow localListPopupWindow = getPopup();
      if ((localListPopupWindow == null) || (!localListPopupWindow.isShowing()));
      ListPopupWindow.DropDownListView localDropDownListView;
      do
      {
        return false;
        localDropDownListView = localListPopupWindow.mDropDownList;
      }
      while ((localDropDownListView == null) || (!localDropDownListView.isShown()));
      MotionEvent localMotionEvent = MotionEvent.obtainNoHistory(paramMotionEvent);
      toGlobalMotionEvent(localView, localMotionEvent);
      toLocalMotionEvent(localDropDownListView, localMotionEvent);
      boolean bool = localDropDownListView.onForwardedEvent(localMotionEvent, this.mActivePointerId);
      localMotionEvent.recycle();
      int j = MotionEventCompat.getActionMasked(paramMotionEvent);
      if ((j != i) && (j != 3))
      {
        int k = i;
        if ((!bool) || (k == 0))
          break label128;
      }
      while (true)
      {
        return i;
        int m = 0;
        break;
        label128: i = 0;
      }
    }

    private boolean onTouchObserved(MotionEvent paramMotionEvent)
    {
      View localView = this.mSrc;
      if (!localView.isEnabled());
      int i;
      do
      {
        return false;
        switch (MotionEventCompat.getActionMasked(paramMotionEvent))
        {
        default:
          return false;
        case 0:
          this.mActivePointerId = paramMotionEvent.getPointerId(0);
          this.mWasLongPress = false;
          if (this.mDisallowIntercept == null)
            this.mDisallowIntercept = new DisallowIntercept(null);
          localView.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
          if (this.mTriggerLongPress == null)
            this.mTriggerLongPress = new TriggerLongPress(null);
          localView.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
          return false;
        case 2:
          i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
        case 1:
        case 3:
        }
      }
      while ((i < 0) || (pointInView(localView, paramMotionEvent.getX(i), paramMotionEvent.getY(i), this.mScaledTouchSlop)));
      clearCallbacks();
      localView.getParent().requestDisallowInterceptTouchEvent(true);
      return true;
      clearCallbacks();
      return false;
    }

    private static boolean pointInView(View paramView, float paramFloat1, float paramFloat2, float paramFloat3)
    {
      return (paramFloat1 >= -paramFloat3) && (paramFloat2 >= -paramFloat3) && (paramFloat1 < paramFloat3 + (paramView.getRight() - paramView.getLeft())) && (paramFloat2 < paramFloat3 + (paramView.getBottom() - paramView.getTop()));
    }

    private boolean toGlobalMotionEvent(View paramView, MotionEvent paramMotionEvent)
    {
      int[] arrayOfInt = this.mTmpLocation;
      paramView.getLocationOnScreen(arrayOfInt);
      paramMotionEvent.offsetLocation(arrayOfInt[0], arrayOfInt[1]);
      return true;
    }

    private boolean toLocalMotionEvent(View paramView, MotionEvent paramMotionEvent)
    {
      int[] arrayOfInt = this.mTmpLocation;
      paramView.getLocationOnScreen(arrayOfInt);
      paramMotionEvent.offsetLocation(-arrayOfInt[0], -arrayOfInt[1]);
      return true;
    }

    public abstract ListPopupWindow getPopup();

    protected boolean onForwardingStarted()
    {
      ListPopupWindow localListPopupWindow = getPopup();
      if ((localListPopupWindow != null) && (!localListPopupWindow.isShowing()))
        localListPopupWindow.show();
      return true;
    }

    protected boolean onForwardingStopped()
    {
      ListPopupWindow localListPopupWindow = getPopup();
      if ((localListPopupWindow != null) && (localListPopupWindow.isShowing()))
        localListPopupWindow.dismiss();
      return true;
    }

    public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      boolean bool1 = this.mForwarding;
      boolean bool2;
      if (bool1)
        if (this.mWasLongPress)
          bool2 = onTouchForwarded(paramMotionEvent);
      label137: 
      while (true)
      {
        this.mForwarding = bool2;
        boolean bool3;
        if (!bool2)
        {
          bool3 = false;
          if (!bool1);
        }
        else
        {
          bool3 = true;
        }
        return bool3;
        if ((onTouchForwarded(paramMotionEvent)) || (!onForwardingStopped()));
        for (bool2 = true; ; bool2 = false)
          break;
        if ((onTouchObserved(paramMotionEvent)) && (onForwardingStarted()));
        for (bool2 = true; ; bool2 = false)
        {
          if (!bool2)
            break label137;
          long l = SystemClock.uptimeMillis();
          MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
          this.mSrc.onTouchEvent(localMotionEvent);
          localMotionEvent.recycle();
          break;
        }
      }
    }

    private class DisallowIntercept
      implements Runnable
    {
      private DisallowIntercept()
      {
      }

      public void run()
      {
        ListPopupWindow.ForwardingListener.this.mSrc.getParent().requestDisallowInterceptTouchEvent(true);
      }
    }

    private class TriggerLongPress
      implements Runnable
    {
      private TriggerLongPress()
      {
      }

      public void run()
      {
        ListPopupWindow.ForwardingListener.this.onLongPress();
      }
    }
  }

  private class ListSelectorHider
    implements Runnable
  {
    private ListSelectorHider()
    {
    }

    public void run()
    {
      ListPopupWindow.this.clearListSelection();
    }
  }

  private class PopupDataSetObserver extends DataSetObserver
  {
    private PopupDataSetObserver()
    {
    }

    public void onChanged()
    {
      if (ListPopupWindow.this.isShowing())
        ListPopupWindow.this.show();
    }

    public void onInvalidated()
    {
      ListPopupWindow.this.dismiss();
    }
  }

  private class PopupScrollListener
    implements AbsListView.OnScrollListener
  {
    private PopupScrollListener()
    {
    }

    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
    {
      if ((paramInt == 1) && (!ListPopupWindow.this.isInputMethodNotNeeded()) && (ListPopupWindow.this.mPopup.getContentView() != null))
      {
        ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
        ListPopupWindow.this.mResizePopupRunnable.run();
      }
    }
  }

  private class PopupTouchInterceptor
    implements View.OnTouchListener
  {
    private PopupTouchInterceptor()
    {
    }

    public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      int j = (int)paramMotionEvent.getX();
      int k = (int)paramMotionEvent.getY();
      if ((i == 0) && (ListPopupWindow.this.mPopup != null) && (ListPopupWindow.this.mPopup.isShowing()) && (j >= 0) && (j < ListPopupWindow.this.mPopup.getWidth()) && (k >= 0) && (k < ListPopupWindow.this.mPopup.getHeight()))
        ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250L);
      while (true)
      {
        return false;
        if (i == 1)
          ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
      }
    }
  }

  private class ResizePopupRunnable
    implements Runnable
  {
    private ResizePopupRunnable()
    {
    }

    public void run()
    {
      if ((ListPopupWindow.this.mDropDownList != null) && (ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount()) && (ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum))
      {
        ListPopupWindow.this.mPopup.setInputMethodMode(2);
        ListPopupWindow.this.show();
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ListPopupWindow
 * JD-Core Version:    0.6.2
 */