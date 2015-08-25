package android.support.v4.app;

import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction
  implements FragmentManager.BackStackEntry, Runnable
{
  static final int OP_ADD = 1;
  static final int OP_ATTACH = 7;
  static final int OP_DETACH = 6;
  static final int OP_HIDE = 4;
  static final int OP_NULL = 0;
  static final int OP_REMOVE = 3;
  static final int OP_REPLACE = 2;
  static final int OP_SHOW = 5;
  static final boolean SUPPORTS_TRANSITIONS = false;
  static final String TAG = "FragmentManager";
  boolean mAddToBackStack;
  boolean mAllowAddToBackStack = true;
  int mBreadCrumbShortTitleRes;
  CharSequence mBreadCrumbShortTitleText;
  int mBreadCrumbTitleRes;
  CharSequence mBreadCrumbTitleText;
  boolean mCommitted;
  int mEnterAnim;
  int mExitAnim;
  Op mHead;
  int mIndex = -1;
  final FragmentManagerImpl mManager;
  String mName;
  int mNumOp;
  int mPopEnterAnim;
  int mPopExitAnim;
  ArrayList<String> mSharedElementSourceNames;
  ArrayList<String> mSharedElementTargetNames;
  Op mTail;
  int mTransition;
  int mTransitionStyle;

  static
  {
    if (Build.VERSION.SDK_INT >= 21);
    for (boolean bool = true; ; bool = false)
    {
      SUPPORTS_TRANSITIONS = bool;
      return;
    }
  }

  public BackStackRecord(FragmentManagerImpl paramFragmentManagerImpl)
  {
    this.mManager = paramFragmentManagerImpl;
  }

  private TransitionState beginTransition(SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2, boolean paramBoolean)
  {
    TransitionState localTransitionState = new TransitionState();
    localTransitionState.nonExistentView = new View(this.mManager.mActivity);
    int i = 0;
    for (int j = 0; j < paramSparseArray1.size(); j++)
      if (configureTransitions(paramSparseArray1.keyAt(j), localTransitionState, paramBoolean, paramSparseArray1, paramSparseArray2))
        i = 1;
    for (int k = 0; k < paramSparseArray2.size(); k++)
    {
      int m = paramSparseArray2.keyAt(k);
      if ((paramSparseArray1.get(m) == null) && (configureTransitions(m, localTransitionState, paramBoolean, paramSparseArray1, paramSparseArray2)))
        i = 1;
    }
    if (i == 0)
      localTransitionState = null;
    return localTransitionState;
  }

  private void calculateFragments(SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    if (!this.mManager.mContainer.hasView());
    Op localOp;
    do
    {
      return;
      localOp = this.mHead;
    }
    while (localOp == null);
    switch (localOp.cmd)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    }
    while (true)
    {
      localOp = localOp.next;
      break;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      Fragment localFragment1 = localOp.fragment;
      if (this.mManager.mAdded != null)
      {
        int i = 0;
        if (i < this.mManager.mAdded.size())
        {
          Fragment localFragment2 = (Fragment)this.mManager.mAdded.get(i);
          if ((localFragment1 == null) || (localFragment2.mContainerId == localFragment1.mContainerId))
          {
            if (localFragment2 != localFragment1)
              break label177;
            localFragment1 = null;
          }
          while (true)
          {
            i++;
            break;
            label177: setFirstOut(paramSparseArray1, localFragment2);
          }
        }
      }
      setLastIn(paramSparseArray2, localFragment1);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
    }
  }

  private void callSharedElementEnd(TransitionState paramTransitionState, Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean, ArrayMap<String, View> paramArrayMap)
  {
    if (paramBoolean);
    for (SharedElementCallback localSharedElementCallback = paramFragment2.mEnterTransitionCallback; ; localSharedElementCallback = paramFragment1.mEnterTransitionCallback)
    {
      if (localSharedElementCallback != null)
        localSharedElementCallback.onSharedElementEnd(new ArrayList(paramArrayMap.keySet()), new ArrayList(paramArrayMap.values()), null);
      return;
    }
  }

  private static Object captureExitingViews(Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, View paramView)
  {
    if (paramObject != null)
      paramObject = FragmentTransitionCompat21.captureExitingViews(paramObject, paramFragment.getView(), paramArrayList, paramArrayMap, paramView);
    return paramObject;
  }

  private boolean configureTransitions(int paramInt, TransitionState paramTransitionState, boolean paramBoolean, SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    ViewGroup localViewGroup = (ViewGroup)this.mManager.mContainer.findViewById(paramInt);
    if (localViewGroup == null)
      return false;
    final Fragment localFragment1 = (Fragment)paramSparseArray2.get(paramInt);
    Fragment localFragment2 = (Fragment)paramSparseArray1.get(paramInt);
    Object localObject1 = getEnterTransition(localFragment1, paramBoolean);
    Object localObject2 = getSharedElementTransition(localFragment1, localFragment2, paramBoolean);
    Object localObject3 = getExitTransition(localFragment2, paramBoolean);
    if ((localObject1 == null) && (localObject2 == null) && (localObject3 == null))
      return false;
    ArrayList localArrayList1 = new ArrayList();
    ArrayMap localArrayMap1 = null;
    SharedElementCallback localSharedElementCallback;
    if (localObject2 != null)
    {
      localArrayMap1 = remapSharedElements(paramTransitionState, localFragment2, paramBoolean);
      localArrayList1.add(paramTransitionState.nonExistentView);
      localArrayList1.addAll(localArrayMap1.values());
      if (!paramBoolean)
        break label465;
      localSharedElementCallback = localFragment2.mEnterTransitionCallback;
      if (localSharedElementCallback != null)
      {
        ArrayList localArrayList4 = new ArrayList(localArrayMap1.keySet());
        ArrayList localArrayList5 = new ArrayList(localArrayMap1.values());
        localSharedElementCallback.onSharedElementStart(localArrayList4, localArrayList5, null);
      }
    }
    ArrayList localArrayList2 = new ArrayList();
    View localView1 = paramTransitionState.nonExistentView;
    Object localObject4 = captureExitingViews(localObject3, localFragment2, localArrayList2, localArrayMap1, localView1);
    if ((this.mSharedElementTargetNames != null) && (localArrayMap1 != null))
    {
      Object localObject6 = this.mSharedElementTargetNames.get(0);
      View localView2 = (View)localArrayMap1.get(localObject6);
      if (localView2 != null)
      {
        if (localObject4 != null)
          FragmentTransitionCompat21.setEpicenter(localObject4, localView2);
        if (localObject2 != null)
          FragmentTransitionCompat21.setEpicenter(localObject2, localView2);
      }
    }
    FragmentTransitionCompat21.ViewRetriever local1 = new FragmentTransitionCompat21.ViewRetriever()
    {
      public View getView()
      {
        return localFragment1.getView();
      }
    };
    if (localObject2 != null)
      prepareSharedElementTransition(paramTransitionState, localViewGroup, localObject2, localFragment1, localFragment2, paramBoolean, localArrayList1);
    ArrayList localArrayList3 = new ArrayList();
    ArrayMap localArrayMap2 = new ArrayMap();
    if (paramBoolean);
    for (boolean bool = localFragment1.getAllowReturnTransitionOverlap(); ; bool = localFragment1.getAllowEnterTransitionOverlap())
    {
      Object localObject5 = FragmentTransitionCompat21.mergeTransitions(localObject1, localObject4, localObject2, bool);
      if (localObject5 != null)
      {
        FragmentTransitionCompat21.addTransitionTargets(localObject1, localObject2, localViewGroup, local1, paramTransitionState.nonExistentView, paramTransitionState.enteringEpicenterView, paramTransitionState.nameOverrides, localArrayList3, localArrayMap2, localArrayList1);
        excludeHiddenFragmentsAfterEnter(localViewGroup, paramTransitionState, paramInt, localObject5);
        FragmentTransitionCompat21.excludeTarget(localObject5, paramTransitionState.nonExistentView, true);
        excludeHiddenFragments(paramTransitionState, paramInt, localObject5);
        FragmentTransitionCompat21.beginDelayedTransition(localViewGroup, localObject5);
        FragmentTransitionCompat21.cleanupTransitions(localViewGroup, paramTransitionState.nonExistentView, localObject1, localArrayList3, localObject4, localArrayList2, localObject2, localArrayList1, localObject5, paramTransitionState.hiddenFragmentViews, localArrayMap2);
      }
      if (localObject5 == null)
        break label485;
      return true;
      label465: localSharedElementCallback = localFragment1.mEnterTransitionCallback;
      break;
    }
    label485: return false;
  }

  private void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    paramFragment.mFragmentManager = this.mManager;
    if (paramString != null)
    {
      if ((paramFragment.mTag != null) && (!paramString.equals(paramFragment.mTag)))
        throw new IllegalStateException("Can't change tag of fragment " + paramFragment + ": was " + paramFragment.mTag + " now " + paramString);
      paramFragment.mTag = paramString;
    }
    if (paramInt1 != 0)
    {
      if ((paramFragment.mFragmentId != 0) && (paramFragment.mFragmentId != paramInt1))
        throw new IllegalStateException("Can't change container ID of fragment " + paramFragment + ": was " + paramFragment.mFragmentId + " now " + paramInt1);
      paramFragment.mFragmentId = paramInt1;
      paramFragment.mContainerId = paramInt1;
    }
    Op localOp = new Op();
    localOp.cmd = paramInt2;
    localOp.fragment = paramFragment;
    addOp(localOp);
  }

  private void excludeHiddenFragments(TransitionState paramTransitionState, int paramInt, Object paramObject)
  {
    if (this.mManager.mAdded != null)
    {
      int i = 0;
      if (i < this.mManager.mAdded.size())
      {
        Fragment localFragment = (Fragment)this.mManager.mAdded.get(i);
        if ((localFragment.mView != null) && (localFragment.mContainer != null) && (localFragment.mContainerId == paramInt))
        {
          if (!localFragment.mHidden)
            break label122;
          if (!paramTransitionState.hiddenFragmentViews.contains(localFragment.mView))
          {
            FragmentTransitionCompat21.excludeTarget(paramObject, localFragment.mView, true);
            paramTransitionState.hiddenFragmentViews.add(localFragment.mView);
          }
        }
        while (true)
        {
          i++;
          break;
          label122: FragmentTransitionCompat21.excludeTarget(paramObject, localFragment.mView, false);
          paramTransitionState.hiddenFragmentViews.remove(localFragment.mView);
        }
      }
    }
  }

  private void excludeHiddenFragmentsAfterEnter(final View paramView, final TransitionState paramTransitionState, final int paramInt, final Object paramObject)
  {
    paramView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public boolean onPreDraw()
      {
        paramView.getViewTreeObserver().removeOnPreDrawListener(this);
        BackStackRecord.this.excludeHiddenFragments(paramTransitionState, paramInt, paramObject);
        return true;
      }
    });
  }

  private static Object getEnterTransition(Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null)
      return null;
    if (paramBoolean);
    for (Object localObject = paramFragment.getReenterTransition(); ; localObject = paramFragment.getEnterTransition())
      return FragmentTransitionCompat21.cloneTransition(localObject);
  }

  private static Object getExitTransition(Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null)
      return null;
    if (paramBoolean);
    for (Object localObject = paramFragment.getReturnTransition(); ; localObject = paramFragment.getExitTransition())
      return FragmentTransitionCompat21.cloneTransition(localObject);
  }

  private static Object getSharedElementTransition(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean)
  {
    if ((paramFragment1 == null) || (paramFragment2 == null))
      return null;
    if (paramBoolean);
    for (Object localObject = paramFragment2.getSharedElementReturnTransition(); ; localObject = paramFragment1.getSharedElementEnterTransition())
      return FragmentTransitionCompat21.cloneTransition(localObject);
  }

  private ArrayMap<String, View> mapEnteringSharedElements(TransitionState paramTransitionState, Fragment paramFragment, boolean paramBoolean)
  {
    ArrayMap localArrayMap = new ArrayMap();
    View localView = paramFragment.getView();
    if ((localView != null) && (this.mSharedElementSourceNames != null))
    {
      FragmentTransitionCompat21.findNamedViews(localArrayMap, localView);
      if (paramBoolean)
        localArrayMap = remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, localArrayMap);
    }
    else
    {
      return localArrayMap;
    }
    localArrayMap.retainAll(this.mSharedElementTargetNames);
    return localArrayMap;
  }

  private ArrayMap<String, View> mapSharedElementsIn(TransitionState paramTransitionState, boolean paramBoolean, Fragment paramFragment)
  {
    ArrayMap localArrayMap = mapEnteringSharedElements(paramTransitionState, paramFragment, paramBoolean);
    if (paramBoolean)
    {
      if (paramFragment.mExitTransitionCallback != null)
        paramFragment.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, localArrayMap);
      setBackNameOverrides(paramTransitionState, localArrayMap, true);
      return localArrayMap;
    }
    if (paramFragment.mEnterTransitionCallback != null)
      paramFragment.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, localArrayMap);
    setNameOverrides(paramTransitionState, localArrayMap, true);
    return localArrayMap;
  }

  private void prepareSharedElementTransition(final TransitionState paramTransitionState, final View paramView, final Object paramObject, final Fragment paramFragment1, final Fragment paramFragment2, final boolean paramBoolean, final ArrayList<View> paramArrayList)
  {
    paramView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public boolean onPreDraw()
      {
        paramView.getViewTreeObserver().removeOnPreDrawListener(this);
        if (paramObject != null)
        {
          FragmentTransitionCompat21.removeTargets(paramObject, paramArrayList);
          paramArrayList.clear();
          ArrayMap localArrayMap = BackStackRecord.this.mapSharedElementsIn(paramTransitionState, paramBoolean, paramFragment1);
          paramArrayList.add(paramTransitionState.nonExistentView);
          paramArrayList.addAll(localArrayMap.values());
          FragmentTransitionCompat21.addTargets(paramObject, paramArrayList);
          BackStackRecord.this.setEpicenterIn(localArrayMap, paramTransitionState);
          BackStackRecord.this.callSharedElementEnd(paramTransitionState, paramFragment1, paramFragment2, paramBoolean, localArrayMap);
        }
        return true;
      }
    });
  }

  private static ArrayMap<String, View> remapNames(ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, ArrayMap<String, View> paramArrayMap)
  {
    if (paramArrayMap.isEmpty())
      return paramArrayMap;
    ArrayMap localArrayMap = new ArrayMap();
    int i = paramArrayList1.size();
    for (int j = 0; j < i; j++)
    {
      View localView = (View)paramArrayMap.get(paramArrayList1.get(j));
      if (localView != null)
        localArrayMap.put(paramArrayList2.get(j), localView);
    }
    return localArrayMap;
  }

  private ArrayMap<String, View> remapSharedElements(TransitionState paramTransitionState, Fragment paramFragment, boolean paramBoolean)
  {
    ArrayMap localArrayMap = new ArrayMap();
    if (this.mSharedElementSourceNames != null)
    {
      FragmentTransitionCompat21.findNamedViews(localArrayMap, paramFragment.getView());
      if (!paramBoolean)
        break label74;
      localArrayMap.retainAll(this.mSharedElementTargetNames);
    }
    while (paramBoolean)
    {
      if (paramFragment.mEnterTransitionCallback != null)
        paramFragment.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, localArrayMap);
      setBackNameOverrides(paramTransitionState, localArrayMap, false);
      return localArrayMap;
      label74: localArrayMap = remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, localArrayMap);
    }
    if (paramFragment.mExitTransitionCallback != null)
      paramFragment.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, localArrayMap);
    setNameOverrides(paramTransitionState, localArrayMap, false);
    return localArrayMap;
  }

  private void setBackNameOverrides(TransitionState paramTransitionState, ArrayMap<String, View> paramArrayMap, boolean paramBoolean)
  {
    int i;
    int j;
    label13: String str1;
    String str2;
    if (this.mSharedElementTargetNames == null)
    {
      i = 0;
      j = 0;
      if (j >= i)
        return;
      str1 = (String)this.mSharedElementSourceNames.get(j);
      View localView = (View)paramArrayMap.get((String)this.mSharedElementTargetNames.get(j));
      if (localView != null)
      {
        str2 = FragmentTransitionCompat21.getTransitionName(localView);
        if (!paramBoolean)
          break label100;
        setNameOverride(paramTransitionState.nameOverrides, str1, str2);
      }
    }
    while (true)
    {
      j++;
      break label13;
      i = this.mSharedElementTargetNames.size();
      break;
      label100: setNameOverride(paramTransitionState.nameOverrides, str2, str1);
    }
  }

  private void setEpicenterIn(ArrayMap<String, View> paramArrayMap, TransitionState paramTransitionState)
  {
    if ((this.mSharedElementTargetNames != null) && (!paramArrayMap.isEmpty()))
    {
      View localView = (View)paramArrayMap.get(this.mSharedElementTargetNames.get(0));
      if (localView != null)
        paramTransitionState.enteringEpicenterView.epicenter = localView;
    }
  }

  private static void setFirstOut(SparseArray<Fragment> paramSparseArray, Fragment paramFragment)
  {
    if (paramFragment != null)
    {
      int i = paramFragment.mContainerId;
      if ((i != 0) && (!paramFragment.isHidden()) && (paramFragment.isAdded()) && (paramFragment.getView() != null) && (paramSparseArray.get(i) == null))
        paramSparseArray.put(i, paramFragment);
    }
  }

  private void setLastIn(SparseArray<Fragment> paramSparseArray, Fragment paramFragment)
  {
    if (paramFragment != null)
    {
      int i = paramFragment.mContainerId;
      if (i != 0)
        paramSparseArray.put(i, paramFragment);
    }
  }

  private static void setNameOverride(ArrayMap<String, String> paramArrayMap, String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null) && (!paramString1.equals(paramString2)));
    for (int i = 0; i < paramArrayMap.size(); i++)
      if (paramString1.equals(paramArrayMap.valueAt(i)))
      {
        paramArrayMap.setValueAt(i, paramString2);
        return;
      }
    paramArrayMap.put(paramString1, paramString2);
  }

  private void setNameOverrides(TransitionState paramTransitionState, ArrayMap<String, View> paramArrayMap, boolean paramBoolean)
  {
    int i = paramArrayMap.size();
    int j = 0;
    if (j < i)
    {
      String str1 = (String)paramArrayMap.keyAt(j);
      String str2 = FragmentTransitionCompat21.getTransitionName((View)paramArrayMap.valueAt(j));
      if (paramBoolean)
        setNameOverride(paramTransitionState.nameOverrides, str1, str2);
      while (true)
      {
        j++;
        break;
        setNameOverride(paramTransitionState.nameOverrides, str2, str1);
      }
    }
  }

  private static void setNameOverrides(TransitionState paramTransitionState, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    if (paramArrayList1 != null)
      for (int i = 0; i < paramArrayList1.size(); i++)
      {
        String str1 = (String)paramArrayList1.get(i);
        String str2 = (String)paramArrayList2.get(i);
        setNameOverride(paramTransitionState.nameOverrides, str1, str2);
      }
  }

  public FragmentTransaction add(int paramInt, Fragment paramFragment)
  {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }

  public FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString)
  {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }

  public FragmentTransaction add(Fragment paramFragment, String paramString)
  {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }

  void addOp(Op paramOp)
  {
    if (this.mHead == null)
    {
      this.mTail = paramOp;
      this.mHead = paramOp;
    }
    while (true)
    {
      paramOp.enterAnim = this.mEnterAnim;
      paramOp.exitAnim = this.mExitAnim;
      paramOp.popEnterAnim = this.mPopEnterAnim;
      paramOp.popExitAnim = this.mPopExitAnim;
      this.mNumOp = (1 + this.mNumOp);
      return;
      paramOp.prev = this.mTail;
      this.mTail.next = paramOp;
      this.mTail = paramOp;
    }
  }

  public FragmentTransaction addSharedElement(View paramView, String paramString)
  {
    if (SUPPORTS_TRANSITIONS)
    {
      String str = FragmentTransitionCompat21.getTransitionName(paramView);
      if (str == null)
        throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
      if (this.mSharedElementSourceNames == null)
      {
        this.mSharedElementSourceNames = new ArrayList();
        this.mSharedElementTargetNames = new ArrayList();
      }
      this.mSharedElementSourceNames.add(str);
      this.mSharedElementTargetNames.add(paramString);
    }
    return this;
  }

  public FragmentTransaction addToBackStack(String paramString)
  {
    if (!this.mAllowAddToBackStack)
      throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    this.mAddToBackStack = true;
    this.mName = paramString;
    return this;
  }

  public FragmentTransaction attach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 7;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  void bumpBackStackNesting(int paramInt)
  {
    if (!this.mAddToBackStack);
    while (true)
    {
      return;
      if (FragmentManagerImpl.DEBUG)
        Log.v("FragmentManager", "Bump nesting in " + this + " by " + paramInt);
      for (Op localOp = this.mHead; localOp != null; localOp = localOp.next)
      {
        if (localOp.fragment != null)
        {
          Fragment localFragment2 = localOp.fragment;
          localFragment2.mBackStackNesting = (paramInt + localFragment2.mBackStackNesting);
          if (FragmentManagerImpl.DEBUG)
            Log.v("FragmentManager", "Bump nesting of " + localOp.fragment + " to " + localOp.fragment.mBackStackNesting);
        }
        if (localOp.removed != null)
          for (int i = -1 + localOp.removed.size(); i >= 0; i--)
          {
            Fragment localFragment1 = (Fragment)localOp.removed.get(i);
            localFragment1.mBackStackNesting = (paramInt + localFragment1.mBackStackNesting);
            if (FragmentManagerImpl.DEBUG)
              Log.v("FragmentManager", "Bump nesting of " + localFragment1 + " to " + localFragment1.mBackStackNesting);
          }
      }
    }
  }

  public void calculateBackFragments(SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    if (!this.mManager.mContainer.hasView());
    Op localOp;
    do
    {
      return;
      localOp = this.mHead;
    }
    while (localOp == null);
    switch (localOp.cmd)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    }
    while (true)
    {
      localOp = localOp.next;
      break;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      if (localOp.removed != null)
        for (int i = -1 + localOp.removed.size(); i >= 0; i--)
          setLastIn(paramSparseArray2, (Fragment)localOp.removed.get(i));
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
      continue;
      setLastIn(paramSparseArray2, localOp.fragment);
      continue;
      setFirstOut(paramSparseArray1, localOp.fragment);
    }
  }

  public int commit()
  {
    return commitInternal(false);
  }

  public int commitAllowingStateLoss()
  {
    return commitInternal(true);
  }

  int commitInternal(boolean paramBoolean)
  {
    if (this.mCommitted)
      throw new IllegalStateException("commit already called");
    if (FragmentManagerImpl.DEBUG)
    {
      Log.v("FragmentManager", "Commit: " + this);
      dump("  ", null, new PrintWriter(new LogWriter("FragmentManager")), null);
    }
    this.mCommitted = true;
    if (this.mAddToBackStack);
    for (this.mIndex = this.mManager.allocBackStackIndex(this); ; this.mIndex = -1)
    {
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    }
  }

  public FragmentTransaction detach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 6;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public FragmentTransaction disallowAddToBackStack()
  {
    if (this.mAddToBackStack)
      throw new IllegalStateException("This transaction is already being added to the back stack");
    this.mAllowAddToBackStack = false;
    return this;
  }

  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    dump(paramString, paramPrintWriter, true);
  }

  public void dump(String paramString, PrintWriter paramPrintWriter, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mName=");
      paramPrintWriter.print(this.mName);
      paramPrintWriter.print(" mIndex=");
      paramPrintWriter.print(this.mIndex);
      paramPrintWriter.print(" mCommitted=");
      paramPrintWriter.println(this.mCommitted);
      if (this.mTransition != 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mTransition=#");
        paramPrintWriter.print(Integer.toHexString(this.mTransition));
        paramPrintWriter.print(" mTransitionStyle=#");
        paramPrintWriter.println(Integer.toHexString(this.mTransitionStyle));
      }
      if ((this.mEnterAnim != 0) || (this.mExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mEnterAnim));
        paramPrintWriter.print(" mExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mExitAnim));
      }
      if ((this.mPopEnterAnim != 0) || (this.mPopExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mPopEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mPopEnterAnim));
        paramPrintWriter.print(" mPopExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mPopExitAnim));
      }
      if ((this.mBreadCrumbTitleRes != 0) || (this.mBreadCrumbTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
        paramPrintWriter.print(" mBreadCrumbTitleText=");
        paramPrintWriter.println(this.mBreadCrumbTitleText);
      }
      if ((this.mBreadCrumbShortTitleRes != 0) || (this.mBreadCrumbShortTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
        paramPrintWriter.print(" mBreadCrumbShortTitleText=");
        paramPrintWriter.println(this.mBreadCrumbShortTitleText);
      }
    }
    if (this.mHead != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      String str1 = paramString + "    ";
      Op localOp = this.mHead;
      for (int i = 0; localOp != null; i++)
      {
        String str2;
        int j;
        switch (localOp.cmd)
        {
        default:
          str2 = "cmd=" + localOp.cmd;
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  Op #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.print(str2);
          paramPrintWriter.print(" ");
          paramPrintWriter.println(localOp.fragment);
          if (paramBoolean)
          {
            if ((localOp.enterAnim != 0) || (localOp.exitAnim != 0))
            {
              paramPrintWriter.print(paramString);
              paramPrintWriter.print("enterAnim=#");
              paramPrintWriter.print(Integer.toHexString(localOp.enterAnim));
              paramPrintWriter.print(" exitAnim=#");
              paramPrintWriter.println(Integer.toHexString(localOp.exitAnim));
            }
            if ((localOp.popEnterAnim != 0) || (localOp.popExitAnim != 0))
            {
              paramPrintWriter.print(paramString);
              paramPrintWriter.print("popEnterAnim=#");
              paramPrintWriter.print(Integer.toHexString(localOp.popEnterAnim));
              paramPrintWriter.print(" popExitAnim=#");
              paramPrintWriter.println(Integer.toHexString(localOp.popExitAnim));
            }
          }
          if ((localOp.removed == null) || (localOp.removed.size() <= 0))
            break label804;
          j = 0;
          label641: if (j >= localOp.removed.size())
            break label804;
          paramPrintWriter.print(str1);
          if (localOp.removed.size() == 1)
            paramPrintWriter.print("Removed: ");
          break;
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        }
        while (true)
        {
          paramPrintWriter.println(localOp.removed.get(j));
          j++;
          break label641;
          str2 = "NULL";
          break;
          str2 = "ADD";
          break;
          str2 = "REPLACE";
          break;
          str2 = "REMOVE";
          break;
          str2 = "HIDE";
          break;
          str2 = "SHOW";
          break;
          str2 = "DETACH";
          break;
          str2 = "ATTACH";
          break;
          if (j == 0)
            paramPrintWriter.println("Removed:");
          paramPrintWriter.print(str1);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(j);
          paramPrintWriter.print(": ");
        }
        label804: localOp = localOp.next;
      }
    }
  }

  public CharSequence getBreadCrumbShortTitle()
  {
    if (this.mBreadCrumbShortTitleRes != 0)
      return this.mManager.mActivity.getText(this.mBreadCrumbShortTitleRes);
    return this.mBreadCrumbShortTitleText;
  }

  public int getBreadCrumbShortTitleRes()
  {
    return this.mBreadCrumbShortTitleRes;
  }

  public CharSequence getBreadCrumbTitle()
  {
    if (this.mBreadCrumbTitleRes != 0)
      return this.mManager.mActivity.getText(this.mBreadCrumbTitleRes);
    return this.mBreadCrumbTitleText;
  }

  public int getBreadCrumbTitleRes()
  {
    return this.mBreadCrumbTitleRes;
  }

  public int getId()
  {
    return this.mIndex;
  }

  public String getName()
  {
    return this.mName;
  }

  public int getTransition()
  {
    return this.mTransition;
  }

  public int getTransitionStyle()
  {
    return this.mTransitionStyle;
  }

  public FragmentTransaction hide(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 4;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public boolean isAddToBackStackAllowed()
  {
    return this.mAllowAddToBackStack;
  }

  public boolean isEmpty()
  {
    return this.mNumOp == 0;
  }

  public TransitionState popFromBackStack(boolean paramBoolean, TransitionState paramTransitionState, SparseArray<Fragment> paramSparseArray1, SparseArray<Fragment> paramSparseArray2)
  {
    if (FragmentManagerImpl.DEBUG)
    {
      Log.v("FragmentManager", "popFromBackStack: " + this);
      dump("  ", null, new PrintWriter(new LogWriter("FragmentManager")), null);
    }
    if (SUPPORTS_TRANSITIONS)
    {
      if (paramTransitionState != null)
        break label216;
      if ((paramSparseArray1.size() != 0) || (paramSparseArray2.size() != 0))
        paramTransitionState = beginTransition(paramSparseArray1, paramSparseArray2, true);
    }
    label91: bumpBackStackNesting(-1);
    int i;
    label103: int j;
    label110: Op localOp;
    int k;
    if (paramTransitionState != null)
    {
      i = 0;
      if (paramTransitionState == null)
        break label252;
      j = 0;
      localOp = this.mTail;
      if (localOp == null)
        break label577;
      if (paramTransitionState == null)
        break label261;
      k = 0;
      label128: if (paramTransitionState == null)
        break label271;
    }
    label261: label271: for (int m = 0; ; m = localOp.popExitAnim)
      switch (localOp.cmd)
      {
      default:
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
        label216: if (paramBoolean)
          break label91;
        ArrayList localArrayList1 = this.mSharedElementTargetNames;
        ArrayList localArrayList2 = this.mSharedElementSourceNames;
        setNameOverrides(paramTransitionState, localArrayList1, localArrayList2);
        break label91;
        i = this.mTransitionStyle;
        break label103;
        label252: j = this.mTransition;
        break label110;
        k = localOp.popEnterAnim;
        break label128;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      }
    Fragment localFragment8 = localOp.fragment;
    localFragment8.mNextAnim = m;
    this.mManager.removeFragment(localFragment8, FragmentManagerImpl.reverseTransit(j), i);
    while (true)
    {
      localOp = localOp.prev;
      break;
      Fragment localFragment6 = localOp.fragment;
      if (localFragment6 != null)
      {
        localFragment6.mNextAnim = m;
        this.mManager.removeFragment(localFragment6, FragmentManagerImpl.reverseTransit(j), i);
      }
      if (localOp.removed != null)
      {
        for (int n = 0; n < localOp.removed.size(); n++)
        {
          Fragment localFragment7 = (Fragment)localOp.removed.get(n);
          localFragment7.mNextAnim = k;
          this.mManager.addFragment(localFragment7, false);
        }
        Fragment localFragment5 = localOp.fragment;
        localFragment5.mNextAnim = k;
        this.mManager.addFragment(localFragment5, false);
        continue;
        Fragment localFragment4 = localOp.fragment;
        localFragment4.mNextAnim = k;
        this.mManager.showFragment(localFragment4, FragmentManagerImpl.reverseTransit(j), i);
        continue;
        Fragment localFragment3 = localOp.fragment;
        localFragment3.mNextAnim = m;
        this.mManager.hideFragment(localFragment3, FragmentManagerImpl.reverseTransit(j), i);
        continue;
        Fragment localFragment2 = localOp.fragment;
        localFragment2.mNextAnim = k;
        this.mManager.attachFragment(localFragment2, FragmentManagerImpl.reverseTransit(j), i);
        continue;
        Fragment localFragment1 = localOp.fragment;
        localFragment1.mNextAnim = k;
        this.mManager.detachFragment(localFragment1, FragmentManagerImpl.reverseTransit(j), i);
      }
    }
    label577: if (paramBoolean)
    {
      this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(j), i, true);
      paramTransitionState = null;
    }
    if (this.mIndex >= 0)
    {
      this.mManager.freeBackStackIndex(this.mIndex);
      this.mIndex = -1;
    }
    return paramTransitionState;
  }

  public FragmentTransaction remove(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 3;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public FragmentTransaction replace(int paramInt, Fragment paramFragment)
  {
    return replace(paramInt, paramFragment, null);
  }

  public FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString)
  {
    if (paramInt == 0)
      throw new IllegalArgumentException("Must use non-zero containerViewId");
    doAddOp(paramInt, paramFragment, paramString, 2);
    return this;
  }

  public void run()
  {
    if (FragmentManagerImpl.DEBUG)
      Log.v("FragmentManager", "Run: " + this);
    if ((this.mAddToBackStack) && (this.mIndex < 0))
      throw new IllegalStateException("addToBackStack() called after commit()");
    bumpBackStackNesting(1);
    boolean bool = SUPPORTS_TRANSITIONS;
    TransitionState localTransitionState = null;
    if (bool)
    {
      SparseArray localSparseArray1 = new SparseArray();
      SparseArray localSparseArray2 = new SparseArray();
      calculateFragments(localSparseArray1, localSparseArray2);
      localTransitionState = beginTransition(localSparseArray1, localSparseArray2, false);
    }
    int i;
    label112: int j;
    label119: Op localOp;
    int k;
    if (localTransitionState != null)
    {
      i = 0;
      if (localTransitionState == null)
        break label233;
      j = 0;
      localOp = this.mHead;
      if (localOp == null)
        break label727;
      if (localTransitionState == null)
        break label242;
      k = 0;
      label137: if (localTransitionState == null)
        break label252;
    }
    label233: label242: label252: for (int m = 0; ; m = localOp.exitAnim)
      switch (localOp.cmd)
      {
      default:
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
        i = this.mTransitionStyle;
        break label112;
        j = this.mTransition;
        break label119;
        k = localOp.enterAnim;
        break label137;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      }
    Fragment localFragment8 = localOp.fragment;
    localFragment8.mNextAnim = k;
    this.mManager.addFragment(localFragment8, false);
    while (true)
    {
      localOp = localOp.next;
      break;
      Fragment localFragment6 = localOp.fragment;
      if (this.mManager.mAdded != null)
      {
        int n = 0;
        if (n < this.mManager.mAdded.size())
        {
          Fragment localFragment7 = (Fragment)this.mManager.mAdded.get(n);
          if (FragmentManagerImpl.DEBUG)
            Log.v("FragmentManager", "OP_REPLACE: adding=" + localFragment6 + " old=" + localFragment7);
          if ((localFragment6 == null) || (localFragment7.mContainerId == localFragment6.mContainerId))
          {
            if (localFragment7 != localFragment6)
              break label432;
            localFragment6 = null;
            localOp.fragment = null;
          }
          while (true)
          {
            n++;
            break;
            label432: if (localOp.removed == null)
              localOp.removed = new ArrayList();
            localOp.removed.add(localFragment7);
            localFragment7.mNextAnim = m;
            if (this.mAddToBackStack)
            {
              localFragment7.mBackStackNesting = (1 + localFragment7.mBackStackNesting);
              if (FragmentManagerImpl.DEBUG)
                Log.v("FragmentManager", "Bump nesting of " + localFragment7 + " to " + localFragment7.mBackStackNesting);
            }
            this.mManager.removeFragment(localFragment7, j, i);
          }
        }
      }
      if (localFragment6 != null)
      {
        localFragment6.mNextAnim = k;
        this.mManager.addFragment(localFragment6, false);
        continue;
        Fragment localFragment5 = localOp.fragment;
        localFragment5.mNextAnim = m;
        this.mManager.removeFragment(localFragment5, j, i);
        continue;
        Fragment localFragment4 = localOp.fragment;
        localFragment4.mNextAnim = m;
        this.mManager.hideFragment(localFragment4, j, i);
        continue;
        Fragment localFragment3 = localOp.fragment;
        localFragment3.mNextAnim = k;
        this.mManager.showFragment(localFragment3, j, i);
        continue;
        Fragment localFragment2 = localOp.fragment;
        localFragment2.mNextAnim = m;
        this.mManager.detachFragment(localFragment2, j, i);
        continue;
        Fragment localFragment1 = localOp.fragment;
        localFragment1.mNextAnim = k;
        this.mManager.attachFragment(localFragment1, j, i);
      }
    }
    label727: this.mManager.moveToState(this.mManager.mCurState, j, i, true);
    if (this.mAddToBackStack)
      this.mManager.addBackStackState(this);
  }

  public FragmentTransaction setBreadCrumbShortTitle(int paramInt)
  {
    this.mBreadCrumbShortTitleRes = paramInt;
    this.mBreadCrumbShortTitleText = null;
    return this;
  }

  public FragmentTransaction setBreadCrumbShortTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbShortTitleRes = 0;
    this.mBreadCrumbShortTitleText = paramCharSequence;
    return this;
  }

  public FragmentTransaction setBreadCrumbTitle(int paramInt)
  {
    this.mBreadCrumbTitleRes = paramInt;
    this.mBreadCrumbTitleText = null;
    return this;
  }

  public FragmentTransaction setBreadCrumbTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbTitleRes = 0;
    this.mBreadCrumbTitleText = paramCharSequence;
    return this;
  }

  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2)
  {
    return setCustomAnimations(paramInt1, paramInt2, 0, 0);
  }

  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mEnterAnim = paramInt1;
    this.mExitAnim = paramInt2;
    this.mPopEnterAnim = paramInt3;
    this.mPopExitAnim = paramInt4;
    return this;
  }

  public FragmentTransaction setTransition(int paramInt)
  {
    this.mTransition = paramInt;
    return this;
  }

  public FragmentTransaction setTransitionStyle(int paramInt)
  {
    this.mTransitionStyle = paramInt;
    return this;
  }

  public FragmentTransaction show(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 5;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("BackStackEntry{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    if (this.mIndex >= 0)
    {
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mIndex);
    }
    if (this.mName != null)
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mName);
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }

  static final class Op
  {
    int cmd;
    int enterAnim;
    int exitAnim;
    Fragment fragment;
    Op next;
    int popEnterAnim;
    int popExitAnim;
    Op prev;
    ArrayList<Fragment> removed;
  }

  public class TransitionState
  {
    public FragmentTransitionCompat21.EpicenterView enteringEpicenterView = new FragmentTransitionCompat21.EpicenterView();
    public ArrayList<View> hiddenFragmentViews = new ArrayList();
    public ArrayMap<String, String> nameOverrides = new ArrayMap();
    public View nonExistentView;

    public TransitionState()
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.BackStackRecord
 * JD-Core Version:    0.6.2
 */