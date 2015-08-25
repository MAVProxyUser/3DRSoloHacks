package io.fabric.sdk.android.services.common;

public class ResponseParser
{
  public static final int ResponseActionDiscard = 0;
  public static final int ResponseActionRetry = 1;

  public static int parse(int paramInt)
  {
    if ((paramInt >= 200) && (paramInt <= 299));
    do
    {
      return 0;
      if ((paramInt >= 300) && (paramInt <= 399))
        return 1;
    }
    while ((paramInt >= 400) && (paramInt <= 499));
    if (paramInt >= 500)
      return 1;
    return 1;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.ResponseParser
 * JD-Core Version:    0.6.2
 */