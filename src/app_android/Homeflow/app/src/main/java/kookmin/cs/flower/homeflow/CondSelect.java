package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing condselect.xml layout
 * @details This class includes cond_classify_spin spinner and cond_select_btn button You can select
 * condition category by clicking cond_classify_spin. If you click cond_select_btn, timecond.xml
 * layout will appear.
 * @date 2015-04-06
 */
public class CondSelect extends Fragment implements View.OnClickListener {

  Spinner cond_classify_spin;

  static ArrayList<String> condSelectList = new ArrayList<String>();

  static {
    condSelectList.add("시간");
    condSelectList.add("다른 조건");
  }

  /**
   * @return rootView
   * @brief method for showing condselect.xml layout
   * @details This method sets a clicked-event on cond_select_btn
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.condselect, container, false);

    cond_classify_spin = (Spinner) rootView.findViewById(R.id.cond_classify_spin);

    ArrayAdapter<String>
        condSelectAdapter =
        new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1,
                           condSelectList);
    cond_classify_spin.setAdapter(condSelectAdapter);

    //cond_classify_spin.setOnItemSelectedListener(this);

    Button cond_select_btn = (Button) rootView.findViewById(R.id.cond_select_btn);
    cond_select_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining action of cond_select_btn
   * @detail If you click cond_select_btn, timecond.xml layout will appear.
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.cond_select_btn) {
      TimeCond timeCond = new TimeCond();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, timeCond).commit();
    }
  }
}
