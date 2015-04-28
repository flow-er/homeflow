package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @brief class for showing tabs.xml layout
 * @details This class includes tabhost fragmenttabhost which contains three tabs.
 * It also includes login_btn button. If you click login_btn, Login.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.6
 * @date 2015-04-19
 */
public class Tabs extends FragmentActivity implements TabHost.OnTabChangeListener, View.OnClickListener {

  /**
   * @brief method for showing tabs.xml layout
   * @details This method concatenates three tabs to tabhost.
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tabs);

    FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
    tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

    TabSpec tabSpec1 = tabHost.newTabSpec("Tab1");
    tabSpec1.setIndicator("Home");
    tabHost.addTab(tabSpec1, HomeTab.class, null);

    // Editor Tab
    TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
    tabSpec2.setIndicator("Editor"); // Tab Subject
    tabHost.addTab(tabSpec2, EditTab.class, null);

    // Dashboard Tab
    TabSpec tabSpec3 = tabHost.newTabSpec("Tab3");
    tabSpec3.setIndicator("Dashboard"); // Tab Subject
    tabHost.addTab(tabSpec3, DashTab.class, null);

    // show First Tab Content
    tabHost.setCurrentTab(0);
    tabHost.setOnTabChangedListener(this);

    Button login_btn = (Button)findViewById(R.id.login_btn);
    login_btn.setOnClickListener(this);
  }

  /**
   * @brief method for determining action of login_btn
   * @details If you click login_btn, login.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.login_btn) {
      Intent intent = new Intent(this, Personal.class);
      startActivity(intent);
    }
  }

  /**
   * @brief method for determining action of Tab1, Tab2, and Tab3
   * @details If you select Tab1, hometab.xml layout will appear.
   *            If you select Tab2, edittab.xml layout will appear.
   *            If you select Tab3, dashtab.xml layout will appear.
   * @param tabId
   */
  @Override
  public void onTabChanged(String tabId) {
    switch(tabId) {
      case "Tab1":
        HomeTab homeTab = new HomeTab();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, homeTab).commit();
        break;
      case "Tab2":
        EditTab editTab = new EditTab();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, editTab).commit();
        break;
      case "Tab3":
        DashTab dashTab = new DashTab();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, dashTab).commit();
        break;
    }
  }
}