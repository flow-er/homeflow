package kookmin.cs.flower.homeflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class LoopSelect extends Fragment implements AdapterView.OnItemSelectedListener {

  Spinner loop_classify_spin;

  static ArrayList<String> loopSelectList = new ArrayList<String>();

  static {
    loopSelectList.add("선택하세요.");
    loopSelectList.add("시간");
    loopSelectList.add("온도");
    loopSelectList.add("습도");
    loopSelectList.add("기기 상태");
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
    View rootView = inflater.inflate(R.layout.loopselect, container, false);

    loop_classify_spin = (Spinner) rootView.findViewById(R.id.loop_classify_spin);

    ArrayAdapter<String> condSelectAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, loopSelectList);
    loop_classify_spin.setAdapter(condSelectAdapter);

    loop_classify_spin.setOnItemSelectedListener(this);

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

    if(((TextView)parent.getChildAt(0)).getText().toString().equals("시간")) {
      TimeLoop timeLoop = new TimeLoop();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, timeLoop).commit();
    }
    else if(((TextView)parent.getChildAt(0)).getText().toString().equals("온도")) {
      Temperature temperature = new Temperature();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, temperature).commit();
    }
    else if(((TextView)parent.getChildAt(0)).getText().toString().equals("습도")) {
      Humidity humidity = new Humidity();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, humidity).commit();
    }
    else if(((TextView)parent.getChildAt(0)).getText().toString().equals("기기 상태")) {
      Appliance appliance = new Appliance();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, appliance).commit();
    }
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
}