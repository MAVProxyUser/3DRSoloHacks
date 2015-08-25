package com.google.android.gms.playlog.internal;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

public abstract interface zza extends IInterface
{
  public abstract void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
    throws RemoteException;

  public abstract void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, List<LogEvent> paramList)
    throws RemoteException;

  public abstract void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, byte[] paramArrayOfByte)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.zza
 * JD-Core Version:    0.6.2
 */