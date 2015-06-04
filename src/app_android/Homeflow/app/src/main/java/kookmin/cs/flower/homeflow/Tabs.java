package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import kookmin.cs.flower.homeflow.Service.setServerActivity;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.8
 * @brief class for showing tabs.xml layout
 * @details This class includes tabhost fragmenttabhost which contains three tabs. It also includes
 * login_btn button. If you click login_btn, Login.xml layout will appear.
 * @date 2015-05-01
 */
public class Tabs extends FragmentActivity
    implements TabHost.OnTabChangeListener, View.OnClickListener {

  FragmentTabHost tabHost;

  /**
   * @brief method for showing tabs.xml layout
   * @details This method concatenates three tabs to tabhost.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tabs);

    tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
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

    for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
      tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFFFF"));
      TextView
          tv =
          (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
      tv.setTextColor(Color.parseColor("#FF2A3F13"));
    }

    tabHost.getTabWidget().setCurrentTab(0);
    tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#FF2A3F13"));
    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
    tv.setTextColor(Color.parseColor("#FFFFFFFF"));

    Button login_btn = (Button) findViewById(R.id.login_btn);
    login_btn.setOnClickListener(this);
  }

  /**
   * @brief method for determining action of login_btn
   * @details If you click login_btn, login.xml layout will appear.
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
   * @details If you select Tab1, hometab.xml layout will appear. If you select Tab2, edittab.xml
   * layout will appear. If you select Tab3, dashtab.xml layout will appear.
   */
  @Override
  public void onTabChanged(String tabId) {
    switch (tabId) {
      case "Tab1":
        HomeTab homeTab = new HomeTab();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, homeTab)
            .commit();
        break;
      case "Tab2":
        EditTab editTab = new EditTab();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, editTab)
            .commit();
        break;
      case "Tab3":
        DashTab dashTab = new DashTab();
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, dashTab)
            .commit();
        break;
    }

    for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
      tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFFFF"));
      TextView
          tv =
          (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
      tv.setTextColor(Color.parseColor("#FF2A3F13"));
    }

    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
        .setBackgroundColor(Color.parseColor("#FF2A3F13"));
    TextView
        tv =
        (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
            .findViewById(android.R.id.title);
    tv.setTextColor(Color.parseColor("#FFFFFFFF"));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      startActivity(new Intent(this, setServerActivity.class));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}