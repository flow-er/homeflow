package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing login.xml layout
 * @details This class includes sign_up_btn and log_in_btn buttons. If you click sign_up_btn,
 * account.xml layout will appear. If you click log_in_btn, login.xml layout will appear.
 * @date 2015-05-01
 */
public class condActivity extends FragmentActivity {

  /**
   * @brief method for showing personal.xml layout
   * @details This method sets clicked-events on sign_up_btn and log_in_btn.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.cond_activity);

    CondSelect condSelect = new CondSelect();
    getSupportFragmentManager().beginTransaction().replace(R.id.cond_activity_container, condSelect).commit();

  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    // TODO Auto-generated method stub
    if (outState != null) {
      super.onSaveInstanceState(outState);
    }
  }


}
