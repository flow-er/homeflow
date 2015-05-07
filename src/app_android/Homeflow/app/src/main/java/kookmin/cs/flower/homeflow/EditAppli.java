package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @brief class for showing editappli.xml layout
 * @details This class includes edit_appli_btn button.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.0
 * @date 2015-05-07
 */
public class EditAppli extends Fragment implements View.OnClickListener {

  /**
   * @brief method for showing editappli.xml layout
   * @details
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.editappli, container, false);

    Button edit_appli_btn = (Button) rootView.findViewById(R.id.edit_appli_btn);
    edit_appli_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining action of edit_appli_btn
   * @details If you click edit_appli_btn, applireg.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.edit_appli_btn) {
        AppliReg appliReg = new AppliReg();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
    }
  }
}
