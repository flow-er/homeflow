package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing login.xml layout
 * @details This class includes sign_up_btn and log_in_btn buttons. If you click sign_up_btn,
 * account.xml layout will appear. If you click log_in_btn, login.xml layout will appear.
 * @date 2015-05-01
 */
public class Personal extends FragmentActivity implements View.OnClickListener {

  /**
   * @brief method for showing personal.xml layout
   * @details This method sets clicked-events on sign_up_btn and log_in_btn.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.personal);

    Login login = new Login();
    getSupportFragmentManager().beginTransaction().add(R.id.personal_frag, login).commit();

    Button sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
    Button log_in_btn = (Button) findViewById(R.id.log_in_btn);

    sign_up_btn.setOnClickListener(this);
    log_in_btn.setOnClickListener(this);
  }

  /**
   * @brief method for determining actions of sign_up_btn and log_in_btn
   * @details If you click sign_up_btn, account.xml layout will appear. If you click log_in_btn,
   * login.xml layout will appear.
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sign_up_btn:
        Account account = new Account();
        getSupportFragmentManager().beginTransaction().replace(R.id.personal_frag, account)
            .commit();
        break;
      case R.id.log_in_btn:
        Login login = new Login();
        getSupportFragmentManager().beginTransaction().replace(R.id.personal_frag, login).commit();
        break;
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
  }
}
