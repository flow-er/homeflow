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
 * @brief class for showing timecond
 * @details This class includes time_cond_btn. If you click time_cond_btn, workentry.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class TimeLoop extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner hour_spin, minute_spin, second_spin;

  static ArrayList<String> hourList = new ArrayList<String>();
  static ArrayList<String> minuteList = new ArrayList<String>();
  static ArrayList<String> secondList = new ArrayList<String>();

  static {
    hourList.add("1");hourList.add("2");hourList.add("3");hourList.add("4");
    hourList.add("5");hourList.add("6");hourList.add("7");hourList.add("8");
    hourList.add("9");hourList.add("10");hourList.add("11");hourList.add("12");

    minuteList.add("0");minuteList.add("1");minuteList.add("2");minuteList.add("3");
    minuteList.add("4");minuteList.add("5");minuteList.add("6");minuteList.add("7");
    minuteList.add("8");minuteList.add("9");minuteList.add("10");minuteList.add("11");
    minuteList.add("12");minuteList.add("13");minuteList.add("14");minuteList.add("15");
    minuteList.add("16");minuteList.add("17");minuteList.add("18");minuteList.add("19");
    minuteList.add("20");minuteList.add("21");minuteList.add("22");minuteList.add("23");
    minuteList.add("24");minuteList.add("25");minuteList.add("26");minuteList.add("27");
    minuteList.add("28");minuteList.add("29");minuteList.add("30");minuteList.add("31");
    minuteList.add("32");minuteList.add("33");minuteList.add("34");minuteList.add("35");
    minuteList.add("36");minuteList.add("37");minuteList.add("38");minuteList.add("39");
    minuteList.add("40");minuteList.add("41");minuteList.add("42");minuteList.add("43");
    minuteList.add("44");minuteList.add("45");minuteList.add("46");minuteList.add("47");
    minuteList.add("48");minuteList.add("49");minuteList.add("50");minuteList.add("51");
    minuteList.add("52");minuteList.add("53");minuteList.add("54");minuteList.add("55");
    minuteList.add("56");minuteList.add("57");minuteList.add("58");minuteList.add("59");

    secondList.add("0");secondList.add("1");secondList.add("2");secondList.add("3");
    secondList.add("4");secondList.add("5");secondList.add("6");secondList.add("7");
    secondList.add("8");secondList.add("9");secondList.add("10");secondList.add("11");
    secondList.add("12");secondList.add("13");secondList.add("14");secondList.add("15");
    secondList.add("16");secondList.add("17");secondList.add("18");secondList.add("19");
    secondList.add("20");secondList.add("21");secondList.add("22");secondList.add("23");
    secondList.add("24");secondList.add("25");secondList.add("26");secondList.add("27");
    secondList.add("28");secondList.add("29");secondList.add("30");secondList.add("31");
    secondList.add("32");secondList.add("33");secondList.add("34");secondList.add("35");
    secondList.add("36");secondList.add("37");secondList.add("38");secondList.add("39");
    secondList.add("40");secondList.add("41");secondList.add("42");secondList.add("43");
    secondList.add("44");secondList.add("45");secondList.add("46");secondList.add("47");
    secondList.add("48");secondList.add("49");secondList.add("50");secondList.add("51");
    secondList.add("52");secondList.add("53");secondList.add("54");secondList.add("55");
    secondList.add("56");secondList.add("57");secondList.add("58");secondList.add("59");
  }
  /**
   * @brief method for showing timecond.xml layout
   * @details This method sets a clicked-event on time_cond_btn
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.timeloop, container, false);

    Button time_loop_btn = (Button) rootView.findViewById(R.id.time_loop_btn);
    time_loop_btn.setOnClickListener(this);

    hour_spin = (Spinner) rootView.findViewById(R.id.hour_spin);
    minute_spin = (Spinner) rootView.findViewById(R.id.minute_spin);
    second_spin = (Spinner) rootView.findViewById(R.id.second_spin);

    ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, hourList);
    ArrayAdapter<String> minuteAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, minuteList);
    ArrayAdapter<String> secondAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, secondList);

    hour_spin.setAdapter(hourAdapter);
    minute_spin.setAdapter(minuteAdapter);
    second_spin.setAdapter(secondAdapter);

    hour_spin.setOnItemSelectedListener(this);
    minute_spin.setOnItemSelectedListener(this);
    second_spin.setOnItemSelectedListener(this);

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
   * @brief method for determining action of time_cond_btn
   * @details If you click time_cond_btn, workentry.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.time_loop_btn) {
      WorkEntry workEntry = new WorkEntry();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }
}