package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing login.xml layout
 * @details This class includes login_btn.
 * @date 2015-05-01
 */
public class Login extends Fragment implements View.OnClickListener {

  /**
   * @return rootView
   * @brief method for showing login.xml layout
   * @details This method sets clicked-events on login_btn.
   */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

      View rootView = inflater.inflate(R.layout.login, container, false);

      Button login_btn = (Button) rootView.findViewById(R.id.login_btn);
      login_btn.setOnClickListener(this);

      return rootView;
  }

  /**
   * @brief method for determining action of login_btn
   * @details
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.login_btn) {
      getActivity().finish();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}