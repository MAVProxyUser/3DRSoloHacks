package io.fabric.sdk.android.services.concurrency;

public enum Priority
{
  static
  {
    HIGH = new Priority("HIGH", 2);
    IMMEDIATE = new Priority("IMMEDIATE", 3);
    Priority[] arrayOfPriority = new Priority[4];
    arrayOfPriority[0] = LOW;
    arrayOfPriority[1] = NORMAL;
    arrayOfPriority[2] = HIGH;
    arrayOfPriority[3] = IMMEDIATE;
  }

  static <Y> int compareTo(PriorityProvider paramPriorityProvider, Y paramY)
  {
    if ((paramY instanceof PriorityProvider));
    for (Priority localPriority = ((PriorityProvider)paramY).getPriority(); ; localPriority = NORMAL)
      return localPriority.ordinal() - paramPriorityProvider.getPriority().ordinal();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.Priority
 * JD-Core Version:    0.6.2
 */