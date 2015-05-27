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
 * @brief class for showing appliance.xml
 * @details This class includes appli_spin, func_spin and appliance_btn.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-05-27
 */
public class Appliance extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

  Spinner appli_spin, func_spin;

  static ArrayList<String> appliList = new ArrayList<String>();
  static ArrayList<String> funcList = new ArrayList<String>();

  static {
    appliList.add("기기1");
    appliList.add("기기2");
    appliList.add("기기3");
    appliList.add("기기4");
    appliList.add("기기5");

    funcList.add("ON");
    funcList.add("OFF");
  }
  /**
   * @brief method for showing appliance.xml layout
   * @details This method sets selected-events on spinners and clicked-event on appliance_btn.
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.appliance, container, false);

    Button appliance_btn = (Button) rootView.findViewById(R.id.appliance_btn);
    appliance_btn.setOnClickListener(this);

    appli_spin = (Spinner) rootView.findViewById(R.id.appli_spin);
    func_spin = (Spinner) rootView.findViewById(R.id.func_spin);

    ArrayAdapter<String>
        appliAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, appliList);
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
   * @brief method for determining action of appliance_btn
   * @details If you click appliance_btn, workentry.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.appliance_btn) {
      WorkEntry workEntry = new WorkEntry();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }
  }
}
