package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class AnimatorSet extends Animator
{
  private ValueAnimator mDelayAnim = null;
  private long mDuration = -1L;
  private boolean mNeedsSort = true;
  private HashMap<Animator, Node> mNodeMap = new HashMap();
  private ArrayList<Node> mNodes = new ArrayList();
  private ArrayList<Animator> mPlayingSet = new ArrayList();
  private AnimatorSetListener mSetListener = null;
  private ArrayList<Node> mSortedNodes = new ArrayList();
  private long mStartDelay = 0L;
  private boolean mStarted = false;
  boolean mTerminated = false;

  private void sortNodes()
  {
    if (this.mNeedsSort)
    {
      this.mSortedNodes.clear();
      ArrayList localArrayList1 = new ArrayList();
      int n = this.mNodes.size();
      for (int i1 = 0; i1 < n; i1++)
      {
        Node localNode4 = (Node)this.mNodes.get(i1);
        if ((localNode4.dependencies == null) || (localNode4.dependencies.size() == 0))
          localArrayList1.add(localNode4);
      }
      ArrayList localArrayList2 = new ArrayList();
      while (localArrayList1.size() > 0)
      {
        int i2 = localArrayList1.size();
        for (int i3 = 0; i3 < i2; i3++)
        {
          Node localNode2 = (Node)localArrayList1.get(i3);
          this.mSortedNodes.add(localNode2);
          if (localNode2.nodeDependents != null)
          {
            int i4 = localNode2.nodeDependents.size();
            for (int i5 = 0; i5 < i4; i5++)
            {
              Node localNode3 = (Node)localNode2.nodeDependents.get(i5);
              localNode3.nodeDependencies.remove(localNode2);
              if (localNode3.nodeDependencies.size() == 0)
                localArrayList2.add(localNode3);
            }
          }
        }
        localArrayList1.clear();
        localArrayList1.addAll(localArrayList2);
        localArrayList2.clear();
      }
      this.mNeedsSort = false;
      if (this.mSortedNodes.size() != this.mNodes.size())
        throw new IllegalStateException("Circular dependencies cannot exist in AnimatorSet");
    }
    else
    {
      int i = this.mNodes.size();
      for (int j = 0; j < i; j++)
      {
        Node localNode1 = (Node)this.mNodes.get(j);
        if ((localNode1.dependencies != null) && (localNode1.dependencies.size() > 0))
        {
          int k = localNode1.dependencies.size();
          for (int m = 0; m < k; m++)
          {
            Dependency localDependency = (Dependency)localNode1.dependencies.get(m);
            if (localNode1.nodeDependencies == null)
              localNode1.nodeDependencies = new ArrayList();
            if (!localNode1.nodeDependencies.contains(localDependency.node))
              localNode1.nodeDependencies.add(localDependency.node);
          }
        }
        localNode1.done = false;
      }
    }
  }

  public void cancel()
  {
    this.mTerminated = true;
    if (isStarted())
    {
      ArrayList localArrayList1 = this.mListeners;
      ArrayList localArrayList2 = null;
      if (localArrayList1 != null)
      {
        localArrayList2 = (ArrayList)this.mListeners.clone();
        Iterator localIterator3 = localArrayList2.iterator();
        while (localIterator3.hasNext())
          ((Animator.AnimatorListener)localIterator3.next()).onAnimationCancel(this);
      }
      if ((this.mDelayAnim != null) && (this.mDelayAnim.isRunning()))
        this.mDelayAnim.cancel();
      while (localArrayList2 != null)
      {
        Iterator localIterator2 = localArrayList2.iterator();
        while (localIterator2.hasNext())
          ((Animator.AnimatorListener)localIterator2.next()).onAnimationEnd(this);
        if (this.mSortedNodes.size() > 0)
        {
          Iterator localIterator1 = this.mSortedNodes.iterator();
          while (localIterator1.hasNext())
            ((Node)localIterator1.next()).animation.cancel();
        }
      }
      this.mStarted = false;
    }
  }

  public AnimatorSet clone()
  {
    AnimatorSet localAnimatorSet = (AnimatorSet)super.clone();
    localAnimatorSet.mNeedsSort = true;
    localAnimatorSet.mTerminated = false;
    localAnimatorSet.mStarted = false;
    localAnimatorSet.mPlayingSet = new ArrayList();
    localAnimatorSet.mNodeMap = new HashMap();
    localAnimatorSet.mNodes = new ArrayList();
    localAnimatorSet.mSortedNodes = new ArrayList();
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = this.mNodes.iterator();
    while (localIterator1.hasNext())
    {
      Node localNode3 = (Node)localIterator1.next();
      Node localNode4 = localNode3.clone();
      localHashMap.put(localNode3, localNode4);
      localAnimatorSet.mNodes.add(localNode4);
      localAnimatorSet.mNodeMap.put(localNode4.animation, localNode4);
      localNode4.dependencies = null;
      localNode4.tmpDependencies = null;
      localNode4.nodeDependents = null;
      localNode4.nodeDependencies = null;
      ArrayList localArrayList1 = localNode4.animation.getListeners();
      if (localArrayList1 != null)
      {
        ArrayList localArrayList2 = null;
        Iterator localIterator4 = localArrayList1.iterator();
        while (localIterator4.hasNext())
        {
          Animator.AnimatorListener localAnimatorListener = (Animator.AnimatorListener)localIterator4.next();
          if ((localAnimatorListener instanceof AnimatorSetListener))
          {
            if (localArrayList2 == null)
              localArrayList2 = new ArrayList();
            localArrayList2.add(localAnimatorListener);
          }
        }
        if (localArrayList2 != null)
        {
          Iterator localIterator5 = localArrayList2.iterator();
          while (localIterator5.hasNext())
            localArrayList1.remove((Animator.AnimatorListener)localIterator5.next());
        }
      }
    }
    Iterator localIterator2 = this.mNodes.iterator();
    while (localIterator2.hasNext())
    {
      Node localNode1 = (Node)localIterator2.next();
      Node localNode2 = (Node)localHashMap.get(localNode1);
      if (localNode1.dependencies != null)
      {
        Iterator localIterator3 = localNode1.dependencies.iterator();
        while (localIterator3.hasNext())
        {
          Dependency localDependency = (Dependency)localIterator3.next();
          localNode2.addDependency(new Dependency((Node)localHashMap.get(localDependency.node), localDependency.rule));
        }
      }
    }
    return localAnimatorSet;
  }

  public void end()
  {
    this.mTerminated = true;
    if (isStarted())
    {
      if (this.mSortedNodes.size() != this.mNodes.size())
      {
        sortNodes();
        Iterator localIterator3 = this.mSortedNodes.iterator();
        while (localIterator3.hasNext())
        {
          Node localNode = (Node)localIterator3.next();
          if (this.mSetListener == null)
            this.mSetListener = new AnimatorSetListener(this);
          localNode.animation.addListener(this.mSetListener);
        }
      }
      if (this.mDelayAnim != null)
        this.mDelayAnim.cancel();
      if (this.mSortedNodes.size() > 0)
      {
        Iterator localIterator2 = this.mSortedNodes.iterator();
        while (localIterator2.hasNext())
          ((Node)localIterator2.next()).animation.end();
      }
      if (this.mListeners != null)
      {
        Iterator localIterator1 = ((ArrayList)this.mListeners.clone()).iterator();
        while (localIterator1.hasNext())
          ((Animator.AnimatorListener)localIterator1.next()).onAnimationEnd(this);
      }
      this.mStarted = false;
    }
  }

  public ArrayList<Animator> getChildAnimations()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
      localArrayList.add(((Node)localIterator.next()).animation);
    return localArrayList;
  }

  public long getDuration()
  {
    return this.mDuration;
  }

  public long getStartDelay()
  {
    return this.mStartDelay;
  }

  public boolean isRunning()
  {
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
      if (((Node)localIterator.next()).animation.isRunning())
        return true;
    return false;
  }

  public boolean isStarted()
  {
    return this.mStarted;
  }

  public Builder play(Animator paramAnimator)
  {
    if (paramAnimator != null)
    {
      this.mNeedsSort = true;
      return new Builder(paramAnimator);
    }
    return null;
  }

  public void playSequentially(List<Animator> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0))
    {
      this.mNeedsSort = true;
      if (paramList.size() != 1)
        break label44;
      play((Animator)paramList.get(0));
    }
    while (true)
    {
      return;
      label44: for (int i = 0; i < -1 + paramList.size(); i++)
        play((Animator)paramList.get(i)).before((Animator)paramList.get(i + 1));
    }
  }

  public void playSequentially(Animator[] paramArrayOfAnimator)
  {
    if (paramArrayOfAnimator != null)
    {
      this.mNeedsSort = true;
      if (paramArrayOfAnimator.length != 1)
        break label24;
      play(paramArrayOfAnimator[0]);
    }
    while (true)
    {
      return;
      label24: for (int i = 0; i < -1 + paramArrayOfAnimator.length; i++)
        play(paramArrayOfAnimator[i]).before(paramArrayOfAnimator[(i + 1)]);
    }
  }

  public void playTogether(Collection<Animator> paramCollection)
  {
    if ((paramCollection != null) && (paramCollection.size() > 0))
    {
      this.mNeedsSort = true;
      Builder localBuilder = null;
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        Animator localAnimator = (Animator)localIterator.next();
        if (localBuilder == null)
          localBuilder = play(localAnimator);
        else
          localBuilder.with(localAnimator);
      }
    }
  }

  public void playTogether(Animator[] paramArrayOfAnimator)
  {
    if (paramArrayOfAnimator != null)
    {
      this.mNeedsSort = true;
      Builder localBuilder = play(paramArrayOfAnimator[0]);
      for (int i = 1; i < paramArrayOfAnimator.length; i++)
        localBuilder.with(paramArrayOfAnimator[i]);
    }
  }

  public AnimatorSet setDuration(long paramLong)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("duration must be a value of zero or greater");
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
      ((Node)localIterator.next()).animation.setDuration(paramLong);
    this.mDuration = paramLong;
    return this;
  }

  public void setInterpolator(Interpolator paramInterpolator)
  {
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
      ((Node)localIterator.next()).animation.setInterpolator(paramInterpolator);
  }

  public void setStartDelay(long paramLong)
  {
    this.mStartDelay = paramLong;
  }

  public void setTarget(Object paramObject)
  {
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
    {
      Animator localAnimator = ((Node)localIterator.next()).animation;
      if ((localAnimator instanceof AnimatorSet))
        ((AnimatorSet)localAnimator).setTarget(paramObject);
      else if ((localAnimator instanceof ObjectAnimator))
        ((ObjectAnimator)localAnimator).setTarget(paramObject);
    }
  }

  public void setupEndValues()
  {
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
      ((Node)localIterator.next()).animation.setupEndValues();
  }

  public void setupStartValues()
  {
    Iterator localIterator = this.mNodes.iterator();
    while (localIterator.hasNext())
      ((Node)localIterator.next()).animation.setupStartValues();
  }

  public void start()
  {
    this.mTerminated = false;
    this.mStarted = true;
    sortNodes();
    int i = this.mSortedNodes.size();
    for (int j = 0; j < i; j++)
    {
      Node localNode3 = (Node)this.mSortedNodes.get(j);
      ArrayList localArrayList4 = localNode3.animation.getListeners();
      if ((localArrayList4 != null) && (localArrayList4.size() > 0))
      {
        Iterator localIterator2 = new ArrayList(localArrayList4).iterator();
        while (localIterator2.hasNext())
        {
          Animator.AnimatorListener localAnimatorListener = (Animator.AnimatorListener)localIterator2.next();
          if (((localAnimatorListener instanceof DependencyListener)) || ((localAnimatorListener instanceof AnimatorSetListener)))
            localNode3.animation.removeListener(localAnimatorListener);
        }
      }
    }
    final ArrayList localArrayList1 = new ArrayList();
    int k = 0;
    if (k < i)
    {
      Node localNode2 = (Node)this.mSortedNodes.get(k);
      if (this.mSetListener == null)
      {
        AnimatorSetListener localAnimatorSetListener = new AnimatorSetListener(this);
        this.mSetListener = localAnimatorSetListener;
      }
      if ((localNode2.dependencies == null) || (localNode2.dependencies.size() == 0))
        localArrayList1.add(localNode2);
      while (true)
      {
        localNode2.animation.addListener(this.mSetListener);
        k++;
        break;
        int i3 = localNode2.dependencies.size();
        for (int i4 = 0; i4 < i3; i4++)
        {
          Dependency localDependency = (Dependency)localNode2.dependencies.get(i4);
          Animator localAnimator = localDependency.node.animation;
          DependencyListener localDependencyListener = new DependencyListener(this, localNode2, localDependency.rule);
          localAnimator.addListener(localDependencyListener);
        }
        localNode2.tmpDependencies = ((ArrayList)localNode2.dependencies.clone());
      }
    }
    if (this.mStartDelay <= 0L)
    {
      Iterator localIterator1 = localArrayList1.iterator();
      while (localIterator1.hasNext())
      {
        Node localNode1 = (Node)localIterator1.next();
        localNode1.animation.start();
        this.mPlayingSet.add(localNode1.animation);
      }
    }
    this.mDelayAnim = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    this.mDelayAnim.setDuration(this.mStartDelay);
    ValueAnimator localValueAnimator = this.mDelayAnim;
    AnimatorListenerAdapter local1 = new AnimatorListenerAdapter()
    {
      boolean canceled = false;

      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        this.canceled = true;
      }

      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        if (!this.canceled)
        {
          int i = localArrayList1.size();
          for (int j = 0; j < i; j++)
          {
            AnimatorSet.Node localNode = (AnimatorSet.Node)localArrayList1.get(j);
            localNode.animation.start();
            AnimatorSet.this.mPlayingSet.add(localNode.animation);
          }
        }
      }
    };
    localValueAnimator.addListener(local1);
    this.mDelayAnim.start();
    if (this.mListeners != null)
    {
      ArrayList localArrayList3 = (ArrayList)this.mListeners.clone();
      int i1 = localArrayList3.size();
      for (int i2 = 0; i2 < i1; i2++)
        ((Animator.AnimatorListener)localArrayList3.get(i2)).onAnimationStart(this);
    }
    if ((this.mNodes.size() == 0) && (this.mStartDelay == 0L))
    {
      this.mStarted = false;
      if (this.mListeners != null)
      {
        ArrayList localArrayList2 = (ArrayList)this.mListeners.clone();
        int m = localArrayList2.size();
        for (int n = 0; n < m; n++)
          ((Animator.AnimatorListener)localArrayList2.get(n)).onAnimationEnd(this);
      }
    }
  }

  private class AnimatorSetListener
    implements Animator.AnimatorListener
  {
    private AnimatorSet mAnimatorSet;

    AnimatorSetListener(AnimatorSet arg2)
    {
      Object localObject;
      this.mAnimatorSet = localObject;
    }

    public void onAnimationCancel(Animator paramAnimator)
    {
      if ((!AnimatorSet.this.mTerminated) && (AnimatorSet.this.mPlayingSet.size() == 0) && (AnimatorSet.this.mListeners != null))
      {
        int i = AnimatorSet.this.mListeners.size();
        for (int j = 0; j < i; j++)
          ((Animator.AnimatorListener)AnimatorSet.this.mListeners.get(j)).onAnimationCancel(this.mAnimatorSet);
      }
    }

    public void onAnimationEnd(Animator paramAnimator)
    {
      paramAnimator.removeListener(this);
      AnimatorSet.this.mPlayingSet.remove(paramAnimator);
      ((AnimatorSet.Node)this.mAnimatorSet.mNodeMap.get(paramAnimator)).done = true;
      if (!AnimatorSet.this.mTerminated)
      {
        ArrayList localArrayList1 = this.mAnimatorSet.mSortedNodes;
        int i = 1;
        int j = localArrayList1.size();
        for (int k = 0; ; k++)
          if (k < j)
          {
            if (!((AnimatorSet.Node)localArrayList1.get(k)).done)
              i = 0;
          }
          else
          {
            if (i == 0)
              return;
            if (AnimatorSet.this.mListeners == null)
              break;
            ArrayList localArrayList2 = (ArrayList)AnimatorSet.this.mListeners.clone();
            int m = localArrayList2.size();
            for (int n = 0; n < m; n++)
              ((Animator.AnimatorListener)localArrayList2.get(n)).onAnimationEnd(this.mAnimatorSet);
          }
        AnimatorSet.access$302(this.mAnimatorSet, false);
      }
    }

    public void onAnimationRepeat(Animator paramAnimator)
    {
    }

    public void onAnimationStart(Animator paramAnimator)
    {
    }
  }

  public class Builder
  {
    private AnimatorSet.Node mCurrentNode;

    Builder(Animator arg2)
    {
      Object localObject;
      this.mCurrentNode = ((AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(localObject));
      if (this.mCurrentNode == null)
      {
        this.mCurrentNode = new AnimatorSet.Node(localObject);
        AnimatorSet.this.mNodeMap.put(localObject, this.mCurrentNode);
        AnimatorSet.this.mNodes.add(this.mCurrentNode);
      }
    }

    public Builder after(long paramLong)
    {
      ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
      localValueAnimator.setDuration(paramLong);
      after(localValueAnimator);
      return this;
    }

    public Builder after(Animator paramAnimator)
    {
      AnimatorSet.Node localNode = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(paramAnimator);
      if (localNode == null)
      {
        localNode = new AnimatorSet.Node(paramAnimator);
        AnimatorSet.this.mNodeMap.put(paramAnimator, localNode);
        AnimatorSet.this.mNodes.add(localNode);
      }
      AnimatorSet.Dependency localDependency = new AnimatorSet.Dependency(localNode, 1);
      this.mCurrentNode.addDependency(localDependency);
      return this;
    }

    public Builder before(Animator paramAnimator)
    {
      AnimatorSet.Node localNode = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(paramAnimator);
      if (localNode == null)
      {
        localNode = new AnimatorSet.Node(paramAnimator);
        AnimatorSet.this.mNodeMap.put(paramAnimator, localNode);
        AnimatorSet.this.mNodes.add(localNode);
      }
      localNode.addDependency(new AnimatorSet.Dependency(this.mCurrentNode, 1));
      return this;
    }

    public Builder with(Animator paramAnimator)
    {
      AnimatorSet.Node localNode = (AnimatorSet.Node)AnimatorSet.this.mNodeMap.get(paramAnimator);
      if (localNode == null)
      {
        localNode = new AnimatorSet.Node(paramAnimator);
        AnimatorSet.this.mNodeMap.put(paramAnimator, localNode);
        AnimatorSet.this.mNodes.add(localNode);
      }
      localNode.addDependency(new AnimatorSet.Dependency(this.mCurrentNode, 0));
      return this;
    }
  }

  private static class Dependency
  {
    static final int AFTER = 1;
    static final int WITH;
    public AnimatorSet.Node node;
    public int rule;

    public Dependency(AnimatorSet.Node paramNode, int paramInt)
    {
      this.node = paramNode;
      this.rule = paramInt;
    }
  }

  private static class DependencyListener
    implements Animator.AnimatorListener
  {
    private AnimatorSet mAnimatorSet;
    private AnimatorSet.Node mNode;
    private int mRule;

    public DependencyListener(AnimatorSet paramAnimatorSet, AnimatorSet.Node paramNode, int paramInt)
    {
      this.mAnimatorSet = paramAnimatorSet;
      this.mNode = paramNode;
      this.mRule = paramInt;
    }

    private void startIfReady(Animator paramAnimator)
    {
      if (this.mAnimatorSet.mTerminated)
        return;
      int i = this.mNode.tmpDependencies.size();
      for (int j = 0; ; j++)
      {
        Object localObject = null;
        if (j < i)
        {
          AnimatorSet.Dependency localDependency = (AnimatorSet.Dependency)this.mNode.tmpDependencies.get(j);
          if ((localDependency.rule == this.mRule) && (localDependency.node.animation == paramAnimator))
          {
            localObject = localDependency;
            paramAnimator.removeListener(this);
          }
        }
        else
        {
          this.mNode.tmpDependencies.remove(localObject);
          if (this.mNode.tmpDependencies.size() != 0)
            break;
          this.mNode.animation.start();
          this.mAnimatorSet.mPlayingSet.add(this.mNode.animation);
          return;
        }
      }
    }

    public void onAnimationCancel(Animator paramAnimator)
    {
    }

    public void onAnimationEnd(Animator paramAnimator)
    {
      if (this.mRule == 1)
        startIfReady(paramAnimator);
    }

    public void onAnimationRepeat(Animator paramAnimator)
    {
    }

    public void onAnimationStart(Animator paramAnimator)
    {
      if (this.mRule == 0)
        startIfReady(paramAnimator);
    }
  }

  private static class Node
    implements Cloneable
  {
    public Animator animation;
    public ArrayList<AnimatorSet.Dependency> dependencies = null;
    public boolean done = false;
    public ArrayList<Node> nodeDependencies = null;
    public ArrayList<Node> nodeDependents = null;
    public ArrayList<AnimatorSet.Dependency> tmpDependencies = null;

    public Node(Animator paramAnimator)
    {
      this.animation = paramAnimator;
    }

    public void addDependency(AnimatorSet.Dependency paramDependency)
    {
      if (this.dependencies == null)
      {
        this.dependencies = new ArrayList();
        this.nodeDependencies = new ArrayList();
      }
      this.dependencies.add(paramDependency);
      if (!this.nodeDependencies.contains(paramDependency.node))
        this.nodeDependencies.add(paramDependency.node);
      Node localNode = paramDependency.node;
      if (localNode.nodeDependents == null)
        localNode.nodeDependents = new ArrayList();
      localNode.nodeDependents.add(this);
    }

    public Node clone()
    {
      try
      {
        Node localNode = (Node)super.clone();
        localNode.animation = this.animation.clone();
        return localNode;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
      }
      throw new AssertionError();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.animation.AnimatorSet
 * JD-Core Version:    0.6.2
 */