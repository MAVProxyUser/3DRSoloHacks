package io.fabric.sdk.android.services.common;

public enum DeliveryMechanism
{
  public static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
  private final int id;

  static
  {
    TEST_DISTRIBUTION = new DeliveryMechanism("TEST_DISTRIBUTION", 2, 3);
    APP_STORE = new DeliveryMechanism("APP_STORE", 3, 4);
    DeliveryMechanism[] arrayOfDeliveryMechanism = new DeliveryMechanism[4];
    arrayOfDeliveryMechanism[0] = DEVELOPER;
    arrayOfDeliveryMechanism[1] = USER_SIDELOAD;
    arrayOfDeliveryMechanism[2] = TEST_DISTRIBUTION;
    arrayOfDeliveryMechanism[3] = APP_STORE;
  }

  private DeliveryMechanism(int paramInt)
  {
    this.id = paramInt;
  }

  public static DeliveryMechanism determineFrom(String paramString)
  {
    if ("io.crash.air".equals(paramString))
      return TEST_DISTRIBUTION;
    if (paramString != null)
      return APP_STORE;
    return DEVELOPER;
  }

  public int getId()
  {
    return this.id;
  }

  public String toString()
  {
    return Integer.toString(this.id);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.DeliveryMechanism
 * JD-Core Version:    0.6.2
 */