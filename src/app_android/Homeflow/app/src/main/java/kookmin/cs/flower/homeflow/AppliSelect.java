package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;

/**
 * @brief class for showing appliselect.xml layout
 * @details This class includes sel_appli_name_spin spinner, set_appli_func_spin spinner, alarm_config_spin spinner,
 *            alarm_time_spin spinner, and appli_select_btn button.
 *            You can select appliance by clicking sel_appli_name_spin.
 *            You can select appliance function by clicking sel_appli_func_spin.
 *            You can determine alarm configuration by clicking alarm_config_spin.
 *            You can determine alarm time by clicking alarm_time_spin.
 *            If you click appli_select_btn, workentry.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class AppliSelect extends Fragment implements View.OnClickListener,
                                                     AdapterView.OnItemSelectedListener {

  Spinner sel_appli_name_spin, sel_appli_func_spin, alarm_config_spin, alarm_time_spin;
  String argu="";
  static ArrayList<String> selAppliFuncList = new ArrayList<String>();
  static ArrayList<String> alarmConfigList = new ArrayList<String>();
  static ArrayList<String> alarmTimeList = new ArrayList<String>();

  static {
    selAppliFuncList.add("ON");
    selAppliFuncList.add("OFF");

    alarmConfigList.add("ON");
    alarmConfigList.add("OFF");

    alarmTimeList.add("완료 시");
  }

  /**
   * @brief method for showing appliselect.xml layout
   * @details This method sets a selected-event on sel_appli_name_spin, and a clicked_event on appli_select_btn.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.appliselect, container, false);

    sel_appli_name_spin = (Spinner) rootView.findViewById(R.id.sel_appli_name_spin);
    sel_appli_func_spin = (Spinner) rootView.findViewById(R.id.sel_appli_func_spin);
    alarm_config_spin = (Spinner) rootView.findViewById(R.id.alarm_config_spin);
    alarm_time_spin = (Spinner) rootView.findViewById(R.id.alarm_time_spin);

    ArrayAdapter<String> selAppliNameAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                             FileManager.getApplianceList());
    sel_appli_name_spin.setAdapter(selAppliNameAdapter);
    ArrayAdapter<String> selAppliFuncAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, selAppliFuncList);
    sel_appli_func_spin.setAdapter(selAppliFuncAdapter);
    ArrayAdapter<String> alarmConfigAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, alarmConfigList);
    alarm_config_spin.setAdapter(alarmConfigAdapter);
    ArrayAdapter<String> alarmTimeAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, alarmTimeList);
    alarm_time_spin.setAdapter(alarmTimeAdapter);

    sel_appli_name_spin.setOnItemSelectedListener(this);

    Button appli_select_btn = (Button) rootView.findViewById(R.id.appli_select_btn);
    appli_select_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining action of appli_select_btn
   * @details If you click appli_select_btn, workentry.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.appli_select_btn) {
      Bundle bundle = new Bundle();
      WorkEntry workEntry = new WorkEntry();
      Log.i("mytag", argu);

      bundle.putString("result", argu);
      workEntry.setArguments(bundle);

      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    argu = FileManager.getApplianceList().get(adapterView.getSelectedItemPosition());
    Log.i("mytag", "position : " + adapterView.getSelectedItemPosition() + " " +argu);

  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}