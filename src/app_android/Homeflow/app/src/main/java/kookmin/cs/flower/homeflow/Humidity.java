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
 * @brief class for showing humidity.xml
 * @details This class includes humid_spin, comp_spin and humidity_btn.
 *            If you click humidity_btn, workentry.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-27
 */
public class Humidity extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner humid_spin, comp_spin, act_count_spin;

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

  static ArrayList<String> humidList = new ArrayList<String>();
  static ArrayList<String> compList = new ArrayList<String>();

  static {
    humidList.add("0%");humidList.add("1%");humidList.add("2%");humidList.add("3%");humidList.add("4%");
    humidList.add("5%");humidList.add("6%");humidList.add("7%");humidList.add("8%");humidList.add("9%");
    humidList.add("10%");humidList.add("11%");humidList.add("12%");humidList.add("13%");humidList.add("14%");
    humidList.add("15%");humidList.add("16%");humidList.add("17%");humidList.add("18%");humidList.add("19%");
    humidList.add("20%");humidList.add("21%");humidList.add("22%");humidList.add("23%");humidList.add("24%");
    humidList.add("25%");humidList.add("26%");humidList.add("27%");humidList.add("28%");humidList.add("29%");
    humidList.add("30%");humidList.add("31%");humidList.add("32%");humidList.add("33%");humidList.add("34%");
    humidList.add("35%");humidList.add("36%");humidList.add("37%");humidList.add("38%");humidList.add("39%");
    humidList.add("40%");humidList.add("41%");humidList.add("42%");humidList.add("43%");humidList.add("44%");
    humidList.add("45%");humidList.add("46%");humidList.add("47%");humidList.add("48%");humidList.add("49%");
    humidList.add("50%");humidList.add("51%");humidList.add("52%");humidList.add("53%");humidList.add("54%");
    humidList.add("55%");humidList.add("56%");humidList.add("57%");humidList.add("58%");humidList.add("59%");
    humidList.add("60%");humidList.add("61%");humidList.add("62%");humidList.add("63%");humidList.add("64%");
    humidList.add("65%");humidList.add("66%");humidList.add("67%");humidList.add("68%");humidList.add("69%");
    humidList.add("70%");humidList.add("71%");humidList.add("72%");humidList.add("73%");humidList.add("74%");
    humidList.add("75%");humidList.add("76%");humidList.add("77%");humidList.add("78%");humidList.add("79%");
    humidList.add("80%");humidList.add("81%");humidList.add("82%");humidList.add("83%");humidList.add("84%");
    humidList.add("85%");humidList.add("86%");humidList.add("87%");humidList.add("88%");humidList.add("89%");
    humidList.add("90%");humidList.add("91%");humidList.add("92%");humidList.add("93%");humidList.add("94%");
    humidList.add("95%");humidList.add("96%");humidList.add("97%");humidList.add("98%");humidList.add("99%");
    humidList.add("99%");

    compList.add("<");compList.add("<=");
    compList.add(">");compList.add(">=");
    compList.add("==");
  }

  /**
   * @brief method for showing humidity.xml layout
   * @details This method sets a clicked-event on humidity_btn
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.humidity, container, false);

    act_count_spin = (Spinner) rootView.findViewById(R.id.act_count_spin);

    ArrayAdapter<String> actCountAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, actCountList);
    act_count_spin.setAdapter(actCountAdapter);

    act_count_spin.setOnItemSelectedListener(this);

    Button humidity_btn = (Button) rootView.findViewById(R.id.humidity_btn);
    humidity_btn.setOnClickListener(this);

    humid_spin = (Spinner) rootView.findViewById(R.id.humid_spin);
    comp_spin = (Spinner) rootView.findViewById(R.id.comp_spin);

    ArrayAdapter<String>
        humidAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, humidList);
    ArrayAdapter<String> compAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, compList);

    humid_spin.setAdapter(humidAdapter);
    comp_spin.setAdapter(compAdapter);

    humid_spin.setOnItemSelectedListener(this);
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
   * @brief method for determining action of humidity_btn
   * @details If you click humidity_btn, workentry.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.humidity_btn) {
      AddFlow addFlow = new AddFlow();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addFlow).commit();
    }
  }
}
