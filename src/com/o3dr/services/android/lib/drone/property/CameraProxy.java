package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.item.complex.CameraDetail;
import java.util.ArrayList;
import java.util.List;

public class CameraProxy
  implements Parcelable
{
  public static final Parcelable.Creator<CameraProxy> CREATOR = new Parcelable.Creator()
  {
    public CameraProxy createFromParcel(Parcel paramAnonymousParcel)
    {
      return new CameraProxy(paramAnonymousParcel, null);
    }

    public CameraProxy[] newArray(int paramAnonymousInt)
    {
      return new CameraProxy[paramAnonymousInt];
    }
  };
  private List<CameraDetail> availableCameraInfos = new ArrayList();
  private CameraDetail cameraDetail;
  private FootPrint currentFieldOfView;
  private List<FootPrint> footPrints = new ArrayList();

  private CameraProxy(Parcel paramParcel)
  {
    this.cameraDetail = ((CameraDetail)paramParcel.readParcelable(CameraDetail.class.getClassLoader()));
    paramParcel.readTypedList(this.footPrints, FootPrint.CREATOR);
    this.currentFieldOfView = ((FootPrint)paramParcel.readParcelable(FootPrint.class.getClassLoader()));
    paramParcel.readTypedList(this.availableCameraInfos, CameraDetail.CREATOR);
  }

  public CameraProxy(CameraDetail paramCameraDetail, FootPrint paramFootPrint, List<FootPrint> paramList, List<CameraDetail> paramList1)
  {
    this.cameraDetail = paramCameraDetail;
    this.currentFieldOfView = paramFootPrint;
    this.footPrints = paramList;
    this.availableCameraInfos = paramList1;
  }

  public int describeContents()
  {
    return 0;
  }

  public List<CameraDetail> getAvailableCameraInfos()
  {
    return this.availableCameraInfos;
  }

  public CameraDetail getCameraDetail()
  {
    return this.cameraDetail;
  }

  public FootPrint getCurrentFieldOfView()
  {
    return this.currentFieldOfView;
  }

  public List<FootPrint> getFootPrints()
  {
    return this.footPrints;
  }

  public FootPrint getLastFootPrint()
  {
    return (FootPrint)this.footPrints.get(-1 + this.footPrints.size());
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.cameraDetail, 0);
    paramParcel.writeTypedList(this.footPrints);
    paramParcel.writeParcelable(this.currentFieldOfView, 0);
    paramParcel.writeTypedList(this.availableCameraInfos);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.CameraProxy
 * JD-Core Version:    0.6.2
 */