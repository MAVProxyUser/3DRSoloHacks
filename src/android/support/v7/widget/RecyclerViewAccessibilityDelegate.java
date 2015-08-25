package android.support.v7.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat
{
  final AccessibilityDelegateCompat mItemDelegate = new AccessibilityDelegateCompat()
  {
    public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
      if ((!RecyclerViewAccessibilityDelegate.this.shouldIgnore()) && (RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null))
        RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
    }

    public boolean performAccessibilityAction(View paramAnonymousView, int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      if (super.performAccessibilityAction(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle))
        return true;
      if ((!RecyclerViewAccessibilityDelegate.this.shouldIgnore()) && (RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null))
        return RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle);
      return false;
    }
  };
  final RecyclerView mRecyclerView;

  public RecyclerViewAccessibilityDelegate(RecyclerView paramRecyclerView)
  {
    this.mRecyclerView = paramRecyclerView;
  }

  private boolean shouldIgnore()
  {
    return this.mRecyclerView.hasPendingAdapterUpdates();
  }

  AccessibilityDelegateCompat getItemDelegate()
  {
    return this.mItemDelegate;
  }

  public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(RecyclerView.class.getName());
    if (((paramView instanceof RecyclerView)) && (!shouldIgnore()))
    {
      RecyclerView localRecyclerView = (RecyclerView)paramView;
      if (localRecyclerView.getLayoutManager() != null)
        localRecyclerView.getLayoutManager().onInitializeAccessibilityEvent(paramAccessibilityEvent);
    }
  }

  public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
    paramAccessibilityNodeInfoCompat.setClassName(RecyclerView.class.getName());
    if ((!shouldIgnore()) && (this.mRecyclerView.getLayoutManager() != null))
      this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfoCompat);
  }

  public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
  {
    if (super.performAccessibilityAction(paramView, paramInt, paramBundle))
      return true;
    if ((!shouldIgnore()) && (this.mRecyclerView.getLayoutManager() != null))
      return this.mRecyclerView.getLayoutManager().performAccessibilityAction(paramInt, paramBundle);
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     android.support.v7.widget.RecyclerViewAccessibilityDelegate
 * JD-Core Version:    0.6.2
 */