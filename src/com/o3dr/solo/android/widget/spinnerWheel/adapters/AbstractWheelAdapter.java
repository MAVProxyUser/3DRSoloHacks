package com.o3dr.solo.android.widget.spinnerWheel.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractWheelAdapter
  implements WheelViewAdapter
{
  private List<DataSetObserver> datasetObservers;

  public View getEmptyItem(View paramView, ViewGroup paramViewGroup)
  {
    return null;
  }

  protected void notifyDataChangedEvent()
  {
    if (this.datasetObservers != null)
    {
      Iterator localIterator = this.datasetObservers.iterator();
      while (localIterator.hasNext())
        ((DataSetObserver)localIterator.next()).onChanged();
    }
  }

  protected void notifyDataInvalidatedEvent()
  {
    if (this.datasetObservers != null)
    {
      Iterator localIterator = this.datasetObservers.iterator();
      while (localIterator.hasNext())
        ((DataSetObserver)localIterator.next()).onInvalidated();
    }
  }

  public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    if (this.datasetObservers == null)
      this.datasetObservers = new LinkedList();
    this.datasetObservers.add(paramDataSetObserver);
  }

  public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    if (this.datasetObservers != null)
      this.datasetObservers.remove(paramDataSetObserver);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.widget.spinnerWheel.adapters.AbstractWheelAdapter
 * JD-Core Version:    0.6.2
 */