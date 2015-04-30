package kookmin.cs.flower.homeflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @brief class for showing hometab.xml layout
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-01
 */
public class HomeTab extends Fragment implements TabHost.OnTabChangeListener {

  TabHost tabHost;

  /**
   * @brief method for showing hometab.xml layout
   * @details This method concatenates two tabs to tabhost.
   * @param savedInstanceState
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View V = inflater.inflate(R.layout.hometab, container, false);
    ImageView imageView = (ImageView)V.findViewById(R.id.hometabimage);
    imageView.setAdjustViewBounds(true);

    tabHost = (TabHost)V.findViewById(R.id.tabhost);
    tabHost.setup();

    TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1");
    tabSpec1.setContent(R.id.tab1);
    tabSpec1.setIndicator("Introduction");
    tabHost.addTab(tabSpec1);

    TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2");
    tabSpec2.setContent(R.id.tab2);
    tabSpec2.setIndicator("Manual");
    tabHost.addTab(tabSpec2);

    tabHost.setCurrentTab(0);

    for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
      tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF808ED2"));
      TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
      tv.setTextColor(Color.parseColor("#FFFFFFFF"));
    }

    tabHost.getTabWidget().setCurrentTab(0);
    tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#FFCA93E8"));

    ScrollView mainScrollView = (ScrollView)V.findViewById(R.id.hometabscroll);

    mainScrollView.smoothScrollTo(0, 0);

    return V;
  }

  /**
   * @brief method for determining actions of tab1 and tab2
   * @details If you select tab1, introduction page will appear.
   *            If you select Tab2, manual page will appear.
   * @param tabId
   */
  @Override
  public void onTabChanged(String tabId) {
    for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
      tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF2A3F13"));

    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FF2A3F13"));
  }
}
