package com.o3dr.solo.android.service.sololink.tlv;

import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import java.nio.ByteBuffer;

public class SoloCableCamWaypoint extends TLVPacket
{
  private LatLongAlt coordinate;
  private float degreesYaw;
  private float pitch;

  public SoloCableCamWaypoint(double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(1001, 28);
    this.coordinate = new LatLongAlt(paramDouble1, paramDouble2, paramFloat1);
    this.degreesYaw = paramFloat2;
    this.pitch = paramFloat3;
  }

  public LatLongAlt getCoordinate()
  {
    return this.coordinate;
  }

  public float getDegreesYaw()
  {
    return this.degreesYaw;
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putDouble(this.coordinate.getLatitude());
    paramByteBuffer.putDouble(this.coordinate.getLongitude());
    paramByteBuffer.putFloat((float)this.coordinate.getAltitude());
    paramByteBuffer.putFloat(this.degreesYaw);
    paramByteBuffer.putFloat(this.pitch);
  }

  public float getPitch()
  {
    return this.pitch;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloCableCamWaypoint
 * JD-Core Version:    0.6.2
 */