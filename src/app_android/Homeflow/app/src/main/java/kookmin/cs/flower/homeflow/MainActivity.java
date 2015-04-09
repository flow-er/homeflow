package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import kookmin.cs.flower.homeflow.Service.ReadstateReceiver;
import kookmin.cs.flower.homeflow.Service.SocketService;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing activity_main.xml layout
 * @details This class includes tabhost fragmenttabhost which contains three tabs.
 * @date 2015-04-08
 */
public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

  private ReadstateReceiver m_ReadStateReceiver=null;
  /**
   * @brief method for showing activity_main.xml layout
   * @details This method concatenates three tabs to tabhost.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
  }

  @Override
  public void onResume() {
    super.onResume();

    StartRcvReadStateBrodcast();
  }

  public void onPause() {
    super.onPause();

    StopRcvReadStateBrodcast();
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
    switch (id) {
      case R.id.action_settings:
        return true;

      case R.id.action_start_service:
        startService(new Intent(this, SocketService.class));
        return true;

      case R.id.action_stop_service:
        stopService(new Intent(this, SocketService.class));
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void StartRcvReadStateBrodcast() {
    IntentFilter filter;
    filter = new IntentFilter(SocketService.READ_STATE);
    m_ReadStateReceiver = new ReadstateReceiver();
    registerReceiver(m_ReadStateReceiver, filter);
  }
  private void StopRcvReadStateBrodcast() {
    if(m_ReadStateReceiver != null) {
      unregisterReceiver(m_ReadStateReceiver);
      m_ReadStateReceiver = null;
    }
  }
}