package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * @brief class for showing login.xml layout
 * @details This class includes sign_up_btn and login_login_btn buttons.
 * If you click sign_up_btn, account.xml layout will appear.
 * If you click login_login_btn, tabs.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.0
 * @date 2015-04-19
 */
public class Login extends FragmentActivity implements View.OnClickListener {

  /**
   * @brief method for showing login.xml layout
   * @details This method sets clicked_events on sign_up_btn and login_login_btn.
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

    Button sign_up_btn = (Button)findViewById(R.id.sign_up_btn);
    sign_up_btn.setOnClickListener(this);

    Button login_login_btn = (Button)findViewById(R.id.login_login_btn);
    login_login_btn.setOnClickListener(this);
  }

  /**
   * @brief method for determining actions of sign_up_btn and login_login_btn
   * @details If you click sign_up_btn, account.xml layout will appear.
   * If you click login_login_btn, tabs.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    Intent intent;
    switch(v.getId()) {
      case R.id.sign_up_btn:
        intent = new Intent(this, Account.class);
        startActivity(intent);
        break;
      case R.id.login_login_btn:
        intent = new Intent(this, Tabs.class);
        startActivity(intent);
        break;
    }
  }
}