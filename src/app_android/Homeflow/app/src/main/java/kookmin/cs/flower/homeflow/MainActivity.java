package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Timer;
import java.util.TimerTask;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @brief class for showing activity_main.xml layout
 * @details This class includes timer-related task.
 * @date 2015-04-14
 */
public class MainActivity extends FragmentActivity {

  /**
   * @brief method for showing activity_main.xml layout
   * @details This method changes the layout into tabs.xml after 3 secs.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TimerTask task = new TimerTask() {

      @Override
      public void run() {
        Intent intent = new Intent(getApplicationContext(), Tabs.class);
        FileManager.updateFlow();
        FileManager.updateAppliance();
        startActivity(intent);
        finish();
      }
    };

    Timer timer = new Timer();
    timer.schedule(task, 3000);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}