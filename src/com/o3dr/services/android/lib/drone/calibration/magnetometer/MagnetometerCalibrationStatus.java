package com.o3dr.services.android.lib.drone.calibration.magnetometer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MagnetometerCalibrationStatus
  implements Parcelable
{
  public static final Parcelable.Creator<MagnetometerCalibrationStatus> CREATOR = new Parcelable.Creator()
  {
    public MagnetometerCalibrationStatus createFromParcel(Parcel paramAnonymousParcel)
    {
      return new MagnetometerCalibrationStatus(paramAnonymousParcel, null);
    }

    public MagnetometerCalibrationStatus[] newArray(int paramAnonymousInt)
    {
      return new MagnetometerCalibrationStatus[paramAnonymousInt];
    }
  };
  private boolean calibrationCancelled;
  private final Map<Integer, MagnetometerCalibrationProgress> calibrationProgressTracker = new HashMap();
  private final Map<Integer, MagnetometerCalibrationResult> calibrationResultTracker = new HashMap();
  private final List<Integer> compassList = new ArrayList();

  public MagnetometerCalibrationStatus()
  {
  }

  private MagnetometerCalibrationStatus(Parcel paramParcel)
  {
    ArrayList localArrayList1 = new ArrayList();
    paramParcel.readTypedList(localArrayList1, MagnetometerCalibrationProgress.CREATOR);
    Iterator localIterator1 = localArrayList1.iterator();
    while (localIterator1.hasNext())
      addCalibrationProgress((MagnetometerCalibrationProgress)localIterator1.next());
    ArrayList localArrayList2 = new ArrayList();
    paramParcel.readTypedList(localArrayList2, MagnetometerCalibrationResult.CREATOR);
    Iterator localIterator2 = localArrayList2.iterator();
    while (localIterator2.hasNext())
      addCalibrationResult((MagnetometerCalibrationResult)localIterator2.next());
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.calibrationCancelled = bool;
      return;
    }
  }

  public void addCalibrationProgress(MagnetometerCalibrationProgress paramMagnetometerCalibrationProgress)
  {
    if (paramMagnetometerCalibrationProgress != null)
    {
      int i = paramMagnetometerCalibrationProgress.getCompassId();
      this.calibrationProgressTracker.put(Integer.valueOf(i), paramMagnetometerCalibrationProgress);
      this.compassList.add(Integer.valueOf(i));
    }
  }

  public void addCalibrationResult(MagnetometerCalibrationResult paramMagnetometerCalibrationResult)
  {
    if (paramMagnetometerCalibrationResult != null)
    {
      int i = paramMagnetometerCalibrationResult.getCompassId();
      this.calibrationResultTracker.put(Integer.valueOf(paramMagnetometerCalibrationResult.getCompassId()), paramMagnetometerCalibrationResult);
      this.compassList.add(Integer.valueOf(i));
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public MagnetometerCalibrationProgress getCalibrationProgress(int paramInt)
  {
    return (MagnetometerCalibrationProgress)this.calibrationProgressTracker.get(Integer.valueOf(paramInt));
  }

  public MagnetometerCalibrationResult getCalibrationResult(int paramInt)
  {
    return (MagnetometerCalibrationResult)this.calibrationResultTracker.get(Integer.valueOf(paramInt));
  }

  public List<Integer> getCompassIds()
  {
    return this.compassList;
  }

  public boolean isCalibrationCancelled()
  {
    return this.calibrationCancelled;
  }

  public boolean isCalibrationComplete()
  {
    Iterator localIterator = this.compassList.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      if (!this.calibrationResultTracker.containsKey(localInteger))
        return false;
    }
    return true;
  }

  public void setCalibrationCancelled(boolean paramBoolean)
  {
    this.calibrationCancelled = paramBoolean;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeTypedList(new ArrayList(this.calibrationProgressTracker.values()));
    paramParcel.writeTypedList(new ArrayList(this.calibrationResultTracker.values()));
    if (this.calibrationCancelled);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationStatus
 * JD-Core Version:    0.6.2
 */