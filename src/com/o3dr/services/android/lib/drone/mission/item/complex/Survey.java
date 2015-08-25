package com.o3dr.services.android.lib.drone.mission.item.complex;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.ComplexItem;
import com.o3dr.services.android.lib.util.MathUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Survey extends MissionItem
  implements MissionItem.ComplexItem<Survey>, Parcelable
{
  public static final Parcelable.Creator<Survey> CREATOR = new Parcelable.Creator()
  {
    public Survey createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Survey(paramAnonymousParcel, null);
    }

    public Survey[] newArray(int paramAnonymousInt)
    {
      return new Survey[paramAnonymousInt];
    }
  };
  private List<LatLong> cameraLocations;
  private List<LatLong> gridPoints;
  private boolean isValid;
  private double polygonArea;
  private List<LatLong> polygonPoints;
  private SurveyDetail surveyDetail = new SurveyDetail();

  public Survey()
  {
    super(MissionItemType.SURVEY);
    this.surveyDetail.setAltitude(50.0D);
    this.surveyDetail.setAngle(0.0D);
    this.surveyDetail.setOverlap(50.0D);
    this.surveyDetail.setSidelap(60.0D);
    this.polygonPoints = new ArrayList();
    this.gridPoints = new ArrayList();
    this.cameraLocations = new ArrayList();
  }

  private Survey(Parcel paramParcel)
  {
    super(paramParcel);
    this.surveyDetail.setAltitude(50.0D);
    this.surveyDetail.setAngle(0.0D);
    this.surveyDetail.setOverlap(50.0D);
    this.surveyDetail.setSidelap(60.0D);
    this.polygonPoints = new ArrayList();
    this.gridPoints = new ArrayList();
    this.cameraLocations = new ArrayList();
    this.surveyDetail = ((SurveyDetail)paramParcel.readParcelable(SurveyDetail.class.getClassLoader()));
    this.polygonArea = paramParcel.readDouble();
    paramParcel.readTypedList(this.polygonPoints, LatLong.CREATOR);
    paramParcel.readTypedList(this.gridPoints, LatLong.CREATOR);
    paramParcel.readTypedList(this.cameraLocations, LatLong.CREATOR);
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.isValid = bool;
      return;
    }
  }

  public Survey(Survey paramSurvey)
  {
    this();
    copy(paramSurvey);
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
    return new Survey(this);
  }

  public void copy(Survey paramSurvey)
  {
    this.surveyDetail = new SurveyDetail(paramSurvey.surveyDetail);
    this.polygonArea = paramSurvey.polygonArea;
    this.polygonPoints = copyPointsList(paramSurvey.polygonPoints);
    this.gridPoints = copyPointsList(paramSurvey.gridPoints);
    this.cameraLocations = copyPointsList(paramSurvey.cameraLocations);
    this.isValid = paramSurvey.isValid;
  }

  public int getCameraCount()
  {
    return getCameraLocations().size();
  }

  public List<LatLong> getCameraLocations()
  {
    return this.cameraLocations;
  }

  public double getGridLength()
  {
    return MathUtils.getPolylineLength(this.gridPoints);
  }

  public List<LatLong> getGridPoints()
  {
    return this.gridPoints;
  }

  public int getNumberOfLines()
  {
    return this.gridPoints.size() / 2;
  }

  public double getPolygonArea()
  {
    return this.polygonArea;
  }

  public List<LatLong> getPolygonPoints()
  {
    return this.polygonPoints;
  }

  public SurveyDetail getSurveyDetail()
  {
    return this.surveyDetail;
  }

  public boolean isValid()
  {
    return this.isValid;
  }

  public void setCameraLocations(List<LatLong> paramList)
  {
    this.cameraLocations = paramList;
  }

  public void setGridPoints(List<LatLong> paramList)
  {
    this.gridPoints = paramList;
  }

  public void setPolygonArea(double paramDouble)
  {
    this.polygonArea = paramDouble;
  }

  public void setPolygonPoints(List<LatLong> paramList)
  {
    this.polygonPoints = paramList;
  }

  public void setSurveyDetail(SurveyDetail paramSurveyDetail)
  {
    this.surveyDetail = paramSurveyDetail;
  }

  public void setValid(boolean paramBoolean)
  {
    this.isValid = paramBoolean;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeParcelable(this.surveyDetail, 0);
    paramParcel.writeDouble(this.polygonArea);
    paramParcel.writeTypedList(this.polygonPoints);
    paramParcel.writeTypedList(this.gridPoints);
    paramParcel.writeTypedList(this.cameraLocations);
    boolean bool = this.isValid;
    byte b = 0;
    if (bool)
      b = 1;
    paramParcel.writeByte(b);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.complex.Survey
 * JD-Core Version:    0.6.2
 */