package io.fabric.sdk.android.services.events;

public class DisabledEventsStrategy<T>
  implements EventsStrategy<T>
{
  public void cancelTimeBasedFileRollOver()
  {
  }

  public void deleteAllEvents()
  {
  }

  public FilesSender getFilesSender()
  {
    return null;
  }

  public void recordEvent(T paramT)
  {
  }

  public boolean rollFileOver()
  {
    return false;
  }

  public void scheduleTimeBasedRollOverIfNeeded()
  {
  }

  public void sendEvents()
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.DisabledEventsStrategy
 * JD-Core Version:    0.6.2
 */