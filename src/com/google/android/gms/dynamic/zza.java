package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate>
{
  private T zzacd;
  private Bundle zzace;
  private LinkedList<zza> zzacf;
  private final zzf<T> zzacg = new zzf()
  {
    public void zza(T paramAnonymousT)
    {
      zza.zza(zza.this, paramAnonymousT);
      Iterator localIterator = zza.zza(zza.this).iterator();
      while (localIterator.hasNext())
        ((zza.zza)localIterator.next()).zzb(zza.zzb(zza.this));
      zza.zza(zza.this).clear();
      zza.zza(zza.this, null);
    }
  };

  private void zza(Bundle paramBundle, zza paramzza)
  {
    if (this.zzacd != null)
    {
      paramzza.zzb(this.zzacd);
      return;
    }
    if (this.zzacf == null)
      this.zzacf = new LinkedList();
    this.zzacf.add(paramzza);
    if (paramBundle != null)
    {
      if (this.zzace != null)
        break label76;
      this.zzace = ((Bundle)paramBundle.clone());
    }
    while (true)
    {
      zza(this.zzacg);
      return;
      label76: this.zzace.putAll(paramBundle);
    }
  }

  public static void zzb(FrameLayout paramFrameLayout)
  {
    Context localContext = paramFrameLayout.getContext();
    final int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(localContext);
    String str1 = com.google.android.gms.common.internal.zzf.zzh(localContext, i);
    String str2 = com.google.android.gms.common.internal.zzf.zzi(localContext, i);
    LinearLayout localLinearLayout = new LinearLayout(paramFrameLayout.getContext());
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
    paramFrameLayout.addView(localLinearLayout);
    TextView localTextView = new TextView(paramFrameLayout.getContext());
    localTextView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
    localTextView.setText(str1);
    localLinearLayout.addView(localTextView);
    if (str2 != null)
    {
      Button localButton = new Button(localContext);
      localButton.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
      localButton.setText(str2);
      localLinearLayout.addView(localButton);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          this.zzoH.startActivity(GooglePlayServicesUtil.zzar(i));
        }
      });
    }
  }

  private void zzdu(int paramInt)
  {
    while ((!this.zzacf.isEmpty()) && (((zza)this.zzacf.getLast()).getState() >= paramInt))
      this.zzacf.removeLast();
  }

  public void onCreate(final Bundle paramBundle)
  {
    zza(paramBundle, new zza()
    {
      public int getState()
      {
        return 1;
      }

      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onCreate(paramBundle);
      }
    });
  }

  public View onCreateView(final LayoutInflater paramLayoutInflater, final ViewGroup paramViewGroup, final Bundle paramBundle)
  {
    final FrameLayout localFrameLayout = new FrameLayout(paramLayoutInflater.getContext());
    zza(paramBundle, new zza()
    {
      public int getState()
      {
        return 2;
      }

      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        localFrameLayout.removeAllViews();
        localFrameLayout.addView(zza.zzb(zza.this).onCreateView(paramLayoutInflater, paramViewGroup, paramBundle));
      }
    });
    if (this.zzacd == null)
      zza(localFrameLayout);
    return localFrameLayout;
  }

  public void onDestroy()
  {
    if (this.zzacd != null)
    {
      this.zzacd.onDestroy();
      return;
    }
    zzdu(1);
  }

  public void onDestroyView()
  {
    if (this.zzacd != null)
    {
      this.zzacd.onDestroyView();
      return;
    }
    zzdu(2);
  }

  public void onInflate(final Activity paramActivity, final Bundle paramBundle1, final Bundle paramBundle2)
  {
    zza(paramBundle2, new zza()
    {
      public int getState()
      {
        return 0;
      }

      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onInflate(paramActivity, paramBundle1, paramBundle2);
      }
    });
  }

  public void onLowMemory()
  {
    if (this.zzacd != null)
      this.zzacd.onLowMemory();
  }

  public void onPause()
  {
    if (this.zzacd != null)
    {
      this.zzacd.onPause();
      return;
    }
    zzdu(5);
  }

  public void onResume()
  {
    zza(null, new zza()
    {
      public int getState()
      {
        return 5;
      }

      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onResume();
      }
    });
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.zzacd != null)
      this.zzacd.onSaveInstanceState(paramBundle);
    while (this.zzace == null)
      return;
    paramBundle.putAll(this.zzace);
  }

  public void onStart()
  {
    zza(null, new zza()
    {
      public int getState()
      {
        return 4;
      }

      public void zzb(LifecycleDelegate paramAnonymousLifecycleDelegate)
      {
        zza.zzb(zza.this).onStart();
      }
    });
  }

  public void onStop()
  {
    if (this.zzacd != null)
    {
      this.zzacd.onStop();
      return;
    }
    zzdu(4);
  }

  protected void zza(FrameLayout paramFrameLayout)
  {
    zzb(paramFrameLayout);
  }

  protected abstract void zza(zzf<T> paramzzf);

  public T zzou()
  {
    return this.zzacd;
  }

  private static abstract interface zza
  {
    public abstract int getState();

    public abstract void zzb(LifecycleDelegate paramLifecycleDelegate);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.dynamic.zza
 * JD-Core Version:    0.6.2
 */