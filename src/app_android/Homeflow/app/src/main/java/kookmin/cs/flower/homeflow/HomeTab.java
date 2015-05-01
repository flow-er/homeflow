package kookmin.cs.flower.homeflow;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;

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

    tabHost.getTabWidget().setDividerDrawable(null);

    Context context01 = V.getContext();
    ImageView tabwidget01 = new ImageView(context01);
    tabwidget01.setImageResource(R.drawable.intro_tab);

    Context context02 = V.getContext();
    ImageView tabwidget02 = new ImageView(context02);
    tabwidget02.setImageResource(R.drawable.man_tab);

    TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1");
    tabSpec1.setContent(R.id.tab1);
    tabSpec1.setIndicator(tabwidget01);
    tabHost.addTab(tabSpec1);

    TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2");
    tabSpec2.setContent(R.id.tab2);
    tabSpec2.setIndicator(tabwidget02);
    tabHost.addTab(tabSpec2);

    tabHost.setCurrentTab(0);

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
      tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF808ED2"));

    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFCA93E8"));
  }
}
