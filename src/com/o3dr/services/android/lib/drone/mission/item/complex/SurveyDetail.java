package com.o3dr.services.android.lib.drone.mission.item.complex;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SurveyDetail
  implements Parcelable
{
  public static final Parcelable.Creator<SurveyDetail> CREATOR = new Parcelable.Creator()
  {
    public SurveyDetail createFromParcel(Parcel paramAnonymousParcel)
    {
      return new SurveyDetail(paramAnonymousParcel, null);
    }

    public SurveyDetail[] newArray(int paramAnonymousInt)
    {
      return new SurveyDetail[paramAnonymousInt];
    }
  };
  private double altitude;
  private double angle;
  private CameraDetail cameraDetail;
  private double overlap;
  private double sidelap;

  public SurveyDetail()
  {
  }

  private SurveyDetail(Parcel paramParcel)
  {
    this.altitude = paramParcel.readDouble();
    this.angle = paramParcel.readDouble();
    this.overlap = paramParcel.readDouble();
    this.sidelap = paramParcel.readDouble();
    this.cameraDetail = ((CameraDetail)paramParcel.readParcelable(CameraDetail.class.getClassLoader()));
  }

  public SurveyDetail(SurveyDetail paramSurveyDetail)
  {
    this.altitude = paramSurveyDetail.altitude;
    this.angle = paramSurveyDetail.angle;
    this.overlap = paramSurveyDetail.overlap;
    this.sidelap = paramSurveyDetail.sidelap;
    if (paramSurveyDetail.cameraDetail == null);
    for (CameraDetail localCameraDetail = null; ; localCameraDetail = new CameraDetail(paramSurveyDetail.cameraDetail))
    {
      this.cameraDetail = localCameraDetail;
      return;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public double getAltitude()
  {
    return this.altitude;
  }

  public double getAngle()
  {
    return this.angle;
  }

  public CameraDetail getCameraDetail()
  {
    return this.cameraDetail;
  }

  public double getGroundResolution()
  {
    return this.altitude * this.cameraDetail.getSensorLateralSize().doubleValue() / this.cameraDetail.getFocalLength() * (this.altitude * this.cameraDetail.getSensorLongitudinalSize().doubleValue() / this.cameraDetail.getFocalLength()) / (1000.0D * this.cameraDetail.getSensorResolution()) / 10000.0D;
  }

  public double getLateralFootPrint()
  {
    return this.altitude * this.cameraDetail.getSensorLateralSize().doubleValue() / this.cameraDetail.getFocalLength();
  }

  public double getLateralPictureDistance()
  {
    return getLateralFootPrint() * (1.0D - 0.01D * this.sidelap);
  }

  public double getLongitudinalFootPrint()
  {
    return this.altitude * this.cameraDetail.getSensorLongitudinalSize().doubleValue() / this.cameraDetail.getFocalLength();
  }

  public double getLongitudinalPictureDistance()
  {
    return getLongitudinalFootPrint() * (1.0D - 0.01D * this.overlap);
  }

  public double getOverlap()
  {
    return this.overlap;
  }

  public double getSidelap()
  {
    return this.sidelap;
  }

  public void setAltitude(double paramDouble)
  {
    this.altitude = paramDouble;
  }

  public void setAngle(double paramDouble)
  {
    this.angle = paramDouble;
  }

  public void setCameraDetail(CameraDetail paramCameraDetail)
  {
    this.cameraDetail = paramCameraDetail;
  }

  public void setOverlap(double paramDouble)
  {
    this.overlap = paramDouble;
  }

  public void setSidelap(double paramDouble)
  {
    this.sidelap = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.altitude);
    paramParcel.writeDouble(this.angle);
    paramParcel.writeDouble(this.overlap);
    paramParcel.writeDouble(this.sidelap);
    paramParcel.writeParcelable(this.cameraDetail, 0);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.complex.SurveyDetail
 * JD-Core Version:    0.6.2
 */