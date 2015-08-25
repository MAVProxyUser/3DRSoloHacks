package com.google.android.gms.dynamic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public final class zzb extends zzc.zza
{
  private Fragment zzacp;

  private zzb(Fragment paramFragment)
  {
    this.zzacp = paramFragment;
  }

  public static zzb zza(Fragment paramFragment)
  {
    if (paramFragment != null)
      return new zzb(paramFragment);
    return null;
  }

  public Bundle getArguments()
  {
    return this.zzacp.getArguments();
  }

  public int getId()
  {
    return this.zzacp.getId();
  }

  public boolean getRetainInstance()
  {
    return this.zzacp.getRetainInstance();
  }

  public String getTag()
  {
    return this.zzacp.getTag();
  }

  public int getTargetRequestCode()
  {
    return this.zzacp.getTargetRequestCode();
  }

  public boolean getUserVisibleHint()
  {
    return this.zzacp.getUserVisibleHint();
  }

  public zzd getView()
  {
    return zze.zzt(this.zzacp.getView());
  }

  public boolean isAdded()
  {
    return this.zzacp.isAdded();
  }

  public boolean isDetached()
  {
    return this.zzacp.isDetached();
  }

  public boolean isHidden()
  {
    return this.zzacp.isHidden();
  }

  public boolean isInLayout()
  {
    return this.zzacp.isInLayout();
  }

  public boolean isRemoving()
  {
    return this.zzacp.isRemoving();
  }

  public boolean isResumed()
  {
    return this.zzacp.isResumed();
  }

  public boolean isVisible()
  {
    return this.zzacp.isVisible();
  }

  public void setHasOptionsMenu(boolean paramBoolean)
  {
    this.zzacp.setHasOptionsMenu(paramBoolean);
  }

  public void setMenuVisibility(boolean paramBoolean)
  {
    this.zzacp.setMenuVisibility(paramBoolean);
  }

  public void setRetainInstance(boolean paramBoolean)
  {
    this.zzacp.setRetainInstance(paramBoolean);
  }

  public void setUserVisibleHint(boolean paramBoolean)
  {
    this.zzacp.setUserVisibleHint(paramBoolean);
  }

  public void startActivity(Intent paramIntent)
  {
    this.zzacp.startActivity(paramIntent);
  }

  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    this.zzacp.startActivityForResult(paramIntent, paramInt);
  }

  public void zze(zzd paramzzd)
  {
    View localView = (View)zze.zzg(paramzzd);
    this.zzacp.registerForContextMenu(localView);
  }

  public void zzf(zzd paramzzd)
  {
    View localView = (View)zze.zzg(paramzzd);
    this.zzacp.unregisterForContextMenu(localView);
  }

  public zzd zzov()
  {
    return zze.zzt(this.zzacp.getActivity());
  }

  public zzc zzow()
  {
    return zza(this.zzacp.getParentFragment());
  }

  public zzd zzox()
  {
    return zze.zzt(this.zzacp.getResources());
  }

  public zzc zzoy()
  {
    return zza(this.zzacp.getTargetFragment());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.dynamic.zzb
 * JD-Core Version:    0.6.2
 */