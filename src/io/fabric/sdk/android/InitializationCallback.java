package io.fabric.sdk.android;

public abstract interface InitializationCallback<T>
{
  public static final InitializationCallback EMPTY = new Empty(null);

  public abstract void failure(Exception paramException);

  public abstract void success(T paramT);

  public static class Empty
    implements InitializationCallback<Object>
  {
    public void failure(Exception paramException)
    {
    }

    public void success(Object paramObject)
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.InitializationCallback
 * JD-Core Version:    0.6.2
 */