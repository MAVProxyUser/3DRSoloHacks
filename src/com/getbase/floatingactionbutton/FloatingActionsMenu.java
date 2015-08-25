package com.getbase.floatingactionbutton;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

public class FloatingActionsMenu extends ViewGroup
{
  private static final int ANIMATION_DURATION = 300;
  private static final float COLLAPSED_PLUS_ROTATION = 0.0F;
  private static final float EXPANDED_PLUS_ROTATION = 135.0F;
  public static final int EXPAND_DOWN = 1;
  public static final int EXPAND_LEFT = 2;
  public static final int EXPAND_RIGHT = 3;
  public static final int EXPAND_UP = 0;
  public static final int LABELS_ON_LEFT_SIDE = 0;
  public static final int LABELS_ON_RIGHT_SIDE = 1;
  private static Interpolator sAlphaExpandInterpolator = new DecelerateInterpolator();
  private static Interpolator sCollapseInterpolator;
  private static Interpolator sExpandInterpolator = new OvershootInterpolator();
  private AddFloatingActionButton mAddButton;
  private int mAddButtonColorNormal;
  private int mAddButtonColorPressed;
  private int mAddButtonPlusColor;
  private int mAddButtonSize;
  private boolean mAddButtonStrokeVisible;
  private int mButtonSpacing;
  private int mButtonsCount;
  private AnimatorSet mCollapseAnimation = new AnimatorSet().setDuration(300L);
  private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(300L);
  private int mExpandDirection;
  private boolean mExpanded;
  private int mLabelsMargin;
  private int mLabelsPosition;
  private int mLabelsStyle;
  private int mLabelsVerticalOffset;
  private OnFloatingActionsMenuUpdateListener mListener;
  private int mMaxButtonHeight;
  private int mMaxButtonWidth;
  private RotatingDrawable mRotatingDrawable;
  private TouchDelegateGroup mTouchDelegateGroup;

  static
  {
    sCollapseInterpolator = new DecelerateInterpolator(3.0F);
  }

  public FloatingActionsMenu(Context paramContext)
  {
    this(paramContext, null);
  }

