package android.support.v7.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.dimen;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.view.CollapsibleActionView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView extends LinearLayoutCompat
  implements CollapsibleActionView
{
  private static final boolean DBG = false;
  static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER;
  private static final String IME_OPTION_NO_MICROPHONE = "nm";
  private static final boolean IS_AT_LEAST_FROYO = false;
  private static final String LOG_TAG = "SearchView";
  private Bundle mAppSearchData;
  private boolean mClearingFocus;
  private final ImageView mCloseButton;
  private final ImageView mCollapsedIcon;
  private int mCollapsedImeOptions;
  private final CharSequence mDefaultQueryHint;
  private final View mDropDownAnchor;
  private boolean mExpandedInActionView;
  private final ImageView mGoButton;
  private boolean mIconified;
  private boolean mIconifiedByDefault;
  private int mMaxWidth;
  private CharSequence mOldQueryText;
  private final View.OnClickListener mOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (paramAnonymousView == SearchView.this.mSearchButton)
        SearchView.this.onSearchClicked();
      do
      {
        return;
        if (paramAnonymousView == SearchView.this.mCloseButton)
        {
          SearchView.this.onCloseClicked();
          return;
        }
        if (paramAnonymousView == SearchView.this.mGoButton)
        {
          SearchView.this.onSubmitQuery();
          return;
        }
        if (paramAnonymousView == SearchView.this.mVoiceButton)
        {
          SearchView.this.onVoiceClicked();
          return;
        }
      }
      while (paramAnonymousView != SearchView.this.mSearchSrcTextView);
      SearchView.this.forceSuggestionQuery();
    }
  };
  private OnCloseListener mOnCloseListener;
  private final TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener()
  {
    public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
    {
      SearchView.this.onSubmitQuery();
      return true;
    }
  };
  private final AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      SearchView.this.onItemClicked(paramAnonymousInt, 0, null);
    }
  };
  private final AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener()
  {
    public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      SearchView.this.onItemSelected(paramAnonymousInt);
    }

    public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
    {
    }
  };
  private OnQueryTextListener mOnQueryChangeListener;
  private View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
  private View.OnClickListener mOnSearchClickListener;
  private OnSuggestionListener mOnSuggestionListener;
  private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache = new WeakHashMap();
  private CharSequence mQueryHint;
  private boolean mQueryRefinement;
  private Runnable mReleaseCursorRunnable = new Runnable()
  {
    public void run()
    {
      if ((SearchView.this.mSuggestionsAdapter != null) && ((SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter)))
        SearchView.this.mSuggestionsAdapter.changeCursor(null);
    }
  };
  private final ImageView mSearchButton;
  private final View mSearchEditFrame;
  private final Drawable mSearchHintIcon;
  private final View mSearchPlate;
  private final SearchAutoComplete mSearchSrcTextView;
  private SearchableInfo mSearchable;
  private Runnable mShowImeRunnable = new Runnable()
  {
    public void run()
    {
      InputMethodManager localInputMethodManager = (InputMethodManager)SearchView.this.getContext().getSystemService("input_method");
      if (localInputMethodManager != null)
        SearchView.HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(localInputMethodManager, SearchView.this, 0);
    }
  };
  private final View mSubmitArea;
  private boolean mSubmitButtonEnabled;
  private final int mSuggestionCommitIconResId;
  private final int mSuggestionRowLayout;
  private CursorAdapter mSuggestionsAdapter;
  View.OnKeyListener mTextKeyListener = new View.OnKeyListener()
  {
    public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
    {
      if (SearchView.this.mSearchable == null);
      do
      {
        return false;
        if ((SearchView.this.mSearchSrcTextView.isPopupShowing()) && (SearchView.this.mSearchSrcTextView.getListSelection() != -1))
          return SearchView.this.onSuggestionsKey(paramAnonymousView, paramAnonymousInt, paramAnonymousKeyEvent);
      }
      while ((SearchView.SearchAutoComplete.access$1600(SearchView.this.mSearchSrcTextView)) || (!KeyEventCompat.hasNoModifiers(paramAnonymousKeyEvent)) || (paramAnonymousKeyEvent.getAction() != 1) || (paramAnonymousInt != 66));
      paramAnonymousView.cancelLongPress();
      SearchView.this.launchQuerySearch(0, null, SearchView.this.mSearchSrcTextView.getText().toString());
      return true;
    }
  };
  private TextWatcher mTextWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
    }

    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      SearchView.this.onTextChanged(paramAnonymousCharSequence);
    }
  };
  private final TintManager mTintManager;
  private final Runnable mUpdateDrawableStateRunnable = new Runnable()
  {
    public void run()
    {
      SearchView.this.updateFocusedState();
    }
  };
  private CharSequence mUserQuery;
  private final Intent mVoiceAppSearchIntent;
  private final ImageView mVoiceButton;
  private boolean mVoiceButtonEnabled;
  private final Intent mVoiceWebSearchIntent;

  static
  {
    if (Build.VERSION.SDK_INT >= 8);
    for (boolean bool = true; ; bool = false)
    {
      IS_AT_LEAST_FROYO = bool;
      HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
      return;
    }
  }

  public SearchView(Context paramContext)
  {
    this(paramContext, null);
  }

  public SearchView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.searchViewStyle);
  }

  public SearchView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.SearchView, paramInt, 0);
    this.mTintManager = localTintTypedArray.getTintManager();
    LayoutInflater.from(paramContext).inflate(localTintTypedArray.getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), this, true);
    this.mSearchSrcTextView = ((SearchAutoComplete)findViewById(R.id.search_src_text));
    this.mSearchSrcTextView.setSearchView(this);
    this.mSearchEditFrame = findViewById(R.id.search_edit_frame);
    this.mSearchPlate = findViewById(R.id.search_plate);
    this.mSubmitArea = findViewById(R.id.submit_area);
    this.mSearchButton = ((ImageView)findViewById(R.id.search_button));
    this.mGoButton = ((ImageView)findViewById(R.id.search_go_btn));
    this.mCloseButton = ((ImageView)findViewById(R.id.search_close_btn));
    this.mVoiceButton = ((ImageView)findViewById(R.id.search_voice_btn));
    this.mCollapsedIcon = ((ImageView)findViewById(R.id.search_mag_icon));
    this.mSearchPlate.setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_queryBackground));
    this.mSubmitArea.setBackgroundDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_submitBackground));
    this.mSearchButton.setImageDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_searchIcon));
    this.mGoButton.setImageDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_goIcon));
    this.mCloseButton.setImageDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_closeIcon));
    this.mVoiceButton.setImageDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_voiceIcon));
    this.mCollapsedIcon.setImageDrawable(localTintTypedArray.getDrawable(R.styleable.SearchView_searchIcon));
    this.mSearchHintIcon = localTintTypedArray.getDrawable(R.styleable.SearchView_searchHintIcon);
    this.mSuggestionRowLayout = localTintTypedArray.getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line);
    this.mSuggestionCommitIconResId = localTintTypedArray.getResourceId(R.styleable.SearchView_commitIcon, 0);
    this.mSearchButton.setOnClickListener(this.mOnClickListener);
    this.mCloseButton.setOnClickListener(this.mOnClickListener);
    this.mGoButton.setOnClickListener(this.mOnClickListener);
    this.mVoiceButton.setOnClickListener(this.mOnClickListener);
    this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
    this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
    this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
    this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
    this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
    this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
    this.mSearchSrcTextView.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (SearchView.this.mOnQueryTextFocusChangeListener != null)
          SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, paramAnonymousBoolean);
      }
    });
    setIconifiedByDefault(localTintTypedArray.getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
    int i = localTintTypedArray.getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
    if (i != -1)
      setMaxWidth(i);
    this.mDefaultQueryHint = localTintTypedArray.getText(R.styleable.SearchView_defaultQueryHint);
    this.mQueryHint = localTintTypedArray.getText(R.styleable.SearchView_queryHint);
    int j = localTintTypedArray.getInt(R.styleable.SearchView_android_imeOptions, -1);
    if (j != -1)
      setImeOptions(j);
    int k = localTintTypedArray.getInt(R.styleable.SearchView_android_inputType, -1);
    if (k != -1)
      setInputType(k);
    setFocusable(localTintTypedArray.getBoolean(R.styleable.SearchView_android_focusable, true));
    localTintTypedArray.recycle();
    this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
    this.mVoiceWebSearchIntent.addFlags(268435456);
    this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
    this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
    this.mVoiceAppSearchIntent.addFlags(268435456);
    this.mDropDownAnchor = findViewById(this.mSearchSrcTextView.getDropDownAnchor());
    if (this.mDropDownAnchor != null)
    {
      if (Build.VERSION.SDK_INT < 11)
        break label798;
      addOnLayoutChangeListenerToDropDownAnchorSDK11();
    }
    while (true)
    {
      updateViewsVisibility(this.mIconifiedByDefault);
      updateQueryHint();
      return;
      label798: addOnLayoutChangeListenerToDropDownAnchorBase();
    }
  }

  private void addOnLayoutChangeListenerToDropDownAnchorBase()
  {
    this.mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        SearchView.this.adjustDropDownSizeAndPosition();
      }
    });
  }

  @TargetApi(11)
  private void addOnLayoutChangeListenerToDropDownAnchorSDK11()
  {
    this.mDropDownAnchor.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
    {
      public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
      {
        SearchView.this.adjustDropDownSizeAndPosition();
      }
    });
  }

  private void adjustDropDownSizeAndPosition()
  {
    int i;
    Rect localRect;
    int j;
    if (this.mDropDownAnchor.getWidth() > 1)
    {
      Resources localResources = getContext().getResources();
      i = this.mSearchPlate.getPaddingLeft();
      localRect = new Rect();
      boolean bool = ViewUtils.isLayoutRtl(this);
      if (!this.mIconifiedByDefault)
        break label132;
      j = localResources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) + localResources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left);
      this.mSearchSrcTextView.getDropDownBackground().getPadding(localRect);
      if (!bool)
        break label138;
    }
    label132: label138: for (int k = -localRect.left; ; k = i - (j + localRect.left))
    {
      this.mSearchSrcTextView.setDropDownHorizontalOffset(k);
      int m = j + (this.mDropDownAnchor.getWidth() + localRect.left + localRect.right) - i;
      this.mSearchSrcTextView.setDropDownWidth(m);
      return;
      j = 0;
      break;
    }
  }

  private Intent createIntent(String paramString1, Uri paramUri, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    Intent localIntent = new Intent(paramString1);
    localIntent.addFlags(268435456);
    if (paramUri != null)
      localIntent.setData(paramUri);
    localIntent.putExtra("user_query", this.mUserQuery);
    if (paramString3 != null)
      localIntent.putExtra("query", paramString3);
    if (paramString2 != null)
      localIntent.putExtra("intent_extra_data_key", paramString2);
    if (this.mAppSearchData != null)
      localIntent.putExtra("app_data", this.mAppSearchData);
    if (paramInt != 0)
    {
      localIntent.putExtra("action_key", paramInt);
      localIntent.putExtra("action_msg", paramString4);
    }
    if (IS_AT_LEAST_FROYO)
      localIntent.setComponent(this.mSearchable.getSearchActivity());
    return localIntent;
  }

  private Intent createIntentFromSuggestion(Cursor paramCursor, int paramInt, String paramString)
  {
    try
    {
      str1 = SuggestionsAdapter.getColumnString(paramCursor, "suggest_intent_action");
      if ((str1 == null) && (Build.VERSION.SDK_INT >= 8))
      {
        str1 = this.mSearchable.getSuggestIntentAction();
        break label218;
        str2 = SuggestionsAdapter.getColumnString(paramCursor, "suggest_intent_data");
        if ((IS_AT_LEAST_FROYO) && (str2 == null))
          str2 = this.mSearchable.getSuggestIntentData();
        if (str2 == null)
          break label231;
        String str3 = SuggestionsAdapter.getColumnString(paramCursor, "suggest_intent_data_id");
        if (str3 == null)
          break label231;
        str2 = str2 + "/" + Uri.encode(str3);
        break label231;
        while (true)
        {
          String str4 = SuggestionsAdapter.getColumnString(paramCursor, "suggest_intent_query");
          return createIntent(str1, (Uri)localObject, SuggestionsAdapter.getColumnString(paramCursor, "suggest_intent_extra_data"), str4, paramInt, paramString);
          Uri localUri = Uri.parse(str2);
          localObject = localUri;
        }
      }
    }
    catch (RuntimeException localRuntimeException1)
    {
      while (true)
      {
        String str1;
        String str2;
        Object localObject;
        try
        {
          int j = paramCursor.getPosition();
          i = j;
          Log.w("SearchView", "Search suggestions cursor at row " + i + " returned exception.", localRuntimeException1);
          return null;
        }
        catch (RuntimeException localRuntimeException2)
        {
          int i = -1;
          continue;
        }
        label218: if (str1 == null)
        {
          str1 = "android.intent.action.SEARCH";
          continue;
          label231: if (str2 == null)
            localObject = null;
        }
      }
    }
  }

  @TargetApi(8)
  private Intent createVoiceAppSearchIntent(Intent paramIntent, SearchableInfo paramSearchableInfo)
  {
    ComponentName localComponentName = paramSearchableInfo.getSearchActivity();
    Intent localIntent1 = new Intent("android.intent.action.SEARCH");
    localIntent1.setComponent(localComponentName);
    PendingIntent localPendingIntent = PendingIntent.getActivity(getContext(), 0, localIntent1, 1073741824);
    Bundle localBundle = new Bundle();
    if (this.mAppSearchData != null)
      localBundle.putParcelable("app_data", this.mAppSearchData);
    Intent localIntent2 = new Intent(paramIntent);
    String str1 = "free_form";
    int i = 1;
    int j = Build.VERSION.SDK_INT;
    String str2 = null;
    String str3 = null;
    if (j >= 8)
    {
      Resources localResources = getResources();
      if (paramSearchableInfo.getVoiceLanguageModeId() != 0)
        str1 = localResources.getString(paramSearchableInfo.getVoiceLanguageModeId());
      int k = paramSearchableInfo.getVoicePromptTextId();
      str3 = null;
      if (k != 0)
        str3 = localResources.getString(paramSearchableInfo.getVoicePromptTextId());
      int m = paramSearchableInfo.getVoiceLanguageId();
      str2 = null;
      if (m != 0)
        str2 = localResources.getString(paramSearchableInfo.getVoiceLanguageId());
      if (paramSearchableInfo.getVoiceMaxResults() != 0)
        i = paramSearchableInfo.getVoiceMaxResults();
    }
    localIntent2.putExtra("android.speech.extra.LANGUAGE_MODEL", str1);
    localIntent2.putExtra("android.speech.extra.PROMPT", str3);
    localIntent2.putExtra("android.speech.extra.LANGUAGE", str2);
    localIntent2.putExtra("android.speech.extra.MAX_RESULTS", i);
    if (localComponentName == null);
    for (String str4 = null; ; str4 = localComponentName.flattenToShortString())
    {
      localIntent2.putExtra("calling_package", str4);
      localIntent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", localPendingIntent);
      localIntent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", localBundle);
      return localIntent2;
    }
  }

  @TargetApi(8)
  private Intent createVoiceWebSearchIntent(Intent paramIntent, SearchableInfo paramSearchableInfo)
  {
    Intent localIntent = new Intent(paramIntent);
    ComponentName localComponentName = paramSearchableInfo.getSearchActivity();
    if (localComponentName == null);
    for (String str = null; ; str = localComponentName.flattenToShortString())
    {
      localIntent.putExtra("calling_package", str);
      return localIntent;
    }
  }

  private void dismissSuggestions()
  {
    this.mSearchSrcTextView.dismissDropDown();
  }

  private void forceSuggestionQuery()
  {
    HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView);
    HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView);
  }

  private CharSequence getDecoratedHint(CharSequence paramCharSequence)
  {
    if ((!this.mIconifiedByDefault) || (this.mSearchHintIcon == null))
      return paramCharSequence;
    int i = (int)(1.25D * this.mSearchSrcTextView.getTextSize());
    this.mSearchHintIcon.setBounds(0, 0, i, i);
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder("   ");
    localSpannableStringBuilder.setSpan(new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
    localSpannableStringBuilder.append(paramCharSequence);
    return localSpannableStringBuilder;
  }

  private int getPreferredWidth()
  {
    return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
  }

  @TargetApi(8)
  private boolean hasVoiceSearch()
  {
    SearchableInfo localSearchableInfo = this.mSearchable;
    boolean bool1 = false;
    Intent localIntent;
    if (localSearchableInfo != null)
    {
      boolean bool2 = this.mSearchable.getVoiceSearchEnabled();
      bool1 = false;
      if (bool2)
      {
        if (!this.mSearchable.getVoiceSearchLaunchWebSearch())
          break label76;
        localIntent = this.mVoiceWebSearchIntent;
      }
    }
    while (true)
    {
      bool1 = false;
      if (localIntent != null)
      {
        ResolveInfo localResolveInfo = getContext().getPackageManager().resolveActivity(localIntent, 65536);
        bool1 = false;
        if (localResolveInfo != null)
          bool1 = true;
      }
      return bool1;
      label76: boolean bool3 = this.mSearchable.getVoiceSearchLaunchRecognizer();
      localIntent = null;
      if (bool3)
        localIntent = this.mVoiceAppSearchIntent;
    }
  }

  static boolean isLandscapeMode(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().orientation == 2;
  }

  private boolean isSubmitAreaEnabled()
  {
    return ((this.mSubmitButtonEnabled) || (this.mVoiceButtonEnabled)) && (!isIconified());
  }

  private void launchIntent(Intent paramIntent)
  {
    if (paramIntent == null)
      return;
    try
    {
      getContext().startActivity(paramIntent);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.e("SearchView", "Failed launch activity: " + paramIntent, localRuntimeException);
    }
  }

  private void launchQuerySearch(int paramInt, String paramString1, String paramString2)
  {
    Intent localIntent = createIntent("android.intent.action.SEARCH", null, null, paramString2, paramInt, paramString1);
    getContext().startActivity(localIntent);
  }

  private boolean launchSuggestion(int paramInt1, int paramInt2, String paramString)
  {
    Cursor localCursor = this.mSuggestionsAdapter.getCursor();
    if ((localCursor != null) && (localCursor.moveToPosition(paramInt1)))
    {
      launchIntent(createIntentFromSuggestion(localCursor, paramInt2, paramString));
      return true;
    }
    return false;
  }

  private void onCloseClicked()
  {
    if (TextUtils.isEmpty(this.mSearchSrcTextView.getText()))
    {
      if ((this.mIconifiedByDefault) && ((this.mOnCloseListener == null) || (!this.mOnCloseListener.onClose())))
      {
        clearFocus();
        updateViewsVisibility(true);
      }
      return;
    }
    this.mSearchSrcTextView.setText("");
    this.mSearchSrcTextView.requestFocus();
    setImeVisibility(true);
  }

  private boolean onItemClicked(int paramInt1, int paramInt2, String paramString)
  {
    boolean bool1;
    if (this.mOnSuggestionListener != null)
    {
      boolean bool2 = this.mOnSuggestionListener.onSuggestionClick(paramInt1);
      bool1 = false;
      if (bool2);
    }
    else
    {
      launchSuggestion(paramInt1, 0, null);
      setImeVisibility(false);
      dismissSuggestions();
      bool1 = true;
    }
    return bool1;
  }

  private boolean onItemSelected(int paramInt)
  {
    if ((this.mOnSuggestionListener == null) || (!this.mOnSuggestionListener.onSuggestionSelect(paramInt)))
    {
      rewriteQueryFromSuggestion(paramInt);
      return true;
    }
    return false;
  }

  private void onSearchClicked()
  {
    updateViewsVisibility(false);
    this.mSearchSrcTextView.requestFocus();
    setImeVisibility(true);
    if (this.mOnSearchClickListener != null)
      this.mOnSearchClickListener.onClick(this);
  }

  private void onSubmitQuery()
  {
    Editable localEditable = this.mSearchSrcTextView.getText();
    if ((localEditable != null) && (TextUtils.getTrimmedLength(localEditable) > 0) && ((this.mOnQueryChangeListener == null) || (!this.mOnQueryChangeListener.onQueryTextSubmit(localEditable.toString()))))
    {
      if (this.mSearchable != null)
        launchQuerySearch(0, null, localEditable.toString());
      setImeVisibility(false);
      dismissSuggestions();
    }
  }

  private boolean onSuggestionsKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.mSearchable == null);
    do
    {
      do
        return false;
      while ((this.mSuggestionsAdapter == null) || (paramKeyEvent.getAction() != 0) || (!KeyEventCompat.hasNoModifiers(paramKeyEvent)));
      if ((paramInt == 66) || (paramInt == 84) || (paramInt == 61))
        return onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
      if ((paramInt == 21) || (paramInt == 22))
      {
        if (paramInt == 21);
        for (int i = 0; ; i = this.mSearchSrcTextView.length())
        {
          this.mSearchSrcTextView.setSelection(i);
          this.mSearchSrcTextView.setListSelection(0);
          this.mSearchSrcTextView.clearListSelection();
          HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mSearchSrcTextView, true);
          return true;
        }
      }
    }
    while ((paramInt != 19) || (this.mSearchSrcTextView.getListSelection() != 0));
    return false;
  }

  private void onTextChanged(CharSequence paramCharSequence)
  {
    boolean bool1 = true;
    Editable localEditable = this.mSearchSrcTextView.getText();
    this.mUserQuery = localEditable;
    boolean bool2;
    if (!TextUtils.isEmpty(localEditable))
    {
      bool2 = bool1;
      updateSubmitButton(bool2);
      if (bool2)
        break label100;
    }
    while (true)
    {
      updateVoiceButton(bool1);
      updateCloseButton();
      updateSubmitArea();
      if ((this.mOnQueryChangeListener != null) && (!TextUtils.equals(paramCharSequence, this.mOldQueryText)))
        this.mOnQueryChangeListener.onQueryTextChange(paramCharSequence.toString());
      this.mOldQueryText = paramCharSequence.toString();
      return;
      bool2 = false;
      break;
      label100: bool1 = false;
    }
  }

  @TargetApi(8)
  private void onVoiceClicked()
  {
    if (this.mSearchable == null);
    SearchableInfo localSearchableInfo;
    do
    {
      return;
      localSearchableInfo = this.mSearchable;
      try
      {
        if (localSearchableInfo.getVoiceSearchLaunchWebSearch())
        {
          Intent localIntent2 = createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, localSearchableInfo);
          getContext().startActivity(localIntent2);
          return;
        }
      }
      catch (ActivityNotFoundException localActivityNotFoundException)
      {
        Log.w("SearchView", "Could not find voice search activity");
        return;
      }
    }
    while (!localSearchableInfo.getVoiceSearchLaunchRecognizer());
    Intent localIntent1 = createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, localSearchableInfo);
    getContext().startActivity(localIntent1);
  }

  private void postUpdateFocusedState()
  {
    post(this.mUpdateDrawableStateRunnable);
  }

  private void rewriteQueryFromSuggestion(int paramInt)
  {
    Editable localEditable = this.mSearchSrcTextView.getText();
    Cursor localCursor = this.mSuggestionsAdapter.getCursor();
    if (localCursor == null)
      return;
    if (localCursor.moveToPosition(paramInt))
    {
      CharSequence localCharSequence = this.mSuggestionsAdapter.convertToString(localCursor);
      if (localCharSequence != null)
      {
        setQuery(localCharSequence);
        return;
      }
      setQuery(localEditable);
      return;
    }
    setQuery(localEditable);
  }

  private void setImeVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
      post(this.mShowImeRunnable);
    InputMethodManager localInputMethodManager;
    do
    {
      return;
      removeCallbacks(this.mShowImeRunnable);
      localInputMethodManager = (InputMethodManager)getContext().getSystemService("input_method");
    }
    while (localInputMethodManager == null);
    localInputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
  }

  private void setQuery(CharSequence paramCharSequence)
  {
    this.mSearchSrcTextView.setText(paramCharSequence);
    SearchAutoComplete localSearchAutoComplete = this.mSearchSrcTextView;
    if (TextUtils.isEmpty(paramCharSequence));
    for (int i = 0; ; i = paramCharSequence.length())
    {
      localSearchAutoComplete.setSelection(i);
      return;
    }
  }

  private void updateCloseButton()
  {
    int i = 1;
    int j;
    label35: int k;
    label47: Drawable localDrawable;
    if (!TextUtils.isEmpty(this.mSearchSrcTextView.getText()))
    {
      j = i;
      if ((j == 0) && ((!this.mIconifiedByDefault) || (this.mExpandedInActionView)))
        break label90;
      ImageView localImageView = this.mCloseButton;
      k = 0;
      if (i == 0)
        break label95;
      localImageView.setVisibility(k);
      localDrawable = this.mCloseButton.getDrawable();
      if (localDrawable != null)
        if (j == 0)
          break label102;
    }
    label90: label95: label102: for (int[] arrayOfInt = ENABLED_STATE_SET; ; arrayOfInt = EMPTY_STATE_SET)
    {
      localDrawable.setState(arrayOfInt);
      return;
      j = 0;
      break;
      i = 0;
      break label35;
      k = 8;
      break label47;
    }
  }

  private void updateFocusedState()
  {
    if (this.mSearchSrcTextView.hasFocus());
    for (int[] arrayOfInt = FOCUSED_STATE_SET; ; arrayOfInt = EMPTY_STATE_SET)
    {
      Drawable localDrawable1 = this.mSearchPlate.getBackground();
      if (localDrawable1 != null)
        localDrawable1.setState(arrayOfInt);
      Drawable localDrawable2 = this.mSubmitArea.getBackground();
      if (localDrawable2 != null)
        localDrawable2.setState(arrayOfInt);
      invalidate();
      return;
    }
  }

  private void updateQueryHint()
  {
    Object localObject = getQueryHint();
    SearchAutoComplete localSearchAutoComplete = this.mSearchSrcTextView;
    if (localObject == null)
      localObject = "";
    localSearchAutoComplete.setHint(getDecoratedHint((CharSequence)localObject));
  }

  @TargetApi(8)
  private void updateSearchAutoComplete()
  {
    int i = 1;
    this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
    this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
    int j = this.mSearchable.getInputType();
    if ((j & 0xF) == i)
    {
      j &= -65537;
      if (this.mSearchable.getSuggestAuthority() != null)
        j = 0x80000 | (j | 0x10000);
    }
    this.mSearchSrcTextView.setInputType(j);
    if (this.mSuggestionsAdapter != null)
      this.mSuggestionsAdapter.changeCursor(null);
    if (this.mSearchable.getSuggestAuthority() != null)
    {
      this.mSuggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
      this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
      SuggestionsAdapter localSuggestionsAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
      if (this.mQueryRefinement)
        i = 2;
      localSuggestionsAdapter.setQueryRefinement(i);
    }
  }

  private void updateSubmitArea()
  {
    int i = 8;
    if ((isSubmitAreaEnabled()) && ((this.mGoButton.getVisibility() == 0) || (this.mVoiceButton.getVisibility() == 0)))
      i = 0;
    this.mSubmitArea.setVisibility(i);
  }

  private void updateSubmitButton(boolean paramBoolean)
  {
    int i = 8;
    if ((this.mSubmitButtonEnabled) && (isSubmitAreaEnabled()) && (hasFocus()) && ((paramBoolean) || (!this.mVoiceButtonEnabled)))
      i = 0;
    this.mGoButton.setVisibility(i);
  }

  private void updateViewsVisibility(boolean paramBoolean)
  {
    boolean bool1 = true;
    int i = 8;
    this.mIconified = paramBoolean;
    int j;
    boolean bool2;
    label33: int k;
    if (paramBoolean)
    {
      j = 0;
      if (TextUtils.isEmpty(this.mSearchSrcTextView.getText()))
        break label112;
      bool2 = bool1;
      this.mSearchButton.setVisibility(j);
      updateSubmitButton(bool2);
      View localView = this.mSearchEditFrame;
      if (!paramBoolean)
        break label118;
      k = i;
      label61: localView.setVisibility(k);
      ImageView localImageView = this.mCollapsedIcon;
      if (!this.mIconifiedByDefault)
        break label124;
      label81: localImageView.setVisibility(i);
      updateCloseButton();
      if (bool2)
        break label129;
    }
    while (true)
    {
      updateVoiceButton(bool1);
      updateSubmitArea();
      return;
      j = i;
      break;
      label112: bool2 = false;
      break label33;
      label118: k = 0;
      break label61;
      label124: i = 0;
      break label81;
      label129: bool1 = false;
    }
  }

  private void updateVoiceButton(boolean paramBoolean)
  {
    int i = 8;
    if ((this.mVoiceButtonEnabled) && (!isIconified()) && (paramBoolean))
    {
      i = 0;
      this.mGoButton.setVisibility(8);
    }
    this.mVoiceButton.setVisibility(i);
  }

  public void clearFocus()
  {
    this.mClearingFocus = true;
    setImeVisibility(false);
    super.clearFocus();
    this.mSearchSrcTextView.clearFocus();
    this.mClearingFocus = false;
  }

  public int getImeOptions()
  {
    return this.mSearchSrcTextView.getImeOptions();
  }

  public int getInputType()
  {
    return this.mSearchSrcTextView.getInputType();
  }

  public int getMaxWidth()
  {
    return this.mMaxWidth;
  }

  public CharSequence getQuery()
  {
    return this.mSearchSrcTextView.getText();
  }

  public CharSequence getQueryHint()
  {
    if (this.mQueryHint != null)
      return this.mQueryHint;
    if ((IS_AT_LEAST_FROYO) && (this.mSearchable != null) && (this.mSearchable.getHintId() != 0))
      return getContext().getText(this.mSearchable.getHintId());
    return this.mDefaultQueryHint;
  }

  int getSuggestionCommitIconResId()
  {
    return this.mSuggestionCommitIconResId;
  }

  int getSuggestionRowLayout()
  {
    return this.mSuggestionRowLayout;
  }

  public CursorAdapter getSuggestionsAdapter()
  {
    return this.mSuggestionsAdapter;
  }

  public boolean isIconfiedByDefault()
  {
    return this.mIconifiedByDefault;
  }

  public boolean isIconified()
  {
    return this.mIconified;
  }

  public boolean isQueryRefinementEnabled()
  {
    return this.mQueryRefinement;
  }

  public boolean isSubmitButtonEnabled()
  {
    return this.mSubmitButtonEnabled;
  }

  public void onActionViewCollapsed()
  {
    setQuery("", false);
    clearFocus();
    updateViewsVisibility(true);
    this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
    this.mExpandedInActionView = false;
  }

  public void onActionViewExpanded()
  {
    if (this.mExpandedInActionView)
      return;
    this.mExpandedInActionView = true;
    this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
    this.mSearchSrcTextView.setImeOptions(0x2000000 | this.mCollapsedImeOptions);
    this.mSearchSrcTextView.setText("");
    setIconified(false);
  }

  protected void onDetachedFromWindow()
  {
    removeCallbacks(this.mUpdateDrawableStateRunnable);
    post(this.mReleaseCursorRunnable);
    super.onDetachedFromWindow();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (isIconified())
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    switch (i)
    {
    default:
    case -2147483648:
    case 1073741824:
      while (true)
      {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), paramInt2);
        return;
        if (this.mMaxWidth > 0)
        {
          j = Math.min(this.mMaxWidth, j);
        }
        else
        {
          j = Math.min(getPreferredWidth(), j);
          continue;
          if (this.mMaxWidth > 0)
            j = Math.min(this.mMaxWidth, j);
        }
      }
    case 0:
    }
    if (this.mMaxWidth > 0);
    for (j = this.mMaxWidth; ; j = getPreferredWidth())
      break;
  }

  void onQueryRefine(CharSequence paramCharSequence)
  {
    setQuery(paramCharSequence);
  }

  void onTextFocusChanged()
  {
    updateViewsVisibility(isIconified());
    postUpdateFocusedState();
    if (this.mSearchSrcTextView.hasFocus())
      forceSuggestionQuery();
  }

  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    postUpdateFocusedState();
  }

  public boolean requestFocus(int paramInt, Rect paramRect)
  {
    boolean bool;
    if (this.mClearingFocus)
      bool = false;
    do
    {
      return bool;
      if (!isFocusable())
        return false;
      if (isIconified())
        break;
      bool = this.mSearchSrcTextView.requestFocus(paramInt, paramRect);
    }
    while (!bool);
    updateViewsVisibility(false);
    return bool;
    return super.requestFocus(paramInt, paramRect);
  }

  public void setAppSearchData(Bundle paramBundle)
  {
    this.mAppSearchData = paramBundle;
  }

  public void setIconified(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      onCloseClicked();
      return;
    }
    onSearchClicked();
  }

  public void setIconifiedByDefault(boolean paramBoolean)
  {
    if (this.mIconifiedByDefault == paramBoolean)
      return;
    this.mIconifiedByDefault = paramBoolean;
    updateViewsVisibility(paramBoolean);
    updateQueryHint();
  }

  public void setImeOptions(int paramInt)
  {
    this.mSearchSrcTextView.setImeOptions(paramInt);
  }

  public void setInputType(int paramInt)
  {
    this.mSearchSrcTextView.setInputType(paramInt);
  }

  public void setMaxWidth(int paramInt)
  {
    this.mMaxWidth = paramInt;
    requestLayout();
  }

  public void setOnCloseListener(OnCloseListener paramOnCloseListener)
  {
    this.mOnCloseListener = paramOnCloseListener;
  }

  public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener paramOnFocusChangeListener)
  {
    this.mOnQueryTextFocusChangeListener = paramOnFocusChangeListener;
  }

  public void setOnQueryTextListener(OnQueryTextListener paramOnQueryTextListener)
  {
    this.mOnQueryChangeListener = paramOnQueryTextListener;
  }

  public void setOnSearchClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mOnSearchClickListener = paramOnClickListener;
  }

  public void setOnSuggestionListener(OnSuggestionListener paramOnSuggestionListener)
  {
    this.mOnSuggestionListener = paramOnSuggestionListener;
  }

  public void setQuery(CharSequence paramCharSequence, boolean paramBoolean)
  {
    this.mSearchSrcTextView.setText(paramCharSequence);
    if (paramCharSequence != null)
    {
      this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
      this.mUserQuery = paramCharSequence;
    }
    if ((paramBoolean) && (!TextUtils.isEmpty(paramCharSequence)))
      onSubmitQuery();
  }

  public void setQueryHint(CharSequence paramCharSequence)
  {
    this.mQueryHint = paramCharSequence;
    updateQueryHint();
  }

  public void setQueryRefinementEnabled(boolean paramBoolean)
  {
    this.mQueryRefinement = paramBoolean;
    SuggestionsAdapter localSuggestionsAdapter;
    if ((this.mSuggestionsAdapter instanceof SuggestionsAdapter))
    {
      localSuggestionsAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
      if (!paramBoolean)
        break label35;
    }
    label35: for (int i = 2; ; i = 1)
    {
      localSuggestionsAdapter.setQueryRefinement(i);
      return;
    }
  }

  public void setSearchableInfo(SearchableInfo paramSearchableInfo)
  {
    this.mSearchable = paramSearchableInfo;
    if (this.mSearchable != null)
    {
      if (IS_AT_LEAST_FROYO)
        updateSearchAutoComplete();
      updateQueryHint();
    }
    if ((IS_AT_LEAST_FROYO) && (hasVoiceSearch()));
    for (boolean bool = true; ; bool = false)
    {
      this.mVoiceButtonEnabled = bool;
      if (this.mVoiceButtonEnabled)
        this.mSearchSrcTextView.setPrivateImeOptions("nm");
      updateViewsVisibility(isIconified());
      return;
    }
  }

  public void setSubmitButtonEnabled(boolean paramBoolean)
  {
    this.mSubmitButtonEnabled = paramBoolean;
    updateViewsVisibility(isIconified());
  }

  public void setSuggestionsAdapter(CursorAdapter paramCursorAdapter)
  {
    this.mSuggestionsAdapter = paramCursorAdapter;
    this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
  }

  private static class AutoCompleteTextViewReflector
  {
    private Method doAfterTextChanged;
    private Method doBeforeTextChanged;
    private Method ensureImeVisible;
    private Method showSoftInputUnchecked;

    AutoCompleteTextViewReflector()
    {
      try
      {
        this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
        this.doBeforeTextChanged.setAccessible(true);
        try
        {
          label27: this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
          this.doAfterTextChanged.setAccessible(true);
          try
          {
            label50: Class[] arrayOfClass2 = new Class[1];
            arrayOfClass2[0] = Boolean.TYPE;
            this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", arrayOfClass2);
            this.ensureImeVisible.setAccessible(true);
            try
            {
              label84: Class[] arrayOfClass1 = new Class[2];
              arrayOfClass1[0] = Integer.TYPE;
              arrayOfClass1[1] = ResultReceiver.class;
              this.showSoftInputUnchecked = InputMethodManager.class.getMethod("showSoftInputUnchecked", arrayOfClass1);
              this.showSoftInputUnchecked.setAccessible(true);
              return;
            }
            catch (NoSuchMethodException localNoSuchMethodException4)
            {
            }
          }
          catch (NoSuchMethodException localNoSuchMethodException3)
          {
            break label84;
          }
        }
        catch (NoSuchMethodException localNoSuchMethodException2)
        {
          break label50;
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException1)
      {
        break label27;
      }
    }

    void doAfterTextChanged(AutoCompleteTextView paramAutoCompleteTextView)
    {
      if (this.doAfterTextChanged != null);
      try
      {
        this.doAfterTextChanged.invoke(paramAutoCompleteTextView, new Object[0]);
        return;
      }
      catch (Exception localException)
      {
      }
    }

    void doBeforeTextChanged(AutoCompleteTextView paramAutoCompleteTextView)
    {
      if (this.doBeforeTextChanged != null);
      try
      {
        this.doBeforeTextChanged.invoke(paramAutoCompleteTextView, new Object[0]);
        return;
      }
      catch (Exception localException)
      {
      }
    }

    void ensureImeVisible(AutoCompleteTextView paramAutoCompleteTextView, boolean paramBoolean)
    {
      if (this.ensureImeVisible != null);
      try
      {
        Method localMethod = this.ensureImeVisible;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Boolean.valueOf(paramBoolean);
        localMethod.invoke(paramAutoCompleteTextView, arrayOfObject);
        return;
      }
      catch (Exception localException)
      {
      }
    }

    void showSoftInputUnchecked(InputMethodManager paramInputMethodManager, View paramView, int paramInt)
    {
      if (this.showSoftInputUnchecked != null)
        try
        {
          Method localMethod = this.showSoftInputUnchecked;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramInt);
          arrayOfObject[1] = null;
          localMethod.invoke(paramInputMethodManager, arrayOfObject);
          return;
        }
        catch (Exception localException)
        {
        }
      paramInputMethodManager.showSoftInput(paramView, paramInt);
    }
  }

  public static abstract interface OnCloseListener
  {
    public abstract boolean onClose();
  }

  public static abstract interface OnQueryTextListener
  {
    public abstract boolean onQueryTextChange(String paramString);

    public abstract boolean onQueryTextSubmit(String paramString);
  }

  public static abstract interface OnSuggestionListener
  {
    public abstract boolean onSuggestionClick(int paramInt);

    public abstract boolean onSuggestionSelect(int paramInt);
  }

  public static class SearchAutoComplete extends AppCompatAutoCompleteTextView
  {
    private SearchView mSearchView;
    private int mThreshold = getThreshold();

    public SearchAutoComplete(Context paramContext)
    {
      this(paramContext, null);
    }

    public SearchAutoComplete(Context paramContext, AttributeSet paramAttributeSet)
    {
      this(paramContext, paramAttributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public SearchAutoComplete(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
    }

    private boolean isEmpty()
    {
      return TextUtils.getTrimmedLength(getText()) == 0;
    }

    public boolean enoughToFilter()
    {
      return (this.mThreshold <= 0) || (super.enoughToFilter());
    }

    protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
    {
      super.onFocusChanged(paramBoolean, paramInt, paramRect);
      this.mSearchView.onTextFocusChanged();
    }

    public boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent)
    {
      if (paramInt == 4)
      {
        if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getRepeatCount() == 0))
        {
          KeyEvent.DispatcherState localDispatcherState2 = getKeyDispatcherState();
          if (localDispatcherState2 != null)
            localDispatcherState2.startTracking(paramKeyEvent, this);
          return true;
        }
        if (paramKeyEvent.getAction() == 1)
        {
          KeyEvent.DispatcherState localDispatcherState1 = getKeyDispatcherState();
          if (localDispatcherState1 != null)
            localDispatcherState1.handleUpEvent(paramKeyEvent);
          if ((paramKeyEvent.isTracking()) && (!paramKeyEvent.isCanceled()))
          {
            this.mSearchView.clearFocus();
            this.mSearchView.setImeVisibility(false);
            return true;
          }
        }
      }
      return super.onKeyPreIme(paramInt, paramKeyEvent);
    }

    public void onWindowFocusChanged(boolean paramBoolean)
    {
      super.onWindowFocusChanged(paramBoolean);
      if ((paramBoolean) && (this.mSearchView.hasFocus()) && (getVisibility() == 0))
      {
        ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput(this, 0);
        if (SearchView.isLandscapeMode(getContext()))
          SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
      }
    }

    public void performCompletion()
    {
    }

    protected void replaceText(CharSequence paramCharSequence)
    {
    }

    void setSearchView(SearchView paramSearchView)
    {
      this.mSearchView = paramSearchView;
    }

    public void setThreshold(int paramInt)
    {
      super.setThreshold(paramInt);
      this.mThreshold = paramInt;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.SearchView
 * JD-Core Version:    0.6.2
 */