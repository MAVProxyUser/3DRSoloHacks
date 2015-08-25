package io.fabric.sdk.android.services.events;

public abstract interface EventsStrategy<T> extends FileRollOverManager, EventsManager<T>
{
  public abstract FilesSender getFilesSender();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EventsStrategy
 * JD-Core Version:    0.6.2
 */