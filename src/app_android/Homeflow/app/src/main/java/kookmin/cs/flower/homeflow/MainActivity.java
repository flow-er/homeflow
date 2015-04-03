package kookmin.cs.flower.homeflow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
    tabHost.setup();

    // Home Tab
    TabSpec tabSpec1 = tabHost.newTabSpec("Tab1");
    tabSpec1.setIndicator("Home"); // Tab Subject
    tabSpec1.setContent(R.id.tab_view1); // Tab Content
    tabHost.addTab(tabSpec1);

    // Editor Tab
    TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
    tabSpec2.setIndicator("Editor"); // Tab Subject
    tabSpec2.setContent(R.id.tab_view2); // Tab Content
    tabHost.addTab(tabSpec2);

    // Dashboard Tab
    TabSpec tabSpec3 = tabHost.newTabSpec("Tab3");
    tabSpec3.setIndicator("Dashboard"); // Tab Subject
    tabSpec3.setContent(R.id.tab_view3); // Tab Content
    tabHost.addTab(tabSpec3);

    // show First Tab Content
    tabHost.setCurrentTab(0);
  }
}
