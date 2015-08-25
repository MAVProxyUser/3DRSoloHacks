package android.support.v7.internal.view.menu;

class BaseWrapper<T>
{
  final T mWrappedObject;

  BaseWrapper(T paramT)
  {
    if (paramT == null)
      throw new IllegalArgumentException("Wrapped Object can not be null.");
    this.mWrappedObject = paramT;
  }

  public T getWrappedObject()
  {
    return this.mWrappedObject;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.BaseWrapper
 * JD-Core Version:    0.6.2
 */