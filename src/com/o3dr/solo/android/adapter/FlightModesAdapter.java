package com.o3dr.solo.android.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import java.util.ArrayList;
import java.util.List;

public class FlightModesAdapter extends RecyclerView.Adapter<ViewHolder>
{
  private OnItemClickListener itemClickListener;
  private VehicleMode selectedMode;
  private final List<VehicleMode> vehicleModes = new ArrayList();

  public FlightModesAdapter()
  {
  }

  public FlightModesAdapter(List<VehicleMode> paramList)
  {
    this.vehicleModes.addAll(paramList);
  }

  public void clear()
  {
    this.vehicleModes.clear();
    notifyDataSetChanged();
  }

  public int getItemCount()
  {
    return this.vehicleModes.size();
  }

  public int getSelectedPosition()
  {
    if (this.selectedMode == null)
    {
      j = -1;
      return j;
    }
    int i = this.vehicleModes.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label51;
      if (this.selectedMode == this.vehicleModes.get(j))
        break;
    }
    label51: return -1;
  }

  public void onBindViewHolder(final ViewHolder paramViewHolder, int paramInt)
  {
    final VehicleMode localVehicleMode = (VehicleMode)this.vehicleModes.get(paramInt);
    RadioButton localRadioButton = paramViewHolder.checkBox;
    boolean bool;
    if (localVehicleMode == this.selectedMode)
    {
      bool = true;
      localRadioButton.setChecked(bool);
      switch (2.$SwitchMap$com$o3dr$services$android$lib$drone$property$VehicleMode[localVehicleMode.ordinal()])
      {
      default:
        paramViewHolder.flightModeLabel.setText(localVehicleMode.getLabel());
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      }
    }
    while (true)
    {
      paramViewHolder.itemView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FlightModesAdapter.access$202(FlightModesAdapter.this, localVehicleMode);
          FlightModesAdapter.ViewHolder.access$000(paramViewHolder).setChecked(true);
          if (FlightModesAdapter.this.itemClickListener != null)
            FlightModesAdapter.this.itemClickListener.onItemClick(localVehicleMode);
        }
      });
      return;
      bool = false;
      break;
      paramViewHolder.flightModeLabel.setText(2131099770);
      continue;
      paramViewHolder.flightModeLabel.setText(2131099766);
      continue;
      paramViewHolder.flightModeLabel.setText(2131099772);
      continue;
      paramViewHolder.flightModeLabel.setText(2131099769);
      continue;
      paramViewHolder.flightModeLabel.setText(2131099768);
      continue;
      paramViewHolder.flightModeLabel.setText(2131099771);
      continue;
      paramViewHolder.flightModeLabel.setText(2131099767);
    }
  }

  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(2130903124, paramViewGroup, false);
    RadioButton localRadioButton = (RadioButton)localView.findViewById(2131493353);
    return new ViewHolder(localView, (TextView)localView.findViewById(2131493352), localRadioButton);
  }

  public void setFlightModes(List<VehicleMode> paramList)
  {
    if (paramList == null)
      return;
    this.vehicleModes.clear();
    this.vehicleModes.addAll(paramList);
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.itemClickListener = paramOnItemClickListener;
  }

  public void setSelected(VehicleMode paramVehicleMode)
  {
    this.selectedMode = paramVehicleMode;
    notifyDataSetChanged();
  }

  public static abstract interface OnItemClickListener
  {
    public abstract void onItemClick(VehicleMode paramVehicleMode);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder
  {
    private final RadioButton checkBox;
    private final TextView flightModeLabel;

    public ViewHolder(View paramView, TextView paramTextView, RadioButton paramRadioButton)
    {
      super();
      this.flightModeLabel = paramTextView;
      this.checkBox = paramRadioButton;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.adapter.FlightModesAdapter
 * JD-Core Version:    0.6.2
 */