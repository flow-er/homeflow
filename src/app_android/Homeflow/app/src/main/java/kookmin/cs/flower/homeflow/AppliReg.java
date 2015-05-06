package kookmin.cs.flower.homeflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @brief class for showing applireg.xml layout
 * @details This class includes appli_reg_to_edit button, appli_reg_add button, and appli_reg_del button.
 *            If you click appli_reg_to_edit, edittab.xml layout will appear.
 *            If you click appli_reg_add, addappli.xml layout will appear.
 *            If you click appli_reg_del, delappli.xml layout will appear.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-06
 */
public class AppliReg extends Fragment implements View.OnClickListener {

  ListView listView;

  /**
   * @brief method for showing applireg.xml layout
   * @details This method sets clicked-events on appli_reg_to_edit, appli_reg_add, and appli_reg_del
   * @param inflater
   * @param container
   * @param savedInstanceState
   * @return rootView
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.applireg, container, false);

    Button appli_reg_to_edit = (Button) rootView.findViewById(R.id.appli_reg_to_edit);
    Button appli_reg_add = (Button) rootView.findViewById(R.id.appli_reg_add);
    Button appli_reg_del = (Button) rootView.findViewById(R.id.appli_reg_del);

    appli_reg_to_edit.setOnClickListener(this);
    appli_reg_add.setOnClickListener(this);
    appli_reg_del.setOnClickListener(this);

    ArrayList<MyCustomDTO2> appliRegList = new ArrayList<MyCustomDTO2>();
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));
    appliRegList.add(new MyCustomDTO2("appliance"));

    listView = (ListView) rootView.findViewById(R.id.appli_reg_list);/*
    ArrayAdapter appliRegAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                                                    FileManager.getApplianceList());*/


    MyCustomAdapter2 appliRegAdapter = new MyCustomAdapter2(getActivity(), R.layout.appliregrow, appliRegList);
    listView.setAdapter(appliRegAdapter);

    return rootView;
  }

  /**
   * @brief method for determining actions of appli_reg_to_edit, appli_reg_add, and appli_reg_del
   * @details If you click appli_reg_to_edit, edittab.xml layout will appear.
   *            If you click appli_reg_add, addappli.xml layout will appear.
   *            If you click appli_reg_del, delappli.xml layout will appear.
   * @param v
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.appli_reg_to_edit:
        EditTab editTab = new EditTab();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, editTab).commit();
        break;
      case R.id.appli_reg_add:
        AddAppli addAppli = new AddAppli();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, addAppli).commit();
        break;
      case R.id.appli_reg_del:
        DelAppli delAppli = new DelAppli();
        getFragmentManager().beginTransaction().replace(R.id.realtabcontent, delAppli).commit();
        break;
    }
  }
}