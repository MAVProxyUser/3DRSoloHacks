package android.support.v7.internal.app;

import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemSelectedListener;
import android.view.View;

class NavItemSelectedListener
  implements AdapterViewCompat.OnItemSelectedListener
{
  private final ActionBar.OnNavigationListener mListener;

  public NavItemSelectedListener(ActionBar.OnNavigationListener paramOnNavigationListener)
  {
    this.mListener = paramOnNavigationListener;
  }

  public void onItemSelected(AdapterViewCompat<?> paramAdapterViewCompat, View paramView, int paramInt, long paramLong)
  {
    if (this.mListener != null)
      this.mListener.onNavigationItemSelected(paramInt, paramLong);
  }

  public void onNothingSelected(AdapterViewCompat<?> paramAdapterViewCompat)
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.NavItemSelectedListener
 * JD-Core Version:    0.6.2
 */