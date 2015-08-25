package com.o3dr.solo.android.adapter;

import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ScanResultAdapter extends RecyclerView.Adapter<ViewHolder>
{
  private OnItemClickListener itemClickListener;
  private final ArrayList<ScanResult> scanResults = new ArrayList();
  private String selectedSSID;

  public ScanResultAdapter(ArrayList<ScanResult> paramArrayList)
  {
    this.scanResults.addAll(paramArrayList);
  }

  public int getItemCount()
  {
    return this.scanResults.size();
  }

  public int getSelectedPosition()
  {
    if (TextUtils.isEmpty(this.selectedSSID))
    {
      j = -1;
      return j;
    }
    int i = this.scanResults.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label59;
      if (this.selectedSSID.equals(((ScanResult)this.scanResults.get(j)).SSID))
        break;
    }
    label59: return -1;
  }

  public void onBindViewHolder(ViewHolder paramViewHolder, int paramInt)
  {
    final ScanResult localScanResult = (ScanResult)this.scanResults.get(paramInt);
    final String str = localScanResult.SSID;
    paramViewHolder.soloLinkCheck.setChecked(str.equals(this.selectedSSID));
    paramViewHolder.soloLinkLabel.setText(localScanResult.SSID);
    paramViewHolder.itemView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ScanResultAdapter.this.setSelectedLink(str);
        if (ScanResultAdapter.this.itemClickListener != null)
          ScanResultAdapter.this.itemClickListener.onItemClick(localScanResult);
      }
    });
  }

  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(2130903123, paramViewGroup, false);
    return new ViewHolder(localView, (TextView)localView.findViewById(2131493350), (RadioButton)localView.findViewById(2131493351));
  }

  public void setAvailableLinks(List<ScanResult> paramList)
  {
    this.scanResults.clear();
    this.scanResults.addAll(paramList);
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.itemClickListener = paramOnItemClickListener;
  }

  public void setSelectedLink(String paramString)
  {
    this.selectedSSID = paramString;
    notifyDataSetChanged();
  }

  public static abstract interface OnItemClickListener
  {
    public abstract void onItemClick(ScanResult paramScanResult);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder
  {
    final RadioButton soloLinkCheck;
    final TextView soloLinkLabel;

    public ViewHolder(View paramView, TextView paramTextView, RadioButton paramRadioButton)
    {
      super();
      this.soloLinkLabel = paramTextView;
      this.soloLinkCheck = paramRadioButton;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.adapter.ScanResultAdapter
 * JD-Core Version:    0.6.2
 */