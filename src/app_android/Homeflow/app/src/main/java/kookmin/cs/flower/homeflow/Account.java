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
 * @brief class for showing account.xml layout
 * @details This class includes signup_btn.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.6
 * @date 2015-05-27
 */
public class Account extends Fragment implements View.OnClickListener,
                                                 AdapterView.OnItemSelectedListener {

  Spinner age_spin, sex_spin, job_spin, maintime_spin;

  static ArrayList<String> ageList = new ArrayList<String>();
  static ArrayList<String> sexList = new ArrayList<String>();
  static ArrayList<String> jobList = new ArrayList<String>();
  static ArrayList<String> mainTimeList = new ArrayList<String>();

  static {
    ageList.add("10대");
    ageList.add("20대");
    ageList.add("30대");
    ageList.add("40대");
    ageList.add("50대");
    ageList.add("60대");

    sexList.add("남");
    sexList.add("여");

    jobList.add("학생");
    jobList.add("직장인");
    jobList.add("주부");

    mainTimeList.add("오전");
    mainTimeList.add("오후");
    mainTimeList.add("밤");
  }

  /**
   * @brief method for showing account.xml layout
   * @details This method sets clicked-event on signup_btn and selected-events on age_spin, sex_spin, job_spin and maintime_spin.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.account, container, false);

    age_spin = (Spinner) rootView.findViewById(R.id.age_spin);
    sex_spin = (Spinner) rootView.findViewById(R.id.sex_spin);
    job_spin = (Spinner) rootView.findViewById(R.id.job_spin);
    maintime_spin = (Spinner) rootView.findViewById(R.id.maintime_spin);

    ArrayAdapter<String>
        selAgeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ageList);
    age_spin.setAdapter(selAgeAdapter);
    ArrayAdapter<String>
        selSexAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sexList);
    sex_spin.setAdapter(selSexAdapter);
    ArrayAdapter<String>
        selJobAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, jobList);
    job_spin.setAdapter(selJobAdapter);
    ArrayAdapter<String>
        selMainTimeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mainTimeList);
    maintime_spin.setAdapter(selMainTimeAdapter);

    age_spin.setOnItemSelectedListener(this);
    sex_spin.setOnItemSelectedListener(this);
    job_spin.setOnItemSelectedListener(this);
    maintime_spin.setOnItemSelectedListener(this);

    Button signup_btn = (Button)rootView.findViewById(R.id.signup_btn);
    signup_btn.setOnClickListener(this);

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
   * @brief method for determining action of signup_btn
   * @details
   * @param v
   */
  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.signup_btn) {

    }
  }
}