package io.fabric.sdk.android.services.events;

public abstract interface EventsManager<T>
{
  public abstract void deleteAllEvents();

  public abstract void recordEvent(T paramT);

  public abstract void sendEvents();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EventsManager
 * JD-Core Version:    0.6.2
 */