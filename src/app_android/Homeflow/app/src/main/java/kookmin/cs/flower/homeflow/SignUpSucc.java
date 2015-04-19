package kookmin.cs.flower.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * @brief class for showing signupsucc.xml layout
 * @details This class includes log_in_btn button.
 * If you click log_in_btn, login.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.0
 * @date 2015-04-19
 */
public class SignUpSucc extends FragmentActivity implements View.OnClickListener {

  /**
   * @brief method for showing signupsucc.xml layout
   * @details This method sets a clicked_event on log_in_btn.
   * @param savedInstanceState
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signupsucc);

    Button log_in_btn = (Button)findViewById(R.id.log_in_btn);
    log_in_btn.setOnClickListener(this);
  }

  /**
   * @brief method for determining action of log_in_btn.
   * @details If you click log_in_btn, login.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.log_in_btn) {
      Intent intent = intent = new Intent(this, Login.class);
      startActivity(intent);
    }
  }
}
