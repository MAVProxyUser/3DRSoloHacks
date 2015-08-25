package android.support.v7.widget;

import java.util.ArrayList;

class PositionMap<E>
  implements Cloneable
{
  private static final Object DELETED = new Object();
  private boolean mGarbage = false;
  private int[] mKeys;
  private int mSize;
  private Object[] mValues;

  public PositionMap()
  {
    this(10);
  }

  public PositionMap(int paramInt)
  {
    if (paramInt == 0)
      this.mKeys = ContainerHelpers.EMPTY_INTS;
    int i;
    for (this.mValues = ContainerHelpers.EMPTY_OBJECTS; ; this.mValues = new Object[i])
    {
      this.mSize = 0;
      return;
      i = idealIntArraySize(paramInt);
      this.mKeys = new int[i];
    }
  }

  private void gc()
  {
    int i = this.mSize;
    int j = 0;
    int[] arrayOfInt = this.mKeys;
    Object[] arrayOfObject = this.mValues;
    for (int k = 0; k < i; k++)
    {
      Object localObject = arrayOfObject[k];
      if (localObject != DELETED)
      {
        if (k != j)
        {
          arrayOfInt[j] = arrayOfInt[k];
          arrayOfObject[j] = localObject;
          arrayOfObject[k] = null;
        }
        j++;
      }
    }
    this.mGarbage = false;
    this.mSize = j;
  }

  static int idealBooleanArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt);
  }

  static int idealByteArraySize(int paramInt)
  {
    for (int i = 4; ; i++)
      if (i < 32)
      {
        if (paramInt <= -12 + (1 << i))
          paramInt = -12 + (1 << i);
      }
      else
        return paramInt;
  }

  static int idealCharArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 2) / 2;
  }

  static int idealFloatArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 4) / 4;
  }

  static int idealIntArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 4) / 4;
  }

  static int idealLongArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 8) / 8;
  }

  static int idealObjectArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 4) / 4;
  }

  static int idealShortArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 2) / 2;
  }

  public void append(int paramInt, E paramE)
  {
    if ((this.mSize != 0) && (paramInt <= this.mKeys[(-1 + this.mSize)]))
    {
      put(paramInt, paramE);
      return;
    }
    if ((this.mGarbage) && (this.mSize >= this.mKeys.length))
      gc();
    int i = this.mSize;
    if (i >= this.mKeys.length)
    {
      int j = idealIntArraySize(i + 1);
      int[] arrayOfInt = new int[j];
      Object[] arrayOfObject = new Object[j];
      System.arraycopy(this.mKeys, 0, arrayOfInt, 0, this.mKeys.length);
      System.arraycopy(this.mValues, 0, arrayOfObject, 0, this.mValues.length);
      this.mKeys = arrayOfInt;
      this.mValues = arrayOfObject;
    }
    this.mKeys[i] = paramInt;
    this.mValues[i] = paramE;
    this.mSize = (i + 1);
  }

  public void clear()
  {
    int i = this.mSize;
    Object[] arrayOfObject = this.mValues;
    for (int j = 0; j < i; j++)
      arrayOfObject[j] = null;
    this.mSize = 0;
    this.mGarbage = false;
  }

  public PositionMap<E> clone()
  {
    PositionMap localPositionMap = null;
    try
    {
      localPositionMap = (PositionMap)super.clone();
      localPositionMap.mKeys = ((int[])this.mKeys.clone());
      localPositionMap.mValues = ((Object[])this.mValues.clone());
      return localPositionMap;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
    }
    return localPositionMap;
  }

  public void delete(int paramInt)
  {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    if ((i >= 0) && (this.mValues[i] != DELETED))
    {
      this.mValues[i] = DELETED;
      this.mGarbage = true;
    }
  }

  public E get(int paramInt)
  {
    return get(paramInt, null);
  }

  public E get(int paramInt, E paramE)
  {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    if ((i < 0) || (this.mValues[i] == DELETED))
      return paramE;
    return this.mValues[i];
  }

  public int indexOfKey(int paramInt)
  {
    if (this.mGarbage)
      gc();
    return ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
  }

  public int indexOfValue(E paramE)
  {
    if (this.mGarbage)
      gc();
    for (int i = 0; i < this.mSize; i++)
      if (this.mValues[i] == paramE)
        return i;
    return -1;
  }

  public void insertKeyRange(int paramInt1, int paramInt2)
  {
  }

  public int keyAt(int paramInt)
  {
    if (this.mGarbage)
      gc();
    return this.mKeys[paramInt];
  }

  public void put(int paramInt, E paramE)
  {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    if (i >= 0)
    {
      this.mValues[i] = paramE;
      return;
    }
    int j = i ^ 0xFFFFFFFF;
    if ((j < this.mSize) && (this.mValues[j] == DELETED))
    {
      this.mKeys[j] = paramInt;
      this.mValues[j] = paramE;
      return;
    }
    if ((this.mGarbage) && (this.mSize >= this.mKeys.length))
    {
      gc();
      j = 0xFFFFFFFF ^ ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramInt);
    }
    if (this.mSize >= this.mKeys.length)
    {
      int k = idealIntArraySize(1 + this.mSize);
      int[] arrayOfInt = new int[k];
      Object[] arrayOfObject = new Object[k];
      System.arraycopy(this.mKeys, 0, arrayOfInt, 0, this.mKeys.length);
      System.arraycopy(this.mValues, 0, arrayOfObject, 0, this.mValues.length);
      this.mKeys = arrayOfInt;
      this.mValues = arrayOfObject;
    }
    if (this.mSize - j != 0)
    {
      System.arraycopy(this.mKeys, j, this.mKeys, j + 1, this.mSize - j);
      System.arraycopy(this.mValues, j, this.mValues, j + 1, this.mSize - j);
    }
    this.mKeys[j] = paramInt;
    this.mValues[j] = paramE;
    this.mSize = (1 + this.mSize);
  }

  public void remove(int paramInt)
  {
    delete(paramInt);
  }

  public void removeAt(int paramInt)
  {
    if (this.mValues[paramInt] != DELETED)
    {
      this.mValues[paramInt] = DELETED;
      this.mGarbage = true;
    }
  }

  public void removeAtRange(int paramInt1, int paramInt2)
  {
    int i = Math.min(this.mSize, paramInt1 + paramInt2);
    for (int j = paramInt1; j < i; j++)
      removeAt(j);
  }

  public void removeKeyRange(ArrayList<E> paramArrayList, int paramInt1, int paramInt2)
  {
  }

  public void setValueAt(int paramInt, E paramE)
  {
    if (this.mGarbage)
      gc();
    this.mValues[paramInt] = paramE;
  }

  public int size()
  {
    if (this.mGarbage)
      gc();
    return this.mSize;
  }

  public String toString()
  {
    if (size() <= 0)
      return "{}";
    StringBuilder localStringBuilder = new StringBuilder(28 * this.mSize);
    localStringBuilder.append('{');
    int i = 0;
    if (i < this.mSize)
    {
      if (i > 0)
        localStringBuilder.append(", ");
      localStringBuilder.append(keyAt(i));
      localStringBuilder.append('=');
      Object localObject = valueAt(i);
      if (localObject != this)
        localStringBuilder.append(localObject);
      while (true)
      {
        i++;
        break;
        localStringBuilder.append("(this Map)");
      }
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }

  public E valueAt(int paramInt)
  {
    if (this.mGarbage)
      gc();
    return this.mValues[paramInt];
  }

  static class ContainerHelpers
  {
    static final boolean[] EMPTY_BOOLEANS = new boolean[0];
    static final int[] EMPTY_INTS = new int[0];
    static final long[] EMPTY_LONGS = new long[0];
    static final Object[] EMPTY_OBJECTS = new Object[0];

    static int binarySearch(int[] paramArrayOfInt, int paramInt1, int paramInt2)
    {
      int i = 0;
      int j = paramInt1 - 1;
      while (i <= j)
      {
        k = i + j >>> 1;
        int m = paramArrayOfInt[k];
        if (m < paramInt2)
        {
          i = k + 1;
        }
        else
        {
          if (m <= paramInt2)
            break label61;
          j = k - 1;
        }
      }
      int k = i ^ 0xFFFFFFFF;
      label61: return k;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.PositionMap
 * JD-Core Version:    0.6.2
 */