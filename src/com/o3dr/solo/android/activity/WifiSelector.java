package com.o3dr.solo.android.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.ViewGroup.LayoutParams;
import com.o3dr.solo.android.adapter.ScanResultAdapter;
import com.o3dr.solo.android.adapter.ScanResultAdapter.OnItemClickListener;
import com.o3dr.solo.android.service.AppService;
import java.util.ArrayList;

public class WifiSelector extends ActionBarActivity
  implements ScanResultAdapter.OnItemClickListener
{
  private ScanResultAdapter scanResultAdapter;
  private RecyclerView soloLinks;

  private void handleIntent(Intent paramIntent)
  {
    if (paramIntent == null)
      return;
    String str = paramIntent.getStringExtra("extra_selected_solo_link");
    ArrayList localArrayList = paramIntent.getParcelableArrayListExtra("extra_available_solo_links");
    if (this.scanResultAdapter == null)
    {
      this.scanResultAdapter = new ScanResultAdapter(localArrayList);
      this.scanResultAdapter.setSelectedLink(str);
      this.scanResultAdapter.setOnItemClickListener(this);
      this.soloLinks.setAdapter(this.scanResultAdapter);
    }
    while (true)
    {
      ViewGroup.LayoutParams localLayoutParams = this.soloLinks.getLayoutParams();
      localLayoutParams.height = ((int)(Math.min(4, localArrayList.size()) * getResources().getDimension(2131165301)));
      this.soloLinks.setLayoutParams(localLayoutParams);
      this.soloLinks.requestLayout();
      int i = this.scanResultAdapter.getSelectedPosition();
      if (i <= -1)
        break;
      this.soloLinks.getLayoutManager().scrollToPosition(i);
      return;
      this.scanResultAdapter.setAvailableLinks(localArrayList);
      this.scanResultAdapter.setSelectedLink(str);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903081);
    this.soloLinks = ((RecyclerView)findViewById(2131493063));
    this.soloLinks.setHasFixedSize(true);
    this.soloLinks.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
    handleIntent(getIntent());
  }

  public void onItemClick(ScanResult paramScanResult)
  {
    startService(new Intent(getApplicationContext(), AppService.class).setAction("com.o3dr.solo.android.action.CONNECT_TO_WIFI").putExtra("extra_wifi_scan_result", paramScanResult));
    finish();
  }

  public void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    handleIntent(paramIntent);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.WifiSelector
 * JD-Core Version:    0.6.2
 */