package kookmin.cs.flower.homeflow;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.Service.PushActivity;
import kookmin.cs.flower.homeflow.Service.SocketService;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @brief class for showing account.xml layout
 * @details This class includes signup_btn.
 * @date 2015-05-016
 */

public class Account extends Fragment implements View.OnClickListener,
                                                 AdapterView.OnItemSelectedListener {

  public static String USER_ID = "user_id";
  public static String USER_PASS = "user_passwd";
  public static String USER_AGE = "user_age";
  public static String USER_GENDER = "user_gender";
  public static String USER_JOB = "user_job";
  public static String USER_ATIV_TIME = "user_ativ_time";

  Spinner age_spin, sex_spin, job_spin, maintime_spin;

  static ArrayList<String> ageList = new ArrayList<>();
  static ArrayList<String> sexList = new ArrayList<>();
  static ArrayList<String> jobList = new ArrayList<>();
  static ArrayList<String> mainTimeList = new ArrayList<>();

  private String reg_id = "";

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
   * @return rootView
   * @brief method for showing account.xml layout
   * @details This method sets clicked-events on signup_btn.
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
        selAgeAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ageList);
    age_spin.setAdapter(selAgeAdapter);
    ArrayAdapter<String>
        selSexAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, sexList);
    sex_spin.setAdapter(selSexAdapter);
    ArrayAdapter<String>
        selJobAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, jobList);
    job_spin.setAdapter(selJobAdapter);
    ArrayAdapter<String>
        selMainTimeAdapter =
        new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mainTimeList);
    maintime_spin.setAdapter(selMainTimeAdapter);

    age_spin.setOnItemSelectedListener(this);
    sex_spin.setOnItemSelectedListener(this);
    job_spin.setOnItemSelectedListener(this);
    maintime_spin.setOnItemSelectedListener(this);

    Button signup_btn = (Button) rootView.findViewById(R.id.signup_btn);
    signup_btn.setOnClickListener(this);

    startActivityForResult(new Intent(getActivity().getApplicationContext(), PushActivity.class),
                           1000);

    return rootView;
  }

  /**
   * @brief method for showing action of spinners when selected
   * @details This method sets selected-events on the spinners.
   */
  @Override
  public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
  }

  /**
   * @brief method for showing action of spinners when they are not selected
   * @details This method sets events if the spinners aren't selected.
   */
  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }

  /**
   * @brief method for determining action of signup_btn
   * @details
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.signup_btn) {
      Intent sendData = new Intent(getActivity().getApplicationContext(), SocketService.class);
      sendData.putExtra("mode", 2);
      sendData.putExtra(PushActivity.PROPERTY_REG_ID, reg_id);
      sendData.putExtra(USER_ID, "tempid");
      sendData.putExtra(USER_PASS, "1234");
      sendData.putExtra(USER_AGE, "20");
      sendData.putExtra(USER_GENDER, "man");
      sendData.putExtra(USER_JOB, "student");
      sendData.putExtra(USER_ATIV_TIME, "afternoon");

      getActivity().startService(sendData);
      Toast.makeText(getActivity().getApplicationContext(), "계정이 생성되었습니다.", Toast.LENGTH_SHORT)
          .show();
      goLogin();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1000) {
      reg_id = data.getStringExtra(PushActivity.PROPERTY_REG_ID);

      if (reg_id.isEmpty()) {
        Toast.makeText(getActivity().getApplicationContext(), "APK를 지원하지 않아 계정을 만들 수 없습니다.",
                       Toast.LENGTH_SHORT).show();
        goLogin();
      }
    }
  }

  private void goLogin() {
    getFragmentManager().beginTransaction().replace(R.id.personal_frag, new Login()).commit();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
  }
}
