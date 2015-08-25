package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.drawable;
import android.util.SparseArray;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class TintManager
{
  private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
  private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
  private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
  private static final TintManager.ColorFilterLruCache COLOR_FILTER_CACHE;
  private static final boolean DEBUG = false;
  private static final PorterDuff.Mode DEFAULT_MODE;
  private static final WeakHashMap<Context, TintManager> INSTANCE_CACHE;
  public static final boolean SHOULD_BE_USED = false;
  private static final String TAG = "TintManager";
  private static final int[] TINT_CHECKABLE_BUTTON_LIST;
  private static final int[] TINT_COLOR_CONTROL_NORMAL;
  private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
  private final WeakReference<Context> mContextRef;
  private ColorStateList mDefaultColorStateList;
  private SparseArray<ColorStateList> mTintLists;

  static
  {
    if (Build.VERSION.SDK_INT < 21);
    for (boolean bool = true; ; bool = false)
    {
      SHOULD_BE_USED = bool;
      DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
      INSTANCE_CACHE = new WeakHashMap();
      COLOR_FILTER_CACHE = new TintManager.ColorFilterLruCache(6);
      int[] arrayOfInt1 = new int[3];
      arrayOfInt1[0] = R.drawable.abc_textfield_search_default_mtrl_alpha;
      arrayOfInt1[1] = R.drawable.abc_textfield_default_mtrl_alpha;
      arrayOfInt1[2] = R.drawable.abc_ab_share_pack_mtrl_alpha;
      COLORFILTER_TINT_COLOR_CONTROL_NORMAL = arrayOfInt1;
      int[] arrayOfInt2 = new int[12];
      arrayOfInt2[0] = R.drawable.abc_ic_ab_back_mtrl_am_alpha;
      arrayOfInt2[1] = R.drawable.abc_ic_go_search_api_mtrl_alpha;
      arrayOfInt2[2] = R.drawable.abc_ic_search_api_mtrl_alpha;
      arrayOfInt2[3] = R.drawable.abc_ic_commit_search_api_mtrl_alpha;
      arrayOfInt2[4] = R.drawable.abc_ic_clear_mtrl_alpha;
      arrayOfInt2[5] = R.drawable.abc_ic_menu_share_mtrl_alpha;
      arrayOfInt2[6] = R.drawable.abc_ic_menu_copy_mtrl_am_alpha;
      arrayOfInt2[7] = R.drawable.abc_ic_menu_cut_mtrl_alpha;
      arrayOfInt2[8] = R.drawable.abc_ic_menu_selectall_mtrl_alpha;
      arrayOfInt2[9] = R.drawable.abc_ic_menu_paste_mtrl_am_alpha;
      arrayOfInt2[10] = R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha;
      arrayOfInt2[11] = R.drawable.abc_ic_voice_search_api_mtrl_alpha;
      TINT_COLOR_CONTROL_NORMAL = arrayOfInt2;
      int[] arrayOfInt3 = new int[4];
      arrayOfInt3[0] = R.drawable.abc_textfield_activated_mtrl_alpha;
      arrayOfInt3[1] = R.drawable.abc_textfield_search_activated_mtrl_alpha;
      arrayOfInt3[2] = R.drawable.abc_cab_background_top_mtrl_alpha;
      arrayOfInt3[3] = R.drawable.abc_text_cursor_mtrl_alpha;
      COLORFILTER_COLOR_CONTROL_ACTIVATED = arrayOfInt3;
      int[] arrayOfInt4 = new int[3];
      arrayOfInt4[0] = R.drawable.abc_popup_background_mtrl_mult;
      arrayOfInt4[1] = R.drawable.abc_cab_background_internal_bg;
      arrayOfInt4[2] = R.drawable.abc_menu_hardkey_panel_mtrl_mult;
      COLORFILTER_COLOR_BACKGROUND_MULTIPLY = arrayOfInt4;
      int[] arrayOfInt5 = new int[10];
      arrayOfInt5[0] = R.drawable.abc_edit_text_material;
      arrayOfInt5[1] = R.drawable.abc_tab_indicator_material;
      arrayOfInt5[2] = R.drawable.abc_textfield_search_material;
      arrayOfInt5[3] = R.drawable.abc_spinner_mtrl_am_alpha;
      arrayOfInt5[4] = R.drawable.abc_spinner_textfield_background_material;
      arrayOfInt5[5] = R.drawable.abc_ratingbar_full_material;
      arrayOfInt5[6] = R.drawable.abc_switch_track_mtrl_alpha;
      arrayOfInt5[7] = R.drawable.abc_switch_thumb_material;
      arrayOfInt5[8] = R.drawable.abc_btn_default_mtrl_shape;
      arrayOfInt5[9] = R.drawable.abc_btn_borderless_material;
      TINT_COLOR_CONTROL_STATE_LIST = arrayOfInt5;
      int[] arrayOfInt6 = new int[2];
      arrayOfInt6[0] = R.drawable.abc_btn_check_material;
      arrayOfInt6[1] = R.drawable.abc_btn_radio_material;
      TINT_CHECKABLE_BUTTON_LIST = arrayOfInt6;
      return;
    }
  }

  private TintManager(Context paramContext)
  {
    this.mContextRef = new WeakReference(paramContext);
  }

  private static boolean arrayContains(int[] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
      if (paramArrayOfInt[j] == paramInt)
        return true;
    return false;
  }

  private ColorStateList createButtonColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[4][];
    int[] arrayOfInt1 = new int[4];
    int i = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorButtonNormal);
    int j = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlHighlight);
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorButtonNormal);
    int k = 0 + 1;
    arrayOfInt[k] = ThemeUtils.PRESSED_STATE_SET;
    arrayOfInt1[k] = ColorUtils.compositeColors(j, i);
    int m = k + 1;
    arrayOfInt[m] = ThemeUtils.FOCUSED_STATE_SET;
    arrayOfInt1[m] = ColorUtils.compositeColors(j, i);
    int n = m + 1;
    arrayOfInt[n] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[n] = i;
    (n + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }

  private ColorStateList createCheckableButtonColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.CHECKED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }

  private ColorStateList createEditTextColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }

  private ColorStateList createSpinnerColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }

  private ColorStateList createSwitchThumbColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    ColorStateList localColorStateList = ThemeUtils.getThemeAttrColorStateList(paramContext, R.attr.colorSwitchThumbNormal);
    if ((localColorStateList != null) && (localColorStateList.isStateful()))
    {
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = localColorStateList.getColorForState(arrayOfInt[0], 0);
      int k = 0 + 1;
      arrayOfInt[k] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[k] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      int m = k + 1;
      arrayOfInt[m] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[m] = localColorStateList.getDefaultColor();
      (m + 1);
    }
    while (true)
    {
      return new ColorStateList(arrayOfInt, arrayOfInt1);
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorSwitchThumbNormal);
      int i = 0 + 1;
      arrayOfInt[i] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      int j = i + 1;
      arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorSwitchThumbNormal);
      (j + 1);
    }
  }

  private ColorStateList createSwitchTrackColorStateList(Context paramContext)
  {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getThemeAttrColor(paramContext, 16842800, 0.1F);
    int i = 0 + 1;
    arrayOfInt[i] = ThemeUtils.CHECKED_STATE_SET;
    arrayOfInt1[i] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated, 0.3F);
    int j = i + 1;
    arrayOfInt[j] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[j] = ThemeUtils.getThemeAttrColor(paramContext, 16842800, 0.3F);
    (j + 1);
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }

  public static TintManager get(Context paramContext)
  {
    TintManager localTintManager = (TintManager)INSTANCE_CACHE.get(paramContext);
    if (localTintManager == null)
    {
      localTintManager = new TintManager(paramContext);
      INSTANCE_CACHE.put(paramContext, localTintManager);
    }
    return localTintManager;
  }

  private ColorStateList getDefaultColorStateList(Context paramContext)
  {
    if (this.mDefaultColorStateList == null)
    {
      int i = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlNormal);
      int j = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      int[][] arrayOfInt = new int[7][];
      int[] arrayOfInt1 = new int[7];
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorControlNormal);
      int k = 0 + 1;
      arrayOfInt[k] = ThemeUtils.FOCUSED_STATE_SET;
      arrayOfInt1[k] = j;
      int m = k + 1;
      arrayOfInt[m] = ThemeUtils.ACTIVATED_STATE_SET;
      arrayOfInt1[m] = j;
      int n = m + 1;
      arrayOfInt[n] = ThemeUtils.PRESSED_STATE_SET;
      arrayOfInt1[n] = j;
      int i1 = n + 1;
      arrayOfInt[i1] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[i1] = j;
      int i2 = i1 + 1;
      arrayOfInt[i2] = ThemeUtils.SELECTED_STATE_SET;
      arrayOfInt1[i2] = j;
      int i3 = i2 + 1;
      arrayOfInt[i3] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[i3] = i;
      (i3 + 1);
      this.mDefaultColorStateList = new ColorStateList(arrayOfInt, arrayOfInt1);
    }
    return this.mDefaultColorStateList;
  }

  public static Drawable getDrawable(Context paramContext, int paramInt)
  {
    if (isInTintList(paramInt))
      return get(paramContext).getDrawable(paramInt);
    return ContextCompat.getDrawable(paramContext, paramInt);
  }

  private static boolean isInTintList(int paramInt)
  {
    return (arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt)) || (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, paramInt)) || (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, paramInt)) || (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt)) || (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, paramInt)) || (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt)) || (paramInt == R.drawable.abc_cab_background_top_material);
  }

  private static void setPorterDuffColorFilter(Drawable paramDrawable, int paramInt, PorterDuff.Mode paramMode)
  {
    if (paramMode == null)
      paramMode = DEFAULT_MODE;
    PorterDuffColorFilter localPorterDuffColorFilter = COLOR_FILTER_CACHE.get(paramInt, paramMode);
    if (localPorterDuffColorFilter == null)
    {
      localPorterDuffColorFilter = new PorterDuffColorFilter(paramInt, paramMode);
      COLOR_FILTER_CACHE.put(paramInt, paramMode, localPorterDuffColorFilter);
    }
    paramDrawable.setColorFilter(localPorterDuffColorFilter);
  }

  public static void tintViewBackground(View paramView, TintInfo paramTintInfo)
  {
    Drawable localDrawable = paramView.getBackground();
    PorterDuff.Mode localMode;
    if (paramTintInfo.mHasTintList)
    {
      int i = paramTintInfo.mTintList.getColorForState(paramView.getDrawableState(), paramTintInfo.mTintList.getDefaultColor());
      if (paramTintInfo.mHasTintMode)
      {
        localMode = paramTintInfo.mTintMode;
        setPorterDuffColorFilter(localDrawable, i, localMode);
      }
    }
    while (true)
    {
      if (Build.VERSION.SDK_INT <= 10)
        paramView.invalidate();
      return;
      localMode = null;
      break;
      localDrawable.clearColorFilter();
    }
  }

  public Drawable getDrawable(int paramInt)
  {
    return getDrawable(paramInt, false);
  }

  public Drawable getDrawable(int paramInt, boolean paramBoolean)
  {
    Context localContext = (Context)this.mContextRef.get();
    Drawable localDrawable;
    if (localContext == null)
      localDrawable = null;
    do
    {
      PorterDuff.Mode localMode;
      do
      {
        do
        {
          return localDrawable;
          localDrawable = ContextCompat.getDrawable(localContext, paramInt);
        }
        while (localDrawable == null);
        if (Build.VERSION.SDK_INT >= 8)
          localDrawable = localDrawable.mutate();
        ColorStateList localColorStateList = getTintList(paramInt);
        if (localColorStateList == null)
          break;
        localDrawable = DrawableCompat.wrap(localDrawable);
        DrawableCompat.setTintList(localDrawable, localColorStateList);
        localMode = getTintMode(paramInt);
      }
      while (localMode == null);
      DrawableCompat.setTintMode(localDrawable, localMode);
      return localDrawable;
      if (paramInt == R.drawable.abc_cab_background_top_material)
      {
        Drawable[] arrayOfDrawable = new Drawable[2];
        arrayOfDrawable[0] = getDrawable(R.drawable.abc_cab_background_internal_bg);
        arrayOfDrawable[1] = getDrawable(R.drawable.abc_cab_background_top_mtrl_alpha);
        return new LayerDrawable(arrayOfDrawable);
      }
    }
    while ((tintDrawableUsingColorFilter(paramInt, localDrawable)) || (!paramBoolean));
    return null;
  }

  public final ColorStateList getTintList(int paramInt)
  {
    Context localContext = (Context)this.mContextRef.get();
    ColorStateList localColorStateList = null;
    if (localContext == null);
    while (true)
    {
      return localColorStateList;
      SparseArray localSparseArray = this.mTintLists;
      localColorStateList = null;
      if (localSparseArray != null)
        localColorStateList = (ColorStateList)this.mTintLists.get(paramInt);
      if (localColorStateList == null)
      {
        if (paramInt == R.drawable.abc_edit_text_material)
          localColorStateList = createEditTextColorStateList(localContext);
        while (localColorStateList != null)
        {
          if (this.mTintLists == null)
            this.mTintLists = new SparseArray();
          this.mTintLists.append(paramInt, localColorStateList);
          return localColorStateList;
          if (paramInt == R.drawable.abc_switch_track_mtrl_alpha)
            localColorStateList = createSwitchTrackColorStateList(localContext);
          else if (paramInt == R.drawable.abc_switch_thumb_material)
            localColorStateList = createSwitchThumbColorStateList(localContext);
          else if ((paramInt == R.drawable.abc_btn_default_mtrl_shape) || (paramInt == R.drawable.abc_btn_borderless_material))
            localColorStateList = createButtonColorStateList(localContext);
          else if ((paramInt == R.drawable.abc_spinner_mtrl_am_alpha) || (paramInt == R.drawable.abc_spinner_textfield_background_material))
            localColorStateList = createSpinnerColorStateList(localContext);
          else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt))
            localColorStateList = ThemeUtils.getThemeAttrColorStateList(localContext, R.attr.colorControlNormal);
          else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt))
            localColorStateList = getDefaultColorStateList(localContext);
          else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt))
            localColorStateList = createCheckableButtonColorStateList(localContext);
        }
      }
    }
  }

  final PorterDuff.Mode getTintMode(int paramInt)
  {
    int i = R.drawable.abc_switch_thumb_material;
    PorterDuff.Mode localMode = null;
    if (paramInt == i)
      localMode = PorterDuff.Mode.MULTIPLY;
    return localMode;
  }

  public final boolean tintDrawableUsingColorFilter(int paramInt, Drawable paramDrawable)
  {
    Context localContext = (Context)this.mContextRef.get();
    if (localContext == null);
    while (true)
    {
      return false;
      PorterDuff.Mode localMode = null;
      int i = -1;
      int k;
      int m;
      if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, paramInt))
      {
        k = R.attr.colorControlNormal;
        m = 1;
      }
      while (m != 0)
      {
        setPorterDuffColorFilter(paramDrawable, ThemeUtils.getThemeAttrColor(localContext, k), localMode);
        if (i != -1)
          paramDrawable.setAlpha(i);
        return true;
        if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, paramInt))
        {
          k = R.attr.colorControlActivated;
          m = 1;
          localMode = null;
        }
        else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, paramInt))
        {
          k = 16842801;
          m = 1;
          localMode = PorterDuff.Mode.MULTIPLY;
        }
        else
        {
          int j = R.drawable.abc_list_divider_mtrl_alpha;
          k = 0;
          m = 0;
          localMode = null;
          if (paramInt == j)
          {
            k = 16842800;
            m = 1;
            i = Math.round(40.799999F);
            localMode = null;
          }
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.TintManager
 * JD-Core Version:    0.6.2
 */