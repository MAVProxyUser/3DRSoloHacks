package com.google.android.gms.common.data;

import java.util.ArrayList;

public abstract class zzf<T> extends AbstractDataBuffer<T>
{
  private boolean zzRO = false;
  private ArrayList<Integer> zzRP;

  protected zzf(DataHolder paramDataHolder)
  {
    super(paramDataHolder);
  }

  private void zzlu()
  {
    while (true)
    {
      int k;
      Object localObject3;
      try
      {
        if (this.zzRO)
          break label198;
        int i = this.zzPy.getCount();
        this.zzRP = new ArrayList();
        if (i <= 0)
          break label193;
        this.zzRP.add(Integer.valueOf(0));
        String str = zzlt();
        int j = this.zzPy.zzaD(0);
        localObject2 = this.zzPy.zzd(str, 0, j);
        k = 1;
        if (k >= i)
          break label193;
        int m = this.zzPy.zzaD(k);
        localObject3 = this.zzPy.zzd(str, k, m);
        if (localObject3 == null)
          throw new NullPointerException("Missing value for markerColumn: " + str + ", at row: " + k + ", for window: " + m);
      }
      finally
      {
      }
      if (!((String)localObject3).equals(localObject2))
      {
        this.zzRP.add(Integer.valueOf(k));
        break label205;
        label193: this.zzRO = true;
      }
      else
      {
        label198: localObject3 = localObject2;
      }
      label205: k++;
      Object localObject2 = localObject3;
    }
  }

  public final T get(int paramInt)
  {
    zzlu();
    return zzh(zzaG(paramInt), zzaH(paramInt));
  }

  public int getCount()
  {
    zzlu();
    return this.zzRP.size();
  }

  int zzaG(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.zzRP.size()))
      throw new IllegalArgumentException("Position " + paramInt + " is out of bounds for this buffer");
    return ((Integer)this.zzRP.get(paramInt)).intValue();
  }

  protected int zzaH(int paramInt)
  {
    int i;
    if ((paramInt < 0) || (paramInt == this.zzRP.size()))
      i = 0;
    label137: 
    while (true)
    {
      return i;
      if (paramInt == -1 + this.zzRP.size());
      for (i = this.zzPy.getCount() - ((Integer)this.zzRP.get(paramInt)).intValue(); ; i = ((Integer)this.zzRP.get(paramInt + 1)).intValue() - ((Integer)this.zzRP.get(paramInt)).intValue())
      {
        if (i != 1)
          break label137;
        int j = zzaG(paramInt);
        int k = this.zzPy.zzaD(j);
        String str = zzlv();
        if ((str == null) || (this.zzPy.zzd(str, j, k) != null))
          break;
        return 0;
      }
    }
  }

  protected abstract T zzh(int paramInt1, int paramInt2);

  protected abstract String zzlt();

  protected String zzlv()
  {
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zzf
 * JD-Core Version:    0.6.2
 */