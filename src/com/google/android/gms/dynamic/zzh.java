package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public final class zzh extends zzc.zza
{
  private Fragment zzTb;

  private zzh(Fragment paramFragment)
  {
    this.zzTb = paramFragment;
  }

  public static zzh zza(Fragment paramFragment)
  {
    if (paramFragment != null)
      return new zzh(paramFragment);
    return null;
  }

  public Bundle getArguments()
  {
    return this.zzTb.getArguments();
  }

  public int getId()
  {
    return this.zzTb.getId();
  }

  public boolean getRetainInstance()
  {
    return this.zzTb.getRetainInstance();
  }

  public String getTag()
  {
    return this.zzTb.getTag();
  }

  public int getTargetRequestCode()
  {
    return this.zzTb.getTargetRequestCode();
  }

  public boolean getUserVisibleHint()
  {
    return this.zzTb.getUserVisibleHint();
  }

  public zzd getView()
  {
    return zze.zzt(this.zzTb.getView());
  }

  public boolean isAdded()
  {
    return this.zzTb.isAdded();
  }

  public boolean isDetached()
  {
    return this.zzTb.isDetached();
  }

  public boolean isHidden()
  {
    return this.zzTb.isHidden();
  }

  public boolean isInLayout()
  {
    return this.zzTb.isInLayout();
  }

  public boolean isRemoving()
  {
    return this.zzTb.isRemoving();
  }

  public boolean isResumed()
  {
    return this.zzTb.isResumed();
  }

  public boolean isVisible()
  {
    return this.zzTb.isVisible();
  }

  public void setHasOptionsMenu(boolean paramBoolean)
  {
    this.zzTb.setHasOptionsMenu(paramBoolean);
  }

  public void setMenuVisibility(boolean paramBoolean)
  {
    this.zzTb.setMenuVisibility(paramBoolean);
  }

  public void setRetainInstance(boolean paramBoolean)
  {
    this.zzTb.setRetainInstance(paramBoolean);
  }

  public void setUserVisibleHint(boolean paramBoolean)
  {
    this.zzTb.setUserVisibleHint(paramBoolean);
  }

  public void startActivity(Intent paramIntent)
  {
    this.zzTb.startActivity(paramIntent);
  }

  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    this.zzTb.startActivityForResult(paramIntent, paramInt);
  }

  public void zze(zzd paramzzd)
  {
    View localView = (View)zze.zzg(paramzzd);
    this.zzTb.registerForContextMenu(localView);
  }

  public void zzf(zzd paramzzd)
  {
    View localView = (View)zze.zzg(paramzzd);
    this.zzTb.unregisterForContextMenu(localView);
  }

  public zzd zzov()
  {
    return zze.zzt(this.zzTb.getActivity());
  }

  public zzc zzow()
  {
    return zza(this.zzTb.getParentFragment());
  }

  public zzd zzox()
  {
    return zze.zzt(this.zzTb.getResources());
  }

  public zzc zzoy()
  {
    return zza(this.zzTb.getTargetFragment());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.dynamic.zzh
 * JD-Core Version:    0.6.2
 */