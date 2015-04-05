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

import kookmin.cs.flower.homeflow.filestream.FileContent;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class AppliSelect extends Fragment implements View.OnClickListener {

  Spinner sel_appli_name_spin, sel_appli_func_spin, alarm_config_spin, alarm_time_spin;

  static ArrayList<String> list8 = new ArrayList<String>();
  static ArrayList<String> list9 = new ArrayList<String>();
  static ArrayList<String> list10 = new ArrayList<String>();

  static {
    list8.add("ON");
    list8.add("OFF");

    list9.add("ON");
    list9.add("OFF");

    list10.add("완료 시");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.appliselect, container, false);

    sel_appli_name_spin = (Spinner) rootView.findViewById(R.id.sel_appli_name_spin);
    sel_appli_func_spin = (Spinner) rootView.findViewById(R.id.sel_appli_func_spin);
    alarm_config_spin = (Spinner) rootView.findViewById(R.id.alarm_config_spin);
    alarm_time_spin = (Spinner) rootView.findViewById(R.id.alarm_time_spin);

    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                             FileContent.getApplianceList());
    sel_appli_name_spin.setAdapter(adapter2);
    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list8);
    sel_appli_func_spin.setAdapter(adapter3);
    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list9);
    alarm_config_spin.setAdapter(adapter4);
    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, list10);
    alarm_time_spin.setAdapter(adapter5);

    Button appli_select_btn = (Button) rootView.findViewById(R.id.appli_select_btn);
    appli_select_btn.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.appli_select_btn) {
      WorkEntry workEntry = new WorkEntry();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }
}