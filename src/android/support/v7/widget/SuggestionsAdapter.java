package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter extends ResourceCursorAdapter
  implements View.OnClickListener
{
  private static final boolean DBG = false;
  static final int INVALID_INDEX = -1;
  private static final String LOG_TAG = "SuggestionsAdapter";
  private static final int QUERY_LIMIT = 50;
  static final int REFINE_ALL = 2;
  static final int REFINE_BY_ENTRY = 1;
  static final int REFINE_NONE;
  private boolean mClosed = false;
  private final int mCommitIconResId;
  private int mFlagsCol = -1;
  private int mIconName1Col = -1;
  private int mIconName2Col = -1;
  private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
  private final Context mProviderContext;
  private int mQueryRefinement = 1;
  private final SearchManager mSearchManager = (SearchManager)this.mContext.getSystemService("search");
  private final SearchView mSearchView;
  private final SearchableInfo mSearchable;
  private int mText1Col = -1;
  private int mText2Col = -1;
  private int mText2UrlCol = -1;
  private ColorStateList mUrlColor;

  public SuggestionsAdapter(Context paramContext, SearchView paramSearchView, SearchableInfo paramSearchableInfo, WeakHashMap<String, Drawable.ConstantState> paramWeakHashMap)
  {
    super(paramContext, paramSearchView.getSuggestionRowLayout(), null, true);
    this.mSearchView = paramSearchView;
    this.mSearchable = paramSearchableInfo;
    this.mCommitIconResId = paramSearchView.getSuggestionCommitIconResId();
    this.mProviderContext = paramContext;
    this.mOutsideDrawablesCache = paramWeakHashMap;
  }

  private Drawable checkIconCache(String paramString)
  {
    Drawable.ConstantState localConstantState = (Drawable.ConstantState)this.mOutsideDrawablesCache.get(paramString);
    if (localConstantState == null)
      return null;
    return localConstantState.newDrawable();
  }

  private CharSequence formatUrl(CharSequence paramCharSequence)
  {
    if (this.mUrlColor == null)
    {
      TypedValue localTypedValue = new TypedValue();
      this.mContext.getTheme().resolveAttribute(R.attr.textColorSearchUrl, localTypedValue, true);
      this.mUrlColor = this.mContext.getResources().getColorStateList(localTypedValue.resourceId);
    }
    SpannableString localSpannableString = new SpannableString(paramCharSequence);
    localSpannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, paramCharSequence.length(), 33);
    return localSpannableString;
  }

  private Drawable getActivityIcon(ComponentName paramComponentName)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    int i;
    Drawable localDrawable;
    do
    {
      ActivityInfo localActivityInfo;
      try
      {
        localActivityInfo = localPackageManager.getActivityInfo(paramComponentName, 128);
        i = localActivityInfo.getIconResource();
        if (i == 0)
        {
          localDrawable = null;
          return localDrawable;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.w("SuggestionsAdapter", localNameNotFoundException.toString());
        return null;
      }
      localDrawable = localPackageManager.getDrawable(paramComponentName.getPackageName(), i, localActivityInfo.applicationInfo);
    }
    while (localDrawable != null);
    Log.w("SuggestionsAdapter", "Invalid icon resource " + i + " for " + paramComponentName.flattenToShortString());
    return null;
  }

  private Drawable getActivityIconWithCache(ComponentName paramComponentName)
  {
    String str = paramComponentName.flattenToShortString();
    if (this.mOutsideDrawablesCache.containsKey(str))
    {
      Drawable.ConstantState localConstantState = (Drawable.ConstantState)this.mOutsideDrawablesCache.get(str);
      if (localConstantState == null)
        return null;
      return localConstantState.newDrawable(this.mProviderContext.getResources());
    }
    Drawable localDrawable = getActivityIcon(paramComponentName);
    if (localDrawable == null);
    for (Object localObject = null; ; localObject = localDrawable.getConstantState())
    {
      this.mOutsideDrawablesCache.put(str, localObject);
      return localDrawable;
    }
  }

  public static String getColumnString(Cursor paramCursor, String paramString)
  {
    return getStringOrNull(paramCursor, paramCursor.getColumnIndex(paramString));
  }

  private Drawable getDefaultIcon1(Cursor paramCursor)
  {
    Drawable localDrawable = getActivityIconWithCache(this.mSearchable.getSearchActivity());
    if (localDrawable != null)
      return localDrawable;
    return this.mContext.getPackageManager().getDefaultActivityIcon();
  }

  private Drawable getDrawable(Uri paramUri)
  {
    try
    {
      boolean bool = "android.resource".equals(paramUri.getScheme());
      if (bool)
        try
        {
          Drawable localDrawable2 = getDrawableFromResourceUri(paramUri);
          return localDrawable2;
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          throw new FileNotFoundException("Resource does not exist: " + paramUri);
        }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Log.w("SuggestionsAdapter", "Icon not found: " + paramUri + ", " + localFileNotFoundException.getMessage());
      return null;
    }
    InputStream localInputStream = this.mProviderContext.getContentResolver().openInputStream(paramUri);
    if (localInputStream == null)
      throw new FileNotFoundException("Failed to open " + paramUri);
    try
    {
      Drawable localDrawable1 = Drawable.createFromStream(localInputStream, null);
      try
      {
        localInputStream.close();
        return localDrawable1;
      }
      catch (IOException localIOException2)
      {
        Log.e("SuggestionsAdapter", "Error closing icon stream for " + paramUri, localIOException2);
        return localDrawable1;
      }
    }
    finally
    {
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        Log.e("SuggestionsAdapter", "Error closing icon stream for " + paramUri, localIOException1);
    }
  }

  private Drawable getDrawableFromResourceValue(String paramString)
  {
    Drawable localDrawable1;
    if ((paramString == null) || (paramString.length() == 0) || ("0".equals(paramString)))
      localDrawable1 = null;
    while (true)
    {
      return localDrawable1;
      try
      {
        int i = Integer.parseInt(paramString);
        String str = "android.resource://" + this.mProviderContext.getPackageName() + "/" + i;
        localDrawable1 = checkIconCache(str);
        if (localDrawable1 == null)
        {
          Drawable localDrawable3 = ContextCompat.getDrawable(this.mProviderContext, i);
          storeInIconCache(str, localDrawable3);
          return localDrawable3;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localDrawable1 = checkIconCache(paramString);
        if (localDrawable1 == null)
        {
          Drawable localDrawable2 = getDrawable(Uri.parse(paramString));
          storeInIconCache(paramString, localDrawable2);
          return localDrawable2;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        Log.w("SuggestionsAdapter", "Icon resource not found: " + paramString);
      }
    }
    return null;
  }

  private Drawable getIcon1(Cursor paramCursor)
  {
    Drawable localDrawable;
    if (this.mIconName1Col == -1)
      localDrawable = null;
    do
    {
      return localDrawable;
      localDrawable = getDrawableFromResourceValue(paramCursor.getString(this.mIconName1Col));
    }
    while (localDrawable != null);
    return getDefaultIcon1(paramCursor);
  }

  private Drawable getIcon2(Cursor paramCursor)
  {
    if (this.mIconName2Col == -1)
      return null;
    return getDrawableFromResourceValue(paramCursor.getString(this.mIconName2Col));
  }

  private static String getStringOrNull(Cursor paramCursor, int paramInt)
  {
    if (paramInt == -1)
      return null;
    try
    {
      String str = paramCursor.getString(paramInt);
      return str;
    }
    catch (Exception localException)
    {
      Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", localException);
    }
    return null;
  }

  private void setViewDrawable(ImageView paramImageView, Drawable paramDrawable, int paramInt)
  {
    paramImageView.setImageDrawable(paramDrawable);
    if (paramDrawable == null)
    {
      paramImageView.setVisibility(paramInt);
      return;
    }
    paramImageView.setVisibility(0);
    paramDrawable.setVisible(false, false);
    paramDrawable.setVisible(true, false);
  }

  private void setViewText(TextView paramTextView, CharSequence paramCharSequence)
  {
    paramTextView.setText(paramCharSequence);
    if (TextUtils.isEmpty(paramCharSequence))
    {
      paramTextView.setVisibility(8);
      return;
    }
    paramTextView.setVisibility(0);
  }

  private void storeInIconCache(String paramString, Drawable paramDrawable)
  {
    if (paramDrawable != null)
      this.mOutsideDrawablesCache.put(paramString, paramDrawable.getConstantState());
  }

  private void updateSpinnerState(Cursor paramCursor)
  {
    if (paramCursor != null);
    for (Bundle localBundle = paramCursor.getExtras(); ; localBundle = null)
    {
      if ((localBundle != null) && (localBundle.getBoolean("in_progress")));
      return;
    }
  }

  public void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    ChildViewCache localChildViewCache = (ChildViewCache)paramView.getTag();
    int i = this.mFlagsCol;
    int j = 0;
    if (i != -1)
      j = paramCursor.getInt(this.mFlagsCol);
    if (localChildViewCache.mText1 != null)
    {
      String str2 = getStringOrNull(paramCursor, this.mText1Col);
      setViewText(localChildViewCache.mText1, str2);
    }
    Object localObject;
    if (localChildViewCache.mText2 != null)
    {
      String str1 = getStringOrNull(paramCursor, this.mText2UrlCol);
      if (str1 == null)
        break label246;
      localObject = formatUrl(str1);
      if (!TextUtils.isEmpty((CharSequence)localObject))
        break label259;
      if (localChildViewCache.mText1 != null)
      {
        localChildViewCache.mText1.setSingleLine(false);
        localChildViewCache.mText1.setMaxLines(2);
      }
    }
    while (true)
    {
      setViewText(localChildViewCache.mText2, (CharSequence)localObject);
      if (localChildViewCache.mIcon1 != null)
        setViewDrawable(localChildViewCache.mIcon1, getIcon1(paramCursor), 4);
      if (localChildViewCache.mIcon2 != null)
        setViewDrawable(localChildViewCache.mIcon2, getIcon2(paramCursor), 8);
      if ((this.mQueryRefinement != 2) && ((this.mQueryRefinement != 1) || ((j & 0x1) == 0)))
        break label288;
      localChildViewCache.mIconRefine.setVisibility(0);
      localChildViewCache.mIconRefine.setTag(localChildViewCache.mText1.getText());
      localChildViewCache.mIconRefine.setOnClickListener(this);
      return;
      label246: localObject = getStringOrNull(paramCursor, this.mText2Col);
      break;
      label259: if (localChildViewCache.mText1 != null)
      {
        localChildViewCache.mText1.setSingleLine(true);
        localChildViewCache.mText1.setMaxLines(1);
      }
    }
    label288: localChildViewCache.mIconRefine.setVisibility(8);
  }

  public void changeCursor(Cursor paramCursor)
  {
    if (this.mClosed)
    {
      Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
      if (paramCursor != null)
        paramCursor.close();
    }
    while (true)
    {
      return;
      try
      {
        super.changeCursor(paramCursor);
        if (paramCursor != null)
        {
          this.mText1Col = paramCursor.getColumnIndex("suggest_text_1");
          this.mText2Col = paramCursor.getColumnIndex("suggest_text_2");
          this.mText2UrlCol = paramCursor.getColumnIndex("suggest_text_2_url");
          this.mIconName1Col = paramCursor.getColumnIndex("suggest_icon_1");
          this.mIconName2Col = paramCursor.getColumnIndex("suggest_icon_2");
          this.mFlagsCol = paramCursor.getColumnIndex("suggest_flags");
          return;
        }
      }
      catch (Exception localException)
      {
        Log.e("SuggestionsAdapter", "error changing cursor and caching columns", localException);
      }
    }
  }

  public void close()
  {
    changeCursor(null);
    this.mClosed = true;
  }

  public CharSequence convertToString(Cursor paramCursor)
  {
    Object localObject;
    if (paramCursor == null)
      localObject = null;
    do
    {
      return localObject;
      localObject = getColumnString(paramCursor, "suggest_intent_query");
    }
    while (localObject != null);
    if (this.mSearchable.shouldRewriteQueryFromData())
    {
      String str2 = getColumnString(paramCursor, "suggest_intent_data");
      if (str2 != null)
        return str2;
    }
    if (this.mSearchable.shouldRewriteQueryFromText())
    {
      String str1 = getColumnString(paramCursor, "suggest_text_1");
      if (str1 != null)
        return str1;
    }
    return null;
  }

  Drawable getDrawableFromResourceUri(Uri paramUri)
    throws FileNotFoundException
  {
    String str = paramUri.getAuthority();
    if (TextUtils.isEmpty(str))
      throw new FileNotFoundException("No authority: " + paramUri);
    Resources localResources;
    List localList;
    try
    {
      localResources = this.mContext.getPackageManager().getResourcesForApplication(str);
      localList = paramUri.getPathSegments();
      if (localList == null)
        throw new FileNotFoundException("No path: " + paramUri);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new FileNotFoundException("No package found for authority: " + paramUri);
    }
    int i = localList.size();
    if (i == 1);
    int j;
    while (true)
    {
      try
      {
        int k = Integer.parseInt((String)localList.get(0));
        j = k;
        if (j != 0)
          break;
        throw new FileNotFoundException("No resource found for: " + paramUri);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new FileNotFoundException("Single path segment is not a resource ID: " + paramUri);
      }
      if (i == 2)
        j = localResources.getIdentifier((String)localList.get(1), (String)localList.get(0), str);
      else
        throw new FileNotFoundException("More than two path segments: " + paramUri);
    }
    return localResources.getDrawable(j);
  }

  public int getQueryRefinement()
  {
    return this.mQueryRefinement;
  }

  Cursor getSearchManagerSuggestions(SearchableInfo paramSearchableInfo, String paramString, int paramInt)
  {
    if (paramSearchableInfo == null);
    String str1;
    do
    {
      return null;
      str1 = paramSearchableInfo.getSuggestAuthority();
    }
    while (str1 == null);
    Uri.Builder localBuilder = new Uri.Builder().scheme("content").authority(str1).query("").fragment("");
    String str2 = paramSearchableInfo.getSuggestPath();
    if (str2 != null)
      localBuilder.appendEncodedPath(str2);
    localBuilder.appendPath("search_suggest_query");
    String str3 = paramSearchableInfo.getSuggestSelection();
    if (str3 != null);
    for (String[] arrayOfString = { paramString }; ; arrayOfString = null)
    {
      if (paramInt > 0)
        localBuilder.appendQueryParameter("limit", String.valueOf(paramInt));
      Uri localUri = localBuilder.build();
      return this.mContext.getContentResolver().query(localUri, null, str3, arrayOfString, null);
      localBuilder.appendPath(paramString);
    }
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView1;
    try
    {
      View localView2 = super.getView(paramInt, paramView, paramViewGroup);
      localView1 = localView2;
      return localView1;
    }
    catch (RuntimeException localRuntimeException)
    {
      do
      {
        Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", localRuntimeException);
        localView1 = newView(this.mContext, this.mCursor, paramViewGroup);
      }
      while (localView1 == null);
      ((ChildViewCache)localView1.getTag()).mText1.setText(localRuntimeException.toString());
    }
    return localView1;
  }

  public boolean hasStableIds()
  {
    return false;
  }

  public View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    View localView = super.newView(paramContext, paramCursor, paramViewGroup);
    localView.setTag(new ChildViewCache(localView));
    ((ImageView)localView.findViewById(R.id.edit_query)).setImageResource(this.mCommitIconResId);
    return localView;
  }

  public void notifyDataSetChanged()
  {
    super.notifyDataSetChanged();
    updateSpinnerState(getCursor());
  }

  public void notifyDataSetInvalidated()
  {
    super.notifyDataSetInvalidated();
    updateSpinnerState(getCursor());
  }

  public void onClick(View paramView)
  {
    Object localObject = paramView.getTag();
    if ((localObject instanceof CharSequence))
      this.mSearchView.onQueryRefine((CharSequence)localObject);
  }

  public Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence)
  {
    String str;
    if (paramCharSequence == null)
    {
      str = "";
      if ((this.mSearchView.getVisibility() == 0) && (this.mSearchView.getWindowVisibility() == 0))
        break label40;
    }
    while (true)
    {
      return null;
      str = paramCharSequence.toString();
      break;
      try
      {
        label40: Cursor localCursor = getSearchManagerSuggestions(this.mSearchable, str, 50);
        if (localCursor != null)
        {
          localCursor.getCount();
          return localCursor;
        }
      }
      catch (RuntimeException localRuntimeException)
      {
        Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", localRuntimeException);
      }
    }
    return null;
  }

  public void setQueryRefinement(int paramInt)
  {
    this.mQueryRefinement = paramInt;
  }

  private static final class ChildViewCache
  {
    public final ImageView mIcon1;
    public final ImageView mIcon2;
    public final ImageView mIconRefine;
    public final TextView mText1;
    public final TextView mText2;

    public ChildViewCache(View paramView)
    {
      this.mText1 = ((TextView)paramView.findViewById(16908308));
      this.mText2 = ((TextView)paramView.findViewById(16908309));
      this.mIcon1 = ((ImageView)paramView.findViewById(16908295));
      this.mIcon2 = ((ImageView)paramView.findViewById(16908296));
      this.mIconRefine = ((ImageView)paramView.findViewById(R.id.edit_query));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.SuggestionsAdapter
 * JD-Core Version:    0.6.2
 */