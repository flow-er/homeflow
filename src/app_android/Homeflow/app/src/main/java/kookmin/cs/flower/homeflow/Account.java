package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * @brief class for showing account.xml layout
 * @details This class includes log_in_btn and signup_signup_btn buttons.
 * If you click log_in_btn, login.xml layout will appear.
 * If you click signup_signup_btn, signupsucc.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.0
 * @date 2015-04-19
 */
public class Account extends FragmentActivity implements View.OnClickListener {

  /**
   * @brief method for showing account.xml layout
   * @details This method sets clicked_events on log_in_btn and signup_signup_btn.
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.account);

    Button log_in_btn = (Button)findViewById(R.id.log_in_btn);
    log_in_btn.setOnClickListener(this);

    Button signup_signup_btn = (Button)findViewById(R.id.signup_signup_btn);
    signup_signup_btn.setOnClickListener(this);
  }

  /**
   * @brief method for determining actions of log_in_btn and signup_signup_btn
   * @details If you click log_in_btn, login.xml layout will appear.
   * If you click signup_signup_btn, signupsucc.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    Intent intent;
    switch(v.getId()) {
      case R.id.log_in_btn:
        intent = new Intent(this, Login.class);
        startActivity(intent);
        break;
      case R.id.signup_signup_btn:
        intent = new Intent(this, SignUpSucc.class);
        startActivity(intent);
        break;
    }
  }
}