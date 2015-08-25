package android.support.v7.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.TaskStackBuilder.SupportParentable;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class AppCompatActivity extends FragmentActivity
  implements AppCompatCallback, TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider
{
  private AppCompatDelegate mDelegate;

  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    getDelegate().addContentView(paramView, paramLayoutParams);
  }

  public AppCompatDelegate getDelegate()
  {
    if (this.mDelegate == null)
      this.mDelegate = AppCompatDelegate.create(this, this);
    return this.mDelegate;
  }

  @Nullable
  public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()
  {
    return getDelegate().getDrawerToggleDelegate();
  }

  public MenuInflater getMenuInflater()
  {
    return getDelegate().getMenuInflater();
  }

  @Nullable
  public ActionBar getSupportActionBar()
  {
    return getDelegate().getSupportActionBar();
  }

  @Nullable
  public Intent getSupportParentActivityIntent()
  {
    return NavUtils.getParentActivityIntent(this);
  }

  public void invalidateOptionsMenu()
  {
    getDelegate().invalidateOptionsMenu();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    getDelegate().onConfigurationChanged(paramConfiguration);
  }

  public void onContentChanged()
  {
    onSupportContentChanged();
  }

  protected void onCreate(@Nullable Bundle paramBundle)
  {
    getDelegate().installViewFactory();
    super.onCreate(paramBundle);
    getDelegate().onCreate(paramBundle);
  }

  public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder paramTaskStackBuilder)
  {
    paramTaskStackBuilder.addParentStack(this);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    getDelegate().onDestroy();
  }

  public final boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (super.onMenuItemSelected(paramInt, paramMenuItem))
      return true;
    ActionBar localActionBar = getSupportActionBar();
    if ((paramMenuItem.getItemId() == 16908332) && (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0))
      return onSupportNavigateUp();
    return false;
  }

  protected void onPostCreate(@Nullable Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    getDelegate().onPostCreate(paramBundle);
  }

  protected void onPostResume()
  {
    super.onPostResume();
    getDelegate().onPostResume();
  }

  public void onPrepareSupportNavigateUpTaskStack(TaskStackBuilder paramTaskStackBuilder)
  {
  }

  protected void onStop()
  {
    super.onStop();
    getDelegate().onStop();
  }

  public void onSupportActionModeFinished(ActionMode paramActionMode)
  {
  }

  public void onSupportActionModeStarted(ActionMode paramActionMode)
  {
  }

  @Deprecated
  public void onSupportContentChanged()
  {
  }

  public boolean onSupportNavigateUp()
  {
    Intent localIntent = getSupportParentActivityIntent();
    if (localIntent != null)
    {
      if (supportShouldUpRecreateTask(localIntent))
      {
        TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
        onCreateSupportNavigateUpTaskStack(localTaskStackBuilder);
        onPrepareSupportNavigateUpTaskStack(localTaskStackBuilder);
        localTaskStackBuilder.startActivities();
      }
      while (true)
      {
        try
        {
          ActivityCompat.finishAffinity(this);
          return true;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          finish();
          continue;
        }
        supportNavigateUpTo(localIntent);
      }
    }
    return false;
  }

  protected void onTitleChanged(CharSequence paramCharSequence, int paramInt)
  {
    super.onTitleChanged(paramCharSequence, paramInt);
    getDelegate().setTitle(paramCharSequence);
  }

  @Nullable
  public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback paramCallback)
  {
    return null;
  }

  public void setContentView(@LayoutRes int paramInt)
  {
    getDelegate().setContentView(paramInt);
  }

  public void setContentView(View paramView)
  {
    getDelegate().setContentView(paramView);
  }

  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    getDelegate().setContentView(paramView, paramLayoutParams);
  }

  public void setSupportActionBar(@Nullable Toolbar paramToolbar)
  {
    getDelegate().setSupportActionBar(paramToolbar);
  }

  @Deprecated
  public void setSupportProgress(int paramInt)
  {
  }

  @Deprecated
  public void setSupportProgressBarIndeterminate(boolean paramBoolean)
  {
  }

  @Deprecated
  public void setSupportProgressBarIndeterminateVisibility(boolean paramBoolean)
  {
  }

  @Deprecated
  public void setSupportProgressBarVisibility(boolean paramBoolean)
  {
  }

  public ActionMode startSupportActionMode(ActionMode.Callback paramCallback)
  {
    return getDelegate().startSupportActionMode(paramCallback);
  }

  public void supportInvalidateOptionsMenu()
  {
    getDelegate().invalidateOptionsMenu();
  }

  public void supportNavigateUpTo(Intent paramIntent)
  {
    NavUtils.navigateUpTo(this, paramIntent);
  }

  public boolean supportRequestWindowFeature(int paramInt)
  {
    return getDelegate().requestWindowFeature(paramInt);
  }

  public boolean supportShouldUpRecreateTask(Intent paramIntent)
  {
    return NavUtils.shouldUpRecreateTask(this, paramIntent);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.app.AppCompatActivity
 * JD-Core Version:    0.6.2
 */