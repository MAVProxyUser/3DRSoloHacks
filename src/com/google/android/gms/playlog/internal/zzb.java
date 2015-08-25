package com.google.android.gms.playlog.internal;

import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzoc.zzd;
import java.util.ArrayList;

public class zzb
{
  private final ArrayList<zza> zzayu = new ArrayList();
  private int zzayv;

  public zzb()
  {
    this(100);
  }

  public zzb(int paramInt)
  {
    this.zzayv = paramInt;
  }

  private void zzvs()
  {
    while (getSize() > getCapacity())
      this.zzayu.remove(0);
  }

  public void clear()
  {
    this.zzayu.clear();
  }

  public int getCapacity()
  {
    return this.zzayv;
  }

  public int getSize()
  {
    return this.zzayu.size();
  }

  public boolean isEmpty()
  {
    return this.zzayu.isEmpty();
  }

  public void zza(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    this.zzayu.add(new zza(paramPlayLoggerContext, paramLogEvent, null));
    zzvs();
  }

  public ArrayList<zza> zzvr()
  {
    return this.zzayu;
  }

  public static class zza
  {
    public final PlayLoggerContext zzayw;
    public final LogEvent zzayx;
    public final zzoc.zzd zzayy;

    private zza(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
    {
      this.zzayw = ((PlayLoggerContext)zzv.zzr(paramPlayLoggerContext));
      this.zzayx = ((LogEvent)zzv.zzr(paramLogEvent));
      this.zzayy = null;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.zzb
 * JD-Core Version:    0.6.2
 */