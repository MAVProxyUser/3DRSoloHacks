package io.fabric.sdk.android.services.concurrency;

public class UnmetDependencyException extends RuntimeException
{
  public UnmetDependencyException()
  {
  }

  public UnmetDependencyException(String paramString)
  {
    super(paramString);
  }

  public UnmetDependencyException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public UnmetDependencyException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.UnmetDependencyException
 * JD-Core Version:    0.6.2
 */