  public FloatingActionsMenu(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public FloatingActionsMenu(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private int adjustForOvershoot(int paramInt)
  {
    return paramInt * 12 / 10;
  }

  private void createAddButton(Context paramContext)
  {
    this.mAddButton = new AddFloatingActionButton(paramContext)
    {
      Drawable getIconDrawable()
      {
        FloatingActionsMenu.RotatingDrawable localRotatingDrawable = new FloatingActionsMenu.RotatingDrawable(super.getIconDrawable());
        FloatingActionsMenu.access$402(FloatingActionsMenu.this, localRotatingDrawable);
        OvershootInterpolator localOvershootInterpolator = new OvershootInterpolator();
        ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(localRotatingDrawable, "rotation", new float[] { 135.0F, 0.0F });
        ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(localRotatingDrawable, "rotation", new float[] { 0.0F, 135.0F });
        localObjectAnimator1.setInterpolator(localOvershootInterpolator);
        localObjectAnimator2.setInterpolator(localOvershootInterpolator);
        FloatingActionsMenu.this.mExpandAnimation.play(localObjectAnimator2);
        FloatingActionsMenu.this.mCollapseAnimation.play(localObjectAnimator1);
        return localRotatingDrawable;
      }

      void updateBackground()
      {
        this.mPlusColor = FloatingActionsMenu.this.mAddButtonPlusColor;
        this.mColorNormal = FloatingActionsMenu.this.mAddButtonColorNormal;
        this.mColorPressed = FloatingActionsMenu.this.mAddButtonColorPressed;
        this.mStrokeVisible = FloatingActionsMenu.this.mAddButtonStrokeVisible;
        super.updateBackground();
      }
    };
    this.mAddButton.setId(R.id.fab_expand_menu_button);
    this.mAddButton.setSize(this.mAddButtonSize);
    this.mAddButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FloatingActionsMenu.this.toggle();
      }
    });
    addView(this.mAddButton, super.generateDefaultLayoutParams());
  }

  private void createLabels()
  {
    ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(getContext(), this.mLabelsStyle);
    int i = 0;
    if (i < this.mButtonsCount)
    {
      FloatingActionButton localFloatingActionButton = (FloatingActionButton)getChildAt(i);
      String str = localFloatingActionButton.getTitle();
      if ((localFloatingActionButton == this.mAddButton) || (str == null) || (localFloatingActionButton.getTag(R.id.fab_label) != null));
      while (true)
      {
        i++;
        break;
        TextView localTextView = new TextView(localContextThemeWrapper);
        localTextView.setTextAppearance(getContext(), this.mLabelsStyle);
        localTextView.setText(localFloatingActionButton.getTitle());
        addView(localTextView);
        localFloatingActionButton.setTag(R.id.fab_label, localTextView);
      }
    }
  }

  private boolean expandsHorizontally()
  {
    return (this.mExpandDirection == 2) || (this.mExpandDirection == 3);
  }

  private int getColor(int paramInt)
  {
    return getResources().getColor(paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    this.mButtonSpacing = ((int)(getResources().getDimension(R.dimen.fab_actions_spacing) - getResources().getDimension(R.dimen.fab_shadow_radius) - getResources().getDimension(R.dimen.fab_shadow_offset)));
    this.mLabelsMargin = getResources().getDimensionPixelSize(R.dimen.fab_labels_margin);
    this.mLabelsVerticalOffset = getResources().getDimensionPixelSize(R.dimen.fab_shadow_offset);
    this.mTouchDelegateGroup = new TouchDelegateGroup(this);
    setTouchDelegate(this.mTouchDelegateGroup);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FloatingActionsMenu, 0, 0);
    this.mAddButtonPlusColor = localTypedArray.getColor(R.styleable.FloatingActionsMenu_fab_addButtonPlusIconColor, getColor(17170443));
    this.mAddButtonColorNormal = localTypedArray.getColor(R.styleable.FloatingActionsMenu_fab_addButtonColorNormal, getColor(17170451));
    this.mAddButtonColorPressed = localTypedArray.getColor(R.styleable.FloatingActionsMenu_fab_addButtonColorPressed, getColor(17170450));
    this.mAddButtonSize = localTypedArray.getInt(R.styleable.FloatingActionsMenu_fab_addButtonSize, 0);
    this.mAddButtonStrokeVisible = localTypedArray.getBoolean(R.styleable.FloatingActionsMenu_fab_addButtonStrokeVisible, true);
    this.mExpandDirection = localTypedArray.getInt(R.styleable.FloatingActionsMenu_fab_expandDirection, 0);
    this.mLabelsStyle = localTypedArray.getResourceId(R.styleable.FloatingActionsMenu_fab_labelStyle, 0);
    this.mLabelsPosition = localTypedArray.getInt(R.styleable.FloatingActionsMenu_fab_labelsPosition, 0);
    localTypedArray.recycle();
    if ((this.mLabelsStyle != 0) && (expandsHorizontally()))
      throw new IllegalStateException("Action labels in horizontal expand orientation is not supported.");
    createAddButton(paramContext);
  }

  public void addButton(FloatingActionButton paramFloatingActionButton)
  {
    addView(paramFloatingActionButton, -1 + this.mButtonsCount);
    this.mButtonsCount = (1 + this.mButtonsCount);
    if (this.mLabelsStyle != 0)
      createLabels();
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return super.checkLayoutParams(paramLayoutParams);
  }

  public void collapse()
  {
    if (this.mExpanded)
    {
      this.mExpanded = false;
      this.mTouchDelegateGroup.setEnabled(false);
      this.mCollapseAnimation.start();
      this.mExpandAnimation.cancel();
      if (this.mListener != null)
        this.mListener.onMenuCollapsed();
    }
  }

  public void expand()
  {
    if (!this.mExpanded)
    {
      this.mExpanded = true;
      this.mTouchDelegateGroup.setEnabled(true);
      this.mCollapseAnimation.cancel();
      this.mExpandAnimation.start();
      if (this.mListener != null)
        this.mListener.onMenuExpanded();
    }
  }

  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(super.generateDefaultLayoutParams());
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(super.generateLayoutParams(paramAttributeSet));
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(super.generateLayoutParams(paramLayoutParams));
  }

  public boolean isExpanded()
  {
    return this.mExpanded;
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    bringChildToFront(this.mAddButton);
    this.mButtonsCount = getChildCount();
    if (this.mLabelsStyle != 0)
      createLabels();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (this.mExpandDirection)
    {
    default:
      return;
    case 0:
    case 1:
      int i3;
      label47: int i4;
      label77: int i5;
      label97: int i7;
      int i8;
      if (this.mExpandDirection == 0)
      {
        i3 = 1;
        if (paramBoolean)
          this.mTouchDelegateGroup.clearTouchDelegates();
        if (i3 == 0)
          break label235;
        i4 = paramInt4 - paramInt2 - this.mAddButton.getMeasuredHeight();
        if (this.mLabelsPosition != 0)
          break label241;
        i5 = paramInt3 - paramInt1 - this.mMaxButtonWidth / 2;
        int i6 = i5 - this.mAddButton.getMeasuredWidth() / 2;
        this.mAddButton.layout(i6, i4, i6 + this.mAddButton.getMeasuredWidth(), i4 + this.mAddButton.getMeasuredHeight());
        i7 = this.mMaxButtonWidth / 2 + this.mLabelsMargin;
        if (this.mLabelsPosition != 0)
          break label252;
        i8 = i5 - i7;
        label169: if (i3 == 0)
          break label262;
      }
      View localView2;
      label262: for (int i9 = i4 - this.mButtonSpacing; ; i9 = i4 + this.mAddButton.getMeasuredHeight() + this.mButtonSpacing)
      {
        for (int i10 = -1 + this.mButtonsCount; i10 >= 0; i10--)
        {
          localView2 = getChildAt(i10);
          if ((localView2 != this.mAddButton) && (localView2.getVisibility() != 8))
            break label282;
        }
        i3 = 0;
        break label47;
        label235: i4 = 0;
        break label77;
        label241: i5 = this.mMaxButtonWidth / 2;
        break label97;
        label252: i8 = i5 + i7;
        break label169;
      }
      label282: int i11 = i5 - localView2.getMeasuredWidth() / 2;
      int i12;
      label309: float f4;
      float f5;
      label352: float f6;
      label369: View localView3;
      int i13;
      label468: int i14;
      label479: int i15;
      label490: float f7;
      label625: float f8;
      if (i3 != 0)
      {
        i12 = i9 - localView2.getMeasuredHeight();
        localView2.layout(i11, i12, i11 + localView2.getMeasuredWidth(), i12 + localView2.getMeasuredHeight());
        f4 = i4 - i12;
        if (!this.mExpanded)
          break label730;
        f5 = 0.0F;
        localView2.setTranslationY(f5);
        if (!this.mExpanded)
          break label737;
        f6 = 1.0F;
        localView2.setAlpha(f6);
        LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
        localLayoutParams2.mCollapseDir.setFloatValues(new float[] { 0.0F, f4 });
        localLayoutParams2.mExpandDir.setFloatValues(new float[] { f4, 0.0F });
        localLayoutParams2.setAnimationsTarget(localView2);
        localView3 = (View)localView2.getTag(R.id.fab_label);
        if (localView3 != null)
        {
          if (this.mLabelsPosition != 0)
            break label743;
          i13 = i8 - localView3.getMeasuredWidth();
          if (this.mLabelsPosition != 0)
            break label756;
          i14 = i13;
          if (this.mLabelsPosition != 0)
            break label763;
          i15 = i8;
          int i16 = i12 - this.mLabelsVerticalOffset + (localView2.getMeasuredHeight() - localView3.getMeasuredHeight()) / 2;
          int i17 = i16 + localView3.getMeasuredHeight();
          localView3.layout(i14, i16, i15, i17);
          Rect localRect = new Rect(Math.min(i11, i14), i12 - this.mButtonSpacing / 2, Math.max(i11 + localView2.getMeasuredWidth(), i15), i12 + localView2.getMeasuredHeight() + this.mButtonSpacing / 2);
          TouchDelegateGroup localTouchDelegateGroup = this.mTouchDelegateGroup;
          TouchDelegate localTouchDelegate = new TouchDelegate(localRect, localView2);
          localTouchDelegateGroup.addTouchDelegate(localTouchDelegate);
          if (!this.mExpanded)
            break label770;
          f7 = 0.0F;
          localView3.setTranslationY(f7);
          if (!this.mExpanded)
            break label777;
          f8 = 1.0F;
          label642: localView3.setAlpha(f8);
          LayoutParams localLayoutParams3 = (LayoutParams)localView3.getLayoutParams();
          localLayoutParams3.mCollapseDir.setFloatValues(new float[] { 0.0F, f4 });
          localLayoutParams3.mExpandDir.setFloatValues(new float[] { f4, 0.0F });
          localLayoutParams3.setAnimationsTarget(localView3);
        }
        if (i3 == 0)
          break label783;
      }
      label770: label777: label783: for (i9 = i12 - this.mButtonSpacing; ; i9 = i12 + localView2.getMeasuredHeight() + this.mButtonSpacing)
      {
        break;
        i12 = i9;
        break label309;
        label730: f5 = f4;
        break label352;
        label737: f6 = 0.0F;
        break label369;
        label743: i13 = i8 + localView3.getMeasuredWidth();
        break label468;
        label756: i14 = i8;
        break label479;
        label763: i15 = i13;
        break label490;
        f7 = f4;
        break label625;
        f8 = 0.0F;
        break label642;
      }
    case 2:
    case 3:
    }
    int i;
    label812: int j;
    label831: int k;
    if (this.mExpandDirection == 2)
    {
      i = 1;
      if (i == 0)
        break label954;
      j = paramInt3 - paramInt1 - this.mAddButton.getMeasuredWidth();
      k = paramInt4 - paramInt2 - this.mMaxButtonHeight + (this.mMaxButtonHeight - this.mAddButton.getMeasuredHeight()) / 2;
      this.mAddButton.layout(j, k, j + this.mAddButton.getMeasuredWidth(), k + this.mAddButton.getMeasuredHeight());
      if (i == 0)
        break label960;
    }
    View localView1;
    label954: label960: for (int m = j - this.mButtonSpacing; ; m = j + this.mAddButton.getMeasuredWidth() + this.mButtonSpacing)
    {
      for (int n = -1 + this.mButtonsCount; n >= 0; n--)
      {
        localView1 = getChildAt(n);
        if ((localView1 != this.mAddButton) && (localView1.getVisibility() != 8))
          break label980;
      }
      break;
      i = 0;
      break label812;
      j = 0;
      break label831;
    }
    label980: int i1;
    label995: float f1;
    float f2;
    label1058: float f3;
    if (i != 0)
    {
      i1 = m - localView1.getMeasuredWidth();
      int i2 = k + (this.mAddButton.getMeasuredHeight() - localView1.getMeasuredHeight()) / 2;
      localView1.layout(i1, i2, i1 + localView1.getMeasuredWidth(), i2 + localView1.getMeasuredHeight());
      f1 = j - i1;
      if (!this.mExpanded)
        break label1163;
      f2 = 0.0F;
      localView1.setTranslationX(f2);
      if (!this.mExpanded)
        break label1170;
      f3 = 1.0F;
      label1075: localView1.setAlpha(f3);
      LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      localLayoutParams1.mCollapseDir.setFloatValues(new float[] { 0.0F, f1 });
      localLayoutParams1.mExpandDir.setFloatValues(new float[] { f1, 0.0F });
      localLayoutParams1.setAnimationsTarget(localView1);
      if (i == 0)
        break label1176;
    }
    label1163: label1170: label1176: for (m = i1 - this.mButtonSpacing; ; m = i1 + localView1.getMeasuredWidth() + this.mButtonSpacing)
    {
      break;
      i1 = m;
      break label995;
      f2 = f1;
      break label1058;
      f3 = 0.0F;
      break label1075;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    measureChildren(paramInt1, paramInt2);
    int i = 0;
    int j = 0;
    this.mMaxButtonWidth = 0;
    this.mMaxButtonHeight = 0;
    int k = 0;
    int m = 0;
    if (m < this.mButtonsCount)
    {
      View localView = getChildAt(m);
      if (localView.getVisibility() == 8);
      label190: 
      while (true)
      {
        m++;
        break;
        switch (this.mExpandDirection)
        {
        default:
        case 0:
        case 1:
        case 2:
        case 3:
        }
        while (true)
        {
          if (expandsHorizontally())
            break label190;
          TextView localTextView = (TextView)localView.getTag(R.id.fab_label);
          if (localTextView == null)
            break;
          k = Math.max(k, localTextView.getMeasuredWidth());
          break;
          this.mMaxButtonWidth = Math.max(this.mMaxButtonWidth, localView.getMeasuredWidth());
          j += localView.getMeasuredHeight();
          continue;
          i += localView.getMeasuredWidth();
          this.mMaxButtonHeight = Math.max(this.mMaxButtonHeight, localView.getMeasuredHeight());
        }
      }
    }
    if (!expandsHorizontally())
    {
      int n = this.mMaxButtonWidth;
      int i1 = 0;
      if (k > 0)
        i1 = k + this.mLabelsMargin;
      i = n + i1;
      switch (this.mExpandDirection)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      }
    }
    while (true)
    {
      setMeasuredDimension(i, j);
      return;
      j = this.mMaxButtonHeight;
      break;
      j = adjustForOvershoot(j + this.mButtonSpacing * (-1 + getChildCount()));
      continue;
      i = adjustForOvershoot(i + this.mButtonSpacing * (-1 + getChildCount()));
    }
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      SavedState localSavedState = (SavedState)paramParcelable;
      this.mExpanded = localSavedState.mExpanded;
      this.mTouchDelegateGroup.setEnabled(this.mExpanded);
      RotatingDrawable localRotatingDrawable;
      if (this.mRotatingDrawable != null)
      {
        localRotatingDrawable = this.mRotatingDrawable;
        if (!this.mExpanded)
          break label69;
      }
      label69: for (float f = 135.0F; ; f = 0.0F)
      {
        localRotatingDrawable.setRotation(f);
        super.onRestoreInstanceState(localSavedState.getSuperState());
        return;
      }
    }
    super.onRestoreInstanceState(paramParcelable);
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.mExpanded = this.mExpanded;
    return localSavedState;
  }

  public void removeButton(FloatingActionButton paramFloatingActionButton)
  {
    removeView(paramFloatingActionButton.getLabelView());
    removeView(paramFloatingActionButton);
    this.mButtonsCount = (-1 + this.mButtonsCount);
  }

  public void setOnFloatingActionsMenuUpdateListener(OnFloatingActionsMenuUpdateListener paramOnFloatingActionsMenuUpdateListener)
  {
    this.mListener = paramOnFloatingActionsMenuUpdateListener;
  }

  public void toggle()
  {
    if (this.mExpanded)
    {
      collapse();
      return;
    }
    expand();
  }

  private class LayoutParams extends ViewGroup.LayoutParams
  {
    private boolean animationsSetToPlay;
    private ObjectAnimator mCollapseAlpha = new ObjectAnimator();
    private ObjectAnimator mCollapseDir = new ObjectAnimator();
    private ObjectAnimator mExpandAlpha = new ObjectAnimator();
    private ObjectAnimator mExpandDir = new ObjectAnimator();

    public LayoutParams(ViewGroup.LayoutParams arg2)
    {
      super();
      this.mExpandDir.setInterpolator(FloatingActionsMenu.sExpandInterpolator);
      this.mExpandAlpha.setInterpolator(FloatingActionsMenu.sAlphaExpandInterpolator);
      this.mCollapseDir.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
      this.mCollapseAlpha.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
      this.mCollapseAlpha.setProperty(View.ALPHA);
      this.mCollapseAlpha.setFloatValues(new float[] { 1.0F, 0.0F });
      this.mExpandAlpha.setProperty(View.ALPHA);
      this.mExpandAlpha.setFloatValues(new float[] { 0.0F, 1.0F });
      switch (FloatingActionsMenu.this.mExpandDirection)
      {
      default:
        return;
      case 0:
      case 1:
        this.mCollapseDir.setProperty(View.TRANSLATION_Y);
        this.mExpandDir.setProperty(View.TRANSLATION_Y);
        return;
      case 2:
      case 3:
      }
      this.mCollapseDir.setProperty(View.TRANSLATION_X);
      this.mExpandDir.setProperty(View.TRANSLATION_X);
    }

    public void setAnimationsTarget(View paramView)
    {
      this.mCollapseAlpha.setTarget(paramView);
      this.mCollapseDir.setTarget(paramView);
      this.mExpandAlpha.setTarget(paramView);
      this.mExpandDir.setTarget(paramView);
      if (!this.animationsSetToPlay)
      {
        FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseAlpha);
        FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseDir);
        FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandAlpha);
        FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandDir);
        this.animationsSetToPlay = true;
      }
    }
  }

  public static abstract interface OnFloatingActionsMenuUpdateListener
  {
    public abstract void onMenuCollapsed();

    public abstract void onMenuExpanded();
  }

  private static class RotatingDrawable extends LayerDrawable
  {
    private float mRotation;

    public RotatingDrawable(Drawable paramDrawable)
    {
      super();
    }

    public void draw(Canvas paramCanvas)
    {
      paramCanvas.save();
      paramCanvas.rotate(this.mRotation, getBounds().centerX(), getBounds().centerY());
      super.draw(paramCanvas);
      paramCanvas.restore();
    }

    public float getRotation()
    {
      return this.mRotation;
    }

    public void setRotation(float paramFloat)
    {
      this.mRotation = paramFloat;
      invalidateSelf();
    }
  }

  public static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public FloatingActionsMenu.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new FloatingActionsMenu.SavedState(paramAnonymousParcel, null);
      }

      public FloatingActionsMenu.SavedState[] newArray(int paramAnonymousInt)
      {
        return new FloatingActionsMenu.SavedState[paramAnonymousInt];
      }
    };
    public boolean mExpanded;

    private SavedState(Parcel paramParcel)
    {
      super();
      if (paramParcel.readInt() == i);
      while (true)
      {
        this.mExpanded = i;
        return;
        i = 0;
      }
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(@NonNull Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      if (this.mExpanded);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.getbase.floatingactionbutton.FloatingActionsMenu
 * JD-Core Version:    0.6.2
 */