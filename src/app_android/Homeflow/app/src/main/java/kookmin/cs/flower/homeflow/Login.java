package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Login extends Fragment implements View.OnClickListener {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.login, container, false);

    Button login_btn = (Button)rootView.findViewById(R.id.login_btn);
    login_btn.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.login_btn) {

    }
  }
}