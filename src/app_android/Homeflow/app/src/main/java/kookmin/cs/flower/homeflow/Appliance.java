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

import kookmin.cs.flower.homeflow.data.DataSheet;

/**
 * @brief class for showing timecond
 * @details This class includes time_cond_btn. If you click time_cond_btn, workentry.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-06
 */
public class Appliance extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner appli_spin, func_spin;

  static ArrayList<String> funcList = new ArrayList<String>();

  static {
    funcList.add("ON");
    funcList.add("OFF");
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
    View rootView = inflater.inflate(R.layout.appliance, container, false);

    Button appliance_btn = (Button) rootView.findViewById(R.id.appliance_btn);
    appliance_btn.setOnClickListener(this);

    appli_spin = (Spinner) rootView.findViewById(R.id.appli_spin);
    func_spin = (Spinner) rootView.findViewById(R.id.func_spin);

    ArrayAdapter<kookmin.cs.flower.homeflow.data.Appliance>
        appliAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                DataSheet.getApplianceList());
    ArrayAdapter<String> funcAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, funcList);

    appli_spin.setAdapter(appliAdapter);
    func_spin.setAdapter(funcAdapter);

    appli_spin.setOnItemSelectedListener(this);
    func_spin.setOnItemSelectedListener(this);

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
    if (v.getId() == R.id.appliance_btn) {
      Bundle args = new Bundle();
      args.putString("test", "0");
      WorkEntry workEntry = new WorkEntry();
      workEntry.setArguments(args);
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }
}