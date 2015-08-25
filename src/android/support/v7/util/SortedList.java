package android.support.v7.util;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SortedList<T>
{
  private static final int CAPACITY_GROWTH = 10;
  private static final int DELETION = 2;
  private static final int INSERTION = 1;
  public static final int INVALID_POSITION = -1;
  private static final int LOOKUP = 4;
  private static final int MIN_CAPACITY = 10;
  private BatchedCallback mBatchedCallback;
  private Callback mCallback;
  T[] mData;
  private int mSize;
  private final Class<T> mTClass;

  public SortedList(Class<T> paramClass, Callback<T> paramCallback)
  {
    this(paramClass, paramCallback, 10);
  }

  public SortedList(Class<T> paramClass, Callback<T> paramCallback, int paramInt)
  {
    this.mTClass = paramClass;
    this.mData = ((Object[])Array.newInstance(paramClass, paramInt));
    this.mCallback = paramCallback;
    this.mSize = 0;
  }

  private int add(T paramT, boolean paramBoolean)
  {
    int i = findIndexOf(paramT, 1);
    if (i == -1)
      i = 0;
    Object localObject;
    do
    {
      do
      {
        addToData(i, paramT);
        if (paramBoolean)
          this.mCallback.onInserted(i, 1);
        return i;
      }
      while (i >= this.mSize);
      localObject = this.mData[i];
    }
    while (!this.mCallback.areItemsTheSame(localObject, paramT));
    if (this.mCallback.areContentsTheSame(localObject, paramT))
    {
      this.mData[i] = paramT;
      return i;
    }
    this.mData[i] = paramT;
    this.mCallback.onChanged(i, 1);
    return i;
  }

  private void addToData(int paramInt, T paramT)
  {
    if (paramInt > this.mSize)
      throw new IndexOutOfBoundsException("cannot add item to " + paramInt + " because size is " + this.mSize);
    if (this.mSize == this.mData.length)
    {
      Object[] arrayOfObject = (Object[])Array.newInstance(this.mTClass, 10 + this.mData.length);
      System.arraycopy(this.mData, 0, arrayOfObject, 0, paramInt);
      arrayOfObject[paramInt] = paramT;
      System.arraycopy(this.mData, paramInt, arrayOfObject, paramInt + 1, this.mSize - paramInt);
      this.mData = arrayOfObject;
    }
    while (true)
    {
      this.mSize = (1 + this.mSize);
      return;
      System.arraycopy(this.mData, paramInt, this.mData, paramInt + 1, this.mSize - paramInt);
      this.mData[paramInt] = paramT;
    }
  }

  private int findIndexOf(T paramT, int paramInt)
  {
    int i = 0;
    int j = this.mSize;
    while (i < j)
    {
      int k = (i + j) / 2;
      Object localObject = this.mData[k];
      int m = this.mCallback.compare(localObject, paramT);
      if (m < 0)
      {
        i = k + 1;
      }
      else
      {
        if (m == 0)
        {
          if (this.mCallback.areItemsTheSame(localObject, paramT));
          int n;
          do
          {
            return k;
            n = linearEqualitySearch(paramT, k, i, j);
            if (paramInt != 1)
              break;
          }
          while (n == -1);
          return n;
          return n;
        }
        j = k;
      }
    }
    if (paramInt == 1);
    while (true)
    {
      return i;
      i = -1;
    }
  }

  private int linearEqualitySearch(T paramT, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 - 1;
    Object localObject2;
    if (i >= paramInt2)
    {
      localObject2 = this.mData[i];
      if (this.mCallback.compare(localObject2, paramT) == 0)
        break label69;
    }
    for (int j = paramInt1 + 1; ; j++)
    {
      Object localObject1;
      if (j < paramInt3)
      {
        localObject1 = this.mData[j];
        if (this.mCallback.compare(localObject1, paramT) == 0);
      }
      else
      {
        return -1;
        label69: if (this.mCallback.areItemsTheSame(localObject2, paramT))
          return i;
        i--;
        break;
      }
      if (this.mCallback.areItemsTheSame(localObject1, paramT))
        return j;
    }
  }

  private boolean remove(T paramT, boolean paramBoolean)
  {
    int i = findIndexOf(paramT, 2);
    if (i == -1)
      return false;
    removeItemAtIndex(i, paramBoolean);
    return true;
  }

  private void removeItemAtIndex(int paramInt, boolean paramBoolean)
  {
    System.arraycopy(this.mData, paramInt + 1, this.mData, paramInt, -1 + (this.mSize - paramInt));
    this.mSize = (-1 + this.mSize);
    this.mData[this.mSize] = null;
    if (paramBoolean)
      this.mCallback.onRemoved(paramInt, 1);
  }

  public int add(T paramT)
  {
    return add(paramT, true);
  }

  public void beginBatchedUpdates()
  {
    if ((this.mCallback instanceof BatchedCallback))
      return;
    if (this.mBatchedCallback == null)
      this.mBatchedCallback = new BatchedCallback(this.mCallback);
    this.mCallback = this.mBatchedCallback;
  }

  public void clear()
  {
    if (this.mSize == 0)
      return;
    int i = this.mSize;
    Arrays.fill(this.mData, 0, i, null);
    this.mSize = 0;
    this.mCallback.onRemoved(0, i);
  }

  public void endBatchedUpdates()
  {
    if ((this.mCallback instanceof BatchedCallback))
      ((BatchedCallback)this.mCallback).dispatchLastEvent();
    if (this.mCallback == this.mBatchedCallback)
      this.mCallback = this.mBatchedCallback.mWrappedCallback;
  }

  public T get(int paramInt)
    throws IndexOutOfBoundsException
  {
    if ((paramInt >= this.mSize) || (paramInt < 0))
      throw new IndexOutOfBoundsException("Asked to get item at " + paramInt + " but size is " + this.mSize);
    return this.mData[paramInt];
  }

  public int indexOf(T paramT)
  {
    return findIndexOf(paramT, 4);
  }

  public void recalculatePositionOfItemAt(int paramInt)
  {
    Object localObject = get(paramInt);
    removeItemAtIndex(paramInt, false);
    int i = add(localObject, false);
    if (paramInt != i)
      this.mCallback.onMoved(paramInt, i);
  }

  public boolean remove(T paramT)
  {
    return remove(paramT, true);
  }

  public T removeItemAt(int paramInt)
  {
    Object localObject = get(paramInt);
    removeItemAtIndex(paramInt, true);
    return localObject;
  }

  public int size()
  {
    return this.mSize;
  }

  public void updateItemAt(int paramInt, T paramT)
  {
    Object localObject = get(paramInt);
    int i;
    if ((localObject == paramT) || (!this.mCallback.areContentsTheSame(localObject, paramT)))
    {
      i = 1;
      if ((localObject == paramT) || (this.mCallback.compare(localObject, paramT) != 0))
        break label71;
      this.mData[paramInt] = paramT;
      if (i != 0)
        this.mCallback.onChanged(paramInt, 1);
    }
    label71: int j;
    do
    {
      return;
      i = 0;
      break;
      if (i != 0)
        this.mCallback.onChanged(paramInt, 1);
      removeItemAtIndex(paramInt, false);
      j = add(paramT, false);
    }
    while (paramInt == j);
    this.mCallback.onMoved(paramInt, j);
  }

  public static class BatchedCallback<T2> extends SortedList.Callback<T2>
  {
    static final int TYPE_ADD = 1;
    static final int TYPE_CHANGE = 3;
    static final int TYPE_MOVE = 4;
    static final int TYPE_NONE = 0;
    static final int TYPE_REMOVE = 2;
    int mLastEventCount = -1;
    int mLastEventPosition = -1;
    int mLastEventType = 0;
    private final SortedList.Callback<T2> mWrappedCallback;

    public BatchedCallback(SortedList.Callback<T2> paramCallback)
    {
      this.mWrappedCallback = paramCallback;
    }

    public boolean areContentsTheSame(T2 paramT21, T2 paramT22)
    {
      return this.mWrappedCallback.areContentsTheSame(paramT21, paramT22);
    }

    public boolean areItemsTheSame(T2 paramT21, T2 paramT22)
    {
      return this.mWrappedCallback.areItemsTheSame(paramT21, paramT22);
    }

    public int compare(T2 paramT21, T2 paramT22)
    {
      return this.mWrappedCallback.compare(paramT21, paramT22);
    }

    public void dispatchLastEvent()
    {
      if (this.mLastEventType == 0)
        return;
      switch (this.mLastEventType)
      {
      default:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        this.mLastEventType = 0;
        return;
        this.mWrappedCallback.onInserted(this.mLastEventPosition, this.mLastEventCount);
        continue;
        this.mWrappedCallback.onRemoved(this.mLastEventPosition, this.mLastEventCount);
        continue;
        this.mWrappedCallback.onChanged(this.mLastEventPosition, this.mLastEventCount);
      }
    }

    public void onChanged(int paramInt1, int paramInt2)
    {
      if ((this.mLastEventType == 3) && (paramInt1 <= this.mLastEventPosition + this.mLastEventCount) && (paramInt1 + paramInt2 >= this.mLastEventPosition))
      {
        int i = this.mLastEventPosition + this.mLastEventCount;
        this.mLastEventPosition = Math.min(paramInt1, this.mLastEventPosition);
        this.mLastEventCount = (Math.max(i, paramInt1 + paramInt2) - this.mLastEventPosition);
        return;
      }
      dispatchLastEvent();
      this.mLastEventPosition = paramInt1;
      this.mLastEventCount = paramInt2;
      this.mLastEventType = 3;
    }

    public void onInserted(int paramInt1, int paramInt2)
    {
      if ((this.mLastEventType == 1) && (paramInt1 >= this.mLastEventPosition) && (paramInt1 <= this.mLastEventPosition + this.mLastEventCount))
      {
        this.mLastEventCount = (paramInt2 + this.mLastEventCount);
        this.mLastEventPosition = Math.min(paramInt1, this.mLastEventPosition);
        return;
      }
      dispatchLastEvent();
      this.mLastEventPosition = paramInt1;
      this.mLastEventCount = paramInt2;
      this.mLastEventType = 1;
    }

    public void onMoved(int paramInt1, int paramInt2)
    {
      dispatchLastEvent();
      this.mWrappedCallback.onMoved(paramInt1, paramInt2);
    }

    public void onRemoved(int paramInt1, int paramInt2)
    {
      if ((this.mLastEventType == 2) && (this.mLastEventPosition == paramInt1))
      {
        this.mLastEventCount = (paramInt2 + this.mLastEventCount);
        return;
      }
      dispatchLastEvent();
      this.mLastEventPosition = paramInt1;
      this.mLastEventCount = paramInt2;
      this.mLastEventType = 2;
    }
  }

  public static abstract class Callback<T2>
  {
    public abstract boolean areContentsTheSame(T2 paramT21, T2 paramT22);

    public abstract boolean areItemsTheSame(T2 paramT21, T2 paramT22);

    public abstract int compare(T2 paramT21, T2 paramT22);

    public abstract void onChanged(int paramInt1, int paramInt2);

    public abstract void onInserted(int paramInt1, int paramInt2);

    public abstract void onMoved(int paramInt1, int paramInt2);

    public abstract void onRemoved(int paramInt1, int paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.util.SortedList
 * JD-Core Version:    0.6.2
 */