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
 * @date 2015-04-06
 */
public class AddAppli extends Fragment implements View.OnClickListener {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.addappli, container, false);

    Button add_appli_btn = (Button) rootView.findViewById(R.id.add_appli_btn);

    add_appli_btn.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.add_appli_btn) {
      AppliReg appliReg = new AppliReg();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
    }
  }
}
