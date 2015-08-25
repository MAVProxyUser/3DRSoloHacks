package io.fabric.sdk.android.services.concurrency;

public abstract interface Task
{
  public abstract Throwable getError();

  public abstract boolean isFinished();

  public abstract void setError(Throwable paramThrowable);

  public abstract void setFinished(boolean paramBoolean);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.Task
 * JD-Core Version:    0.6.2
 */