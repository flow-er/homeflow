package kookmin.cs.flower.homeflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @brief class for showing condselect.xml layout
 * @details This class includes cond_classify_spin spinner and cond_select_btn button
 *            You can select condition category by clicking cond_classify_spin.
 *            If you click cond_select_btn, timecond.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class CondSelect extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner cond_classify_spin;

  static ArrayList<String> condSelectList = new ArrayList<String>();

  static {
    condSelectList.add("바로 실행");
    condSelectList.add("시간");
    condSelectList.add("온도");
    condSelectList.add("습도");
    condSelectList.add("기기 상태");
  }

  /**
   * @brief method for showing condselect.xml layout
   * @details This method sets a clicked-event on cond_select_btn
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.condselect, container, false);

    cond_classify_spin = (Spinner) rootView.findViewById(R.id.cond_classify_spin);

    ArrayAdapter<String> condSelectAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, condSelectList);
    cond_classify_spin.setAdapter(condSelectAdapter);

    cond_classify_spin.setOnItemSelectedListener(this);

    Button cond_select_btn = (Button) rootView.findViewById(R.id.cond_select_btn);
    cond_select_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for showing action of spinners when selected
   * @details This method sets selected-events on the spinners.
   * @param parent
   * @param v
   * @param position
   * @param id
   * @return
   */
  @Override
  public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
  }

  /**
   * @brief method for showing action of spinners when they are not selected
   * @details This method sets events if the spinners aren't selected.
   * @param parent
   * @return
   */
  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }

  /**
   * @brief method for determining action of cond_select_btn
   * @detail If you click cond_select_btn, timecond.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.cond_select_btn) {
      TimeCond timeCond = new TimeCond();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, timeCond).commit();
    }
  }
}
