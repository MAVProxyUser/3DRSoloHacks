package android.support.v7.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdapterHelper
  implements OpReorderer.Callback
{
  private static final boolean DEBUG = false;
  static final int POSITION_TYPE_INVISIBLE = 0;
  static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
  private static final String TAG = "AHT";
  final Callback mCallback;
  final boolean mDisableRecycler;
  Runnable mOnItemProcessedCallback;
  final OpReorderer mOpReorderer;
  final ArrayList<UpdateOp> mPendingUpdates = new ArrayList();
  final ArrayList<UpdateOp> mPostponedList = new ArrayList();
  private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool(30);

  AdapterHelper(Callback paramCallback)
  {
    this(paramCallback, false);
  }

  AdapterHelper(Callback paramCallback, boolean paramBoolean)
  {
    this.mCallback = paramCallback;
    this.mDisableRecycler = paramBoolean;
    this.mOpReorderer = new OpReorderer(this);
  }

  private void applyAdd(UpdateOp paramUpdateOp)
  {
    postponeAndUpdateViewHolders(paramUpdateOp);
  }

  private void applyMove(UpdateOp paramUpdateOp)
  {
    postponeAndUpdateViewHolders(paramUpdateOp);
  }

  private void applyRemove(UpdateOp paramUpdateOp)
  {
    int i = paramUpdateOp.positionStart;
    int j = 0;
    int k = paramUpdateOp.positionStart + paramUpdateOp.itemCount;
    int m = -1;
    int n = paramUpdateOp.positionStart;
    if (n < k)
    {
      int i1;
      if ((this.mCallback.findViewHolder(n) != null) || (canFindInPreLayout(n)))
      {
        i1 = 0;
        if (m == 0)
        {
          dispatchAndUpdateViewHolders(obtainUpdateOp(1, i, j));
          i1 = 1;
        }
        m = 1;
        label82: if (i1 == 0)
          break label136;
        n -= j;
        k -= j;
      }
      label136: for (j = 1; ; j++)
      {
        n++;
        break;
        i1 = 0;
        if (m == 1)
        {
          postponeAndUpdateViewHolders(obtainUpdateOp(1, i, j));
          i1 = 1;
        }
        m = 0;
        break label82;
      }
    }
    if (j != paramUpdateOp.itemCount)
    {
      recycleUpdateOp(paramUpdateOp);
      paramUpdateOp = obtainUpdateOp(1, i, j);
    }
    if (m == 0)
    {
      dispatchAndUpdateViewHolders(paramUpdateOp);
      return;
    }
    postponeAndUpdateViewHolders(paramUpdateOp);
  }

  private void applyUpdate(UpdateOp paramUpdateOp)
  {
    int i = paramUpdateOp.positionStart;
    int j = 0;
    int k = paramUpdateOp.positionStart + paramUpdateOp.itemCount;
    int m = -1;
    int n = paramUpdateOp.positionStart;
    if (n < k)
    {
      if ((this.mCallback.findViewHolder(n) != null) || (canFindInPreLayout(n)))
        if (m == 0)
        {
          dispatchAndUpdateViewHolders(obtainUpdateOp(2, i, j));
          j = 0;
          i = n;
        }
      for (m = 1; ; m = 0)
      {
        j++;
        n++;
        break;
        if (m == 1)
        {
          postponeAndUpdateViewHolders(obtainUpdateOp(2, i, j));
          j = 0;
          i = n;
        }
      }
    }
    if (j != paramUpdateOp.itemCount)
    {
      recycleUpdateOp(paramUpdateOp);
      paramUpdateOp = obtainUpdateOp(2, i, j);
    }
    if (m == 0)
    {
      dispatchAndUpdateViewHolders(paramUpdateOp);
      return;
    }
    postponeAndUpdateViewHolders(paramUpdateOp);
  }

  private boolean canFindInPreLayout(int paramInt)
  {
    int i = this.mPostponedList.size();
    label109: for (int j = 0; j < i; j++)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp.cmd == 3)
      {
        if (findPositionOffset(localUpdateOp.itemCount, j + 1) == paramInt)
          return true;
      }
      else if (localUpdateOp.cmd == 0)
      {
        int k = localUpdateOp.positionStart + localUpdateOp.itemCount;
        for (int m = localUpdateOp.positionStart; ; m++)
        {
          if (m >= k)
            break label109;
          if (findPositionOffset(m, j + 1) == paramInt)
            break;
        }
      }
    }
    return false;
  }

  private void dispatchAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    if ((paramUpdateOp.cmd == 0) || (paramUpdateOp.cmd == 3))
      throw new IllegalArgumentException("should not dispatch add or move for pre layout");
    int i = updatePositionWithPostponed(paramUpdateOp.positionStart, paramUpdateOp.cmd);
    int j = 1;
    int k = paramUpdateOp.positionStart;
    int m;
    int n;
    label105: int i1;
    int i3;
    switch (paramUpdateOp.cmd)
    {
    default:
      throw new IllegalArgumentException("op should be remove or update." + paramUpdateOp);
    case 2:
      m = 1;
      n = 1;
      if (n >= paramUpdateOp.itemCount)
        break label274;
      i1 = updatePositionWithPostponed(paramUpdateOp.positionStart + m * n, paramUpdateOp.cmd);
      int i2 = paramUpdateOp.cmd;
      i3 = 0;
      switch (i2)
      {
      default:
        if (i3 != 0)
          j++;
        break;
      case 2:
      case 1:
      }
      break;
    case 1:
    }
    while (true)
    {
      n++;
      break label105;
      m = 0;
      break;
      if (i1 == i + 1);
      for (i3 = 1; ; i3 = 0)
        break;
      if (i1 == i);
      for (i3 = 1; ; i3 = 0)
        break;
      UpdateOp localUpdateOp2 = obtainUpdateOp(paramUpdateOp.cmd, i, j);
      dispatchFirstPassAndUpdateViewHolders(localUpdateOp2, k);
      recycleUpdateOp(localUpdateOp2);
      if (paramUpdateOp.cmd == 2)
        k += j;
      i = i1;
      j = 1;
    }
    label274: recycleUpdateOp(paramUpdateOp);
    if (j > 0)
    {
      UpdateOp localUpdateOp1 = obtainUpdateOp(paramUpdateOp.cmd, i, j);
      dispatchFirstPassAndUpdateViewHolders(localUpdateOp1, k);
      recycleUpdateOp(localUpdateOp1);
    }
  }

  private void postponeAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    this.mPostponedList.add(paramUpdateOp);
    switch (paramUpdateOp.cmd)
    {
    default:
      throw new IllegalArgumentException("Unknown update op type for " + paramUpdateOp);
    case 0:
      this.mCallback.offsetPositionsForAdd(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      return;
    case 3:
      this.mCallback.offsetPositionsForMove(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      return;
    case 1:
      this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
      return;
    case 2:
    }
    this.mCallback.markViewHoldersUpdated(paramUpdateOp.positionStart, paramUpdateOp.itemCount);
  }

  private int updatePositionWithPostponed(int paramInt1, int paramInt2)
  {
    int i = -1 + this.mPostponedList.size();
    if (i >= 0)
    {
      UpdateOp localUpdateOp2 = (UpdateOp)this.mPostponedList.get(i);
      int k;
      int m;
      if (localUpdateOp2.cmd == 3)
        if (localUpdateOp2.positionStart < localUpdateOp2.itemCount)
        {
          k = localUpdateOp2.positionStart;
          m = localUpdateOp2.itemCount;
          label63: if ((paramInt1 < k) || (paramInt1 > m))
            break label189;
          if (k != localUpdateOp2.positionStart)
            break label147;
          if (paramInt2 != 0)
            break label127;
          localUpdateOp2.itemCount = (1 + localUpdateOp2.itemCount);
          label101: paramInt1++;
        }
      while (true)
      {
        i--;
        break;
        k = localUpdateOp2.itemCount;
        m = localUpdateOp2.positionStart;
        break label63;
        label127: if (paramInt2 != 1)
          break label101;
        localUpdateOp2.itemCount = (-1 + localUpdateOp2.itemCount);
        break label101;
        label147: if (paramInt2 == 0)
          localUpdateOp2.positionStart = (1 + localUpdateOp2.positionStart);
        while (true)
        {
          paramInt1--;
          break;
          if (paramInt2 == 1)
            localUpdateOp2.positionStart = (-1 + localUpdateOp2.positionStart);
        }
        label189: if (paramInt1 < localUpdateOp2.positionStart)
          if (paramInt2 == 0)
          {
            localUpdateOp2.positionStart = (1 + localUpdateOp2.positionStart);
            localUpdateOp2.itemCount = (1 + localUpdateOp2.itemCount);
          }
          else if (paramInt2 == 1)
          {
            localUpdateOp2.positionStart = (-1 + localUpdateOp2.positionStart);
            localUpdateOp2.itemCount = (-1 + localUpdateOp2.itemCount);
            continue;
            if (localUpdateOp2.positionStart <= paramInt1)
            {
              if (localUpdateOp2.cmd == 0)
                paramInt1 -= localUpdateOp2.itemCount;
              else if (localUpdateOp2.cmd == 1)
                paramInt1 += localUpdateOp2.itemCount;
            }
            else if (paramInt2 == 0)
              localUpdateOp2.positionStart = (1 + localUpdateOp2.positionStart);
            else if (paramInt2 == 1)
              localUpdateOp2.positionStart = (-1 + localUpdateOp2.positionStart);
          }
      }
    }
    int j = -1 + this.mPostponedList.size();
    if (j >= 0)
    {
      UpdateOp localUpdateOp1 = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp1.cmd == 3)
        if ((localUpdateOp1.itemCount == localUpdateOp1.positionStart) || (localUpdateOp1.itemCount < 0))
        {
          this.mPostponedList.remove(j);
          recycleUpdateOp(localUpdateOp1);
        }
      while (true)
      {
        j--;
        break;
        if (localUpdateOp1.itemCount <= 0)
        {
          this.mPostponedList.remove(j);
          recycleUpdateOp(localUpdateOp1);
        }
      }
    }
    return paramInt1;
  }

  AdapterHelper addUpdateOp(UpdateOp[] paramArrayOfUpdateOp)
  {
    Collections.addAll(this.mPendingUpdates, paramArrayOfUpdateOp);
    return this;
  }

  public int applyPendingUpdatesToPosition(int paramInt)
  {
    int i = this.mPendingUpdates.size();
    int j = 0;
    UpdateOp localUpdateOp;
    if (j < i)
    {
      localUpdateOp = (UpdateOp)this.mPendingUpdates.get(j);
      switch (localUpdateOp.cmd)
      {
      case 2:
      default:
      case 0:
      case 1:
      case 3:
      }
    }
    while (true)
    {
      j++;
      break;
      if (localUpdateOp.positionStart <= paramInt)
      {
        paramInt += localUpdateOp.itemCount;
        continue;
        if (localUpdateOp.positionStart <= paramInt)
        {
          if (localUpdateOp.positionStart + localUpdateOp.itemCount > paramInt)
          {
            paramInt = -1;
            return paramInt;
          }
          paramInt -= localUpdateOp.itemCount;
          continue;
          if (localUpdateOp.positionStart == paramInt)
          {
            paramInt = localUpdateOp.itemCount;
          }
          else
          {
            if (localUpdateOp.positionStart < paramInt)
              paramInt--;
            if (localUpdateOp.itemCount <= paramInt)
              paramInt++;
          }
        }
      }
    }
  }

  void consumePostponedUpdates()
  {
    int i = this.mPostponedList.size();
    for (int j = 0; j < i; j++)
      this.mCallback.onDispatchSecondPass((UpdateOp)this.mPostponedList.get(j));
    recycleUpdateOpsAndClearList(this.mPostponedList);
  }

  void consumeUpdatesInOnePass()
  {
    consumePostponedUpdates();
    int i = this.mPendingUpdates.size();
    int j = 0;
    if (j < i)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPendingUpdates.get(j);
      switch (localUpdateOp.cmd)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        if (this.mOnItemProcessedCallback != null)
          this.mOnItemProcessedCallback.run();
        j++;
        break;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.offsetPositionsForAdd(localUpdateOp.positionStart, localUpdateOp.itemCount);
        continue;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.offsetPositionsForRemovingInvisible(localUpdateOp.positionStart, localUpdateOp.itemCount);
        continue;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.markViewHoldersUpdated(localUpdateOp.positionStart, localUpdateOp.itemCount);
        continue;
        this.mCallback.onDispatchSecondPass(localUpdateOp);
        this.mCallback.offsetPositionsForMove(localUpdateOp.positionStart, localUpdateOp.itemCount);
      }
    }
    recycleUpdateOpsAndClearList(this.mPendingUpdates);
  }

  void dispatchFirstPassAndUpdateViewHolders(UpdateOp paramUpdateOp, int paramInt)
  {
    this.mCallback.onDispatchFirstPass(paramUpdateOp);
    switch (paramUpdateOp.cmd)
    {
    default:
      throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
    case 1:
      this.mCallback.offsetPositionsForRemovingInvisible(paramInt, paramUpdateOp.itemCount);
      return;
    case 2:
    }
    this.mCallback.markViewHoldersUpdated(paramInt, paramUpdateOp.itemCount);
  }

  int findPositionOffset(int paramInt)
  {
    return findPositionOffset(paramInt, 0);
  }

  int findPositionOffset(int paramInt1, int paramInt2)
  {
    int i = this.mPostponedList.size();
    int j = paramInt2;
    UpdateOp localUpdateOp;
    if (j < i)
    {
      localUpdateOp = (UpdateOp)this.mPostponedList.get(j);
      if (localUpdateOp.cmd == 3)
        if (localUpdateOp.positionStart == paramInt1)
          paramInt1 = localUpdateOp.itemCount;
    }
    while (true)
    {
      j++;
      break;
      if (localUpdateOp.positionStart < paramInt1)
        paramInt1--;
      if (localUpdateOp.itemCount <= paramInt1)
      {
        paramInt1++;
        continue;
        if (localUpdateOp.positionStart <= paramInt1)
          if (localUpdateOp.cmd == 1)
          {
            if (paramInt1 < localUpdateOp.positionStart + localUpdateOp.itemCount)
            {
              paramInt1 = -1;
              return paramInt1;
            }
            paramInt1 -= localUpdateOp.itemCount;
          }
          else if (localUpdateOp.cmd == 0)
          {
            paramInt1 += localUpdateOp.itemCount;
          }
      }
    }
  }

  boolean hasPendingUpdates()
  {
    return this.mPendingUpdates.size() > 0;
  }

  public UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3)
  {
    UpdateOp localUpdateOp = (UpdateOp)this.mUpdateOpPool.acquire();
    if (localUpdateOp == null)
      return new UpdateOp(paramInt1, paramInt2, paramInt3);
    localUpdateOp.cmd = paramInt1;
    localUpdateOp.positionStart = paramInt2;
    localUpdateOp.itemCount = paramInt3;
    return localUpdateOp;
  }

  boolean onItemRangeChanged(int paramInt1, int paramInt2)
  {
    this.mPendingUpdates.add(obtainUpdateOp(2, paramInt1, paramInt2));
    return this.mPendingUpdates.size() == 1;
  }

  boolean onItemRangeInserted(int paramInt1, int paramInt2)
  {
    this.mPendingUpdates.add(obtainUpdateOp(0, paramInt1, paramInt2));
    return this.mPendingUpdates.size() == 1;
  }

  boolean onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 1;
    if (paramInt1 == paramInt2)
      return false;
    if (paramInt3 != i)
      throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    this.mPendingUpdates.add(obtainUpdateOp(3, paramInt1, paramInt2));
    if (this.mPendingUpdates.size() == i);
    while (true)
    {
      return i;
      i = 0;
    }
  }

  boolean onItemRangeRemoved(int paramInt1, int paramInt2)
  {
    this.mPendingUpdates.add(obtainUpdateOp(1, paramInt1, paramInt2));
    return this.mPendingUpdates.size() == 1;
  }

  void preProcess()
  {
    this.mOpReorderer.reorderOps(this.mPendingUpdates);
    int i = this.mPendingUpdates.size();
    int j = 0;
    if (j < i)
    {
      UpdateOp localUpdateOp = (UpdateOp)this.mPendingUpdates.get(j);
      switch (localUpdateOp.cmd)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        if (this.mOnItemProcessedCallback != null)
          this.mOnItemProcessedCallback.run();
        j++;
        break;
        applyAdd(localUpdateOp);
        continue;
        applyRemove(localUpdateOp);
        continue;
        applyUpdate(localUpdateOp);
        continue;
        applyMove(localUpdateOp);
      }
    }
    this.mPendingUpdates.clear();
  }

  public void recycleUpdateOp(UpdateOp paramUpdateOp)
  {
    if (!this.mDisableRecycler)
      this.mUpdateOpPool.release(paramUpdateOp);
  }

  void recycleUpdateOpsAndClearList(List<UpdateOp> paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++)
      recycleUpdateOp((UpdateOp)paramList.get(j));
    paramList.clear();
  }

  void reset()
  {
    recycleUpdateOpsAndClearList(this.mPendingUpdates);
    recycleUpdateOpsAndClearList(this.mPostponedList);
  }

  static abstract interface Callback
  {
    public abstract RecyclerView.ViewHolder findViewHolder(int paramInt);

    public abstract void markViewHoldersUpdated(int paramInt1, int paramInt2);

    public abstract void offsetPositionsForAdd(int paramInt1, int paramInt2);

    public abstract void offsetPositionsForMove(int paramInt1, int paramInt2);

    public abstract void offsetPositionsForRemovingInvisible(int paramInt1, int paramInt2);

    public abstract void offsetPositionsForRemovingLaidOutOrNewView(int paramInt1, int paramInt2);

    public abstract void onDispatchFirstPass(AdapterHelper.UpdateOp paramUpdateOp);

    public abstract void onDispatchSecondPass(AdapterHelper.UpdateOp paramUpdateOp);
  }

  static class UpdateOp
  {
    static final int ADD = 0;
    static final int MOVE = 3;
    static final int POOL_SIZE = 30;
    static final int REMOVE = 1;
    static final int UPDATE = 2;
    int cmd;
    int itemCount;
    int positionStart;

    UpdateOp(int paramInt1, int paramInt2, int paramInt3)
    {
      this.cmd = paramInt1;
      this.positionStart = paramInt2;
      this.itemCount = paramInt3;
    }

    String cmdToString()
    {
      switch (this.cmd)
      {
      default:
        return "??";
      case 0:
        return "add";
      case 1:
        return "rm";
      case 2:
        return "up";
      case 3:
      }
      return "mv";
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      UpdateOp localUpdateOp;
      do
      {
        do
        {
          return true;
          if ((paramObject == null) || (getClass() != paramObject.getClass()))
            return false;
          localUpdateOp = (UpdateOp)paramObject;
          if (this.cmd != localUpdateOp.cmd)
            return false;
        }
        while ((this.cmd == 3) && (Math.abs(this.itemCount - this.positionStart) == 1) && (this.itemCount == localUpdateOp.positionStart) && (this.positionStart == localUpdateOp.itemCount));
        if (this.itemCount != localUpdateOp.itemCount)
          return false;
      }
      while (this.positionStart == localUpdateOp.positionStart);
      return false;
    }

    public int hashCode()
    {
      return 31 * (31 * this.cmd + this.positionStart) + this.itemCount;
    }

    public String toString()
    {
      return "[" + cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + "]";
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AdapterHelper
 * JD-Core Version:    0.6.2
 */