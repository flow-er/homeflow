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
public class EditTab extends Fragment implements View.OnClickListener {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.edittab, container, false);

    Button appli_reg_btn = (Button) rootView.findViewById(R.id.appli_reg_btn);
    Button flow_reg_btn = (Button) rootView.findViewById(R.id.flow_reg_btn);
    //이벤트 등록
    appli_reg_btn.setOnClickListener(this);
    flow_reg_btn.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.appli_reg_btn:
        AppliReg appliReg = new AppliReg(); // 프래그먼트 생성
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliReg).commit();
        break;
      case R.id.flow_reg_btn:
        FlowReg flowReg = new FlowReg(); // 프래그먼트 생성
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
        break;
    }
  }
}
