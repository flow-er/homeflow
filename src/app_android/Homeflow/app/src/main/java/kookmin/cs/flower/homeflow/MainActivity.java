package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.TabSpec;

import kookmin.cs.flower.homeflow.Service.SocketService;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class MainActivity extends FragmentActivity {

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
  }
}