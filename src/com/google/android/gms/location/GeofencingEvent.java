package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeofencingEvent
{
  private final int zzapQ;
  private final List<Geofence> zzapR;
  private final Location zzapS;
  private final int zzyl;

  private GeofencingEvent(int paramInt1, int paramInt2, List<Geofence> paramList, Location paramLocation)
  {
    this.zzyl = paramInt1;
    this.zzapQ = paramInt2;
    this.zzapR = paramList;
    this.zzapS = paramLocation;
  }

  public static GeofencingEvent fromIntent(Intent paramIntent)
  {
    if (paramIntent == null)
      return null;
    return new GeofencingEvent(paramIntent.getIntExtra("gms_error_code", -1), zzl(paramIntent), zzm(paramIntent), (Location)paramIntent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
  }

  private static int zzl(Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
    if (i == -1);
    while ((i != 1) && (i != 2) && (i != 4))
      return -1;
    return i;
  }

  private static List<Geofence> zzm(Intent paramIntent)
  {
    ArrayList localArrayList1 = (ArrayList)paramIntent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
    if (localArrayList1 == null)
      return null;
    ArrayList localArrayList2 = new ArrayList(localArrayList1.size());
    Iterator localIterator = localArrayList1.iterator();
    while (localIterator.hasNext())
      localArrayList2.add(ParcelableGeofence.zzk((byte[])localIterator.next()));
    return localArrayList2;
  }

  public int getErrorCode()
  {
    return this.zzyl;
  }

  public int getGeofenceTransition()
  {
    return this.zzapQ;
  }

  public List<Geofence> getTriggeringGeofences()
  {
    return this.zzapR;
  }

  public Location getTriggeringLocation()
  {
    return this.zzapS;
  }

  public boolean hasError()
  {
    return this.zzyl != -1;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.GeofencingEvent
 * JD-Core Version:    0.6.2
 */