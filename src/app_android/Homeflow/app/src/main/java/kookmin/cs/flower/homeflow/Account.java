package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Account extends Fragment implements View.OnClickListener {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.account, container, false);

    Button signup_btn = (Button)rootView.findViewById(R.id.signup_btn);
    signup_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining actions of log_in_btn and signup_signup_btn
   * @details If you click log_in_btn, login.xml layout will appear.
   * If you click signup_signup_btn, signupsucc.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.signup_btn) {

    }
  }
}