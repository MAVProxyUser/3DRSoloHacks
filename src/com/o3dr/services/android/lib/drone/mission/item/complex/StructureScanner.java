package com.o3dr.services.android.lib.drone.mission.item.complex;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.ComplexItem;
import com.o3dr.services.android.lib.drone.mission.item.spatial.BaseSpatialItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StructureScanner extends BaseSpatialItem
  implements MissionItem.ComplexItem<StructureScanner>, Parcelable
{
  public static final Parcelable.Creator<StructureScanner> CREATOR = new Parcelable.Creator()
  {
    public StructureScanner createFromParcel(Parcel paramAnonymousParcel)
    {
      return new StructureScanner(paramAnonymousParcel, null);
    }

    public StructureScanner[] newArray(int paramAnonymousInt)
    {
      return new StructureScanner[paramAnonymousInt];
    }
  };
  private boolean crossHatch = false;
  private double heightStep = 5.0D;
  private List<LatLong> path = new ArrayList();
  private double radius = 10.0D;
  private int stepsCount = 2;
  private SurveyDetail surveyDetail = new SurveyDetail();

  public StructureScanner()
  {
    super(MissionItemType.STRUCTURE_SCANNER);
  }

  private StructureScanner(Parcel paramParcel)
  {
    super(paramParcel);
    this.radius = paramParcel.readDouble();
    this.heightStep = paramParcel.readDouble();
    this.stepsCount = paramParcel.readInt();
    int i = paramParcel.readByte();
    boolean bool = false;
    if (i != 0)
      bool = true;
    this.crossHatch = bool;
    this.surveyDetail = ((SurveyDetail)paramParcel.readParcelable(SurveyDetail.class.getClassLoader()));
    paramParcel.readTypedList(this.path, LatLong.CREATOR);
  }

  public StructureScanner(StructureScanner paramStructureScanner)
  {
    super(paramStructureScanner);
    copy(paramStructureScanner);
  }

  private List<LatLong> copyPointsList(List<LatLong> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      localArrayList.add(new LatLong((LatLong)localIterator.next()));
    return localArrayList;
  }

  public MissionItem clone()
  {
    return new StructureScanner(this);
  }

  public void copy(StructureScanner paramStructureScanner)
  {
    this.radius = paramStructureScanner.radius;
    this.heightStep = paramStructureScanner.heightStep;
    this.stepsCount = paramStructureScanner.stepsCount;
    this.crossHatch = paramStructureScanner.crossHatch;
    this.surveyDetail = new SurveyDetail(paramStructureScanner.surveyDetail);
    this.path = copyPointsList(paramStructureScanner.path);
  }

  public double getHeightStep()
  {
    return this.heightStep;
  }

  public List<LatLong> getPath()
  {
    return this.path;
  }

  public double getRadius()
  {
    return this.radius;
  }

  public int getStepsCount()
  {
    return this.stepsCount;
  }

  public SurveyDetail getSurveyDetail()
  {
    return this.surveyDetail;
  }

  public boolean isCrossHatch()
  {
    return this.crossHatch;
  }

  public void setCrossHatch(boolean paramBoolean)
  {
    this.crossHatch = paramBoolean;
  }

  public void setHeightStep(double paramDouble)
  {
    this.heightStep = paramDouble;
  }

  public void setPath(List<LatLong> paramList)
  {
    this.path = paramList;
  }

  public void setRadius(double paramDouble)
  {
    this.radius = paramDouble;
  }

  public void setStepsCount(int paramInt)
  {
    this.stepsCount = paramInt;
  }

  public void setSurveyDetail(SurveyDetail paramSurveyDetail)
  {
    this.surveyDetail = paramSurveyDetail;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.radius);
    paramParcel.writeDouble(this.heightStep);
    paramParcel.writeInt(this.stepsCount);
    if (this.crossHatch);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      paramParcel.writeParcelable(this.surveyDetail, 0);
      paramParcel.writeTypedList(this.path);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.complex.StructureScanner
 * JD-Core Version:    0.6.2
 */