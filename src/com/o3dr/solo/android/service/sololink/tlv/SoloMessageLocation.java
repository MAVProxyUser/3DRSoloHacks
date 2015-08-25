package com.o3dr.solo.android.service.sololink.tlv;

import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import java.nio.ByteBuffer;

public class SoloMessageLocation extends TLVPacket
{
  private LatLongAlt coordinate;

  public SoloMessageLocation(double paramDouble1, double paramDouble2, float paramFloat)
  {
    super(2, 20);
    this.coordinate = new LatLongAlt(paramDouble1, paramDouble2, paramFloat);
  }

  public SoloMessageLocation(LatLongAlt paramLatLongAlt)
  {
    super(2, 20);
    this.coordinate = paramLatLongAlt;
  }

  public LatLongAlt getCoordinate()
  {
    return this.coordinate;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putDouble(this.coordinate.getLatitude());
    paramByteBuffer.putDouble(this.coordinate.getLongitude());
    paramByteBuffer.putFloat((float)this.coordinate.getAltitude());
  }

  public void setCoordinate(LatLongAlt paramLatLongAlt)
  {
    this.coordinate = paramLatLongAlt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloMessageLocation
 * JD-Core Version:    0.6.2
 */