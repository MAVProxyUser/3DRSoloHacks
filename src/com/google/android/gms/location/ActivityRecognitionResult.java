package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ActivityRecognitionResult
  implements SafeParcelable
{
  public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();

  @Deprecated
  public static final String EXTRA_ACTIVITY_RESULT = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT";
  List<DetectedActivity> zzapB;
  long zzapC;
  long zzapD;
  int zzapE;
  private final int zzzH;

  public ActivityRecognitionResult(int paramInt1, List<DetectedActivity> paramList, long paramLong1, long paramLong2, int paramInt2)
  {
    this.zzzH = paramInt1;
    this.zzapB = paramList;
    this.zzapC = paramLong1;
    this.zzapD = paramLong2;
    this.zzapE = paramInt2;
  }

  public ActivityRecognitionResult(DetectedActivity paramDetectedActivity, long paramLong1, long paramLong2)
  {
    this(paramDetectedActivity, paramLong1, paramLong2, 0);
  }

  public ActivityRecognitionResult(DetectedActivity paramDetectedActivity, long paramLong1, long paramLong2, int paramInt)
  {
    this(Collections.singletonList(paramDetectedActivity), paramLong1, paramLong2, paramInt);
  }

  public ActivityRecognitionResult(List<DetectedActivity> paramList, long paramLong1, long paramLong2)
  {
    this(paramList, paramLong1, paramLong2, 0);
  }

  public ActivityRecognitionResult(List<DetectedActivity> paramList, long paramLong1, long paramLong2, int paramInt)
  {
    boolean bool2;
    if ((paramList != null) && (paramList.size() > 0))
    {
      bool2 = bool1;
      zzv.zzb(bool2, "Must have at least 1 detected activity");
      if ((paramLong1 <= 0L) || (paramLong2 <= 0L))
        break label85;
    }
    while (true)
    {
      zzv.zzb(bool1, "Must set times");
      this.zzzH = 2;
      this.zzapB = paramList;
      this.zzapC = paramLong1;
      this.zzapD = paramLong2;
      this.zzapE = paramInt;
      return;
      bool2 = false;
      break;
      label85: bool1 = false;
    }
  }

  public static ActivityRecognitionResult extractResult(Intent paramIntent)
  {
    if (!hasResult(paramIntent))
      return null;
    Object localObject = paramIntent.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
    if ((localObject instanceof byte[]))
    {
      Parcel localParcel = Parcel.obtain();
      localParcel.unmarshall((byte[])localObject, 0, ((byte[])localObject).length);
      localParcel.setDataPosition(0);
      return CREATOR.createFromParcel(localParcel);
    }
    if ((localObject instanceof ActivityRecognitionResult))
      return (ActivityRecognitionResult)localObject;
    return null;
  }

  public static boolean hasResult(Intent paramIntent)
  {
    if (paramIntent == null)
      return false;
    return paramIntent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
  }

  public int describeContents()
  {
    return 0;
  }

  public int getActivityConfidence(int paramInt)
  {
    Iterator localIterator = this.zzapB.iterator();
    while (localIterator.hasNext())
    {
      DetectedActivity localDetectedActivity = (DetectedActivity)localIterator.next();
      if (localDetectedActivity.getType() == paramInt)
        return localDetectedActivity.getConfidence();
    }
    return 0;
  }

  public long getElapsedRealtimeMillis()
  {
    return this.zzapD;
  }

  public DetectedActivity getMostProbableActivity()
  {
    return (DetectedActivity)this.zzapB.get(0);
  }

  public List<DetectedActivity> getProbableActivities()
  {
    return this.zzapB;
  }

  public long getTime()
  {
    return this.zzapC;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public String toString()
  {
    return "ActivityRecognitionResult [probableActivities=" + this.zzapB + ", timeMillis=" + this.zzapC + ", elapsedRealtimeMillis=" + this.zzapD + "]";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ActivityRecognitionResultCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.ActivityRecognitionResult
 * JD-Core Version:    0.6.2
 */