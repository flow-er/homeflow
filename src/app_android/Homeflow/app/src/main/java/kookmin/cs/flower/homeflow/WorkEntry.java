package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @brief class for showing workentry.xml layout
 * @details This class includes cond_btn button, appli_btn button, and work_entry_btn button.
 *            If you click cond_btn, condselect.xml layout will appear
 *            If you click appli_btn, appliselect.xml layout will appear
 *            If you click work_entry_btn, addflow.xml layout will appear
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class WorkEntry extends Fragment implements View.OnClickListener {

  /**
   * @brief method for showing workentry.xml layout
   * @details This method sets clicked-events on cond_btn, appli_btn, and work_entry_btn
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.workentry, container, false);

    Button cond_btn = (Button) rootView.findViewById(R.id.cond_btn);
    Button appli_btn = (Button) rootView.findViewById(R.id.appli_btn);
    Button work_entry_btn = (Button) rootView.findViewById(R.id.work_entry_btn);

    if(getArguments() != null) {
      Log.i("mytag", "" + getArguments().toString());
      Log.i("mytag", getArguments().getString("result"));
    }
    if (getArguments() != null && getArguments().containsKey("result")) {
      appli_btn.setText(getArguments().getString("result"));
    }

    cond_btn.setOnClickListener(this);
    appli_btn.setOnClickListener(this);
    work_entry_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining actions of cond_btn, appli_btn, and work_entry_btn
   * @details If you click cond_btn, condselect.xml layout will appear.
   *            If you click appli_btn, appliselect.xml layout will appear.
   *            If you click work_entry_btn, addflow.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cond_btn:
        CondSelect condSelect = new CondSelect();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, condSelect).commit();
        break;
      case R.id.appli_btn:
        AppliSelect appliSelect = new AppliSelect();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliSelect).commit();
        break;
      case R.id.work_entry_btn:
        AddFlow addFlow = new AddFlow();
        addFlow.setArguments(getArguments());
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addFlow).commit();
        break;
    }
  }
}
