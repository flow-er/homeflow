package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @brief class for showing edittab.xml layout
 * @method This class includes appli_reg_btn button and flow_reg_btn button.
 *          If you click appli_reg_btn, applireg.xml layout will appear.
 *          If you click flow_reg_btn, flowreg.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-03
 */
public class EditTab extends Fragment implements View.OnClickListener {

  /**
   * @brief method for showing edittab.xml layout
   * @details This method sets clicked-events on appli_reg_btn and flow_reg_btn.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.edittab, container, false);

    ImageView imageView = (ImageView)rootView.findViewById(R.id.edittabimg);
    imageView.setAdjustViewBounds(true);

    imageView = (ImageView)rootView.findViewById(R.id.edittab_appimg);
    imageView.setAdjustViewBounds(true);

    imageView = (ImageView)rootView.findViewById(R.id.edittab_flowimg);
    imageView.setAdjustViewBounds(true);


    Button appli_reg_btn = (Button) rootView.findViewById(R.id.appli_reg_btn);
    Button flow_reg_btn = (Button) rootView.findViewById(R.id.flow_reg_btn);

    appli_reg_btn.setOnClickListener(this);
    flow_reg_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining actions of appli_reg_btn and flow_reg_btn
   * @details If you click appli_reg_btn, applireg.xml layout will appear.
   *            If you click flow_reg_btn, flowreg.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.appli_reg_btn:
        AppliReg appliReg = new AppliReg();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
        break;
      case R.id.flow_reg_btn:
        FlowReg flowReg = new FlowReg();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
        break;
    }
  }
}
