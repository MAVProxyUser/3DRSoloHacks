package com.o3dr.solo.android.fragment.wizards;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CoachMarkView extends RelativeLayout
{
  public static final int BLACK_GREEN = 0;
  public static final int BOTTOM_CENTER = 1;
  public static final int TOP_CENTER = 0;
  public static final int TOP_LEFT = 3;
  public static final int TOP_RIGHT = 2;
  public static final int WHITE_BLACK = 1;
  private static final int margin = 20;
  private ImageView arrow;
  private int arrowPosition;
  private TextView coachMarkText;
  private final LayoutInflater mInflater;
  private RelativeLayout.LayoutParams params;
  private Point position;

  public CoachMarkView(Context paramContext)
  {
    super(paramContext);
    this.mInflater = LayoutInflater.from(paramContext);
    init();
  }

  private void cleanUpLayoutRules()
  {
    this.params = ((RelativeLayout.LayoutParams)this.coachMarkText.getLayoutParams());
    this.params.removeRule(3);
    this.params.removeRule(2);
    this.params.removeRule(13);
    this.coachMarkText.setLayoutParams(this.params);
    this.params = ((RelativeLayout.LayoutParams)this.arrow.getLayoutParams());
    this.params.removeRule(3);
    this.params.removeRule(2);
    this.params.removeRule(18);
    this.params.removeRule(19);
    this.arrow.setLayoutParams(this.params);
    this.arrow.setRotation(0.0F);
    this.coachMarkText.setTextColor(-16777216);
    this.coachMarkText.setBackgroundResource(2130837940);
    this.arrow.getDrawable().setColorFilter(-1, PorterDuff.Mode.MULTIPLY);
  }

  public void init()
  {
    this.mInflater.inflate(2130903090, this, true);
    this.arrow = ((ImageView)findViewById(2131493100));
    this.coachMarkText = ((TextView)findViewById(2131493101));
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.position != null)
      switch (this.arrowPosition)
      {
      default:
      case 2:
      case 0:
      case 3:
      case 1:
      }
    while (true)
    {
      setViewPosition(this.position);
      return;
      Point localPoint4 = this.position;
      localPoint4.x -= -20 + getWidth();
      continue;
      Point localPoint3 = this.position;
      localPoint3.x -= getWidth() / 2;
      continue;
      Point localPoint2 = this.position;
      localPoint2.x = (-20 + localPoint2.x);
      continue;
      Point localPoint1 = this.position;
      localPoint1.x -= getWidth() / 2;
    }
  }

  public void setCoachMarkView(String paramString, int paramInt, Point paramPoint)
  {
    this.coachMarkText.setText(paramString);
    this.arrowPosition = paramInt;
    this.position = paramPoint;
    cleanUpLayoutRules();
    switch (this.arrowPosition)
    {
    default:
      return;
    case 0:
      this.params = ((RelativeLayout.LayoutParams)this.coachMarkText.getLayoutParams());
      this.params.addRule(3, 2131493100);
      this.coachMarkText.setLayoutParams(this.params);
      this.params = ((RelativeLayout.LayoutParams)this.arrow.getLayoutParams());
      this.params.addRule(18, 2131493101);
      this.params.addRule(19, 2131493101);
      this.arrow.setLayoutParams(this.params);
      return;
    case 1:
      this.params = ((RelativeLayout.LayoutParams)this.arrow.getLayoutParams());
      this.params.addRule(3, 2131493101);
      this.params.addRule(18, 2131493101);
      this.params.addRule(19, 2131493101);
      this.arrow.setLayoutParams(this.params);
      this.arrow.setRotation(180.0F);
      return;
    case 2:
      this.params = ((RelativeLayout.LayoutParams)this.coachMarkText.getLayoutParams());
      this.params.addRule(3, 2131493100);
      this.coachMarkText.setLayoutParams(this.params);
      this.params = ((RelativeLayout.LayoutParams)this.arrow.getLayoutParams());
      this.params.addRule(19, 2131493101);
      this.params.setMarginEnd(20);
      this.arrow.setLayoutParams(this.params);
      return;
    case 3:
    }
    this.params = ((RelativeLayout.LayoutParams)this.coachMarkText.getLayoutParams());
    this.params.addRule(3, 2131493100);
    this.coachMarkText.setLayoutParams(this.params);
    this.params = ((RelativeLayout.LayoutParams)this.arrow.getLayoutParams());
    this.params.addRule(18, 2131493101);
    this.params.setMarginStart(20);
    this.arrow.setLayoutParams(this.params);
  }

  public void setCoachMarkView(String paramString, int paramInt1, Point paramPoint, int paramInt2)
  {
    setCoachMarkView(paramString, paramInt1, paramPoint);
    switch (paramInt2)
    {
    default:
      return;
    case 0:
      this.coachMarkText.setBackgroundColor(getResources().getColor(2131427422));
      this.coachMarkText.setTextColor(getResources().getColor(2131427395));
      this.arrow.getDrawable().setColorFilter(2131427422, PorterDuff.Mode.MULTIPLY);
      return;
    case 1:
    }
    this.coachMarkText.setBackgroundResource(2130837940);
    this.coachMarkText.setTextColor(-16777216);
    this.arrow.getDrawable().setColorFilter(-1, PorterDuff.Mode.MULTIPLY);
  }

  public void setViewPosition(int paramInt1, int paramInt2)
  {
    setX(paramInt1);
    setY(paramInt2);
    invalidate();
  }

  public void setViewPosition(Point paramPoint)
  {
    setViewPosition(paramPoint.x, paramPoint.y);
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface ArrowLocation
  {
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface Style
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.wizards.CoachMarkView
 * JD-Core Version:    0.6.2
 */