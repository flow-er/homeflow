package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @brief class for showing account.xml layout
 * @details This class includes signup_btn.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-05-01
 */
public class Account extends Fragment implements View.OnClickListener {

  /**
   * @brief method for showing account.xml layout
   * @details This method sets clicked-events on signup_btn.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.account, container, false);

    Button signup_btn = (Button)rootView.findViewById(R.id.signup_btn);
    signup_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining action of signup_btn
   * @details
   * @param v
   */
  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.signup_btn) {

    }
  }
}