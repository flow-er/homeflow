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
 * @brief class for showing temperature
 * @details This class includes temper_spin, comp_spin and temperature_btn.
 *            If you click temperature_btn, workentry.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-27
 */
public class Temperature extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner temper_spin, comp_spin, act_count_spin;

  static ArrayList<String> actCountList = new ArrayList<String>();

  static {
    actCountList.add("선택하세요.");
    actCountList.add("루프 반복");
    actCountList.add("1번");actCountList.add("2번");actCountList.add("3번");actCountList.add("4번");
    actCountList.add("5번");actCountList.add("6번");actCountList.add("7번");actCountList.add("8번");
    actCountList.add("9번");actCountList.add("10번");actCountList.add("11번");actCountList.add("12번");
    actCountList.add("13번");actCountList.add("14번");actCountList.add("15번");actCountList.add("16번");
    actCountList.add("17번");actCountList.add("18번");actCountList.add("19번");actCountList.add("20번");
  }

  static ArrayList<String> temperList = new ArrayList<String>();
  static ArrayList<String> compList = new ArrayList<String>();

  static {
    temperList.add("0℃");temperList.add("1℃");temperList.add("2℃");temperList.add("3℃");
    temperList.add("4℃");temperList.add("5℃");temperList.add("6℃");temperList.add("7℃");
    temperList.add("8℃");temperList.add("9℃");temperList.add("10℃");temperList.add("11℃");
    temperList.add("12℃");temperList.add("13℃");temperList.add("14℃");temperList.add("15℃");
    temperList.add("16℃");temperList.add("17℃");temperList.add("18℃");temperList.add("19℃");
    temperList.add("20℃");temperList.add("21℃");temperList.add("22℃");temperList.add("23℃");
    temperList.add("24℃");temperList.add("25℃");temperList.add("26℃");temperList.add("27℃");
    temperList.add("28℃");temperList.add("29℃");temperList.add("30℃");temperList.add("31℃");
    temperList.add("32℃");temperList.add("33℃");temperList.add("34℃");temperList.add("35℃");
    temperList.add("36℃");temperList.add("37℃");temperList.add("38℃");temperList.add("39℃");
    temperList.add("40℃");

    compList.add("<");compList.add("<=");
    compList.add(">");compList.add(">=");
    compList.add("==");
  }
  /**
   * @brief method for showing temperature.xml layout
   * @details This method sets select-events on temper_spin and comp_spin, and clicked_events on temperature_btn.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.temperature, container, false);

    act_count_spin = (Spinner) rootView.findViewById(R.id.act_count_spin);

    ArrayAdapter<String> actCountAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, actCountList);
    act_count_spin.setAdapter(actCountAdapter);

    act_count_spin.setOnItemSelectedListener(this);

    Button temperature_btn = (Button) rootView.findViewById(R.id.temperature_btn);
    temperature_btn.setOnClickListener(this);

    temper_spin = (Spinner) rootView.findViewById(R.id.temper_spin);
    comp_spin = (Spinner) rootView.findViewById(R.id.comp_spin);

    ArrayAdapter<String> temperAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, temperList);
    ArrayAdapter<String> compAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, compList);

    temper_spin.setAdapter(temperAdapter);
    comp_spin.setAdapter(compAdapter);

    temper_spin.setOnItemSelectedListener(this);
    comp_spin.setOnItemSelectedListener(this);

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

    if(((TextView)parent.getChildAt(0)).getText().toString().equals("루프 반복")) {
      LoopSelect loopSelect = new LoopSelect();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, loopSelect).commit();
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
  /**
   * @brief method for determining action of temperature_btn
   * @details If you click temperature_btn, workentry.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.temperature_btn) {
      AddFlow addFlow = new AddFlow();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addFlow).commit();
    }
  }
}
