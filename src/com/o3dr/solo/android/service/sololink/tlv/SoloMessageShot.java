package com.o3dr.solo.android.service.sololink.tlv;

import android.content.Context;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import java.nio.ByteBuffer;

public abstract class SoloMessageShot extends TLVPacket
{
  public static final int SHOT_CABLECAM = 2;
  public static final int SHOT_FOLLOW = 5;
  public static final int SHOT_FREE_FLIGHT = -1;
  public static final int SHOT_ORBIT = 1;
  public static final int SHOT_SELFIE;
  private int shotType;

  public SoloMessageShot(int paramInt1, int paramInt2)
  {
    super(paramInt1, 4);
    this.shotType = paramInt2;
  }

  public static CharSequence getFlightModeLabel(Context paramContext, VehicleMode paramVehicleMode)
  {
    if ((paramContext == null) || (paramVehicleMode == null))
      return null;
    switch (1.$SwitchMap$com$o3dr$services$android$lib$drone$property$VehicleMode[paramVehicleMode.ordinal()])
    {
    case 4:
    default:
      return paramVehicleMode.getLabel();
    case 1:
      return paramContext.getText(2131099770);
    case 2:
      return paramContext.getText(2131099766);
    case 3:
      return paramContext.getText(2131099772);
    case 5:
      return paramContext.getText(2131099768);
    case 6:
      return paramContext.getText(2131099771);
    case 7:
    }
    return paramContext.getText(2131099767);
  }

  public static CharSequence getShotLabel(Context paramContext, int paramInt)
  {
    if (paramContext == null)
      return null;
    switch (paramInt)
    {
    case 3:
    case 4:
    default:
      return null;
    case -1:
      return paramContext.getText(2131099888);
    case 0:
      return paramContext.getText(2131099915);
    case 1:
      return paramContext.getText(2131099902);
    case 2:
      return paramContext.getText(2131099853);
    case 5:
    }
    return paramContext.getText(2131099887);
  }

  protected void getMessageValue(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.putInt(this.shotType);
  }

  public int getShotType()
  {
    return this.shotType;
  }

  public void setShotType(int paramInt)
  {
    this.shotType = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot
 * JD-Core Version:    0.6.2
 */